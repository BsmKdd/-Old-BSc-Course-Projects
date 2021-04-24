const key = "KeyForDatabase";
$(document).ready(function(){
    $('.tabs').tabs();
});

auth.onAuthStateChanged(user => {
    if(user) {
        user.getIdTokenResult().then(idTokenResult => {
            console.log(idTokenResult.claims.admin)
        });
    } else {

    }  
})

document.addEventListener("DOMContentLoaded", loadMembers);

var membersTab = document.getElementById('membersTab');
var coachesTab = document.getElementById('coachesTab');
var bartendersTab = document.getElementById('bartendersTab');
var workoutsTab = document.getElementById('workoutsTab');
var movesTab = document.getElementById('movesTab');
var machinesTab = document.getElementById('machinesTab');
var menuTab = document.getElementById('menuTab');
var adsTab = document.getElementById('adsTab');
var messagesTab = document.getElementById('messagesTab');
var currentWorkoutsTab = document.getElementById('currentWorkoutsTab');
var previousWorkoutsTab = document.getElementById('previousWorkoutsTab');
var assignedWorkoutsTab = document.getElementById('assignedWorkoutsTab');


membersTab.addEventListener("click", loadMembers);
coachesTab.addEventListener("click", loadCoaches);
bartendersTab.addEventListener("click", loadBartenders);
machinesTab.addEventListener("click", loadMachines);
menuTab.addEventListener("click", loadMenuItems);
adsTab.addEventListener("click", loadAds);
messagesTab.addEventListener("click", loadmessages);
workoutsTab.addEventListener("click", function(){
    currentWorkoutsTab.click();
});
movesTab.addEventListener("click", loadMoves);
currentWorkoutsTab.addEventListener("click", loadCurrentWorkouts);
previousWorkoutsTab.addEventListener("click", loadPreviousWorkouts);
assignedWorkoutsTab.addEventListener("click", loadAssignedWorkouts);

var currentModal = "modal-signup";

const membersTable = document.querySelector('#membersTable');
const coachesTable = document.querySelector('#coachesTable');
const bartendersTable = document.querySelector('#bartendersTable');
const movesTable = document.querySelector('#movesTable');
const machinesTable = document.querySelector('#machinesTable');
const menuItemsTable = document.querySelector('#menuItemsTable');
const adsTable = document.querySelector('#adsTable');
const messagesTable = document.querySelector('#messagesTable');
const currentWorkoutsTable = document.querySelector('#currentWorkoutsTable');
const previousWorkoutsTable = document.querySelector('#previousWorkoutsTable');
const assignedWorkoutsTable = document.querySelector('#assignedWorkoutsTable');


var isMember = "member";
var editAction = "https://gym-senior-2020.000webhostapp.com/Admin%20Actions/editMemberAction.php";
var deleteAction = "https://gym-senior-2020.000webhostapp.com/Admin%20Actions/deleteAccountAction.php";
var editImageAction = "https://gym-senior-2020.000webhostapp.com/Admin%20Actions/editImageAction.php";



