import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute} from '@angular/router';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Principal, User, UserService, ResponseWrapper, GroupDetailsService, GroupDetails } from '../../shared';
import {UserCellule} from '../../entities/user-cellule';
import { RefCompagnieService } from '../../entities/ref-compagnie/ref-compagnie.service';
import { RefModeGestionService } from '../../entities/ref-mode-gestion/ref-mode-gestion.service';
import { RefModeGestion } from '../../entities/ref-mode-gestion/ref-mode-gestion.model';
import { Group } from '../../shared/user/group.model';
import { GroupService } from '../../shared/user/group.service';
import { RefCompagnie } from '../../entities/ref-compagnie';
import { TreeviewItem, TreeviewConfig, DownlineTreeviewItem } from 'ngx-treeview';
import { Produit } from '../produit/produit.model';
import { ProduitService } from '../produit/produit.service';
import { Subject } from 'rxjs/Subject';
import { Subscription } from 'rxjs/Subscription';

@Component({
    selector: 'jhi-group-mgmt',
    templateUrl: './group-management.component.html'
})
export class GroupMgmtComponent implements OnInit, OnDestroy {

    currentAccount: any;
    users: User[] = [];
    listUsersWithoutPagination: User[] = [];
    error: any;
    success: any;
    routeData: any;
    links: any;
    totalItems: any;
    queryCount: any;
    itemsPerPage: any;
    page: any;
    predicate: any;
    previousPage: any
    reverse: any;
    authoritiesExterne: any;
    authoritiesForUser: any;
    userCellules: any;
    celluleForUser: any;
    authoritiesForUserExterne: any[] = [];
    authoritiesForUserInGrillee: any[] = [];
    result: any;
    externe: any[] = [];
    interne: any[] = [];
    allUsers: any;
    user: User = new User();
    authoritiesInterne: any[] = [];
    authoritiesActive: any[] = [];
    profils: any[] = [];
    listReparateurs: any[] = [];
    listExperts: any[] = [];
    listAgent: any[] = [];
    listCompagnie: RefCompagnie[] = [];
    selectedReparateur: any;
    selectedExpert: any;
    selectedAgent: any;
    selectedCompagnie: any;
    selectedRole: any;
    selectedClient: any;
    val
    authorities: any[] = [];
    selectedAuthoritiesCellule: any[] = [];
    selectedUsersGroup: User[] = [];
    selectedModeGestionGroup: any[] = [];
    authoritiesStatus: any[] = [];
    listModeGestion: RefModeGestion[];
    group: Group;
    groupDetails: GroupDetails;
    listGroupDetails: GroupDetails[] = [];
    listGroupDetailByGroupId: GroupDetails[];
    groupDetailsCreated: any;
    usersGroup: User[];
    createdGroup: Group;
    userUpdated: any;
    userselected: User = new User();
    selectedProfil: any;
    userCellule: UserCellule = new UserCellule();
    auth: any;
    cellule: any;
    userSaved: any;
    public authoritiesCellules: any[] = [];
    isSaving: Boolean;
    userCelluleCree: any;
    groups: Group[] = [];
    dropdownEnabled = true;
    usersTree: TreeviewItem[];
    clientTree: TreeviewItem[];
    values: any[];
    rows: any[] = [];
    client: any;
    produits: Produit[];
    public ShowFilter = true;
    public Disabled = false;
    public FilterPlaceholder = 'Type here to filter elements...';
    public MaxDisplayed = 5;
    public multipleSelected = [];
    public listSelected = [];
    listtree: TreeviewItem[];
    dtOptions: any = {};
    dtTrigger: Subject<Group> = new Subject();
    eventSubscriber: Subscription;
  

    constructor(
        private userService: UserService,
        private compagnieService: RefCompagnieService,
        private refModeGestionService: RefModeGestionService,
        private groupService: GroupService,
        private groupDetailsService: GroupDetailsService,
        private produitService: ProduitService,
        
        private alertService: JhiAlertService,
        private principal: Principal,
        private eventManager: JhiEventManager,
       
        
    ) {
       
    }

