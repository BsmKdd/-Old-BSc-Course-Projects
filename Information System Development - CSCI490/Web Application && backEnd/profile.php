<?php
    include('connection.php');
?>

<!DOCTYPE html>
<html lang="en">
<head>     
    <!-- Google's Icons -->
    <link href="bootstrap-4.3.1-dist/css/bootstrap.min.css" rel="stylesheet"/>
    <link type="text/css"  href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link type="text/css" rel="stylesheet" href="css/materialize.min.css"  media="screen,projection"/>
    <link type="text/css" rel="stylesheet" href="css/profile.css"  media="screen,projection"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>




    <title>Profile</title>

</head>

<body>  
    <nav class="nav-wrapper">
        <a href="index.html" class="brand-logo center"><img style="margin-top: -20px !important; height: 60px !important;"  src="tempImages/LogoMakr_27qblP.png" alt=""></a>
        <a href="#" data-target="mobile-demo" class="sidenav-trigger"><i class="material-icons">menu</i></a>
        <ul class="right hide-on-med-and-down">
            <li><a href="index.html">Home</a></li>
            <li><a href="team.html">Our Team</a></li>
            <li><a href="about.html">About</a></li>
            <li><a href="contact.html">Contact Us</a></li>
            <li><a id="login"class="waves-effect waves-light purple btn-small modal-trigger logged-out" style="display: none;"data-target="modal-login">Login</a></li>
              <!-- Dropdown Trigger -->
            <li id="image-dropdown" class="dropdown-trigger ml-1 logged-in" style="display: none;" href='#' data-target='account-dropdown'><img id="profile-image" src="" style="height: 50px; border-radius: 50%;"></li>
    
            <!-- Dropdown Structure -->
        </ul>
    
        <ul id='account-dropdown' class='dropdown-content'>
            <li><a href="profile.php">Profile</a></li>
            <li><a id="logout" href="#!">Logout</a></li>
            <!-- <li><a id="logout" class="waves-effect waves-light purple btn-small logged-in" style="display: none;">Logout</a></li> -->
            <!-- <li><a href="#!"><i class="material-icons">cloud</i>five</a></li> -->
        </ul>
    
    </nav>
    <div class="sidenav center-align "  id="mobile-demo">
        <ul class="my-auto">
        <!-- <div class=""> -->
                <a class="logged-in my-auto" style="display: none;" href='profile.php'><img id="profile-image-side" class="mb-5 mt-5" src="" style="height: 150px; border-radius: 50%;" ></a>
                <li><a href="profile.php">Profile</a></li>
                <li><a href="index.html">Home</a></li>
                <li><a href="team.html">Our Team</a></li>
                <li><a href="about.html">About</a></li>
                <li><a href="contact.html">Contact Us</a></li>
                <li><a id="loginSide" class="waves-effect waves-light purple btn-small modal-trigger logged-out" style="display: none;"data-target="modal-login">Login</a></li>
                <li><a id="logoutSide" class="waves-effect waves-light purple btn-small modal-trigger logged-in" href="#!">Logout</a></li>
        <!-- </div> -->
        </ul>
    </div>

