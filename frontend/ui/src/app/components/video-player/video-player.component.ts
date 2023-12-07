import {Component, ElementRef, Input, OnChanges, SimpleChanges, ViewChild} from '@angular/core';

@Component({
  selector: 'app-video-player',
  templateUrl: './video-player.component.html',
  styleUrls: ['./video-player.component.scss']
})
export class VideoPlayerComponent  implements OnChanges{

  @Input()
  videoUrl!:string | '';

  @ViewChild('media', { static: true }) media!: ElementRef;

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['videoUrl']) {
      this.reloadVideoPlayer();
    }
  };

  private reloadVideoPlayer(): void {
    const videoPlayerElement:HTMLVideoElement = this.media.nativeElement;
    videoPlayerElement.pause();
    videoPlayerElement.src = this.videoUrl;
    videoPlayerElement.load();
  };
}
