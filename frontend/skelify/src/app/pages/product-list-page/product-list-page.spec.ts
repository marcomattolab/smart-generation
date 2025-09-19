import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ProductListPage } from './product-list-page';
import { ProductService } from '../../services/product.service';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { of } from 'rxjs';
import { Pipe, PipeTransform } from '@angular/core';
import {TranslateModule} from '@ngx-translate/core';

// Standalone mock pipe
@Pipe({ name: 'translate', standalone: true })
class MockTranslatePipe implements PipeTransform {
  transform(value: any): any { return value; }
}

// Mock ProductService
const mockProductService = {
  getProducts: jasmine.createSpy('getProducts').and.returnValue(of([])),
  createProduct: jasmine.createSpy('createProduct').and.returnValue(of({ id: 1, name: 'Test Product', price: 10 })),
  deleteProduct: jasmine.createSpy('deleteProduct').and.returnValue(of({})),
};


describe('ProductListPage', () => {
  let component: ProductListPage;
  let fixture: ComponentFixture<ProductListPage>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        ProductListPage,
        ReactiveFormsModule,
        TranslateModule.forRoot()
      ],
      providers: [
        { provide: ProductService, useValue: mockProductService },
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

  it('should call getProducts on init', () => {
    expect(mockProductService.getProducts).toHaveBeenCalled();
  });

  it('should create a new product', () => {
    component.productForm.controls['name'].setValue('New Product');
    component.createProduct();
    expect(mockProductService.createProduct).toHaveBeenCalled();
  });

  it('should delete a product', () => {
    component.deleteProduct(1);
    expect(mockProductService.deleteProduct).toHaveBeenCalledWith(1);
  });
});
