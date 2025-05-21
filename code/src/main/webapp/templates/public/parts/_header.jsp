<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<header id="page-header">
    <img src="../../static/images/logo.png" alt="Logo" class="logo">

    <button class="hamburger" id="hamburger-btn" aria-label="Abrir menu">
        ☰
    </button>

    <nav id="nav-menu">
        <ul>
            <li class="nav-item"><a href=".">Home</a></li>
            <li class="nav-item"><a href="#">Denúncias</a></li>
            <li class="nav-item"><a href="/scenario/">Cenários educativos</a></li>
            <li class="nav-button"><a href="${pageContext.request.contextPath}/accounts/login/" class="btn-primary">Login</a></li>
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