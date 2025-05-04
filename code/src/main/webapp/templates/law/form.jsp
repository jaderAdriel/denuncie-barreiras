<%--
  Created by IntelliJ IDEA.
  User: jader
  Date: 5/3/25
  Time: 10:15 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <title>Form Lei</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="https://cdn.tailwindcss.com"></script>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@24,400,0,0" />
    <link href="https://cdn.jsdelivr.net/npm/flowbite@3.1.2/dist/flowbite.min.css" rel="stylesheet" />
    <script src="https://cdn.jsdelivr.net/npm/flowbite@3.1.2/dist/flowbite.min.js"></script>
    <style>
        .sidebar-overlay {
            display: none;
            position: fixed;
            top: 0;
            left: 0;
            right: 0;
            bottom: 0;
            background-color: rgba(0, 0, 0, 0.5);
            z-index: 30;
        }

        @media (max-width: 640px) {
            .sidebar-overlay.active {
                display: block;
            }
        }
    </style>
</head>
<body class="bg-gray-100 ">

<!-- Botão para abrir o sidebar em mobile -->
<button data-drawer-target="default-sidebar" data-drawer-toggle="default-sidebar" aria-controls="default-sidebar" type="button" class="fixed z-30 inline-flex items-center p-2 mt-2 ms-3 text-sm text-gray-500 rounded-lg sm:hidden hover:bg-gray-100 focus:outline-none focus:ring-2 focus:ring-gray-200 text-gray-400 hover:bg-gray-700 focus:ring-gray-600">
    <span class="sr-only">Abrir menu</span>
    <svg class="w-6 h-6" aria-hidden="true" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg">
        <path clip-rule="evenodd" fill-rule="evenodd" d="M2 4.75A.75.75 0 012.75 4h14.5a.75.75 0 010 1.5H2.75A.75.75 0 012 4.75zm0 10.5a.75.75 0 01.75-.75h7.5a.75.75 0 010 1.5h-7.5a.75.75 0 01-.75-.75zM2 10a.75.75 0 01.75-.75h14.5a.75.75 0 010 1.5H2.75A.75.75 0 012 10z"></path>
    </svg>
</button>

<!-- Overlay para fechar o sidebar ao clicar fora (mobile) -->
<div id="sidebar-overlay" class="sidebar-overlay" data-drawer-target="default-sidebar" data-drawer-toggle="default-sidebar" aria-controls="default-sidebar"></div>

