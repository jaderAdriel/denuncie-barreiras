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
            <a href="${pageContext.request.contextPath}/report/create/" class="btn btn-success py-1 px-3 mt-2" title="Adicionar nova denúncia"><i class="mdi mdi-plus mdi-18px"></i></a>
        </div>
    </div>

    <div class="card-body p-4">
        <table class="table table-sm table-hover data-table">
            <thead>
            <tr>
                <th>Código</th>
                <th>Tipo</th>
                <th>Descrição</th>
                <th>Ambientes</th>
                <th>Status</th>
                <th>Ações</th>
            </tr>

            </thead>
            <tbody>
            <c:forEach items="${reportList}" var="report">
                <tr>
                    <td>${report.id}</td>
                    <td>${report.type.getTranslation()}</td>
                    <td>
                        <p class="text-truncate" style="max-width: 10rem; white-space: nowrap;overflow: hidden;text-overflow: ellipsis;">
                                ${report.eventDetailing}
                        </p>

                    </td>
                    <td>${report.ambient.getTranslation()}</td>
                    <td class="px-4 text-sm text-nowrap">
                        <c:if test="${report.reviewStatus.toUpperCase() == 'NÃO VÁLIDO'}">
                            <span class="badge text-danger fw-medium px-2 py-1">
                                    ${report.reviewStatus}
                            </span>
                        </c:if>

                        <c:if test="${report.reviewStatus.toUpperCase() == 'VÁLIDO'}">
                            <span class="badge text-success fw-medium px-2 py-1">
                                    ${report.reviewStatus}
                            </span>
                        </c:if>

                        <c:if test="${report.reviewStatus.toUpperCase() == 'PENDENTE'}">
                            <span class="badge text-black fw-medium px-2 py-1">
                                    ${report.reviewStatus}
                            </span>
                        </c:if>
                    </td>

                    <td class="text-end align-middle">
                        <c:if test="${user.role.toString() eq 'MODERATOR'}">
                            <a href="/report/review/${report.id}" class="mr-3 text-success" title="Review de denúncia">
                                <span class="material-symbols-rounded fs-5">data_loss_prevention</span>
                            </a>

                            <a href="/report/review/delete/${report.id}" class="mr-3 text-danger">
                                <span class="material-symbols-rounded fs-3">delete</span>
                            </a>
                        </c:if>

                        <a href="/public/report/${report.id}/" class="text-muted">
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