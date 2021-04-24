<?php

include("connection.php");

$updatedValue = json_decode($_POST["jsonUpdate"], true);

$newValue = $updatedValue["updatedText"];
$ID = $updatedValue["ID"];
$updatedcol = $updatedValue["colName"];

$qry="UPDATE accounts SET $updatedcol = '$newValue' WHERE ID = '$ID'";

mysqli_query($con,$qry) or die (mysqli_error($con));

echo json_encode($updatedValue);

header('location:adminCp.php');

?>