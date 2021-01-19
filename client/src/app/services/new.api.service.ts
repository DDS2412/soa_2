import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {NgxXml2jsonService} from "ngx-xml2json";
import {Router} from "@angular/router";
import {EndpointService} from "./endpoint.service";
import {map} from "rxjs/operators";
import * as converter from "xml-js";

@Injectable({
  providedIn: 'root'
})
export class NewApiService {
  private starshipId: number;
  private apiEndpoint: string;
  public apiStarship: string;

  private httpOptions;

  constructor(private http: HttpClient,
              private ngxXml2jsonService: NgxXml2jsonService,
              private router: Router,
              private endpoint: EndpointService) {
    this.apiEndpoint = endpoint.NEW_URL;

    this.apiStarship = "/starship/";
    this.starshipId = 1;

    this.httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/xml',
        'Accept': 'application/xml',
        'Response-Type': 'text'
      })
    }
  }

  public loadSpaceMarine(spaceMarineId: number){
    return this.http.post(`${this.apiEndpoint}${this.apiStarship}${this.starshipId}/load/${spaceMarineId}`, null, this.httpOptions);
  }

  public unloadSpaceMarine(spaceMarineId: number){
    return this.http.post(`${this.apiEndpoint}${this.apiStarship}${this.starshipId}/unload/${spaceMarineId}`, null, this.httpOptions);
  }

  public getSpaceMarineState(spaceMarineId: number){
    return  this
      .http
      .get(`${this.apiEndpoint}${this.apiStarship}space/marine/${spaceMarineId}`,
        {responseType: 'text'})
      .pipe(
        map((res) => {
          let xmlToJson = converter.xml2json(res, {compact: true, spaces: 2});
          return JSON.parse(xmlToJson);
        }));
  }
}
