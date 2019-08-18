<!DOCTYPE html>
<html lang="en"
      xmlns="http://www.w3.org/1999/xhtml"
      xmlns:th="http://www.thymeleaf.org"
      xmlns:sec="http://www.thymeleaf.org/thymeleaf-extras-springsecurity3">

<head>

<#include "../header.ftl">
    <link href="/dist/select2/css/select2.min.css" rel="stylesheet"/>

    <style>
        .select2 {
            width: 100% !important;
        }

        .select2-search__field{
            width: 100% !important;
        }

    </style>
</head>

<body ng-app="lottery" ng-cloak>
<!-- container section start -->
<section id="container" class="" ng-controller="drawController">
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
                    <h3 class="page-header"><i class="fa fa-file-text-o"></i>Fòm pou kreye tiraj</h3>
                    <ol class="breadcrumb">
                        <li><i class="fa fa-home"></i><a href="/home">Paj Akèy</a></li>
                        <li><i class="fa fa-file-text-o"></i>Kreye Tiraj</li>
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

                            <form class="form-horizontal" action="/draw/create" th:object="${draw}" method="post">
                                <div class="form-group">
                                    <label class="col-lg-2 col-md-2 col-sm-2 control-label col-xs-12" for="drawDate">Dat<span class="required">*</span></label>
                                    <div class="col-lg-10 col-md-10 col-sm-10 col-xs-12">
                                        <input type="date" name="drawDate" id="drawDate" class="form-control" required>
                                    </div>
                                </div>


                                <div class="form-group">
                                    <label class="control-label col-lg-2 col-md-2 col-sm-2 col-xs-12" for="shift">Tip Tiraj<span class="required">*</span></label>
                                    <div class="col-lg-10 col-md-10 col-sm-10 col-xs-12">
                                        <select class="form-control selectpicker"
                                                name="shift"
                                                id="shift"
                                                data-size="5"
                                                data-none-selected-text="Chwazi Tip Tiraj la"
                                                data-allow-clear="true"
                                                data-none-results-text="Tip tiraj sa pa egziste"
                                                data-placeholder="Chwazi tip tiraj la"
                                                required>
                                                <#if shifts??>
                                                    <#list shifts as shift>
                                                        <option value="${shift.id}">${shift.name}</option>
                                                    </#list>
                                                </#if>

                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-lg-2 col-md-2 col-sm-2 col-xs-12" for="numberThreeDigits">Loto 3 chif <span class="required">*</span></label>
                                    <div class="col-lg-10 col-md-10 col-sm-10 col-xs-12">
                                        <select class="form-control selectpicker"
                                                name="numberThreeDigits"
                                                id="numberThreeDigits"
                                                data-allow-clear="true"
                                                data-none-results-text="Nimewo sa pa egziste"
                                                data-placeholder="Chwazi yon loto 3 chif"
                                                data-size="7"
                                                required>
                                            <#if numberThreeDigits??>
                                                <#list numberThreeDigits as numberThreeDigit>
                                                    <option value="${numberThreeDigit.id}">${numberThreeDigit.numberInStringFormat}</option>
                                                </#list>
                                            </#if>
                                        </select>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="control-label col-lg-2 col-md-2 col-sm-2 col-xs-12" for="firstDraw">Bòlet <span class="required">*</span></label>
                                    <div class="col-lg-4 col-md-4 col-sm-4 col-xs-12">
                                        <select class="form-control selectpicker"
                                                name="firstDraw"
                                                id="firstDraw"
                                                data-allow-clear="true"
                                                data-placeholder="Chwazi premye lo a"
                                                data-size="7"
                                                required>
                                            <#if numberTwoDigits??>
                                                <#list numberTwoDigits as numberTwoDigit>
                                                    <option value="${numberTwoDigit.id}">${numberTwoDigit.numberInStringFormat}</option>
                                                </#list>
                                            </#if>
                                        </select>
                                    </div>
                                    <div class="col-lg-3 col-md-3 col-sm-3 col-xs-12 ">
                                        <select class="form-control selectpicker"
                                                name="secondDraw"
                                                id="secondDraw"
                                                data-allow-clear="true"
                                                data-placeholder="Chwazi dezyem lo a"
                                                data-size="7"
                                                required>
                                            <#if numberTwoDigits??>
                                                <#list numberTwoDigits as numberTwoDigit>
                                                    <option value="${numberTwoDigit.id}">${numberTwoDigit.numberInStringFormat}</option>
                                                </#list>
                                            </#if>
                                        </select>
                                    </div>
                                    <div class="col-lg-3 col-md-3 col-sm-3 col-xs-12">
                                        <select class="form-control selectpicker"
                                                name="thirdDraw"
                                                id="thirdDraw"
                                                data-allow-clear="true"
                                                data-placeholder="Chwazi twazyem lo a"
                                                data-size="7"
                                                required>
                                            <#if numberTwoDigits??>
                                                <#list numberTwoDigits as numberTwoDigit>
                                                    <option value="${numberTwoDigit.id}">${numberTwoDigit.numberInStringFormat}</option>
                                                </#list>
                                            </#if>
                                        </select>
                                    </div>
                                </div>


                                <div class="form-group">
                                    <div class="col-lg-6 col-md-6 col-sm-12 col-lg-offset-6 col-md-offset-6 col-xs-12">
                                        <div class="row">
                                            <div class="col-lg-6 col-md-6 col-sm-12 col-xs-12">
                                                <button class="btn btn-danger form-control"
                                                        type="reset"
                                                        title="Efase tout done tiraj sa">
                                                        <i class="fa fa-times" aria-hidden="true"></i>
                                                        Reyajiste
                                                </button>
                                            </div>
                                            <div class="col-lg-6 col-md-6 col-sm-12 col-xs-12">
                                                <button class="btn btn-primary form-control"
                                                        type="submit"
                                                        id="save"
                                                        onclick="onSave(event)"
                                                        title="Anrejistre tout done tiraj sa">
                                                        <i class="fa fa-save"></i>
                                                        Anrejistre
                                                </button>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                            </form>
                        </div>
                    </section>
                </div>
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
<!-- container section end -->
<#include "../scripts.ftl">

