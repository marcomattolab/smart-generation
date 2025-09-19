import { ChangeDetectionStrategy, Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { TodoService } from '../../services/todo.service';
import { ITodo } from '../../models/page/todo.model';
import { TranslateModule } from '@ngx-translate/core';

@Component({
  selector: 'app-todo-list-page',
  templateUrl: './todo-list-page.html',
  styleUrls: ['./todo-list-page.scss'],
  imports: [CommonModule, ReactiveFormsModule, TranslateModule],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class TodoListPage implements OnInit {
  private readonly todoService = inject(TodoService);
  private readonly fb = inject(FormBuilder);

  todos: ITodo[] = [];
  editing = false;

  // Define reactive forms
  newTodoForm!: FormGroup;
  editTodoForm!: FormGroup;

  ngOnInit(): void {
    this.getTodos();

    this.newTodoForm =
    this.fb.group({
      title: ['', Validators.required]
    });

    this.editTodoForm = this.fb.group({
      id: [''],
      title: ['', Validators.required],
      completed: [false],
      createdAt: [new Date()]
    });
  }

  getTodos(): void {
    this.todoService.getTodos().subscribe(todos => this.todos = todos);
  }

  createTodo(): void {
    if (this.newTodoForm.invalid) return;

    const newTodo: ITodo = {
      id: 0,
      title: this.newTodoForm.value.title,
      completed: false,
      createdAt: new Date()
    };

    this.todoService.createTodo(newTodo).subscribe(createdTodo => {
      this.todos.unshift(createdTodo);
      this.newTodoForm.reset();
    });
  }

  deleteTodo(id: number): void {
    this.todoService.deleteTodo(id).subscribe(() => {
      this.todos = this.todos.filter(todo => todo.id !== id);
    });
  }

  startEditing(todo: ITodo): void {
    this.editing = true;
    this.editTodoForm.setValue({ ...todo });
  }

  updateTodo(): void {
    if (this.editTodoForm.invalid) return;

    const updatedTodo: ITodo = this.editTodoForm.value;
    this.todoService.updateTodo(updatedTodo).subscribe(result => {
      const index = this.todos.findIndex(t => t.id === result.id);
      if (index > -1) this.todos[index] = result;
      this.cancelEditing();
    });
  }

  toggleCompleted(todo: ITodo): void {
    const updatedTodo = { ...todo, completed: !todo.completed };
    this.todoService.updateTodo(updatedTodo).subscribe(result => {
      const index = this.todos.findIndex(t => t.id === result.id);
      if (index > -1) this.todos[index] = result;
    });
  }

  cancelEditing(): void {
    this.editing = false;
    this.editTodoForm.reset();
  }
}
