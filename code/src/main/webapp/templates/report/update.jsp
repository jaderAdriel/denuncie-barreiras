<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ include file="../_head.jsp"%>
    <title>Denúncias</title>
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
            <h2 class="text-2xl  mb-8">Verificar Denúncia</h2>
            <span class="px-3 py-1 text-xs text-blue-600 bg-blue-100 rounded-full">moderação</span>
        </div>
    </div>

    <form action="${action}" method="post" class="bg-white p-8">

        <div class="flex gap-4 mb-4">
            <div class="w-full">
                <label class="block text-md font-medium text-gray-900">Ambiente</label>
                <div class="mt-2">
                    <select name="environment" disabled
                            class="block bg-gray-100 appearance-none border border-gray-300 w-full rounded-md px-5 py-3 text-base text-gray-900 outline-1 -outline-offset-1 outline-gray-300 sm:text-md">
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
                <label class="block text-md font-medium text-gray-900">Tipo de barreira</label>
                <div class="mt-2">
                    <select name="barrierType" disabled
                            class="block bg-gray-100 appearance-none border border-gray-300 w-full rounded-md px-5 py-3 text-base text-gray-900 outline-1 -outline-offset-1 outline-gray-300 sm:text-md">
                        <c:if test="${not empty barrierTypeOptions}">
                            <c:forEach items="${barrierTypeOptions}" var="typeOption">
                                <option value="${typeOption.toString()}"
                                        <c:if test="${not empty barrierType}">
                                            <c:if test="${barrierType eq typeOption}">selected</c:if>
                                        </c:if>>
                                        ${typeOption.getTranslation()}
                                </option>
                            </c:forEach>
                        </c:if>
                    </select>
                    <c:if test="${not empty barrierTypeError}"><p class="text-red-500 text-sm mt-2">${barrierTypeError}</p></c:if>
                </div>
            </div>
        </div>

        <!-- Checkbox disabled -->
        <div>
            <label class="block text-md font-medium text-gray-900">Denúncia Anônima</label>
            <div class="mt-2 space-y-2">
                <div class="flex items-center">
                    <input id="anonymous" name="anonymous" type="checkbox" value="true" disabled
                           class="h-4 w-4 border-gray-300 text-indigo-600 focus:ring-indigo-600 bg-gray-100"
                    <c:if test="${anonymous == 'true'}"> checked </c:if>>
                    <label for="anonymous" class="ml-2 text-gray-700">
                        <c:choose>
                            <c:when test="${anonymous == 'true'}">Sim</c:when>
                            <c:otherwise>Não</c:otherwise>
                        </c:choose>
                    </label>
                </div>
            </div>
            <c:if test="${not empty anonymousError}">
                <div class="flex items-center gap-1 text-red-500 text-sm mt-2">
                    <!-- Ícone de erro -->
                    <p>${anonymousError}</p>
                </div>
            </c:if>
        </div>

        <!-- Textarea disabled -->
        <div class="mt-4">
            <label class="block mb-2 text-sm font-medium text-gray-900">Detalhamento do Ocorrido</label>
            <textarea id="incidentDetails" name="incidentDetails" rows="4" disabled
                      class="border border-gray-300 text-gray-900 text-sm rounded-lg block w-full p-2.5 bg-gray-100 resize-none">${not empty incidentDetails ? incidentDetails : ''}</textarea>
            <c:if test='${not empty incidentDetailsError}'>
                <div class="flex items-center gap-1 text-red-500 text-sm mt-2">
                    <!-- Ícone de erro -->
                    <p>${incidentDetailsError}</p>
                </div>
            </c:if>
        </div>

        <!-- Botão de submit mantido e funcional -->
        <button type="submit"
                class="w-full bg-blue-600 hover:bg-blue-700 text-white font-semibold py-3 px-4 rounded-md transition duration-200 text-md mt-4">
            Confirmar Moderação
        </button>

    </form>
</section>

<%@ include file="../_footer.jsp"%>

<!-- Desativa o SimpleMDE quando disabled -->
<script>
    document.addEventListener('DOMContentLoaded', function() {
        const isDisabled = document.getElementById('incidentDetails').disabled;
        if (!isDisabled) {
            const simplemde = new SimpleMDE({
                element: document.getElementById("incidentDetails"),
                spellChecker: false,
                forceSync: true,
                placeholder: "Digite o conteúdo em Markdown...",
            });
        }
    });
</script>

</body>
</html>