<!DOCTYPE html>
<html lang="en"
      xmlns="http://www.w3.org/1999/xhtml"
      xmlns:th="http://www.thymeleaf.org"
      xmlns:sec="http://www.thymeleaf.org/thymeleaf-extras-springsecurity3">

<head>
    <#include "../header.ftl">
</head>

<body>
<!-- container section start -->
<section id="container" class="">
  <!--nav start-->
    <#include "../nav.ftl" >
  <!--header end-->

  <!--sidebar start-->
  <aside>
      <#include "../sidebar.ftl">
  </aside>
  <!--sidebar end-->

  <!--main content start-->
  <section id="main-content">
    <section class="wrapper">
      <div class="row">
        <div class="col-lg-12">
          <h3 class="page-header"><i class="fa fa-edit"></i> Fòm pou Aktyalize Antrepriz</h3>
          <ol class="breadcrumb">
            <li><i class="fa fa-home"></i><a href="/home">Paj Akèy</a></li>
            <li><i class="fa fa-edit"></i>Aktyalize Antrepriz</li>
          </ol>
        </div>
      </div>
        <#if error??>
          <div class="row">
            <div class="col-lg-12 col-xs-12 col-xl-12 col-md-12 col-sm-12">
              <div class="alert alert-danger" role="alert">${error}</div>
            </div>
          </div>
        </#if>
      <div class="row">
        <div class="col-lg-12">
          <section class="panel">
            <header class="panel-heading">
              Antrepriz
            </header>
            <div class="panel-body">
              <form class="form-horizontal form-validate" id="enterpriseForm" action="/enterprise/update"
                    th:object="${enterprise}"
                    method="post">
                <input type="hidden" name="id" id="id" value="${enterprise.id}">
                <div class="form-group">
                  <label class="col-lg-2 col-md-2 col-sm-2 control-label col-xs-12" for="enterprise">Antrepriz<span
                            class="required">*</span></label>
                  <div class="col-lg-10 col-md-10 col-sm-10 col-xs-12">
                    <input type="text" class="form-control round-input" id="enterprise"
                           name="enterprise" value="${(enterprise.name)!""}" required>
                  </div>
                </div>

                <div class="form-group">
                  <div class="col-lg-3 col-md-9 col-sm-12 col-xs-12 col-lg-offset-9 col-md-offset-3 col-xs-12">
                    <div class="row">
                      <div class="col-lg-6 col-md-6 col-sm-12 col-xs-12">
                        <button class="btn btn-danger form-control"
                                type="reset"
                                title="Efase tout done pos sa">
                          <i class="fa fa-times"></i>
                          Reyajiste
                        </button>
                      </div>
                      <div class="col-lg-6 col-md-6 col-sm-12 col-xs-12">
                        <button class="btn btn-primary form-control"
                                type="submit"
                                title="Anrejistre tout done pos sa">
                          <i class="fa fa-save"></i>
                          Anrejistre
                        </button>
                      </div>
                    </div>
                  </div>
                </div>
              </form>
            </div>
          </section>
        </div>
    </section>
    <!--main content end-->
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
  </section>
    <#include "../loader.ftl">

    <#include "../scripts.ftl">

  <script type="text/javascript">
      $("#enterpriseForm").on("submit", function () {
          $("#custom-loader").fadeIn();
      });//sumbmit
  </script>

</body>

</html>