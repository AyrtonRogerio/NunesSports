import { Component, EventEmitter, Input, OnChanges, OnInit, Output } from '@angular/core';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormControl, FormGroup, FormsModule, Validators } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { ProductService } from '../../services/product.service';
import { Product } from '../../model/product';
import { ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-product-form',
  standalone: true,
  imports: [
    CommonModule,
    MatButtonModule,
    ReactiveFormsModule,
    RouterModule
  ],
  templateUrl: './product-form.component.html',
  styleUrls: ['./product-form.component.css']
})
export class ProductFormComponent implements OnChanges {

  @Input() data: Product | null = null;
  @Output() onCloseModel = new EventEmitter();

  productForm!: FormGroup;

  product: Product = { id: '', name: '', code: '', description: '', price: 0 };
  isEditing = false;

  constructor(
    private fb: FormBuilder,
    private productService: ProductService,
    // private toastr: ToastrService
  ) {
    this.productForm = this.fb.group({
      name: new FormControl('', [Validators.required]),
      code: new FormControl('', [Validators.required]),
      description: new FormControl('', [Validators.required]),
      price: new FormControl('', [Validators.required]),
    });
  }


  onClose() {
    this.onCloseModel.emit(false);
  }

  ngOnChanges(): void {
    console.log('Product data received:', this.data);
    if (this.data) {
      this.isEditing = true;
      this.productForm.patchValue(this.data);
    } else {
      this.isEditing = false;
      this.productForm.reset();
    }
  }

  onSubmit() {
    if (this.productForm.valid) {
      const productData = this.productForm.value as Product;
      if (this.isEditing && this.data?.id) {
        productData.id = this.data.id
        this.productService
          .updateProduct(productData)
          .subscribe({
            next: (response: any) => {
              this.resetProductForm();
            },
            error: (err: any) => {
              console.error('Erro ao atualizar o produto:', err);
            }
          });
      } else {
        this.productService.createProduct(productData).subscribe({
          next: (response: any) => {
            this.resetProductForm();
          },
          error: (err: any) => {
            console.error('Erro ao criar o produto:', err);
          }
        });
      }
    } else {
      this.productForm.markAllAsTouched();
    }
  }


  resetProductForm() {
    this.productForm.reset();
    this.onClose();
  }
}
