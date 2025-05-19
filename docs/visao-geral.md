# Documentação do Sistema de Denúncias de Barreiras Atitudinais

## Visão Geral do Sistema

O sistema foi projetado para mapear, registrar e combater barreiras atitudinais - comportamentos, práticas ou discursos discriminatórios que limitam a participação plena de indivíduos na sociedade. Ele permite:

1. **Conscientização**: Através de cenários exemplares de barreiras atitudinais
2. **Denúncia**: Registro formal de incidentes discriminatórios reais
3. **Engajamento**: Interação de usuários através de comentários e curtidas

## Diagrama de Classes - Explicação

O diagrama apresenta 9 classes principais:

### Classes Principais
- **User**: Classe base para todos os usuários do sistema
- **Moderator**: Usuário com privilégios para criar cenários educativos
- **BarrierScenario**: Casos-modelo de barreiras atitudinais
- **Report**: Registros de ocorrências reais
- **Law**: Legislações relacionadas às barreiras
- **Comment**: Interações dos usuários
- **Attachment**: Arquivos complementares
- **Address**: Informações de endereço
- **ReportReview**: Análises de denúncias por moderadores

## Dicionário de Dados

### 1. User
| Atributo       | Tipo | Descrição                                    | Obrigatório |
|----------------|------|----------------------------------------------|-------------|
| id             | Long | Identificador único                          | Sim |
| name           | String | Nome completo                                | Sim |
| email          | String | E-mail válido                                | Sim |
| birthDate      | LocalDate | Data de nascimento                           | Sim |
| gender         | GenderEnum | Identidade de gênero                         | Sim |
| password       | String | Senha criptografada                          | Sim |
| username       | String | Nome de usuário único                        | Sim |

Métodos:
- authenticate(): Valida credenciais
- changePassword(): Atualiza senha
- reportIncident(): Inicia processo de denúncia

### 2. Moderator
| Atributo | Tipo | Descrição | Obrigatório |
|----------|------|-----------|-------------|
| phone    | String | Contato principal | Sim |

Herda todos atributos e métodos de User

### 3. BarrierScenario
| Atributo | Tipo | Descrição | Obrigatório |
|----------|------|-----------|-------------|
| type     | AttitudinalBarrierType | Classificação da barreira | Sim |
| author   | Moderator | Criador do cenário | Sim |
| content  | Text | Descrição detalhada | Sim |
| title    | String | Título resumido | Sim |
| creationDate | LocalDate | Data de registro | Sim |
| likes    | List<User> | Usuários que aprovaram | Não |
| comments | List<Comment> | Comentários associados | Não |

Métodos:
- addComment(): Adiciona novo comentário

### 4. Report
| Atributo | Tipo | Descrição | Obrigatório |
|----------|------|-----------|-------------|
| type     | AttitudinalBarrierType | Classificação | Sim |
| environment | EnvironmentTypeEnum | Local da ocorrência | Sim |
| incidentLocation | Address | Dados geográficos | Sim |
| severity | Integer | Nível de gravidade | Sim |
| anonymous | Boolean | Identidade oculta | Sim |
| incidentDetails | Text | Relato completo | Sim |
| relatedScenario | BarrierScenario | Cenário inspirador | Não |
| reporter | User | Autor da denúncia | Não |

### 5. Law
| Atributo | Tipo | Descrição | Obrigatório |
|----------|------|-----------|-------------|
| code     | String | Identificação legal | Sim |
| date     | String | Data de promulgação | Sim |
| link     | URL | Acesso ao texto completo | Sim |
| title    | String | Nome popular | Sim |
| description | Text | Resumo do conteúdo | Sim |

### 6. Comment
| Atributo | Tipo | Descrição | Obrigatório |
|----------|------|-----------|-------------|
| dateTime | DateTime | Data e hora | Sim |
| content  | Text | Texto do comentário | Sim |
| parentComment | Comment | Resposta a outro comentário | Não |
| author   | User | Autor do comentário | Sim |

