const loggedOutLinks = document.querySelectorAll('.logged-out');
const loggedInLinks = document.querySelectorAll('.logged-in');
const accountImg = document.querySelectorAll('#profile-image, #profile-image-side, #profile-image-account');

const setupUi = (user) =>
{
    if(user)
    {
        db.collection('users').doc(user.uid).get().then(doc =>
            {
                var imgURL = doc.data().img;
                console.log(imgURL);
                accountImg.forEach(profileImg => 
                    {
                        profileImg.src = imgURL;

                    })
            })

        loggedInLinks.forEach(item => item.style.display = '');
        loggedOutLinks.forEach(item => item.style.display = 'none');
    } else 
    {
        loggedInLinks.forEach(item => item.style.display = 'none');
        loggedOutLinks.forEach(item => item.style.display = '');
    }
}

document.addEventListener('DOMContentLoaded', function()
{    
    var modals = document.querySelectorAll('.modal');
    M.Modal.init(modals);
    $("#modal-login").modal({
        onCloseEnd: changeFormsBack
      });
    var items = document.querySelectorAll('.collapsible');
    M.Collapsible.init(items);
});

$('.parallax').parallax();

//Sidenav initialization
$(document).ready(function()
{
    $('.sidenav').sidenav();
})

//image dropdown
$('.dropdown-trigger').dropdown(
    {
        hover:true,
        coverTrigger:false
    }
);



//Character counter
$(document).ready(function() {
$('textarea#address').characterCounter();
});


//Country codes
let countryCode= document.getElementById('countryCode');

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

//Sign up rules
emailReady = false;
passwordReady = false;
passwordConfirmReady = false;

var email = document.getElementById('email');
// email.addEventListener('change', checkEmail);

email.addEventListener('change', mailFormat);
function mailFormat()
{
    var emailVal = email.value;
    var filter = /([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
    if (!filter.test(emailVal)) 
    {
        email.setCustomValidity("Invalid email");
        email.reportValidity();
        emailReady = false;
    } else {
        email.setCustomValidity("");
        email.reportValidity();
        emailReady = true;
    }
};

var password = document.getElementById('password');
password.addEventListener('change', passwordCheck);


function passwordCheck()
{
    password.setCustomValidity("6-20 characters with at least 1 digit, lower and UPPER case letters.");
    var passw = password.value;

    var letter = /[a-z]/;
    var letterUp= /[A-Z]/;
    var number = /[0-9]/;

    if(passw.length < 6 || !letter.test(passw) || !number.test(passw) || !letterUp.test(passw)) 
    {
        password.reportValidity();
        passwordReady = false;

    } else {
        password.setCustomValidity('');
        passwordReady = true;
    }
};


var passwordConfirm = document.getElementById('confirmPassword');
passwordConfirm.addEventListener('change', passwordConfirmCheck);

function passwordConfirmCheck()
{
    passw = password.value;
    passw2 = passwordConfirm.value; 
    if(passw != passw2)
    {	
        passwordConfirm.setCustomValidity("Passwords do not match");
        passwordConfirm.reportValidity();
        passwordConfirmReady = false;
    } else {
        passwordConfirm.setCustomValidity('');
        passwordConfirm.reportValidity();
        passwordConfirmReady = true;
    }
};


function checkForm()
{
    passwordConfirmCheck();
    passwordCheck();
    mailFormat();

    if(emailReady && passwordReady && passwordConfirmReady)
    {
        return true;
    } else {
        return false;
    }
}

let forgotPasswordURL = document.getElementById("forgotPasswordURL");
forgotPasswordURL.addEventListener('click', function()
{
    console.log("OI");
    document.getElementById("forgotPasswordForm").classList.remove("d-none");
    document.getElementById("loginForm").classList.add("d-none");
    return false;
}, false);

function changeFormsBack()
{
    document.getElementById("forgotPasswordForm").classList.add("d-none")
    document.getElementById("forgotPasswordForm").reset();
    document.getElementById("loginForm").classList.remove("d-none");
}

window.onload = function()
{   
    if(window.innerWidth < 700)
    {
        document.getElementById("parallaxHeader").src = "tempImages/smallPhone.jpg";
        document.getElementById("headerTextWrapper").style = "background: #80008059";
    } else if (window.innerWidth < 900) {
        document.getElementById("parallaxHeader").src = "tempImages/phoneBG.jpg";
    } else { 
        document.getElementById("parallaxHeader").src = "tempImages/stockback.jpg";
    }
}