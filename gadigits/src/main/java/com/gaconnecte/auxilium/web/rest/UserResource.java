package com.gaconnecte.auxilium.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.gaconnecte.auxilium.config.Constants;
import com.gaconnecte.auxilium.domain.User;
import com.gaconnecte.auxilium.repository.UserRepository;
import com.gaconnecte.auxilium.repository.search.UserSearchRepository;
import com.gaconnecte.auxilium.security.AuthoritiesConstants;
import com.gaconnecte.auxilium.security.SecurityUtils;
import com.gaconnecte.auxilium.service.CustomUserService;
import com.gaconnecte.auxilium.service.HistoryService;
import com.gaconnecte.auxilium.service.MailService;
import com.gaconnecte.auxilium.service.UserService;
import com.gaconnecte.auxilium.service.UserExtraService;
import com.gaconnecte.auxilium.service.dto.UserDTO;
import com.gaconnecte.auxilium.service.dto.PermissionAccessDTO;
import com.gaconnecte.auxilium.service.dto.UserExtraDTO;
import com.gaconnecte.auxilium.service.mapper.UserMapper;
import com.gaconnecte.auxilium.service.util.ExcelUtil;
import com.gaconnecte.auxilium.web.rest.util.HeaderUtil;
import com.gaconnecte.auxilium.web.rest.util.PaginationUtil;
import com.gaconnecte.auxilium.web.rest.vm.ManagedUserVM;

import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * REST controller for managing users.
 * <p>
 * This class accesses the User entity, and needs to fetch its collection of authorities.
 * <p>
 * For a normal use-case, it would be better to have an eager relationship between User and Authority,
 * and send everything to the client side: there would be no View Model and DTO, a lot less code, and an outer-join
 * which would be good for performance.
 * <p>
 * We use a View Model and a DTO for 3 reasons:
 * <ul>
 * <li>We want to keep a lazy association between the user and the authorities, because people will
 * quite often do relationships with the user, and we don't want them to get the authorities all
 * the time for nothing (for performance reasons). This is the #1 goal: we should not impact our users'
 * application because of this use-case.</li>
 * <li> Not having an outer join causes n+1 requests to the database. This is not a real issue as
 * we have by default a second-level cache. This means on the first HTTP call we do the n+1 requests,
 * but then all authorities come from the cache, so in fact it's much better than doing an outer join
 * (which will get lots of data from the database, for each HTTP call).</li>
 * <li> As this manages users, for security reasons, we'd rather have a DTO layer.</li>
 * </ul>
 * <p>
 * Another option would be to have a specific JPA entity graph to handle this case.
 */
@RestController("JhipsterUserResource")
@RequestMapping("/api")
public class UserResource {

    private final Logger log = LoggerFactory.getLogger(UserResource.class);

    private static final String ENTITY_NAME = "userManagement";
    private static final String APPLICATION_NAME = "auxiliumApp";

    private final UserRepository userRepository;

    private final MailService mailService;

    private final UserMapper userMapper;

    private final UserService userService;

    private final UserExtraService userExtraService;
    
    @Autowired
    private HistoryService historyService;

    @Autowired
    private CustomUserService customUserService;

    private User user = new User();

    private final UserSearchRepository userSearchRepository;

    public UserResource(UserMapper userMapper, UserRepository userRepository, MailService mailService,
			UserExtraService userExtraService, UserService userService, UserSearchRepository userSearchRepository) {

        this.userRepository = userRepository;
        this.mailService = mailService;
        this.userMapper = userMapper;
        this.userService = userService;
        this.userExtraService = userExtraService;
        this.userSearchRepository = userSearchRepository;
    }

