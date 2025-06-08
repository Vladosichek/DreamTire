<template>
  <div class="welcome-container">
    <div class="welcome-box">
      <h1>Вітаємо</h1>
      <div class="button-group">
        <button @click="$router.push('/register')">Реєстрація</button>
        <button @click="$router.push('/login')">Авторизація</button>
      </div>
    </div>
  </div>
</template>

<style scoped>
@import './WelcomePagesStyle.css';
</style>
