const loggedOutLinks = document.querySelectorAll('.logged-out');
const loggedInLinks = document.querySelectorAll('.logged-in');
const memberName = document.querySelector('#memberName');
const accountImg = document.querySelectorAll('#profile-image, #profile-image-side, #profile-image-account');
const profileForm = document.querySelector('#profileForm');
const saveButton = document.getElementById('saveButton');

let countryCode= document.getElementById('countryCode');
countryCodeValue = "(975)";

let currentUid = "";

auth.onAuthStateChanged(user => 
{
    if(user)
    {
        if(user.emailVerified)
        {
            loadWorkouts(user.uid);
            currentUid = user.uid;
        } else { 
            currentUid = "";
            location.replace("index.html");
        }
    } else {
        currentUid = "";
        location.replace("index.html");
        // setupUi();
    }
});


$.getJSON('js/countries.json', function(data)
{
    var country = null;

    let defaultOption = document.createElement('option');
    defaultOption.text = 'LB (961)';
    defaultOption.value = '(961)';

    countryCode.add(defaultOption);

    for( var i = 0; i < data.length; i++ )
    { 
        option = document.createElement("option");
        option.style.color = ''
        option.text = data[i].code + " " + data[i].dialCode;
        option.value = data[i].dialCode;
        countryCode.add(option);
    } 
            
});

