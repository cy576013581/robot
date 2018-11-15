import {Component, OnInit} from '@angular/core';
import {NzMessageService} from "ng-zorro-antd";
import {Text} from "./text";
import {Question} from "./question";
import {HttpClient} from "@angular/common/http";
import {RequestOptions} from "@angular/http";

@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.css']
})
export class ChatComponent implements OnInit {

  robotName = '小y';

  isLoading = false;

  text: string;

  infos: Text[] = [];

  constructor(private message: NzMessageService,
              private http: HttpClient) {
  }

  ngOnInit() {
  }

  send(): void {
    if (this.text == "" || this.text == null) {
      this.message.error("请输入你要问的问题哟！")
    } else {
      this.isLoading = true;

      this.infos.push(new Text(2, this.text));
      let headers = new Headers({ 'Content-Type': 'application/json' });
      this.http
        .post("http://localhost:8090/sys/robot", headers,new Question(this.text)
        )
        .subscribe(
          val => {
            console.log("Post call successful value returned in body", val);
            // this.infos.push(a);
            this.isLoading = false;
          },
          error => {
            console.log("Post call in error", error);
            this.infos.push(new Text(1, "抱歉，服务器发生错误"));
            this.isLoading = false;
          },
          () => {
            console.log("The Post observable is now completed.");
          }
        );
      this.text = "";
    }
  }

}
