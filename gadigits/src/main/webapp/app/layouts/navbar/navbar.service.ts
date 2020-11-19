import { Injectable } from '@angular/core';

@Injectable()
export class NavbarService {
    showHideSidebar(e) {
        e.preventDefault();
        if ($(window).outerWidth() > 1194) {
            $('nav.side-navbar').toggleClass('shrink');
            $('.page').toggleClass('active');
        } else {
            $('nav.side-navbar').toggleClass('show-sm');
            $('.page').toggleClass('active-sm');
        }
    };
}




