
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset='utf-8'>
    <meta http-equiv='X-UA-Compatible' content='IE=edge'>
    <title>Denuncie Barreiras</title>
    <meta name='viewport' content='width=device-width, initial-scale=1'>
    <base href="<%= request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/" %>">
    <link rel='stylesheet' type='text/css' media='screen' href='/static/css/public/style.css'>
    <link rel='stylesheet' type='text/css' media='screen' href='/static/css/public/home.css'>
<%--    <script src='assets/index.js' defer></script>--%>
    <link href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined" rel="stylesheet">
    <link rel="shortcut icon" href="static/images/logo.png" type="image/x-icon">
    <style>
        .material-symbols-outlined {
          font-variation-settings: 'FILL' 0,'wght' 400,'GRAD' 0,'opsz' 24;
          font-family: 'Material Symbols Outlined';
          vertical-align: middle;
          margin-right: 0.5rem;
        }
      </style>
</head>
<body>
    <div class="wrapper">

        <%@ include file="parts/_header.jsp"%>
        
        <main>
            <section class="page-section section hero" id="hero-section" >
                
        
                <div class="hero-content">
                    <header class="hero-header">
                        <h1 class="hero-title">
                            Combata Barreiras Atitudinais.<br>Dê voz à Inclusão.
                        </h1>
                    </header>
                    <p class="hero-description">
                        Denuncie comportamentos discriminatórios, explore cenários educativos e engaje-se por uma sociedade mais justa.
                    </p>
                    <a href="report/create/" class="btn btn-primary">Faça uma denúncia</a>
                </div>


                <div class="hero-image">
                    <img src="static/images/man-breaking-wall.png" alt="Hero Image">
                </div>
        
            </section>
            <section class="section info">
                <header class="section-header">
                    <h2 class="section-title">Conscientize-se com cenários educativos</h2>
                </header>
                
                <div class="section-content">
                    <img src="static/images/woman-hands-up.png" alt="Mulher levantando as mãos">
                    <div class="text">
                        <p>
                            A conscientização é o primeiro passo para combater barreiras atitudinais. O sistema oferece uma galeria de Cenários Educativos, criados por moderadores capacitados, que representam situações comuns de discriminação em diversos contextos sociais.
                        </p>
                        <p>
                            Esses cenários ajudam os usuários a identificar atitudes discriminatórias — muitas vezes naturalizadas — e entender o impacto delas nas vidas das pessoas afetadas. Ao explorar esses exemplos, qualquer cidadão pode se tornar mais sensível às questões de inclusão e respeito à diversidade.
                        </p>
                    </div>
                </div>
            </section>

            <section class="section info">
                <header class="section-header">
                    <h2 class="section-title">Denuncie ocorrências reais com segurança</h2>
                </header>
                
                <div class="section-content">
                    <div class="text">
                        <p>
                            Quando uma pessoa vivencia ou presencia uma situação discriminatória, o sistema permite o registro formal da ocorrência por meio da funcionalidade de denúncia. O formulário é intuitivo e garante privacidade total ao denunciante, que pode optar por registrar o fato de forma anônima.
                        </p>
                        <p>
                            Além do relato detalhado, é possível anexar arquivos (como fotos, vídeos ou documentos) e, se desejar, relacionar a denúncia a um cenário educativo semelhante. Isso fortalece a base de dados do sistema e auxilia na identificação de padrões de discriminação.
                        </p>
                    </div>
                    <img src="static/images/megaphone.svg" alt="Mulher levantando as mãos">
                </div>
            </section>

            <section class="section info">
                <header class="section-header">
                    <h2 class="section-title">Engaje comentando e curtindo conteúdos</h2>
                </header>
                
                <div class="section-content">
                    <img src="static/images/people-talking.png" alt="Mulher levantando as mãos">
                    <div class="text">
                        <p>
                            A plataforma não é apenas um repositório de denúncias, mas também um espaço de diálogo e apoio mútuo. Usuários podem comentar nos cenários e nas postagens de outros membros, criando uma rede colaborativa de troca de experiências e soluções. 
                        </p>
                        <p>
                            Além disso, é possível curtir conteúdos relevantes, ajudando a destacar temas importantes. Essa interação estimula o debate construtivo e reforça o papel ativo da sociedade na superação de barreiras atitudinais.
                         </p>
                    </div>
                </div>
            </section>
        </main>
    </div>

    <%@ include file="parts/_footer.jsp" %>
</body>
</html>