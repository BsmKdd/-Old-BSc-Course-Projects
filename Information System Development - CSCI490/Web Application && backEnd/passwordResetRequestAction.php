<?php

    $selector = bin2hex(random_bytes(8));
    $token = random_bytes(32);

    $url = "https://gym-senior-2020.000webhostapp.com/resetPassword.php?selector=".$selector."&validator=".bin2hex($token);

    $expires = date("U") + 1800;

    require 'connection.php';

    $userEmail = $_POST["email"];

    $sqlVerify = "SELECT * FROM accounts WHERE Email = '$userEmail'";
	$result = mysqli_query($con,$sqlVerify) or die(mysqli_error($con));
    $row = mysqli_fetch_assoc($result);

	if (mysqli_num_rows($result) === 0)
	{
        echo "This email doesn't exist in our database.";
        exit();
    } 

    $sql = "DELETE FROM passwordReset WHERE email=?;";
    $stmt = mysqli_stmt_init($con);

    if(!mysqli_stmt_prepare($stmt, $sql))
    {
        echo "There was an error";
        exit();
    } else { 
        mysqli_stmt_bind_param($stmt, "s", $userEmail);
        mysqli_stmt_execute($stmt);
    }


    $sql = "INSERT  INTO passwordReset (email, selector, token, expire) VALUES (?,?,?,?);";
    $stmt = mysqli_stmt_init($con);

    if(!mysqli_stmt_prepare($stmt, $sql))
    {
        echo "There was an error";
        exit();
    } else { 
        $hashedToken = password_hash($token, PASSWORD_DEFAULT);
        mysqli_stmt_bind_param($stmt, "ssss", $userEmail, $selector, $hashedToken, $expires);
        mysqli_stmt_execute($stmt);
    }

    mysqli_stmt_close($stmt);
    mysqli_close($con);

    $to = $userEmail;
    $subject = "myGym Password Reset";
    $message = '<p> We have received a password reset request for the account associated with this email. The link to reset your password is below. If you did not make this request, you can ignore this email. 
    <br>
    The link expires in 30 minutes: 
    <br>
    <a href="'.$url.'">'.$url.'</a></p>';

    $headers = "From: <myGym@outlook.com>\r\n";
    $headers .= "Reply-To: myGym@outlook.com r\n";
    $headers .= "Content-type: text/html\r\n";

    mail($to, $subject, $message, $headers);

    echo "success";
?>