    /**
     * POST  /users  : Creates a new user.
     * <p>
     * Creates a new user if the login and email are not already used, and sends an
     * mail with an activation link.
     * The user needs to be activated on creation.
     *
     * @param managedUserVM the user to create
     * @return the ResponseEntity with status 201 (Created) and with body the new user, or with status 400 (Bad Request) if the login or email is already in use
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/users")
    @Timed 
    //@Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity createUser(@Valid @RequestBody ManagedUserVM managedUserVM) throws URISyntaxException {
        log.debug("REST request to save  User : {}", managedUserVM);
        if (managedUserVM.getId() != null) {
            return ResponseEntity.badRequest()
                    .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new user cannot already have an ID"))
                    .body(null);
        } else if (userRepository.findOneByEmailIgnoreCase(managedUserVM.getEmail()).isPresent()) {
            return ResponseEntity.badRequest()
                    .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "emailexists", "Email already in use"))
                    .body(null);
        } else {
            User newUser = userService.createUser(managedUserVM);
            //customUserService.createAcitivitiUser(newUser);
            //customUserService.createPersonnePhysiqueFromUser(newUser);
            mailService.sendCreationEmail(newUser);
            this.user = newUser;
            User historyUser = new User();
            historyUser = historyUser(newUser, historyUser);
            if (historyUser != null) {
                historyService.historysave("User", historyUser.getId(), null, historyUser, 0, 1, "Création");
            }
            return ResponseEntity.created(new URI("/api/users/" + newUser.getId()))
                    .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, newUser.getLogin()))
                    .body(newUser);
        }

    }
    
    @PostMapping("/usersDto")
    @Timed
    //@Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity createUserDto(@Valid @RequestBody ManagedUserVM managedUserVM) throws URISyntaxException {
        log.debug("REST request to save User : {}", managedUserVM);
        
        if (managedUserVM.getId() != null) {
            return ResponseEntity.badRequest()
                .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new user cannot already have an ID"))
                .body(null);
        } else if (userRepository.findOneByEmailIgnoreCase(managedUserVM.getEmail()).isPresent()) {
            return ResponseEntity.badRequest()
                .headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "emailexists", "Email already in use"))
                .body(null);
        } else {
            UserDTO newUser = userService.createUserReturnDto(managedUserVM);
        //    customUserService.createAcitivitiUser(newUser);
            //customUserService.createPersonnePhysiqueFromUser(newUser);
            mailService.sendCreationEmailDto(newUser);
          
            return ResponseEntity.created(new URI("/api/usersDto/" + newUser.getId()))
                .headers(HeaderUtil.createAlert(ENTITY_NAME, newUser.getId().toString()))
                .body(newUser);
        }

    }

    /**
     * PUT  /users : Updates an existing User.
     *
     * @param managedUserVM the user to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated user,
     * or with status 400 (Bad Request) if the login or email is already in use,
     * or with status 500 (Internal Server Error) if the user couldn't be updated
     */
    @PutMapping("/users")
    @Timed
    //@Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity updateUser(@Valid @RequestBody ManagedUserVM managedUserVM) throws URISyntaxException {
        log.debug("REST request to update User : {}", managedUserVM);
        
        //log.debug("REST request to number authority update User : {}", managedUserVM.getAuthorities().size());
        //User oldUser = userRepository.findOneById(managedUserVM.getId()).get();
        
