const key = "KeyForDatabase";

let ordersTab = document.querySelector('#orders');
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

//Global for all tables
function editTD()
{
    this.setAttribute('contenteditable','true');
    this.focus();
    this.style = "background-color: #f6f6df ; border:1px solid #0099CC;";
}


function releaseTD()
{
    this.setAttribute('contenteditable','false');
    this.style = "background-color: none";

    var updatedText = this.textContent;
    var updatedTextId = row[this.parentNode.rowIndex].cells[0].textContent;
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
            loadMenuItems();
        }
    }
}

var selectedOrder;
var byUid;
function renderOrder(doc)
{
    const data = doc.data();
    div1 = document.createElement("div");
    div1.className = "col-sm-6 col-lg-3 col-12";
    div1.setAttribute('data-id', doc.id);
    
    div2 = document.createElement("div");
    div2.className = "card";
    div2.id = "card" + doc.id;

    spanMember = document.createElement("span");
    spanMember.className = "card-title text-center mt-1";
    spanMember.innerHTML = "By: " + data['ByMember'];

    spanTime = document.createElement("span");
    spanTime.className = "text-center mb-1";
    spanTime.innerHTML = data['Preparation Time'] + " minutes";

    div4 = document.createElement("div");
    div4.className = "card-content";

    div5 = document.createElement("div");
    div5.className = "card-action";

    p = document.createElement("p");

    div4.appendChild(p);
    div2.appendChild(spanMember);
    div2.appendChild(spanTime);
    div2.appendChild(div4);
    div2.appendChild(div5);
    div1.appendChild(div2);

    var btnReject = document.createElement('button');
    btnReject.className = "waves-effect waves-light red btn-small";
    btnReject.style = "border: 0px; background: none";
    btnReject.innerHTML = "Reject"  + "<i class=\"material-icons right\">close</i>" ;
    btnReject.id = doc.id + "REJECT";
    btnReject.setAttribute('docID', doc.id);
    btnReject.setAttribute('fromUID', data['ByUID']);
    btnReject.addEventListener('click', function() 
    {
        byUid = this.getAttribute('fromUID');
        selectedOrder = this.getAttribute('docID');
        if(this.textContent.includes("Reject"))
        {
            let instance = M.Modal.getInstance(document.getElementById('modal-rejection'));
            instance.open();
        } else if (this.textContent.includes("Unserved")) 
        {
            db.collection('orders').doc(selectedOrder).delete();
            updateStatus("unserved");
        }
    });


    btnConfirm = document.createElement('button');
    btnConfirm.className = "waves-effect waves-light green btn-small right";
    btnConfirm.style = "border: 0px; background: none";
    btnConfirm.innerHTML = "Confirm"  + "<i class=\"material-icons right\">done</i>" ;
    btnConfirm.setAttribute('docID', doc.id);
    btnConfirm.setAttribute('fromUID', data['ByUID']);
    btnConfirm.addEventListener("click", function()
    {
        byUid = this.getAttribute('fromUID');
        selectedOrder = this.getAttribute('docID');
        if(this.textContent.includes("Confirm"))
        {
            sendNotification("Your Order Was Confirmed!", "Your order is being prepared by the bartender.");
            this.innerHTML = "Ready"  + "<i class=\"material-icons right\">done</i>"
            updateStatus("confirmed");
        } else if (this.textContent.includes("Ready")) {
            sendNotification("Your Order is Ready!", "Your order is ready to be picked up at the bar.");
            this.innerHTML = "Served"  + "<i class=\"material-icons right\">done</i>";
            updateStatus("prepared");
            document.getElementById(doc.id + "REJECT").innerHTML = "Unserved"  + "<i class=\"material-icons right\">close</i>";
            document.getElementById("card" + doc.id).style.backgroundColor = "#4caf504f";

        } else if (this.textContent.includes("Served")) {
            updateStatus("served");
            db.collection('orders').doc(selectedOrder).delete();
        }
    })


    div5.appendChild(btnConfirm);
    div5.appendChild(btnReject);
    // btnDelete = document.createElement('button');
    // btnDelete.style = "color: red; border: 0px; background: none";
    // btnDelete.id = workoutID;

    // btnDelete = document.createElement('button');
    // btnDelete.style = "color: red; border: 0px; background: none";
    // btnDelete.id = workoutID;

    ordersTab.appendChild(div1);

    var table = document.createElement("table");
    var thName = document.createElement("th");
    var thAmount = document.createElement("th");
    var thPrice = document.createElement("th");
    thName.textContent = "Item";
    
    thAmount.textContent = "Quantity";
    thAmount.className = "text-center";

    thPrice.textContent = "Price";
    thPrice.className = "text-center";

    table.appendChild(thName);
    table.appendChild(thAmount);
    table.appendChild(thPrice);
    for (var key of Object.keys(data)) {
        if(key.includes("meal"))
        {
            var tr = document.createElement("tr");
            var tdName = document.createElement("td");

            var tdAmount = document.createElement("td");
            tdAmount.className = "text-center";

            var tdPrice = document.createElement("td");
            tdPrice.className = "text-center";

            var splitter = data[key];
            var item = splitter.split("#");
            var item2 = item[1].split("$");
            
            var itemName = item[0];
            var itemAmount = item2[0];
            var itemPrice = item2[1] + "$";

            tdName.textContent = itemName;
            tdAmount.textContent = itemAmount;
            tdPrice.textContent = itemPrice;
            tr.appendChild(tdName);
            tr.appendChild(tdAmount);
            tr.appendChild(tdPrice);
            table.appendChild(tr);
        }
    }   

    for (var key of Object.keys(data)) {
        if(key.includes("salad"))
        {
            var tr = document.createElement("tr");
            var tdName = document.createElement("td");

            var tdAmount = document.createElement("td");
            tdAmount.className = "text-center";

            var tdPrice = document.createElement("td");
            tdPrice.className = "text-center";

            var splitter = data[key];
            var item = splitter.split("#");
            var item2 = item[1].split("$");
            
            var itemName = item[0];
            var itemAmount = item2[0];
            var itemPrice = item2[1] + "$";

            tdName.textContent = itemName;
            tdAmount.textContent = itemAmount;
            tdPrice.textContent = itemPrice;
            tr.appendChild(tdName);
            tr.appendChild(tdAmount);
            tr.appendChild(tdPrice);
            table.appendChild(tr);
        }
    }  

    for (var key of Object.keys(data)) {
        if(key.includes("drink"))
        {
            var tr = document.createElement("tr");
            var tdName = document.createElement("td");

            var tdAmount = document.createElement("td");
            tdAmount.className = "text-center";

            var tdPrice = document.createElement("td");
            tdPrice.className = "text-center";

            var splitter = data[key];
            var item = splitter.split("#");
            var item2 = item[1].split("$");
            
            var itemName = item[0];
            var itemAmount = item2[0];
            var itemPrice = item2[1] + "$";

            tdName.textContent = itemName;
            tdAmount.textContent = itemAmount;
            tdPrice.textContent = itemPrice;
            tr.appendChild(tdName);
            tr.appendChild(tdAmount);
            tr.appendChild(tdPrice);
            table.appendChild(tr);
        }
    }  

    for (var key of Object.keys(data)) {
        if(key.includes("bar"))
        {
            var tr = document.createElement("tr");
            var tdName = document.createElement("td");

            var tdAmount = document.createElement("td");
            tdAmount.className = "text-center";

            var tdPrice = document.createElement("td");
            tdPrice.className = "text-center";

            var splitter = data[key];
            var item = splitter.split("#");
            var item2 = item[1].split("$");
            
            var itemName = item[0];
            var itemAmount = item2[0];
            var itemPrice = item2[1] + "$";

            tdName.textContent = itemName;
            tdAmount.textContent = itemAmount;
            tdPrice.textContent = itemPrice;
            tr.appendChild(tdName);
            tr.appendChild(tdAmount);
            tr.appendChild(tdPrice);
            table.appendChild(tr);
        }
    }     
    div4.appendChild(table);

    var totalPriceSpan = document.createElement("span");
    totalPriceSpan.className = "text-center right mr-3 font-weight-bold h5";
    totalPriceSpan.textContent = data['Total Price'] + "$";
    div4.appendChild(totalPriceSpan);
}


