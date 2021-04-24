<!DOCTYPE html>
<html lang="en">
<head>     
    <!-- Google's Icons -->
    <link href="bootstrap-4.3.1-dist/css/bootstrap.min.css" rel="stylesheet"/>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link type="text/css" rel="stylesheet" href="css/materialize.min.css"  media="screen,projection"/>
    <link type="text/css" rel="stylesheet" href="css/index.css"  media="screen,projection"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>

    <title>Reset Password</title>

    <style>
        body
        {
            background-color: #f7f7f7;
        }

        #formContainer
        {
            background-color: white;
            padding: 15px;
            margin-top: 5px;
        }
        @media screen and (max-width: 480px) 
        {
            #formContainer
            {
                width: 95%;
                padding: 15px;
            }
        }
        @media screen and (min-width: 481px) {
            #formContainer
            {
                width: 450px
            }
        }

        .row
        {
            margin: 0px;
        }

    </style>
</head>
<body>
    <div id="formContainer" class="container shadow mx-auto">
        <h5 class="col">Enter your new password</h5>
        <?php
            $selector = $_GET["selector"];
            $validator = $_GET["validator"];

            if(empty($selector) || empty($validator))
            {
                echo "Could not validate your request.";
            } else {
                if(ctype_xdigit($selector) !== false && ctype_xdigit($validator))
                {
                    ?>

                    <form id="newPasswordForm" action="" autocomplete="off" method="post">
                        <input type="hidden" id="selector"  name="selector" value=<?php echo $selector; ?>>
                        <input type="hidden" id="validator" name="validator" value=<?php echo $validator; ?>>
                        <div class="row">
                            <div class="input-field col s6">
                                <input id="password" type="password" class="validate" data-length="20" maxlength="20"  > 
                                <label for="password">Password</label>
                            </div>
                        </div>
                        <div class="row">
                            <div class="input-field col s6">
                                <input id="confirmPassword" type="password" class="validate" data-length="20" maxlength="20" >
                                <label for="confirmPassword">Confirm password</label>
                            </div>
                        </div>  
                        
                        <div class="row">
                            <div class="col col-12 text-right">
                                <button class ="btn waves-effect  waves-light purple white-text w-100" type="submit" name="resetSubmit">Reset</button>
                            </div>
                        </div>

                        <div class="row">
                            <p id="responseMessage" class="col text-center">
                            </p>
                        </div>
                    </form>

                    <?php
                }
            }
        ?>
    </div>

      <!-- The core Firebase JS SDK is always required and must be listed first -->
  <script src="https://www.gstatic.com/firebasejs/7.13.0/firebase-app.js"></script>

    <!-- TODO: Add SDKs for Firebase products that you want to use
        https://firebase.google.com/docs/web/setup#available-libraries -->
    <script src="https://www.gstatic.com/firebasejs/7.13.0/firebase-analytics.js"></script>
    <script src="https://www.gstatic.com/firebasejs/7.13.0/firebase-auth.js"></script>
    <script src="https://www.gstatic.com/firebasejs/7.13.0/firebase-firestore.js"></script>
    <script src="https://www.gstatic.com/firebasejs/7.14.0/firebase-functions.js"></script>

    <script>
    // Your web app's Firebase configuration
    var firebaseConfig = {
        apiKey: "AIzaSyBWk2mYsYjV3bxSBZj5MKy5Knh-LJapTzg",
        authDomain: "mygym-97497.firebaseapp.com",
        databaseURL: "https://mygym-97497.firebaseio.com",
        projectId: "mygym-97497",
        storageBucket: "mygym-97497.appspot.com",
        messagingSenderId: "1073663096495",
        appId: "1:1073663096495:web:2a1b9b5090163f1300c65a",
        measurementId: "G-TPKR8TE6MN"
    };
    // Initialize Firebase
    firebase.initializeApp(firebaseConfig);
    firebase.analytics();

    //auth and firestore references
    const auth = firebase.auth();
    const db = firebase.firestore();
    const functions = firebase.functions();

    </script>
    <script>
        const newPasswordForm = document.querySelector('#newPasswordForm');
        let responseMessage = document.getElementById("responseMessage");

        newPasswordForm.addEventListener('submit',(e) => 
        {
            e.preventDefault();

            //get the info
            const password = newPasswordForm['password'].value;
            const confirmPassword = newPasswordForm['confirmPassword'].value;
            const selector = newPasswordForm['selector'].value;
            const validator = newPasswordForm['validator'].value;

            var letter = /[a-z]/;
            var letterUp= /[A-Z]/;
            var number = /[0-9]/;

            if(password.length < 6 || !letter.test(password) || !number.test(password) || !letterUp.test(password)) 
            {
                responseMessage.textContent = "The password needs to be 6-20 characters with at least 1 digit, lower and UPPER case letters.";
                responseMessage.style.color = "red";
                return;
            } 

            if(password != confirmPassword)
            {
                responseMessage.textContent = "The two fields do not match.";
                responseMessage.style.color = "red";
                return;
            }

            var newPackage = JSON.stringify({
                                            password: password,
                                            confirmPassword: confirmPassword,
                                            validator: validator,
                                            selector: selector
                                        });

            var xhr = new XMLHttpRequest();
            xhr.open('POST','updatePassword.php',true);
            xhr.setRequestHeader('Content-type','application/x-www-form-urlencoded');
            xhr.send("jsonPackage="+newPackage);
            
            xhr.onload = function()
            {
                if(this.status === 200)
                {   

                    try{
                        var arr = JSON.parse(this.responseText);
                        var state = arr.state;
                        if(state === "success")
                        {
                            var token = arr.token;
                            var email = arr.email;
                            console.log(token);
                            const updateUser = functions.httpsCallable('updateUser');
                            data = {
                                uid: token,
                                email: email,
                                password: password
                            }
                            updateUser(data).then(result => {
                                console.log(result);
                                responseMessage.textContent = "Your password has been updated."
                                responseMessage.style.color = "green";
                            }) 

                        } else {
                            responseMessage.textContent = state;
                            responseMessage.style.color = "red";
                        }
                    } catch (e) {
                        console.log(e.message)
                        responseMessage.textContent = this.responseText;
                        responseMessage.style.color = "red";
                    }

                }
            }
        })
    </script>
    <script type="text/javascript" src="js/jquery-3.4.1.min.js"></script>
    <script type="text/javascript" src="js/materialize.min.js"></script>
    <script src="https://kit.fontawesome.com/ebcbd60fef.js" crossorigin="anonymous"></script>
</body>
</html>