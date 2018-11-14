import {Component, OnInit} from '@angular/core';
import {NzMessageService} from "ng-zorro-antd";
import {Text} from "./text";
import {ChatService} from "./chat.service";
import {Question} from "./question";

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
              private chatService: ChatService) {
  }

  ngOnInit() {
  }

  send(): void {
    if (this.text == "" || this.text == null) {
      this.message.error("请输入你要问的问题哟！")
    } else {
      this.isLoading = true;

      this.infos.push(new Text(2, this.text));
      this.chatService.chat(new Question(this.text)).subscribe(
        (a) => {
          // this.infos.push(a);
          this.isLoading = false;
        },
        (e) => {
          this.infos.push(new Text(2, "抱歉，服务器发生错误"));
          this.isLoading = false;
        }
      );
      this.text = "";
      this.isLoading = false;
    }
  }

}
