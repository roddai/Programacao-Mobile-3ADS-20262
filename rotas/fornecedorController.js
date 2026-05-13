import { pool } from '../db.js'

// Criar fornecedor
export async function createFornecedor(req, res) {
  const { nome, cnpj, email, telefone } = req.body

  if (!nome || !cnpj) {
    return res.status(400).json({ error: 'Nome e CNPJ são obrigatórios' })
  }

  try {
    const [result] = await pool.query(
      'INSERT INTO fornecedores (nome, cnpj, email, telefone) VALUES (?, ?, ?, ?)',
      [nome, cnpj, email, telefone]
    )

    res.status(201).json({ id: result.insertId })
  } catch (err) {
    if (err.code === 'ER_DUP_ENTRY') {
      return res.status(409).json({ error: 'CNPJ já cadastrado' })
    }

    res.status(500).json({ error: 'Erro ao criar fornecedor' })
  }
}

// Listar todos
export async function getFornecedores(_, res) {
  try {
    const [rows] = await pool.query(
      'SELECT * FROM fornecedores ORDER BY id DESC'
    )
    res.json(rows)
  } catch {
    res.status(500).json({ error: 'Erro ao listar fornecedores' })
  }
}

// Buscar por ID
export async function getFornecedorById(req, res) {
  const { id } = req.params

  try {
    const [rows] = await pool.query(
      'SELECT * FROM fornecedores WHERE id = ?',
      [id]
    )

    if (rows.length === 0) {
      return res.status(404).json({ error: 'Fornecedor não encontrado' })
    }

    res.json(rows[0])
  } catch {
    res.status(500).json({ error: 'Erro ao buscar fornecedor' })
  }
}

// Atualizar
export async function updateFornecedor(req, res) {
  const { id } = req.params
  const { nome, cnpj, email, telefone } = req.body

  if (!nome || !cnpj) {
    return res.status(400).json({ error: 'Nome e CNPJ são obrigatórios' })
  }

  try {
    const [result] = await pool.query(
      'UPDATE fornecedores SET nome=?, cnpj=?, email=?, telefone=? WHERE id=?',
      [nome, cnpj, email, telefone, id]
    )

    if (result.affectedRows === 0) {
      return res.status(404).json({ error: 'Fornecedor não encontrado' })
    }

    res.json({ message: 'Fornecedor atualizado' })
  } catch (err) {
    if (err.code === 'ER_DUP_ENTRY') {
      return res.status(409).json({ error: 'CNPJ já cadastrado' })
    }

    res.status(500).json({ error: 'Erro ao atualizar fornecedor' })
  }
}

// Deletar
export async function deleteFornecedor(req, res) {
  const { id } = req.params

  try {
    const [result] = await pool.query(
      'DELETE FROM fornecedores WHERE id=?',
      [id]
    )

    if (result.affectedRows === 0) {
      return res.status(404).json({ error: 'Fornecedor não encontrado' })
    }

    res.json({ message: 'Fornecedor removido' })
  } catch {
    res.status(500).json({ error: 'Erro ao deletar fornecedor' })
  }
}