    ngOnInit() {

        this.dtOptions = {
            pagingType: 'full_numbers',
            pageLength: 10,
            retrieve: true,
            // Declare the use of the extension in the dom parameter
            dom: '<"row"<"col-sm-6"l><"col-sm-6"f>>t<"row"<"col-sm-6"B><"col-sm-6 text-align: center;"p>>',

            language: {
                processing: 'Traitement en cours...',
                search: 'Rechercher&nbsp;:',
                lengthMenu: 'Afficher _MENU_ &eacute;l&eacute;ments',
                info: '_START_ - _END_ / _TOTAL_',
                infoEmpty: 'La liste est vide',
                infoFiltered: '(filtr&eacute; de _MAX_ &eacute;l&eacute;ments au total)',
                infoPostFix: '',
                loadingRecords: 'Chargement en cours...',
                zeroRecords: 'Aucun &eacute;l&eacute;ment &agrave; afficher',
                emptyTable: 'Aucune donnée disponible dans le tableau',
                paginate: {
                    first: '<button type="button" class="btn btn-sm btn-default btn-rounded"><i class="fa fa-angle-double-left" style="font-size:16px"></i></button>&nbsp;',
                    previous: '<i class="fa fa-angle-left" style="font-size:16px"></i>&nbsp;',
                    next: '&nbsp;<i class="fa fa-angle-right" style="font-size:16px"></i>',
                    last: '&nbsp;<i class="fa fa-angle-double-right" style="font-size:16px"></i>'
                },
                aria: {
                    sortAscending: ': activer pour trier la colonne par ordre croissant',
                    sortDescending: ': activer pour trier la colonne par ordre décroissant'
                }
            },
            // Declare the use of the extension in the dom parameter
            //dom: 'Bfrtip',
            // Configure the buttons
            buttons: [
                {
                    extend: 'print',
                    text: '<span class="btn btn-default btn-sm"><i class="fa fa-print"></i><b> Imprimer </b></span> '
                },
                {
                    extend: 'csvHtml5',
                    text: '<span class="btn btn-default btn-sm"><i class="fa fa-file-text-o"></i><b>   Csv   </b></span>',
                    fieldSeparator: ';'
                },
                {
                    extend: 'excelHtml5',
                    text: '<span class="btn btn-default btn-sm"><i class="fa fa-file-excel-o"></i><b>  Excel  </b></span>'
                },
                {
                    extend: 'pdfHtml5',
                    text: '<span class="btn btn-default btn-sm"><i class="fa fa-file-pdf-o"></i><b>   Pdf   </b></span>',
                    orientation: 'landscape'
                }
            ]
        };

        this.isSaving = false;
        this.group = new Group();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
            this.loadAll();
            this.registerChangeInGroups();

        });
       
            this.produitService.query().subscribe((res: ResponseWrapper) => { this.produits = res.json; });
            this.groupService.query().subscribe((res: ResponseWrapper) => {
            this.groups = res.json; 
            })
            this.groupDetailsService.query().subscribe((res: ResponseWrapper) => {
                this.listGroupDetails = res.json; 
            })
            this.userService.findAllWithoupPagination().subscribe((res: ResponseWrapper) => {
            this.listUsersWithoutPagination = res.json;
            this.usersTree = this.getUsers();      
    });

    // Get list of customers
   
            this.compagnieService.getAgreementCompany().subscribe((res: ResponseWrapper) => {
            this.listCompagnie = res.json;
            this.clientTree = this.getClient();
        });
    }

    /**
     * Tree config
     */
        config = TreeviewConfig.create({
            hasAllCheckBox: true,
            hasFilter: true,
            hasCollapseExpand: true,
            decoupleChildFromParent: false,
            maxHeight: 150
        });

   
    /**
     * Fill tree of user 
     */
    getUsers(): TreeviewItem[] {
        const items: TreeviewItem[] = [];
        for (let i = 0; i < this.listUsersWithoutPagination.length; i++) {
                this.userService.findById(this.listUsersWithoutPagination[i].id).subscribe((subRes) => {
                this.userselected = subRes;
                const item = new TreeviewItem({ text: `${this.listUsersWithoutPagination[i].firstName + '    ------------>    ' +  this.userselected.authorities[0].split("_")[1]}`, value: `${this.listUsersWithoutPagination[i].id}`, checked: false });
                items.push(item);
                })
            };
        return items;
    }

    /**
     * Fill tree of client with gestion mode
     */

    getClient(): TreeviewItem[] {
        const items: TreeviewItem[] = [];
        const children: any[] = [];
        for (let i = 0; i < this.listCompagnie.length; i++) {        
                this.refModeGestionService.findModesGestionByClient(this.listCompagnie[i].id).subscribe((res: ResponseWrapper) => {
                children.splice(0, children.length);
                this.listModeGestion = res.json;
                for (let j = 0; j < this.listModeGestion.length; j++) {
                    const textChild = this.listModeGestion[j].libelle;
                    const valueChild = this.listModeGestion[j].id;
                    children.push({text:textChild,value:valueChild,checked:false});
                }
                if(this.listModeGestion.length >0){
                const item = new TreeviewItem({ text: `${this.listCompagnie[i].nom}`, value: `${this.listCompagnie[i].id}`,children});
                items.push(item);
                }
                });
                    
    }
        return items;
    }


    /**
     * return values when select or deselect tree 
     * @param downlineItems 
     */

    onSelectedChange(downlineItems: DownlineTreeviewItem[]) {
        if(this.rows != undefined && this.rows.length > 0){
        this.rows.splice(0, this.rows.length);
        }
        downlineItems.forEach(downlineItem => {
            const item = downlineItem.item;
            const value = item.value;        
            let parent = downlineItem.parent;
            let details = {idClient:parent.item.value,idMode:value};
            this.rows.push(details);
           
        });
    }
       
    /**
     * Get gestion mode by Client
     */

    getModeGestionByClient(values:any,id:number){     
        this.refModeGestionService.findModesGestionByClient(id).subscribe((res: ResponseWrapper) => {
            this.listModeGestion = res.json;
        });
   
    }  
     /**
      * Save Group and Group details
      */
     saveGroup(){   
            this.groupDetails = new GroupDetails();
            this.groupService.create(this.group).subscribe((res) => {
            this.createdGroup = res;
          
        for (let i = 0; i < this.values.length; i++) {                             
                this.userService.findById(this.values[i].item.value).subscribe((subRes) => {
                    this.userselected = subRes;
                    this.userselected.idGroup = this.createdGroup.id
                    this.userService.updateGroup(this.createdGroup.id,this.values[i].item.value).subscribe((subRes) => {
                        this.userUpdated = subRes;
                        
                    });
                   
                });                                  
        }

        for (let i = 0; i < this.rows.length; i++) {               
            this.groupDetails.groupId = this.createdGroup.id;
            this.groupDetails.clientId = this.rows[i].idClient;
            this.groupDetails.refModeGestionId = this.rows[i].idMode;    
                    this.groupDetailsService.create(this.groupDetails).subscribe((subRes) => {
                        this.groupDetailsCreated = subRes; 
                                            
                    });              
            }
            this.groupService.query().subscribe((res: ResponseWrapper) => {
                this.groups = res.json; 
                })
    })
    
    }

    clear(){
        this.group = new Group();
        this.usersTree = this.getUsers();
        this.clientTree = this.getClient();
    }
     /**
      * Ges group détails by Id
      * @param id 
      */

     getIdGroup(id:number){

     this.groupService.find(id).subscribe((subRes) => {
        this.group = subRes;
     });
       
       //Get select user  by group
      
        this.userService.findAllWithoupPagination().subscribe((res: ResponseWrapper) => {
        this.listUsersWithoutPagination = res.json;
        const itemsUsers: TreeviewItem[] = [];

        for (let i = 0; i < this.listUsersWithoutPagination.length; i++) {
                    this.userService.findById(this.listUsersWithoutPagination[i].id).subscribe((subRes) => {
                    this.userselected = subRes;

                    if(this.userselected.idGroup == id){
                    const item = new TreeviewItem({ text: `${this.listUsersWithoutPagination[i].firstName + '    ------------>    ' +  this.userselected.authorities[0].split("_")[1]}`, value: `${this.listUsersWithoutPagination[i].id}`, checked: true });
                    itemsUsers.push(item);    
                    }else{
                    const itemUser = new TreeviewItem({ text: `${this.listUsersWithoutPagination[i].firstName + '    ------------>    ' +  this.userselected.authorities[0].split("_")[1]}`, value: `${this.listUsersWithoutPagination[i].id}`, checked: false });
                    itemsUsers.push(itemUser);
                    }
                    
                    })
              
        };
                    this.usersTree = itemsUsers;
            
        });


     /**
      *  Get select client and mode gestion by group
      *
      */

        const itemsClient: TreeviewItem[] = [];
        this.groupDetailsService.findByGroupId(id).subscribe((res: ResponseWrapper) => {  // Get detail By group Id
            this.listGroupDetailByGroupId = res.json;
            
        
        
        const children: any[] = [];
        for (let i = 0; i < this.listCompagnie.length; i++) {
                this.refModeGestionService.findModesGestionByClient(this.listCompagnie[i].id).subscribe((res: ResponseWrapper) => {
                children.splice(0, children.length);
                this.listModeGestion = res.json;
                for (let j = 0; j < this.listModeGestion.length; j++) {
                    const textChild = this.listModeGestion[j].libelle;
                    const valueChild = this.listModeGestion[j].id;
                    let checkedChild =  false;
                   
                    for(let k = 0; k <this.listGroupDetailByGroupId.length; ++k){ // cheking mode gestion for selected group
                     if((this.listGroupDetailByGroupId[k].clientId == this.listCompagnie[i].id) && (this.listGroupDetailByGroupId[k].refModeGestionId== this.listModeGestion[j].id)){
                                 checkedChild = true;
                             
                            }
                         }
                    children.push({text:textChild,value:valueChild,checked:checkedChild});
                    }
                    if(this.listModeGestion.length >0){
            const itemClient = new TreeviewItem({ text: `${this.listCompagnie[i].nom}`, value: `${this.listCompagnie[i].id}`,children});

            itemsClient.push(itemClient);
                    }
            });
            
        
    }
       this.clientTree = itemsClient;

    })                            
     }
   
    private onSaveSuccess(result) {
        this.eventManager.broadcast({ name: 'groupListModification', content: 'OK' });
        this.isSaving = false;
        
    }
   
    ngOnDestroy() {
       
        if(this.eventSubscriber !== null && this.eventSubscriber !== undefined)
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInGroups() {
        this.eventManager.subscribe('groupListModification', (response) => {this.loadAll()});
    }
    
    loadAll() {
        this.groupService.query().subscribe(
                (res: ResponseWrapper) => {
                    this.groups = res.json;
                    this.dtTrigger.next(); // Actualize datatables
                },
                (res: ResponseWrapper) => this.onError(res.json)
        );
        
    }

    trackIdentity(index, item: Group) {
        return item.id;
    }
  
    private onError(error) {
        this.alertService.error(error.error, error.message, null);
    }

    
}
