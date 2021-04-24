<?php

include("connection.php");

$deletedValue = json_decode($_POST["jsonDelete"], true);

$universityCode = $deletedValue["ID"];

$qry="DELETE from universities where code = '$universityCode'";

mysqli_query($con,$qry) or die (mysqli_error($con));

echo "University Deleted";
?>