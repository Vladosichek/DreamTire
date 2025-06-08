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
      <form class="styled-form" @submit.prevent="createUser">
        <div class="form-grid">
          <div>
            <label>Ім'я</label>
            <input v-model="form.name" />
          </div>
          <div>
            <label>Email</label>
            <input v-model="form.email" />
          </div>
          <div>
            <label>Телефон</label>
            <input v-model="form.telephone" />
          </div>
          <div>
            <label>Пароль</label>
            <input v-model="form.password" />
          </div>
          <div>
            <label>Роль</label>
            <select v-model="form.role">
              <option v-for="role in roles" :key="role" :value="role">{{ role }}</option>
            </select>
          </div>
          <div>
            <label>Станція</label>
            <select v-model="form.stationId">
              <option :value="null">Не прив'язано</option>
              <option v-for="station in stations" :key="station.id" :value="station.id">
                {{ station.name }}
              </option>
            </select>
          </div>
        </div>
        <div class="form-buttons">
          <button type="submit">Створити</button>
          <button type="button" @click="updateUser" :disabled="!selectedUser">Оновити</button>
          <button type="button" @click="deleteUser" :disabled="!selectedUser">Видалити</button>
        </div>
      </form>

      <form @submit.prevent="searchUsers" class="search-form">
        <div>
          <input v-model="searchName" placeholder="Пошук за ім'ям" />
        </div>
        <div>
          <input v-model="searchEmail" placeholder="Пошук за email" />
        </div>
        <div>
          <select v-model="searchRole">
            <option value="">Всі ролі</option>
            <option v-for="role in roles" :key="role" :value="role">{{ role }}</option>
          </select>
        </div>
        <div class="form-buttons">
          <button type="submit">Пошук</button>
          <button type="button" @click="resetSearch">Скинути</button>
        </div>
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
      form: {
        name: '',
        email: '',
        telephone: '',
        password: '',
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
    async createUser() {
      const res = await axios.post('/api/users', this.form);
      await this.fetchUsers();
      this.clearForm();
    },
    async updateUser() {
      await axios.put(`/api/users/${this.selectedUser.id}`, this.form);
      await this.fetchUsers();
    },
    async deleteUser() {
      try {
        await axios.delete(`/api/users/${this.selectedUser.id}`);
        this.clearForm();
        await this.fetchUsers();
      } catch (e) {
        alert('Не можна видалити користувача — є пов\'язані записи.');
      }
    },
    selectUser(user) {
        this.selectedUser = user;
        this.form = {
            name: user.name,
            email: user.email,
            telephone: user.telephone,
            password: user.password, 
            role: user.role,
            stationId: user.stationId ?? null
        };
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
      this.form = {
        name: '',
        email: '',
        telephone: '',
        password: '',
        role: 'CLIENT',
        stationId: null
      };
      this.selectedUser = null;
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