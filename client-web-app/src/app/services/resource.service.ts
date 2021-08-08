import {Observable} from 'rxjs';
import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {map} from 'rxjs/operators';

@Injectable({providedIn: 'root'})
export class ResourceService {
  constructor(private http: HttpClient) {
  }

  public create<T>(url: string, model: any): Observable<T> {
    return this.http.post(url, model).pipe(
      map((response: HttpResponse<T>) => {
        return response.body;
      })
    );
  }

  public get<T>(url: string): Observable<any> {
    return this.http.get(url);
  }

  public update<T>(url: any, model: any): Observable<T> {
    return this.http.put(url, model).pipe(
      map((response: HttpResponse<T>) => {
        return response.body;
      })
    );
  }

  public delete<T>(url: string): Observable<T> {
    return this.http.delete(url).pipe(
      map((response: HttpResponse<T>) => {
        return response.body;
      })
    );
  }
}
