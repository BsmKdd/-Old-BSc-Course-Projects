<?php

session_start();
include("connection.php");

if(isset($_POST['email']))
{	
	$email=$_POST["email"];
	$username=$_POST["username"];
	$fName=$_POST["fName"];
	$lName=$_POST["lName"];
	$university = $_POST['university'];
	$password=$_POST["password"];
	// $filename = $_FILES['upl']['name'];
	$checkqry = "SELECT * FROM accounts WHERE email='$email'";
	$validation = mysqli_query($con,$checkqry);
	$qry="INSERT INTO accounts(email,Username,firstName,lastName,university,password,admin) VALUES('$email','$username','$fName','$lName','$university','$password',0)";

	if(mysqli_num_rows($validation) < 1)
	{
	    if(!($_SERVER['HTTP_USER_AGENT'] == "Android"))
        {
    		header('location:index.php');
    		mysqli_query($con,$qry);
    		$_SESSION['user'] = $username;
        }
        else if($_SERVER['HTTP_USER_AGENT'] == "Android")
        {    
            if($_POST['key'] == "BassamMobile")
            {
    		    mysqli_query($con,$qry);
                echo "Success";
            } else echo "Key Error";
        } 
	}
}

?>



