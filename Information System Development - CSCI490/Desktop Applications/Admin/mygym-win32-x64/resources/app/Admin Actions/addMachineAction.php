<?php

session_start();
include("../connection.php");

$newMachine = json_decode($_POST["jsonSignup"], true);

if(empty($newMachine['key']))
{
    exit("Access Denied");
} else if($newMachine['key'] != 'KeyForDatabase'){
    exit("Access Denied");
};

$name = $newMachine["name"];
$code = $newMachine["code"];
$floor = $newMachine["floor"];
$section = $newMachine["section"];
$img = $newMachine["img"];

// $filename = $_FILES['upl']['name'];

$newMachineQry="INSERT INTO machines(machineName, machineCode, machineImage) 
    VALUES('$name','$code','$img')";
$newMachineLocationQry="INSERT INTO machinelocation(machineID, floor, section) 
                VALUES(LAST_INSERT_ID(), '$floor', '$section')";

mysqli_query($con,$newMachineQry) or die(mysqli_error($con));
mysqli_query($con,$newMachineLocationQry) or die(mysqli_error($con));


?>



