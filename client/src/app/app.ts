import { Component, signal, inject } from '@angular/core';
import { RouterOutlet, RouterLink, Router } from '@angular/router';
import { BillboardComponent } from './announcement/billboard/billboard.component';
import { AuthService } from './auth/auth.service';
import { AnnouncementService } from './announcement/announcement.service';


@Component({
  selector: 'app-root',
  imports: [RouterOutlet,RouterLink, BillboardComponent],
  templateUrl: './app.html',
  standalone: true,
  styleUrl: './app.scss'
})
export class App {
  protected readonly authService = inject(AuthService);
  protected readonly announce = inject(AnnouncementService);
  protected readonly title = signal('client');
  private router = inject(Router);

  isAuthPage() {
    return this.router.url.startsWith('/login') ||
      this.router.url.startsWith('/register');
  }

  protected isHomePage() {
    return this.router.url === '/' || this.router.url === '';
  }
}
