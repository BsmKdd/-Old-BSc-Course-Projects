<?php

include('../connection.php');

$id = $_POST['userID'];
$isMember = $_POST['accountType'];

$Name = $_FILES['profileImage']['name'];
$error = $_FILES['profileImage']['error'];
$size = $_FILES['profileImage']['size'];
$ext = pathinfo($Name, PATHINFO_EXTENSION);
$allowed = array('jpg','jpeg','png');

if(in_array($ext, $allowed))
{
    if($error === 0)
    {
        if($size <= 5242880)
        {
            $moveImageName = time().'.'.$ext;

            $target = '../Moves Images/'.$moveImageName;
            if(move_uploaded_file($_FILES['profileImage']['tmp_name'], $target))
            {
                $sql = "UPDATE moves 
                SET moveImage2 = '$moveImageName'
                WHERE id = '$id'";

                if(mysqli_query($con, $sql) or die(mysqli_error($con)))
                {

                } else {
                    echo "Upload Failed";
                }
            
            } else {
                echo "Failed to upload.";
            }
            
        } else {
            echo "Maximum file size is 5MB";
        }
    } else {
        echo " Error code: ", $error, ".";
    }
} else {
    echo "Allowed file types: jpeg, jpg, png.";
}  

?>