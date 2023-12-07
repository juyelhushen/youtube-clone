import {Component, Input} from '@angular/core';
import {UserService} from "../../service/user.service";
import {Videoresponse} from "../../model/videoresponse";

@Component({
  selector: 'app-history',
  templateUrl: './history.component.html',
  styleUrls: ['./history.component.scss']
})
export class HistoryComponent {

  userId!: string;
  videos: Videoresponse[] = [];

  constructor(private userService: UserService) {
  }

  ngOnInit() {
    this.userService.registerUser();
    this.getUserHistory();
  }

  getUserHistory = () => {
    this.userId = this.userService.getUserId();
    this.userService.userHistory(this.userId).subscribe({
      next: (res: Videoresponse[]) => {
        this.videos = res;
      }
    });
  };
}
