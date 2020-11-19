import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { LeafletModule } from '@asymmetrik/ngx-leaflet';
import { BrowserModule } from '@angular/platform-browser';
import { AuxiliumSharedModule } from '../../shared';

import {
    GageoComponent,
    GageoResolvePagingParams,
    gaGeoRoute
  

} from './';

import { AuxiliumRefRemorqueurModule } from '../ref-remorqueur/ref-remorqueur.module';
import { AuxiliumPersonneMoraleModule } from '../personne-morale/personne-morale.module';
import { AuxiliumPersonnePhysiqueModule } from '../personne-physique/personne-physique.module';
import { AuxiliumAssureModule } from '../assure/assure.module';


const ENTITY_STATES = [
    ...gaGeoRoute,
];

/*const customNotifierOptions: NotifierOptions = {
    position: {
          horizontal: {
              position: 'left',
              distance: 12
          },
          vertical: {
              position: 'bottom',
              distance: 12,
              gap: 10
          }
      },
    theme: 'material',
    behaviour: {
      autoHide: 5000,
      onClick: 'hide',
      onMouseover: 'pauseAutoHide',
      showDismissButton: true,
      stacking: 4
    },
    animations: {
      enabled: true,
      show: {
        preset: 'slide',
        speed: 300,
        easing: 'ease'
      },
      hide: {
        preset: 'fade',
        speed: 300,
        easing: 'ease',
        offset: 50
      },
      shift: {
        speed: 300,
        easing: 'ease'
      },
      overlap: 150
    }
};*/

@NgModule({
    imports: [
        AuxiliumRefRemorqueurModule,
        AuxiliumPersonneMoraleModule,
        AuxiliumAssureModule,
        BrowserModule,
        AuxiliumPersonnePhysiqueModule,
        AuxiliumSharedModule,
        RouterModule.forChild(ENTITY_STATES),
        LeafletModule.forRoot(),

        /*RouterModule.forRoot(ENTITY_STATES, { useHash: true }),
        LeafletModule.forChild()*/
    ],
    declarations: [
        GageoComponent,
    ],
    entryComponents: [
        GageoComponent,
    ],
    providers: [
        GageoResolvePagingParams,
     
        
        
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AuxiliumGaGeoModule {}