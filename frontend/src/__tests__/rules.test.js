import { describe, expect, it } from 'vitest'
import { allowedNext, isValidEmail, validateTicket } from '../rules.js'

const valid = {
  title: 'Laptop will not start',
  description: 'The laptop shows a black screen after the logo.',
  priority: 'high',
  requester_email: 'employee@example.com',
}

describe('validateTicket (FR-02, BR-03)', () => {
  it('accepts a valid ticket', () => {
    expect(validateTicket(valid)).toEqual({})
  })

  it('rejects a missing title', () => {
    expect(validateTicket({ ...valid, title: '' })).toHaveProperty('title')
  })

  it('rejects a description shorter than 10 characters', () => {
    expect(validateTicket({ ...valid, description: 'Too short' })).toHaveProperty('description')
  })

  it('rejects an invalid priority', () => {
    expect(validateTicket({ ...valid, priority: 'urgent' })).toHaveProperty('priority')
  })

  it('rejects an invalid email', () => {
    expect(validateTicket({ ...valid, requester_email: 'not-an-email' })).toHaveProperty(
      'requester_email',
    )
  })
})

describe('allowedNext (FR-07, FR-08, BR-02)', () => {
  it('follows the workflow', () => {
    expect(allowedNext('open')).toEqual(['in_progress'])
    expect(allowedNext('in_progress')).toEqual(['resolved'])
    expect(allowedNext('resolved')).toEqual(['closed', 'open'])
  })

  it('allows nothing after closed', () => {
    expect(allowedNext('closed')).toEqual([])
  })
})

describe('isValidEmail', () => {
  it('checks the format', () => {
    expect(isValidEmail('agent@example.com')).toBe(true)
    expect(isValidEmail('agent')).toBe(false)
  })
})
