<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <meta charset='utf-8'>
  <meta http-equiv='X-UA-Compatible' content='IE=edge'>
  <title>Denuncie Barreiras - Denúncias</title>
  <meta name='viewport' content='width=device-width, initial-scale=1'>
  <base href="<%= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/" %>">
  <script src="https://cdn.tailwindcss.com"></script>
  <link rel='stylesheet' type='text/css' media='screen' href='/static/css/public/style.css'>
  <link href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined" rel="stylesheet">
  <link rel="shortcut icon" href="static/images/logo.png" type="image/x-icon">
  <style>
    .material-symbols-outlined {
      font-variation-settings: 'FILL' 0,'wght' 400,'GRAD' 0,'opsz' 24;
      font-family: 'Material Symbols Outlined';
      vertical-align: middle;
    }
    .report-card {
      transition: all 0.3s ease;
    }
    .report-card:hover {
      transform: translateY(-2px);
      box-shadow: 0 10px 25px -5px rgba(0, 0, 0, 0.1);
    }
    .truncate-text {
      display: -webkit-box;
      -webkit-line-clamp: 3;
      -webkit-box-orient: vertical;
      overflow: hidden;
    }
  </style>
</head>
<body class="bg-gray-50">
<div class="wrapper">

  <%@ include file="parts/_header.jsp"%>

  <!-- Hero Section -->
  <section class="bg-blue-600 text-white py-12 md:py-20">
    <div class="container mx-auto px-4 text-center">
      <h1 class="text-3xl md:text-4xl font-bold mb-4">Denúncias de Barreiras Atitudinais</h1>
      <p class="text-lg md:text-xl max-w-2xl mx-auto">
        Sua voz pode quebrar preconceitos e transformar vidas! Denuncie e ajude a construir um ambiente mais inclusivo.
      </p>
      <a href="${pageContext.request.contextPath}/report/create/"
         class="mt-6 inline-flex items-center px-6 py-3 bg-white text-blue-600 font-medium rounded-lg hover:bg-gray-100 transition">
        <span class="material-symbols-outlined mr-2">add</span>
        Fazer nova denúncia
      </a>
    </div>
  </section>

  <!-- Main Content -->
  <main class="container mx-auto px-4 py-8 md:py-12">
    <div class="flex flex-col lg:flex-row gap-6">

      <!-- Sidebar Filters -->
      <aside class="w-full lg:w-72 flex-shrink-0">
        <div class="bg-white p-6 rounded-xl shadow-sm border border-gray-100 sticky top-6">
          <div class="flex justify-between items-center mb-6">
            <h2 class="text-xl font-semibold text-gray-800">Filtrar Denúncias</h2>
            <button class="text-blue-600 hover:text-blue-800 text-sm font-medium">
              Limpar
            </button>
          </div>

          <!-- Filter by Type -->
          <div class="mb-6">
            <h3 class="text-sm font-medium mb-3 text-gray-600 uppercase tracking-wider">Tipo de Barreira</h3>
            <ul class="space-y-2">
              <c:forEach items="${barrierTypeOptions}" var="type">
                <li>
                  <label class="flex items-center text-sm text-gray-700 hover:text-blue-600 cursor-pointer transition">
                    <input type="checkbox" class="rounded text-blue-600 focus:ring-blue-500 mr-3"
                           name="barrierType" value="${type}">
                    <span>${type.getTranslation()}</span>
                  </label>
                </li>
              </c:forEach>
            </ul>
          </div>

          <!-- Filter by Scenario -->
          <div class="mb-6">
            <h3 class="text-sm font-medium mb-3 text-gray-600 uppercase tracking-wider">Cenário Relacionado</h3>
            <ul class="space-y-2">
              <c:forEach items="${scenarios}" var="scenario">
                <li>
                  <label class="flex items-center text-sm text-gray-700 hover:text-blue-600 cursor-pointer transition">
                    <input type="checkbox" class="rounded text-blue-600 focus:ring-blue-500 mr-3"
                           name="scenario" value="${scenario.id}">
                    <span class="truncate">${scenario.title}</span>
                  </label>
                </li>
              </c:forEach>
            </ul>
          </div>

          <button type="submit" class="w-full bg-blue-600 hover:bg-blue-700 text-white py-2 px-4 rounded-lg transition flex items-center justify-center">
            <span class="material-symbols-outlined mr-2">filter_alt</span>
            Aplicar Filtros
          </button>
        </div>
      </aside>

      <!-- Reports List -->
      <div class="flex-grow">
        <!-- Search Bar -->
        <div class="bg-white p-4 rounded-xl shadow-sm border border-gray-100 mb-6">
          <form class="flex">
            <input type="text" placeholder="Buscar denúncias..."
                   class="flex-grow px-4 py-3 text-sm border border-gray-300 rounded-l-lg focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-blue-500">
            <button type="submit" class="bg-blue-600 hover:bg-blue-700 text-white px-5 py-3 rounded-r-lg transition flex items-center">
              <span class="material-symbols-outlined">search</span>
              <span class="ml-2 hidden sm:inline">Buscar</span>
            </button>
          </form>
        </div>

        <!-- Reports Grid -->
        <div class="grid grid-cols-1 gap-6">
          <c:forEach items="${reportList}" var="report">
            <article class="report-card bg-white p-6 rounded-xl shadow-sm border border-gray-100 hover:shadow-md transition">
              <div class="flex flex-col sm:flex-row sm:justify-between sm:items-start gap-4">
                <div class="flex-grow">
                  <div class="flex items-center gap-2 mb-3">
                    <span class="bg-blue-100 text-blue-800 text-xs font-medium px-2.5 py-0.5 rounded-full">
                        ${report.ambient.getTranslation()}
                    </span>
                    <span class="bg-red-100 text-red-800 text-xs font-medium px-2.5 py-0.5 rounded-full">
                        ${report.type.getTranslation()}
                    </span>
                    <c:if test="${report.anonymousReport}">
                      <span class="bg-gray-100 text-gray-800 text-xs font-medium px-2.5 py-0.5 rounded-full">
                        Anônimo
                      </span>
                    </c:if>
                  </div>

                  <h3 class="text-xl font-semibold text-gray-800 mb-2">
                    Denúncia em ${report.ambient.getTranslation()}
                  </h3>

                  <div class="text-gray-600 mb-4 truncate-text">
                      ${report.eventDetailing}
                  </div>
                </div>

                <div class="flex sm:flex-col justify-between sm:justify-start gap-2 sm:gap-3">
                  <span class="text-sm text-gray-500">
                    <span class="material-symbols-outlined align-middle text-base">calendar_today</span>
                    ${report.creationDate}
                  </span>

                  <a href="${pageContext.request.contextPath}/public/report/${report.id}/"
                     class="inline-flex items-center text-blue-600 hover:text-blue-800 font-medium transition">
                    Ler mais
                    <span class="material-symbols-outlined ml-1">arrow_forward</span>
                  </a>
                </div>
              </div>
            </article>
          </c:forEach>
        </div>

        <!-- Pagination -->
        <div class="mt-10 flex justify-center">
          <nav class="inline-flex rounded-lg shadow-sm overflow-hidden border border-gray-200">
            <a href="#" class="px-4 py-2 text-sm text-gray-500 bg-white hover:bg-gray-50 transition">
              <span class="material-symbols-outlined">chevron_left</span>
            </a>
            <a href="#" class="px-4 py-2 text-sm font-medium text-blue-600 bg-blue-50">1</a>
            <a href="#" class="px-4 py-2 text-sm text-gray-700 bg-white hover:bg-gray-50 transition">2</a>
            <a href="#" class="px-4 py-2 text-sm text-gray-700 bg-white hover:bg-gray-50 transition">3</a>
            <span class="px-4 py-2 text-sm text-gray-500 bg-white">...</span>
            <a href="#" class="px-4 py-2 text-sm text-gray-700 bg-white hover:bg-gray-50 transition">8</a>
            <a href="#" class="px-4 py-2 text-sm text-gray-500 bg-white hover:bg-gray-50 transition">
              <span class="material-symbols-outlined">chevron_right</span>
            </a>
          </nav>
        </div>
      </div>
    </div>
  </main>

  <%@ include file="parts/_footer.jsp" %>
</div>
</body>
</html>