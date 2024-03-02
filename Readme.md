Click on this link to view publish documentation
https://documenter.getpostman.com/view/31783055/2sA2xb6vb1
This contact management system is a software application designed to efficiently organize and manage contact.

Built using Maven for streamlined development, MySql for flexible data storage, and JUnit for comprehensive API testing. This ensures a reliable and efficient system for these Api's below;

Register User
Login User
Add Contact
Edit Contact
Edit User Info
View A Contact
View All Contacts in a Category
View All Contact
Block Contact
Unblock Contact
Delete A Contact
Delete All Contacts in A Catetory
Delete Account
HOW TO INSTALL PROGRAM

Install git
Click on this link https://github.com/AdewuyiDelighted/Expense-Tracker
Clone the project by following the cloning process
Install mySql for proper database functions
Ensure all dependencies in the the above project are well injected in your pom.xml
Ensure your project is on the right server.port
Open your Post man Application,paste the accurate url on the given url space
GET
ViewAllContact
localhost:8086/api/viewAllContact?email=JudithJoseph@gmail.com
This Api enable the registered user to be able to view all saved contacts because it retrives the data from the database,It takes in the user email

it return list of the user's saved contacts

Method:GET

Header: Content-Type:application/json

PARAMS
email
JudithJoseph@gmail.com

Body
raw (json)
json
{
"email":"delighteddeborah5@gmail.com"

}
Example Request
sucessViewAllContact
View More
curl
curl --location --request GET 'localhost:8086/api/viewAllContact?email=JudithJoseph%40gmail.com' \
--data-raw '{
"email":"delighteddeborah5@gmail.com"

}'
200 OK
Example Response
Body
Headers (5)
View More
json
{
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
}
POST
BlockContact
localhost:8086/api/blockContact
This Api enable users to be able block a specific contact,whereby there would be certain restrictions on the contact,it accpect the user email,contact firstname,contact surname

Method:POST

Header: Content-Type:application/json

Body
raw (json)
json
{
"surname":"Adeshina",
"firstname":"Qudus",
"email":"JudithJoseph@gmail.com"
}
DELETE
DeleteAContact
localhost:8086/api/deleteAContact
This Api enable the user to be able a specific contact that was stored,it accept the user email,the contact surname,the contact firstname

Method:DELETE

Header: Content-Type:application/json

Body
raw (json)
json
{
"userEmail":"JudithJoseph@gmail.com",
"surname":"Ojo",
"firstName":"Tobi"
}
Example Request
errorDeleteAContact
View More
curl
curl --location --request DELETE 'localhost:8086/api/deleteAContact' \
--data-raw '{
"userEmail":"JudithJoseph@gmail.com",
"surname":"Ojo",
"firstName":"Tobi"
}'
400 BAD REQUEST
Example Response
Body
Headers (4)
json
{
"data": {
"message": "Contact doesnt not exist"
},
"successful": false
}
DELETE
DeleteAllContactsInACategory
localhost:8086/api/deleteAllContactInCategory
This Api enable the user to be delete contacts that are in the specified category,it accept the category name,the user email

Method:DELETE

Header: Content-Type:application/json

Body
raw (json)
json
{
"email":"JudithJoseph@gmail.com",
"categoryName":"school"
}
Example Request
successDeleteAllContactsInACategory
View More
curl
curl --location --request DELETE 'localhost:8086/api/deleteAllContactInCategory' \
--data-raw '{
"email":"JudithJoseph@gmail.com",
"categoryName":"school"
}'
200 OK
Example Response
Body
Headers (5)
json
{
"data": {
"message": "All contact in category deleted"
},
"successful": true
}
DELETE
DeleteAccount
localhost:8084/api/deleteAccount/?email=JudithJoseph@gmail.com
This Api enable the users to able to delete their register account,and all their details would be deleted from the database,it accept the user email

Method:DELETE

Header: Content-Type:application/json

PARAMS
email
JudithJoseph@gmail.com