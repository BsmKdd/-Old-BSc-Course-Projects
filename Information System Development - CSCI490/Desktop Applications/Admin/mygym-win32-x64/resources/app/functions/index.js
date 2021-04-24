// const electron = require("electron");
// const ipc = electron.ipcRenderer;
const functions = require('firebase-functions');
const admin = require('firebase-admin');
admin.initializeApp();

exports.addAdminRole = functions.https.onCall((data, context) => 
{
    //get user and add custom claim
    return admin.auth().getUserByEmail(data.email).then(user => 
    {
        return admin.auth().setCustomUserClaims(user.uid,
        {
            admin: true
        });
    }).then(() => 
    {
        return {
            message: `Success! ${data.email} has been made an admin`
        }
    }).catch(err => {return err});
});

exports.addCoachRole = functions.https.onCall((data, context) => 
{
    //get user and add custom claim
    return admin.auth().getUserByEmail(data.email).then(user => 
    {
        return admin.auth().setCustomUserClaims(user.uid,
        {
            coach: true
        });
    }).then(() => 
    {
        return {
            message: `Success! ${data.email} has been made a coach`
        }
    }).catch(err => {return err});
});

exports.addBartenderRole = functions.https.onCall((data, context) => 
{
    //get user and add custom claim
    return admin.auth().getUserByEmail(data.email).then(user => 
    {
        return admin.auth().setCustomUserClaims(user.uid,
        {
            bartender: true
        });
    }).then(() => 
    {
        return {
            message: `Success! ${data.email} has been made a bartender`
        }
    }).catch(err => {return err});
});

exports.activateMembership = functions.https.onCall((data, context) => 
{
    //get user and add custom claim
    return admin.auth().getUserByEmail(data.email).then(user => 
    {
        return admin.auth().setCustomUserClaims(user.uid,
        {
            activeMember: true
        });
    }).then(() => 
    {
        return {
            message: `Success! ${data.email} has been activated`
        }
    }).catch(err => {return err});
});

exports.deactivateMembership = functions.https.onCall((data, context) => 
{
    //get user and add custom claim
    return admin.auth().getUserByEmail(data.email).then(user => 
    {
        return admin.auth().setCustomUserClaims(user.uid, null);
    }).then(() => 
    {
        return {
            message: `Success! ${data.email} has been deactivated`
        }
    }).catch(err => {return err});
});

exports.deleteUser = functions.https.onCall((data) => {
    return admin.auth().deleteUser(data)
    .then(function() {
        return {
            message: `Success! ${data} has been deleted`
        }
    })
    .catch(function(error) {
      console.log('Error deleting user:', error);
    });
})

exports.updateUser = functions.https.onCall((data, context) => {
    return admin.auth().updateUser(data.uid, {
        email: data.email,
        password: data.password,
    })
    .then(function(userRecord) {
    // See the UserRecord reference doc for the contents of userRecord.
    return {
        message: `Success! user updated ` + userRecord.toJSON
    }
    })
    .catch(function(error) {
    console.log('Error updating user:', error);
    });    
})

exports.addUser = functions.https.onCall((data, context) => {
    return admin.auth().createUser({
        email: data.email,
        emailVerified: false,
        password: data.password,
      })
    .then(function(userRecord) {
        // See the UserRecord reference doc for the contents of userRecord.
        return {
            message: `Success! Create new user: ` + userRecord.uid,
            uid: userRecord.uid
        }
    })
    .catch(function(error) {
        console.log('Error creating new user:', error);
    });    
})

exports.sendNotification = functions.firestore
.document("users/{user_id}/Notifications/{notification_id}").onWrite((change, context) => {
    const user_id = context.params.user_id;
    const notification_id = context.params.notification_id;

    console.log("User ID: " + user_id + " \n Notification ID: " + notification_id);

    return admin.firestore().collection("users").doc(user_id).collection("Notifications").doc(notification_id).get().then(queryResult => {

        const from_user_id = queryResult.data().from;
        const from_message = queryResult.data().message;
        const from_title = queryResult.data().title;
        const to_data = admin.firestore().collection("users").doc(user_id).get();

        return Promise.all([to_data]).then(result => {
            
            const fcm = result[0].data().fcm;

            const payload = {
                notification: {
                    title: from_title,
                    body: from_message,
                    sound: "default"
                }
            };

            return admin.messaging().sendToDevice(fcm, payload).then(result => {
                console.log("Notification Sent!");
            });
        });
    });
});