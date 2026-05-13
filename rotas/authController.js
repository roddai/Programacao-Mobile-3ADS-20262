import {pool} from '../db.js'
import bcrypt from 'bcrypt'
import { createToken, denyToken } from '../services/tokenService.js'

const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/
const sanitizeUser = (u) =>({
    id: u.id, name: u.name, email: u.email
})
//Criar conta com Senha
export async function register(req, res) {
    const {name, email, password} = req.body
    if(!name || !email || !password)
        return res.status(400).json({error: 'Name, email e Password são Obrigatórios'})
    if(!emailRegex.test(email))
        return res.status(400).json({error:'Email Inválido'})
    try{
        const hash = await bcrypt.hash(password, 10)
        const [result] = await pool.query(
            'INSERT INTO users (name, email, password) VALUES(?, ?, ?)',
            [name, email, hash]
        )
        res.status(201).json({id: result.insertId, name, email})
    } catch (err){
        if(err.code === 'ER_DUP_ENTRY')
            return res.status(409).json({error: 'Email já Cadastrado'})
        res.status(500).json({error: 'Erro ao registrar o usuário'})
    }
}
//Autenticar e retornar o Token
export async function login(req, res) {
    const {email, password}=req.body
    if(!email || !password)
        return res.status(400).json({error: 'Email e Password Obrigatórios'})
    try{
        const[rows] = await pool.query(
            'SELECT * FROM users WHERE email =?', 
            [email]
        )
        if(!rows.length)
            return res.status(401).json({error:'Credenciais Inválidas'})
        const user = rows[0]
        const match = await bcrypt.compare(password, user.password)

        if(!match)
            return res.status(401).json({error:'Credenciais Inválidas'})
        const {token} = createToken({id: user.id})
        res.json({token, user: sanitizeUser(user)})
    } catch {
        res.status(500).json({error: 'Erro no Login'})
    }
}
//Revogar o token
export async function logout(req, res) {
    try{
        denyToken(req.user.jti)
        res.json({message: 'Logout realizado com sucesso'})
    } catch(err){
        console.log('Erro', err.message)
        res.status(500).json({error: 'Erro no Logout'})
    }
}
//Redefinir a Senha
export async function forgotPassword(req, res) {
    const{email, newPassword} = req.body
    if(!email || !newPassword)
        return res.json(400).json({error:'email e newPassword são obrigatórios'})
    try{
        const hash = await bcrypt.hash(newPassword, 10)
        await pool.query(
            'UPDATE users SET password = ? WHERE email =?', 
            [hash, email]
        )
        res.json({message: 'Se o email existir, a senha foi redefinida'})
    } catch {
        res.status(500).json({error:'Erro ao redefinir senha'})
    }
}