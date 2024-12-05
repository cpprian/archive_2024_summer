<!DOCTYPE html>
<html>
<head>
  <title>Ecodom+</title>
  <link rel="preconnect" href="https://fonts.googleapis.com">
  <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
  <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap" rel="stylesheet">
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
    <?php
      $conn = mysqli_connect('localhost', 'root', '', 'ecodomDB');
      if ($conn->connect_error) {
          echo "Nie udało się połączyć z bazą danych: " . mysqli_connect_error();
          exit();
      }
    ?>
      <div class="filter">
        <form method="post" action="all_devices.php">
          <select name="Choose your room" class="filter_select">
            <?php
              $query = "SELECT id, nazwa FROM Pomieszczenia;";
              $stmt = mysqli_prepare($conn, $query);
              mysqli_stmt_execute($stmt);
              $result = mysqli_stmt_get_result($stmt);

              echo "<option value='0'>Choose your room</option>";
              while ($row = mysqli_fetch_assoc($result)) {
                  echo "<option value='" . $row["id"] . "'>" . $row["nazwa"] . "</option>";
              }
            ?>
          </select>
          <select name="Filter by" class="filter_select">
            <option value="0">Filter by</option>
            <option value="1">Power usage</option>
            <option value="2">Electricity cost</option>
            <option value="3">Room</option>
          </select>
          <select name="Sort by" class="filter_select">
            <option value="0">Sort by</option>
            <option value="1">Increasing</option>
            <option value="2">Decreasing</option>
          </select>
          <button type="submit" name="filtruj" class="icon_button" title="Filtruj">
            <i class="fas fa-filter"></i>
          </button>
        </form>
      </div>
      <?php
        if ($_SERVER["REQUEST_METHOD"] == "POST") {
          if (isset($_POST["usun_urzadzenie"])) {
              $urzadzenie_id = $_POST["urzadzenie_id"];
      
              $conn = mysqli_connect('localhost', 'root', '', 'ecodomDB');
              if (!$conn) {
                  echo "Nie udało się połączyć z bazą danych: " . mysqli_connect_error();
                  exit();
              }
      
              $query = "DELETE FROM Urzadzenia WHERE id = ?;";
              $stmt = mysqli_prepare($conn, $query);
              mysqli_stmt_bind_param($stmt, "i", $urzadzenie_id);
              mysqli_stmt_execute($stmt);
          } 

          if (isset($_POST["filtruj"])) {
            $room = $_POST["Choose_your_room"];
            $filter = $_POST["Filter_by"];
            $sort = $_POST["Sort_by"];

            if ($room != 0) {
              $query_where .= " WHERE p.id = " . $room;
            }
        
            if ($filter != 0) {
              switch ($filter) {
                case 1:
                  $filter_query = "u.moc";
                  break;
                case 2:
                  $filter_query = "ob.suma_kosztow";
                  break;
                case 3:
                  $filter_query = "pnazwa";
                  break;
                default:
                  $filter_query = "";
                  break;
              }
            }
        
            if ($sort != 0 && $filter_query != "") {
              $sort_query = " ORDER BY ";
              switch ($sort) {
                case 1:
                  $sort_query .= "$filter_query ASC";
                  break;
                case 2:
                  $sort_query .= "$filter_query DESC";
                  break;
                default:
                  $sort_query = "";
                  break;
            }
          }
        }

        if (isset($_POST['turn_off_on'])) {
            $idu = $_POST['id_urzadzenia'];
            $currentDate = date("Y-m-d");
            $currentTime = date("H:i:s");
            var_dump($idu, $currentDate, $currentTime);
            if (!returnIsTurnedOn($idu)) {
               // on
              $query = "INSERT INTO ZuzycieEnergii (id_urzadzenia, `data`, godzina, zuzycie) VALUES ($idu, '$currentDate', '$currentTime', NULL);";
              mysqli_query($conn, $query);
            } else {
              // off
              $query = "UPDATE ZuzycieEnergii set zuzycie = TIMEDIFF('$currentTime', godzina) / 10 where id_urzadzenia = $idu and zuzycie is NULL;";
              var_dump($query);
              mysqli_query($conn, $query);
            }
        }
      }

        $query = "SELECT p.id AS pid, p.nazwa AS pnazwa, u.id AS uid, u.nazwa AS unazwa, u.moc, u.harmonogram, ob.suma_kosztow
                  FROM Urzadzenia AS u LEFT JOIN Pomieszczenia AS p ON u.id_pomieszczenia = p.id 
                  LEFT JOIN (SELECT ze.id_urzadzenia, SUM(
                    CASE 
                      WHEN ze.godzina BETWEEN '07:00:00' AND '21:59:59' THEN (kp.taryfa_dzienna + kp.koszt_jednostkowy) * ze.zuzycie
                      ELSE (kp.taryfa_nocna + kp.koszt_jednostkowy) * ze.zuzycie
                    END) AS suma_kosztow
                  FROM ZuzycieEnergii AS ze JOIN KosztyPradu AS kp ON ze.data = kp.data
                  GROUP BY ze.id_urzadzenia) AS ob ON ob.id_urzadzenia = u.id
                  $query_where
                  $sort_query;";
        $stmt = mysqli_prepare($conn, $query);
        mysqli_stmt_execute($stmt);
        $result = mysqli_stmt_get_result($stmt);


        if (mysqli_num_rows($result) > 0) {
            while ($row = mysqli_fetch_assoc($result)) {
                echo '<div class="urzadzenie">';
                echo '<div class="urzadzenie_info">';
                echo getIcon($row['pid']);
                echo '<p>' . $row['unazwa'] . '</p>';
                echo '</div>';
                echo '<div class="urzadzenie_info">';
                echo '<form method="post" action="all_devices.php">
                        <input type="hidden" name="id_urzadzenia" value="' . $row['uid'] . '">
                        <button type="submit" name="turn_off_on" class="icon_button" title="Usuń urządzenie">
                          <i class="fas ' . getTurnedOnOffIcon($row['uid']) .'"></i>
                        </button>
                      </form>';
                echo '</div>';
                echo '<div class="urzadzenie_info">';
                echo '<h3>Room</h3>';
                echo '<p>' . $row['pnazwa'] . '</p>';
                echo '</div>';
                echo '<div class="urzadzenie_info">';
                echo '<h3>Power usage (kWh)</h3>';
                echo '<p>' . $row['moc']/1000 . '</p>';
                echo '</div>';
                echo '<div class="urzadzenie_info">';
                echo '<h3>Electricity cost (PLN)</h3>';
                echo '<p>' . round($row['suma_kosztow'], 2) . '</p>';
                echo '</div>';
                echo '<div class="urzadzenie_info">';
                echo '<form method="post" action="all_devices.php">';
                echo '<input type="hidden" name="urzadzenie_id" value="' . $row['uid'] . '">';
                echo '<button type="submit" name="usun_urzadzenie" class="icon_button" title="Usuń urządzenie">
                        <i class="fas fa-trash"></i>
                      </button>';
                echo '</form>';
                echo '</div>';
                echo '</div>';
            }
        } else {
            echo '<h1>Brak urządzeń w bazie danych.</h1>';
        }

        mysqli_close($conn);
      ?>
    </div>
  </div>
</body>
</html>

<?php
function getIcon($id_pomieszczenia) {
    switch ($id_pomieszczenia) {
        case 1:
            return '<i class="fas fa-television"></i>';
        case 2:
            return '<i class="fas fa-cutlery"></i>';
        case 3:
            return '<i class="fas fa-bed"></i>'; 
        case 4:
            return '<i class="fas fa-bathtub"></i>'; 
        default:
            return '<i class="fas fa-laptop"></i>';
    }
}

function getTurnedOnOffIcon($id) {
  switch (returnIsTurnedOn($id)) {
    case 0:
      return 'fa-toggle-on';
    default:
      return 'fas fa-toggle-off';
  }
}

function returnIsTurnedOn($id) {
  include 'db_connection.php';

  $query = "SELECT COUNT(u.id) FROM Urzadzenia as u INNER JOIN ZuzycieEnergii as ze on u.id = ze.id_urzadzenia
  where u.id = $id AND ze.zuzycie is NULL;";

  mysqli_query($conn, $query);
  $result = mysqli_query($conn, $query);
  $row = mysqli_fetch_assoc($result);

  if ($row['COUNT(u.id)'] == 0) {
    return 0; // on
  } else {
    return 1; // off
  }
}
?>