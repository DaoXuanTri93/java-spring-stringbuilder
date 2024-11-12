// src/facades/ProductFacade.ts
import type { Product, Search } from '@/store/Types.ts'
import { ref } from 'vue'
import myService from '@/services/apiService'

export const useProductFacade = () => {
  const search = ref({ id: '', name: '' })
  const form = ref({ id: 0, name: '', price: 0 })
  const listData = ref<Product[]>([])
  const editingStatus = ref(false)

  return {
    form,
    search,
    listData,
    editingStatus,

    searchProducts: async (searchParam: Search) => {
      try {
        const response = await myService.getAll('products', searchParam)
        return response // type ApiResponse
      } catch (error) {
        console.error('Failed to load products:', error)
        return { data: [], message: 'Error loading products' } // ApiResponse lỗi
      }
    },

    addProduct: async (Product: Product) => {
      try {
        return (await myService.create('products', Product)).data
      } catch (error) {
        console.error('Failed to load products:', error)
      }
    },
    updateProduct: async (Product: Product) => {
      try {
        return (await myService.update('products', Product.id, Product)).data
      } catch (error) {
        console.error('Failed to load products:', error)
      }
    },
    deleteProduct: async (ProductId: number) => {
      try {
        return (await myService.delete('products', ProductId)).data
      } catch (error) {
        console.error('Failed to load products:', error)
      }
    },

    // using state
    // searchProduct: async (search: Search) =>
    //   store.dispatch('products/searchProduct', search),

    // addProduct: (product: Product) =>
    //   store.dispatch('products/addProduct', product),

    // updateProduct: (product: Product) =>
    //   store.dispatch('products/updateProduct', product),

    // deleteProduct: (productId: number) =>
    //   store.dispatch('products/deleteProduct', productId),
  }
}
