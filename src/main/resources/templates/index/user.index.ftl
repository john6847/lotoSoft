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
                      Itilizatè
                  </div>

                  <div class="col-lg-8 text-right" style="margin-top: 5px">
                      <div class="row">
                          <div class="col-lg-offset-6 col-md-offset-6 col-xs-offset-6 col-lg-6 col-md-6 col-sm-6">
                            <div class="form-group">
                              <select class="form-control"
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
                  <div class=" col-md-12" >
                    <div class="table-responsive">
                        <table datatable="ng" dt-options="dtOptions"
                                class="table table-striped table-advance table-hover">
                            <thead>
                                <tr>
                                    <th style="width:5%">#</th>
                                    <th style="width:20%">Non</th>
                                    <th style="width:10%">Itilizatè</th>
                                    <th style="width:5% text-align: center">Vandè</th>
                                    <th style="width:5% text-align: center">Adm</th>
                                    <th style="width:5% text-align: center">Sipèvizè</th>
                                    <th style="width:5% text-align: center">Rekolektè</th>
                                    <th style="width:10%">Dat Kreyasyon</th>
                                    <th style="width:5%; text-align: center">Actif</th>
                                    <th style="width:10%"></th>
                                    <th style="width:10%"></th>
                                    <th style="width:10%"></th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr ng-repeat="user in users">
                                    <td>{{$index+1}}</td>
                                    <td>{{user.name}}</td>
                                    <td>{{user.username}}</td>
                                    <td style="text-align: center"><i class="fa fa-{{isRole(user.roles,'ROLE_SELLER')? 'check' : 'times'}}" style="color: {{isRole(user.roles,'ROLE_SELLER')? 'green' : 'red'}};"></i> <p style="display: none">{{isRole(user.roles,'ROLE_SELLER')? 'Wi' : 'Non' }}</p> </td>
                                    <td style="text-align: center"><i class="fa fa-{{isRole(user.roles,'ROLE_ADMIN')? 'check' : 'times'}}" style="color: {{isRole(user.roles,'ROLE_ADMIN')? 'green' : 'red'}};"></i><p style="display: none">{{isRole(user.roles,'ROLE_ADMIN')? 'Wi' : 'Non' }}</p></td>
                                    <td style="text-align: center"><i class="fa fa-{{isRole(user.roles,'ROLE_SUPERVISOR')? 'check' : 'times'}}" style="color: {{isRole(user.roles,'ROLE_SUPERVISOR')? 'green' : 'red'}};"></i><p style="display: none">{{isRole(user.roles,'ROLE_SUPERVISOR')? 'Wi' : 'Non' }}</p></td>
                                    <td style="text-align: center"><i class="fa fa-{{isRole(user.roles,'ROLE_COLLECTOR')? 'check' : 'times'}}" style="color: {{isRole(user.roles,'ROLE_COLLECTOR')? 'green' : 'red'}};"></i><p style="display: none">{{isRole(user.roles,'ROLE_COLLECTOR')? 'Wi' : 'Non' }}</p></td>
                                    <td>{{user.modificationDate | date: 'dd/MM/yyyy'}}</td>
                                    <td style="text-align: center"><i class="fa fa-{{user.enabled? 'check' : 'times' }}" style="color: {{user.enabled? 'green' : 'red'}} ;"></i><p style="display: none">{{user.enabled? 'Wi' : 'Non' }}</p> </td>
                                    <td>
                                        <a class="btn btn-warning btn-xs" href="/user/1/update/{{user.id}}">
                                            <i class="fa fa-edit"></i> Aktyalize
                                        </a>
                                    </td>
                                    <td>
                                        <a class="btn btn-danger btn-xs" href="/user/1/delete{{user.id}}">
                                            <i class="fa fa-trash-o"></i> Elimine
                                        </a>
                                    </td>
                                    <td>
                                        <a class="btn btn-{{user.enabled? 'primary' : 'default' }} btn-xs" href="/configuration/user/{{user.id}}">
                                            <i class="fa fa-{{user.enabled? 'lock' : 'unlock'}}" aria-hidden="true"></i> {{user.enabled? 'Bloke' : 'Debloke'}}
                                        </a>
                                    </td>
                                </tr>
                            </tbody>
                            <tfoot>
                                <tr>
                                    <th style="width:10%">#</th>
                                    <th style="width:20%">Non</th>
                                    <th style="width:10%">Itilizatè</th>
                                    <th style="width:20%">Vandè</th>
                                    <th style="width:15%">Adm</th>
                                    <th style="width:20%">Sipèvizè</th>
                                    <th style="width:20%">Rekolektè</th>
                                    <th style="width:10%">Kreyasyon</th>
                                    <th style="width:10%; text-align: center">Actif</th>
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

</body>

</html>