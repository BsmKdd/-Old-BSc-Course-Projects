<?php
    include("../connection.php");

    if(!isset($_POST['key']))
    {
        exit("Access Denied");
    } else if($_POST['key'] != 'KeyForDatabase'){
        exit("Access Denied");
    };
    
    $menuItemsQry=
    "SELECT mi.id as id, mi.itemName as Name, mi.itemDescription as Description, mi.itemImg as img, 
    mi.calories as Calories, mi.fats as Fats, mi.protein as Protein, mi.carbohydrates as Carbohydrates, 
    mi.sugar as Sugar, mi.itemPrice as Price, mi.available as Available, mi.preparation as Time
    FROM menuitems mi";
    
    $result = mysqli_query($con,$menuItemsQry) or die(mysqli_error($con));
    $menuItems = array();

    if(mysqli_num_rows($result) > 0)
    {
        while($row = mysqli_fetch_assoc($result))
        {
            $menuItem = [  
                        'id' => $row['id'],
                        'Name' => $row['Name'],
                        'Description' => $row['Description'],
                        'Img' => $row['img'],
                        'Calories' => $row['Calories'],
                        'Fats' => $row['Fats'],
                        'Protein' => $row['Protein'],
                        'Carbohydrates' => $row['Carbohydrates'],
                        'Sugar' => $row['Sugar'],
                        'Time' => $row['Time'],
                        'Price' => $row['Price'],
                        'Available' => $row['Available']
                    ];
            array_push($menuItems,$menuItem);
        }
        echo json_encode($menuItems);
    }
?>