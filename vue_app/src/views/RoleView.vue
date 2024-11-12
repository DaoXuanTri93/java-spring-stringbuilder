<template>
  <div>
    <el-container>
      <el-header class="centered-header">ROLE MASTER</el-header>
      <el-container class="input-container">
        <div class="input-group">
          <span class="label">Role ID :</span>
          <el-input
            id="input1"
            v-model="search.roleId"
            :disabled="isRoleSelected"
          ></el-input>
        </div>
        <div class="input-group">
          <span class="label">Role Name :</span>
          <el-input
            id="input2"
            v-model="search.roleName"
            :disabled="isRoleSelected"
          ></el-input>
        </div>
      </el-container>
    </el-container>
    <el-button
      type="primary"
      class="search-button"
      @click="fetchRoles"
      :disabled="isRoleSelected"
    >
      SEARCH
    </el-button>
    <el-button
      type="primary"
      class="clear-button"
      @click="clearSearch"
      :disabled="isRoleSelected"
    >
      CLEAR
    </el-button>
    <el-container class="table-container">
      <el-header class="centered-header-form">Search Results</el-header>
      <el-table
        :data="paginatedRoles"
        border
        style="width: 100%; height: 530px"
        :header-cell-style="{ backgroundColor: '#3172c8', color: 'white' }"
      >
        <el-table-column
          header-align="center"
          prop="Role ID"
          label="Role ID"
          width="180"
        >
          <template #default="{ row }">
            <el-button @click="selectRole(row)" :disabled="isRoleSelected">
              {{ row.id }}
            </el-button>
          </template>
        </el-table-column>
        <el-table-column
          header-align="center"
          prop="roleName"
          label="Role Name"
        ></el-table-column>
        <el-table-column
          header-align="center"
          prop="description"
          label="Description"
        ></el-table-column>
        <el-table-column
          header-align="center"
          prop="createdBy"
          label="Created By"
        ></el-table-column>
        <el-table-column
          header-align="center"
          prop="createdDate"
          label="Created Date"
        ></el-table-column>
        <el-table-column
          header-align="center"
          prop="updatedBy"
          label="Update By"
        ></el-table-column>
        <el-table-column
          header-align="center"
          prop="updatedDate"
          label="Update Date"
        ></el-table-column>
      </el-table>

      <el-pagination
        v-if="roles.length > 0"
        background
        layout="prev, pager, next"
        :total="roles.length"
        :page-size="pageSize"
        @current-change="handlePageChange"
        style="display: flex; justify-content: flex-end; margin-top: 20px"
      />
    </el-container>

    <el-header class="centered-header-form">Input</el-header>
    <el-container class="input-container">
      <div class="input-group">
        <span class="label">Role Name<span class="required"> *</span>:</span>
        <el-input v-model="role.roleName"></el-input>
      </div>
      <div class="input-group">
        <span class="label" for="input4">Description :</span>
        <el-input
          id="input4"
          v-model="role.description"
          type="textarea"
          :autosize="{ minRows: 2, maxRows: 4 }"
        ></el-input>
      </div>
    </el-container>
    <el-button type="primary" class="search-button" @click="saveRole">
      SAVE
    </el-button>
    <el-button type="primary" class="search-button" @click="cancelRole">
      CANCEL
    </el-button>
  </div>
</template>

<script setup lang="ts">
import { useRoleFacade } from '@/facades/RoleFacade'

const {
  roles,
  role,
  initialRole,
  search,
  isRoleSelected,
  currentPage,
  pageSize,
  paginatedRoles,
  fetchRoles,
  handlePageChange,
  clearSearch,
  cancelRole,
  resetFields,
  selectRole,
  saveRole,
} = useRoleFacade()
</script>

<style scoped>
.centered-header {
  background-color: #3172c8;
  color: #f5f7fa;
  display: flex;
  justify-content: center;
  align-items: center;
  font-weight: bold;
  font-size: 1.2em;
  height: 30px;
}
.centered-header-form {
  background-color: #3172c8;
  color: #f5f7fa;
  display: flex;
  align-items: center;
  font-weight: bold;
  font-size: 1.2em;
  height: 30px;
}
.table-container {
  display: flex;
  justify-content: center;
  margin-top: 20px;
}
.input-container {
  display: flex;
  gap: 16px;
  justify-content: flex-start;
  align-items: center;
  height: 200px;
}
.input-group {
  display: flex;
  align-items: center;
  gap: 8px;
}
.label {
  width: 150px;
  display: inline-block;
  font-weight: bold;
  color: #333;
}
.required {
  color: red;
}
.el-table th,
.el-table td {
  text-align: center;
  padding: 8px;
}
.el-table .el-table__row:hover {
  background-color: #f5f7fa;
}
</style>
