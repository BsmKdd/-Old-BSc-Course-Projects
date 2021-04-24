<?php
    include("connection.php");

    if(isset($_GET['param1']))
    {
        $scannedMachine = $_GET['param1'];
    
        $movesQry=
        "SELECT mo.id as moveID, mo.moveName AS moveName, mo.moveImage1 AS moveImage1,
        mo.moveImage2 AS moveImage2, mo.moveGif AS moveGif, 
        mo.moveDescription AS moveDescription, mo.muscleGroup1 AS moveMuscleGroup1,
        ma.id AS machineID, ma.machineImage as machineImage, ma.machineName as machineName, 
        maLo.floor AS floor, maLo.section as section
        FROM moves mo 
        INNER JOIN machines ma ON mo.machineID = ma.id
        INNER JOIN machinelocation maLo ON ma.id = maLo.machineID
        WHERE ma.machineCode = '$scannedMachine'";
        
        $result = mysqli_query($con,$movesQry) or die(mysqli_error($con));
        $moves = array();
    
        if(mysqli_num_rows($result) > 0)
        {
            while($row = mysqli_fetch_assoc($result))
            {
                $move = [  
                            'ID' => $row['moveID'],
                            'machineID' => $row['machineID'],
                            'Name' => $row['moveName'],
                            'Image1' => $row['moveImage1'],
                            'Image2' => $row['moveImage2'],
                            'Gif' => $row['moveGif'],
                            'Description' => $row['moveDescription'],
                            'muscleGroup1' => $row['moveMuscleGroup1'],
                            'machineName' => $row['machineName'],
                            'machineImage' => $row['machineImage'],
                            'floor' => $row['floor'],
                            'section' => $row['section']
                        ];
                array_push($moves,$move);
            }
            echo json_encode($moves);
        }
    }
?>