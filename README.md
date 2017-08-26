# _Online Cancer API_
####  _This application was written with the help of the IntelliJ and Java. The API was created with the help of SQL, SPARK, and POSTMAN.  Published on 08/25/2017._
#### By _**Kimberly Lu**_

## Description
Welcome to this application. Here you will be able to research information regarding cancer as reflected in different patients.  If you are an administrator, you can add cancer types and patient statics.  You can also update and delete entry regarding the patient information and/or the cancers that affect that individual.

![alt text] (images/postman.png "Postman to create API")

## Development Specifications
| Behavior      | Example Input         | Example Output        |
| ------------- | ------------- | ------------- |
| Create a cancer type | sarcoma  |     sarcoma        |
| add a description to the cancer type  |      "cancer of the connective tissue"  | "cancer of the connective tissue"|
| add a patient to the cancer type| "Ann"| "Ann", "female", "45", "breast cancer early stage"|  
| add more than one patient | "Ann", "Bob"| "Ann", "Bob" |
| update cancer type | "melanoma"| "melanoma"|
|delete an individual cancer type |delete|" " |
|show the details of the cancer type|"sarcoma", "cancer of the connective tissue"| "sarcoma", "cancer of the connective tissue"|
|show details of the patient|"Ann, "female", "45", "breast cancer early stage"|"Ann, "female", "45", "breast cancer early stage"|
update patient info|"Annette"|"Annette"|
|delete patient from database|delete|" "|

## Setup/Installation Requirements
_Download the following project from the gitHub by tapping "Download" or using 'git clone' from the terminal_

## Support and contact details
_For any concerns or questions email to: klu@gmail.com_

### License
MIT license & Copyright (c) 2017 **_Kimberly Lu_**
