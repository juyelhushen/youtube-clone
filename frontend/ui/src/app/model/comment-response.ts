export interface CommentResponse {

  id: string;
  authorId: string;
  commentText: string;
  likeCount: number;
  disLikeCount: number;
  dateTime: Date;
}
