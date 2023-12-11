import {Component, EventEmitter, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {UserService} from "../../../service/user.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {UserRequest} from "../../../model/userrequest";
import {UserResponse} from "../../../model/userresponse";
import {SnackbarService} from "../../../service/snackbar.service";

@Component({
  selector: 'app-manage-channel',
  templateUrl: './manage-channel.component.html',
  styleUrls: ['./manage-channel.component.scss']
})
export class ManageChannelComponent {

  onEditChannel = new EventEmitter();
  channelForm: any =FormGroup;

  constructor(@Inject(MAT_DIALOG_DATA) public dialogData:any,
              private userService: UserService,
              private fb:FormBuilder,
              private snackBar: SnackbarService,
              private dialogRef:MatDialogRef<ManageChannelComponent>) {
  }

  ngOnInit():void {
    this.validatedForm();
  }

  validatedForm = ()  => {
    let channelName = this.dialogData.data.name;
    this.channelForm = this.fb.group({
      name:[channelName,[Validators.required]],
      description:[this.dialogData.data.description],
    });
  };

  editChannelNameOnClick = () => {
    let userId = this.dialogData.data.id;
    console.log("userId is - " + userId);
    const formData =  this.channelForm.value;
    const data:UserRequest = {
      channelDescription: formData.description,
      channelName: formData.name
    }
    this.userService.patchUserUpdate(userId,data).subscribe({
      next:(res:string) => {
        this.dialogRef.close();
        this.onEditChannel.emit();
        this.snackBar.openSuccessSnackBar(res,'OK');
      }
    });
  };

  public myError = (controlName: string, errorName: string) => {
    return this.channelForm.controls[controlName].hasError(errorName);
  }

}
