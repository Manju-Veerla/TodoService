-- Create the database if it doesn't exist
CREATE DATABASE IF NOT EXISTS todo_db;

-- Create user with proper permissions
CREATE USER IF NOT EXISTS 'admin'@'%' IDENTIFIED BY 'admin';
GRANT ALL PRIVILEGES ON todo_db.* TO 'admin'@'%';
FLUSH PRIVILEGES;

-- Use the database
USE todo_db;
