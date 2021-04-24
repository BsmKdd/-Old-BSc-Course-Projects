<?php
    include("connection.php");

    $uniQry="SELECT * FROM exams";
    $result = mysqli_query($con,$uniQry) or die(mysqli_error($con));
    $exams = array();

    if(mysqli_num_rows($result) > 0)
    {
        while($row = mysqli_fetch_assoc($result))
        {
            $exam = ['id' => $row['id'],
                        'fileDir' => $row['fileDir'],
                        'university' => $row['university'],
                        'school' => $row['school'],
                        'major' => $row['major'],
                        'year' => $row['year'],
                        'semester' => $row['semester'],
                        'number' => $row['number'],
                        'note' => $row['note'],
                        'uploadDate' => $row['uploadDate'],

                        ];
            array_push($exams,$exam);
        }
        echo json_encode($exams);
    }
?>