<?php

include("../connection.php");

$updatedValue = json_decode($_POST["jsonUpdate"], true);

if(empty($updatedValue['key']))
{
    exit("Access Denied");
} else if($updatedValue['key'] != 'KeyForDatabase'){
    exit("Access Denied");
};

$newValue = $updatedValue["updatedText"];
$id = $updatedValue["id"];
$updatedcol = $updatedValue["colName"];

$qry="UPDATE menuitems mi
SET $updatedcol = '$newValue' WHERE mi.id = '$id'";

mysqli_query($con,$qry) or die (mysqli_error($con));
?>