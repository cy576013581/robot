import { Component, OnInit } from '@angular/core';
import {NzMessageService} from "ng-zorro-antd";

@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.css']
})
export class ChatComponent implements OnInit {

  robotName = '小y';

  isLoading = false;

  text : string;

  constructor(private message: NzMessageService) { }

  ngOnInit() {
  }

  send(): void {
    if (this.text == "" || this.text == null){
      this.message.error("请输入你要问的问题哟！")
    } else{
      this.isLoading = true;

      this.message.info(this.text);
      this.text="";
      this.isLoading = false;
    }
  }

}
