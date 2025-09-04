export type ProductApi = {
  id: string,
  name: string,
  description: string,
  barcode: string,
  reference: string,
  htPrice: string,
  vatRate: string,
  idCatalog: number,
  idCategory: number,
  quantity?: number,
}

export type ProductWithCategoryApi = {
  id: string,
  name: string,
  description: string,
  barcode: string,
  reference: string,
  htPrice: string,
  vatRate: string,
  idCategory: string
}

export type ProductSaving = {
  idCategory: string,
  name: string,
  description: string,
  reference: string,
  idCatalog: string,
  products: ProductApi[]
}

export type GetAllProducts = ProductApi[] | undefined;
export type Product = ProductApi | undefined;

export interface ProductsGateway {
  getProductById(id: string): Promise<Product>;
  getProductsByCatalogId(catalogId: string): Promise<GetAllProducts>;
  getProductsByCategoryId(categoryId: string): Promise<GetAllProducts>;
  createProduct(payload: ProductSaving): Promise<ProductWithCategoryApi[]>;
  updateProduct(id: string, payload: Product): Promise<Product>;
  deleteProductById(id: string): Promise<void>;
}
