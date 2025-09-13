describe('Product Page', () => {
  beforeEach(() => {
    const baseurl="http://localhost:4200"; // TODO Move into constant
    cy.visit(baseurl + '/product');
  });

  it('should perform a full CRUD cycle on a product item', () => {
    const newProduct = 'My new product';

    // Create
    cy.get('[data-cy="new-product-input"]').type(newProduct);
    cy.get('[data-cy="add-product-button"]').click();
    cy.get('[data-cy="product-list"]').should('contain', newProduct);

  });
});
