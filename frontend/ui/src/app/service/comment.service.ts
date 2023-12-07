import {Injectable} from '@angular/core';
import {environment} from "../../environments/environment";
import {HttpClient, HttpHeaders, HttpRequest} from "@angular/common/http";
import {Observable} from "rxjs";
import {Comment, CommentRequest} from "../model/comment";
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

  getAllComment(videoId: string): Observable<CommentResponse[]> {
    return this.http.get<CommentResponse[]>(this.url + 'videos/comments/' + videoId,);
  };

  getTotalCommentCount(videoId: string): Observable<number> {
    return this.http.get<number>(this.url + 'videos/comments/count/' + videoId)
  };

  deleteComment(data: CommentRequest): Observable<string> {
    return this.http.post(this.url + 'videos/comment/delete', data, {
      headers: new HttpHeaders().set('Content-type', 'application/json'),
      responseType: 'text'
    });
  };

  likeComment(data: CommentRequest): Observable<CommentResponse> {
    return this.http.post<CommentResponse>(this.url + 'videos/comment/like', data);
  }

  disLikeComment(data: CommentRequest): Observable<CommentResponse> {
    return this.http.post<CommentResponse>(this.url + 'videos/comment/dislike', data);
  }


}
