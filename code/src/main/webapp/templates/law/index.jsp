<%--
  Created by IntelliJ IDEA.
  User: jader
  Date: 5/3/25
  Time: 11:03 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
  <title>Listagem de Leis</title>
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
<body class="bg-gray-100">

<!-- Botão para abrir o sidebar em mobile -->
<button data-drawer-target="default-sidebar" data-drawer-toggle="default-sidebar" aria-controls="default-sidebar" type="button" class="fixed z-30 inline-flex items-center p-2 mt-2 ms-3 text-sm text-gray-500 rounded-lg sm:hidden hover:bg-gray-100 focus:outline-none focus:ring-2 focus:ring-gray-200">
  <span class="sr-only">Abrir menu</span>
  <svg class="w-6 h-6" aria-hidden="true" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg">
    <path clip-rule="evenodd" fill-rule="evenodd" d="M2 4.75A.75.75 0 012.75 4h14.5a.75.75 0 010 1.5H2.75A.75.75 0 012 4.75zm0 10.5a.75.75 0 01.75-.75h7.5a.75.75 0 010 1.5h-7.5a.75.75 0 01-.75-.75zM2 10a.75.75 0 01.75-.75h14.5a.75.75 0 010 1.5H2.75A.75.75 0 012 10z"></path>
  </svg>
</button>

<!-- Overlay para fechar o sidebar ao clicar fora (mobile) -->
<div id="sidebar-overlay" class="sidebar-overlay" data-drawer-target="default-sidebar" data-drawer-toggle="default-sidebar" aria-controls="default-sidebar"></div>

<!-- Sidebar -->
<aside id="default-sidebar" class="fixed top-0 left-0 z-40 w-64 h-screen transition-transform -translate-x-full sm:translate-x-0" aria-label="Sidebar">
  <div class="h-full px-3 py-4 overflow-y-auto bg-white">
    <div class="flex items-center justify-between px-2 mb-5">
      <span class="text-xl font-semibold text-gray-800">Menu</span>
      <button type="button" data-drawer-target="default-sidebar" data-drawer-toggle="default-sidebar" aria-controls="default-sidebar" class="text-gray-400 sm:hidden bg-transparent hover:bg-gray-200 hover:text-gray-900 rounded-lg text-sm p-1.5 inline-flex items-center">
        <svg class="w-5 h-5" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg">
          <path fill-rule="evenodd" d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z" clip-rule="evenodd"></path>
        </svg>
        <span class="sr-only">Fechar menu</span>
      </button>
    </div>

    <ul class="space-y-2 font-medium">
      <li>
        <a href="#" class="flex items-center p-2 text-gray-900 rounded-lg hover:bg-gray-100 group">
          <svg class="w-5 h-5 text-gray-500 transition duration-75 group-hover:text-gray-900" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 22 21">
            <path d="M16.975 11H10V4.025a1 1 0 0 0-1.066-.998 8.5 8.5 0 1 0 9.039 9.039.999.999 0 0 0-1-1.066h.002Z"/>
            <path d="M12.5 0c-.157 0-.311.01-.565.027A1 1 0 0 0 11 1.02V10h8.975a1 1 0 0 0 1-.935c.013-.188.028-.374.028-.565A8.51 8.51 0 0 0 12.5 0Z"/>
          </svg>
          <span class="ms-3">Dashboard</span>
        </a>
      </li>
      <li>
        <a href="#" class="flex items-center p-2 text-gray-900 rounded-lg hover:bg-gray-100 group">
          <svg class="shrink-0 w-5 h-5 text-gray-500 transition duration-75 group-hover:text-gray-900" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 20 18">
            <path d="M14 2a3.963 3.963 0 0 0-1.4.267 6.439 6.439 0 0 1-1.331 6.638A4 4 0 1 0 14 2Zm1 9h-1.264A6.957 6.957 0 0 1 15 15v2a2.97 2.97 0 0 1-.184 1H19a1 1 0 0 0 1-1v-1a5.006 5.006 0 0 0-5-5ZM6.5 9a4.5 4.5 0 1 0 0-9 4.5 4.5 0 0 0 0 9ZM8 10H5a5.006 5.006 0 0 0-5 5v2a1 1 0 0 0 1 1h11a1 1 0 0 0 1-1v-2a5.006 5.006 0 0 0-5-5Z"/>
          </svg>
          <span class="flex-1 ms-3 whitespace-nowrap">Usuários</span>
        </a>
      </li>
      <li>
        <a href="#" class="flex items-center p-2 text-gray-900 rounded-lg hover:bg-gray-100 group">
          <svg class="shrink-0 w-5 h-5 text-gray-500 transition duration-75 group-hover:text-gray-900" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 20 20">
            <path d="M10 .5a9.5 9.5 0 1 0 9.5 9.5A9.51 9.51 0 0 0 10 .5ZM9.5 4a1.5 1.5 0 1 1 0 3 1.5 1.5 0 0 1 0-3ZM12 15H8a1 1 0 0 1 0-2h1v-3H8a1 1 0 0 1 0-2h2a1 1 0 0 1 1 1v4h1a1 1 0 0 1 0 2Z"/>
          </svg>
          <span class="flex-1 ms-3 whitespace-nowrap">Denúncias</span>
          <span class="inline-flex items-center justify-center w-3 h-3 p-3 ms-3 text-sm font-medium text-red-800 bg-red-100 rounded-full">5</span>
        </a>
      </li>
      <li>
        <a href="#" class="flex items-center p-2 text-gray-900 rounded-lg hover:bg-gray-100 group">
          <svg class="shrink-0 w-5 h-5 text-gray-500 transition duration-75 group-hover:text-gray-900" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor">
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

