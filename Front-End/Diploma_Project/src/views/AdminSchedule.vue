<template>
  <div class="schedule-page">
    <div class="navbar full-width">
      <button @click="$router.push('/admin-stations')" :class="{ active: $route.path === '/admin-stations' }">Станції</button>
      <button @click="$router.push('/admin-user')" :class="{ active: $route.path === '/admin-user' }">Користувачі</button>
      <button @click="$router.push('/admin-services')" :class="{ active: $route.path === '/admin-services' }">Послуги</button>
      <button @click="$router.push('/admin-storage')" :class="{ active: $route.path === '/admin-storage' }">Склад</button>
      <button @click="$router.push('/admin-schedule')" :class="{ active: $route.path === '/admin-schedule' }">Графік роботи</button>
      <button @click="$router.push('/admin-messages')" :class="{ active: $route.path === '/admin-messages' }">Повідомлення</button>
      <button @click="$router.push('/admin-statistic')" :class="{ active: $route.path === '/admin-statistic' }">Статистика</button>
      <button @click="logout">Вийти</button>
    </div>

    <div class="container-box">
      <h1>Сеанси обслуговування</h1>
      <form @submit.prevent="createSession" class="styled-form">
        <div class="form-grid">
          <div>
            <label>Дата</label>
            <input v-model="form.date" type="date" required />
          </div>
          <div>
            <label>Час</label>
            <select v-model="form.time" required>
              <option value="" disabled>Оберіть час</option>
              <option v-for="time in availableTimes" :key="time" :value="time">
                {{ formatTime(time) }}
              </option>
            </select>
          </div>
          <div>
            <label>Тривалість</label>
            <input v-model="form.duration" type="number" placeholder="Тривалість" required readonly />
          </div>
          <div>
            <label>Вартість</label>
            <input v-model="form.cost" type="number" step="0.01" placeholder="Вартість" required readonly />
          </div>
          <div>
            <label>Знижка</label>
            <input v-model="form.discount" type="number" step="0.01" placeholder="Знижка" />
          </div>
          <div>
            <label>Опис</label>
            <input v-model="form.description" placeholder="Опис" />
          </div>
          <div>
            <label>Клієнт</label>
            <select v-model="form.customerId" required>
              <option value="" disabled>Оберіть клієнта</option>
              <option v-for="user in clients" :key="user.id" :value="user.id">
                {{ user.name }}
              </option>
            </select>
          </div>
          <div>
            <label>Майстер</label>
            <select v-model="form.masterId">
              <option value="">Без майстра</option>
              <option v-for="user in filteredMasters" :key="user.id" :value="user.id">
                {{ user.name }}
              </option>
            </select>
          </div>
          <div>
            <label>Автомобіль</label>
            <select v-model="form.carId" required>
              <option value="" disabled>Оберіть авто</option>
              <option v-for="car in filteredCars" :key="car.id" :value="car.id">
                {{ car.number }}
              </option>
            </select>
          </div>
          <div>
            <label>Станція</label>
            <select v-model="form.stationId" required>
              <option value="" disabled>Оберіть станцію</option>
              <option v-for="station in stations" :key="station.id" :value="station.id">
                {{ station.name }}
              </option>
            </select>
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
              <button type="button" @click="removeService(index)">❌</button>
            </div>
          </div>
          <button type="button" @click="addService">➕ Додати послугу</button>
        </div>
        <div>
          <p><strong>Загальна тривалість:</strong> {{ totalDuration }} хв</p>
          <p><strong>Загальна вартість:</strong> {{ totalCost.toFixed(2) }} грн</p>
        </div>
        <div class="form-buttons">
          <button type="submit">Створити</button>
          <button type="button" @click="updateSession" :disabled="!selectedSessionId">Оновити</button>
          <button type="button" @click="deleteSession" :disabled="!selectedSessionId">Видалити</button>
          <button type="button" @click="clearForm">Очистити</button>
        </div>
      </form>

      <form @submit.prevent="searchSessions" class="search-form">
        <input v-model="searchDate" type="date" required />
        <select v-model="searchStation" required>
            <option value="" disabled>Оберіть станцію</option>
            <option v-for="station in stations" :key="station.id" :value="station.name">
              {{ station.name }}
            </option>
          </select>
        <button type="submit">Пошук</button>
        <button type="button" @click="resetSearch">Скинути</button>
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
          <tr v-for="session in sessions" :key="session.id" :class="{ selected: session.id === selectedSessionId }" @click="selectSession(session)">
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
      searchDate: '',
      searchStation: '',

      totalDuration: 0,
      totalCost: 0,
      availableTimes: [],

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
  watch: {
    'form.services': {
      handler() {
        this.recalculate()
      },
      deep: true
    },
    'form.date': 'recalculate',
    'form.stationId': 'recalculate'
  },
  methods: {
    async loadAllData() {
      this.stations = (await axios.get('/api/stations')).data
      this.clients = (await axios.get('/api/users/role/CLIENT')).data
      this.masters = (await axios.get('/api/users/role/MASTER')).data
      this.cars = (await axios.get('/api/cars')).data
      this.allServices = (await axios.get('/api/services')).data
      this.statuses = (await axios.get('/api/sessions/statuses')).data
      await this.loadAllSessions()
    },
    async loadAllSessions() {
      this.sessions = (await axios.get('/api/sessions')).data
    },
    async searchSessions() {
      if (!this.searchDate && !this.searchStation) {
        await this.loadAllSessions()
        return
      }
      const response = await axios.get('/api/sessions/by-date-and-station', {
        params: {
          date: this.searchDate || undefined,
          stationName: this.searchStation || undefined
        }
      })
      this.sessions = response.data
    },
    async createSession() {
      try {
        const response = await axios.post('/api/sessions', this.form)
        if (response?.data?.id) {
          alert('Сеанс створено успішно')
          await this.clearForm()
          await this.loadAllSessions()
        } else {
          alert('Не вдалося створити сеанс')
        }
      } catch (error) {
        console.error('Помилка при створенні сеансу:', error)
        alert('Виникла помилка при створенні сеансу')
      }
    },
    async updateSession() {
      if (!this.selectedSessionId) {
        alert("Сеанс не вибрано для оновлення")
        return
      }
      await axios.put(`/api/sessions/${this.selectedSessionId}`, this.form)
      alert("Сеанс оновлено")
      await this.clearForm()
      await this.loadAllSessions()
    },
    async deleteSession() {
      if (!this.selectedSessionId) {
        alert("Сеанс не вибрано для видалення")
        return
      }
      if (!confirm("Ви впевнені, що хочете видалити цей сеанс?")) return
      await axios.delete(`/api/sessions/${this.selectedSessionId}`)
      alert("Сеанс видалено")
      await this.clearForm()
      await this.loadAllSessions()
    },
    resetSearch() {
      this.searchDate = '';
      this.searchStation = '';
      this.loadAllSessions();
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
      this.availableTimes = []
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
      this.form.duration = dur
      this.form.cost = cost
      this.loadAvailableTimes()
    },
    async loadAvailableTimes() {
      if (!this.form.stationId || !this.form.date || this.totalDuration === 0) return
      const payload = {
        stationId: this.form.stationId,
        date: this.form.date,
        services: this.form.services.map(s => ({
          serviceId: s.serviceId,
          quantity: s.quantity
        }))
      }
      const response = await axios.post('/api/bookings/available-times', payload)
      this.availableTimes = response.data
    },
    logout() {
      localStorage.removeItem('userId')
      localStorage.removeItem('role')
      this.$router.push('/')
    }
  },
  mounted() {
    this.loadAllData()
  }
}
</script>

<style scoped>
@import './PagesStyle.css';
</style>