import { Component, OnInit, signal, inject } from '@angular/core'; // Add 'inject' here
import { CommonModule } from '@angular/common';
import { Announcement, AnnouncementService } from '../announcement.service';

@Component({
  selector: 'app-billboard',
  imports: [],
  templateUrl: './billboard.component.html',
  styleUrl: './billboard.component.scss',
  standalone: true
})
export class BillboardComponent {
  private announcementService = inject(AnnouncementService);

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
}
