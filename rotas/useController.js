import {pool} from '../db.js'
import fs from 'fs'
import bcrypt from 'bcrypt'
import path from 'path'

//Criar Usuário
export async function createUser(req, res){
    const {name, email} = req.body
    if(!name || !email) {
        fs.unlink(req.file.path, ()=>{})
        return res.status(400).json({error: 'Nome e Email são obrigatórios'})
    }
    const imgPath = req.file ? 'uploads/' + path.basename(req.file.path) : null
    try{
        const [result] = await pool.query(
            'INSERT INTO users (name, email, img) VALUES (?,?,?)',
            [name, email, imgPath]
        )
        res.status(201).json({id: result.insertId, img: imgPath})
    } catch (err){
        if(req.file) fs.unlink(req.file.path, ()=>{})
        if(err.code === 'ER_DUP_ENTRY'){
            return res.status(409).json({error: 'Email já cadastrado'})
        }
        res.status(500).json({error: 'Erro ao criar usuário'})
    }
}
//Listar Todos
export async function getUsers(_, res){
    try{
        const [rows] = await pool.query(
            'SELECT id, name, email, img, created_at FROM users ORDER BY id DESC'
        )
        res.json(rows)
    } catch {
        res.status(500).json({error: 'Erro ao listar users'})
    }
}
//Buscar por ID
export async function getUserById(req, res){
    const {id} = req.params
    try{
        const [rows] = await pool.query(
            'SELECT id, name, email, img, created_at FROM users WHERE id= ?',
            [id]
        )
        if(rows.length === 0){
            return res.status(404).json({error: 'Usuário não encontrado'})
        }
        res.json(rows[0])
    } catch {
        res.status(500).json({error: 'Erro ao Buscar Usuário'})
    }
}
//Rota do Tipo PUT (Alterar)
export async function updateUser(req, res){
    const {id} = req.params
    const {name, email} = req.body
    if(!name || !email){
        return res.status(400).json({error:'Nome e email são obrigatórios!'})
    }
    try{
        const [rows] = await pool.query(
            'SELECT * FROM users WHERE id =?',
            [id]
        )
        if(rows.length === 0)
            return res.status(404).json({error:'usuário não encontrado'})
        const oldImg = rows[0].img
        const newImg = req.file ? 'uploads/' + path.basename(req.file.path) : oldImg
        const[result] = await pool.query(
            'UPDATE users SET name =?, email=?, img=? WHERE id=?',
            [name, email, newImg, id]
        )
        if(result.affectedRows === 0)
            return res.status(404).json({error:'usuário não encontrado'})
        if(req.file && oldImg)
            fs.unlink(oldImg, err =>{if(err) console.warn('Remover:', err)})
        const[updated] = await pool.query(
            'SELECT id, name, email, img, created_at FROM users WHERE id=?',
            [id]
        )
        res.json(updated[0])
    } catch(err){
        if(err.code === 'ER_DUP_ENTRY'){
            return res.status(409).json({error:'Email já cadastrado'})
        }
        res.status(500).json({error:'Erro ao atualizar Usuário'})
    }
}
//Rota do Tipo DELETE
export async function deleteUser(req, res){
    const {id} = req.params
    if(parseInt(id, 10) !==req.user.id)
        return res.status(403).json({error: 'Você só pode deletar a própria conta'})
    try{
        const [rows] = await pool.query('SELECT * FROM users WHERE id =?', [id])
        if(rows.length === 0)
            return res.status(404).json({error:'Usuário não encontrado!'})
        const imgPath = rows[0].img
        await pool.query(
            'DELETE FROM users WHERE id =?', [id]
        )
        if(imgPath)
            fs.unlink(imgPath, err =>{if (err) console.warn('Remover:', err)})
        res.json({message:'Usuário excluído com sucesso'})
    } catch {
        res.status(500).json({error:'Erro ao excluir usuário'})
    }
}
//Perfil do Usuário Logado
export async function profile(req, res) {
    try{
        const [rows] = await pool.query(
            'SELECT id, name, email, img, created_at FROM users WHERE id=?',
            [req.user.id]
        )
        if(!rows.length)
            return res.status(404).json({error: 'Usuário não encontrado'})
        res.json(rows[0])
    } catch{
        res.status(500).json({error: 'Erro ao buscar o perfil'})
    }
}
//UpdateMe
export async function updateMe(req, res) {
    const{name, currentPassword, newPassword} = req.body
    if(!name && !newPassword)
        return res.status(400).json({error:'Envie ao menos o name ou newPassword'})
    try{
        const [rows] = await pool.query(
            'SELECT * FROM users WHERE id=?',
            [req.user.id]
        )
        if(!rows.length)
            return res.status(404).json({error:'Usuário não encontrado'})
        const user = rows[0]
        const updates = []
        const params =[]
        if(name){// inserir este bloco
            updates.push('name=?')
            params.push(name)
        }
        if(newPassword){
            if(!currentPassword)
                return res.status(400).json({error:'Envie currentPassowrd para trocar a senha'})
            const match = await bcrypt.compare(currentPassword, user.password)
            if(!match)
                return res.status(401).json({error: 'Senha Atual Incorreta'})
            const hash = await bcrypt.hash(newPassword, 10)
            updates.push('password=?')
            params.push(hash)
        }
        params.push(req.user.id)
        await pool.query(
         `UPDATE users SET ${updates.join(', ')} WHERE id=?`, params   
        )
        const [fresh] = await pool.query(
            'SELECT id, name, email, img, created_at From users WHERE id=?',
            [req.user.id]
        )
        res.json(fresh[0])
    } catch(err){
        console.log('ERRO', err.message)
        res.status(500).json({error: 'Erro ao atualizar'})
    }
}