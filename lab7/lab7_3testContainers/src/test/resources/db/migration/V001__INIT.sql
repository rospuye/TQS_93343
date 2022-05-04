CREATE TABLE books (
    id BIGSERIAL PRIMARY KEY,
    title varchar(255) not null,
    author varchar(255) not null,
    page_number NUMBER not null,
);

INSERT INTO books (title, author, page_number) VALUES ('Pride And Prejudice', 'Jane Austen', 367);
INSERT INTO books (title, author, page_number) VALUES ('A Sibila', 'Augustina Bessa-Luís', 249);