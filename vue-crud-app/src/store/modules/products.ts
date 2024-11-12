import { Module, ActionContext, ActionTree, GetterTree, MutationTree } from 'vuex';
import { RootState, Product, ProductState } from '../types';
import { fetchProducts, createProduct, updateProduct, deleteProduct } from '@/utils/api';


// Khởi tạo state
const state: ProductState = {
  products: [], // Giữ lại tên trường items
  isLoading: false, // Thêm trường isLoading nếu cần
};

// Định nghĩa mutations
const mutations: MutationTree<ProductState> = {
  SET_PRODUCTS(state, products: Product[]) {
    state.products = products;
  },
  ADD_PRODUCT(state, product: Product) {
    state.products.push(product);
  },
  UPDATE_PRODUCT(state, updatedProduct: Product) {
    const index = state.products.findIndex(p => p.id === updatedProduct.id);
    if (index !== -1) {
      state.products.splice(index, 1, updatedProduct);
    }
  },
  DELETE_PRODUCT(state, productId: number) {
    state.products = state.products.filter(p => p.id !== productId);
  }
};

// Định nghĩa actions
const actions: ActionTree<ProductState, RootState> = {
  async fetchProducts({ commit }: ActionContext<ProductState, RootState>, search = {}) {
    try {
      const response = await fetchProducts(search);
      commit('SET_PRODUCTS', response.data);
    } catch (error) {
      console.error('Failed to fetch products:', error);
    }
  },

  async addProduct({ commit }: ActionContext<ProductState, RootState>, product: Product) {
    try {
      const response = await createProduct(product);
      commit('ADD_PRODUCT', response.data);
    } catch (error) {
      console.error('Failed to add product:', error);
    }
  },

  async updateProduct({ commit }: ActionContext<ProductState, RootState>, product: Product) {
    try {
      const response = await updateProduct(product);
      commit('UPDATE_PRODUCT', response.data);
    } catch (error) {
      console.error('Failed to update product:', error);
    }
  },

  async deleteProduct({ commit }: ActionContext<ProductState, RootState>, productId: number) {
    try {
      await deleteProduct(productId);
      commit('DELETE_PRODUCT', productId);
    } catch (error) {
      console.error('Failed to delete product:', error);
    }
  }
};

// Định nghĩa getters
const getters: GetterTree<ProductState, RootState> = {
  allProducts: (state): Product[] => state.products
};

// Cấu hình module với namespace
const products: Module<ProductState, RootState> = {
  namespaced: true, // Bật namespace
  state,
  mutations,
  actions,
  getters
};

export default products;
