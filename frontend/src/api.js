// Small wrapper around fetch. Every request goes to /api/..., which Vite forwards to the API.

async function request(path, options = {}) {
  const response = await fetch(`/api${path}`, {
    headers: { 'Content-Type': 'application/json' },
    ...options,
  })

  let data = null
  try {
    data = await response.json()
  } catch {
    // an empty body is fine
  }

  if (!response.ok) {
    const detail = data && data.detail ? data.detail : `Request failed (${response.status})`
    const error = new Error(detail)
    error.status = response.status
    throw error
  }
  return data
}

export function listTickets({ status = '', priority = '' } = {}) {
  const params = new URLSearchParams()
  if (status) params.set('status', status)
  if (priority) params.set('priority', priority)
  const query = params.toString()
  return request(`/tickets${query ? `?${query}` : ''}`)
}

export function getTicket(id) {
  return request(`/tickets/${id}`)
}

export function createTicket(payload) {
  return request('/tickets', { method: 'POST', body: JSON.stringify(payload) })
}

export function assignTicket(id, assignee) {
  return request(`/tickets/${id}/assignee`, {
    method: 'PATCH',
    body: JSON.stringify({ assignee }),
  })
}

export function changeStatus(id, status) {
  return request(`/tickets/${id}/status`, {
    method: 'PATCH',
    body: JSON.stringify({ status }),
  })
}

export function getSummary() {
  return request('/reports/summary')
}
