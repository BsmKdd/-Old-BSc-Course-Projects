<?php
session_start();
include('connection.php');

if(isset($_POST['email']) && !empty($_POST['email']) &&
isset($_POST['password']) && !empty($_POST['password']))
{
	$LoginEmail = $_POST['email'];
	$LoginPassword = $_POST['password'];

	$q = "SELECT * FROM accounts WHERE email = '$LoginEmail' AND password = '$LoginPassword'";
	$result = mysqli_query($con,$q) or die(mysqli_error($con));
	
	if (mysqli_num_rows($result) > 0)
	{
		$account = mysqli_fetch_array($result);
        
        if($account['admin'] == 1 && !empty($_POST['key'])) echo "admin";
        else if($account['admin'] == 0 && !empty($_POST['key'])) echo "user";
		else if($account['admin'] == 1)
		{
			$_SESSION['admin'] = $account['Username'];
			header('location:adminCp.php');  
		} else if($account['admin'] == 0) {
			$_SESSION['user'] = $account['Username'];
			header('location:index.php');
		}
	}
	else
	{
	    if(!isset($_POST['key'])) header('location:login.php');  	
	    echo "Invalid Credentials!";
	}
} ;
?>