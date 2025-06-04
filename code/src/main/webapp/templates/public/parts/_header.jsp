<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<header id="page-header">
    <a href="${pageContext.request.contextPath}/">
        <img src="../../static/images/logo.png" alt="Logo" class="logo">
    </a>
    <button class="hamburger" id="hamburger-btn" aria-label="Abrir menu">
        ☰
    </button>

    <nav id="nav-menu">
        <ul>
            <li class="nav-item"><a href=".">Home</a></li>
            <li class="nav-item"><a href="${pageContext.request.contextPath}/public/report/">Denúncias</a></li>
            <li class="nav-item"><a href="${pageContext.request.contextPath}/public/scenario/">Cenários educativos</a></li>

            <c:if test="${ user == null}">
                <li class="nav-button"><a href="${pageContext.request.contextPath}/accounts/login/" class="btn-primary">Login</a></li>
            </c:if>

            <c:if test="${ user != null}">
                <li class="nav-button"><a href="${pageContext.request.contextPath}/report/index/" class="">Ir para dashboard</a></li>
            </c:if>
        </ul>
    </nav>

    <script defer>
        const urlPath = window.location.pathname;

        document.querySelectorAll("#nav-menu .nav-item").forEach(item => {
            let navItemPath = item.querySelector("a").getAttribute("href");

            if (navItemPath === urlPath || (navItemPath === '.' && urlPath === "" )) {
                item.classList.add("active");
            }
        });
    </script>
</header>