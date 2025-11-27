import { Component, OnInit } from '@angular/core';
import { AnnouncementService } from '../announcement.service';
import { ActivatedRoute, Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { AnnouncementCreateUpdate } from '../announcement.service';

@Component({
  selector: 'app-edit',
  templateUrl: './edit.html',
  styleUrls: ['./edit.scss'],
  standalone: true,
  imports: [FormsModule, CommonModule]
})
export class Edit implements OnInit{
  id!: number;

  form: AnnouncementCreateUpdate = {
    title: '',
    content: '',
    startDate: '',
    endDate: '',
    published: true,
    topics: []
  };

  constructor(
    private service: AnnouncementService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.id = this.route.snapshot.params['id'];
    this.service.getById(Number(this.id)).subscribe(announcement => {
      this.form = {
        title: announcement.title,
        content: announcement.content,
        startDate: announcement.startDate?.split('T')[0] ?? '',
        endDate: announcement.endDate?.split('T')[0] ?? '',
        published: announcement.published,
        topics: announcement.topics || []
      };
    });
  }

  submit(){
    const payload = {
      ...this.form,
      startDate: this.form.startDate ? this.form.startDate + 'T00:00:00' : null,
      endDate: this.form.endDate ? this.form.endDate + 'T00:00:00' : null
    };
    this.service.update(this.id,payload).subscribe(() => {
      this.router.navigate(['/announcement/mine']);
    });
  }

  cancel(){
    this.router.navigate(['/announcement/mine']);
  }

  topicInput = "";

  addTopic(event: any) {
    event.preventDefault();

    if (!this.topicInput || this.topicInput.trim() === '') return;

    this.form.topics.push(this.topicInput.trim());
    this.topicInput = '';
  }

  removeTopic(index: number) {
    this.form.topics.splice(index, 1);
  }

  protected readonly KeyboardEvent = KeyboardEvent;

}
