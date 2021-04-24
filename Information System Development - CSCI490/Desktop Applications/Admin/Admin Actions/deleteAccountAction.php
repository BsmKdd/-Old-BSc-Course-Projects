<?php

    include("../connection.php");
    $deletedValue = json_decode($_POST["deletePackage"], true);

    if(($deletedValue['key']) != 'KeyForDatabase')
    {
        exit("Not Authorized");
    }
    
    $id = $deletedValue['id'];

    $qrySelect = "SELECT token from accounts where id= $id";
    $result = mysqli_query($con, $qrySelect) or die(mysqli_error($con));
    $token = null;
    if(mysqli_num_rows($result) > 0)
    {
        $row = mysqli_fetch_assoc($result);
        $token = $row['token'];
    }

    $qryDelete="DELETE from accounts where id = $id";
    mysqli_query($con,$qryDelete) or die (mysqli_error($con));

    if(!empty($token))  echo $token;

?>