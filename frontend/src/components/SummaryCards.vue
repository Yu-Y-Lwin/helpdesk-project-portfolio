<script setup>
import { STATUSES, PRIORITIES, statusLabel } from '../rules.js'

// US-08: counts of tickets by status and priority
defineProps({
  summary: { type: Object, default: null },
})
</script>

<template>
  <div v-if="summary" class="summary" data-test="summary">
    <div class="summary-card">
      <div class="value" data-test="summary-total">{{ summary.total }}</div>
      <div class="label">Total tickets</div>
    </div>
    <div v-for="status in STATUSES" :key="status" class="summary-card">
      <div class="value">{{ summary.by_status[status] }}</div>
      <div class="label">{{ statusLabel(status) }}</div>
    </div>
    <div v-for="priority in PRIORITIES" :key="priority" class="summary-card">
      <div class="value">{{ summary.by_priority[priority] }}</div>
      <div class="label">{{ priority }} priority</div>
    </div>
  </div>
</template>
