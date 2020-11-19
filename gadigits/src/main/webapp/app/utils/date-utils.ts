import { DatePipe } from '@angular/common';
import { Injectable } from '@angular/core';

/**
 * An utility service for date.
 */
@Injectable()
export class DateUtils {
    private pattern = 'yyyy-MM-dd';
    private patternDateTime = 'yyyy-MM-ddTHH:mm:ss';
    private datePipe = new DatePipe('en');

    constructor() { }

    /**
     * Method to convert the date time from server into JS date object
     */
    convertDateTimeFromServer(date: any): Date {
        if (date) {
            return new Date(date);
        } else {
            return null;
        }
    };

    /**
     * Method to convert the JS date object into specified date pattern
     */
    convertDateTimeToServer(date, pattern): string {
        if (pattern === undefined) {
            pattern = this.patternDateTime;
        }
        if (date) {
            const newDate = new Date(date.year, date.month - 1, date.day, date.hour, date.minute, date.second);
            return this.datePipe.transform(newDate, pattern);
        } else {
            return null;
        }
    };

    /**
     * Method to convert the JS date object into specified date pattern
     */
    convertCalDateToJsDate(date): Date {
        if (date) {
            return new Date(date.year, date.month - 1, date.day);;
        } else {
            return null;
        }
    };

}
