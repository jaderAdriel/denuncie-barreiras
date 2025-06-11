<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<%-- Incluir o cabeçalho base, garantindo que o Bootstrap CSS esteja carregado --%>
<jsp:include page="../__base_start.jsp">
  <jsp:param name="title" value="Adicionar Denúncia" />
</jsp:include>

<section class="p-4 md:p-6">
  <div class="flex flex-col md:flex-row items-start md:items-center justify-between mb-6 gap-4">
    <div class="flex items-center gap-x-3">
      <h2 class="text-2xl  mb-8">Adicionar Cenário Barreira</h2>
      <span class="px-3 py-1 text-xs text-blue-600 bg-blue-100 rounded-full">novo</span>
    </div>
  </div>

  <form action="${pageContext.request.contextPath}${action}" method="${method}" class="bg-white p-8" enctype="multipart/form-data">

    <div class="flex gap-4 mb-4">
      <div class="w-full">
        <label for="title" class="block text-md font-medium text-gray-900">Título</label>
        <div class="mt-2">
          <input class="block  border border-gray-300 w-full rounded-md bg-white px-5 py-3 text-base text-gray-900 outline-1 -outline-offset-1 outline-gray-300 placeholder:text-gray-400 focus:outline-2 focus:-outline-offset-2 focus:outline-indigo-600 sm:text-md"
                 type="text"
                 name="title"
                 id="title"
                 required
          <c:if test="${not empty title}"> value="${title}" </c:if>
          >
          <c:if test="${not empty titleError}"><p class="text-red-500 text-sm mt-2">${titleError}</p></c:if>
        </div>
      </div>



      <div class="w-full ">
        <label for="title" class="block text-md font-medium text-gray-900">Tipo de barreira</label>
        <div class="mt-2">
          <select name="barrierType"
                  class="block  bg-none appearance-none border border-gray-300 w-full rounded-md bg-white px-5 py-3 text-base text-gray-900 outline-1 -outline-offset-1 outline-gray-300 placeholder:text-gray-400 focus:outline-2 focus:-outline-offset-2 focus:outline-indigo-600 sm:text-md">
            <c:if test="${not empty barrierTypeOptions}">
              <c:forEach items="${barrierTypeOptions}" var="typeOption">
                <option value="${typeOption.toString()}"
                        <c:if test="${not empty barrierType}">
                          <c:if test="${barrierType eq typeOption}">selected</c:if>
                        </c:if>
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

    <div class="w-full mb-8">
      <label for="associatedLaws" class="block text-md font-medium text-gray-900">Leis associadas</label>
      <div class="mt-2 relative">
        <select name="associatedLaws" multiple
                class="block  bg-none appearance-none border border-gray-300 w-full rounded-md bg-white px-5 py-3 text-base text-gray-900 outline-1 -outline-offset-1 outline-gray-300 placeholder:text-gray-400 focus:outline-2 focus:-outline-offset-2 focus:outline-indigo-600 sm:text-md">
          <c:if test="${not empty lawOptions}">
            <c:forEach items="${lawOptions}" var="law">
              <option value="${law.code}"
                      <c:forEach items="${associatedLaws}" var="associated">
                        <c:if test="${associated.code eq law.code}">selected</c:if>
                      </c:forEach>
              >
                  ${law.code} - ${law.title}
              </option>
            </c:forEach>
          </c:if>
        </select>
        <c:if test="${not empty lawError}"><p class="text-red-500 text-sm mt-2">${lawError}</p></c:if>
      </div>
    </div>

    <div id="image-upload-section" class="w-full  flex flex-col items-center gap-6 p-4">
      <div class="dragzone relative w-full p-8 flex flex-col justify-center items-center rounded-lg outline-2 outline-dashed outline-gray-300 bg-white text-center transition-all duration-300 ease-in-out" id="dragzone">
        <div class="dragzone__icon">
          <i class="fas fa-images"></i>
        </div>

        <span class="dragzone__title">Drop file here</span>
        <span class="dragzone__title">Or <span class="btn dragzone__button">browse</span></span>
        <span class="dragzone__info">supports : JPEG, JPG, PNG & SVG</span>

        <input type="file" name="file" hidden accept="image/*" id="fileInput">
      </div>

      <div id="imagePreview" class="preview">
        <div class="button" id="closePreview">  </div>
        <div class="file w-full h-64 flex justify-center items-center overflow-hidden rounded-md bg-gray-200">
          <c:if test="${not empty image}" >
            <img src="${image}" alt="" class="preview-media w-full h-full object-contain">
          </c:if>
        </div>
      </div>
    </div>
    <!-- Editor Markdown -->
    <div class="mb-6">
      <label class="block text-sm font-medium text-gray-700 mb-1">Conteúdo</label>
      <textarea id="content" name="content">${not empty content ? content : ''}</textarea>
    </div>


    <button type="submit"
            class="w-full bg-blue-600 hover:bg-blue-700 text-white font-semibold py-3 px-4 rounded-md transition duration-200 text-md">
      Salvar
    </button>

  </form>
</section>


<script>
  $(document).ready(function() {
    $('select[name="associatedLaws"]').select2(),
            $('select[name="associatedLaws"]').select2('container').addClass('block  bg-none appearance-none border border-gray-300 w-full rounded-md bg-white px-5 py-3 text-base text-gray-900 outline-1 -outline-offset-1 outline-gray-300 placeholder:text-gray-400 focus:outline-2 focus:-outline-offset-2 focus:outline-indigo-600 sm:text-md');

  });
</script>
<!-- Inicialização do SimpleMDE -->
<script>
  document.addEventListener('DOMContentLoaded', function() {
    const simplemde = new SimpleMDE({
      element: document.getElementById("content"),
      spellChecker: false,
      forceSync: true,
      placeholder: "Digite o conteúdo em Markdown...",
    });

    simplemde.codemirror.on("change", function() {
      const markdownContent = simplemde.value();

      document.getElementById("content").value = markdownContent;

      console.log("Current content:", markdownContent);
    });

    const initialContent = document.getElementById("content").value;
    if (initialContent && initialContent.trim() !== "") {
      simplemde.value(initialContent);
    }
  });
</script>

<%@ include file="../__base_end.jsp"%>
