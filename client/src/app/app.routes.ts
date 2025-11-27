import { Routes } from '@angular/router';
import { LoginComponent } from './auth/login/login.component';
import { RegisterComponent } from './auth/register/register.component';
import {Create} from './announcement/create/create'
import {Edit} from './announcement/edit/edit'
import {ListMine} from './announcement/list-mine/list-mine'
import {UsersList} from './announcement/users/users-list/users-list'
import {AnnouncementEdit} from './announcement/announcement-edit/announcement-edit';

export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'announcement/new', component: Create },
  { path: 'announcement/:id/edit', component: Edit },
  { path: 'announcement/mine', component: ListMine },
  { path: 'admin/users', component: UsersList},
  {
    path: 'admin/announcement/edit/:id',
    loadComponent: () =>
      import('./announcement/announcement-edit/announcement-edit')
        .then(m => m.AnnouncementEdit)
  }
];
