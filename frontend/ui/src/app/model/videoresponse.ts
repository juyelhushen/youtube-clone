export interface Videoresponse {
  userId:string;
  videoId: string;
  title: string;
  description:string;
  tags:Array<any>[];
  videoStatus:string;
  thumbnailUrl:string;
  videoUrl:string;
  likedCount:number;
  disLikedCount:number;
  viewCount:number;
  uploadOn:Date;
}

