import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils } from 'ng-jhipster';

import { Gageo } from './ga-geo.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

import{Assure} from '../assure'

import { LatLngExpression} from 'leaflet';
import { RefRemorqueur } from '../ref-remorqueur';

export class Marker {
  id: number;
  name: String;
  description: String;
  position: LatLngExpression
}

@Injectable()
export class GageoService {

  private resourceUrl = '/ref-remorqueurs/3RemorqueursActif';
  public assure: Assure;
  public latitudeAssure: any;
  public longitudeAssure: any;
   
  
  constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    markers: Marker[] = [
    {
      id: 1,
      name: 'Marker name 1',
      description: 'descr 1',
      position: [ 46.879966, -121.726909 ]
    },
    {
      id: 2,
      name: 'Marker name 2',
      description: 'descr 2',
      position: [ 46.000966, -123.726909 ]
    }
  ];

  getMarkers() {
    return this.markers;
  }

  getMarkerById(id) {
    return this.markers.filter((entry) => entry.id === id)[0];
  }

  changeMarkerData() {
    for(let marker of this.markers) {
      // just add a random number at the end
      marker.description = `Some random value ${Math.random() * 100}`;
    }
  }

  
    recherche3RemorqueurActif(latitudeAssure: number, longitudeAssure: number): Observable<RefRemorqueur> {
      this.latitudeAssure = this.assure.latitude;
      this.longitudeAssure=this.assure.longitude;
      console.log(latitudeAssure + longitudeAssure);
    return this.http.get(`${this.resourceUrl}/${latitudeAssure}/${longitudeAssure}`).map((res: Response) => {
      return res.json();
    });
}
  

}

