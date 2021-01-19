import { Component, OnInit } from '@angular/core';
import {FormControl, Validators} from "@angular/forms";
import {SpaceMarine} from "../../models/space.marine";
import {ApiService} from "../../services/api.service";
import {EndpointService} from "../../services/endpoint.service";
import {LocalStorageService} from "../../services/local-storage.service";

@Component({
  selector: 'app-new-page',
  templateUrl: './new-page.component.html',
  styleUrls: ['./new-page.component.css']
})
export class NewPageComponent implements OnInit {
  public errorMsg: boolean;

  public spaceMarine : SpaceMarine;

  constructor(private apiService: ApiService,  private storage: LocalStorageService) {
    if (storage.getItem("state_api") =="true") {
      this.apiService.setOldEndpoint();
    } else {
      this.apiService.setNewEndpoint();
    }
  }

  ngOnInit(): void {
    this.spaceMarine = SpaceMarine.createBasicSpaceMarine();
  }

  public createSpaceMarine(): void {
    this.errorMsg = this.apiService.createNewSpaceMarine(this.spaceMarine);
  }
}
