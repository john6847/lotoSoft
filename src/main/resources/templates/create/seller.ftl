<!DOCTYPE html >
<html lang="en"
      xmlns="http://www.w3.org/1999/xhtml"
      xmlns:th="http://www.thymeleaf.org"
      xmlns:sec="http://www.thymeleaf.org/thymeleaf-extras-springsecurity3">

<head>
<#include "../header.ftl">

</head>

<body ng-app="lottery" ng-cloak>
<!-- container section start -->
<section id="container" class="" ng-controller="sellerController">
    <!--nav start-->
<#include "../nav.ftl" >
    <!--header end-->

    <!--sidebar start-->
    <aside>
    <#include "../sidebar.ftl">
    </aside>
    <!--sidebar end-->

    <!--main content start-->
    <section id="main-content" ng-init="fetchAllSellers()">
        <section class="wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h3 class="page-header"><i class="fa fa-file-text-o"></i>Fòm Pou Kreye Vandè</h3>
                    <ol class="breadcrumb">
                        <li><i class="fa fa-home"></i><a href="/home">Paj Akèy</a></li>
                        <li><i class="fa fa-file-text-o"></i>Kreye Vandè</li>
                    </ol>
                </div>
            </div>
            <div class="row">
                <div class="col-lg-12">
                    <#if error??>
                        <div class="col-lg-12 col-xs-12 col-xl-12 col-md-12 col-sm-12">
                            <div class="alert alert-danger" role="alert">${error}</div>
                        </div>
                    </#if>
                    <section class="panel">
                        <header class="panel-heading">
                            Vandè
                        </header>
                        <div class="panel-body">
                            <form class="form-horizontal" action="/seller/create" th:object="${seller}" method="post">
                                <div class="form-group">
                                    <label class="control-label col-lg-2 col-md-2 col-sm-2">Gen Itilizatè deja<span class="required">*</span></label>
                                    <div class="col-lg-10 col-md-10 col-sm-10">
                                        <input type="checkbox" name="haveAUser" ng-model="haveUser">
                                    </div>
                                </div>
                                <div class="form-group" ng-if="!haveUser">
                                    <label class="control-label col-lg-2 col-md-2 col-sm-2">Non</label>
                                    <div class="col-lg-10 col-md-10 col-sm-10">
                                        <input type="text"
                                           class="form-control round-input"
                                           name="name"
                                           placeholder="Antre non vandè"
                                           id="name"
                                           minlength="5"
                                           maxlength="100"
                                           required>
                                    </div>
                                </div>
                                <div class="form-group" ng-if="!haveUser">
                                    <label class="control-label col-lg-2 col-md-2 col-sm-2">Non Itilizatè</label>
                                    <div class="col-lg-10 col-md-10 col-sm-10">
                                        <input type="text"
                                           class="form-control round-input"
                                           name="username"
                                           id="username"
                                           placeholder="Antre non itilizatè"
                                           minlength="5"
                                           maxlength="20"
                                           required>
                                    </div>
                                </div>

                                <div class="form-group" ng-if="!haveUser">
                                    <label class="control-label col-lg-2 col-md-2 col-sm-2">Modpas</label>
                                    <div class="col-lg-10 col-md-10 col-sm-10">
                                        <input type="password"
                                           class="form-control round-input"
                                           name="password"
                                           placeholder="********"
                                           id="password"
                                           minlength="8"
                                           required>
                                    </div>
                                </div>

                                <div class="form-group" ng-if ="haveUser">
                                    <label class="col-lg-2 col-md-2 col-sm-2 control-label">Itilizatè</label>
                                    <div class="col-lg-10 col-md-10 col-sm-10">
                                        <select class="form-control round-input"
                                            name="user"
                                            data-live-search="true"
                                            data-size="5"
                                            data-none-selected-text="Chwazi Itilizatè a"
                                            data-none-results-text="Itilizatè sa pa egziste"
                                            data-placeholder="Chwazi Itilizatè a"
                                            id="user">
                                            <#if usersSelect??>
                                                <#list usersSelect as u >
                                                    <option value="${u.id}">${u.username}</option>
                                                </#list>
                                            </#if>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-lg-2 col-md-2 col-sm-2 control-label">Machin</label>
                                    <div class="col-lg-10   col-md-10 col-sm-10">
                                        <select class="form-control round-input"
                                                name="pos"
                                                id="pos"
                                                data-live-search="true"
                                                data-none-results-text="Machin sa pa egziste"
                                                data-placeholder="Chwazi machin nan"
                                                data-none-selected-text="Chwazi Machin nan"
                                                data-size="5"
                                                required>
                                                <#if pos??>
                                                    <#list pos as p>
                                                        <option value="${p.id}">${p.description}</option>
                                                    </#list>
                                                </#if>
                                        </select>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="control-label col-lg-2 col-md-2 col-sm-2">Fè Pati de yon gwoup</label>
                                    <div class="col-lg-10 col-md-10 col-sm-10">
                                        <input type="checkbox" name="haveAGroup" ng-model="haveGroup">
                                    </div>
                                </div>

                                <div class="form-group" ng-if="haveGroup">
                                    <label class="col-lg-2 col-md-2 col-sm-2 control-label">Gwoup</label>
                                    <div class="col-lg-10 col-md-10 col-sm-10">
                                        <select class="form-control round-input"
                                                name="groups"
                                                data-live-search="true"
                                                data-size="5"
                                                data-none-selected-text="Chwazi yon Gwoup pou vandè a"
                                                data-none-results-text="Gwoup sa pa egziste"
                                                data-placeholder="Chwazi Gwoup la"
                                                id="groups">
                                            <#if allGroups??>
                                                <#list allGroups as g >
                                                    <option value="${g.id}">${g.description}</option>
                                                </#list>
                                            </#if>
                                        </select>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="control-label col-lg-2 col-md-2 col-sm-2">Itilize peman pa mwa<span class="required">*</span></label>
                                    <div class="col-lg-10 col-md-10 col-sm-10">
                                        <input type="checkbox" name="useMonthlyPayment" ng-model="useMonthlyPayment">
                                    </div>
                                </div>
                                <div class="form-group" ng-if="!useMonthlyPayment">
                                    <label class="col-lg-2 col-md-2 col-sm-2 control-label">Pousantaj</label>
                                    <div class="col-lg-10 col-md-10 col-sm-10 input-group">
                                        <input type="number" class="form-control round-input" name="percentageCharged" id="percentageCharged">
                                        <span class="input-group-addon">%</span>
                                    </div>
                                </div>
                                <div class="form-group" ng-if="useMonthlyPayment">
                                    <label class="col-lg-2 col-md-2 col-sm-2 control-label">Peman chak mwa</label>
                                    <div class="col-lg-10 col-md-10 col-sm-10 input-group">
                                        <input type="number" class="form-control round-input" name="amountCharged" id="amountCharged">
                                        <span class="input-group-addon">HTG</span>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <div class="col-lg-6 col-md-6 col-sm-12 col-lg-offset-6 col-md-offset-6">
                                        <div class="row">
                                            <div class="col-lg-6 col-md-6 col-sm-12 ">
                                                <button class="btn btn-danger form-control"
                                                        type="reset"
                                                        title="Efase tout done vandè sa">
                                                        <i class="fa fa-times"></i>
                                                        Reyajiste
                                                </button>
                                            </div>
                                            <div class="col-lg-6 col-md-6 col-sm-12">
                                                <button class="btn btn-primary form-control"
                                                        type="submit"
                                                        title="Anrejistre tout done vandè sa">
                                                        <i class="fa fa-save"></i>
                                                        Anrejistre
                                                </button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </form>
                        </div>
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
                </div>
            </div>
        </section>

        <!-- container section end -->
    <#include "../scripts.ftl">

        <script
            type = "text/javascript">
            $(document).ready(function () {
                $('.selectpicker').selectpicker();
            });
        </script>
</body>

</html>