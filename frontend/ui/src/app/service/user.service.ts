import {Injectable} from '@angular/core';
import {environment} from "../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Videoresponse} from "../model/videoresponse";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  url: string = environment.apiUrl;
  private userId: string = '';

  constructor(private http: HttpClient) {
  }
  registerUser(): void {
    this.http.get(this.url + 'user/register',{responseType:'text'}).subscribe({
      next: (data: string) => {
        this.userId = data;
      }
    });
  };
  getUserId(): string {
    return this.userId;
  }

  subscribeToUser(userId: string): Observable<boolean> {
    return this.http.post<boolean>(this.url + 'user/subscribe/' + userId, null);
  };

  unSubscribeToUser(userId: string): Observable<boolean> {
    return this.http.post<boolean>(this.url + 'user/unsubscribe/' + userId, null);
  };

  userHistory(userId:string) : Observable<Videoresponse[]> {
    return this.http.get<Videoresponse[]>(this.url+'videos/'+userId+'/videoHistory');
  }

  userLikedVideos(userId:string) : Observable<Videoresponse[]> {
    return this.http.get<Videoresponse[]>(this.url+'videos/'+userId+'/likedVideos');
  }



}
