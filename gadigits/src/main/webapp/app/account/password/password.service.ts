import { Injectable } from '@angular/core';
import { Http } from '@angular/http';
import { Observable } from 'rxjs/Rx';

@Injectable()
export class PasswordService {

    constructor(private http: Http) {}

    save(newPassword: string, oldPassword: string): Observable<any> {
        let formdata: FormData = new FormData();
        formdata.append('newpassword', newPassword);
        formdata.append('oldpassword', oldPassword);
        return this.http.post('api/account/change_password', formdata);
    }
}
