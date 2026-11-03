<?php
include 'db_config.php';

header('Content-Type: application/json');

// Obtém os dados enviados via POST (JSON ou Form-Data)
$nome = $_POST['nome'] ?? '';
$email = $_POST['email'] ?? '';
$senha = $_POST['senha'] ?? '';

if (empty($nome) || empty($email) || empty($senha)) {
    echo json_encode(array("status" => "erro", "mensagem" => "Preencha todos os campos."));
    exit();
}

try {
    // Verifica se o e-mail já existe
    $sqlCheck = "SELECT usu_id FROM usuario WHERE usu_email = :email";
    $stmtCheck = $conn->prepare($sqlCheck);
    $stmtCheck->bindParam(':email', $email);
    $stmtCheck->execute();

    if ($stmtCheck->rowCount() > 0) {
        echo json_encode(array("status" => "erro", "mensagem" => "Este e-mail já está cadastrado."));
        exit();
    }

    // Criptografa a senha para segurança
    $senhaHash = password_hash($senha, PASSWORD_DEFAULT);

    // Insere o novo usuário
    $sqlInsert = "INSERT INTO usuario (usu_nome, usu_email, usu_password) VALUES (:nome, :email, :senha)";
    $stmtInsert = $conn->prepare($sqlInsert);
    $stmtInsert->bindParam(':nome', $nome);
    $stmtInsert->bindParam(':email', $email);
    $stmtInsert->bindParam(':senha', $senhaHash);
    $stmtInsert->execute();

    echo json_encode(array("status" => "sucesso", "mensagem" => "Usuário cadastrado com sucesso!"));

} catch (PDOException $e) {
    echo json_encode(array("status" => "erro", "mensagem" => "Erro no banco de dados: " . $e->getMessage()));
}
?>