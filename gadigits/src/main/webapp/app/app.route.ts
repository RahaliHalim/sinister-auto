import { Route, Routes } from '@angular/router';

import { NavbarComponent } from './layouts';
import { LoginComponent } from './login/login.component';

export const navbarRoute: Route = {
    path: '',
    component: NavbarComponent,
    outlet: 'navbar'
};
