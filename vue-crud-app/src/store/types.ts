// src/store/types.ts

export interface RootState {
    products: ProductState;
}

export interface ProductState {
    products: Product[];
    isLoading: boolean;
}

export interface Product {
    id: number;
    name: string;
    price: number;
}
