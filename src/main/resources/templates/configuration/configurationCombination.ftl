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
    <section id="main-content" ng-controller="configurationController"">
      <section class="wrapper" ng-init="init('combination')">
        <div class="row">
          <div class="col-lg-12">
            <h3 class="page-header"><i class="icon_cog"></i>Paj Pou Konfigire Konbinezon</h3>
            <ol class="breadcrumb">
              <li><i class="fa fa-home"></i><a href="/home">Paj Akèy</a></li>
              <li><i class="icon_cog"></i>Konfigirasyon Konbinezon</li>
            </ol>
          </div>
        </div>

        <div class="row">
          <div class="col-lg-12 col-xs-12 col-xl-12 col-md-12 col-sm-12" ng-if="combinationField.message">
            <div class="alert alert-success" role="alert" ng-if="combinationField.message.saved">{{combinationField.message.message}}</div>
            <div class="alert alert-danger" role="alert" ng-if="!combinationField.message.saved">{{combinationField.message.message}}</div>
           </div>
          <div class="col-lg-12 col-xs-12 col-xl-12 col-md-12 col-sm-12" ng-if="combinationGroupField.message">
            <div class="alert alert-success" role="alert" ng-if="combinationGroupField.message.saved">{{combinationGroupField.message.message}}</div>
            <div class="alert alert-danger" role="alert" ng-if="!combinationGroupField.message.saved">{{combinationGroupField.message.message}}</div>
          </div>
        </div>

        <div class="row">
          <div class="col-lg-12">
            <section class="panel">
              <header class="panel-heading">
                Konbinezon
              </header>
              <div class="panel-body">
                <form class="form-horizontal">

                <div class="form-group">
                  <div class="col-lg-3 col-md-3 col-sm-12 col-xs-12" layout-gt-md="row">
                      <md-input-container>
                        <label>Opsyon</label>
                        <md-select ng-model="selectType.type" ng-change="changeSelectType()">
                          <md-option ng-repeat="type in selectType.types track by type.id" ng-value="{{type.id}}">
                              {{type.name}}
                          </md-option>
                        </md-select>
                      </md-input-container>
                  </div>

                  <div class="col-lg-6 col-md-6 col-sm-12 col-xs-12" ng-if="selectType.type > 1">
                    <select class="form-control round-input"
                            name="type"
                            ng-model="combinationGroupField.selectedCombinationType"
                            ng-change="combinationTypeChange()"
                            id="type"
                            ng-options="type.products.name for type in combinationGroupField.combinationTypes">
                                <option value="" selected disabled="disabled">Chwazi Tip Konbinezon an</option>
                    </select>
                  </div>

                  <div class="col-lg-6 col-md-6 col-sm-12 col-xs-12" ng-if="selectType.type <= 1">
                    <div class="input-group">
                      <input type="text"
                             maxlength="7"
                             class="form-control round-input"
                             ng-model-options="{ debounce: 1000 }"
                             ng-model = "combinationField.combination"
                             ng-click="combinationField.showList = !combinationField.showList"
                             ng-change="combinationChange()"
                             placeholder="Cheche yon Konbinezon">
                      <span class="input-group-btn">
                        <button class="btn btn-default" ng-click="resetCombination()" type="button"><i class="fa fa-times" th:title="Efase"></i></button>
                      </span>
                    </div>
                    <div style="box-shadow: 5px 5px 5px grey" ng-if="combinationField.showList && combinationField.combinations">
                      <ul class="list-group">
                        <li class="list-group-item" style="padding: 0px 0px 0px 0px;" ng-repeat="comb in combinationField.combinations" ng-click="selectCombination(comb.id)">
                            <span class="row .bg-info">
                               <span class="col-md-4 col-sm-3 col-lg-4 col-xs-4"><span class="badge badge-primary">{{comb.resultCombination}}</span></span>
                               <span class="col-md-4 col-sm-3 col-lg-4 col-xs-4 lead" style="margin-bottom: 0px">{{comb.combinationType.products.name}}</span>
                               <span class="col-md-4 col-sm-3 col-lg-4 col-xs-4 text-right">
                                 <h5 class="label label-{{comb.enabled? 'success' : 'danger'}}">{{comb.enabled? 'Actif' : 'Bloke'}}</h5>
                               </span>
                            </span>
                        </li>
                      </ul>
                    </div>
                  </div>
                </div>


                  <div class="form-group" ng-if="combinationField.selectedCombination != null">
                    <div class="col-lg-offset-3 col-sm-offset-3 col-md-offset-3 col-lg-6 col-xs-12 col-md-6 col-sm-6">
                      <md-card md-theme-watch>
                        <md-card-title>
                          <md-card-title-text>
                            <span class="md-headline">Valè Aktyèl</span>
                            <span class="md-subhead">{{combinationField.selectedCombination.combinationType.products.name}}</span>
                          </md-card-title-text>

                          <md-card-title-media>
                            <div class="md-media-lg card-media text-center">
                              <div class="info-box {{combinationField.selectedCombination.enabled? 'green' : 'red'}}-bg" style="position: relative;  width: 100%; height: 73%;">
                                <div class="count">
                                  <span style="font-size: 30px">{{combinationField.selectedCombination.resultCombination}}</span>
                                </div>
                                <div class="title">{{combinationField.selectedCombination.enabled? 'Actif' : 'Bloke'}}</div>
                               </div>
                            </div>
                          </md-card-title-media>
                        </md-card-title>

                        <md-card-content layout-align="start center">
                          <p>Pri Maksimòm: {{combinationField.selectedCombination.maxPrice}}</p>
                        </md-card-content>

                        <md-card-actions layout="row" layout-align="end center">
                          <md-button ng-click="combinationField.changePrice = !combinationField.changePrice; combinationField.changeState = false" class="md-raised md-primary">
                            <i class="fa fa-dollar"></i>
                            Chanje pri
                          </md-button>
                          <md-button ng-click="combinationField.changeState = !combinationField.changeState; combinationField.changePrice = false" class="md-raised md-primary">
                            <i class="fa fa-{{combinationField.selectedCombination.enabled? 'lock' : 'unlock'}}"></i>
                            {{combinationField.selectedCombination.enabled? ' Bloke' : ' Debloke'}}
                          </md-button>
                        </md-card-actions>
                      </md-card>
                    </div>
                      <div class="col-lg-3 col-xs-12 col-md-3 col-sm-12"
                           ng-if="(combinationField.changeState || combinationField.changePrice) && combinationField.selectedCombination!=null">
                        <md-card md-theme-watch >
                          <md-content>
                            <md-card-title layout-align="start center">
                              <md-card-title-text >
                                <span class="md-headline" ng-if="combinationField.changeState">
                                  {{combinationField.selectedCombination.enabled? 'Bloke' : 'Debloke'}} konbinezon
                                </span>
                                <span class="md-headline" ng-if="combinationField.changePrice">Chanje Pri a </span>
                              </md-card-title-text>
                            </md-card-title>
                            <md-card-content layout="row">

                              <div layout="row" ng-if="combinationField.changeState">
                                <md-input-container class="md-block">
                                  <md-checkbox ng-model="combinationField.resultCombination.enabled">
                                    <i class="fa fa-{{combinationField.resultCombination.enabled? 'unlock' : 'lock'}}"
                                      style="color: {{combinationField.resultCombination.enabled? 'green' : 'red'}};">
                                      {{combinationField.resultCombination.enabled? ' Actif' : ' Bloke'}}
                                    </i>
                                  </md-checkbox>
                                </md-input-container>
                              </div>

                              <div layout="row" ng-if="combinationField.changePrice">
                                <md-input-container class="md-block">
                                  <label>Pri Maksimòm</label>
                                  <input ng-model="combinationField.resultCombination.maxPrice">
                                </md-input-container>
                              </div>
                            </md-card-content>
                            <md-card-actions layout="row" layout-align="end center">
                              <md-button ng-click="saveCombination()" class="md-raised md-primary">Anrejistre</md-button>
                            </md-card-actions>
                          </md-content>

                        </md-card>
                      </div>
                    </div>

                  <div class="form-group" ng-if="combinationGroupField.selectedCombinationType">
                    <div class="col-lg-offset-3 col-sm-offset-3 col-md-offset-3 col-lg-6 col-xs-12 col-md-6 col-sm-6">
                      <md-card md-theme-watch>
                        <md-card-title>
                          <md-card-title-text>
                              <div layout-gt-md="row">
                                  <div flex-gt-md>
                                      <md-input-container style="margin-right: 10px;">
                                      </md-input-container>
                                  </div>
                                  <div flex-gt-md layout-align="end center">
                                      <div class="info-box blue-bg">
                                          <p>Konbinezon</p>
                                          <div class="count text-right">{{replaceString(combinationGroupField.selectedCombinationType.products.name, ' ')}}</div>
                                      </div>
                                  </div>
                              </div>
                          </md-card-title-text>
                        </md-card-title>

                        <md-card-content layout-align="start center" ng-cloak>
                            <div layout-gt-md="row">
                                <div flex-gt-md ng-if="selectType.changeType <= 1">
                                    <h4>Pri Maksimòm</h4>
                                    <md-input-container class="md-block">
                                        <input ng-model="combinationGroupField.resultCombinationGroup.maxPrice">
                                    </md-input-container>
                                 </div>
                                <div flex-gt-md ng-if="selectType.changeType > 1">
                                  <md-input-container class="md-block">
                                    <md-checkbox ng-model="combinationGroupField.resultCombinationGroup.enabled">
                                      <i class="fa fa-{{combinationGroupField.resultCombinationGroup.enabled? 'unlock' : 'lock'}}"
                                         style="color: {{combinationGroupField.resultCombinationGroup.enabled? 'green' : 'red'}};">
                                          {{combinationGroupField.resultCombinationGroup.enabled?
                                            ' Debloke tout '+ replaceString(combinationGroupField.selectedCombinationType.products.name, ' ').toLowerCase()+ ' yo' :
                                            ' Bloke tout '+ replaceString(combinationGroupField.selectedCombinationType.products.name, ' ').toLowerCase() + ' yo'}}
                                      </i>
                                    </md-checkbox>
                                  </md-input-container>
                                </div>
                            </div>
                            <md-card-actions layout="row" layout-align="end center">
                                <md-button ng-click="saveCombinationGroup()" ng-disabled="selectType.changeType <= 1 && combinationGroupField.resultCombinationGroup.maxPrice <= 0" class="md-raised md-primary">Anrejistre</md-button>
                            </md-card-actions>
                        </md-card-content>
                      </md-card>
                    </div>
                  </div>

                </form>
              </div>
            </section>
          </div>
            <div class="col-lg-4">
                <section class="panel">
                    <header class="panel-heading">
                        Konbinezon Ki bloke yo
                    </header>
                    <div class="panel-body">
                        <md-card md-theme-watch >
                            <md-content>
                                <md-card-title layout-align="start center">
                                    <md-card-title-text >
                                        <span class="md-headline">List Konbinezon ki bloke yo
                                        </span>
                                    </md-card-title-text>
                                </md-card-title>
                                <md-card-content layout="row">
                                    <md-list flex>
                                        <md-list-item class="md-2-line" ng-repeat="phone in phones">
                                            <div class="md-list-item-text" ng-class="{'md-offset': phone.options.offset }">
                                                <h3> {{ phone.number }} </h3>
                                                <p> {{ phone.type }} </p>
                                            </div>
                                        </md-list-item>
                                    </md-list>
                                </md-card-content>
                            </md-content>

                        </md-card>
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
    });
</script>



</body>

</html>