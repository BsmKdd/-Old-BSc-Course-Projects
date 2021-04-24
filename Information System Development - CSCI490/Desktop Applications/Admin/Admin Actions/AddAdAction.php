<?php

session_start();
include("../connection.php");

$newAccount = json_decode($_POST["jsonSignup"], true);

if(empty($newAccount['key']))
{
    exit("Access Denied");
} else if($newAccount['key'] != 'KeyForDatabase'){
    exit("Access Denied");
};

$email = $newAccount["email"];
$title = $newAccount["title"];
$trainerDescription = $newAccount["trainerDescription"];
$phoneNumber = $newAccount["phoneNumber"];
$name = $newAccount["name"];
$img = $newAccount["img"];

$newPT="INSERT INTO advertisementspt(namePT,descriptionPT,emailPT,phonePT,titlePT,image1PT) 
    VALUES('$name','$trainerDescription','$email','$phoneNumber','$title', '$img')";

mysqli_query($con,$newPT) or die(mysqli_error($con));

?>



