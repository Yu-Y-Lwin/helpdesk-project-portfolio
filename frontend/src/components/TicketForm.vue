<script setup>
import { reactive, ref } from 'vue'
import { PRIORITIES, SLA_HOURS, validateTicket } from '../rules.js'

// US-01 create a ticket, US-02 clear errors for invalid input
const props = defineProps({
  // async function that sends the ticket; returns true when it worked
  submit: { type: Function, required: true },
  serverError: { type: String, default: '' },
})

function emptyForm() {
  return { title: '', description: '', priority: 'medium', requester_email: '' }
}

const form = reactive(emptyForm())
const errors = ref({})
const sending = ref(false)

async function onSubmit() {
  errors.value = validateTicket(form)
  if (Object.keys(errors.value).length > 0) {
    return
  }
  sending.value = true
  const ok = await props.submit({
    title: form.title.trim(),
    description: form.description.trim(),
    priority: form.priority,
    requester_email: form.requester_email.trim(),
  })
  sending.value = false
  if (ok) {
    Object.assign(form, emptyForm())
    errors.value = {}
  }
}
</script>

<template>
  <form class="card" novalidate @submit.prevent="onSubmit" data-test="ticket-form">
    <h2>Report a problem</h2>

    <label for="title">Title</label>
    <input id="title" v-model="form.title" type="text" maxlength="100" />
    <div v-if="errors.title" class="field-error" data-test="error-title">{{ errors.title }}</div>

    <label for="description">Description</label>
    <textarea id="description" v-model="form.description" maxlength="1000"></textarea>
    <div v-if="errors.description" class="field-error" data-test="error-description">
      {{ errors.description }}
    </div>

    <label for="priority">Priority</label>
    <select id="priority" v-model="form.priority">
      <option v-for="p in PRIORITIES" :key="p" :value="p">{{ p }} ({{ SLA_HOURS[p] }} hour target)</option>
    </select>
    <div v-if="errors.priority" class="field-error" data-test="error-priority">{{ errors.priority }}</div>

    <label for="email">Your email</label>
    <input id="email" v-model="form.requester_email" type="email" />
    <div v-if="errors.requester_email" class="field-error" data-test="error-email">
      {{ errors.requester_email }}
    </div>

    <div v-if="serverError" class="banner error" style="margin-top: 12px" data-test="server-error">
      {{ serverError }}
    </div>

    <div class="actions">
      <button type="submit" :disabled="sending" data-test="submit">
        {{ sending ? 'Sending...' : 'Create ticket' }}
      </button>
    </div>
  </form>
</template>
