<?php
    $conn = mysqli_connect('localhost', 'root', '', 'ecodomDB');
    if ($conn->connect_error) {
        echo "Nie udało się połączyć z bazą danych: " . mysqli_connect_error();
        exit();
    }
?>