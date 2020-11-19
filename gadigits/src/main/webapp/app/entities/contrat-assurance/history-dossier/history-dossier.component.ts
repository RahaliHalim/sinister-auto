import { Component, OnInit, Input } from '@angular/core';
import { GaDatatable } from '../../../constants/app.constants';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Sinister, SinisterService, SinisterPrestation } from '../../sinister';
import { ResponseWrapper } from '../../../shared';
import { Subject } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { ActivatedRoute } from '@angular/router';
import { HistoryService, History } from '../../history';

@Component({
  selector: 'jhi-history-dossier',
  templateUrl: './history-dossier.component.html',
  styles: []
})
export class HistoryDossierComponent implements OnInit {

  @Input() entityId: number;
  @Input() entityName: string;
  histories: History[];
  sortedHistories: History[];
  dtOptions: any = {};
  dtTrigger: Subject<History> = new Subject();


  constructor(
    public activeModal: NgbActiveModal,
    private historyService: HistoryService,
  ) { }

  ngOnInit() {
    console.log(this.entityId);


    this.dtOptions = GaDatatable.defaultDtOptions;
    this.historyService.findHistoriesByEntity(this.entityId, this.entityName).subscribe((res: ResponseWrapper) => {
      this.histories = res.json;

      this.histories.forEach(element => {
        element.descriptiveOfChange = '';
        element.changeValues.forEach(changeValues => {
          element.descriptiveOfChange = element.descriptiveOfChange + '\n' + changeValues.nameAttribute + " est changé de " + changeValues.lastValue + " en " + changeValues.newValue + '\n';
        });
      });
      this.sortedHistories = this.histories.sort((val2, val1) => { return new Date(val2.operationDate).getTime() - new Date(val1.operationDate).getTime() });
      //this.dtTrigger.next();
    });
  }

  clear() {
    this.activeModal.dismiss('cancel');
  }
}
