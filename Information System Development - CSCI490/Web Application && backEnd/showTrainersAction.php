<?php
    include("connection.php");

    $trainersQry="SELECT * FROM advertisementspt";
    $result = mysqli_query($con,$trainersQry) or die(mysqli_error($con));
    $trainers = array();

    if(mysqli_num_rows($result) > 0)
    {
        while($row = mysqli_fetch_assoc($result))
        {
            $trainer = [   'ID' => $row['id'],
                        'Name' => $row['namePT'],
                        'Description' => $row['descriptionPT'],
                        'Email' => $row['emailPT'],
                        'Phone' => $row['phonePT'],
                        'Title' => $row['titlePT'],
                        'ProfileImage' => $row['image1PT'],
                        'BannerImage' => $row['image2PT']
                        ];
            array_push($trainers,$trainer);
        }
        echo json_encode($trainers);
    }
?>