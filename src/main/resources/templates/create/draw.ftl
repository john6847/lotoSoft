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
                                    <label class="col-lg-2 col-md-2 col-sm-2 control-label">Dat<span class="required">*</span></label>
                                    <div class="col-lg-10 col-md-10 col-sm-10">
                                        <input type="date" name="drawDate" class="form-control" required>
                                    </div>
                                </div>


                                <div class="form-group">
                                    <label class="control-label col-lg-2 col-md-2 col-sm-2" for="inputSuccess">Tip Tiraj<span class="required">*</span></label>
                                    <div class="col-lg-10 col-md-10 col-sm-10">
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
                                    <label class="control-label col-lg-2 col-md-2 col-sm-2" for="inputSuccess">Loto 3 chif <span class="required">*</span></label>
                                    <div class="col-lg-10 col-md-10 col-sm-10">
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
                                    <label class="control-label col-lg-2 col-md-2 col-sm-2" for="inputSuccess">Bòlet <span class="required">*</span></label>
                                    <div class="col-lg-10 col-md-10 col-sm-10">
                                        <select class="form-control select2"
                                                name="numberTwoDigits"
                                                id="numberTwoDigits"
                                                data-allow-clear="true"
                                                data-placeholder="Chwazi twa boul bòlet"
                                                data-maximum-selection-length ="3"
                                                data-tags="true"
                                                data-multiple="true"
                                                multiple="multiple"
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
                                    <div class="col-lg-6 col-md-6 col-sm-12 col-lg-offset-6 col-md-offset-6">
                                        <div class="row">
                                            <div class="col-lg-6 col-md-6 col-sm-12 ">
                                                <button class="btn btn-danger form-control"
                                                        type="reset"
                                                        title="Efase tout done tiraj sa">
                                                        <i class="fa fa-times" aria-hidden="true"></i>
                                                        Reyajiste
                                                </button>
                                            </div>
                                            <div class="col-lg-6 col-md-6 col-sm-12">
                                                <button class="btn btn-primary form-control"
                                                        type="submit"
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

<script type = "text/javascript" >
    $('.selectpicker').selectpicker();
    $.fn.selectpicker.Constructor.BootstrapVersion = '4';
</script>

</body>

</html>