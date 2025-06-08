<template>
  <div class="schedule-page">
    <div class="navbar full-width">
      <button @click="$router.push('/master-schedule')" :class="{ active: $route.path === '/master-schedule' }">Графік роботи</button>
      <button @click="$router.push('/master-writeoff')" :class="{ active: $route.path === '/master-writeoff' }">Списання матеріалів</button>
      <button @click="logout">Вийти</button>
    </div>

    <div class="container-box">
      <h1>Списання матеріалів</h1>
      <form class="form-grid">
        <div class="styled-form">
        <h3>Додати матеріали до списання</h3>
        <div class="scroll-box">
          <div v-for="(item, index) in consumptionList" :key="index" class="service-item">
            <select v-model="item.materialId" required>
              <option value="" disabled>Оберіть матеріал</option>
              <option v-for="mat in materials" :key="mat.id" :value="mat.id">
                {{ mat.name }} (залишок: {{ mat.quantity }})
              </option>
            </select>
            <input
              type="number"
              v-model.number="item.quantity"
              placeholder="Кількість для списання"
              min="1"
              required
              class="form-input"
            />
            <button type="button" @click="removeMaterial(index)">Видалити</button>
          </div>
        </div>
      </div>
      </form>
      <div class="form-buttons">
        <button type="button" @click="addMaterial">Додати матеріал</button>
        <button  @click="clearList">Очистити</button>
        <button  @click="confirmConsumption">Підтвердити</button>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      userId: null,
      user: null,
      materials: [],
      consumptionList: []
    };
  },
  methods: {
    async fetchUser() {
      this.userId = localStorage.getItem('userId');
      if (!this.userId) {
        alert('Користувач не авторизований');
        return;
      }
      try {
        const res = await axios.get(`/api/users/${this.userId}`);
        this.user = res.data;
        if (this.user.stationId) {
          await this.fetchMaterials(this.user.stationId);
        }
      } catch (error) {
        console.error('Помилка отримання користувача:', error);
      }
    },
    async fetchMaterials(stationId) {
      try {
        const res = await axios.get(`/api/materials/station/${stationId}`);
        this.materials = res.data;
      } catch (error) {
        console.error('Помилка отримання матеріалів:', error);
      }
    },
    addMaterial() {
      this.consumptionList.push({ materialId: '', quantity: 1 });
    },
    removeMaterial(index) {
      this.consumptionList.splice(index, 1);
    },
    clearList() {
      this.consumptionList = [];
    },
    async confirmConsumption() {
      const updates = [];
      for (const item of this.consumptionList) {
        const material = this.materials.find(m => m.id === item.materialId);
        if (!material) continue;
        const newQuantity = material.quantity - item.quantity;
        if (newQuantity < 0) {
          alert(`Недостатньо матеріалу: ${material.name}`);
          return;
        }
        updates.push(axios.put(`/api/materials/${material.id}`, {
          ...material,
          quantity: newQuantity
        }));
      }
      try {
        await Promise.all(updates);
        alert('Списання успішне');
        this.clearList();
        if (this.user?.stationId) {
          await this.fetchMaterials(this.user.stationId);
        }
      } catch (error) {
        console.error('Помилка списання:', error);
        alert('Сталася помилка при списанні');
      }
    },
    logout() {
      localStorage.removeItem('userId')
      localStorage.removeItem('role')
      this.$router.push('/')
    }
  },
  mounted() {
    this.fetchUser();
  }
};
</script>

<style scoped>
@import './PagesStyle.css';
</style>