import axios from 'axios';
// import { Product } from '@/store/modules/products';
import { Product } from '@/store/types';

//BASE API (gateway)
const BASE_API_URL="http://localhost:8081"; 

// PRODUCT BASE
const API_PRODUCT_URL = `${BASE_API_URL}/api/products`;

// PRODUCT
export const fetchProducts = (search = {}) => {
    const params = {
      ...search,
    };
    return axios.get<Product[]>(API_PRODUCT_URL, { params });
  };
export const createProduct = (product: Product) => axios.post<Product>(API_PRODUCT_URL, product);
export const updateProduct = (product: Product) => axios.put<Product>(`${API_PRODUCT_URL}/${product.id}`, product);
export const deleteProduct = (id: number) => axios.delete(`${API_PRODUCT_URL}/${id}`);
