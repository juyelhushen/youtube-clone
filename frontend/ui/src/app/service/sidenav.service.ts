import { Injectable } from '@angular/core';
import {BehaviorSubject} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class SidenavService {

  private isSideNavOpenSubject = new BehaviorSubject<boolean>(false);
  state = this.isSideNavOpenSubject.asObservable();

  toggle = () => {
    this.isSideNavOpenSubject.next(!this.isSideNavOpenSubject.value);
  }

}
