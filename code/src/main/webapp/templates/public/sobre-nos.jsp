<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset='utf-8'>
    <meta http-equiv='X-UA-Compatible' content='IE=edge'>
    <title>Denuncie Barreiras - Sobre Nós</title>
    <meta name='viewport' content='width=device-width, initial-scale=1'>
    <%-- Base href ajustado para considerar o contextPath da aplicação --%>
    <base href="<%= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/" %>">
    <script src="https://cdn.tailwindcss.com"></script>
    <link href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined" rel="stylesheet">
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/static/images/logo.png" type="image/x-icon">
    <link rel='stylesheet' type='text/css' media='screen' href='${pageContext.request.contextPath}/static/css/public/style.css'>
    <%-- scenario.css pode não ser necessário aqui, mas mantido se for um CSS global --%>
    <link rel='stylesheet' type='text/css' media='screen' href='${pageContext.request.contextPath}/static/css/public/scenario.css'>
    <style>
        .material-symbols-outlined {
            font-variation-settings: 'FILL' 0,'wght' 400,'GRAD' 0,'opsz' 24;
            font-family: 'Material Symbols Outlined';
            vertical-align: middle;
        }
    </style>
</head>
<body class="bg-gray-50">
<div class="wrapper">

    <%@ include file="parts/_header.jsp"%>

    <main class="flex-grow container mx-auto px-4 py-10">

        <section class="p-4 w-full">
            <div class="text-center mb-10">
                <h1 class="text-4xl md:text-5xl font-bold text-gray-800 mb-4">Sobre Nós</h1>
                <p class="text-lg text-gray-600">Conheça a equipe por trás do sistema "Denuncie Barreiras".</p>
            </div>

            <!-- Visão Geral do Projeto -->
            <div class="bg-white rounded-lg shadow-md p-6 md:p-8 mb-10">
                <h2 class="text-2xl font-bold text-gray-800 mb-4 flex items-center">
                    <span class="material-symbols-outlined text-blue-600 mr-2">info</span> Visão Geral do Projeto
                </h2>
                <p class="text-gray-700 leading-relaxed mb-4">
                    Este sistema foi desenvolvido com o propósito de ser uma plataforma robusta para o recebimento e
                    monitoramento de denúncias sobre barreiras atitudinais. Nosso foco principal é a promoção da
                    acessibilidade e a garantia dos direitos das pessoas com deficiência no ambiente escolar.
                </p>
                <p class="text-gray-700 leading-relaxed mb-4">
                    As barreiras atitudinais, que incluem comportamentos negativos como discriminação, bullying e exclusão,
                    são obstáculos significativos para a participação plena de indivíduos com deficiência. A plataforma
                    "Denuncie Barreiras" permite que membros da comunidade escolar (alunos, professores, pais) registrem
                    ocorrências de forma anônima, categorizadas por tipo, local e gravidade.
                </p>
                <p class="text-gray-700 leading-relaxed">
                    As denúncias são encaminhadas à gestão escolar para análise, permitindo o acompanhamento do status,
                    registro de pareceres e a adoção de medidas corretivas. Os usuários recebem notificações sobre o
                    andamento dos casos e têm acesso a um painel completo com o histórico de suas denúncias.
                </p>
            </div>

            <!-- Seção da Equipe -->
            <div class="bg-white rounded-lg shadow-md p-6 md:p-8 mb-10">
                <h2 class="text-2xl font-bold text-gray-800 mb-6 flex items-center">
                    <span class="material-symbols-outlined text-purple-600 mr-2">group</span> A Equipe
                </h2>
                <p class="text-gray-700 mb-6">
                    Este projeto foi idealizado e desenvolvido como uma atividade avaliativa, fruto do trabalho colaborativo de um grupo dedicado de estudantes.
                </p>
                <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-6">
                    <!-- Membro 1 -->
                    <div class="flex flex-col items-center text-center p-4 bg-gray-50 rounded-lg shadow-sm">
                        <img class="w-24 h-24 rounded-full mb-3 object-cover" src="https://avatars.githubusercontent.com/u/101850276?v=4" alt="Avatar Jader Adriel">
                        <h3 class="text-xl font-semibold text-gray-800">Jader Adriel Miranda Souza</h3>
                        <p class="text-gray-600">Desenvolvedor</p>
                    </div>
                    <!-- Membro 2 -->
                    <div class="flex flex-col items-center text-center p-4 bg-gray-50 rounded-lg shadow-sm">
                        <img class="w-24 h-24 rounded-full mb-3 object-cover" src="https://avatars.githubusercontent.com/u/187862564?v=4" alt="Avatar Sávio Lopes">
                        <h3 class="text-xl font-semibold text-gray-800">Sávio Kauan Silveira Lopes</h3>
                        <p class="text-gray-600">Desenvolvedor</p>
                    </div>
                    <!-- Membro 3 -->
                    <div class="flex flex-col items-center text-center p-4 bg-gray-50 rounded-lg shadow-sm">
                        <img class="w-24 h-24 rounded-full mb-3 object-cover" src="https://avatars.githubusercontent.com/u/140507701?v=4" alt="Avatar Samara Mercês">
                        <h3 class="text-xl font-semibold text-gray-800">Samara Mercês Pereira</h3>
                        <p class="text-gray-600">Desenvolvedora</p>
                    </div>
                    <!-- Membro 4 -->
                    <div class="flex flex-col items-center text-center p-4 bg-gray-50 rounded-lg shadow-sm">
                        <img class="w-24 h-24 rounded-full mb-3 object-cover" src="https://avatars.githubusercontent.com/u/140507701?v=4" alt="Avatar Ana Rode">
                        <h3 class="text-xl font-semibold text-gray-800">Ana Rode Rodrigues dos Santos</h3>
                        <p class="text-gray-600">Desenvolvedora</p>
                    </div>
                </div>
                <p class="text-gray-700 mt-6 text-center">
                    A colaboração e a dedicação de cada membro foram essenciais para a concretização desta iniciativa.
                </p>
            </div>

            <!-- Seção Professor e Disciplina -->
            <div class="bg-white rounded-lg shadow-md p-6 md:p-8">
                <h2 class="text-2xl font-bold text-gray-800 mb-6 flex items-center">
                    <span class="material-symbols-outlined text-green-600 mr-2">school</span> Contexto Acadêmico
                </h2>
                <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
                    <div>
                        <p class="info-label text-gray-700">Professor Orientador</p>
                        <div class="info-value text-gray-900 text-lg font-semibold bg-gray-50 p-3 rounded-md">
                            Woquiton Lima Fernandes
                        </div>
                    </div>
                    <div>
                        <p class="info-label text-gray-700">Disciplina</p>
                        <div class="info-value text-gray-900 text-lg font-semibold bg-gray-50 p-3 rounded-md">
                            Linguagem de Programação Orientada a Objetos (POO)
                        </div>
                    </div>
                </div>
                <p class="text-gray-700 mt-6">
                    Este projeto foi desenvolvido como uma atividade avaliativa da disciplina de Linguagem de Programação Orientada a Objetos,
                    buscando aplicar conceitos teóricos na construção de uma solução prática com impacto social relevante.
                </p>
            </div>

        </section>

    </main>

    <%@ include file="parts/_footer.jsp" %>
</div>
</body>
</html>