<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="model.Data"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>Live Page</title>
<link rel="icon" href="favicon.ico" type="image/x-icon">

<style>
body {
	font-family: Arial, sans-serif;
	margin: 0;
	padding: 0;
	background-image: url("img/login-bg.png");
	background-size: cover;
}

#video-container {
	text-align: center;
	margin-top: 50px;
}

#live-video {
	max-width: 800px;
	width: 100%;
}

#button-container {
	text-align: center;
	margin-top: 20px;
}

button {
	padding: 10px 20px;
	font-size: 16px;
	cursor: pointer;
}

#coin-balance {
	text-align: center;
	margin-top: 20px;
	font-size: 18px;
}

.userinfo {
	margin: 50px 450px 0 450px;
	background-color: rgba(255, 255, 255, 0.1);
}

label {
	font-size: medium;
	font-weight: bolder;
}

.user-fieldset {
	border-style: dotted;
}

.payment-section {
	padding: 20px;
	margin-top: 20px;
	background-color: rgba(255, 255, 255, 0.2);
	border-radius: 20px;
	width: 100%;
}

.history-section {
	padding: 20px;
	margin-top: auto;
	background-color: rgba(255, 255, 255, 0.2);
	border-radius: 20px;
	width: 100%;
}

.payments-Realated {
	display: flex;
	justify-content: space-evenly;
	gap: 10px;
	padding: 0;
	margin: 25px;
}

a {
	color: azure;
	text-decoration: none;
}

a:hover {
	text-decoration: underline;
	color: aliceblue;
	font-size: large;
}
</style>

</head>

<body>
	<div id="video-container">
		<h1>Live Video Stream</h1>
		<div id="video-player"></div>
	</div>

	<div id="button-container">
		<button id="go-live-btn" onclick="startLive()">Go Live</button>
		<button id="end-live-btn" onclick="endLive()" disabled>End
			Live</button>
	</div>

	<div class="userinfo" id="user-info-container">
		<fieldset class="user-fieldset">
			<legend style="text-align: center; font-weight: bold;">
				<h2>User Information</h2>
			</legend>
			<%
			// Import necessary classes
			List<Data> dataList = (List<Data>) request.getAttribute("userDataList");
			if (dataList != null) {
				for (Data userData : dataList) {
			%>
			<p>
				<label for="name">Name:</label>
				<%=userData.getName()%>
			</p>
			<p>
				<label for="email">Email:</label> <span id="user-email"><%=userData.getEmail()%></span>
			</p>
			<p>
				<label for="password">Password:</label>
				<%=userData.getPassword()%>
			</p>
			<p>
				<label for="coins">Coins balance:</label> <span id="coin-balance"><%=userData.getCoins()%></span>
			</p>

			<form id="update-balance-form" action="paymentCtrl" method="post"
				style="display: none;">
				<input type="text" id="email" name="email" value="<%=userData.getEmail()%>"> 
				<input type="text" id="updated-coins" name="coins" value="">
			</form>
			<form id="history-form" action="TransactionHistoryServlet"
				style="display: none;">
				<input type="hidden" id="user-email" name="email"
					value="<%=userData.getEmail()%>">
			</form>
			<%
			}
			} else {
			%>
			<p>No user data available.</p>
			<%
			}
			%>
		</fieldset>
	</div>

	<div class="payments-Realated">
		<div class="payment-section">
			<h2>Payment Section</h2>
			<%
			// Retrieve email from session
			String email = (String) session.getAttribute("email");
			%>

			<p>
				Make a payment or update balance click on this link now . <a
					href="PaymentPage.jsp?email=<%=email%>">Pay now</a>
			</p>

		</div>


		<div class="history-section" id="xyz">
			<h2>Transaction History</h2>
			<p>
				Users can view their transaction <span>
					<button id="history" onclick="submitForm()"
						style="border-radius: 20px">history</button>
				</span>here.
			</p>
			<form id="history-form" action="TransactionHistoryServlet"
				style="display: none;">
				<input type="hidden" id="user-email" name="email" value="">
			</form>
		</div>
	</div>




	<script>
		var goLiveButton = document.getElementById('go-live-btn');
		var endLiveButton = document.getElementById('end-live-btn');
		var historyButton = document.getElementById('history'); // Renamed variable
		var videoDuration = 0;

		function startLive() {
			var coinBalanceElement = document.getElementById('coin-balance');
			var currentCoins = parseInt(coinBalanceElement.innerText.trim());

			if (currentCoins <= 0) {
				alert('Insufficient coins! Please update your balance.');
				return;
			}

			startTime = Date.now(); // Record the start time

			var videoContainer = document.getElementById('video-player');
			// Remove any existing video player
			videoContainer.innerHTML = '';
			// Create new iframe for the live video
			var liveVideo = document.createElement('iframe');
			liveVideo.id = 'live-video';
			liveVideo.width = '560';
			liveVideo.height = '315';
			liveVideo.src = 'https://www.youtube.com/embed/BGTx91t8q50?si=djQs3QBsmJ1jShIv&autoplay=1';
			liveVideo.title = 'YouTube video player';
			liveVideo.frameborder = '0';
			liveVideo.allow = 'accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share';
			liveVideo.allowfullscreen = true;

			// Append the live video iframe to the video container
			videoContainer.appendChild(liveVideo);
			// Disable Go Live button and enable End Live button
			goLiveButton.disabled = true;
			endLiveButton.disabled = false;
		}

		function endLive() {
			var coinBalanceElement = document.getElementById('coin-balance');
			var currentCoins = parseInt(coinBalanceElement.innerText.trim());

			var endTime = Date.now(); // Record the end time

			var durationInSeconds = (endTime - startTime) / 1000;

			// Convert duration to minutes or any other format as needed
			var durationInMinutes = Math.ceil(durationInSeconds / 60);

			var coinsToDeduct = durationInMinutes;

			// Deduct coins from balance
			var newCoins = currentCoins - coinsToDeduct;
			coinBalanceElement.innerText = newCoins;
			document.getElementById('updated-coins').value = newCoins;

			// Submit form or perform any other necessary actions
			document.getElementById('update-balance-form').submit();

			var videoContainer = document.getElementById('video-player');
			videoContainer.innerHTML = ''; // Remove the live video iframe
			goLiveButton.disabled = false;
			endLiveButton.disabled = true;

		}

		function submitForm() {

			document.getElementById('history-form').submit();
		}
	</script>

	<%
	// Check if the "balanceUpdateSuccess" attribute is present in the request
	Boolean balanceUpdateSuccess = (Boolean) request.getAttribute("balanceUpdateSuccess");
	if (balanceUpdateSuccess != null && balanceUpdateSuccess) {
	%>
	<script>
		alert('Balance Update Successful. Redirecting to liveStream Page');
	</script>
	<%
	}
	%>


</body>

</html>
