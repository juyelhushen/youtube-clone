import {Component} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {MatChipEditedEvent, MatChipInputEvent} from "@angular/material/chips";
import {COMMA, ENTER} from "@angular/cdk/keycodes";
import {VideoService} from "../../service/video.service";
import {SnackbarService} from "../../service/snackbar.service";
import {Videoresponse} from "../../model/videoresponse";
import {Videorequest} from "../../model/videorequest";
import {UserService} from "../../service/user.service";

@Component({
  selector: 'app-save-video-detail',
  templateUrl: './save-video-detail.component.html',
  styleUrls: ['./save-video-detail.component.scss']
})
export class SaveVideoDetailComponent {

  videoStatus: string[] = ['PUBLIC', 'PRIVATE', 'UNLISTED'];
  videoDetailForm: any = FormGroup;

  addOnBlur = true;
  readonly separatorKeysCodes = [ENTER, COMMA] as const;
  tags: any[] = [];
  selectedFile!: File;
  selectedFileName = '';
  isFileSelected: boolean = false;
  videoId!: string;
  responseMessage: any;
  videoUrl!: string;
  thumbnailUrl!: string;


  constructor(private fb: FormBuilder,
              private activatedRouter: ActivatedRoute,
              private videoService: VideoService,
              private snackbar: SnackbarService,
              private userService: UserService) {
    this.videoId = this.activatedRouter.snapshot.params['videoId'];
  }

  ngOnInit(): void {
    this.userService.registerUser();
    this.videoFormValidate();
    this.getVideo();
  }

  add = (event: MatChipInputEvent): void => {
    const value = (event.value || '').trim();

    // Add our fruit
    if (value) {
      this.tags.push(value);
    }

    // Clear the input value
    event.chipInput!.clear();
  }

  remove = (value: string): void => {
    const index = this.tags.indexOf(value);

    if (index >= 0) {
      this.tags.splice(index, 1);

    }
  }

  edit(tag: string, event: MatChipEditedEvent) {
    const value = event.value.trim();

    // Remove fruit if it no longer has a name
    if (!value) {
      this.remove(tag);
      return;
    }

    // Edit existing fruit
    const index = this.tags.indexOf(tag);
    if (index >= 0) {
      this.tags[index] = value;
    }
  }

  videoFormValidate = () => {
    this.videoDetailForm = this.fb.group({
      title: [null, [Validators.required]],
      description: [null, [Validators.required]],
      videoStatus: [null, [Validators.required]]
    });
  };


  onSelectFile($event: Event) {
    // @ts-ignore
    this.selectedFile = $event.target.files[0];
    this.selectedFileName = this.selectedFile.name;
    this.isFileSelected = true;
  }

  uploadOnClick = () => {
    this.videoService.uploadThumbnail(this.selectedFile, this.videoId).subscribe({
      next: (res: any) => {
        this.responseMessage = res;
        this.snackbar.openSuccessSnackBar(this.responseMessage, 'OK');
      },
      error: (err) => {
        this.responseMessage = err;
        this.snackbar.openErrorSnackBar(this.responseMessage, 'ERRO');
      }
    })
  }

  getVideo(): void {
    this.videoService.getVideoById(this.videoId).subscribe({
      next: (res: Videoresponse) => {
        this.videoUrl = res.videoUrl;
        this.thumbnailUrl = res.thumbnailUrl;
        this.snackbar.openSuccessSnackBar(this.videoUrl, 'OK');
      },
      error: (err: any) => {
        this.snackbar.openErrorSnackBar('Something went wrong', 'Error');
      }
    });
  };

  saveVideoOnClick = () => {
    const formData = this.videoDetailForm.value;
    const data: Videorequest = {
      title: formData.title,
      userId:this.userService.getUserId(),
      description: formData.description,
      tags: this.tags,
      thumbnailUrl: this.thumbnailUrl,
      videoStatus: formData.videoStatus,
      videoUrl: this.videoUrl
    };
    this.videoService.editVideo(this.videoId,data).subscribe({
      next: (res:String) => {
        this.responseMessage = res;
        this.snackbar.openSuccessSnackBar(this.responseMessage,'OK');
      },
      error:(err) => {
        this.responseMessage = err;
        this.snackbar.openErrorSnackBar(this.responseMessage,'Error');
      }
    })
  }
}