const setupUi = (user) =>
{
    if(user)
    {
        db.collection('users').doc(user.uid).get().then(doc =>
        {
            const fName = profileForm['first_name'];
            const lName = profileForm['last_name'];
            const email = profileForm['email'];
            const countryCode = profileForm['countryCode'];
            const telephone = profileForm['telephone'];
            const address = profileForm['address'];
            const plan = document.getElementById('plan');
            const expires = profileForm['expire'];

            const editFirstName = document.getElementById('editFirstName');
            const editLastName = document.getElementById('editLastName');
            const editTelephone = document.getElementById('editTelephone');
            const editAddress = document.getElementById('editAddress');

            var imgURL = doc.data().img;
            var Name = doc.data().fName + " " + doc.data().lName;
            accountImg.forEach(profileImg => 
            {
                profileImg.src = imgURL;
            });

            memberName.innerHTML = Name;
            fName.value = doc.data().fName;
            lName.value = doc.data().lName;
            email.value = user.email;
            telephone.value = doc.data().phoneNumber.split(" ")[1];
            countryCodeValue = doc.data().phoneNumber.split(" ")[0];
            address.innerHTML = doc.data().address;
            if(doc.data().subscriptionPlan === 1)
            {
                plan.innerHTML = "45$/month"
            } else if (doc.data().subscriptionPlan == 3)
            {
                plan.innerHTML = "120$/3Months"
            } else {
                plan.innerHTML = "400$/12Months"
            }

            editFirstName.addEventListener("click", function()
            {
                fName.removeAttribute("disabled");
                saveButton.removeAttribute("hidden");
                fName.focus();
            })

            editLastName.addEventListener("click", function()
            {
                lName.removeAttribute("disabled");
                saveButton.removeAttribute("hidden");
                lName.focus();
            })

            editTelephone.addEventListener("click", function()
            {
                countryCode.removeAttribute("disabled");
                telephone.removeAttribute("disabled");
                saveButton.removeAttribute("hidden");

                telephone.focus();

                $('select').formSelect();
            })

            editAddress.addEventListener("click", function()
            {
                address.removeAttribute("disabled");
                saveButton.removeAttribute("hidden");
                address.focus();
            })
            

            countryCode.value = countryCodeValue;
            $('select').formSelect();

            profileForm.addEventListener('submit',(e) => 
            {
                e.preventDefault();

                fName.setAttribute("disabled", true);
                lName.setAttribute("disabled", true);
                countryCode.setAttribute("disabled", true);
                telephone.setAttribute("disabled", true);
                address.setAttribute("disabled", true);
                saveButton.setAttribute("hidden", true);

                var newPackage = JSON.stringify({
                    firstName:fName.value,
                    lastName:lName.value,
                    phoneNumber:countryCode.value + ' ' + telephone.value,
                    address:address.value,
                    token: auth.currentUser.uid,
                });

                var xhr = new XMLHttpRequest();
                xhr.open('POST','Editing Profile Actions/editProfileAction.php',true);
                xhr.setRequestHeader('Content-type','application/x-www-form-urlencoded');
                xhr.send("jsonEdit=" + newPackage);

                xhr.onload = function()
                {  
                    if(this.status == 200)
                    {
                        if(true)
                        {
                            db.collection('users').doc(auth.currentUser.uid).update
                            ({
                                firstName:fName.value,
                                lastName:lName.value,
                                phoneNumber:countryCode.value + ' ' + telephone.value,
                                address:address.value
                            }).then(() =>
                            {
                                M.toast
                                ({
                                    html: "Profile Updated",
                                    displayLength: 2000,
                                    classes: "purple"
                                });
                            }) 
                        } else 
                        {
                            M.toast({html: this.responseText})
                        }
                    }
                }
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



//Character counter
$(document).ready(function() {
$('textarea#address').characterCounter();
$('textarea#messageContent').characterCounter();
});

const realButton = document.getElementById("profileImage");
const imageForm = document.getElementById("uploadForm");
const imageUpload = document.getElementById("profile-image-account");

imageUpload.addEventListener('click', function()
{
    realButton.click();
})


//AJAX Upload Image
realButton.addEventListener('change', function()
{
    const xhr = new XMLHttpRequest();
    const formData = new FormData();
    for(const file of realButton.files)
    {
        formData.append("profileImage", file);
    }
    formData.append("userUID", auth.currentUser.uid);

    xhr.open("post","Editing Profile Actions/uploadImageAction.php");
    xhr.send(formData);

    xhr.onload = function()
        {   
            if(this.status == 200)
            {
                if(true)
                {
                    M.toast
                    ({
                        html: "Image Uploaded!",
                        displayLength: 2000,
                        classes: "purple"
                    });
                    db.collection('users').doc(auth.currentUser.uid).update
                    ({
                        img: this.responseText
                    }).then(() =>
                    {
                        setupUi(auth.currentUser);
                    }) 
                } else 
                {
                    M.toast({html: this.responseText})
                }
            }
        }
})

//load previous workouts


function loadWorkouts(userUid) 
{
    var userUid;

    const workoutsUl = document.getElementById('workoutsList');

    var xhr = new XMLHttpRequest();
    xhr.open('POST','returnPreviousWorkoutsAction.php',true);
    xhr.setRequestHeader('Content-type','application/x-www-form-urlencoded');
    xhr.send("token=" + userUid);
    
    xhr.onload = function()
    {   
        if(this.status == 200)
        {
            var arr = JSON.parse(this.responseText);
            
        }
        // console.log(Object.keys(arr[0]).length);

        // while(universitiesTBody.rows.length > 0)
        // {
        //     universitiesTBody.removeChild(universitiesTBody.firstChild);
        // }

        for(var i = 0; i < Object.keys(arr).length; i++)
        {
            var workoutDate = arr[i].date;
            var moveCount = arr[i].moveCount;   

            let workoutFinish = new Date(workoutDate);
        

            li = document.createElement("li");
            div = document.createElement("div");
            div.className = "collapsible-header grey mb-1 lighten-4";
            div.innerHTML = workoutDate + " <br>    Moves: " + moveCount;
            divBody = document.createElement("div");
            divBody.className = "collapsible-body grey lighten-";
            li.appendChild(div);
            divRow = document.createElement("div");
            divRow.className = "row collapsible-body-inner";
            var img = document.createElement("img");
            var p = document.createElement("p");
            p.className = "my-auto";

            for (var key in arr[i]) {
                if (arr[i].hasOwnProperty(key)) {

                    if(key.includes("moveName"))
                    {
                        var p = document.createElement("p");
                        p.className = "my-auto";
                        p.innerHTML = arr[i][key];  
                        divRow.appendChild(p);
                    }

                    if(key.includes("moveImg"))
                    {
                        
                        divRow = document.createElement("div");
                        divRow.className = "row collapsible-body-inner";
                        var img = document.createElement("img");
                        img.src = "Moves Images/" + arr[i][key];
                        divRow.appendChild(img);
                        divRow.appendChild(p);
                        divBody.appendChild(divRow);
                        li.appendChild(divBody);
                    }
                }
            }
            workoutsUl.appendChild(li);

        }  
    }
 }

 
const contactForm = document.querySelector('#contactForm');
contactForm.addEventListener('submit', function(e)
{
    e.preventDefault();
    const subject = contactForm['subject'];
    const content = contactForm['messageContent'];
    const alert = document.querySelector('.alert');
    if(content.value.length < 15)
    {
        alert.removeAttribute('hidden');
        return false;
    } else {
        alert.setAttribute('hidden', true);
    }

    var newPackage = JSON.stringify({
        subject: subject.value,
        content: content.value,
        token: currentUid,
    });

    var xhr = new XMLHttpRequest();
    xhr.open('POST','contactAction.php',true);
    xhr.setRequestHeader('Content-type','application/x-www-form-urlencoded');
    xhr.send("jsonMessage=" + newPackage);

    xhr.onload = function()
    {  
        if(this.status == 200)
        {
            M.toast({html: this.responseText})
        }
    }
});

$('.dropdown-trigger').dropdown(
    {
        hover:true,
        coverTrigger:false
    }
);



    








             