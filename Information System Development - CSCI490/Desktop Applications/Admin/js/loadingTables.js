function loadMembers() 
{
    isMember =  "member";
    currentModal = "modal-signup";
    row = membersTable.rows;

    editAction = 'https://gym-senior-2020.000webhostapp.com/Admin%20Actions/editMemberAction.php';
    deleteAction = 'https://gym-senior-2020.000webhostapp.com/Admin%20Actions/deleteAccountAction.php';
    editImageAction = "https://gym-senior-2020.000webhostapp.com/Admin%20Actions/editImageAction.php";

    const membersTBody = document.querySelector("#membersTable > tbody");

    var xhr = new XMLHttpRequest();
    xhr.open('POST','https://gym-senior-2020.000webhostapp.com/Admin%20Actions/returnMembersAction.php',true);
    xhr.setRequestHeader('Content-type','application/x-www-form-urlencoded');
    xhr.send("key="+key);
    
    xhr.onload = function()
    {   

        while(membersTBody.rows.length > 0)
        {
            membersTBody.removeChild(membersTBody.firstChild);
        }

        if(this.status == 200)
        {
            var arr = null;
            try {            
                arr = JSON.parse(this.responseText);
            } catch(e) { console.log(e.message); return;}
            // console.log(arr);
        }

        if(arr != null){
            for(var i = 0; i < Object.keys(arr).length; i++)
            {
                var id = arr[i].id;
                var accountID = arr[i].accountID;
                var email = arr[i].email;  
                var fName = arr[i].fName; 
                var lName = arr[i].lName;
                var password = arr[i].password;
                var phoneNumber = arr[i].phoneNumber;
                var address = arr[i].address;
                var img = arr[i].img;
                var status = arr[i].status;
                var expire = arr[i].expire;
                var token = arr[i].token;
                var registration = arr[i].registration;
    
                var td1 = document.createElement("td");
                td1.id = accountID;
                // td1.addEventListener('dblclick', editTD); 
                // td1.addEventListener('focusout', releaseTD); 
    
                var td2 = document.createElement("td");
                td2.addEventListener('dblclick', editTD); 
                td2.addEventListener('focusout', releaseEmail); 
    
                var td3 = document.createElement("td");
                td3.addEventListener('dblclick', editTD); 
                td3.addEventListener('focusout', releaseTD); 
    
                var td4 = document.createElement("td");
                td4.addEventListener('dblclick', editTD); 
                td4.addEventListener('focusout', releaseTD); 
    
                var td5 = document.createElement("td");
                td5.addEventListener('dblclick', editTD); 
                td5.addEventListener('focusout', releaseEmail); 
    
                var td6 = document.createElement("td");
                td6.addEventListener('dblclick', editTD); 
                td6.addEventListener('focusout', releaseTD); 
    
                var td7 = document.createElement("td");
                td7.addEventListener('dblclick', editTD); 
                td7.addEventListener('focusout', releaseTD); 
                td7.className = "text-center";
                viewIcon = document.createElement("i");
                viewIcon.className = "material-icons";
                viewIcon.innerHTML = "remove_red_eye";
                viewIcon.style = "color:#800080; cursor: pointer; font-size: 1rem";
                viewIcon.id = address;
                viewIcon.addEventListener('click', function()
                {
                    var instance = M.Modal.getInstance(document.getElementById('myModal'));
                    document.getElementById("myModalContent").textContent = this.id;
                    instance.open();
    
                })
    
    
    
                var td8 = document.createElement("td");
                // td8.addEventListener('dblclick', editTD); 
                // td8.addEventListener('focusout', releaseTD); 
    
                var td9 = document.createElement("td");
    
                var td10 = document.createElement("td");
                td10.addEventListener('dblclick', editTD); 
                td10.addEventListener('focusout', releaseTD); 
    
                var td11 = document.createElement("td");
                td11.addEventListener('dblclick', editTD); 
                td11.addEventListener('focusout', releaseTD); 
    
                var imgDisplay = document.createElement("img");
                var tdActions = document.createElement("td");
                // td1.addEventListener('dblclick', editTD); 
                // td1.addEventListener('focusout', releaseTD); 
    
    
                imgDisplay.src = "https://gym-senior-2020.000webhostapp.com/Member Images/" + img;
                imgDisplay.addEventListener('click', imageUpload);
                imgDisplay.id = accountID;
    
                td1.textContent = id;
                td2.textContent = email;
                td3.textContent = fName;
                td4.textContent = lName;
                td5.textContent = password;
                td6.textContent = phoneNumber;
                // td7.textContent = address;
                td7.appendChild(viewIcon);
                td8.appendChild(imgDisplay);
                td8.className = "text-center";



                let divSwitch = document.createElement("div");
                divSwitch.className = "switch";
                let divSwitchLabel = document.createElement("label");
                let divSwitchInput = document.createElement("input");
                divSwitchInput.type = "checkbox";
                if(status > 0)
                {
                    divSwitchInput.checked = true;
                } else divSwitchInput.checked = false;
                divSwitchInput.setAttribute("email", email);
                divSwitchInput.setAttribute("token", token);
                divSwitchInput.setAttribute("memberID", id);
                divSwitchInput.addEventListener("change", function()
                {
                    if(this.checked)
                    {
                        this.disabled = true;
                        const activateMembership = functions.httpsCallable('activateMembership');
                        activateMembership({email:this.getAttribute("email")}).then(result =>
                        {
                            console.log(result);

                            var updatedText = "1";
                            var updatedTextId = this.getAttribute("memberID");
                            var updatedCol = "status";
                        
                            var updatedPackage = JSON.stringify({
                                                                key:key,
                                                                updatedText:updatedText,
                                                                id:updatedTextId, 
                                                                colName:updatedCol
                                                                });
                                                                
                            var xhr = new XMLHttpRequest();
                            xhr.open('POST', editAction,true);
                            xhr.setRequestHeader('Content-type','application/x-www-form-urlencoded');
                            xhr.send("jsonUpdate=" + updatedPackage);
                            this.disabled = false;

                        })
                    } else {
                        this.disabled = true;
                        const deactivateMembership = functions.httpsCallable('deactivateMembership');
                        deactivateMembership({email:this.getAttribute("email")}).then(result =>
                        {
                            console.log(result);

                            var updatedText = "0";
                            var updatedTextId = this.getAttribute("memberID");
                            var updatedCol = "status";
                        
                            var updatedPackage = JSON.stringify({
                                                                key:key,
                                                                updatedText:updatedText,
                                                                id:updatedTextId, 
                                                                colName:updatedCol
                                                                });
                                                                
                            var xhr = new XMLHttpRequest();
                            xhr.open('POST', editAction,true);
                            xhr.setRequestHeader('Content-type','application/x-www-form-urlencoded');
                            xhr.send("jsonUpdate=" + updatedPackage);
                            this.disabled = false;
                        })
                    }
                });
                divSwitchSpan = document.createElement("span");
                divSwitchSpan.className = "lever";

                divSwitchLabel.appendChild(divSwitchInput);
                divSwitchLabel.appendChild(divSwitchSpan);
                divSwitch.appendChild(divSwitchLabel); 
                td9.appendChild(divSwitch);
                /*
                  <div class="switch">
                    <label>
                        <input type="checkbox">
                        <span class="lever"></span>
                    </label>
                </div>
                */


                // td10.textContent = expire;
                td11.textContent = registration;
    
                var icon = document.createElement("i");
                icon.className = "small material-icons mx-auto";
                icon.innerHTML = "delete";
                
                btnDelete = document.createElement('button');
                btnDelete.style = "color: red; border: 0px; background: none";
                btnDelete.id = accountID;
                btnDelete.appendChild(icon);
    
                aDelete = document.createElement('a');
                aDelete.id = accountID;
                aDelete.addEventListener("click", deleteAccount);
                aDelete.className = "ml-2";
                aDelete.appendChild(btnDelete);
    
    
                var tr = document.createElement("tr");
    
                tr.appendChild(td1);
                tr.appendChild(td2);
                tr.appendChild(td3);
                tr.appendChild(td4);
                tr.appendChild(td5);
                tr.appendChild(td6);
                tr.appendChild(td7);
                tr.appendChild(td8);
                tr.appendChild(td9);
                // tr.appendChild(td10);
                tr.appendChild(td11);
                tr.appendChild(tdActions);
    
                tdActions.appendChild(aDelete);
                tdActions.style = "text-align:center";
                membersTBody.appendChild(tr);            
            } 
        }


        for (var i = 1; i < row.length; i++) 
        {
            for (var j = 1; j < row[i].cells.length - 1; j++ ) 
            {
                // row[i].cells[j].addEventListener('dblclick', editTD); 
                // row[i].cells[j].addEventListener('focusout', releaseTD); 
            }
        }

        $(function() {
            $(membersTable).tablesorter({
                theme : "materialize",
                widthFixed: true,
                // widget code contained in the jquery.tablesorter.widgets.js file
                // use the zebra stripe widget if you plan on hiding any rows (filter widget)
                widgets : [ "filter", "zebra" ],  
                widgetOptions : {
                    // using the default zebra striping class name, so it actually isn't included in the theme variable above
                    // this is ONLY needed for materialize theming if you are using the filter widget, because rows are hidden
                    zebra : ["even", "odd"],
                    // reset filters button
                    filter_reset : ".reset",
                    // extra css class name (string or array) added to the filter element (input or select)
                    // select needs a "browser-default" class or it gets hidden
                    filter_cssFilter: ["", "", "browser-default"]
                }
            })
            .tablesorterPager({
                // target the pager markup - see the HTML block below
                container: $(".tablepagerMembers"),
                // target the pager page select dropdown - choose a page
                cssGoto  : ".pagenum",
                // remove rows from the table to speed up the sort of large tables.
                // setting this to false, only hides the non-visible rows; needed if you plan to add/remove rows with the pager enabled.
                removeRows: true,
                // output string - default is '{page}/{totalPages}';
                // possible variables: {page}, {totalPages}, {filteredPages}, {startRow}, {endRow}, {filteredRows} and {totalRows}
                output: '{startRow} - {endRow} / {filteredRows} ({totalRows})'
        
            });
        
        });
        $(membersTable).trigger("update");
    }
 }

