# OFFERSHOPPER

-------------
## TECHNOLOGIES 

- Eureka for Lookup
- Ribbon for Load Balancing. 
- Hystrix is used for resilience.
- Hystrix has a dashboard. Turbine can be used to combine the data
  from multiple sources. 
- Zuul is used to route HTTP requests from the outside to the
  different services.

--------------------------
## MICROSERVICES AND AUTHOR

- carry-bag-database-service 	: Mukesh and Anand
- feedback-database-service  	: Pooja ans Rajneesh
- offer-database-service     	: Anish and Ketan
- subscribe-database-service 	: Ashutosh and Dinesh
- user-database-service      	: Vishruty and Yashika
- wishlist-database-service  	: Gursharan
- referral-database-service  	: Vishruty
- uaa-server                 	: Girish Mehta and Nikhil KK
- registration-service       	: Girish Mehta and Nikhil KK
- vendor-profile-service     	: Ashutosh and Dinesh
- userVendor-activity-service	: Rajneesh,Anand,Gursharan and Yashika 
- search-service             	: Anish,Ketan and Mukesh
- eureka-server                 : Girish Mehta
- config-server                 : Girish Mehta
- zuul-server                   : Girish Mehta and Nikhil KK

-----------------------
## MICROSERVICES MAPPING

-----------------------------
### CARRY-BAG-DATABASE-SERVICE
-----------------------------

- POST	 : /bag/add
- PUT  	 : /bag/update
- GET  	 : /bag/userId
- GET    : /bag/userId/{userId}
- DELETE : /bag/userId/{userId}/offerId/{offerId}

----------------------------
### FEEDBACK-DATABASE-SERVICE
----------------------------

- POST : /os/addfeedback
- POST : /os/addfeedback/comment
- GET  : /os/getfeedback/couponId/{couponId}
- GET  : /os/getfeedback/userId/{userId}/offerId/{offerId}
- GET  : /os/getfeedback/{offerId}

-------------------------
### OFFER-DATABASE-SERVICE
-------------------------

- POST   : /offers
- DELETE : /offers/{id}
- PUT    : /offers/{id}
- GET    : /offers/{id}
- GET	 : /vendor/{userId}/offers

-----------------------------
### SUBSCRIBE-DATABASE-SERVICE
-----------------------------

- POST   : /subscribe/add/bycategory
- POST	 : /subscribe/add/byvendorid
- DELETE : /subscribe/del/bycategory/{userId}/{category}
- DELETE : /subscribe/del/byvendorid/{userId}/{vendorId}
- GET	 : /subscribe/get
- GET	 : /subscribe/getUser/{userId}

------------------------
### USER-DATABASE-SERVICE
------------------------

- POST   : /users/add
- PUT	 : /users/update
- GET    : /users/details/{email}
- DELETE : /users/delete/{email}

----------------------------
### WISHLIST-DATABASE-SERVICE
----------------------------

- POST   : /add-to-wishlist
- DELETE : /delete-wishlist-offer/{offerId}
- DELETE : /delete-wishlist-offer/{offerId}/{userId}
- DELETE : /delete-wishlist-user/{userId}
- GET	 : /getWishlist/{userId}
- PUT	 : /update-wishlist-offer

----------------------------
### REFERRAL-DATABASE-SERVICE
----------------------------

- PUT    : /spin/{email1}/{email2}

-------------
### UAA-SERVER
-------------

- GET    : /forgotpassword/{email}
- GET    : /registration/verification/{token}
- GET    : /sendemail
- GET    : /token/register/vendor/{uname}/{role}
- GET    : /verifyToken/user/{Jwe}
- GET    : /verifytoken/{applicationToken}
- POST   : /token/login
- POST   : /token/register
- POST   : /forgetpassword

-----------------------
### REGISTRATION-SERVICE
-----------------------

- GET  	 : /all
- GET    : /register
- GET    : /register/{id}
- POST 	 : /login
- POST   : /register
- POST   : /register/user
       	 : /updatepassword

-------------------------
### VENDOR-PROFILE-SERVICE
-------------------------

- GET    : /vendor-profile/{email}

------------------------------
### USERVENDOR-ACTIVITY-SERVICE
------------------------------

- GET    : /carrybag/{userId}
- GET    : /getsubscription
- GET    : /getwishlist{userId}
- GET    : /logout
- GET    : /userdata/{userId}
- PUT    : /update
- PUT    : /updateOffer/{id}
- PUT    : /updatespincount/{spinvalue}
- POST   : /addOffer
- POST   : /addincarrybag
- POST   : /addsubscription
- POST   : /addwish/{userId}
- POST   : /converttovendor/{userId}/{role}
- POST   : /forgotpassword
- POST   : /referral/{userId1}/{userId2}
- DELETE : /deleteOffer/{id}
- DELETE : /deleteincarrybag/{offerId}
- DELETE : /deletesubscription{userId}
- DELETE : /deletewish/{offerId}/{user/Id}

-----------------
### SEARCH-SERVICE
-----------------

- GET    : /search-key/{searchKey}
- GET    : /offerCategories/{offerCategories}/search-key/{searchKey}
- GET    : /offerCategories/{offerCategories}
- GET    : /q
- GET  	 : /q/{q}
- GET    : /load-data
- POST   : /add-code