//Add member action
const addMemberForm = document.querySelector('#addMemberForm');
addMemberForm.addEventListener('submit',(e) => 
{
    e.preventDefault();

    //get the info
    const email = addMemberForm['email'].value;
    const password = addMemberForm['password'].value;
    var fName = addMemberForm['first_name'].value;
    var lName = addMemberForm['last_name'].value;
    var phoneNumber = addMemberForm['countryCodeMember'].value + ' ' + addMemberForm['telephoneMember'].value;
    var address = addMemberForm['address'].value;

    var selectedPlan = 0;
    var plan = document.getElementsByName('plan');
    for(var i = 0; i < plan.length; i++){
        if(plan[i].checked){
            selectedPlan = plan[i].value;
            break;
        }
    }

    const addUser = functions.httpsCallable('addUser');
    data = {
        email: email,
        password: password
    }
    
    addUser(data).then(result => {
        var newPackage = JSON.stringify({
            key:key,
            email:email,
            password:password,
            firstName:fName,
            lastName:lName,
            phoneNumber:phoneNumber,
            address:address,
            sibscriptionPlan:selectedPlan,
            img:'placeholder.jpeg',
            token: result.data.uid,
            });

            console.log(newPackage);
            var xhr = new XMLHttpRequest();
            xhr.open('POST','https://gym-senior-2020.000webhostapp.com/Admin%20Actions/addMemberAction.php',true);
            xhr.setRequestHeader('Content-type','application/x-www-form-urlencoded');
            xhr.send("jsonSignup=" + newPackage);

            xhr.onload = function()
            {
                if(this.status === 200)
                {   
                    const modal = document.getElementById(currentModal);
                    M.Modal.getInstance(modal).close();
                    addMemberForm.reset();
                    addMemberForm.querySelector('.error').innerHTML = '';

                    if(fName == "") fName = "empty";
                    if(lName == "") lName = "empty";
                    if(phoneNumber == "") phoneNumber = "empty";
                    if(address == "") address = "empty";
                    
                    return db.collection('users').doc(result.data.uid).set
                    ({
                        fName: fName,
                        lName: lName,
                        phoneNumber: phoneNumber,
                        address: address,
                        subscriptionPlan: selectedPlan,
                        img: 'Member Images/placeholder.jpeg'
                    }).then(() => {
                        loadMembers();
                    });
                }
            }

        
    }).catch(error => 
    {
        addMemberForm.querySelector('.error').innerHTML = error.message;
    });

});

const addCoachForm = document.querySelector('#addCoachForm');
addCoachForm.addEventListener('submit',(e) => 
{
    e.preventDefault();

    //get the info
    const email = addCoachForm['email'].value;
    const password = addCoachForm['password'].value;
    var fName = addCoachForm['first_name'].value;
    var lName = addCoachForm['last_name'].value;
    var phoneNumber = addCoachForm['countryCodeCoach'].value + ' ' + addCoachForm['telephoneCoach'].value;
    var address = addCoachForm['address'].value;
    var salary = addCoachForm['salaryCoach'].value;
    var description = addCoachForm['description'].value;
    var expertise = addCoachForm['expertise'].value;


    const addUser = functions.httpsCallable('addUser');
    data = {
        email: email,
        password: password
    }

    addUser(data).then(result => {
        //Making the user a coach
        const addCoachRole = functions.httpsCallable('addCoachRole');
        addCoachRole({email:email}).then(result =>
        {
            console.log(result);
        })

        var newPackage = JSON.stringify({
            key:key,
            email:email,
            password:password,
            firstName:fName,
            lastName:lName,
            phoneNumber:phoneNumber,
            address:address,
            expertise:expertise,
            description:description,
            img:'placeholder.jpeg',
            salary:salary,
            token: result.data.uid
            });
    
        console.log(newPackage);
        var xhr = new XMLHttpRequest();
        xhr.open('POST','https://gym-senior-2020.000webhostapp.com/Admin%20Actions/addCoachAction.php',true);
        xhr.setRequestHeader('Content-type','application/x-www-form-urlencoded');
        xhr.send("jsonSignup=" + newPackage);
    
        xhr.onload = function()
        {
            if(this.status === 200)
            {   
                const modal = document.getElementById(currentModal);
                M.Modal.getInstance(modal).close();
                addCoachForm.reset();
                addCoachForm.querySelector('.error').innerHTML = '';
                loadCoaches();
            }
        }
        
    }).catch(error => 
    {
        addCoachForm.querySelector('.error').innerHTML = error.message;
    });
});

