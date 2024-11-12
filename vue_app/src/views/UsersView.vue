<template>
  <div>
    <div class="text-center bg-primary py-2 mb-5">
      <el-text size="large" class="text-white">USER MASTER</el-text>
    </div>
    <el-row class="mb-5">
      <el-col :span="2">
        <BaseLabel text="UserID:" for="userId"></BaseLabel>
      </el-col>
      <el-col :span="4">
        <BaseTextBox v-model="userIdSearchTerm" id="userId"></BaseTextBox>
      </el-col>
      <el-col :span="2">
        <BaseLabel text="UserName:" for="userName"></BaseLabel>
      </el-col>
      <el-col :span="4">
        <BaseTextBox v-model="userNameSearchTerm" id="userName"></BaseTextBox>
      </el-col>
      <el-col :span="2">
        <BaseLabel text="Team:" for="teamId"></BaseLabel>
      </el-col>
      <el-col :span="4">
        <BaseDropdown
          :items="teams"
          v-model="selectedTeamId"
          id="teamId"
        ></BaseDropdown>
      </el-col>
      <el-col :span="2">
        <BaseLabel text="Role:" for="roleId"></BaseLabel>
      </el-col>
      <el-col :span="4">
        <BaseDropdown
          :items="roles"
          v-model="selectedRoleId"
          id="roleId"
        ></BaseDropdown>
      </el-col>
    </el-row>
    <div class="mb-5">
      <el-button
        @click="fetchUsers()"
        type="primary"
        style="--el-color-primary: rgb(49, 114, 200)"
        >Search</el-button
      >
    </div>
    <div class="bg-primary py-2 mb-5">
      <el-text size="large" class="text-white">Search Results</el-text>
    </div>
    <div class="mb-3">
      <el-table
        :data="users"
        style="
          width: 100%;
          --el-table-header-bg-color: rgb(49, 114, 200);
          --el-table-header-text-color: white;
        "
        border
      >
        <el-table-column
          v-for="(column, index) in columns"
          :key="index"
          :prop="column.prop"
          :label="column.label"
          :header-align="column.align || 'center'"
          :align="column.align || 'left'"
        >
          <template #default="scope">
            <el-link
              v-if="column.prop === 'userName'"
              type="primary"
              @click="handleEdit(scope.row)"
            >
              {{ scope.row.userName }}
            </el-link>
            <span v-else>
              {{ scope.row[column.prop] }}
            </span>
          </template>
        </el-table-column>
        <el-table-column label="" header-align="center" align="center">
          <template #default="scope">
            <el-button
              type="danger"
              size="small"
              @click="deleteUser(scope.row.id)"
              :icon="Delete"
              circle
            ></el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div class="mb-5">
      <BasePagination
        :currentPage="currentPage"
        :pageSize="pageSize"
        :totalElements="totalElements"
        @update:currentPage="handlePageChange"
      ></BasePagination>
    </div>
    <div class="bg-primary py-2 mb-5">
      <el-text size="large" class="text-white">Input</el-text>
    </div>
    <div class="mb-5">
      <el-form
        label-width="120px"
        ref="userFormRef"
        label-position="left"
        :model="userForm"
        :rules="reactiveRules"
        status-icon
        require-asterisk-position="right"
      >
        <el-row :gutter="30">
          <el-col :span="8">
            <el-row class="mb-3">
              <el-form-item label="User Name:" prop="userName">
                <BaseTextBox v-model="userForm.userName"></BaseTextBox>
              </el-form-item>
            </el-row>
            <el-row class="mb-3">
              <el-form-item label="Full Name:" prop="name">
                <BaseTextBox v-model="userForm.name"></BaseTextBox
              ></el-form-item>
            </el-row>
          </el-col>
          <el-col :span="8">
            <el-row class="mb-3">
              <el-form-item label="Team:" prop="teamId">
                <BaseDropdown
                  :items="teams"
                  v-model="userForm.teamId"
                ></BaseDropdown>
              </el-form-item>
            </el-row>
            <el-row class="mb-3">
              <el-form-item label="Role:" prop="roleId">
                <BaseDropdown
                  :items="roles"
                  v-model="userForm.roleId"
                ></BaseDropdown>
              </el-form-item>
            </el-row>
          </el-col>
          <el-col :span="8">
            <el-space direction="horizontal" alignment="flex-start">
              <el-form-item label="Description:" prop="description">
                <BaseTextBox
                  v-model="userForm.description"
                  :isTextArea="true"
                  :rows="4"
                ></BaseTextBox>
              </el-form-item>
            </el-space>
          </el-col>
        </el-row>
        <el-button
          type="primary"
          style="--el-color-primary: rgb(49, 114, 200)"
          @click="submitForm(userFormRef)"
          >Save</el-button
        >
      </el-form>
    </div>
  </div>
</template>
<script setup lang="ts">
import { Delete } from '@element-plus/icons-vue'
import { onMounted } from 'vue'
import BaseLabel from '../components/BaseLabel.vue'
import BaseDropdown from '@/components/BaseDropdown.vue'
import BaseTextBox from '@/components/BaseTextBox.vue'
import BasePagination from '@/components/BasePagination.vue'
import { useUserFacade } from '@/facades/UserFacade'

const {
  submitForm,
  fetchRolesAndTeams,
  fetchUsers,
  userFormRef,
  columns,
  users,
  teams,
  roles,
  currentPage,
  totalElements,
  pageSize,
  reactiveRules,
  userNameSearchTerm,
  userIdSearchTerm,
  selectedRoleId,
  selectedTeamId,
  userForm,
  handleEdit,
  deleteUser,
  handlePageChange,
} = useUserFacade()

onMounted(() => {
  fetchUsers()
  fetchRolesAndTeams()
})
</script>
<style scoped>
.text-right {
  text-align: right;
}

.text-center {
  text-align: center;
}

.bg-primary {
  background: rgb(49, 114, 200);
}

.text-white {
  color: white !important;
}

.py-2 {
  padding: 0.5rem;
}

.mb-3 {
  margin-bottom: 1rem;
}

.mb-5 {
  margin-bottom: 1.5rem;
}

.text-danger {
  color: red;
}

.justify-content-end {
  justify-content: flex-end !important;
}

a:hover {
  background: transparent;
}
</style>
