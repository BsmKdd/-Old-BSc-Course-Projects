<?php

    include("connection.php");

    $username = json_decode($_POST["jsonUsername"],false);

    $checkqry = "SELECT Username FROM accounts WHERE Username='$username'";
    $validation = mysqli_query($con,$checkqry);

    echo json_encode(mysqli_num_rows($validation) > 0);

?>