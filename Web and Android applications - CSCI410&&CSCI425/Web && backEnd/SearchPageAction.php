<?php
    include("connection.php");

    $examsQry="SELECT * FROM exams";
    $result = mysqli_query($con,$examsQry) or die(mysqli_error($con));
    $exams = array();

    if(mysqli_num_rows($result) > 0)
    {
        while($row = mysqli_fetch_assoc($result))
        {
            $exam = [   'ID' => $row['fileDir'],
                        'University' => $row['university'],
                        'School' => $row['school'],
                        'Major' => $row['major'],
                        'Year' => $row['year'],
                        'Semester' => $row['semester'],
                        'Time' => $row['number'],
                        'Note' => $row['note'],
                        'UploadDate' => $row['uploadDate']
                        ];
            array_push($exams,$exam);
        }
        echo json_encode($exams);
    }
?>