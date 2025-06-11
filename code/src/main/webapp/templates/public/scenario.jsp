<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
    .like-btn {
      transition: all 0.2s ease;
      cursor: pointer;
    }
    .like-btn:hover {
      transform: scale(1.1);
    }
    .like-btn.active {
      color: #3b82f6;
    }
    .like-btn.active:hover {
      color: #2563eb;
    }
  </style>
</head>
<body class="bg-gray-50">
<div class="wrapper">

  <%@ include file="parts/_header.jsp"%>
  <c:set var="monthNames" value="Janeiro, Fevereiro, Março, Abril, Maio, Junho, Julho, Agosto, Setembro, Outubro, Novembro, Dezembro" />
  <c:set var="monthArray" value="${fn:split(monthNames, ', ')}" />

  <main class="flex-grow container mx-auto px-4 py-8 flex flex-col md:flex-row gap-8">

    <div class="flex-grow">


      <article class="bg-white p-6 rounded-lg shadow-md">



        <div class="flex justify-between items-start">
          <h1 class="text-2xl md:text-3xl font-bold text-gray-800 mb-4">${post.title}</h1>
        </div>

        <c:if test="${ not empty post.imageCoverPath}">
          <div class="w-full flex justify-center  py-8 ">
            <img class="h-auto max-w-lg rounded-lg  py-8 "  src="${pageContext.request.contextPath}/uploads/report/${post.imageCoverPath}" alt="image description">
          </div>
        </c:if>
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

        <!-- Barra de Interação do Post (Curtir e Denunciar) -->
        <div class="mt-6 border-t pt-4 flex justify-between items-center">
          <div class="flex items-center gap-4">
            <form action="/scenario/interactions/${post.id}/like/" method="POST" id="like-form">
              <button type="submit" class="like-btn flex items-center gap-1 ${isLikedByUser ? 'active' : ''}"
                      data-post-id="${post.id}" data-type="post">
                <span class="material-symbols-outlined">thumb_up</span>
                <span class="like-count">${post.likes.size()}</span>
              </button>
            </form>

          </div>

          <a href="/report/create/${post.id}/" class="inline-flex items-center bg-blue-600 hover:bg-blue-700 text-white font-semibold py-2 px-4 rounded-lg transition duration-200">
            <span class="material-symbols-outlined mr-2">add_circle</span>
            Fazer Denúncia Relacionada
          </a>
        </div>

        <!-- Ação final -->
        <div class="mt-8 flex justify-between items-center">
          <div class="text-sm text-gray-500">
            Postado por ${post.author.name} em
            <p class="text-sm text-gray-400">
              ${post.creationDate.dayOfMonth} de ${monthArray[post.creationDate.monthValue - 1]} de ${post.creationDate.year}
          </div>
        </div>
      </article>
    </div>
  </main>

  <section class="flex-grow container mx-auto px-4 py-8 flex flex-col md:flex-row gap-8">
    <div class="flex-grow">
      <article class="bg-white p-6 rounded-lg shadow-md">
        <h2 class="text-2xl md:text-3xl font-bold text-gray-800 mb-6">
          Discussão (${post.comments.size()})
        </h2>

        <!-- Formulário de comentário -->

        <form action="/scenario/interactions/${post.id}/comments/create/" method="POST" id="form-comment" class="mb-6">
          <div class="py-2 px-4 mb-4 bg-white rounded-lg border border-gray-200">
            <label for="comment-content" class="sr-only">Seu comentário</label>
            <textarea id="comment-content" rows="6" name="comment-content"
                      class="px-0 w-full text-sm text-gray-900 border-0 focus:ring-0 focus:outline-none"
                      placeholder="Escreva um comentário..." required></textarea>
          </div>
          <button type="submit"
                  class="inline-flex items-center py-2.5 px-4 text-sm font-semibold text-white bg-blue-600 rounded-lg hover:bg-blue-700 focus:ring-4 focus:ring-blue-300">
            Postar Comentário
          </button>
        </form>

        <!-- Lista de comentários -->
        <div class="space-y-4" id="comment-list">
          <c:forEach items="${post.comments}" var="comment">
            <div class="flex items-start gap-4 p-4 bg-gray-50 rounded-lg relative">
              <img class="w-10 h-10 rounded-full"
                   src="https://upload.wikimedia.org/wikipedia/commons/a/ac/Default_pfp.jpg"
                   alt="Avatar">
              <div class="flex-grow">
                <div class="flex justify-between items-center mb-1">
                  <div>
                    <p class="text-sm font-semibold text-gray-900">${comment.getAuthorName()}</p>
                    <c:set var="monthNames" value="Janeiro, Fevereiro, Março, Abril, Maio, Junho, Julho, Agosto, Setembro, Outubro, Novembro, Dezembro" />
                    <c:set var="monthArray" value="${fn:split(monthNames, ', ')}" />
                    <p class="text-xs text-gray-500">
                        ${comment.creationDateTime.dayOfMonth} de ${monthArray[comment.creationDateTime.monthValue - 1]} de ${comment.creationDateTime.year}
                    </p>
                  </div>

                  <div class="flex items-center gap-4">

                    <c:if test="${cookie.userId.value eq comment.author.id}">
                      <!-- Dropdown -->
                      <details class="relative">
                        <summary class="cursor-pointer list-none">
                          <span class="material-symbols-outlined mr-2">more_horiz</span>
                        </summary>
                        <div class="absolute right-0 mt-2 w-28 bg-white border border-gray-200 rounded-md shadow-lg z-50">
                          <form action="/scenario/${post.id}/interactions/comments/delete/${comment.id}/" method="POST">
                            <button type="submit" class="block px-4 py-2 text-sm text-red-600 hover:bg-red-50">
                              Deletar
                            </button>
                          </form>

                        </div>
                      </details>
                    </c:if>
                  </div>
                </div>
                <p class="text-gray-700">${comment.content}</p>
              </div>
            </div>
          </c:forEach>
        </div>
      </article>
    </div>
  </section>

</div>
<%@ include file="parts/_footer.jsp" %>
</body>

<!-- Carregando a versão correta do Showdown -->
<script src="https://cdn.jsdelivr.net/npm/showdown@2.1.0/dist/showdown.min.js"></script>

<script>
  document.addEventListener("DOMContentLoaded", () => {
    const converter = new showdown.Converter({
      simpleLineBreaks: true,
      disableForced4SpacesIndentedSublists: true,
      ghCompatibleHeaderId: true
    });

    const contentEl = document.getElementById("post-content");
    if (!contentEl) return;

    const escapedContent = contentEl.textContent.split("\n")
            .map(line => line.replace(/^\s+/, ""))
            .join("\n");

    const html = converter.makeHtml(escapedContent);

    contentEl.innerHTML = html;
  });
</script>

</html>