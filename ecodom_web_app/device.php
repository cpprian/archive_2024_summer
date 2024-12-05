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
      <div class="add_component_form">
        <h1>Add Device</h1>
        <form method="post" action="device.php">
          <label for="id_pomieszczenia">Room Name:</label>
          <select name="Choose your room" class="filter_select add_device_select" required>
            <?php
              $conn = mysqli_connect('localhost', 'root', '', 'ecodomDB');
              if ($conn->connect_error) {
                  echo "Nie udało się połączyć z bazą danych: " . mysqli_connect_error();
                  exit();
              }
              $query = "SELECT id, nazwa FROM Pomieszczenia;";
              $stmt = mysqli_prepare($conn, $query);
              mysqli_stmt_execute($stmt);
              $result = mysqli_stmt_get_result($stmt);

              echo "<option value=''>Choose your room</option>";
              while ($row = mysqli_fetch_assoc($result)) {
                  echo "<option value='" . $row["id"] . "'>" . $row["nazwa"] . "</option>";
              }

              mysqli_stmt_close($stmt);
              mysqli_close($conn);
            ?>
          </select></br></br>
          <label for="nazwa">Name:</label>
          <input type="text" id="nazwa" name="nazwa" required><br><br>
          <label for="moc">Power:</label>
          <input type="number" id="moc" name="moc" step="0.01" required><br><br>
          <div class="harmonogram_form">
          <label for="harmonogram">Schedule:</label>
              <select name="harmonogram_start" class="filter_select add_device_select" required>
              <option value="">Starting day</option>
              <option value="PN">PN</option>
              <option value="WT">WT</option>
              <option value="SR">SR</option>
              <option value="CZ">CZ</option>
              <option value="PT">PT</option>
              <option value="SB">SB</option>
              <option value="ND">ND</option>
            </select>
            <select name="harmonogram_end" class="filter_select add_device_select" required>
              <option value="">Ending day</option>
              <option value="PN">PN</option>
              <option value="WT">WT</option>
              <option value="SR">SR</option>
              <option value="CZ">CZ</option>
              <option value="PT">PT</option>
              <option value="SB">SB</option>
              <option value="ND">ND</option>
            </select>
          </div>
          <button type="submit" name="dodaj" class="icon_button" title="Dodaj">
            <i class="fas fa-plus-circle"></i>
          </button>
        </form>
      </div>
      <?php
        $conn = mysqli_connect('localhost', 'root', '', 'ecodomDB');
        if ($conn->connect_error) {
            echo "Nie udało się połączyć z bazą danych: " . mysqli_connect_error();
            exit();
        }
        if (isset($_POST['dodaj'])) {
          $id_pomieszczenia = $_POST['Choose_your_room'];
          $nazwa = $_POST['nazwa'];
          $moc = $_POST['moc'];
          $harmonogram_start = $_POST['harmonogram_start'];
          $harmonogram_end = $_POST['harmonogram_end'];
          $harmonogram = $harmonogram_start . "-" . $harmonogram_end;

          $query = "INSERT INTO Urzadzenia (id_pomieszczenia, nazwa, moc, harmonogram) VALUES ($id_pomieszczenia, '$nazwa', $moc, '$harmonogram');";
          $stmt = mysqli_prepare($conn, $query);
          mysqli_stmt_execute($stmt);
          echo "<script type='text/javascript'>alert('Dodano urządzenie!');</script>";
          mysqli_stmt_close($stmt);
        }
        mysqli_close($conn);
      ?>
    </div>
  </div>
</body>
</html>