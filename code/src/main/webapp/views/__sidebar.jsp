<%--
  Created by IntelliJ IDEA.
  User: jader
  Date: 6/5/25
  Time: 9:13 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<aside class="left-sidebar  sidebar-light]"  id="left-sidebar">
  <div id="sidebar" class="sidebar sidebar-with-footer  bg-white text-dark">
    <!-- Aplication Brand -->
    <div class="app-brand">
      <a href="${pageContext.request.contextPath}/">
        <img src="${pageContext.request.contextPath}/theme/images/logo.png" alt="Mono">
        <span class="brand-name text-dark">Denuncie</span> <span class="text-primary"></span>
      </a>
    </div>
    <!-- begin sidebar scrollbar -->
    <div class="sidebar-left" data-simplebar style="height: 100%;">
      <!-- sidebar menu -->
      <ul class="nav sidebar-inner text-dark" id="sidebar-menu">
        <li>
          <a href="${pageContext.request.contextPath}/report/index/" class="sidenav-item-link text-dark">
            <span class="material-symbols-outlined">breaking_news</span>
            <span class="ml-4">Denúncias</span>
          </a>
        </li>
        <li>
          <a href="${pageContext.request.contextPath}/scenario/index/" class="sidenav-item-link text-dark">
            <span class="material-symbols-outlined">menu_book</span>
            <span class="ml-4">Cenários barreiras</span>
          </a>
        </li>
        <li>
          <a href="${pageContext.request.contextPath}/law/index/" class="sidenav-item-link text-dark">
            <span class="material-symbols-outlined">gavel</span>
            <span class="ml-4">Leis</span>
          </a>
        </li>

<%--        <li>--%>
<%--          <a href="${pageContext.request.contextPath}/entity/index/" class="sidenav-item-link text-dark">--%>
<%--            <span class="material-symbols-outlined">group</span>--%>
<%--            <span class="ml-4">Usuários</span>--%>
<%--          </a>--%>
<%--        </li>--%>

        <li>
          <a href="${pageContext.request.contextPath}/entity/index/" class="sidenav-item-link text-dark">
            <span class="material-symbols-outlined">source_environment</span>
            <span class="ml-4">Entidades</span>
          </a>
        </li>

      </ul>

    </div>

    <div class="sidebar-footer">
      <div class="sidebar-footer-content">
        <ul class="d-flex">
          <li>
            <a href="user-account-settings.html" data-toggle="tooltip" title="Profile settings"><i class="mdi mdi-settings"></i></a></li>
          <li>
            <a href="#" data-toggle="tooltip" title="No chat messages"><i class="mdi mdi-chat-processing"></i></a>
          </li>
        </ul>
      </div>
    </div>
  </div>
</aside>
