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
    <section id="main-content" ng-controller="bankController">
        <section class="wrapper" ng-init="init(true)">
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
                <div class="col-lg-12">
                    <section class="panel">
                        <header class="panel-heading">Bank</header>
                        <div class="panel-body">
                            <div class="row">
                                <div class="col-md-12">
                                    <div class="table-responsive">
                                        <table ng-table="global.tableParams"
                                               class="table table-striped table-bordered table-striped table-condensed table-hover">
                                            <tr ng-repeat="row in $data track by row.id">
                                                <td style="vertical-align: middle;" data-title="'Id'">{{start+$index+1}}</td>
                                                <td style="vertical-align: middle;" data-title="'Deskripsyon'">{{row.description}}</td>
                                                <td style="vertical-align: middle;" data-title="'Nimewo'">{{row.serial}}</td>
                                                <td style="vertical-align: middle;" data-title="'Adres'">{{row.address.address}}</td>
                                                <td style="vertical-align: middle;" data-title="'Vande'">{{row.seller.user.name}}</td>
                                                <td style="vertical-align: middle;" data-title="'Machin'">{{row.seller.pos.description}}</td>
                                                <td style="vertical-align: middle;" data-title="'DatKreyasyon'">{{row.modificationDate | date: 'short' :"America/Port-au-Prince"}}</td>
                                                <td style="text-align: center; vertical-align: middle" data-title="'Actif'">
                                                    <i class="fa fa-{{row.enabled? 'check' : 'times' }}" style="color: {{row.enabled? 'green' : 'red'}} ;"></i><p style="display: none">{{row.enabled? 'Wi' : 'Non' }}</p> </td>
                                                <td  style="vertical-align: middle;text-align: center;" data-title="'Aktyalize'">
                                                    <a class="btn btn-warning btn-xs load" href="/bank/update/{{row.id}}">
                                                        <i class="fa fa-edit"></i> Aktyalize
                                                    </a>
                                                </td>
                                                <td  style="vertical-align: middle;text-align: center;" data-title="'Elimine'">
                                                    <a class="btn btn-danger btn-xs delete" id="delete" onclick="onDelete(event)" href="/bank/delete/{{row.id}}">
                                                        <i class="fa fa-trash-o"></i> Elimine
                                                    </a>
                                                </td>
                                                <td style="vertical-align: middle;text-align: center;" data-title="'Bloke/Debloke'">
                                                    <a class="btn btn-{{row.enabled? 'primary' : 'default' }} btn-xs" id="block" onclick="onBlock(event)" href="/configuration/bank/{{row.id}}">
                                                        <i class="fa fa-{{row.enabled? 'lock' : 'unlock'}}" aria-hidden="true"></i> {{row.enabled? 'Bloke' : 'Debloke'}}
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
                                    <a class="btn btn-primary load" href="/bank/create">
                                        <i class="fa fa-plus-circle"></i> Ajoute Nouvo Bank
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

<#include "../loader.ftl">

<#include "../scripts.ftl">
<script>

    $(".load").on("click", function(){
        $("#custom-loader").fadeIn();
    });//submit

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
                    $("#custom-loader").fadeIn();
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
                    $("#custom-loader").fadeIn();
                }
            }
        });
    }
</script>

</body>

</html>