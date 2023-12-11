import {Injectable} from '@angular/core';
import {environment} from "../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Videoresponse} from "../model/videoresponse";
import {UserResponse} from "../model/userresponse";
import {UserRequest} from "../model/userrequest";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  url: string = environment.apiUrl;
  private userId: string = '';

  constructor(private http: HttpClient) {
  }

  registerUser(): void {
    this.http.get(this.url + 'user/register', {responseType: 'text'}).subscribe({
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

  userHistory(userId: string): Observable<Videoresponse[]> {
    return this.http.get<Videoresponse[]>(this.url + 'videos/' + userId + '/videoHistory');
  }

  userLikedVideos(userId: string): Observable<Videoresponse[]> {
    return this.http.get<Videoresponse[]>(this.url + 'videos/' + userId + '/likedVideos');
  }

  uploadProfile(userId: string, file: File): Observable<string> {
    const formData = new FormData();
    formData.append("file", file, file.name);
    return this.http.put(this.url + "user/upload/profile/" + userId, formData, {
      responseType: 'text'
    });
  };

  getUserResponse(userId: string): Observable<UserResponse> {
    return this.http.get<UserResponse>(this.url + 'user/' + userId);
  };

  patchUserUpdate(userId: string, data: UserRequest): Observable<string> {
    return this.http.patch(this.url + "user/patch/update/" + userId, data, {
      responseType: 'text'
    });
  };

  getVideosByUserId(userId: string) : Observable<Videoresponse[]> {
    return this.http.get<Videoresponse[]>(this.url + 'videos/user/' + userId);
  }

}
