<?php
    include("../connection.php");

    if(!isset($_POST['key']))
    {
        exit("Access Denied");
    } else if($_POST['key'] != 'KeyForDatabase'){
        exit("Access Denied");
    };
    
    $adsQry="SELECT id, namePT, descriptionPT, emailPT, phonePT, titlePT, image1PT, image2PT
                 FROM advertisementspt";
    $result = mysqli_query($con,$adsQry) or die(mysqli_error($con));
    $ads = array();

    if(mysqli_num_rows($result) > 0)
    {
        while($row = mysqli_fetch_assoc($result))
        {
            $ad = [  
                        'id' => $row['id'],
                        'email' => $row['emailPT'],
                        'name' => $row['namePT'],
                        'description' => $row['descriptionPT'],
                        'phoneNumber' => $row['phonePT'],
                        'title' => $row['titlePT'],
                        'img' => $row['image1PT'],
                        'banner' => $row['image2PT']
                    ];
            array_push($ads,$ad);
        }
        echo json_encode($ads);
    }
?>