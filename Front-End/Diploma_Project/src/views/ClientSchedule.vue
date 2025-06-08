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
      <h1>Розклад роботи</h1>
      <form class="styled-form">
        <div class="form-grid">
          <div><strong>Назва станції:</strong> {{ form.name }}</div>
          <div><strong>Адреса станції:</strong> {{ form.adress }}</div>
          <div v-for="(dayLabel, i) in dayLabels" :key="i">
            <strong>{{ dayLabel.label }}:</strong>
            з {{ formatMinutes(form[dayLabel.start]) }} до {{ formatMinutes(form[dayLabel.end]) }}
          </div>
        </div>
      </form>

      <form @submit.prevent="searchStations" class="search-form"> 
        <input
          v-model="searchQuery"
          placeholder="Пошук за назвою"
          required
        />
        <button type="submit">Пошук</button>
        <button type="button" @click="resetSearch">Скинути</button>
      </form>

      <table class="session-table">
        <thead>
          <tr>
            <th @click="sortBy('name')">Назва</th>
            <th @click="sortBy('adress')">Адреса</th>
          </tr>
        </thead>
        <tbody>
          <tr
            v-for="station in sortedStations"
            :key="station.id"
            :class="{ selected: selected && selected.id === station.id }"
            @click="selectStation(station)"
          >
            <td>{{ station.name }}</td>
            <td>{{ station.adress }}</td>
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
      dayLabels: [
        { label: 'Пн', start: 'monStart', end: 'monFinish' },
        { label: 'Вт', start: 'tueStart', end: 'tueFinish' },
        { label: 'Ср', start: 'wenStart', end: 'wenFinish' },
        { label: 'Чт', start: 'thuStart', end: 'thuFinish' },
        { label: 'Пт', start: 'friStart', end: 'friFinish' },
        { label: 'Сб', start: 'satStart', end: 'satFinish' },
        { label: 'Нд', start: 'sunStart', end: 'sunFinish' },
      ]
    }
  },
  computed: {
    sortedStations() {
      return [...this.stations].sort((a, b) => {
        const res = a[this.sortKey]?.localeCompare?.(b[this.sortKey]) || 0
        return this.sortAsc ? res : -res
      })
    }
  },
  methods: {
    formatMinutes(minutes) {
      if (minutes == null) return ''
      const h = Math.floor(minutes / 60).toString().padStart(2, '0')
      const m = (minutes % 60).toString().padStart(2, '0')
      return `${h}:${m}`
    },
    async loadStations() {
      const { data } = await axios.get('/api/stations')
      this.stations = data
    },
    async searchStations() {
      if (this.searchQuery.trim() === '') {
        await this.loadStations()
        return
      }
      const { data } = await axios.get('/api/stations/search', {
        params: { name: this.searchQuery },
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
    resetSearch() {
      this.searchQuery = '';
      this.loadStations();
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