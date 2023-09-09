create table topicos(
id BIGINT not null auto_increment,
titulo VARCHAR(100) not null,
mensaje VARCHAR(100) not null,
fecha_creacion DATE not null,
estatus VARCHAR(100) not null,
autor_id BIGINT  not null,
curso_id BIGINT  not null,
primary key (id)
);