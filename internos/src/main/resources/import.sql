insert into grupos (nombre) values ('CABA');
insert into grupos (nombre) values ('Vicente Lopez');
insert into grupos (nombre) values ('Otros');

insert into subgrupos (nombre,grupo_id) values ('Compras Especialidades',1);
insert into subgrupos (nombre,grupo_id) values ('Planta Accesorios',1);
insert into subgrupos (nombre,grupo_id) values ('Bunker',1);
insert into subgrupos (nombre,grupo_id) values ('Computos VL',2);
insert into subgrupos (nombre,grupo_id) values ('Recepcion',2);
insert into subgrupos (nombre,grupo_id) values ('Expedicion',2);
insert into subgrupos (nombre,grupo_id) values ('Farmacias',3);
insert into subgrupos (nombre,grupo_id) values ('Sifaco',3);
insert into subgrupos (nombre,grupo_id) values ('Validadores',3);
insert into contactos (nombre,numero,subgrupo_id) values('Lucas',44592794,1);

insert into usuarios (username,password,enabled) values ('Lucas','$2a$10$Wn/sOTUM8ISBL6rjKn4Vq.bWjLyI.q0q33wP/wT2wh1qnTp9maD6m',1);
insert into usuarios (username,password,enabled) values ('Admin','$2a$10$47O6t0lZAV1HOFQiXmLRNeI02fCNKCZAFejZZIIQSOQ4py7zIZnaq',1);

insert into roles (nombre) values ('ROLE_ADMIN');
insert into roles (nombre) values('ROLE_USER');

insert into usuarios_roles (usuario_id,role_id) values(1,2);
insert into usuarios_roles (usuario_id,role_id) values(2,2);
insert into usuarios_roles (usuario_id,role_id) values(2,1);
