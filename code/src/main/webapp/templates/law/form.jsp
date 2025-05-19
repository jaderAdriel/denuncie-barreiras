<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ include file="../_head.jsp"%>
    <title>Cenários</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <!-- EasyMDE CSS -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/simplemde/latest/simplemde.min.css">
    <script src="https://cdn.jsdelivr.net/simplemde/latest/simplemde.min.js"></script>

    <link rel='stylesheet' type='text/css' media='screen' href="/static/css/editor.css">
</head>
<body>
<%@ include file="../_sidebar.jsp"%>
<%@ include file="../_header.jsp"%>

<section class="p-4 md:p-6">
    <div class="flex flex-col md:flex-row items-start md:items-center justify-between mb-6 gap-4">
        <div class="flex items-center gap-x-3">
            <h2 class="text-2xl font-bold text-gray-800 mb-6">
                <c:choose>
                    <c:when test="${method == 'POST'}">Adicionar Lei</c:when>
                    <c:when test="${method == 'PUT'}">Alterar Lei ${law.getCode()}</c:when>
                    <c:when test="${method == 'DELETE'}">Excluir Lei ${law.getCode()}</c:when>
                </c:choose>
            </h2>
        </div>
    </div>

    <form action="${action}" method="POST" class="bg-white p-8">
        <!-- Código -->
        <div>
            <label for="code" class="block mb-2 text-sm font-medium text-gray-900">Código</label>
            <input
                    type="text"
                    id="code"
                    name="code"
                    class="border border-gray-300 text-gray-900 text-sm rounded-lg block w-full p-2.5
                   <c:if test='${method != "POST"}'> bg-gray-200 cursor-not-allowed </c:if>
                   <c:if test='${method == "POST"}'> bg-gray-50 </c:if>"
            <c:if test='${not empty code}'> value='${code}' </c:if>
            <c:if test='${method != "POST"}'> readonly disabled </c:if>
                    required
            >
            <c:if test='${not empty codeError}'>
                <div class="flex items-center gap-1 text-red-500 text-sm mt-2">
                    <svg xmlns="http://www.w3.org/2000/svg" height="20px" viewBox="0 -960 960 960" width="20px" fill="currentColor">
                        <path d="..."></path>
                    </svg>
                    <p>${codeError}</p>
                </div>
            </c:if>
        </div>

        <!-- Data -->
        <div class="mt-4">
            <label for="date" class="block mb-2 text-sm font-medium text-gray-900">Data</label>
            <input
                    type="date"
                    id="date"
                    name="date"
                    class="border border-gray-300 text-gray-900 text-sm rounded-lg block w-full p-2.5
                   <c:if test='${method != "POST"}'> bg-gray-200 cursor-not-allowed </c:if>
                   <c:if test='${method == "POST"}'> bg-gray-50 </c:if>"
            <c:if test='${not empty date}'> value='${date}' </c:if>
            <c:if test='${method != "POST"}'> readonly disabled </c:if>
            >
            <c:if test='${not empty dateError}'>
                <div class="flex items-center gap-1 text-red-500 text-sm mt-2">
                    <svg xmlns="http://www.w3.org/2000/svg" height="20px" viewBox="0 -960 960 960" width="20px" fill="currentColor">
                        <path d="..."></path>
                    </svg>
                    <p>${dateError}</p>
                </div>
            </c:if>
        </div>

        <!-- Link Oficial -->
        <div class="mt-4">
            <label for="officialLink" class="block mb-2 text-sm font-medium text-gray-900">Link Oficial</label>
            <input
                    type="url"
                    id="officialLink"
                    name="officialLink"
                    class="border border-gray-300 text-gray-900 text-sm rounded-lg block w-full p-2.5
                   <c:if test='${method == "DELETE"}'> bg-gray-200 cursor-not-allowed </c:if>
                   <c:if test='${method != "DELETE"}'> bg-gray-50 </c:if>"
                    placeholder="Título descritivo"
                    required
            <c:if test='${not empty officialLink}'> value='${officialLink}' </c:if>
            <c:if test='${method == "DELETE"}'> readonly disabled </c:if>
            >
            <c:if test='${not empty officialLinkError}'>
                <div class="flex items-center gap-1 text-red-500 text-sm mt-2">
                    <svg xmlns="http://www.w3.org/2000/svg" height="20px" viewBox="0 -960 960 960" width="20px" fill="currentColor">
                        <path d="..."></path>
                    </svg>
                    <p>${officialLinkError}</p>
                </div>
            </c:if>
        </div>

        <!-- Título -->
        <div class="mt-4">
            <label for="title" class="block mb-2 text-sm font-medium text-gray-900">Título</label>
            <input
                    type="text"
                    id="title"
                    name="title"
                    class="border border-gray-300 text-gray-900 text-sm rounded-lg block w-full p-2.5
                   <c:if test='${method != "POST"}'> bg-gray-200 cursor-not-allowed </c:if>
                   <c:if test='${method == "POST"}'> bg-gray-50 </c:if>"
            <c:if test='${not empty title}'> value='${title}' </c:if>
            >
            <c:if test='${not empty titleError}'>
                <div class="flex items-center gap-1 text-red-500 text-sm mt-2">
                    <svg xmlns="http://www.w3.org/2000/svg" height="20px" viewBox="0 -960 960 960" width="20px" fill="currentColor">
                        <path d="..."></path>
                    </svg>
                    <p>${titleError}</p>
                </div>
            </c:if>
        </div>

        <!-- Descrição -->
        <div class="mt-4">
            <label for="description" class="block mb-2 text-sm font-medium text-gray-900">Descrição</label>
            <textarea
                    id="description"
                    name="description"
                    rows="4"
                    class="border border-gray-300 text-gray-900 text-sm rounded-lg block w-full p-2.5 resize-none
               <c:if test='${method == "DELETE"}'> bg-gray-200 cursor-not-allowed </c:if>
               <c:if test='${method != "DELETE"}'> bg-gray-50 </c:if>"
                    <c:if test='${method == "DELETE"}'> readonly disabled </c:if>
            >${not empty description ? description : ''}</textarea>

            <c:if test='${not empty descriptionError}'>
                <div class="flex items-center gap-1 text-red-500 text-sm mt-2">
                    <svg xmlns="http://www.w3.org/2000/svg" height="20px" viewBox="0 -960 960 960" width="20px" fill="currentColor">
                        <path d="..."></path>
                    </svg>
                    <p>${descriptionError}</p>
                </div>
            </c:if>
        </div>
        <!-- Botões de ação -->
        <div class="flex justify-end space-x-4 pt-4">
            <a href="/law/index/" type="button" class="text-gray-900 bg-white focus:outline-none focus:ring-4 font-medium rounded-lg text-sm px-5 py-2.5">
                Cancelar
            </a>
            <button type="submit" class="text-white  focus:ring-4 font-medium rounded-lg text-sm px-5 py-2.5 focus:outline-none
                    <c:choose>
                        <c:when test="${method == 'POST'}"> bg-green-500  </c:when>
                        <c:when test="${method == 'PUT'}"> bg-yellow-400 </c:when>
                        <c:when test="${method == 'DELETE'}"> bg-red-500 </c:when>
                    </c:choose>
                ">
                <c:choose>
                    <c:when test="${method == 'POST'}">Salvar</c:when>
                    <c:when test="${method == 'PUT'}">Atualizar</c:when>
                    <c:when test="${method == 'DELETE'}">Confirmar Exclusão</c:when>
                </c:choose>
            </button>
        </div>
    </form>
</section>


<%@ include file="../_footer.jsp"%>

</body>
</html>
