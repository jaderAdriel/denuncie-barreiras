INSERT INTO User (name, email, birth_date, password, gender) VALUES
('Samara Mercês', 'samaramerces746@gmail.com', '2004-07-18', 'SamaraM1807', 'Feminina'),
('Ana Rode', 'Arode1566@gmail.com', '2002-12-07', 'AnaR123', 'Feminina'),
('Luiz Filipe', 'luizfilipe1980@gamil.com', '2002-10-21', 'LuizF25', 'Masculino');


INSERT INTO Moderator (user_fk, cellphone) VALUES
(1, '77991973716');


INSERT INTO Law (code, date, officialLink, title, description) VALUES
('LBI', '2015-07-06', 'https://www.planalto.gov.br/ccivil_03/_ato2015-2018/2015/lei/l13146.htm', 
 'Lei Brasileira de Inclusão da Pessoa com Deficiência', 
 'Garante e promove, em condições de igualdade, o exercício dos direitos e das liberdades fundamentais por pessoa com deficiência.'),

('Decreto 5.296', '2004-12-02', 'https://www.planalto.gov.br/ccivil_03/_ato2004-2006/2004/decreto/d5296.htm', 
 'Decreto nº 5.296', 
 'Estabelece normas gerais e critérios básicos para a promoção da acessibilidade.'),

('Lei 10.098', '2000-12-19', 'https://www.planalto.gov.br/ccivil_03/leis/L10098.htm', 
 'Lei nº 10.098', 
 'Estabelece normas gerais para a promoção da acessibilidade.'),

('Decreto 6.949', '2009-08-25', 'https://www.planalto.gov.br/ccivil_03/_ato2007-2010/2009/decreto/d6949.htm', 
 'Convenção sobre os Direitos das Pessoas com Deficiência', 
 'Promulga a Convenção Internacional com força de emenda constitucional.'),

('Lei 12.587', '2012-01-03', 'https://www.planalto.gov.br/ccivil_03/_ato2011-2014/2012/lei/l12587.htm', 
 'Política Nacional de Mobilidade Urbana', 
 'Diretrizes para assegurar o direito ao transporte acessível.');

INSERT INTO BarrierScenario (type, author_fk, content, title, likes) VALUES
('Física', 1, 'Calçada sem rebaixamento.', 'Calçada com barreira arquitetônica', 10),
('Atitudinal', 2, 'Negligência em relação à prioridade de atendimento.', 'Falta de prioridade', 5),
('Comunicação', 3, 'Falta de intérprete de Libras.', 'Ausência de acessibilidade comunicacional', 12),
('Física', 1, 'Escadas sem corrimão.', 'Escadaria sem acessibilidade', 7),
('Tecnológica', 2, 'Site público sem acessibilidade.', 'Portal sem acessibilidade', 8),
('Física', 3, 'Banheiro público sem adaptações.', 'Banheiro não acessível', 15),
('Atitudinal', 1, 'Desrespeito no transporte público.', 'Motorista não respeita PCD', 4),
('Física', 2, 'Prédio sem elevador.', 'Acesso restrito em edifício', 6),
('Comunicação', 3, 'Materiais escolares sem versão em braille.', 'Falta de braille na escola', 3),
('Tecnológica', 1, 'Aplicativo bancário inacessível.', 'App sem acessibilidade', 9),
('Física', 2, 'Transporte público sem rampa.', 'Ônibus sem rampa', 11),
('Atitudinal', 3, 'Funcionários despreparados.', 'Atendimento despreparado', 5),
('Comunicação', 1, 'Sinalização precária.', 'Placas mal localizadas', 7),
('Física', 2, 'Acesso ao parque bloqueado.', 'Parque sem acessibilidade', 6),
('Atitudinal', 3, 'Falta de empatia.', 'Comportamento inadequado com PCDs', 4),
('Comunicação', 1, 'Ausência de legendas em vídeos.', 'Vídeos não acessíveis', 8),
('Tecnológica', 2, 'Softwares educacionais inacessíveis.', 'Tecnologia sem inclusão', 9),
('Física', 3, 'Estacionamento sem vaga PCD.', 'Vaga PCD inexistente', 13),
('Atitudinal', 1, 'Empresas não contratam PCDs.', 'Barreira no mercado de trabalho', 5),
('Comunicação', 2, 'Informações sonoras apenas.', 'Falta de acessibilidade visual', 6);


INSERT INTO BarrierScenario_Law (barrierScenario_fk, law_fk) VALUES
(1, 'Lei 10.098'), (1, 'Decreto 5.296'),
(2, 'LBI'),
(3, 'LBI'), (3, 'Decreto 6.949'),
(4, 'Lei 10.098'),
(5, 'Lei 12.587'),
(6, 'Lei 10.098'), (6, 'Decreto 5.296'),
(7, 'LBI'),
(8, 'Decreto 5.296'),
(9, 'Decreto 6.949'),
(10, 'Lei 12.587'),
(11, 'Lei 10.098'),
(12, 'LBI'),
(13, 'Lei 10.098'),
(14, 'Decreto 5.296'),
(15, 'LBI'),
(16, 'Decreto 6.949'),
(17, 'Lei 10.098'),
(18, 'LBI'),
(19, 'Decreto 6.949'),
(20, 'Lei 12.587');


