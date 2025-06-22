<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:include page="../__base_start.jsp">
    <jsp:param name="title" value="Denúncias" />
</jsp:include>


<div class="card card-default p-0 shadow-sm">

    <div class="card-header">
      <span>
        <h1 class="m-0">Leis</h1>
        <p class="">Gerenciamento de leis</p>
      </span>
        <div class="card-header-action">
            <a href="${pageContext.request.contextPath}/scenario/create/" class="btn btn-success py-1 px-3 mt-2" title="Adicionar nova denúncia"><i class="mdi mdi-plus mdi-18px"></i></a>
        </div>
    </div>

    <div class="card-body p-4">
        <table class="min-w-full divide-y divide-gray-200 table table-sm table-hover data-table">
            <thead class="bg-gray-50">
            <tr>
                <th scope="col" class="py-3.5 px-4 text-sm font-normal text-left rtl:text-right text-gray-500">
                    <div class="flex items-center gap-x-3">
                        <input type="checkbox" class="text-blue-500 border-gray-300 rounded">
                        <span>Código</span>
                    </div>
                </th>

                <th scope="col" class="px-4 py-3.5 text-sm font-normal text-left rtl:text-right text-gray-500">
                    <button class="flex items-center gap-x-2">
                        <span>Data</span>
                        <svg class="h-3" viewBox="0 0 10 11" fill="none" xmlns="http://www.w3.org/2000/svg">
                            <path d="M2.13347 0.0999756H2.98516L5.01902 4.79058H3.86226L3.45549 3.79907H1.63772L1.24366 4.79058H0.0996094L2.13347 0.0999756ZM2.54025 1.46012L1.96822 2.92196H3.11227L2.54025 1.46012Z" fill="currentColor" stroke="currentColor" stroke-width="0.1" />
                            <path d="M0.722656 9.60832L3.09974 6.78633H0.811638V5.87109H4.35819V6.78633L2.01925 9.60832H4.43446V10.5617H0.722656V9.60832Z" fill="currentColor" stroke="currentColor" stroke-width="0.1" />
                            <path d="M8.45558 7.25664V7.40664H8.60558H9.66065C9.72481 7.40664 9.74667 7.42274 9.75141 7.42691C9.75148 7.42808 9.75146 7.42993 9.75116 7.43262C9.75001 7.44265 9.74458 7.46304 9.72525 7.49314C9.72522 7.4932 9.72518 7.49326 9.72514 7.49332L7.86959 10.3529L7.86924 10.3534C7.83227 10.4109 7.79863 10.418 7.78568 10.418C7.77272 10.418 7.73908 10.4109 7.70211 10.3534L7.70177 10.3529L5.84621 7.49332C5.84617 7.49325 5.84612 7.49318 5.84608 7.49311C5.82677 7.46302 5.82135 7.44264 5.8202 7.43262C5.81989 7.42993 5.81987 7.42808 5.81994 7.42691C5.82469 7.42274 5.84655 7.40664 5.91071 7.40664H6.96578H7.11578V7.25664V0.633865C7.11578 0.42434 7.29014 0.249976 7.49967 0.249976H8.07169C8.28121 0.249976 8.45558 0.42434 8.45558 0.633865V7.25664Z" fill="currentColor" stroke="currentColor" stroke-width="0.3" />
                        </svg>
                    </button>
                </th>

                <th scope="col" class="px-4 py-3.5 text-sm font-normal text-left rtl:text-right text-gray-500">
                    <button class="flex items-center gap-x-2">
                        <span>Título</span>
                        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="2" stroke="currentColor" class="w-4 h-4">
                            <path stroke-linecap="round" stroke-linejoin="round" d="M9.879 7.519c1.171-1.025 3.071-1.025 4.242 0 1.172 1.025 1.172 2.687 0 3.712-.203.179-.43.326-.67.442-.745.361-1.45.999-1.45 1.827v.75M21 12a9 9 0 11-18 0 9 9 0 0118 0zm-9 5.25h.008v.008H12v-.008z" />
                        </svg>
                    </button>
                </th>

                <th scope="col" class="px-4 py-3.5 text-sm font-normal rtl:text-right text-gray-500">
                    <span>Ações</span>
                </th>
            </tr>
            </thead>
            <tbody class="bg-white divide-y divide-gray-200">
            <c:if test="${not empty scenarioList}">
                <c:forEach items="${scenarioList}" var="scenario">
                    <tr>
                        <td class="px-4 py-4 text-sm font-medium text-gray-700 whitespace-nowrap">
                            <div class="inline-flex items-center gap-x-3">
                                <input type="checkbox" class="text-blue-500 border-gray-300 rounded">
                                <div>
                                    <h2 class="font-medium text-gray-800">${scenario.id}</h2>
                                </div>
                            </div>
                        </td>
                        <td class="px-4 py-4 text-sm font-medium text-gray-700 whitespace-nowrap">
                                ${scenario.creationDate.toString()}
                        </td>
                        <td class="px-4 py-4 text-sm text-gray-500 whitespace-nowrap">${scenario.title}</td>
                        <td class="px-4 py-4 text-sm whitespace-nowrap">
                            <div class="flex items-center justify-end gap-x-6">
                                <a href="/scenario/update/${scenario.id}" class="text-gray-500 transition-colors duration-200 hover:text-blue-500 focus:outline-none">
                                    <span class="material-symbols-outlined text-md">edit_note</span>
                                </a>

                                <a href="/scenario/delete/${scenario.id}" class="text-gray-500 transition-colors duration-200 hover:text-red-500 focus:outline-none">
                                    <span class="material-symbols-outlined text-md">delete</span>
                                </a>

                                <a href="/public/scenario/${scenario.id}/" class="text-gray-500 transition-colors duration-200 hover:text-red-500 focus:outline-none">
                                    <span class="material-symbols-outlined text-md">open_in_new</span>
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

<%@ include file="../__base_end.jsp"%>