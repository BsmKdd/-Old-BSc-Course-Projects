<?php
session_start();
include('connection.php');

if(isset($_POST['email']) && isset($_POST['token']))
{
	$LoginEmail = $_POST['email'];
	$UserToken = $_POST['token'];
	
	$qry = "UPDATE accounts SET token = '$UserToken' WHERE email = '$LoginEmail'";
	$result = mysqli_query($con,$qry) or die(mysqli_error($con));

	$q = "SELECT * FROM accounts WHERE email = '$LoginEmail'";
	$result = mysqli_query($con,$q) or die(mysqli_error($con));
    $row = mysqli_fetch_assoc($result);

	if (mysqli_num_rows($result) > 0)
	{
        $member =   [ 
                        'ID' => $row['id'],
                        'First Name' => $row['fName'],
                        'Last Name' => $row['lName'],
                        'Email' => $row['Email'],
                        'Image' => $row['img']
                    ];
                    
      echo json_encode($member);   
    } else echo "Error";

} ;
?>