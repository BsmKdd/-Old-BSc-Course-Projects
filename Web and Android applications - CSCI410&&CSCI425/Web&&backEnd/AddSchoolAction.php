<?php

include("connection.php");

$newValue = json_decode($_POST["jsonAdd"], true);

$schoolId = $newValue["ID"];
$schoolName = $newValue["Name"];
$universityCode = $newValue["relUniversity"];

$qry="INSERT INTO schools(id,name,universityCode) VALUES('$schoolId','$schoolName','$universityCode')";
mysqli_query($con,$qry);

?>