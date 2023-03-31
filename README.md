# CarbonEnergyClient
2023 GDSC solution challeneg Carbon Energy Android Client source code

### How to run the app
#### Option 1: 
Download the most recent release version of apk file in release page, read the instruction on release page.
#### Option 2: 
Clone the repo to your local computer and open with Android Studio, then build your own version app from source code. Everything should able to run at open box.


### Be aware!
The development was finished locally, so the URL for API call is based on local IP address, if you run th apk directly, the app will crash because it cannot connect to server. To fix this, run the CarbonEnergyServer repo first (see me profile page) and then change the BASE_URI to your server's IP address. This problem can be easily fixed by deploy the CarbonEnergyServer to the Google Cloud, and set the BASE_URI to Google Could's IP/domain name.
