import {Component, Input} from '@angular/core';
import {Videoresponse} from "../../model/videoresponse";

@Component({
  selector: 'app-video-card',
  templateUrl: './video-card.component.html',
  styleUrls: ['./video-card.component.scss']
})
export class VideoCardComponent {

  @Input()
  video!:Videoresponse;

}