function loadMoves() 
{
    isMember =  "move";
    currentModal = "modal-move";
    row = movesTable.rows;

    editAction = 'https://gym-senior-2020.000webhostapp.com/Admin%20Actions/editMoveAction.php';
    deleteAction = 'https://gym-senior-2020.000webhostapp.com/Admin%20Actions/deleteMoveAction.php';
    editImageAction = "https://gym-senior-2020.000webhostapp.com/Admin%20Actions/editMoveImageAction.php";
    editGifAction = "https://gym-senior-2020.000webhostapp.com/Admin%20Actions/editMoveGifAction.php";

    const movesTBody = document.querySelector("#movesTable > tbody");

    var xhr = new XMLHttpRequest();
    xhr.open('POST','https://gym-senior-2020.000webhostapp.com/Admin%20Actions/returnMovesAction.php',true);
    xhr.setRequestHeader('Content-type','application/x-www-form-urlencoded');
    xhr.send("key="+key);
    
    xhr.onload = function()
    {   

        while(movesTBody.rows.length > 0)
        {
            movesTBody.removeChild(movesTBody.firstChild);
        }

        if(this.status == 200)
        {
            var arr = null;
            try {            
                arr = JSON.parse(this.responseText);
            } catch(e) { console.log(e.message); return;}
            // console.log(arr);
        }

        if(arr != null){
            for(var i = 0; i < Object.keys(arr).length; i++)
            {
                var id = arr[i].id;
                var machineID = arr[i].machineID;  
                var Name = arr[i].Name; 
                var Description = arr[i].Description;
                var muscleGroup1 = arr[i].muscleGroup1;
                var moveImage1 = arr[i].moveImage1;
                var moveImage2 = arr[i].moveImage2;
                var moveGif = arr[i].moveGif;

                var td1 = document.createElement("td");
                // td1.addEventListener('dblclick', editTD); 
                // td1.addEventListener('focusout', releaseTD); 
                td1.className = "text-center";

                var td2 = document.createElement("td");
                td2.addEventListener('dblclick', editTD); 
                td2.addEventListener('focusout', releaseEmail); 
                td2.className = "text-center";

                var td3 = document.createElement("td");
                td3.addEventListener('dblclick', editTD); 
                td3.addEventListener('focusout', releaseTD);
                
                var td4 = document.createElement("td");
                td4.addEventListener('dblclick', editTD); 
                td4.addEventListener('focusout', releaseTD); 
                td4.className = "text-center";
                viewIcon = document.createElement("i");
                viewIcon.className = "material-icons";
                viewIcon.innerHTML = "remove_red_eye";
                viewIcon.style = "color:#800080; cursor: pointer; font-size: 1rem";
                viewIcon.id = Description;
                viewIcon.addEventListener('click', function()
                {
                    var instance = M.Modal.getInstance(document.getElementById('myModal'));
                    document.getElementById("myModalContent").textContent = this.id;
                    instance.open();
    
                })
    
                var td5 = document.createElement("td");
                td5.addEventListener('dblclick', editTD); 
                td5.addEventListener('focusout', releaseTD); 
                td5.className = "text-center";

                var td6 = document.createElement("td");
                // td6.addEventListener('dblclick', editTD); 
                // td6.addEventListener('focusout', releaseEmail); 
                td6.className = "text-center";

                var td7 = document.createElement("td");
                // td7.addEventListener('dblclick', editTD); 
                // td7.addEventListener('focusout', releaseTD); 
                td7.className = "text-center";

                var td8 = document.createElement("td");
                // td8.addEventListener('dblclick', editTD); 
                // td8.addEventListener('focusout', releaseTD); 
                td8.className = "text-center";

    
                var imgDisplay1 = document.createElement("img");
                var imgDisplay2 = document.createElement("img");
                var gifDisplay = document.createElement("img");

                var tdActions = document.createElement("td");
                // td1.addEventListener('dblclick', editTD); 
                // td1.addEventListener('focusout', releaseTD); 
    
    
                imgDisplay1.src = "https://gym-senior-2020.000webhostapp.com/Moves Images/" + moveImage1;
                imgDisplay2.src = "https://gym-senior-2020.000webhostapp.com/Moves Images/" + moveImage2;
                gifDisplay.src = "https://gym-senior-2020.000webhostapp.com/Moves Gifs/" + moveGif;



                imgDisplay1.id = id;
                imgDisplay1.addEventListener('click', function(){
                   editImageAction = "https://gym-senior-2020.000webhostapp.com/Admin%20Actions/editMoveImage1Action.php";
                });
                imgDisplay1.addEventListener('click', imageUpload);

                imgDisplay2.id = id;
                imgDisplay2.addEventListener('click', function(){
                   editImageAction = "https://gym-senior-2020.000webhostapp.com/Admin%20Actions/editMoveImage2Action.php";
                });
                imgDisplay2.addEventListener('click', imageUpload);

                gifDisplay.id = id;
                gifDisplay.addEventListener('click', function(){
                    editImageAction = editGifAction;
                });
                gifDisplay.addEventListener('click', imageUpload);
    
                td1.textContent = id;
                td2.textContent = machineID;
                td3.textContent = Name;
                td4.appendChild(viewIcon);
                td5.textContent = muscleGroup1;
                td6.appendChild(imgDisplay1);
                td7.appendChild(imgDisplay2);
                td8.appendChild(gifDisplay);
    
                var icon = document.createElement("i");
                icon.className = "small material-icons mx-auto";
                icon.innerHTML = "delete";
                
                btnDelete = document.createElement('button');
                btnDelete.style = "color: red; border: 0px; background: none";
                btnDelete.id = id;
                btnDelete.appendChild(icon);
    
                aDelete = document.createElement('a');
                aDelete.id = id;
                aDelete.addEventListener("click", deleteAccount);
                aDelete.className = "ml-2";
                aDelete.appendChild(btnDelete);
    
    
                var tr = document.createElement("tr");
    
                tr.appendChild(td1);
                tr.appendChild(td2);
                tr.appendChild(td3);
                tr.appendChild(td4);
                tr.appendChild(td5);
                tr.appendChild(td6);
                tr.appendChild(td7);
                tr.appendChild(td8);
                tr.appendChild(tdActions);
    
                tdActions.appendChild(aDelete);
                tdActions.style = "text-align:center";
                movesTBody.appendChild(tr);            
            } 
        }


        for (var i = 1; i < row.length; i++) 
        {
            for (var j = 1; j < row[i].cells.length - 1; j++ ) 
            {
                // row[i].cells[j].addEventListener('dblclick', editTD); 
                // row[i].cells[j].addEventListener('focusout', releaseTD); 
            }
        }

        $(function() {
            $(movesTable).tablesorter({
                theme : "materialize",
                widthFixed: true,
                // widget code contained in the jquery.tablesorter.widgets.js file
                // use the zebra stripe widget if you plan on hiding any rows (filter widget)
                widgets : [ "filter", "zebra" ],  
                widgetOptions : {
                    // using the default zebra striping class name, so it actually isn't included in the theme variable above
                    // this is ONLY needed for materialize theming if you are using the filter widget, because rows are hidden
                    zebra : ["even", "odd"],
                    // reset filters button
                    filter_reset : ".reset",
                    // extra css class name (string or array) added to the filter element (input or select)
                    // select needs a "browser-default" class or it gets hidden
                    filter_cssFilter: ["", "", "browser-default"]
                }
            })
            .tablesorterPager({
                // target the pager markup - see the HTML block below
                container: $(".tablepagerMoves"),
                // target the pager page select dropdown - choose a page
                cssGoto  : ".pagenum",
                // remove rows from the table to speed up the sort of large tables.
                // setting this to false, only hides the non-visible rows; needed if you plan to add/remove rows with the pager enabled.
                removeRows: true,
                // output string - default is '{page}/{totalPages}';
                // possible variables: {page}, {totalPages}, {filteredPages}, {startRow}, {endRow}, {filteredRows} and {totalRows}
                output: '{startRow} - {endRow} / {filteredRows} ({totalRows})'
        
            });
        
        });
        $(movesTable).trigger("update");
    }
 }

