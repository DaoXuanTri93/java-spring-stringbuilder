import { createRouter, createWebHashHistory, RouteRecordRaw } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import UsersView from '../views/UsersView.vue'
import ProductMaster from '../views/ProductView.vue'
import TeamsView from '../views/TeamsView.vue'
const routes: Array<RouteRecordRaw> = [
  {
    path: '/',
    name: 'home',
    component: HomeView
  },  
  {
    path: '/user-master',
    name: 'users',
    component: UsersView
  },
  {
    path: '/product-master',
    name: 'product',
    component: ProductMaster
  },
  {
    path: '/teams-master',
    name: 'teams',
    component: TeamsView
  }
]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

export default router