INSERT INTO Report (reporter_fk, type, ambient, severity, anonymous_report, event_detailing, related_scenario_fk) VALUES
(1, 'Física', 'Urbano', 3, 0, 'Calçada obstruída.', 1),
(2, 'Atitudinal', 'Serviço', 2, 1, 'Atendimento desrespeitoso.', 2),
(3, 'Comunicação', 'Educação', 4, 0, 'Faltam materiais acessíveis.', 9),
(1, 'Física', 'Transporte', 3, 0, 'Ônibus sem rampa.', 11),
(2, 'Tecnológica', 'Digital', 4, 1, 'App sem acessibilidade.', 10),
(3, 'Atitudinal', 'Comércio', 2, 0, 'Despreparo no atendimento.', 12),
(1, 'Física', 'Lazer', 3, 0, 'Parque inacessível.', 14),
(2, 'Comunicação', 'Cultura', 2, 1, 'Vídeos sem legenda.', 16),
(3, 'Física', 'Estacionamento', 4, 0, 'Sem vaga PCD.', 18),
(1, 'Atitudinal', 'Emprego', 3, 0, 'Empresas não contratam PCD.', 19),
(2, 'Física', 'Urbano', 2, 1, 'Buraco perigoso.', 1),
(3, 'Física', 'Transporte', 3, 0, 'Estação sem elevador.', 8),
(1, 'Comunicação', 'Saúde', 4, 1, 'Instruções não adaptadas.', 13),
(2, 'Tecnológica', 'Digital', 2, 0, 'Website inacessível.', 5),
(3, 'Física', 'Serviço', 3, 0, 'Prédio sem acessibilidade.', 4),
(1, 'Física', 'Educação', 2, 1, 'Sala de aula inadequada.', 9),
(2, 'Comunicação', 'Cultura', 3, 0, 'Ausência de audiodescrição.', 16),
(3, 'Física', 'Lazer', 3, 1, 'Parque sem rampa.', 14),
(1, 'Tecnológica', 'Serviço', 4, 0, 'Sistema inacessível.', 5),
(2, 'Física', 'Saúde', 2, 0, 'Hospital sem adaptação.', 6),
(3, 'Atitudinal', 'Emprego', 3, 1, 'Discriminação evidente.', 19),
(1, 'Comunicação', 'Educação', 3, 0, 'Falta de intérprete.', 3),
(2, 'Tecnológica', 'Digital', 4, 1, 'Portal inacessível.', 5),
(3, 'Física', 'Comércio', 2, 0, 'Entrada bloqueada.', 4),
(1, 'Atitudinal', 'Serviço', 3, 0, 'Falta de prioridade.', 2),
(2, 'Física', 'Urbano', 3, 1, 'Calçada irregular.', 1),
(3, 'Comunicação', 'Educação', 4, 0, 'Sem materiais adaptados.', 9),
(1, 'Física', 'Transporte', 3, 0, 'Veículo sem adaptação.', 11),
(2, 'Tecnológica', 'Digital', 4, 1, 'Sistema de transporte inacessível.', 10),
(3, 'Atitudinal', 'Comércio', 2, 0, 'Negligência no atendimento.', 12),
(1, 'Física', 'Lazer', 3, 0, 'Acesso bloqueado.', 14),
(2, 'Comunicação', 'Cultura', 2, 1, 'Vídeos sem recursos.', 16),
(3, 'Física', 'Estacionamento', 4, 0, 'Sem espaço reservado.', 18),
(1, 'Atitudinal', 'Emprego', 3, 0, 'Preconceito.', 19),
(2, 'Física', 'Urbano', 2, 1, 'Barreira arquitetônica.', 1),
(3, 'Física', 'Transporte', 3, 0, 'Terminal inacessível.', 8),
(1, 'Comunicação', 'Saúde', 4, 1, 'Avisos apenas sonoros.', 13),
(2, 'Tecnológica', 'Digital', 2, 0, 'Softwares ineficientes.', 5);


INSERT INTO Comment (user_pk, report_pk, content) VALUES
(1, 1, 'Concordo, precisamos de mais fiscalização.'),
(2, 2, 'Relato importante.'),
(3, 3, 'Isso acontece sempre!'),
(1, 4, 'Já denunciei isso também.'),
(2, 5, 'Muito grave essa situação.');


INSERT INTO BarrierScenario_Like (user_fk, scenario_fk) VALUES
(1, 1),
(2, 3),
(3, 5),
(1, 7),
(2, 9);
