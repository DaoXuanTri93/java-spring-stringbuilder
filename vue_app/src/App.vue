<script setup lang="ts">
import { RouterLink, RouterView } from 'vue-router'
</script>

<template>
  <div class="layout">
    <header class="fixed-header">
      <nav class="sidebar">
        <RouterLink to="/">Home</RouterLink>
        <RouterLink to="/user-master">User Master</RouterLink>
        <RouterLink to="/product-master">Product Master</RouterLink>
        <RouterLink to="/team-master">Team Master</RouterLink>
        <RouterLink to="/role-master">Role Master</RouterLink>
        <RouterLink to="/about">About</RouterLink>
      </nav>
    </header>
    <div class="content">
      <RouterView />
    </div>
  </div>
</template>

<style scoped>
/* Reset padding và margin cho toàn bộ trang */
html,
body {
  margin: 0;
  padding: 0;
  width: 100%;
  height: 100%;
}

* {
  box-sizing: border-box;
}

.fixed-header {
  position: fixed;
  top: 0;
  left: 0;
  height: 100vh;
  width: 15%; /* Chiều rộng menu bên trái */
  background-color: #f8f9fa;
  padding: 1rem;
  box-shadow: 4px 0 8px rgba(0, 0, 0, 0.1);
  display: flex;
  flex-direction: column;
}

.sidebar a {
  display: block;
  padding: 0.5rem 0;
  color: #333;
  text-decoration: none;
  font-weight: bold;
}

.sidebar a.router-link-exact-active {
  color: #007bff;
}

.content {
  margin-left: 15%; /* Chừa khoảng trống bằng chiều rộng của sidebar */
  padding: 1rem;
  width: 85%; /* Đảm bảo content full chiều rộng trừ đi sidebar */
  overflow-y: auto;
}
</style>
