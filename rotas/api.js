import { API_BASE_URL } from './config.js'

// ── Helpers de UI ─────────────────────────────────────────
export function showLoading(on) {
  const el = document.getElementById('loading')
  if (el) el.hidden = !on
}

export function showErro(msg) {
  const el = document.getElementById('msgErro')
  if (!el) return
  el.textContent = msg || ''
  el.hidden = !msg
  if (msg) setTimeout(() => { el.textContent = ''; el.hidden = true }, 5000)
}

export function showOk(msg) {
  const el = document.getElementById('msgOk')
  if (!el) return
  el.textContent = msg || ''
  el.hidden = !msg
  if (msg) setTimeout(() => { el.textContent = ''; el.hidden = true }, 4000)
}

// ── Cliente HTTP genérico ─────────────────────────────────
export async function request(path, options = {}) {
  showLoading(true)
  try {
    const token = localStorage.getItem('token')
    const headers = { 'Content-Type': 'application/json', ...(options.headers || {}) }
    if (token) headers['Authorization'] = `Bearer ${token}`

    const res = await fetch(`${API_BASE_URL}${path}`, { ...options, headers })
    const text = await res.text()
    const data = text ? JSON.parse(text) : null

    if (!res.ok) {
      const msg = (data && (data.message || data.error)) || `Erro ${res.status}`
      throw new Error(msg)
    }
    return data
  } finally {
    showLoading(false)
  }
}