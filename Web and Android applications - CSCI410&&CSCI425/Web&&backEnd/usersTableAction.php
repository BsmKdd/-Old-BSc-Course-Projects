<?php
    include("connection.php");

    $uniQry="SELECT * FROM accounts WHERE admin=0";
    $result = mysqli_query($con,$uniQry) or die(mysqli_error($con));
    $accounts = array();

    if(mysqli_num_rows($result) > 0)
    {
        while($row = mysqli_fetch_assoc($result))
        {
            $account = ['ID' => $row['ID'],
                        'Email' => $row['email'],
                        'Username' => $row['Username'],
                        'First' => $row['firstName'],
                        'Last' => $row['lastName'],
                        'University' => $row['university'],
                        'Password' => $row['password'],
                        ];
            array_push($accounts,$account);
        }
        echo json_encode($accounts);
    }
?>