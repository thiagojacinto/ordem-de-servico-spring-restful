create table comentario (
	id bigserial not null,
	ordem_servico_id bigint not null,
	descricao text not null,
	data_publicacao timestamp not null,
	data_edicao timestamp,
	
	primary key (id)
);

alter table comentario add constraint fk_comentario_ordem_servico
foreign key (ordem_servico_id) references ordem_servico (id);