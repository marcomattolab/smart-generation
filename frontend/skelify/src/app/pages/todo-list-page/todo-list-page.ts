import { ChangeDetectionStrategy, Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import {FormBuilder, FormGroup, FormsModule, NgForm, ReactiveFormsModule, Validators} from '@angular/forms';
import { TodoService } from '../../services/todo.service';
import { Todo } from '../../models/todo';

@Component({
  selector: 'app-todo-list-page',
  templateUrl: './todo-list-page.html',
  imports: [CommonModule,ReactiveFormsModule],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class TodoListPage implements OnInit {
  private readonly todoService = inject(TodoService);
  private readonly fb = inject(FormBuilder);

  todos: Todo[] = [];
  editing = false;

  // define reactive forms
  newTodoForm!: FormGroup;
  editTodoForm!: FormGroup;

  ngOnInit(): void {
    this.getTodos();

    this.newTodoForm = this.fb.group({
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

    const newTodo: Todo = {
      id: '',
      title: this.newTodoForm.value.title,
      completed: false,
      createdAt: new Date()
    };

    this.todoService.createTodo(newTodo).subscribe(createdTodo => {
      this.todos.unshift(createdTodo);
      this.newTodoForm.reset();
    });
  }

  deleteTodo(id: string): void {
    this.todoService.deleteTodo(id).subscribe(() => {
      this.todos = this.todos.filter(todo => todo.id !== id);
    });
  }

  startEditing(todo: Todo): void {
    this.editing = true;
    this.editTodoForm.setValue({ ...todo });
  }

  updateTodo(): void {
    if (this.editTodoForm.invalid) return;

    const updatedTodo: Todo = this.editTodoForm.value;
    this.todoService.updateTodo(updatedTodo).subscribe(result => {
      const index = this.todos.findIndex(t => t.id === result.id);
      if (index > -1) this.todos[index] = result;
      this.cancelEditing();
    });
  }

  toggleCompleted(todo: Todo): void {
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
