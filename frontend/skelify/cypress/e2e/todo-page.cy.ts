describe('Todo Page', () => {
  beforeEach(() => {
    const baseurl="http://localhost:4200"; // TODO Move into constant
    cy.visit(baseurl + '/todo');
  });

  it('should perform a full CRUD cycle on a todo item', () => {
    const newTodo = 'My new todo';

    // Create
    cy.get('[data-cy="new-todo-input"]').type(newTodo);
    cy.get('[data-cy="add-todo-button"]').click();
    cy.get('[data-cy="todo-list"]').should('contain', newTodo);
  });
});
