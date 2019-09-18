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
    <section id="main-content" ng-init="init(true)">
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
                      <table  ng-table="global.tableParams"
                              class="table table-striped table-bordered table-striped table-condensed table-hover">
                          <tr ng-repeat="combinationType in $data track by combinationType.id">
                              <td style="vertical-align: middle" data-title="'#'">{{$index+1}}</td>
                              <td style="vertical-align: middle" data-title="'Konbinezon'">{{combinationType.products.name}}</td>
                              <td style="vertical-align: middle" data-title="'Pri Peman'" ng-if="combinationType.products.id === 1">{{combinationType.payedPriceFirstDraw}}, {{combinationType.payedPriceSecondDraw}}, {{combinationType.payedPriceThirdDraw}}</td>
                              <td style="vertical-align: middle" data-title="'Pri Peman'" ng-if="combinationType.products.id !== 1">{{combinationType.payedPrice}}</td>
                              <td style="vertical-align: middle" data-title="'Dat Kreyasyon'">{{combinationType.creationDate | date:'dd/MM/yyyy'}}</td>
                              <td style="vertical-align: middle" data-title="'Dat Modifikasyon'">{{combinationType.modificationDate | date:'dd/MM/yyyy'}}</td>
                              <td style="vertical-align: middle; text-align: center" data-title="'Aktyalize'">
                                  <a class="btn btn-warning btn-xs" href="/combinationType/update/{{combinationType.id}}"><i class="fa fa-edit"></i> Aktyalize</a>
                              </td>
                          </tr>
                      </table>
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