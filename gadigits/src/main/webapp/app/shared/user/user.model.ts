import { UserEntiteFonctionnalite } from "../../admin/user-entite-fonctionnalite/user-entite-fonctionnalite.model";
import { UserRelation } from "../auth/user-relation.model";
import { UserExtra } from '../../entities/user-extra';

export class User {
    public id?: any;
    public login?: string;
    public firstName?: string;
    public lastName?: string;
    public email?: string;
    public activated?: boolean;
    public langKey?: string;
    public authorities?: any[];
    public createdBy?: string;
    public createdDate?: Date;
    public lastModifiedBy?: string;
    public lastModifiedDate?: Date;
    public idExterne?: any;
    public idProfil?: any;
    public idGroup?: any;
    public password?: string;
    public userExtra?: UserExtra;
    public strongUserRelationDTOs?: UserRelation[];
    public weakUserRelationDTOs?: UserRelation[];

    constructor(
        id?: any,
        login?: string,
        firstName?: string,
        lastName?: string,
        email?: string,
        activated?: boolean,
        langKey?: string,
        authorities?: any[],
        createdBy?: string,
        createdDate?: Date,
        lastModifiedBy?: string,
        lastModifiedDate?: Date,
        idExterne?: any,
        idProfil?: any,
        idGroup?: any,
        password?: string,
        userExtra?: UserExtra,
        strongUserRelationDTOs?: UserRelation[],
        weakUserRelationDTOs?: UserRelation[]
    ) {
        this.id = id ? id : null;
        this.login = login ? login : null;
        this.firstName = firstName ? firstName : null;
        this.lastName = lastName ? lastName : null;
        this.email = email ? email : null;
        this.activated = activated ? activated : false;
        this.langKey = langKey ? langKey : null;
        this.authorities = authorities ? authorities : null;
        this.createdBy = createdBy ? createdBy : null;
        this.createdDate = createdDate ? createdDate : null;
        this.lastModifiedBy = lastModifiedBy ? lastModifiedBy : null;
        this.lastModifiedDate = lastModifiedDate ? lastModifiedDate : null;
        this.idExterne = idExterne ? idExterne : null;
        this.idProfil = idProfil ? idProfil : null;
        this.idGroup = idGroup ? idGroup : null;
        this.password = password ? password : null;
        this.userExtra = userExtra ? userExtra : null;
        this.strongUserRelationDTOs = strongUserRelationDTOs ? strongUserRelationDTOs : [];
        this.weakUserRelationDTOs = weakUserRelationDTOs ? weakUserRelationDTOs : [];
    }
}
