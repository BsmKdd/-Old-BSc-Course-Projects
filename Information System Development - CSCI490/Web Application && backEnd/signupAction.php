<?php

session_start();
include("connection.php");

$newAccount = json_decode($_POST["jsonSignup"], true);

$email = $newAccount["email"];
$password = $newAccount["password"];
$fName = $newAccount["firstName"];
$lName = $newAccount["lastName"];
$phoneNumber = $newAccount["phoneNumber"];
$address = $newAccount["address"];
$subscriptionPlan = $newAccount["sibscriptionPlan"];
$img = $newAccount["img"];
$token = $newAccount["token"];

	// $filename = $_FILES['upl']['name'];
	$checkqry = "SELECT * FROM accounts WHERE Email='$email'";
	$validation = mysqli_query($con,$checkqry);
	$newAccountQry="INSERT INTO accounts(Email,fName,lName,password,phoneNumber,address,type,token,img) 
        VALUES('$email','$fName','$lName','$password','$phoneNumber','$address',0,'$token','$img')";
    $newMemberQry="INSERT INTO members(accountID, status) 
                    VALUES(LAST_INSERT_ID(), 0)";

	if(mysqli_num_rows($validation) < 1)
	{
        mysqli_query($con,$newAccountQry) or die(mysqli_error($con));
        mysqli_query($con,$newMemberQry) or die(mysqli_error($con));

	}

?>



