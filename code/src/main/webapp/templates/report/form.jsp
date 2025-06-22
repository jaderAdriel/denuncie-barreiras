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
            <h2 class="text-2xl  mb-8">Adicionar Denúncia</h2>
            <span class="px-3 py-1 text-xs text-blue-600 bg-blue-100 rounded-full">novo</span>
        </div>
    </div>

    <form action="${action}" method="post" class="bg-white p-8">

        <div class="flex gap-4 mb-4">
            <div class="w-full">
                <label for="environment" class="block text-md font-medium text-gray-900">Ambiente</label>
                <div class="mt-2">
                    <select name="environment" id="environment"
                            class="block  bg-none appearance-none border border-gray-300 w-full rounded-md bg-white px-5 py-3 text-base text-gray-900 outline-1 -outline-offset-1 outline-gray-300 placeholder:text-gray-400 focus:outline-2 focus:-outline-offset-2 focus:outline-indigo-600 sm:text-md">
                        <option value="">Selecione uma opção</option>
                        <c:if test="${not empty environmentOptions}">
                            <c:forEach items="${environmentOptions}" var="environmentOption">
                                <option value="${environmentOption.toString()}"
                                        <c:if test="${not empty environment and environment eq environmentOption}">selected</c:if>
                                >
                                        ${environmentOption.getTranslation()}
                                </option>
                            </c:forEach>
                        </c:if>
                    </select>

                    <c:if test="${not empty environmentError}"><p class="text-red-500 text-sm mt-2">${environmentError}</p></c:if>
                </div>
            </div>

            <div class="w-full">
                <label for="entityCnpjSelect" class="block text-md font-medium text-gray-900">Entidade denunciada</label>
                <div class="mt-2">
                    <select name="entity_cnpj" id="entityCnpjSelect"
                            class="block  bg-none appearance-none border border-gray-300 w-full rounded-md bg-white px-5 py-3 text-base text-gray-900 outline-1 -outline-offset-1 outline-gray-300 placeholder:text-gray-400 focus:outline-2 focus:-outline-offset-2 focus:outline-indigo-600 sm:text-md">
                        <option value="">Selecione uma opção</option>
                        <option value="none" <c:if test="${not empty newEntityCnpj or (not empty entity and entity.cnpj eq 'none')}">selected</c:if>>Nenhuma das opções</option>
                        <c:if test="${not empty entityOptions}">
                            <c:forEach items="${entityOptions}" var="entityOption">
                                <option value="${entityOption.cnpj}"
                                        <c:if test="${not empty entity and entity.cnpj eq entityOption.cnpj}">selected</c:if>
                                >
                                        ${entityOption.name} | ${entityOption.address}
                                </option>
                            </c:forEach>
                        </c:if>
                    </select>

                    <c:if test="${not empty entityError}"><p class="text-red-500 text-sm mt-2">${entityError}</p></c:if>
                </div>
            </div>

            <div class="w-full">
                <label for="barrierType" class="block text-md font-medium text-gray-900">Tipo de barreira</label>
                <div class="mt-2">
                    <select name="barrierType" id="barrierType"
                            class="block  bg-none appearance-none border border-gray-300 w-full rounded-md bg-white px-5 py-3 text-base text-gray-900 outline-1 -outline-offset-1 outline-gray-300 placeholder:text-gray-400 focus:outline-2 focus:-outline-offset-2 focus:outline-indigo-600 sm:text-md">
                        <option value="">Selecione uma opção</option>
                        <c:if test="${not empty barrierTypeOptions}">
                            <c:forEach items="${barrierTypeOptions}" var="typeOption">
                                <option value="${typeOption.toString()}"
                                        <c:if test="${not empty barrierType and barrierType eq typeOption}">selected</c:if>
                                >
                                        ${typeOption.getTranslation()}
                                </option>
                            </c:forEach>
                        </c:if>
                    </select>

                    <c:if test="${not empty barrierTypeError}"><p class="text-red-500 text-sm mt-2">${barrierTypeError}</p></c:if>
                </div>
            </div>

        </div>

        <%-- New fields for entity details if "Nenhuma das opções" is selected --%>
        <div id="newEntityFields" class="hidden mt-4 bg-gray-50 p-6 rounded-md shadow-inner">
            <h3 class="text-lg font-semibold mb-4 text-gray-800">Detalhes da Nova Entidade</h3>
            <p class="text-sm text-gray-600 mb-6">Preencha os campos abaixo com as informações da entidade que não foi encontrada na lista.</p>

            <div class="mb-4">
                <label for="new_entity_cnpj" class="block text-md font-medium text-gray-900">CNPJ</label>
                <div class="mt-2">
                    <input type="text" name="new_entity_cnpj" id="new_entity_cnpj" value="${not empty newEntityCnpj ? newEntityCnpj : ''}"
                           class="block w-full rounded-md border-0 py-3 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-md sm:leading-6"
                           placeholder="Ex: 00.000.000/0001-00">
                    <c:if test="${not empty newEntityCnpjError}"><p class="text-red-500 text-sm mt-2">${newEntityCnpjError}</p></c:if>
                </div>
            </div>
            <div class="mb-4">
                <label for="new_entity_name" class="block text-md font-medium text-gray-900">Nome da Entidade</label>
                <div class="mt-2">
                    <input type="text" name="new_entity_name" id="new_entity_name" value="${not empty newEntityName ? newEntityName : ''}"
                           class="block w-full rounded-md border-0 py-3 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-md sm:leading-6"
                           placeholder="Ex: Minha Empresa Ltda.">
                    <c:if test="${not empty newEntityNameError}"><p class="text-red-500 text-sm mt-2">${newEntityNameError}</p></c:if>
                </div>
            </div>
            <div class="mb-4">
                <label for="new_entity_address" class="block text-md font-medium text-gray-900">Endereço</label>
                <div class="mt-2">
                    <input type="text" name="new_entity_address" id="new_entity_address" value="${not empty newEntityAddress ? newEntityAddress : ''}"
                           class="block w-full rounded-md border-0 py-3 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-md sm:leading-6"
                           placeholder="Ex: Rua Exemplo, 123 - Centro, Cidade - UF">
                    <c:if test="${not empty newEntityAddressError}"><p class="text-red-500 text-sm mt-2">${newEntityAddressError}</p></c:if>
                </div>
            </div>
            <div class="mb-4">
                <label for="new_entity_phone" class="block text-md font-medium text-gray-900">Telefone</label>
                <div class="mt-2">
                    <input type="text" name="new_entity_phone" id="new_entity_phone" value="${not empty newEntityPhone ? newEntityPhone : ''}"
                           class="block w-full rounded-md border-0 py-3 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-md sm:leading-6"
                           placeholder="Ex: (XX) XXXX-XXXX">
                    <c:if test="${not empty newEntityPhoneError}"><p class="text-red-500 text-sm mt-2">${newEntityPhoneError}</p></c:if>
                </div>
            </div>
        </div>
        <%-- End new fields --%>


        <div class="mt-6">
            <label class="block text-md font-medium text-gray-900">Denúncia Anônima</label>
            <div class="mt-2 space-y-2">
                <div class="flex items-center">
                    <input id="anonymous" name="anonymous" type="checkbox" value="true"
                           class="h-4 w-4 border-gray-300 text-indigo-600 focus:ring-indigo-600"
                    <c:if test="${anonymous == 'true'}"> checked </c:if>
                    >
                    <label for="anonymous" class="ml-2 block text-sm text-gray-900">Marque para enviar denúncia anonimamente</label>
                </div>
            </div>
            <c:if test="${not empty anonymousError}">
                <div class="flex items-center gap-1 text-red-500 text-sm mt-2">
                    <svg xmlns="http://www.w3.org/2000/svg" height="20px" viewBox="0 -960 960 960" width="20px" fill="currentColor">
                        <path d="M480-290.77q13.73 0 23.02-9.29t9.29-23.02q0-13.73-9.29-23.02-9.29-9.28-23.02-9.28t-23.02 9.28q-9.29 9.29-9.29 23.02t9.29 23.02q9.29 9.29 23.02 9.29Zm-30-146.15h60v-240h-60v240ZM480.07-100q-78.84 0-148.21-29.92t-120.68-81.21q-51.31-51.29-81.25-120.63Q100-401.1 100-479.93q0-78.84 29.92-148.21t81.21-120.68q51.29-51.31 120.63-81.25Q401.1-860 479.93-860q78.84 0 148.21 29.92t120.68 81.21q51.31 51.29 81.25 120.63Q860-558.9 860-480.07q0 78.84-29.92 148.21t-81.21 120.68q-51.29 51.31-120.63 81.25Q558.9-100 480.07-100Zm-.07-60q134 0 227-93t93-227q0-134-93-227t-227-93q-134 0-227 93t-93 227q0 134 93 227t227 93Zm0-320Z"></path>
                    </svg>
                    <p>${anonymousError}</p>
                </div>
            </c:if>
        </div>

        <!-- Editor Markdown -->
        <div class="mt-4">
            <label for="incidentDetails" class="block mb-2 text-sm font-medium text-gray-900">Detalhamento do Ocorrido</label>
            <textarea
                    id="incidentDetails"
                    name="incidentDetails"
                    rows="4"
                    class="border border-gray-300 text-gray-900 text-sm rounded-lg block w-full p-2.5 resize-none
               <c:if test='${method == "DELETE"}'> bg-gray-200 cursor-not-allowed </c:if>
               <c:if test='${method != "DELETE"}'> bg-gray-50 </c:if>"
                    <c:if test='${method == "DELETE"}'> readonly disabled </c:if>
            >${not empty incidentDetails ? incidentDetails : ''}</textarea>

            <c:if test='${not empty incidentDetailsError}'>
                <div class="flex items-center gap-1 text-red-500 text-sm mt-2">
                    <svg xmlns="http://www.w3.org/2000/svg" height="20px" viewBox="0 -960 960 960" width="20px" fill="currentColor">
                        <path d="M480-290.77q13.73 0 23.02-9.29t9.29-23.02q0-13.73-9.29-23.02-9.29-9.28-23.02-9.28t-23.02 9.28q-9.29 9.29-9.29 23.02t9.29 23.02q9.29 9.29 23.02 9.29Zm-30-146.15h60v-240h-60v240ZM480.07-100q-78.84 0-148.21-29.92t-120.68-81.21q-51.31-51.29-81.25-120.63Q100-401.1 100-479.93q0-78.84 29.92-148.21t81.21-120.68q51.29-51.31 120.63-81.25Q401.1-860 479.93-860q78.84 0 148.21 29.92t120.68 81.21q51.31 51.29 81.25 120.63Q860-558.9 860-480.07q0 78.84-29.92 148.21t-81.21 120.68q-51.29 51.31-120.63 81.25Q558.9-100 480.07-100Zm-.07-60q134 0 227-93t93-227q0-134-93-227t-227-93q-134 0-227 93t-93 227q0 134 93 227t227 93Zm0-320Z"></path>
                    </svg>
                    <p>${incidentDetailsError}</p>
                </div>
            </c:if>
        </div>

        <br>
        <br>
        <button type="submit"
                class="w-full bg-blue-600 hover:bg-blue-700 text-white font-semibold py-3 px-4 rounded-md transition duration-200 text-md">
            Salvar
        </button>

    </form>
