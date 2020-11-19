import { Component, AfterViewInit, Renderer, ElementRef } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Router } from '@angular/router';
import { JhiEventManager } from 'ng-jhipster';

import { LoginService, StateStorageService } from '../shared';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements AfterViewInit {
  authenticationError: boolean;
  password: string;
  rememberMe: boolean;
  username: string;
  credentials: any;

  constructor(
      private eventManager: JhiEventManager,
      private loginService: LoginService,
      private stateStorageService: StateStorageService,
      private elementRef: ElementRef,
      private renderer: Renderer,
      private router: Router
  ) {
      this.credentials = {};
  }

  ngAfterViewInit() {
	  this.loginService.reLogin();
      this.renderer.invokeElementMethod(this.elementRef.nativeElement.querySelector('#username'), 'focus', []);
  }
 
  cancel() {
      this.credentials = {
          username: null,
          password: null,
          rememberMe: true
      };
      this.authenticationError = false;
  }

  login() {
      this.loginService.login({
          username: this.username,
          password: this.password,
          rememberMe: this.rememberMe
      }).then(() => {
          this.authenticationError = false;
          if (this.router.url === '/register' || (/activate/.test(this.router.url)) ||
              this.router.url === '/finishReset' || this.router.url === '/requestReset') {
              this.router.navigate(['']);
              localStorage.setItem('login-event', 'login' + Math.random());
          }

          this.eventManager.broadcast({
              name: 'authenticationSuccess',
              content: 'Sending Authentication Success'
          });

          // // previousState was set in the authExpiredInterceptor before being redirected to login modal.
          // // since login is succesful, go to stored previousState and clear previousState
          const redirect = this.stateStorageService.getUrl();
          if (redirect) {
              this.router.navigate(['']);
          }
      }).catch(() => {
          this.authenticationError = true;
      });
  }

  register() {
      this.router.navigate(['/register']);
  }

  requestResetPassword() {
      this.router.navigate(['/reset', 'request']);
  }
}