### 7. Attachment
| Atributo | Tipo | Descrição | Obrigatório |
|----------|------|-----------|-------------|
| fileName | String | Nome original | Sim |
| fileType | String | Extensão/Formato | Sim |
| filePath | String | Local de armazenamento | Sim |
| uploadDate | LocalDateTime | Data/hora do upload | Sim |

### 8. Address
| Atributo | Tipo | Descrição | Obrigatório |
|----------|------|-----------|-------------|
| city     | String | Cidade | Sim |
| state    | String | Estado | Sim |
| street   | String | Rua | Sim |
| zipCode  | String | CEP | Sim |

### 9. ReportReview
| Atributo | Tipo | Descrição | Obrigatório |
|----------|------|-----------|-------------|
| description | String | Análise detalhada | Sim |
| date     | LocalDate | Data da análise | Sim |
| finalStatus | ReportStatus | Status final | Sim |
| author   | Moderator | Moderador responsável | Sim |


## **Relacionamentos Entre Classes**

| **Classe Origem**       | **Cardinalidade** | **Classe Destino**       | **Tipo de Relacionamento** | **Descrição** |
|-------------------------|------------------|--------------------------|---------------------------|---------------|
| **User**                | 1 → N            | **Report**               | Associação                | Um usuário pode submeter múltiplas denúncias. |
| **Moderator**           | 1 → 0..*         | **BarrierScenario**      | Associação                | Um moderador pode criar vários cenários de barreira. |
| **Moderator**           | 1 → 1            | **Address**              | Composição (`*--`)        | Um moderador tem um endereço cadastrado. |
| **Report**              | 1 → 0..*         | **BarrierScenario**      | Associação (`--`)         | Uma denúncia pode estar relacionada a um cenário (opcional). |
| **BarrierScenario**     | 1 → 0..*         | **Comment**              | Agregação (`*--`)         | Um cenário pode ter vários comentários. |
| **BarrierScenario**     | 1 → 0..*         | **Attachment**           | Agregação (`*--`)         | Um cenário pode ter múltiplos anexos (imagens, documentos). |
| **Report**              | 1 → 0..*         | **Attachment**           | Agregação (`*--`)         | Uma denúncia pode incluir vários anexos (provas). |
| **Report**              | 1 → N            | **ReportReview**         | Associação (`-*`)         | Uma denúncia pode ter várias análises de moderadores. |
| **Moderator**           | 1 → N            | **ReportReview**         | Associação (`--`)         | Um moderador pode revisar várias denúncias. |
| **Comment**             | 1 → N            | **Comment** (self)       | Auto-relacionamento       | Um comentário pode ter respostas (hierarquia). |
| **User**                | 1 → N            | **Comment**              | Associação (`--`)         | Um usuário pode fazer vários comentários. |
| **Law**                 | N → M            | **BarrierScenario**      | Associação (`--`)         | Leis podem estar vinculadas a múltiplos cenários (e vice-versa). |

---

### **Observações sobre os Relacionamentos**

[//]: # (1. **Agregação &#40;`*--`&#41; vs. Composição &#40;`--*`&#41;**)

[//]: # (   - **Agregação** &#40;ex: `BarrierScenario *-- Attachment`&#41;: Os anexos podem existir independentemente do cenário.)

[//]: # (   - **Composição** &#40;ex: `Moderator *-- Address`&#41;: O endereço não existe sem o moderador &#40;dependência forte&#41;.)

1. **Cardinalidades**
   - `1 → N`: Um para muitos (ex: Um `User` tem N `Reports`).
   - `0..*`: Opcional e múltiplo (ex: Um `Report` pode ter 0 ou N `Attachments`).

2. **Auto-relacionamento**
   - `Comment` pode responder a outro `Comment` (árvore de comentários).
---

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