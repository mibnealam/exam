Interview tasks and instructions!
Note:

There were two tasks given.
Create two projects in two separate folders, namely, task-01 and task-02 and upload the solution in same repo.
At the end branch look like this,

 exam---
│   README.md
└───task-01
│   │
│   └─── app
│       │   build.gradle 
|       └─── src
│       │   ...
│   │   build.gradle
│   │   settings.gradle
│   
└───task-02
│   │
│   └─── app
│       │   build.gradle 
|       └─── src
│       │   ...
│   │   build.gradle
│   │   settings.gradle
│   


Task: 01
Design an app where user can download a large file from internet.

Features:
It is a one screen app with a single button in the centre that will start the download.
Display a progress widget in the screen to show the download progress/status.
After starting the download user may leave the app. But the download should continue in background.
If user leaves the app, notify user about download progress/status using notification.
If user navigates to the app again, dismiss the notification and show the current download progress/status in the app screen.

Specifications:
Use standard material UI.
Sample file url to download: http://dropbox.sandbox2000.com/intrvw/SampleVideo_1280x720_30mb.mp4


Task: 02
Design an app where a list of users will be displayed in the app coming from a backend server using REST API

Features:
Display a list of users.
Each cell or item will contain 4 information:
First name
Last name
Mobile number
Profile image

Specifications:
Use standard material UI.
List of users can be found using the API, GET: http://dropbox.sandbox2000.com/intrvw/users.json
Each of the user has a photo attribute with an integer ID. You can get the profile picture from this URL, https://randomuser.me/api/portraits/women/{PHOTO_ID}.jpg For exmaple, https://randomuser.me/api/portraits/women/41.jpg
Note that, if the user gender attribute is male use this URL, https://randomuser.me/api/portraits/men/{PHOTO_ID}.jpg
if female then use this URL, https://randomuser.me/api/portraits/women/{PHOTO_ID}.jpg

Sample User JSON data,
{
      "id": 1719844608,
      "firstName": "Johnnie",
      "lastName": "Jonathan",
      "phones": {
        "home": "524-789-3468",
        "mobile": "691-772-7392"
      },
      "email": [
        "krtxlhamd@itc.com",
        "ljyaaojkr@itc.org"
      ],
      "dateOfBirth": "1999-05-19T00:00:00.000Z",
      "registered": true,
      "gender": "female",
      "photo": 41
}