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
                        <div class="row">
                            <div class="col-lg-12">
                                <section class="panel">
                                    <header class="panel-heading">
                                        Enfomasyon Antrepriz la
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
            <#--                                https://bootsnipp.com/snippets/Zk2Pz -->

                                    </div>

                                </section>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-lg-12">
                                <section class="panel">
                                    <header class="panel-heading">
                                        Fonksyon Nan Antrepriz la
                                    </header>
                                    <div class="panel-body">
                                        <div class="form-group funkyradio">
                                            <div class="col-lg-4 col-md-4 col-sm-4 col-xs-12 funkyradio-warning">
                                                <input type="checkbox" name="superAdmin" id="superAdmin" checked/>
                                                <label for="superAdmin">SUPERADMIN</label>
                                            </div>
                                            <div class="col-lg-4 col-md-4 col-sm-4 col-xs-12 funkyradio-warning">
                                                <input type="checkbox" name="admin" id="admin" checked/>
                                                <label for="admin">ADMIN</label>
                                            </div>
                                            <div class="col-lg-4 col-md-4 col-sm-4 col-xs-12 funkyradio-warning">
                                                <input type="checkbox" name="seller" id="seller" checked/>
                                                <label for="seller">VANDE</label>
                                            </div>
                                        </div>

                                        <div class="form-group funkyradio">
                                            <div class="col-lg-6 col-md-6 col-sm-6 col-xs-12 funkyradio-warning">
                                                <input type="checkbox" name="recollector" id="recollector" checked/>
                                                <label for="recollector">REKOLEKTE</label>
                                            </div>
                                            <div class="col-lg-6 col-md-6 col-sm-6 col-xs-12 funkyradio-warning">
                                                <input type="checkbox" name="supervisor" id="supervisor" checked/>
                                                <label for="supervisor">SIPEVIZE</label>
                                            </div>
                                        </div>
                                    </div>
                                </section>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-6 col-md-6 col-sm-12 col-xs-12">
                        <div class="row">
                            <div class="col-12">
                                <section class="panel"  style="margin-right: 10px">
                                    <header class="panel-heading">
                                        Tip Konbinezon Antrepriz la Manevre
                                    </header>
                                    <div class="panel-body">

                                        <div class="form-group funkyradio">
                                            <div class="col-lg-4 col-md-4 col-sm-6 col-xs-12 funkyradio-info">
                                                <input type="checkbox" name="bolet" id="bolet" checked/>
                                                <label for="bolet">BOLET</label>
                                            </div>
                                            <div class="col-lg-4 col-md-4 col-sm-6 col-xs-12 funkyradio-info">
                                                <input type="checkbox" name="lotoTwaChif" id="lotoTwaChif" checked/>
                                                <label for="lotoTwaChif">LOTO 3 CHIF</label>
                                            </div>
                                            <div class="col-lg-4 col-md-4 col-sm-6 col-xs-12 funkyradio-info">
                                                <input type="checkbox" name="lotoKatChif" id="lotoKatChif" checked/>
                                                <label for="lotoKatChif">LOTO 4 CHIF</label>
                                            </div>
                                        </div>

                                        <div class="form-group funkyradio">
                                            <div class="col-lg-4 col-md-4 col-sm-6 col-xs-12 funkyradio-info">
                                                <input type="checkbox" name="opsyon" id="opsyon" checked/>
                                                <label for="opsyon">OPSYON</label>
                                            </div>
                                            <div class="col-lg-4 col-md-4 col-sm-6 col-xs-12 funkyradio-info">
                                                <input type="checkbox" name="maryaj" id="maryaj" checked/>
                                                <label for="maryaj">MARYAJ</label>
                                            </div>
                                            <div class="col-lg-4 col-md-4 col-sm-6 col-xs-12 funkyradio-info">
                                                <input type="checkbox" name="extra" id="extra"/>
                                                <label for="extra">EXTRA</label>
                                            </div>
                                        </div>

                                    </div>
                                </section>

                            </div>
                        </div>

                        <div class="row">
                            <div class="col-12">
                                <section class="panel"  style="margin-right: 10px">
                                    <header class="panel-heading">
                                        Adrès Antrepriz la
                                    </header>
                                    <div class="panel-body">

                                        <div class="form-group">
                                            <label class="control-label col-lg-2 col-md-2 col-sm-2">Peyi</label>
                                            <div class="col-lg-10 col-md-10 col-sm-10">
                                                <input type="text"
                                                       class="form-control round-input"
                                                       name="country"
                                                       placeholder="Antre non peyi a"
                                                       id="country"
                                                       maxlength="100">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-lg-2 col-md-2 col-sm-2">Depatman</label>
                                            <div class="col-lg-10 col-md-10 col-sm-10">
                                                <input type="text"
                                                       class="form-control round-input"
                                                       name="region"
                                                       id="region"
                                                       placeholder="Antre non depatman an"
                                                       maxlength="100">
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
                                                       maxlength="100">
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
                                                       maxlength="100">
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
                                                       maxlength="100">
                                            </div>
                                        </div>

                                        <div class="form-group">
                                            <label class="control-label col-lg-2 col-md-2 col-sm-2">Nimewo</label>
                                            <div class="col-lg-10 col-md-10 col-sm-10">
                                                <input type="number"
                                                       class="form-control round-input"
                                                       name="number"
                                                       id="number"
                                                       placeholder="Antre non rout la"
                                                       maxlength="100">
                                            </div>
                                        </div>

                                        <div class="form-group">
                                            <label class="control-label col-lg-2 col-md-2 col-sm-2">Telefòn</label>
                                            <div class="col-lg-10 col-md-10 col-sm-10">
                                                <input type="text"
                                                       class="form-control round-input"
                                                       name="phone"
                                                       id="phone"
                                                       placeholder="Antre nimewo telefòn gwoup"
                                                       maxlength="30">
                                            </div>
                                        </div>

                                        <div class="form-group">
                                            <label class="control-label col-lg-2 col-md-2 col-sm-2">Imel</label>
                                            <div class="col-lg-10 col-md-10 col-sm-10">
                                                <input type="email"
                                                       class="form-control round-input"
                                                       name="email"
                                                       id="email"
                                                       placeholder="Antre imel la"
                                                       maxlength="30">
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