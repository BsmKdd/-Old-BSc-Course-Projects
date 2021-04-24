<?php
    session_start();
    include('connection.php');
?>
<link href="bootstrap-4.3.1-dist/css/bootstrap.min.css" rel="stylesheet"/>
<link href="index.css" rel="stylesheet"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Home</title>
</head>
<body class="text-center overflow-auto mx-auto p-sm-3 pb-sm-0 p-0 col-lg-8  col-12" onload="fillYears()">
    <div class="container text-left p-0 pt-0 pt-sm-4 shadow col-12 p-sm-4" id="containerId">
        <div class="header row" id="headerDiv">
            <div name="imageDic" class="col-sm-6 col-4 text-left">
                <img name="tempImage" src="Images/hiclipart.com.png" alt="tempImage" class="img-fluid rounded img-min" >
                <label for="tempImage" class="mt-auto">Temporary Logo</label>
            </div>
            <?php

             if(!isset($_SESSION['user']))
             {  echo "
                <div class=\"col-sm-6 col-8 text-right m-auto\">
                <a href=\"signup.html\"><button class=\"btn btn-info\">Sign Up</button></a>
                <a href=\"login.php\"><button class=\"btn btn-info\">Log In</button></a>
                </div>";
             } else {

                echo "
                <div class=\"col-sm-6 col-8 text-right m-auto\">
                Welcome ".$_SESSION['user']." <a href=\"logout.php\"><button class=\"btn btn-info ml-2\">Log Out</button></a>
                </div>";
             } 

            ?>
        </div>
                
        <div class="row"> 
            <div class="col-12 mt-5" id="navTabs" name="navTabs">
                <ul class="nav nav-tabs row mx-auto ">
                    <li class="col-4 col-sm-3 p-0 nav-item">
                        <a class="nav-link  text-center" data-toggle="tab" href="#home">Home</a>
                    </li>
                    <li class="col-4 col-sm-3 p-0 nav-item">
                        <a class="nav-link active text-center" data-toggle="tab" href="#Contribute">Contribute</a>
                    </li>
                    <li class="col-4 col-sm-3 p-0 nav-item">
                        <a class="nav-link text-center" href="SearchPage.html">Search</a>
                    </li>
                </ul>
            </div>

            <div class="mt-0 mt-sm-4 tab-content col-12 row " id="toGiveMx">
                <div class="tab-pane col-12 fade" id="home">
                <p class="text-justify">Lorem ipsum dolor sit, amet consectetur adipisicing elit. Molestias est soluta illo culpa sit nostrum expedita beatae animi, quod reprehenderit necessitatibus dolores temporibus obcaecati voluptates dignissimos enim delectus deleniti. Molestias iusto quis quibusdam, beatae consectetur sint magnam, maxime exercitationem modi assumenda hic ratione dolorum doloribus deserunt voluptates inventore cum sequi quia repellat pariatur odit perferendis, eligendi culpa. Magnam, tempore nisi expedita consequatur vel aut facilis sit ab qui quam reiciendis modi commodi ipsum magni perferendis repellendus, nulla libero explicabo id perspiciatis consectetur corporis amet, laboriosam exercitationem? Repellat obcaecati odio iusto nihil hic voluptatem, accusamus optio voluptatibus iure doloremque architecto beatae quae in. Pariatur ullam quisquam tempore ea, iste unde temporibus facere accusantium sapiente illum tenetur delectus beatae veniam commodi nisi. Quasi quod nesciunt, nam veritatis earum esse similique magnam nisi aliquam. Minima sunt consequatur animi vitae sint in optio explicabo dicta, unde perspiciatis possimus quod fuga a similique beatae? Laboriosam beatae quia magnam est inventore blanditiis quasi, perferendis officiis maxime atque neque consequuntur dolorum tempora a pariatur! Est illum molestiae optio quia. Tempore perferendis sunt nostrum itaque veritatis laboriosam excepturi, mollitia iste a minima asperiores debitis ullam enim. Est voluptatem vitae reiciendis doloribus porro et exercitationem, temporibus totam consequuntur perferendis? </p>
                
                </div>

                <div class="tab-pane col-12 active break-text" id="Contribute">
                    <br id="toDeleteBr">
                    <p id = "contributeText" class="font-weight-bold">This website is made possible through the contributions of the users.
                    <br><br>
                    So, if you have any exams that you think will expand our database and help others,
                    please do not hesitate to upload them.
                    <br><br>
                    If you have several exams to upload (>10), contact an admin directly through our contact forms in
                    order to facilitate the process.
                    </p>
                    <h4 class="mt-5 font-bold" id="uploadHeader">Exam Details: </h4>
                    <form id="uploadForm" method="" name="uploadForm" enctype="multipart/form-data" class = "mb-2 mb-sm-3 col-12 mt-0 mt-sm-3 mx-auto">
                        <div class = "form-group">
                        <div name = "department" class = "row mt-2 mt-sm-3" id="schoolDiv">
                            <select name="university" class="form-control col-sm-6 col-12" id="university" autocomplete="off" required>
                                <option value="" selected hidden disabled>Select University</option>
                                <option value="LIU">Lebanese International University</option>
                                <option value="AUB">American University of Beirut</option>
                                <option value="LAU">Lebanese Ameircan University</option>
                                <option value="AUST">American University of Science and Technology</option>
                            </select>
                        </div>
                        <div name = "department" class = "row" id="schoolDiv">
                            <select name="school" class="form-control col-12 col-sm-6 mt-sm-3" disabled="disabled" autocomplete="off" id="school" required>
                                <option value="" selected hidden >Select School</option>
                            </select>
                            <p name ="depWarning" class="col-12 col-sm-6 d-none d-sm-inline font-italic break-text">Note that this refers to the school responsible for the exam.</p>
                        </div>
                        <div name = "majorDiv" class = "row mt-0 mt-sm-3" id="majorDiv">
                            <select name="major" class="form-control col-sm-6 col-12 " disabled="disabled" autocomplete="off" id="major"  required>
                                <option value="" selected hidden >Major</option>
                            </select>
                            <!-- <p name ="depWarning" class="col-12 col-sm-6 font-italic break-text mt-2"></p> -->
                        </div>
                        </div>
                        <div name="time" class="row form-group mt-3">
                            <select id="year" name="year" class="form-control col-sm-3 col-12 " >
                            <option value="" selected  disabled>Year:</option>
                            </select>
                            <select name="semester" class="form-control ml-sm-3  col-sm-3 col-12 " >
                                <option value="" selected  hidden disabled  >Semester: </option>
                                <option value="Fall">Winter/Fall</option>
                                <option value="Spring">Spring</option>
                                <option value="Summer">Summer</option>
                            </select>
                            <select name="examNumber" class="form-control ml-sm-3 col-sm-3 col-12"  >
                                <option value="" selected disabled>Time</option>
                                <option value="Midterm">Midterm Exam</option>
                                <option value="First Exam">First Exam</option>
                                <option value="Final Exam">Final Exam</option>
                            </select>
                        </div>
                        <div class="row mt-1 mt-sm-3">
                        <textarea class="form-control" name="examNotes" placeholder="Write some notes about the attached exam, for example: Question 3, part b, X = 32, not 24." class="form-control rounded-5" rows="4" maxlength="336"></textarea> 
                        </div>
                        <div class = "row mt-1 mt-sm-3" >
                            <div class="form-group custom-file mb-0 mt-1 col-md-6 col-sm-6">
                                <input type="file" class="custom-file-input" name="files[]" disabled id="customFile" onchange="fileName();"  required>
                                <label class="custom-file-label text-truncate w-100" id="fileLabel" for="customFile">Choose file</label>
                            </div>
                            <p class="col-sm-5 col-12 d-none d-sm-inline mb-0 text-danger" name = "testP" id="testP">*Allowed formats: .doc/.docx/.pdf <br>  Max file size: 5MB </p>
                        </div>
                        <div id="progressDiv" class="row progress d-none" style="height:5px">
                            <div id="progressBar" class="progress-bar " role="progressbar" style="width: 0%;" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100">0%</div>
                        </div>
                        <div class="row mt-1 mt-sm-4">
                            <button  class="btn btn-info col-12 m-0 p-1 pr-0"  value="submit" onclick="" id="addSchool">Upload</button>
                        </div>
                        
                    </form>
            
                </div>

                <a href="SearchPage.html"><div class="tab-pane col-12 fade break-text" id="Search"><br></a>
                </div>
            </div>
         </div>   
    </div>
    <script type="text/javascript" src="bootstrap-4.3.1-dist/js/jquery-3.4.1.min.js"></script>
    <script type="text/javascript" src="bootstrap-4.3.1-dist/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="uploadForm.js"></script>
</body>
</html>