const addBartenderForm = document.querySelector('#addBartenderForm');
addBartenderForm.addEventListener('submit',(e) => 
{
    e.preventDefault();

    //get the info
    const email = addBartenderForm['email'].value;
    const password = addBartenderForm['password'].value;
    var fName = addBartenderForm['first_name'].value;
    var lName = addBartenderForm['last_name'].value;
    var phoneNumber = addBartenderForm['countryCodeBartender'].value + ' ' + addBartenderForm['telephoneBartender'].value;
    var address = addBartenderForm['address'].value;
    var salary = addBartenderForm['salaryBartender'].value;

    const addUser = functions.httpsCallable('addUser');
    data = {
        email: email,
        password: password
    }

    addUser(data).then(result => {
        const addBartenderRole = functions.httpsCallable('addBartenderRole');
        addBartenderRole({email:email}).then(result =>
        {
            console.log(result);
        })

        var newPackage = JSON.stringify({
            key:key,
            email:email,
            password:password,
            firstName:fName,
            lastName:lName,
            phoneNumber:phoneNumber,
            address:address,
            img:'placeholder.jpeg',
            salary:salary,
            token: result.data.uid
            });
    
        console.log(newPackage);
        var xhr = new XMLHttpRequest();
        xhr.open('POST','https://gym-senior-2020.000webhostapp.com/Admin%20Actions/addBartenderAction.php',true);
        xhr.setRequestHeader('Content-type','application/x-www-form-urlencoded');
        xhr.send("jsonSignup=" + newPackage);
    
        xhr.onload = function()
        {
            if(this.status === 200)
            {   
                const modal = document.getElementById(currentModal);
                M.Modal.getInstance(modal).close();
                addBartenderForm.reset();
                addBartenderForm.querySelector('.error').innerHTML = '';
                loadBartenders();
            }
        }
    
    });
});

const addMachineForm = document.querySelector('#addMachineForm');
addMachineForm.addEventListener('submit',(e) => 
{
    e.preventDefault();

    //get the info
    const name = addMachineForm['name'].value;
    const code = addMachineForm['code'].value;
    var floor = addMachineForm['floor'].value;
    var section = addMachineForm['section'].value;

    var newPackage = JSON.stringify({
        key:key,
        name:name,
        code:code,
        floor:floor,
        section:section,
        img:'placeholder.jpeg'
        });

    console.log(newPackage);
    var xhr = new XMLHttpRequest();
    xhr.open('POST','https://gym-senior-2020.000webhostapp.com/Admin%20Actions/addMachineAction.php',true);
    xhr.setRequestHeader('Content-type','application/x-www-form-urlencoded');
    xhr.send("jsonSignup=" + newPackage);

    xhr.onload = function()
    {
        if(this.status === 200)
        {   
            const modal = document.getElementById(currentModal);
            M.Modal.getInstance(modal).close();
            addMachineForm.reset();
            addMachineForm.querySelector('.error').innerHTML = '';
            loadMachines();
        }
    }
});

const addMenuItemForm = document.querySelector('#addMenuItemForm');
addMenuItemForm.addEventListener('submit',(e) => 
{
    e.preventDefault();

    //get the info
    const name = addMenuItemForm['name'].value;
    const calories = addMenuItemForm['calories'].value;
    const fats = addMenuItemForm['fats'].value;
    const protein = addMenuItemForm['protein'].value;
    const carbohydrates = addMenuItemForm['carbohydrates'].value;
    const sugar = addMenuItemForm['sugar'].value;
    const time = addMenuItemForm['prepTime'].value;
    const type = addMenuItemForm['type'].value;
    const price = addMenuItemForm['price'].value;
    var available = addMenuItemForm['available'].value;
    var description = addMenuItemForm['description'].value;

    var newPackage = JSON.stringify({
        key:key,
        name:name,
        calories:calories,
        fats:fats,
        protein:protein,
        carbohydrates:carbohydrates,
        sugar:sugar,
        time:time,
        type:type,
        price:price,
        available:available,
        description:description,
        img:'placeholder.jpeg'
        });

    console.log(newPackage);
    var xhr = new XMLHttpRequest();
    xhr.open('POST','https://gym-senior-2020.000webhostapp.com/Admin%20Actions/addMenuItemAction.php',true);
    xhr.setRequestHeader('Content-type','application/x-www-form-urlencoded');
    xhr.send("jsonSignup=" + newPackage);

    xhr.onload = function()
    {
        if(this.status === 200)
        {   
            const modal = document.getElementById(currentModal);
            M.Modal.getInstance(modal).close();
            addMenuItemForm.reset();
            addMenuItemForm.querySelector('.error').innerHTML = '';
            loadMenuItems();
        }
    }
});

