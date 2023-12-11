import {Component, EventEmitter, Input, Output} from '@angular/core';
import {OidcSecurityService} from "angular-auth-oidc-client";
import {Router} from "@angular/router";
import {UserService} from "../../service/user.service";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent {

  @Output()
  toggleSidenav = new EventEmitter<void>();

  isAuthenticated: boolean = false;
  userId!:string;

  constructor(private oidcSecurityService: OidcSecurityService,
              private router:Router,
              private userService: UserService) {
  }

  ngOnInit(): void {
    this.oidcSecurityService.isAuthenticated$.subscribe(({isAuthenticated}) => {
      this.isAuthenticated = isAuthenticated;
    });

  }

  onToggleSidenav = () => {
    this.toggleSidenav.emit();
  }

  login = () => {
    this.oidcSecurityService.authorize();
    this.router.navigateByUrl('/callback');

  }

  logout  = () => {
    this.oidcSecurityService.logoffAndRevokeTokens();
    this.oidcSecurityService.logoffLocal();
  }

  // viewChannel() {
  //   this.userId = this.userService.getUserId();
  //   this.router.navigate(['/channel/' + this.userId]).then(r => {
  //     console.log("profile");
  //   });
  // }
}
