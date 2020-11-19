import { Governorate } from './../entities/governorate/governorate.model';

import { Injectable } from '@angular/core';

/**
 * An utility service for referential
 */
@Injectable()
export class ReferentialUtils {

    constructor() { }

    /**
     * Method to convert the date time from server into JS date object
     */
    fetchDelegationGovernorate(delegationId: number): Governorate {
        if (delegationId) {
            return new Governorate();
        } else {
            return null;
        }
    };
}
