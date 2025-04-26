# Documentação do Sistema de Denúncias de Barreiras Atitudinais

## Visão Geral do Sistema

O sistema foi projetado para mapear, registrar e combater barreiras atitudinais - comportamentos, práticas ou discursos discriminatórios que limitam a participação plena de indivíduos na sociedade. Ele permite:

1. **Conscientização**: Através de cenários exemplares de barreiras atitudinais
2. **Denúncia**: Registro formal de incidentes discriminatórios reais
3. **Engajamento**: Interação de usuários através de comentários e curtidas

## Diagrama de Classes - Explicação

O diagrama apresenta 7 classes principais:

### Classes Principais
- **Usuario**: Classe base para todos os usuários do sistema
- **Moderador**: Usuário com privilégios para criar cenários educativos
- **CenarioBarreira**: Casos-modelo de barreiras atitudinais
- **Denuncia**: Registros de ocorrências reais
- **Lei**: Legislações relacionadas às barreiras
- **Comentario**: Interações dos usuários
- **Anexo**: Arquivos complementares

## Dicionário de Dados

### 1. Usuario
| Atributo | Tipo | Descrição | Obrigatório |
|----------|------|-----------|-------------|
| id | Long | Identificador único | Sim |
| nome | String | Nome completo | Sim |
| email | String | E-mail válido | Sim |
| dataNascimento | LocalDate | Data de nascimento | Sim |
| genero | GeneroEnum | Identidade de gênero | Sim |
| senha | String | Senha criptografada | Sim |
| login | String | Nome de usuário único | Sim |

Métodos:
- autenticar(): Valida credenciais
- alterarSenha(): Atualiza senha
- realizarDenuncia(): Inicia processo de denúncia

### 2. Moderador
| Atributo | Tipo | Descrição | Obrigatório |
|----------|------|-----------|-------------|
| telefone | String | Contato principal | Sim |
| 2Factor | boolean | Autenticação em dois fatores | Sim |

Métodos:
- autenticar(): Valida credenciais com fatores adicionais

### 3. CenarioBarreira
| Atributo | Tipo | Descrição | Obrigatório |
|----------|------|-----------|-------------|
| tipo | TipoBarreiraAtitudinal | Classificação da barreira | Sim |
| autor | Moderador | Criador do cenário | Sim |
| conteudo | Text | Descrição detalhada | Sim |
| titulo | String | Título resumido | Sim |
| dataCriacao | LocalDate | Data de registro | Sim |
| curtidas | List<Usuario> | Usuários que aprovaram | Não |

### 4. Denuncia
| Atributo | Tipo | Descrição | Obrigatório |
|----------|------|-----------|-------------|
| tipo | TipoBarreiraAtitudinal | Classificação | Sim |
| ambiente | TipoAmbienteEnum | Local da ocorrência | Sim |
| localizacaoOcorrido | Endereco | Dados geográficos | Sim |
| severidade | Integer | Nível de gravidade (1-5) | Sim |
| denunciaAnonima | Boolean | Identidade oculta | Sim |
| detalhamentoOcorrido | Text | Relato completo | Sim |
| cenarioRelacionado | CenarioBarreira | Cenário inspirador | Não |
| denunciante | Usuario | Autor da denúncia | Não |

### 5. Lei
| Atributo | Tipo | Descrição | Obrigatório |
|----------|------|-----------|-------------|
| codigo | String | Identificação legal | Sim |
| data | String | Data de promulgação | Sim |
| link | URL | Acesso ao texto completo | Sim |
| titulo | String | Nome popular | Sim |
| descricao | Text | Resumo do conteúdo | Sim |

### 6. Comentario
| Atributo | Tipo | Descrição | Obrigatório |
|----------|------|-----------|-------------|
| dataHora | DateTime | Data e hora | Sim |
| conteudo | Text | Texto do comentário | Sim |
| comentarioPai | Comentario | Resposta a outro comentário | Não |

### 7. Anexo
| Atributo | Tipo | Descrição | Obrigatório |
|----------|------|-----------|-------------|
| nomeArquivo | String | Nome original | Sim |
| tipo | String | Extensão/Formato | Sim |
| caminho | String | Local de armazenamento | Sim |
| dataEnvio | LocalDateTime | Data/hora do upload | Sim |

## Enums Utilizados

1. **GeneroEnum**: MASCULINO, FEMININO, NAO_BINARIO, OUTRO
2. **TipoBarreiraAtitudinal**: MISOGINIA, RACISMO, HOMOFOBIA, LESBOFOBIA, TRANSFOBIA, CAPACITISMO, ETARISMO, OUTRO
3. **TipoAmbienteEnum**: TRABALHO, ESCOLA, UNIVERSIDADE, ESPACO_PUBLICO, TRANSPORTE, ONLINE, DOMICILIO, OUTRO

## Fluxos Principais

1. **Criação de Cenário Educativo**
   - Moderador identifica padrão discriminatório
   - Cria CenarioBarreira com exemplos genéricos
   - Vincula leis pertinentes

2. **Registro de Denúncia**
   - Usuário vivencia situação
   - Consulta cenários relacionados
   - Preenche Denuncia com detalhes
   - Anexa anexos (opcional)

3. **Interação Comunitária**
   - Usuários comentam em Cenarios
   - Curtem conteúdos relevantes
   - Debatem soluções


## Regras de Negócio

1. Todo CenarioBarreira deve ter pelo menos um tipo de barreira definido
2. Denúncias anônimas ocultam automaticamente o denunciante
3. Apenas moderadores podem criar CenariosBarreira
4. Um comentário pode ter múltiplas respostas (árvore de discussão)
5. Cada denúncia pode referenciar um cenário relacionado, mas não é obrigatório



Este sistema promove a conscientização sobre barreiras atitudinais enquanto oferece um canal seguro para denúncias e engajamento comunitário na busca por soluções.