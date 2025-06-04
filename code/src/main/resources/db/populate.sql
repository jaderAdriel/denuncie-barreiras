-- Inserindo 25 denúncias de barreiras atitudinais
INSERT INTO Report (reporter_fk, type, ambient, anonymous_report, event_detailing, related_scenario_fk, published) VALUES
-- Denúncias em Sala de Aula
(2, 'PREJUDICE', 'CLASSROOM', 0, 'O professor frequentemente ignora alunos com deficiência visual, nunca lendo em voz alta os conteúdos escritos no quadro.', NULL, 1),
(3, 'ABLEISM', 'CLASSROOM', 1, 'Colegas de classe riem e imitam a fala de um aluno com gagueira durante apresentações orais.', 2, 1),
(4, 'UNEQUAL_TREATMENT', 'CLASSROOM', 0, 'Professora dá menos tempo para alunos com TDAH completarem as provas, mesmo com laudo médico.', NULL, 1),
(5, 'PEJORATIVE_LANGUAGE', 'CLASSROOM', 1, 'Monitor da turma chama alunos com dificuldade de aprendizado de "burros" e "incapazes".', 6, 0),
(6, 'EXCLUSION', 'CLASSROOM', 0, 'Grupos de trabalho sistematicamente excluem colegas com autismo das atividades.', NULL, 1),

-- Denúncias na Biblioteca
(7, 'DISCRIMINATION', 'LIBRARY', 1, 'Bibliotecária impede aluno com síndrome de Down de acessar computadores, dizendo que ele não sabe usar.', NULL, 1),
(8, 'BULLYING', 'LIBRARY', 0, 'Colegas escondem constantemente os livros em braille do aluno cego na biblioteca.', 2, 1),
(9, 'VERBAL_VIOLENCE', 'LIBRARY', 1, 'Funcionário grita com aluna com deficiência auditiva por não ouvir seus chamados.', NULL, 0),

-- Denúncias no Laboratório
(2, 'ABLEISM', 'LABORATORY', 0, 'Professor de química diz que aluno com deficiência física não deveria estar no curso por não poder fazer todos os experimentos.', NULL, 1),
(3, 'EXCLUSION', 'LABORATORY', 1, 'Grupo exclui colega com mobilidade reduzida de trabalho prático alegando que "atrapalharia".', 6, 1),

-- Denúncias nos Corredores
(4, 'BULLYING', 'HALLWAY', 0, 'Alunos imitam e zoam a forma de andar de colega com paralisia cerebral nos corredores.', NULL, 1),
(5, 'PHYSICAL_VIOLENCE', 'HALLWAY', 1, 'Empurrões deliberados em aluno com deficiência visual que usa bengala.', 2, 1),

-- Denúncias na Quadra
(6, 'EXCLUSION', 'COURT', 0, 'Professor de educação física sempre deixa aluno com deficiência física de fora das atividades sem adaptações.', NULL, 1),
(7, 'PEJORATIVE_LANGUAGE', 'COURT', 1, 'Alunos chamam colega com sobrepeso de "baleia" durante aulas de educação física.', NULL, 0),

-- Denúncias no Pátio
(8, 'BULLYING', 'PATIO', 0, 'Alunos fingem ser amigos de colega com deficiência intelectual para tirar sarro dele no pátio.', 6, 1),
(9, 'DISCRIMINATION', 'PATIO', 1, 'Monitor do recreio proíbe aluno com autismo de brincar no balanço por "se balançar diferente".', NULL, 1),

-- Denúncias no Banheiro
(2, 'PHYSICAL_VIOLENCE', 'BATHROOM', 0, 'Aluno com deficiência é trancado repetidamente no banheiro pelos colegas.', NULL, 1),
(3, 'BULLYING', 'BATHROOM', 1, 'Notas ofensivas sobre a aparência de aluna com vitiligo são deixadas no espelho do banheiro.', 2, 0),

-- Denúncias no Refeitório
(4, 'EXCLUSION', 'CAFETERIA', 0, 'Mesas ficam vazias quando aluno com síndrome de Tourette se senta para almoçar.', NULL, 1),
(5, 'PREJUDICE', 'CAFETERIA', 1, 'Funcionários do refeitório servem porções menores para alunos com deficiência, achando que "comem menos".', 6, 1),

