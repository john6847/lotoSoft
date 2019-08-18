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
    <section id="main-content">
        <section class="wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <#if isSuperAdmin?? >
                        <#if isSuperAdmin == true >
                            <h3 class="page-header"><i class="fa fa-file-text-o"></i>Fòm Pou Kreye Kliyan</h3>
                            <ol class="breadcrumb">
                                <li><i class="fa fa-home"></i><a href="/home">Paj Akèy</a></li>
                                <li><i class="fa fa-edit">Kreye Kliyan</i></li>
                            </ol>
                        <#else>
                            <h3 class="page-header"><i class="fa fa-file-text-o"></i>Fòm Pou Kreye Ititilizatè</h3>
                            <ol class="breadcrumb">
                                <li><i class="fa fa-home"></i><a href="/home">Paj Akèy</a></li>
                                <li><i class="fa fa-edit">Kreye Ititilizatè</i></li>
                            </ol>
                        </#if>
                    </#if>
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
                            <#if isSuperAdmin?? >
                                <#if isSuperAdmin == true >
                                    Kliyan
                                <#else>
                                    Ititilizatè
                                </#if>
                            </#if>
                        </header>
                        <div class="panel-body">
                            <form name="userForm" class="form-horizontal form-validate" action="/user/create" th:object="${users}" method="post">
                                <#if isSuperAdmin?? >
                                    <#if isSuperAdmin == true >
                                        <input type="checkbox" checked name="isSuperAdmin" style="display: none;">
                                        <div class="form-group">
                                            <label class="col-lg-2 col-md-2 col-sm-2 control-label col-xs-12" for="enterprise">Antrepriz</label>
                                            <div class="col-lg-10 col-md-10 col-sm-10 col-xs-12">
                                                <select class="form-control round-input"
                                                        name="enterprise"
                                                        id="enterprise">
                                                        <#if enterprises??>
                                                            <#list enterprises as e >
                                                                <option value="${e.id}">${e.name}</option>
                                                            </#list>
                                                        </#if>
                                                </select>
                                            </div>
                                        </div>
                                    </#if>
                                </#if>
                                <div class="form-group">
                                    <#if isSuperAdmin?? >
                                        <#if isSuperAdmin == true>
                                            <label class="col-lg-2 col-md-2 col-sm-2 control-label col-xs-12" for="name">Kliyan<span class="required">*</span></label>
                                            <#else>
                                            <label class="col-lg-2 col-md-2 col-sm-2 control-label col-xs-12" for="name">Non<span class="required">*</span></label>
                                        </#if>
                                    </#if>
                                    <div class="col-lg-10 col-md-10 col-sm-10 col-xs-12">
                                        <input type="text" class="form-control round-input" placeholder="Egzanp: John Doe" id="name" name="name" minlength="2" maxlength="100" required>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-lg-2 col-md-2 col-sm-2 control-label col-xs-12" for="username">Non Ititilizatè <span class="required">*</span></label>
                                    <div class="col-lg-10 col-md-10 col-sm-10 col-xs-12">
                                        <input type="text"
                                               class="form-control round-input"
                                               placeholder="Egzanp: john25"
                                               id="username"
                                               name="username"
                                               minlength="4"
                                               maxlength="20"
                                               ng-model = "username"
                                               ng-model-options="{ debounce: 1000 }"
                                               ng-change="usernameChange()"
                                               required>
                                        <span style="color: red;" ng-show="userForm.username.$dirty && usernameExist">Non itilizatè sa pa apropriye!! </span>
                                        <span style="color: red;" ng-show="userForm.username.$dirty && usernameExist && suggestedUsername.length > 0">Eseye <b> {{suggestedUsername[0]}}</b> oubyen <b>{{suggestedUsername[1]}}</b></span>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-lg-2 col-md-2 col-sm-2 control-label col-xs-12" for="password">Modpas<span class="required">*</span></label>
                                    <div class="col-lg-10   col-md-10 col-sm-10 col-xs-12">
                                        <input type="password" class="form-control round-input" id="password" placeholder="******" name="password" maxlength="20" minlength="8" required>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-lg-2 col-md-2 col-sm-2 control-label col-xs-12" for="confirmPassword">Konfime Modpas<span class="required">*</span></label>
                                    <div class="col-lg-10   col-md-10 col-sm-10 col-xs-12">
                                        <input type="password" class="form-control round-input" id="confirmPassword"  placeholder="******" name="confirmPassword" maxlength="20" minlength="8" required>
                                    </div>
                                </div>

                                <#if isSuperAdmin == false>
                                    <#if roles??>
                                            <#assign admin = 0>
                                            <#assign collector = 0>
                                            <#assign seller = 0>
                                            <#assign supervisor = 0>
                                            <#list roles as role>
                                                <#if role.name=="ROLE_ADMIN">
                                                    <#assign admin = 1>
                                                </#if>
                                                <#if role.name=="ROLE_SELLER">
                                                    <#assign seller = 1>
                                                </#if>
                                                <#if role.name=="ROLE_SUPERVISOR">
                                                    <#assign supervisor = 1>
                                                </#if>
                                                <#if role.name=="ROLE_COLLECTOR">
                                                    <#assign collector = 1>
                                                </#if>
                                            </#list>
                                        <#if (!(admin == 0 && seller == 0))>
                                            <div class="form-group funkyradio">
                                                <div class="col-sm-2 col-sm-2 col-sm-2 "></div>
                                                <#if admin == 1>
                                                    <div class="col-lg-4 col-md-4 col-sm-4 col-xs-12 funkyradio-info">
                                                        <input type="checkbox" name="isAdmin" id="isAdmin">
                                                        <label for="isAdmin">Se Administratè</label>
                                                    </div>
                                                </#if>
                                                <#if seller == 1>
                                                    <div class="col-lg-4 col-md-4 col-sm-4 col-xs-12 funkyradio-info">
                                                        <input type="checkbox" name="isSeller" id="isSeller">
                                                        <label for="isSeller">Se Vandè</label>
                                                    </div>
                                                </#if>
                                            </div>
                                        </#if>
                                        <#if (!(collector == 0 && supervisor == 0))>
                                            <div class="form-group funkyradio">
                                                <div class="col-sm-2 col-sm-2 col-sm-2"></div>
                                                <#if supervisor == 1>
                                                    <div class="col-lg-4 col-md-4 col-sm-4 col-xs-12 funkyradio-info">
                                                        <input type="checkbox" name="isSupervisor" id="isSupervisor">
                                                        <label for="isSupervisor">Se Sipevizè</label>
                                                    </div>
                                                </#if>
                                                <#if collector == 1>
                                                    <div class="col-lg-4 col-md-4 col-sm-4 col-xs-12 funkyradio-info">
                                                        <input type="checkbox" name="isCollector" id="isCollector">
                                                        <label for="isCollector">Se Rekolektè</label>
                                                    </div>
                                                </#if>
                                            </div>
                                        </#if>
                                    </#if>
                                </#if>

                                <div class="form-group">
                                    <div class="col-lg-6 col-md-6 col-sm-12 col-lg-offset-6 col-md-offset-6 col-xs-12">
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



        <!-- container section end -->
        <!-- javascripts -->
    <#include "../scripts.ftl">

            <script type = "text/javascript" >
                    $('.selectpicker').selectpicker();
        </script>
</body>

</html>