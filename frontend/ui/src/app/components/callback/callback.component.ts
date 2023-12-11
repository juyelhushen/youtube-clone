import { Component } from '@angular/core';
import {UserService} from "../../service/user.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-callback',
  templateUrl: './callback.component.html',
  styleUrls: ['./callback.component.scss']
})
export class CallbackComponent {

  constructor(private userService:UserService,
              private router:Router) {
  }

  ngOnInit(): void {
    this.userService.registerUser();
    this.router.navigateByUrl('/featured');
  }

}
