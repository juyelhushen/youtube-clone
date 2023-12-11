import {Component} from '@angular/core';
import {VideoService} from "../../service/video.service";
import {Videoresponse} from "../../model/videoresponse";

@Component({
  selector: 'app-featured',
  templateUrl: './featured.component.html',
  styleUrls: ['./featured.component.scss']
})
export class FeaturedComponent {

  videos: Array<Videoresponse> = [];

  constructor(private videoService: VideoService) {
  }

  ngOnInit(): void {
    this.getAllVideos();
  };

  getAllVideos = () => {
    this.videoService.getAllVideos().subscribe({
      next: (res: Array<Videoresponse>) => {
        this.videos = res;
      }
    });
  };
}
