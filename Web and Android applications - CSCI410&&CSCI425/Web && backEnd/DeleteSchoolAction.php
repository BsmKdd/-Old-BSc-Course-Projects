<?php

include("connection.php");

$deletedValue = json_decode($_POST["jsonDelete"], true);

$schoolCode = $deletedValue["ID"];

$qry="DELETE from schools where id = '$schoolCode'";

mysqli_query($con,$qry) or die (mysqli_error($con));

echo "School Deleted";
?>