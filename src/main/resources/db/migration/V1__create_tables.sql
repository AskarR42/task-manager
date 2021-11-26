CREATE TABLE users
(
    user_id  UUID         PRIMARY KEY,
    name     VARCHAR(64)  NOT NULL,
    email    VARCHAR(128) NOT NULL,
    password VARCHAR(64)  NOT NULL
);

CREATE TABLE projects
(
    project_id UUID         PRIMARY KEY,
    title      VARCHAR(128) NOT NULL,
    user_id    UUID,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE tasks
(
    task_id      UUID         PRIMARY KEY,
    text         VARCHAR(128) NOT NULL,
    column_id    INT,
    project_id   UUID,
    FOREIGN KEY (project_id) REFERENCES projects(project_id)
);