import { Injectable } from '@angular/core';
import {Question} from "./chat/question";
import {Observable} from "rxjs";
import {Result} from "./chat/result";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class ChatService {

  constructor(private http: HttpClient) { }

  chat(question: Question): Observable<Result> {
    return this.http.post(`http://localhost:8090/sys/robot`, question);
  }
}
