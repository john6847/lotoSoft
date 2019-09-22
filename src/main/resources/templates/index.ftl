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
              <!--/.info-box-->
          </div>
            <!--/.col-->

<#--            <div class="col-lg-3 col-md-3 col-sm-12 col-xs-12">-->
<#--              <div class="info-box brown-bg">-->
<#--                <i class="fa fa-shopping-cart"></i>-->
<#--                <div class="count">7.538</div>-->
<#--                <div class="title">Purchased</div>-->
<#--              </div>-->
<#--              <!--/.info-box&ndash;&gt;-->
<#--            </div>-->
            <!--/.col-->


<#--            <div class="col-lg-3 col-md-3 col-sm-12 col-xs-12">-->
<#--              <div class="info-box green-bg">-->
<#--                <i class="fa fa-cubes"></i>-->
<#--                <div class="count">1.426</div>-->
<#--                <div class="title">Stock</div>-->
<#--              </div>-->
<#--              <!--/.info-box&ndash;&gt;-->
<#--            </div>-->
<#--            <!--/.col&ndash;&gt;-->
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
                                      <span class="badge badge-success badge-pill"><small>{{item.max_price}} HTG</small></span>
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
                                        <span class="badge badge-success badge-pill"><small>{{item.max_price}} HTG</small></span>
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
                                        <span class="badge badge-success badge-pill"><small>{{item.max_price}} HTG</small></span>
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
                                        <span class="badge badge-success badge-pill"><small>{{item.max_price}} HTG</small></span>
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
                                      <span class="badge badge-success badge-pill"><small>{{item.max_price}} HTG</small></span>
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
                                        <span class="badge badge-success badge-pill"><small>{{item.max_price}} HTG</small></span>
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

          <!-- Today status end -->

          <div class="row">
            <div class="col-lg-9 col-md-12">
              <div class="panel panel-default">
                <div class="panel-heading">
                  <h2>
                    <i class="fa fa-flag-o red"></i
                    ><strong>Registered Users</strong>
                  </h2>
                  <div class="panel-actions">
                    <a href="index.ftl#" class="btn-setting"
                      ><i class="fa fa-rotate-right"></i
                    ></a>
                    <a href="index.ftl#" class="btn-minimize"
                      ><i class="fa fa-chevron-up"></i
                    ></a>
                    <a href="index.ftl#" class="btn-close"
                      ><i class="fa fa-times"></i
                    ></a>
                  </div>
                </div>
                <div class="panel-body">
                  <table class="table bootstrap-datatable countries">
                    <thead>
                      <tr>
                        <th></th>
                        <th>Country</th>
                        <th>Users</th>
                        <th>Online</th>
                        <th>Performance</th>
                      </tr>
                    </thead>
                    <tbody>
                      <tr>
                        <td>
                          <img
                            src="img/Germany.png"
                            style="height:18px; margin-top:-2px;"
                          />
                        </td>
                        <td>Germany</td>
                        <td>2563</td>
                        <td>1025</td>
                        <td>
                          <div class="progress thin">
                            <div
                              class="progress-bar progress-bar-danger"
                              role="progressbar"
                              aria-valuenow="73"
                              aria-valuemin="0"
                              aria-valuemax="100"
                              style="width: 73%"
                            ></div>
                            <div
                              class="progress-bar progress-bar-warning"
                              role="progressbar"
                              aria-valuenow="27"
                              aria-valuemin="0"
                              aria-valuemax="100"
                              style="width: 27%"
                            ></div>
                          </div>
                          <span class="sr-only">73%</span>
                        </td>
                      </tr>
                      <tr>
                        <td>
                          <img
                            src="img/India.png"
                            style="height:18px; margin-top:-2px;"
                          />
                        </td>
                        <td>India</td>
                        <td>3652</td>
                        <td>2563</td>
                        <td>
                          <div class="progress thin">
                            <div
                              class="progress-bar progress-bar-danger"
                              role="progressbar"
                              aria-valuenow="57"
                              aria-valuemin="0"
                              aria-valuemax="100"
                              style="width: 57%"
                            ></div>
                            <div
                              class="progress-bar progress-bar-warning"
                              role="progressbar"
                              aria-valuenow="43"
                              aria-valuemin="0"
                              aria-valuemax="100"
                              style="width: 43%"
                            ></div>
                          </div>
                          <span class="sr-only">57%</span>
                        </td>
                      </tr>
                      <tr>
                        <td>
                          <img
                            src="img/Spain.png"
                            style="height:18px; margin-top:-2px;"
                          />
                        </td>
                        <td>Spain</td>
                        <td>562</td>
                        <td>452</td>
                        <td>
                          <div class="progress thin">
                            <div
                              class="progress-bar progress-bar-danger"
                              role="progressbar"
                              aria-valuenow="93"
                              aria-valuemin="0"
                              aria-valuemax="100"
                              style="width: 93%"
                            ></div>
                            <div
                              class="progress-bar progress-bar-warning"
                              role="progressbar"
                              aria-valuenow="7"
                              aria-valuemin="0"
                              aria-valuemax="100"
                              style="width: 7%"
                            ></div>
                          </div>
                          <span class="sr-only">93%</span>
                        </td>
                      </tr>
                      <tr>
                        <td>
                          <img
                            src="img/India.png"
                            style="height:18px; margin-top:-2px;"
                          />
                        </td>
                        <td>Russia</td>
                        <td>1258</td>
                        <td>958</td>
                        <td>
                          <div class="progress thin">
                            <div
                              class="progress-bar progress-bar-danger"
                              role="progressbar"
                              aria-valuenow="20"
                              aria-valuemin="0"
                              aria-valuemax="100"
                              style="width: 20%"
                            ></div>
                            <div
                              class="progress-bar progress-bar-warning"
                              role="progressbar"
                              aria-valuenow="80"
                              aria-valuemin="0"
                              aria-valuemax="100"
                              style="width: 80%"
                            ></div>
                          </div>
                          <span class="sr-only">20%</span>
                        </td>
                      </tr>
                      <tr>
                        <td>
                          <img
                            src="img/Spain.png"
                            style="height:18px; margin-top:-2px;"
                          />
                        </td>
                        <td>USA</td>
                        <td>4856</td>
                        <td>3621</td>
                        <td>
                          <div class="progress thin">
                            <div
                              class="progress-bar progress-bar-danger"
                              role="progressbar"
                              aria-valuenow="20"
                              aria-valuemin="0"
                              aria-valuemax="100"
                              style="width: 20%"
                            ></div>
                            <div
                              class="progress-bar progress-bar-warning"
                              role="progressbar"
                              aria-valuenow="80"
                              aria-valuemin="0"
                              aria-valuemax="100"
                              style="width: 80%"
                            ></div>
                          </div>
                          <span class="sr-only">20%</span>
                        </td>
                      </tr>
                      <tr>
                        <td>
                          <img
                            src="img/Germany.png"
                            style="height:18px; margin-top:-2px;"
                          />
                        </td>
                        <td>Brazil</td>
                        <td>265</td>
                        <td>102</td>
                        <td>
                          <div class="progress thin">
                            <div
                              class="progress-bar progress-bar-danger"
                              role="progressbar"
                              aria-valuenow="20"
                              aria-valuemin="0"
                              aria-valuemax="100"
                              style="width: 20%"
                            ></div>
                            <div
                              class="progress-bar progress-bar-warning"
                              role="progressbar"
                              aria-valuenow="80"
                              aria-valuemin="0"
                              aria-valuemax="100"
                              style="width: 80%"
                            ></div>
                          </div>
                          <span class="sr-only">20%</span>
                        </td>
                      </tr>
                      <tr>
                        <td>
                          <img
                            src="img/Germany.png"
                            style="height:18px; margin-top:-2px;"
                          />
                        </td>
                        <td>Coloumbia</td>
                        <td>265</td>
                        <td>102</td>
                        <td>
                          <div class="progress thin">
                            <div
                              class="progress-bar progress-bar-danger"
                              role="progressbar"
                              aria-valuenow="20"
                              aria-valuemin="0"
                              aria-valuemax="100"
                              style="width: 20%"
                            ></div>
                            <div
                              class="progress-bar progress-bar-warning"
                              role="progressbar"
                              aria-valuenow="80"
                              aria-valuemin="0"
                              aria-valuemax="100"
                              style="width: 80%"
                            ></div>
                          </div>
                          <span class="sr-only">20%</span>
                        </td>
                      </tr>
                      <tr>
                        <td>
                          <img
                            src="img/Germany.png"
                            style="height:18px; margin-top:-2px;"
                          />
                        </td>
                        <td>France</td>
                        <td>265</td>
                        <td>102</td>
                        <td>
                          <div class="progress thin">
                            <div
                              class="progress-bar progress-bar-danger"
                              role="progressbar"
                              aria-valuenow="20"
                              aria-valuemin="0"
                              aria-valuemax="100"
                              style="width: 20%"
                            ></div>
                            <div
                              class="progress-bar progress-bar-warning"
                              role="progressbar"
                              aria-valuenow="80"
                              aria-valuemin="0"
                              aria-valuemax="100"
                              style="width: 80%"
                            ></div>
                          </div>
                          <span class="sr-only">20%</span>
                        </td>
                      </tr>
                    </tbody>
                  </table>
                </div>
              </div>
            </div>
            <!--/col-->
            <div class="col-md-3">
              <div class="social-box facebook">
                <i class="fa fa-facebook"></i>
                <ul>
                  <li>
                    <strong>256k</strong>
                    <span>friends</span>
                  </li>
                  <li>
                    <strong>359</strong>
                    <span>feeds</span>
                  </li>
                </ul>
              </div>
              <!--/social-box-->
            </div>
            <div class="col-md-3">
              <div class="social-box google-plus">
                <i class="fa fa-google-plus"></i>
                <ul>
                  <li>
                    <strong>962</strong>
                    <span>followers</span>
                  </li>
                  <li>
                    <strong>256</strong>
                    <span>circles</span>
                  </li>
                </ul>
              </div>
              <!--/social-box-->
            </div>
            <!--/col-->
            <div class="col-md-3">
              <div class="social-box twitter">
                <i class="fa fa-twitter"></i>
                <ul>
                  <li>
                    <strong>1562k</strong>
                    <span>followers</span>
                  </li>
                  <li>
                    <strong>2562</strong>
                    <span>tweets</span>
                  </li>
                </ul>
              </div>
              <!--/social-box-->
            </div>
            <!--/col-->
          </div>

          <!-- statics end -->

          <!-- project team & activity start -->
          <div class="row">
            <div class="col-md-4 portlets">
              <!-- Widget -->
              <div class="panel panel-default">
                <div class="panel-heading">
                  <div class="pull-left">Message</div>
                  <div class="widget-icons pull-right">
                    <a href="#" class="wminimize"
                      ><i class="fa fa-chevron-up"></i
                    ></a>
                    <a href="#" class="wclose"><i class="fa fa-times"></i></a>
                  </div>
                  <div class="clearfix"></div>
                </div>

                <div class="panel-body">
                  <!-- Widget content -->
                  <div class="padd sscroll">
                    <ul class="chats">
                      <!-- Chat by us. Use the class "by-me". -->
                      <li class="by-me">
                        <!-- Use the class "pull-left" in avatar -->
                        <div class="avatar pull-left">
                          <img src="img/user.jpg" alt="" />
                        </div>

                        <div class="chat-content">
                          <!-- In meta area, first include "name" and then "time" -->
                          <div class="chat-meta">
                            John Smith
                            <span class="pull-right">3 hours ago</span>
                          </div>
                          Vivamus diam elit diam, consectetur dapibus adipiscing
                          elit.
                          <div class="clearfix"></div>
                        </div>
                      </li>

                      <!-- Chat by other. Use the class "by-other". -->
                      <li class="by-other">
                        <!-- Use the class "pull-right" in avatar -->
                        <div class="avatar pull-right">
                          <img src="img/user22.png" alt="" />
                        </div>

                        <div class="chat-content">
                          <!-- In the chat meta, first include "time" then "name" -->
                          <div class="chat-meta">
                            3 hours ago
                            <span class="pull-right">Jenifer Smith</span>
                          </div>
                          Vivamus diam elit diam, consectetur fconsectetur
                          dapibus adipiscing elit.
                          <div class="clearfix"></div>
                        </div>
                      </li>

                      <li class="by-me">
                        <div class="avatar pull-left">
                          <img src="img/user.jpg" alt="" />
                        </div>

                        <div class="chat-content">
                          <div class="chat-meta">
                            John Smith
                            <span class="pull-right">4 hours ago</span>
                          </div>
                          Vivamus diam elit diam, consectetur fermentum sed
                          dapibus eget, Vivamus consectetur dapibus adipiscing
                          elit.
                          <div class="clearfix"></div>
                        </div>
                      </li>

                      <li class="by-other">
                        <!-- Use the class "pull-right" in avatar -->
                        <div class="avatar pull-right">
                          <img src="img/user22.png" alt="" />
                        </div>

                        <div class="chat-content">
                          <!-- In the chat meta, first include "time" then "name" -->
                          <div class="chat-meta">
                            3 hours ago
                            <span class="pull-right">Jenifer Smith</span>
                          </div>
                          Vivamus diam elit diam, consectetur fermentum sed
                          dapibus eget, Vivamus consectetur dapibus adipiscing
                          elit.
                          <div class="clearfix"></div>
                        </div>
                      </li>
                    </ul>
                  </div>
                  <!-- Widget footer -->
                  <div class="widget-foot">
                    <form class="form-inline">
                      <div class="form-group">
                        <input
                          type="text"
                          class="form-control"
                          placeholder="Type your message here..."
                        />
                      </div>
                      <button type="submit" class="btn btn-info">Send</button>
                    </form>
                  </div>
                </div>
              </div>
            </div>

            <div class="col-lg-8">
              <!--Project Activity start-->
              <section class="panel">
                <div class="panel-body progress-panel">
                  <div class="row">
                    <div class="col-lg-8 task-progress pull-left">
                      <h1>To Do Everyday</h1>
                    </div>
                    <div class="col-lg-4">
                      <span class="profile-ava pull-right">
                        <img
                          alt=""
                          class="simple"
                          src="img/avatar1_small.jpg"
                        />
                        Jenifer smith
                      </span>
                    </div>
                  </div>
                </div>
                <table class="table table-hover personal-task">
                  <tbody>
                    <tr>
                      <td>Today</td>
                      <td>
                        web design
                      </td>
                      <td>
                        <span class="badge bg-important">Upload</span>
                      </td>
                      <td>
                        <span class="profile-ava">
                          <img
                            alt=""
                            class="simple"
                            src="img/avatar1_small.jpg"
                          />
                        </span>
                      </td>
                    </tr>
                    <tr>
                      <td>Yesterday</td>
                      <td>
                        Project Design Task
                      </td>
                      <td>
                        <span class="badge bg-success">Task</span>
                      </td>
                      <td>
                        <div id="work-progress2"></div>
                      </td>
                    </tr>
                    <tr>
                      <td>21-10-14</td>
                      <td>
                        Generate Invoice
                      </td>
                      <td>
                        <span class="badge bg-success">Task</span>
                      </td>
                      <td>
                        <div id="work-progress3"></div>
                      </td>
                    </tr>
                    <tr>
                      <td>22-10-14</td>
                      <td>
                        Project Testing
                      </td>
                      <td>
                        <span class="badge bg-primary">To-Do</span>
                      </td>
                      <td>
                        <span class="profile-ava">
                          <img
                            alt=""
                            class="simple"
                            src="img/avatar1_small.jpg"
                          />
                        </span>
                      </td>
                    </tr>
                    <tr>
                      <td>24-10-14</td>
                      <td>
                        Project Release Date
                      </td>
                      <td>
                        <span class="badge bg-info">Milestone</span>
                      </td>
                      <td>
                        <div id="work-progress4"></div>
                      </td>
                    </tr>
                    <tr>
                      <td>28-10-14</td>
                      <td>
                        Project Release Date
                      </td>
                      <td>
                        <span class="badge bg-primary">To-Do</span>
                      </td>
                      <td>
                        <div id="work-progress5"></div>
                      </td>
                    </tr>
                    <tr>
                      <td>Last week</td>
                      <td>
                        Project Release Date
                      </td>
                      <td>
                        <span class="badge bg-primary">To-Do</span>
                      </td>
                      <td>
                        <div id="work-progress1"></div>
                      </td>
                    </tr>
                    <tr>
                      <td>last month</td>
                      <td>
                        Project Release Date
                      </td>
                      <td>
                        <span class="badge bg-success">To-Do</span>
                      </td>
                      <td>
                        <span class="profile-ava">
                          <img
                            alt=""
                            class="simple"
                            src="img/avatar1_small.jpg"
                          />
                        </span>
                      </td>
                    </tr>
                  </tbody>
                </table>
              </section>
              <!--Project Activity end-->
            </div>
          </div>
          <br /><br />

          <div class="row">
            <div class="col-md-6 portlets">
              <div class="panel panel-default">
                <div class="panel-heading">
                  <h2><strong>Calendar</strong></h2>
                  <div class="panel-actions">
                    <a href="#" class="wminimize"
                      ><i class="fa fa-chevron-up"></i
                    ></a>
                    <a href="#" class="wclose"><i class="fa fa-times"></i></a>
                  </div>
                </div>
                <br /><br /><br />
                <div class="panel-body">
                  <!-- Widget content -->

                  <!-- Below line produces calendar. I am using FullCalendar plugin. -->
                  <div id="calendar"></div>
                </div>
              </div>
            </div>

            <div class="col-md-6 portlets">
              <div class="panel panel-default">
                <div class="panel-heading">
                  <div class="pull-left">Quick Post</div>
                  <div class="widget-icons pull-right">
                    <a href="#" class="wminimize"
                      ><i class="fa fa-chevron-up"></i
                    ></a>
                    <a href="#" class="wclose"><i class="fa fa-times"></i></a>
                  </div>
                  <div class="clearfix"></div>
                </div>
                <div class="panel-body">
                  <div class="padd">
                    <div class="form quick-post">
                      <!-- Edit profile form (not working)-->
                      <form class="form-horizontal">
                        <!-- Title -->
                        <div class="form-group">
                          <label class="control-label col-lg-2" for="title"
                            >Title</label
                          >
                          <div class="col-lg-10">
                            <input
                              type="text"
                              class="form-control"
                              id="title"
                            />
                          </div>
                        </div>
                        <!-- Content -->
                        <div class="form-group">
                          <label class="control-label col-lg-2" for="content"
                            >Content</label
                          >
                          <div class="col-lg-10">
                            <textarea
                              class="form-control"
                              id="content"
                            ></textarea>
                          </div>
                        </div>
                        <!-- Cateogry -->
                        <div class="form-group">
                          <label class="control-label col-lg-2">Category</label>
                          <div class="col-lg-10">
                            <select class="form-control">
                              <option value="">- Choose Cateogry -</option>
                              <option value="1">General</option>
                              <option value="2">News</option>
                              <option value="3">Media</option>
                              <option value="4">Funny</option>
                            </select>
                          </div>
                        </div>
                        <!-- Tags -->
                        <div class="form-group">
                          <label class="control-label col-lg-2" for="tags"
                            >Tags</label
                          >
                          <div class="col-lg-10">
                            <input type="text" class="form-control" id="tags" />
                          </div>
                        </div>

                        <!-- Buttons -->
                        <div class="form-group">
                          <!-- Buttons -->
                          <div class="col-lg-offset-2 col-lg-9">
                            <button type="submit" class="btn btn-primary">
                              Publish
                            </button>
                            <button type="submit" class="btn btn-danger">
                              Save Draft
                            </button>
                            <button type="reset" class="btn btn-default">
                              Reset
                            </button>
                          </div>
                        </div>
                      </form>
                    </div>
                  </div>
                  <div class="widget-foot">
                    <!-- Footer goes here -->
                  </div>
                </div>
              </div>
            </div>
          </div>
          <!-- project team & activity end -->
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

    <!-- javascripts -->
