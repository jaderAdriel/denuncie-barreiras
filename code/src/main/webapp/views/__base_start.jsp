<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>

<html lang="en" dir="ltr">
<head>
    <%@ include file="__head.jsp"%>
    <title><%= request.getParameter("title") %></title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/simplemde/latest/simplemde.min.css">
    <script src="https://cdn.jsdelivr.net/simplemde/latest/simplemde.min.js"></script>
    <script src="https://cdn.tailwindcss.com"></script>
</head>


<body class="navbar-fixed sidebar-fixed" id="body">
<script>
    NProgress.configure({ showSpinner: false });
    NProgress.start();
</script>

<!-- ====================================
——— WRAPPER
===================================== -->
<div class="wrapper">
    <!-- ====================================
      ——— LEFT SIDEBAR WITH OUT FOOTER
    ===================================== -->
    <%@ include file="__sidebar.jsp"%>



    <!-- ====================================
    ——— PAGE WRAPPER
    ===================================== -->
    <div class="page-wrapper">

        <!-- Header -->
        <%@ include file="__topbar.jsp"%>

        <!-- ====================================
        ——— CONTENT WRAPPER
        ===================================== -->
        <div class="content-wrapper">
            <main class="m-0 p-3">
                <c:if test="not empty ">
                    <div class="alert alert-success alert-icon" role="alert">
                        <i class="mdi mdi-checkbox-marked-outline"></i> A simple success alert—check it out!
                        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                            <span aria-hidden="true">×</span>
                        </button>
                    </div>
                </c:if>

