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
                    <h3 class="page-header"><i class="fa fa-file-text-o"></i>Fòm Pou Kreye Gwoup</h3>
                    <ol class="breadcrumb">
                        <li><i class="fa fa-home"></i><a href="/home">Paj Akèy</a></li>
                        <li><i class="fa fa-file-text-o"></i>Kreye Gwoup</li>
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
                            Gwoup
                        </header>
                        <div class="panel-body">
                            <form class="form-horizontal" action="/group/create" th:object="${group}" method="post">

                                <div class="form-group">
                                    <label class="col-lg-2 col-md-2 col-sm-2 control-label">Deskripsyon<span class="required">*</span></label>
                                    <div class="col-lg-10   col-md-10 col-sm-10">
                                        <input type="text" class="form-control round-input" id="description" name="description" minlength="2" required>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-lg-2 col-md-2 col-sm-2 control-label">Respoonsab <span class="required">*</span></label>
                                    <div class="col-lg-10 col-md-10 col-sm-10">
                                        <select class="form-control round-input"
                                                name="parentSeller"
                                                data-live-search="true"
                                                data-size="5"
                                                data-none-selected-text="Chwazi Responsab la"
                                                data-none-results-text="Responsab sa pa egziste"
                                                data-placeholder="Chwazi Responsab a"
                                                id="parentSeller">
                                            <#if sellers??>
                                                <#list sellers as seller >
                                                    <option value="${seller.id}">${seller.user.name}</option>
                                                </#list>
                                            </#if>
                                        </select>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="control-label col-lg-2 col-md-2 col-sm-2">Peyi</label>
                                    <div class="col-lg-10 col-md-10 col-sm-10">
                                        <input type="text"
                                           class="form-control round-input"
                                           name="country"
                                           placeholder="Antre non peyi a"
                                           id="country"
                                           maxlength="100"
                                           required>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-lg-2 col-md-2 col-sm-2">Vil</label>
                                    <div class="col-lg-10 col-md-10 col-sm-10">
                                        <input type="text"
                                           class="form-control round-input"
                                           name="city"
                                           id="city"
                                           placeholder="Antre non vil la"
                                           maxlength="100"
                                           required>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="control-label col-lg-2 col-md-2 col-sm-2">Sektè</label>
                                    <div class="col-lg-10 col-md-10 col-sm-10">
                                        <input type="text"
                                           class="form-control round-input"
                                           name="sector"
                                           id="sector"
                                           placeholder="Antre non sektè a"
                                           maxlength="100"
                                           required>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="control-label col-lg-2 col-md-2 col-sm-2">Telefòn</label>
                                    <div class="col-lg-10 col-md-10 col-sm-10">
                                        <input type="text"
                                           class="form-control round-input"
                                           name="phone"
                                           id="phone"
                                           placeholder="Antre nimewo telefòn responsab gwoup la a"
                                           maxlength="30"
                                           required>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <div class="col-lg-6 col-md-6 col-sm-12 col-lg-offset-6 col-md-offset-6">
                                        <div class="row">
                                            <div class="col-lg-6 col-md-6 col-sm-12 ">
                                                <button class="btn btn-danger form-control"
                                                        type="reset"
                                                        title="Efase tout done gwoup sa">
                                                        <i class="fa fa-times"></i>
                                                        Reyajiste
                                                </button>
                                            </div>
                                            <div class="col-lg-6 col-md-6 col-sm-12">
                                                <button class="btn btn-primary form-control"
                                                        type="submit"
                                                        title="Anrejistre tout done gwoup sa">
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
    </section>

        <!-- container section end -->
    <#include "../scripts.ftl">

        <script
            type = "text/javascript">
            $(document).ready(function () {
                $('.selectpicker').selectpicker();
            });
        </script>
</section>
</body>

</html>