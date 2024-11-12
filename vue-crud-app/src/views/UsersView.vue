<template>
  <div>
    <h1>Danh sách người dùng</h1>
    <div class="text-right">
      <el-button plain @click="showCreateDialog"
        ><span>Thêm mới người dùng</span></el-button
      >
    </div>
    <el-table :data="users" style="width: 100%; height: 350px">
      <el-table-column prop="id" label="ID"></el-table-column>
      <el-table-column prop="name" label="Tên"></el-table-column>
      <el-table-column prop="email" label="Email"></el-table-column>
      <el-table-column label="Hành động">
        <template #default="scope">
          <el-button
            size="small"
            @click="showEditDialog(scope.row)"
            :icon="Edit"
            circle
          ></el-button>
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
    <el-dialog v-model="isDialogVisible" title="Thông tin người dùng">
      <el-form :model="user">
        <el-form-item label="Tên">
          <el-input v-model="user.name"></el-input>
        </el-form-item>
        <el-form-item label="Email">
          <el-input v-model="user.email"></el-input>
        </el-form-item>
      </el-form>
      <div class="text-right">
        <el-button @click="isDialogVisible = false">Huỷ</el-button>
        <el-button type="primary" @click="saveUser">{{
          isEditMode ? "Cập nhật" : "Thêm"
        }}</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script lang="ts" setup>
import { Delete, Edit } from "@element-plus/icons-vue";
</script>

<script lang="ts">
interface User {
  id?: string | null;
  name: string;
  email: string;
}

import { defineComponent } from "vue";
import axios from "axios";

export default defineComponent({
  name: "UsersView",
  data() {
    return {
      header: "Thêm người dùng",
      rawHtml: `<span style="color: red">This should be red.</span>`,
      users: [] as User[],
      user: {} as User,
      isEditMode: false,
      isDialogVisible: false,
    };
  },
  methods: {
    fetchUsers() {
      axios.get("http://localhost:8081/api/users").then((response) => {
        this.users = response.data;
      });
    },
    showCreateDialog() {
      this.isEditMode = false;
      this.user = { id: null, name: "", email: "" };
      this.isDialogVisible = true;
    },
    showEditDialog(user: User) {
      this.isEditMode = true;
      this.user = { ...user };
      this.isDialogVisible = true;
    },
    saveUser() {
      if (this.isEditMode) {
        axios
          .put(`http://localhost:8081/api/users/${this.user.id}`, this.user)
          .then(() => {
            this.fetchUsers();
            this.isDialogVisible = false;
          });
      } else {
        axios
          .post("http://localhost:8081/api/users", this.user, {
            headers: {
              "Content-Type": "application/json",
            },
          })
          .then(() => {
            this.fetchUsers();
            this.isDialogVisible = false;
          });
      }
    },
    deleteUser(id: string) {
      axios.delete(`http://localhost:8081/api/users/${id}`).then(() => {
        this.fetchUsers();
      });
    },
  },
  mounted() {
    this.fetchUsers();
  },
});
</script>

<style scoped>
.text-right {
  text-align: right;
}
</style>
