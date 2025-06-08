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
      <h1>Матеріали</h1>
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
            <label>Кількість</label>
            <input type="number" v-model.number="form.quantity" required />
          </div>
          <div>
            <label>Критичний рівень</label>
            <input type="number" v-model.number="form.critical" required />
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
        </div>
        <div class="form-buttons">
          <button type="submit">{{ form.id ? 'Оновити' : 'Створити' }}</button>
          <button v-if="form.id" type="button" @click="deleteMaterial">Видалити</button>
          <button type="button" @click="clearForm">Очистити</button>
        </div>
      </form>

      <form @submit.prevent class="search-form">
        <input
            v-model="searchName"
            placeholder="Пошук за назвою"
            @input="fetchMaterials"
          />
        <select v-model="searchStationId" @change="fetchMaterials">
            <option value="">Усі станції</option>
            <option v-for="station in stations" :key="station.id" :value="station.id">
              {{ station.name }}
            </option>
          </select>
      </form>

      <div class="services-box">
        <table class="session-table">
          <thead>
            <tr>
              <th>Назва</th>
              <th>Станція</th>
              <th>Кількість</th>
            </tr>
          </thead>
          <tbody>
            <tr
              v-for="material in filteredMaterials"
              :key="material.id"
              @click="selectMaterial(material)"
              :class="{ selected: selectedMaterial?.id === material.id }"
            >
              <td>{{ material.name }}</td>
              <td>{{ material.stationName }}</td>
              <td>{{ material.quantity }}</td>
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
      materials: [],
      stations: [],
      selectedMaterial: null,
      form: {
        id: null,
        name: '',
        description: '',
        quantity: 0,
        critical: 0,
        stationId: ''
      },
      searchName: '',
      searchStationId: ''
    };
  },
  computed: {
    filteredMaterials() {
      return this.materials.filter(m =>
        (!this.searchName || m.name.toLowerCase().includes(this.searchName.toLowerCase())) &&
        (!this.searchStationId || m.stationId == this.searchStationId)
      );
    }
  },
  methods: {
    async fetchMaterials() {
      const res = await axios.get('/api/materials');
      this.materials = res.data.map(m => {
        const station = this.stations.find(s => s.id === m.stationId);
        return {
          ...m,
          stationName: station ? station.name : 'Невідомо'
        };
      });
    },
    async fetchStations() {
      const res = await axios.get('/api/stations');
      this.stations = res.data;
    },
    selectMaterial(material) {
      this.selectedMaterial = material;
      this.form = { ...material };
    },
    async submitForm() {
      if (this.form.id) {
        await axios.put(`/api/materials/${this.form.id}`, this.form);
      } else {
        await axios.post('/api/materials', this.form);
      }
      this.clearForm();
      this.fetchMaterials();
    },
    async deleteMaterial() {
      if (confirm('Ви впевнені, що хочете видалити цей матеріал?')) {
        await axios.delete(`/api/materials/${this.form.id}`);
        this.clearForm();
        this.fetchMaterials();
      }
    },
    clearForm() {
      this.form = {
        id: null,
        name: '',
        description: '',
        quantity: 0,
        critical: 0,
        stationId: ''
      };
      this.selectedMaterial = null;
    },
    logout() {
    localStorage.removeItem('userId')
    localStorage.removeItem('role')
    this.$router.push('/')
    }
  },
  mounted() {
    this.fetchStations().then(() => {
    this.fetchMaterials();
  });
  }
};
</script>

<style scoped>
@import './PagesStyle.css';
</style>