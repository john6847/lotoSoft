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
<section id="container" class="" ng-controller="posController">
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
                    <h3 class="page-header"><i class="fa fa-file-text-o"></i>Fòm Pou Kreye Antrepriz</h3>
                    <ol class="breadcrumb">
                        <li><i class="fa fa-home"></i><a href="/home">Paj Akèy</a></li>
                        <li><i class="fa fa-file-text-o"></i>Kreye Antrepriz</li>
                    </ol>
                </div>
            </div>
            <div class="row">
                <div class="col-lg-12 col-xs-12 col-xl-12 col-md-12 col-sm-12">
                    <#if error??>
                        <div class="alert alert-danger" role="alert">${error}</div>
                    </#if>
                </div>
                <form class="form-horizontal form-validate" action="/enterprise/create" th:object="${enterprise}" method="post">
                    <div class="col-lg-6 col-md-6 col-sm-12 col-xs-12">
                        <section class="panel">
                            <header class="panel-heading">
                                Antrepriz
                            </header>
                            <div class="panel-body">
                                    <div class="form-group">
                                        <label class="col-lg-2 col-md-2 col-sm-2 control-label">Antrepriz <span class="required">*</span></label>
                                        <div class="col-lg-10   col-md-10 col-sm-10">
                                            <input type="text" class="form-control round-input" id="name" name="name" minlength="2" required>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-lg-2 col-md-2 col-sm-2 control-label">Kantite Anplwaye</label>
                                        <div class="col-lg-10   col-md-10 col-sm-10">
                                            <input type="number" class="form-control round-input" id="numberOfEmployee" name="numberOfEmployee" min="0">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-lg-2 col-md-2 col-sm-2 control-label">Telefon</label>
                                        <div class="col-lg-10   col-md-10 col-sm-10">
                                            <input type="text" class="form-control round-input" id="phone" name="phone">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-lg-2 col-md-2 col-sm-2 control-label">RNC</label>
                                        <div class="col-lg-10   col-md-10 col-sm-10">
                                            <input type="text" class="form-control round-input" id="rnc" name="rnc">
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-lg-2 col-md-2 col-sm-2 control-label">Logo Url</label>
                                        <div class="col-lg-10   col-md-10 col-sm-10">
                                            <input type="text" class="form-control round-input" id="logoUrl" name="logoUrl">
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="col-lg-2 col-md-2 col-sm-2 control-label">Sub Domain</label>
                                        <div class="col-lg-10 col-md-10 col-sm-10">
                                            <input type="text" class="form-control round-input" id="subDomain" name="subDomain">
                                        </div>
                                    </div>
    <#--                                https://bootsnipp.com/snippets/Zk2Pz-->

                                    <div class="form-group">
                                        <div class="col-lg-6 col-md-6 col-sm-12 col-lg-offset-6 col-md-offset-6">
                                            <div class="row">
                                                <div class="col-lg-6 col-md-6 col-sm-12 ">
                                                    <button type="reset"
                                                            class="btn btn-danger form-control"
                                                            title="Efase tout done antrepriz la">
                                                            <i class="fa fa-times"></i>
                                                            Reyajiste
                                                    </button>
                                                </div>
                                                <div class="col-lg-6 col-md-6 col-sm-12">
                                                    <button class="btn btn-primary form-control"
                                                            type="submit"
                                                            title="Anrejistre tout done antrepriz la">
                                                            <i class="fa fa-save"></i>
                                                            Anrejistre
                                                    </button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                            </div>

                        </section>
                    </div>
                    <div class="col-lg-6 col-md-6 col-sm-12 col-xs-12">
                        <div class="row">
                            <div class="col-12">
                                <section class="panel">
                                    <header class="panel-heading">
                                        Tip Konbinezon
                                    </header>
                                    <div class="panel-body funkyradio">

                                        <div class="form-group">
                                            <div class="col-lg-4 col-md-4 col-sm-6 col-xs-12 funkyradio-info">
                                                <input type="checkbox" name="checkbox" id="checkbox1" checked/>
                                                <label for="checkbox1">BOLET</label>
                                            </div>
                                            <div class="col-lg-4 col-md-4 col-sm-6 col-xs-12 funkyradio-info">
                                                <input type="checkbox" name="checkbox" id="checkbox2" checked/>
                                                <label for="checkbox2">LOTO 3 CHIF</label>
                                            </div>
                                            <div class="col-lg-4 col-md-4 col-sm-6 col-xs-12 funkyradio-info">
                                                <input type="checkbox" name="checkbox" id="checkbox3" checked/>
                                                <label for="checkbox3">LOTO 4 CHIF</label>
                                            </div>
                                        </div>

                                        <div class="form-group">
                                            <div class="col-lg-4 col-md-4 col-sm-6 col-xs-12 funkyradio-info">
                                                <input type="checkbox" name="checkbox" id="checkbox4" checked/>
                                                <label for="checkbox4">OPSYON</label>
                                            </div>
                                            <div class="col-lg-4 col-md-4 col-sm-6 col-xs-12 funkyradio-info">
                                                <input type="checkbox" name="checkbox" id="checkbox5" checked/>
                                                <label for="checkbox5">MARYAJ</label>
                                            </div>
                                            <div class="col-lg-4 col-md-4 col-sm-6 col-xs-12 funkyradio-info">
                                                <input type="checkbox" name="checkbox" id="checkbox6" checked/>
                                                <label for="checkbox6">EXTRA</label>
                                            </div>
                                        </div>

                                    </div>
                                </section>

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

</body>

</html>