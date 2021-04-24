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


// function loadCoaches() 
// {

var xhr = new XMLHttpRequest();
xhr.open('GET','returnCoachesAction.php', true);
xhr.setRequestHeader('Content-type','application/x-www-form-urlencoded');
xhr.send();
xhr.onload = function()
{   
    if(this.status == 200)
    {
        var arr = JSON.parse(this.responseText);
    }

    const coaches = document.getElementById("coachesSection");
    for(var i = 0; i < Object.keys(arr).length; i++)
    {
        var expertise = arr[i].expertise;
        var description = arr[i].description;   
        var fName = arr[i].fName;   
        var lName = arr[i].lName;   
        var img = arr[i].img;   

        console.log(fName + " " + lName + " " + expertise + " " + description + " " +img + "\n");        

        div1 = document.createElement("div");
        div1.className = "col-sm-6 col-lg-3 col-12";

        div2 = document.createElement("div");
        div2.className = "card";
    
        div3 = document.createElement("div");
        div3.className = "card-image";

        image = document.createElement("img");
        image.src = "Coach Images/" + img;

        span = document.createElement("span");
        span.className = "card-title mb-1 mt-1";
        span.innerHTML = fName + " " +lName;

        div4 = document.createElement("div");
        div4.className = "card-content";

        div5 = document.createElement("div");
        div5.className = "card-action";
        div5.innerHTML = expertise;

        p = document.createElement("p");
        p.innerHTML = description;

        div4.appendChild(p);
        div3.appendChild(image);
        div2.appendChild(div3);
        div2.appendChild(span);
        div2.appendChild(div4);
        div2.appendChild(div5);
        div1.appendChild(div2);

        coaches.appendChild(div1);

        // for (var key in arr[i]) {
        //     if (arr[i].hasOwnProperty(key)) {

        //         if(key.includes("moveName"))
        //         {
        //             var p = document.createElement("p");
        //             p.className = "my-auto";
        //             p.innerHTML = arr[i][key];  
        //             divRow.appendChild(p);
        //         }

        //         if(key.includes("moveImg"))
        //         {
                    
        //             divRow = document.createElement("div");
        //             divRow.className = "row collapsible-body-inner";
        //             var img = document.createElement("img");
        //             img.src = "Moves Images/" + arr[i][key];
        //             divRow.appendChild(img);
        //             divRow.appendChild(p);
        //             divBody.appendChild(divRow);
        //             li.appendChild(divBody);
        //         }
        //     }
        // }
        // workoutsUl.appendChild(li);

    }  
}
//  }


    








             