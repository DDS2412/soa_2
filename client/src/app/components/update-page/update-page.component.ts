import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {ApiService} from "../../services/api.service";
import {SpaceMarine} from "../../models/space.marine";
import {LocalStorageService} from "../../services/local-storage.service";

@Component({
  selector: 'app-update-page',
  templateUrl: './update-page.component.html',
  styleUrls: ['./update-page.component.css']
})
export class UpdatePageComponent implements OnInit {
  public errorMsg: boolean;
  public spaceMarine : SpaceMarine;
  public spaceMarineId: number;

  constructor(private apiService: ApiService,
              private activateRoute: ActivatedRoute,
              private router: Router,
              private storage: LocalStorageService) {
    if (storage.getItem("state_api") =="true") {
      this.apiService.setOldEndpoint();
    } else {
      this.apiService.setNewEndpoint();
    }
}

  ngOnInit(): void {
    this.spaceMarine = SpaceMarine.createBasicSpaceMarine();
    this.spaceMarineId = this.activateRoute.snapshot.params['id'];

    this.apiService
      .findSpaceMarineById(this.spaceMarineId)
      .subscribe(
        data => this.spaceMarine = SpaceMarine.createSpaceMarineFromObject(data.SpaceMarine),
        error => this.router.navigate(['']));
  }

  public updateSpaceMarine(){
    this.errorMsg = this.apiService.updateSpaceMarine(this.spaceMarine, this.spaceMarineId);
  }
}
