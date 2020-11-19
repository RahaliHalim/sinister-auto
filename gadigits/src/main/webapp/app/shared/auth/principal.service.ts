import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { Subject } from 'rxjs/Subject';
import { AccountService } from './account.service';
import { Router, ActivatedRoute } from '@angular/router';
import { DatePipe } from '@angular/common';
import { LienService } from '../../entities/lien/lien.service';
import { ListeLien } from '../..//layouts/sidebar/lien.model';
import { Features } from '../../entities/feature/feature.model';
import { FeatureService } from '../../entities/feature/feature.service';
import { ListeFeatureLien } from '../../layouts/sidebar/feature-lien.model';
import { UserService } from '../user/user.service';
import { User } from '../user/user.model';
import { MenuList, MenuSquelette } from '../../constants/app.constants';
import { MenuItems } from '../../entities/MenuItems/menu.model';
import { SubMenu, ElementMenu } from './menu.model';
import { UserReslationService } from './user-relation.service';
import { UserExtraService, UserExtra } from '../../entities/user-extra';
import { NotifAlertUserService } from '../../entities/notif-alert-user/notif-alert-user.service';
import { NotifAlertUser } from '../../entities/notif-alert-user/notif-alert-user.model';
@Injectable()
export class Principal {
    public selectedValue;
    public var;
    public dateparse: any;
    public dateparseToSeconde: any;
    private userIdentity: any;
    private authenticated = false;
    private authenticationState = new Subject<any>();
    public value = 'select';
    public isAdminAssist = false;
    public isAdminAccept = false;
    public isAdminReparation = false;
    public isAdminSupport = false;
    public listeLiens: any;
    public listeLiensAssistance: any;
    public listeLiensPEC: any;
    public list: any[] = [];
    public assistance: any;
    public lienWithoutParent: any;
    public featureWithoutParent: any;
    public lienNotParent: any;
    public lienFeature: Features[];
    private lienParentFeature: any;
    public lienPrincipal: any;
    public lienAllowForConnectedUser: any;
    public listLienFeature: any;
    public long: number = 0;
    public ok: boolean = false;
    typeNotif = "N";
    typeAlert = "A";

    public user: User;

    public parent: MenuItems = new MenuItems();
    public parents: MenuItems[] = [];
    public menu: ElementMenu[] = [];
    public index: number;
    notifications: NotifAlertUser[] = [];
    alerts: NotifAlertUser[] = [];


    constructor(
        private datePipe: DatePipe,
        private lienService: LienService,
        private route: Router,
        private account: AccountService,
        private userExtraService: UserExtraService,
        private featureService: FeatureService,
        private userService: UserService,
        private UserRelationService: UserReslationService,
        private notifAlertUserService: NotifAlertUserService,
    ) { }

    reload() {
        window.location.reload();
    }

    parseDate(date) {
        this.dateparse = this.datePipe.transform(date, 'yyyy-MM-dd HH:mm');
        return this.dateparse
    }

    parseDateWithSeconde(date) {
        this.dateparseToSeconde = this.datePipe.transform(date, 'yyyy-MM-dd HH:mm:ss');
        return this.dateparseToSeconde
    }

    parseDateWithMinute(date) {
        this.dateparseToSeconde = this.datePipe.transform(date, 'dd-MM-yyyy HH:mm');
        return this.dateparseToSeconde
    }

    parseDateJour(date) {
        this.dateparse = this.datePipe.transform(date, 'yyyy-MM-dd');
        return this.dateparse
    }

    authenticate(identity) {
        this.userIdentity = identity;
        this.authenticated = identity !== null;
        this.authenticationState.next(this.userIdentity);
    }

    hasAnyAuthority(authorities: string[]): Promise<boolean> {
        return Promise.resolve(this.hasAnyAuthorityDirect(authorities));
    }

    hasAnyAuthorityDirect(authorities: string[]): boolean {
        if (!this.authenticated || !this.userIdentity || !this.userIdentity.authorities) {
            return false;
        }

        for (let i = 0; i < authorities.length; i++) {
            if (this.userIdentity.authorities.indexOf(authorities[i]) !== -1) {
                return true;
            }
        }
        return false;
    }

    hasAuthority(authority: string): Promise<boolean> {
        if (!this.authenticated) {
            return Promise.resolve(false);
        }

        return this.identity().then((id) => {
            return Promise.resolve(id.authorities && id.authorities.indexOf(authority) !== -1);
        }, () => {
            return Promise.resolve(false);
        });
    }

