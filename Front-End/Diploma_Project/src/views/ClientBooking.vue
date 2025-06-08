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
      <h1>Бронювання сеансу</h1>
      <form @submit.prevent="bookSession" class="styled-form">
        <div class="form-grid">
          <div>
            <label>Станція</label>
            <select v-model="form.stationId" required @change="loadServices">
              <option disabled value="">Оберіть станцію</option>
              <option v-for="station in stations" :key="station.id" :value="station.id">
                {{ station.name }}
              </option>
            </select>
          </div>
          <div>
            <label>Дата</label>
            <input v-model="form.date" type="date" required @change="loadAvailableTimes" />
          </div>
          <div>
            <label>Авто</label>
            <select v-model="form.carId" required>
              <option disabled value="">Оберіть авто</option>
              <option v-for="car in cars" :key="car.id" :value="car.id">
                {{ car.number }}
              </option>
            </select>
          </div>
          <div>
            <label>Опис</label>
            <input v-model="form.description" placeholder="Опис (необов’язково)" />
          </div>
        </div>
        <div class="services-box">
          <h3>Послуги</h3>
          <div class="scroll-box">
            <div v-for="(service, index) in form.services" :key="index" class="service-item">
              <select v-model="service.serviceId" required @change="recalculate">
                <option value="" disabled>Оберіть послугу</option>
                <option v-for="srv in filteredServices" :key="srv.id" :value="srv.id">
                  {{ srv.name }}
                </option>
              </select>
              <input
                type="number"
                v-model.number="service.quantity"
                min="1"
                placeholder="К-сть"
                required
                @input="recalculate"
              />
              <button type="button" @click="removeService(index)">Видалити</button>
            </div>
          </div>
          <button type="button" @click="addService">Додати послугу</button>
        </div>
        <div class="form-grid">
          <p><strong>Загальна тривалість:</strong> {{ totalDuration }} хв</p>
          <p><strong>Загальна вартість:</strong> {{ totalCost.toFixed(2) }} грн</p>
        </div>
        <div class="form-grid">
          <div>
            <label>Час</label>
            <select v-model="form.time" required>
              <option value="" disabled>Оберіть час</option>
              <option v-for="slot in availableTimes" :key="slot" :value="slot">
                {{ formatTime(slot) }}
              </option>
            </select>
          </div>
        </div>
        <div class="form-buttons">
          <button type="submit">Забронювати</button>
        </div>
      </form>
    </div>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  data() {
    return {
      form: {
        stationId: '',
        carId: '',
        date: '',
        time: '',
        description: '',
        services: []
      },
      stations: [],
      cars: [],
      allServices: [],
      availableTimes: [],
      totalDuration: 0,
      totalCost: 0
    }
  },
  computed: {
    filteredServices() {
      return this.allServices.filter(
        s => s.stationId === Number(this.form.stationId)
      )
    }
  },
  methods: {
    async loadServices() {
      if (!this.form.stationId) return
      this.allServices = (await axios.get('/api/services')).data
      this.recalculate()
    },
    async loadAvailableTimes() {
      if (
        !this.form.stationId ||
        !this.form.date ||
        this.totalDuration === 0
      )
        return

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
    addService() {
      this.form.services.push({ serviceId: '', quantity: 1 })
    },
    removeService(index) {
      this.form.services.splice(index, 1)
      this.recalculate()
    },
    formatTime(min) {
      const h = String(Math.floor(min / 60)).padStart(2, '0')
      const m = String(min % 60).padStart(2, '0')
      return `${h}:${m}`
    },
    async bookSession() {
      const userId = localStorage.getItem('userId')
      if (!userId) {
        alert('Користувач не авторизований')
        return
      }
      const payload = {
        ...this.form,
        customerId: Number(userId),
        sessionId: null,
        masterId: null,
        discount: null,
        status: 'BOOKED'
      }
      await axios.post('/api/sessions', payload)
      alert('Бронювання створено!')
      this.form = {
        stationId: '',
        carId: '',
        date: '',
        time: '',
        description: '',
        services: []
      }
      this.totalCost = 0
      this.totalDuration = 0
      this.availableTimes = []
    },
    logout() {
      localStorage.removeItem('userId')
      localStorage.removeItem('role')
      this.$router.push('/')
    }
  },
  async mounted() {
    const userId = localStorage.getItem('userId')
    if (!userId) {
      alert('Користувач не авторизований')
      return
    }
    this.stations = (await axios.get('/api/stations')).data
    this.cars = (await axios.get(`/api/cars/user/${userId}`)).data
  }
}
</script>

<style scoped>
@import './PagesStyle.css';
</style>