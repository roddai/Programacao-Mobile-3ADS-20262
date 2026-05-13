import { Router } from 'express'
import upload from './uploadConfig.js'
import { verifyTokenMiddleware } from './middlewares/authMiddleware.js'

import {
    register, login, logout, forgotPassword
} from './controllers/authController.js'

import {
    getUsers, getUserById, createUser, updateUser, deleteUser,
    profile, updateMe
} from './controllers/useController.js'

import {
  getFornecedores,
  getFornecedorById,
  createFornecedor,
  updateFornecedor,
  deleteFornecedor
} from './controllers/fornecedorController.js'

const r = Router()

// AUTH
r.post('/auth/register', register)
r.post('/auth/login', login)
r.post('/auth/forgot-password', forgotPassword)
r.post('/auth/logout', verifyTokenMiddleware, logout)

// USERS
r.get('/users/profile', verifyTokenMiddleware, profile)
r.put('/users/me', verifyTokenMiddleware, updateMe)

r.get('/users', getUsers)
r.get('/users/:id', verifyTokenMiddleware, getUserById)
r.post('/users', verifyTokenMiddleware, upload.single('image'), createUser)
r.put('/users/:id', verifyTokenMiddleware, upload.single('image'), updateUser)
r.delete('/users/:id', verifyTokenMiddleware, deleteUser)

r.get('/fornecedores', verifyTokenMiddleware, getFornecedores)
r.get('/fornecedores/:id', verifyTokenMiddleware, getFornecedorById)
r.post('/fornecedores', verifyTokenMiddleware, createFornecedor)
r.put('/fornecedores/:id', verifyTokenMiddleware, updateFornecedor)
r.delete('/fornecedores/:id', verifyTokenMiddleware, deleteFornecedor)

export default r