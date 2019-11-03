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
  <section id="main-content" ng-controller="enterpriseController">
    <section class="wrapper" ng-init="init(true)">
      <div class="row">
        <div class="col-lg-12">
          <h3 class="page-header"><i class="fa fa-eye"></i>Paj pou gade antrepriz</h3>
          <ol class="breadcrumb">
            <li><i class="fa fa-home"></i><a href="/home">Paj Akèy</a></li>
            <li><i class="fa fa-eye"></i>Antrepriz</li>
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
              Antrepriz
            </header>
            <div class="panel-body">
              <div class="row">
                  <#--style="overflow-y:scroll; height:150px;"-->
                <div class="col-md-12">
                  <div class="table-responsive">
                    <table ng-table="global.tableParams"
                           class="table table-striped table-bordered table-striped table-condensed table-hover">
                      <tr ng-repeat="enterprise in $data track by enterprise.id">
                        <td style="vertical-align: middle;" data-title="'#'">{{start+$index+1}}</td>
                        <td style="vertical-align: middle;" data-title="'Non Antrepriz'">{{enterprise.name}}</td>
                        <td style="vertical-align: middle;" data-title="'Idantifikasyon'">{{enterprise.identifier}}</td>
                        <td style="vertical-align: middle;" data-title="'Dat Kreyasyon'">{{enterprise.modificationDate | customDateFormat}}</td>
                        <td style="vertical-align: middle; text-align: center" data-title="'Actif'">
                          <i class="fa fa-{{enterprise.enabled? 'check' : 'times' }}"
                             style="color: {{enterprise.enabled? 'green' : 'red'}} ;">
                          </i>
                          <p style="display: none">{{enterprise.enabled? 'Wi' : 'Non' }}</p>
                        </td>

                        <td style="vertical-align: middle; text-align: center;" data-title="'Aksyon'">
                          <a class="btn btn-warning btn-xs load" title="Aktyalize" href="/enterprise/update/{{enterprise.id}}">
                            <i class="fa fa-edit"></i>
                          </a>

                          <a class="btn btn-danger btn-xs" title="Elimine" href="/enterprise/delete/{{enterprise.id}}">
                            <i class="fa fa-trash-o"></i>
                          </a>

                          <a class="btn btn-{{enterprise.enabled? 'primary' : 'default' }} btn-xs"
                             title="{{enterprise.enabled? 'Bloke' : 'Debloke'}}"
                             href="/configuration/enterprise/{{enterprise.id}}">
                            <i class="fa fa-{{enterprise.enabled? 'lock' : 'unlock'}}" aria-hidden="true"></i>
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
                  <a class="btn btn-primary load" href="/enterprise/create">
                    <i class="fa fa-plus-circle"></i> Ajoute Nouvo Antrepriz
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
    $(".load").on("click", function () {
        $("#custom-loader").fadeIn();
    });//submit
</script>

</body>

</html>