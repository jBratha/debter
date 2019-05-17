INSERT INTO user (id, username, password, firstname, lastname, email, enabled, last_password_reset_date)
VALUES (1,
        'admin',
        '{bcrypt}$2y$12$erXdvVObWEUBYKRTepqLIeptlaVWQN9LWwpWTHaGpd7oBe10K1/lG',
        'admin',
        'admin',
        'admin@admin.com',
        1,
        str_to_date('01-01-2018', '%d-%m-%Y %H-%i-%s'));
INSERT INTO user (id, username, password, firstname, lastname, email, enabled, last_password_reset_date)
VALUES (2,
        'user',
        '{bcrypt}$2y$12$CEagJ7BEZzjIAvIJGQUIButx3LnyFH.zemAdZHyco.c/yzt2g/Ot.',
        'user',
        'user',
        'enabled@user.com',
        1,
        str_to_date('01-01-2018', '%d-%m-%Y %H-%i-%s'));

INSERT INTO authority (id, name)
VALUES (1, 'ROLE_USER');
INSERT INTO authority (id, name)
VALUES (2, 'ROLE_ADMIN');

INSERT INTO user_authority (user_id, authority_id)
VALUES (1, 1);
INSERT INTO user_authority (user_id, authority_id)
VALUES (1, 2);
INSERT INTO user_authority (user_id, authority_id)
VALUES (2, 1);

select @admin_id := id
from user
where username = 'admin';
select @user_id := id
from user
where username = 'user';

INSERT INTO debts (id, debtor, creditor, amount, description, status, to_confirm_by)
VALUES (1, @admin_id, @user_id, 7.6, 'za kino', 'CONFIRMED', null);
INSERT INTO debts (id, debtor, creditor, amount, description, status, to_confirm_by)
VALUES (2, @admin_id, @user_id, 12.3, '10 maja - impreza', 'RESOLVED', null);
INSERT INTO debts (id, debtor, creditor, amount, description, status, to_confirm_by)
VALUES (3, @admin_id, @user_id, 5.5, 'urodziny testera', 'NOT_CONFIRMED', @admin_id);
INSERT INTO debts (id, debtor, creditor, amount, description, status, to_confirm_by)
VALUES (4, @user_id, @admin_id, 8.4, 'bo tak', 'RESOLVED', null);
INSERT INTO debts (id, debtor, creditor, amount, description, status, to_confirm_by)
VALUES (5, @user_id, @admin_id, 25.2, 'odsetki', 'CONFIRMED', null);
INSERT INTO debts (id, debtor, creditor, amount, description, status, to_confirm_by)
VALUES (6, @user_id, @admin_id, 14.1, 'za robotę', 'NOT_CONFIRMED', @admin_id);