<div class="p-4 sm:ml-64 min-h-screen">  <!-- Changed min-h-full to min-h-screen -->
  <section class="container px-4 mx-auto">
    <div class="flex items-center justify-between mb-6">  <!-- Changed div structure -->
      <div class="flex items-center gap-x-3">
        <h2 class="text-lg font-medium text-gray-800">Leis de Inclusão</h2>
        <span class="px-3 py-1 text-xs text-blue-600 bg-blue-100 rounded-full">${lawList.size()} leis</span>
      </div>
      <a href="/law/create/" class="flex items-center justify-center px-5 py-2 text-sm text-white transition-colors duration-200 bg-blue-500 rounded-lg gap-x-2 hover:bg-blue-600">
        <span class="material-symbols-outlined">add</span>
        <span>Criar Lei</span>
      </a>
    </div>

    <div class="flex flex-col mt-6">
      <div class="-mx-4 -my-2 overflow-x-auto sm:-mx-6 lg:-mx-8">
        <div class="inline-block min-w-full py-2 align-middle md:px-6 lg:px-8">
          <div class="overflow-hidden border border-gray-200 md:rounded-lg">
            <table class="min-w-full divide-y divide-gray-200">
              <thead class="bg-gray-50">
              <tr>
                <th scope="col" class="py-3.5 px-4 text-sm font-normal text-left rtl:text-right text-gray-500">
                  <div class="flex items-center gap-x-3">
                    <input type="checkbox" class="text-blue-500 border-gray-300 rounded">
                    <span>Código</span>
                  </div>
                </th>

                <th scope="col" class="px-12 py-3.5 text-sm font-normal text-left rtl:text-right text-gray-500">
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

                <th scope="col" class="px-4 py-3.5 text-sm font-normal text-left rtl:text-right text-gray-500">Descrição</th>

                <th scope="col" class="relative py-3.5 px-4">
                  <span class="sr-only">Ações</span>
                </th>
              </tr>
              </thead>
              <tbody class="bg-white divide-y divide-gray-200 ">
                <c:if test="${not empty lawList}">
                    <c:forEach items="${lawList}" var="law">
                      <tr>
                        <td class="px-4 py-4 text-sm font-medium text-gray-700 whitespace-nowrap">
                          <div class="inline-flex items-center gap-x-3">
                            <input type="checkbox" class="text-blue-500 border-gray-300 rounded">
                            <div>
                              <h2 class="font-medium text-gray-800">${law.code}</h2>
                            </div>
                          </div>
                        </td>
                        <td class="px-12 py-4 text-sm font-medium text-gray-700 whitespace-nowrap">
                            ${law.date.toString()}
                        </td>
                        <td class="px-4 py-4 text-sm text-gray-500 whitespace-nowrap">${law.title}</td>
                        <td class="px-4 py-4 text-sm text-gray-500">${law.description}</td>
                        <td class="px-4 py-4 text-sm whitespace-nowrap">
                          <div class="flex items-center gap-x-6">
                            <a href="/law/update/${law.code}" class="text-gray-500 transition-colors duration-200 hover:text-red-500 focus:outline-none">
                              <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-5 h-5">
                                <path stroke-linecap="round" stroke-linejoin="round" d="M16.862 4.487l1.687-1.688a1.875 1.875 0 112.652 2.652L10.582 16.07a4.5 4.5 0 01-1.897 1.13L6 18l.8-2.685a4.5 4.5 0 011.13-1.897l8.932-8.931zm0 0L19.5 7.125M18 14v4.75A2.25 2.25 0 0115.75 21H5.25A2.25 2.25 0 013 18.75V8.25A2.25 2.25 0 015.25 6H10" />
                              </svg>
                            </a>

                            <a href="/law/delete/${law.code}" class="text-gray-500 transition-colors duration-200 hover:text-yellow-500 focus:outline-none">
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

    <div class="flex items-center justify-between mt-6">
      <a href="#" class="flex items-center px-5 py-2 text-sm text-gray-700 capitalize transition-colors duration-200 bg-white border rounded-md gap-x-2 hover:bg-gray-100">
        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-5 h-5 rtl:-scale-x-100">
          <path stroke-linecap="round" stroke-linejoin="round" d="M6.75 15.75L3 12m0 0l3.75-3.75M3 12h18" />
        </svg>
        <span>Anterior</span>
      </a>

      <div class="items-center hidden lg:flex gap-x-3">
        <a href="#" class="px-2 py-1 text-sm text-blue-500 rounded-md bg-blue-100/60">1</a>
        <a href="#" class="px-2 py-1 text-sm text-gray-500 rounded-md hover:bg-gray-100">2</a>
        <a href="#" class="px-2 py-1 text-sm text-gray-500 rounded-md hover:bg-gray-100">3</a>
        <a href="#" class="px-2 py-1 text-sm text-gray-500 rounded-md hover:bg-gray-100">...</a>
        <a href="#" class="px-2 py-1 text-sm text-gray-500 rounded-md hover:bg-gray-100">8</a>
      </div>

      <a href="#" class="flex items-center px-5 py-2 text-sm text-gray-700 capitalize transition-colors duration-200 bg-white border rounded-md gap-x-2 hover:bg-gray-100">
        <span>Próximo</span>
        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-5 h-5 rtl:-scale-x-100">
          <path stroke-linecap="round" stroke-linejoin="round" d="M17.25 8.25L21 12m0 0l-3.75 3.75M21 12H3" />
        </svg>
      </a>
    </div>
  </section>
</div>

<script>
  // Adiciona evento para fechar o sidebar ao clicar no overlay
  document.getElementById('sidebar-overlay').addEventListener('click', function() {
    const sidebar = document.getElementById('default-sidebar');
    sidebar.classList.add('-translate-x-full');
    this.classList.remove('active');
  });

  // Adiciona evento para mostrar o overlay quando o sidebar é aberto
  document.querySelector('[data-drawer-toggle="default-sidebar"]').addEventListener('click', function() {
    const sidebar = document.getElementById('default-sidebar');
    const overlay = document.getElementById('sidebar-overlay');
  });
</script>
</body>
</html>