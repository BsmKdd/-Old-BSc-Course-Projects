<?php

    include("connection.php");
    
    if(!($_SERVER['HTTP_USER_AGENT'] == "Android"))
    {
    $email = json_decode($_POST["jsonEmail"],false);

    $checkqry = "SELECT email FROM accounts WHERE email='$email'";
    $validation = mysqli_query($con,$checkqry);

    echo json_encode(mysqli_num_rows($validation) > 0);
    } else {

        if($_POST['key'] == "BassamMobile")
        {    
            $email = $_POST["email"];
            $checkqry = "SELECT email FROM accounts WHERE email='$email'";
            $validation = mysqli_query($con,$checkqry);
            // echo "Login Success!";
            if(mysqli_num_rows($validation) == 0) echo "Success";
            else echo "Email is taken.";
        
        } else echo "Access denied.";


    }
?>