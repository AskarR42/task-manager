CREATE TABLE users
(
    user_id  UUID         PRIMARY KEY,
    name     VARCHAR(64)  NOT NULL,
    nickname VARCHAR(64)  NOT NULL,
    email    VARCHAR(128) NOT NULL,
    password VARCHAR(64)  NOT NULL
);

CREATE TABLE task_lists
(
    task_list_id UUID         PRIMARY KEY,
    name         VARCHAR(128) NOT NULL,
    user_id      UUID         NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE tasks
(
    task_id          UUID          PRIMARY KEY,
    status           VARCHAR(8)    NOT NULL,
    text             VARCHAR(128)  NOT NULL,
    task_list_id     UUID          NOT NULL,
    FOREIGN KEY (task_list_id) REFERENCES task_lists(task_list_id)
);

CREATE TABLE projects
(
    project_id  UUID         PRIMARY KEY,
    name        VARCHAR(128) NOT NULL,
    description VARCHAR(256),
    date        VARCHAR(16),
    color       VARCHAR(16),
    user_id     UUID         NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE big_tasks
(
    big_task_id UUID         PRIMARY KEY,
    name        VARCHAR(128) NOT NULL,
    description VARCHAR(256),
    date        VARCHAR(16),
    color       VARCHAR(16),
    column_id   INT          NOT NULL,
    project_id  UUID         NOT NULL,
    FOREIGN KEY (project_id) REFERENCES projects(project_id)
);


CREATE TABLE little_tasks
(
    little_task_id UUID         PRIMARY KEY,
    status         VARCHAR(8)   NOT NULL,
    text           VARCHAR(128) NOT NULL,
    big_task_id   UUID          NOT NULL,
    FOREIGN KEY (big_task_id) REFERENCES big_tasks(big_task_id)
);