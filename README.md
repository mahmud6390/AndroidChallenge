# ClientServer communication

The whole idea of this challenge is to use JSON parse with test cases and
render the results of the response in a native Android application.

## Getting Started



### Prerequisities
To run this app
minSdkVersion 15
targetSdkVersion 23

android {
    compileSdkVersion 23
    buildToolsVersion "23.0.3"
 ..

}

for junit test:
dependencies {
    ...
    testCompile 'junit:junit:4.12'
    testCompile 'org.hamcrest:hamcrest-library:1.3'
}
### Using

Step 1: Press "+" to add user input data
Step 2: Put your input data screen and press "Add" button
Step 3: Response comes from server like:offerlist/failed
Step 4: View all offer list at view
Step 5: We may clear data from option menu to reset device id or view.

## Running the tests

Test cases exist at src/test for JVM cases and src/androidtest for android test


### And coding style tests

using java doc for coding style.

private member variable: private String mTitle

/*
* for method naming
*/

## Deployment

Add additional notes about how to deploy this on a live system

## Built With

classpath 'com.android.tools.build:gradle:2.0.0' 

