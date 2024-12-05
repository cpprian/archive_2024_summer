<!DOCTYPE html>
<html>
<head>
    <title>My Daily Activities</title>
</head>
<head>
    <title>Menu Boczne</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link rel="stylesheet" href="style.css">
<body>
<div class="container">
    <div class="menu">
        <ul>
            <li><a href="index.php"><i class="fas fa-home"></i>Dashboard</a></li>
            <li><a href="all_devices.php"><i class="fas fa-desktop"></i>All Devices</a></li>
            <li><a href="photovoltaics.php"><i class="fas fa-plug"></i>Photovoltaics</a></li>
            <li><a href="device.php"><i class="fas fa-plus-circle"></i>Add device</a></li>
            <li><a href="room.php"><i class="fas fa-plus-circle"></i>Add room</a></li>
        </ul>
    </div>
    <div class="content">
        <div style="text-align: center;">
            <img src="images/chart.PNG" alt="Description of the image" width="492" style="display: block; margin: auto; margin-bottom: 50px;">
        </div>
        <div>

        </div>
        <style>
            form {
                display: flex;
                flex-direction: column;
                align-items: center;
                margin-top: 20px;
            }

            label {
                font-weight: bold;
            }

            input, textarea {
                padding: 10px;
                margin-bottom: 20px;
                border: 1px solid #ccc;
                border-radius: 5px;
                width: 300px;
                font-size: 16px;
            }

            textarea {
                height: 150px;
            }

            input[type="submit"] {
                background-color: #4CAF50;
                color: white;
                border: none;
                border-radius: 5px;
                padding: 10px 20px;
                cursor: pointer;
                font-size: 16px;
            }

            input[type="submit"]:hover {
                background-color: #3e8e41;
            }
        </style>
        <?php


        if ($_SERVER['REQUEST_METHOD'] === 'POST') {
            $model = $_POST['model_panelu'];
            $wyd = $_POST['srednia_wydajnosc'];
            $dat = $_POST['dat'];
            $poj = $_POST['poj'];
            $pcnt= $_POST['pcnt'];
            $pow = $_POST['pow'];

            $conn = mysqli_connect('localhost', 'root', '', 'ecodomDB');
            if ($conn->connect_error) {
                echo "Nie udało się połączyć z bazą danych: " . mysqli_connect_error();
                exit();
            }

            $query = "INSERT INTO ecodomDB.PanelFotowoltaiczny
            (powierzchnia_paneli, ilosc_paneli, pojemnosc_akumulatorow, data_zamontowania, srednia_wydajnosc, model_panelu)
            VALUES 
                ($pow, $pcnt, $poj, '$dat', $wyd, '$model');";

            mysqli_query($conn, $query);

            echo '<div class="panel" style="background-color: #000000; color: #dccccc; border: 1px solid #3a3333; border-radius: 5px; box-shadow: 0 2px 5px rgba(0,0,0,0.1); display: inline-block; padding: 20px; width: 450px;">';
            echo '<div class="panel-container" style="text-align: center;">';
            echo '<h2>Dodaj kolejny panel</h2>';
            echo '</div>';
            echo '<form method="POST">';

            echo '<label for="model_panelu">Nazwa panelu:</label>';
            echo '<input type="text" id="model" name="model_panelu"><br>';

            echo '<label>Średnia wydajnosc:</label>';
            echo '<input type="text" id="wyd" name="srednia_wydajnosc"><br>';

            echo '<label>Data zamontowania:</label>';
            echo '<input type="text" id="dat" name="dat"><br>';

            echo '<label>Pojemnosc akumulatorow:</label>';
            echo '<input type="text" id="poj" name="poj"><br>';

            echo '<label>Ilosc paneli:</label>';
            echo '<input type="text" id="pcnt" name="pcnt"><br>';

            echo '<label>Powierzchnia paneli</label>';
            echo '<input type="text" id="pow" name="pow"><br>';

            echo '<input type="submit" value="Send">';
            echo '</form>';
            echo '</div>';
        }
        else {
            echo '<div class="panel" style="background-color: #000000; color: #dccccc; border: 1px solid #3a3333; border-radius: 5px; box-shadow: 0 2px 5px rgba(0,0,0,0.1); display: inline-block; padding: 20px; width: 450px;">';
            echo '<div class="panel-container" style="text-align: center;">';
            echo '<h2>Dodaj kolejny panel</h2>';
            echo '</div>';
            echo '<form method="POST">';

            echo '<label for="model_panelu">Nazwa panelu:</label>';
            echo '<input type="text" id="model" name="model_panelu" required><br>';

            echo '<label>Średnia wydajnosc:</label>';
            echo '<input type="text" id="wyd" name="srednia_wydajnosc" required><br>';

            echo '<label>Data zamontowania:</label>';
            echo '<input type="text" id="dat" name="dat" required><br>';

            echo '<label>Pojemnosc akumulatorow:</label>';
            echo '<input type="text" id="poj" name="poj" required><br>';

            echo '<label>Ilosc paneli:</label>';
            echo '<input type="text" id="pcnt" name="pcnt" required><br>';

            echo '<label>Powierzchnia paneli</label>';
            echo '<input type="text" id="pow" name="pow" required><br>';

            echo '<input type="submit" value="Send">';
            echo '</form>';
            echo '</div>';
        }

        echo '<div style="margin-top: 20px;">';
        // Bo się rozjeżdża
        echo '</div>';

        $conn = mysqli_connect('localhost', 'root', '', 'ecodomDB');
        if ($conn->connect_error) {
            echo "Nie udało się połączyć z bazą danych: " . mysqli_connect_error();
            exit();
        }

        $query = "SELECT *, DATEDIFF(CURDATE(), data_zamontowania) as dif FROM PanelFotowoltaiczny";
        $result = mysqli_query($conn, $query);
        $it = 1;

        // Generowanie komponentów HTML na podstawie danych z bazy
        if (mysqli_num_rows($result) > 0) {
            while ($row = mysqli_fetch_assoc($result)) {
                $rng = rand(0, 2);

                echo '<div class="panel">';
                echo '<div class="panel-container"><h2>Panel number #' . $it . '</h2><img src="images/icon' .
                    $rng . '.png" alt="icon" class="panel-icon" style="padding-left: 100px;"></div>';
                echo '<div class="panel-info">';
                echo '<p>Model panelu: ' . $row['model_panelu'] . '</p>';
                echo '<p>Powierzchnia paneli: ' . $row['powierzchnia_paneli'] . '</p>';
                echo '<p>Ilość paneli: ' . $row['ilosc_paneli'] . '</p>';
                echo '<p>Pojemność akumulatorów: ' . $row['pojemnosc_akumulatorow'] . '</p>';
                echo '<p>Łącznie zaoszczędzono około: ' . $row['dif'] * 24 / 100  . ' PLN' . '</p>';
                echo '</div>';
                echo '</div>';
                $it += 1;
            }
        } else {
            echo '<h1>Twój dom nie posiada paneli słonecznych.</h1>';
        }

        mysqli_close($conn);
        ?>
    </div>
</div>
</body>
</html>
