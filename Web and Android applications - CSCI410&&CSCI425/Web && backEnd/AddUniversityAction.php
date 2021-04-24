<?php

include("connection.php");

$newValue = json_decode($_POST["jsonAdd"], true);

$universityCode = $newValue["Code"];
$universityName = $newValue["Name"];

$qry="INSERT INTO universities(code,name) VALUES('$universityCode','$universityName')";
mysqli_query($con,$qry);

?>