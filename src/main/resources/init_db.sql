USE library_jdbc_intro;

CREATE TABLE books (
                       id INT NOT NULL AUTO_INCREMENT Primary Key,
                       title VARCHAR(255) NOT NULL,
                       price DECIMAL (10, 2) NOT NULL
);
