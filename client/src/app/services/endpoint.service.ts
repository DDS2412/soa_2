import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class EndpointService {
  public OLD_URL: string;
  public NEW_URL: string;

  constructor() {
    this.OLD_URL = "https://localhost:8444/space_marine_base";
    this.NEW_URL = "https://localhost:8443/space_marine_2";
  }
}
