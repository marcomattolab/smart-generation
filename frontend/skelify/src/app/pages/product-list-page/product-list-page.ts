import { ChangeDetectionStrategy, Component, computed, inject, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { TranslateModule } from '@ngx-translate/core';
import { IProduct } from '../../models/page/product.model';
import { ProductService } from '../../services/product.service';

@Component({
  selector: 'app-product-list-page',
  templateUrl: './product-list-page.html',
  styleUrls: ['./product-list-page.scss'],
  imports: [CommonModule, ReactiveFormsModule, TranslateModule],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ProductListPage implements OnInit {
  private readonly productService = inject(ProductService);
  private readonly fb = inject(FormBuilder);

  // Signals
  products = signal<IProduct[]>([]);
  editing = signal(false);
  editingProductId = signal<number | null>(null);

  // Forms
  productForm!: FormGroup;
  editProductForm!: FormGroup;

  ngOnInit(): void {
    this.loadProducts();

    this.productForm = this.fb.group({
      name: ['', Validators.required],
      price: [0, [Validators.required, Validators.min(0)]],
    });

    this.editProductForm = this.fb.group({
      id: null,
      name: ['', Validators.required],
      price: [0, [Validators.required, Validators.min(0)]],
    });
  }

  loadProducts(): void {
    this.productService.getProducts().subscribe((products) => {
      this.products.set(products);
    });
  }

  createProduct(): void {
    if (this.productForm.invalid) return;

    const newProduct: IProduct = {
      id: 0,
      name: this.productForm.value.name,
      price: this.productForm.value.price,
    };

    this.productService.createProduct(newProduct).subscribe((created) => {
      this.products.update((prev) => [created, ...prev]);
      this.productForm.reset();
    });
  }

  deleteProduct(id: number): void {
    this.productService.deleteProduct(id).subscribe(() => {
      this.products.update((prev) => prev.filter((p) => p.id !== id));
    });
  }

  startEditing(product: IProduct): void {
    this.editing.set(true);
    this.editingProductId.set(product.id);
    this.editProductForm.setValue({ ...product });
  }

  updateProduct(): void {
    if (this.editProductForm.invalid) return;

    const updatedProduct: IProduct = this.editProductForm.value;
    this.productService.updateProduct(updatedProduct).subscribe((result) => {
      this.products.update((prev) =>
        prev.map((p) => (p.id === result.id ? result : p))
      );
      this.cancelEditing();
    });
  }

  cancelEditing(): void {
    this.editing.set(false);
    this.editingProductId.set(null);
    this.editProductForm.reset();
  }
}
