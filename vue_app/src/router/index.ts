import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import UsersView from '../views/UsersView.vue'
import ProductMaster from '../views/ProductView.vue'
import RoleView from '../views/RoleView.vue'
import TeamsView from '../views/TeamsView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView,
    },
    {
      path: '/about',
      name: 'about',
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import('../views/AboutView.vue'),
    },
    {
      path: '/user-master',
      name: 'users',
      component: UsersView,
    },
    {
      path: '/product-master',
      name: 'product',
      component: ProductMaster,
    },
    {
      path: '/role-master',
      name: 'role',
      component: RoleView,
    },
    {
      path: '/team-master',
      name: 'team',
      component: TeamsView,
    },
  ],
})

export default router
