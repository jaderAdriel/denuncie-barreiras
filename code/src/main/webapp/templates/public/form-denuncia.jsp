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
    <form action="${action}" method="post" class="bg-white p-8">

      <div class="flex gap-4 mb-4">
        <div class="w-full">
          <label for="title" class="block text-md font-medium text-gray-900">Ambiente</label>
          <div class="mt-2">
            <select name="environment"
                    class="block  bg-none appearance-none border border-gray-300 w-full rounded-md bg-white px-5 py-3 text-base text-gray-900 outline-1 -outline-offset-1 outline-gray-300 placeholder:text-gray-400 focus:outline-2 focus:-outline-offset-2 focus:outline-indigo-600 sm:text-md">
              <option>Selecione uma opção</option>
              <c:if test="${not empty environmentOptions}">
                <c:forEach items="${environmentOptions}" var="environmentOption">
                  <option value="${environmentOption.toString()}"
                          <c:if test="${not empty environment}">
                            <c:if test="${environment eq environmentOption}">selected</c:if>
                          </c:if>>
                      ${environmentOption.getTranslation()}
                  </option>
                </c:forEach>
              </c:if>
            </select>

            <c:if test="${not empty environmentError}"><p class="text-red-500 text-sm mt-2">${environmentError}</p></c:if>
          </div>
        </div>

        <div class="w-full">
          <label for="title" class="block text-md font-medium text-gray-900">Tipo de barreira</label>
          <div class="mt-2">
            <select name="barrierType"
                    class="block  bg-none appearance-none border border-gray-300 w-full rounded-md bg-white px-5 py-3 text-base text-gray-900 outline-1 -outline-offset-1 outline-gray-300 placeholder:text-gray-400 focus:outline-2 focus:-outline-offset-2 focus:outline-indigo-600 sm:text-md">
              <option>Selecione uma opção</option>
              <c:if test="${not empty barrierTypeOptions}">
                <c:forEach items="${barrierTypeOptions}" var="typeOption">
                  <option value="${typeOption.toString()}"
                          <c:if test="${not empty barrierType}">
                            <c:if test="${barrierType eq typeOption}">selected</c:if>
                          </c:if>
                  >
                      ${typeOption.getTranslation()}
                  </option>
                </c:forEach>
              </c:if>
            </select>

            <c:if test="${not empty barrierTypeError}"><p class="text-red-500 text-sm mt-2">${barrierTypeError}</p></c:if>
          </div>
        </div>

        <div class="w-full">
          <label for="title" class="block text-md font-medium text-gray-900">Entidade denunciada</label>
          <div class="mt-2">
            <select name="entity_cnpj"
                    class="block  bg-none appearance-none border border-gray-300 w-full rounded-md bg-white px-5 py-3 text-base text-gray-900 outline-1 -outline-offset-1 outline-gray-300 placeholder:text-gray-400 focus:outline-2 focus:-outline-offset-2 focus:outline-indigo-600 sm:text-md">
              <option>Selecione uma opção</option>
              <option>Nenhuma das opções</option>
              <c:if test="${not empty entityOptions}">
                <c:forEach items="${entityOptions}" var="entity">
                  <option value="${entity.cnpj}"
                          <c:if test="${not empty entity}">
                            <c:if test="${entity eq entity}">selected</c:if>
                          </c:if>
                  >
                      ${entity.name} | ${entity.address}
                  </option>
                </c:forEach>
              </c:if>
            </select>

            <c:if test="${not empty entityError}"><p class="text-red-500 text-sm mt-2">${entityError}</p></c:if>
          </div>
        </div>

      </div>

      <div>
        <label class="block text-md font-medium text-gray-900">Denúncia Anônima</label>
        <div class="mt-2 space-y-2">
          <div class="flex items-center">
            <input id="anonymous" name="anonymous" type="checkbox" value="true"
                   class="h-4 w-4 border-gray-300 text-indigo-600 focus:ring-indigo-600"
            <c:if test="${anonymous == 'true'}"> checked </c:if>
            <c:if test="${anonymous == 'false'}"> report.reporter.name </c:if>
            >
          </div>
        </div>
        <c:if test="${not empty anonymousError}">
          <div class="flex items-center gap-1 text-red-500 text-sm mt-2">
            <svg xmlns="http://www.w3.org/2000/svg" height="20px" viewBox="0 -960 960 960" width="20px" fill="currentColor">
              <path d="M480-290.77q13.73 0 23.02-9.29t9.29-23.02q0-13.73-9.29-23.02-9.29-9.28-23.02-9.28t-23.02 9.28q-9.29 9.29-9.29 23.02t9.29 23.02q9.29 9.29 23.02 9.29Zm-30-146.15h60v-240h-60v240ZM480.07-100q-78.84 0-148.21-29.92t-120.68-81.21q-51.31-51.29-81.25-120.63Q100-401.1 100-479.93q0-78.84 29.92-148.21t81.21-120.68q51.29-51.31 120.63-81.25Q401.1-860 479.93-860q78.84 0 148.21 29.92t120.68 81.21q51.31 51.29 81.25 120.63Q860-558.9 860-480.07q0 78.84-29.92 148.21t-81.21 120.68q-51.29 51.31-120.63 81.25Q558.9-100 480.07-100Zm-.07-60q134 0 227-93t93-227q0-134-93-227t-227-93q-134 0-227 93t-93 227q0 134 93 227t227 93Zm0-320Z"></path>
            </svg>
            <p>${anonymousError}</p>
          </div>
        </c:if>
      </div>

      <!-- Editor Markdown -->
      <div class="mt-4">
        <label for="incidentDetails" class="block mb-2 text-sm font-medium text-gray-900">Detalhamento do Ocorrido</label>
        <textarea
                id="incidentDetails"
                name="incidentDetails"
                rows="4"
                class="border border-gray-300 text-gray-900 text-sm rounded-lg block w-full p-2.5 resize-none
               <c:if test='${method == "DELETE"}'> bg-gray-200 cursor-not-allowed </c:if>
               <c:if test='${method != "DELETE"}'> bg-gray-50 </c:if>"
                <c:if test='${method == "DELETE"}'> readonly disabled </c:if>
        >${not empty incidentDetails ? incidentDetails : ''}</textarea>

        <c:if test='${not empty incidentDetailsError}'>
          <div class="flex items-center gap-1 text-red-500 text-sm mt-2">
            <svg xmlns="http://www.w3.org/2000/svg" height="20px" viewBox="0 -960 960 960" width="20px" fill="currentColor">
              <path d="..."></path>
            </svg>
            <p>${incidentDetailsError}</p>
          </div>
        </c:if>
      </div>

      <br>
      <br>
      <button type="submit"
              class="w-full bg-blue-600 hover:bg-blue-700 text-white font-semibold py-3 px-4 rounded-md transition duration-200 text-md">
        Salvar
      </button>

    </form>
  </main>

</div>
<%@ include file="parts/_footer.jsp" %>
</body>
</html>