import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import axios from 'axios'

axios.defaults.baseURL = import.meta.env.VITE_API_BASE_URL

createApp(App).use(router).mount('#app')
