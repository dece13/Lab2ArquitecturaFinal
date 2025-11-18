// mongo-init.js
db = db.getSiblingDB("persona_db");  // Selecciona la base persona_db

db.createUser({
    user: "persona_user",
    pwd: "contrasena",
    roles: [
        { role: "readWrite", db: "persona_db" },
        { role: "dbAdmin", db: "persona_db" }
    ]
});
