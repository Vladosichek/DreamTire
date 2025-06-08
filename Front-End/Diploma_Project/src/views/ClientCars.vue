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
      <h1>Мої машини</h1>
      <form class="styled-form" @submit.prevent="submitForm">
        <div class="form-grid">
          <div>
            <label>Номер</label>
            <input v-model="form.number" required />
          </div>
          <div>
            <label>Марка</label>
            <input v-model="form.make" required />
          </div>
          <div>
            <label>Модель</label>
            <input v-model="form.model" required />
          </div>
        </div>
        <div class="form-buttons">
          <button type="submit">{{ form.id ? 'Оновити' : 'Створити' }}</button>
          <button v-if="form.id" type="button" @click="deleteCar">Видалити</button>
          <button type="button" @click="clearForm">Очистити</button>
        </div>
      </form>

      <table class="session-table">
        <thead>
          <tr>
            <th>Номер</th>
            <th>Марка</th>
            <th>Модель</th>
          </tr>
        </thead>
        <tbody>
          <tr
            v-for="car in cars"
            :key="car.id"
            @click="selectCar(car)"
            :class="{ selected: selectedCar?.id === car.id }"
          >
            <td>{{ car.number }}</td>
            <td>{{ car.make }}</td>
            <td>{{ car.model }}</td>
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
      cars: [],
      selectedCar: null,
      user: null,
      form: {
        id: null,
        number: '',
        make: '',
        model: '',
        userId: ''
      }
    }
  },
  methods: {
    async fetchCars() {
      const res = await axios.get(`/api/cars/user/${this.user}`)
      this.cars = res.data
    },
    selectCar(car) {
      this.selectedCar = car
      this.form = { ...car }
    },
    async submitForm() {
      const userId = localStorage.getItem('userId')
      this.user = userId
      this.form.userId = userId

      if (this.form.id) {
        await axios.put(`/api/cars/${this.form.id}`, this.form)
      } else {
        await axios.post('/api/cars', this.form)
      }

      this.clearForm()
      await this.fetchCars()
    },
    async deleteCar() {
      if (confirm('Ви впевнені, що хочете видалити цю машину?')) {
        await axios.delete(`/api/cars/${this.form.id}`)
        this.clearForm()
        await this.fetchCars()
      }
    },
    clearForm() {
      this.form = {
        id: null,
        number: '',
        make: '',
        model: '',
        userId: ''
      }
      this.selectedCar = null
    },
    logout() {
      localStorage.removeItem('userId')
      localStorage.removeItem('role')
      this.$router.push('/')
    }
  },
  mounted() {
    const userId = localStorage.getItem('userId')
    if (userId) {
      this.user = userId
      this.fetchCars()
  }
  }
}
</script>

<style scoped>
@import './PagesStyle.css';
</style>