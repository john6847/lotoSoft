<!DOCTYPE html>
<html lang="en"
      xmlns="http://www.w3.org/1999/xhtml"
      xmlns:th="http://www.thymeleaf.org"
>

<head>
<#include "../header.ftl">
</head>

<body ng-app="lottery" ng-cloak>
<!-- container section start -->
<section id="container" class="" ng-controller="bankController">
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
                    <h3 class="page-header"><i class="fa fa-file-text-o"></i>Fòm Pou Kreye Bank</h3>
                    <ol class="breadcrumb">
                        <li><i class="fa fa-home"></i><a href="/home">Paj Akèy</a></li>
                        <li><i class="fa fa-file-text-o"></i>Kreye Bank</li>
                    </ol>
                </div>
            </div>
            <div class="row">
                <div class="col-lg-12 col-xs-12 col-xl-12 col-md-12 col-sm-12">
                    <#if error??>
                        <div class="alert alert-danger" role="alert">${error}</div>
                    </#if>
                </div>
                <form class="form-horizontal form-validate" action="/bank/create" th:object="${bank}" method="post">
                    <div class="col-lg-6 col-md-6 col-sm-12 col-xs-12">
                        <div class="row">
                            <div class="col-lg-12">
                                <section class="panel">
                                    <header class="panel-heading">
                                        Enfomasyon Bank lan
                                    </header>
                                    <div class="panel-body">
                                        <div class="form-group">
                                            <label class="col-lg-2 col-md-2 col-sm-2 control-label">Deskripsyon<span class="required">*</span></label>
                                            <div class="col-lg-10   col-md-10 col-sm-10">
                                                <input type="text" class="form-control round-input" id="description" name="description" minlength="2" required>
                                            </div>
                                        </div>

                                        <div class="form-group">
                                            <label class="control-label col-lg-2 col-md-2 col-sm-2" for="inputSuccess">Vande<span class="required">*</span></label>
                                            <div class="col-lg-10 col-md-10 col-sm-10">
                                                <select class="form-control round-input selectpicker"
                                                        name="seller"
                                                        id="seller"
                                                        data-size="5"
                                                        data-none-selected-text="Chwazi Vande a"
                                                        data-allow-clear="true"
                                                        data-none-results-text="Vande sa pa egziste"
                                                        data-placeholder="Chwazi vande la">
                                                    <#if sellers??>
                                                        <#list sellers as seller>
                                                            <option value="${seller.id}">${seller.user.name}</option>
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
                                    </div>
                                </section>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-6 col-md-6 col-sm-12 col-xs-12">
                        <div class="row">
                            <div class="col-lg-12">
                                <section class="panel">
                                    <header class="panel-heading">
                                        Adrès Bank lan
                                    </header>
                                    <div class="panel-body">
                                        <div class="form-group">
                                            <label class="control-label col-lg-2 col-md-2 col-sm-2">Depatman</label>
                                            <div class="col-lg-10 col-md-10 col-sm-10">
                                                <input type="text"
                                                       class="form-control round-input"
                                                       name="region"
                                                       id="region"
                                                       placeholder="Antre non depatman an"
                                                       maxlength="100"
                                                       >
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
                                                       >
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
                                                       >
                                            </div>
                                        </div>

                                        <div class="form-group">
                                            <label class="control-label col-lg-2 col-md-2 col-sm-2">Rout</label>
                                            <div class="col-lg-10 col-md-10 col-sm-10">
                                                <input type="text"
                                                       class="form-control round-input"
                                                       name="street"
                                                       id="street"
                                                       placeholder="Antre non rout la"
                                                       maxlength="100"
                                                       >
                                            </div>
                                        </div>


                                    </div>

                                </section>
                            </div>
                        </div>
                    </div>

                    <div class="col-lg-12">
                        <div class="breadcrumb" style="height: auto;">
                            <div class="form-group" style="margin-bottom: 10px;">
                                <div class="col-lg-6 col-md-6 col-sm-12 col-lg-offset-6 col-md-offset-6">
                                    <div class="row">
                                        <div class="col-lg-6 col-md-6 col-sm-12 ">
                                            <button type="reset"
                                                    class="btn btn-danger form-control"
                                                    title="Efase tout done bank lan">
                                                <i class="fa fa-times"></i>
                                                Reyajiste
                                            </button>
                                        </div>
                                        <div class="col-lg-6 col-md-6 col-sm-12">
                                            <button class="btn btn-primary form-control"
                                                    type="submit"
                                                    title="Anrejistre tout done bank la">
                                                <i class="fa fa-save"></i>
                                                Anrejistre
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
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


<#include "../scripts.ftl">
    <script type = "text/javascript" >
        $('.selectpicker').selectpicker();
        $.fn.selectpicker.Constructor.BootstrapVersion = '4';
    </script>


</body>

</html>