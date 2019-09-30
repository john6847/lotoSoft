<!DOCTYPE html>
<html lang="en"
      xmlns="http://www.w3.org/1999/xhtml"
      xmlns:th="http://www.thymeleaf.org"
      xmlns:sec="http://www.thymeleaf.org/thymeleaf-extras-springsecurity3">

<head>
    <#include "../header.ftl">
</head>

<body ng-app="lottery" ng-cloak>
<!-- container section start -->
<section id="container" class="" ng-controller="userController">
  <!--nav start-->
    <#include "../nav.ftl" >
  <!--header end-->

  <!--sidebar start-->
  <aside>
      <#include "../sidebar.ftl">
  </aside>
  <!--sidebar end-->

  <!--main content start-->
  <section id="main-content" ng-init="init(false)">
    <section class="wrapper">
      <div class="row">
        <div class="col-lg-12">
          <h3 class="page-header"><i class="fa fa-edit"></i>Fòm Pou Modifye Modpas</h3>
          <ol class="breadcrumb">
            <li><i class="fa fa-home"></i><a href="/home">Paj Akèy</a></li>
            <li><i class="fa fa-edit">Modifye Modpas</i></li>
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
              Chanje modpas
            </header>
            <div class="panel-body">
              <form name="userForm" id="userForm" class="form-horizontal form-validate" action="/user/password/reset"
                   th:object="${passwordResetViewModel}" method="post">
                  <input type="hidden" name="type" value="${type ! ""}">
                  <div class="form-group">
                      <label class="col-lg-2 col-md-2 col-sm-2 control-label col-xs-12" for="user">Itilizatè</label>
                      <div class="col-lg-10 col-md-10 col-sm-10 col-xs-12">
                          <select class="form-control round-input"
                                  name="users"
                                  data-live-search="true"
                                  data-size="5"
                                  data-none-selected-text="Chwazi Itilizatè a"
                                  data-none-results-text="Itilizatè sa pa egziste"
                                  data-placeholder="Chwazi Itilizatè a"
                                  id="user"
                                  required>
                              <#if users??>
                                  <#list users as u >
                                      <option value="${u.id}">${u.name}</option>
                                  </#list>
                              </#if>
                          </select>
                      </div>
                  </div>

                <div class="form-group">
                  <label class="col-lg-2 col-md-2 col-sm-2 control-label col-xs-12" for="password">Modpas<span
                            class="required">*</span></label>
                  <div class="col-lg-10   col-md-10 col-sm-10 col-xs-12">
                    <input type="password" class="form-control round-input" id="password" placeholder="******"
                           name="password" maxlength="20" minlength="8" required>
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-lg-2 col-md-2 col-sm-2 control-label col-xs-12" for="confirmPassword">Konfime Modpas<span
                            class="required">*</span></label>
                  <div class="col-lg-10   col-md-10 col-sm-10 col-xs-12">
                    <input type="password" class="form-control round-input" id="confirmPassword" placeholder="******"
                           name="confirmPassword" maxlength="20" minlength="8" required>
                  </div>
                </div>

                <div class="form-group">
                  <div class="col-lg-3 col-md-9 col-sm-12 col-xs-12 col-lg-offset-9 col-md-offset-3 col-xs-12">
                    <div class="row">
                      <div class="col-lg-6 col-md-6 col-sm-12 col-xs-12">
                        <button class="btn btn-danger form-control"
                                type="reset"
                                title="Efase tout done itilizatè sa">
                          <i class="fa fa-times"></i>
                          Reyajiste
                        </button>
                      </div>
                      <div class="col-lg-6 col-md-6 col-sm-12 col-xs-12">
                        <button class="btn btn-primary form-control"
                                type="submit"
                                ng-disabled="usernameExist"
                                title="Anrejistre tout done itilizatè sa">
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
      </div>
    </section>
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

<!-- container section end -->
<!-- javascripts -->
<#include "../scripts.ftl">

<script type="text/javascript">
    $('.selectpicker').selectpicker();

    $("#userForm").on("submit", function () {
        $("#custom-loader").fadeIn();
    });
</script>
</body>

</html>