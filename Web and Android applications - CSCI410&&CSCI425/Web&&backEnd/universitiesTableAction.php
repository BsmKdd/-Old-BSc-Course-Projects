<?php
    include("connection.php");

    $uniQry="SELECT * FROM universities";
    $result = mysqli_query($con,$uniQry) or die(mysqli_error($con));
    $universities = array();

    if(mysqli_num_rows($result) > 0)
    {
        while($row = mysqli_fetch_assoc($result))
        {
            $university = [ 'Code' => $row['code'],
                            'Name' => $row['name'],
                            ];
            array_push($universities,$university);
        }
        echo json_encode($universities);
    }
?>