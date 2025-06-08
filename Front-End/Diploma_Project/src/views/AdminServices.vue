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
      <h1>Послуги</h1>
      <form class="styled-form" @submit.prevent="submitForm">
        <div class="form-grid">
          <div>
            <label>Назва</label>
            <input v-model="form.name" required />
          </div>
          <div>
            <label>Опис</label>
            <input v-model="form.description" required />
          </div>
          <div>
            <label>Тривалість (хв)</label>
            <input type="number" v-model="form.duration" required />
          </div>
          <div>
            <label>Вартість</label>
            <input type="number" v-model="form.cost" required step="0.01" />
          </div>
          <div>
            <label>Станція</label>
            <select v-model="form.stationId" required>
              <option v-for="station in stations" :value="station.id" :key="station.id">{{ station.name }}</option>
            </select>
          </div>
        </div>
        <div class="form-buttons">
          <button type="submit">{{ form.id ? 'Оновити' : 'Створити' }}</button>
          <button v-if="form.id" type="button" @click="deleteService">Видалити</button>
          <button type="button" @click="clearForm">Очистити</button>
        </div>
      </form>

      <form class="search-form" @submit.prevent>
        <input v-model="searchName" placeholder="Пошук за назвою" @input="filterServices" />
        <select v-model="searchStation" @change="filterServices">
          <option value="">Усі станції</option>
          <option v-for="station in stations" :key="station.id" :value="station.name">{{ station.name }}</option>
        </select>
      </form>

      <div class="services-box">
        <table class="session-table">
          <thead>
            <tr>
              <th>Назва</th>
              <th>Станція</th>
              <th>Опис</th>
            </tr>
          </thead>
          <tbody>
            <tr
              v-for="service in filteredServices"
              :key="service.id"
              :class="{ selected: selectedService?.id === service.id }"
              @click="selectService(service)"
            >
              <td>{{ service.name }}</td>
              <td>{{ service.stationName }}</td>
              <td>{{ service.description }}</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      services: [],
      filteredServices: [],
      stations: [],
      selectedService: null,
      searchName: '',
      searchStation: '',
      form: {
        id: null,
        name: '',
        description: '',
        duration: '',
        cost: '',
        stationId: ''
      }
    };
  },
  methods: {
    async fetchData() {
      const [servicesRes, stationsRes] = await Promise.all([
        axios.get('/api/services'),
        axios.get('/api/stations')
      ]);
      this.services = servicesRes.data;
      this.stations = stationsRes.data;
      this.filterServices();
    },
    filterServices() {
      const name = this.searchName.toLowerCase();
      const station = this.searchStation.toLowerCase();
      this.filteredServices = this.services.filter(s =>
        (!name || s.name.toLowerCase().includes(name)) &&
        (!station || s.stationName?.toLowerCase() === station)
      );
    },
    selectService(service) {
      this.selectedService = service;
      this.form = {
        id: service.id,
        name: service.name,
        description: service.description,
        duration: service.duration,
        cost: service.cost,
        stationId: service.stationId
      };
    },
    async submitForm() {
      if (this.form.id) {
        await axios.put(`/api/services/${this.form.id}`, this.form);
      } else {
        await axios.post('/api/services', this.form);
      }
      await this.fetchData();
      this.clearForm();
    },
    async deleteService() {
      if (confirm('Ви впевнені, що хочете видалити цю послугу?')) {
        await axios.delete(`/api/services/${this.form.id}`);
        await this.fetchData();
        this.clearForm();
      }
    },
    clearForm() {
      this.form = {
        id: null,
        name: '',
        description: '',
        duration: '',
        cost: '',
        stationId: ''
      };
      this.selectedService = null;
    },
    logout() {
    localStorage.removeItem('userId')
    localStorage.removeItem('role')
    this.$router.push('/')
    }
  },
  mounted() {
    this.fetchData();
  }
};
</script>

<style scoped>
@import './PagesStyle.css';
</style>