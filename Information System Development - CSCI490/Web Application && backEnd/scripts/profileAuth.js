//logout
const logoutOptions = document.querySelectorAll("#logout, #logoutSide");
logoutOptions.forEach(node => {
    node.addEventListener('click', (e) => 
    {
        e.preventDefault();
        auth.signOut();
        location.replace("index.html");

    })});


//listen for auth
auth.onAuthStateChanged(user => 
{
    if(user)
    {
        if(!user.emailVerified) auth.signOut();
        else setupUi(user);
    } else {
        setupUi();
    }
});

