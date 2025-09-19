import { BASE_URL } from '../support/constants';

describe('Product Page', () => {
  beforeEach(() => {
    cy.visit(BASE_URL + '/product');
  });

  it('should perform a full CRUD cycle on a product item', () => {
    const newProduct = 'My new product';
    const newPrice = '123.45';

    // Create
    cy.get('[data-cy="new-product-input"]').type(newProduct);
    cy.get('[data-cy="new-product-price-input"]').type(newPrice);
    cy.get('[data-cy="add-product-button"]').click();
    cy.get('[data-cy="product-list"]').should('contain', newProduct);
    cy.get('[data-cy="product-list"]').should('contain', '$123.45');

  });
});
