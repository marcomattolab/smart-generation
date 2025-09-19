import { ComponentFixture, TestBed } from '@angular/core/testing';
import { TodoListPage } from './todo-list-page';
import { TodoService } from '../../services/todo.service';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { of } from 'rxjs';
import { TranslateModule } from '@ngx-translate/core';
import { ITodo } from '../../models/page/todo.model';

// Mock TodoService
const mockTodoService = {
  getTodos: jasmine.createSpy('getTodos').and.returnValue(of([])),
  createTodo: jasmine.createSpy('createTodo').and.returnValue(
    of({ id: '1', title: 'Test Todo', completed: false, createdAt: new Date() })
  ),
  updateTodo: jasmine.createSpy('updateTodo').and.returnValue(of({})),
  deleteTodo: jasmine.createSpy('deleteTodo').and.returnValue(of({}))
};

describe('TodoListPage', () => {
  let component: TodoListPage;
  let fixture: ComponentFixture<TodoListPage>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        TodoListPage,
        ReactiveFormsModule,
        TranslateModule.forRoot()
      ],
      providers: [
        { provide: TodoService, useValue: mockTodoService },
        FormBuilder
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(TodoListPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should load todos on init', () => {
    expect(mockTodoService.getTodos).toHaveBeenCalled();
  });

  it('should add a todo', () => {
    component.newTodoForm.controls['title'].setValue('New Todo');
    component.createTodo();
    expect(mockTodoService.createTodo).toHaveBeenCalled();
  });

  it('should delete a todo', () => {
    component.deleteTodo(1);
    expect(mockTodoService.deleteTodo).toHaveBeenCalledWith(1);
  });
});
