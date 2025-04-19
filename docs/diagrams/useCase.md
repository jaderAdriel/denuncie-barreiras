
---

## 🧾 Casos de Uso – Descrição

---

### ✅ **UC0 – Fazer Login**
**Ator Principal:** Aluno, Funcionário Escolar  
**Pré-condições:** O usuário deve estar cadastrado no sistema.  
**Fluxo Principal:**
1. O usuário acessa a tela de login.
2. Informa seu login e senha.
3. O sistema valida as credenciais.
4. O sistema concede acesso às funcionalidades de acordo com o perfil do usuário.  
   **Fluxo Alternativo:**
- 3a. Credenciais inválidas → exibir mensagem de erro e solicitar nova tentativa.  
  **Pós-condição:** Usuário autenticado no sistema.

---

### ✅ **UC1 – Realizar Denúncia**
**Ator Principal:** Aluno  
**Pré-condição:** Usuário autenticado  
**Fluxo Principal:**
1. O aluno acessa a funcionalidade para registrar uma denúncia.
2. Informa os dados da ocorrência (ambiente, tipo de barreira, severidade, descrição etc.).
3. Escolhe se deseja manter o anonimato.
4. Informa os dados da vítima (se aplicável).
5. Registra a denúncia.  
   **Inclusões:** UC3 – Registrar Testemunha  
   **Extensões:** UC2 – Anexar Evidências  
   **Pós-condição:** Denúncia registrada no sistema.

---

### ✅ **UC2 – Anexar Evidências**
**Ator Principal:** Aluno  
**Pré-condição:** Denúncia iniciada  
**Fluxo Principal:**
1. O aluno seleciona a opção de anexar evidências.
2. Escolhe o tipo de evidência (imagem, vídeo, captura de tela).
3. Anexa os arquivos e descreve-os.
4. Confirma o envio.  
   **Pós-condição:** Evidência vinculada à denúncia.

---

### ✅ **UC3 – Registrar Testemunha**
**Ator Principal:** Aluno  
**Pré-condição:** Denúncia iniciada  
**Fluxo Principal:**
1. O aluno acessa a seção de testemunhas.
2. Informa os dados da testemunha (nome, contato, relato, data).
3. Indica se o depoimento é anônimo.
4. Confirma o registro da testemunha.  
   **Pós-condição:** Testemunha associada à denúncia.

---

### ✅ **UC4 – Tramitar Denúncia**
**Ator Principal:** Funcionário Escolar  
**Pré-condição:** Denúncia registrada  
**Fluxo Principal:**
1. O funcionário escolar visualiza denúncias recebidas.
2. Seleciona uma denúncia e define o departamento de destino.
3. Altera o status da denúncia para "encaminhado" ou "finalizado".
4. Encaminha para o próximo responsável.  
   **Inclusões:** UC5 – Emitir Parecer  
   **Pós-condição:** Denúncia tramitada para outro departamento.

---

### ✅ **UC5 – Emitir Parecer**
**Ator Principal:** Funcionário Escolar  
**Pré-condição:** Denúncia tramitada para o departamento  
**Fluxo Principal:**
1. O funcionário acessa a denúncia recebida.
2. Analisa os dados, evidências e testemunhos.
3. Registra o parecer descritivo.
4. Finaliza o trâmite, alterando o status para "finalizado".  
   **Pós-condição:** Parecer registrado e denúncia encerrada.

---

### ✅ **UC6 – Gerenciar Departamentos**
**Ator Principal:** Funcionário Escolar  
**Pré-condição:** Usuário autenticado com permissão  
**Fluxo Principal:**
1. O funcionário acessa a tela de gerenciamento de departamentos.
2. Visualiza lista de departamentos existentes.
3. Pode adicionar, editar ou remover membros.
4. Define o responsável por cada departamento.  
   **Pós-condição:** Departamentos atualizados.

---

### ✅ **UC7 – Consultar Status**
**Ator Principal:** Aluno  
**Pré-condição:** Usuário autenticado  
**Fluxo Principal:**
1. O aluno acessa a tela de acompanhamento.
2. Visualiza as denúncias registradas por ele.
3. Consulta o status atual de cada denúncia (recebido, encaminhado, finalizado).  
   **Pós-condição:** Aluno informado sobre o andamento de suas denúncias.

---