<!-- Sidebar -->
<aside id="default-sidebar" class="fixed top-0 left-0 z-40 w-64 h-screen transition-transform -translate-x-full sm:translate-x-0" aria-label="Sidebar">
    <div class="h-full px-3 py-4 overflow-y-auto bg-gray-50 ">
        <div class="flex items-center justify-between px-2 mb-5">
            <span class="text-xl font-semibold text-gray-800 ">Menu</span>
            <button type="button" data-drawer-target="default-sidebar" data-drawer-toggle="default-sidebar" aria-controls="default-sidebar" class="text-gray-400  sm:hidden bg-transparent hover:bg-gray-200 hover:text-gray-900 rounded-lg text-sm p-1.5 inline-flex items-center hover:bg-gray-600 hover:text-white">
                <svg class="w-5 h-5" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg">
                    <path fill-rule="evenodd" d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z" clip-rule="evenodd"></path>
                </svg>
                <span class="sr-only">Fechar menu</span>
            </button>
        </div>

        <ul class="space-y-2 font-medium">
            <li>
                <a href="#" class="flex items-center p-2 text-gray-900 rounded-lg  hover:bg-gray-100 hover:bg-gray-100 group">
                    <svg class="shrink-0 w-5 h-5 text-gray-500 transition duration-75 group-hover:text-blue-500" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 22 21">
                        <path d="M16.975 11H10V4.025a1 1 0 0 0-1.066-.998 8.5 8.5 0 1 0 9.039 9.039.999.999 0 0 0-1-1.066h.002Z"/>
                        <path d="M12.5 0c-.157 0-.311.01-.565.027A1 1 0 0 0 11 1.02V10h8.975a1 1 0 0 0 1-.935c.013-.188.028-.374.028-.565A8.51 8.51 0 0 0 12.5 0Z"/>
                    </svg>
                    <span class="ms-3">Dashboard</span>
                </a>
            </li>
            <li>
                <a href="#" class="flex items-center p-2 text-gray-900 rounded-lg  hover:bg-gray-100 hover:bg-gray-100 group">
                    <svg class="shrink-0 w-5 h-5 text-gray-500 transition duration-75 group-hover:text-blue-500" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 20 18">
                        <path d="M14 2a3.963 3.963 0 0 0-1.4.267 6.439 6.439 0 0 1-1.331 6.638A4 4 0 1 0 14 2Zm1 9h-1.264A6.957 6.957 0 0 1 15 15v2a2.97 2.97 0 0 1-.184 1H19a1 1 0 0 0 1-1v-1a5.006 5.006 0 0 0-5-5ZM6.5 9a4.5 4.5 0 1 0 0-9 4.5 4.5 0 0 0 0 9ZM8 10H5a5.006 5.006 0 0 0-5 5v2a1 1 0 0 0 1 1h11a1 1 0 0 0 1-1v-2a5.006 5.006 0 0 0-5-5Z"/>
                    </svg>
                    <span class="flex-1 ms-3 whitespace-nowrap">Usuários</span>
                </a>
            </li>
            <li>
                <a href="#" class="flex items-center p-2 text-gray-900 rounded-lg  hover:bg-gray-100 hover:bg-gray-100 group">
                    <svg class="shrink-0 w-5 h-5 text-gray-500 transition duration-75 group-hover:text-blue-500" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 20 20">
                        <path d="M10 .5a9.5 9.5 0 1 0 9.5 9.5A9.51 9.51 0 0 0 10 .5ZM9.5 4a1.5 1.5 0 1 1 0 3 1.5 1.5 0 0 1 0-3ZM12 15H8a1 1 0 0 1 0-2h1v-3H8a1 1 0 0 1 0-2h2a1 1 0 0 1 1 1v4h1a1 1 0 0 1 0 2Z"/>
                    </svg>
                    <span class="flex-1 ms-3 whitespace-nowrap">Denúncias</span>
                    <!-- Opcional: Badge para mostrar número de denúncias pendentes -->
                    <span class="inline-flex items-center justify-center w-3 h-3 p-3 ms-3 text-sm font-medium text-red-800 bg-red-100 rounded-full bg-red-900 text-red-300">5</span>
                </a>
            </li>

            <li>
                <a href="#" class="flex items-center p-2 text-gray-900 rounded-lg  hover:bg-gray-100 hover:bg-gray-100 group">
                    <svg class="shrink-0 w-5 h-5 text-gray-500 transition duration-75 group-hover:text-blue-500" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" >
                        <path fill-rule="evenodd" d="M3 2.25a.75.75 0 0 1 .75.75v.54l1.838-.46a9.75 9.75 0 0 1 6.725.738l.108.054A8.25 8.25 0 0 0 18 4.524l3.11-.732a.75.75 0 0 1 .917.81 47.784 47.784 0 0 0 .005 10.337.75.75 0 0 1-.574.812l-3.114.733a9.75 9.75 0 0 1-6.594-.77l-.108-.054a8.25 8.25 0 0 0-5.69-.625l-2.202.55V21a.75.75 0 0 1-1.5 0V3A.75.75 0 0 1 3 2.25Z" clip-rule="evenodd" />
                    </svg>
                    <span class="flex-1 ms-3 whitespace-nowrap">Barreiras/Cenários</span>
                </a>
            </li>

            <li>
                <a href="/law/index/" class="flex items-center p-2 text-gray-900 rounded-lg bg-gray-200  hover:bg-gray-400 hover:bg-gray-400 group">
                    <svg class="shrink-0 w-5 h-5 transition duration-75 text-blue-500" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 20 20">
                        <path d="M5 5V.13a2.96 2.96 0 0 0-1.293.749L.879 3.707A2.96 2.96 0 0 0 .13 5H5Z"/>
                        <path d="M6.737 11.061a2.961 2.961 0 0 1 .81-1.515l6.117-6.116A4.839 4.839 0 0 1 16 2.141V2a1.97 1.97 0 0 0-1.933-2H7v5a2 2 0 0 1-2 2H0v11a1.969 1.969 0 0 0 1.933 2h12.134A1.97 1.97 0 0 0 16 18v-3.093l-1.546 1.546c-.413.413-.94.695-1.513.81l-3.4.679a2.947 2.947 0 0 1-1.85-.227 2.96 2.96 0 0 1-1.635-3.257l.681-3.397Z"/>
                        <path d="M8.961 16a.93.93 0 0 0 .189-.019l3.4-.679a.961.961 0 0 0 .49-.263l6.118-6.117a2.884 2.884 0 0 0-4.079-4.078l-6.117 6.117a.96.96 0 0 0-.263.491l-.679 3.4A.961.961 0 0 0 8.961 16Zm7.477-9.8a.958.958 0 0 1 .68-.281.961.961 0 0 1 .682 1.644l-.315.315-1.36-1.36.313-.318Zm-5.911 5.911 4.236-4.236 1.359 1.359-4.236 4.237-1.7.339.341-1.699Z"/>
                    </svg>
                    <span class="flex-1 ms-3 whitespace-nowrap">Leis</span>
                </a>
            </li>
        </ul>
    </div>
</aside>

<!-- ... (cabeçalho e sidebar permanecem iguais) ... -->

<div class="p-4 sm:ml-64">
    <div class="max-w-4xl mx-auto bg-white rounded-lg shadow-md p-6">
        <h2 class="text-2xl font-bold text-gray-800 mb-6">
            <c:choose>
                <c:when test="${method == 'POST'}">Adicionar Lei</c:when>
                <c:when test="${method == 'PUT'}">Alterar Lei ${law.getCode()}</c:when>
                <c:when test="${method == 'DELETE'}">Excluir Lei ${law.getCode()}</c:when>
            </c:choose>
        </h2>
        <form action="${action}" method="POST" class="space-y-6">
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
            <div>
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
            <div>
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
            <div>
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
            <div>
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

    </div>
</div>

<!-- ... (script permanece igual) ... -->
<script>
    // Adiciona evento para fechar o sidebar ao clicar no overlay
    document.getElementById('sidebar-overlay').addEventListener('click', function() {
        const sidebar = document.getElementById('default-sidebar');
        sidebar.classList.add('-translate-x-full');
        this.classList.remove('active');

        const overlay = document.getElementById('sidebar-overlay');

    });

    // Adiciona evento para mostrar o overlay quando o sidebar é aberto
    document.querySelector('[data-drawer-toggle="default-sidebar"]').addEventListener('click', function() {
        const sidebar = document.getElementById('default-sidebar');
        const overlay = document.getElementById('sidebar-overlay');

        // if (sidebar.classList.contains('-translate-x-full')) {
        //     overlay.classList.add('active');
        // } else {
        //     overlay.classList.remove('active');
        // }
    });
</script>
</body>
</html>