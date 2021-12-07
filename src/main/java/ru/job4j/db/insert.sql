insert into rule (id, name) values(1, 'Access to public folders'), (2, 'Access to secret folders'), (3, 'Instruct');
insert into role (id, name) values(1, 'Simple worker'), (2, 'Superviser');
insert into rules_for_roles (id, role_id, rule_id) values(1, 1, 1), (2, 2, 2), (3, 2, 3);
insert into category (id, name) values (1, 'Minor'), (2, 'Major');
insert into state (id, name) values (1, 'Open'), (2, 'Close');
insert into item (id, name, category_id, state_id) values (1, 'incorrect client data', 1, 1), (2, 'Problem with connection', 1, 2), (3, 'Customer feedback', 2, 2);
insert into attach (id, item_id, file) values (1, 3, E'\x7f\x7f');
insert into comment (id, item_id, text) values (1, 3, 'Need call to customer'), (2, 3, 'Resolved');

