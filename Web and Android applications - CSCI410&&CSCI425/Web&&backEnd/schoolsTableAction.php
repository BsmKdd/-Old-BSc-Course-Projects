<?php
    include("connection.php");

    $schoolQry="SELECT * FROM schools";
    $result = mysqli_query($con,$schoolQry) or die(mysqli_error($con));
    $schools = array();

    if(mysqli_num_rows($result) > 0)
    {
        while($row = mysqli_fetch_assoc($result))
        {
            $school =   [ 
                        'ID' => $row['id'],
                        'Name' => $row['name'],
                        'universityCode' => $row['universityCode']
                        ];
            array_push($schools,$school);
        }
        echo json_encode($schools);
    }
?>