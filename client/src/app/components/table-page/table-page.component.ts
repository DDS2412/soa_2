import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {MatPaginator} from '@angular/material/paginator';
import {MatTableDataSource} from '@angular/material/table';
import {ApiService} from "../../services/api.service";
import {catchError, map, startWith, switchMap} from "rxjs/operators";
import {merge, Observable} from "rxjs";
import {SpaceMarine} from "../../models/space.marine";
import {MatSort} from "@angular/material/sort";
import {LocalStorageService} from "../../services/local-storage.service";
import {NewApiService} from "../../services/new.api.service";

@Component({
  selector: 'app-table-page',
  templateUrl: './table-page.component.html',
  styleUrls: ['./table-page.component.css']
})
export class TablePageComponent implements OnInit, AfterViewInit {
  public spaceMarineDataSource: MatTableDataSource<SpaceMarine> = new MatTableDataSource();

  public displayedColumns = [
    'Name',
    'Health',
    'Height',
    'Category',
    'Melee Weapon',
    'ChapterName',
    'ChapterMarinesCount',
    'CoordinateX',
    'CoordinateY',
    'Delete',
    'Update',
    'At the spaceship'];

  resultsLength = 0;
  pageNumber = 0;
  isLoadingResults = false;
  isRateLimitReached = false;
  filterString: string;
  deleteString: any;
  spaceMarineCategory: string;
  meanHealth: number;

