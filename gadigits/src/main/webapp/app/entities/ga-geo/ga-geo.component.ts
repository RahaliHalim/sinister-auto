import { Component, ElementRef, OnInit, OnDestroy, Input} from '@angular/core';
import { Subscription,  } from 'rxjs/Rx';
import { JhiEventManager} from 'ng-jhipster';
import { icon, Map, latLng, tileLayer, marker, Marker,LayerGroup, polyline, Circle } from 'leaflet';
import { RefRemorqueurService } from '../ref-remorqueur';
import { RefRemorqueur } from '../ref-remorqueur';
import { PersonneMorale } from '../personne-morale';
import { PersonnePhysique } from '../personne-physique';
import { Assure } from '../assure';
import { AssureService } from '../assure';
import { VehiculeAssure } from '../vehicule-assure';
import { ContratAssurance } from '../contrat-assurance';
import { ContratAssuranceService } from '../contrat-assurance';
import { Principal, ResponseWrapper } from '../../shared';
import * as L from 'leaflet';
import { SinisterService } from '../sinister/sinister.service';
import { SinisterPrestation } from '../sinister/sinister-prestation.model';
import { Sinister } from '../sinister/sinister.model';
import { DateUtils } from '../../utils/date-utils';
import { SinisterPrestationService } from '../sinister/sinister-prestation.service';
import { Router } from '@angular/router';


interface RmqMarkerMetaData {
    rmqMarkerInstance: Marker<any>;
    rmqComponentInstance: RefRemorqueur;
}

interface AssrMarkerMetaData {
    assrMarkerInstance: Marker<any>;
    assrComponentInstance: Assure;
}

@Component({
    selector: 'jhi-ga-geo',
    templateUrl: './ga-geo.component.html',
    styleUrls: ['./ga-geo.css']
})

export class GageoComponent implements OnInit, OnDestroy {

    vehiculeAssures: VehiculeAssure[] = [];
    vehiculeAssure: VehiculeAssure = new VehiculeAssure();
    refRemorqueurs: RefRemorqueur[];
    refRemorqueur: RefRemorqueur = new RefRemorqueur();
    personneMorales: PersonneMorale[];
    personneMorale: PersonneMorale = new PersonneMorale();
    assures: Assure[];
    assure: Assure = new Assure();
    assuresNotif: Assure[];
    aassures: Assure[];
    aassure: Assure = new Assure();
    personnePhysiques: PersonnePhysique[];
    personnePhysique: PersonnePhysique = new PersonnePhysique();
    contratAssurances: ContratAssurance[] = [];
    contratAssurance: ContratAssurance = new ContratAssurance();
    sinistrePrestations: SinisterPrestation[];
    sinisterS: Sinister = new Sinister();
    rmqOccDataTab: RmqMarkerMetaData[] = [];
    rmqLibDataTab: RmqMarkerMetaData[] = [];
    assrDataTab: AssrMarkerMetaData[] = [];
    rmqProcheListe: RefRemorqueur[] = [];
    eventSubscriber: Subscription;
    currentAccount: any;
    isServed: boolean = false;
    yessssss: string = 'Veuillez affecter un remorqueur parmi cette liste :';
    chaine: string = '<p> Veuillez affecter un remorqueur parmi cette liste :</p>';
    circles: Circle[] = [];
    remqMarkers: Marker[] = [];
    RemqMarker: Marker<any>;
    AssureMarker: Marker<any>;
    clickedRemq = false;
    bb: Circle<any>;
    bbb: boolean = false;
    remqLibLayerG = new LayerGroup<any>();
    remqOccLayerG = new LayerGroup<any>();
    assrServLayerG = new LayerGroup<any>();
    assrLibreLayerG = new LayerGroup<any>();
    map: Map;
    CountMyMessages: number = 0;
    CountServerMessages: number = 0;
    ChatArray: Array<{}> = [];
    message = {};
    bool: boolean = false;;
    interval: any;
    refresh: boolean = false;
    sinister: Sinister;
    sinistrePrestation: SinisterPrestation;
    exit = 0;
    ws: any;
    setInterval = setInterval;
    constructor(
        public principal: Principal,
        private eventManager: JhiEventManager,
        private refRemorqueurService: RefRemorqueurService,
        private assureService: AssureService,
        private contratAssuranceService: ContratAssuranceService,
        private sinistreService: SinisterService,
        private sinistrePrestationService: SinisterPrestationService,
        private owerDateUtils: DateUtils,
        private elementRef: ElementRef,
        private router: Router,

    ) {
       
    }
    ngOnInit() {
       
        this.loadAll();
        this.registerChangeInRefRemorqueurs();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.interval = setInterval(() => {    
            this.loadAll();
             }, 5000);
    }
    
