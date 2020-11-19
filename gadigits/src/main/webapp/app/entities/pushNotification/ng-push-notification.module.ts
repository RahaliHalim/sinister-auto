import { BrowserModule } from '@angular/platform-browser';

import { ModuleWithProviders, NgModule } from '@angular/core';

import { PushNotificationService } from './ng-push-notification.service';
import { PushNotificationSettings } from './ng-push-notification.settings';
import { DEFAULT_NOTIFICATION_SETTINGS } from './ng-push-notification.config';

@NgModule({declarations: [
    
  ],
  imports: [
    BrowserModule
  ],
  providers: [PushNotificationService],
  bootstrap: []
})
export class PushNotificationModule {
    static forRoot(config?: PushNotificationSettings): ModuleWithProviders {
        return {
            ngModule: PushNotificationModule,
            providers: [
                {provide: DEFAULT_NOTIFICATION_SETTINGS, useValue: config},
                PushNotificationService,
            ],
        };
    }
}