function loadCoaches() 
{
    isMember = "coach";
    currentModal = "modal-coach";
    row = coachesTable.rows;

    editAction = 'https://gym-senior-2020.000webhostapp.com/Admin%20Actions/editCoachAction.php';
    deleteAction = 'https://gym-senior-2020.000webhostapp.com/Admin%20Actions/deleteAccountAction.php';
    editImageAction = "https://gym-senior-2020.000webhostapp.com/Admin%20Actions/editImageAction.php";

    const coachesTBody = document.querySelector("#coachesTable > tbody");

    var xhr = new XMLHttpRequest();
    xhr.open('POST','https://gym-senior-2020.000webhostapp.com/Admin%20Actions/returnCoachesAction.php',true);
    xhr.setRequestHeader('Content-type','application/x-www-form-urlencoded');
    xhr.send("key="+key);
    
    xhr.onload = function()
    {   

        while(coachesTBody.rows.length > 0)
        {
            coachesTBody.removeChild(coachesTBody.firstChild);
        }

        if(this.status == 200)
        {
            var arr = null;
            try {            
                arr = JSON.parse(this.responseText);
            } catch(e) { console.log(e.message); return;}
            // console.log(arr);
        }

        if(arr != null){
            for(var i = 0; i < Object.keys(arr).length; i++)
            {
                var id = arr[i].id;
                var accountID = arr[i].accountID;
                var email = arr[i].email;  
                var fName = arr[i].fName; 
                var lName = arr[i].lName;
                var password = arr[i].password;
                var phoneNumber = arr[i].phoneNumber;
                var address = arr[i].address;
                var img = arr[i].img;
                var expertise = arr[i].expertise;
                var description = arr[i].description;
                var salary = arr[i].salary;
                var hired = arr[i].hired;
    
                var td1 = document.createElement("td");
                td1.id = accountID;
                // td1.addEventListener('dblclick', editTD); 
                // td1.addEventListener('focusout', releaseTD); 
    
                var td2 = document.createElement("td");
                td2.addEventListener('dblclick', editTD); 
                td2.addEventListener('focusout', releaseEmail); 
    
                var td3 = document.createElement("td");
                td3.addEventListener('dblclick', editTD); 
                td3.addEventListener('focusout', releaseTD); 
    
                var td4 = document.createElement("td");
                td4.addEventListener('dblclick', editTD); 
                td4.addEventListener('focusout', releaseTD); 
    
                var td5 = document.createElement("td");
                td5.addEventListener('dblclick', editTD); 
                td5.addEventListener('focusout', releaseEmail); 
    
                var td6 = document.createElement("td");
                td6.addEventListener('dblclick', editTD); 
                td6.addEventListener('focusout', releaseTD); 
    
                var td7 = document.createElement("td");
                td7.addEventListener('dblclick', editTD); 
                td7.addEventListener('focusout', releaseTD); 
                td7.className = "text-center";
                viewIcon = document.createElement("i");
                viewIcon.className = "material-icons";
                viewIcon.innerHTML = "remove_red_eye";
                viewIcon.style = "color:#800080; cursor: pointer; font-size: 1rem";
                viewIcon.id = address;
                viewIcon.addEventListener('click', function()
                {
                    var instance = M.Modal.getInstance(document.getElementById('myModal'));
                    document.getElementById("myModalContent").textContent = this.id;
                    instance.open();
    
                })
    
    
    
                var td8 = document.createElement("td");
                // td8.addEventListener('dblclick', editTD); 
                // td8.addEventListener('focusout', releaseTD); 
    
                var td9 = document.createElement("td");
                td9.addEventListener('dblclick', editTD); 
                td9.addEventListener('focusout', releaseTD); 
    
                var td10 = document.createElement("td");
                td10.addEventListener('dblclick', editTD); 
                td10.addEventListener('focusout', releaseTD); 
                td10.className = "text-center";
                viewIconDescription = document.createElement("i");
                viewIconDescription.className = "material-icons";
                viewIconDescription.innerHTML = "description";
                viewIconDescription.style = "color:#800080; cursor: pointer; font-size: 1rem";
                viewIconDescription.id = description;
                viewIconDescription.addEventListener('click', function()
                {
                    var instance = M.Modal.getInstance(document.getElementById('myModal'));
                    document.getElementById("myModalContent").textContent = this.id;
                    instance.open();
    
                })
    
                var td11 = document.createElement("td");
                td11.addEventListener('dblclick', editTD); 
                td11.addEventListener('focusout', releaseTD); 
    
                var td12 = document.createElement("td");
                td12.addEventListener('dblclick', editTD); 
                td12.addEventListener('focusout', releaseTD); 
    
                var imgDisplay = document.createElement("img");
                var tdActions = document.createElement("td");
    
    
                imgDisplay.src = "https://gym-senior-2020.000webhostapp.com/Coach Images/" + img;
                imgDisplay.addEventListener('click', imageUpload);
                imgDisplay.id = accountID;
    
                td1.textContent = id;
                td2.textContent = email;
                td3.textContent = fName;
                td4.textContent = lName;
                td5.textContent = password;
                td6.textContent = phoneNumber;
                // td7.textContent = address;
                td7.appendChild(viewIcon);
                td8.appendChild(imgDisplay);
                td8.style = "text-align:center";
                td9.textContent = expertise;
                td10.appendChild(viewIconDescription);
                td11.textContent = salary;
                td12.textContent = hired;
    
    
                var icon = document.createElement("i");
                icon.className = "small material-icons mx-auto";
                icon.innerHTML = "delete";
                
                btnDelete = document.createElement('button');
                btnDelete.style = "color: red; border: 0px; background: none";
                btnDelete.id = accountID;
                btnDelete.appendChild(icon);
    
                aDelete = document.createElement('a');
                aDelete.id = accountID;
                aDelete.addEventListener("click", deleteAccount);
                aDelete.className = "ml-2";
                aDelete.appendChild(btnDelete);
    
    
                var tr = document.createElement("tr");
    
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
                tr.appendChild(td11);
                tr.appendChild(td12);
                tr.appendChild(tdActions);
    
                tdActions.appendChild(aDelete);
                tdActions.style = "text-align:center";
                coachesTBody.appendChild(tr);            
            }
        }


        // for (var i = 1; i < row.length; i++) 
        // {
        //     for (var j = 1; j < row[i].cells.length - 1; j++ ) 
        //     {
        //         // row[i].cells[j].addEventListener('dblclick', editTD); 
        //         // row[i].cells[j].addEventListener('focusout', releaseTD); 
        //     }
        // }

        $(function() {
            $(coachesTable).tablesorter({
                theme : "materialize",
                widthFixed: true,
                // widget code contained in the jquery.tablesorter.widgets.js file
                // use the zebra stripe widget if you plan on hiding any rows (filter widget)
                widgets : [ "filter", "zebra" ],  
                widgetOptions : {
                    // using the default zebra striping class name, so it actually isn't included in the theme variable above
                    // this is ONLY needed for materialize theming if you are using the filter widget, because rows are hidden
                    zebra : ["even", "odd"],
                    // reset filters button
                    filter_reset : ".reset",
                    // extra css class name (string or array) added to the filter element (input or select)
                    // select needs a "browser-default" class or it gets hidden
                    filter_cssFilter: ["", "", "browser-default"]
                }
            })
            .tablesorterPager({
                // target the pager markup - see the HTML block below
                container: $(".tablepagerCoach"),
                // target the pager page select dropdown - choose a page
                cssGoto  : ".pagenum",
                // remove rows from the table to speed up the sort of large tables.
                // setting this to false, only hides the non-visible rows; needed if you plan to add/remove rows with the pager enabled.
                // removeRows: true,
                // output string - default is '{page}/{totalPages}';
                // possible variables: {page}, {totalPages}, {filteredPages}, {startRow}, {endRow}, {filteredRows} and {totalRows}
                output: '{startRow} - {endRow} / {filteredRows} ({totalRows})'
        
            });
        
        });
    }
 }

