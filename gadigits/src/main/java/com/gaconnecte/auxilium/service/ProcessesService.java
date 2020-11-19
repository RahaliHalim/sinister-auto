package com.gaconnecte.auxilium.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gaconnecte.auxilium.domain.User;
import com.gaconnecte.auxilium.domain.enumeration.Decision;
import com.gaconnecte.auxilium.domain.enumeration.EtatDossierRmq;
import com.gaconnecte.auxilium.repository.UserRepository;
import com.gaconnecte.auxilium.security.SecurityUtils;
import com.gaconnecte.auxilium.service.dto.PrestationPECDTO;
import com.gaconnecte.auxilium.service.dto.TodoPrestationAvtDTO;
import com.gaconnecte.auxilium.service.dto.TodoPrestationPecDTO;

@Service
@Transactional
public class ProcessesService {
	
	private final Logger log = LoggerFactory.getLogger(ProcessesService.class);

    private static final String REFERENCE = "reference";

    private static final String ASSISTANCE = "assistance";

    private static final String PEC = "pec";

    private static final String AUXILIUM = "auxilium";

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ActivitiRolesService activitiRolesService;

    @Autowired
    private PrestationPECService prestationPECService;

    /**
     * Start AUXILIUM processes.
     *
     * @param reference
     */
    public void startProcess(String reference) {
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put(REFERENCE, reference);
        runtimeService.startProcessInstanceByKey(AUXILIUM, variables);
    }


    /**
     * complete Task.
     *
     * @param referenceDossier
     */
    public void completeTask(String referenceDossier) {
        List<Task> currentTask = taskService.createTaskQuery().processVariableValueEquals(referenceDossier).list();
        for (Task task : currentTask) {
            if (task.getProcessDefinitionId().indexOf(AUXILIUM) > 0) {
                taskService.complete(currentTask.get(0).getId());
                break;
            }
        }
    }

    /**
     * Start ASSISTANCE processes.
     *
     * @param reference
     */
    public void startAssistanceProcess(String reference) {
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put(REFERENCE, reference);
        runtimeService.startProcessInstanceByKey(ASSISTANCE, variables);

    }


    /**
     * Start PEC processes.
     *
     * @param reference
     */
    public void startPECProcess(String reference) {
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put(REFERENCE, reference);
        runtimeService.startProcessInstanceByKey(PEC, variables);
    }


    public Page<PrestationPECDTO> findAllPrestationPEC(Pageable pageable) {
    	Map<String, String> references = new HashMap<>();
        String login = SecurityUtils.getCurrentUserLogin();
        User user = userRepository.findOneUserByLogin(login);
        List<String> roles = activitiRolesService.getActivitiRoles(user);

        List<Task> tasks = new ArrayList<>();
        for (String role : roles) {
            log.info("[ProcessesService:findAllPrestationPEC] treatment of act role {}", role);
            tasks.addAll(taskService.createTaskQuery().taskCandidateGroup(role).list());
        }
        if (!tasks.isEmpty()) {
            List<PrestationPECDTO> prestationPECDTOs = new ArrayList<>();
            for (Task task : tasks) {
                Map<String, Object> variables = runtimeService.getVariables(task.getProcessInstanceId());
                PrestationPECDTO prestationPECDTO = prestationPECService.findPrestationPECByReference((String) variables.get(REFERENCE));
                if (prestationPECDTO != null && !references.containsKey((String) variables.get(REFERENCE))) {
                	references.put((String) variables.get(REFERENCE), (String) variables.get(REFERENCE));
                    prestationPECDTOs.add(prestationPECDTO);
                }
            }
            return new PageImpl<>(prestationPECDTOs, pageable, prestationPECDTOs.size());
        }
        return new PageImpl<>(new ArrayList<>(), pageable, 0);

    }



    /**
     * Get list task by role user.
     *
     * @param assignee
     * @param pageable
     * @return Set<TodoPrestationAvtDTO>
     */
    public Set<TodoPrestationAvtDTO> findAllAvtService() {
        String login = SecurityUtils.getCurrentUserLogin();
        User user = userRepository.findOneUserByLogin(login);
        Map<String, String> references = new HashMap<>();
        List<Task> tasks = new ArrayList<>();
        List<String> activities = activitiRolesService.getActivitiRoles(user);
        for (String act : activities) {
            tasks.addAll(taskService.createTaskQuery().taskCandidateGroup(act).list());
        }

        if (!tasks.isEmpty()) {
            Set<TodoPrestationAvtDTO> services = new TreeSet<>();
            for (Task task : tasks) {
                Map<String, Object> variables = runtimeService.getVariables(task.getProcessInstanceId());
                TodoPrestationAvtDTO todo = null;
                
                if (todo != null && !references.containsKey((String) variables.get(REFERENCE))) {
                	references.put((String) variables.get(REFERENCE), (String) variables.get(REFERENCE));
                    services.add(todo);
                }
            }
            return services;
        }
        return new TreeSet<>();
    }

    /**
     * Get list task by role user.
     *
     * @return Set<TodoPrestationPecDTO>
     */
    public Set<TodoPrestationPecDTO> findAllPecService() {
    	Map<String, String> references = new HashMap<>();
        String login = SecurityUtils.getCurrentUserLogin();
        User user = userRepository.findOneUserByLogin(login);

        List<Task> tasks = new ArrayList<>();
        List<String> activities = activitiRolesService.getActivitiRoles(user);
        for (String authority : activities) {
            tasks.addAll(taskService.createTaskQuery().taskCandidateGroup(authority).list());
        }
        if (!tasks.isEmpty()) {
            Set<TodoPrestationPecDTO> services = new TreeSet<>();
            for (Task task : tasks) {
                Map<String, Object> variables = runtimeService.getVariables(task.getProcessInstanceId());
                TodoPrestationPecDTO todo = prestationPECService.findInProgressPrestationByReference((String) variables.get(REFERENCE));
                if (todo != null && !references.containsKey((String) variables.get(REFERENCE))) {
                	references.put((String) variables.get(REFERENCE), (String) variables.get(REFERENCE));
                    services.add(todo);
                }
            }
            return services;
        }
        return new TreeSet<>();

    }

    public void completeAssistanceTask(String referenceDossier, Boolean existeRmq, EtatDossierRmq etat) {
        List<Task> currentTask = taskService.createTaskQuery().processVariableValueEquals(referenceDossier).list();
        for (Task task : currentTask) {
            if (task.getProcessDefinitionId().indexOf(ASSISTANCE) > -1) {
                Map<String, Object> variables = new HashMap<String, Object>();
                variables.put("existeRmq", existeRmq);
                variables.put("etat", etat);
                taskService.complete(currentTask.get(0).getId(), variables);
                break;
            }
        }
    }

    public void closePecSaveTask(String reference) {
        // Completing the phone interview with success should trigger two new tasks
        Map<String, Object> taskVariables = new HashMap<String, Object>();
        taskVariables.put(REFERENCE, reference);
        Task currentTask = taskService.createTaskQuery().processVariableValueEquals(REFERENCE, reference).singleResult();
        taskService.complete(currentTask.getId(), taskVariables);

    }

    public void completePECTask(String reference, Decision decision) {
        List<Task> currentTask = taskService.createTaskQuery().processVariableValueEquals(reference).list();
        for (Task task : currentTask) {
            if (task.getProcessDefinitionId().indexOf(PEC) > -1) {
                Map<String, Object> variables = new HashMap<String, Object>();
                variables.put("decision", decision);
                taskService.complete(currentTask.get(0).getId(), variables);
                break;
            }
        }
    }


}