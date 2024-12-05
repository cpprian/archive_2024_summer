<!DOCTYPE html>
<html>
<head>
  <title>Ecodom+</title>
  <link rel="preconnect" href="https://fonts.googleapis.com">
  <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
  <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap" rel="stylesheet">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
  <link rel="stylesheet" href="style.css">
  <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>
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
	
	<div class="room_labels">
  <span class="labe">Living Room</span>
    <span class="labe">Kitchen Room</span>
  <span class="labe">See More</span>
  <span class="labe">+Add</span>
</div>
	
	
    <div class="container2">

	 <div class="chart-status">Usage Status</div>
    <select name="Day" class="day_select">
            <option value="0">Today</option>
            <option value="1">Yesterday</option>
            <option value="2">2 Days ago</option>
            <option value="3">Week ago</option>
          </select>
		      </div>

<div class="chart-labels">

  <div class="chart-label-container">
    <div class="chart-label">Total spend</div>
    <div class="chart-label-value">50Kwh</div>
  </div>
  <div class="chart-label-container">
    <div class="chart-label">Total hours</div>
    <div class="chart-label-value">24h</div>
  </div>
  <div class="chart-label-container">
    <div class="chart-label">Total cost</div>
    <div class="chart-label-value">100 PLN</div>
  </div>
</div>
<canvas id="myChart"></canvas>

	    <div class="container3">

	 <div class="chart-status">My devices</div>
  <button class="add_deb">+Add device</button>




		      </div>
<div class="empty-space"></div>
<div class="rounded-component">
  <div class="computer-image"></div>
  <div class="switch"></div>
  <div class="computer-text">
    <span class="computer-name">Computer</span>
    <span class="computer-status">active 1 hour ago</span>
  </div>
  <div class="usage">
    <div class="usage-line"></div>
    <div class="usage-text">5kWh</div>
  </div>
</div>
  
  <script>
    const labels = ['9:00', '10:00', '11:00', '12:00', '13:00', '14:00', '15:00', '16:00', '17:00', '18:00'];
    const data = {
      labels: labels,
      datasets: [{
        label: 'Electricity usage',
        backgroundColor: 'rgb(255, 255, 255)',
        borderColor: 'rgb(255, 255, 255)',
        data: [50, 3, 5, 5, 6, 3, 5, 2, 7, 6],
        borderWidth: 1,
		 borderRadius: 10,
		 borderRadius: 10,
        fill: false
      }]
    };
	const config = {
  type: 'bar',
  data,
  options: {
    scales: {
      x: {
        display: true,
        grid: {
          display: false
        },
        ticks: {
          color: 'white',
		        font: {
            size: 17,
          
          }
        }
      },
      y: {
        display: false,
		
      },
    },
    plugins: {
      legend: {
        display: false
      }
    }
  },
};

var myChart = new Chart(document.getElementById('myChart'), config);
  </script>
  
</body>
</html>
