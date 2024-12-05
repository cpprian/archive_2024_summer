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
        <li><a href="#"><i class="fas fa-plug"></i>Photovoltaics</a></li>
        <li><a href="device.php"><i class="fas fa-plus-circle"></i>Add device</a></li>
        <li><a href="room.php"><i class="fas fa-plus-circle"></i>Add room</a></li>
      </ul>
    </div>
    <div class="content">
      <div class="add_component_form">
        <h1>Add Room</h1>
        <form method="post" action="room.php">
          <label for="nazwa">Name:</label>
          <input type="text" id="nazwa" name="nazwa" required><br><br>
          <label for="surface_area">Surface Area:</label>
          <input type="number" id="area" name="area" step="0.01" required><br><br>
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
          $nazwa = $_POST['nazwa'];
          $area = $_POST['area'];

          $query = "INSERT INTO Pomieszczenia (nazwa, powierzchnia) VALUES ('$nazwa', $area);";
          var_dump($query);
          $stmt = mysqli_prepare($conn, $query);
          mysqli_stmt_execute($stmt);
          echo "<script type='text/javascript'>alert('Dodano pomieszczenia!');</script>";
          mysqli_stmt_close($stmt);
        }
        mysqli_close($conn);
      ?>
    </div>
  </div>
</body>
</html>