var uid;
$(document).ready(function(){
    $('.tabs').tabs();
});

auth.onAuthStateChanged(user => {
    if(user) {
        user.getIdTokenResult().then(idTokenResult => {
            console.log(idTokenResult.claims.admin)
        });
        uid = user.uid;
    } else {

    }  
})

var menuTab = document.getElementById('menuTab');
menuTab.addEventListener("click", loadMenuItems);
var ordersTabButton = document.getElementById("ordersTab");

ordersTabButton.addEventListener("click", function()
{
    ordersTab.classList.add("d-flex");
    
});


var currentModal = "modal-signup";

const menuItemsTable = document.querySelector('#menuItemsTable');



var isMember = "menuItem";
editAction = 'https://gym-senior-2020.000webhostapp.com/Admin%20Actions/editMenuItemAction.php';
deleteAction = 'https://gym-senior-2020.000webhostapp.com/Admin%20Actions/deleteMenuItemAction.php';
editImageAction = "https://gym-senior-2020.000webhostapp.com/Admin%20Actions/editMenuItemImageAction.php";

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
            loadMenuItems();
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
require('electron').ipcRenderer.on('ping', () => {
    triggerModal();
})

//Character counter initilizing
$(document).ready(function() {
    $('textarea#description').characterCounter();
});
    