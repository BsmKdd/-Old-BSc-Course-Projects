const electron = require('electron');
const path = require('path');
const url = require('url');
const ipc = electron.ipcMain;
// const prompt = require('electron-prompt');
const {app, BrowserWindow, Menu} = electron;

//initializing window
let mainWindow;

//when app is ready...

app.on('ready', function()
{
    //creating a new window
    mainWindow = new BrowserWindow({webPreferences: {
        nodeIntegration: true
    }});
    mainWindow.maximize();
    //loading into the main page (index)
    mainWindow.loadURL(url.format(
        {
            pathname: path.join(__dirname, 'login.html'),
            protocol: 'file:',
            slashes: true
        }
    ))

    //quit app when closed
    mainWindow.on('closed', function()
    {
        app.quit();
    });

    mainWindow.on('closed', () => 
    {
        win = null;
    });

    //Building custom menu from template
    const mainMenu = Menu.buildFromTemplate(menuTemplate);
    //Insert Menu
    Menu.setApplicationMenu(mainMenu);
});

//Creating a menu

const menuTemplate = 
[{
    label:'File',
    submenu:
    [
        {
            label: 'Quit',
            accelerator: process.platform == 'darwin' ? 'Command+Q' : 'Ctrl+Q',
            click()
            {
                app.quit();
            }
        }
    ],
},
{
    label:'Insert',
    submenu:
    [
        {
            label: 'New Record',
            accelerator: process.platform == 'darwin' ? 'Command+Shift+N' : 'Ctrl+Shift+N',
            click()
            {
                mainWindow.webContents.send('ping')
            }
        }
    ],
},
{
    label:'Reload',
    submenu:
    [
        {
            role: 'reload',
        }
    ]
}

];
//If on a mac, add a new object menu
if(process.platform == 'darwin')
{
    menuTemplate.unshift({});
};


//Quit when all windows are closed
app.on('window-all-closed', () =>
{
    if(process.platform !== 'darwin')
    {
        app.quit();
    }
});

//Add dev tools if not in production
// if(process.env.NODE_ENV !== 'production')
// {
//     menuTemplate.push(
//         {
//             label: 'Dev Tools',
//             submenu:
//             [{
//                 label: 'Toggle DevTools',
//                 accelerator: process.platform == 'darwin' ? 'Command+i' : 'Ctrl+i',
//                 click(item, focusedWindow)
//                 {
//                     focusedWindow.toggleDevTools();
//                 }
//             },
//             {
//                 role: 'reload',
//             }]
//         }
//     )
// }