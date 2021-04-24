
//listen for auth
auth.onAuthStateChanged(user => 
    {
        if(user)
        {   
            console.log(user.emailVerified);
            if(user.emailVerified)
            {
                console.log(user.emailVerified);
                setupUi(user);
            }
        } else {
            setupUi();
        }
    });
    
//signup
const signupForm = document.querySelector('#signupForm');
signupForm.addEventListener('submit',(e) => 
{
    e.preventDefault();

    //get the info
    const email = signupForm['email'].value;
    const password = signupForm['password'].value;
    var selectedPlan;

    //signing up the user
    auth.createUserWithEmailAndPassword(email, password).then( cred =>
    {
        var plan = document.getElementsByName('plan');
        for(var i = 0; i < plan.length; i++){
            if(plan[i].checked){
                selectedPlan = plan[i].value;
                break;
            }
        }


        return db.collection('users').doc(cred.user.uid).set(
            {
                fName: signupForm['first_name'].value,
                lName: signupForm['last_name'].value,
                phoneNumber: signupForm['countryCode'].value + ' ' + signupForm['telephone'].value,
                address: signupForm['address'].value,
                subscriptionPlan: selectedPlan,
                img: 'Member Images/placeholder.jpeg'
            }
        );

    }).then(() => 
    {
        auth.currentUser.sendEmailVerification().then(function() {
        // Email sent.
        }).catch(function(error) {
        // An error happened.
        });

        var newPackage = JSON.stringify({
                                        email:signupForm['email'].value,
                                        password:signupForm['password'].value,
                                        firstName:signupForm['first_name'].value,
                                        lastName:signupForm['last_name'].value,
                                        phoneNumber:signupForm['countryCode'].value + ' ' + signupForm['telephone'].value,
                                        address:signupForm['address'].value,
                                        sibscriptionPlan:selectedPlan,
                                        img:'placeholder.jpeg',
                                        token: auth.currentUser.uid,
                                        });

        console.log(newPackage);
        var xhr = new XMLHttpRequest();
        xhr.open('POST','signupAction.php',true);
        xhr.setRequestHeader('Content-type','application/x-www-form-urlencoded');
        xhr.send("jsonSignup=" + newPackage);
        
        xhr.onload = function()
        {
            if(this.status === 200)
            {   
                const modal = document.querySelector('#modal-signup');
                signupForm.querySelector('.error').innerHTML = 'A verification email has been sent to you, follow the instructions to activate your account.';
                auth.signOut();
            }
        }

    }).catch(error => 
    {
        signupForm.querySelector('.error').innerHTML = error.message;
    });
});

//logout
const logoutOptions = document.querySelectorAll("#logout, #logoutSide");
logoutOptions.forEach(node => {
    node.addEventListener('click', (e) => 
    {
        e.preventDefault();
        auth.signOut();
        location.replace("index.html");

    })});

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
        if(!auth.currentUser.emailVerified)
        {
            console.log(auth.currentUser.emailVerified);
            loginForm.querySelector('.error').innerHTML = "This account is not verified. " + "<span id = \"resendVerification\" style=\"color:#3366BB; cursor:pointer; \">Resend Verification Email</span>" ;
            var tempUser = auth.currentUser;
            document.getElementById("resendVerification").addEventListener("click", function()
            {
                tempUser.sendEmailVerification().then(function() {

                    }).catch(function(error) {
                        console.log(error.message);
                    });
            })
            auth.signOut();
        } else {
        const modal = document.querySelector('#modal-login');
        M.Modal.getInstance(modal).close();
        loginForm.reset();
        loginForm.querySelector('.error').innerHTML = '';
        }
    }).catch(error => 
    {
        loginForm.querySelector('.error').innerHTML = error.message;
        });
    });
    
    
const forgotPasswordForm = document.querySelector('#forgotPasswordForm');
forgotPasswordForm.addEventListener('submit',(e) => 
{
    e.preventDefault();

    //get the info
    const email = forgotPasswordForm['emailReset'].value;

    var xhr = new XMLHttpRequest();
    xhr.open('POST','passwordResetRequestAction.php',true);
    xhr.setRequestHeader('Content-type','application/x-www-form-urlencoded');
    xhr.send("email="+email);
    
    xhr.onload = function()
    {
        if(this.status === 200)
        {   
            let responseMessage = document.getElementById("responseMessage");
            if(this.responseText == "success")
            {
                responseMessage.textContent = "Success! You will receive an email within the next 10 minutes."
                responseMessage.style.color = "green";
            } else {
                responseMessage.textContent = this.responseText;
                responseMessage.style.color = "red";
            }
        }
    }
})

    
    
    