import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { httpOptions } from 'src/app/util/http-util';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class PlaceService {

  private url: string;

  constructor(
    private http: HttpClient
  ) {
    this.url = environment.restPath + '/place';
  }

  public getPlaces() {
    return this.http.get(this.url + 's', {headers: httpOptions()});
  }

  public searchPlaces(name: string, numOfPage: number, sizeOfPage: number) {
    const param = new HttpParams()
      .append('name', name)
      .append('num', numOfPage.toString())
      .append('size', sizeOfPage.toString());
    return this.http.get(this.url + 's/search',
      {
        headers: httpOptions(),
        params: param
      }
    );
  }
}
