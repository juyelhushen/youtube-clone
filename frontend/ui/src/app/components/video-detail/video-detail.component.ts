import {Component} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {Videoresponse} from "../../model/videoresponse";
import {SnackbarService} from "../../service/snackbar.service";
import {VideoService} from "../../service/video.service";
import {UserService} from "../../service/user.service";

@Component({
  selector: 'app-video-detail',
  templateUrl: './video-detail.component.html',
  styleUrls: ['./video-detail.component.scss']
})
export class VideoDetailComponent {

  videoId!: string;
  videoUrl!: string;
  title!: string;
  description!: string;
  video!: Videoresponse;
  userId: string = '';
  isSubscribed: boolean = false;
  isUnsubscribed: boolean = true;


  constructor(private activateRouter: ActivatedRoute,
              private snackbar: SnackbarService,
              private videoService: VideoService,
              private userService: UserService) {
  }


  ngOnInit(): void {
    this.videoId = this.activateRouter.snapshot.params['videoId'];
    this.userService.registerUser();
    this.getVideo();
  };

  getVideo = (): void => {
    this.videoService.getVideoById(this.videoId).subscribe({
      next: (res: Videoresponse) => {
        this.videoUrl = res.videoUrl;
        this.title = res.title;
        this.description = res.description;
        this.video = res;
        this.snackbar.openSuccessSnackBar(this.videoUrl, 'OK');
      },
      error: (err: any) => {
        this.snackbar.openErrorSnackBar('Something went wrong', 'Error');
      }
    });
  };

  likeVideo = () => {
    this.videoService.likeVideo(this.videoId).subscribe({
      next: (res: Videoresponse) => {
        this.video.likedCount = res.likedCount;
        this.video.disLikedCount = res.disLikedCount;
      }
    });
  };

  disLikeVideo = () => {
    this.videoService.disLikeVideo(this.videoId).subscribe({
      next: (res: Videoresponse) => {
        this.video.likedCount = res.likedCount;
        this.video.disLikedCount = res.disLikedCount;
      }
    });
  };

  subscribedUser = () => {
    this.userId = this.userService.getUserId();
    this.userService.subscribeToUser(this.userId).subscribe({
      next: (data: boolean) => {
        this.isSubscribed = true;
        this.isUnsubscribed = false;
      }
    });
  };

  unSubscribedUser = () => {
    this.userId = this.userService.getUserId();
    this.userService.unSubscribeToUser(this.userId).subscribe({
      next: (data: boolean) => {
        this.isSubscribed = false;
        this.isUnsubscribed = true;
      }
    });
  };
}
