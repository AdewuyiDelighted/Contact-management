### **Table of Content**

* Introduction
* How to install
* Features
* Endpoint


#### **INTRODUCTION**
This contact management system is a software application designed to efficiently organize and manage contact.
Built using Maven for streamlined development, MySql for flexible data storage, and JUnit for comprehensive API testing. 
This ensures a reliable and efficient system for these Api's below;

##### **FEATURES**

* Register User
* Login User
* Add Contact
* Edit Contact
* Edit User Info
* View A Contact
* View All Contacts in a Category
* View All Contact
* Block Contact
* Unblock Contact
* Delete A Contact
* Delete All Contacts in A Catetory
* Delete Account

##### **HOW TO INSTALL PROGRAM**
HOW TO INSTALL PROGRAM

* Install git
* Click on this link (https://github.com/AdewuyiDelighted/Contact-management)
* Clone the project by following the cloning process
* Install mySql for proper database functions
* Ensure all dependencies in the the above project are well injected in your pom.xml
* Ensure your project is on the right server.port
* Open your Post man Application,paste the accurate url on the given url space

### **ENDPOINTS**

## **POST RegisterRequest**
This Api is used to create a new user,it accept new user details that are saved in the database,
it return successful message when request is accomplished or error message when isnt successful
_Required fields are_:
* Firstname
* Surname
* Email
* PhoneNumber
* Address
* Password
Method:POST
Header: Content-Type:application/json

### **Response 1**

`
stautus code 201 created
Body
{
"data": {
"message": "Account created successfully"
},
"successful": true
}`

### **Response 2**

`
stautus code 400 Bad Request
Body
{
"data": {
"message": "email already exist"
},
"successful": false
}`

### **Response **

`
stautus code 400 Bad Request
Body
{
"data": {
"message": "Password too weak"
},
"successful": false
}`
## **POST LoginRequest**

This Api is enables register user to have access to the application features,
It accepts user email and password,it compares the given details with the data stored in database

Method:POST
Header: Content-Type:application/json

### **Response 1**

`stautus code 202 accepted 
Body
"data": {
"message": "Account login"
},
"successful": true`

### **Response 2**
`
stautus code 400 Bad Request
Body
"data": {
"message": "Invalid detail"
},
"successful": false`


## **POST AddAContact**

This Api does the important function of the application of adding contacts and saving the contacts into database, 
it's require fields are:

* Firstname
* Surname
* Address
* Phone number
* Email
* User Email
* Category Name

Method:POST
Header: Content-Type:application/json


### **Response 1**
`stautus code 200 ok
Body
"data": {
"message": "Contact Added"
},
"successful": true`

### **Response 2**
`
stautus code 400 Bad Request
Body
"data": {
"message": "Invalid detail"
},
"successful": false`

## **POST EditContact**

This Api allows registered users to be able to edit contacts that were added,
it accept all the field of add contact,and the new values that user would like to update,it returns a message
Method:POST
Header: Content-Type:application/json

* formerFirstName
* formerSurname
* newFirstName
* newSurname
* formerPhoneNumber
* email
* userEmail
* categoryName
* address

### **Response 1**
`stautus code 200 ok
Body
"data": {
"message": "Contact Udpatded  to date"
},
"successful": true`

### **Response 2**
`
stautus code 400 Bad Request
Body
"data": {
"message": "Contact doesnt exit"
},
"successful": false`


## **POST EditUserContact**

This Api's enable the user's to be able to edit details that was inputted when the register,
it accept the old fields and new fields
Method:POST
Header: Content-Type:application/json

* formerFirstname
* newFirstname
* formerSurname
* newSurname
* formerEmail
* newEmail
* formerPhoneNumber
* newPhoneNumber
* formerAddress
* newAddress

### **Response 1**
`{
stautus code 200 ok
Body
"data": {
"message": "Your info updated to date"
},
"successful": true
}`

### **Response 2**
`{
stautus code  400 Bad Request
Body
"data": {
"message": "Invalid details"
},
"successful": false
}`

## **GET ViewAContact**
This Api enable the user to be able view a specific contact that was saved,
it accepts user email,surname,firstname.it return the contact with the corresponding details
Method:GET
Header: Content-Type:application/json
Required field:
* UserEmail
* Surname
* FirstName
### **Response 1**

` 
status code 200 ok
"data": {
"contact":{
    "contactId": 1,
    "contactManagementId": 1,
    "surname": "Teller",
    "firstname": "John",
    "categoryName": "school",
    "address": "surulere street",
    "email": "JohnTeller11@gmail.com",
    "phoneNumber": "+234814523657",
    "userEmail": "JudithJoseph@gmail.com",
    "blocked": false,
}
"message": ""
},
"successful": true`

### **Response 2**
`
status code 400 Bad request
body
"data": {
"contact":
"message": "Contact doesnt exist"
},
"successful": false`

## **GET ViewAllContactInCategory**
This Api enable them to be able to view all contacts that have the given tyes of category name
It accept the user email and the name if the category
Method:GET
Header: Content-Type:application/json

Required field
* email
* categoryName



### **Response 1**

  
`
status code 200 ok 
body
"data": {
  "contacts": [
  {
  "contactId": 1,
  "contactManagementId": 1,
  "surname": "Teller",
  "firstname": "John",
  "categoryName": "school",
  "address": "surulere street",
  "email": "JohnTeller11@gmail.com",
  "phoneNumber": "+234814523657",
  "userEmail": "JudithJoseph@gmail.com",
  "blocked": false
  },
``
  {
  "contactId": 2,
  "contactManagementId": 1,
  "surname": "Ruth",
  "firstname": "Debby",
  "categoryName": "school",
  "address": "surulere street",
  "email": "debbyRuth@gmail.com",
  "phoneNumber": "+234814523657",
  "userEmail": "JudithJoseph@gmail.com",
  "blocked": false
  }
  ],
  "message": null
  },
  "successful": true
`
### **Response 2**

`
status code 400 Bad request
body
"data": {
"contacts":
"message": ""No Contact Added""
},
"successful": false
`
### **Response 3**

`
status code 400 Bad request
body
"data": {
"contacts":
"message": ""Invalid details""
},
"successful": false
`

## **GET ViewAllContact**
This Api enable the registered user to be able to view all saved contacts because it retrives the data from the database,It takes in the user email
it return list of the user's saved contacts
Method:GET
Header: Content-Type:application/json

Required field
* Email 
### **Response 1**

`
  status code 200 ok
  body
  "data": {
  "contacts": [
  {
  "contactId": 1,
  "contactManagementId": 1,
  "surname": "Teller",
  "firstname": "John",
  "categoryName": "school",
  "address": "surulere street",
  "email": "JohnTeller11@gmail.com",
  "phoneNumber": "+234814523657",
  "userEmail": "JudithJoseph@gmail.com",
  "blocked": false
  },
  ``
{
  "contactId": 2,
  "contactManagementId": 1,
  "surname": "Fina",
  "firstname": "Reuben",
  "categoryName": "Home",
  "address": "sabo street",
  "email": "finareuben@gmail.com",
  "phoneNumber": "+234814523657",
  "userEmail": "JudithJoseph@gmail.com",
  "blocked": false
  }
  ],
  "message": null
  },
  "successful": true
  }
`
### **Response 2**

`
status code 200 Bad Request
body
"data": {
"contacts": 
"message": No Contact Added
},
"successful": false
}
`

## **POST BlockContact**
This Api enable users to be able block a specific contact,
whereby there would be certain restrictions on the contact,it accpect the user email,contact firstname,contact surname
Method:POST
Header: Content-Type:application/json

Required field
* Surname
* Firstname
* Email

### **Response 1**
`
status code 200 ok
body
"data": {
"message": "Contact Blocked"
},
"successful": true
`
### **Response 2**
`
status code 400 Bad request
body
"data": {
"message": "Contact doesnt exit"
},
"successful": false
`

## **POST unblockContact**
This Api is used to allow users to be able to unblock contacts that were blocked,and takes off the restrictions
it accpect the user email,contact firstname,contact surname
Method:POST
Header: Content-Type:application/json

Required field
* Surname
* Firstname
* Email

### **Response 1**
`
status code 200 ok
body
"data": {
"message": "Contact unblocked"
},
"successful": true
`
### **Response 2**
`
status code 400 Bad request
body
"data": {
"message": "Contact doesnt exit"
},
"successful": false
`

## **DELETE DeleteAContact**
This Api enable the user to be able a specific contact that was stored,
it accept the user email,the contact surname,the contact firstname
Method:DELETE
Header: Content-Type:application/json

Required field
* UserEmail
* Surname
* FirstName
  ### **Response 1**
  `
  status code 200 ok
  body
  "data": {
  "message": "Contact Deleted"
  },
  "successful": true
  `
  ### **Response 2**
  `
  status code 400 Bad request
  body
  "data": {
  "message": "Contact doesnt exit"
  },
  "successful": false
  `

##  **DELETE DeleteAllContactsInACategory**
This Api enable the user to be delete contacts that are in the specified category,
it accept the category name,the user email
Method:DELETE
Header: Content-Type:application/json
Required field
* UserEmail
* categoryName
`
### **Response 1**
status code 200 ok
body
{
"data": {
"message": "All contact in category deleted"
},
"successful": true
}
`
### **Response 2**
`
status code 400 Bad request
body
"data": {
"message": "Invalid details"
},
"successful": false
`

## **DELETE DeleteAccount**
This Api enable the users to able to delete their register account,
and all their details would be deleted from the database,it accept the user email
Method:DELETE
Header: Content-Type:application/json

Required field
* email
### **Response 1**
`
  status code 200 ok
  body
  {
  "data": {
  "message": "Account deleted"
  },
  "successful": true
  }
  `
### **Response 2**
  `
  status code 400 Bad request
  body
  "data": {
  "message": "Invalid details"
  },
  "successful": false
  `













































