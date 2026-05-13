import express from 'express'
import cors from 'cors'
import routes from './routes.js'
import path from 'path'
import { fileURLToPath } from 'url'
import { dirname } from 'path'

const app = express()
const __filename = fileURLToPath(import.meta.url)
const __dirname = dirname(__filename)

app.use(cors())
app.use(express.json())
app.use('/api', routes)

// Serve a raiz do projeto como estático (index.html, style.css, js/)
app.use(express.static(path.join(__dirname, '..')))
app.use('/uploads', express.static(path.join(__dirname, '../uploads')))

export default app