  apiState: boolean;

  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatPaginator) paginator: MatPaginator;

  public constructor(private apiService: ApiService, private storage: LocalStorageService, private newApiService: NewApiService) {
    this.apiState = (storage.getItem("state_api") =="true");

    if (this.apiState) {
      this.apiService.setOldEndpoint();
    } else {
      this.apiService.setNewEndpoint();
    }
    this.meanHealth = 0;
  }

  public ngAfterViewInit() {
    this.updatePage();
  }

  ngOnInit(): void {
  }

  public changeApiVersion(){
    this.updateStarshipState()

    this.apiState = !this.apiState;
    this.storage.setItem("state_api", String(this.apiState));

    if (this.apiState){
      this.apiService.setOldEndpoint();
    } else {
      this.apiService.setNewEndpoint();
    }
  }

  public deleteSpaceMarineById(spaceMarineId: number){
    this.apiService.deleteSpaceMarine(spaceMarineId).subscribe((value) => this.updatePage());
  }

  public filterSpaceMarine(){
    this.updatePage();
    this.filterString = "";
  }

  public deleteSpaceMarineWhenHealth(){
    if ((this.deleteString as number) != null){
      this.apiService.deleteSpaceMarineByHealth(this.deleteString).subscribe(() => this.updatePage());
    }

    this.deleteString = "";
  }

  public findSpaceMarineWhenCategory(){
    this.apiService
      .findSpaceMarineWhereCategory(this.spaceMarineCategory)
      .subscribe(data => {
        if (data['List']['item'] != undefined) {
          let spaceMarines = new Array<SpaceMarine>();
          let array = data['List']['item'];

          if(data['List']['item'].length == undefined){
            let spaceMarine = new SpaceMarine(array.name._text, array.category._text, array.height._text,
              array.health._text, array.meleeWeapon._text, array.chapter.name._text,
              array.chapter.marinesCount._text, array.coordinates.x._text, array.coordinates.y._text);
            spaceMarine.id = array.id._text;

            spaceMarines.push(spaceMarine);
          } else{
            for (let value = 0; value < array.length; value++){
              let spaceMarine = new SpaceMarine(array[value].name._text, array[value].category._text, array[value].height._text,
                array[value].health._text, array[value].meleeWeapon._text, array[value].chapter.name._text,
                array[value].chapter.marinesCount._text, array[value].coordinates.x._text, array[value].coordinates.y._text);
              spaceMarine.id = array[value].id._text;

              spaceMarines.push(spaceMarine);
            }
          }

          this.spaceMarineDataSource.data = spaceMarines;
        } else {
          this.spaceMarineDataSource.data = new Array<SpaceMarine>();
        }
        this.getMeanHealth();
      });

    this.spaceMarineCategory = "";
  }

  public changeStateOfSpaceship(starshipState: boolean, spaceMarineId: number){
    if (starshipState == undefined){
      this.newApiService.loadSpaceMarine(spaceMarineId).subscribe(() => {this.updateStarshipState();})
    } else if (starshipState == false){
      this.newApiService.loadSpaceMarine(spaceMarineId).subscribe(() => {this.updateStarshipState();})
    } else {
      this.newApiService.unloadSpaceMarine(spaceMarineId).subscribe(() => {this.updateStarshipState();})
    }
  }

  private getMeanHealth(){
    this.meanHealth = 0;
    if (this.spaceMarineDataSource.data.length == 0){
      return;
    }

    this.apiService
      .getMeanHealth(this.spaceMarineDataSource.data)
      .subscribe(value => {
        this.meanHealth = value['FloatDto']['value']._text;
        if (this.meanHealth == null){
          this.meanHealth = 0;
        }
      })
  }

  private updatePage(){
    this.sort.sortChange.subscribe(() => this.paginator.pageIndex = 0);
    merge(this.sort.sortChange, this.paginator.page)
      .pipe(
        startWith({}),
        switchMap( () => {
          this.isLoadingResults = true;
          return this.apiService.getAllSpaceMarines(
            this.prepareSortingParams(this.sort.active),
            this.sort.direction,
            this.paginator.pageIndex + 1,
            this.paginator.pageSize,
            this.filterString);
        }),
        map(data => {
          this.isLoadingResults = false;
          this.isRateLimitReached = false;

          if (data == undefined) {
            return data;
          }

          this.pageNumber = (data.pageNumber as any)._text - 1;
          this.resultsLength = (data.totalElements as any)._text;

          return data.spaceMarines;
        }),
        catchError(() => {
          this.isLoadingResults = false;
          this.isRateLimitReached = true;

          return new Observable<SpaceMarine[]>();
        })
      ).subscribe(data => {
        if (data != undefined){
          this.spaceMarineDataSource.data = this.convertObjectToSpaceMarines(data);
        } else {
          this.spaceMarineDataSource.data = new Array<SpaceMarine>();
        }
        this.getMeanHealth();
        this.updateStarshipState();
    });
  }

  private prepareSortingParams(sortActive): string {
    if (sortActive == undefined) {
      return "";
    }

    let newResult: string;
    switch (sortActive.trim()) {
      case "Name": {
        newResult = "name";
        break;
      }
      case "Health": {
        newResult = "health";
        break;
      }
      case "Height": {
        newResult = "height";
        break;
      }
      case "Category": {
        newResult = "category";
        break;
      }
      case "Melee Weapon": {
        newResult = "meleeWeapon";
        break;
      }
      case "ChapterName": {
        newResult = "chapterName";
        break;
      }
      case "ChapterMarinesCount": {
        newResult = "chapterMarinesCount";
        break;
      }
      case "CoordinateX": {
        newResult = "xPosition";
        break;
      }
      case "CoordinateY": {
        newResult = "yPosition";
        break;
      }
    }

    return newResult;
  }

  private convertObjectToSpaceMarines(object: any): SpaceMarine[]{
    let spaceMarines = new Array<SpaceMarine>();

    if (!object.spaceMarines.length) {
      spaceMarines.push(SpaceMarine.createSpaceMarineFromObject(object.spaceMarines));
      return spaceMarines;
    }

    for (let i = 0; i < object.spaceMarines.length; i++){
      spaceMarines.push(SpaceMarine.createSpaceMarineFromObject(object.spaceMarines[i]));
    }

    return spaceMarines;
  }

  private updateStarshipState(){
    for (let i = 0; i < this.spaceMarineDataSource.data.length; i++){
      this.newApiService.getSpaceMarineState(this.spaceMarineDataSource.data[i]['id'])
        .subscribe((value) => {
          this.spaceMarineDataSource.data[i]['atStarhip'] = (value['Boolean']._text == "true");
        })
    }
  }
}
