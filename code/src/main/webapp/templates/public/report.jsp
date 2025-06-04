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

  <main class="flex-grow container mx-auto px-4 py-10">

  <section class="p-4 w-full">
    <div class="flex flex-col md:flex-row items-start md:items-center justify-between mb-6 gap-4">
      <div class="flex items-center gap-x-3">
        <h2 class="text-2xl font-semibold text-gray-800">Denúncia</h2>
        <span class="bg-blue-100 text-blue-800 text-sm font-medium px-2.5 py-0.5 rounded">#<c:out value="${report.id}"/></span>
      </div>
      <div class="text-sm text-gray-500">
        Denuncia criada em: ${report.creationDate.toString()}
      </div>
    </div>

    <div class="bg-white rounded-lg shadow-sm border border-gray-200 overflow-hidden">
      <!-- Report Information Section -->
      <div class="p-6 border-b border-gray-200">
        <h3 class="section-title">Detalhes da Denúncia</h3>

        <div class="grid grid-cols-1 md:grid-cols-2 gap-6 mb-4">
          <div>
            <p class="info-label">Ambiente</p>
            <div class="info-value readonly-field font-semibold font-medium">
              <c:out value="${report.ambient.getTranslation()}"/>
            </div>
          </div>

          <div>
            <p class="info-label">Tipo de Barreira</p>
            <div class="info-value readonly-field font-semibold font-medium">
              <c:out value="${report.type.getTranslation()}"/>
            </div>
          </div>
        </div>

        <div class="grid grid-cols-1 md:grid-cols-2 gap-6 mb-4">
          <div>
            <p class="info-label">Denunciante</p>
            <div class="info-value readonly-field font-semibold font-medium">
              <c:choose>
                <c:when test="${report.anonymousReport == true}">Anônimo</c:when>
                <c:otherwise><c:out value="${report.reporter.name}"/></c:otherwise>
              </c:choose>
            </div>
          </div>
        </div>

        <div class="mb-4">
          <p class="info-label">Detalhamento do Ocorrido</p>
          <div class="mt-2">
            <div id="incidentDetails" class="readonly-field font-semibold font-medium">
              ${not empty report.eventDetailing ? report.eventDetailing : 'Nenhum detalhamento fornecido.'}
            </div>
          </div>
        </div>
      </div>

      <!-- Review Section -->
      <div class="p-6">
        <c:choose>
          <%-- If a review already exists --%>
          <c:when test="${not empty report.review}">
            <div class="bg-green-50 border border-green-200 text-green-800 p-4 rounded-md mb-6">
              <div class="flex items-start">
                <svg class="w-5 h-5 mr-2 mt-0.5 flex-shrink-0" fill="currentColor" viewBox="0 0 20 20">
                  <path fill-rule="evenodd" d="M18 10a8 8 0 11-16 0 8 8 0 0116 0zm-7-4a1 1 0 11-2 0 1 1 0 012 0zM9 9a1 1 0 000 2v3a1 1 0 001 1h1a1 1 0 100-2h-1V9z" clip-rule="evenodd"></path>
                </svg>
                <div>
                  <p class="font-medium">Esta denúncia foi validada.</p>
                  <p class="text-sm mt-1">
                    Revisado por: <span class="font-semibold"><c:out value="${report.review.author.name}"/></span> em
                    <span class="font-semibold">${report.review.getCreatAtFormated()}</span>
                  </p>
                </div>
              </div>
            </div>

            <div class="bg-blue-50 border border-blue-200 text-blue-800 p-4 rounded-md mb-6">
              <p class="info-label">Comentário do Moderador</p>
              <div class="mt-2">
                <div class="readonly-field min-h-[120px]">
                  <c:out value="${not empty report.review.comment ? report.review.comment : 'Nenhum comentário foi adicionado.'}"/>
                </div>
              </div>
            </div>

            <div class="bg-gray-50 px-4 py-3 text-right sm:px-6 rounded-b-lg">
              <a href="/public/report" class="inline-flex items-center px-4 py-2 border border-gray-300 shadow-sm text-sm font-medium rounded-md text-gray-700 bg-white hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500">
                Voltar para a lista
              </a>
            </div>
          </c:when>
        </c:choose>
      </div>
    </div>
  </section>

</main>

<%@ include file="parts/_footer.jsp" %>
</body>
</html>