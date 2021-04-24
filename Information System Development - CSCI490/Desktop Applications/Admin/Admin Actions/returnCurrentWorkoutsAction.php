<?php
    include("../connection.php");

    if(!isset($_POST['key']))
    {
        exit("Access Denied");
    } else if($_POST['key'] != 'KeyForDatabase'){
        exit("Access Denied");
    };
    
    $currentWorkoutsQry=
    "SELECT cw.id as id, cw.memberID as memberID, cw.workoutID as workoutID
    FROM currentworkouts cw";
    
    $result = mysqli_query($con,$currentWorkoutsQry) or die(mysqli_error($con));
    $currentWorkouts = array();

    if(mysqli_num_rows($result) > 0)
    {
        while($row = mysqli_fetch_assoc($result))
        {
            $currentWorkout = [  
                        'id' => $row['id'],
                        'memberID' => $row['memberID'],
                        'workoutID' => $row['workoutID']
                    ];
            array_push($currentWorkouts,$currentWorkout);
        }
        echo json_encode($currentWorkouts);
    }
?>