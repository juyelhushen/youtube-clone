import {Component} from '@angular/core';
import {FileSystemDirectoryEntry, FileSystemFileEntry, NgxFileDropEntry} from "ngx-file-drop";
import {VideoService} from "../../service/video.service";
import {SnackbarService} from "../../service/snackbar.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-upload-video',
  templateUrl: './upload-video.component.html',
  styleUrls: ['./upload-video.component.scss']
})
export class UploadVideoComponent {

  public files: NgxFileDropEntry[] = [];
  fileEntry!: FileSystemFileEntry;
  isFileUploaded: boolean = false;

  constructor(private videoService: VideoService,
              private snackBarService: SnackbarService,
              private router: Router) {
  }

  public dropped(files: NgxFileDropEntry[]) {
    this.files = files;


    for (const droppedFile of files) {
      // Is it a file?
      if (droppedFile.fileEntry.isFile) {

        this.fileEntry = droppedFile.fileEntry as FileSystemFileEntry;
        this.fileEntry.file((file: File) => {

          // Here you can access the real file
          console.log(droppedFile.relativePath, file);
          this.isFileUploaded = true;


          /**
           // You could upload it like this:
           const formData = new FormData()
           formData.append('logo', file, relativePath)

           // Headers
           const headers = new HttpHeaders({
           'security-token': 'mytoken'
           })

           this.http.post('https://mybackend.com/api/upload/sanitize-and-save-logo', formData, { headers: headers, responseType: 'blob' })
           .subscribe(data => {
           // Sanitized logo returned from backend
           })
           **/

        });
      } else {
        // It was a directory (empty directories are added, otherwise only files)
        const fileEntry = droppedFile.fileEntry as FileSystemDirectoryEntry;
        console.log(droppedFile.relativePath, fileEntry);
      }
    }
  }

  public fileOver(event: any) {
    console.log(event);
  }

  public fileLeave(event: any) {
    console.log(event);
  }


  uploadOnClick() {
    if (this.fileEntry !== undefined) {
      this.fileEntry.file(file => {
        this.videoService.uploadVideo(file).subscribe({
          next: (res: any) => {
            this.snackBarService.openSuccessSnackBar('Upload Successfully', '');
            this.router.navigateByUrl('/save-video-detail/' + res.videoId);
          },
          error: (err) => {
            console.log("Something went wrong");
          }
        })
      })
    }
  }
}
