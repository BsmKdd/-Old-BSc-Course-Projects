<?php
    include("connection.php");

    $majorQry="SELECT * FROM majors";
    $result = mysqli_query($con,$majorQry) or die(mysqli_error($con));
    $majors = array();

    if(mysqli_num_rows($result) > 0)
    {
        while($row = mysqli_fetch_assoc($result))
        {
            $major =   [ 
                        'ID' => $row['id'],
                        'Name' => $row['name'],
                        'schoolId' => $row['schoolId']
                        ];
            array_push($majors,$major);
        }
        echo json_encode($majors);
    }
?>