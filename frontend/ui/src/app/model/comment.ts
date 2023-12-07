export interface Comment {
  authorId: string;
  commentText: string;
}

export interface CommentRequest {
  videoId: string;
  commentId: string;
}
