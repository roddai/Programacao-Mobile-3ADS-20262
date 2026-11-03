<?php
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "mobile_app_teste";

// Cria a conexão usando PDO para maior segurança contra SQL Injection
try {
    $conn = new PDO("mysql:host=$servername;dbname=$dbname;charset=utf8", $username,$password);
    // Configura o modo de erro do PDO para exceção
    $conn->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
} catch(PDOException $e) {
    echo json_encode(array("status" => "erro", "mensagem" => "Falha na conexão: " . $e->getMessage()));
    exit();
}
?>