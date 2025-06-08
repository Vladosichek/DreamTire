<template>
  <div class="schedule-page">
    <div class="navbar full-width">
      <button @click="$router.push('/master-schedule')" :class="{ active: $route.path === '/master-schedule' }">Графік роботи</button>
      <button @click="$router.push('/master-writeoff')" :class="{ active: $route.path === '/master-writeoff' }">Списання матеріалів</button>
      <button @click="logout">Вийти</button>
    </div>

    <div class="container-box">
      <h1>Сеанси майстра</h1>
      <form class="styled-form" @submit.prevent="updateSession">
        <div class="form-grid">
          <div>
            <label>Клієнт</label>
            <input :value="selectedClientName" placeholder="Клієнт" readonly />
          </div>
          <div>
            <label>Номер авто</label>
            <input :value="selectedCarNumber" placeholder="Номер авто" readonly />
          </div>
          <div>
            <label>Марка</label>
            <input :value="selectedCarMake" placeholder="Марка" readonly />
          </div>
          <div>
            <label>Модель</label>
            <input :value="selectedCarModel" placeholder="Модель" readonly />
          </div>
          <div>
            <label>Дата</label>
            <input v-model="form.date" type="date" placeholder="Дата" readonly />
          </div>
          <div>
            <label>Час</label>
            <select v-model="form.time" disabled>
              <option :value="form.time">{{ formatTime(form.time) }}</option>
            </select>
          </div>
          <div>
            <label>Тривалість</label>
            <input v-model="form.duration" type="number" placeholder="Тривалість" readonly />
          </div>
          <div>
            <label>Опис</label>
            <input v-model="form.description" placeholder="Опис" />
          </div>
        
          <div>
            <label>Статус</label>
            <select v-model="form.status" required>
              <option v-for="status in statuses" :key="status" :value="status">
                {{ status }}
              </option>
            </select>
          </div>
        </div>
        <div class="services-box">
          <h3>Послуги</h3>
          <div class="scroll-box">
            <div v-for="(service, index) in form.services" :key="index" class="service-item">
              <select v-model="service.serviceId" required>
                <option value="" disabled>Оберіть послугу</option>
                <option v-for="srv in filteredServices" :key="srv.id" :value="srv.id">
                  {{ srv.name }}
                </option>
              </select>
              <input type="number" v-model="service.quantity" placeholder="Кількість" required />
              <span>{{ getServiceNameById(service.serviceId) }}</span>
              <button type="button" @click="removeService(index)">Видалити</button>
            </div>
          </div>
          <button type="button" @click="addService" :disabled="!form.stationId">Додати послугу</button>
        </div>
        <div class="form-buttons">
          <button type="submit" :disabled="!selectedSessionId">Оновити</button>
          <button type="button" @click="clearForm">Очистити</button>
        </div>
      </form>

      <table class="session-table">
        <thead>
          <tr>
            <th>Дата</th>
            <th>Час</th>
            <th>Тривалість</th>
            <th>Статус</th>
          </tr>
        </thead>
        <tbody>
          <tr
            v-for="session in sessions"
            :key="session.id"
            :class="{ selected: selectedSessionId === session.id }"
            @click="selectSession(session)"
          >
            <td>{{ session.date }}</td>
            <td>{{ formatTime(session.time) }}</td>
            <td>{{ session.duration }} хв</td>
            <td>{{ session.status }}</td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  data() {
    return {
      sessions: [],
      statuses: [],
      allServices: [],

      form: {
        date: '',
        time: '',
        duration: '',
        cost: '',
        discount: '',
        description: '',
        customerId: '',
        masterId: '',
        carId: '',
        stationId: '',
        status: '',
        services: []
      },

      selectedSessionId: null,
      selectedClientName: '',
      selectedCarNumber: '',
      selectedCarMake: '',
      selectedCarModel: '',

      stationId: null,
      customers: [],
      cars: []
    }
  },
  computed: {
    filteredServices() {
      return this.allServices
    }
  },
  methods: {
    async loadCustomers() {
      const res = await axios.get('/api/users/role/CLIENT')
      this.customers = res.data
    },
    async loadCars() {
      const res = await axios.get('/api/cars')
      this.cars = res.data
    },
    async loadMasterInfoAndServices() {
      const userId = localStorage.getItem('userId')
      if (!userId) return

      const userRes = await axios.get(`/api/users/${userId}`)
      this.stationId = userRes.data.stationId

      const servicesRes = await axios.get(`/api/services/station/${this.stationId}`)
      this.allServices = servicesRes.data

      await this.loadSessionsByMaster()
    },
    async loadSessionsByMaster() {
      const userId = localStorage.getItem('userId')
      const res = await axios.get(`/api/sessions/master/${userId}`)
      this.sessions = res.data
    },
    async updateSession() {
      if (!this.selectedSessionId) return
      await axios.put(`/api/sessions/${this.selectedSessionId}`, { ...this.form })
      alert('Сеанс оновлено')
      this.clearForm()
      await this.loadSessionsByMaster()
    },
    clearForm() {
      this.selectedSessionId = null
      this.form = {
        date: '',
        time: '',
        duration: '',
        cost: '',
        discount: '',
        description: '',
        customerId: '',
        masterId: '',
        carId: '',
        stationId: '',
        status: '',
        services: []
      }
      this.selectedClientName = ''
      this.selectedCarNumber = ''
      this.selectedCarMake = ''
      this.selectedCarModel = ''
    },
    selectSession(session) {
      this.selectedSessionId = session.id
      this.form = {
        date: session.date,
        time: session.time,
        duration: session.duration,
        cost: session.cost ?? '',
        discount: session.discount ?? '',
        description: session.description ?? '',
        customerId: session.customerId ?? '',
        masterId: session.masterId ?? '',
        carId: session.carId ?? '',
        stationId: session.stationId ?? '',
        status: session.status,
        services: (session.services || []).map(s => ({
          serviceId: s.service?.id ?? s.serviceId,
          quantity: s.quantity
        }))
      }
      const customer = this.customers.find(c => c.id === session.customerId)
      this.selectedClientName = customer ? customer.name : '—'
      const car = this.cars.find(c => c.id === session.carId)
      this.selectedCarNumber = car ? car.number : '—'
      this.selectedCarMake = car ? car.make : '—'
      this.selectedCarModel = car ? car.model : '—'
    },
    addService() {
      this.form.services.push({ serviceId: '', quantity: 1 })
    },
    removeService(index) {
      this.form.services.splice(index, 1)
    },
    formatTime(time) {
      const h = Math.floor(time / 60).toString().padStart(2, '0')
      const m = (time % 60).toString().padStart(2, '0')
      return `${h}:${m}`
    },
    getServiceNameById(id) {
      const service = this.allServices.find(s => s.id === id)
      return service ? service.name : '—'
    },
    logout() {
      localStorage.removeItem('userId')
      localStorage.removeItem('role')
      this.$router.push('/')
    }
  },
  async mounted() {
    this.statuses = (await axios.get('/api/sessions/statuses')).data
    await this.loadCustomers()
    await this.loadCars()
    await this.loadMasterInfoAndServices()
  }
}
</script>

<style scoped>
@import './PagesStyle.css';
</style>