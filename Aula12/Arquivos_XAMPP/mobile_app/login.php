<?php
include 'db_config.php';

header('Content-Type: application/json');

$email = $_POST['email'] ?? '';
$senha = $_POST['senha'] ?? '';

if (empty($email) || empty($senha)) {
    echo json_encode(array("status" => "erro", "mensagem" => "Informe o e-mail e a senha."));
    exit();
}

try {
    $sql = "SELECT usu_id, usu_nome, usu_password FROM usuario WHERE usu_email = :email";
    $stmt = $conn->prepare($sql);
    $stmt->bindParam(':email', $email);
    $stmt->execute();

    if ($stmt->rowCount() > 0) {
        $usuario = $stmt->fetch(PDO::FETCH_ASSOC);
        
        // Valida a senha criptografada
        if (password_verify($senha, $usuario['usu_password'])) {
            echo json_encode(array(
                "status" => "sucesso", 
                "mensagem" => "Autenticado com sucesso!",
                "usuario" => array("id" => $usuario['usu_id'], "nome" => $usuario['usu_nome'])
            ));
        } else {
            echo json_encode(array("status" => "erro", "mensagem" => "Senha incorreta."));
        }
    } else {
        echo json_encode(array("status" => "erro", "mensagem" => "E-mail não encontrado."));
    }

} catch (PDOException $e) {
    echo json_encode(array("status" => "erro", "mensagem" => "Erro no servidor: " . $e->getMessage()));
}
?>