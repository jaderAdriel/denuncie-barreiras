<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:include page="../__base_start.jsp">
  <jsp:param name="title" value="Denúncias" />
</jsp:include>

<div class="card card-default p-0 shadow-sm">

  <div class="card-header">
                      <span>
                        <h1 class="m-0">Denúncias</h1>
                        <p class="">Gerenciamento de denúncias</p>
                      </span>
    <div class="card-header-action">
      <a href="${pageContext.request.contextPath}/entity/create/" class="btn btn-success py-1 px-3 mt-2" title="Adicionar nova denúncia"><i class="mdi mdi-plus mdi-18px"></i></a>
    </div>
  </div>

  <div class="card-body p-4">
    <table class="table table-sm table-hover data-table">
      <thead>
      <tr>
        <th>CNPJ</th>
        <th>Tipo</th>
        <th>Nome</th>
        <th>Ações</th>
      </tr>

      </thead>
      <tbody>
      <c:forEach items="${entities}" var="entity">
        <tr>
          <td>${entity.cnpj}</td>
          <td>${entity.type.getTranslation()}</td>
          <td>${entity.name}</td>
          <td>
            <a href="/entity/${entity.cnpj}/" class="text-muted">
              <span class="material-symbols-rounded fs-3">open_in_new</span>
            </a>
          </td>
        </tr>
      </c:forEach>

      </tbody>
    </table>
  </div>
</div>

<%@ include file="../__base_end.jsp"%>