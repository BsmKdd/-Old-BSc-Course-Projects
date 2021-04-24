document.addEventListener("DOMContentLoaded", showExams);
myExamsTable = document.getElementById("examsTable");

function showExams() 
{
    row = myExamsTable.rows;
    
    const examsTBody = document.querySelector("#examsTable > tbody");
    var xhr = new XMLHttpRequest();
    xhr.open('POST','SearchPageAction.php',true);
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
            var examDir = arr[i].ID;
            var examUniversity = arr[i].University;
            var examSchool = arr[i].School;  
            var examMajor = arr[i].Major; 
            var examYear = arr[i].Year;
            var examSemester = arr[i].Semester;
            var examTime = arr[i].Time;
            var examNote = arr[i].Note;
            var examUploadDate = arr[i].UploadDate;

            var td1 = document.createElement("td");
            var td2 = document.createElement("td");
            var td3 = document.createElement("td");
            var td4 = document.createElement("td");
            var td5 = document.createElement("td");
            var td6 = document.createElement("td");
            var td7 = document.createElement("td");
            var td8 = document.createElement("td");



            var aExamNote = document.createElement('a');
            aExamNote.className = "btn fa fa-clipboard fa-lg p-0";
            aExamNote.style = "color: #51a9c4";
            aExamNote.id = examNote;
            aExamNote.addEventListener('click', function()
            {
                $("#myModal").modal('toggle');
                document.getElementById("modalContent").textContent = this.id;
            });
            

            td7.className  = "text-center";

            td1.textContent = examUniversity;
            td2.textContent = examSchool;
            td3.textContent = examMajor;
            td4.textContent = examYear;
            td5.textContent = examSemester;
            td6.textContent = examTime;
            td7.appendChild(aExamNote);
            td8.textContent = examUploadDate;

            var tdDownload = document.createElement("td");
            var aDownload = document.createElement('a');
            aDownload.setAttribute('download',examDir);
            aDownload.setAttribute('href', 'uploads/' + examDir);


            btnDownload = document.createElement('button');
            btnDownload.className = "btn p-0 fas fa-download fa-lg";
            btnDownload.style = "color: #51a9c4";

            aDownload.appendChild(btnDownload);

            var tr = document.createElement("tr");

            tdDownload.appendChild(aDownload);

            tr.appendChild(td1);
            tr.appendChild(td2);
            tr.appendChild(td3);
            tr.appendChild(td4);
            tr.appendChild(td5);
            tr.appendChild(td6);
            tr.appendChild(td7);
            tr.appendChild(td8);
            tr.appendChild(tdDownload);
            
            tdDownload.className = "text-center w-02";

            examsTBody.appendChild(tr);            
        }  
        $(document).ready( function () {
            $('#examsTable').DataTable();
        } );
    }
}




