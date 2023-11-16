import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {Observable} from "rxjs";
import {Videoresponse} from "../model/videoresponse";
import {Videorequest} from "../model/videorequest";
import {observableToBeFn} from "rxjs/internal/testing/TestScheduler";

@Injectable({
  providedIn: 'root'
})
export class VideoService {

  url: string = environment.apiUrl;

  constructor(private http: HttpClient) {
  }

  uploadVideo(fileEntry: File): Observable<Videoresponse> {

    const formData = new FormData()
    formData.append('file', fileEntry, fileEntry.name);

    return this.http.post<Videoresponse>(this.url + 'videos/upload', formData);

  }

  uploadThumbnail(fileEntry: File, videoId: string): Observable<string> {

    const formData = new FormData()
    formData.append('file', fileEntry, fileEntry.name);
    formData.append("videoId", videoId);

    return this.http.post(this.url + 'videos/thumbnail', formData, {
      responseType: 'text'
    });
  }

  getVideoById(videoId: string): Observable<Videoresponse> {
    return this.http.get<Videoresponse>(this.url + 'videos/' + videoId);
  }

  editVideo(videoId: string, data: Videorequest): Observable<String> {
    return this.http.put(this.url + 'videos/update/' + videoId, data, {
      responseType: 'text'
    });
  }

  getAllVideos(): Observable<Array<Videoresponse>> {
    return this.http.get<Array<Videoresponse>>(this.url + 'videos/getall');
  }

  likeVideo(videoId:string) : Observable<Videoresponse> {
    return this.http.post<Videoresponse>(this.url+'videos/like/'+ videoId,null);
  }

  disLikeVideo(videoId:string) : Observable<Videoresponse> {
    return this.http.post<Videoresponse>(this.url+'videos/dislike/'+ videoId,null);
  }



}
