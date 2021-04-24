<?php
    include("../connection.php");

    if(!isset($_POST['key']))
    {
        exit("Access Denied");
    } else if($_POST['key'] != 'KeyForDatabase'){
        exit("Access Denied");
    };
    
    $bartendersQry="SELECT b.id as bartenderID, b.salary as salary, a.email as email, a.fName as fName, a.lName as lName, a.img as img,
                 a.id as accountID, a.password as password, a.address as address, a.phoneNumber as phoneNumber 
                 FROM bartenders b
                 INNER JOIN accounts a ON b.accountID = a.id";
    $result = mysqli_query($con,$bartendersQry) or die(mysqli_error($con));
    $bartenders = array();

    if(mysqli_num_rows($result) > 0)
    {
        while($row = mysqli_fetch_assoc($result))
        {
            $bartender = [  
                        'id' => $row['bartenderID'],
                        'accountID' => $row['accountID'],
                        'salary' => $row['salary'],
                        'email' => $row['email'],
                        'fName' => $row['fName'],
                        'lName' => $row['lName'],
                        'img' => $row['img'],
                        'password' => $row['password'],
                        'address' => $row['address'],
                        'phoneNumber' => $row['phoneNumber'],
                    ];
            array_push($bartenders, $bartender);
        }
        echo json_encode($bartenders);
    }
?>