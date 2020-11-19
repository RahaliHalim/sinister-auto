import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils } from 'ng-jhipster';

import { Devis } from './devis.model';
import { ResponseWrapper, createRequestOption } from '../../shared';
import { AccordPriseCharge } from './accord-prise-charge.model';
import { PieceJointe } from '../piece-jointe/piece-jointe.model';
import { SinisterPec } from '../sinister-pec/sinister-pec.model';

@Injectable()
export class DevisService {

    devis: any;

    pieceJointe: PieceJointe;

    private resourceUrl = 'api/devis/estimation';
    private resourceSearchUrl = 'api/_search/devis';
    private resourceUrlByPrestation = 'api/devis/prestation';
    private resourceUrlQuotationByPrestation = 'api/quotation/prestation';
    private resourceUrlLastDevisVersion = 'api/devis/lastVersion';
    private resourceUrlSoumettre = 'api/soumettreDevis';
    private resourceUrlSoumis = 'api/devis/soumis';
    private resourceUrlAccepteOuValideOuRefuse = 'api/devis/accepteOuValideOuRefuse';
    private refuserUrl = 'api/devis/refuser';
    private accepterUrl = 'api/devis/accepterDevis';
    private validerUrl = 'api/devis/validerDevis';
    private valideElseSoumisUrl = 'api/devis/valideElseSoumis';
    private resourceUrlValide = 'api/devis/valide';
    private resourceUrlAccepte = 'api/devis/accepte';
    private resourceUrlFacture = 'api/devis/facture';
    private resourceUrlBonSortieAccepte = 'api/devis/bonSortieAccepte';
    private resourceUrlRefusAferFacture = 'api/devis/refusAferFacture';
    private resourceUrlSauvegarde = 'api/devis/sauvegarde';
    private resourceUrlSauvegardeRefuseValidGes = 'api/devis/sauvegardeRefuseValidGes';
    private resourceUrlExportBonSortie = 'api/bonSortiePdf';
    private resourceUrlExportPiece = 'api/pieceJointePdf';
    private resourceUrlExportAccordPriseEnCharge = 'api/accordPriseChargePdf';
    private validerGesUrl = 'api/validGesTech';
    private resourceLastEtatDevisUrl = 'api/devis/lastEtatDevis';
    private resourceUrlValidGes = 'api/devis/valideGestTechnique';
    
    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(devis: Devis): Observable<Devis> {
        const copy = this.convert(devis);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }
    soumettre(devis: Devis): Observable<Devis> {


        /*this.find(id).subscribe((res) => this.devis = res)
        return this.http.put(`${this.resourceUrlSoumettre}/${id}`, this.devis).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });*/

       
        const copy = this.convert(devis);
        return this.http.post(this.resourceUrlSoumettre, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }
    generateBonSortie(accordPriseCharge: AccordPriseCharge): Observable<Response> {
        const copy = this.convertAccordPriseCharge(accordPriseCharge);
        return this.http.post(this.resourceUrlExportBonSortie, copy);
    }

    generatePiece(pieceJointe : PieceJointe): Observable<Response> {
        const copy = this.convertPieceJointe(pieceJointe);
        return this.http.post(this.resourceUrlExportPiece, copy);
    }

    

    /*generateAccordPrisCharge(accordPriseCharge: AccordPriseCharge ): Observable<Response> {
        const copy = this.convertAccordPriseCharge(accordPriseCharge);
        return this.http.post(this.resourceUrlExportAccordPriseEnCharge, copy);
    }*/
    generateAccordPrisCharge(sinisterPec: SinisterPec, id: number,confirmation: boolean): Observable<Response> {
        //const copy = this.convertAccordPriseCharge(sinisterPec);
        return this.http.post(`${this.resourceUrlExportAccordPriseEnCharge}/${id}/${confirmation}`, sinisterPec);
    }

    update(devis: Devis): Observable<Devis> {
        const copy = this.convert(devis);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }


    updateFacturation(devis: Devis): Observable<Devis> {
        const copy = this.convert(devis);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return  res.json();
             
        });
    }

    accepter(id: number): Observable<Devis> {
        this.find(id).subscribe((res) => this.devis = res)
        return this.http.put(`${this.accepterUrl}/${id}`, this.devis).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    valider(id: number): Observable<Devis> {
        this.find(id).subscribe((res) => this.devis = res)
        return this.http.put(`${this.validerUrl}/${id}`, this.devis).map((res: Response) => {
            const jsonResponse = res.json();
            return jsonResponse;
        });
    }

    find(id: number): Observable<Devis> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return jsonResponse;
        });
    }

    findDevisSoumis(id: number): Observable<Devis> {
        return this.http.get(`${this.resourceUrlSoumis}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return jsonResponse;
        });
    }
    findDevisValideElseSoumis(id: number): Observable<Devis> {
        return this.http.get(`${this.valideElseSoumisUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return jsonResponse;
        });
    }

    findDevisValide(id: number): Observable<Devis> {
        return this.http.get(`${this.resourceUrlValide}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return jsonResponse;
        });
    }
    findDevisBonSortieAccepte(id: number): Observable<Devis> {
        return this.http.get(`${this.resourceUrlBonSortieAccepte}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return jsonResponse;
        });
    }
    findDevisRefuseAfterFacture(id: number): Observable<Devis> {
        return this.http.get(`${this.resourceUrlRefusAferFacture}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return jsonResponse;
        });
    }

    findDevisFacture(id: number): Observable<Devis> {
        return this.http.get(`${this.resourceUrlFacture}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return jsonResponse;
        });
    }

    findDevisAccepte(id: number): Observable<Devis> {
        return this.http.get(`${this.resourceUrlAccepte}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return jsonResponse;
        });
    }

    findDevisAccepteOuValideOuRefuse(id: number): Observable<Devis> {
        return this.http.get(`${this.resourceUrlAccepteOuValideOuRefuse}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return jsonResponse;
        });
    }

    findDevisSauvegarde(id: number): Observable<Devis> {
        return this.http.get(`${this.resourceUrlSauvegarde}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return jsonResponse;
        });
    }

    findDevisSauvegardeRefuseValidGes(id: number): Observable<Devis> {
        return this.http.get(`${this.resourceUrlSauvegardeRefuseValidGes}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return jsonResponse;
        });
    }

    findLastDevisVersion(id: number): Observable<Devis> {
        return this.http.get(`${this.resourceUrlLastDevisVersion}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return jsonResponse;
        });
    }

    findListDevisByPrestation(id: number): Observable<Devis[]> {
        return this.http.get(`${this.resourceUrlByPrestation}/${id}`).map((res: Response) => {
            return res.json();
        });
    }
    findQuotationByPrestation(id: number): Observable<Devis> {
        return this.http.get(`${this.resourceUrlQuotationByPrestation}/${id}`).map((res: Response) => {
            return res.json();
        });
    }
    findLastDevisByPrestation(id: number): Observable<Devis> {
        return this.http.get(`${this.resourceLastEtatDevisUrl}/${id}`).map((res: Response) => {
            return res.json();
        });
    }
    findDevisValidGestionnaire(id: number):Observable<Devis> {
        return this.http.get(`${this.resourceUrlValidGes}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return jsonResponse;
        });
    }

    refuserDevis(id: number, motifs: number[]): Observable<Devis> {
        this.find(id).subscribe((res) => this.devis = res)
        console.log("id devis "+this.devis);
        //const copy = this.convert(this.devis);
      
        return this.http.put(`${this.refuserUrl}/${id}/${motifs}`, this.devis).map((res: Response) => {
            const jsonResponse = res.json();
            return jsonResponse;
        });
    }

    validGesAccord(id: number): Observable<Devis> {

        this.find(id).subscribe((res) => this.devis = res)
        return this.http.put(`${this.validerGesUrl}/${id}`, this.devis).map((res: Response) => {
            const jsonResponse = res.json();
            return jsonResponse;
        });

    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    search(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceSearchUrl, options)
            .map((res: any) => this.convertResponse(res));
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convertItemFromServer(entity: any) {
        entity.dateGeneration = this.dateUtils.convertDateTimeFromServer(entity.dateGeneration);
        entity.dateExpertise = this.dateUtils.convertLocalDateFromServer(entity.dateExpertise);
    }

    private convert(devis: Devis): Devis {
        const copy: Devis = Object.assign({}, devis);

        copy.dateGeneration = this.dateUtils.toDate(devis.dateGeneration);
        copy.dateExpertise = this.dateUtils.convertLocalDateToServer(devis.dateExpertise);
            
        return copy;
    }

    private convertAccordPriseCharge(accordPriseCharge: AccordPriseCharge): AccordPriseCharge {
        const copy: AccordPriseCharge = Object.assign({}, accordPriseCharge);
        return copy;
    }

    private convertPieceJointe(pieceJointe: PieceJointe): PieceJointe {
        const copy: PieceJointe = Object.assign({}, pieceJointe);
        return copy;
    }
    
    createEstimation(req?: any): Observable<Response> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl + '/creation', options);
    }
    updateEstimation(id: number): Observable<Devis> {
        return this.http.get(`${this.resourceUrl}+ '/update'+/${id}`).map((res: Response) => {
            return res.json();
        });
    }










}
