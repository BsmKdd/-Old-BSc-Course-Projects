<?php

include('../connection.php');

$id = $_POST['userID'];
$isMember = $_POST['accountType'];

echo $id;

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
            $profileImageName = time().'.'.$ext;

            if($isMember == "member") $target = '../Member Images/'.$profileImageName;
            else if($isMember == "coach") $target = '../Coach Images/'.$profileImageName;
            else if($isMember == "bartender") $target = '../Bartender Images/'.$profileImageName;

            if(move_uploaded_file($_FILES['profileImage']['tmp_name'], $target))
            {
                $sql = "UPDATE accounts 
                SET img = '$profileImageName'
                WHERE id = '$id'";

                if(mysqli_query($con, $sql) or die(mysqli_error($con)))
                {
                    if($isMember != "member") exit(); //*******//

                    $selectSQL = "SELECT token 
                    FROM accounts
                    WHERE id = '$id'";

                    $result = mysqli_query($con, $selectSQL) or die(mysqli_error($con));
                    $row = mysqli_fetch_assoc($result);
                    
                    $data = [ 'img' => $target, 'uid' => $row['token']];

                    echo json_encode($data);
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