import { Component, Input, OnInit } from '@angular/core';
import { GageoService } from './ga-geo.service';



@Component({
  selector: 'html-marker',
  template: `
    <h3>{{ data.name }}</h3>
    <p>
      {{ data.description }}
    </p>
  `
})
export class HTMLMarkerComponent {
  @Input() data;
}
