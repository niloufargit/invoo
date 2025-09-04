type CatalogAPI = {
  id: number;
  name: string;
  description: string;
  reference: string;
  idCompany: string;
};

export type CategoryDto ={
  id: number;
  name: string;
  description: string;
  reference: string;
  idCatalog: string;
}

export type CatalogWithCategories = {
  id: number;
  name: string;
  description: string;
  reference: string;
  idCompany: string;
  categories: CategoryDto[];
}

export type GetAllCatalogs = CatalogWithCategories[] | undefined;
export type Catalog = CatalogAPI | undefined;

export interface CatalogsGateway {
  getCatalogs(): Promise<GetAllCatalogs>;
  getCatalogById(id: string): Promise<Catalog>;
  createCatalog(payload: Catalog): Promise<Catalog>;
  updateCatalog(id: string, payload: Catalog): Promise<Catalog>;
}
