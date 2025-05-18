
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Login</title>
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
        <form action="${pageContext.request.contextPath}" method="POST" autocomplete="off" class="w-full max-w-2xl px-8 py-10 space-y-6">
            <h2 class="text-4xl font-bold text-blue-600 mb-16">Entrar</h2>

            <div>
                <label for="email" class="block text-md font-medium text-gray-900">Email</label>
                <div class="mt-2">
                    <input
                            type="email"
                            name="email"
                            id="email"
                            required
                    <c:if test="${not empty email}"> value="${email}" </c:if>
                            class="block border border-gray-300  w-full rounded-md bg-white px-5 py-3 text-md text-gray-900 outline-1 -outline-offset-1 outline-gray-300 placeholder:text-gray-400 focus:outline-2 focus:-outline-offset-2 focus:outline-indigo-600">
                    <c:if test="${not empty emailError}">
                        <div class="flex items-center gap-1 text-red-500 text-sm mt-2">
                            <svg xmlns="http://www.w3.org/2000/svg" height="24px" viewBox="0 -960 960 960" width="24px" fill="currentColor">
                                <path d="M480-290.77q13.73 0 23.02-9.29t9.29-23.02q0-13.73-9.29-23.02-9.29-9.28-23.02-9.28t-23.02 9.28q-9.29 9.29-9.29 23.02t9.29 23.02q9.29 9.29 23.02 9.29Zm-30-146.15h60v-240h-60v240ZM480.07-100q-78.84 0-148.21-29.92t-120.68-81.21q-51.31-51.29-81.25-120.63Q100-401.1 100-479.93q0-78.84 29.92-148.21t81.21-120.68q51.29-51.31 120.63-81.25Q401.1-860 479.93-860q78.84 0 148.21 29.92t120.68 81.21q51.31 51.29 81.25 120.63Q860-558.9 860-480.07q0 78.84-29.92 148.21t-81.21 120.68q-51.29 51.31-120.63 81.25Q558.9-100 480.07-100Zm-.07-60q134 0 227-93t93-227q0-134-93-227t-227-93q-134 0-227 93t-93 227q0 134 93 227t227 93Zm0-320Z"></path>
                            </svg>
                            <p>${emailError}</p>
                        </div>
                    </c:if>
                </div>
            </div>

            <div>
                <div class="flex items-center justify-between">
                    <label for="password" class="block text-md font-medium text-gray-900">Senha</label>
                    <a href="#" class="text-sm text-blue-600 hover:underline">Esqueceu a senha?</a>
                </div>
                <div class="mt-2">
                    <input
                            type="password"
                            name="password"
                            id="password"
                            required
                            class="block w-full border border-gray-300  rounded-md bg-white px-5 py-3 text-md text-gray-900 outline-1 -outline-offset-1 outline-gray-300 placeholder:text-gray-400 focus:outline-2 focus:-outline-offset-2 focus:outline-indigo-600">
                    <c:if test="${not empty passwordError}">
                        <div class="flex items-center gap-1 text-red-500 text-sm mt-2">
                            <svg xmlns="http://www.w3.org/2000/svg" height="24px" viewBox="0 -960 960 960" width="24px" fill="currentColor">
                                <path d="M480-290.77q13.73 0 23.02-9.29t9.29-23.02q0-13.73-9.29-23.02-9.29-9.28-23.02-9.28t-23.02 9.28q-9.29 9.29-9.29 23.02t9.29 23.02q9.29 9.29 23.02 9.29Zm-30-146.15h60v-240h-60v240ZM480.07-100q-78.84 0-148.21-29.92t-120.68-81.21q-51.31-51.29-81.25-120.63Q100-401.1 100-479.93q0-78.84 29.92-148.21t81.21-120.68q51.29-51.31 120.63-81.25Q401.1-860 479.93-860q78.84 0 148.21 29.92t120.68 81.21q51.31 51.29 81.25 120.63Q860-558.9 860-480.07q0 78.84-29.92 148.21t-81.21 120.68q-51.29 51.31-120.63 81.25Q558.9-100 480.07-100Zm-.07-60q134 0 227-93t93-227q0-134-93-227t-227-93q-134 0-227 93t-93 227q0 134 93 227t227 93Zm0-320Z"></path>
                            </svg>
                            <p>${passwordError}</p>
                        </div>
                    </c:if>
                </div>
            </div>

            <c:if test="${not empty error}">
                <div class="flex items-center gap-1 text-red-500 text-md mt-2">
                    <svg xmlns="http://www.w3.org/2000/svg" height="24px" viewBox="0 -960 960 960" width="24px" fill="currentColor">
                        <path d="M480-290.77q13.73 0 23.02-9.29t9.29-23.02q0-13.73-9.29-23.02-9.29-9.28-23.02-9.28t-23.02 9.28q-9.29 9.29-9.29 23.02t9.29 23.02q9.29 9.29 23.02 9.29Zm-30-146.15h60v-240h-60v240ZM480.07-100q-78.84 0-148.21-29.92t-120.68-81.21q-51.31-51.29-81.25-120.63Q100-401.1 100-479.93q0-78.84 29.92-148.21t81.21-120.68q51.29-51.31 120.63-81.25Q401.1-860 479.93-860q78.84 0 148.21 29.92t120.68 81.21q51.31 51.29 81.25 120.63Q860-558.9 860-480.07q0 78.84-29.92 148.21t-81.21 120.68q-51.29 51.31-120.63 81.25Q558.9-100 480.07-100Zm-.07-60q134 0 227-93t93-227q0-134-93-227t-227-93q-134 0-227 93t-93 227q0 134 93 227t227 93Zm0-320Z"></path>
                    </svg>
                    <p>${error}</p>
                </div>
            </c:if>

            <div class="flex items-center justify-between">
                <label class="flex items-center text-md">
                    <input type="checkbox" class="form-checkbox h-5 w-5 text-blue-600" />
                    <span class="ml-2 text-gray-600">Lembrar-me</span>
                </label>
            </div>

            <button type="submit"
                    class="w-full bg-blue-600 hover:bg-blue-700 text-white font-semibold py-3 px-4 rounded-md transition duration-200 text-sm">
                Entrar
            </button>


            <p class="text-center text-md text-gray-600">
                Não tem uma conta?
                <a href="/accounts/register/" class="text-blue-600 hover:underline font-medium">Cadastre-se</a>
            </p>
        </form>
    </div>

    <!-- Imagem com tamanho natural -->
    <div class="hidden lg:flex lg:w-1/2 items-center justify-center bg-green-50">
        <img src="static/images/megaphone.svg" alt="Imagem de fundo"
             class="object-contain w-auto h-auto max-h-[55rem]" />
    </div>
</div>

</body>
</html>