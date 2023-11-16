import {Injectable} from '@angular/core';
import {environment} from "../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Comment} from "../model/comment";
import {CommentResponse} from "../model/comment-response";

@Injectable({
  providedIn: 'root'
})
export class CommentService {

  url: string = environment.apiUrl;

  constructor(private http: HttpClient) {
  }

  addComment(videoId: string, comment: Comment): Observable<string> {
    return this.http.post(this.url + 'videos/addcomment/' + videoId, comment, {
      responseType: 'text'
    });
  };

  getAllComment(videoId:string) : Observable<CommentResponse[]> {
    return this.http.get<CommentResponse[]>(this.url + 'videos/comments/'+videoId,);
  }


}
