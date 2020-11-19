import { SinisterPec } from './sinister-pec.model';
import { ResponseWrapper, createRequestOption } from '../../shared';
import { Injectable } from '@angular/core';
import { Http, Response, ResponseContentType } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils } from 'ng-jhipster';
import { Estimation } from '../reparation/estimation.model';
import { User } from '../../shared/user/user.model';
import { TodoPrestationPec } from './todo-sinister-pec';
import { Attachment } from '../attachments';
import { Quotation } from '../quotation/quotation.model';
import { PrimaryQuotation } from '../PrimaryQuotation';
@Injectable()
export class SinisterPecService {

    private resourceUrl = 'api/sinister-pecs';
    private resourceUrlView = 'api/view-sinister-pecs';

    private resourceUrlPecAndAttachment = 'api/sinister-pecs-attachments';
    private resourceUrlMPConfirme = 'api/sinister-pecs-mp-com-confirmer';
    private resourceSearchUrl = 'api/_search/sinister-pecs';
    public sinisterPecId: any;
    public id: any;
    private resourceDemandUrl = "api/demands/pec";
    private resourceSinisterPecRefused = "api/sinister-pecs/refused";
    private resourceSinisterPecCanceled = "api/sinister-pecs/canceled";
    private resourceSinisterPecAccWithResrve = "api/sinister-pecs/accepted-with-reserve";
    private resourceSinisterPecAccWithChangeStatus = "api/sinister-pecs/accepted-with-change-status";
    private resourceSinisterPecRefusedAndCanceled = "api/sinister-pecs/refused-and-canceled";
    public resourceUrlAttachments = 'api/attachement';
    private gaEstimate = 'api/devis/estimation';
    private gaEstimateCreation = 'api/devis/estimation-creation';
    private gtEstimate = 'api/devis/estimation/gtestimate';
    private fileStorege = 'api/storage/folder';
    private resourceUrlForExpertOpinion = 'api/sinisters-pecs/expert-opinion';
    private resourcePECByDossierUrl = 'api/sinisters-pecs/dossier';
    private resourceuserForAuthorityGestionnaireUrl = 'api/authorities/users';
    private resourceUrlExportAffectRep = 'api/affectRepPDF';
    private resourceUrlExportAffectExpert = 'api/affectExpertPDF';
    private resourceUrlExportlettreIDA = 'api/generatelettreIDA';
    private generateQuotePdf = 'api/generatequotation';
    private resourceUrlReparateur = 'api/sinister-pecs/reparateur';
    private resourceUrlSignatureAccord = 'api/sinister-pecs/signature-accord';
    private resourceUrlSignatureAccordApec = 'api/signature-accord';
    private resourceUrlReparateurQuotation = 'api/sinister-pecs/reparateur/quotation';
    private resourceUrlBonSortie = 'api/bonSortiePdf';
    private resourceUrlOrdreMissionExpert = 'api/ordreMissionExpertPdf';
    private resourceUrlExportlettreReouverture = 'api/generatelettreReouverture';
    private resourceUrlprestationPECWhenTiersNotNull = 'api/sinisterPecWhenTiersNotNull';
    private resourceUrlprestationPECAcceptedWhenRepairNull = 'api/sinsterPecAcceptedReparateurNull';
    private resourceUrlprestationPECAcceptedWhenRepaExpertNotNull = 'api/sinisterPecAcceptedExpertAndReparateurNotNull';
    private resourceUrlprestationPECWithGenerateAccordStatusForDevis = 'api/sinsterPecPECWithGenerateAccordStatusForDevis';
    private resourceUrlprestationPECWithCanceledDecision = 'api/sinisterPECWithCanceledEtat';
    private resourceUrlprestationPECWithAcceptedDecision = 'api/SinisterPECWithAcceptedEtat';
    private resourceUrlprestationPECWithReserveDecision = 'api/sinisterPECWithReserveEtat';
    private resourceUrlprestationPECInProgress = 'api/resourceUrlsSinisterPECInProgress';
    private toDoListUrl = 'api/service-pec-todo';
    private resourceNbreMissionReparatorUrl = 'api/nbre-mission-reparator';
    private resourceNbreMissionExpertUrl = 'api/nbre-mission-expert';
    private resourceUrlJounal = 'api/journal/sinister-pec';
    private resourceUrlConstat = 'api/sinister-pecs/attachments';
    private resourceUrlRep = 'api/sinister-pecs/attachments-rep';
    private resourceUrlPecPlus = 'api/sinister-pecs/attachments-pec-plus';
    private resourceUrlQuotation = 'api/sinister-pecs/attachments-quotation';
    private resourceUrlFacture = 'api/sinister-pecs/attachments-facture';
    private resourceUrlAutresPiecesJointes = 'api/sinister-pecs/autres-pieces-jointes';
    private resourceUrlSaveAttachments = 'api/sinister-pecs/attachments/entity';
    private resourceUrlQueryPecInVerification = 'api/sinister-pecs/verification';
    private resourceUrlPecsToApprove = 'api/sinister-pecs/to-approve';
    private resourceUrlPecsToCancelPec = 'api/view-sinister-pecs/annulation-pec';
    private resourceUrlPecsToRefusPec = 'api/view-sinister-pecs/refused-pec';
    private resourceUrlPecsToConfirmCancelPec = 'api/sinister-pecs/annulation-pec-confirm';
    private resourceUrlPecsToConfirmRefusPec = 'api/sinister-pecs/refused-pec-confirm';
    private resourceUrlPecsBeingProcessed = 'api/sinister-pecs/being-processed';
    private resourceUrlPecsConsulterDemandePec = 'api/sinister-pecs/consulter-demande-pec';
    private annulerAffectationReparateur = 'api/sinister-pecs/annuler-affecter-reparateur';
    private resourceUrlInCheckSupported = 'api/sinister-pecs/in-check-supported';
    private resourceUrlForDismantling = 'api/sinister-pecs/for-dismantling';
    private resourceUrlPiecesJointesFileAccordComplementaire = 'api/sinister-pec/pieces-jointes-file-accord-complementaire';
    private resourceUrlIdaOuverture = 'api/view-sinister-pecs/ida-ouverture';
    private resourceUrlPecEmpty = 'api/sinister-pecs/pec-empty';
    private resourceUrlQueryConfirmationDevisComplementaire = 'api/sinister-pecs/confirmation-devis-complementaire';
    private resourceUrlQueryConfirmationDevis = 'api/sinister-pecs/confirmation-devis';
    private resourceUrlQueryUpdateDevis = 'api/sinister-pecs/update-devis';
    private resourceUrlInNotCancelExpertMission = 'api/sinister-pecs/in-not-cancel-expert-mission';
    private resourceUrlwithDecision = 'api/sinister-pecs/with-decision';
    private resourceUrlImprimeAttachments = 'api/sinister/attachementImprime';
    private resourceUrlPhotoReparationAttachments = 'api/sinister/attachementReparation';
    private resourceUrlExpertiseAttachments = 'api/sinister/attachementExpertise';
    private resourceUrlPlusPecAttachments = 'api/sinister/plus-pec-attachments';
    private resourceUrlAttachmentsHistory = 'api/prestation-pecs-attachment';
    private resourceUrlprestationPECWithValidAccord = 'api/apecs/sinister-pecs-with-accord';
    private resourceAccordBySinisterUrl = 'api/apecs/by-sinister-pec';
    private resourceUrlprestationPECOriginalsPrinted = 'api/sinister-pecs/verification-printed';
    private resourceUrlBonSortieAttachment = 'api/sinister-pecs/attachments-bon-sortie';
    private resourceUrlprestationPECForSignatureBonSortie = 'api/sinister-pecs/signature-bon-sortie';
    private resourceUrlForRevueValidationDevis = 'api/sinister-pecs/revue/validation/devis';
    private resourceUrlprestationPECForModificationPrestation = 'api/sinister-pecs/modification-prestation';
    private resourceUrlQueryConfirmationModificationPrix = 'api/sinister-pecs/confirmation-modification-prix';
    private resourceUrlBonSortieAttachments = 'api/sinister/attachementBonSortie';
    private resourceUrlBonSortiePdf = 'api/sinister-pecs/bon-sortie/pdf';
    private resourceUrlGTPdf = 'api/sinister-pecs/gt-rapport/pdf';
    private resourceUrlOrdreMisssionExpertPdf = 'api/sinister-pecs/ordre-mission-expert/pdf';
    private resourceUrlSignatureAccordPdf = 'api/sinister-pecs/signature-accord/pdf';
    private resourceUrlStepPec = 'api/listPec';
    private resourceUrlStepPecByNumber = 'api/listPecByNumber';
    private resourceAccordBySinisterPecUrl = 'api/apecs-by-pec';
    private resourceUrlCountPec = 'api/sinister-pecs/count';
    private resourceUrlAutresPiecesJointesFile = 'api/sinister/attachement-autres';
    private resourceUrlPiecesJointesFile = 'api/sinister-pec/pieces-jointes-file';
    private resourceUrlPiecesJointesFileForAttachment = 'api/sinister-pec/pieces-jointes-file-for-attachment';
    private resourceUrlPhotoAvantReparationAttachments = 'api/sinister/attachement-photo-avant-reparation';
    private resourceUrlAttachmentsAccordSigne = 'api/sinister/attachement-accord-signe';
    private resourceUrlPiecesJointesFileIOOB = 'api/sinister-pec/pieces-jointes-file-i-o-o-b';
    private resourceUrlAttachmentDelete = 'api/sinister-pecs/attachment';
    private resourceUrlQuotationStatus = 'api/devis/status/quotation';
    private resourceUrlFusion = 'api/details-pieces-fusion-quotation';
    private resourceUrlSignatureBonS = 'api/sinister-pecs/signature-bon-sortie';
    private resourceUrlModifPrixInst = 'api/sinister-pecs/modification_prix';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(sinisterPec: SinisterPec): Observable<SinisterPec> {
        const copy = this.convert(sinisterPec);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }
    /* appel  d'Màj  de nouveau estimation  dans la base  */
    saveEstimation(id: number, sinisterPecId: number): Observable<PrimaryQuotation> {
        this.id = id;
        this.sinisterPecId = sinisterPecId;
        return this.http.post(`${this.gtEstimate}/${id}/${sinisterPecId}`, id).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    saveQuotationStatus(id: number): Observable<Response> {
        return this.http.post(`${this.resourceUrlQuotationStatus}/${id}`, id);
    }

    fusionDevisForSinisterPec(sinisterPecId: number, primaryQuotationId: number): Observable<Response> {
        return this.http.post(`${this.resourceUrlFusion}/${sinisterPecId}/${primaryQuotationId}`, sinisterPecId);
    }

    findStepPec(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrlStepPec, options)
            .map((res: Response) => this.convertResponse(res));
    }

