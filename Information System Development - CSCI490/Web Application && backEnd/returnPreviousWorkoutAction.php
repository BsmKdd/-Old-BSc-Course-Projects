<?php

    include("connection.php");

    if(isset($_GET['token']))
    {
        $memberToken = $_GET['token'];
        $workoutQry = "
        SELECT pw.workoutID as workoutID, pw.date as finishDate
        FROM previousworkouts pw
        
        INNER JOIN members m ON m.id = pw.memberID
        INNER JOIN accounts am ON m.accountID = am.id
        
        WHERE am.token ='$memberToken'
        ORDER BY pw.date DESC";
        
        $result = mysqli_query($con, $workoutQry) or die(mysqli_error($con));
        $workouts = array();
        
        if(mysqli_num_rows($result) > 0)
        {
            while($row = mysqli_fetch_assoc($result))
            {
                $workoutID = $row['workoutID'];
                $moveCountQry = "SELECT * FROM workoutmoves WHERE workoutID = $workoutID";
                $count = mysqli_query($con, $moveCountQry) or die(mysqli_error($con));
                $counter = 0;

                while($rowCount = mysqli_fetch_assoc($count))
                {
                    $counter++;
                }
                
                $workout = 
                [  
                    'workoutID' => $row['workoutID'],
                    'date' => $row['finishDate'],
                    'moveCount' => $counter
                ];
                array_push($workouts, $workout);
            }
        }
        echo json_encode($workouts);
    }
?>