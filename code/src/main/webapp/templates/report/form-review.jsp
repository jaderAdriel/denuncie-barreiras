<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ include file="../_head.jsp"%>
    <title>Revisar Denúncia</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <!-- EasyMDE CSS -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/simplemde/latest/simplemde.min.css">
    <script src="https://cdn.jsdelivr.net/simplemde/latest/simplemde.min.js"></script>

    <link rel='stylesheet' type='text/css' media='screen' href="/static/css/editor.css">
    <style>
        .info-card {
            background-color: #f8fafc;
            border: 1px solid #e2e8f0;
            border-radius: 0.5rem;
            padding: 1.25rem;
            margin-bottom: 1.5rem;
        }
        .info-label {
            font-weight: 500;
            color: #64748b;
            margin-bottom: 0.25rem;
        }
        .info-value {
            font-weight: 500;
            color: #1e293b;
            margin-bottom: 0.75rem;
        }
        .section-title {
            font-size: 1.125rem;
            font-weight: 600;
            color: #1e293b;
            margin-bottom: 1rem;
            padding-bottom: 0.5rem;
            border-bottom: 1px solid #e2e8f0;
        }
        .readonly-field {
            background-color: #f8fafc;
            border: 1px solid #e2e8f0;
            border-radius: 0.375rem;
            padding: 0.75rem;
            min-height: 2.5rem;
        }
    </style>
</head>
<body>
<%@ include file="../_sidebar.jsp"%>
<%@ include file="../_header.jsp"%>
<c:set var="monthNames" value="Janeiro, Fevereiro, Março, Abril, Maio, Junho, Julho, Agosto, Setembro, Outubro, Novembro, Dezembro" />
<c:set var="monthArray" value="${fn:split(monthNames, ', ')}" />

