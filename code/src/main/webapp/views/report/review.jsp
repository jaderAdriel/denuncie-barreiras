<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="../__base_start.jsp">
    <jsp:param name="title" value="Review Denúncia" />
</jsp:include>

<section class="p-4 w-100">
    <div class="d-flex flex-column flex-md-row align-items-start align-items-md-center justify-content-between mb-4 gap-3">
        <div class="d-flex align-items-center gap-2">
            <h2 class="h4 fw-semibold text-dark mb-0">Revisar Denúncia</h2>
            <span class="badge bg-primary text-light">#<c:out value="${report.id}"/></span>
        </div>
        <div class="text-muted small">
            Criada em: ${report.creationDate.toString()}
        </div>
    </div>

    <form action="${action}" method="${method}" class="bg-white rounded shadow-sm border p-4">
        <%-- Report Information Section --%>
        <div class="mb-4 border-bottom pb-4">
            <h5 class="mb-3">Detalhes da Denúncia</h5>

            <div class="row mb-3">
                <div class="col-md-6 mb-3 mb-md-0">
                    <p class="fw-semibold mb-1">Ambiente</p>
                    <div class="form-control-plaintext border rounded p-2 bg-light">
                        <c:out value="${report.ambient.getTranslation()}"/>
                    </div>
                </div>

                <div class="col-md-6">
                    <p class="fw-semibold mb-1">Tipo de Barreira</p>
                    <div class="form-control-plaintext border rounded p-2 bg-light">
                        <c:out value="${report.type.getTranslation()}"/>
                    </div>
                </div>
            </div>

            <div class="row mb-3">
                <div class="col-md-6 mb-3 mb-md-0">
                    <p class="fw-semibold mb-1">Denunciante</p>
                    <div class="form-control-plaintext border rounded p-2 bg-light">
                        <c:choose>
                            <c:when test="${report.anonymousReport == true}">Anônimo</c:when>
                            <c:otherwise><c:out value="${report.reporter.name}"/></c:otherwise>
                        </c:choose>
                    </div>
                </div>

                <div class="col-md-6">
                    <p class="fw-semibold mb-1">Entidade</p>
                    <div class="form-control-plaintext border rounded p-2 bg-light">
                        <c:choose>
                            <c:when test="${not empty report.entity}">
                                ${report.entity.type.getTranslation()} ${report.entity.name} - ${report.entity.cnpj}
                            </c:when>
                            <c:otherwise>Não informado</c:otherwise> <%-- Corrected original issue: removed report.reporter.name here --%>
                        </c:choose>
                    </div>
                </div>
            </div>

            <div>
                <p class="fw-semibold mb-1">Detalhamento do Ocorrido</p>
                <div class="form-control-plaintext border rounded p-2 bg-light min-vh-20">
                    ${not empty report.eventDetailing ? report.eventDetailing : 'Nenhum detalhamento fornecido.'}
                </div>
            </div>
        </div>

        <%-- Review Section --%>
        <div>
            <h5 class="mb-3">Revisão do Moderador</h5>

            <c:choose>
                <%-- Caso já tenha revisão --%>
                <c:when test="${not empty review}">
                    <div class="alert alert-primary d-flex align-items-start gap-2 mb-4">
                        <i class="bi bi-info-circle-fill fs-5"></i>
                        <div>
                            <p class="mb-1 fw-semibold">Esta denúncia já foi revisada.</p>
                            <p class="mb-0 small">
                                Revisado por: <strong>${review.author.name}</strong> em
                                <strong>${review.getCreatAtFormated()}</strong>
                            </p>
                        </div>
                    </div>

                    <div class="row mb-4">
                        <div class="col-md-6">
                            <p class="fw-semibold mb-1">Resultado da Análise</p>
                            <div class="form-control-plaintext border rounded p-2 bg-light">
                                <c:choose>
                                    <c:when test="${review.isValid == true}">Válida</c:when>
                                    <c:otherwise>Inválida</c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </div>

                    <div class="mb-4">
                        <p class="fw-semibold mb-1">Comentário do Moderador</p>
                        <div class="form-control-plaintext border rounded p-3 bg-light" style="min-height: 120px;">
                            <c:out value="${not empty review.comment ? review.comment : 'Nenhum comentário foi adicionado.'}"/>
                        </div>
                    </div>

                    <div class="text-end">
                        <a href="/report/index/" class="btn btn-outline-secondary">Voltar para a lista</a>
                    </div>
                </c:when>

                <%-- Caso ainda não tenha revisão --%>
                <c:otherwise>
                    <div class="mb-4">
                        <p class="fw-semibold mb-1">Status da Denúncia <span class="text-danger">*</span></p>
                        <div class="form-check">
                            <input class="form-check-input" type="radio" name="isValid" id="isValidTrue" value="true" required
                            <c:if test="${isValid == true}"> checked </c:if>>
                            <label class="form-check-label" for="isValidTrue">Válida</label>
                        </div>
                        <div class="form-check">
                            <input class="form-check-input" type="radio" name="isValid" id="isValidFalse" value="false" required
                            <c:if test="${isValid == false}"> checked </c:if>>
                            <label class="form-check-label" for="isValidFalse">Inválida</label>
                        </div>
                        <c:if test="${not empty isValidError}">
                            <div class="text-danger small mt-1">${isValidError}</div>
                        </c:if>
                    </div>

                    <div class="mb-4">
                        <label for="comment" class="form-label">Comentário do Moderador <span class="text-danger">*</span></label>
                        <textarea id="comment" name="comment" rows="4" required
                                  class="form-control"
                                  placeholder="Forneça detalhes sobre sua análise...">${comment}</textarea>
                        <c:if test="${not empty commentError}">
                            <div class="text-danger small mt-1">${commentError}</div>
                        </c:if>
                    </div>

                    <div class="text-end">
                        <a href="/report/index/" class="btn btn-outline-secondary me-2">Cancelar</a>
                        <button type="submit" class="btn btn-primary">Salvar Revisão</button>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </form>
</section>

<%@ include file="../__base_end.jsp"%>