</section>

<%@ include file="../_footer.jsp"%>

<!-- Inicialização do SimpleMDE and New Entity Fields Logic -->
<script>
    document.addEventListener('DOMContentLoaded', function() {
        const entityCnpjSelect = document.getElementById('entityCnpjSelect');
        const newEntityFields = document.getElementById('newEntityFields');
        const newEntityCnpjInput = document.getElementById('new_entity_cnpj');
        const newEntityNameInput = document.getElementById('new_entity_name');
        const newEntityAddressInput = document.getElementById('new_entity_address');
        const newEntityPhoneInput = document.getElementById('new_entity_phone');

        function toggleNewEntityFields() {
            if (entityCnpjSelect.value === 'none') {
                newEntityFields.classList.remove('hidden');
            } else {
                newEntityFields.classList.add('hidden');
                // Optionally clear fields when hidden to prevent accidental submission of stale data
                newEntityCnpjInput.value = '';
                newEntityNameInput.value = '';
                newEntityAddressInput.value = '';
                newEntityPhoneInput.value = '';
            }
        }

        // Initial check on page load to set correct visibility
        toggleNewEntityFields();

        // Listen for changes on the entity selection
        entityCnpjSelect.addEventListener('change', toggleNewEntityFields);


        // SimpleMDE Initialization
        const simplemde = new SimpleMDE({
            element: document.getElementById("incidentDetails"), // Corrected ID to match textarea
            spellChecker: false,
            forceSync: true,
            placeholder: "Digite o detalhamento do ocorrido em Markdown...",
        });

        // This block ensures the hidden textarea gets updated.
        // With forceSync: true, SimpleMDE usually handles this automatically on form submit,
        // but explicit handling ensures the JS variable is always in sync if needed for other operations.
        simplemde.codemirror.on("change", function() {
            const markdownContent = simplemde.value();
            document.getElementById("incidentDetails").value = markdownContent;
            console.log("Current incident details content:", markdownContent);
        });

        // Set initial content if it exists (e.g., after a form submission with errors)
        const initialIncidentDetails = document.getElementById("incidentDetails").value;
        if (initialIncidentDetails && initialIncidentDetails.trim() !== "") {
            simplemde.value(initialIncidentDetails);
        }
    });
</script>

</body>
</html>