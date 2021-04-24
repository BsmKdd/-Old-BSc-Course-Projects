<?php
    include("../connection.php");

    if(!isset($_POST['key']))
    {
        exit("Access Denied");
    } else if($_POST['key'] != 'KeyForDatabase'){
        exit("Access Denied");
    };
    
    $previousWorkoutsQry=
    "SELECT pw.id as id, pw.memberID as memberID, pw.workoutID as workoutID, pw.date as date
    FROM previousworkouts pw";
    
    $result = mysqli_query($con,$previousWorkoutsQry) or die(mysqli_error($con));
    $previousWorkouts = array();

    if(mysqli_num_rows($result) > 0)
    {
        while($row = mysqli_fetch_assoc($result))
        {
            $previousWorkout = [  
                        'id' => $row['id'],
                        'memberID' => $row['memberID'],
                        'workoutID' => $row['workoutID'],
                        'date' => $row['date']
                    ];
            array_push($previousWorkouts,$previousWorkout);
        }
        echo json_encode($previousWorkouts);
    }
?>