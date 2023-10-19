import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {FileSystemFileEntry} from "ngx-file-drop";
import {environment} from "../../environments/environment";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class VideoService {

  url: string = environment.apiUrl;

  constructor(private http:HttpClient) { }
    uploadVideo(fileEntry: File) : Observable<any> {

    const formData = new FormData()
    formData.append('file', fileEntry, fileEntry.name);

    return this.http.post(this.url + 'videos/upload', formData);

  }
}
