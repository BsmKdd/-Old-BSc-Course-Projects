<?php
    include("../connection.php");

    if(!isset($_POST['key']))
    {
        exit("Access Denied");
    } else if($_POST['key'] != 'KeyForDatabase'){
        exit("Access Denied");
    };
    
    $coachesQry="SELECT c.id as coachID, c.expertise as expertise, c.description as description,
                 c.hireDate as hired, c.salary as salary,
                 a.email as email, a.fName as fName, a.lName as lName, a.img as img,
                 a.id as accountID, a.password as password, a.address as address, a.phoneNumber as phoneNumber 
                 FROM coaches c
                 INNER JOIN accounts a ON c.accountID = a.id";
    $result = mysqli_query($con,$coachesQry) or die(mysqli_error($con));
    $coaches = array();

    if(mysqli_num_rows($result) > 0)
    {
        while($row = mysqli_fetch_assoc($result))
        {
            $coach = [  
                        'id' => $row['coachID'],
                        'accountID' => $row['accountID'],
                        'expertise' => $row['expertise'],
                        'description' => $row['description'],
                        'hired' => $row['hired'],
                        'salary' => $row['salary'],
                        'email' => $row['email'],
                        'fName' => $row['fName'],
                        'lName' => $row['lName'],
                        'img' => $row['img'],
                        'password' => $row['password'],
                        'address' => $row['address'],
                        'phoneNumber' => $row['phoneNumber'],
                    ];
            array_push($coaches,$coach);
        }
        echo json_encode($coaches);
    }
?>