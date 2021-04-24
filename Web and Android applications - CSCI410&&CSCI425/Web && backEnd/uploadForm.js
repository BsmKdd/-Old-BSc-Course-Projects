    var universityList = document.getElementById('university');
    universityList.addEventListener('change',showSchool);

    var schoolList = document.getElementById('school');
    schoolList.addEventListener('change',showMajor);

    var majorList = document.getElementById('major');
    majorList.addEventListener('change',showCourse);

    function showSchool(e)
    {
        universityChosen = universityList.value;
        jsonObject = JSON.stringify(universityChosen);
        var xhr = new XMLHttpRequest();
        xhr.open('POST','indexSchoolsAction.php',true);
        xhr.setRequestHeader('Content-type','application/x-www-form-urlencoded');
        xhr.send("jsonUniversity=" + jsonObject);

        xhr.onload = function()
        {   
            if(this.status == 200)
            {
                var arr = JSON.parse(this.responseText);
            }

            schoolList.options.length = 0;
            for(var i = 0; i < Object.keys(arr).length; i++)
            {
                var schoolValue = arr[i].id;
                var schoolName = arr[i].name;  
                var school = document.createElement("option");
                school.textContent = schoolName;
                school.value = schoolValue;
                schoolList.appendChild(school);
            }  
            majorList.disabled = true;
            majorList.selectedIndex = 0;

            schoolList.disabled = false;
            schoolList.selectedIndex = 0;
        }
        
    }

    function showMajor(e)
    {
        schoolChosen = schoolList.value;
        jsonObject = JSON.stringify(schoolChosen);
        var xhr = new XMLHttpRequest();
        xhr.open('POST','indexMajorsAction.php',true);
        xhr.setRequestHeader('Content-type','application/x-www-form-urlencoded');
        xhr.send("jsonSchool=" + jsonObject);

        xhr.onload = function()
        {   
            if(this.status == 200)
            {
                var arra = JSON.parse(this.responseText);
            }

            majorList.options.length = 0;
            for(var i = 0; i < Object.keys(arra).length; i++)
            {
                var majorValue = arra[i].id;
                var majorName = arra[i].name;  
                var major = document.createElement("option");
                major.textContent = majorName;
                major.value = majorValue;
                majorList.appendChild(major);
            }  
            majorList.disabled = false; 
            majorList.selectedIndex = 0;   

            document.getElementById("customFile").disabled = false;
        }
        
    }

    function showCourse(e)
    {
        document.getElementById("customFile").disabled = false;
    }

    $(document).ready(function()
    {
        $('#uploadForm').on('submit', function(event)
        {
            event.preventDefault();
            var formData = new FormData($('form')[0]);
            $.ajax({ 
                    xhr: function()
                    {          
                        var xhr = new window.XMLHttpRequest();

                        $('#progressBar').attr('aria-valuenow', 0).css('width', 0 + '%').text(0 + '%');
                        $('#progressDiv').removeClass('d-none');

                        xhr.upload.addEventListener('progress', function(e)
                        {
                            if(e.lengthComputable)
                            {
                                console.log('Bytes loaded: ' + e.loaded);
                                console.log('Total Size:' + e.total);
                                console.log('Percentage Uploaded: ' + (e.loaded/e.total));

                                var percent = Math.round((e.loaded/e.total)*100)

                                $('#progressBar').attr('aria-valuenow', percent).css('width', percent + '%').text(percent + '%');
                            }
                        });

                        return xhr;
                    },

                    type: 'POST',
                    url: 'uploadExamAction.php',
                    data: formData,
                    processData: false,
                    contentType: false,
                    success: function(data)
                    {
                        if(data.trim() != '') alert(data);
                    },
                })
        });
    });

    function fillYears()
    {
        var start = new Date().getFullYear();
        var end = 1975;
        var options = "";

        for(var year = start ; year >= end; year--)
        {
        options += "<option>"+ year +"</option>";
        }

        document.getElementById("year").innerHTML += options;
   }


   function fileName() 
   {    
        $('#progressBar').attr('aria-valuenow', 0).css('width', 0 + '%').text(0 + '%');
        $('#progressDiv').addClass('d-none');
        
        var x = document.getElementById("customFile").value;
        x = x.replace(/^.*[\\\/]/, '');
        document.getElementById("fileLabel").innerHTML = x;
    }
