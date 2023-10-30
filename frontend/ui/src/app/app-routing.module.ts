import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {UploadVideoComponent} from "./components/upload-video/upload-video.component";
import {SaveVideoDetailComponent} from "./components/save-video-detail/save-video-detail.component";

const routes: Routes = [
  {
    path: 'upload-video',
    component: UploadVideoComponent
  },
  {
    path:'save-video-detail/:videoId',
    component:SaveVideoDetailComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