<#--    <script src="js/jquery.js"></script>-->
<#--    <script src="js/jquery-ui-1.10.4.min.js"></script>-->
<#--    <script src="js/jquery-1.8.3.min.js"></script>-->
<#--    <script-->
<#--      type="text/javascript"-->
<#--      src="js/jquery-ui-1.9.2.custom.min.js"-->
<#--    ></script>-->
<#--    <!-- bootstrap &ndash;&gt;-->
<#--    <script src="js/bootstrap.min.js"></script>-->
<#--    <!-- nice scroll &ndash;&gt;-->
<#--    <script src="js/jquery.scrollTo.min.js"></script>-->
<#--    <script src="js/jquery.nicescroll.js" type="text/javascript"></script>-->
<#--    <!-- charts scripts &ndash;&gt;-->
<#--    <script src="assets/jquery-knob/js/jquery.knob.js"></script>-->
<#--    <script src="js/jquery.sparkline.js" type="text/javascript"></script>-->
<#--    <script src="assets/jquery-easy-pie-chart/jquery.easy-pie-chart.js"></script>-->
<#--    <script src="js/owl.carousel.js"></script>-->
<#--    <!-- jQuery full calendar &ndash;&gt;-->
<#--    <<script src="js/fullcalendar.min.js"></script>-->
<#--    <!-- Full Google Calendar - Calendar &ndash;&gt;-->
<#--    <script src="assets/fullcalendar/fullcalendar/fullcalendar.js"></script>-->
<#--    <!--script for this page only&ndash;&gt;-->
<#--    <script src="js/calendar-custom.js"></script>-->
<#--    <script src="js/jquery.rateit.min.js"></script>-->
<#--    <!-- custom select &ndash;&gt;-->
<#--    <script src="js/jquery.customSelect.min.js"></script>-->
<#--    <script src="assets/chart-master/Chart.js"></script>-->

