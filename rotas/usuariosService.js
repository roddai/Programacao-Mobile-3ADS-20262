import { request } from './api.js'

// ── Auth ──────────────────────────────────────────────────
export const authApi = {
  login:     (body) => request('/auth/login',    { method: 'POST', body: JSON.stringify(body) }),
  cadastrar: (body) => request('/auth/register', { method: 'POST', body: JSON.stringify(body) }),
  logout:    ()     => request('/auth/logout',   { method: 'POST' }),
}

// ── CRUD Usuários (para o dashboard do admin) ─────────────
export const usuariosApi = {
  listar:    ()         => request('/users'),
  buscar:    (id)       => request(`/users/${id}`),
  atualizar: (id, body) => request(`/users/${id}`, { method: 'PUT',    body: JSON.stringify(body) }),
  remover:   (id)       => request(`/users/${id}`, { method: 'DELETE' }),
}

//Fornecedor
export const fornecedoresApi = {
  listar: async () => {
    const res = await fetch('/fornecedores', {
      headers: { Authorization: 'Bearer ' + localStorage.getItem('token') }
    })
    return res.json()
  },

  criar: async (dados) => {
    const res = await fetch('/fornecedores', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        Authorization: 'Bearer ' + localStorage.getItem('token')
      },
      body: JSON.stringify(dados)
    })
    return res.json()
  },

  remover: async (id) => {
    await fetch(`/fornecedores/${id}`, {
      method: 'DELETE',
      headers: { Authorization: 'Bearer ' + localStorage.getItem('token') }
    })
  }
}

export const produtosApi = {
  listar: async () => {
    const res = await fetch('/produtos', {
      headers: { Authorization: 'Bearer ' + localStorage.getItem('token') }
    })
    return res.json()
  },

  criar: async (dados) => {
    const res = await fetch('/produtos', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        Authorization: 'Bearer ' + localStorage.getItem('token')
      },
      body: JSON.stringify(dados)
    })
    return res.json()
  }
}