function loadBartenders() 
{
    isMember = "bartender";
    currentModal = "modal-bartender";
    row = bartendersTable.rows;

    editAction = 'https://gym-senior-2020.000webhostapp.com/Admin%20Actions/editBartenderAction.php';
    deleteAction = 'https://gym-senior-2020.000webhostapp.com/Admin%20Actions/deleteAccountAction.php';
    editImageAction = "https://gym-senior-2020.000webhostapp.com/Admin%20Actions/editImageAction.php";

    const bartendersTBody = document.querySelector("#bartendersTable > tbody");

    var xhr = new XMLHttpRequest();
    xhr.open('POST','https://gym-senior-2020.000webhostapp.com/Admin%20Actions/returnBartendersAction.php',true);
    xhr.setRequestHeader('Content-type','application/x-www-form-urlencoded');
    xhr.send("key="+key);
    
    xhr.onload = function()
    {   

        while(bartendersTBody.rows.length > 0)
        {
            bartendersTBody.removeChild(bartendersTBody.firstChild);
        }

        if(this.status == 200)
        {
            var arr = null;
            try {            
                arr = JSON.parse(this.responseText);
            } catch(e) { console.log(e.message); return;}
            // console.log(arr);
        }

        if(arr != null){
            for(var i = 0; i < Object.keys(arr).length; i++)
            {
                var id = arr[i].id;
                var accountID = arr[i].accountID;
                var email = arr[i].email;  
                var fName = arr[i].fName; 
                var lName = arr[i].lName;
                var password = arr[i].password;
                var phoneNumber = arr[i].phoneNumber;
                var address = arr[i].address;
                var img = arr[i].img;
                var salary = arr[i].salary;
    
                var td1 = document.createElement("td");
                td1.id = accountID;
                // td1.addEventListener('dblclick', editTD); 
                // td1.addEventListener('focusout', releaseTD); 
    
                var td2 = document.createElement("td");
                td2.addEventListener('dblclick', editTD); 
                td2.addEventListener('focusout', releaseEmail); 
    
                var td3 = document.createElement("td");
                td3.addEventListener('dblclick', editTD); 
                td3.addEventListener('focusout', releaseTD); 
    
                var td4 = document.createElement("td");
                td4.addEventListener('dblclick', editTD); 
                td4.addEventListener('focusout', releaseTD); 
    
                var td5 = document.createElement("td");
                td5.addEventListener('dblclick', editTD); 
                td5.addEventListener('focusout', releaseEmail); 
    
                var td6 = document.createElement("td");
                td6.addEventListener('dblclick', editTD); 
                td6.addEventListener('focusout', releaseTD); 
    
                var td7 = document.createElement("td");
                td7.addEventListener('dblclick', editTD); 
                td7.addEventListener('focusout', releaseTD); 
                td7.className = "text-center";
                viewIcon = document.createElement("i");
                viewIcon.className = "material-icons";
                viewIcon.innerHTML = "remove_red_eye";
                viewIcon.style = "color:#800080; cursor: pointer; font-size: 1rem";
                viewIcon.id = address;
                viewIcon.addEventListener('click', function()
                {
                    var instance = M.Modal.getInstance(document.getElementById('myModal'));
                    document.getElementById("myModalContent").textContent = this.id;
                    instance.open();
    
                })
    
                var td8 = document.createElement("td");
                // td1.addEventListener('dblclick', editTD); 
                // td1.addEventListener('focusout', releaseTD); 
    
                var td11 = document.createElement("td");
                td11.addEventListener('dblclick', editTD); 
                td11.addEventListener('focusout', releaseTD); 
    
    
                var imgDisplay = document.createElement("img");
                var tdActions = document.createElement("td");
                // td1.addEventListener('dblclick', editTD); 
                // td1.addEventListener('focusout', releaseTD); 
    
                imgDisplay.src = "https://gym-senior-2020.000webhostapp.com/Bartender Images/" + img;
                imgDisplay.addEventListener('click', imageUpload);
                imgDisplay.id = accountID;
    
                td1.textContent = id;
                td2.textContent = email;
                td3.textContent = fName;
                td4.textContent = lName;
                td5.textContent = password;
                td6.textContent = phoneNumber;
                // td7.textContent = address;
                td7.appendChild(viewIcon);
                td8.appendChild(imgDisplay);
                td8.style = "text-align:center";
                td11.textContent = salary;
    
                var icon = document.createElement("i");
                icon.className = "small material-icons mx-auto";
                icon.innerHTML = "delete";
                
                btnDelete = document.createElement('button');
                btnDelete.style = "color: red; border: 0px; background: none";
                btnDelete.id = accountID;
                btnDelete.appendChild(icon);
    
                aDelete = document.createElement('a');
                aDelete.id = accountID;
                aDelete.addEventListener("click", deleteAccount);
                aDelete.className = "ml-2";
                aDelete.appendChild(btnDelete);
    
    
                var tr = document.createElement("tr");
    
                tr.appendChild(td1);
                tr.appendChild(td2);
                tr.appendChild(td3);
                tr.appendChild(td4);
                tr.appendChild(td5);
                tr.appendChild(td6);
                tr.appendChild(td7);
                tr.appendChild(td8);
                tr.appendChild(td11);
                tr.appendChild(tdActions);
    
                tdActions.appendChild(aDelete);
                tdActions.style = "text-align:center";
                bartendersTBody.appendChild(tr);            
            } 
        }

        $(function() {
            $(bartendersTable).tablesorter({
                theme : "materialize",
                widthFixed: true,
                // widget code contained in the jquery.tablesorter.widgets.js file
                // use the zebra stripe widget if you plan on hiding any rows (filter widget)
                widgets : [ "filter", "zebra" ],  
                widgetOptions : {
                    // using the default zebra striping class name, so it actually isn't included in the theme variable above
                    // this is ONLY needed for materialize theming if you are using the filter widget, because rows are hidden
                    zebra : ["even", "odd"],
                    // reset filters button
                    filter_reset : ".reset",
                    // extra css class name (string or array) added to the filter element (input or select)
                    // select needs a "browser-default" class or it gets hidden
                    filter_cssFilter: ["", "", "browser-default"]
                }
            })
            .tablesorterPager({
                // target the pager markup - see the HTML block below
                container: $(".tablepagerBartender"),
                // target the pager page select dropdown - choose a page
                cssGoto  : ".pagenum",
                // remove rows from the table to speed up the sort of large tables.
                // setting this to false, only hides the non-visible rows; needed if you plan to add/remove rows with the pager enabled.
                removeRows: true,
                // output string - default is '{page}/{totalPages}';
                // possible variables: {page}, {totalPages}, {filteredPages}, {startRow}, {endRow}, {filteredRows} and {totalRows}
                output: '{startRow} - {endRow} / {filteredRows} ({totalRows})'
        
            });
        
        });
        $(bartendersTable).trigger("update");
    }
 }

function loadMachines() 
{
    isMember = "machine";
    currentModal = "modal-machine";
    row = machinesTable.rows;

    editAction = 'https://gym-senior-2020.000webhostapp.com/Admin%20Actions/editMachineAction.php';
    deleteAction = 'https://gym-senior-2020.000webhostapp.com/Admin%20Actions/deleteMachineAction.php';
    editImageAction = "https://gym-senior-2020.000webhostapp.com/Admin%20Actions/editMachineImageAction.php";
    
    const machinesTBody = document.querySelector("#machinesTable > tbody");

    var xhr = new XMLHttpRequest();
    xhr.open('POST','https://gym-senior-2020.000webhostapp.com/Admin%20Actions/returnMachinesAction.php',true);
    xhr.setRequestHeader('Content-type','application/x-www-form-urlencoded');
    xhr.send("key="+key);
    
    xhr.onload = function()
    {   
        while(machinesTBody.rows.length > 0)
        {
            machinesTBody.removeChild(machinesTBody.firstChild);
        }

        if(this.status == 200)
        {
            var arr = null;
            try {            
                arr = JSON.parse(this.responseText);
            } catch(e) { console.log(e.message); return;}
            // console.log(arr);
        }

        if(arr != null){
            for(var i = 0; i < Object.keys(arr).length; i++)
            {
                var id = arr[i].id;
                var Name = arr[i].Name; 
                var Code = arr[i].Code;
                var img = arr[i].img;
                var floor = arr[i].floor;
                var section = arr[i].section;
    
                var td1 = document.createElement("td");
                // td1.addEventListener('dblclick', editTD); 
                // td1.addEventListener('focusout', releaseTD); 
                td1.className = "text-center";
    
                var td2 = document.createElement("td");
                td2.addEventListener('dblclick', editTD); 
                td2.addEventListener('focusout', releaseTD); 
    
                var td3 = document.createElement("td");
                td3.addEventListener('dblclick', editTD); 
                td3.addEventListener('focusout', releaseTD); 
    
                var td4 = document.createElement("td");
                // td4.addEventListener('dblclick', editTD); 
                // td4.addEventListener('focusout', releaseTD); 
                td4.className = "text-center";
    
                var td5 = document.createElement("td");
                td5.addEventListener('dblclick', editTD); 
                td5.addEventListener('focusout', releaseTD); 
                td5.className = "text-center";
    
                var td6 = document.createElement("td");
                td6.addEventListener('dblclick', editTD); 
                td6.addEventListener('focusout', releaseTD);
                td6.className = "text-center"; 
    
                var td7 = document.createElement("td");
                td7.addEventListener('dblclick', editTD); 
                td7.addEventListener('focusout', releaseTD); 
                td7.className = "text-center";
                viewIcon = document.createElement("i");
                viewIcon.className = "material-icons";
                viewIcon.innerHTML = "remove_red_eye";
                viewIcon.style = "color:#800080; cursor: pointer; font-size: 1rem";
                viewIcon.id = address;
                viewIcon.addEventListener('click', function()
                {
                    var instance = M.Modal.getInstance(document.getElementById('myModal'));
                    document.getElementById("myModalContent").textContent = this.id;
                    instance.open();
    
                })
    
                var imgDisplay = document.createElement("img");
                var tdActions = document.createElement("td");
                // td1.addEventListener('dblclick', editTD); 
                // td1.addEventListener('focusout', releaseTD); 
    
                imgDisplay.src = "https://gym-senior-2020.000webhostapp.com/Machines Images/" + img;
                imgDisplay.addEventListener('click', imageUpload);
                imgDisplay.id = id;
    
                td1.textContent = id;
                td2.textContent = Name;
                td3.textContent = Code;
                td4.appendChild(imgDisplay);
                td5.textContent = floor;
                td6.textContent = section;
    
                var icon = document.createElement("i");
                icon.className = "small material-icons mx-auto";
                icon.innerHTML = "delete";
                
                btnDelete = document.createElement('button');
                btnDelete.style = "color: red; border: 0px; background: none";
                btnDelete.id = id;
                btnDelete.appendChild(icon);
    
                aDelete = document.createElement('a');
                aDelete.id = id;
                aDelete.addEventListener("click", deleteAccount);
                aDelete.className = "ml-2";
                aDelete.appendChild(btnDelete);
    
    
                var tr = document.createElement("tr");
    
                tr.appendChild(td1);
                tr.appendChild(td2);
                tr.appendChild(td3);
                tr.appendChild(td4);
                tr.appendChild(td5);
                tr.appendChild(td6);
                tr.appendChild(tdActions);
    
                tdActions.appendChild(aDelete);
                tdActions.style = "text-align:center";
                machinesTBody.appendChild(tr);  
            }
        }
          
        


        // for (var i = 1; i < row.length; i++) 
        // {
        //     for (var j = 1; j < row[i].cells.length - 1; j++ ) 
        //     {
        //         // row[i].cells[j].addEventListener('dblclick', editTD); 
        //         // row[i].cells[j].addEventListener('focusout', releaseTD); 
        //     }
        // }

        $(function() {
            $(machinesTable).tablesorter({
                theme : "materialize",
                widthFixed: true,
                // widget code contained in the jquery.tablesorter.widgets.js file
                // use the zebra stripe widget if you plan on hiding any rows (filter widget)
                widgets : [ "filter", "zebra" ],  
                widgetOptions : {
                    // using the default zebra striping class name, so it actually isn't included in the theme variable above
                    // this is ONLY needed for materialize theming if you are using the filter widget, because rows are hidden
                    zebra : ["even", "odd"],
                    // reset filters button
                    filter_reset : ".reset",
                    // extra css class name (string or array) added to the filter element (input or select)
                    // select needs a "browser-default" class or it gets hidden
                    filter_cssFilter: ["", "", "browser-default"]
                }
            })
            .tablesorterPager({
                // target the pager markup - see the HTML block below
                container: $(".tablepagerMachines"),
                // target the pager page select dropdown - choose a page
                cssGoto  : ".pagenum",
                // remove rows from the table to speed up the sort of large tables.
                // setting this to false, only hides the non-visible rows; needed if you plan to add/remove rows with the pager enabled.
                removeRows: true,
                // output string - default is '{page}/{totalPages}';
                // possible variables: {page}, {totalPages}, {filteredPages}, {startRow}, {endRow}, {filteredRows} and {totalRows}
                output: '{startRow} - {endRow} / {filteredRows} ({totalRows})'
        
            });
        
        });
        $(machinesTable).trigger("update");
    }
}

