<?php

include("connection.php");

$updatedValue = json_decode($_POST["jsonUpdate"], true);

$newValue = $updatedValue["updatedText"];
$ID = $updatedValue["ID"];
$updatedcol = $updatedValue["colName"];

$qry="UPDATE majors SET $updatedcol = '$newValue' WHERE id = '$ID'";

mysqli_query($con,$qry) or die (mysqli_error($con));

?>