const addAdForm = document.querySelector('#addAdform');
addAdForm.addEventListener('submit',(e) => 
{
    e.preventDefault();

    //get the info
    const email = addAdForm['email'].value;
    var name = addAdForm['trainerName'].value;
    var title = addAdForm['title'].value;
    var trainerDescription = addAdForm['trainerDescription'].value;
    var phoneNumber = addAdForm['countryCodeTrainer'].value + ' ' + addAdForm['telephoneTrainer'].value;

        var newPackage = JSON.stringify({
            key:key,
            email:email,
            title:title,
            trainerDescription:trainerDescription,
            phoneNumber:phoneNumber,
            name:name,
            img:'placeholder.jpeg'
            });
    
        console.log(newPackage);
        var xhr = new XMLHttpRequest();
        xhr.open('POST','https://gym-senior-2020.000webhostapp.com/Admin%20Actions/AddAdAction.php',true);
        xhr.setRequestHeader('Content-type','application/x-www-form-urlencoded');
        xhr.send("jsonSignup=" + newPackage);
    
        xhr.onload = function()
        {
            if(this.status === 200)
            {   
                const modal = document.getElementById(currentModal);
                M.Modal.getInstance(modal).close();
                addAdForm.reset();
                loadAds();
            }
        }
});

const addMoveForm = document.querySelector('#addMoveForm');
addMoveForm.addEventListener('submit',(e) => 
{
    e.preventDefault();

    //get the info
    const name = addMoveForm['moveName'].value;
    const difficulty = addMoveForm['moveDifficulty'].value;
    const muscleGroup1 = addMoveForm['muscleGroup1'].value;
    const machineID = addMoveForm['machineID'].value;
    const description = addMoveForm['moveDescription'].value;

    var newPackage = JSON.stringify({
        key:key,
        name:name,
        difficulty:difficulty,
        muscleGroup1:muscleGroup1,
        machineID:machineID,
        description:description,
        img1:'placeholder.jpeg',
        img2:'placeholder.jpeg',
        gif:'placeholder.jpeg'
        });

    console.log(newPackage);
    var xhr = new XMLHttpRequest();
    xhr.open('POST','https://gym-senior-2020.000webhostapp.com/Admin%20Actions/addMoveAction.php',true);
    xhr.setRequestHeader('Content-type','application/x-www-form-urlencoded');
    xhr.send("jsonSignup=" + newPackage);

    xhr.onload = function()
    {
        if(this.status === 200)
        {   
            const modal = document.getElementById(currentModal);
            M.Modal.getInstance(modal).close();
            addMoveForm.reset();
            // addMoveForm.querySelector('.error').innerHTML = '';
            loadMoves();
        }
    }
});

//Edit Images, function is connected with loadTables.js
const realButton = document.getElementById("profileImage");
function imageUpload()
{
    realButton.id = this.id;
    realButton.click();
}

//AJAX Upload Image
realButton.addEventListener('change', function()
{
    userID = this.id;
    console.log(userID);
    const xhr = new XMLHttpRequest();
    const formData = new FormData();
    for(const file of realButton.files)
    {
        formData.append("profileImage", file);
    }
    formData.append("userID", userID);
    formData.append("accountType", isMember);

    xhr.open("post", editImageAction);
    xhr.send(formData);
    xhr.onload = function()
    {   
        if(this.status == 200)
        {
            var response;
            try { response = JSON.parse(this.responseText); } 
            catch(e){ console.log(e.message); }

            switch(isMember) {
                case "member":
                    db.collection('users').doc(response.uid).update
                    ({
                        img: response.img
                    }).then(() =>
                    {      
                        M.toast
                        ({
                            html: "Image Uploaded!",
                            displayLength: 2000,
                            classes: "purple"
                        });
                        loadMembers();       
                    }); 
                    break;
                case "coach":
                    loadCoaches();
                    break;
                case "bartender":
                    loadBartenders();
                    break;
                case "machine":
                    loadMachines();
                    break;
                case "menuItem":
                    loadMenuItems();
                    break;
                case "ad":
                    loadAds();
                    break;
                case "move":
                    loadMoves();
                    break;
            }
        }
    }
})

//Preparing all the modals
$(document).ready(function(){
    $('.modal').modal();
});

//Opening the proper modal
function triggerModal(){
    var instance = M.Modal.getInstance(document.getElementById(currentModal));
    instance.open();
}

//Receiving the ping from main.js to open the modal
const { ipcRenderer } = require('electron');
require('electron').ipcRenderer.on('newRecord', () => {
    triggerModal();
})

