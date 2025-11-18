-- Crear base de datos si no existe
CREATE DATABASE IF NOT EXISTS personapp;

-- Crear usuario 'user' con contraseña 'user' si no existe
CREATE USER IF NOT EXISTS 'user'@'%' IDENTIFIED BY 'contrasena';

-- Dar todos los permisos sobre la base de datos personapp al usuario
GRANT ALL PRIVILEGES ON personapp.* TO 'user'@'%';

-- Aplicar cambios de privilegios
FLUSH PRIVILEGES;
