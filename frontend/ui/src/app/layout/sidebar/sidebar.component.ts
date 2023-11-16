import {Component} from '@angular/core';
import {Sidenavitem} from "../../model/sidenavitem";

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.scss']
})
export class SidebarComponent {

  sideNavItems: Sidenavitem[] = [
    {
      name: 'Home',
      link: 'featured',
      icon:'home'
    },
    {
      name: 'Subscriptions',
      link: 'subscriptions',
      icon:'subscriptions'
    },
    {
      name: 'History',
      link: 'history',
      icon:'history'
    },
    {
      name: 'Liked Videos',
      link: 'liked/videos',
      icon:'thumb_up'
    },
  ]

}
