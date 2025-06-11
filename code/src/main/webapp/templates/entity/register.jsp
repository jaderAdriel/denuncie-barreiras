<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Cadastro de Entidade</title>
    <base href="<%= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/" %>">
    <script src="https://cdn.tailwindcss.com"></script>
    <style>
        @import url('https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap');
        body {
            font-family: 'Inter', sans-serif;
        }
    </style>
</head>
<body class="min-h-screen bg-gray-50">

<div class="flex flex-col lg:flex-row h-screen w-full">

    <!-- Formulário -->
    <div class="w-full lg:w-1/2 h-full flex items-center justify-center bg-white">
        <form action="${pageContext.request.contextPath}" method="POST" autocomplete="off" class="w-full max-w-3xl px-8 py-10 space-y-6">
            <h2 class="text-4xl font-bold text-blue-600 mb-16">Cadastro de Entidade</h2>

            <!-- Nome -->
            <div>
                <label for="name" class="block text-md font-medium text-gray-900">Nome</label>
                <div class="mt-2">
                    <input
                            type="text"
                            name="name"
                            id="name"
                            required
                            autocomplete="new-name"
                    <c:if test="${not empty name}"> value="${name}" </c:if>
                            class="block border border-gray-300 w-full rounded-md bg-white px-5 py-3 text-base text-gray-900 outline-1 -outline-offset-1 outline-gray-300 placeholder:text-gray-400 focus:outline-2 focus:-outline-offset-2 focus:outline-indigo-600 sm:text-md"
                    >
                    <c:if test="${not empty nameError}">
                        <div class="flex items-center gap-1 text-red-500 text-sm mt-2">
                            <svg xmlns="http://www.w3.org/2000/svg" height="20px" viewBox="0 -960 960 960" width="20px" fill="currentColor">
                                <path d="M480-290.77q13.73 0 23.02-9.29t9.29-23.02q0-13.73-9.29-23.02-9.29-9.28-23.02-9.28t-23.02 9.28q-9.29 9.29-9.29 23.02t9.29 23.02q9.29 9.29 23.02 9.29Zm-30-146.15h60v-240h-60v240ZM480.07-100q-78.84 0-148.21-29.92t-120.68-81.21q-51.31-51.29-81.25-120.63Q100-401.1 100-479.93q0-78.84 29.92-148.21t81.21-120.68q51.29-51.31 120.63-81.25Q401.1-860 479.93-860q78.84 0 148.21 29.92t120.68 81.21q51.31 51.29 81.25 120.63Q860-558.9 860-480.07q0 78.84-29.92 148.21t-81.21 120.68q-51.29 51.31-120.63 81.25Q558.9-100 480.07-100Zm-.07-60q134 0 227-93t93-227q0-134-93-227t-227-93q-134 0-227 93t-93 227q0 134 93 227t227 93Zm0-320Z"></path>
                            </svg>
                            <p>${nameError}</p>
                        </div>
                    </c:if>
                </div>
            </div>

            <!-- CNPJ -->
            <div>
                <label for="cnpj" class="block text-md font-medium text-gray-900">CNPJ</label>
                <div class="mt-2">
                    <input
                            type="text"
                            name="cnpj"
                            id="cnpj"
                            required
                            placeholder="00.000.000/0000-00"
                    <c:if test="${not empty cnpj}"> value="${cnpj}" </c:if>
                            class="block border border-gray-300 w-full rounded-md bg-white px-5 py-3 text-base text-gray-900 outline-1 -outline-offset-1 outline-gray-300 placeholder:text-gray-400 focus:outline-2 focus:-outline-offset-2 focus:outline-indigo-600 sm:text-md"
                    >
                    <c:if test="${not empty cnpjError}">
                        <div class="flex items-center gap-1 text-red-500 text-sm mt-2">
                            <svg xmlns="http://www.w3.org/2000/svg" height="20px" viewBox="0 -960 960 960" width="20px" fill="currentColor">
                                <path d="M480-290.77q13.73 0 23.02-9.29t9.29-23.02q0-13.73-9.29-23.02-9.29-9.28-23.02-9.28t-23.02 9.28q-9.29 9.29-9.29 23.02t9.29 23.02q9.29 9.29 23.02 9.29Zm-30-146.15h60v-240h-60v240ZM480.07-100q-78.84 0-148.21-29.92t-120.68-81.21q-51.31-51.29-81.25-120.63Q100-401.1 100-479.93q0-78.84 29.92-148.21t81.21-120.68q51.29-51.31 120.63-81.25Q401.1-860 479.93-860q78.84 0 148.21 29.92t120.68 81.21q51.31 51.29 81.25 120.63Q860-558.9 860-480.07q0 78.84-29.92 148.21t-81.21 120.68q-51.29 51.31-120.63 81.25Q558.9-100 480.07-100Zm-.07-60q134 0 227-93t93-227q0-134-93-227t-227-93q-134 0-227 93t-93 227q0 134 93 227t227 93Zm0-320Z"></path>
                            </svg>
                            <p>${cnpjError}</p>
                        </div>
                    </c:if>
                </div>
            </div>

            <!-- Tipo (Instituição de Ensino ou Empresa) -->
            <div>
                <label class="block text-md font-medium text-gray-900">Tipo</label>
                <div class="mt-2 space-y-2">
                    <div class="flex items-center">
                        <input id="instituicao" name="type" type="radio" value="INSTITUICAO_ENSINO"
                               class="h-4 w-4 border-gray-300 text-indigo-600 focus:ring-indigo-600"
                        <c:if test="${not empty type and type == 'INSTITUICAO_ENSINO'}"> checked </c:if>
                        >
                        <label for="instituicao" class="ml-3 block text-md font-medium text-gray-700">Instituição de Ensino</label>
                    </div>
                    <div class="flex items-center">
                        <input id="empresa" name="type" type="radio" value="EMPRESA"
                               class="h-4 w-4 border-gray-300 text-indigo-600 focus:ring-indigo-600"
                        <c:if test="${not empty type and type == 'EMPRESA'}"> checked </c:if>
                        >
                        <label for="empresa" class="ml-3 block text-md font-medium text-gray-700">Empresa</label>
                    </div>
                </div>
                <c:if test="${not empty typeError}">
                    <div class="flex items-center gap-1 text-red-500 text-sm mt-2">
                        <svg xmlns="http://www.w3.org/2000/svg" height="20px" viewBox="0 -960 960 960" width="20px" fill="currentColor">
                            <path d="M480-290.77q13.73 0 23.02-9.29t9.29-23.02q0-13.73-9.29-23.02-9.29-9.28-23.02-9.28t-23.02 9.28q-9.29 9.29-9.29 23.02t9.29 23.02q9.29 9.29 23.02 9.29Zm-30-146.15h60v-240h-60v240ZM480.07-100q-78.84 0-148.21-29.92t-120.68-81.21q-51.31-51.29-81.25-120.63Q100-401.1 100-479.93q0-78.84 29.92-148.21t81.21-120.68q51.29-51.31 120.63-81.25Q401.1-860 479.93-860q78.84 0 148.21 29.92t120.68 81.21q51.31 51.29 81.25 120.63Q860-558.9 860-480.07q0 78.84-29.92 148.21t-81.21 120.68q-51.29 51.31-120.63 81.25Q558.9-100 480.07-100Zm-.07-60q134 0 227-93t93-227q0-134-93-227t-227-93q-134 0-227 93t-93 227q0 134 93 227t227 93Zm0-320Z"></path>
                        </svg>
                        <p>${typeError}</p>
                    </div>
                </c:if>
            </div>

            <!-- Endereço -->
            <div>
                <label for="address" class="block text-md font-medium text-gray-900">Endereço</label>
                <div class="mt-2">
                    <input
                            type="text"
                            name="address"
                            id="address"
                            required
                    <c:if test="${not empty address}"> value="${address}" </c:if>
                            class="block border border-gray-300 w-full rounded-md bg-white px-5 py-3 text-base text-gray-900 outline-1 -outline-offset-1 outline-gray-300 placeholder:text-gray-400 focus:outline-2 focus:-outline-offset-2 focus:outline-indigo-600 sm:text-md"
                    >
                    <c:if test="${not empty addressError}">
                        <div class="flex items-center gap-1 text-red-500 text-sm mt-2">
                            <svg xmlns="http://www.w3.org/2000/svg" height="20px" viewBox="0 -960 960 960" width="20px" fill="currentColor">
                                <path d="M480-290.77q13.73 0 23.02-9.29t9.29-23.02q0-13.73-9.29-23.02-9.29-9.28-23.02-9.28t-23.02 9.28q-9.29 9.29-9.29 23.02t9.29 23.02q9.29 9.29 23.02 9.29Zm-30-146.15h60v-240h-60v240ZM480.07-100q-78.84 0-148.21-29.92t-120.68-81.21q-51.31-51.29-81.25-120.63Q100-401.1 100-479.93q0-78.84 29.92-148.21t81.21-120.68q51.29-51.31 120.63-81.25Q401.1-860 479.93-860q78.84 0 148.21 29.92t120.68 81.21q51.31 51.29 81.25 120.63Q860-558.9 860-480.07q0 78.84-29.92 148.21t-81.21 120.68q-51.29 51.31-120.63 81.25Q558.9-100 480.07-100Zm-.07-60q134 0 227-93t93-227q0-134-93-227t-227-93q-134 0-227 93t-93 227q0 134 93 227t227 93Zm0-320Z"></path>
                            </svg>
                            <p>${addressError}</p>
                        </div>
                    </c:if>
                </div>
            </div>

            <!-- Telefone -->
            <div>
                <label for="phone" class="block text-md font-medium text-gray-900">Telefone</label>
                <div class="mt-2">
                    <input
                            type="tel"
                            name="phone"
                            id="phone"
                            required
                            placeholder="(00) 00000-0000"
                    <c:if test="${not empty phone}"> value="${phone}" </c:if>
                            class="block border border-gray-300 w-full rounded-md bg-white px-5 py-3 text-base text-gray-900 outline-1 -outline-offset-1 outline-gray-300 placeholder:text-gray-400 focus:outline-2 focus:-outline-offset-2 focus:outline-indigo-600 sm:text-md"
                    >
                    <c:if test="${not empty phoneError}">
                        <div class="flex items-center gap-1 text-red-500 text-sm mt-2">
                            <svg xmlns="http://www.w3.org/2000/svg" height="20px" viewBox="0 -960 960 960" width="20px" fill="currentColor">
                                <path d="M480-290.77q13.73 0 23.02-9.29t9.29-23.02q0-13.73-9.29-23.02-9.29-9.28-23.02-9.28t-23.02 9.28q-9.29 9.29-9.29 23.02t9.29 23.02q9.29 9.29 23.02 9.29Zm-30-146.15h60v-240h-60v240ZM480.07-100q-78.84 0-148.21-29.92t-120.68-81.21q-51.31-51.29-81.25-120.63Q100-401.1 100-479.93q0-78.84 29.92-148.21t81.21-120.68q51.29-51.31 120.63-81.25Q401.1-860 479.93-860q78.84 0 148.21 29.92t120.68 81.21q51.31 51.29 81.25 120.63Q860-558.9 860-480.07q0 78.84-29.92 148.21t-81.21 120.68q-51.29 51.31-120.63 81.25Q558.9-100 480.07-100Zm-.07-60q134 0 227-93t93-227q0-134-93-227t-227-93q-134 0-227 93t-93 227q0 134 93 227t227 93Zm0-320Z"></path>
                            </svg>
                            <p>${phoneError}</p>
                        </div>
                    </c:if>
                </div>
            </div>

            <button type="submit"
                    class="w-full bg-blue-600 hover:bg-blue-700 text-white font-semibold py-3 px-4 rounded-md transition duration-200 text-md">
                Cadastrar
            </button>
        </form>
    </div>

    <div class="hidden lg:flex lg:w-1/2 items-center justify-center bg-green-50">
        <img src="static/images/report.svg" alt="Imagem de fundo"
             class="object-cover w-full h-auto" />
    </div>
</div>
</body>
</html>