    findStepPecByNumber(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrlStepPecByNumber, options)
            .map((res: Response) => this.convertResponse(res));
    }



    update(sinisterPec: SinisterPec): Observable<SinisterPec> {
        const copy = this.convert(sinisterPec);
        return this.http.put(this.resourceUrl, sinisterPec).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }
    updatePecForQuotation(sinisterPec: SinisterPec, modeEdit: boolean): Observable<SinisterPec> {
        return this.http.put(`${this.resourceUrl}/quotation/${modeEdit}`, sinisterPec).map((res: Response) => {
            const jsonResponse = res.json();
            return jsonResponse;
        });
    }

    countSinPec(id: number): Observable<number> {
        this.id = id;
        return this.http.get(`${this.resourceUrlCountPec}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return jsonResponse;
        });
    }

    updateIt(sinisterPec: SinisterPec): Observable<SinisterPec> {
        //const copy = this.converter(sinisterPec);
        return this.http.put(this.resourceUrl, sinisterPec).map((res: Response) => {
            const jsonResponse = res.json();
            //this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    updateMP(sinisterPec: SinisterPec): Observable<SinisterPec> {
        //const copy = this.converter(sinisterPec);
        return this.http.put(this.resourceUrlMPConfirme, sinisterPec).map((res: Response) => {
            const jsonResponse = res.json();
            //this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }


    find(id: number): Observable<SinisterPec> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }
    findBySinisterId(id: number): Observable<SinisterPec> {
        return this.http.get(`${this.resourceUrl}/sinister/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }
    queryToApprove(id: number, req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(`${this.resourceUrlPecsToApprove}/${id}`, options)
            .map((res: Response) => this.convertResponsePec(res));
    }

    queryToCanceledPec(id: number, req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(`${this.resourceUrlPecsToCancelPec}/${id}`, options)
            .map((res: Response) => this.convertResponsePec(res));
    }

    queryToConfirmCanceledPec(id: number, req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(`${this.resourceUrlPecsToConfirmCancelPec}/${id}`, options)
            .map((res: Response) => this.convertResponsePec(res));
    }

    queryToRefusedPec(id: number, req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(`${this.resourceUrlPecsToRefusPec}/${id}`, options)
            .map((res: Response) => this.convertResponsePec(res));
    }

    queryToConfirmRefusedPec(id: number, req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(`${this.resourceUrlPecsToConfirmRefusPec}/${id}`, options)
            .map((res: Response) => this.convertResponsePec(res));
    }

    findByUser(id: number): Observable<SinisterPec[]> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
        });
    }
    queryByUserId(id: number): Observable<SinisterPec[]> {
        return this.http.get(`${this.resourceUrl}/user/${id}`)
            .map((res: Response) => res.json());

    }

    queryByAssignedId(id: number, req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http
            .get(`${this.resourceUrl}/assigned/${id}`, options)
            .map((res: Response) => this.convertResponsePec(res));
    }
    /* get with solving the problem of date */

    /* get with solving the problem of date */
    queryToDate(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertDateResponse(res));
    }
    queryPrestationsInCheckSupported(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrlInCheckSupported, options)
            .map((res: Response) => this.convertResponsePec(res));
    }
    querySinisterPecForDismantling(id: number, req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(`${this.resourceUrlForDismantling}/${id}`, options)
            .map((res: Response) => this.convertResponsePec(res));
    }
    querySinisterPecInExpertOpinion(id: number, req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(`${this.resourceUrlForExpertOpinion}/${id}`, options)
            .map((res: Response) => this.convertResponsePec(res));
    }


    revueValidationDevis(id: number, req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(`${this.resourceUrlForRevueValidationDevis}/${id}`, options)
            .map((res: Response) => this.convertResponsePec(res));
    }
    queryConfirmationDevis(id: number, req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(`${this.resourceUrlQueryConfirmationDevis}/${id}`, options)
            .map((res: Response) => this.convertResponsePec(res));
    }
    queryConfirmationDevisComplementaire(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(`${this.resourceUrlQueryConfirmationDevisComplementaire}`, options)
            .map((res: Response) => this.convertResponsePec(res));
    }

    queryConfirmationModificationPrix(id: number, req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrlQueryConfirmationModificationPrix, options)
            .map((res: Response) => this.convertResponsePec(res));
    }

    queryPrestationsInUpdateDevis(id: number, req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(`${this.resourceUrlQueryUpdateDevis}/${id}`, options)
            .map((res: Response) => this.convertResponsePec(res));
    }

    queryPrestationsInNotCancelExpertMission(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrlInNotCancelExpertMission, options)
            .map((res: Response) => this.convertResponsePec(res));
    }
    queryPrestationsToVerification(id: number, req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(`${this.resourceUrlQueryPecInVerification}/${id}`, options)
            .map((res: Response) => this.convertResponsePec(res));
    }


    search(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceSearchUrl, options)
            .map((res: any) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }
    findCanceledSinisterPec(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(`${this.resourceUrl}/$/canceled`)
            .map((res: Response) => this.convertResponse(res));

    }

    findRefusededSinisterPec(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(`${this.resourceUrl}/$/refused`)
            .map((res: Response) => this.convertResponse(res));

    }
    findPrestationPec(id: number): Observable<SinisterPec> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            //this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });

    }
    queryReparateur(userId: number, req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(`${this.resourceUrlReparateur}/${userId}`, options)
            .map((res: Response) => this.convertResponsePec(res));
    }
    querySignatureAccord(status: number, userId: number, req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(`${this.resourceUrlSignatureAccord}/${userId}/${status}`, options)
            .map((res: Response) => this.convertResponsePec(res));
    }
    queryReparateurCpl(operation: number): Observable<ResponseWrapper> {
        return this.http.get(`${this.resourceUrlReparateurQuotation}/${operation}`)
            .map((res: Response) => this.convertResponse(res));
    }

    deleteAttachmentsImprimes(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrlAttachmentDelete}/${id}`);
    }


    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        for (let i = 0; i < jsonResponse.length; i++) {
            this.convertItemFromServer(jsonResponse[i]);
        }
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    /* created to solve the probleme of converting the date */
    private convertDateResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        /*for (let i = 0; i < jsonResponse.length; i++) {
            this.convertItemFromServer(jsonResponse[i]);
        }*/
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }
    private convertResponsePec(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        /*for (let i = 0; i < jsonResponse.length; i++) {
            this.convertItemFromServer(jsonResponse[i]);
        }*/
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }


    private convertItemFromServer(entity: any) {
        entity.dateReceptionVehicule = this.dateUtils
            .convertDateTimeFromServer(entity.dateReceptionVehicule);
        entity.dateDerniereMaj = this.dateUtils
            .convertDateTimeFromServer(entity.dateDerniereMaj);
        //entity.declarationDate = this.dateUtils
        //.convertLocalDateFromServer(entity.declarationDate);
    }
    private convert(sinisterPec: SinisterPec): SinisterPec {
        const copy: SinisterPec = Object.assign({}, sinisterPec);
        copy.dateRDVReparation = this.dateUtils.toDate(sinisterPec.dateRDVReparation);
        copy.dateDerniereMaj = this.dateUtils.toDate(sinisterPec.dateDerniereMaj);
        // copy.vehicleReceiptDate = this.dateUtils.toDate(sinisterPec.vehicleReceiptDate);

        return copy;
    }
    private converter(sinisterPec: SinisterPec): SinisterPec {
        const copy: SinisterPec = Object.assign({}, sinisterPec);
        return copy;
    }


    /* la liste de attachements  pour prestation pec  avec demontage*/
    getAttachments(id: number, req?: any): Observable<ResponseWrapper> {
        console.log("service get attachments");
        return this.http.get(`${this.resourceUrlAttachments}/${id}`).map((res: Response) =>
            this.convertResponse(res));
    }
    saveAttachments(id: number, constatFile: File, label: String): Observable<Attachment> {
        let formdata: FormData = new FormData();
        formdata.append('constat', constatFile);
        return this.http.post(`${this.resourceUrlConstat}/${id}/${label}`, formdata).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });

    }
    saveAttachmentsNw(id: number, constatFile: File, label: String, nomImage: String): Observable<Attachment> {
        let formdata: FormData = new FormData();
        formdata.append('constat', constatFile);
        return this.http.post(`${this.resourceUrlConstat}/${id}/${label}/${nomImage}`, formdata).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });

    }


    saveAttachmentsQuotationNw(id: number, constatFile: File, label: String, nomImage: String, nomFolder: String): Observable<Attachment> {
        let formdata: FormData = new FormData();
        formdata.append('constat', constatFile);
        return this.http.post(`${this.resourceUrlQuotation}/${id}/${label}/${nomImage}/${nomFolder}`, formdata).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });

    }


    saveAttachmentsQuotation(id: number, constatFile: File, label: String): Observable<Attachment> {
        let formdata: FormData = new FormData();
        formdata.append('constat', constatFile);
        return this.http.post(`${this.resourceUrlQuotation}/${id}/${label}`, formdata).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });

    }

    saveAttachmentsFacture(id: number, constatFile: File, label: String): Observable<Attachment> {
        let formdata: FormData = new FormData();
        formdata.append('constat', constatFile);
        return this.http.post(`${this.resourceUrlFacture}/${id}/${label}`, formdata).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });

    }
    saveAttachmentsFactureNw(id: number, constatFile: File, label: String, nomImage: String): Observable<Attachment> {
        let formdata: FormData = new FormData();
        formdata.append('constat', constatFile);
        return this.http.post(`${this.resourceUrlFacture}/${id}/${label}/${nomImage}`, formdata).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });

    }

    saveAttachmentsPiece(id: number, constatFile: File, label: String, description: String, original: boolean): Observable<Attachment> {
        let formdata: FormData = new FormData();
        formdata.append('constat', constatFile);
        return this.http.post(`${this.resourceUrlConstat}/${id}/${label}/${description}/${original}`, formdata).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });

    }


    saveAttachmentsPieceNw(id: number, constatFile: File, label: String, description: String, original: boolean, nomImage: String): Observable<Attachment> {
        let formdata: FormData = new FormData();
        formdata.append('constat', constatFile);
        return this.http.post(`${this.resourceUrlConstat}/${id}/${label}/${description}/${original}/${nomImage}`, formdata).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });

    }



    saveAttachmentsAutresPiecesJointes(id: number, constatFile: File, label: String): Observable<Attachment> {
        let formdata: FormData = new FormData();
        formdata.append('constat', constatFile);
        return this.http.post(`${this.resourceUrlAutresPiecesJointes}/${id}/${label}`, formdata).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });

    }


    saveAttachmentsAutresPiecesJointesNw(id: number, constatFile: File, label: String, nomImage: String, note: string): Observable<Attachment> {
        let formdata: FormData = new FormData();
        formdata.append('constat', constatFile);
        formdata.append('note', note);
        return this.http.post(`${this.resourceUrlAutresPiecesJointes}/${id}/${label}/${nomImage}`, formdata).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });

    }


    saveAttachmentsPiecePhotoReparation(id: number, constatFile: File, label: String, nomImage: String, nomFolder: String): Observable<Attachment> {
        let formdata: FormData = new FormData();
        formdata.append('constat', constatFile);
        return this.http.post(`${this.resourceUrlRep}/${id}/${label}/${nomImage}/${nomFolder}`, formdata).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });

    }

    saveAttachmentsPiecePhotoPlus(id: number, constatFile: File, label: String, nomImage: String): Observable<Attachment> {
        let formdata: FormData = new FormData();
        formdata.append('constat', constatFile);
        return this.http.post(`${this.resourceUrlPecPlus}/${id}/${label}/${nomImage}`, formdata).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });

    }

    saveAttachmentsPiecePhotoReparationNw(id: number, constatFile: File, label: String, nomImage: String): Observable<Attachment> {
        let formdata: FormData = new FormData();
        formdata.append('constat', constatFile);
        return this.http.post(`${this.resourceUrlRep}/${id}/${label}/${nomImage}`, formdata).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });

    }
    saveAttachmentsEntity(id: number, constatFile: File, label: String, description: String, nameEntity: String): Observable<Attachment> {
        let formdata: FormData = new FormData();
        formdata.append('constat', constatFile);
        return this.http.post(`${this.resourceUrlSaveAttachments}/${id}/${label}/${description}/${nameEntity}`, formdata).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });

    }
    updateAttachments(id: number, constatFile: File, label: String): Observable<Attachment> {
        let formdata: FormData = new FormData();
        formdata.append('constat', constatFile);
        return this.http.put(`${this.resourceUrlConstat}/${id}/${label}`, formdata).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });

    }

    updateAttachmentsQuotation(id: number, constatFile: File, label: String): Observable<Attachment> {
        let formdata: FormData = new FormData();
        formdata.append('constat', constatFile);
        return this.http.put(`${this.resourceUrlQuotation}/${id}/${label}`, formdata).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });

    }

    updateAttachmentsFacture(id: number, constatFile: File, label: String): Observable<Attachment> {
        let formdata: FormData = new FormData();
        formdata.append('constat', constatFile);
        return this.http.put(`${this.resourceUrlFacture}/${id}/${label}`, formdata).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });

    }

    findTodos(): Observable<TodoPrestationPec[]> {
        return this.http.get(this.toDoListUrl).map((res: Response) => {
            return res.json();
        });
    }
    createPrestationDemontage(formdata: FormData, id: number): Observable<SinisterPec> {
        return this.http.post(`${this.resourceUrl}/${id}`, formdata).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    getBonSortiePdf(id: number): Observable<Blob> {
        return this.http.get(`${this.resourceUrlBonSortiePdf}/${id}`, { responseType: ResponseContentType.Blob }).map((res: Response) => {
            let mediatype = 'application/pdf;charset=UTF-8';

            const data = res.blob();
            return data;
        });
    }

    /*getGTPdf(id: number): Observable<Blob> {
        return this.http.get(`${this.resourceUrlGTPdf}/${id}`, { responseType: ResponseContentType.Blob }).map((res: Response) => {
            let mediatype = 'application/pdf;charset=UTF-8';

            let data  = res.blob();
            if(res.blob().size == 0){
                data = null;
            }
            
            return data;
        });
    }*/


    getOrdreMissionExpertPdf(id: number): Observable<Blob> {
        return this.http.get(`${this.resourceUrlOrdreMisssionExpertPdf}/${id}`, { responseType: ResponseContentType.Blob }).map((res: Response) => {
            let mediatype = 'application/pdf;charset=UTF-8';
            const data = res.blob();;
            return data;
        });
    }

    getSignatureAccordPdf(id: number): Observable<Blob> {
        return this.http.get(`${this.resourceUrlSignatureAccordPdf}/${id}`, { responseType: ResponseContentType.Blob }).map((res: Response) => {
            let mediatype = 'application/pdf;charset=UTF-8';
            const data = res.blob();;
            return data;
        });
    }

    generatePrestationPEC(sinisterPec: SinisterPec): Observable<Response> {
        const copy = this.convertSinisterPec(sinisterPec);
        return this.http.post(this.resourceUrlExportAffectExpert, copy);
    }
    generatelettreIDA(sinisterPec: SinisterPec): Observable<Response> {
        console.log(sinisterPec)
        const copy = this.convertSinisterPec(sinisterPec);
        return this.http.post(this.resourceUrlExportlettreIDA, copy);
    }
    generateQuoatationPdf(quote: Quotation): Observable<Response> {
        return this.http.post(this.generateQuotePdf, quote);
    }
    generateBonSortie(sinisterPec: SinisterPec): Observable<Response> {
        console.log(sinisterPec)
        const copy = this.convertSinisterPec(sinisterPec);
        return this.http.post(this.resourceUrlBonSortie, copy);
    }
    generateOrdreMissionExpert(sinisterPec: SinisterPec): Observable<Response> {
        console.log(sinisterPec)
        const copy = this.convertSinisterPec(sinisterPec);
        return this.http.post(this.resourceUrlOrdreMissionExpert, copy);
    }
    generatelettreReouverture(sinisterPec: SinisterPec): Observable<Response> {
        console.log(sinisterPec);
        const copy = this.convertSinisterPec(sinisterPec);
        return this.http.post(this.resourceUrlExportlettreReouverture, copy);
    }

    decider(sinisterPec: SinisterPec, decision: string, motifs: number[]): Observable<SinisterPec> {
        const copy = this.convert(sinisterPec);
        return this.http.put(`${this.resourceUrl}/${decision}/${motifs}`, copy).map((res: Response) => {
            return res.json();
        });
    }

    /* get  url de creation de nouveau estimation   Gt estimate */
    getEstimateCreationUrl(id: number, req?: any): Observable<Estimation> {
        const options = createRequestOption(req);
        return this.http.get(`${this.gaEstimateCreation}/${id}`, options).map((res: Response) => {
            return res.json();
        });

    }
    /* get  url de update de nouveau estimation   Gt estimate */
    getEstimateUpdateUrl(id: number, sinisterPecId: number): Observable<Estimation> {
        this.id = id;
        const options = createRequestOption(id);
        return this.http.get(`${this.gaEstimate}/${id}/${sinisterPecId}`, options).map((res: Response) => {
            return res.json();
        });
    }
    /* get  url file storage  */
    getUrlFileStorage(): Observable<Estimation> {
        const options = createRequestOption();
        return this.http.get(`${this.fileStorege}`, options).map((res: Response) => {
            return res.json();
        });

    }

    findjournal(id: number): Observable<SinisterPec> {
        this.id = id;
        console.log("get id prestation  pec" + id);
        return this.http.get(`${this.resourceUrlJounal}/${id}`).map((res: Response) => {
            return res.json();
        });
    }
    findUserForAuthorityGestionnaire(): Observable<User[]> {
        return this.http.get(this.resourceuserForAuthorityGestionnaireUrl).map((res: Response) => {
            return res.json();
        });
    }

    findPECByDossier(id: number): Observable<SinisterPec> {
        return this.http.get(`${this.resourcePECByDossierUrl}/${id}`).map((res: Response) => {
            return res.json();
        });
    }


    findAllPrestationPECWhenTiersIsNotNull(id: number, req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(`${this.resourceUrlprestationPECWhenTiersNotNull}/${id}`, options)
            .map((res: Response) => this.convertResponse(res));
    }

    findAllPrestationPECWithValidAccord(id: number, req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(`${this.resourceUrlprestationPECWithValidAccord}/${id}`, options)
            .map((res: Response) => this.convertResponse(res));
    }
    findByEtatAccord(userId: number, etat: number, req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(`${this.resourceUrlSignatureAccordApec}/${userId}/${etat}`, options)

            .map((res: Response) => this.convertResponse(res));
    }
    findAllPrestationPECOriginalsPrinted(userId: number, req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(`${this.resourceUrlprestationPECOriginalsPrinted}/${userId}`, options)
            .map((res: Response) => this.convertResponse(res));
    }

    findAllSinPecForSignatureBonSortie(userId: number, req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(`${this.resourceUrlprestationPECForSignatureBonSortie}/${userId}`, options)
            .map((res: Response) => this.convertResponse(res));
    }

    findAllprestationPECAcceptedExpertAndReparateurNotNull(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrlprestationPECAcceptedWhenRepaExpertNotNull, options)
            .map((res: Response) => this.convertResponse(res));

    }

    findAllPrestationPECForIdaOuverture(userId: number, req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(`${this.resourceUrlIdaOuverture}/${userId}`, options)
            .map((res: Response) => this.convertResponse(res));

    }

    findAllSinPecWithDecision(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrlwithDecision, options)
            .map((res: Response) => this.convertResponsePec(res));

    }

    findPrestationEmpty(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrlPecEmpty, options)
            .map((res: Response) => this.convertResponse(res));

    }

    annulerAffecterReparateur(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.annulerAffectationReparateur, options)
            .map((res: Response) => this.convertResponsePec(res));

    }
    findAllPrestationPECWithGenerateAccordStatusForDevis(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrlprestationPECWithGenerateAccordStatusForDevis, options)
            .map((res: Response) => this.convertResponse(res));

    }

    findAllPrestationPECForModificationPrestation(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrlprestationPECForModificationPrestation, options)
            .map((res: Response) => this.convertResponsePec(res));

    }

    findAllPrestationPECWithCanceledDecision(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrlprestationPECWithCanceledDecision, options)
            .map((res: Response) => this.convertResponse(res));

    }

    findAllPrestationPECInProgressDecision(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrlprestationPECInProgress, options)
            .map((res: Response) => this.convertResponse(res));

    }

    findAllPrestationPECWithAcceptedDecision(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrlprestationPECWithAcceptedDecision, options)
            .map((res: Response) => this.convertResponse(res));

    }

    findAllPrestationPECWitReserveDecision(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrlprestationPECWithReserveDecision, options)
            .map((res: Response) => this.convertResponse(res));

    }

    findAllNewExternanlDemands(userId: number, req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http
            .get(`${this.resourceDemandUrl}/${userId}`, options)
            .map((res: Response) => this.convertResponsePec(res));
    }

    findAccordValidBySinisterPec(id: number, req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http
            .get(`${this.resourceAccordBySinisterUrl}/${id}`, options)
            .map((res: Response) => this.convertResponsePec(res));
    }

    findAccordBySinisterPec(id: number, req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http
            .get(`${this.resourceAccordBySinisterPecUrl}/${id}`, options)
            .map((res: Response) => this.convertResponsePec(res));
    }

    findAllPrestationPECAcceptedReparateurNull(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrlprestationPECAcceptedWhenRepairNull, options)
            .map((res: Response) => this.convertResponsePec(res));
    }

    findAttachmentBonSortie(id: number): Observable<Attachment> {
        return this.http.get(`${this.resourceUrlBonSortieAttachments}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            //this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    findPrestRefusedAprouvedAndAprouvedWithModif(userId: number, req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http
            .get(`${this.resourceSinisterPecRefused}/${userId}`, options)
            .map((res: Response) => this.convertResponsePec(res));
    }


    findPrestViewRefusedAprouvedAndAprouvedWithModif(userId: number, req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http
            .get(`${this.resourceUrlView}/refused/${userId}`, options)
            .map((res: Response) => this.convertResponsePec(res));
    }

    
    getPieceBySinPecAndLabelForAccordComplementaire(entityName: string, id: number, label: string): Observable<Blob> {
        return this.http.get(`${this.resourceUrlPiecesJointesFileAccordComplementaire}/${id}/${entityName}/${label}`, { responseType: ResponseContentType.Blob }).map((res: Response) => {
            const data = res.blob();;
            return data;
        });
    }

    findPrestCanceledAprouvedAndAprouvedWithModif(id: number, req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http
            .get(`${this.resourceSinisterPecCanceled}/${id}`, options)
            .map((res: Response) => this.convertResponsePec(res));
    }

    findPrestAccWithResrveAprouvedAndAprouvedWithModif(id: number, req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http
            .get(`${this.resourceSinisterPecAccWithResrve}/${id}`, options)
            .map((res: Response) => this.convertResponsePec(res));
    }

    findPrestAccWithChangeStatusAprouvedAndAprouvedWithModif(id: number, req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http
            .get(`${this.resourceSinisterPecAccWithChangeStatus}/${id}`, options)
            .map((res: Response) => this.convertResponsePec(res));
    }

    findPrestAccRefusedAndCanceledAndAprouvedAndAprouvedWithModif(id: number, req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http
            .get(`${this.resourceSinisterPecRefusedAndCanceled}/${id}`, options)
            .map((res: Response) => this.convertResponsePec(res));
    }


       
    findViewPrestAccRefusedAndCanceledAndAprouvedAndAprouvedWithModif(id: number, req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http
            .get(`${this.resourceUrlView}/refused-and-canceled/${id}`, options)
            .map((res: Response) => this.convertResponsePec(res));
    }


    queryConsulterDemandePec(userId: number, req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http
            .get(`${this.resourceUrlPecsConsulterDemandePec}/${userId}`, options)
            .map((res: Response) => this.convertResponsePec(res));
    }

    queryBeingProcessed(id: number, req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(`${this.resourceUrlPecsBeingProcessed}/${id}`, options)
            .map((res: Response) => this.convertResponsePec(res));
    }

    getImprimeAttachments(id: number, req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(`${this.resourceUrlImprimeAttachments}/${id}`, options)
            .map((res: Response) => this.convertResponsePec(res));
    }
    getAutresPiecesJointes(id: number, req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(`${this.resourceUrlAutresPiecesJointesFile}/${id}`, options)
            .map((res: Response) => this.convertResponsePec(res));
    }
    getPhotoReparationAttachments(id: number, req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(`${this.resourceUrlPhotoReparationAttachments}/${id}`, options)
            .map((res: Response) => this.convertResponsePec(res));
    }

    getExpertiseAttachments(id: number, req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(`${this.resourceUrlExpertiseAttachments}/${id}`, options)
            .map((res: Response) => this.convertResponsePec(res));
    }

    getPlusPecAttachments(id: number, req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(`${this.resourceUrlPlusPecAttachments}/${id}`, options)
            .map((res: Response) => this.convertResponsePec(res));
    }

    getPhotoAvantReparationAttachments(entityName: string, entityId: number, req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(`${this.resourceUrlPhotoAvantReparationAttachments}/${entityName}/${entityId}`, options)
            .map((res: Response) => this.convertResponsePec(res));
    }

    getHistoryAttachments(id: number, name: string): Observable<ResponseWrapper> {
        return this.http.get(`${this.resourceUrlAttachmentsHistory}/${id}/${name}`, null)
            .map((res: Response) => this.convertResponsePec(res));
    }

    getNbreMissionReparator(id: number, req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(`${this.resourceNbreMissionReparatorUrl}/${id}`, options)
            .map((res: Response) => this.convertResponse(res));

    }
    getNbreMissionExpert(id: number, req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(`${this.resourceNbreMissionExpertUrl}/${id}`, options)
            .map((res: Response) => this.convertResponse(res));

    }

    updatePecForQuotationAndAttachments(sinisterPec: SinisterPec, modeEdit: boolean, faceAvantDroitFiles: File, carteGriseFiles: File, faceAvantGaucheFiles: File,
        faceArriereDroitFiles: File, faceArriereGaucheFiles: File, finitionFiles: File, nSerieFiles: File, immatriculationFiles: File, compteurFiles: File): Observable<SinisterPec> {
        let formdata: FormData = new FormData();
        formdata.append('Face Avant Droit', faceAvantDroitFiles);
        formdata.append('Carte Grise Quotation', carteGriseFiles);
        formdata.append('Face Avant Gauche', faceAvantGaucheFiles);
        formdata.append('Face Arriere Droit', faceArriereDroitFiles);
        formdata.append('Face Arriere Gauche', faceArriereGaucheFiles);
        formdata.append('Finition', finitionFiles);
        formdata.append('NSerie', nSerieFiles);
        formdata.append('Immatriculation', immatriculationFiles);
        formdata.append('Compteur', compteurFiles);
        formdata.append('sinisterPec', new Blob([JSON.stringify(sinisterPec)], { type: 'application/json' }));
        return this.http.put(`${this.resourceUrl}/quotation-with-attachment/${modeEdit}`, formdata).map((res: Response) => {
            const jsonResponse = res.json();
            return jsonResponse;
        });
    }

    saveAttachmentsBonSortie(id: number, bonSortieFile: File, label: String, description: String, original: boolean): Observable<Attachment> {
        let formdata: FormData = new FormData();
        formdata.append('constat', bonSortieFile);
        return this.http.post(`${this.resourceUrlBonSortieAttachment}/${id}/${label}/${description}/${original}`, formdata).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });

    }

    getPieceBySinPecAndLabel(entityName: string, id: number, label: string): Observable<Blob> {
        return this.http.get(`${this.resourceUrlPiecesJointesFile}/${id}/${entityName}/${label}`, { responseType: ResponseContentType.Blob }).map((res: Response) => {
            const data = res.blob();
            return data;
        });
    }

    getPieceBySinPecAndLabelIOOB(entityName: string, id: number, label: string): Observable<Blob> {
        return this.http.get(`${this.resourceUrlPiecesJointesFileIOOB}/${id}/${entityName}/${label}`, { responseType: ResponseContentType.Blob }).map((res: Response) => {
            const data = res.blob();
            return data;
        });
    }

    updatePecAndAttachment(sinisterPec: SinisterPec, faceAvantDroitFiles: File, carteGriseFiles: File, faceAvantGaucheFiles: File,
        faceArriereDroitFiles: File, faceArriereGaucheFiles: File, finitionFiles: File, nSerieFiles: File, immatriculationFiles: File, compteurFiles: File): Observable<SinisterPec> {
        let formdata: FormData = new FormData();
        formdata.append('Face Avant Droit', faceAvantDroitFiles);
        formdata.append('Carte Grise Quotation', carteGriseFiles);
        formdata.append('Face Avant Gauche', faceAvantGaucheFiles);
        formdata.append('Face Arriere Droit', faceArriereDroitFiles);
        formdata.append('Face Arriere Gauche', faceArriereGaucheFiles);
        formdata.append('Finition', finitionFiles);
        formdata.append('NSerie', nSerieFiles);
        formdata.append('Immatriculation', immatriculationFiles);
        formdata.append('Compteur', compteurFiles);
        formdata.append('sinisterPec', new Blob([JSON.stringify(sinisterPec)], { type: 'application/json' }));
        return this.http.put(this.resourceUrlPecAndAttachment, formdata).map((res: Response) => {
            const jsonResponse = res.json();
            return jsonResponse;
        });
    }

    updatePecModifPrix(id: number): Observable<SinisterPec> {
        //const copy = this.converter(sinisterPec);
        return this.http.put(`${this.resourceUrlModifPrixInst}/${id}`, id).map((res: Response) => {
            const jsonResponse = res.json();
            //this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    getPieceByAttachmentIdAndEntityName(entityName: string, id: number): Observable<Blob> {
        return this.http.get(`${this.resourceUrlPiecesJointesFileForAttachment}/${id}/${entityName}`, { responseType: ResponseContentType.Blob }).map((res: Response) => {
            const data = res.blob();;
            return data;
        });
    }

    getAttachmentsAccordSigne(entityName: string, entityId: number, label: string, req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(`${this.resourceUrlAttachmentsAccordSigne}/${entityName}/${entityId}/${label}`, options)
            .map((res: Response) => this.convertResponsePec(res));
    }

    updatePec(sinisterPecId: number): Observable<SinisterPec> {
        //const copy = this.converter(sinisterPec);
        return this.http.put(`${this.resourceUrlSignatureBonS}/${sinisterPecId}`, sinisterPecId).map((res: Response) => {
            const jsonResponse = res.json();
            //this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }


    private convertSinisterPec(sinisterPec: SinisterPec): SinisterPec { // convertAffectExpert
        const copy: SinisterPec = Object.assign({}, sinisterPec);
        return copy;
    }
}


