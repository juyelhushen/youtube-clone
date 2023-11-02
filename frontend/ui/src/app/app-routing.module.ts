import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {UploadVideoComponent} from "./components/upload-video/upload-video.component";
import {SaveVideoDetailComponent} from "./components/save-video-detail/save-video-detail.component";
import {VideoDetailComponent} from "./components/video-detail/video-detail.component";

const routes: Routes = [
  {
    path: 'upload-video',
    component: UploadVideoComponent
  },
  {
    path:'save-video-detail/:videoId',
    component:SaveVideoDetailComponent
  },
  {
    path:'video-detail/:videoId',
    component:VideoDetailComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
