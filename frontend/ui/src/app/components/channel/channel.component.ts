import {Component} from '@angular/core';
import {UserService} from "../../service/user.service";
import {SnackbarService} from "../../service/snackbar.service";
import {DomSanitizer, SafeUrl} from "@angular/platform-browser";
import {UserResponse} from "../../model/userresponse";
import {MatDialog, MatDialogConfig} from "@angular/material/dialog";
import {ManageChannelComponent} from "../../shared/dialog/manage-channel/manage-channel.component";
import {Router} from "@angular/router";
import {Videoresponse} from "../../model/videoresponse";

@Component({
  selector: 'app-channel',
  templateUrl: './channel.component.html',
  styleUrls: ['./channel.component.scss']
})
export class ChannelComponent {

  userId!: string;
  selectedFile: File | null = null;
  isFileSelected: boolean = false;
  imageUrl: SafeUrl | null = null;
  userResponse!: UserResponse;
  videoResponse:Videoresponse[] = []


  constructor(private userService: UserService,
              private snackbar: SnackbarService,
              private sanitizer: DomSanitizer,
              private dialog: MatDialog,
              private router: Router) {
  }

  ngOnInit() {
    this.userService.registerUser();
    this.getUserResponse();
    this.getMyVideos();
  }

  getUserResponse = () => {
    this.userId = this.userService.getUserId();
    console.log("userId  - " + this.userId);
    this.userService.getUserResponse(this.userId).subscribe({
      next: (res: UserResponse) => {
        this.userResponse = res;
        this.imageUrl = res.profileUrl;
      }
    });
  };

  // onFileSelected($event:Event):void {
  //   this.selectedFile = $event.target.files[0] as File;
  // }

  onFileSelected($event: any): void {
    this.selectedFile = $event.target.files[0] as File;
    this.imageUrl = this.sanitizer.bypassSecurityTrustUrl(URL.createObjectURL(this.selectedFile));
    this.isFileSelected = true;
  }

  uploadProfileOnClick = () => {
    this.userId = this.userService.getUserId();
    if (this.selectedFile) {
      this.userService.uploadProfile(this.userId, this.selectedFile).subscribe({
        next: (res: string) => {
          this.imageUrl = res;
          this.isFileSelected = false;
          this.snackbar.openSuccessSnackBar("Upload Successfully", 'OK');
        },
        error: (err) => {
          this.snackbar.openErrorSnackBar(err, 'Error');
        }
      });
    }
  };

  handleEditChannelName = () => {
    const dialogConfig = new MatDialogConfig();
    this.userId = this.userService.getUserId();
    dialogConfig.data = {
      action: 'Edit',
      data: {id:this.userResponse.id,name:this.userResponse.channelName,description:this.userResponse.channelDescription}
    }
    dialogConfig.minWidth = '300px';
    dialogConfig.maxWidth = '400px';
    const dialogRef = this.dialog.open(ManageChannelComponent, dialogConfig);
    this.router.events.subscribe({
      next: () => {
        dialogRef.close();
      }
    });
    const sub = dialogRef.afterClosed().subscribe({
      next: () => {
        this.getUserResponse();
      }
    });
  }

  getMyVideos = () => {
    this.userId = this.userService.getUserId();
    this.userService.getVideosByUserId(this.userId).subscribe({
      next:(res:Videoresponse[]) => {
        this.videoResponse = res;
      }
    })
  }
}
