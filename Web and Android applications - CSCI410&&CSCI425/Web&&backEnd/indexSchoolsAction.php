<?php
    include("connection.php");
    $university = "";
    // if(!($_SERVER['HTTP_USER_AGENT'] == "Android"))
    // {
    //     $university = json_decode($_POST["jsonUniversity"],false);
    // }
    // else if($_SERVER['HTTP_USER_AGENT'] == "Android")
    // {    
    //     if($_POST['key'] == "BassamMobile")
    //     {
    //         $university = $_POST['schoolUniversity'];
    //     } else echo "Key Error";
    // } 
    
    // if(isset($_POST['schoolUniversity'])) 
    // {
    //     if(!empty($_POST['schoolUniversity']))
    //     $university = $_POST['schoolUniversity'];
    // }
    if(isset($_GET['id'])) $university = $_GET['id'];
    else $university = json_decode($_POST["jsonUniversity"],false);
    
    if(!empty($university)) $uniQry="SELECT * FROM schools WHERE universityCode = '$university'";
    else $uniQry="SELECT * FROM schools";

    $result = mysqli_query($con,$uniQry) or die(mysqli_error($con));
    $schools = array();
    $placeHolder = ['id' => '', 'name' => 'Select School:'];
    array_push($schools,$placeHolder);

    if(mysqli_num_rows($result) > 0)
    {
        while($row = mysqli_fetch_assoc($result))
        {
            $school = [ 'id' => $row['id'],
                        'name' => $row['name']
                        ];
            array_push($schools,$school);
        }
        echo json_encode($schools);
    }
?>