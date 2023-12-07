import {ChangeDetectionStrategy, Component} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {Videoresponse} from "../../model/videoresponse";
import {SnackbarService} from "../../service/snackbar.service";
import {VideoService} from "../../service/video.service";
import {UserService} from "../../service/user.service";

@Component({
  selector: 'app-video-detail',
  templateUrl: './video-detail.component.html',
  styleUrls: ['./video-detail.component.scss'],
})
export class VideoDetailComponent {

  videoId!: string;
  videoUrl!: string;
  title!: string;
  description!: string;
  tags: any[] = [];
  video!: Videoresponse;
  userId: string = '';
  isLiked:boolean = false;
  isDisLiked:boolean = false;
  suggestedVideos: Videoresponse[] = [];
  isSubscribed: boolean = false;
  isUnsubscribed: boolean = true;


  constructor(private activateRouter: ActivatedRoute,
              private snackbar: SnackbarService,
              private videoService: VideoService,
              private userService: UserService,
              private router: Router) {
  }


  ngOnInit(): void {
    // this.activateRouter.params.subscribe((params: any) => {
    //   this.videoId = params.toString['videoId'];
    // })
    this.videoId = this.activateRouter.snapshot.params['videoId'];
    this.userService.registerUser();
    this.getVideoById();
    this.getSuggestedVideos();
  };

  getVideoById = (): void => {
    this.videoService.getVideoById(this.videoId).subscribe({
      next: (res: Videoresponse) => {
        this.userId = res.userId;
        this.videoUrl = res.videoUrl;
        this.title = res.title;
        this.description = res.description;
        this.tags = res.tags;
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
        this.isLiked = true;
        this.video.likedCount = res.likedCount;
        this.video.disLikedCount = res.disLikedCount;
      }
    });
  };

  disLikeVideo = () => {
    this.videoService.disLikeVideo(this.videoId).subscribe({
      next: (res: Videoresponse) => {
        this.isDisLiked = true;
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

  getSuggestedVideos() {
    this.videoService.suggestedVideos(this.videoId).subscribe({
      next: (data: Videoresponse[]) => {
        this.suggestedVideos = data;
      }
    });
  };

  canSubscribeToChannel = (): boolean => {
    const currentUser = this.userService.getUserId();
    return this.userId !== currentUser;
  }

  openSuggestedVideo(videoId: string) {
    console.log('Navigating to suggested video detail with videoId:', videoId);

    // Update the route without a page reload
    this.router.navigate(['video-detail', videoId]);

    // Update the videoId and fetch the new video details
    this.videoId = videoId;
    this.getVideoById();
  }
}
