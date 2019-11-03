<!DOCTYPE html>
<html lang="en" ng-app="lottery" ng-cloak>

<head>
    <#include "../header.ftl">

</head>

<body>
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
    <section id="main-content" ng-controller="drawController">
      <section class="wrapper" ng-init="init(true)">
        <div class="row">
          <div class="col-lg-12">
            <h3 class="page-header"><i class="fa fa-eye"></i>Paj pou gade tiraj</h3>
            <ol class="breadcrumb">
              <li><i class="fa fa-home"></i><a href="/home">Paj Akèy</a></li>
              <li><i class="fa fa-eye"></i>Tiraj</li>
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
                Tiraj
              </header>
              <div class="panel-body">
                <div class="row">
                  <div class=" col-md-12">
                    <div class="table-responsive">
                      <table ng-table="global.tableParams"
                             class="table table-striped table-bordered table-striped table-condensed table-hover">

                        <tr ng-repeat="draw in $data track by draw.id">
                          <td style="vertical-align: middle;" data-title="'#'">{{$index+1}}</td>
                          <td style="vertical-align: middle;" data-title="'Dat Tiraj'">{{draw.drawDate | customDateFormat}}</td>
                          <td style="vertical-align: middle;" data-title="'Tip'">{{draw.shift.name}}</td>
                          <td style="vertical-align: middle;" data-title="'Loto 3'" class="text-center"><span
                                    class="badge bg-primary">{{draw.numberThreeDigits.numberInStringFormat}}</span></td>
                          <td style="vertical-align: middle;" data-title="'Bolet'" class="text-center">
                            <span class="badge bg-warning">{{draw.firstDraw.numberInStringFormat}}</span>
                            <span class="badge bg-success">{{draw.secondDraw.numberInStringFormat}}</span>
                            <span class="badge bg-info">{{draw.thirdDraw.numberInStringFormat}}</span>
                          </td>
                          <td style="vertical-align: middle;" data-title="'Dat Kreyasyon'">{{draw.modificationDate | customDateFormat}}
                          </td>
                          <td style="vertical-align: middle;text-align: center" data-title="'Aktif'">
                            <i class="fa fa-{{draw.enabled? 'check' : 'times' }}"
                               style="color: {{draw.enabled? 'green' : 'red'}} ;"></i>
                            <p style="display: none">{{draw.enabled? 'Wi' : 'Non' }}</p>
                          </td>

                          <td style="vertical-align: middle; text-align: center;" data-title="'Aksyon'">
                            <a class="btn btn-warning btn-xs load" title="Aktyalize" href="/draw/update/{{draw.id}}">
                              <i class="fa fa-edit"></i>
                            </a>

                            <a class="btn btn-danger btn-xs cancel" id="cancel" title="Anile" onclick="onCancel(event)"
                               href="/draw/cancel/{{draw.id}}"><i class="fa fa-ban"></i>
                            </a>
                          </td>
                        </tr>
                      </table>
                    </div>
                  </div>
                </div>
              </div>
          </div>
          <footer class="panel-footer">
            <div class="row">
              <div class="col-xs-12 col-md-6 col-lg-6" style="float: left">
                <a class="btn btn-primary load" href="/draw/create">
                  <i class="fa fa-plus-circle"></i> Ajoute Nouvo Tiraj
                </a>
              </div>
            </div>
          </footer>
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
  <#include "../loader.ftl">


  <!-- container section end -->
  <#include "../scripts.ftl">
  <script type="text/javascript">
      $('.selectpicker').selectpicker();

      $(".load").on("click", function () {
          $("#custom-loader").fadeIn();
      });//submit
  </script>

  <script>
      var responseCancel = false;

      function onCancel(e,) {
          if (!responseCancel)
              e.preventDefault();
          bootbox.confirm({
              message: "Ou preske anile tiraj sa, ou deside kontinye?",
              size: 'small',
              buttons: {
                  confirm: {
                      label: '<i class="fa fa-check"></i> Wi',
                      className: ' btn-success'
                  },
                  cancel: {
                      label: '<i class="fa fa-times"></i> Non',
                      className: 'btn-danger'
                  }
              }, callback: function (result) {
                  if (result) {
                      responseCancel = true;
                      document.getElementById('cancel').click();
                    $("#custom-loader").fadeIn();
                  }
              }
          });
      }
  </script>
</body>

</html>


<#--<div class="row">-->
<#--  <div class="col-lg-8">-->
<#--    <div class="row text-right">-->
<#--      <div class="col-lg-12 col-md-12 col-sm-12">-->
<#--        <div class="row">-->
<#--          <div class="col-lg-6 col-md-6 col-sm-6">-->
<#--            <div class="form-group" style="padding: 10px">-->
<#--              <select class="form-control m-bot15 selectpicker"-->
<#--                      data-live-search="true"-->
<#--                      data-size="5"-->
<#--                      ng-model="state"-->
<#--                      ng-change="getData()"-->
<#--                      name="state"-->
<#--                      id="state"-->
<#--                      autofocus>-->
<#--                <option value="0">Bloke</option>-->
<#--                <option value="1">Tout</option>-->
<#--                <option value="2">Actif</option>-->
<#--              </select>-->
<#--            </div>-->
<#--          </div>-->
<#--          <div class="col-lg-6 col-md-6 col-sm-6" >-->
<#--            <div class="row">-->
<#--              <div class="col-lg-4 col-md-4 col-sm-4" >-->
<#--                <div class="form-group" style="padding: 10px">-->
<#--                  <select title="Filtre pa jou"-->
<#--                          class="form-control m-bot15 selectpicker"-->
<#--                          data-live-search="true"-->
<#--                          data-size="5"-->
<#--                          ng-model="day"-->
<#--                          ng-change="getData(1)"-->
<#--                          name="day"-->
<#--                          id="day">-->
<#--                    <option ng-repeat="day in range(days.min, days.max)" value="{{day}}">{{day}}</option>-->
<#--                  </select>-->
<#--                </div>-->
<#--              </div>-->
<#--              <div class="col-lg-4 col-md-4 col-sm-4">-->
<#--                <div class="form-group" style="padding: 10px">-->
<#--                  <select class="form-control m-bot15 selectpicker"-->
<#--                          data-live-search="true"-->
<#--                          data-size="5"-->
<#--                          ng-model="month"-->
<#--                          ng-change="getData(1)"-->
<#--                          name="month"-->
<#--                          id="month"-->
<#--                          title="Filtre pa mwa" >-->
<#--                    <option ng-repeat="month in months" value="{{month.id}}">{{month.month}}</option>-->
<#--                  </select>-->
<#--                </div>-->
<#--              </div>-->
<#--              <div class="col-lg-4 col-md-4 col-sm-4">-->
<#--                <div class="form-group" style="padding: 10px">-->
<#--                  <select class="form-control m-bot15 selectpicker"-->
<#--                          data-live-search="true"-->
<#--                          data-size="5"-->
<#--                          ng-model="year"-->
<#--                          ng-change="getData(1)"-->
<#--                          name="year"-->
<#--                          id="year"-->
<#--                          title="Filtre pa ane">-->
<#--                    <option ng-repeat="year in years" value="{{year}}">{{year}}</option>-->
<#--                  </select>-->
<#--                </div>-->
<#--              </div>-->
<#--            </div>-->
<#--          </div>-->
<#--        </div>-->
<#--      </div>-->
<#--    </div>-->
<#--  </div>-->
<#--</div>-->