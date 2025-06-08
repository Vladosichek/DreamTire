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
      <h1>Станції</h1>
      <form @submit.prevent="selected ? updateStation() : createStation()" class="styled-form">
        <div class="form-grid">
          <div>
            <label>Назва станції</label>
            <input v-model="form.name" />
          </div>
          <div>
            <label>Адреса станції</label>
            <input v-model="form.adress" />
          </div>
        </div>
        <div class="form-grid">
          <template v-for="(label, key) in timeFields" :key="key">
            <div>
              <label>{{ label }}</label>
              <input
                type="number"
                min="0"
                max="1439"
                v-model.number="form[key]"
                placeholder="0–1439"
              />
            </div>
          </template>
        </div>
        <div class="form-buttons">
          <button type="submit">{{ selected ? 'Оновити' : 'Створити' }}</button>
          <button v-if="selected" @click.prevent="deleteStation">Видалити</button>
          <button @click.prevent="resetForm">Очистити</button>
        </div>
      </form>

      <form class="search-form" @submit.prevent>
        <input
          v-model="searchQuery"
          placeholder="Пошук за назвою станції"
        />
        <button @click="searchStations">Пошук</button>
        <button @click="resetSearch">Скинути</button>
      </form>

      <div class="services-box">
        <table class="session-table">
          <thead>
            <tr>
              <th @click="sortBy('name')" class="clickable-header">Назва</th>
              <th @click="sortBy('adress')" class="clickable-header">Адреса</th>
            </tr>
          </thead>
          <tbody>
            <tr
              v-for="station in sortedStations"
              :key="station.id"
              :class="{ selected: selected?.id === station.id }"
              @click="selectStation(station)"
            >
              <td>{{ station.name }}</td>
              <td>{{ station.adress }}</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>


<script>
import axios from 'axios'

export default {
  data() {
    return {
      stations: [],
      selected: null,
      searchQuery: '',
      sortKey: 'name',
      sortAsc: true,
      form: {
        name: '',
        adress: '',
        monStart: 0, monFinish: 0,
        tueStart: 0, tueFinish: 0,
        wenStart: 0, wenFinish: 0,
        thuStart: 0, thuFinish: 0,
        friStart: 0, friFinish: 0,
        satStart: 0, satFinish: 0,
        sunStart: 0, sunFinish: 0,
      },
      timeFields: {
        monStart: 'Пн: початок', monFinish: 'Пн: кінець',
        tueStart: 'Вт: початок', tueFinish: 'Вт: кінець',
        wenStart: 'Ср: початок', wenFinish: 'Ср: кінець',
        thuStart: 'Чт: початок', thuFinish: 'Чт: кінець',
        friStart: 'Пт: початок', friFinish: 'Пт: кінець',
        satStart: 'Сб: початок', satFinish: 'Сб: кінець',
        sunStart: 'Нд: початок', sunFinish: 'Нд: кінець',
      }
    }
  },
  computed: {
    sortedStations() {
      return [...this.stations].sort((a, b) => {
        const aVal = a[this.sortKey] || ''
        const bVal = b[this.sortKey] || ''
        const result = aVal > bVal ? 1 : aVal < bVal ? -1 : 0
        return this.sortAsc ? result : -result
      })
    }
  },
  methods: {
    async loadStations() {
      const { data } = await axios.get('/api/stations')
      this.stations = data
    },
    async searchStations() {
      if (!this.searchQuery.trim()) {
        await this.loadStations()
        return
      }
      const { data } = await axios.get('/api/stations/search', {
        params: { name: this.searchQuery }
      })
      this.stations = data
    },
    sortBy(key) {
      if (this.sortKey === key) {
        this.sortAsc = !this.sortAsc
      } else {
        this.sortKey = key
        this.sortAsc = true
      }
    },
    selectStation(station) {
      this.selected = station
      Object.assign(this.form, station)
    },
    resetForm() {
      this.selected = null
      Object.keys(this.form).forEach(k => {
        this.form[k] = typeof this.form[k] === 'number' ? 0 : ''
      })
    },
    async createStation() {
      await axios.post('/api/stations', this.form)
      await this.loadStations()
      this.resetForm()
    },
    async updateStation() {
      if (!this.selected) return
      await axios.put(`/api/stations/${this.selected.id}`, this.form)
      await this.loadStations()
      this.resetForm()
    },
    resetSearch() {
      this.searchQuery = '';
      this.loadStations();
    },
    async deleteStation() {
      if (!this.selected) return
      try {
        await axios.delete(`/api/stations/${this.selected.id}`)
        await this.loadStations()
        this.resetForm()
      } catch (err) {
        alert('Неможливо видалити станцію: вона має повʼязані записи.')
      }
    },
    logout() {
      localStorage.removeItem('userId')
      localStorage.removeItem('role')
      this.$router.push('/')
    }
  },
  mounted() {
    this.loadStations()
  }
}
</script>

<style scoped>
@import './PagesStyle.css';
</style>