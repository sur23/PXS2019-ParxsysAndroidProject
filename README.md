Android App for Current Location

#Instructions

1.In Build.gradle add dependency for google:

    implementation 'com.android.volley:volley:1.1.1'
    implementation 'com.google.code.gson:gson:2.8.5'
    implementation 'com.android.support:recyclerview-v7:29.0.0'
3.Add premissions in androidmanifest.xml:

    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    
#This repo contains the following Detail:

// First Activity 
1. Retrieve the information form Employee List Api.

2. Shows the Dropdown List of Emplyoees Name along with Emp_id , Months and Year from 1990 to Current Year.

3. Select all the manadotry infromation from dropdown Employeelist,month,year.

4. Send all the information  to Employee log details activity.

// Second Activity
1. Fetched all the details from First Activity.

2.Shows the full Attendace Report for respective Month along with total_hours,total_present,total_absent.