<#--    <!--custome script for all page&ndash;&gt;-->
<#--    <script src="js/scripts.js"></script>-->
<#--    <!-- custom script for this page&ndash;&gt;-->
<#--    <script src="js/sparkline-chart.js"></script>-->
<#--    <script src="js/easy-pie-chart.js"></script>-->
<#--    <script src="js/jquery-jvectormap-1.2.2.min.js"></script>-->
<#--    <script src="js/jquery-jvectormap-world-mill-en.js"></script>-->
<#--    <script src="js/xcharts.min.js"></script>-->
<#--    <script src="js/jquery.autosize.min.js"></script>-->
<#--    <script src="js/jquery.placeholder.min.js"></script>-->
<#--    <script src="js/gdp-data.js"></script>-->
<#--    <script src="js/morris.min.js"></script>-->
<#--    <script src="js/sparklines.js"></script>-->
<#--    <script src="js/charts.js"></script>-->
<#--    <script src="js/jquery.slimscroll.min.js"></script>-->

    <#include "scripts.ftl">
<#--    <script>-->

<#--      //knob-->
<#--      $(function() {-->
<#--        $(".knob").knob({-->
<#--          draw: function() {-->
<#--            $(this.i).val(this.cv + "%");-->
<#--          }-->
<#--        });-->
<#--      });-->

<#--      //carousel-->
<#--      $(document).ready(function() {-->
<#--        $("#owl-slider").owlCarousel({-->
<#--          navigation: true,-->
<#--          slideSpeed: 300,-->
<#--          paginationSpeed: 400,-->
<#--          singleItem: true-->
<#--        });-->
<#--      });-->

<#--      //custom select box-->

<#--      $(function() {-->
<#--        $("select.styled").customSelect();-->
<#--      });-->

<#--      /* ---------- Map ---------- */-->
<#--      $(function() {-->
<#--        $("#map").vectorMap({-->
<#--          map: "world_mill_en",-->
<#--          series: {-->
<#--            regions: [-->
<#--              {-->
<#--                values: gdpData,-->
<#--                scale: ["#000", "#000"],-->
<#--                normalizeFunction: "polynomial"-->
<#--              }-->
<#--            ]-->
<#--          },-->
<#--          backgroundColor: "#eef3f7",-->
<#--          onLabelShow: function(e, el, code) {-->
<#--            el.html(el.html() + " (GDP - " + gdpData[code] + ")");-->
<#--          }-->
<#--        });-->
<#--      });-->
<#--    </script>-->

  </body>
</html>
