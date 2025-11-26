import { Component } from '@angular/core';
import {CommonModule} from '@angular/common'
import {RouterModule} from '@angular/router'
import {AnnouncementService} from '../announcement.service'


@Component({
  selector: 'app-list-mine',
  imports: [CommonModule, RouterModule],
  templateUrl: './list-mine.html',
  styleUrl: './list-mine.scss',
  standalone: true
})
export class ListMine {
  announcements$ : any;

  constructor(private service: AnnouncementService) {}

  ngOnInit() {
    this.announcements$ = this.service.listMine();
  }
  delete(id: number) {
    if (!confirm('Delete this announcement?')) return;

    this.service.delete(id).subscribe(() => {
      this.announcements$ = this.service.listMine();
    });
  }

}
