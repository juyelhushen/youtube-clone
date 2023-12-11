import {Component, Input} from '@angular/core';
import {Videoresponse} from "../../model/videoresponse";
import {UserService} from "../../service/user.service";
import {UserResponse} from "../../model/userresponse";

@Component({
  selector: 'app-video-card',
  templateUrl: './video-card.component.html',
  styleUrls: ['./video-card.component.scss']
})
export class VideoCardComponent {

  @Input()
  video!: Videoresponse;

  timeAgo: string = '';
  user!:UserResponse
  userProfileUrl!:string;

  constructor(private userService: UserService) {
  }

  ngOnInit(): void {
    this.getTimeAgo()
    this.fetchUserDetails();
  }



  getTimeAgo = () => {
    if (this.video && this.video.uploadOn) {
      const uploadTime = new Date(this.video.uploadOn);
      const currentTime = new Date();

      const timeDifferenceInSeconds =
        Math.floor((currentTime.getTime() - uploadTime.getTime()) / 1000);

      if (timeDifferenceInSeconds < 60) {
        this.timeAgo = timeDifferenceInSeconds + ' second' + `${timeDifferenceInSeconds !== 1 ? 's' : ''}` + ' ago';
      } else if (timeDifferenceInSeconds < 60 * 60) {
        const minutes = Math.floor(timeDifferenceInSeconds / 60);
        this.timeAgo = minutes + ' minute' + `${minutes !== 1 ? 's' : ''}` + ' ago';
      } else if (timeDifferenceInSeconds < 24 * 60 * 60) {
        const hours = Math.floor(timeDifferenceInSeconds / (60 * 60));
        this.timeAgo = hours + ' hour' + `${hours !== 1 ? 's' : ''}` + ' ago';
      } else if (timeDifferenceInSeconds < 7 * 24 * 60 * 60) {
        const days = Math.floor(timeDifferenceInSeconds / (24 * 60 * 60));
        this.timeAgo = `${days} day${days !== 1 ? 's' : ''} ago`;
      } else if (timeDifferenceInSeconds < 30 * 24 * 60 * 60) {
        const weeks = Math.floor(timeDifferenceInSeconds / (7 * 24 * 60 * 60));
        this.timeAgo = `${weeks} week${weeks !== 1 ? 's' : ''} ago`;
      } else if (timeDifferenceInSeconds < 12 * 30 * 24 * 60 * 60) {
        const months = Math.floor(timeDifferenceInSeconds / (30 * 24 * 60 * 60));
        this.timeAgo = `${months} month${months !== 1 ? 's' : ''} ago`;
      } else {
        const years = Math.floor(timeDifferenceInSeconds / (12 * 30 * 24 * 60 * 60));
        this.timeAgo = `${years} year${years !== 1 ? 's' : ''} ago`;
      }
    }
  }

  private fetchUserDetails(): void {
    if (this.video && this.video.userId) {
      this.userService.getUserResponse(this.video.userId).subscribe({
        next:(res:UserResponse) => {
          this.user = res;
          this.userProfileUrl = res.profileUrl;
        },
        error:(err:any) => {
          console.error('Error fetching user details:', err);
        }
      })
    }
  }


}
