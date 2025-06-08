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
      <h1>Статистика по станціям</h1>
      <form @submit.prevent="fetchData" class="styled-form">
        <div class="form-grid">
          <div>
            <label>Дата початку:</label>
            <input type="date" v-model="startDate" />
          </div>
          <div>
            <label>Дата завершення:</label>
            <input type="date" v-model="endDate" />
          </div>
          <div>
            <label>Тип статистики:</label>
            <select v-model="statType">
              <option value="BUSY">Зайнятість (хв)</option>
              <option value="INCOME">Дохід (грн)</option>
            </select>
          </div>
        </div>
        <div class="form-buttons">
          <button type="submit">Отримати статистику</button>
        </div>
      </form>

      <div v-if="responseData">
        <div class="form-buttons">
          <button @click="exportToExcel">Експорт в Excel</button>
          <button @click="exportToPDF">Експорт в PDF</button>
        </div>
        <div class="services-box">
          <table class="session-table">
            <thead>
              <tr>
                <th>Дата</th>
                <th v-for="station in responseData.stationNames" :key="station">
                  {{ station }}
                </th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="date in responseData.dates" :key="date">
                <td>{{ date }}</td>
                <td v-for="station in responseData.stationNames" :key="station">
                  {{ responseData.data[date][station] }}
                </td>
              </tr>
            </tbody>
            <tfoot>
              <tr>
                <td>Разом</td>
                <td v-for="station in responseData.stationNames" :key="station">
                  {{ responseData.totalPerStation[station].toFixed(2) }}
                </td>
              </tr>
            </tfoot>
          </table>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import ExcelJS from 'exceljs'
import jsPDF from 'jspdf'
import autoTable from 'jspdf-autotable'
export default {
  data() {
    return {
      startDate: '',
      endDate: '',
      statType: 'BUSY',
      responseData: null
    }
  },
  methods: {
    async fetchData() {
      if (!this.startDate || !this.endDate) {
        alert('Введіть обидві дати')
        return
      }
      const res = await axios.post('/api/statistics', {
        startDate: this.startDate,
        endDate: this.endDate,
        statType: this.statType,
      })
      this.responseData = res.data
    },
    async exportToExcel() {
      if (!this.responseData) return
      const workbook = new ExcelJS.Workbook()
      const worksheet = workbook.addWorksheet('Статистика')
      worksheet.addRow(['Дата', ...this.responseData.stationNames])
      this.responseData.dates.forEach(date => {
        const row = [date]
        for (const s of this.responseData.stationNames) {
          row.push(this.responseData.data[date][s])
        }
        worksheet.addRow(row)
      })
      const totalRow = ['Разом', ...this.responseData.stationNames.map(s => this.responseData.totalPerStation[s])]
      worksheet.addRow(totalRow)
      worksheet.columns.forEach(column => {
        column.width = 15
      })
      const buffer = await workbook.xlsx.writeBuffer()
      const blob = new Blob([buffer], {
        type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet',
      })
      const link = document.createElement('a')
      link.href = URL.createObjectURL(blob)
      link.download = 'statistics.xlsx'
      link.click()
    },
    exportToPDF() {
      if (!this.responseData) return
      const doc = new jsPDF()
      const head = [['Дата', ...this.responseData.stationNames]]
      const body = this.responseData.dates.map(date => [
        date,
        ...this.responseData.stationNames.map(s => this.responseData.data[date][s]),
      ])
      body.push(['Разом', ...this.responseData.stationNames.map(s => this.responseData.totalPerStation[s])])
      autoTable(doc, { head, body })
      doc.save('statistics.pdf')
    },
    logout() {
      localStorage.removeItem('userId')
      localStorage.removeItem('role')
      this.$router.push('/')
    }
  }
}
</script>

<style scoped>
@import './PagesStyle.css';
</style>