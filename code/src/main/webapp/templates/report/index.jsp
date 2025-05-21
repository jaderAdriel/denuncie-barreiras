<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ include file="../_head.jsp"%>
    <title>Denúncias</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <link href="https://cdn.jsdelivr.net/npm/flowbite@3.1.2/dist/flowbite.min.css" rel="stylesheet" />
    <script src="https://cdn.jsdelivr.net/npm/flowbite@3.1.2/dist/flowbite.min.js"></script>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body class="bg-gray-50">
<%@ include file="../_sidebar.jsp"%>
<%@ include file="../_header.jsp"%>

<section class="p-4 md:p-6">
    <div class="flex flex-col md:flex-row items-start md:items-center justify-between mb-6 gap-4">
        <div class="flex items-center gap-x-3">
            <h2 class="text-lg font-medium text-gray-800">Listagem de Denúncias</h2>
            <span class="px-3 py-1 text-xs text-blue-600 bg-blue-100 rounded-full">${reportList.size()} leis</span>
        </div>
        <a href="/report/create/" class="flex items-center justify-center px-5 py-2 text-sm text-white transition-colors duration-200 bg-blue-500 rounded-lg gap-x-2 hover:bg-blue-600 w-full md:w-auto">
            <span class="material-symbols-outlined">add</span>
            <span>Efetuar Denúncia</span>
        </a>
    </div>

    <!-- Mobile Cards View -->
    <div class="block md:hidden space-y-4">
        <c:if test="${not empty reportList}">
            <c:forEach items="${reportList}" var="report">
                <div class="bg-white p-4 rounded-lg shadow border border-gray-200">
                    <div class="flex justify-between items-start">
                        <div>
                            <h3 class="font-medium text-gray-800">${report.id}</h3>
                            <p class="text-sm text-gray-500 mt-1">${report.creationDate.toString()}</p>
                        </div>
                        <div class="flex gap-x-2">
                            <a href="/report/update/${report.id}" class="text-gray-500 hover:text-blue-500">
                                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-5 h-5">
                                    <path stroke-linecap="round" stroke-linejoin="round" d="M16.862 4.487l1.687-1.688a1.875 1.875 0 112.652 2.652L10.582 16.07a4.5 4.5 0 01-1.897 1.13L6 18l.8-2.685a4.5 4.5 0 011.13-1.897l8.932-8.931zm0 0L19.5 7.125M18 14v4.75A2.25 2.25 0 0115.75 21H5.25A2.25 2.25 0 013 18.75V8.25A2.25 2.25 0 015.25 6H10" />
                                </svg>
                            </a>
                            <a href="/report/delete/${report.id}" class="text-gray-500 hover:text-red-500">
                                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-5 h-5">
                                    <path stroke-linecap="round" stroke-linejoin="round" d="M14.74 9l-.346 9m-4.788 0L9.26 9m9.968-3.21c.342.052.682.107 1.022.166m-1.022-.165L18.16 19.673a2.25 2.25 0 01-2.244 2.077H8.084a2.25 2.25 0 01-2.244-2.077L4.772 5.79m14.456 0a48.108 48.108 0 00-3.478-.397m-12 .562c.34-.059.68-.114 1.022-.165m0 0a48.11 48.11 0 013.478-.397m7.5 0v-.916c0-1.18-.91-2.164-2.09-2.201a51.964 51.964 0 00-3.32 0c-1.18.037-2.09 1.022-2.09 2.201v.916m7.5 0a48.667 48.667 0 00-7.5 0" />
                                </svg>
                            </a>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </c:if>
    </div>

    <!-- Desktop Table View -->
    <div class="hidden md:block">
        <div class="overflow-x-auto">
            <div class="inline-block min-w-full align-middle">
                <div class="overflow-hidden border border-gray-200 rounded-lg">
                    <table class="min-w-full divide-y divide-gray-200">
                        <thead class="bg-gray-50">
                        <tr>
                            <th scope="col" class="py-3.5 px-4 text-sm font-normal text-left rtl:text-right text-gray-500">
                                <div class="flex items-left">
                                    <input type="checkbox" class="text-blue-500 border-gray-300 rounded">
                                    <span>Tipo</span>
                                </div>
                            </th>

                            <th scope="col" class="px-4 py-3.5 text-sm font-normal text-left rtl:text-right text-gray-500">
                                <button class="flex items-center gap-x-2">
                                    <span>Data</span>
                                </button>
                            </th>

                            <th scope="col" class="px-4 py-3.5 text-sm font-normal text-left rtl:text-right text-gray-500">
                                <button class="flex items-center gap-x-2">
                                    <span>Autor</span>
                                </button>
                            </th>

                            <th scope="col" class="px-4 py-3.5 text-sm font-normal rtl:text-right text-gray-500">
                                <span>Ações</span>
                            </th>
                        </tr>
                        </thead>
                        <tbody class="bg-white divide-y divide-gray-200">
                        <c:if test="${not empty reportList}">
                            <c:forEach items="${reportList}" var="report">
                                <tr>
                                    <td class="px-4 py-4 text-sm font-medium text-gray-700 whitespace-nowrap">
                                        <div class="inline-flex items-center gap-x-3">
                                            <input type="checkbox" class="text-blue-500 border-gray-300 rounded">
                                            <div>
                                                <h2 class="font-medium text-gray-800">${report.type.getTranslation()}</h2>
                                            </div>
                                        </div>
                                    </td>
                                    <td class="px-4 py-4 text-sm font-medium text-gray-700 whitespace-nowrap">
                                            ${report.creationDate.toString()}
                                    </td>
                                    <td class="px-4 py-4 text-sm text-gray-500 whitespace-nowrap">
                                        <c:if test="${not empty report.reporter and not report.anonymousReport}">
                                            ${report.reporter.name}
                                        </c:if>
                                        <c:if test="${report.anonymousReport or not empty report.reporter}">
                                            Anônimo
                                        </c:if>
                                    </td>
                                    <td class="px-4 py-4 text-sm whitespace-nowrap">
                                        <div class="flex items-center gap-x-6">
                                            <a href="/report/update/${report.type}" class="text-gray-500 transition-colors duration-200 hover:text-blue-500 focus:outline-none">
                                                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-5 h-5">
                                                    <path stroke-linecap="round" stroke-linejoin="round" d="M16.862 4.487l1.687-1.688a1.875 1.875 0 112.652 2.652L10.582 16.07a4.5 4.5 0 01-1.897 1.13L6 18l.8-2.685a4.5 4.5 0 011.13-1.897l8.932-8.931zm0 0L19.5 7.125M18 14v4.75A2.25 2.25 0 0115.75 21H5.25A2.25 2.25 0 013 18.75V8.25A2.25 2.25 0 015.25 6H10" />
                                                </svg>
                                            </a>

                                            <a href="/report/delete/${report.type}" class="text-gray-500 transition-colors duration-200 hover:text-red-500 focus:outline-none">
                                                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-5 h-5">
                                                    <path stroke-linecap="round" stroke-linejoin="round" d="M14.74 9l-.346 9m-4.788 0L9.26 9m9.968-3.21c.342.052.682.107 1.022.166m-1.022-.165L18.16 19.673a2.25 2.25 0 01-2.244 2.077H8.084a2.25 2.25 0 01-2.244-2.077L4.772 5.79m14.456 0a48.108 48.108 0 00-3.478-.397m-12 .562c.34-.059.68-.114 1.022-.165m0 0a48.11 48.11 0 013.478-.397m7.5 0v-.916c0-1.18-.91-2.164-2.09-2.201a51.964 51.964 0 00-3.32 0c-1.18.037-2.09 1.022-2.09 2.201v.916m7.5 0a48.667 48.667 0 00-7.5 0" />
                                                </svg>
                                            </a>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:if>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>

    <!-- Paginação -->
    <div class="flex flex-col md:flex-row items-center justify-between mt-6 gap-4">
        <div class="flex-1 text-sm text-gray-500">
            Mostrando 1 a ${reportList.size()} de ${reportList.size()} resultados
        </div>
        <div class="flex items-center gap-4">
            <a href="#" class="flex items-center px-4 py-2 text-sm text-gray-700 capitalize transition-colors duration-200 bg-white border rounded-md gap-x-2 hover:bg-gray-100">
                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-5 h-5 rtl:-scale-x-100">
                    <path stroke-linecap="round" stroke-linejoin="round" d="M6.75 15.75L3 12m0 0l3.75-3.75M3 12h18" />
                </svg>
                <span class="hidden sm:inline">Anterior</span>
            </a>

            <div class="items-center hidden sm:flex gap-x-1">
                <a href="#" class="px-3 py-1 text-sm text-blue-500 rounded-md bg-blue-100/60">1</a>
                <a href="#" class="px-3 py-1 text-sm text-gray-500 rounded-md hover:bg-gray-100">2</a>
                <a href="#" class="px-3 py-1 text-sm text-gray-500 rounded-md hover:bg-gray-100">3</a>
                <span class="px-1">...</span>
                <a href="#" class="px-3 py-1 text-sm text-gray-500 rounded-md hover:bg-gray-100">8</a>
            </div>

            <a href="#" class="flex items-center px-4 py-2 text-sm text-gray-700 capitalize transition-colors duration-200 bg-white border rounded-md gap-x-2 hover:bg-gray-100">
                <span class="hidden sm:inline">Próximo</span>
                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-5 h-5 rtl:-scale-x-100">
                    <path stroke-linecap="round" stroke-linejoin="round" d="M17.25 8.25L21 12m0 0l-3.75 3.75M21 12H3" />
                </svg>
            </a>
        </div>
    </div>
</section>

<%@ include file="../_footer.jsp"%>

</body>
</html>