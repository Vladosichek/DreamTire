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
      <h1>Користувачі</h1>
      <div class="styled-form">
        <div class="form-grid">
          <div>
            <label>Ім'я</label>
            <input v-model="form.name" disabled />
          </div>
          <div>
            <label>Email</label>
            <input v-model="form.email" disabled />
          </div>
          <div>
            <label>Телефон</label>
            <input v-model="form.telephone" disabled />
          </div>
          <div>
            <label>Роль</label>
            <select v-model="form.role" disabled>
              <option v-for="role in roles" :key="role" :value="role">{{ role }}</option>
            </select>
          </div>
          <div>
            <label>Станція</label>
            <select v-model="form.stationId" disabled>
              <option :value="null">Не прив'язано</option>
              <option v-for="station in stations" :key="station.id" :value="station.id">
                {{ station.name }}
              </option>
            </select>
          </div>
        </div>
        <div>
          <label>Повідомлення</label>
          <textarea v-model="messageContent" rows="5" placeholder="Введіть повідомлення..."></textarea>
        </div>
        <div class="form-buttons">
          <button @click="clearForm">Очистити</button>
          <button @click="sendMessage" :disabled="!messageContent.trim()">Надіслати повідомлення</button>
        </div>
      </div>

      <form @submit.prevent="searchUsers" class="search-form">
        <input v-model="searchName" placeholder="Пошук за ім'ям" />
        <input v-model="searchEmail" placeholder="Пошук за email" />
        <select v-model="searchRole">
          <option value="">Всі ролі</option>
          <option v-for="role in roles" :key="role" :value="role">{{ role }}</option>
        </select>
        <button type="submit">Пошук</button>
        <button type="button" @click="resetSearch">Скинути</button>
      </form>

      <div class="services-box">
        <table class="session-table">
          <thead>
            <tr>
              <th @click="sortBy('name')">Ім'я</th>
              <th @click="sortBy('email')">Email</th>
              <th @click="sortBy('telephone')">Телефон</th>
            </tr>
          </thead>
          <tbody>
            <tr
              v-for="user in sortedUsers"
              :key="user.id"
              :class="{ selected: selectedUser?.id === user.id }"
              @click="selectUser(user)"
            >
              <td>{{ user.name }}</td>
              <td>{{ user.email }}</td>
              <td>{{ user.telephone }}</td>
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
  name: 'UserTab',
  data() {
    return {
      users: [],
      stations: [],
      roles: ['ADMIN', 'MASTER', 'CLIENT'],
      searchRole: '',
      searchName: '',
      searchEmail: '',
      sortKey: '',
      selectedUser: null,
      messageContent: '',
      form: {
        name: '',
        email: '',
        telephone: '',
        role: 'CLIENT',
        stationId: null
      }
    };
  },
  computed: {
    sortedUsers() {
      let list = [...this.users];
      if (this.sortKey) {
        list.sort((a, b) => (a[this.sortKey] > b[this.sortKey] ? 1 : -1));
      }
      return list;
    }
  },
  methods: {
    async fetchUsers() {
      const res = await axios.get('/api/users');
      this.users = res.data;
    },
    async fetchStations() {
      const res = await axios.get('/api/stations');
      this.stations = res.data;
    },
    async sendMessage() {
        if (!this.selectedUser || !this.selectedUser.id) {
            alert('Користувач не вибраний');
            return;
        }

        const email = this.selectedUser.email;
        if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email)) {
            alert('Email користувача недійсний');
            return;
        }

        if (!this.messageContent.trim()) {
            alert('Повідомлення не може бути порожнім');
            return;
        }

        try {
            await axios.post(`/api/users/${this.selectedUser.id}/send-message`, this.messageContent, {
                headers: { 'Content-Type': 'text/plain' }
            });
            alert('Повідомлення надіслано');
            this.messageContent = '';
        } catch (e) {
            alert(e.response?.data || 'Помилка при надсиланні повідомлення');
        }
    },
    selectUser(user) {
      this.selectedUser = user;
      this.form = {
        name: user.name,
        email: user.email,
        telephone: user.telephone,
        role: user.role,
        stationId: user.stationId ?? null
      };
      this.messageContent = '';
    },
    searchUsers() {
      this.fetchUsers().then(() => {
        this.users = this.users.filter((u) =>
          u.name.toLowerCase().includes(this.searchName.toLowerCase()) &&
          u.email.toLowerCase().includes(this.searchEmail.toLowerCase()) &&
          (this.searchRole === '' || u.role === this.searchRole)
        );
      });
    },
    resetSearch() {
      this.searchName = '';
      this.searchEmail = '';
      this.searchRole = '';
      this.fetchUsers();
    },
    clearForm() {
      this.selectedUser = null;
      this.form = {
        name: '',
        email: '',
        telephone: '',
        role: 'CLIENT',
        stationId: null
      };
      this.messageContent = '';
    },
    sortBy(key) {
      this.sortKey = key;
    },
    logout() {
    localStorage.removeItem('userId')
    localStorage.removeItem('role')
    this.$router.push('/')
  }
  },
  mounted() {
    this.fetchUsers();
    this.fetchStations();
  } 
};
</script>

<style scoped>
@import './PagesStyle.css';
</style>
