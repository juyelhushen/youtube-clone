import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {UploadVideoComponent} from "./components/upload-video/upload-video.component";
import {SaveVideoDetailComponent} from "./components/save-video-detail/save-video-detail.component";
import {VideoDetailComponent} from "./components/video-detail/video-detail.component";
import {HomeComponent} from "./components/home/home.component";
import {HistoryComponent} from "./components/history/history.component";
import {SubscriptionComponent} from "./components/subscription/subscription.component";
import {LikedVideosComponent} from "./components/liked-videos/liked-videos.component";
import {FeaturedComponent} from "./components/featured/featured.component";
import {CallbackComponent} from "./components/callback/callback.component";

const routes: Routes = [
  {
    path: '',
    component: HomeComponent,
    children: [
      {
        path: 'featured',
        component: FeaturedComponent
      }, {
        path: 'history',
        component: HistoryComponent
      },
      {
        path: 'liked/videos',
        component: LikedVideosComponent
      },
      {
        path: 'subscriptions',
        component: SubscriptionComponent
      }
    ]
  },
  {
    path: 'upload-video',
    component: UploadVideoComponent
  },
  {
    path: 'save-video-detail/:videoId',
    component: SaveVideoDetailComponent
  },
  {
    path: 'video-detail/:videoId',
    component: VideoDetailComponent
  },
  {
    path: 'callback',
    component: CallbackComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
