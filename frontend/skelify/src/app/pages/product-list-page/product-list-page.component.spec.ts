import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ProductListPage } from './product-list-page.component';
import { ProductService } from '../../services/product.service';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { of } from 'rxjs';
import { TranslateModule, TranslateService } from '@ngx-translate/core';
import { IProduct } from '../../models/page/product.model';

describe('ProductListPage', () => {
  let component: ProductListPage;
  let fixture: ComponentFixture<ProductListPage>;
  let mockProductService: jasmine.SpyObj<ProductService>;
  let mockTranslateService: jasmine.SpyObj<TranslateService>;

  const mockProducts: IProduct[] = [
    { id: 1, name: 'Test Product 1', price: 10 },
    { id: 2, name: 'Test Product 2', price: 20 },
  ];

  beforeEach(async () => {
    mockProductService = jasmine.createSpyObj('ProductService', ['getProducts', 'createProduct', 'deleteProduct', 'updateProduct']);
    mockProductService.getProducts.and.returnValue(of(mockProducts));
    mockProductService.createProduct.and.callFake((product: IProduct) => of({ ...product, id: 3 }));
    mockProductService.deleteProduct.and.returnValue(of(undefined));
    mockProductService.updateProduct.and.callFake((product: IProduct) => of(product));

    mockTranslateService = jasmine.createSpyObj('TranslateService', ['get']);
    mockTranslateService.get.and.returnValue(of(''));

    await TestBed.configureTestingModule({
      imports: [ProductListPage, ReactiveFormsModule, TranslateModule.forRoot()],
      providers: [
        { provide: ProductService, useValue: mockProductService },
        { provide: TranslateService, useValue: mockTranslateService },
        FormBuilder,
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(ProductListPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should get products on init', () => {
    expect(mockProductService.getProducts).toHaveBeenCalled();
    expect(component.products.length).toBe(2);
  });

  it('should create a new product', () => {
    component.productForm.setValue({ name: 'New Product' });
    component.createProduct();
    expect(mockProductService.createProduct).toHaveBeenCalled();
    expect(component.products.length).toBe(3);
  });

  it('should delete a product', () => {
    component.deleteProduct(1);
    expect(mockProductService.deleteProduct).toHaveBeenCalledWith(1);
    expect(component.products.length).toBe(1);
  });

  it('should update a product', () => {
    const productToUpdate = { ...mockProducts[0], name: 'Updated Product' };
    component.editProductForm.setValue(productToUpdate);
    component.updateProduct();
    expect(mockProductService.updateProduct).toHaveBeenCalledWith(productToUpdate);
    expect(component.products[0].name).toBe('Updated Product');
  });
});
