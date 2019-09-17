<!DOCTYPE html>
<html lang="en" ng-app="lottery">

<#--https://code.jquery.com/jquery-2.2.4.min.js-->

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
    <section id="main-content" ng-controller="userController">
      <section class="wrapper">
        <div class="row">
          <div class="col-lg-12">
            <h3 class="page-header"><i class="fa fa-eye"></i>Paj pou wè tout itilizatè yo</h3>
            <ol class="breadcrumb">
              <li><i class="fa fa-home"></i><a href="/home">Paj Akèy</a></li>
              <li><i class="fa fa-eye"></i>Itilizatè</li>
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
          <div class="col-lg-12" ng-init="getData()">
            <section class="panel">
              <header class="panel-heading">
                Itilizatè
              </header>
              <div class="panel-body">
                <div class="row">
                  <div class=" col-md-12" >
                    <div class="table-responsive">
                      <table ng-table="global.tableParams" class="table table-striped table-bordered table-striped table-condensed table-hover">
                          <tr ng-repeat="user in $data track by user.id">
                              <td style="vertical-align: middle" data-title="'#'">{{$index+1}}</td>
                              <td style="vertical-align: middle" data-title="'Non'">{{user.name}}</td>
                              <td style="vertical-align: middle" data-title="'Itilizatè'">{{user.username}}</td>
                              <td style="vertical-align: middle;text-align: center" data-title="'Vandè'" style="text-align: center"><i class="fa fa-{{isRole(user.roles,'ROLE_SELLER')? 'check' : 'times'}}" style="color: {{isRole(user.roles,'ROLE_SELLER')? 'green' : 'red'}};"></i> <p style="display: none">{{isRole(user.roles,'ROLE_SELLER')? 'Wi' : 'Non' }}</p> </td>
                              <td style="vertical-align: middle;text-align: center" data-title="'Adm.'" style="text-align: center"><i class="fa fa-{{isRole(user.roles,'ROLE_ADMIN')? 'check' : 'times'}}" style="color: {{isRole(user.roles,'ROLE_ADMIN')? 'green' : 'red'}};"></i><p style="display: none">{{isRole(user.roles,'ROLE_ADMIN')? 'Wi' : 'Non' }}</p></td>
                              <td style="vertical-align: middle;text-align: center" data-title="'Sipèvizè'" style="text-align: center"><i class="fa fa-{{isRole(user.roles,'ROLE_SUPERVISOR')? 'check' : 'times'}}" style="color: {{isRole(user.roles,'ROLE_SUPERVISOR')? 'green' : 'red'}};"></i><p style="display: none">{{isRole(user.roles,'ROLE_SUPERVISOR')? 'Wi' : 'Non' }}</p></td>
                              <td style="vertical-align: middle;text-align: center" data-title="'Rekolektè'" style="text-align: center"><i class="fa fa-{{isRole(user.roles,'ROLE_COLLECTOR')? 'check' : 'times'}}" style="color: {{isRole(user.roles,'ROLE_COLLECTOR')? 'green' : 'red'}};"></i><p style="display: none">{{isRole(user.roles,'ROLE_COLLECTOR')? 'Wi' : 'Non' }}</p></td>
                              <td style="vertical-align: middle" data-title="'Dat Kreyasyon'">{{user.modificationDate | date: 'dd/MM/yyyy'}}</td>
                              <td style="vertical-align: middle; text-align: center" data-title="'Actif'"><i class="fa fa-{{user.enabled? 'check' : 'times' }}" style="color: {{user.enabled? 'green' : 'red'}} ;"></i><p style="display: none">{{user.enabled? 'Wi' : 'Non' }}</p> </td>
                              <td style="vertical-align: middle; text-align: center" data-title="'ktyalize'">
                                  <a class="btn btn-warning btn-xs" href="/user/1/update/{{user.id}}">
                                      <i class="fa fa-edit"></i> Aktyalize
                                  </a>
                              </td>
                              <td style="vertical-align: middle; text-align: center" data-title="'Elimine'">
                                  <a class="btn btn-danger btn-xs" id="delete" onclick="onDelete(event)" href="/user/1/delete{{user.id}}">
                                      <i class="fa fa-trash-o"></i> Elimine
                                  </a>
                              </td>
                              <td style="vertical-align: middle; text-align: center" data-title="'Bloke/Debloke'">
                                  <a class="btn btn-{{user.enabled? 'primary' : 'default' }} btn-xs" id="block" onclick="onBlock(event)" href="/configuration/user/{{user.id}}">
                                      <i class="fa fa-{{user.enabled? 'lock' : 'unlock'}}" aria-hidden="true"></i> {{user.enabled? 'Bloke' : 'Debloke'}}
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
                    <a class="btn btn-primary" href="/user/1/create">
                        <i class="fa fa-plus-circle"></i> Ajoute Nouvo Itilizatè
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
<script>
    var responseDelete = false;
    function onDelete (e, ) {
        if(!responseDelete)
            e.preventDefault();
        bootbox.confirm({
            message: "Ou preske elimine itilizate sa, ou deside kontinye?",
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
                if (result){
                    responseDelete = true;
                    document.getElementById('delete').click();
                }
            }
        });
    }

    var responseBlock = false;
    function onBlock (e) {
        if(!responseBlock)
            e.preventDefault();
        bootbox.confirm({
            message: "Wap reayilize yon aksyon ki ap bloke oubyen debloke itilizate sa, ou deside kontinye?",
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
                if (result){
                    responseBlock = true;
                    document.getElementById('block').click();
                }
            }
        });
    }
</script>
<script type = "text/javascript" >
    $('.selectpicker').selectpicker();
</script>

</body>

</html>