<script>
    $(document).ready(function() {
        $('.select2').select2();

        $(".select2").on("select2:select", function (evt) {
            var element = evt.params.data.element;
            var $element = $(element);

            $element.detach();
            $(this).append($element);
            $(this).trigger("change");
        });
    });
</script>

<script>
    var response = false;
    function onSave (e) {
        var date = document.getElementById("drawDate").value;
        var shift = document.getElementById("shift");
        shift = shift.options[shift.selectedIndex].text;
        var loto3chif = document.getElementById("numberThreeDigits");
        loto3chif = loto3chif.options[loto3chif.selectedIndex].text;
        var firstDraw = document.getElementById("firstDraw");
        firstDraw = firstDraw.options[firstDraw.selectedIndex].text;
        var secondDraw = document.getElementById("secondDraw");
        secondDraw = secondDraw.options[secondDraw.selectedIndex].text;
        var thirdDraw = document.getElementById("thirdDraw");
        thirdDraw = thirdDraw.options[thirdDraw.selectedIndex].text;

        if(!response)
            e.preventDefault();
        bootbox.confirm({
            message: "<h4 class='lead'>Verifye enfomasyon tiraj la byen avan ou anrejistrel</h4><hr>"+
                    "<div class='row'>"+
                        "<div class='col-md-6'><p style='font-size:1.5em;font-weight:bold'>Dat: <span class='lead'>"+ date +"</span></p></div>" +
                        "<div class='col-md-6'><p style='font-size:1.5em;font-weight:bold'>Tiraj: <span class='lead'>"+ shift +"</span></p></div>" +
                    "</div>"+
                    "<div class='row'>"+
                        "<div class='col-md-6'>" +
                            "<p style='font-size:1.5em;font-weight:bold'>" +
                                "Loto 3 chif: <span class='lead label label-primary'>"+ loto3chif +"</span>" +
                            "</p>"+
                        "</div>"+
                        "<div class='col-md-6'>" +
                            "<p style='font-size:1.5em; font-weight:bold'>Bolet: "+
                                "<span class='lead label label-warning' style='margin-right:5px'>"+ firstDraw +"</span>"+
                                "<span class='lead label label-success' style='margin-right:5px'>"+ secondDraw +"</span>"+
                                "<span class='lead label label-info' style='margin-right:5px'>"+ thirdDraw +"</span>" +
                            "</p>"+
                        "</div>" +
                    "</div>",
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
                    response = true;
                    document.getElementById('save').click();
                }
            }
        });
    }
</script

<script type = "text/javascript" >
    $('.selectpicker').selectpicker();
    $.fn.selectpicker.Constructor.BootstrapVersion = '4';
</script>

</body>

</html>