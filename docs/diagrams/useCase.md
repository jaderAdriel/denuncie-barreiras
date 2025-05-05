## Documentação do Diagrama de Caso de Uso

Este documento descreve o diagrama de caso de uso apresentado, detalhando os atores, casos de uso e seus relacionamentos. O diagrama visa representar as funcionalidades de um sistema, provavelmente uma plataforma online focada em denúncias, cenários educativos e legislação relacionada.

**1. Visão Geral**

O diagrama de caso de uso representa um sistema dividido em quatro grandes áreas:

*   **Autenticação:** Gerencia o acesso seguro ao sistema.
*   **Denúncia:** Permite que usuários reportem incidentes ou irregularidades.
*   **Cenários Educativos:** Oferece recursos educacionais relacionados aos temas abordados nas denúncias.
*   **Leis:** Permite o acesso e consulta de legislação relevante.

**2. Atores**

O diagrama identifica dois atores principais:

*   **Usuário:** É o ator principal, representando qualquer indivíduo que interage com o sistema para realizar denúncias, consultar informações e interagir com os cenários educativos.
*   **Moderador:** Possui privilégios adicionais para gerenciar o conteúdo do sistema, como criar cenários educativos e vincular leis a denúncias.

**3. Casos de Uso**

Cada caso de uso representa uma funcionalidade específica do sistema.

**3.1. Autenticação**

*   **UC0: Autenticar no Sistema:** Permite que os usuários e moderadores façam login no sistema, verificando suas credenciais e concedendo acesso às funcionalidades permitidas. Este caso de uso é um pré-requisito para a maioria das interações.

**3.2. Denúncia**

*   **UC1: Realizar Denúncia:** Permite que os usuários registrem denúncias, fornecendo informações detalhadas sobre o incidente ou irregularidade.
*   **UC2: Anexar Arquivos:** Permite que os usuários adicionem arquivos (imagens, vídeos, documentos, etc.) às suas denúncias como evidência.
*   **UC3: Consultar Denúncia:** Permite que usuários visualizem o status e detalhes das suas próprias denúncias.
*   **UC10: Vincular Cenário à Denúncia:** Permite que tanto o usuário ao criar uma denúncia, quanto o usuário ao consultar a denúncia, vincule um cenário educativo já existente a denúncia.

**3.3. Cenários Educativos**

*   **UC4: Consultar Cenários:** Permite que usuários e moderadores pesquisem e visualizem cenários educativos disponíveis.
*   **UC6: Curtir Cenário:** Permite que usuários e moderadores curtam cenários educativos para mostrar seu apoio ou interesse.
*   **UC7: Criar Cenário Educativo:** Permite que moderadores criem novos cenários educativos, adicionando conteúdo, informações e recursos relevantes.
*   **UC8: Vincular Lei ao Cenário:** Permite que moderadores vinculem leis específicas aos cenários educativos, relacionando o conteúdo educativo ao arcabouço legal.
*   **UC11: Comentar Cenário:** Permite que usuários e moderadores comentem nos cenários educativos, criando um espaço para discussões e feedback.

**3.4. Leis**

*   **UC9: Consultar Leis Relacionadas:** Permite que usuários e moderadores pesquisem e visualizem leis relacionadas aos temas abordados nas denúncias e cenários educativos.

**4. Relacionamentos**

O diagrama utiliza diferentes tipos de relacionamentos para representar a interação entre os atores e os casos de uso:

*   **Associação:** Representada por uma linha sólida, indica que um ator participa de um caso de uso (por exemplo, o Usuário pode Realizar Denúncia).
*   **<<include>>:** Representada por uma linha tracejada com a etiqueta `<<include>>`, indica que um caso de uso depende obrigatoriamente da execução de outro (por exemplo, Realizar Denúncia *inclui* Autenticar no Sistema).  Isso significa que antes de realizar uma denúncia, o usuário precisa estar autenticado.
*   **<<extend>>:** Representada por uma linha tracejada com a etiqueta `<<extend>>`, indica que um caso de uso pode opcionalmente estender a funcionalidade de outro (por exemplo, Realizar Denúncia *estende* Anexar Arquivos).  Isso significa que um usuário *pode* anexar arquivos ao realizar uma denúncia, mas não é obrigatório.

**5. Notas**

As notas no diagrama fornecem informações adicionais sobre as permissões de cada ator:

*   **Usuário:** Tem permissão para realizar denúncias, consultar informações e interagir com os cenários educativos.
*   **Moderador:** Tem todas as permissões do usuário, além da capacidade de criar cenários educativos e vincular leis a cenários.

**6. Conclusão**

O diagrama de caso de uso fornece uma visão geral clara e concisa das funcionalidades do sistema e das interações entre os atores e os casos de uso. Ele serve como um guia para o desenvolvimento e teste do sistema, garantindo que todas as funcionalidades sejam implementadas corretamente e que os atores possam realizar suas tarefas de forma eficiente.
