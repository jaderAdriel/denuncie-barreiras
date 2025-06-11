<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width, initial-scale=1" />

<!-- GOOGLE FONTS -->
<link href="https://fonts.googleapis.com/css?family=Karla:400,700|Roboto" rel="stylesheet">
<link href="${pageContext.request.contextPath}/theme/plugins/material/css/materialdesignicons.min.css" rel="stylesheet" />
<link href="${pageContext.request.contextPath}/theme/plugins/simplebar/simplebar.css" rel="stylesheet" />
<link href="${pageContext.request.contextPath}/theme/plugins/DataTables/DataTables-1.10.18/css/jquery.dataTables.min.css" rel="stylesheet"/>
<!-- PLUGINS CSS STYLE -->
<link href="${pageContext.request.contextPath}/theme/plugins/nprogress/nprogress.css" rel="stylesheet" />
<link href="${pageContext.request.contextPath}/theme/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" />
<link href="https://fonts.googleapis.com/css2?family=Material+Symbols+Rounded" rel="stylesheet">

<link href="${pageContext.request.contextPath}/theme/plugins/prism/prism.css" rel="stylesheet" />
<link href="${pageContext.request.contextPath}/theme/css/global.css" rel="stylesheet" />

<link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined" />


<link href="https://cdn.quilljs.com/1.3.6/quill.snow.css" rel="stylesheet">

<link rel="stylesheet" href="https://cdn.jsdelivr.net/simplemde/latest/simplemde.min.css">
<script src="https://cdn.jsdelivr.net/simplemde/latest/simplemde.min.js"></script>

<link rel='stylesheet' type='text/css' media='screen' href="/static/css/editor.css">

<link rel="stylesheet" href="https://cdn.jsdelivr.net/simplemde/latest/simplemde.min.css">
<script src="https://cdn.jsdelivr.net/simplemde/latest/simplemde.min.js"></script>
<script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
<link href="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/css/select2.min.css" rel="stylesheet" />
<script src="https://cdn.jsdelivr.net/npm/select2@4.1.0-rc.0/dist/js/select2.min.js"></script>


<link rel='stylesheet' type='text/css' media='screen' href="${pageContext.request.contextPath}/static/css/editor.css">
<link rel='stylesheet' type='text/css' media='screen' href="${pageContext.request.contextPath}/static/css/image-dragzone.css">
<script src="${pageContext.request.contextPath}/static/js/image-dragzone.js" defer></script>

<!-- MONO CSS -->
<link id="main-css-href" rel="stylesheet" href="${pageContext.request.contextPath}/theme/css/style.css" />

<![endif]-->
<script src="${pageContext.request.contextPath}/theme/plugins/nprogress/nprogress.js"></script>

<style>
    .CodeMirror {
        min-height: 500px;
        max-height: 600px; /* ou qualquer valor desejado */
        overflow-y: auto;
    }

    .CodeMirror-fullscreen.CodeMirror {
        max-height: none;
    }

    .CodeMirror-fullscreen .CodeMirror-scroll {
        max-height: none;
    }

    .material-symbols-rounded {
        font-variation-settings:
                'FILL' 0,
                'wght' 200,
                'GRAD' 0,
                'opsz' 24
    }
</style>