db.collection('orders').onSnapshot(snapshot => 
    {
        let changes = snapshot.docChanges();
        changes.forEach(change => 
            {
                if(change.type == 'added')
                {
                    desktopNotify("New Order", "A new order has been placed...");
                    renderOrder(change.doc);
                } else if (change.type == 'removed')
                {
                    let div = ordersTab.querySelector('[data-id=' + change.doc.id + ']');
                    try{
                        let div = ordersTab.querySelector('[data-id=' + change.doc.id + ']');
                    } catch(error){
                        console.log(error.message);
                        div = ordersTab.querySelector('[data-id=' + selectedOrder + ']');
                    };
                    ordersTab.removeChild(div);
                }
            })
    })

var rejectionForm = document.getElementById("rejectForm");
document.getElementById('btnReject').addEventListener('click', function()
{
    db.collection('orders').doc(selectedOrder).delete().then(() =>
    {      
        let instance = M.Modal.getInstance(document.getElementById('modal-rejection'));
        instance.close();
        rejectionForm.reset();  
        updateStatus("denied");
        sendNotification("Your Order Was Denied!", document.getElementById("rejectionReason").value);
    }); 
});

function sendNotification(Title, Message)
{ 
    db.collection('users/' + byUid + '/Notifications').doc().set
    ({
        title: Title,
        message: Message,
    })
}

function desktopNotify(notificationTitle, notificationBody)
{

    const notifier = require('node-notifier');
    const path = require('path');
    
    notifier.notify(
      {
        appId: "com.example.mygymbar",
        title: notificationTitle,
        message: notificationBody,
        icon: path.join(__dirname, 'forkknife.png'), // Absolute path (doesn't work on balloons)
        sound: true, // Only Notification Center or Windows Toasters
        remove: undefined
    }, (err) => {
        if (err) {
          console.error('Snoretoast error: ', err);
        }
    });
      

    notifier.on('click', function(notifierObject, options, event) {

    });
}

function updateStatus(status)
{
    var updatedPackage = JSON.stringify({
                                        key:key,
                                        updatedText:status,
                                        id:selectedOrder,
                                        uid:uid
                                        });
                                        
    var xhr = new XMLHttpRequest();
    xhr.open('POST', "https://gym-senior-2020.000webhostapp.com/updateOrderStatusAction.php",true);
    xhr.setRequestHeader('Content-type','application/x-www-form-urlencoded');
    xhr.send("jsonUpdate=" + updatedPackage);

    xhr.onload = function()
    {
    }
}

