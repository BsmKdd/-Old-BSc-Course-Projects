<?php

include("../connection.php");

$updatedValue = json_decode($_POST["jsonUpdate"], true);

if(!empty($updatedValue['key']))
{
    exit("Access Denied");
} else if($updatedValue['key'] != 'KeyForDatabase'){
    exit("Access Denied");
};

$newValue = $updatedValue["updatedText"];
$id = $updatedValue["id"];
$updatedcol = $updatedValue["colName"];

$qry="UPDATE accounts a 
INNER JOIN bartenders b on b.accountID = a.id
SET $updatedcol = '$newValue' WHERE a.id = '$id'";

mysqli_query($con,$qry) or die (mysqli_error($con));

$qrySelect = "SELECT token from accounts where id= $id";
$result = mysqli_query($con, $qrySelect) or die(mysqli_error($con));
$token = null;
if(mysqli_num_rows($result) > 0)
{
    $row = mysqli_fetch_assoc($result);
    $token = $row['token'];
}

echo $token;

?>