<!DOCTYPE html>
<html lang="en" ng-app="lottery" ng-cloak>

<head>
<#include "../header.ftl">
    <link rel="stylesheet"; href="https://unpkg.com/ng-table@2.0.2/bundles/ng-table.min.css">
<#--    <script rel="stylesheet" src="/dist/css/ng-table.min.css"></script>-->


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
    <section id="main-content" ng-controller="bankController">
        <section class="wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h3 class="page-header"><i class="fa fa-eye"></i>Paj pou gade Bank</h3>
                    <ol class="breadcrumb">
                        <li><i class="fa fa-home"></i><a href="/home">Paj Akèy</a></li>
                        <li><i class="fa fa-eye"></i>Bank</li>
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
                                    Bank
                                </div>
                                <div class="col-lg-8" >
                                    <div class="row text-right">
                                        <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                                            <div class="col-lg-12 col-md-12 col-sm-12">
                                                <div class="row">
                                                    <div class="col-lg-6 col-md-6 col-sm-6"></div>
                                                    <div class="col-lg-6 col-md-6 col-sm-6">
                                                        <div class="form-group" style="margin: 10px">
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
                                    </div>
                                </div>
                            </div>
                        </header>
                        <div class="panel-body">
                            <div class="row">
                            <#--style="overflow-y:scroll; height:150px;"-->
                                <div class="col-md-12">
                                    <div class="table-responsive">
<#--                                        <table datatable="ng" dt-options="dtOptions"-->
<#--                                               class="table table-striped table-advance table-hover">-->
                                            <table ng-table="global.tableParams" show-filter="true"
                                               class="table table-striped table-bordered table-striped table-condensed table-hover">
<#--                                           <thead>-->
<#--                                                <tr>-->
<#--                                                    <th>#</th>-->
<#--                                                    <th style="width:15%">Deskripsyon</th>-->
<#--                                                    <th style="width:15%">Nimewo</th>-->
<#--                                                    <th style="width:15%">Adres</th>-->
<#--                                                    <th style="width:15%">Vande</th>-->
<#--                                                    <th style="width:15%">Machin</th>-->
<#--                                                    <th style="width:15%">Dat Kreyasyon</th>-->
<#--                                                    <th style="width:15%; text-align: center">Actif</th>-->
<#--                                                    <th style="width:5%"></th>-->
<#--                                                    <th style="width:5%"></th>-->
<#--                                                    <th style="width:5%"></th>-->

<#--                                                </tr>-->
<#--                                           </thead>-->
<#--                                            <tbody>-->
<#--                                            <tr ng-repeat="bank in banks">-->
                                            <tr ng-repeat="bank in $data track by row.id">
<#--                                                <td>{{start+$index+1}}</td>-->
<#--                                                <td>{{bank.description}}</td>-->
<#--                                                <td>{{bank.serial}}</td>-->
<#--                                                <td>{{bank.address.address}}</td>-->
<#--                                                <td>{{bank.seller.user.name}}</td>-->
<#--                                                <td>{{bank.seller.pos.description}}</td>-->
<#--                                                <td>{{bank.modificationDate | date: 'dd/MM/yyyy' :"America/Port-au-Prince"}}</td>-->
<#--                                                <td style="text-align: center"><i class="fa fa-{{bank.enabled? 'check' : 'times' }}" style="color: {{bank.enabled? 'green' : 'red'}} ;"></i><p style="display: none">{{bank.enabled? 'Wi' : 'Non' }}</p> </td>  -->
<#--                                                -->
                                                <td style="vertical-align: middle;" data-title="'Id'" filter="{Id: 'number'}" sortable="'Id'">{{start+$index+1}}</td>
                                                <td style="vertical-align: middle;" data-title="'Deskripsyon'" filter="{Deskripsyon: 'text'}" sortable="'Deskripsyon'">{{bank.description}}</td>
                                                <td style="vertical-align: middle;" data-title="'Nimewo'" filter="{Nimewo: 'text'}" sortable="'Nimewo'">{{bank.serial}}</td>
                                                <td style="vertical-align: middle;" data-title="'Adres'" filter="{Adres: 'text'}" sortable="'Adres'">{{bank.address.address}}</td>
                                                <td style="vertical-align: middle;" data-title="'Vande'" filter="{Vande: 'text'}" sortable="'Vande'">{{bank.seller.user.name}}</td>
                                                <td style="vertical-align: middle;" data-title="'Machin'" filter="{Machin: 'text'}" sortable="'Machin'">{{bank.seller.pos.description}}</td>
                                                <td style="vertical-align: middle;" data-title="'DatKreyasyon'" filter="{DatKreyasyon: 'text'}" sortable="'Dat Kreyasyon'">{{bank.modificationDate | date: 'dd/MM/yyyy' :"America/Port-au-Prince"}}</td>
                                                <td style="text-align: center; vertical-align: middle" data-title="'Actif'" filter="{ Actif: 'select'}" filter-data="['Actif', 'Bloke']">
                                                    <i class="fa fa-{{bank.enabled? 'check' : 'times' }}" style="color: {{bank.enabled? 'green' : 'red'}} ;"></i><p style="display: none">{{bank.enabled? 'Wi' : 'Non' }}</p> </td>
                                                <td>
                                                    <a class="btn btn-warning btn-xs" href="/bank/update/{{bank.id}}">
                                                        <i class="fa fa-edit"></i> Aktyalize
                                                    </a>
                                                </td>
                                                <td>
                                                    <a class="btn btn-danger btn-xs delete" id="delete" onclick="onDelete(event)" href="/bank/delete/{{bank.id}}">
                                                        <i class="fa fa-trash-o"></i> Elimine
                                                    </a>
                                                </td>
                                                <td>
                                                    <a class="btn btn-{{bank.enabled? 'primary' : 'default' }} btn-xs" id="block" onclick="onBlock(event)" href="/configuration/bank/{{bank.id}}">
                                                        <i class="fa fa-{{bank.enabled? 'lock' : 'unlock'}}" aria-hidden="true"></i> {{bank.enabled? 'Bloke' : 'Debloke'}}
                                                    </a>
                                                </td>
                                            </tr>
<#--                                            </tbody>-->
                                            <tfoot>
                                                <tr>
                                                    <th>#</th>
                                                    <th style="width:15%">Deskripsyon</th>
                                                    <th style="width:15%">Nimewo</th>
                                                    <th style="width:15%">Adres</th>
                                                    <th style="width:15%">Vande</th>
                                                    <th style="width:15%">Machin</th>
                                                    <th style="width:15%">Dat Kreyasyon</th>
                                                    <th style="width:15%; text-align: center">Actif</th>
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
                                    <a class="btn btn-primary" href="/bank/create">
                                        <i class="fa fa-plus-circle"></i> Ajoute Nouvo Bank
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
            message: "Ou preske elimine bank sa, ou deside kontinye?",
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
            message: "Wap reayilize yon aksyon ki ap bloke oubyen debloke bank sa, ou deside kontinye?",
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
<#--<script src="/dist/js/ng-table.min.js"></script>-->

</body>

</html>