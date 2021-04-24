const loggedOutLinks = document.querySelectorAll('.logged-out');
const loggedInLinks = document.querySelectorAll('.logged-in');
const accountImg = document.querySelectorAll('#profile-image, #profile-image-side, #profile-image-account');

let currentUid = "";

auth.onAuthStateChanged(user => 
{
    if(user)
    {        currentUid = user.uid;
    } else {
        // setupUi();
        currentUid = "";
    }
});


const setupUi = (user) =>
{
    if(user)
    {
        db.collection('users').doc(user.uid).get().then(doc =>
        {
            var imgURL = doc.data().img;
            var Name = doc.data().firstName + " " + doc.data().lastName;
            accountImg.forEach(profileImg => 
            {
                profileImg.src = imgURL;
            });
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

    var items = document.querySelectorAll('.collapsible');
    M.Collapsible.init(items);
})
  

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


$('.dropdown-trigger').dropdown(
    {
        hover:true,
        coverTrigger:false
    }
);

//login
const loginForm = document.querySelector('#loginForm');
loginForm.addEventListener('submit', (e) => 
{
    e.preventDefault();

    const email = loginForm['loginEmail'].value;
    const password = loginForm['loginPassword'].value;

    auth.signInWithEmailAndPassword(email, password).then(cred => 
    {
        //close modal
        const modal = document.querySelector('#modal-login');
        M.Modal.getInstance(modal).close();
        loginForm.reset();
        loginForm.querySelector('.error').innerHTML = '';

    }).catch(error => 
    {
        loginForm.querySelector('.error').innerHTML = error.message;
    });
});



    








             