<?php
    include("connection.php");


    if(!empty($_FILES['files']['name'][0]))
    {

        $files = $_FILES['files'];

        $successful = array();
        $failed = array();

        $allowed = array('pdf','doc','docx');
        $count = 0;
        foreach($files['name'] as $position => $file_name)
        {
            $count++;
            $file_tmp = $files['tmp_name'][$position];
            $file_size = $files['size'][$position];
            $file_error = $files['error'][$position];

            $file_university = $_POST['university'];
            $file_school = $_POST['school'];
            $file_major = $_POST['major'];
            $file_year;
            $file_semester;
            $file_number;
            $file_note;

            if(empty($_POST['year'])) $file_year = "";
            else $file_year = $_POST['year'];
            if(empty($_POST['semester'])) $file_semester = "";
            else $file_semester = $_POST['semester'];
            if(empty($_POST['examNumber'])) $file_number = "";
            else $file_number = $_POST['examNumber'];
            if(empty($_POST['examNotes'])) $file_note = "No notes about this exam.";
            else $file_note = $_POST['examNotes'];

            $file_ext = explode('.', $file_name);
            $file_ext = strtolower(end($file_ext));

            if(in_array($file_ext, $allowed))
            {
                if($file_error === 0)
                {
                    if($file_size <= 5242880)
                    {
                        $file_name_new = $file_major.$file_year.$file_semester.rand(1,1000000).'.'.$file_ext;
                        $file_destination = 'uploads/'.$file_name_new;

                        if(move_uploaded_file($file_tmp, $file_destination))
                        {
                            $successful[$position] = $file_destination;
                            $currentDateTime = date('Y-m-d-h-i-s');
                            $qry="INSERT INTO exams(fileDir,university,school,major,year,semester,number,note,uploadDate) 
                                                    VALUES('$file_name_new','$file_university','$file_school','$file_major','$file_year','$file_semester','$file_number','$file_note','$currentDateTime');";
                                                    
                                                    
                            if(mysqli_query($con,$qry)) echo "File uploaded!";

                            else echo "Error!";
                            
                        } else {
                            $failed[$position] = "[{$file_name}] failed to upload.";
                            echo $file_name ," Failed to upload.  \n";
                        }
                        
                    } else {
                        $failed[$position] = "[{$file_name}] is too large.";
                        echo $file_name ," is too large.  \n";
                    }
                } else {
                    $failed[$position] = "[{$file_name}] error with code {$file_error}";
                    echo $file_name ," Error code: ", $file_error, " \n";
                }
            } else {
                $failed[$position] = "[{$file_name}] file extension '{$file_ext}' is not allowed.";
                echo $file_name ," file extension ", $file_ext," is not allowed. \n";
            }     
        }

        if(!empty($successful))
        {
            // echo "Upload successful";
        }
        
        if(!empty($failed))
        {
            // print_r($failed);
        }
    }


?>
