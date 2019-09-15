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
    <section id="main-content" ng-controller="posController">
        <section class="wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h3 class="page-header"><i class="fa fa-eye"></i>Paj pou gade Machin</h3>
                    <ol class="breadcrumb">
                        <li><i class="fa fa-home"></i><a href="/home">Paj Akèy</a></li>
                        <li><i class="fa fa-eye"></i>Machin</li>
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
                            <div class="row">
                                <div class="col-lg-4">
                                    Machin
                                </div>
                                <div class="col-lg-8">
                                    <div class="row text-right">
                                        <div class="col-lg-12 col-md-12 col-sm-12">
                                            <div class="row">
                                                <div class="col-lg-6 col-md-6 col-sm-6"></div>
                                                <div class="col-lg-6 col-md-6 col-sm-6">
                                                    <div class="form-group" style="margin: 10px">
                                                        <select class="form-control m-bot15 selectpicker"
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
                                </div>
                            </div>
                        </header>
                        <div class="panel-body">
                            <div class="row">
                            <#--style="overflow-y:scroll; height:150px;"-->
                                <div class="col-md-12">
                                    <div class="table-responsive">
                                        <table datatable="ng" dt-options="global.dtOptions"
                                               class="table table-striped table-advance table-hover" ng-init="fetchAllPos()">
                                           <thead>
                                                <tr>
                                                    <th style="width: 10%">#</th>
                                                    <th style="width:15%">Deskripsyon</th>
                                                    <th style="width:15%">Serial</th>
                                                    <th style="width:15%">Dat Kreyasyon</th>
                                                    <th style="width:15%; text-align: center">Actif</th>
                                                    <th style="width:15%; text-align: center">Bloke</th>
                                                    <th style="width:5%"></th>
                                                    <th style="width:5%"></th>
                                                    <th style="width:5%"></th>

                                                </tr>
                                           </thead>
                                            <tbody>
                                            <tr ng-repeat="pos in global.pos">
                                                <td>{{start+$index+1}}</td>
                                                <td>{{pos.description}}</td>
                                                <td>{{pos.serial}}</td>
                                                <td>{{pos.modificationDate | date: 'dd/MM/yyyy'}}</td>
                                                <td style="text-align: center"><i class="fa fa-{{pos.enabled? 'check' : 'times' }}" style="color: {{pos.enabled? 'green' : 'red'}} ;"></i><p style="display: none">{{pos.enabled? 'Wi' : 'Non' }}</p> </td>
                                                <td style="text-align: center"><i class="fa fa-{{pos.enabled? 'times' : 'check' }}" style="color: {{pos.enabled? 'red' :'green' }} ;"></i><p style="display: none">{{pos.enabled? 'Wi' : 'Non' }}</p></td>
                                                <td>
                                                    <a class="btn btn-warning btn-xs" href="/pos/update/{{pos.id}}">
                                                        <i class="fa fa-edit"></i> Aktyalize
                                                    </a>
                                                </td>
                                                <td>
                                                    <a class="btn btn-danger btn-xs delete" id="delete" onclick="onDelete(event)" href="/pos/delete/{{pos.id}}">
                                                        <i class="fa fa-trash-o"></i> Elimine
                                                    </a>
                                                </td>
                                                <td>
                                                    <a class="btn btn-{{pos.enabled? 'primary' : 'default' }} btn-xs" id="block" onclick="onBlock(event)" href="/configuration/pos/{{pos.id}}">
                                                        <i class="fa fa-{{pos.enabled? 'lock' : 'unlock'}}" aria-hidden="true"></i> {{pos.enabled? 'Bloke' : 'Debloke'}}
                                                    </a>
                                                </td>
                                            </tr>
                                            </tbody>
                                            <tfoot>
                                                <tr>
                                                    <th style="width: 10%">#</th>
                                                    <th style="width:15%">Deskripsyon</th>
                                                    <th style="width:15%">Serial</th>
                                                    <th style="width:15%">Dat Kreyasyon</th>
                                                    <th style="width:15%; text-align: center">Actif</th>
                                                    <th style="width:15%; text-align: center">Bloke</th>
                                                    <th style="width:5%"></th>
                                                    <th style="width:5%"></th>
                                                    <th style="width:5%"></th>
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
                                    <a class="btn btn-primary" href="/pos/create">
                                        <i class="fa fa-plus-circle"></i> Ajoute Nouvo Machin
                                    </a>
                                </div>
                            </div>
                        </footer>
                    </section>
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
    function onDelete (e) {
        if(!responseDelete)
            e.preventDefault();
        bootbox.confirm({
            message: "Wap reayilize yon aksyon ki ap bloke oubyen debloke machin sa, ou deside kontinye",
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
            message: "Ou preske bloke machin sa, ou deside kontinye?",
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