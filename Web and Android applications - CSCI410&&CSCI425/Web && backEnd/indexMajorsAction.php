<?php
    include("connection.php");

    $schools = "";
    
    if(isset($_GET['id'])) $school = $_GET['id'];
    else $school = json_decode($_POST["jsonSchool"],false);

    if(!empty($school)) $schQry="SELECT * FROM majors WHERE schoolId = '$school'";
    else     $schQry="SELECT * FROM majors";


    $result = mysqli_query($con,$schQry) or die(mysqli_error($con));
    $majors = array();
    $placeHolder = ['id' => '', 'name' => 'Select Major:'];
    array_push($majors,$placeHolder);
    if(mysqli_num_rows($result) > 0)
    {
        while($row = mysqli_fetch_assoc($result))
        {
            $major = ['id' => $row['id'],'name' => $row['name']];
            array_push($majors,$major);
        }
        echo json_encode($majors);
    }
?>