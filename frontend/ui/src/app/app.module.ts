import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {UploadVideoComponent} from './components/upload-video/upload-video.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {NgxFileDropModule} from 'ngx-file-drop';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {MaterialModule} from "./shared/material-module";
import {HeaderComponent} from './layout/header/header.component';
import {SaveVideoDetailComponent} from './components/save-video-detail/save-video-detail.component';
import {VgCoreModule} from '@videogular/ngx-videogular/core';
import {VgControlsModule} from '@videogular/ngx-videogular/controls';
import {VgOverlayPlayModule} from '@videogular/ngx-videogular/overlay-play';
import {VgBufferingModule} from '@videogular/ngx-videogular/buffering';
import {VideoPlayerComponent} from './components/video-player/video-player.component';
import {AuthConfigModule} from './auth/auth-config.module';
import {AuthInterceptor} from "angular-auth-oidc-client";
import { VideoDetailComponent } from './components/video-detail/video-detail.component';
import { HomeComponent } from './components/home/home.component';
import { HistoryComponent } from './components/history/history.component';
import { SubscriptionComponent } from './components/subscription/subscription.component';
import { SidebarComponent } from './layout/sidebar/sidebar.component';
import { LikedVideosComponent } from './components/liked-videos/liked-videos.component';
import { FeaturedComponent } from './components/featured/featured.component';
import { VideoCardComponent } from './components/video-card/video-card.component';
import {NgOptimizedImage} from "@angular/common";
import { CallbackComponent } from './components/callback/callback.component';
import { CommentComponent } from './components/comment/comment.component';
import { ChannelComponent } from './components/channel/channel.component';

@NgModule({
  declarations: [
    AppComponent,
    UploadVideoComponent,
    HeaderComponent,
    SaveVideoDetailComponent,
    VideoPlayerComponent,
    VideoDetailComponent,
    HomeComponent,
    HistoryComponent,
    SubscriptionComponent,
    SidebarComponent,
    LikedVideosComponent,
    FeaturedComponent,
    VideoCardComponent,
    CallbackComponent,
    CommentComponent,
    ChannelComponent,
  ],
    imports: [
        BrowserModule,
        FormsModule,
        HttpClientModule,
        NgxFileDropModule,
        AppRoutingModule,
        BrowserAnimationsModule,
        MaterialModule,
        ReactiveFormsModule,
        VgCoreModule,
        VgControlsModule,
        VgOverlayPlayModule,
        VgBufferingModule,
        AuthConfigModule,
        NgOptimizedImage,
    ],
  providers: [
    {provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true}
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
