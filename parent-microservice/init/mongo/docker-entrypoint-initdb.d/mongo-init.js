print("INIT SCRIPT EXECUTED");

// Access the database
db = db.getSiblingDB('product-service');

// Create a user with authentication
db.createUser(
    {
        user: 'admin',
        pwd: 'password',
        roles: [{role: 'readWrite', db: 'product-service'}],
    }
);

// Create the collection
db.createCollection('user');

print("INIT SCRIPT ENDED");

