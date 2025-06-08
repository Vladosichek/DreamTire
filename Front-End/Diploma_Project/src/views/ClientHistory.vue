<template>
  <div class="schedule-page">
    <div class="navbar full-width">
      <button @click="$router.push('/client-cars')" :class="{ active: $route.path === '/client-cars' }">Мої машини</button>
      <button @click="$router.push('/client-schedule')" :class="{ active: $route.path === '/client-schedule' }">Розклад</button>
      <button @click="$router.push('/client-booking')" :class="{ active: $route.path === '/client-booking' }">Бронювання сеансу</button>
      <button @click="$router.push('/client-history')" :class="{ active: $route.path === '/client-history' }">Історія</button>
      <button @click="logout">Вийти</button>
    </div>

    <div class="container-box">
      <h1>Сеанси обслуговування</h1>
      <form class="styled-form">
        <div class="form-grid">
          <div>
            <label>Дата</label>
            <input v-model="form.date" type="date" readonly />
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
            <label>Вартість</label>
            <input v-model="form.cost" type="number" step="0.01" placeholder="Вартість" readonly />
          </div>
          <div>
            <label>Знижка</label>
            <input v-model="form.discount" type="number" step="0.01" placeholder="Знижка" readonly />
          </div>
          <div>
            <label>Опис</label>
            <input v-model="form.description" placeholder="Опис" readonly />
          </div>
          <div>
            <label>Клієнт</label>
            <select v-model="form.customerId" disabled>
              <option v-for="user in clients" :key="user.id" :value="user.id">
                {{ user.name }}
              </option>
            </select>
          </div>
          <div>
            <label>Майстер</label>
            <select v-model="form.masterId" disabled>
              <option value="">Без майстра</option>
              <option v-for="user in filteredMasters" :key="user.id" :value="user.id">
                {{ user.name }}
              </option>
            </select>
          </div>
          <div>
            <label>Машина</label>
            <select v-model="form.carId" disabled>
              <option v-for="car in filteredCars" :key="car.id" :value="car.id">
                {{ car.number }}
              </option>
            </select>
          </div>
          <div>
            <label>Станція</label>
            <select v-model="form.stationId" disabled>
              <option v-for="station in stations" :key="station.id" :value="station.id">
                {{ station.name }}
              </option>
            </select>
          </div>
          <div>
            <label>Статус</label>
            <select v-model="form.status" disabled>
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
              <select v-model="service.serviceId" disabled>
                <option v-for="srv in filteredServices" :key="srv.id" :value="srv.id">
                  {{ srv.name }}
                </option>
              </select>
              <input type="number" v-model="service.quantity" readonly />
            </div>
          </div>
        </div>
        <div class="form-grid">
          <p><strong>Загальна тривалість:</strong> {{ totalDuration }} хв</p>
          <p><strong>Загальна вартість:</strong> {{ totalCost.toFixed(2) }} грн</p>
        </div>
        <div class="form-buttons">
          <button type="button" @click="deleteSession" :disabled="!selectedSessionId">Видалити</button>
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
            @click="selectSession(session)"
            :class="{ selected: selectedSessionId === session.id }"
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
      stations: [],
      clients: [],
      masters: [],
      cars: [],
      allServices: [],
      sessions: [],
      statuses: [],
      selectedSessionId: null,

      totalDuration: 0,
      totalCost: 0,

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
      }
    }
  },
  computed: {
    filteredCars() {
      if (!this.form.customerId) return []
      return this.cars.filter(car => car.userId === this.form.customerId)
    },
    filteredMasters() {
      if (!this.form.stationId) return []
      return this.masters.filter(master => master.stationId === this.form.stationId)
    },
    filteredServices() {
      return this.allServices.filter(s => s.stationId === this.form.stationId)
    }
  },
  methods: {
    async loadCustomerSessions() {
      const userId = localStorage.getItem('userId')
      if (!userId) return
      const response = await axios.get(`/api/sessions/customer/${userId}`)
      this.sessions = response.data
    },
    async deleteSession() {
      if (!this.selectedSessionId) {
        alert("Сеанс не вибрано для видалення")
        return
      }
      const selected = this.sessions.find(s => s.id === this.selectedSessionId)
      if (selected?.status !== 'BOOKED') {
        alert("Можна видалити лише сеанси зі статусом BOOKED")
        return
      }
      if (!confirm("Ви впевнені, що хочете видалити цей сеанс?")) return
      await axios.delete(`/api/sessions/${this.selectedSessionId}`)
      alert("Сеанс видалено")
      await this.clearForm()
      await this.loadCustomerSessions()
    },
    async clearForm() {
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
      this.totalCost = 0
      this.totalDuration = 0
    },
    formatTime(time) {
      const h = Math.floor(time / 60).toString().padStart(2, '0')
      const m = (time % 60).toString().padStart(2, '0')
      return `${h}:${m}`
    },
    selectSession(session) {
      this.selectedSessionId = session.id
      this.form = {
        date: session.date,
        time: session.time,
        duration: session.duration,
        cost: session.cost,
        discount: session.discount,
        description: session.description,
        customerId: session.customer?.id ?? session.customerId ?? null,
        masterId: session.master?.id ?? session.masterId ?? null,
        carId: session.car?.id ?? session.carId ?? null,
        stationId: session.station?.id ?? session.stationId ?? null,
        status: session.status,
        services: (session.services || []).map(s => ({
          serviceId: s.service?.id ?? s.serviceId,
          quantity: s.quantity
        }))
      }
      this.recalculate()
    },
    recalculate() {
      let dur = 0
      let cost = 0
      for (const s of this.form.services) {
        const full = this.allServices.find(e => e.id === s.serviceId)
        if (full) {
          dur += full.duration * s.quantity
          cost += full.cost * s.quantity
        }
      }
      this.totalDuration = dur
      this.totalCost = cost
    },
    logout() {
      localStorage.removeItem('userId')
      localStorage.removeItem('role')
      this.$router.push('/')
    }
  },
  async mounted() {
    this.stations = (await axios.get('/api/stations')).data
    this.clients = (await axios.get('/api/users/role/CLIENT')).data
    this.masters = (await axios.get('/api/users/role/MASTER')).data
    this.cars = (await axios.get('/api/cars')).data
    this.allServices = (await axios.get('/api/services')).data
    this.statuses = (await axios.get('/api/sessions/statuses')).data
    await this.loadCustomerSessions()
  }
}
</script>

<style scoped>
@import './PagesStyle.css';
</style>