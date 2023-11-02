import {Component} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {Videoresponse} from "../../model/videoresponse";
import {SnackbarService} from "../../service/snackbar.service";
import {VideoService} from "../../service/video.service";

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

  constructor(private activateRouter: ActivatedRoute,
              private snackbar: SnackbarService,
              private videoService: VideoService) {

  }


  ngOnInit(): void {
    this.videoId = this.activateRouter.snapshot.params['videoId'];
    this.getVideo();
  }

  getVideo = (): void => {
    this.videoService.getVideoById(this.videoId).subscribe({
      next: (res: Videoresponse) => {
        this.videoUrl = res.videoUrl;
        this.title = res.title;
        this.description = res.description;
        this.snackbar.openSuccessSnackBar(this.videoUrl, 'OK');
      },
      error: (err: any) => {
        this.snackbar.openErrorSnackBar('Something went wrong', 'Error');
      }
    });
  };

}
