import { ComponentFixture, TestBed } from '@angular/core/testing';
import { TodoListPage } from './todo-list-page';
import { TodoService } from '../../services/todo.service';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { of } from 'rxjs';
import { TranslateModule, TranslateService } from '@ngx-translate/core';
import { ITodo } from '../../models/page/todo.model';

describe('TodoListPage', () => {
  let component: TodoListPage;
  let fixture: ComponentFixture<TodoListPage>;
  let mockTodoService: jasmine.SpyObj<TodoService>;
  let mockTranslateService: jasmine.SpyObj<TranslateService>;

  const mockTodos: ITodo[] = [
    { id: '1', title: 'Test Todo 1', completed: false, createdAt: new Date() },
    { id: '2', title: 'Test Todo 2', completed: true, createdAt: new Date() },
  ];

  beforeEach(async () => {
    mockTodoService = jasmine.createSpyObj('TodoService', ['getTodos', 'createTodo', 'deleteTodo', 'updateTodo']);
    mockTodoService.getTodos.and.returnValue(of(mockTodos));
    mockTodoService.createTodo.and.callFake((todo: ITodo) => of({ ...todo, id: '3' }));
    mockTodoService.deleteTodo.and.returnValue(of(undefined));
    mockTodoService.updateTodo.and.callFake((todo: ITodo) => of(todo));

    mockTranslateService = jasmine.createSpyObj('TranslateService', ['get']);
    mockTranslateService.get.and.returnValue(of(''));

    await TestBed.configureTestingModule({
      imports: [TodoListPage, ReactiveFormsModule, TranslateModule.forRoot()],
      providers: [
        { provide: TodoService, useValue: mockTodoService },
        { provide: TranslateService, useValue: mockTranslateService },
        FormBuilder,
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(TodoListPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should get todos on init', () => {
    expect(mockTodoService.getTodos).toHaveBeenCalled();
    expect(component.todos.length).toBe(2);
  });

  it('should create a new todo', () => {
    component.newTodoForm.setValue({ title: 'New Todo' });
    component.createTodo();
    expect(mockTodoService.createTodo).toHaveBeenCalled();
    expect(component.todos.length).toBe(3);
  });

  it('should delete a todo', () => {
    component.deleteTodo('1');
    expect(mockTodoService.deleteTodo).toHaveBeenCalledWith('1');
    expect(component.todos.length).toBe(1);
  });

  it('should update a todo', () => {
    const todoToUpdate = { ...mockTodos[0], title: 'Updated Todo' };
    component.editTodoForm.setValue(todoToUpdate);
    component.updateTodo();
    expect(mockTodoService.updateTodo).toHaveBeenCalledWith(todoToUpdate);
    expect(component.todos[0].title).toBe('Updated Todo');
  });

  it('should toggle completed status', () => {
    const todo = mockTodos[0];
    component.toggleCompleted(todo);
    expect(mockTodoService.updateTodo).toHaveBeenCalledWith({ ...todo, completed: !todo.completed });
  });
});
