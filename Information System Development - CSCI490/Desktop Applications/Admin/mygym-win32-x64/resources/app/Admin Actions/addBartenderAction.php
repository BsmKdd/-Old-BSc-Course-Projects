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
$password = $newAccount["password"];
$fName = $newAccount["firstName"];
$lName = $newAccount["lastName"];
$phoneNumber = $newAccount["phoneNumber"];
$address = $newAccount["address"];
$salary = $newAccount["salary"];
$img = $newAccount["img"];
$token =  $newAccount["token"];
// $filename = $_FILES['upl']['name'];
$checkqry = "SELECT * FROM accounts WHERE Email='$email'";
$validation = mysqli_query($con,$checkqry);
$newAccountQry="INSERT INTO accounts(Email,fName,lName,password,phoneNumber,address,type,img, token) 
    VALUES('$email','$fName','$lName','$password','$phoneNumber','$address',3,'$img','$token')";
$newBartenderQuery="INSERT INTO bartenders(accountID, salary) 
                VALUES(LAST_INSERT_ID(),'$salary')";

if(mysqli_num_rows($validation) < 1)
{
    mysqli_query($con,$newAccountQry) or die(mysqli_error($con));
    mysqli_query($con,$newBartenderQuery) or die(mysqli_error($con));
}

?>



