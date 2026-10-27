<script setup>
import { computed, onMounted, ref } from 'vue'
import * as api from './api.js'
import SummaryCards from './components/SummaryCards.vue'
import TicketForm from './components/TicketForm.vue'
import TicketList from './components/TicketList.vue'
import TicketDetail from './components/TicketDetail.vue'

const tickets = ref([])
const summary = ref(null)
const selectedId = ref(null)
const filterStatus = ref('')
const filterPriority = ref('')

const formError = ref('')
const detailError = ref('')
const pageError = ref('')
const message = ref('')

const selectedTicket = computed(() => tickets.value.find((t) => t.id === selectedId.value) ?? null)

async function refresh() {
  try {
    const [list, report] = await Promise.all([
      api.listTickets({ status: filterStatus.value, priority: filterPriority.value }),
      api.getSummary(),
    ])
    tickets.value = list
    summary.value = report
    pageError.value = ''
  } catch (error) {
    pageError.value = `Could not reach the API: ${error.message}. Is it running on port 8000?`
  }
}

async function createTicket(payload) {
  formError.value = ''
  message.value = ''
  try {
    const ticket = await api.createTicket(payload)
    filterStatus.value = ''
    filterPriority.value = ''
    await refresh()
    selectedId.value = ticket.id
    message.value = `Ticket ${ticket.id} created. Target resolution: ${ticket.sla_hours} hours.`
    return true
  } catch (error) {
    formError.value = error.message
    return false
  }
}

function selectTicket(id) {
  selectedId.value = id
  detailError.value = ''
  message.value = ''
}

async function assign(email) {
  detailError.value = ''
  try {
    await api.assignTicket(selectedId.value, email)
    await refresh()
  } catch (error) {
    detailError.value = error.message
  }
}

async function changeStatus(next) {
  detailError.value = ''
  try {
    await api.changeStatus(selectedId.value, next)
    await refresh()
  } catch (error) {
    detailError.value = error.message
  }
}

onMounted(refresh)
</script>

<template>
  <main class="page">
    <header class="page-header">
      <h1>CampusDesk IT Helpdesk</h1>
      <p>A practice project: report problems, track them, and see the workload.</p>
    </header>

    <div v-if="pageError" class="banner error" data-test="page-error">{{ pageError }}</div>
    <div v-if="message" class="banner success" data-test="message">{{ message }}</div>

    <SummaryCards :summary="summary" />

    <div class="layout">
      <TicketForm :submit="createTicket" :server-error="formError" />

      <div>
        <TicketList
          :tickets="tickets"
          :selected-id="selectedId"
          :status="filterStatus"
          :priority="filterPriority"
          @select="selectTicket"
          @update:status="(value) => { filterStatus = value; refresh() }"
          @update:priority="(value) => { filterPriority = value; refresh() }"
        />
        <TicketDetail
          :ticket="selectedTicket"
          :error="detailError"
          @assign="assign"
          @change-status="changeStatus"
        />
      </div>
    </div>
  </main>
</template>
