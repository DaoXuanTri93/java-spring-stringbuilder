<template>
  <div>
    <h2>Product Management</h2>

    <!-- Tìm kiếm sản phẩm -->
    <div class="search-bar">
      <input v-model="search.id" placeholder="Product ID" />
      <input v-model="search.name" placeholder="Product Name" />
      <button @click="loadProducts">Search</button>
    </div>

    <!-- Bảng hiển thị sản phẩm -->
    <table>
      <thead>
        <tr>
          <th>Product ID</th>
          <th>Product Name</th>
          <th>Price</th>
          <th>Actions</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="product in products" :key="product.id">
          <td>{{ product.id }}</td>
          <td>{{ product.name }}</td>
          <td>{{ product.price }}</td>
          <td>
            <button @click="editProduct(product)">Edit</button>
            <button @click="removeProduct(product.id)">Delete</button>
          </td>
        </tr>
      </tbody>
    </table>

    <!-- Form nhập liệu -->
    <h3>{{ editingProduct ? 'Edit Product' : 'Add Product' }}</h3>
    <form @submit.prevent="handleFormSubmit">
      <label>Product Name:</label>
      <input v-model="form.name" required />

      <label>Price:</label>
      <input type="number" v-model="form.price" required />

      <button type="submit">{{ editingProduct ? 'Update' : 'Save' }}</button>
      <button type="button" @click="resetForm">Cancel</button>
    </form>
  </div>
</template>

<script>
import { mapActions, mapGetters } from 'vuex';

export default {
  data() {
    return {
      search: {
        id: '',
        name: ''
      },
      form: {
        id: null,
        name: '',
        price: 0
      },
      editingProduct: false
    };
  },
  computed: {
    ...mapGetters('products', ['allProducts']),
    products() {
      return this.allProducts;
    }
  },
  methods: {
    ...mapActions('products', ['fetchProducts', 'addProduct', 'updateProduct', 'deleteProduct']),
    loadProducts() {
      this.fetchProducts(this.search);
    },
    editProduct(product) {
      this.form = { ...product };
      this.editingProduct = true;
    },
    removeProduct(productId) {
      this.deleteProduct(productId).then(() => {
        this.fetchProducts(this.search);
      });
    },
    handleFormSubmit() {
      if (this.editingProduct) {
        this.updateProduct(this.form).then(() => {
          this.fetchProducts(this.search);
          this.resetForm();
        });
      } else {
        this.addProduct(this.form).then(() => {
          this.fetchProducts(this.search);
          this.resetForm();
        });
      }
    },
    resetForm() {
      this.form = { id: null, productName: '', price: 0 };
      this.editingProduct = false;
    }
  },
  created() {
    this.fetchProducts();
  }
};
</script>
