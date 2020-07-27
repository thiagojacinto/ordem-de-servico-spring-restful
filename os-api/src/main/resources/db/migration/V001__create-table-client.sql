create table cliente (
	id bigserial not null,
	nome varchar(60) not null,
	email varchar(255) not null,
	phone varchar(20) not null,
	endereco varchar(255) not null,
	
	primary key (id)
);