<?php

include("connection.php");

$deletedValue = json_decode($_POST["jsonDelete"], true);

$examId = $deletedValue["ID"];

$qry="DELETE from exams where id = '$examId'";

mysqli_query($con,$qry) or die (mysqli_error($con));

echo "Exam Deleted";
?>