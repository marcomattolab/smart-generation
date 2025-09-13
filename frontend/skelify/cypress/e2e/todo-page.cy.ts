describe('Todo Page', () => {
  beforeEach(() => {
    // For a real application, you would seed the database or use cy.intercept to mock the API calls.
    // For this example, we will just visit the page.
    cy.visit('/todo');
  });

  it('should perform a full CRUD cycle on a todo item', () => {
    const newTodo = 'My new todo';
    const editedTodo = 'My edited todo';

    // Create
    cy.get('[data-cy="new-todo-input"]').type(newTodo);
    cy.get('[data-cy="add-todo-button"]').click();
    cy.get('[data-cy="todo-list"]').should('contain', newTodo);

    // Find the created todo and get its id
    cy.contains('[data-cy^="todo-item-"]', newTodo).invoke('attr', 'data-cy').then(dataCy => {
      const id = dataCy!.split('-').pop();

      // Edit
      cy.get(`[data-cy="edit-todo-button-${id}"]`).click();
      cy.get(`[data-cy="edit-todo-input-${id}"]`).clear().type(editedTodo);
      cy.get(`[data-cy="save-todo-button-${id}"]`).click();
      cy.get(`[data-cy="todo-item-title-${id}"]`).should('contain', editedTodo);

      // Complete
      cy.get(`[data-cy="complete-todo-button-${id}"]`).click();
      cy.get(`[data-cy="todo-item-${id}"]`).should('have.class', 'opacity-60');

      // Delete
      cy.get(`[data-cy="delete-todo-button-${id}"]`).click();
      cy.get(`[data-cy="todo-item-${id}"]`).should('not.exist');
    });
  });
});
