<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <meta charset='utf-8'>
  <meta http-equiv='X-UA-Compatible' content='IE=edge'>
  <title>Denuncie Barreiras - Cenários Educativos</title>
  <meta name='viewport' content='width=device-width, initial-scale=1'>
  <%-- Base href ajustado para considerar o contextPath da aplicação --%>
  <base href="<%= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/" %>">
  <script src="https://cdn.tailwindcss.com"></script>
  <link href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined" rel="stylesheet">
  <link rel="shortcut icon" href="${pageContext.request.contextPath}/static/images/logo.png" type="image/x-icon">
  <link rel='stylesheet' type='text/css' media='screen' href='${pageContext.request.contextPath}/static/css/public/style.css'>
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
<div class="wrapper ">

  <%@ include file="parts/_header.jsp"%>

  <section class="bg-white">
    <div class="py-8 px-4 mx-auto max-w-screen-xl sm:py-16 lg:px-6">
      <div class="mx-auto max-w-screen-sm text-center">
        <h2 class="mb-4 text-4xl tracking-tight font-extrabold leading-tight text-gray-900">Cenários Educativos</h2>
        <p class="mb-6 font-light text-gray-600 md:text-lg">
          Explore nossos cenários educativos para conscientização e reflexão sobre barreiras no cotidiano.
        </p>
      </div>
    </div>
  </section>

  <%-- O formulário agora envolve todo o conteúdo principal (sidebar + área de posts) --%>
  <main class="flex-grow container mx-auto px-4 py-10">
    <form method="GET" action="${pageContext.request.contextPath}/scenario/" class="flex flex-col md:flex-row gap-8">

      <!-- Sidebar de Filtros -->
      <aside class="w-full md:w-64 flex-shrink-0">
        <div class="bg-white p-5 rounded-xl shadow-sm border border-gray-100">
          <h2 class="text-lg font-semibold mb-4 text-gray-800">Filtrar por</h2>

          <!-- Filtro por Tipo de Barreira -->
          <div class="mb-5">
            <h3 class="text-sm font-medium mb-2 text-gray-600">Tipo de Barreira</h3>
            <ul class="space-y-2">
              <c:forEach items="${barrierTypeOptions}" var="type">
                <li>
                  <label class="flex items-center text-sm text-gray-700 hover:text-blue-700 cursor-pointer transition">
                    <input type="checkbox" class="rounded text-blue-600 focus:ring-blue-500 mr-2" name="barrierType" value="${type}"
                    <c:if test="${paramValues.barrierType != null}">
                    <c:forEach items="${paramValues.barrierType}" var="selectedType">
                           <c:if test="${selectedType == type.toString()}">checked</c:if>
                    </c:forEach>
                    </c:if>
                    >
                    <span>${type.getTranslation()}</span>
                  </label>
                </li>
              </c:forEach>
            </ul>
          </div>

          <!-- Filtro por Legislação Relacionada -->
          <div>
            <h3 class="text-sm font-medium mb-2 text-gray-600">Legislação Relacionada</h3>
            <ul class="space-y-2">
              <c:forEach items="${laws}" var="law">
                <li>
                  <label class="flex items-center text-sm text-gray-700 hover:text-blue-700 cursor-pointer transition">
                    <input type="checkbox" class="rounded text-blue-600 focus:ring-blue-500 mr-2" name="law" value="${law.code}"
                    <c:if test="${paramValues.law != null}">
                    <c:forEach items="${paramValues.law}" var="selectedLawCode">
                           <c:if test="${selectedLawCode == law.code}">checked</c:if>
                    </c:forEach>
                    </c:if>
                    >
                    <span>${law.code}</span>
                  </label>
                </li>
              </c:forEach>
            </ul>
          </div>
        </div>

        <div class="mt-5 space-y-3">
          <button type="submit" class="block w-full bg-blue-600 hover:bg-blue-700 text-white font-medium py-2 px-4 rounded-lg transition duration-200 ease-in-out flex items-center justify-center">
            <span class="material-symbols-outlined text-base mr-2">tune</span>
            Aplicar Filtros
          </button>
          <%-- Botão para Limpar Filtros --%>
          <c:if test="${not empty param.barrierType || not empty param.law || not empty param.searchTerm}">
            <a href="${pageContext.request.contextPath}/scenario/" class="block w-full bg-gray-200 hover:bg-gray-300 text-gray-800 font-medium py-2 px-4 rounded-lg transition duration-200 ease-in-out flex items-center justify-center">
              <span class="material-symbols-outlined text-base mr-2">filter_off</span>
              Limpar Filtros
            </a>
          </c:if>
        </div>
      </aside>

      <!-- Conteúdo Principal (Barra de Busca, Lista de Posts, Paginação) -->
      <div class="flex-grow">

        <!-- Barra de Busca -->
        <div class="bg-white p-4 rounded-xl shadow-sm border border-gray-100 mb-6">
          <div class="flex">
            <input type="text" placeholder="Buscar cenários por título..." class="flex-grow px-4 py-2 text-sm border border-gray-300 rounded-l-md focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-blue-500" name="searchTerm" value="${param.searchTerm != null ? param.searchTerm : ''}">
            <button type="submit" class="bg-blue-600 hover:bg-blue-700 text-white px-4 py-2 rounded-r-md transition duration-200 ease-in-out">
              <span class="material-symbols-outlined">search</span>
            </button>
          </div>
        </div>

        <!-- Lista de Cenários Educativos -->
        <div class="space-y-6 flex flex-wrap ">
          <c:choose>
            <c:when test="${not empty scenarioList}">
              <c:forEach items="${scenarioList}" var="post">
                <article class="flex-1 md:flex-none md:basis-1/2 bg-white rounded-xl shadow-sm border border-gray-100 hover:shadow-lg transition duration-200 ease-in-out overflow-hidden flex flex-col">
                    <%-- Imagem do cenário (placeholder) - Aumentada a altura para h-56 ou h-64 --%>

                      <c:if test="${not empty post.imageCoverPath}">
                        <img src="${pageContext.request.contextPath}/uploads/report/${post.imageCoverPath}/" alt="Imagem do Cenário Educativo ${post.title}" class="w-full  h-64 object-cover object-center rounded-t-xl">
                      </c:if>

                      <c:if test="${empty post.imageCoverPath}">
                        <img src="https://picsum.photos/seed/${post.id + 100}/600/350" alt="Imagem do Cenário Educativo ${post.title}" class="w-full h-56 md:h-64 object-cover object-center rounded-t-xl">
                      </c:if>


                  <div class="p-5 flex flex-col flex-grow">
                    <div class="flex justify-between items-start mb-2">
                      <h3 class="text-lg font-semibold text-gray-800">
                        <a href="${pageContext.request.contextPath}/scenario/${post.id}/" class="hover:text-blue-600 transition duration-200 ease-in-out">${post.title}</a>
                      </h3>
                      <span class="bg-blue-100 text-blue-800 text-xs font-medium px-2.5 py-0.5 rounded-full">${post.barrierType.getTranslation()}</span>
                    </div>

                    <div class="flex flex-wrap gap-2 mt-2 mb-4">
                      <c:forEach items="${post.associatedLaws}" var="law">
                        <span class="bg-gray-100 text-gray-700 text-xs px-2.5 py-1 rounded-full border border-gray-200">${law.code}</span>
                      </c:forEach>
                    </div>

                      <%-- O link "Ler mais" agora está fixado na parte inferior do card --%>
                    <div class="mt-auto"> <%-- Adiciona flex-grow ao container acima para empurrar este para baixo --%>
                      <a href="${pageContext.request.contextPath}/scenario/${post.id}/" class="text-blue-600 hover:text-blue-800 text-sm font-medium inline-flex items-center transition duration-200 ease-in-out">
                        Ler mais
                        <span class="material-symbols-outlined ml-1 text-base">arrow_forward</span>
                      </a>
                    </div>
                  </div>
                </article>
              </c:forEach>
            </c:when>
            <c:otherwise>
              <div class="text-center py-16 text-gray-600 bg-white rounded-xl shadow-sm border border-gray-100">
                <p class="text-xl font-semibold mb-3">Ops! Nenhum cenário encontrado.</p>
                <p class="text-md">Não encontramos cenários educativos com os filtros aplicados.</p>
                <p class="text-sm mt-2">Tente ajustar seus filtros ou <a href="${pageContext.request.contextPath}/scenario/" class="text-blue-600 hover:underline">limpar todos os filtros</a> para ver mais resultados.</p>
              </div>
            </c:otherwise>
          </c:choose>
        </div>

        <!-- Paginação (mantida como placeholder, exigiria lógica no Servlet para passar parâmetros de filtro) -->
        <div class="mt-10 flex justify-center">
          <nav class="inline-flex rounded-lg shadow-sm overflow-hidden border border-gray-200">
            <a href="#" class="px-3 py-2 text-sm text-gray-500 bg-white hover:bg-gray-50 transition duration-150 ease-in-out">
              <span class="material-symbols-outlined text-base">chevron_left</span>
            </a>
            <a href="#" class="px-3 py-2 text-sm text-gray-700 bg-white hover:bg-gray-50 transition duration-150 ease-in-out font-semibold">1</a>
            <a href="#" class="px-3 py-2 text-sm text-gray-700 bg-white hover:bg-gray-50 transition duration-150 ease-in-out">2</a>
            <a href="#" class="px-3 py-2 text-sm text-gray-700 bg-white hover:bg-gray-50 transition duration-150 ease-in-out">3</a>
            <a href="#" class="px-3 py-2 text-sm text-gray-500 bg-white hover:bg-gray-50 transition duration-150 ease-in-out">
              <span class="material-symbols-outlined text-base">chevron_right</span>
            </a>
          </nav>
        </div>

      </div>
    </form>
  </main>

</div>
<%@ include file="parts/_footer.jsp" %>
</body>
</html>