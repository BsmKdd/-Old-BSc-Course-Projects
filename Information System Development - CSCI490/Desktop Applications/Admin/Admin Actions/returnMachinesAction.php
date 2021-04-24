<?php
    include("../connection.php");

    if(!isset($_POST['key']))
    {
        exit("Access Denied");
    } else if($_POST['key'] != 'KeyForDatabase'){
        exit("Access Denied");
    };
    
    $machinesQry="SELECT m.id as id, m.machineName as Name, m.machineCode as Code, m.machineImage as Img,
                 ml.floor as floor, ml.section as section
                 FROM machines m
                 INNER JOIN machinelocation ml ON ml.machineID = m.id";
    $result = mysqli_query($con,$machinesQry) or die(mysqli_error($con));
    $machines = array();

    if(mysqli_num_rows($result) > 0)
    {
        while($row = mysqli_fetch_assoc($result))
        {
            $machine = [  
                        'id' => $row['id'],
                        'Name' => $row['Name'],
                        'Code' => $row['Code'],
                        'img' => $row['Img'],
                        'floor' => $row['floor'],
                        'section' => $row['section']
                    ];
            array_push($machines, $machine);
        }
        echo json_encode($machines);
    }
?>