require('electron').ipcRenderer.on('reloadTable', () => {
    switch(isMember){
        case "member":
            loadMembers();
            break;
        case "coach":
            loadCoaches();
            break;
        case "bartender":
            loadBartenders();
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
        case "ad":
            loadAds();
            break;
        case "move":
            loadMoves();
            break;
    }
})

//Character counter initilizing
$(document).ready(function() {
    $('textarea#address').characterCounter();
    $('textarea#description').characterCounter();
    $('textarea#moveDescription').characterCounter();
    $('textarea#trainerDescription').characterCounter();

});


//Populate the machine list in the moves modal
populateMachines(addMoveForm['machineID']);
function populateMachines(machines){
    var machine = null;

    var xhr = new XMLHttpRequest();
    xhr.open('POST','https://gym-senior-2020.000webhostapp.com/Admin%20Actions/returnMachinesAction.php',true);
    xhr.setRequestHeader('Content-type','application/x-www-form-urlencoded');
    xhr.send("key="+key);

    xhr.onload = function()
    {   
        if(this.status == 200)
        {
            var arr = null;
            try {            
                arr = JSON.parse(this.responseText);
            } catch(e) { console.log(e.message); return;}
            // console.log(arr);
        }

        var id = arr[0].id;
        var Name = arr[0].Name; 

        let defaultOption = document.createElement('option');
        defaultOption.text = id + " - " + Name;
        defaultOption.value = id;
        machines.add(defaultOption);
        machines.selectedIndex = 0;

        if(arr != null){
            for(var i = 1; i < Object.keys(arr).length; i++)
            {
                var id = arr[i].id;
                var Name = arr[i].Name; 

                option = document.createElement("option");
                option.text = id + " - " + Name;
                option.value = id;
                machines.add(option);
     
            }
        }
        $('select').formSelect();
    }
}

//Populating the country selectors
populateCountry(document.getElementById('countryCodeMember'));
populateCountry(document.getElementById('countryCodeCoach'));
populateCountry(document.getElementById('countryCodeBartender'));
populateCountry(document.getElementById('countryCodeTrainer'));

function populateCountry(countryCode){
    $.getJSON('js/countries.json', function(data)
    {
        var country = null;

        let defaultOption = document.createElement('option');
        defaultOption.text = 'LB (961)';
        defaultOption.value = '(961)';

        countryCode.add(defaultOption);
        countryCode.selectedIndex = 0;

    for( var i = 0; i < data.length; i++ )
    { 
        option = document.createElement("option");
        option.style.color = ''
        option.text = data[i].code + " " + data[i].dialCode;
        option.value = data[i].dialCode;
        countryCode.add(option);
    } 

$('select').formSelect();
});
}





//Adding the admin
// const addAdminRole = functions.httpsCallable('addAdminRole');
// addAdminRole({email:"admin@admin.admin"}).then(result =>
// {
//     console.log(result);
// })


//Only allow numbers in telephone input
function setInputFilter(textbox, inputFilter) {
    ["input", "keydown", "keyup", "mousedown", "mouseup", "select", "contextmenu", "drop"].forEach(function(event) {
      textbox.addEventListener(event, function() {
        if (inputFilter(this.value)) {
          this.oldValue = this.value;
          this.oldSelectionStart = this.selectionStart;
          this.oldSelectionEnd = this.selectionEnd;
        } else if (this.hasOwnProperty("oldValue")) {
          this.value = this.oldValue;
          this.setSelectionRange(this.oldSelectionStart, this.oldSelectionEnd);
        } else {
          this.value = "";
        }
      });
    });
  }

setInputFilter(document.getElementById("telephoneMember"), function(value) {
return /^-?\d*$/.test(value); });
setInputFilter(document.getElementById("telephoneCoach"), function(value) {
    return /^-?\d*$/.test(value); });
setInputFilter(document.getElementById("telephoneBartender"), function(value) {
    return /^-?\d*$/.test(value); });
setInputFilter(document.getElementById("salaryCoach"), function(value) {
    return /^-?\d*$/.test(value); });
setInputFilter(document.getElementById("salaryBartender"), function(value) {
    return /^-?\d*$/.test(value); });
    