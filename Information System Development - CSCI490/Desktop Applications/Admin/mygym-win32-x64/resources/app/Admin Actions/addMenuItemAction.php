<?php

session_start();
include("../connection.php");

$newMenuItem = json_decode($_POST["jsonSignup"], true);

if(empty($newMenuItem['key']))
{
    exit("Access Denied");
} else if($newMenuItem['key'] != 'KeyForDatabase'){
    exit("Access Denied");
};

$name = $newMenuItem["name"];
$calories = $newMenuItem["calories"];
$fats = $newMenuItem["fats"];
$protein = $newMenuItem["protein"];
$carbohydrates = $newMenuItem["carbohydrates"];
$sugar = $newMenuItem["sugar"];
$time = $newMenuItem["time"];
$price = $newMenuItem["price"];
$type = $newMenuItem["type"];
$available = $newMenuItem["available"];
$description = $newMenuItem["description"];
$img = $newMenuItem["img"];

// $filename = $_FILES['upl']['name'];
$newMenuItemQry="INSERT INTO menuitems(itemName, itemDescription, calories, fats, protein, carbohydrates, sugar, itemPrice, preparation, available, itemImg, type) 
    VALUES('$name','$description','$calories','$fats','$protein','$carbohydrates','$sugar','$price','$time','$available','$img','$type')";

mysqli_query($con,$newMenuItemQry) or die(mysqli_error($con));


?>



