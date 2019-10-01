<!DOCTYPE html>
<html lang="en">
  <head>
  <#include "header.ftl">

    <style>
      .flex-container {
        display: flex;
        background-color: DodgerBlue;
      }

      .flex-container > div {
        background-color: #f1f1f1;
        margin: 2px;
        padding: 20px;
        font-size: 14px;
      }
    </style>
  </head>

  <body ng-app="lottery" ng-cloak>
    <!-- container section start -->
    <section id="container" ng-controller="appController">
      <!--nav start-->
      <#include "nav.ftl" >
      <!--header end-->

      <!--sidebar start-->
      <aside>
        <#include "sidebar.ftl">
      </aside>
      <!--sidebar end-->


      <section id="main-content" >
      <!--main content start-->
        <section class="wrapper">
          <!--overview start-->
          <div class="row">
            <div class="col-lg-12">
              <h3 class="page-header">
                <i class="fa fa-laptop"></i> Byen Vini
              </h3>
              <ol class="breadcrumb">
                <li><i class="fa fa-home"></i><a href="index.ftl">Paj Akèy</a></li>
                <li><i class="fa fa-laptop"></i>Dashboard</li>
              </ol>
            </div>
          </div>

          <div class="row">
            <div class="col-lg-4 col-md-4 col-sm-12 col-xs-12">
              <div class="info-box dark-bg tooltips" data-placement="top" data-toggle="tooltip" data-original-title="Lè Sèvè a">
                <i class="fa fa-clock-o"></i>
                <div class="count">{{global.systemDate | date:'hh:mm:ss a'}}</div>
                <div class="title">{{global.systemDate  | date:'dd MMM yyyy'}}</div>
              </div>
              <!--/.info-box-->
            </div>
            <!--/.col-->
              <#if user??>
                <#list user.roles as rol>
                  <#if rol.name=="ROLE_ADMIN" || rol.name=="ROLE_SUPER_ADMIN">
                  <div class="col-lg-4 col-md-4 col-sm-12 col-xs-12" >
                    <section class="panel info-box tooltips" data-placement="top" data-toggle="tooltip" data-original-title="Denye Tiraj la">
                      <span style="color: darkblue; font-weight: bold; font-size: 16px">Denye Tiraj la</span>
                      <#if draw??>
                        <div class="row">
                            <div class="col-md-4 count facebook-bg text-center"><span class="label label-danger">${draw.numberThreeDigits.numberInStringFormat}</span></div>
                            <div class="col-md-4 count linkedin-bg text-center"><span class="label label-warning">${draw.secondDraw.numberInStringFormat}</span></div>
                            <div class="col-md-4 count facebook-bg text-center"><span class="label label-warning">${draw.thirdDraw.numberInStringFormat}</span></div>
                          </div>
                        <#else>
                          <p style="font-family: 'Lato, sans-serif'; color: red">Ouuppsss! Denye Tiraj la poko egziste ajoutel kounya!</p>
                      </#if>
                    </section>
                  </div>
                  </#if>
                </#list>
              </#if>
            <#if user??>
              <#list user.roles as rol>
                <#if rol.name=="ROLE_ADMIN" || rol.name=="ROLE_SUPER_ADMIN">
                  <div class="col-lg-4 col-md-4 col-sm-12 col-xs-12">
                    <section class="panel info-box" style="overflow-y: auto; max-height: 100px" data-placement="top" data-toggle="tooltip" data-original-title="Konbinezon ki bloke yo">
                      <p style="color: darkblue; font-weight: bold; font-size: 16px">Konbinezon ki bloke yo</p>
                      <div >
                        <p ng-if="global.blockedCombinations.length <= 0" style="font-family: 'Lato, sans-serif'; color: red">Pa gen konbinezn ki bloke kounya</p>

                        <span ng-repeat="combination in global.blockedCombinations track by $index">
                          <span ng-if="combination.combinationType.products.id === 1" class="label label-info" style="font-size: 1em;vertical-align: middle">{{combination.resultCombination}} <span style="margin-left: 10px;" class="label label-danger"> Bolet</span></span>
                          <span ng-if="combination.combinationType.products.id === 2" class="label label-info" style="font-size: 1em;vertical-align: middle">{{combination.resultCombination}}<span style="margin-left: 10px;" class="label label-success"> Loto 3 chif</span></span>
                          <span ng-if="combination.combinationType.products.id === 3" class="label label-info" style="font-size: 1em;vertical-align: middle">{{combination.resultCombination}} <span style="margin-left: 10px;" class="label label-default"> Loto 4 chif</span></span>
                          <span ng-if="combination.combinationType.products.id === 4" class="label label-info" style="font-size: 1em;vertical-align: middle">{{combination.resultCombination}}<span style="margin-left: 10px;" class="label label-default"> Opsyon</span></span>
                          <span ng-if="combination.combinationType.products.id === 5" class="label label-info" style="font-size: 1em;vertical-align: middle">{{combination.resultCombination}}<span style="margin-left: 10px;" class="label label-warning"> Maryaj</span></span>
                          <span ng-if="combination.combinationType.products.id === 6" class="label label-info" style="font-size: 1em;vertical-align: middle">{{combination.resultCombination}}<span style="margin-left: 10px;" class="label label-primary"> Extra</span></span>
                        </span>

                      </div>
                    </section>
                  </div>
                </#if>
              </#list>
            </#if>
          </div>

          <!--/.row-->
            <#if user??>
                <#list user.roles as rol>
                    <#if rol.name=="ROLE_ADMIN" || rol.name=="ROLE_SUPER_ADMIN">
                      <div class="row">
                        <div class="col-md-12 col-lg-3 col-xl-3 col-sm-12 col-xs-12">
                          <div class="info-box brown-bg tooltips" data-placement="top" style="max-height: 300px; overflow-y: auto;" data-toggle="tooltip" data-original-title="List Itilizatè ki konekte yo">
                            <span style="color: darkblue; font-weight: bold; font-size: 16px">List itilizatè ki konekte yo</span>
                            <ul class="list-group" ng-if="global.users.length > 0" style="color: #263a4f;">
                              <li class="list-group-item" ng-repeat="user in global.users track by $index">{{user.value1}}</li>
                            </ul>
                            <p ng-if="global.users.length <= 0" style="mso-font-info: serif">Pa gen vandè ki konekte kounya!!</p>
                          </div>
                        </div>
                        <div class="col-lg-6 col-md-12 col-sm-12 col-xs-12">
                          <div class="panel panel-default">
                            <div class="panel-heading">
                              List boul ki vann plis pou jounen an
                            </div>
                            <div class="panel-body">
                              <div class="row">
                                <div class="col-md-4 col-lg-4 col-xl-4">
                                  <div class="list-group">
                                    <a href="#" class="list-group-item purple-bg text-center">
                                      <h3 class="list-group-item-heading">Bolet</h3>
                                    </a>
                                      <a href="#" class="list-group-item list-group-item-info" ng-if="!global.topSoldCombinations['BOLET'] || global.topSoldCombinations['BOLET'].length < 0">
                                          <span class="list-group-item-text">Poko gen bolet ki vann pou tiraj sa</span>
                                      </a>
                                    <a href="#" class="list-group-item list-group-item-info" ng-repeat="item in global.topSoldCombinations['BOLET'] track by $index">
                                      <spap class="list-group-item-text">{{item.result_combination}}</spap>
                                      <span class="badge badge-success badge-pill"><small>{{item.sale_total}} HTG</small></span>
                                    </a>
                                  </div>
                                </div>
                                <div class="col-md-4 col-lg-4 col-xl-4">
                                  <div class="list-group">
                                    <a href="#" class="list-group-item facebook-bg text-center">
                                      <h3 class="list-group-item-heading">Maryaj</h3>
                                    </a>
                                      <a href="#" class="list-group-item list-group-item-info" ng-if="!global.topSoldCombinations['MARYAJ'] || global.topSoldCombinations['MARYAJ'].length < 0">
                                          <span class="list-group-item-text">Poko gen maryaj ki vann pou tiraj sa</span>
                                      </a>
                                    <a href="#" class="list-group-item list-group-item-info" ng-repeat="item in global.topSoldCombinations['MARYAJ'] track by $index">
                                        <span class="list-group-item-text">{{item.result_combination}}</span>
                                        <span class="badge badge-success badge-pill"><small>{{item.sale_total}} HTG</small></span>
                                    </a>
                                  </div>
                                </div>
                                 <div class="col-md-4 col-lg-4 col-xl-4">
                                  <div class="list-group">
                                    <a href="#" class="list-group-item magenta-bg text-center">
                                      <h3 class="list-group-item-heading">Loto 3 chif</h3>
                                    </a>
                                      <a href="#" class="list-group-item list-group-item-info" ng-if="!global.topSoldCombinations['LOTO_TWA_CHIF'] || global.topSoldCombinations['LOTO_TWA_CHIF'].length < 0">
                                          <span class="list-group-item-text">Poko gen extra ki vann pou tiraj sa</span>
                                      </a>
                                    <a href="#" class="list-group-item list-group-item-info" ng-repeat="item in global.topSoldCombinations['LOTO_TWA_CHIF'] track by $index">
                                        <span class="list-group-item-text">{{item.result_combination}}</span>
                                        <span class="badge badge-success badge-pill"><small>{{item.sale_total}} HTG</small></span>
                                    </a>
                                  </div>
                                </div>
                              </div>
                              <div class="row">
                                <div class="col-md-4 col-lg-4 col-xl-4">
                                  <div class="list-group">
                                    <a href="#" class="list-group-item purple-bg text-center">
                                      <h3 class="list-group-item-heading">Loto 4 chif</h3>
                                    </a>
                                      <a href="#" class="list-group-item list-group-item-info" ng-if="!global.topSoldCombinations['LOTO_KAT_CHIF'] || global.topSoldCombinations['LOTO_KAT_CHIF'].length < 0">
                                          <span class="list-group-item-text">Poko gen loto 4 chif ki vann pou tiraj sa</span>
                                      </a>
                                    <a href="#" class="list-group-item list-group-item-info" ng-repeat="item in global.topSoldCombinations['LOTO_KAT_CHIF'] track by $index">
                                        <span class="list-group-item-text">{{item.result_combination}}</span>
                                        <span class="badge badge-success badge-pill"><small>{{item.sale_total}} HTG</small></span>
                                    </a>
                                  </div>
                                </div>
                                <div class="col-md-4 col-lg-4 col-xl-4">
                                  <div class="list-group">
                                    <a href="#" class="list-group-item facebook-bg text-center">
                                      <h3 class="list-group-item-heading">Extra</h3>
                                    </a>
                                      <a href="#" class="list-group-item list-group-item-info" ng-if="!global.topSoldCombinations['EXTRA'] || global.topSoldCombinations['EXTRA'].length < 0">
                                          <span class="list-group-item-text">Poko gen extra ki vann pou tiraj sa</span>
                                      </a>
                                    <a href="#" class="list-group-item list-group-item-info" ng-repeat="item in global.topSoldCombinations['EXTRA'] track by $index">
                                      <spap class="list-group-item-text">{{item.result_combination}}</spap>
                                      <span class="badge badge-success badge-pill"><small>{{item.sale_total}} HTG</small></span>
                                    </a>
                                  </div>
                                </div>
                                 <div class="col-md-4 col-lg-4 col-xl-4">
                                  <div class="list-group">
                                    <a href="#" class="list-group-item magenta-bg text-center">
                                      <h3 class="list-group-item-heading">Opsyon</h3>
                                    </a>
                                      <a href="#" class="list-group-item list-group-item-info" ng-if="!global.topSoldCombinations['OPSYON'] || global.topSoldCombinations['OPSYON'].length < 0">
                                          <span class="list-group-item-text">Poko gen opsyon ki vann pou tiraj sa</span>
                                      </a>
                                    <a href="#" class="list-group-item list-group-item-info" ng-repeat="item in global.topSoldCombinations['OPSYON'] track by $index">
                                        <span class="list-group-item-text">{{item.result_combination}}</span>
                                        <span class="badge badge-success badge-pill"><small>{{item.sale_total}} HTG</small></span>
                                    </a>
                                  </div>
                                </div>
                              </div>
                            </div>
                          </div>
                        </div>
                        <div class="col-md-12 col-lg-3 col-xl-3 col-sm-12 col-xs-12">
                          <div class="info-box teal-bg tooltips" data-placement="top" style="max-height: 300px; overflow-y: auto;" data-toggle="tooltip" data-original-title="Konbinezon ki rive nan limit pri yo">
                            <span style="color: darkblue; font-weight: bold; font-size: 16px">Konbinezon ki rive nan limit pri yo</span>
                              <p ng-if="global.combinationsLimited.length <= 0" style="mso-font-info: serif">Pa gen konbinezon ki rive nan limit li kounya</p>
                            <ul class="list-group" style="color: #263a4f;">
                              <li class="list-group-item" ng-repeat="item in global.combinationsLimited track by $index">
                                <span class='lead label label-info' style="font-size: 14px">{{item.combination}}</span>
                                <span style="float: right; font-family: 'serif'; color: #009da7;" >{{item.type}}</span>
                              </li>
                            </ul>
                          </div>
                        </div>
                      </div>
                    </#if>
                </#list>
            </#if>

        </section>
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
      <!--main content end-->
    </section>
    <!-- container section start -->
    <#include "scripts.ftl">

  </body>
</html>
