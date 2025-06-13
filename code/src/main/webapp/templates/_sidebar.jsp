<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div>
    <div class="sidebar-wrapper">
        <aside class="sidebar">
            <div class="header">
                <a href="${pageContext.request.contextPath}/">
                    <div class="logo">
                        <img src="static/images/logo.png" alt="">
                        Denuncie
                    </div>
                </a>
                <button class="menu-button" aria-label="Abrir menu de navegação">
                    <span class="material-symbols-outlined icon" aria-hidden="true">menu</span>
                </button>
            </div>
            <ul>
                <li>
                    <a href="/report/index/" class="nav-item">
                        <span class="material-symbols-outlined">breaking_news</span>
                        <span>Denúncias</span>
                    </a>
                </li>
                <li>
                    <a href="/scenario/index/" class="nav-item">
                        <span class="material-symbols-outlined">menu_book</span>
                        <span>Cenários barreiras</span>
                    </a>
                </li>
                <li>
                    <a href="/law/index/" class="nav-item">
                        <span class="material-symbols-outlined">gavel</span>
                        <span>Leis</span>
                    </a>
                </li>

                <li>
                    <a href="${pageContext.request.contextPath}/entity/index/" class="nav-item">
                        <span class="material-symbols-outlined">source_environment</span>
                        <span class="ml-4">Entidades</span>
                    </a>
                </li>
            </ul>
        </aside>
    </div>

<script>
    document.querySelectorAll('.nav-item').forEach((e) => {
        const pathname = window.location.pathname.split('/');

        const navitempath = e.getAttribute("href").split('/');

        if (navitempath[1] === pathname[1]) {
            e.classList.add('active')
        }

    });
</script>