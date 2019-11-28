-- admin/admin
INSERT INTO USER (ID, USERNAME, PASSWORD, FIRSTNAME, LASTNAME, EMAIL, ENABLED, LAST_PASSWORD_RESET_DATE)
VALUES (1,
        'admin',
        '{bcrypt}$2y$12$erXdvVObWEUBYKRTepqLIeptlaVWQN9LWwpWTHaGpd7oBe10K1/lG',
        'admin',
        'admin',
        'admin@admin.com',
        1,
        PARSEDATETIME('01-01-2016', 'dd-MM-yyyy'));
INSERT INTO USER (ID, USERNAME, PASSWORD, FIRSTNAME, LASTNAME, EMAIL, ENABLED, LAST_PASSWORD_RESET_DATE)
VALUES (2,
        'user',
        '{bcrypt}$2y$12$CEagJ7BEZzjIAvIJGQUIButx3LnyFH.zemAdZHyco.c/yzt2g/Ot.',
        'user',
        'user',
        'enabled@user.com',
        1,
        PARSEDATETIME('01-01-2016', 'dd-MM-yyyy'));
-- INSERT INTO USER (ID, USERNAME, PASSWORD, FIRSTNAME, LASTNAME, EMAIL, ENABLED, LAST_PASSWORD_RESET_DATE) VALUES (3, 'disabled', '{bcrypt}$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC', 'user', 'user', 'disabled@user.com', 0, PARSEDATETIME('01-01-2016','dd-MM-yyyy'));

INSERT INTO AUTHORITY (ID, NAME)
VALUES (1, 'ROLE_USER');
INSERT INTO AUTHORITY (ID, NAME)
VALUES (2, 'ROLE_ADMIN');

INSERT INTO USER_AUTHORITY (USER_ID, AUTHORITY_ID)
VALUES (1, 1);
INSERT INTO USER_AUTHORITY (USER_ID, AUTHORITY_ID)
VALUES (1, 2);
INSERT INTO USER_AUTHORITY (USER_ID, AUTHORITY_ID)
VALUES (2, 1);
-- INSERT INTO USER_AUTHORITY (USER_ID, AUTHORITY_ID) VALUES (3, 1);


select @kator_id := id
from user
where username = 'admin';

select @user_id := id
from user
where username = 'user';

INSERT INTO debts (id, debtor, creditor, amount, description, status, to_confirm_by)
VALUES (1, @kator_id, @user_id, 7.6, 'za kino', 'CONFIRMED', null);
INSERT INTO debts (id, debtor, creditor, amount, description, status, to_confirm_by)
VALUES (2, @kator_id, @user_id, 12.3, '10 maja - impreza', 'RESOLVED', null);
INSERT INTO debts (id, debtor, creditor, amount, description, status, to_confirm_by)
VALUES (3, @kator_id, @user_id, 5.5, 'urodziny testera', 'NOT_CONFIRMED', 'kator');
INSERT INTO debts (id, debtor, creditor, amount, description, status, to_confirm_by)
VALUES (4, @user_id, @kator_id, 8.4, 'bo tak', 'RESOLVED', null);
INSERT INTO debts (id, debtor, creditor, amount, description, status, to_confirm_by)
VALUES (5, @user_id, @kator_id, 25.2, 'odsetki', 'CONFIRMED', null);
INSERT INTO debts (id, debtor, creditor, amount, description, status, to_confirm_by)
VALUES (6, @user_id, @kator_id, 14.1, 'za robotę', 'NOT_CONFIRMED', 'user');