function loadMenuItems() 
{
    isMember = "menuItem";
    currentModal = "modal-menu";
    row = menuItemsTable.rows;

    editAction = 'https://gym-senior-2020.000webhostapp.com/Admin%20Actions/editMenuItemAction.php';
    deleteAction = 'https://gym-senior-2020.000webhostapp.com/Admin%20Actions/deleteMenuItemAction.php';
    editImageAction = "https://gym-senior-2020.000webhostapp.com/Admin%20Actions/editMenuItemImageAction.php";
    
    const menuItemsTBody = document.querySelector("#menuItemsTable > tbody");

    var xhr = new XMLHttpRequest();
    xhr.open('POST','https://gym-senior-2020.000webhostapp.com/Admin%20Actions/returnMenuItemsAction.php',true);
    xhr.setRequestHeader('Content-type','application/x-www-form-urlencoded');
    xhr.send("key="+key);
    
    xhr.onload = function()
    {   
                
        while(menuItemsTBody.rows.length > 0)
        {
            menuItemsTBody.removeChild(menuItemsTBody.firstChild);
        }

        if(this.status == 200)
        {
            var arr = null;
            try {            
                arr = JSON.parse(this.responseText);
            } catch(e) { console.log(e.message); return;}
            // console.log(arr);
        }

        if(arr != null){
            
            for(var i = 0; i < Object.keys(arr).length; i++)
            {
                var id = arr[i].id;
                var Name = arr[i].Name; 
                var Img = arr[i].Img;
                var Description = arr[i].Description;
                var Calories = arr[i].Calories;
                var Fats = arr[i].Fats;
                var Protein = arr[i].Protein;
                var Carbohydrates = arr[i].Carbohydrates;
                var Sugar = arr[i].Sugar;
                var Time = arr[i].Time;
                var Price = arr[i].Price;
                var Available = arr[i].Available;

                var td1 = document.createElement("td");
                // td1.addEventListener('dblclick', editTD); 
                // td1.addEventListener('focusout', releaseTD); 
                td1.className = "text-center";

                var td2 = document.createElement("td");
                td2.addEventListener('dblclick', editTD); 
                td2.addEventListener('focusout', releaseTD); 

                var td3 = document.createElement("td");
                // td3.addEventListener('dblclick', editTD); 
                // td3.addEventListener('focusout', releaseTD); 
                td3.className = "text-center";

                var td4 = document.createElement("td");
                td4.addEventListener('dblclick', editTD); 
                td4.addEventListener('focusout', releaseTD); 
                td4.className = "text-center";
                viewIcon = document.createElement("i");
                viewIcon.className = "material-icons";
                viewIcon.innerHTML = "remove_red_eye";
                viewIcon.style = "color:#800080; cursor: pointer; font-size: 1rem";
                viewIcon.id = Description;
                viewIcon.addEventListener('click', function()
                {
                    var instance = M.Modal.getInstance(document.getElementById('myModal'));
                    document.getElementById("myModalContent").textContent = this.id;
                    instance.open();

                })

                var td5 = document.createElement("td");
                td5.addEventListener('dblclick', editTD); 
                td5.addEventListener('focusout', releaseTD); 
                td5.className = "text-center";

                var td6 = document.createElement("td");
                td6.addEventListener('dblclick', editTD); 
                td6.addEventListener('focusout', releaseTD);
                td6.className = "text-center"; 

                var td7 = document.createElement("td");
                td7.addEventListener('dblclick', editTD); 
                td7.addEventListener('focusout', releaseTD); 
                td7.className = "text-center";


                var imgDisplay = document.createElement("img");
                var tdActions = document.createElement("td");
                // td1.addEventListener('dblclick', editTD); 
                // td1.addEventListener('focusout', releaseTD); 

                imgDisplay.src = "https://gym-senior-2020.000webhostapp.com/menuItem Images/" + Img;
                imgDisplay.addEventListener('click', imageUpload);
                imgDisplay.id = id;

                var td8 = document.createElement("td");
                td8.addEventListener('dblclick', editTD); 
                td8.addEventListener('focusout', releaseTD);
                td8.className = "text-center"; 

                var td9 = document.createElement("td");
                td9.addEventListener('dblclick', editTD); 
                td9.addEventListener('focusout', releaseTD);
                td9.className = "text-center"; 

                var td10 = document.createElement("td");
                td10.addEventListener('dblclick', editTD); 
                td10.addEventListener('focusout', releaseTD);
                td10.className = "text-center"; 

                var td11 = document.createElement("td");
                td11.addEventListener('dblclick', editTD); 
                td11.addEventListener('focusout', releaseTD);
                td11.className = "text-center"; 

                var td12 = document.createElement("td");
                td12.addEventListener('dblclick', editTD); 
                td12.addEventListener('focusout', releaseTD);
                td12.className = "text-center"; 

                td1.textContent = id;
                td2.textContent = Name;
                td3.appendChild(imgDisplay);
                td4.appendChild(viewIcon);
                td5.textContent = Calories;
                td6.textContent = Fats;
                td7.textContent = Protein;
                td8.textContent = Carbohydrates;
                td9.textContent = Sugar;
                td9.textContent = Time;
                td10.textContent = Sugar;
                td11.textContent = Price;
                td12.textContent = Available;


                // td7.textContent = address;

                var icon = document.createElement("i");
                icon.className = "small material-icons mx-auto";
                icon.innerHTML = "delete";
                
                btnDelete = document.createElement('button');
                btnDelete.style = "color: red; border: 0px; background: none";
                btnDelete.id = id;
                btnDelete.appendChild(icon);

                aDelete = document.createElement('a');
                aDelete.id = id;
                aDelete.addEventListener("click", deleteAccount);
                aDelete.className = "ml-2";
                aDelete.appendChild(btnDelete);


                var tr = document.createElement("tr");

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
                tr.appendChild(td11);
                tr.appendChild(td12);
                tr.appendChild(tdActions);

                tdActions.appendChild(aDelete);
                tdActions.style = "text-align:center";
                menuItemsTBody.appendChild(tr);            
            } 
        }


        // for (var i = 1; i < row.length; i++) 
        // {
        //     for (var j = 1; j < row[i].cells.length - 1; j++ ) 
        //     {
        //         // row[i].cells[j].addEventListener('dblclick', editTD); 
        //         // row[i].cells[j].addEventListener('focusout', releaseTD); 
        //     }
        // }

        $(function() {
            $(menuItemsTable).tablesorter({
                theme : "materialize",
                widthFixed: true,
                // widget code contained in the jquery.tablesorter.widgets.js file
                // use the zebra stripe widget if you plan on hiding any rows (filter widget)
                widgets : [ "filter", "zebra" ],  
                widgetOptions : {
                    // using the default zebra striping class name, so it actually isn't included in the theme variable above
                    // this is ONLY needed for materialize theming if you are using the filter widget, because rows are hidden
                    zebra : ["even", "odd"],
                    // reset filters button
                    filter_reset : ".reset",
                    // extra css class name (string or array) added to the filter element (input or select)
                    // select needs a "browser-default" class or it gets hidden
                    filter_cssFilter: ["", "", "browser-default"]
                }
            })
            .tablesorterPager({
                // target the pager markup - see the HTML block below
                container: $(".tablepagerMenu"),
                // target the pager page select dropdown - choose a page
                cssGoto  : ".pagenum",
                // remove rows from the table to speed up the sort of large tables.
                // setting this to false, only hides the non-visible rows; needed if you plan to add/remove rows with the pager enabled.
                removeRows: true,
                // output string - default is '{page}/{totalPages}';
                // possible variables: {page}, {totalPages}, {filteredPages}, {startRow}, {endRow}, {filteredRows} and {totalRows}
                output: '{startRow} - {endRow} / {filteredRows} ({totalRows})'
        
            });
        
        });
        $(menuItemsTable).trigger("update");
    }
}