<div class="container">

    <?php if(!empty($msg)): ?>
    <div class="alert <?php echo $css_class; ?>">
        <?php echo $msg;?>
    </div>
    <?php endif; ?>

    <div class="row profile">
		<div class="col-md-3">
			<div class="profile-sidebar">
                <!-- SIDEBAR USERPIC -->
                <form action="Editing Profile Actions/uploadImageAction.php" method="POST" id="uploadForm" name="uploadForm" enctype="multipart/form-data">
                    <div class="profile-userpic form-group center-align">
                        <img id="profile-image-account" src="" class="img-responsive" alt="">
                        <input  name="profileImage" type="file" id="profileImage" hidden="hidden">
                    </div>
                </form>
				<!-- END SIDEBAR USERPIC -->
				<!-- SIDEBAR USER TITLE -->
				<div class="profile-usertitle">
					<div id="memberName" class="profile-usertitle-name">
					</div>
					<div class="profile-usertitle-job">
						Member
					</div>
				</div>
				<!-- END SIDEBAR USER TITLE -->
				<!-- SIDEBAR BUTTONS -->

				<!-- END SIDEBAR BUTTONS -->
				<!-- SIDEBAR MENU -->
				<div class="profile-usermenu " id="pills-tab" role="tablist">
					<ul class="nav nav-pills">
						<li class="nav-item">
							<a  class=" nav-link  active" id="pills-home-tab" data-toggle="tab" href="#home"  >
                            <i class="tiny material-icons">home</i>
							<span >Overview</span> </a>
						</li>
						<li class="nav-item">
							<a  class="nav-link" id="pills-workout-tab" data-toggle="pill" href="#workout" role="tab" aria-controls="pills-profile" aria-selected="false">
                            <i class="tiny material-icons">watch_later</i>
							Previous Workouts </a>
                        </li>
                        <li class="nav-item">
							<a  class="nav-link" id="pills-contact-tab" data-toggle="pill" href="#contact" role="tab" aria-controls="pills-contact" aria-selected="false">
                            <i class="tiny material-icons">perm_phone_msg</i>
							Contact </a>
						</li>
                    </ul>
				</div>
			</div>
		</div>
		<div class="col-md-9">
            <div class="tab-content" id="v-pills-tabContent">
                <div class="tab-pane fade show active" id="home" role="tabpanel" aria-labelledby="v-pills-home-tab">
                    <form id="profileForm" class="mx-0 mx-sm-5" autocomplete="off">
                        <div class="row">
                            <div class="input-field col col-12 col-sm-6">
                                <input id="first_name" type="text" class="validate" value=" " data-length="50" maxlength="50" disabled  customError>
                                <label for="first_name">First Name</label>
                                <i  id="editFirstName" class="material-icons prefix">edit</i>
                            </div>
                            <div class="input-field col col-12 col-sm-6">
                                <input id="last_name" type="text" class="validate"  value=" " data-length="50" maxlength="50" disabled  customError> 
                                <label for="last_name">Last Name</label>
                                <i id="editLastName"  class="material-icons prefix">edit</i>

                            </div>
                        </div>
                        <div class="row">
                            <div class="input-field col s12">
                                <input id="email" type="email" class="validate"  value="." data-length="150" maxlength="150" disabled  customError>
                                <label for="email">Email</label>
                            </div>
                        </div>
                        <div class="row">
                            <div class="input-field col col-md-3 ">
                                <select id="countryCode" name="countryCode" disabled customError>
                                </select>
                                <label>Country</label>
                                </div>
                            <div class="input-field col col-md-9">
                                <input id="telephone" type="tel" class="validate form-control" data-length="20" maxlength="20" value=" " disabled customError>
                                <label for="telephone">Telephone</label>
                                <i id="editTelephone" class="material-icons prefix">edit</i>

                            </div>
                        </div>             
                        <div class="row mb-0">
                            <div class="input-field col col-12">
                            <textarea id="address" class="materialize-textarea" data-length="150" maxlength="150" disabled customError> </textarea>
                            <label for="address">Address</label>
                            <i id="editAddress" class="tiny material-icons prefix">edit</i>

                            </div>
                        </div>
                
                        <div class="row " style="margin-bottom: 5px !important;">
                            <p class="col-12 col-sm-3 mr-1">Subscription Plan: </p>
                            <p id="plan" class="col ml-1"></p>
                            
                        </div>

                        <div class="row">
                            <p class="col-12 col-sm-3 mr-1">Expires: </p>
                            <p id="expire" class="col ml-1"> 27-06-2020</p>
                            <div class="col-2 right">
                                <button id="saveButton" class="btn-small waves-effect waves-light purple white-text" hidden type="submit" name="action">
                                Save
                            </div>
                        </div>

                    </form>
                </div>
                <div class="tab-pane fade" id="workout" role="tabpanel" aria-labelledby="v-pills-workout-tab">
                    <ul id="workoutsList" class="collapsible z-depth-0 guides" style="border: none;">
                        <!-- <li>
                            <div class="collapsible-header grey lighten-4">Guide title</div>
                            <div class="collapsible-body white">
                                <div class="row collapsible-body-inner">
                                    <img class="col-2 pl-0" src="TrainerImages/Profiles/jounaidalsaadiprofile.jpg" alt="">
                                    <p class="my-auto">Lorem ipsum dolor sit amet consectetur adipisicing.</p>
                                </div>
                                <div class="row collapsible-body-inner">
                                    <img class="col-2 pl-0" src="TrainerImages/Profiles/jounaidalsaadiprofile.jpg" alt="">
                                    <p class="my-auto">Lorem ipsum dolor sit amet consectetur adipisicing.</p>
                                </div>
                                <div class="row collapsible-body-inner">
                                    <img class="col-2 pl-0" src="TrainerImages/Profiles/jounaidalsaadiprofile.jpg" alt="">
                                    <p class="my-auto">Lorem ipsum dolor sit amet consectetur adipisicing.</p>
                                </div>
                            </div>
                        </li> -->
                    </ul>
                </div>
                <div class="tab-pane fade" id="contact" role="tabpanel" aria-labelledby="v-pills-contact-tab">
                    <!-- <div class="row">
                        <div class="input-field col s12">
                            <input id="email" type="email" style="margin-bottom: 0px !important;" class="validate" data-length="150" maxlength="150"  customError>
                            <label for="email">Email</label>
                        </div>
                    </div> -->

                <form action="" id="contactForm" class="mx-0 mx-sm-5">
                    <div class="row">
                        <div class="input-field col s12">
                            <input id="subject" type="text" autocomplete="off"  placeholder="Subject of your message..." class="validate" data-length="50" maxlength="50" required customError>
                            <label for="subject">Subject</label>
                        </div>
                    </div>
                    <div class="row mb-0">
                        <div class="input-field col col-12" style="margin-bottom: 0 !important;">
                        <textarea id="messageContent" autocomplete="off" class="materialize-textarea" placeholder="Write your message here..." data-length="450" maxlength="450" required customError></textarea>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col d-flex">
                            <div class="alert alert-warning" hidden role="alert">
                                Minimum message length: 15 characters.
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col text-right" style="margin-left: auto !important;">
                            <button id="sendButton" style="padding: 0 5px !important;" class ="btn-small waves-effect waves-light purple" type = "submit">Send
                            <i class = "material-icons right">send</i></button>
                        </div>
                    </div>
                </form>
                </div>
              </div>
		</div>
	</div>
