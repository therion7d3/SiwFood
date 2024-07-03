import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})

export class utentiService {

  private baseUrl = 'http://localhost:8080/api/utenti';

  constructor(private http: HttpClient) { }

  getMyself() {
    return this.http.get<any>(this.baseUrl + "/me");
  }

  getAllUsers() {
    return this.http.get<any>(this.baseUrl);
  }

  getUserById(id : number): Observable<any> {
    return this.http.get<any>(this.baseUrl + "/" + id );
  }

  deleteUserById(id : number): Observable<any> {
    return this.http.delete<any>(this.baseUrl + "/" + id );
  }
}
