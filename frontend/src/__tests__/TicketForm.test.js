import { mount } from '@vue/test-utils'
import { describe, expect, it, vi } from 'vitest'
import TicketForm from '../components/TicketForm.vue'

describe('TicketForm (US-01, US-02)', () => {
  it('shows errors and does not send an empty form', async () => {
    const submit = vi.fn().mockResolvedValue(true)
    const wrapper = mount(TicketForm, { props: { submit } })

    await wrapper.find('[data-test="ticket-form"]').trigger('submit')

    expect(submit).not.toHaveBeenCalled()
    expect(wrapper.find('[data-test="error-title"]').exists()).toBe(true)
    expect(wrapper.find('[data-test="error-description"]').exists()).toBe(true)
    expect(wrapper.find('[data-test="error-email"]').exists()).toBe(true)
  })

  it('sends a valid ticket', async () => {
    const submit = vi.fn().mockResolvedValue(true)
    const wrapper = mount(TicketForm, { props: { submit } })

    await wrapper.find('#title').setValue('Laptop will not start')
    await wrapper.find('#description').setValue('The laptop shows a black screen after the logo.')
    await wrapper.find('#priority').setValue('high')
    await wrapper.find('#email').setValue('employee@example.com')
    await wrapper.find('[data-test="ticket-form"]').trigger('submit')

    expect(submit).toHaveBeenCalledWith({
      title: 'Laptop will not start',
      description: 'The laptop shows a black screen after the logo.',
      priority: 'high',
      requester_email: 'employee@example.com',
    })
  })

  it('shows an error returned by the API', () => {
    const wrapper = mount(TicketForm, {
      props: { submit: vi.fn(), serverError: 'title must not be blank' },
    })

    expect(wrapper.find('[data-test="server-error"]').text()).toContain('title must not be blank')
  })
})
