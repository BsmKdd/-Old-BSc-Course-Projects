<?php
    session_start();
    include('connection.php');

    if(!isset($_SESSION['admin']))
    {  
        header('location:login.php');
    }

?>

<link href="bootstrap-4.3.1-dist/css/bootstrap.min.css" rel="stylesheet"/>
<link rel="stylesheet" href="fontawesome-free-5.12.0-web/css/all.css">
<link href="adminCp.css" rel="stylesheet"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
</head>
<body>
    <div class="wrapper">

    <nav id="sidebar">
            <?php
                echo "<p>Welcome ".$_SESSION['admin']."</p>";
             ?>
            <div name="listDiv" class="mb-2">
        <div class="nav flex-column nav-pills" id="v-pills-tab" role="tablist" aria-orientation="vertical">

            <a class="nav-link active text-center p-3" id="usersPage" data-toggle="tab" href="#Users">Users</a>

            <a class="nav-link  text-center p-3" id="examsPage"   data-toggle="tab" href="#Exams">Exams</a>

            <a class="nav-link  text-center p-3" id="universitiesPage"  data-toggle="tab" href="#Universities">Universities</a>

            <a class="nav-link  text-center p-3" id="schoolsPage"  data-toggle="tab" href="#Schools">Schools</a>

            <a class="nav-link  text-center p-3" id="majorsPage"  data-toggle="tab" href="#Majors">Majors</a>

            <a class="nav-link  text-center p-3" id="coursesPage"  data-toggle="tab" href="#Courses">Courses</a>
        </div>
        </div>
        <a href="index.php"><button name="homeBtn" class="btn btn-info mb-2">Home Page</button></a> <br>
        <a href="logout.php"><button  name="logoutBtn" class="btn btn-info" id="logoutBtn">Logout</button></a> 
    </nav>
      <div class="tab-content w-100 p-5">
            <div class="tab-pane active" id="Users">
            <form id="addAccountForm">
                <div class=" row form-group col-12 p-0">   
                    <input name="email" id="email" type="" class="col-3 form-control" autocomplete="off" placeholder="Enter email" maxlength=50>           
                    <input name="username" id="username" type="text" class="col-2 form-control ml-1" placeholder="Username" autocomplete="off" maxlength=25  >
                    <input name="fName" id="fName" type="text" class="col-1 form-control ml-1"  placeholder="First" autocomplete="off" style="min-width: 147px !important;" maxlength=20  >
                    <input name="lName" id="lName" type="text" class="col-1  form-control ml-1" placeholder="Last" autocomplete="off" style="min-width: 146px !important;" maxlength=20  >
                    <select name="university" id="university" class="col-1 form-control ml-1" >
                        <option value="">University</option>
                        <option value="LIU">LIU</option>
                        <option value="AUB">AUB</option>
                        <option value="LAU">LAU</option>
                        <option value="AUST">AUST</option>
                    </select>
                    <input name="password" id="password" type="" class="col-2 form-control ml-2"  placeholder="Password" maxlength=20>
                    <button  class="btn btn-info col-1 ml-1 m-0 p-1 pr-0"  onclick="return false" id="addAccount">Add</button>    
                </div>
                </form>
            <table class="table table-md table-striped table-bordered" id="usersTable">
                <thead class="">
                    <tr>
                        <th scope="col" class="td-xsm text-center">ID</th>
                        <th scope="col">email</th>
                        <th scope="col">Username</th>
                        <th scope="col">firstName</th>
                        <th scope="col">lastName</th>
                        <th scope="col">university</th>
                        <th scope="col">password</th>
                        <th scope="col" class="td-xsm text-center col-1">Actions</th>
                    </tr>
                </thead>
                <tbody id="usersTBody">
                </tbody>
            </table>
            
            </div>
         <div class="tab-pane" id="Exams"> 
            <table class="table table-md table-striped table-bordered" id="examsTable">
                <thead class="">
                    <tr>
                        <th scope="col">ID</th>
                        <th scope="col">fileDir</th>
                        <th scope="col">university</th>
                        <th scope="col">school</th>
                        <th scope="col">major</th>
                        <th scope="col">year</th>
                        <th scope="col">semester</th>
                        <th scope="col">number</th>
                        <th scope="col" class="text-center col-1">note</th>
                        <th scope="col">uploadDate</th>
                        <th scope="col" class="text-center col-1">Actions</th>
                    </tr>
                </thead>
                <tbody id="examsTBody">
                </tbody>
            </table> 
        </div>
        <div class="tab-pane col-8 m-auto " id="Universities"> 
            <form id="addUniversityForm">
            <div class=" row form-group col-12 p-0">   
                <input name="universityCodeInput" id="universityCodeInput" type="" class="col-3 form-control" autocomplete="off" placeholder="Code" maxlength=50>           
                <input name="universityNameInput" id="universityNameInput" type="text" class="col-7 mr-2 form-control ml-1" placeholder="Name" autocomplete="off" maxlength=25  >
                <button  class="btn btn-info col-1 ml-5 m-0 p-1 pr-0"  onclick="return false" id="addUniversity">Add</button>    
            </div>
            </form>  
        <table class="table  table-md table-striped table-bordered " id="universitiesTable">
            <thead class="">
                <tr>
                    <th scope="col"  class="td-xsm text-center">code</th>
                    <th scope="col">name</th>
                    <th scope="col" class="text-center col-1">Actions</th>
                </tr>
            </thead>
            <tbody id="universitiesTBody">
            </tbody>
        </table> 
        </div>
        <div class="tab-pane col-10 m-auto" id="Schools" >
            <form id="addSchoolForm">
                <div class=" row form-group col-12 p-0">   
                    <input name="schoolIdInput" id="schoolIdInput" type="" class="col-3 form-control" autocomplete="off" placeholder="ID" maxlength=50>           
                    <input name="schoolNameInput" id="schoolNameInput" type="text" class="col-4 mr-2 form-control ml-1" placeholder="Name" autocomplete="off" maxlength=25  >
                    <input name="relUniversityCodeInput" id="relUniversityCodeInput" type="text" class="col-3 mr-3 form-control ml-1" placeholder="University Code" autocomplete="off" maxlength=25  >
                    <button  class="btn btn-info col-1 ml-5 m-0 p-1 pr-0"  onclick="return false" id="addSchool">Add</button>    
                </div>
            </form> 
             <table class="table table-md table-striped table-bordered" id="schoolsTable">
                <thead class="">
                    <tr>
                        <th scope="col">id</th>
                        <th scope="col">name</th>
                        <th scope="col">universityCode</th>
                        <th scope="col" class="text-center col-1">Actions</th>
                    </tr>
                </thead>
                <tbody id="schoolsTBody">
                </tbody>
            </table>
         </div>
        <div class="tab-pane col-10 m-auto" id="Majors"> 
            <form id="addMajorForm">
                <div class=" row form-group col-12 p-0">   
                    <input name="majorIdInput" id="majorIdInput" type="" class="col-3 form-control" autocomplete="off" placeholder="ID" maxlength=50>           
                    <input name="majorNameInput" id="majorNameInput" type="text" class="col-4 mr-2 form-control ml-1" placeholder="Name" autocomplete="off" maxlength=25  >
                    <input name="relSchoolIdInput" id="relSchoolIdInput" type="text" class="col-3 mr-3 form-control ml-1" placeholder="School ID" autocomplete="off" maxlength=25  >
                    <button  class="btn btn-info col-1 ml-5 m-0 p-1 pr-0"  onclick="return false" id="addMajor">Add</button>    
                </div>
            </form> 
            <table class="table table-md table-striped table-bordered" id="majorsTable">
                <thead class="">
                    <tr>
                        <th scope="col">id</th>
                        <th scope="col">name</th>
                        <th scope="col">schoolId</th>
                        <th scope="col" class="text-center col-1">Actions</th>
                    </tr>
                </thead>
                <tbody id="majorsTBody">
                </tbody>
            </table>
         </div>
        <div class="tab-pane" id="Courses">
            <table class="table table-md table-striped table-bordered" id="coursesTable">
            <thead class="">
                <tr>
                    <th scope="col">id</th>
                    <th scope="col">name</th>
                    <th scope="col">majorId</th>
                    <th scope="col" class="text-center col-1">Actions</th>
                </tr>
            </thead>
            <tbody id="coursesTBody">
            </tbody>
        </table>
         </div>
        </div> 
    </div>  
    
    <div id="myModal" class="modal fade" role="dialog">
        <div class="modal-dialog">
            <div class="modal-content">
                <div id="modalContent" class="modal-body">
                </div>
            </div>
        </div>
    </div>
 
