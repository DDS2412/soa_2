import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {NavbarComponent} from './components/navbar/navbar.component';

import {MatToolbarModule} from '@angular/material/toolbar';
import {MatButtonModule} from '@angular/material/button';
import {NewPageComponent} from './components/new-page/new-page.component';
import {AppRoutingModule} from "./app-routing.module";
import {MatPaginatorModule} from "@angular/material/paginator";
import {MatTableModule} from "@angular/material/table";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {MatIconModule} from "@angular/material/icon";
import {MatCardModule} from "@angular/material/card";
import {TablePageComponent} from "./components/table-page/table-page.component";
import {ApiService} from "./services/api.service";
import {HttpClientModule} from "@angular/common/http";
import {FormsModule} from "@angular/forms";
import {MatSelectModule} from "@angular/material/select";
import {MatSortModule} from "@angular/material/sort";
import {ErrorPageComponent} from './components/error-page/error-page.component';
import {UpdatePageComponent} from './components/update-page/update-page.component';
import {SpaceshipComponent} from './components/spaceship/spaceship.component';
import {MatButtonToggleModule} from "@angular/material/button-toggle";
import {MatSlideToggleModule} from "@angular/material/slide-toggle";

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    NewPageComponent,
    TablePageComponent,
    ErrorPageComponent,
    UpdatePageComponent,
    SpaceshipComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    MatToolbarModule,
    MatButtonModule,
    AppRoutingModule,
    MatPaginatorModule,
    MatTableModule,
    MatInputModule,
    MatIconModule,
    MatFormFieldModule,
    MatCardModule,
    HttpClientModule,
    FormsModule,
    MatSelectModule,
    MatSortModule,
    MatButtonToggleModule,
    MatSlideToggleModule
  ],
  providers: [
    ApiService
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
