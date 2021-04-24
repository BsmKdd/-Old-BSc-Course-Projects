<?php

    include("connection.php");

    if(isset($_GET['token']))
    {
        $memberToken = $_GET['token'];
        $workoutQry = "
        SELECT aw.workoutID as workoutID, ac.fName as coachFName, 
        ac.lName as coachLName, aw.time as assignedDate
        FROM assignedworkouts aw
        
        INNER JOIN members m ON m.id = aw.assignedTo
        INNER JOIN coaches c ON c.id = aw.assignedBy
        INNER JOIN accounts ac ON c.accountID = ac.id
        INNER JOIN accounts am ON m.accountID = am.id
        
        WHERE am.token ='$memberToken'
        ORDER BY aw.time DESC";
        
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
                    'coachName' => $row['coachFName']." ".$row['coachLName'],
                    'date' => $row['assignedDate'],
                    'moveCount' => $counter
                ];
                array_push($workouts, $workout);
            }
        }
        echo json_encode($workouts);
    }
?>