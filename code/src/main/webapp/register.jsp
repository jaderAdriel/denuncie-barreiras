<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Cadastre-se</title>
  <script src="https://cdn.jsdelivr.net/npm/@tailwindcss/browser@4"></script>
  <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@24,400,0,0&icon_names=error" />
</head>
<body>
    <div class="flex min-h-full flex-col justify-center px-6 py-12 lg:px-8">
        <div class="sm:mx-auto sm:w-full sm:max-w-sm">
            <img class="mx-auto h-10 w-auto" src="https://tailwindcss.com/plus-assets/img/logos/mark.svg?color=indigo&shade=600" alt="Your Company">
            <h2 class="mt-10 text-center text-2xl font-bold tracking-tight text-gray-900">Crie sua conta</h2>
        </div>

        <div class="mt-10 sm:mx-auto sm:w-full sm:max-w-lg">
            <form class="space-y-6" action="." method="POST" autocomplete="off">
                <div>
                    <label for="name" class="block text-sm font-medium text-gray-900">Nome completo</label>
                    <div class="mt-2">
                        <input
                            type="text"
                            name="name"
                            id="name"
                            required
                            autocomplete="new-name"
                            <c:if test="${not empty name}"> value="${name}" </c:if>
                            class="block w-full rounded-md bg-white px-5 py-3 text-base text-gray-900 outline-1 -outline-offset-1 outline-gray-300 placeholder:text-gray-400 focus:outline-2 focus:-outline-offset-2 focus:outline-indigo-600 sm:text-sm">

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

                <div>
                    <label for="email" class="block text-sm font-medium text-gray-900">Email</label>
                    <div class="mt-2">
                        <input
                            type="email"
                            name="email"
                            id="email"
                            required
                            autocomplete="new-email"
                            <c:if test="${not empty email}"> value="${email}" </c:if>
                            class="block w-full rounded-md bg-white px-5 py-3 text-base text-gray-900 outline-1 -outline-offset-1 outline-gray-300 placeholder:text-gray-400 focus:outline-2 focus:-outline-offset-2 focus:outline-indigo-600 sm:text-sm">
                        <c:if test="${not empty emailError}">
                            <div class="flex items-center gap-1 text-red-500 text-sm mt-2">
                                <svg xmlns="http://www.w3.org/2000/svg" height="20px" viewBox="0 -960 960 960" width="20px" fill="currentColor">
                                    <path d="M480-290.77q13.73 0 23.02-9.29t9.29-23.02q0-13.73-9.29-23.02-9.29-9.28-23.02-9.28t-23.02 9.28q-9.29 9.29-9.29 23.02t9.29 23.02q9.29 9.29 23.02 9.29Zm-30-146.15h60v-240h-60v240ZM480.07-100q-78.84 0-148.21-29.92t-120.68-81.21q-51.31-51.29-81.25-120.63Q100-401.1 100-479.93q0-78.84 29.92-148.21t81.21-120.68q51.29-51.31 120.63-81.25Q401.1-860 479.93-860q78.84 0 148.21 29.92t120.68 81.21q51.31 51.29 81.25 120.63Q860-558.9 860-480.07q0 78.84-29.92 148.21t-81.21 120.68q-51.29 51.31-120.63 81.25Q558.9-100 480.07-100Zm-.07-60q134 0 227-93t93-227q0-134-93-227t-227-93q-134 0-227 93t-93 227q0 134 93 227t227 93Zm0-320Z"></path>
                                </svg>
                                <p>${emailError}</p>
                            </div>
                        </c:if>
                    </div>
                </div>

                <div>
                    <label for="birthDate" class="block text-sm font-medium text-gray-900">Data de nascimento</label>
                    <div class="mt-2">
                        <input
                            type="date"
                            name="birthDate"
                            id="birthDate"
                            required
                            autocomplete="new-birthDate"
                            <c:if test="${not empty birthDate}"> value="${birthDate}" </c:if>
                            class="block w-full rounded-md bg-white px-5 py-3 text-base text-gray-900 outline-1 -outline-offset-1 outline-gray-300 placeholder:text-gray-400 focus:outline-2 focus:-outline-offset-2 focus:outline-indigo-600 sm:text-sm"
                        >
                        <c:if test="${not empty birthDateError}">
                            <div class="flex items-center gap-1 text-red-500 text-sm mt-2">
                                <svg xmlns="http://www.w3.org/2000/svg" height="20px" viewBox="0 -960 960 960" width="20px" fill="currentColor">
                                    <path d="M480-290.77q13.73 0 23.02-9.29t9.29-23.02q0-13.73-9.29-23.02-9.29-9.28-23.02-9.28t-23.02 9.28q-9.29 9.29-9.29 23.02t9.29 23.02q9.29 9.29 23.02 9.29Zm-30-146.15h60v-240h-60v240ZM480.07-100q-78.84 0-148.21-29.92t-120.68-81.21q-51.31-51.29-81.25-120.63Q100-401.1 100-479.93q0-78.84 29.92-148.21t81.21-120.68q51.29-51.31 120.63-81.25Q401.1-860 479.93-860q78.84 0 148.21 29.92t120.68 81.21q51.31 51.29 81.25 120.63Q860-558.9 860-480.07q0 78.84-29.92 148.21t-81.21 120.68q-51.29 51.31-120.63 81.25Q558.9-100 480.07-100Zm-.07-60q134 0 227-93t93-227q0-134-93-227t-227-93q-134 0-227 93t-93 227q0 134 93 227t227 93Zm0-320Z"></path>
                                </svg>
                                <p>${birthDateError}</p>
                            </div>
                        </c:if>
                    </div>

                </div>

                <div>
                    <label class="block text-sm font-medium text-gray-900">Gênero</label>
                    <div class="mt-2 space-y-2">
                        <div class="flex items-center">
                            <input id="male" name="gender" type="radio" value="MALE"
                                class="h-4 w-4 border-gray-300 text-indigo-600 focus:ring-indigo-600"
                                <c:if test="${not empty gender and gender == 'MALE'}"> checked </c:if>
                            >
                            <label for="male" class="ml-3 block text-sm font-medium text-gray-700">Masculino</label>
                        </div>
                        <div class="flex items-center">
                            <input id="female" name="gender" type="radio" value="FEMALE"
                                class="h-4 w-4 border-gray-300 text-indigo-600 focus:ring-indigo-600"
                                <c:if test="${not empty gender and gender == 'FEMALE'}"> checked </c:if>
                            >
                            <label for="female" class="ml-3 block text-sm font-medium text-gray-700">Feminino</label>
                        </div>
                        <div class="flex items-center">
                            <input id="other" name="gender" type="radio" value="OTHER"
                                class="h-4 w-4 border-gray-300 text-indigo-600 focus:ring-indigo-600"
                                <c:if test="${not empty gender and gender == 'MALE'}"> checked </c:if>
                            >
                            <label for="other" class="ml-3 block text-sm font-medium text-gray-700">Outro</label>
                        </div>
                    </div>
                    <c:if test="${not empty genderError}">
                        <div class="flex items-center gap-1 text-red-500 text-sm mt-2">
                            <svg xmlns="http://www.w3.org/2000/svg" height="20px" viewBox="0 -960 960 960" width="20px" fill="currentColor">
                                <path d="M480-290.77q13.73 0 23.02-9.29t9.29-23.02q0-13.73-9.29-23.02-9.29-9.28-23.02-9.28t-23.02 9.28q-9.29 9.29-9.29 23.02t9.29 23.02q9.29 9.29 23.02 9.29Zm-30-146.15h60v-240h-60v240ZM480.07-100q-78.84 0-148.21-29.92t-120.68-81.21q-51.31-51.29-81.25-120.63Q100-401.1 100-479.93q0-78.84 29.92-148.21t81.21-120.68q51.29-51.31 120.63-81.25Q401.1-860 479.93-860q78.84 0 148.21 29.92t120.68 81.21q51.31 51.29 81.25 120.63Q860-558.9 860-480.07q0 78.84-29.92 148.21t-81.21 120.68q-51.29 51.31-120.63 81.25Q558.9-100 480.07-100Zm-.07-60q134 0 227-93t93-227q0-134-93-227t-227-93q-134 0-227 93t-93 227q0 134 93 227t227 93Zm0-320Z"></path>
                            </svg>
                            <p>${genderError}</p>
                        </div>
                    </c:if>
                </div>

                <div class="flex gap-4">
                    <div class="w-full">
                        <label for="password" class="block text-sm font-medium text-gray-900">Senha</label>
                        <div class="mt-2">
                            <input
                                type="password"
                                name="password"
                                id="password"
                                required
                                class="block w-full rounded-md bg-white px-5 py-3 text-base text-gray-900 outline-1 -outline-offset-1 outline-gray-300 placeholder:text-gray-400 focus:outline-2 focus:-outline-offset-2 focus:outline-indigo-600 sm:text-sm"
                            >
                            <c:if test="${not empty passwordError}">
                                <div class="flex items-center gap-1 text-red-500 text-sm mt-2">
                                    <svg xmlns="http://www.w3.org/2000/svg" height="20px" viewBox="0 -960 960 960" width="20px" fill="currentColor">
                                        <path d="M480-290.77q13.73 0 23.02-9.29t9.29-23.02q0-13.73-9.29-23.02-9.29-9.28-23.02-9.28t-23.02 9.28q-9.29 9.29-9.29 23.02t9.29 23.02q9.29 9.29 23.02 9.29Zm-30-146.15h60v-240h-60v240ZM480.07-100q-78.84 0-148.21-29.92t-120.68-81.21q-51.31-51.29-81.25-120.63Q100-401.1 100-479.93q0-78.84 29.92-148.21t81.21-120.68q51.29-51.31 120.63-81.25Q401.1-860 479.93-860q78.84 0 148.21 29.92t120.68 81.21q51.31 51.29 81.25 120.63Q860-558.9 860-480.07q0 78.84-29.92 148.21t-81.21 120.68q-51.29 51.31-120.63 81.25Q558.9-100 480.07-100Zm-.07-60q134 0 227-93t93-227q0-134-93-227t-227-93q-134 0-227 93t-93 227q0 134 93 227t227 93Zm0-320Z"></path>
                                    </svg>
                                    <p>${passwordError}</p>
                                </div>
                            </c:if>
                        </div>
                    </div>
                    <div class="w-full">
                        <label for="password2" class="block text-sm font-medium text-gray-900">Confirme a senha</label>
                        <div class="mt-2">
                            <input
                                type="password"
                                name="password2"
                                id="password"
                                required
                                class="block w-full rounded-md bg-white px-5 py-3 text-base text-gray-900 outline-1 -outline-offset-1 outline-gray-300 placeholder:text-gray-400 focus:outline-2 focus:-outline-offset-2 focus:outline-indigo-600 sm:text-sm"
                            >
                            <c:if test="${not empty password2Error}">
                                <div class="flex items-center gap-1 text-red-500 text-sm mt-2">
                                    <svg xmlns="http://www.w3.org/2000/svg" height="20px" viewBox="0 -960 960 960" width="20px" fill="currentColor">
                                        <path d="M480-290.77q13.73 0 23.02-9.29t9.29-23.02q0-13.73-9.29-23.02-9.29-9.28-23.02-9.28t-23.02 9.28q-9.29 9.29-9.29 23.02t9.29 23.02q9.29 9.29 23.02 9.29Zm-30-146.15h60v-240h-60v240ZM480.07-100q-78.84 0-148.21-29.92t-120.68-81.21q-51.31-51.29-81.25-120.63Q100-401.1 100-479.93q0-78.84 29.92-148.21t81.21-120.68q51.29-51.31 120.63-81.25Q401.1-860 479.93-860q78.84 0 148.21 29.92t120.68 81.21q51.31 51.29 81.25 120.63Q860-558.9 860-480.07q0 78.84-29.92 148.21t-81.21 120.68q-51.29 51.31-120.63 81.25Q558.9-100 480.07-100Zm-.07-60q134 0 227-93t93-227q0-134-93-227t-227-93q-134 0-227 93t-93 227q0 134 93 227t227 93Zm0-320Z"></path>
                                    </svg>
                                    <p>${password2Error}</p>
                                </div>
                            </c:if>
                        </div>
                    </div>
                </div>

                <div>
                    <button type="submit" class="flex w-full justify-center rounded-md bg-indigo-600 px-5 py-3 text-sm font-semibold text-white shadow-xs hover:bg-indigo-500 focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600">
                        Cadastrar
                    </button>
                </div>
            </form>

            <p class="mt-10 text-center text-sm text-gray-500">
                Já tem uma conta?
                <a href="/accounts/login/" class="font-semibold text-indigo-600 hover:text-indigo-500">Faça login</a>
            </p>
        </div>
    </div>
</body>
</html>