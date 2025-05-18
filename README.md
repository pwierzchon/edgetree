# edgetree
Project of a backend service which handles a data tree structure.

## setup
This project requires Java version 17 and PostgreSQL 17.

### database setup
In order to run the project without any further configuartions create a database named edgetree with schema public. 
Running command `mvn clean install` should set up the database via liquibase which will create the tables and fill them with data.

### testing the API
This repository contains a Postman collection *Edge Tree.postman_collection.json* which contains example requests for adding an edge, deleting an edge and getting a tree based on the selected root node. 

The endpoints do not allow to delete non-existent edges, to add an edge where from and to nodes are equal, and to add an edge that would create a cycle in the graph.

Enjoy and have fun while testing.

PS. I didn't add any unit testing or so, just for the sake of simplicity. 
