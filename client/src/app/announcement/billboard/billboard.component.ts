import { Component, OnInit, signal, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { Announcement, AnnouncementService } from '../announcement.service';
import { AuthService } from '../../auth/auth.service';

@Component({
  selector: 'app-billboard',
  imports: [CommonModule],
  templateUrl: './billboard.component.html',
  styleUrl: './billboard.component.scss',
  standalone: true
})
export class BillboardComponent {
  private announcementService = inject(AnnouncementService);
  private router = inject(Router);
  protected authService = inject(AuthService);

  items = signal<Announcement[]>([]);
  loading = signal(true);

  ngOnInit() {
    this.announcementService.getAll().subscribe({
      next: (data) => {
        this.items.set(data);
        this.loading.set(false);
      },
      error: (err) => {
        console.error(err);
        this.loading.set(false);
      }
    });
  }

  protected edit(id: number) {
    this.router.navigate(['/admin/announcement/edit', id]);
  }

  protected delete(id:number) {
    if (!confirm('Are you sure you want to delete this announcement?')) {
      return;
    }

    this.announcementService.delete(id).subscribe({
      next: () => {
        this.items.set(this.items().filter(a => a.id !== id));
      },
      error: (err) => console.error("Delete failed",err)
    });

  }
}
