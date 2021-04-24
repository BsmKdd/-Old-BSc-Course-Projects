<?php

session_start();
include("../connection.php");

$newMove = json_decode($_POST["jsonSignup"], true);

if(empty($newMove['key']))
{
    exit("Access Denied");
} else if($newMove['key'] != 'KeyForDatabase'){
    exit("Access Denied");
};

$name = $newMove["name"];
$difficulty = $newMove["difficulty"];
$muscleGroup1 = $newMove["muscleGroup1"];
$description = $newMove["muscleGroup1"];
$machineID = $newMove["machineID"];
$img1 = $newMove["img1"];
$img2 = $newMove["img2"];
$gif = $newMove["gif"];

// $filename = $_FILES['upl']['name'];

$newMoveQry="INSERT INTO moves(machineID, moveDifficulty, moveDescription, muscleGroup1, moveName, moveImage1, moveImage2, moveGif) 
    VALUES('$machineID','$difficulty','$description','$muscleGroup1','$name','$img1','$img2','$gif')";

mysqli_query($con,$newMoveQry) or die(mysqli_error($con));


?>



