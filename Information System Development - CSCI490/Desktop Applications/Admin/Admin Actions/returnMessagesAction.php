<?php
    include("../connection.php");

    if(!isset($_POST['key']))
    {
        exit("Access Denied");
    } else if($_POST['key'] != 'KeyForDatabase'){
        exit("Access Denied");
    };
    
    $messagesQry=
    "SELECT m.id as id, m.subject as subject, m.content as conten, m.email as email, m.type as type, m.time as time
    FROM messages m";
    
    $result = mysqli_query($con,$messagesQry) or die(mysqli_error($con));
    $messages = array();

    if(mysqli_num_rows($result) > 0)
    {
        while($row = mysqli_fetch_assoc($result))
        {
            $message = [  
                        'id' => $row['id'],
                        'subject' => $row['subject'],
                        'content' => $row['content'],
                        'email' => $row['email'],
                        'type' => $row['type'],
                        'time' => $row['time'],
                    ];
            array_push($messages,$message);
        }
        echo json_encode($messages);
    }
?>