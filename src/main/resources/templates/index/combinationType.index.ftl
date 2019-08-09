<!DOCTYPE html>
<html lang="en" ng-app="lottery" ng-cloak>

<head>
  <#include "../header.ftl">
    </head>

<body>
<!-- container section start -->
<section id="container" ng-controller="combinationTypeController">
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
              <h3 class="page-header"><i class="fa fa-eye"></i>Paj pou gade konbinezon</h3>
              <ol class="breadcrumb">
                <li><i class="fa fa-home"></i><a href="/home">Paj Akèy</a></li>
                <li><i class="fa fa-eye"></i>Tip Konbinezon</li>
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
                            Tip konbinezon
                        </div>
                    </div>
                </header>
                <div class="panel-body">
                  <div class="row">
                    <div class=" col-md-12">
                    <div class="table-responsive">
                      <table class="table table-striped table-advance table-hover" ng-init="fetchAllCombinationTypes()">
                        <tbody>
                          <tr>
                            <th style="width:5%">#</th>
                            <th style="width:15%">Konbinezon</th>
                            <th style="width:15%">Pri Peman</th>
                            <th style="width:15%">Dat Kreyasyon</th>
                            <th style="width:15%">Dat Modifikasyon</th>
<#--                            <th style="width:15%; text-align: center">Actif</th>-->
<#--                            <th style="width:15%; text-align: center">Bloke</th>-->
                            <th style="width:5%"></th>
                          </tr>
                          <tr dir-paginate="combinationType in combinationTypes|itemsPerPage:itemsPerPage" total-items="totalCount">
                              <td>{{start+$index+1}}</td>
                              <td>{{combinationType.products.name}}</td>
                              <td ng-if="combinationType.products.id === 1">{{combinationType.payedPriceFirstDraw}}, {{combinationType.payedPriceSecondDraw}}, {{combinationType.payedPriceThirdDraw}}</td>
                              <td ng-if="combinationType.products.id !== 1">{{combinationType.payedPrice}}</td>
                              <td>{{combinationType.creationDate | date:'dd/MM/yyyy'}}</td>
                              <td>{{combinationType.modificationDate | date:'dd/MM/yyyy'}}</td>
<#--                              <td style="text-align: center"><i class="fa fa-{{combinationType.enabled? 'check' : 'times' }}" style="color: {{combinationType.enabled? 'green' : 'red'}} ;"></i></td>-->
<#--                              <td style="text-align: center"><i class="fa fa-{{combinationType.enabled? 'times' : 'check' }}" style="color: {{combinationType.enabled? 'red' :'green' }} ;"></i></td>-->
                              <td>
                                  <a class="btn btn-warning btn-xs" href="/combinationType/update/{{combinationType.id}}"><i class="fa fa-edit"></i> Aktyalize</a>
                              </td>
                          </tr>
                        </tbody>
                      </table>
                        <div class="text-center">
                          <dir-pagination-controls
                            max-size="10"
                            direction-links="true"
                            boundary-links="true"
                            on-page-change="getData(newPageNumber)">
                          </dir-pagination-controls>
                        </div>
                    </div>
                  </div>
                </div>
                </div>
              </section>
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
    <#include "../scripts.ftl">
    <script type = "text/javascript" >
        $('.selectpicker').selectpicker();
    </script>

</body>

</html>