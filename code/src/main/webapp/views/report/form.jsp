<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:include page="../__base_start.jsp">
    <jsp:param name="title" value="Denúncias" />
</jsp:include>

<div class="card card-default p-0 shadow-sm">

    <div class="card-header">
                          <span>
                            <h2 class="m-0">Denúncias</h2>
                            <p class="">Gerenciamento de denúncias</p>
                          </span>
        <div class="card-header-action">
            <a href="${pageContext.request.contextPath}/report/create/" class="btn btn-success py-1 px-3 mt-2" title="Adicionar nova denúncia"><i class="mdi mdi-plus mdi-18px"></i></a>
        </div>
    </div>

    <div class="card-body p-2">
        <table id="table" class="table table-sm table-hover data-table">
            <thead>
            <tr>
                <th>Código</th>
                <th>Tipo</th>
                <th>Ambientes</th>
                <th>Ações</th>
            </tr>

            </thead>
            <tbody>
            <c:forEach items="${reports}" var="report">
                <tr>
                    <td>${report.id}</td>
                    <td>${report.type.getTranslation()}</td>
                    <td>${report.ambient.getTranslation()}</td>
                    <td class="text-right align-middle">
                        <a href="/report/${report.id}/" class="btn btn-sm btn-info py-1 px-3"><i class="mdi mdi-eye"></i></a>
                        <a href="/report/update/${report.id}/" class="btn btn-sm btn-secondary py-1 px-3"><i class="mdi mdi-pen"></i></a>
                        <a href="/report/delete/${report.id}/" class="btn btn-sm btn-danger py-1 px-3"><i class="mdi mdi-trash-can-outline"></i></a>
                    </td>
                </tr>
            </c:forEach>

            </tbody>
        </table>
    </div>
</div>


<%@ include file="../__base_end.jsp"%>