-- Denúncias no Transporte Escolar
(6, 'ABLEISM', 'SCHOOL_TRANSPORT', 0, 'Motorista do ônibus escolar se recusa a usar a cadeira de rodas adaptada, fazendo aluno ser carregado.', NULL, 1),
(7, 'VERBAL_VIOLENCE', 'SCHOOL_TRANSPORT', 1, 'Cobrador do ônibus xinga aluno com deficiência auditiva por não ouvir quando deve descer.', 2, 1),

-- Denúncias em Outros Ambientes
(8, 'DISCRIMINATION', 'OTHER', 0, 'Coordenadora pedagógica diz que escola "não está preparada" para matricular aluno com deficiência múltipla.', NULL, 0),
(9, 'UNEQUAL_TREATMENT', 'OTHER', 1, 'Eventos escolares nunca têm intérprete de LIBRAS ou materiais acessíveis.', 6, 1);

-- Inserindo reviews para as denúncias (avaliações dos moderadores)
INSERT INTO Review (report_fk, author_fk, comment, is_valid) VALUES
-- Reviews para denúncia 1
(6, 1, 'Denúncia verificada. O professor foi notificado e será oferecida capacitação em educação inclusiva.', 1),
-- Reviews para denúncia 2
(7, 1, 'Situação grave confirmada. Serão realizadas palestras sobre respeito às diferenças na turma.', 1),
-- Reviews para denúncia 3
(8, 1, 'Denúncia em análise. Solicitamos cópia do laudo médico para tomar as providências adequadas.', 0),
-- Reviews para denúncia 4
(9, 1, 'Monitor foi chamado para esclarecimentos e será reavaliada sua permanência na função.', 1),
-- Reviews para denúncia 5
(10, 1, 'Programa de inclusão será implementado com formação de grupos heterogêneos supervisionados.', 1),
-- Reviews para denúncia 6
(10, 1, 'Bibliotecária será capacitada. Aluno terá acompanhante para uso dos computadores.', 1),
-- Reviews para denúncia 7
(11, 1, 'Caso encaminhado para coordenação. Alunos envolvidos serão orientados.', 1),
-- Reviews para denúncia 8
(12, 1, 'Funcionário receberá treinamento em LIBRAS e atendimento a pessoas com deficiência auditiva.', 1),
-- Reviews para denúncia 9
(13, 1, 'Denúncia grave. Professor será chamado para reunião disciplinar.', 1),
-- Reviews para denúncia 10
(14, 1, 'Atividades adaptadas serão implementadas. Aluno não pode ser excluído.', 1),
-- Reviews para denúncia 11
(15, 1, 'Alunos identificados e pais serão chamados. Medidas disciplinares aplicadas.', 1),
-- Reviews para denúncia 12
(16, 1, 'Caso encaminhado para diretoria. Pode caracterizar crime de injúria.', 1),
-- Reviews para denúncia 13
(17, 1, 'Professor será orientado sobre adaptações necessárias na educação física.', 1),
-- Reviews para denúncia 14
(18, 1, 'Denúncia em análise. Necessário apurar identidade dos alunos.', 0),
-- Reviews para denúncia 15
(19, 1, 'Programa de conscientização sobre autismo será implementado na escola.', 1),
-- Reviews para denúncia 16
(20, 1, 'Monitor receberá orientação. Aluno tem direito de brincar como preferir.', 1),
-- Reviews para denúncia 17
(21, 1, 'Caso grave. Alunos envolvidos suspensos e pais notificados.', 1),
-- Reviews para denúncia 18
(28, 1, 'Ações educativas sobre vitiligo serão realizadas na escola.', 1),
-- Reviews para denúncia 19
(23, 1, 'Projeto de inclusão no horário do almoço será desenvolvido.', 1),
-- Reviews para denúncia 20
(24, 1, 'Funcionários receberão treinamento. Portaria determina porções iguais para todos.', 1),
-- Reviews para denúncia 21
(25, 1, 'Transporte será adaptado. Motorista receberá advertência formal.', 1),
-- Reviews para denúncia 22
(26, 1, 'Cobrador será substituído. Empresa de transporte notificada.', 1),
-- Reviews para denúncia 23
(27, 1, 'Escola tem obrigação legal de se adaptar. Denúncia encaminhada à secretaria de educação.', 1),
-- Reviews para denúncia 24
(28, 1, 'Solicitada verba para recursos de acessibilidade nos eventos.', 1),
-- Reviews para denúncia 25
(29, 1, 'Denúncia em análise. Necessário apurar detalhes com a família.', 0);