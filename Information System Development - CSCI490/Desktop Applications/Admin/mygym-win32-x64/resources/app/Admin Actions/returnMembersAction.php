<?php
    include("../connection.php");

    if(!isset($_POST['key']))
    {
        exit("Access Denied");
    } else if($_POST['key'] != 'KeyForDatabase'){
        exit("Access Denied");
    };
    
    $membersQry=
    "SELECT a.id as accountID, a.Email as email, a.fName as fName, a.lName as lName, a.password as password,
     a.phoneNumber as phoneNumber, a.address as address, a.img as image, m.id as memberID, m.status as status, a.token as token,
     m.subscriptionExpire as expire, m.registrationDate as registration
    FROM accounts a  
    INNER JOIN members m ON a.id = m.accountID";
    
    $result = mysqli_query($con,$membersQry) or die(mysqli_error($con));
    $members = array();

    if(mysqli_num_rows($result) > 0)
    {
        while($row = mysqli_fetch_assoc($result))
        {
            $member = [  
                        'id' => $row['memberID'],
                        'accountID' => $row['accountID'],
                        'email' => $row['email'],
                        'fName' => $row['fName'],
                        'lName' => $row['lName'],
                        'password' => $row['password'],
                        'phoneNumber' => $row['phoneNumber'],
                        'address' => $row['address'],
                        'img' => $row['image'],
                        'status' => $row['status'],
                        'expire' => $row['expire'],
                        'token' => $row['token'],
                        'registration' => $row['registration']
                    ];
            array_push($members,$member);
        }
        echo json_encode($members);
    }
?>