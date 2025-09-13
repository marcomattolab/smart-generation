describe('Product Page', () => {
  beforeEach(() => {
    cy.visit('/product');
  });

  it('should perform a full CRUD cycle on a product item', () => {
    const newProduct = 'My new product';
    const editedProduct = 'My edited product';

    // Create
    cy.get('[data-cy="new-product-input"]').type(newProduct);
    cy.get('[data-cy="add-product-button"]').click();
    cy.get('[data-cy="product-list"]').should('contain', newProduct);

    // Find the created product and get its id
    cy.contains('[data-cy^="product-item-"]', newProduct).invoke('attr', 'data-cy').then(dataCy => {
      const id = dataCy!.split('-').pop();

      // Edit
      cy.get(`[data-cy="edit-product-button-${id}"]`).click();
      cy.get(`[data-cy="edit-product-input-${id}"]`).clear().type(editedProduct);
      cy.get(`[data-cy="save-product-button-${id}"]`).click();
      cy.get(`[data-cy="product-item-name-${id}"]`).should('contain', editedProduct);

      // Delete
      cy.get(`[data-cy="delete-product-button-${id}"]`).click();
      cy.get(`[data-cy="product-item-${id}"]`).should('not.exist');
    });
  });
});