<section class="p-4 w-full">
    <div class="flex flex-col md:flex-row items-start md:items-center justify-between mb-6 gap-4">
        <div class="flex items-center gap-x-3">
            <h2 class="text-2xl font-semibold text-gray-800">Revisar Denúncia</h2>
            <span class="bg-blue-100 text-blue-800 text-sm font-medium px-2.5 py-0.5 rounded">#<c:out value="${report.id}"/></span>
        </div>
        <div class="text-sm text-gray-500">
            Criada em: ${report.creationDate.toString()}
        </div>
    </div>

    <form action="${action}" method="${method}" class="bg-white rounded-lg shadow-sm border border-gray-200 overflow-hidden">
        <!-- Report Information Section -->
        <div class="p-6 border-b border-gray-200">
            <h3 class="section-title">Detalhes da Denúncia</h3>

            <div class="grid grid-cols-1 md:grid-cols-2 gap-6 mb-4">
                <div>
                    <p class="info-label">Ambiente</p>
                    <div class="info-value readonly-field">
                        <c:out value="${report.ambient.getTranslation()}"/>
                    </div>
                </div>

                <div>
                    <p class="info-label">Tipo de Barreira</p>
                    <div class="info-value readonly-field">
                        <c:out value="${report.type.getTranslation()}"/>
                    </div>
                </div>
            </div>

            <div class="grid grid-cols-1 md:grid-cols-2 gap-6 mb-4">
                <div>
                    <p class="info-label">Denunciante</p>
                    <div class="info-value readonly-field">
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
                    <div id="incidentDetails" class="readonly-field ">
                        ${not empty report.eventDetailing ? report.eventDetailing : 'Nenhum detalhamento fornecido.'}
                    </div>
                </div>
            </div>
        </div>

        <!-- Review Section -->
        <div class="p-6">
            <h3 class="section-title">Revisão do Moderador</h3>

            <c:choose>
                <%-- If a review already exists --%>
                <c:when test="${not empty review}">
                    <div class="bg-blue-50 border border-blue-200 text-blue-800 p-4 rounded-md mb-6">
                        <div class="flex items-start">
                            <svg class="w-5 h-5 mr-2 mt-0.5 flex-shrink-0" fill="currentColor" viewBox="0 0 20 20">
                                <path fill-rule="evenodd" d="M18 10a8 8 0 11-16 0 8 8 0 0116 0zm-7-4a1 1 0 11-2 0 1 1 0 012 0zM9 9a1 1 0 000 2v3a1 1 0 001 1h1a1 1 0 100-2h-1V9z" clip-rule="evenodd"></path>
                            </svg>
                            <div>
                                <p class="font-medium">Esta denúncia já foi revisada.</p>
                                <p class="text-sm mt-1">
                                    Revisado por: <span class="font-semibold"><c:out value="${review.author.name}"/></span> em
                                    <span class="font-semibold">${review.getCreatAtFormated()}</span>
                                </p>
                            </div>
                        </div>
                    </div>

                    <div class="grid grid-cols-1 md:grid-cols-2 gap-6 mb-6">
                        <div>
                            <p class="info-label">Resultado da Análise</p>
                            <div class="info-value readonly-field">
                                <c:choose>
                                    <c:when test="${review.isValid == true}">Válida</c:when>
                                    <c:otherwise>Inválida</c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </div>

                    <div class="mb-6">
                        <p class="info-label">Comentário do Moderador</p>
                        <div class="mt-2">
                            <div class="readonly-field min-h-[120px]">
                                <c:out value="${not empty review.comment ? review.comment : 'Nenhum comentário foi adicionado.'}"/>
                            </div>
                        </div>
                    </div>

                    <div class="bg-gray-50 px-4 py-3 text-right sm:px-6 rounded-b-lg">
                        <a href="/report/index/" class="inline-flex items-center px-4 py-2 border border-gray-300 shadow-sm text-sm font-medium rounded-md text-gray-700 bg-white hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500">
                            Voltar para a lista
                        </a>
                    </div>
                </c:when>

                <%-- If no review exists, show inputs for the moderator to review --%>
                <c:otherwise>
                    <div class="grid grid-cols-1 gap-6 mb-6">
                        <div>
                            <p class="info-label">Status da Denúncia <span class="text-red-500">*</span></p>
                            <div class="mt-2 space-y-3">
                                <div class="flex items-center">
                                    <input id="isValidTrue" name="isValid" type="radio" value="true" required
                                           class="h-4 w-4 border-gray-300 text-indigo-600 focus:ring-indigo-600"
                                    <c:if test="${isValid == true}"> checked </c:if>>
                                    <label for="isValidTrue" class="ml-2 block text-sm font-medium text-gray-700">Válida</label>
                                </div>
                                <div class="flex items-center">
                                    <input id="isValidFalse" name="isValid" type="radio" value="false" required
                                           class="h-4 w-4 border-gray-300 text-indigo-600 focus:ring-indigo-600"
                                    <c:if test="${isValid == false}"> checked </c:if>>
                                    <label for="isValidFalse" class="ml-2 block text-sm font-medium text-gray-700">Inválida</label>
                                </div>
                            </div>
                            <c:if test="${not empty isValidError}"><p class="mt-2 text-sm text-red-600">${isValidError}</p></c:if>
                        </div>
                    </div>

                    <div class="mb-6">
                        <label for="comment" class="block text-sm font-medium text-gray-700 mb-1">Comentário do Moderador <span class="text-red-500">*</span></label>
                        <textarea id="comment" name="comment" rows="4" required
                                  class="shadow-sm focus:ring-indigo-500 focus:border-indigo-500 block w-full sm:text-sm border border-gray-300 rounded-md p-3"
                                  placeholder="Forneça detalhes sobre sua análise..."><c:out value="${comment}"/></textarea>
                        <c:if test="${not empty commentError}"><p class="mt-2 text-sm text-red-600">${commentError}</p></c:if>
                    </div>

                    <div class="bg-gray-50 px-4 py-3 text-right sm:px-6 rounded-b-lg">
                        <a href="/report/index/" class="mr-3 inline-flex items-center px-4 py-2 border border-gray-300 shadow-sm text-sm font-medium rounded-md text-gray-700 bg-white hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500">
                            Cancelar
                        </a>
                        <button type="submit"
                                class="inline-flex items-center px-4 py-2 border border-transparent text-sm font-medium rounded-md shadow-sm text-white bg-indigo-600 hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500">
                            Salvar Revisão
                        </button>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </form>
</section>

<%@ include file="../_footer.jsp"%>


</body>
</html>