import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';

export interface Announcement {
  id: number;
  title: string;
  content: string;
  startDate: string;
  endDate: string;
  published: boolean;
  ownerUsername: string;
  topics: string[];
}

export interface AnnouncementCreateUpdate {
  title: string;
  content: string;
  startDate: string | null;
  endDate: string | null;
  published: boolean;
  topics: string[];
}

@Injectable({
  providedIn: 'root'
})

export class AnnouncementService {
  private apiUrlPublic = 'http://localhost:8080/api/public/billboard';

  constructor(private http: HttpClient) {}

  // Public
  getAll(): Observable<Announcement[]> {
    return this.http.get<Announcement[]>(this.apiUrlPublic);
  }

  // Private
  private readonly apiUrl = 'http://localhost:8080/api/announcement';

  create(announcement: AnnouncementCreateUpdate): Observable<Announcement> {
    return this.http.post<Announcement>(this.apiUrl, announcement);
  }

  getById(id: number): Observable<Announcement> {
    return this.http.get<Announcement>(`${this.apiUrl}/${id}`);
  }

  listMine(): Observable<Announcement[]> {
    return this.http.get<Announcement[]>(`${this.apiUrl}/mine`).pipe(
      tap(data => console.log('Mine',data))
    );
  }

  update(id: number, announcement: AnnouncementCreateUpdate): Observable<Announcement> {
    return this.http.put<Announcement>(`${this.apiUrl}/${id}`, announcement);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
