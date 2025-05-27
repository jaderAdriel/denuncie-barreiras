<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset='utf-8'>
    <meta http-equiv='X-UA-Compatible' content='IE=edge'>
    <title>Denuncie Barreiras - Denúncias</title>
    <meta name='viewport' content='width=device-width, initial-scale=1'>
    <base href="<%= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/" %>">
    <script src="https://cdn.tailwindcss.com"></script>
    <link href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined" rel="stylesheet">
    <link rel="shortcut icon" href="static/images/logo.png" type="image/x-icon">
    <link rel='stylesheet' type='text/css' media='screen' href='/static/css/public/style.css'>
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

    <section class="bg-white">
        <div class="py-8 px-4 mx-auto max-w-screen-xl sm:py-16 lg:px-6">
            <div class="mx-auto max-w-screen-sm text-center">
                <h2 class="mb-4 text-4xl tracking-tight font-extrabold leading-tight text-gray-900">Denúncias</h2>
                <p class="mb-6 font-light text-gray-600 md:text-lg">
                    Sua voz pode quebrar preconceitos e transformar vidas!
                </p>
            </div>
        </div>
    </section>


    <main class="flex-grow container mx-auto px-4 py-10 flex flex-col md:flex-row gap-8">

        <!-- Sidebar filters -->
        <aside class="w-full md:w-64 flex-shrink-0">
            <div class="bg-white p-5 rounded-xl shadow-sm border border-gray-100">
                <h2 class="text-lg font-semibold mb-4 text-gray-800">Filtrar</h2>

                <!-- Filter by Barrier Type -->
                <div class="mb-5">
                    <h3 class="text-sm font-medium mb-2 text-gray-600">Tipo de Barreira</h3>
                    <ul class="space-y-2">
                        <c:forEach items="${barrierTypeOptions}" var="type">
                            <li>
                                <label class="flex items-center text-sm text-gray-700">
                                    <input type="checkbox" class="rounded text-blue-600 mr-2">
                                    <span>${type.getTranslation()}</span>
                                </label>
                            </li>
                        </c:forEach>
                    </ul>
                </div>

                <!-- Filter by Law -->
                <div>
                    <h3 class="text-sm font-medium mb-2 text-gray-600">Cenário Relacionado</h3>
                    <ul class="space-y-2">
                        <c:forEach items="${scenarios}" var="scenario">
                            <li>
                                <label class="flex items-center text-sm text-gray-700">
                                    <input type="checkbox" class="rounded text-blue-600 mr-2">
                                    <span>${scenario.title}</span>
                                </label>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
            </div>

            <div class="mt-5">
                <a href="/denuncia/nova" class="block w-full bg-blue-600 hover:bg-blue-700 text-white text-center font-medium py-1 px-2 rounded-lg transition">
                    <span class="material-symbols-outlined mr-2 align-middle">filter</span>
                    Filtrar
                </a>
            </div>

        </aside>

        <!-- Main content -->
        <div class="flex-grow">

            <!-- Search bar -->
            <div class="bg-white p-4 rounded-xl shadow-sm border border-gray-100 mb-6">
                <form class="flex">
                    <input type="text" placeholder="Buscar denúncias..." class="flex-grow px-4 py-2 text-sm border border-gray-300 rounded-l-md focus:outline-none focus:ring-1 focus:ring-blue-500">
                    <button type="submit" class="bg-blue-600 hover:bg-blue-700 text-white px-4 py-2 rounded-r-md transition">
                        <span class="material-symbols-outlined">search</span>
                    </button>
                </form>
            </div>

            <!-- Posts list -->
            <div class="space-y-6">
                <c:forEach items="${reportList}" var="report">
                    <article class="bg-white p-5 rounded-xl shadow-sm border border-gray-100 hover:shadow-md transition">
                        <div class="flex justify-between items-start">
                            <h3 class="text-lg font-semibold text-gray-800 mb-2">
                                ${report.ambient.getTranslation()}
                            </h3>
                            <span class="bg-blue-50 text-blue-700 text-xs font-medium px-2.5 py-0.5 rounded">${report.type.getTranslation()}</span>
                        </div>
                        <div class="flex justify-between items-start">
                            <p>${report.eventDetailing}</p>
                        </div>
                    </article>
                </c:forEach>
            </div>

            <!-- Pagination -->
            <div class="mt-10 flex justify-center">
                <nav class="inline-flex rounded-lg shadow-sm overflow-hidden border border-gray-200">
                    <a href="#" class="px-3 py-2 text-sm text-gray-500 bg-white hover:bg-gray-50">
                        <span class="material-symbols-outlined">chevron_left</span>
                    </a>
                    <a href="#" class="px-3 py-2 text-sm text-gray-700 bg-white hover:bg-gray-50">1</a>
                    <a href="#" class="px-3 py-2 text-sm text-gray-700 bg-white hover:bg-gray-50">2</a>
                    <a href="#" class="px-3 py-2 text-sm text-gray-700 bg-white hover:bg-gray-50">3</a>
                    <a href="#" class="px-3 py-2 text-sm text-gray-500 bg-white hover:bg-gray-50">
                        <span class="material-symbols-outlined">chevron_right</span>
                    </a>
                </nav>
            </div>

        </div>
    </main>


</div>
<%@ include file="parts/_footer.jsp" %>
</body>
</html>