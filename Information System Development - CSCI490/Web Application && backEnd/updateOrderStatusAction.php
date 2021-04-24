<?php

include("connection.php");

$updatedValue = json_decode($_POST["jsonUpdate"], true);

if(empty($updatedValue['key']))
{
    exit("Access Denied");
} else if($updatedValue['key'] != 'KeyForDatabase'){
    exit("Access Denied");
};

$newValue = $updatedValue["updatedText"];
$ID = $updatedValue["id"];
$uid = $updatedValue["uid"];

$qryBart="SELECT b.id as bartenderID
FROM bartenders b
INNER JOIN accounts a ON b.accountID = a.id
WHERE a.token = '$uid'";
$resultBart = mysqli_query($con, $qryBart) or die(mysqli_error($con));
$rowBart = mysqli_fetch_assoc($resultBart);
$bartenderID = $rowBart['bartenderID'];

$qry="UPDATE orders
SET status = '$newValue', bartenderID = '$bartenderID'
WHERE id = '$ID';";

$result = mysqli_query($con,$qry) or die (mysqli_error($con));
echo $result;
?>