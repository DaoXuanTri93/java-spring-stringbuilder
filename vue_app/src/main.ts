// main.ts
import './assets/main.css'

import { createApp } from 'vue'
import App from './App.vue'
import router from './router/index'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import services from '@/services/apiService'
import config from './config'
const app = createApp(App)
app.use(ElementPlus)
app.use(router)

services.setBaseURL(config.apiBaseUrl)
app.mount('#app')
