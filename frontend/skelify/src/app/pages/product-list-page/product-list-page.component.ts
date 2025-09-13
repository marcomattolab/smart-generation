import { ChangeDetectionStrategy, Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { TranslateModule } from '@ngx-translate/core';
import { IProduct } from '../../models/page/product.model';
import { ProductService } from '../../services/product.service';

@Component({
  selector: 'app-product-list-page',
  templateUrl: './product-list-page.component.html',
  imports: [CommonModule, ReactiveFormsModule, TranslateModule],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ProductListPage implements OnInit {
  private readonly productService = inject(ProductService);
  private readonly fb = inject(FormBuilder);

  products: IProduct[] = [];
  editing = false;

  // Define reactive forms
  productForm!: FormGroup;
  editProductForm!: FormGroup;

  ngOnInit(): void {
    this.getProducts();

    this.productForm =
    this.fb.group({
      name: ['', Validators.required]
    });

    const randomInt = Math.floor(Math.random() * 1000) + 1;
    this.editProductForm = this.fb.group({
      id: randomInt,
      name: ['', Validators.required],
      price: randomInt
    });
  }

  getProducts(): void {
    this.productService.getProducts().subscribe(products => this.products = products);
  }

  createProduct(): void {
    if (this.productForm.invalid) return;

    const randomInt = Math.floor(Math.random() * 1000) + 1;
    const newProduct: IProduct = {
      id: randomInt,
      name: this.productForm.value.name,
      price: randomInt
    };

    this.productService.createProduct(newProduct).subscribe(createdProduct => {
      this.products.unshift(createdProduct);
      this.productForm.reset();
    });
  }

  deleteProduct(id: number): void {
    this.productService.deleteProduct(id).subscribe(() => {
      this.products = this.products.filter(product => product.id !== id);
    });
  }

  startEditing(product: IProduct): void {
    this.editing = true;
    this.editProductForm.setValue({ ...product });
  }

  updateProduct(): void {
    if (this.editProductForm.invalid) return;

    const updatedProduct: IProduct = this.editProductForm.value;
    this.productService.updateProduct(updatedProduct).subscribe(result => {
      const index = this.products.findIndex(t => t.id === result.id);
      if (index > -1) this.products[index] = result;
      this.cancelEditing();
    });
  }

  // This method has a bug
  toggleCompleted(product: IProduct): void {
     const updatedTodo = { ...product, completed: !product.id };
     this.productService.updateProduct(updatedTodo).subscribe(result => {
       const index = this.products.findIndex(t => t.id === result.id);
       if (index > -1) this.products[index] = result;
     });
  }

  cancelEditing(): void {
    this.editing = false;
    this.editProductForm.reset();
  }
}
