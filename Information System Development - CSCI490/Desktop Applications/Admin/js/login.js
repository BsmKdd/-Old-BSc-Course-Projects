var firebaseConfig = 
{
    apiKey: "AIzaSyBWk2mYsYjV3bxSBZj5MKy5Knh-LJapTzg",
    authDomain: "mygym-97497.firebaseapp.com",
    databaseURL: "https://mygym-97497.firebaseio.com",
    projectId: "mygym-97497",
    storageBucket: "mygym-97497.appspot.com",
    messagingSenderId: "1073663096495",
    appId: "1:1073663096495:web:fdb2e76115ae2c7400c65a",
    measurementId: "G-NP8C8WBEYM"
};

// Initialize Firebase
firebase.initializeApp(firebaseConfig);

//auth and firestore references
const auth = firebase.auth();

const loginForm = document.querySelector('#loginForm');
loginForm.addEventListener('submit', (e) => 
{
    e.preventDefault();

    const email = loginForm['loginEmail'].value;
    const password = loginForm['loginPassword'].value;

    auth.setPersistence(firebase.auth.Auth.Persistence.SESSION)
    .then(function() {
    // Existing and future Auth states are now persisted in the current
    // session only. Closing the window would clear any existing state even
    // if a user forgets to sign out.
    // ...
    // New sign-in will be persisted with session persistence.
    return auth.signInWithEmailAndPassword(email, password).then(cred => {
        loginForm.querySelector('.error').innerHTML = '';
    });
    })
    .catch(function(error) {
    // Handle Errors here.
        loginForm.querySelector('.error').innerHTML = error.message;
    });
});

auth.onAuthStateChanged(user => {
    if(user) {
        user.getIdTokenResult().then(idTokenResult => {
            user.admin = idTokenResult.claims.admin;
            if(user.admin)
            {
                document.location.href = 'index.html';
            } else { 
                auth.signOut();
                loginForm.querySelector('.error').innerHTML = 'Not an Admin';
             }
        });
    } else {

    }  
})