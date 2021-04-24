<?php
    include("connection.php");

    $coachesQry="SELECT c.expertise as expertise, c.description as description,
                 a.fName as fName, a.lName as lName, a.img as img  
                 FROM coaches c
                 INNER JOIN accounts a ON c.accountID = a.id";
    $result = mysqli_query($con,$coachesQry) or die(mysqli_error($con));
    $coaches = array();

    if(mysqli_num_rows($result) > 0)
    {
        while($row = mysqli_fetch_assoc($result))
        {
            $coach = [  
                        'expertise' => $row['expertise'],
                        'description' => $row['description'],
                        'fName' => $row['fName'],
                        'lName' => $row['lName'],
                        'img' => $row['img']
                        ];
            array_push($coaches,$coach);
        }
        echo json_encode($coaches);
    }
?>