    trace = polyline([
    ]);
    // Define our base layers so we can reference them multiple times
    // Template 1 : Openstreetmap France
    LAYER_OSMF = {
        id: 'OpenstreetmapF',
        name: 'Openstreetmap France',
        enabled: true, 
        layer: tileLayer('https://{s}.tile.openstreetmap.fr/osmfr/{z}/{x}/{y}.png', {
            maxZoom: 20,
            attribution: 'Openstreetmap France'
        })
        };
        // Template 2 : Wikimedia Maps
        LAYER_OCM = {
        id: 'wikimediamaps',
        name: 'Wikimedia Maps',
        enabled: true, 
        layer: tileLayer('https://maps.wikimedia.org/osm-intl/{z}/{x}/{y}.png', {
            maxZoom: 18,
            attribution: 'Wikimedia Maps'
        })
        };
    // Template 3 : Open Street Map
    LAYER_OSM = {
        id: 'openstreetmap',
        name: 'Open Street Map',
        enabled: false,
        layer: tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
            maxZoom: 18,
            attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors',
            detectRetina: true
        })
    };
    // Set the initial set of displayed layers (binding options to html)
    options = {
        layers: [
            this.LAYER_OSM.layer,
            this.trace, 
            this.remqOccLayerG, 
            this.remqLibLayerG, 
            this.assrServLayerG,
            this.assrLibreLayerG
        ],
        zoom: 7,
        center: latLng([35.033333,9.5])
    };

    // Values to bind to Leaflet Directive (binding layers to html)
    layersControlOptions = {
        position: 'bottomright'
    };
    layersControl = {
        baseLayers: {
            'Open Street Map': this.LAYER_OSM.layer,
            'Wikimedia Maps': this.LAYER_OCM.layer,
            'Open Street Map France': this.LAYER_OSMF.layer
        },
        overlays: {
            'Remorqueurs Libres': this.remqLibLayerG,
            'Remorqueurs Occupés': this.remqOccLayerG,
            'Assurés Affecté': this.assrServLayerG,
            'Assurés En Attente' : this.assrLibreLayerG
        } 
    }
    onMapReady(map: Map) {
     this.map = map;
    }

    ngOnDestroy() {

        this.eventManager.destroy(this.eventSubscriber);
        clearInterval(this.interval);
    }

    registerChangeInRefRemorqueurs() {
        this.eventSubscriber = this.eventManager.subscribe('refRemorqueurListModification', (response) => this.loadAll());
    }
    /**
     * Afficher les markers des remorqueurs et assurés
     */

    loadAll() {
        // Traitement des remorqueurs

        this.remqOccLayerG.clearLayers();
        this.remqLibLayerG.clearLayers();
        this.assrServLayerG.clearLayers();
        this.assrLibreLayerG.clearLayers();
        

    

        this.refRemorqueurService.query().subscribe(
            (res: ResponseWrapper) => {
                this.refRemorqueurs = res.json;
                for (let i = 0; i < this.refRemorqueurs.length; i++) {
                    this.sinistreService.findInProgressPrestationsNotView().subscribe((res: ResponseWrapper) => {
                        this.sinistrePrestations = res.json;

                        for (let j = 0; j < this.sinistrePrestations.length; j++) {
                            if (this.sinistrePrestations[j].affectedTugId == this.refRemorqueurs[i].id ) {
                                this.bool = true;
                                break;
                            }else{
                                this.bool = false;
                            }     
                        } 
                        if (this.bool) {
                            console.log("remorqueur occupé");
                            if (this.refRemorqueurs[i].latitude != null && this.refRemorqueurs[i].latitude != undefined
                                && this.refRemorqueurs[i].longitude != null && this.refRemorqueurs[i].longitude != undefined 
                                && this.refRemorqueurs[i].isConnected == true
                                ) {
                                this.RemqMarker = marker([this.refRemorqueurs[i].latitude,
                                this.refRemorqueurs[i].longitude],
                                    {
                                        icon: icon({
                                            iconSize: [25, 41],
                                            iconAnchor: [13, 41],
                                            iconUrl: require('../../../content/images/marker-icon-red.png'),
                                            shadowUrl: require('../../../content/images/marker-shadow.png')
                                        })
                                    })
                                this.onRmqOccClick(this.RemqMarker, this.refRemorqueurs[i]);
                                this.rmqOccDataTab.push({
                                    rmqMarkerInstance: this.RemqMarker,
                                    rmqComponentInstance: this.refRemorqueurs[i]
                                });                                                      
                                this.map.addLayer(this.RemqMarker);
                                
                                
                                this.remqOccLayerG.addLayer(this.RemqMarker);
                                //this.remqOccLayerG.addLayer(this.RemqMarker);
                            }
                        } else {
                            console.log("remorqueur non occupé");
                            if (this.refRemorqueurs[i].latitude != null && this.refRemorqueurs[i].latitude != undefined
                                && this.refRemorqueurs[i].longitude != null && this.refRemorqueurs[i].longitude != undefined
                                && this.refRemorqueurs[i].isConnected == true) {
                                this.RemqMarker = marker([this.refRemorqueurs[i].latitude,
                                this.refRemorqueurs[i].longitude],
                                    {
                                        icon: icon({
                                            iconSize: [25, 41],
                                            iconAnchor: [13, 41],
                                            iconUrl: require('../../../content/images/marker-icon-green.png'),
                                            shadowUrl: require('../../../content/images/marker-shadow.png')
                                        })
                                    })
                                this.onRmqLibClick(this.RemqMarker, this.refRemorqueurs[i]);
                                this.rmqLibDataTab.push({
                                    rmqMarkerInstance: this.RemqMarker,
                                    rmqComponentInstance: this.refRemorqueurs[i]
                                });                                                                
                                this.map.addLayer(this.RemqMarker);
                                this.remqLibLayerG.addLayer(this.RemqMarker);
                                
                            }
                            //this.remqLibLayerG.addLayer(this.RemqMarker);
                            //this.addRmqTooltip(this.RemqMarker, this.refRemorqueurs[i]);
                        }
                        //this.remqMarkers.push(this.RemqMarker);
                        //this.addRmqTooltip(this.RemqMarker, this.refRemorqueurs[i]);
                    });
                }
            });
        // Traitement des assurés
                                this.sinistreService.findInProgressPrestationsNotView().subscribe((res: ResponseWrapper) => {
                                    this.sinistrePrestations = res.json;
                                    for (let i = 0; i < this.sinistrePrestations.length; i++) {
                                        if (this.sinistrePrestations[i].affectedTugId !== null && this.sinistrePrestations[i].affectedTugId !== undefined) {
                                            this.isServed = true;
                                            
                                        } else {
                                            this.isServed = false;
                                        }
                                        
                                   
                                       if (this.isServed) {
                                           
                                            // Assurés Affecté
                                            this.sinistreService.find(this.sinistrePrestations[i].sinisterId).subscribe((sinister: Sinister) => {
                                                this.sinister = sinister;
                                                this.contratAssuranceService.find(this.sinister.contractId).subscribe((contrat: ContratAssurance) => {
                                                    this.contratAssurance = contrat;
                                                    this.assureService.find(this.contratAssurance.assureId).subscribe((assure: Assure) => {
                                                        this.assure = assure;

                                                        // placement des markers        

                                                        if (this.assure.latitude !== null && this.assure.longitude !== null
                                                            && this.assure.latitude !== undefined && this.assure.longitude !== undefined) {
                                                             
                                                            this.AssureMarker = marker([this.assure.latitude,
                                                            this.assure.longitude],
                                                                {
                                                                    icon: icon({
                                                                        iconSize: [25, 41],
                                                                        iconAnchor: [13, 41],
                                                                        iconUrl: require('../../../content/images/marker-icon-violet.png'),
                                                                        shadowUrl: require('../../../content/images/marker-shadow.png')
                                                                    })
                                                                });

                                                            this.assrDataTab.push({
                                                                assrMarkerInstance: this.AssureMarker,
                                                                assrComponentInstance: this.assure
                                                            });
                                                            
                                                            this.addAssrServTooltip(this.AssureMarker, this.assure);
                                                            // on marker click show circle radius 15km
                                                            //this.onAssrClick(this.AssureMarker, this.sinister);
                                                            // list to add to the layersControl : assuré servi                                                                                                                          
                                                            
                                                            this.addAssTooltip(this.AssureMarker, this.assure,1);
                                                            this.map.addLayer(this.AssureMarker);
                                                            this.assrServLayerG.addLayer(this.AssureMarker);
                                                        }

                                                    });
                                                });
                                            });

                                        } else if (!this.isServed) {
                                            // Assurés En Attente 
                                          
                                            this.sinistreService.find(this.sinistrePrestations[i].sinisterId).subscribe((sinister: Sinister) => {
                                                this.sinister = sinister;
                                                this.contratAssuranceService.find(this.sinister.contractId).subscribe((contrat: ContratAssurance) => {
                                                    this.contratAssurance = contrat;
                                                    if (this.contratAssurance.assureId != null && this.contratAssurance.assureId != undefined) {
                                                        this.assureService.find(this.contratAssurance.assureId).subscribe((assure: Assure) => {
                                                            this.assure = assure;
                                                            if (this.assure != null && this.assure != undefined) {
                                                                if (this.assure.latitude !== null && this.assure.latitude !== undefined
                                                                    && this.assure.longitude !== null && this.assure.longitude !== undefined) {
                                                                    this.AssureMarker = marker([this.assure.latitude,
                                                                    this.assure.longitude],
                                                                        {
                                                                            icon: icon({
                                                                                iconSize: [25, 41],
                                                                                iconAnchor: [13, 41],
                                                                                iconUrl: require('../../../content/images/marker-icon-blue.png'),
                                                                                shadowUrl: require('../../../content/images/marker-shadow.png')
                                                                            })
                                                                        });
                                                                    this.assrDataTab.push({
                                                                        assrMarkerInstance: this.AssureMarker,
                                                                        assrComponentInstance: this.assure
                                                                    });
                                                                    this.addAssTooltip(this.AssureMarker, this.assure, 0);
                                                                    //this.map.addLayer(this.AssureMarker);
                                                                    //this.onAssrClick(this.AssureMarker, this.sinister);  
                                                                    this.map.addLayer(this.AssureMarker);                                                                            
                                                                    this.assrLibreLayerG.addLayer(this.AssureMarker);
                                                                    
                                                                }

                                                            }

                                                        });
                                                    }
                                                });
                                            });
                                        }
                                    
                                    
                                };
                                });
   
    }

    // click assure 
    onAssrClick(assrMarker: Marker<any>, sinister: Sinister) {
        assrMarker.addTo(this.map).on('click', <LeafletMouseEvent>(e) => {
            let latlng = new L.LatLng(e.latlng.lat,
                e.latlng.lng);
            if (this.clickedRemq == false) {
                this.bb = new Circle<any>(latlng, { radius: 15000 });
                this.map.setView(latlng, 11);
                this.circles.push(this.bb);
                this.bb.addTo(this.map);
                this.clickedRemq = true;
                this.chercherRemorqueur(e, sinister);// afficher lesq remiorqueurs dans le cercle de rayon 15000
            } else {
                if (e.latlng.equals(this.bb.getLatLng())) {
                    this.map.setView(latLng([35.033333, 9.5]), 7);
                    this.rmqProcheListe = [];
                    let indx = this.circles.indexOf(this.bb);
                    let cc = this.circles.splice(indx, 1);
                    cc[0].removeFrom(this.map);
                    this.clickedRemq = false;
                } else {
                    this.rmqProcheListe = [];
                    this.map.setView(latlng, 11);
                    let indx = this.circles.indexOf(this.bb);
                    let cc = this.circles.splice(indx, 1);
                    cc[0].removeFrom(this.map);
                    this.bb = new Circle<any>(e.latlng, { radius: 15000 });
                    this.circles.push(this.bb);
                    this.bb.addTo(this.map);
                    this.clickedRemq = true;
                    this.chercherRemorqueur(e, sinister);
                }
            }
            if (this.rmqProcheListe.length != 0) {
                assrMarker.addTo(this.map).on('contextmenu', <LeafletMouseEvent>(e) => {
                    assrMarker.bindPopup(this.textPopup(this.rmqProcheListe)).openPopup();
                    this.chaine = '<p> affecter un remorq:</p> <select>';
                });
            } else {
                assrMarker.addTo(this.map).on('contextmenu', <LeafletMouseEvent>(e) => {
                    assrMarker.bindPopup('<p> il n\'existe pas des remorqueurs dans un rayons de 15km</p>', {
                        closeOnClick: true,
                        closeButton: false
                    }).openPopup();
                });
            }
        }
        );

    }
    textPopup(rmqList: RefRemorqueur[]) {
        this.rmqProcheListe.forEach((rmq) => {
            this.chaine += '<option value='
            this.chaine += rmq.societeRaisonSociale;
            this.chaine += '">';
            this.chaine += rmq.societeRaisonSociale;
            this.chaine += '</option>';
        });
        this.chaine += '</select> <p> </p><button type="submit" class="btn btn-success" (onclick)=alert("testttttt")>Affecter</button> </form>';
        return this.chaine;
    }
    chercherRemorqueur(e: any, sinister: Sinister) {
        this.rmqLibDataTab.forEach((rmqData) => {
            let latlng1 = new L.LatLng(rmqData.rmqComponentInstance.latitude,
                rmqData.rmqComponentInstance.longitude);
            let distance = e.latlng.distanceTo(latlng1);
            if (distance <= 15000) {
                rmqData.rmqMarkerInstance.bindPopup('<p> Le remorqueur :' +
                    rmqData.rmqComponentInstance.societeRaisonSociale + ' </p><p> est à ' +
                    parseInt(distance) + ' mètres de </p> <p> l\'assuré: ' +
                    '</p>' + '' + '<a href="javascript:void(0)" class="affect-link" style="border-radius: 4px;background: #4479BA;color: #FFF;padding: 8px 12px;border: solid 1px #20538D;" >Affecter</a>').openPopup();

                    $('.affect-link').on("click",<LeafletMouseEvent>(e) =>{
                        this.sinistreService.findInProgressPrestations().subscribe((res: ResponseWrapper) => {
                            this.sinistrePrestations = res.json;
                            for (let i = 0; i < this.sinistrePrestations.length; i++) {
                                if (this.sinistrePrestations[i].sinisterId == sinister.id && this.sinistrePrestations[i].serviceTypeId == 2) {
                                    
                                        this.sinistrePrestations[i].affectedTugId = rmqData.rmqComponentInstance.id;
                                        this.sinistrePrestations[i].affectedTugLabel = rmqData.rmqComponentInstance.societeRaisonSociale;
                                        this.sinistrePrestationService.update(this.sinistrePrestations[i]).subscribe((res: SinisterPrestation) => {
                                            this.sinistrePrestation = res;
                                            this.loadAll(); 
                                            this.bb.removeFrom(this.map);
                                            this.map.setView([35.033333,9.5],7);
                                        });
                                }
                            }
                        rmqData.rmqMarkerInstance.closePopup();
                        
                        //
                        });
                      })
                this.rmqProcheListe.push(rmqData.rmqComponentInstance);
            }
        });
    }

    myEvent() {
        console.log("");
    }
    
    addRmqTooltip(remqMarker: Marker<any>, remorqueur: RefRemorqueur) {
        console.log("test id remorqueur" + remorqueur.id);
        remqMarker.bindTooltip('<p> Remorqueur: ' + remorqueur.societeRaisonSociale + '</p>');
        remqMarker.on('mouseover', function (e) {
            this.openTooltip();
        });
    }
    addAssTooltip(assMarker: Marker<any>, assure: Assure, occ:any) {
        if (assure.raisonSociale != null && assure.raisonSociale != undefined) {
          if(occ == 1){
            assMarker.bindTooltip('<p> Assuré: ' + assure.raisonSociale + ' est occupé</p>');
            assMarker.on('mouseover', function (e) {
                this.openTooltip();
            });
          }else{
            assMarker.bindTooltip('<p> Assuré: ' + assure.raisonSociale + ' est libre</p>');
            assMarker.on('mouseover', function (e) {
                this.openTooltip();
            });
          }
        } else {
          if(occ == 1){
            assMarker.bindTooltip('<p> Assuré: ' + assure.fullName + ' est occupé</p>');
            assMarker.on('mouseover', function (e) {
                this.openTooltip();
            });
          }else{
            assMarker.bindTooltip('<p> Assuré: ' + assure.fullName + ' est libre</p>');
            assMarker.on('mouseover', function (e) {
                this.openTooltip();
            });
          }
        }
    }
    addAssrServTooltip(assrMarker: Marker<any>, assure: Assure) {
        assrMarker.bindTooltip('<p> L\'assuré: ' + assure.prenom + ' ' +
            assure.nom + ' est affecté à un remorqueur</p>').openTooltip();
    }
    onRmqLibClick(remqMarker: Marker<any>, remorqueur: RefRemorqueur) {
        remqMarker.bindPopup('<p> Remorqueur ' + remorqueur.societeRaisonSociale + ' est libre </p>');
        remqMarker.on('click', function (e) {
            this.openPopup();
        });
    }
    onRmqOccClick(remqMarker: Marker<any>, remorqueur: RefRemorqueur) {
        remqMarker.bindPopup('<p> Remorqueur ' + remorqueur.societeRaisonSociale + ' est occupé </p>').openPopup();
        remqMarker.on('click', function (e) {
            this.openPopup();
        });
    }


}