function loadAds() 
{
    isMember =  "ad";
    currentModal = "modal-ad";
    row = adsTable.rows;

    editAction = 'https://gym-senior-2020.000webhostapp.com/Admin%20Actions/editAdAction.php';
    deleteAction = 'https://gym-senior-2020.000webhostapp.com/Admin%20Actions/deleteAdAction.php';
    editImageAction = "https://gym-senior-2020.000webhostapp.com/Admin%20Actions/editImageAction.php";

    const adsTBody = document.querySelector("#adsTable > tbody");

    var xhr = new XMLHttpRequest();
    xhr.open('POST','https://gym-senior-2020.000webhostapp.com/Admin%20Actions/returnAdsAction.php',true);
    xhr.setRequestHeader('Content-type','application/x-www-form-urlencoded');
    xhr.send("key="+key);
    
    xhr.onload = function()
    {   

        while(adsTBody.rows.length > 0)
        {
            adsTBody.removeChild(adsTBody.firstChild);
        }

        if(this.status == 200)
        {
            var arr = null;
            try {            
                arr = JSON.parse(this.responseText);
            } catch(e) { console.log(e.message); return;}
            // console.log(arr);
        }

        if(arr != null){
            for(var i = 0; i < Object.keys(arr).length; i++)
            {
                var id = arr[i].id;
                var email = arr[i].email;  
                var name = arr[i].name; 
                var description = arr[i].description;
                var phoneNumber = arr[i].phoneNumber;
                var title = arr[i].title;
                var img = arr[i].img;
                var banner = arr[i].banner;
    
                var td1 = document.createElement("td");
                // td1.addEventListener('dblclick', editTD); 
                // td1.addEventListener('focusout', releaseTD); 
    
                var td2 = document.createElement("td");
                td2.addEventListener('dblclick', editTD); 
                td2.addEventListener('focusout', releaseTD); 
    
                var td3 = document.createElement("td");
                td3.addEventListener('dblclick', editTD); 
                td3.addEventListener('focusout', releaseTD); 
    
                var td4 = document.createElement("td");
                td4.addEventListener('dblclick', editTD); 
                td4.addEventListener('focusout', releaseTD); 
    
                var td5 = document.createElement("td");
                td5.addEventListener('dblclick', editTD); 
                td5.addEventListener('focusout', releaseTD); 
                td5.className = "text-center";
                viewIcon = document.createElement("i");
                viewIcon.className = "material-icons";
                viewIcon.innerHTML = "remove_red_eye";
                viewIcon.style = "color:#800080; cursor: pointer; font-size: 1rem";
                viewIcon.id = description;
                viewIcon.addEventListener('click', function()
                {
                    var instance = M.Modal.getInstance(document.getElementById('myModal'));
                    document.getElementById("myModalContent").textContent = this.id;
                    instance.open();
    
                })

    
                var td6 = document.createElement("td");
                td6.addEventListener('dblclick', editTD); 
                td6.addEventListener('focusout', releaseTD); 
    

                var td7 = document.createElement("td");
                // td7.addEventListener('dblclick', editTD); 
                // td7.addEventListener('focusout', releaseTD); 
    
    
                var td8 = document.createElement("td");
                // td8.addEventListener('dblclick', editTD); 
                // td8.addEventListener('focusout', releaseTD); 
    
        
                var imgDisplay = document.createElement("img");
                imgDisplay.className = "text-center";
                var bannerDisplay = document.createElement("img");

                imgDisplay.src = "https://gym-senior-2020.000webhostapp.com/TrainerImages/Profiles/" + img;
                bannerDisplay.src = "https://gym-senior-2020.000webhostapp.com/TrainerImages/Profiles/" + banner;

                bannerDisplay.style.width = "200px";
                bannerDisplay.style.borderRadius = "5%";

                var tdActions = document.createElement("td");
                // td1.addEventListener('dblclick', editTD); 
                // td1.addEventListener('focusout', releaseTD); 

                imgDisplay.id = id;
                imgDisplay.addEventListener('click', function(){
                    editImageAction = "https://gym-senior-2020.000webhostapp.com/Admin%20Actions/editPTImageAction.php";
                 });
                 imgDisplay.addEventListener('click', imageUpload);

                 bannerDisplay.id = id;
                 bannerDisplay.addEventListener('click', function(){
                    editImageAction = "https://gym-senior-2020.000webhostapp.com/Admin%20Actions/editPTBannerAction.php";
                 });
                 bannerDisplay.addEventListener('click', imageUpload);

    
                td1.textContent = id;
                td2.textContent = email;
                td3.textContent = name;
                td4.textContent = title;
                td5.appendChild(viewIcon);
                td6.textContent = phoneNumber;
                // td7.textContent = address;
                td7.appendChild(imgDisplay);
                td8.appendChild(bannerDisplay);
                td8.className = "text-center";
    
                var icon = document.createElement("i");
                icon.className = "small material-icons mx-auto";
                icon.innerHTML = "delete";
                
                btnDelete = document.createElement('button');
                btnDelete.style = "color: red; border: 0px; background: none";
                btnDelete.id = id;
                btnDelete.appendChild(icon);
    
                aDelete = document.createElement('a');
                aDelete.id = id;
                aDelete.addEventListener("click", deleteAccount);
                aDelete.className = "ml-2";
                aDelete.appendChild(btnDelete);
    
    
                var tr = document.createElement("tr");
    
                tr.appendChild(td1);
                tr.appendChild(td2);
                tr.appendChild(td3);
                tr.appendChild(td4);
                tr.appendChild(td5);
                tr.appendChild(td6);
                tr.appendChild(td7);
                tr.appendChild(td8);
                tr.appendChild(tdActions);
    
                tdActions.appendChild(aDelete);
                tdActions.style = "text-align:center";
                adsTBody.appendChild(tr);            
            } 
        }

        $(function() {
            $(adsTable).tablesorter({
                theme : "materialize",
                widthFixed: true,
                // widget code contained in the jquery.tablesorter.widgets.js file
                // use the zebra stripe widget if you plan on hiding any rows (filter widget)
                widgets : [ "filter", "zebra" ],  
                widgetOptions : {
                    // using the default zebra striping class name, so it actually isn't included in the theme variable above
                    // this is ONLY needed for materialize theming if you are using the filter widget, because rows are hidden
                    zebra : ["even", "odd"],
                    // reset filters button
                    filter_reset : ".reset",
                    // extra css class name (string or array) added to the filter element (input or select)
                    // select needs a "browser-default" class or it gets hidden
                    filter_cssFilter: ["", "", "browser-default"]
                }
            })
            .tablesorterPager({
                // target the pager markup - see the HTML block below
                container: $(".tablepagerAds"),
                // target the pager page select dropdown - choose a page
                cssGoto  : ".pagenum",
                // remove rows from the table to speed up the sort of large tables.
                // setting this to false, only hides the non-visible rows; needed if you plan to add/remove rows with the pager enabled.
                removeRows: true,
                // output string - default is '{page}/{totalPages}';
                // possible variables: {page}, {totalPages}, {filteredPages}, {startRow}, {endRow}, {filteredRows} and {totalRows}
                output: '{startRow} - {endRow} / {filteredRows} ({totalRows})'
        
            });
        
        });
        $(adsTable).trigger("update");
    }
 }

function loadmessages() 
{
    isMember = "message";
    // currentModal = "modal-message";
    row = messagesTable.rows;

    // editAction = 'https://gym-senior-2020.000webhostapp.com/Admin%20Actions/editmessageAction.php';
    deleteAction = 'https://gym-senior-2020.000webhostapp.com/Admin%20Actions/deleteMessageAction.php';
    // editImageAction = "https://gym-senior-2020.000webhostapp.com/Admin%20Actions/editmessageImageAction.php";
    
    const messagesTBody = document.querySelector("#messagesTable > tbody");

    var xhr = new XMLHttpRequest();
    xhr.open('POST','https://gym-senior-2020.000webhostapp.com/Admin%20Actions/returnMessagesAction.php',true);
    xhr.setRequestHeader('Content-type','application/x-www-form-urlencoded');
    xhr.send("key="+key);
    
    xhr.onload = function()
    {   
        while(messagesTBody.rows.length > 0)
        {
            messagesTBody.removeChild(messagesTBody.firstChild);
        }

        if(this.status == 200)
        {
            var arr = null;
            try {            
                arr = JSON.parse(this.responseText);
            } catch(e) { console.log(e.message); return;}
            // console.log(arr);
        }


        if(arr != null){
            for(var i = 0; i < Object.keys(arr).length; i++)
            {
                var id = arr[i].id;
                var subject = arr[i].subject;
                var content = arr[i].content; 
                var email = arr[i].email;
                var type = arr[i].type;
                var time = arr[i].time;

                var td1 = document.createElement("td");
                td1.className = "text-center";

                var td2 = document.createElement("td");
                td2.className = "text-center";
                viewIcon = document.createElement("i");
                viewIcon.className = "material-icons";
                viewIcon.innerHTML = "remove_red_eye";
                viewIcon.style = "color:#800080; cursor: pointer; font-size: 1rem";
                viewIcon.id = subject;
                viewIcon.Name = content;
                viewIcon.addEventListener('click', function()
                {
                    var instance = M.Modal.getInstance(document.getElementById('myModal'));
                    document.getElementById('myModal').style = "max-width:60%; margin-top: 9%";
                    document.getElementById("myModalContent").innerHTML = 
                    `<h5 class="text-center">${this.id}</h5>
                    <p>${this.Name}</p>
                    `
                    instance.open();

                })

                var td3 = document.createElement("td");

                var td4 = document.createElement("td");
                td4.className = "text-center";

                var td5 = document.createElement("td");
                td5.className = "text-center";

                var tdActions = document.createElement("td");


                td1.textContent = id;
                td2.appendChild(viewIcon);
                td3.textContent = email;
                td4.textContent = type;
                td5.textContent = time;

                var icon = document.createElement("i");
                icon.className = "small material-icons mx-auto";
                icon.innerHTML = "delete";
                
                btnDelete = document.createElement('button');
                btnDelete.style = "color: red; border: 0px; background: none";
                btnDelete.id = id;
                btnDelete.appendChild(icon);

                aDelete = document.createElement('a');
                aDelete.id = id;
                aDelete.addEventListener("click", deleteAccount);
                aDelete.className = "ml-2";
                aDelete.appendChild(btnDelete);


                var tr = document.createElement("tr");

                tr.appendChild(td1);
                tr.appendChild(td2);
                tr.appendChild(td3);
                tr.appendChild(td4);
                tr.appendChild(td5);
                tr.appendChild(tdActions);

                tdActions.appendChild(aDelete);
                tdActions.style = "text-align:center";
                messagesTBody.appendChild(tr);            
            }
        } 

        $(function() {
            $(messagesTable).tablesorter({
                theme : "materialize",
                widthFixed: true,
                // widget code contained in the jquery.tablesorter.widgets.js file
                // use the zebra stripe widget if you plan on hiding any rows (filter widget)
                widgets : [ "filter", "zebra" ],  
                widgetOptions : {
                    // using the default zebra striping class name, so it actually isn't included in the theme variable above
                    // this is ONLY needed for materialize theming if you are using the filter widget, because rows are hidden
                    zebra : ["even", "odd"],
                    // reset filters button
                    filter_reset : ".reset",
                    // extra css class name (string or array) added to the filter element (input or select)
                    // select needs a "browser-default" class or it gets hidden
                    filter_cssFilter: ["", "", "browser-default"]
                }
            })
            .tablesorterPager({
                // target the pager markup - see the HTML block below
                container: $(".tablepagerMessages"),
                // target the pager page select dropdown - choose a page
                cssGoto  : ".pagenum",
                // remove rows from the table to speed up the sort of large tables.
                // setting this to false, only hides the non-visible rows; needed if you plan to add/remove rows with the pager enabled.
                removeRows: true,
                // output string - default is '{page}/{totalPages}';
                // possible variables: {page}, {totalPages}, {filteredPages}, {startRow}, {endRow}, {filteredRows} and {totalRows}
                output: '{startRow} - {endRow} / {filteredRows} ({totalRows})'
        
            });
        
        });
        $(messagesTable).trigger("update");
    }
}

