create table ordem_servico (
	id bigserial not null,
	cliente_id bigint not null,
	descricao text not null,
	preco decimal(10,2) not null,
	status varchar(20) not null,
	data_abertura timestamp not null,
	data_modificacao timestamp,
	data_finalizacao timestamp,
	
	primary key (id)
);

alter table ordem_servico add constraint fk_ordem_servico_client
foreign key (cliente_id) references cliente (id);