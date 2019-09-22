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
    <section id="main-content"  ng-init="init(false)">
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
                            Vandè
                        </header>
                        <div class="panel-body">
                            <form name="sellerForm" class="form-horizontal" action="/seller/create" th:object="${seller}" method="post">
                                <div class="form-group funkyradio">
                                    <div class="col-sm-offset-2 col-sm-offset-2 col-sm-offset-2 col-lg-3 col-md-3 col-sm-3 col-xs-12 funkyradio-info">
                                        <input type="checkbox" name="haveAUser" id="haveAUser" ng-model="haveUser"/>
                                        <label for="haveAUser">Gen Itilizatè deja</label>
                                    </div>
                                </div>

                                <div class="form-group" ng-if="!haveUser">
                                    <label class="control-label col-lg-2 col-md-2 col-sm-2 col-xs-12" for="name">Non</label>
                                    <div class="col-lg-10 col-md-10 col-sm-10 col-xs-12">
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
                                    <label class="control-label col-lg-2 col-md-2 col-sm-2 col-xs-12" for="usernam">Non Itilizatè</label>
                                    <div class="col-lg-10 col-md-10 col-sm-10 col-xs-12">
                                        <input type="text"
                                               class="form-control round-input"
                                               name="username"
                                               id="username"
                                               placeholder="Antre non itilizatè"
                                               minlength="4"
                                               maxlength="20"
                                               ng-change="usernameChange()"
                                               ng-model = "username.sellerUsername"
                                               ng-model-options="{ debounce: 1000}"
                                               required>
                                        <span style="color: red;" ng-show="sellerForm.username.$dirty && username.usernameExist">Non itilizatè sa pa apropriye!! </span>
                                        <span style="color: red;" ng-show="sellerForm.username.$dirty && username.usernameExist && username.suggestedUsername.length > 0">Eseye <b> {{username.suggestedUsername[0]}}</b> oubyen <b>{{username.suggestedUsername[1]}}</b></span>
                                    </div>
                                </div>

                                <div class="form-group" ng-if="!haveUser">
                                    <label class="control-label col-lg-2 col-md-2 col-sm-2 col-xs-12" for="password">Modpas</label>
                                    <div class="col-lg-10 col-md-10 col-sm-10 col-xs-12">
                                        <input type="password"
                                               class="form-control round-input"
                                               name="password"
                                               placeholder="********"
                                               id="password"
                                               minlength="8"
                                               maxlength="20"
                                               required>
                                    </div>
                                </div>
                                <div class="form-group" ng-if="!haveUser">
                                    <label class="col-lg-2 col-md-2 col-sm-2 control-label col-xs-12" for="confirmPassword">Konfime Modpas</label>
                                    <div class="col-lg-10 col-md-10 col-sm-10 col-xs-12">
                                        <input type="password"
                                               class="form-control round-input"
                                               id="confirmPassword"
                                               placeholder="********"
                                               name="confirmPassword"
                                               maxlength="20"
                                               minlength="8"
                                               required>
                                    </div>
                                </div>

                                <div class="form-group" ng-if ="haveUser">
                                    <label class="col-lg-2 col-md-2 col-sm-2 control-label col-xs-12" for="user">Itilizatè</label>
                                    <div class="col-lg-10 col-md-10 col-sm-10 col-xs-12">
                                        <select class="form-control round-input"
                                                name="user"
                                                data-live-search="true"
                                                data-size="5"
                                                data-none-selected-text="Chwazi Itilizatè a"
                                                data-none-results-text="Itilizatè sa pa egziste"
                                                data-placeholder="Chwazi Itilizatè a"
                                                id="user"
                                                required>
                                            <#if usersSelect??>
                                                <#list usersSelect as u >
                                                    <option value="${u.id}">${u.username}</option>
                                                </#list>
                                            </#if>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-lg-2 col-md-2 col-sm-2 control-label col-xs-12" for="pos">Machin</label>
                                    <div class="col-lg-10 col-md-10 col-sm-10 col-xs-12">
                                        <select class="form-control round-input select2"
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


                                <div class="form-group funkyradio">
                                    <div class="col-sm-offset-2 col-sm-offset-2 col-sm-offset-2 col-lg-3 col-md-3 col-sm-3 col-xs-12 funkyradio-info">
                                        <input type="checkbox" name="haveAGroup" id="haveAGroup" ng-model="haveGroup"/>
                                        <label for="haveAGroup">Fè pati de yon gwoup</label>
                                    </div>
                                </div>

                                <div class="form-group" ng-if="haveGroup">
                                    <label class="col-lg-2 col-md-2 col-sm-2 control-label col-xs-12" for="groups">Gwoup</label>
                                    <div class="col-lg-10 col-md-10 col-sm-10 col-xs-12">
                                        <select class="form-control round-input selectpicker"
                                                name="groups"
                                                data-live-search="true"
                                                data-size="5"
                                                data-none-selected-text="Chwazi yon Gwoup pou vandè a"
                                                data-none-results-text="Gwoup sa pa egziste"
                                                data-placeholder="Chwazi Gwoup la"
                                                id="groups"
                                                required>
                                            <#if allGroups??>
                                                <#list allGroups as g >
                                                    <option value="${g.id}">${g.description}</option>
                                                </#list>
                                            </#if>
                                        </select>
                                    </div>
                                </div>


                                <div class="form-group funkyradio">
                                    <div class="col-sm-offset-2 col-sm-offset-2 col-sm-offset-2 col-lg-3 col-md-3 col-sm-3 col-xs-12 funkyradio-info">
                                        <input type="checkbox" name="useMonthlyPayment" id="useMonthlyPayment" ng-model="useMonthlyPayment" />
                                        <label for="useMonthlyPayment">Itilize pèman pa mwa</label>
                                    </div>
                                </div>

                                <div class="form-group" ng-if="!useMonthlyPayment">
                                    <label class="col-lg-2 col-md-2 col-sm-2 control-label col-xs-12" for="percentageCharged">Pousantaj</label>
                                    <div class="col-lg-10 col-md-10 col-sm-10 col-xs-12 input-group">
                                        <input type="number" class="form-control round-input" min="0" max="100" name="percentageCharged" id="percentageCharged"  required>
                                        <span class="input-group-addon">%</span>
                                    </div>
                                </div>

                                <div class="form-group" ng-if="useMonthlyPayment">
                                    <label class="col-lg-2 col-md-2 col-sm-2 control-label col-xs-12" for="amountCharged" >Peman chak mwa</label>
                                    <div class="col-lg-10 col-md-10 col-sm-10 input-group">
                                        <input type="number" class="form-control round-input" min="0"   name="amountCharged" id="amountCharged" required>
                                        <span class="input-group-addon">HTG</span>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <div class="col-lg-6 col-md-6 col-sm-12 col-lg-offset-6 col-md-offset-6 col-xs-12">
                                        <div class="row">
                                            <div class="col-lg-6 col-md-6 col-sm-12 col-xs-12">
                                                <button class="btn btn-danger form-control"
                                                        type="reset"
                                                        title="Efase tout done vandè sa">
                                                        <i class="fa fa-times"></i>
                                                        Reyajiste
                                                </button>
                                            </div>
                                            <div class="col-lg-6 col-md-6 col-sm-12 col-xs-12">
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
    </section>

        <!-- container section end -->
    <#include "../scripts.ftl">


    <#--<script type = "text/javascript" >-->
        <#--$('.selectpicker').selectpicker();-->
    <#--</script>-->
</body>

</html>