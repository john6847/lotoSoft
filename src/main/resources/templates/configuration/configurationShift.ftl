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
    <section id="main-content" ng-controller="configurationController" >
      <section class="wrapper">
        <div class="row">
          <div class="col-lg-12">
            <h3 class="page-header"><i class="icon_cog"></i>Paj Pou Konfigirasyon Tip Tiraj</h3>
            <ol class="breadcrumb">
              <li><i class="fa fa-home"></i><a href="/home">Paj Akèy</a></li>
              <li><i class="icon_cog"></i>Konfigirasyon Tip Tiraj</li>
            </ol>
          </div>
        </div>
        <div class="row" ng-init="fetchAllShifts()">
          <div class="col-lg-12 col-xs-12 col-xl-12 col-md-12 col-sm-12" ng-if="shiftField.message">
            <div class="alert alert-success" role="alert" ng-if="shiftField.message.saved">{{shiftField.message.message}}</div>
            <div class="alert alert-danger" role="alert" ng-if="!shiftField.message.saved">{{shiftField.message.message}}</div>
          </div>
          <div class="col-lg-12">
            <section class="panel">
              <header class="panel-heading">
                Tip Tiraj
              </header>
              <div class="panel-body">
                <form name="shiftForm" class="form-horizontal">

                  <div class="form-group">
                    <div class="col-lg-6 col-md-6 col-sm-12 col-xs-12 col-md-offset-3 col-lg-offset-3">
                      <select class="form-control m-bot15 selectpicker"
                              name="shift"
                              id="shift"
                              data-size="5"
                              ng-change="shiftChange()"
                              ng-model="shiftField.selectedShift"
                              data-none-selected-text="Chwazi Tip Tiraj la"
                              ng-options="shift.name for shift in shiftField.shifts"
                              required>
                              <option ng-repeat="shift in shiftField.shifts track by shift.id" value="{{shift.id}}">
                              </option>
                      </select>
                    </div>
                  </div>


                  <div class="form-group" ng-if="shiftField.selectedShift">
                    <div class="col-lg-offset-2 col-sm-offset-2 col-md-offset-2 col-lg-8 col-xs-12 col-md-8 col-sm-8">
                      <md-card md-theme-watch>
                        <md-card-title layout-align="start center">
                          <md-card-title-text>
                            <span class="md-headline">Chanje Lè Tiraj {{shiftField.selectedShift.name}}</span>
                          </md-card-title-text>

                        </md-card-title>

                        <md-card-content layout-align="start center" ng-cloak>
                            <div layout-gt-xs="row" style="margin-right: 10px;">
                                <div flex-gt-xs>
                                    <h4>Lè ouvèti</h4>
                                    <div class="input-group">
                                        <input id="ouveti"
                                               class="form-control"
                                               name="openTime"
                                               ng-model="shiftField.selectedShift.openTime"
                                               placeholder="HH:mm:ss"
                                               min="05:00:00"
                                               max="20:00:00"
                                               required
                                               type="time"/>
                                        <span class="input-group-addon"><i class="fa fa-clock-o"></i></span>
                                    </div>
                                    <span class="alert alert-danger error" ng-show="shiftForm.openTime.$dirty && shiftForm.openTime.$error.required">Rantre yon lè ouvèti!</span>
                                    <span class="alert alert-danger error" ng-show="shiftForm.openTime.$dirty && shiftForm.openTime.$error.time">Lè sa pa bon!</span>
                                </div>

                                <div flex-gt-xs>
                                    <h4>Le li fèmen</h4>
                                    <div class="input-group">
                                        <input id="ouveti"
                                               class="form-control"
                                               name="closeTime"
                                               ng-model="shiftField.selectedShift.closeTime"
                                               placeholder="HH:mm:ss"
                                               min="05:00:00"
                                               max="20:00:00"
                                               required
                                               type="time"/>
                                        <span class="input-group-addon"><i class="fa fa-clock-o"></i></span>
                                    </div>
                                    <span class="alert alert-danger error" role="alert" ng-show="shiftForm.closeTime.$dirty && shiftForm.closeTime.$error.required">Rantre yon lè femèti!</span>
                                    <span class="alert alert-danger error" role="alert" ng-show="shiftForm.closeTime.$dirty && shiftForm.closeTime.$error.time">Lè sa pa bon!</span>
                                </div>
                            </div>
                        </md-card-content>

                        <md-card-actions layout="row" layout-align="end center">
                            <md-button ng-disabled="shiftForm.$invalid" ng-click="saveShift()" class="md-raised md-primary">Anrejistre</md-button>
                        </md-card-actions>
                      </md-card>
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

<script type="text/javascript">
    $('.timepicker').timepicker();
    // $('#timepicker2').timepicker();
</script>



</body>

</html>