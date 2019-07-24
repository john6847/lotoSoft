<!DOCTYPE html>
<html lang="en" ng-app="lottery" ng-cloak>

  <head>
    <#include "../header.ftl">
  </head>

<body>

<#include "../nav.ftl" >
<!--header end-->
<!-- container section start -->
<section id="container" class="">
    <!--sidebar start-->
    <aside>
    <#include "../sidebar.ftl">
    </aside>
    <!--sidebar end-->


    <!--main content start-->
    <section id="main-content" ng-controller="sellerController">
      <section class="wrapper">
        <div class="row">
          <div class="col-lg-12">
            <h3 class="page-header"><i class="fa fa-eye"></i>Paj pou gade Vandè</h3>
            <ol class="breadcrumb">
              <li><i class="fa fa-home"></i><a href="/home">Paj Akèy</a></li>
              <li><i class="fa fa-eye"></i>Vandè</li>
            </ol>
          </div>
        </div>
        <div class="row">
          <div class="col-lg-12 col-xs-12 col-xl-12 col-md-12 col-sm-12">
            <#if error??>
              <div class="alert alert-danger" role="alert">${error}</div>
            </#if>
          </div>
          <div class="col-lg-12">
            <section class="panel">
              <header class="panel-heading">
                <div class="row">
                  <div class="col-lg-4">
                      Vandè
                  </div>
                  <div class="col-lg-8" ng-init="getData()">
                    <div class="row">
                      <div class=" text-right col-lg-offset-6 col-md-offset-6 col-xs-offset-6 col-lg-6 col-md-6 col-sm-6">
                        <div class="form-group">
                            <select class="form-control m-bot15"
                                    data-live-search="true"
                                    data-size="5"
                                    ng-model="state"
                                    ng-change="getData()"
                                    name="state"
                                    id="state"
                                    autofocus>
                              <option value="0">Bloke</option>
                              <option value="1" selected>Tout</option>
                              <option value='2'>Actif</option>
                            </select>
                          </div>
                        </div>
                    </div>
                  </div>
                </div>
              </header>
              <div class="panel-body">

                <div class="row">
                  <#--style="overflow-y:scroll; height:150px;"-->
                  <div class="col-md-12">
                    <div class="table-responsive">
                      <table datatable="ng" dt-options="dtOptions"
                             class="table table-striped table-advance table-hover">
                        <thead>
                        <tr>
                          <th style="width:5%">#</th>
                          <th style="width:15%">Itilizatè</th>
                          <th style="width:10%">Gwoup</th>
                          <th style="width:10%;" class="text-right">Pousantaj(%)</th>
                          <th style="width:15%" class="text-right">Kantite Lajan(HTG)</th>
                          <th style="width:15%">Kalite pèman</th>
                          <th style="width:15%; text-align: center">Aktif</th>
                          <th style="width:5%"></th>
                          <th style="width:5%"></th>
                          <th style="width:5%"></th>

                        </tr>
                        </thead>
                        <tbody>
                        <tr ng-repeat="seller in sellers">
                          <td>{{start+$index+1}}</td>
                          <td>{{seller.user.username}}</td>
                          <td><span ng-if="seller.groups === null || seller.groups === undefined">N/A</span>{{seller.groups.description}}</td>
                          <td class="text-right"><span ng-if="seller.percentageCharged">{{seller.percentageCharged | number: 2}}</span> <span ng-if="!seller.percentageCharged">N/A</span></td>
                          <td class="text-right"><span ng-if="seller.amountCharged">{{seller.amountCharged | number: 2}}</span> <span ng-if="!seller.amountCharged">N/A</span></td>
                          <td>{{paymentType[seller.paymentType].Name}}</td>
                          <td style="text-align: center">
                            <i class="fa fa-{{seller.enabled? 'check' : 'times' }}" style="color: {{seller.enabled? 'green' : 'red'}} ;"></i>
                            <p style="display: none">{{seller.enabled? 'Wi' : 'Non' }}</p>
                          </td>

                          <td>
                            <a class="btn btn-warning btn-xs" href="/seller/update/{{seller.id}}">
                              <i class="fa fa-edit"></i> Aktyalize
                            </a>
                          </td>
                          <td>
                            <a class="btn btn-danger btn-xs" href="/seller/delete/{{seller.id}}">
                              <i class="fa fa-trash-o"></i> Elimine
                            </a>
                          </td>
                          <td>
                            <a class="btn btn-{{seller.enabled? 'primary' : 'default' }} btn-xs" href="/configuration/seller/{{seller.id}}">
                              <i class="fa fa-{{seller.enabled? 'lock' : 'unlock'}}" aria-hidden="true"></i> {{seller.enabled? 'Bloke' : 'Debloke'}}
                            </a>
                          </td>
                        </tr>
                        </tbody>
                        <tfoot>
                          <tr>
                            <th style="width:5%">#</th>
                            <th style="width:15%">Itilizatè</th>
                            <th style="width:10%">Gwoup</th>
                            <th style="width:10%;"class="text-right">Pousantaj(%)</th>
                            <th style="width:15%" class="text-right">Kantite Lajan(HTG)</th>
                            <th style="width:15%">Kalite pèman</th>
                            <th style="width:15%; text-align: center">Aktif</th>
                          </tr>
                        </tfoot>
                      </table>
                    </div>
                  </div>
                </div>
              </div>
              <footer class="panel-footer">
                <div class="row">
                  <div class="col-xs-12 col-md-6 col-lg-6" style="float: left">
                    <a class="btn btn-primary" href="/seller/create">
                      <i class="fa fa-plus-circle"></i> Ajoute Nouvo Vandè
                    </a>
                  </div>
                </div>
              </footer>
          </div>
      </section>
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

  <#include "../scripts.ftl">

</body>

</html>