</body>

    <script type="text/javascript" src="bootstrap-4.3.1-dist/js/jquery-3.4.1.min.js"></script>
    <script type="text/javascript" src="bootstrap-4.3.1-dist/js/popper.min.js"></script>
    <script type="text/javascript" src="bootstrap-4.3.1-dist/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="bootstrap-4.3.1-dist/js/jquery.dataTables.min.js"></script>
    <script type="text/javascript" src="bootstrap-4.3.1-dist/js/datatables.min.js"></script>
</html>

<script type="text/javascript">

    var usersTab = document.getElementById('usersPage');
    var examsTab = document.getElementById('examsPage');
    var universitiesTab = document.getElementById('universitiesPage');
    var schoolsTab = document.getElementById('schoolsPage');
    var majorsTab = document.getElementById('majorsPage');
    var coursesTab = document.getElementById('coursesPage');

    usersTab.addEventListener("click", loadAccounts);
    document.addEventListener("DOMContentLoaded", loadAccounts);
    examsTab.addEventListener("click",loadExams);
    universitiesTab.addEventListener("click", loadUniversities);
    schoolsTab.addEventListener("click", loadSchools);
    majorsTab.addEventListener("click", loadMajors);

    var td1;
    var td2;
    var td3;
    var td4;
    var td5;
    var td6; 
    var td7; 
    var td8;
    var td9;
    var tdActions;
    var aDelete;

    var row;
    var editAction;
    var deleteAction;
    
    var btnDelete;


    //Accounts Table Functions

    var myUsersTable = document.getElementById('usersTable');
    
    function loadAccounts() 
    {
        row = myUsersTable.rows;
        editAction = 'EditUserAction.php';
        deleteAction = 'DeleteUserAction.php';
        
        const usersTBody = document.querySelector("#usersTable > tbody");
        var xhr = new XMLHttpRequest();
        xhr.open('POST','usersTableAction.php',true);
        xhr.setRequestHeader('Content-type','application/x-www-form-urlencoded');
        xhr.send();
        
        xhr.onload = function()
        {   
            if(this.status == 200)
            {
                var arr = JSON.parse(this.responseText);
            }

            while(usersTBody.rows.length > 0)
            {
                usersTBody.removeChild(usersTBody.firstChild);
            }

            for(var i = 0; i < Object.keys(arr).length; i++)
            {
                var userID = arr[i].ID;
                var userEmail = arr[i].Email;  
                var userUsername = arr[i].Username; 
                var userFirst = arr[i].First;
                var userLast = arr[i].Last;
                var userUniversity = arr[i].University;
                var userPassword = arr[i].Password;

                td1 = document.createElement("td");
                td2 = document.createElement("td");
                td3 = document.createElement("td");
                td4 = document.createElement("td");
                td5 = document.createElement("td");
                td6 = document.createElement("td");
                td7 = document.createElement("td");

                td1.textContent = userID;
                td2.textContent = userEmail;
                td3.textContent = userUsername;
                td4.textContent = userFirst;
                td5.textContent = userLast;
                td6.textContent = userUniversity;
                td7.textContent = userPassword;

                tdActions = document.createElement("td");
                aDelete = document.createElement('a');
                aDelete.id = userID;

                btnDelete = document.createElement('button');
                btnDelete.className = "btn p-0 fas fa-trash fa-lg";
                btnDelete.style = "color: red";
                btnDelete.id = userID;
                aDelete.appendChild(btnDelete);

                aDelete.className = "ml-2";

                var tdActions = document.createElement("td");
                aDelete.addEventListener("click", deleteEntry);

                var tr = document.createElement("tr");

                tr.appendChild(td1);
                tr.appendChild(td2);
                tr.appendChild(td3);
                tr.appendChild(td4);
                tr.appendChild(td5);
                tr.appendChild(td6);
                tr.appendChild(td7);
                tr.appendChild(tdActions);

                tdActions.appendChild(aDelete);

                td1.className = "text-center";
                tdActions.className = "text-center";

                usersTBody.appendChild(tr);            
            }  

            for (var i = 1; i < row.length; i++) 
            {
                for (var j = 1; j < row[i].cells.length - 1; j++ ) 
                {
                    row[i].cells[j].addEventListener('dblclick', editTD); 
                    row[i].cells[j].addEventListener('focusout', releaseTD); 
                }
            }

        }
     }
     
    var addAccountButton = document.getElementById("addAccount");
    addAccountButton.addEventListener("click", addAccount);
    function addAccount()
    {
        var eEmail = document.getElementById("email").value;
        var eUsername = document.getElementById("username").value;
        var eFName = document.getElementById("fName").value;
        var eLName = document.getElementById("lName").value;
        var eUniversity = document.getElementById("university").value;
        var ePassword = document.getElementById("password").value;

        var newPackage = JSON.stringify({
                                        Email:eEmail,
                                        Username:eUsername,
                                        fName:eFName,
                                        lName:eLName,
                                        University:eUniversity,
                                        Password:ePassword
                                        });

        var xhr = new XMLHttpRequest();
        xhr.open('POST','AddUserAction.php',true);
        xhr.setRequestHeader('Content-type','application/x-www-form-urlencoded');
        xhr.send("jsonAdd=" + newPackage);
        xhr.onload = function()
		{
			if(this.status === 200)
            {   
                document.getElementById("addAccountForm").reset();                        
                loadAccounts();  
            }
        }
    }

    //Universities table functions

    var myUniversitiesTable = document.getElementById('universitiesTable');
    function loadUniversities() 
    {
        row = myUniversitiesTable.rows;
        editAction = 'EditUniversityAction.php';
        deleteAction = 'DeleteUniversityAction.php';

        const universitiesTBody = document.querySelector("#universitiesTable > tbody");
        var xhr = new XMLHttpRequest();
        xhr.open('POST','universitiesTableAction.php',true);
        xhr.setRequestHeader('Content-type','application/x-www-form-urlencoded');
        xhr.send();
        
        xhr.onload = function()
        {   
            if(this.status == 200)
            {
                var arr = JSON.parse(this.responseText);
            }

            while(universitiesTBody.rows.length > 0)
            {
                universitiesTBody.removeChild(universitiesTBody.firstChild);
            }

            for(var i = 0; i < Object.keys(arr).length; i++)
            {
                var universityCode = arr[i].Code;
                var universityName = arr[i].Name;   

                td1 = document.createElement("td");
                td2 = document.createElement("td");

                td1.textContent = universityCode;
                td2.textContent = universityName;

                tdActions = document.createElement("td");
                aDelete = document.createElement('a');
                aDelete.id = universityCode;

                btnDelete = document.createElement('button');
                btnDelete.className = "btn p-0 fas fa-trash fa-lg";
                btnDelete.style = "color: red";
                btnDelete.id = universityCode;
                aDelete.appendChild(btnDelete);

                aDelete.className = "ml-2";

                var tdActions = document.createElement("td");
                aDelete.addEventListener("click", deleteEntry);

                var tr = document.createElement("tr");

                tr.appendChild(td1);
                tr.appendChild(td2);
                tr.appendChild(tdActions);

                tdActions.appendChild(aDelete);

                td1.className = "text-center";
                tdActions.className = "text-center";

                universitiesTBody.appendChild(tr);            
            }  
            

            for (var i = 1; i < row.length; i++) 
            {
                for (var j = 1; j < row[i].cells.length - 1; j++ ) 
                {
                    row[i].cells[j].addEventListener('dblclick', editTD); 
                    row[i].cells[j].addEventListener('focusout', releaseTD); 
                }
            }
        }
     }
     
    var addUniversityButton = document.getElementById("addUniversity");
    addUniversityButton.addEventListener("click", addUniversity);
    function addUniversity()
    {
        var eUniversityCode = document.getElementById("universityCodeInput").value;
        var eUniversityName = document.getElementById("universityNameInput").value;

        var newPackage = JSON.stringify({
                                        Code:eUniversityCode,
                                        Name:eUniversityName,
                                        });

        var xhr = new XMLHttpRequest();
        xhr.open('POST','AddUniversityAction.php',true);
        xhr.setRequestHeader('Content-type','application/x-www-form-urlencoded');
        xhr.send("jsonAdd=" + newPackage);
        
        xhr.onload = function()
		{
			if(this.status === 200)
            {   
                document.getElementById("addUniversityForm").reset();            
                loadUniversities();  
            }
        }

    }

    //Schools table functions

    var mySchoolsTable = document.getElementById('schoolsTable');

    function loadSchools() 
    {
        row = mySchoolsTable.rows;
        editAction = 'EditSchoolAction.php';
        deleteAction = 'DeleteSchoolAction.php';

        const schoolsTBody = document.querySelector("#schoolsTable > tbody");
        var xhr = new XMLHttpRequest();
        xhr.open('POST','schoolsTableAction.php',true);
        xhr.setRequestHeader('Content-type','application/x-www-form-urlencoded');
        xhr.send();
        
        xhr.onload = function()
        {   
            if(this.status == 200)
            {
                var arr = JSON.parse(this.responseText);
            }

            while(schoolsTBody.rows.length > 0)
            {
                schoolsTBody.removeChild(schoolsTBody.firstChild);
            }

            for(var i = 0; i < Object.keys(arr).length; i++)
            {
                var schoolId = arr[i].ID;
                var schoolName = arr[i].Name;   
                var universityCode = arr[i].universityCode;   

                td1 = document.createElement("td");
                td2 = document.createElement("td");
                td3 = document.createElement("td");

                td1.textContent = schoolId;
                td2.textContent = schoolName;
                td3.textContent = universityCode;

                tdActions = document.createElement("td");
                aDelete = document.createElement('a');
                aDelete.id = schoolId;

                btnDelete = document.createElement('button');
                btnDelete.className = "btn p-0 fas fa-trash fa-lg";
                btnDelete.style = "color: red";
                btnDelete.id = schoolId;
                aDelete.appendChild(btnDelete);

                aDelete.className = "ml-2";

                var tdActions = document.createElement("td");
                aDelete.addEventListener("click", deleteEntry);

                var tr = document.createElement("tr");

                tr.appendChild(td1);
                tr.appendChild(td2);
                tr.appendChild(td3);
                tr.appendChild(tdActions);

                tdActions.appendChild(aDelete);

                tdActions.className = "text-center";

                schoolsTBody.appendChild(tr);            
            }  

            for (var i = 1; i < row.length; i++) 
            {
                for (var j = 1; j < row[i].cells.length - 1; j++ ) 
                {
                    row[i].cells[j].addEventListener('dblclick', editTD); 
                    row[i].cells[j].addEventListener('focusout', releaseTD); 
                }
            }
        }
     }
     
    var addSchoolButton = document.getElementById("addSchool");
    addSchoolButton.addEventListener("click", addSchool);
    function addSchool()
    {
        var eSchoolId = document.getElementById("schoolIdInput").value;
        var eSchoolName = document.getElementById("schoolNameInput").value;
        var eRelUniversityCodeInput = document.getElementById("relUniversityCodeInput").value;

        var newPackage = JSON.stringify({
                                        ID:eSchoolId,
                                        Name:eSchoolName,
                                        relUniversity:eRelUniversityCodeInput
                                        });

        var xhr = new XMLHttpRequest();
        xhr.open('POST','AddSchoolAction.php',true);
        xhr.setRequestHeader('Content-type','application/x-www-form-urlencoded');
        xhr.send("jsonAdd=" + newPackage);
        
        xhr.onload = function()
		{
			if(this.status === 200)
            {   
                document.getElementById("addSchoolForm").reset();            
                loadSchools();  
            }
        }

    }

    //Majos table functions

    var myMajorsTable = document.getElementById('majorsTable');

    function loadMajors() 
    {
        row = myMajorsTable.rows;
        editAction = 'EditMajorAction.php';
        deleteAction = 'DeleteMajorAction.php';

        const majorsTBody = document.querySelector("#majorsTable > tbody");
        var xhr = new XMLHttpRequest();
        xhr.open('POST','majorsTableAction.php',true);
        xhr.setRequestHeader('Content-type','application/x-www-form-urlencoded');
        xhr.send();
        
        xhr.onload = function()
        {   
            if(this.status == 200)
            {
                var arr = JSON.parse(this.responseText);
            }

            while(majorsTBody.rows.length > 0)
            {
                majorsTBody.removeChild(majorsTBody.firstChild);
            }

            for(var i = 0; i < Object.keys(arr).length; i++)
            {
                var majorId = arr[i].ID;
                var majorName = arr[i].Name;   
                var schoolId = arr[i].schoolId;   

                td1 = document.createElement("td");
                td2 = document.createElement("td");
                td3 = document.createElement("td");

                td1.textContent = majorId;
                td2.textContent = majorName;
                td3.textContent = schoolId;

                tdActions = document.createElement("td");
                aDelete = document.createElement('a');
                aDelete.id = majorId;

                btnDelete = document.createElement('button');
                btnDelete.className = "btn p-0 fas fa-trash fa-lg";
                btnDelete.style = "color: red";
                btnDelete.id = majorId;
                aDelete.appendChild(btnDelete);

                aDelete.className = "ml-2";

                var tdActions = document.createElement("td");
                aDelete.addEventListener("click", deleteEntry);

                var tr = document.createElement("tr");

                tr.appendChild(td1);
                tr.appendChild(td2);
                tr.appendChild(td3);
                tr.appendChild(tdActions);

                tdActions.appendChild(aDelete);

                tdActions.className = "text-center";

                majorsTBody.appendChild(tr);            
            }  

            for (var i = 1; i < row.length; i++) 
            {
                for (var j = 1; j < row[i].cells.length - 1; j++ ) 
                {
                    row[i].cells[j].addEventListener('dblclick', editTD); 
                    row[i].cells[j].addEventListener('focusout', releaseTD); 
                }
            }
        }
     }
     
    var addMajorButton = document.getElementById("addMajor");
    addMajorButton.addEventListener("click", addMajor);
    function addMajor()
    {
        var eMajorId = document.getElementById("majorIdInput").value;
        var eMajorName = document.getElementById("majorNameInput").value;
        var eRelSchoolIdInput = document.getElementById("relSchoolIdInput").value;

        var newPackage = JSON.stringify({
                                        ID:eMajorId,
                                        Name:eMajorName,
                                        relSchool:eRelSchoolIdInput
                                        });

        var xhr = new XMLHttpRequest();
        xhr.open('POST','AddMajorAction.php',true);
        xhr.setRequestHeader('Content-type','application/x-www-form-urlencoded');
        xhr.send("jsonAdd=" + newPackage);
        
        xhr.onload = function()
		{
			if(this.status === 200)
            {   
                document.getElementById("addMajorForm").reset();            
                loadMajors();  
            }
        }

    }

    //Schools table functions

    var mySchoolsTable = document.getElementById('schoolsTable');

    function loadSchools() 
    {
        row = mySchoolsTable.rows;
        editAction = 'EditSchoolAction.php';
        deleteAction = 'DeleteSchoolAction.php';

        const schoolsTBody = document.querySelector("#schoolsTable > tbody");
        var xhr = new XMLHttpRequest();
        xhr.open('POST','schoolsTableAction.php',true);
        xhr.setRequestHeader('Content-type','application/x-www-form-urlencoded');
        xhr.send();
        
        xhr.onload = function()
        {   
            if(this.status == 200)
            {
                var arr = JSON.parse(this.responseText);
            }

            while(schoolsTBody.rows.length > 0)
            {
                schoolsTBody.removeChild(schoolsTBody.firstChild);
            }

            for(var i = 0; i < Object.keys(arr).length; i++)
            {
                var schoolId = arr[i].ID;
                var schoolName = arr[i].Name;   
                var universityCode = arr[i].universityCode;   

                td1 = document.createElement("td");
                td2 = document.createElement("td");
                td3 = document.createElement("td");

                td1.textContent = schoolId;
                td2.textContent = schoolName;
                td3.textContent = universityCode;

                tdActions = document.createElement("td");
                aDelete = document.createElement('a');
                aDelete.id = schoolId;

                btnDelete = document.createElement('button');
                btnDelete.className = "btn p-0 fas fa-trash fa-lg";
                btnDelete.style = "color: red";
                btnDelete.id = schoolId;
                aDelete.appendChild(btnDelete);

                aDelete.className = "ml-2";

                var tdActions = document.createElement("td");
                aDelete.addEventListener("click", deleteEntry);

                var tr = document.createElement("tr");

                tr.appendChild(td1);
                tr.appendChild(td2);
                tr.appendChild(td3);
                tr.appendChild(tdActions);

                tdActions.appendChild(aDelete);

                tdActions.className = "text-center";

                schoolsTBody.appendChild(tr);            
            }  

            for (var i = 1; i < row.length; i++) 
            {
                for (var j = 1; j < row[i].cells.length - 1; j++ ) 
                {
                    row[i].cells[j].addEventListener('dblclick', editTD); 
                    row[i].cells[j].addEventListener('focusout', releaseTD); 
                }
            }
        }
     }
     
    var addSchoolButton = document.getElementById("addSchool");
    addSchoolButton.addEventListener("click", addSchool);
    function addSchool()
    {
        var eSchoolId = document.getElementById("schoolIdInput").value;
        var eSchoolName = document.getElementById("schoolNameInput").value;
        var eRelUniversityCodeInput = document.getElementById("relUniversityCodeInput").value;

        var newPackage = JSON.stringify({
                                        ID:eSchoolId,
                                        Name:eSchoolName,
                                        relUniversity:eRelUniversityCodeInput
                                        });

        var xhr = new XMLHttpRequest();
        xhr.open('POST','AddSchoolAction.php',true);
        xhr.setRequestHeader('Content-type','application/x-www-form-urlencoded');
        xhr.send("jsonAdd=" + newPackage);
        
        xhr.onload = function()
		{
			if(this.status === 200)
            {   
                document.getElementById("addSchoolForm").reset();            
                loadSchools();  
            }
        }

    }
    //Exams table functions

    var myExamsTable = document.getElementById('examsTable');

    function loadExams() 
    {
        row = myExamsTable.rows;
        editAction = 'EditExamAction.php';
        deleteAction = 'DeleteExamAction.php';

        const examsTBody = document.querySelector("#examsTable > tbody");
        var xhr = new XMLHttpRequest();
        xhr.open('POST','examsTableAction.php',true);
        xhr.setRequestHeader('Content-type','application/x-www-form-urlencoded');
        xhr.send();
        
        xhr.onload = function()
        {   
            if(this.status == 200)
            {
                var arr = JSON.parse(this.responseText);
            }

            while(examsTBody.rows.length > 0)
            {
                examsTBody.removeChild(examsTBody.firstChild);
            }

            for(var i = 0; i < Object.keys(arr).length; i++)
            {
                var examId = arr[i].id;
                var fileDir = arr[i].fileDir;
                var universityCode = arr[i].university;
                var schoolCode = arr[i].school;
                var majorCode = arr[i].major;
                var year = arr[i].year;
                var semester = arr[i].semester;
                var number = arr[i].number;   
                var note = arr[i].note;
                var uploadDate = arr[i].uploadDate;
   

                td1 = document.createElement("td");
                td2 = document.createElement("td");
                td3 = document.createElement("td");
                td4 = document.createElement("td");
                td5 = document.createElement("td");
                td6 = document.createElement("td");
                td7 = document.createElement("td");
                td8 = document.createElement("td");
                td9 = document.createElement("td");
                var td10 = document.createElement("td");

                var aExamNote = document.createElement('a');
                aExamNote.className = "btn fa fa-clipboard fa-lg p-0";
                aExamNote.style = "color: #51a9c4";
                aExamNote.id = note;
                aExamNote.addEventListener('click', function()
                {
                    $("#myModal").modal('toggle');
                    document.getElementById("modalContent").textContent = this.id;
                });

                td1.textContent = examId;
                td2.textContent = fileDir;
                td3.textContent = universityCode;
                td4.textContent = schoolCode;
                td5.textContent = majorCode;
                td6.textContent = year;
                td7.textContent = semester;
                td8.textContent = number;
                td9.appendChild(aExamNote);
                td10.textContent = uploadDate;




                tdActions = document.createElement("td");
                aDelete = document.createElement('a');
                aDelete.id = examId;

                btnDelete = document.createElement('button');
                btnDelete.className = "btn p-0 fas fa-trash fa-lg";
                btnDelete.style = "color: red";
                btnDelete.id = examId;
                aDelete.appendChild(btnDelete);

                aDelete.className = "mr-2";

                var tdDownload = document.createElement("td");
                var aDownload = document.createElement('a');
                aDownload.setAttribute('download',fileDir);
                aDownload.setAttribute('href', 'uploads/' + fileDir);
                btnDownload = document.createElement('button');
                btnDownload.className = "btn p-0 fas fa-download fa-lg";
                btnDownload.style = "color: #51a9c4";

                aDownload.appendChild(btnDownload);

                var tdActions = document.createElement("td");
                aDelete.addEventListener("click", deleteEntry);

                var tr = document.createElement("tr");
                tdActions.appendChild(aDelete);
                tdActions.appendChild(aDownload);

                tr.appendChild(td1);
                tr.appendChild(td2);
                tr.appendChild(td3);
                tr.appendChild(td4);
                tr.appendChild(td5);
                tr.appendChild(td6);
                tr.appendChild(td7);
                tr.appendChild(td8);
                tr.appendChild(td9);
                tr.appendChild(td10);
                tr.appendChild(tdActions);

                td9.className = "text-center";
                tdActions.className = "text-center";

                examsTBody.appendChild(tr);            
            }  

            for (var i = 1; i < row.length; i++) 
            {
                for (var j = 1; j < row[i].cells.length - 1; j++ ) 
                {
                    row[i].cells[j].addEventListener('dblclick', editTD); 
                    row[i].cells[j].addEventListener('focusout', releaseTD); 
                }
            }
        }
     }
     
    var addSchoolButton = document.getElementById("addSchool");
    addSchoolButton.addEventListener("click", addSchool);
    function addSchool()
    {
        var eSchoolId = document.getElementById("schoolIdInput").value;
        var eSchoolName = document.getElementById("schoolNameInput").value;
        var eRelUniversityCodeInput = document.getElementById("relUniversityCodeInput").value;

        var newPackage = JSON.stringify({
                                        ID:eSchoolId,
                                        Name:eSchoolName,
                                        relUniversity:eRelUniversityCodeInput
                                        });

        var xhr = new XMLHttpRequest();
        xhr.open('POST','AddSchoolAction.php',true);
        xhr.setRequestHeader('Content-type','application/x-www-form-urlencoded');
        xhr.send("jsonAdd=" + newPackage);
        
        xhr.onload = function()
		{
			if(this.status === 200)
            {   
                document.getElementById("addSchoolForm").reset();            
                loadSchools();  
            }
        }

    }


    //Global table functions

    function editTD()
    {
        this.setAttribute('contenteditable','true');
        this.focus();
        this.style = "background-color: #f6f6df ; border:2px solid #0099CC;";
    }

    function releaseTD()
    {
        this.setAttribute('contenteditable','false');
        this.style = "background-color: none";

        var updatedText = this.textContent;
        var updatedTextId = row[this.parentNode.rowIndex].cells[0].textContent;
        var updatedCol;

        updatedCol = row[0].cells[this.cellIndex].textContent;


        var updatedPackage = JSON.stringify({
                                             updatedText:updatedText,
                                             ID:updatedTextId, 
                                             colName:updatedCol
                                            });
                                            
        var xhr = new XMLHttpRequest();
        xhr.open('POST',editAction,true);
        xhr.setRequestHeader('Content-type','application/x-www-form-urlencoded');
        xhr.send("jsonUpdate=" + updatedPackage);
    }
    
    function deleteEntry()
    {
        if(!confirm('Are you sure you want to delete this item?')) return false;

        var toDelete = this.id;
        var deletePackage = JSON.stringify({ID:toDelete});

        var xhr = new XMLHttpRequest();
        xhr.open('POST',deleteAction,true);
        xhr.setRequestHeader('Content-type','application/x-www-form-urlencoded');
        xhr.send("jsonDelete=" + deletePackage);

        xhr.onload = function()
        {
            if(this.status === 200)
            {
                if(this.responseText === "User Deleted") loadAccounts();
                if(this.responseText === "Exam Deleted") loadExams();
                if(this.responseText === "University Deleted") loadUniversities();
                if(this.responseText === "School Deleted") loadSchools();
                if(this.responseText === "Major Deleted") loadMajors();
                if(this.responseText === "Exam Deleted") loadExams();
            }
        }
    }



</script>