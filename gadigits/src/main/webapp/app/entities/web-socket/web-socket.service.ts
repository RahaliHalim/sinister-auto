import { Injectable } from '@angular/core';
import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';

@Injectable()
export class WebSocketService {

  ws: any;

  //constructor(private service1: Service1) { }

  connectAndSend(id: number, typeNotif: string, sinisterPecId: number, quotationId: number){
    //let sockets = new WebSocket("wss://notif.gadigits.com:8443/my-ws/websocket");
    let sockets = new WebSocket("ws://197.13.12.137:8099/my-ws/websocket");
    this.ws = Stomp.over(sockets);
    this.ws.connect({}, this.onConnected.bind(this, id, typeNotif, sinisterPecId, quotationId), this.onError.bind(this));
  }

  onError(error) {
    console.log(error);
}
onConnected(id, typeNotif, sinisterPecId, quotationId) {
  if (id != null && id != undefined) {
    this.ws.send("/app/hello", {}, JSON.stringify({ 'typenotification': typeNotif, 'idApec': id ,'sinisterPecId': sinisterPecId, 'quotationId': quotationId}));
    
  }
}
  /*title = 'app';

  greetings: string[] = [];
  showConversation: boolean = false;
  ws: any;
  name: string;
  disabled: boolean;
  stompClient = null;
  public message: any
  constructor(){}

  connect() {
     //connect to stomp where stomp endpoint is exposed 
    let sockets = new WebSocket("ws://localhost:8080/greeting");
    this.ws = Stomp.over(sockets);
    this.ws.connect({}, this.onConnected.bind(this), this.onError.bind(this));
    /*var socket = new SockJS("http://localhost:8080/greeting");
    this.stompClient = Stomp.over(socket);
    this.stompClient.connect({}, this.onConnected.bind(this), this.onError).bind(this);
  }*/
  /*onError(error) {
    console.log(error);
  }
 
  onConnected() {
    this.ws.subscribe('/topic/reply', (message) => {
      if(message.body) {
        this.message = message.body;
        console.log("message body"+message.body);
      }
    });
    this.ws.send('/app/message', {},JSON.stringify({'name' : "RIDHA BOUAZIZI",'test' : "TEST"}));
 
  }
 
  onMessageReceived() {
 
   console.log("message received------------");
  }
  disconnect() {
    if (this.stompClient != null) {
      this.stompClient.close();
    }
    this.setConnected(false);
  }
  showGreeting(message) {
  }
  setConnected(connected) {
    this.disabled = connected;
  }*/
}
