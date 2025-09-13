import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';

export interface IMenuItem {
  code: string;
  link: string;
}

@Injectable({
  providedIn: 'root',
})
export class MenuService {
  /**
   * GET Menu Items
   * @returns IMenuItem[]
   */
  public getMenuItems(): Observable<IMenuItem[]> {
    const menuItems: IMenuItem[] = [
      { code: 'home', link: '/home' },
      { code: 'Todos', link: '/todo' },
      { code: 'Products', link: '/product' }
    ];
    return of(menuItems);
  }
}
