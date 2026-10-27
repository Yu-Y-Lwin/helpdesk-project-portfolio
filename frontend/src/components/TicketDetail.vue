<script setup>
import { ref } from 'vue'
import { allowedNext, isValidEmail, statusLabel } from '../rules.js'

// US-05 open one ticket, US-06 assign it, US-07 move it through the workflow
const props = defineProps({
  ticket: { type: Object, default: null },
  error: { type: String, default: '' },
})
const emit = defineEmits(['assign', 'change-status'])

const assignee = ref('')
const assigneeError = ref('')

function onAssign() {
  if (!isValidEmail(assignee.value)) {
    assigneeError.value = 'Enter a valid agent email address.'
    return
  }
  assigneeError.value = ''
  emit('assign', assignee.value.trim())
  assignee.value = ''
}
</script>

<template>
  <section class="card detail">
    <h2>Ticket details</h2>

    <p v-if="!ticket" class="empty" data-test="no-selection">Select a ticket to see its details.</p>

    <template v-else>
      <dl data-test="detail">
        <dt>ID</dt>
        <dd>{{ ticket.id }}</dd>
        <dt>Title</dt>
        <dd>{{ ticket.title }}</dd>
        <dt>Description</dt>
        <dd>{{ ticket.description }}</dd>
        <dt>Requester</dt>
        <dd>{{ ticket.requester_email }}</dd>
        <dt>Priority</dt>
        <dd>
          <span class="badge" :class="ticket.priority">{{ ticket.priority }}</span>
          ({{ ticket.sla_hours }} hour target)
        </dd>
        <dt>Status</dt>
        <dd data-test="detail-status">{{ statusLabel(ticket.status) }}</dd>
        <dt>Assignee</dt>
        <dd data-test="detail-assignee">{{ ticket.assignee || 'Unassigned' }}</dd>
      </dl>

      <div v-if="error" class="banner error" style="margin-top: 12px" data-test="detail-error">{{ error }}</div>

      <label for="assignee">Assign to an agent</label>
      <div class="assign">
        <input id="assignee" v-model="assignee" type="email" placeholder="agent@example.com" />
        <button class="secondary" data-test="assign" @click="onAssign">Assign</button>
      </div>
      <div v-if="assigneeError" class="field-error" data-test="assignee-error">{{ assigneeError }}</div>

      <label>Move to the next status</label>
      <div class="actions" style="margin-top: 0">
        <button
          v-for="next in allowedNext(ticket.status)"
          :key="next"
          data-test="status-button"
          @click="emit('change-status', next)"
        >
          {{ next === 'open' ? 'Reopen' : 'Set to ' + statusLabel(next) }}
        </button>
        <span v-if="allowedNext(ticket.status).length === 0" class="hint" data-test="closed-hint">
          Closed tickets cannot change.
        </span>
      </div>
    </template>
  </section>
</template>