    newInitSidebar() {
        console.log('Get user menu');
        console.log(this.menu);
        if (this.userIdentity !== null && this.userIdentity !== undefined) {
            this.userExtraService.find(this.userIdentity.id).subscribe((res: UserExtra) => {
                if (res) {
                    this.menu = [];
                    let menuMap = new Map();
                    if (res.accesses && res.accesses.length > 0) {
                        for (let i = 0; i < res.accesses.length; i++) {
                            if (res.accesses[i].elementMenuId !== null && res.accesses[i].elementMenuId !== undefined) {
                                if (menuMap.get(res.accesses[i].elementMenuId)) { // Key exist
                                    let subMenu = new SubMenu();
                                    subMenu.id = res.accesses[i].id;
                                    subMenu.label = res.accesses[i].translationCode;
                                    subMenu.url = res.accesses[i].url;
                                    menuMap.get(res.accesses[i].elementMenuId).subMenus.push(subMenu);
                                } else {
                                    let eltMenu = new ElementMenu();
                                    eltMenu.id = res.accesses[i].elementMenuId;
                                    eltMenu.label = res.accesses[i].elementMenuLabel;
                                    menuMap.set(res.accesses[i].elementMenuId, eltMenu);
                                    let subMenu = new SubMenu();
                                    subMenu.id = res.accesses[i].id;
                                    subMenu.label = res.accesses[i].translationCode;
                                    subMenu.url = res.accesses[i].url;
                                    menuMap.get(res.accesses[i].elementMenuId).subMenus.push(subMenu);
                                }
                            }
                        }
                        let menuTmp = [];
                        menuMap.forEach(function (valeur, key) {
                            menuTmp.push(valeur);
                        });

                        this.menu = menuTmp;
                        console.log(this.menu);

                    }

                } else {
                    console.log('pas de fonct');
                }
            });
        }
    }

    newInitNavBar() {
        this.notifications = [];
        this.alerts = [];
        if (this.userIdentity !== null && this.userIdentity !== undefined) {
            this.userExtraService.find(this.userIdentity.id).subscribe((resUser: UserExtra) => {

                // Filter notifs or alert
                 this.notifAlertUserService.viewQuery(this.typeNotif).subscribe((resViewNotif) => {
                    this.notifications = resViewNotif;
                    this.notifications.sort(function (a, b) {
                        return b.id - a.id;
                    });
                    var cache = {};
                    this.notifications = this.notifications.filter(
                        function (elem) {
                            return cache[elem.id]
                                ? 0
                                : (cache[elem.id] = 1);
                        }
                    );
                    this.alerts = [];
                });
                //.subscribe((res) => {
                //     // console.log("length notif query" + res.length);
                //     for (let i = 0; i < res.length; i++) {
                //         // console.log("destinationnnnn" + res[i].destination);
                //         // console.log("user extra idddd" + resUser.id);
                //         if (res[i].notification.type == "N" && res[i].destination == resUser.id) {
                //             // console.log("type notif" + res[i].notification.type);
                //             // console.log("code notif" + res[i].notification.code);
                //             // console.log("translation Code" + res[i].notification.translationCode);
                //             res[i].objJson = JSON.parse(res[i].settings);
                //             this.notifications.push(res[i]);
                //             this.notifications.sort(function (a, b) {
                //                 return b.id - a.id;
                //             });
                //             var cache = {};
                //             this.notifications = this.notifications.filter(function (elem) {
                //                 return cache[elem.id] ? 0 : cache[elem.id] = 1;
                //             });
                //         }
                //         if (res[i].notification.type == 'A' && res[i].destination == resUser.id) {
                //             // console.log("type alerte" + res[i].notification.type);
                //             res[i].objJson = JSON.parse(res[i].settings);
                //             this.alerts.push(res[i]);
                //             this.alerts.sort(function (a, b) {
                //                 return b.id - a.id;
                //             });
                //             var cache = {};
                //             this.alerts = this.alerts.filter(function (elem) {
                //                 return cache[elem.id] ? 0 : cache[elem.id] = 1;
                //             });
                //         }
                //     }
                // })
            });
        }
    }

    findLiensByParent(id) {
        this.lienService.findLiens(id).subscribe((res) => { this.listeLiens = res; })
        return this.listeLiens;
    }

    identity(force?: boolean): Promise<any> {
        if (force === true) {
            this.userIdentity = undefined;
        }

        // check and see if we have retrieved the userIdentity data from the server.
        // if we have, reuse it by immediately resolving
        if (this.userIdentity) {
            return Promise.resolve(this.userIdentity);
        }

        // retrieve the userIdentity data from the server, update the identity object, and then resolve.
        return this.account.get().toPromise().then((account) => {
            if (account) {
                this.userIdentity = account;
                this.authenticated = true;
                if (this.menu === null || this.menu === undefined || this.menu.length === 0) {
                    this.newInitSidebar();
                }
                this.newInitNavBar();
            } else {
                this.userIdentity = null;
                this.authenticated = false;
            }
            this.authenticationState.next(this.userIdentity);
            return this.userIdentity;
        }).catch((err) => {
            this.userIdentity = null;
            this.authenticated = false;
            this.authenticationState.next(this.userIdentity);
            return null;
        });
    }

    isAuthenticated(): boolean {
        return this.authenticated;
    }

    getAccountId(): number {
        return this.userIdentity.id;
    }

    getCurrentAccount(): any {
        return this.userIdentity;
    }

    isIdentityResolved(): boolean {
        return this.userIdentity !== undefined;
    }

    getAuthenticationState(): Observable<any> {
        return this.authenticationState.asObservable();
    }

    getImageUrl(): String {
        return this.isIdentityResolved() ? this.userIdentity.imageUrl : null;
    }

}
