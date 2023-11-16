import {Component, Input} from '@angular/core';
import {Comment} from "../../model/comment";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {CommentService} from "../../service/comment.service";
import {UserService} from "../../service/user.service";
import {CommentResponse} from "../../model/comment-response";
import {SnackbarService} from "../../service/snackbar.service";

@Component({
  selector: 'app-comment',
  templateUrl: './comment.component.html',
  styleUrls: ['./comment.component.scss']
})
export class CommentComponent {

  @Input()
  videoId!: string;

  totalComment: number = 0;
  commentForm: any = FormGroup;
  comments: CommentResponse[] = [];
  responseMessage: string = '';

  constructor(private fb: FormBuilder,
              private commentService: CommentService,
              private userService: UserService,
              private snackBar: SnackbarService) {
  }

  ngOnInit(): void {
    this.validateCommentForm();
    this.getComment();
  }

  validateCommentForm = () => {
    this.commentForm = this.fb.group({
      comment: [null, [Validators.required]]
    });
  };

  addComment = () => {
    const commentData = this.commentForm.value;
    const data: Comment = {
      authorId: this.userService.getUserId(),
      commentText: commentData.comment
    };
    this.commentService.addComment(this.videoId, data).subscribe({
      next: (res: string) => {
        this.responseMessage = res;
        this.snackBar.openSuccessSnackBar(this.responseMessage, 'OK');
        this.getComment();
        this.commentForm.get('comment').reset();
      },
      error: (err: any) => {
        this.responseMessage = err;
        this.snackBar.openErrorSnackBar(this.responseMessage, 'ERROR');
      }
    });
  };


  getComment() {
    this.commentService.getAllComment(this.videoId).subscribe({
      next: (res: CommentResponse[]) => {
        this.comments = res;
      },
      error: (err) => {
        this.snackBar.openErrorSnackBar(err, 'Error');
      }
    })
  }

}
