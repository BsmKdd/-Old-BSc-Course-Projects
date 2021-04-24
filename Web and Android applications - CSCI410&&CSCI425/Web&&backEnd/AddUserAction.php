<?php

include("connection.php");

$newValue = json_decode($_POST["jsonAdd"], true);

$email = $newValue["Email"];
$username = $newValue["Username"];
$fName = $newValue["fName"];
$lName = $newValue["lName"];
$university = $newValue["University"];
$Password = $newValue["Password"];

$qry="INSERT INTO accounts(email,Username,firstName,lastName,university,password,admin) VALUES('$email','$username','$fName','$lName','$university','$Password',0)";
mysqli_query($con,$qry);

// echo json_encode("Record added!");


?>