function loadCurrentWorkouts() 
{
    isMember = "currentWorkout";
    // currentModal = "modal-CurrentWorkout";
    row = currentWorkoutsTable.rows;

    // editAction = 'https://gym-senior-2020.000webhostapp.com/Admin%20Actions/editmessageAction.php';
    deleteAction = 'https://gym-senior-2020.000webhostapp.com/Admin%20Actions/deleteWorkoutAction.php';
    // editImageAction = "https://gym-senior-2020.000webhostapp.com/Admin%20Actions/editmessageImageAction.php";
    
    const CurrentWorkoutsTBody = document.querySelector("#currentWorkoutsTable > tbody");

    var xhr = new XMLHttpRequest();
    xhr.open('POST','https://gym-senior-2020.000webhostapp.com/Admin%20Actions/returnCurrentWorkoutsAction.php',true);
    xhr.setRequestHeader('Content-type','application/x-www-form-urlencoded');
    xhr.send("key="+key);
    
    xhr.onload = function()
    {   
        
        while(CurrentWorkoutsTBody.rows.length > 0)
        {
            CurrentWorkoutsTBody.removeChild(CurrentWorkoutsTBody.firstChild);
        }

        if(this.status == 200)
        {
            var arr = null;
            try {            
                arr = JSON.parse(this.responseText);
            } catch(e) { console.log(e.message);}
            // console.log(arr);
        }

        if(arr != null) {
            for(var i = 0; i < Object.keys(arr).length; i++)
            {
                var id = arr[i].id;
                var memberID = arr[i].memberID;
                var workoutID = arr[i].workoutID; 

                var td1 = document.createElement("td");
                td1.className = "text-center";

                var td2 = document.createElement("td");
                td2.className = "text-center";

                var td3 = document.createElement("td");
                td3.className = "text-center";

                var tdActions = document.createElement("td");


                td1.textContent = id;
                td2.textContent = memberID;
                td3.textContent = workoutID;
                
                var icon = document.createElement("i");
                icon.className = "small material-icons mx-auto";
                icon.innerHTML = "delete";

                btnDelete = document.createElement('button');
                btnDelete.style = "color: red; border: 0px; background: none";
                btnDelete.id = workoutID;
                btnDelete.appendChild(icon);

                aDelete = document.createElement('a');
                aDelete.id = workoutID;
                aDelete.addEventListener("click", deleteAccount);
                aDelete.className = "ml-2";
                aDelete.appendChild(btnDelete);


                var tr = document.createElement("tr");

                tr.appendChild(td1);
                tr.appendChild(td2);
                tr.appendChild(td3);
                tr.appendChild(tdActions);

                tdActions.appendChild(aDelete);
                tdActions.style = "text-align:center";
                CurrentWorkoutsTBody.appendChild(tr);            
            } 
        }

        $(function() {
            $(currentWorkoutsTable).tablesorter({
                theme : "materialize",
                widthFixed: true,
                // widget code contained in the jquery.tablesorter.widgets.js file
                // use the zebra stripe widget if you plan on hiding any rows (filter widget)
                widgets : [ "filter", "zebra" ],  
                widgetOptions : {
                    // using the default zebra striping class name, so it actually isn't included in the theme variable above
                    // this is ONLY needed for materialize theming if you are using the filter widget, because rows are hidden
                    zebra : ["even", "odd"],
                    // reset filters button
                    filter_reset : ".reset",
                    // extra css class name (string or array) added to the filter element (input or select)
                    // select needs a "browser-default" class or it gets hidden
                    filter_cssFilter: ["", "", "browser-default"]
                }
            })
            .tablesorterPager({
                // target the pager markup - see the HTML block below
                container: $(".tablepagerCurrentWorkouts"),
                // target the pager page select dropdown - choose a page
                cssGoto  : ".pagenum",
                // remove rows from the table to speed up the sort of large tables.
                // setting this to false, only hides the non-visible rows; needed if you plan to add/remove rows with the pager enabled.
                removeRows: true,
                // output string - default is '{page}/{totalPages}';
                // possible variables: {page}, {totalPages}, {filteredPages}, {startRow}, {endRow}, {filteredRows} and {totalRows}
                output: '{startRow} - {endRow} / {filteredRows} ({totalRows})'
        
            });
        
        });
        $(currentWorkoutsTable).trigger("update");
    }
}

function loadPreviousWorkouts() 
{
    isMember = "previousWorkout";
    // currentModal = "modal-CurrentWorkout";
    row = previousWorkoutsTable.rows;

    // editAction = 'https://gym-senior-2020.000webhostapp.com/Admin%20Actions/editmessageAction.php';
    deleteAction = 'https://gym-senior-2020.000webhostapp.com/Admin%20Actions/deleteWorkoutAction.php';
    // editImageAction = "https://gym-senior-2020.000webhostapp.com/Admin%20Actions/editmessageImageAction.php";
    
    const previousWorkoutsTBody = document.querySelector("#previousWorkoutsTable > tbody");

    var xhr = new XMLHttpRequest();
    xhr.open('POST','https://gym-senior-2020.000webhostapp.com/Admin%20Actions/returnPreviousWorkoutsAction.php',true);
    xhr.setRequestHeader('Content-type','application/x-www-form-urlencoded');
    xhr.send("key="+key);
    
    xhr.onload = function()
    {   
                
        while(previousWorkoutsTBody.rows.length > 0)
        {
            previousWorkoutsTBody.removeChild(previousWorkoutsTBody.firstChild);
        }

        if(this.status == 200)
        {
            var arr = null;
            try {            
                arr = JSON.parse(this.responseText);
            } catch(e) { console.log(e.message);}
            // console.log(arr);
        }
        if(arr != null)
        {
            for(var i = 0; i < Object.keys(arr).length; i++)
            {
                var id = arr[i].id;
                var memberID = arr[i].memberID;
                var workoutID = arr[i].workoutID; 
                var date = arr[i].date; 

                var td1 = document.createElement("td");
                td1.className = "text-center";

                var td2 = document.createElement("td");
                td2.className = "text-center";

                var td3 = document.createElement("td");
                td3.className = "text-center";

                var td4 = document.createElement("td");
                td4.className = "text-center";

                var tdActions = document.createElement("td");


                td1.textContent = id;
                td2.textContent = memberID;
                td3.textContent = workoutID;
                td4.textContent = date;
                
                var icon = document.createElement("i");
                icon.className = "small material-icons mx-auto";
                icon.innerHTML = "delete";

                btnDelete = document.createElement('button');
                btnDelete.style = "color: red; border: 0px; background: none";
                btnDelete.id = workoutID;
                btnDelete.appendChild(icon);

                aDelete = document.createElement('a');
                aDelete.id = workoutID;
                aDelete.addEventListener("click", deleteAccount);
                aDelete.className = "ml-2";
                aDelete.appendChild(btnDelete);


                var tr = document.createElement("tr");

                tr.appendChild(td1);
                tr.appendChild(td2);
                tr.appendChild(td3);
                tr.appendChild(td4);
                tr.appendChild(tdActions);

                tdActions.appendChild(aDelete);
                tdActions.style = "text-align:center";
                previousWorkoutsTBody.appendChild(tr);            
            } 
        }

        $(function() {
            $(previousWorkoutsTable).tablesorter({
                theme : "materialize",
                widthFixed: true,
                // widget code contained in the jquery.tablesorter.widgets.js file
                // use the zebra stripe widget if you plan on hiding any rows (filter widget)
                widgets : [ "filter", "zebra" ],  
                widgetOptions : {
                    // using the default zebra striping class name, so it actually isn't included in the theme variable above
                    // this is ONLY needed for materialize theming if you are using the filter widget, because rows are hidden
                    zebra : ["even", "odd"],
                    // reset filters button
                    filter_reset : ".reset",
                    // extra css class name (string or array) added to the filter element (input or select)
                    // select needs a "browser-default" class or it gets hidden
                    filter_cssFilter: ["", "", "browser-default"]
                }
            })
            .tablesorterPager({
                // target the pager markup - see the HTML block below
                container: $(".tablepagerPreviousWorkouts"),
                // target the pager page select dropdown - choose a page
                cssGoto  : ".pagenum",
                // remove rows from the table to speed up the sort of large tables.
                // setting this to false, only hides the non-visible rows; needed if you plan to add/remove rows with the pager enabled.
                removeRows: true,
                // output string - default is '{page}/{totalPages}';
                // possible variables: {page}, {totalPages}, {filteredPages}, {startRow}, {endRow}, {filteredRows} and {totalRows}
                output: '{startRow} - {endRow} / {filteredRows} ({totalRows})'
        
            });
        
        });
        $(previousWorkoutsTable).trigger("update");
    }
}

