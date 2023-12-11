import {Component} from '@angular/core';
import {Sidenavitem} from "../../model/sidenavitem";
import {SidenavService} from "../../service/sidenav.service";

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.scss']
})
export class SidebarComponent {

  isSidenavOpen = false;

  constructor(private sidenavService: SidenavService) {

  }

  ngOnInit() {
    this.sidenavService.state.subscribe({
      next: (state: boolean) => {
        this.isSidenavOpen = state;
      }
    })
  }


  sideNavItems: Sidenavitem[] = [
    {
      name: 'Home',
      link: 'featured',
      icon: 'home'
    },
    {
      name: 'Subscriptions',
      link: 'subscriptions',
      icon: 'subscriptions'
    },
    {
      name: 'History',
      link: 'history',
      icon: 'history'
    },
    {
      name: 'Liked Videos',
      link: 'liked/videos',
      icon: 'thumb_up'
    },
  ]

}
