<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Payment Page</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-image: url("img/own_upi.jpg");
            background-size: cover;
            overflow: hidden;
        }

        .container {
            max-width: 500px;
            margin: 150px auto;
            margin-top: 60px;
            background-color: rgba(255, 255, 255, 0.1);
            padding: 20px;
            border-radius: 10px;
        }

        label {
            color: aliceblue;
            font-weight: bold;
        }

        input[type="text"],
        input[type="number"],
        input[type="submit"],
        input[type="email"],
        input[type="tel"] {
            width: 90%;
            padding: 10px;
            margin-top: 10px;
            margin-bottom: 10px;
            border: 0px solid #ccc;
            border-radius: 15px;
            background-color: rgba(255, 255, 255, 0.4);
        }

        input[type="submit"] {
            background-color: #007bff;
            color: #fff;
            border: none;
            cursor: pointer;
            width: 20%;
        }

        input[type="submit"]:hover {
            background-color: #0056b3;
        }

        .center-button {
            text-align: center;
        }

        fieldset {
            border: 3px solid #3b3b4f;
            padding: 2rem 0;
            padding-left: 20px;
        }

        h2 {
            color: aliceblue;
            text-align: center;
        }

        legend {
            text-align: center;
            color: rgb(255, 255, 128);
            font-weight: bold;
            font-style: italic;
        }

        header {
            background-image: none;
        }
        ::placeholder {
        color: rgba(0, 0, 0, 0.5); /* Light black color */
    }
    </style>
</head>
<body>
    <header>
        <h2>Welcome to Own UPI Payment gateway</h2>
    </header>
    <div class="container">
        <fieldset>
            <legend>Coin generation payment</legend>
             <form action="paymentCtrl" method="post" onsubmit="return validateForm()">
                <label for="name">Name:</label>
                <input type="text" id="name" name="name" placeholder="Enter your name" required>
                 <input type="hidden" id="emailFromUrl" value="${param.email}">
                <label for="email">Email:</label>
                <input type="email" id="emailInput" name="email" placeholder="Enter your email" required value="${param.email}">
                <label for="coins">Amount :</label>
                <input type="number" id="coins" name="coins" placeholder="Enter amount" min="1" required>
                <label for="phNumber">Phone Number :</label>
                <input type="tel" id="phNumber" name="phNumber" min="10" max="10" placeholder="Enter your number" oninput="generateUPI()" required>
                <label for="allowAutoGenerate">Allow auto-generate UPI ID:</label>
                <input type="checkbox" id="allowAutoGenerate" onclick="generateUPI()">
                <label for="upi">UPI ID:</label>
                <input type="text" id="upi" name="upi" placeholder="Enter your upi id..." required>
               <div class="center-button">
                <button type="submit" onclick="validateEmail()">Submit</button>
            </div>
            </form>
        </fieldset>
    </div>
    
    <script>
        function validateEmail() {
            // Get the email from the URL
            var emailFromUrl = document.getElementById('emailFromUrl').value;
            // Get the email entered in the form
            var enteredEmail = document.getElementById('emailInput').value;
            
            // Compare the emails
            if (emailFromUrl === enteredEmail) {
                // If emails match, proceed with the form submission
                document.getElementById('paymentForm').submit();
            } else {
                // If emails do not match, show a popup message
                alert('Entered email does not match .Please enter the correct email.');
            }
        }
        function generateUPI() {
            var phoneNumber = document.getElementById('phNumber').value;
            var allowAutoGenerate = document.getElementById('allowAutoGenerate').checked;
            if (allowAutoGenerate) {
                var upiID = phoneNumber + '@pay';
                document.getElementById('upi').value = upiID;
            }else{
            	alert('Enter UPI id');
            }
        }
    </script>
</body>
</html>
