// Business rules from docs/01-business-requirements.md, repeated here for the user interface.
// The API checks them again, so these checks only give faster feedback.

export const PRIORITIES = ['low', 'medium', 'high']
export const STATUSES = ['open', 'in_progress', 'resolved', 'closed']

// BR-01: target resolution time in hours
export const SLA_HOURS = { low: 72, medium: 24, high: 4 }

// BR-02: allowed status changes
const NEXT_STATUS = {
  open: ['in_progress'],
  in_progress: ['resolved'],
  resolved: ['closed', 'open'],
  closed: [],
}

const EMAIL_PATTERN = /^[^@\s]+@[^@\s]+\.[^@\s]+$/

export function allowedNext(status) {
  return NEXT_STATUS[status] ?? []
}

export function statusLabel(status) {
  return String(status).replace('_', ' ')
}

// BR-03: returns an object with one message per invalid field. An empty object means valid.
export function validateTicket(form) {
  const errors = {}
  const title = (form.title ?? '').trim()
  const description = (form.description ?? '').trim()

  if (title.length < 3 || title.length > 100) {
    errors.title = 'Title must be 3 to 100 characters.'
  }
  if (description.length < 10 || description.length > 1000) {
    errors.description = 'Description must be 10 to 1000 characters.'
  }
  if (!PRIORITIES.includes(form.priority)) {
    errors.priority = 'Choose low, medium or high.'
  }
  if (!EMAIL_PATTERN.test((form.requester_email ?? '').trim())) {
    errors.requester_email = 'Enter a valid email address.'
  }
  return errors
}

export function isValidEmail(value) {
  return EMAIL_PATTERN.test((value ?? '').trim())
}
