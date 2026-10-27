<script setup>
import { PRIORITIES, STATUSES, statusLabel } from '../rules.js'

// US-03 see the list, US-04 filter by priority (and status)
defineProps({
  tickets: { type: Array, required: true },
  selectedId: { type: Number, default: null },
  status: { type: String, default: '' },
  priority: { type: String, default: '' },
})
const emit = defineEmits(['select', 'update:status', 'update:priority'])
</script>

<template>
  <section class="card">
    <h2>Tickets</h2>

    <div class="filters">
      <div>
        <label for="filter-status">Status</label>
        <select id="filter-status" :value="status" @change="emit('update:status', $event.target.value)">
          <option value="">All</option>
          <option v-for="s in STATUSES" :key="s" :value="s">{{ statusLabel(s) }}</option>
        </select>
      </div>
      <div>
        <label for="filter-priority">Priority</label>
        <select id="filter-priority" :value="priority" @change="emit('update:priority', $event.target.value)">
          <option value="">All</option>
          <option v-for="p in PRIORITIES" :key="p" :value="p">{{ p }}</option>
        </select>
      </div>
    </div>

    <p v-if="tickets.length === 0" class="empty" data-test="empty">No tickets to show.</p>

    <table v-else>
      <thead>
        <tr>
          <th>ID</th>
          <th>Title</th>
          <th>Priority</th>
          <th>Status</th>
          <th>Assignee</th>
          <th>SLA</th>
        </tr>
      </thead>
      <tbody>
        <tr
          v-for="ticket in tickets"
          :key="ticket.id"
          :class="{ selected: ticket.id === selectedId }"
          data-test="ticket-row"
          @click="emit('select', ticket.id)"
        >
          <td>{{ ticket.id }}</td>
          <td>{{ ticket.title }}</td>
          <td><span class="badge" :class="ticket.priority">{{ ticket.priority }}</span></td>
          <td><span class="badge">{{ statusLabel(ticket.status) }}</span></td>
          <td>{{ ticket.assignee || 'Unassigned' }}</td>
          <td>{{ ticket.sla_hours }} h</td>
        </tr>
      </tbody>
    </table>
  </section>
</template>
