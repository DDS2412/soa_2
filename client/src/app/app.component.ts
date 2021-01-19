import { Component } from '@angular/core';
import {LocalStorageService} from "./services/local-storage.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'client';

  constructor(private storage: LocalStorageService){
    if(storage.getItem("state_api") == null){
      storage.setItem("state_api", "true");
    }
  }
}
