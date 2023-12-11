import {Component} from '@angular/core';
import {OidcSecurityService} from "angular-auth-oidc-client";
import {SidenavService} from "./service/sidenav.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'ui';

  constructor(private oidcSecurityService: OidcSecurityService,
              private sidenavService: SidenavService) {
  }

  ngOnInit(): void {
    this.oidcSecurityService.checkAuth().subscribe(({isAuthenticated}) => {
      console.log(isAuthenticated)
    });
  };

  toggleSidenav =  () => {
    this.sidenavService.toggle();
  };

}