</div>

<br>
<br>

<footer class="page-footer purple z-depth-2">
    <div class="container">
        <div class="row">
        <div class="col l4 s12">
            <h5 class="white-text">FOLLOW US</h5>
            <a href="#" id="facebook"><i class="fab fa-facebook-square fa-2x"></i></a>
            <a href="#" id="twitter"><i class="fab fa-twitter-square fa-2x"></i></a>
            <a href="#" id="instagram"><i class="fab fa-instagram-square fa-2x"></i></a>

        </div>

        <div class="col l4 s12 push-l1">
            <h5 class="white-text ">CONTACT</h5>
            <ul>
                <li><span class="font-weight-bold">EMAIL: </span>myGym@gym.gym</li>
                <li class="mb-2 mt-2"><span class="font-weight-bold">PHONE: </span>(961) 71111000</li>
                <li><span class="font-weight-bold">ADDRESS: </span>Sidon, Sidon Street</li>
            </ul>
        </div>

        <div class="col l4 s12 push-l2">
            <h5 class="white-text ">NAVIGATE</h5>
            <ul>
                <li><a class="grey-text text-lighten-3" href="index.html">Home</a></li>
                <li><a class="grey-text text-lighten-3" href="team.html">Our Team</a></li>
                <li><a class="grey-text text-lighten-3" href="about.html">About</a></li>
                <li><a class="grey-text text-lighten-3" href="contact.html">Contact Us</a></li>
            </ul>
        </div>
        </div>
    </div>
    <div class="footer-copyright">
        <div class="container center-align">
        © 2020 Copyright Text
        </div>
    </div>
    </footer>


    <!-- The core Firebase JS SDK is always required and must be listed first -->
    <script src="https://www.gstatic.com/firebasejs/7.13.0/firebase-app.js"></script>

    <!-- TODO: Add SDKs for Firebase products that you want to use
        https://firebase.google.com/docs/web/setup#available-libraries -->
    <script src="https://www.gstatic.com/firebasejs/7.13.0/firebase-analytics.js"></script>
    <script src="https://www.gstatic.com/firebasejs/7.13.0/firebase-auth.js"></script>
    <script src="https://www.gstatic.com/firebasejs/7.13.0/firebase-firestore.js"></script>

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


    </script>
    
    <script type="text/javascript" src="js/jquery-3.4.1.min.js"></script>
    <script type="text/javascript" src="bootstrap-4.3.1-dist/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="js/materialize.min.js"></script>
    <script type="text/javascript" src="scripts/profileAuth.js"></script>
    <script type="text/javascript" src="scripts/profile.js"></script>
    <script src="https://kit.fontawesome.com/ebcbd60fef.js" crossorigin="anonymous"></script>


</body>






















</html>

