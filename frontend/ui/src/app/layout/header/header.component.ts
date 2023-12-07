import {Component, Input} from '@angular/core';
import {OidcSecurityService} from "angular-auth-oidc-client";
import {Router} from "@angular/router";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent {


  isAuthenticated: boolean = false;

  constructor(private oidcSecurityService: OidcSecurityService,
              private router:Router) {
  }

  ngOnInit(): void {
    this.oidcSecurityService.isAuthenticated$.subscribe(({isAuthenticated}) => {
      this.isAuthenticated = isAuthenticated;
    })
  }

  login = () => {
    this.oidcSecurityService.authorize();
    // this.router.navigateByUrl('/callback');

  }

  logout  = () => {
    this.oidcSecurityService.logoffAndRevokeTokens();
    this.oidcSecurityService.logoffLocal();
  }
}
