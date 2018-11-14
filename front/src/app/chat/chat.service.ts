import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Question} from "./question";

@Injectable()
export class ChatService {

  constructor(private http: HttpClient) { }

  chat(question: Question): Observable<Text> {
    return this.http.post(`sys/robot`, question);
  }

}
