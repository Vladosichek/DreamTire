import { createRouter, createWebHistory } from 'vue-router'
import Home from '../views/Home.vue'
import Register from '../views/Register.vue'
import Login from '../views/Login.vue'

import AdminMessages from '@/views/AdminMessages.vue'
import AdminSchedule from '@/views/AdminSchedule.vue'
import AdminServices from '@/views/AdminServices.vue'
import AdminStations from '@/views/AdminStations.vue'
import AdminStatistic from '@/views/AdminStatistic.vue'
import AdminStorage from '@/views/AdminStorage.vue'
import AdminUser from '@/views/AdminUser.vue'

import ClientBooking from '@/views/ClientBooking.vue'
import ClientCars from '@/views/ClientCars.vue'
import ClientHistory from '@/views/ClientHistory.vue'
import ClientSchedule from '@/views/ClientSchedule.vue'

import MasterSchedule from '@/views/MasterSchedule.vue'
import MasterWriteoff from '@/views/MasterWriteoff.vue'

const routes = [
  { path: '/', component: Home },
  { path: '/register', component: Register },
  { path: '/login', component: Login },

  { path: '/admin-messages', component: AdminMessages, meta: { requiresAuth: true, role: 'ADMIN' } },
  { path: '/admin-schedule', component: AdminSchedule, meta: { requiresAuth: true, role: 'ADMIN' } },
  { path: '/admin-services', component: AdminServices, meta: { requiresAuth: true, role: 'ADMIN' } },
  { path: '/admin-stations', component: AdminStations, meta: { requiresAuth: true, role: 'ADMIN' } },
  { path: '/admin-statistic', component: AdminStatistic, meta: { requiresAuth: true, role: 'ADMIN' } },
  { path: '/admin-storage', component: AdminStorage, meta: { requiresAuth: true, role: 'ADMIN' } },
  { path: '/admin-user', component: AdminUser, meta: { requiresAuth: true, role: 'ADMIN' } },

  { path: '/client-booking', component: ClientBooking, meta: { requiresAuth: true, role: 'CLIENT' } },
  { path: '/client-cars', component: ClientCars, meta: { requiresAuth: true, role: 'CLIENT' } },
  { path: '/client-history', component: ClientHistory, meta: { requiresAuth: true, role: 'CLIENT' } },
  { path: '/client-schedule', component: ClientSchedule, meta: { requiresAuth: true, role: 'CLIENT' } },

  { path: '/master-schedule', component: MasterSchedule, meta: { requiresAuth: true, role: 'MASTER' } },
  { path: '/master-writeoff', component: MasterWriteoff, meta: { requiresAuth: true, role: 'MASTER' } },
]

const router = createRouter({
  history: createWebHistory(),
  routes,
})

router.beforeEach((to, from, next) => {
  const userId = localStorage.getItem('userId')
  const role = localStorage.getItem('role')

  if (to.meta.requiresAuth) {
    if (!userId) {
      next('/') 
    } else if (to.meta.role && to.meta.role !== role) {
      next('/')
    } else {
      next()
    }
  } else {
    next()
  }
})

export default router
