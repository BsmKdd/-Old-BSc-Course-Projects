<?php
    include("../connection.php");

    if(!isset($_POST['key']))
    {
        exit("Access Denied");
    } else if($_POST['key'] != 'KeyForDatabase'){
        exit("Access Denied");
    };
    
    $movesQry=
    "SELECT m.id as id, m.machineID as machineID, m.moveDescription as Description, m.muscleGroup1 as muscleGroup1, m.moveName as Name, m.moveImage1 as moveImage1, m.moveImage2 as moveImage2, m.moveGif as moveGif
    FROM moves m";
    
    $result = mysqli_query($con,$movesQry) or die(mysqli_error($con));
    $moves = array();

    if(mysqli_num_rows($result) > 0)
    {
        while($row = mysqli_fetch_assoc($result))
        {
            $move = [  
                        'id' => $row['id'],
                        'machineID' => $row['machineID'],
                        'Name' => $row['Name'],
                        'Description' => $row['Description'],
                        'muscleGroup1' => $row['muscleGroup1'],
                        'moveImage1' => $row['moveImage1'],
                        'moveImage2' => $row['moveImage2'],
                        'moveGif' => $row['moveGif']
                    ];
            array_push($moves,$move);
        }
        echo json_encode($moves);
    }
?>