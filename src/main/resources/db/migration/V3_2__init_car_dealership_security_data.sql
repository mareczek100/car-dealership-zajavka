ALTER TABLE salesman
ADD COLUMN user_id INT,
ADD FOREIGN KEY (user_id) REFERENCES car_dealership_user (user_id);

ALTER TABLE mechanic
ADD COLUMN user_id INT,
ADD FOREIGN KEY (user_id) REFERENCES car_dealership_user (user_id);

ALTER TABLE customer
ADD COLUMN user_id INT NOT NULL,
ADD FOREIGN KEY (user_id) REFERENCES car_dealership_user (user_id);

insert into car_dealership_user (user_name, email, password, active) values ('stefan_sprzedawca', 'stefan_sprzedawca@zajavka.pl', '$2a$12$TwQsp1IusXTDl7LwZqL0qeu49Ypr6vRdEzRq2vAsgb.zvOtrnzm5G', true);
insert into car_dealership_user (user_name, email, password, active) values ('agnieszka_samochodowa', 'agnieszka_samochodowa@zajavka.pl', '$2a$12$TwQsp1IusXTDl7LwZqL0qeu49Ypr6vRdEzRq2vAsgb.zvOtrnzm5G', true);
insert into car_dealership_user (user_name, email, password, active) values ('tomasz_kombi', 'tomasz_kombi@zajavka.pl', '$2a$12$TwQsp1IusXTDl7LwZqL0qeu49Ypr6vRdEzRq2vAsgb.zvOtrnzm5G', true);
insert into car_dealership_user (user_name, email, password, active) values ('rafal_dach', 'rafal_dach@zajavka.pl', '$2a$12$TwQsp1IusXTDl7LwZqL0qeu49Ypr6vRdEzRq2vAsgb.zvOtrnzm5G', true);

insert into car_dealership_user (user_name, email, password, active) values ('robert_srubokret', 'robert_srubokret@zajavka.pl', '$2a$12$TwQsp1IusXTDl7LwZqL0qeu49Ypr6vRdEzRq2vAsgb.zvOtrnzm5G', true);
insert into car_dealership_user (user_name, email, password, active) values ('zygmunt_naprawa', 'zygmunt_naprawa@zajavka.pl', '$2a$12$TwQsp1IusXTDl7LwZqL0qeu49Ypr6vRdEzRq2vAsgb.zvOtrnzm5G', true);
insert into car_dealership_user (user_name, email, password, active) values ('remigiusz_alufelga', 'remigiusz_alufelga@zajavka.pl', '$2a$12$TwQsp1IusXTDl7LwZqL0qeu49Ypr6vRdEzRq2vAsgb.zvOtrnzm5G', true);

UPDATE salesman SET user_id = 1 WHERE pesel = '67020499436';
UPDATE salesman SET user_id = 2 WHERE pesel = '73021314515';
UPDATE salesman SET user_id = 3 WHERE pesel = '55091699846';
UPDATE salesman SET user_id = 4 WHERE pesel = '62081825675';

UPDATE mechanic SET user_id = 5 WHERE pesel = '52070997836';
UPDATE mechanic SET user_id = 6 WHERE pesel = '83011863727';
UPDATE mechanic SET user_id = 7 WHERE pesel = '67111396321';

insert into role (role_id, role) values (1, 'SALESMAN'), (2, 'MECHANIC'), (3, 'CUSTOMER'), (4, 'REST_API');

insert into user_role (user_id, role_id) values (1, 1), (2, 1), (3, 1), (4, 1);
insert into user_role (user_id, role_id) values (1, 4), (2, 4), (3, 4), (4, 4);
insert into user_role (user_id, role_id) values (5, 2), (6, 2), (7, 2);

ALTER TABLE salesman
ALTER COLUMN user_id SET NOT NULL;

ALTER TABLE mechanic
ALTER COLUMN user_id SET NOT NULL;