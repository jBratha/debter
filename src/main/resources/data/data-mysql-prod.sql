INSERT INTO user (id, username, password, firstname, lastname, email, enabled, last_password_reset_date)
VALUES (1,
        'admin',
        '{bcrypt}$2y$12$erXdvVObWEUBYKRTepqLIeptlaVWQN9LWwpWTHaGpd7oBe10K1/lG',
        'admin',
        'admin',
        'admin',
        1,
        str_to_date('01-01-2018', '%d-%m-%Y %H-%i-%s'));

INSERT INTO user (id, username, password, firstname, lastname, email, enabled, last_password_reset_date)
VALUES (2,
        'kator',
        '{bcrypt}$2y$12$erXdvVObWEUBYKRTepqLIeptlaVWQN9LWwpWTHaGpd7oBe10K1/lG',
        'Wojtek',
        'Jaro',
        'wojtasj1996@gmail.com',
        1,
        str_to_date('01-01-2018', '%d-%m-%Y %H-%i-%s'));

INSERT INTO authority (id, name)
VALUES (1, 'ROLE_USER');
INSERT INTO authority (id, name)
VALUES (2, 'ROLE_ADMIN');

INSERT INTO user_authority (user_id, authority_id)
VALUES (1, 2);
INSERT INTO user_authority (user_id, authority_id)
VALUES (2, 1);
