CREATE TABLE users
(
    user_id  UUID         PRIMARY KEY,
    name     VARCHAR(64)  NOT NULL,
    email    VARCHAR(128) NOT NULL,
    password VARCHAR(64)  NOT NULL
);

CREATE TABLE boards
(
    board_id UUID         PRIMARY KEY,
    title    VARCHAR(128) NOT NULL
);

CREATE TABLE tasks
(
    task_id      UUID         PRIMARY KEY,
    text         VARCHAR(128) NOT NULL,
    board_id     UUID,
    FOREIGN KEY (board_id) REFERENCES boards(board_id)
);