        UserExtraDTO oldUserExtra = userExtraService.findOne(managedUserVM.getId());
        Optional<User> existingUser = userRepository.findOneByEmailIgnoreCase(managedUserVM.getEmail());
        if (existingUser.isPresent() && (!existingUser.get().getId().equals(managedUserVM.getId()))) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "emailexists", "Email already in use")).body(null);
        }
        existingUser = userRepository.findOneByLoginIgnoreCase(managedUserVM.getLogin().toLowerCase());
        if (existingUser.isPresent() && (!existingUser.get().getId().equals(managedUserVM.getId()))) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "userexists", "Login already in use")).body(null);
        }
        User newUser = userService.updateUser(managedUserVM);
        UserExtraDTO userExtra = userExtraService.findOne(newUser.getId());
        //historyService.historysave("User", newUser.getId(), oldUser, newUser, 0, 1, "Modification");
        historyService.historysaveUser("User",userExtra.getId(),oldUserExtra,userExtra, "Modification");
        
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert( ENTITY_NAME , newUser.getLogin()))
                .body(newUser); 
       
       
    }
    
    @PutMapping("/usersDto")
    @Timed
    //@Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<UserDTO> updateUserDto(@Valid @RequestBody ManagedUserVM managedUserVM) {
        log.debug("REST request to update User : {}", managedUserVM);
        Optional<User> existingUser = userRepository.findOneByEmailIgnoreCase(managedUserVM.getEmail());
        if (existingUser.isPresent() && (!existingUser.get().getId().equals(managedUserVM.getId()))) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "emailexists", "Email already in use")).body(null);
        }
        existingUser = userRepository.findOneByLoginIgnoreCase(managedUserVM.getLogin().toLowerCase());
        if (existingUser.isPresent() && (!existingUser.get().getId().equals(managedUserVM.getId()))) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "userexists", "Login already in use")).body(null);
        }
             
        Optional<UserDTO> updatedUser = userService.updateUserDto(managedUserVM);
        //customUserService.updateUser(updatedUser.get().getId());
        return ResponseUtil.wrapOrNotFound(updatedUser,
            HeaderUtil.createAlert("auxiliumApp.userManagement.updated", managedUserVM.getLogin()));
    }
    
    
    @PutMapping("/users/{idgroup}/{iduser}")
    @Timed
    public ResponseEntity<User> updateGroup(@PathVariable Long idgroup, @PathVariable Long iduser){

     userService.updateGroup(idgroup);
      User result = customUserService.updateGroupUser(iduser,idgroup);
     return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }
      
    /**
     * GET  /users : get all users.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and with body all users
     */
    

    @GetMapping("/users")
    @Timed
    public ResponseEntity<List<UserDTO>> getAllUsers(@ApiParam Pageable pageable) {
        final Page<UserDTO> page = userService.getAllManagedUsers(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/users");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
    
    
    

    @GetMapping("/users-without-pagination")
    @Timed
    public ResponseEntity<List<User>> getAllUsersWithoutPagination() {
       List<User> users = userService.getUsersWithoutPagination();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    /**
     * @return a string list of the all of the roles
     */
    @GetMapping("/users/authorities")
    @Timed
    public List<String> getAuthorities() {
        return userService.getAuthorities();
    }

    /**
     * GET  /users/:login : get the "login" user.
     *
     * @param login the login of the user to find
     * @return the ResponseEntity with status 200 (OK) and with body the "login" user, or with status 404 (Not Found)
     */
    @GetMapping("/users/{login:" + Constants.LOGIN_REGEX + "}")
    @Timed
    public ResponseEntity<UserDTO> getUser(@PathVariable String login) {
        log.debug("REST request to get User : {}", login);
        return ResponseUtil.wrapOrNotFound(
            userService.getUserWithAuthoritiesByLogin(login)
                .map(UserDTO::new));
    }
    /**
     * GET  /users/:id : get the "id" user.
     *
     * @param login the id of the user to find
     * @return the ResponseEntity with status 200 (OK) and with body the "login" user, or with status 404 (Not Found)
     */
    @GetMapping("/users/{id}")
    @Timed
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
        log.debug("REST request to get User : {}", id);
        User user = userService.getUserWithAuthorities(id);
        UserDTO userDto = userMapper.userToUserDTO(user);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(userDto));
    }

    /**
     * DELETE /users/:login : delete the "login" User.
     *
     * @param login the login of the user to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/users/{login:" + Constants.LOGIN_REGEX + "}")
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<Void> deleteUser(@PathVariable String login) {
        log.debug("REST request to delete User: {}", login);
        customUserService.deleteUserFromUser(login);
        userService.deleteUser(login);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert( "auxiliumApp.userManagement.deleted", login)).build();
    }
    
    /**
     * DELETE /users/:id : delete the "id" User.
     *
     * @param id of the user to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/users/{id}")
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        log.debug("REST request to delete User: {}", id);
        customUserService.deleteUserFromUser(id);
        userService.deleteUser(id);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert( "auxiliumApp.userManagement.deleted", id.toString())).build();
    }

    /**
     * ACTIVATE /users/activate/:id : activate the "id" User.
     *
     * @param id of the user to activate
     * @return the ResponseEntity with status 200 (OK)
     */
    @PutMapping("/users/activate/{id}")
    @Timed
    public ResponseEntity<Void> activateUser(@PathVariable Long id) {
        log.debug("REST request to activate User: {}", id);
        User user = userRepository.findOne(id);
        user.setActivated(true);
        userRepository.save(user);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert( "auxiliumApp.userManagement.activated", id.toString())).build();
    }

    /**
     * DESACTIVATE /users/disable/:id : disable the "id" User.
     *
     * @param id of the user to disable
     * @return the ResponseEntity with status 200 (OK)
     */
    @PutMapping("/users/disable/{id}")
    @Timed
    public ResponseEntity<Void> disableUser(@PathVariable Long id) {
        log.debug("REST request to disable User: {}", id);
        User user = userRepository.findOne(id);
        user.setActivated(false);
        userRepository.save(user);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert( "auxiliumApp.userManagement.disabled", id.toString())).build();
    }

    /**
     * SEARCH  /_search/users/:query : search for the User corresponding
     * to the query.
     *
     * @param query the query to search
     * @return the result of the search
     */
    @GetMapping("/_search/users/{query}")
    @Timed
    public List<User> search(@PathVariable String query) {
        return StreamSupport
            .stream(userSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    @GetMapping("/act-users")
    @Timed
    public ResponseEntity<List<UserDTO>> lanceActivitiProcess() {
        log.debug("REST request to lance activiti process : {}");
        customUserService.createAcitivitiUser(this.user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

	   /**
     *
     * to the query.
     *
     * @param return the Id of entity
     * @return return ther permission access of user
     */
    @GetMapping("/user-extras/byEntity/{idEntity}/{idUser}")
    @Timed
    public ResponseEntity<PermissionAccessDTO> findPermissionUser(@PathVariable Long idEntity,
            @PathVariable Long idUser) {
        log.debug("REST request to lance activiti process  findPermissionUser : {}");

        PermissionAccessDTO functionalityDTO = userExtraService.permission(idUser, idEntity);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(functionalityDTO));
    }

    /**
     * Get user permission
     * @param entityId
     * @param funcId
     * @return 
     */
    @GetMapping("/user-extras/route/{entityId}/{funcId}")
    @Timed
    public ResponseEntity<Boolean> isUserHasPermission(@PathVariable Long entityId, @PathVariable Long funcId) {
        log.debug("REST request to findPermissionUser : entity {}, functionality {}", entityId, funcId);
    	String login = SecurityUtils.getCurrentUserLogin();
    	User currentUser = userService.findOneUserByLogin(login);
        if(currentUser != null) {
            Boolean ret = userExtraService.isUserHasFunctionality(entityId, currentUser.getId(), funcId);
            log.info("REST request to findPermissionUser : result {}", ret);
            return ResponseEntity.ok().body(ret);
        } else {
            return ResponseEntity.ok().body(Boolean.FALSE);
        }
    }

    @GetMapping("/connecteduser")
    @Timed
    public ResponseEntity<UserDTO> findConnectedUser() {
    	String login = SecurityUtils.getCurrentUserLogin();
    	User user = userService.findOneUserByLogin(login);
    	UserDTO userDto = userMapper.userToUserDTO(user);
		return ResponseUtil.wrapOrNotFound(Optional.ofNullable(userDto));
    }    

    @GetMapping(value = "/users/export/excel/{id}")//, produces = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8")
    @Timed
    public void exportUserInExcel(@PathVariable Long id, HttpServletResponse response) {
    	XSSFWorkbook workbook = null;
        HttpHeaders headers = new HttpHeaders();
        /* Here I got the object structure (pulling it from DAO layer) that I want to be export as part of Excel. */
        UserExtraDTO userExtra = userExtraService.findOne(id);
        try {
            /* Logic to Export Excel */
            LocalDateTime localDate = LocalDateTime.now();
            String fileName = "User-" + userExtra.getId() + ".xlsx";
                
            workbook = (XSSFWorkbook) ExcelUtil.getUserExcelExport(userExtra);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            OutputStream out = response.getOutputStream();
            workbook.write(out);
            //ByteArrayInputStream in = new ByteArrayInputStream(baos.toByteArray());
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8");
            //response.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8"));
            response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
            //headers.set("Content-Disposition", "attachment; filename=" + fileName);
            //headers.setContentLength(baos.toByteArray().length);
            out.flush();
            //return new ResponseEntity<>(baos.toByteArray(), headers, HttpStatus.OK);            
        } catch (Exception ecx) {
            //return new ResponseEntity<>(null, null, HttpStatus.BAD_REQUEST);
        } finally {
            if (null != workbook) {
                try {
                    log.info("_____________________________________________________________");
                    workbook.close();
                } catch (IOException eio) {
                    log.error("Error Occurred while exporting to XLS ", eio);
                }
            }
        }
    } 
    
    public User historyUser (User user, User historyUser) {
    	historyUser.setFirstName(user.getFirstName());
    	historyUser.setLastName(user.getLastName());
    	historyUser.setEmail(user.getEmail());
    	historyUser.setId(user.getId());
    	return historyUser;
    	
    }

}