<?php

session_start();
include("connection.php");

$message = json_decode($_POST["jsonMessage"], true);

$date = date('Y/m/d h:i:s');
$subject = $message["subject"];
$content = $message["content"];
$token = null;
$email = null;


if(isset($message['email']))
{
    $email = $message['email'];
}

if(!empty($message['token']))
{
    $token = $message["token"];
    $emailQry = "SELECT Email FROM accounts WHERE token = '$token'";
    $result = mysqli_query($con, $emailQry) or die(mysqli_error($con));
    if(mysqli_num_rows($result) > 0)
    {
        $row = mysqli_fetch_assoc($result);
        $email = $row['Email'];
    }

    $newMessageQry="INSERT INTO messages(subject,content,Email,token,type,time) 
    VALUES('$subject','$content','$email','$token','Member','$date')";
    
} else { 
    $newMessageQry="INSERT INTO messages(subject,content,Email,token,type,time) 
    VALUES('$subject','$content','$email','$token','Guest','$date')";
}



if(mysqli_query($con,$newMessageQry)) echo "Message Sent";
else echo "Error";


?>



