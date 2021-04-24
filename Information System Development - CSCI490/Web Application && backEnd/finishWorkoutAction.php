<?php

    include("connection.php");

    if(isset($_POST['token']))
    {
        $memberToken = $_POST['token'];
        $finishQry = "
        SELECT cw.workoutID as workoutID, m.id as memberID
        FROM currentworkouts cw
        
        INNER JOIN members m ON m.id = cw.memberID
        INNER JOIN accounts a ON m.accountID = a.id
        
        WHERE a.token = '$memberToken'";
        
        $result = mysqli_query($con, $finishQry) or die(mysqli_error($con));

        if(mysqli_num_rows($result) > 0)
        {
            $row = mysqli_fetch_assoc($result);
            $move = $row['workoutID'];
            $memberID = $row['memberID'];
           
            date_default_timezone_set('Asia/Beirut');
            $date = date("Y-m-d H:i:s");

            $insertQry = "
            INSERT INTO previousworkouts(workoutID, memberID, date)
            VALUES($move, $memberID, '$date')";
            mysqli_query($con, $insertQry);
            
            $removeQry = "DELETE FROM currentworkouts 
            WHERE memberID = '$memberID'";
            mysqli_query($con, $removeQry);

        }
    }
?>