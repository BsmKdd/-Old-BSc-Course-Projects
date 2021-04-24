<?php
    include("../connection.php");

    if(!isset($_POST['key']))
    {
        exit("Access Denied");
    } else if($_POST['key'] != 'KeyForDatabase'){
        exit("Access Denied");
    };
    
    $assignedWorkoutsQry=
    "SELECT aw.id as id, aw.assignedBy as coachID, aw.workoutID as workoutID, aw.time as time,
    aw.assignedTo as memberID
    FROM assignedworkouts aw";
    
    $result = mysqli_query($con,$assignedWorkoutsQry) or die(mysqli_error($con));
    $assignedWorkouts = array();

    if(mysqli_num_rows($result) > 0)
    {
        while($row = mysqli_fetch_assoc($result))
        {
            $assignedWorkout = [  
                        'id' => $row['id'],
                        'memberID' => $row['memberID'],
                        'workoutID' => $row['workoutID'],
                        'coachID' => $row['coachID'],
                        'time' => $row['time']
                    ];
            array_push($assignedWorkouts,$assignedWorkout);
        }
        echo json_encode($assignedWorkouts);
    }
?>