function loadAssignedWorkouts() 
{
    isMember = "assignedWorkout";
    // currentModal = "modal-CurrentWorkout";
    row = assignedWorkoutsTable.rows;

    // editAction = 'https://gym-senior-2020.000webhostapp.com/Admin%20Actions/editmessageAction.php';
    deleteAction = 'https://gym-senior-2020.000webhostapp.com/Admin%20Actions/deleteWorkoutAction.php';
    // editImageAction = "https://gym-senior-2020.000webhostapp.com/Admin%20Actions/editmessageImageAction.php";
    
    const assignedWorkoutsTBody = document.querySelector("#assignedWorkoutsTable > tbody");

    var xhr = new XMLHttpRequest();
    xhr.open('POST','https://gym-senior-2020.000webhostapp.com/Admin%20Actions/returnAssignedWorkoutsAction.php',true);
    xhr.setRequestHeader('Content-type','application/x-www-form-urlencoded');
    xhr.send("key="+key);
    
    xhr.onload = function()
    {   
                
        while(assignedWorkoutsTBody.rows.length > 0)
        {
            assignedWorkoutsTBody.removeChild(assignedWorkoutsTBody.firstChild);
        }

        if(this.status == 200)
        {
            var arr = null;
            try {            
                arr = JSON.parse(this.responseText);
            } catch(e) { console.log(e.message);}
            // console.log(arr);
        }
        if(arr != null)
        {
            for(var i = 0; i < Object.keys(arr).length; i++)
            {
                var id = arr[i].id;
                var workoutID = arr[i].workoutID; 
                var coachID = arr[i].coachID;
                var memberID = arr[i].memberID;
                var time = arr[i].time; 

                var td1 = document.createElement("td");
                td1.className = "text-center";

                var td2 = document.createElement("td");
                td2.className = "text-center";

                var td3 = document.createElement("td");
                td3.className = "text-center";

                var td4 = document.createElement("td");
                td4.className = "text-center";

                var td5 = document.createElement("td");
                td5.className = "text-center";

                var tdActions = document.createElement("td");


                td1.textContent = id;
                td2.textContent = workoutID;
                td3.textContent = coachID;
                td4.textContent = memberID;
                td5.textContent = time;
                
                var icon = document.createElement("i");
                icon.className = "small material-icons mx-auto";
                icon.innerHTML = "delete";

                btnDelete = document.createElement('button');
                btnDelete.style = "color: red; border: 0px; background: none";
                btnDelete.id = workoutID;
                btnDelete.appendChild(icon);

                aDelete = document.createElement('a');
                aDelete.id = workoutID;
                aDelete.addEventListener("click", deleteAccount);
                aDelete.className = "ml-2";
                aDelete.appendChild(btnDelete);


                var tr = document.createElement("tr");

                tr.appendChild(td1);
                tr.appendChild(td2);
                tr.appendChild(td3);
                tr.appendChild(td4);
                tr.appendChild(td5);
                tr.appendChild(tdActions);

                tdActions.appendChild(aDelete);
                tdActions.style = "text-align:center";
                assignedWorkoutsTBody.appendChild(tr);            
            } 
        }

        $(function() {
            $(assignedWorkoutsTable).tablesorter({
                theme : "materialize",
                widthFixed: true,
                // widget code contained in the jquery.tablesorter.widgets.js file
                // use the zebra stripe widget if you plan on hiding any rows (filter widget)
                widgets : [ "filter", "zebra" ],  
                widgetOptions : {
                    // using the default zebra striping class name, so it actually isn't included in the theme variable above
                    // this is ONLY needed for materialize theming if you are using the filter widget, because rows are hidden
                    zebra : ["even", "odd"],
                    // reset filters button
                    filter_reset : ".reset",
                    // extra css class name (string or array) added to the filter element (input or select)
                    // select needs a "browser-default" class or it gets hidden
                    filter_cssFilter: ["", "", "browser-default"]
                }
            })
            .tablesorterPager({
                // target the pager markup - see the HTML block below
                container: $(".tablepagerAssignedWorkouts"),
                // target the pager page select dropdown - choose a page
                cssGoto  : ".pagenum",
                // remove rows from the table to speed up the sort of large tables.
                // setting this to false, only hides the non-visible rows; needed if you plan to add/remove rows with the pager enabled.
                removeRows: true,
                // output string - default is '{page}/{totalPages}';
                // possible variables: {page}, {totalPages}, {filteredPages}, {startRow}, {endRow}, {filteredRows} and {totalRows}
                output: '{startRow} - {endRow} / {filteredRows} ({totalRows})'
        
            });
        
        });
        $(assignedWorkoutsTable).trigger("update");
    }
}


//Global for all tables
function editTD()
{
    this.setAttribute('contenteditable','true');
    this.focus();
    this.style = "background-color: #f6f6df ; border:1px solid #0099CC;";
}


//This is just for the members table, because it is connected to firebase
function releaseEmail()
{
    this.setAttribute('contenteditable','false');
    this.style = "background-color: none";

    var updatedText = this.textContent;
    var updatedTextId = row[this.parentNode.rowIndex].cells[0].id;
    var updatedCol = row[0].cells[this.cellIndex].id;

    var email = row[this.parentNode.rowIndex].cells[1].textContent;
    var password = row[this.parentNode.rowIndex].cells[4].textContent;

    var updatedPackage = JSON.stringify({
                                        key:key,
                                        updatedText:updatedText,
                                        id:updatedTextId, 
                                        colName:updatedCol
                                        });
                                        
    var xhr = new XMLHttpRequest();
    xhr.open('POST', editAction,true);
    xhr.setRequestHeader('Content-type','application/x-www-form-urlencoded');
    xhr.send("jsonUpdate=" + updatedPackage);

    xhr.onload = function(){

        if(this.status === 200){
            const updateUser = functions.httpsCallable('updateUser');
            data = {
                uid: this.responseText,
                email: email,
                password: password
            }
            updateUser(data).then(result => {
                console.log(result);
            }) 
        }
    }
}

//Also only for mmebers due to Firebase connections
function releaseTD()
{
    this.setAttribute('contenteditable','false');
    this.style = "background-color: none";

    var updatedText = this.textContent;
    var updatedTextId = row[this.parentNode.rowIndex].cells[0].id;
    var updatedCol = row[0].cells[this.cellIndex].id;

    var updatedPackage = JSON.stringify({
                                        key:key,
                                        updatedText:updatedText,
                                        id:updatedTextId, 
                                        colName:updatedCol
                                        });
                                        
    var xhr = new XMLHttpRequest();
    xhr.open('POST', editAction,true);
    xhr.setRequestHeader('Content-type','application/x-www-form-urlencoded');
    xhr.send("jsonUpdate=" + updatedPackage);

    xhr.onload = function(){

        if(this.status === 200){
            if(isMember == "member"){
                db.collection('users').doc(this.responseText).update
                ({
                    [updatedCol]: updatedText
                }).then(() =>
                {
                }).catch(function(error) {
                    // Handle Errors here.
                    console.log(error.message);
                });
            }
        }
    }
}

function deleteAccount()
{
    if(!confirm('Are you sure you want to delete this item? \nThis action can not be undone and will delete all information dependent on this record.')) return false;
    
    var toDelete = this.id;
    var deletePackage = JSON.stringify({'id':toDelete, 'key':key});

    var xhr = new XMLHttpRequest();
    xhr.open('POST', deleteAction,true);
    xhr.setRequestHeader('Content-type','application/x-www-form-urlencoded');
    xhr.send("deletePackage=" + deletePackage);

    xhr.onload = function()
    {
        if(this.status === 200)
        {      
            // console.log(this.response);
            // console.log(isMember);
            // console.log(this.responseText);
            // Realtime database 
            const deleteUser = functions.httpsCallable('deleteUser');
            const userUID =  this.responseText;

            switch(isMember){
                case "member":
                    loadMembers();
                    console.log(this.responseText);
                    db.collection('users').doc(userUID).delete();   
                    deleteUser(userUID).then(result => {
                        console.log(result);
                    })
                    break;
                case "coach":
                    loadCoaches();
                    console.log(this.responseText);
                    db.collection('users').doc(userUID).delete();   
                    deleteUser(userUID).then(result => {
                        console.log(result);
                    })
                    break;
                case "bartender":
                    loadBartenders();
                    console.log(this.responseText);
                    db.collection('users').doc(userUID).delete();   
                    deleteUser(userUID).then(result => {
                        console.log(result);
                    })
                    break;
                case "machine":
                    loadMachines();
                    break;
                case "menuItem":
                    loadMenuItems();
                    break;
                case "message":
                    loadmessages();
                    break;
                case "previousWorkout":
                    loadPreviousWorkouts();
                    break;
                case "currentWorkout":
                    loadCurrentWorkouts();
                    break;
                case "assignedWorkout":
                    loadAssignedWorkouts();
                    break;
                case "move":
                    loadMoves();
                    break;
            }
        }
    }
}