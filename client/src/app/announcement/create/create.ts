import { Component } from '@angular/core';
import {FormsModule} from '@angular/forms'
import {Router} from '@angular/router'
import {AnnouncementCreateUpdate} from '../announcement.service'
import {AnnouncementService} from '../announcement.service'

@Component({
  selector: 'app-create',
  imports: [FormsModule],
  templateUrl: './create.html',
  styleUrl: './create.scss',
  standalone: true
})
export class Create {
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
      private router: Router
    ) {}

  submit(){
    const payload = {
      ...this.form,
      startDate: this.form.startDate ? this.form.startDate + 'T00:00:00' : null,
      endDate: this.form.endDate ? this.form.endDate + 'T00:00:00' : null
    };
      this.service.create(payload).subscribe(() => {
        this.router.navigate(['/announcement/mine']);
      })
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
