export interface Comment {
  authorId: string;
  commentText: string;
}

export interface DeleteCommentRequest {
  videoId: string;
  commentId: string;
}
