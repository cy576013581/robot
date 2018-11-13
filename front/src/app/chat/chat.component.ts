import { Component, OnInit } from '@angular/core';
import {NzMessageService} from "ng-zorro-antd";
import {Text} from "./text";

@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.css']
})
export class ChatComponent implements OnInit {

  robotName = '小y';

  isLoading = false;

  text : string;

  imessages : Text[] = [];

  constructor(private message: NzMessageService) { }

  ngOnInit() {
  }

  send(): void {
    if (this.text == "" || this.text == null){
      this.message.error("请输入你要问的问题哟！")
    } else{
      this.isLoading = true;

      this.imessages.push(new Text(2,this.text));
      this.imessages.push(new Text(1,"您输入的是："+this.text));
      this.text="";
      this.isLoading = false;
    }
  }

}
