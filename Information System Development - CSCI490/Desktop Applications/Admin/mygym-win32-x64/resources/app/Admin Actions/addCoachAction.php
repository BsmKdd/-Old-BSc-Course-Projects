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
$expertise = $newAccount["expertise"];
$description = $newAccount["description"];
$salary = $newAccount["salary"];
$hireDate = date("Y/m/d");
$img = $newAccount["img"];

// $filename = $_FILES['upl']['name'];
$checkqry = "SELECT * FROM accounts WHERE Email='$email'";
$validation = mysqli_query($con,$checkqry);
$newAccountQry="INSERT INTO accounts(Email,fName,lName,password,phoneNumber,address,type,img) 
    VALUES('$email','$fName','$lName','$password','$phoneNumber','$address',2,'$img')";
$newMemberQry="INSERT INTO coaches(accountID, expertise, description, salary, hireDate) 
                VALUES(LAST_INSERT_ID(), '$expertise', '$description', '$salary', '$hireDate')";

if(mysqli_num_rows($validation) < 1)
{
    mysqli_query($con,$newAccountQry) or die(mysqli_error($con));
    mysqli_query($con,$newMemberQry) or die(mysqli_error($con));
}

?>



