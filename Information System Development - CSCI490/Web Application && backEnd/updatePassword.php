<?php
require "connection.php";

$updatedValue = json_decode($_POST["jsonPackage"], true);

$selector = $updatedValue['selector'];
$validator = $updatedValue['validator'];
$password = $updatedValue['password'];
$confirmPassword = $updatedValue['confirmPassword'];

if(empty($password) || empty($confirmPassword))
{
    echo "Please fill in both fields";
    exit();
} else if($password != $confirmPassword) {
    echo "Passwords do not match";
    exit();
}

$currentDate = date("U");

$sql = "SELECT * FROM passwordReset WHERE selector=? AND expire > $currentDate";

$stmt = mysqli_stmt_init($con);

if(!mysqli_stmt_prepare($stmt, $sql))
{
    echo "There was an error";
    exit();
} else { 

    mysqli_stmt_bind_param($stmt, "s", $selector);
    mysqli_stmt_execute($stmt);

    $result = mysqli_stmt_get_result($stmt);
    if(!$row = mysqli_fetch_assoc($result))
    {
        echo "Your request has expired.";
        exit();
    } else {

        $tokenBin = hex2bin($validator);
        $tokenCheck = password_verify($tokenBin, $row["token"]);

        if($tokenCheck === false)
        {
            echo "You need to resubmin your reset request";
            exit();
        } elseif($tokenCheck === true) {

            $tokenEmail = $row['email'];
            $sql = "SELECT * FROM accounts WHERE Email=?";

            $stmt = mysqli_stmt_init($con);

            if(!mysqli_stmt_prepare($stmt, $sql))
            {
                echo "There was an error";
                exit();
            } else { 
                mysqli_stmt_bind_param($stmt, "s", $tokenEmail);
                mysqli_stmt_execute($stmt);

                
                $result = mysqli_stmt_get_result($stmt);
                if(!$row = mysqli_fetch_assoc($result))
                {
                    echo "There was an error";
                    exit();
                } else {
                    $firebaseToken = $row['token'];
                    $sql = "UPDATE accounts SET password =? WHERE Email=?";
                    
                    $stmt = mysqli_stmt_init($con);

                    if(!mysqli_stmt_prepare($stmt, $sql))
                    {
                        echo "There was an error";
                        exit();
                    } else { 

                        // $newPassHash = password_hash($password, PASSWORD_DEFAULT);

                        mysqli_stmt_bind_param($stmt, "ss", $password, $tokenEmail);
                        mysqli_stmt_execute($stmt);

                        $sql = "DELETE FROM passwordReset WHERE email=?;";
                        $stmt = mysqli_stmt_init($con);
                
                        if(!mysqli_stmt_prepare($stmt, $sql))
                        {
                            echo "There was an error";
                            $obj = ['state' => "error"];
                            exit();
                        } else { 
                            $obj = ['state' => "success", 
                                    'email' => $tokenEmail,
                                    'token' => $firebaseToken];
                            echo json_encode($obj);
                            mysqli_stmt_bind_param($stmt, "s", $tokenEmail);
                            mysqli_stmt_execute($stmt);
                        }
                    }
                }
            }
        }
    }
}