<%--
  Created by IntelliJ IDEA.
  User: jader
  Date: 6/5/25
  Time: 9:13 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<header class="main-header" id="header">
  <nav class="navbar navbar-expand-lg navbar-light" id="navbar">
    <!-- Sidebar toggle button -->
    <button id="sidebar-toggler" class="sidebar-toggle">
      <span class="sr-only">Toggle navigation</span>
    </button>

    <span class="page-title">editor</span>

    <div class="navbar-right ">

      <ul class="nav navbar-nav">
        <!-- User Account -->
        <li class="dropdown user-menu">
          <button class="dropdown-toggle nav-link" data-toggle="dropdown">
            <img src="https://thumbs.dreamstime.com/b/default-profile-picture-avatar-photo-placeholder-vector-illustration-default-profile-picture-avatar-photo-placeholder-vector-189495158.jpg" class="user-image rounded-circle" alt="User Image" />
            <span class="d-none d-lg-inline-block">${ user.name }</span>
          </button>
          <ul class="dropdown-menu dropdown-menu-right">
            <li class="dropdown-footer">
              <a class="dropdown-link-item" href="${pageContext.request.contextPath}/accounts/logout/"> <i class="mdi mdi-logout"></i> Sair </a>
            </li>
          </ul>
        </li>
      </ul>
    </div>
  </nav>


</header>