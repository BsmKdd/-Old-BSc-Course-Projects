<?php

include("connection.php");

$deletedValue = json_decode($_POST["jsonDelete"], true);

$majorId = $deletedValue["ID"];

$qry="DELETE from majors where id = '$majorId'";

mysqli_query($con,$qry) or die (mysqli_error($con));

echo "Major Deleted";
?>