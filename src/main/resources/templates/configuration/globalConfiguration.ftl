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
    <section id="main-content" ng-controller="globalConfigurationController">
        <section class="wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h3 class="page-header"><i class="icon_cog"></i>Paj Pou Konfigirasyon Jeneral</h3>
                    <ol class="breadcrumb">
                        <li><i class="fa fa-home"></i><a href="/home">Paj Akèy</a></li>
                        <li><i class="icon_cog"></i>Konfigirasyon Jeneral</li>
                    </ol>
                </div>
            </div>
            <div class="row">
                <div class="col-lg-12 col-xs-12 col-xl-12 col-md-12 col-sm-12" ng-if="shiftField.message">
                    <div class="alert alert-success" role="alert" ng-if="shiftField.message.saved">
                        {{shiftField.message.message}}
                    </div>
                    <div class="alert alert-danger" role="alert" ng-if="!shiftField.message.saved">
                        {{shiftField.message.message}}
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-lg-12">
                    <form name="globalConfigurationForm" class="form-horizontal" ng-if="globalConfiguration">
                        <div class="form-group">
                            <div class="col-lg-offset-2 col-sm-offset-2 col-md-offset-2 col-lg-8 col-xs-12 col-md-8 col-sm-8">
                                <md-card md-theme-watch>
                                    <md-card-title layout-align="start center">
                                        <md-card-title-text>
                                            <span class="md-headline">Konfigirasyon jeneral</span>
                                        </md-card-title-text>
                                    </md-card-title>

                                    <md-card-content layout-align="start center" ng-cloak>
                                        <div layout-gt-xs="row" style="margin-right: 10px;">
                                            <div layout="row">
                                                <md-input-container class="md-block">
                                                    <md-checkbox ng-model="globalConfiguration.limitCombinationPrice">
                                                        Limite pri konbinezon yo
                                                    </md-checkbox>
                                                </md-input-container>
                                            </div>
                                        </div>
                                        <div layout-gt-xs="row" style="margin-right: 10px;" ng-if="globalConfiguration.limitCombinationPrice">
                                            <div layout="row">
                                                <md-input-container class="md-block">
                                                    <md-checkbox ng-model="globalConfiguration.notifyLimitCombinationPrice">
                                                        Voye notifikasyon lè konbinezon yo rive nan limit yo
                                                    </md-checkbox>
                                                </md-input-container>
                                            </div>
                                        </div>
                                        <div flex-gt-xs="row" ng-if="globalConfiguration.limitCombinationPrice">
                                            <h4>Pri gwo pri yo kapab vann</h4>
                                            <div class="input-group">
                                                <input id="maxLimitCombinationPrice"
                                                       class="form-control"
                                                       name="maxLimitCombinationPrice"
                                                       ng-model="globalConfiguration.maxLimitCombinationPrice"
                                                       required
                                                       min="1000"
                                                       type="number"/>
                                                <span class="input-group-addon"><i
                                                            class="fa fa-clock-o"></i></span>
                                            </div>
                                            <span class="alert alert-danger error"
                                                  ng-show="globalConfigurationForm.maxLimitCombinationPrice.$dirty && globalConfigurationForm.maxLimitCombinationPrice.$error.required">Rantre pri maksimom nan!</span>
                                            <span class="alert alert-danger error"
                                                  ng-show="globalConfigurationForm.maxLimitCombinationPrice.$dirty && globalConfigurationForm.maxLimitCombinationPrice.$error.min">Pri a pa bon! rantre yon valè ki pi gwo ke 1000 htg</span>
                                        </div>

                                        <div layout-gt-xs="row" style="margin-right: 10px;">
                                            <div layout="row">
                                                <md-input-container class="md-block">
                                                    <md-checkbox ng-model="globalConfiguration.transferSaleToAnotherShift">
                                                       Pemèt sistèm nan transfere tikè a nan prochen tiraj la si bòlèt la fèmen
                                                    </md-checkbox>
                                                </md-input-container>
                                            </div>
                                        </div>
                                        <div layout-gt-xs="row" style="margin-right: 10px;">
                                            <div layout="row">
                                                <md-input-container class="md-block">
                                                    <md-checkbox ng-model="globalConfiguration.deleteUserTokenAfterAmountOfTime">
                                                        Pemèt sistèm nan elimine sesyon vandè a apre yon kantite èd tan.
                                                    </md-checkbox>
                                                </md-input-container>
                                            </div>
                                        </div>
                                        <div flex-gt-xs="row"  ng-if="globalConfiguration.deleteUserTokenAfterAmountOfTime">
                                            <h4>Kantite èd tan sesyon an dwe dire</h4>
                                            <div class="input-group">
                                                <input id="userTokenLifeTime"
                                                       class="form-control"
                                                       name="userTokenLifeTime"
                                                       ng-model="globalConfiguration.userTokenLifeTime"
                                                       required
                                                       min="1"
                                                       max="8"
                                                       type="number"/>
                                                <span class="input-group-addon"><i
                                                            class="fa fa-clock-o"></i></span>
                                            </div>
                                            <span class="alert alert-danger error"
                                                  ng-show="globalConfigurationForm.userTokenLifeTime.$dirty && globalConfigurationForm.userTokenLifeTime.$error.required">Rantre kantite lè a !</span>
                                            <span class="alert alert-danger error"
                                                  ng-show="globalConfigurationForm.userTokenLifeTime.$dirty && globalConfigurationForm.userTokenLifeTime.$error.min">Kantite lè sa twòp piti!
                                            </span>
                                            <span class="alert alert-danger error"
                                                  ng-show="globalConfigurationForm.userTokenLifeTime.$dirty && globalConfigurationForm.userTokenLifeTime.$error.max">Kantite lè sa twòp!
                                            </span>
                                        </div>

                                        <div layout-gt-xs="row" style="margin-right: 10px;">
                                            <div layout="row">
                                                <md-input-container class="md-block">
                                                    <md-checkbox ng-model="globalConfiguration.canDeleteTicket">
                                                        Vandè a ka elimine tikè a.
                                                    </md-checkbox>
                                                </md-input-container>
                                            </div>
                                        </div>
                                        <div flex-gt-xs="row" ng-if="globalConfiguration.canDeleteTicket">
                                            <h4>Kantite minit ki pa dwe pase pou elimine tikè a</h4>
                                            <div class="input-group">
                                                <input id="ticketLifeTime"
                                                       class="form-control"
                                                       name="ticketLifeTime"
                                                       ng-model="globalConfiguration.ticketLifeTime"
                                                       required
                                                       min="1"
                                                       type="number"/>
                                                <span class="input-group-addon"><i
                                                            class="fa fa-clock-o"></i></span>
                                            </div>
                                            <span class="alert alert-danger error"
                                                  ng-show="globalConfigurationForm.ticketLifeTime.$dirty && globalConfigurationForm.ticketLifeTime.$error.required">Rantre kantite minit la!</span>
                                            <span class="alert alert-danger error"
                                                  ng-show="globalConfigurationForm.ticketLifeTime.$dirty && globalConfigurationForm.ticketLifeTime.$error.min">Kantite a twò piti!</span>
                                        </div>
                                        <div layout-gt-xs="row" style="margin-right: 10px;">
                                            <div layout="row">
                                                <md-input-container class="md-block">
                                                    <md-checkbox ng-model="globalConfiguration.canReplayTicket">
                                                        Vandè a ka revann tikè a.
                                                    </md-checkbox>
                                                </md-input-container>
                                            </div>
                                        </div>

                                    </md-card-content>

                                    <md-card-actions layout="row" layout-align="end center">
                                        <md-button ng-disabled="globalConfigurationForm.$invalid" ng-click="saveGlobalCOnfiguration()"
                                                   class="md-raised md-primary">Anrejistre
                                        </md-button>
                                    </md-card-actions>
                                </md-card>
                            </div>
                        </div>

                    </form>
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
<script type="text/javascript">
    $('.selectpicker').selectpicker();
</script>

<script type="text/javascript">
    $('.timepicker').timepicker();
</script>


</body>

</html>