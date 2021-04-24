<?php

include("connection.php");

$newValue = json_decode($_POST["jsonAdd"], true);

$majorId = $newValue["ID"];
$majorName = $newValue["Name"];
$schoolId = $newValue["relSchool"];

$qry="INSERT INTO majors(id,name,schoolId) VALUES('$majorId','$majorName','$schoolId')";
mysqli_query($con,$qry);

?>