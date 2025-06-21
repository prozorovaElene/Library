This project is a Spring Boot-based Library Management API. The application supports three primary user roles: Admin, Customer, and Report Panel, each with specific functionality. 
The system integrates with a third-party API (OpenLibrary) to fetch and refresh book data and stores relevant information in a local H2 database.

The application includes features for three main panels: the Admin Panel allows registering, deactivating, and reactivating books, retrieving all books or filtering by customer,
and refreshing book data via a third-party API. The Customer Panel supports viewing available books, reserving and returning them, rating books previously borrowed, and registering new users. 
The Report Panel generates CSV reports of reserved books per customer and tracks how often each book has been borrowed.

