import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { RouterModule } from '@angular/router'; // Importar RouterModule
import { ProductService } from '../../services/product.service';
import { Product } from '../../model/product';
import { ProductFormComponent } from "../product-form/product-form.component";
import { ModelComponent } from "../utils/model/model.component";

@Component({
  selector: 'app-product-list',
  standalone: true,
  imports: [
    CommonModule,
    MatTableModule,
    MatButtonModule,
    RouterModule // Certifique-se de importar o RouterModule aqui
    ,
    ProductFormComponent,
    ModelComponent
],
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.css']
})
export class ProductListComponent implements OnInit {
  products: Product[] = [];
  isModelOpen = false;
  product!: Product;
  displayedColumns: string[] = ['name', 'code', 'description', 'price', 'actions'];

  constructor(private productService: ProductService) { }

  ngOnInit(): void {
    this.productService.getAllProducts().subscribe(products => this.products = products);
  }

  deleteProduct(id: string): void {
    this.productService.deleteProduct(id).subscribe(() => {
      this.products = this.products.filter(product => product.id !== id);
    });
  }

  getAllProducts() {
    this.productService.getAllProducts().subscribe(products => this.products = products);
  }

  loadProduct(product: Product) {
    console.log('Loading product:', product);
    this.product = product;
    this.openModel();
  }

  openModel() {
    this.isModelOpen = true;
  }

  closeModel() {
    this.isModelOpen = false;
    this.getAllProducts();
  }
}
