<?php

include("connection.php");

$deletedValue = json_decode($_POST["jsonDelete"], true);

$ID = $deletedValue["ID"];

$qry="DELETE from accounts where ID = $ID";

mysqli_query($con,$qry) or die (mysqli_error($con));

echo "User Deleted";
?>