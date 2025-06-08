<template>
  <div class="form-container">
    <div class="form-box">
      <h2>Авторизація</h2>
      <input v-model="email" placeholder="Email" :class="{ invalid: emailError }" />
      <input v-model="password" type="password" placeholder="Пароль" :class="{ invalid: passwordError }" />
      <p v-if="errorMessage" class="error">{{ errorMessage }}</p>
      <button @click="login">Увійти</button>
    </div>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  data() {
    return {
      email: '',
      password: '',
      emailError: false,
      passwordError: false,
      errorMessage: ''
    }
  },
  methods: {
    async login() {
      this.emailError = this.passwordError = false
      this.errorMessage = ''
      try {
        const response = await axios.post('/api/auth/login', {
          email: this.email,
          password: this.password
        })
        const { role, userId } = response.data
        localStorage.setItem('userId', userId)
        localStorage.setItem('role', role)
        switch (role) {
          case 'ADMIN':
            this.$router.push('/admin-stations')
            break
          case 'MASTER':
            this.$router.push('/master-schedule')
            break
          case 'CLIENT':
            this.$router.push('/client-cars')
            break
          default:
            this.errorMessage = 'Невідома роль'
        }
      } catch (e) {
        if (e.response) {
          if (e.response.status === 404) {
            this.emailError = true
            this.errorMessage = 'Користувача не знайдено'
          } else if (e.response.status === 401) {
            this.passwordError = true
            this.errorMessage = 'Невірний пароль'
          } else {
            this.errorMessage = 'Серверна помилка'
          }
        } else {
          this.errorMessage = 'Помилка авторизації'
        }
      }
    }
  }
}
</script>

<style scoped>
@import './WelcomePagesStyle.css';
</style>
