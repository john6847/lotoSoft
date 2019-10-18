<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta name="description" content="Creative - Bootstrap 3 Responsive Admin Template">
  <meta name="author" content="GeeksLabs">
  <meta name="keyword" content="Creative, Dashboard, Admin, Template, Theme, Bootstrap, Responsive, Retina, Minimal">
  <link rel="shortcut icon" href="/img/favicon.png">

  <title>Ouvri Sesyon</title>

  <!-- Bootstrap CSS -->
  <link href="/css/bootstrap.min.css" rel="stylesheet">
  <!-- bootstrap theme -->
  <link href="/css/bootstrap-theme.css" rel="stylesheet">
  <!--external css-->
  <!-- font icon -->
  <link href="/css/elegant-icons-style.css" rel="stylesheet" />
  <link href="/css/font-awesome.css" rel="stylesheet" />
  <!-- Custom styles -->
  <link href="/css/style.css" rel="stylesheet">
  <link href="/css/style-responsive.css" rel="stylesheet" />

  <!-- HTML5 shim and Respond.js IE8 support of HTML5 -->
  <!--[if lt IE 9]>
    <script src="/js/html5shiv.js"></script>
    <script src="/js/respond.min.js"></script>
    <![endif]-->

    <!-- =======================================================
      Theme Name: NiceAdmin
      Theme URL: https://bootstrapmade.com/nice-admin-bootstrap-admin-html-template/
      Author: BootstrapMade
      Author URL: https://bootstrapmade.com
    ======================================================= -->
</head>

<body class="login-img3-body">

  <div class="container">

    <form class="login-form" method="post" action="/login">
      <div class="login-wrap">
        <p class="login-img"><i class="icon_lock_alt"></i></p>
        <div class="input-group">
          <span class="input-group-addon"><i class="icon_building"></i></span>
          <input type="text" class="form-control" name="enterprise" id="enterprise" placeholder="Non Antrepriz la" required autofocus>
        </div>
        <div class="input-group">
          <span class="input-group-addon"><i class="icon_profile"></i></span>
          <input type="text" class="form-control" name="username" id="username" placeholder="Non Itilizatè" required>
        </div>
        <div class="input-group">
          <span class="input-group-addon"><i class="icon_key_alt"></i></span>
          <input type="password" class="form-control" name="password" id="password" placeholder="modpas" required>
        </div>
<#--        <label class="checkbox">-->
<#--            <input type="checkbox" value="remember-me"> Sonje mwen-->
<#--            &lt;#&ndash;<span class="pull-right"> <a href="#"> Forgot Password?</a></span>&ndash;&gt;-->
<#--        </label>-->
        <button class="btn btn-primary btn-lg btn-block" type="submit">Ouvri sesyon</button>
<#--        <button class="btn btn-info btn-lg btn-block" type="button">Enskri</button>-->
      </div>
    </form>
    <div class="row">
        <div class="col-xs-12">
        <#if error??>
            <p style="color: red">${error}</p>
        </#if>
        </div>
    </div>
    <div class="text-right">
      <div class="credits">
          <!--
            All the links in the footer should remain intact.
            You can delete the links only if you purchased the pro version.
            Licensing information: https://bootstrapmade.com/license/
            Purchase the pro version form: https://bootstrapmade.com/buy/?theme=NiceAdmin
          -->
          Designed by <a href="https://bootstrapmade.com/">BootstrapMade</a>
        </div>
    </div>
  </div>


</body>

</html>
