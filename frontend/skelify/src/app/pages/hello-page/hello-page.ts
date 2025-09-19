import {ChangeDetectionStrategy, Component, inject, OnInit, signal} from '@angular/core';
import { CommonModule } from '@angular/common';
import {FormsModule, ReactiveFormsModule, Validators} from '@angular/forms';
import {TranslateModule, TranslatePipe} from '@ngx-translate/core';
import {TodoService} from '../../services/todo.service';
import {HelloService} from '../../services/hello.service';
import {ITodo} from '../../models/page/todo.model';
import {IHello} from '../../models/page/hello.model';

@Component({
  selector: 'app-hello-page',
  templateUrl: './hello-page.html',
  styleUrls: ['./hello-page.scss'],
  imports: [CommonModule, TranslateModule],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class HelloPage implements OnInit {
  private readonly helloService = inject(HelloService);
  readonly item = signal<IHello| null>(null);

  ngOnInit(): void {
    this.loadHello();
  }

  private loadHello(): void {
    this.helloService.getHello().subscribe({
      next: (item: IHello) => this.item.set(item),
      error: (err: any) => console.error('Failed to load hello message', err)
    });
  }

}
