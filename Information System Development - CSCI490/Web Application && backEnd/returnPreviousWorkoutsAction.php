<?php

    include("connection.php");

    if(!empty($_POST['token']))
    {
        $memberToken = $_POST['token'];
        $workoutQry = "
        SELECT pw.workoutID as workoutID, pw.date as finishDate
        FROM previousworkouts pw

        INNER JOIN members m ON m.id = pw.memberID
        INNER JOIN accounts am ON m.accountID = am.id

        WHERE am.token ='$memberToken'
        
        GROUP BY workoutID
        ORDER BY pw.date DESC";

        $result = mysqli_query($con, $workoutQry) or die(mysqli_error($con));
        $workouts = array();
        $workoutMoves = array();
        
        if(mysqli_num_rows($result) > 0)
        {
            while($row = mysqli_fetch_assoc($result))
            {
                $workoutID = $row['workoutID'];
                $moveCountQry = 
                "SELECT mo.moveName as moveName, mo.moveImage2 as moveImage
                
                FROM moves mo
                INNER JOIN workoutmoves wm ON wm.moveID = mo.id

                WHERE wm.workoutID = $workoutID
                
                GROUP BY mo.id";
               
                $count = mysqli_query($con, $moveCountQry) or die(mysqli_error($con));
                $counter = 0;
                
                $workout = 
                [  
                    'workoutID' => $row['workoutID'],
                    'date' => $row['finishDate'],
                ];

                while($rowCount = mysqli_fetch_assoc($count))
                {
                    $counter++;
                    $workout += ['moveName'.$counter => $rowCount['moveName']];
                    $workout += ['moveImg'.$counter => $rowCount['moveImage']];
                }

                $workout += ['moveCount' => $counter];


                array_push($workouts, $workout);
            }
        }
        echo json_encode($workouts);
    }
?>