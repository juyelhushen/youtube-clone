export interface CommentResponse {

  id: string;
  authorId: string;
  authorName: string;
  commentText: string;
  likeCount: number;
  disLikeCount: number;
  date: Date;
}
