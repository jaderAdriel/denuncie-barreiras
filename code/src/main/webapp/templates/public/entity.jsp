<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
  <meta charset='utf-8'>
  <meta http-equiv='X-UA-Compatible' content='IE=edge'>
  <title>Estatísticas da Empresa - <c:out value="${entity.name}"/></title>
  <meta name='viewport' content='width=device-width, initial-scale=1'>
  <base href="<%= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/" %>">
  <script src="https://cdn.tailwindcss.com"></script>
  <link href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined" rel="stylesheet">
  <link rel="shortcut icon" href="static/images/logo.png" type="image/x-icon">
  <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">

  <link rel='stylesheet' type='text/css' media='screen' href='/static/css/public/style.css'>
  <%-- Removed editor.css and scenario.css as they are not relevant to this page --%>
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

  <main class="flex-grow container mx-auto px-4 py-8">
    <h1 class="text-3xl md:text-4xl font-bold text-gray-800 mb-8 text-center">
      Estatísticas da Empresa: (<c:out value="${entity.name}"/>)
    </h1>

    <div class="flex flex-col lg:flex-row gap-8">

      <!-- Coluna esquerda: Detalhes da Empresa -->
      <div class="lg:w-1/3">
        <div class="bg-white p-6 rounded-lg shadow-md">
          <h2 class="text-2xl font-bold text-gray-800 mb-4">Detalhes da Empresa</h2>
          <div class="grid grid-cols-1 gap-4 text-gray-700">
            <div>
              <p><strong>CNPJ:</strong> <c:out value="${entity.cnpj}"/></p>
              <p><strong>Tipo:</strong> <c:out value="${entity.type}"/></p>
              <p><strong>Telefone:</strong> <c:out value="${entity.phone}"/></p>
            </div>
            <div>
              <p><strong>Endereço:</strong> <c:out value="${entity.address}"/></p>
            </div>
          </div>
        </div>
      </div>

      <!-- Coluna direita: Denúncias -->
      <div class="lg:w-2/3">
        <section class="bg-white p-6 rounded-lg shadow-md">
          <h2 class="text-2xl font-bold text-gray-800 mb-6">
            Todas as Denúncias Relacionadas ${reports.size()}
          </h2>

          <c:choose>
            <c:when test="${empty reports}">
              <p class="text-gray-600 text-center py-4">Nenhuma denúncia encontrada para esta empresa.</p>
            </c:when>
            <c:otherwise>
              <div class="space-y-6">
                <c:forEach items="${reports}" var="report">
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
            </c:otherwise>
          </c:choose>
        </section>
      </div>

    </div>


  </main>

</div>
<%@ include file="parts/_footer.jsp" %>
</body>
</html>