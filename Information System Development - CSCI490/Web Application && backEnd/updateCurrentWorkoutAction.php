<?php

    include("connection.php");

    if(isset($_POST['memberToken']) && isset($_POST['moveID']))
    {
        $memberToken = $_POST['memberToken'];
        $moveID = $_POST['moveID'];
        $memberID;
        
        $checkQry = 
        "SELECT m.id as memberID
        FROM members m
        INNER JOIN accounts a ON m.accountID = a.id
        WHERE a.token = '$memberToken'";
        
        $result = mysqli_query($con, $checkQry) or die(mysqli_error($con));

        if(mysqli_num_rows($result) > 0)
        {
            $row = mysqli_fetch_assoc($result);
            $memberID = $row['memberID'];

            $VerifyQry = "SELECT * FROM currentworkouts WHERE memberID = $memberID";
            $verifyResult = mysqli_query($con, $VerifyQry) or die(mysqli_error($con));
            if(mysqli_num_rows($verifyResult) > 0)
            {
                $verifyRow = mysqli_fetch_assoc($verifyResult);
                $workoutID = $verifyRow['workoutID'];
                updateWorkout($workoutID, $moveID);
            } else {
                createWorkout($memberID);
                $verifyResult = mysqli_query($con, $VerifyQry) or die(mysqli_error($con));
                $verifyRow = mysqli_fetch_assoc($verifyResult);
                $workoutID = $verifyRow['workoutID'];
                updateWorkout($workoutID, $moveID);
            }
            
        } else {

        }  
    }
    
    function updateWorkout($workoutID, $moveID)
    {
        include("connection.php");
        $doubleCheckQry = "SELECT * FROM workoutmoves WHERE workoutID = $workoutID AND moveID = $moveID";
        $checkResult = mysqli_query($con, $doubleCheckQry) or die(mysqli_error($con));
        
        if(mysqli_num_rows($checkResult) > 0)
        {
            echo "Can't add duplicate moves to current workout.";
        } else {
            $updateQry = "INSERT INTO workoutmoves(workoutID, moveID) VALUES ($workoutID, $moveID)";
            $updateResult = mysqli_query($con, $updateQry) or die(mysqli_error($con));
            echo "Added, refresh your current workout tab.";
        }
            
    }
    
    function createWorkout($memberID)
    {
        include("connection.php");
        $createWorkoutQry = "INSERT INTO workouts (workoutID) VALUES (null)";
        mysqli_query($con, $createWorkoutQry) or die(mysqli_error($con));
        
        $createCurrentQry = "INSERT INTO currentworkouts(memberID, workoutID)
        VALUES ($memberID, LAST_INSERT_ID())";
        mysqli_query($con, $createCurrentQry) or die(mysqli_error($con));
    }
?>