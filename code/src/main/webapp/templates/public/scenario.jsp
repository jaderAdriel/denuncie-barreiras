<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <meta charset='utf-8'>
  <meta http-equiv='X-UA-Compatible' content='IE=edge'>
  <title>Denuncie Barreiras - Postagens</title>
  <meta name='viewport' content='width=device-width, initial-scale=1'>
  <base href="<%= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/" %>">
  <script src="https://cdn.tailwindcss.com"></script>
  <link href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined" rel="stylesheet">
  <link rel="shortcut icon" href="static/images/logo.png" type="image/x-icon">
  <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">


  <link rel='stylesheet' type='text/css' media='screen' href='/static/css/public/style.css'>
  <link rel='stylesheet' type='text/css' media='screen' href='/static/css/editor.css'>
  <link rel='stylesheet' type='text/css' media='screen' href='/static/css/public/scenario.css'>

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

  <main class="flex-grow container mx-auto px-4 py-8 flex flex-col md:flex-row gap-8">

<%--    <!-- Sidebar: Ação -->--%>
<%--    <aside class="w-full md:w-64 flex-shrink-0">--%>
<%--      <div class="sticky top-8 space-y-4">--%>
<%--        <a href="/denuncia/nova" class="w-full bg-blue-600 hover:bg-blue-700 text-white font-bold py-3 px-4 rounded-lg flex items-center justify-center transition duration-200">--%>
<%--          <span class="material-symbols-outlined mr-2">add</span>--%>
<%--          Nova Denúncia--%>
<%--        </a>--%>
<%--      </div>--%>
<%--    </aside>--%>

    <!-- Conteúdo principal -->
    <div class="flex-grow">
      <article class="bg-white p-6 rounded-lg shadow-md">
        <h1 class="text-2xl md:text-3xl font-bold text-gray-800 mb-4">${post.title}</h1>

        <!-- Metainformações -->
        <div class="flex items-center gap-2 mb-6">
        <span class="bg-blue-100 text-blue-800 text-xs font-semibold px-3 py-1 rounded">
          ${post.barrierType.getTranslation()}
        </span>
          <c:forEach items="${post.associatedLaws}" var="law">
            <span class="bg-gray-100 text-gray-800 text-xs px-2 py-1 rounded">${law.code}</span>
          </c:forEach>
        </div>

        <!-- Corpo do post -->
        <div class="prose max-w-none text-gray-700 leading-relaxed" id="post-content">
          ${post.content}
        </div>

        <!-- Ação final -->
        <div class="mt-8">
          <a href="/report/create/${post.id}/" class="inline-flex items-center bg-blue-600 hover:bg-blue-700 text-white font-semibold py-3 px-6 rounded-lg transition duration-200">
            <span class="material-symbols-outlined mr-2">add_circle</span>
            Fazer Denúncia Relacionada
          </a>
        </div>
      </article>
    </div>
  </main>



</div>
<%@ include file="parts/_footer.jsp" %>
</body>

<!-- Carregando a versão correta do Showdown -->
<script src="https://cdn.jsdelivr.net/npm/showdown@2.1.0/dist/showdown.min.js"></script>

<script>
  document.addEventListener("DOMContentLoaded", () => {
    const converter = new showdown.Converter({
      simpleLineBreaks: true, // permite quebra de linha com \n
      disableForced4SpacesIndentedSublists: true,
      ghCompatibleHeaderId: true
    });

    const contentEl = document.getElementById("post-content");
    if (!contentEl) return;

    // Pega o conteúdo como texto puro (com \n visíveis)
    const escapedContent = contentEl.textContent.split("\n")
            .map(line => line.replace(/^\s+/, "")) // remove espaços à esquerda
            .join("\n");;

    // Converte markdown para HTML
    const html = converter.makeHtml(escapedContent);

    // Insere o HTML convertido no mesmo elemento
    contentEl.innerHTML = html;
  });
</script>


</html>