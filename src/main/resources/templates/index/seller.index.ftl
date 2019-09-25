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
    <section class="wrapper" ng-init="init(true)">
      <div class="row">
        <div class="col-lg-12" ng-int="getData()">
          <h3 class="page-header"><i class="fa fa-eye"></i>Paj pou gade vandè</h3>
          <ol class="breadcrumb">
            <li><i class="fa fa-home"></i><a href="/home">Paj Akèy</a></li>
            <li><i class="fa fa-eye"></i>Vandè</li>
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
            <header class="panel-heading">Vandè</header>
            <div class="panel-body">
              <div class="row">
                <div class="col-md-12">
                  <div class="table-responsive">
                    <table ng-table="global.tableParams"
                           class="table table-striped table-bordered table-striped table-condensed table-hover">
                      <tr ng-repeat="seller in $data track by seller.id">
                        <td style="vertical-align: middle" data-title="'#'">{{start+$index+1}}</td>
                        <td style="vertical-align: middle" data-title="'Itilizatè'">{{seller.user.username}}</td>
                        <td style="vertical-align: middle" data-title="'Gwoup'"><span
                                  ng-if="seller.groups === null || seller.groups === undefined">N/A</span>{{seller.groups.description}}
                        </td>
                        <td style="vertical-align: middle" data-title="'Machin'">{{seller.pos.description}}</td>
                        <td style="vertical-align: middle" data-title="'Pousantaj(%)'" class="text-right">
                          <span ng-if="seller.percentageCharged">{{seller.percentageCharged | number: 2}}</span>
                          <span ng-if="!seller.percentageCharged">N/A</span>
                        </td>
                        <td style="vertical-align: middle" data-title="'Kantite Lajan(HTG)'" class="text-right"><span
                                  ng-if="seller.amountCharged">{{seller.amountCharged | number: 2}}</span> <span
                                  ng-if="!seller.amountCharged">N/A</span></td>
                        <td style="vertical-align: middle" data-title="'Tip pèman'">
                          {{paymentType[seller.paymentType].Name}}
                        </td>
                        <td data-title="'Aktif'" style="text-align: center; vertical-align: middle">
                          <i class="fa fa-{{seller.enabled? 'check' : 'times' }}"
                             style="color: {{seller.enabled? 'green' : 'red'}} ;"></i>
                          <p style="display: none">{{seller.enabled? 'Wi' : 'Non' }}</p>
                        </td>

                        <td style="vertical-align: middle; text-align: center;" data-title="'Aktyalize'">
                          <a class="btn btn-warning btn-xs" href="/seller/update/{{seller.id}}">
                            <i class="fa fa-edit"></i> Aktyalize
                          </a>
                        </td>
                        <td style="vertical-align: middle; text-align: center;" data-title="'Elimine'">
                          <a class="btn btn-danger btn-xs" id="delete" onclick="onDelete(event)"
                             href="/seller/delete/{{seller.id}}">
                            <i class="fa fa-trash-o"></i> Elimine
                          </a>
                        </td>
                        <td style="vertical-align: middle; text-align: center;" data-title="'Bloke/Debloke'">
                          <a class="btn btn-{{seller.enabled? 'primary' : 'default' }} btn-xs" id="block"
                             onclick="onBlock(event)" href="/configuration/seller/{{seller.id}}">
                            <i class="fa fa-{{seller.enabled? 'lock' : 'unlock'}}" aria-hidden="true"></i>
                            {{seller.enabled? 'Bloke' : 'Debloke'}}
                          </a>
                        </td>
                      </tr>
                    </table>
                  </div>
                </div>
              </div>
            </div>
            <footer class="panel-footer">
              <div class="row">
                <div class="col-xs-12 col-md-6 col-lg-6" style="float: left">
                  <a class="btn btn-primary" id="create" href="/seller/create">
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
<#include "../loader.ftl">

<#include "../scripts.ftl">
<script>
    $("#create").on("click", function () {
        $("#custom-loader").fadeIn();
    });//submit

    var responseDelete = false;

    function onDelete(e,) {
        if (!responseDelete)
            e.preventDefault();
        bootbox.confirm({
            message: "Ou preske elimine vande sa, ou deside kontinye?",
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
                    responseDelete = true;
                    document.getElementById('delete').click();
                }
            }
        });
    }

    var responseBlock = false;

    function onBlock(e) {
        if (!responseBlock)
            e.preventDefault();
        bootbox.confirm({
            message: "Wap reayilize yon aksyon ki ap bloke oubyen debloke vande sa, ou deside kontinye?",
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
                    responseBlock = true;
                    document.getElementById('block').click();
                }
            }
        });
    }
</script>
<script type="text/javascript">
    $('.selectpicker').selectpicker();
</script>

</body>

</html>