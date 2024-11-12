<template>
  <div class="full_area">
    <!-- Vùng tìm kiếm -->
    <div class="search-area">
      <h2>Product Management</h2>
      <el-input
        v-model="search.id"
        type="number"
        placeholder="Product ID"
        style="width: 200px; margin-right: 10px"
      />
      <el-input
        v-model="search.name"
        placeholder="Product Name"
        style="width: 200px; margin-right: 10px"
      />
      <el-button @click="loadAndSetProducts">Search</el-button>
    </div>

    <!-- Vùng kết quả -->
    <div class="results-area">
      <el-table :data="listData" border>
        <el-table-column
          label="Product ID"
          header-cell-class-name="custom-header"
        >
          <template #default="{ row }">
            <a
              href="#"
              @click="editProduct(row)"
              style="color: blue; text-decoration: underline"
            >
              {{ row.id }}
            </a>
          </template>
        </el-table-column>
        <el-table-column
          prop="name"
          label="Product Name"
          header-cell-class-name="custom-header"
        />
        <el-table-column
          prop="price"
          label="Price"
          header-cell-class-name="custom-header"
        />
      </el-table>
    </div>

    <h3>Input</h3>
    <!-- Vùng nhập thông tin sản phẩm -->
    <el-form @submit.prevent="handleFormSubmit" class="form-area">
      <div class="form-row">
        <el-form-item label="Product ID:">
          <el-input v-model="form.id" disabled readonly />
        </el-form-item>

        <el-form-item label="Product Name:" required>
          <el-input v-model="form.name" placeholder="Enter product name" />
        </el-form-item>

        <el-form-item label="Price:" required>
          <el-input v-model="form.price" placeholder="Enter price" />
        </el-form-item>
      </div>

      <el-form-item>
        <el-button type="primary" native-type="submit">{{
          editingStatus ? 'Update' : 'Save'
        }}</el-button>
        <el-button type="default" @click="resetForm">Cancel</el-button>

        <el-button
          @click="removeProduct(form.id)"
          type="danger"
          :disabled="form.id === null"
        >
          Delete
        </el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script setup lang="ts">
import { onMounted } from 'vue'
import { useProductFacade } from '@/facades/ProductFacade'
import type { Product } from '@/store/Types'

const {
  form,
  search,
  listData,
  editingStatus,
  searchProducts,
  addProduct,
  updateProduct,
  deleteProduct,
} = useProductFacade()

const loadAndSetProducts = async () => {
  const result = await searchProducts(search.value)
  listData.value = result.data
  if (result.message) alert(result.message)
}

// lấy lúc init
onMounted(loadAndSetProducts)

const handleFormSubmit = async () => {
  try {
    if (editingStatus.value) {
      await updateProduct(form.value)
    } else {
      await addProduct(form.value)
    }

    await loadAndSetProducts()
    resetForm()
  } catch (error) {
    console.error('Error submitting form:', error)
    alert('Có lỗi xảy ra. Vui lòng thử lại.')
  }
}

const removeProduct = async (productId: number | string) => {
  if (productId === null) {
    console.error('Product ID cannot be null')
    return
  }

  try {
    await deleteProduct(productId)
    await loadAndSetProducts()
    resetForm()
  } catch (error) {
    console.error('Error removing product:', error)
    // Handle the error accordingly, maybe show a message to the user
  }
}

const resetForm = () => {
  form.value = { id: 0, name: '', price: 0 }
  editingStatus.value = false
}

const editProduct = (product: Product) => {
  form.value = { ...product }
  editingStatus.value = true
}
</script>

<style></style>
