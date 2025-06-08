<template>
  <div class="form-container">
    <div class="form-box">
      <h2>Реєстрація</h2>
      <input v-model="email" placeholder="Email" :class="{ invalid: emailError }" />
      <input v-model="phone" placeholder="Телефон" :class="{ invalid: phoneError }" />
      <input v-model="fullName" placeholder="ПІБ" />
      <input v-model="password" type="password" placeholder="Пароль" />
      <input v-model="confirmPassword" type="password" placeholder="Підтвердження пароля" :class="{ invalid: passwordMismatch }" />
      <p v-if="errorMessage" class="error">{{ errorMessage }}</p>
      <button @click="register">Зареєструватися</button>
    </div>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  data() {
    return {
      email: '',
      phone: '',
      fullName: '',
      password: '',
      confirmPassword: '',
      emailError: false,
      phoneError: false,
      passwordMismatch: false,
      errorMessage: ''
    }
  },
  methods: {
    async register() {
      this.emailError = this.phoneError = this.passwordMismatch = false
      this.errorMessage = ''
      const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/
      const phoneRegex = /^\+?[0-9]{10,15}$/
      if (!emailRegex.test(this.email)) {
        this.emailError = true
        this.errorMessage = 'Некоректний email'
        return
      }
      if (!phoneRegex.test(this.phone)) {
        this.phoneError = true
        this.errorMessage = 'Некоректний номер телефону'
        return
      }
      if (this.password !== this.confirmPassword) {
        this.passwordMismatch = true
        this.errorMessage = 'Паролі не співпадають'
        return
      }
      try {
        const response = await axios.post('/api/auth/register', {
          email: this.email,
          telephone: this.phone,
          name: this.fullName,
          password: this.password
        })
        alert('Реєстрація успішна! Перевірте пошту.')
        this.$router.push('/login')
      } catch (e) {
        if (e.response && e.response.status === 409) {
          this.emailError = true
          this.errorMessage = 'Користувач із такою поштою вже існує'
        } else {
          this.errorMessage = 'Помилка під час реєстрації'
        }
      }
    }
  }
}
</script>

<style scoped>
@import './WelcomePagesStyle.css';
</style>
