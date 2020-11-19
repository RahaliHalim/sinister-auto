import { Injectable } from '@angular/core';

@Injectable()
export class GAEstimateService {

    receiveMessage(event) {
        var RegexTargetURI = /gtestimate.com/i;
        if (RegexTargetURI.test(event.origin)) {
            alert("User has closed the window in the iFrame");
            //Close iFrame
            var iframe = document.getElementById('theIframe');
            iframe.parentNode.removeChild(iframe);
        } else {
            return;
        }
    }

    addListenerToIframe(e) {
        e.preventDefault();
        window.addEventListener("message", this.receiveMessage, false);
        //        if (window.attachEvent) {
        //            window.attachEvent('onmessage', this.receiveMessage);
        //        } else {
        //            window.addEventListener("message", this.receiveMessage, false);
        //        }
    };
}




