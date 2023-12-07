import {Component} from '@angular/core';
import {Videoresponse} from "../../model/videoresponse";
import {UserService} from "../../service/user.service";

@Component({
  selector: 'app-liked-videos',
  templateUrl: './liked-videos.component.html',
  styleUrls: ['./liked-videos.component.scss']
})
export class LikedVideosComponent {

  userId!: string;
  videos: Videoresponse[] = [];

  constructor(private userService: UserService) {
  }

  ngOnInit() {
    this.userService.registerUser();
    this.getLikedVideos();
  }

  getLikedVideos = () => {
    this.userId = this.userService.getUserId();
    this.userService.userLikedVideos(this.userId).subscribe({
      next: (res: Videoresponse[]) => {
        this.videos = res;
      }
    });
  };

}
