<!DOCTYPE html>
<html lang="en"
      xmlns="http://www.w3.org/1999/xhtml"
      xmlns:th="http://www.thymeleaf.org"
      xmlns:sec="http://www.thymeleaf.org/thymeleaf-extras-springsecurity3">

<head>
    <#include "../header.ftl">
</head>

<body ng-app="lottery" ng-cloak>
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
    <section id="main-content">
        <section class="wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h3 class="page-header"><i class="fa fa-file"></i>Rapò Vant</h3>
                </div>
            </div>
            <div class="row">
                <div class="col-lg-12 col-xs-12 col-xl-12 col-md-12 col-sm-12" ng-if="shiftField.message">
                    <div class="alert alert-success" role="alert" ng-if="shiftField.message.saved">
                        {{shiftField.message.message}}
                    </div>
                    <div class="alert alert-danger" role="alert" ng-if="!shiftField.message.saved">
                        {{shiftField.message.message}}
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-lg-3">
                    <md-content layout-padding>
                        <md-card>
                            <div layout-gt-xs="row" layout-lt-lg="row" layout-lt-md="row">
                                <md-input-container>
                                    <label>Soti nan</label>
                                    <md-datepicker ng-model="global.beginDate" aria-label="Antre dat komansman"></md-datepicker>
                                </md-input-container>
                            </div>
                            <div layout-gt-xs="row" layout-lt-lg="row" layout-lt-md="row">
                                <md-input-container>
                                    <label>Pou rive</label>
                                    <md-datepicker ng-model="global.endDate" aria-label="Antre dat komansman"></md-datepicker>
                                </md-input-container>
<#--                                https://material.angularjs.org/latest/demo/input-->
                            </div>
                            <div layout-gt-xs="row" layout-lt-lg="row" layout-lt-md="row">
                                <div  flex-gt-xs>
                                    <md-input-container>
                                        <label>Vande</label>
                                        <md-select ng-model="selectType.type" ng-change="changeSelectType()">
    <#--                                        ng-repeat="type in selectType.types track by type.id" ng-value="{{type.id}}"-->
                                            <md-option >
    <#--                                            {{type.name}}-->
                                            </md-option>
                                        </md-select>
                                    </md-input-container>
                                </div>
                                <div  flex-gt-xs>
                                    <md-input-container>
                                        <label>Tiraj</label>
                                        <md-select ng-model="selectType.type" ng-change="changeSelectType()">
    <#--                                        ng-repeat="type in selectType.types track by type.id" ng-value="{{type.id}}"-->
                                            <md-option >
    <#--                                            {{type.name}}-->
                                            </md-option>
                                        </md-select>
                                    </md-input-container>
                                </div>
                            </div>
                        </md-card>
                    </md-content>
                </div>
                <div class="col-lg-9">
                    <div ng-cloak>
                        <md-content>
                            <md-tabs md-dynamic-height md-border-bottom>
                                <md-tab label="Vant Total">
                                    <md-content class="md-padding">
                                        <h1 class="md-display-2">Vant Total</h1>
                                        <div layout-lt-lg="row">
                                            <div class="table-responsive">
                                                <table class="table">
                                                    <thead>
                                                    <tr>
                                                        <th>Vande</th>
                                                        <th>Vant Total</th>
                                                        <th>Pousantaj</th>
                                                        <th>Vant ne</th>
                                                        <th>Gany</th>
                                                        <th>Rezilta</th>
                                                    </tr>
                                                    </thead>
                                                    <tbody>
                                                    <tr>
                                                        <td>Jerome</td>
                                                        <td>5000.00</td>
                                                        <td>590.00</td>
                                                        <td>4910.00</td>
                                                        <td>3900.00</td>
                                                        <td class="danger">-1010.00</td>
                                                    </tr>
                                                    <tr>
                                                        <td>Ti Pol</td>
                                                        <td>9456.00</td>
                                                        <td>940.00</td>
                                                        <td>8516.00</td>
                                                        <td>200.00</td>
                                                        <td class="success">8316.00</td>
                                                    </tr> <tr>
                                                        <td>Noy</td>
                                                        <td>5000.00</td>
                                                        <td>590.00</td>
                                                        <td>4910.00</td>
                                                        <td>3900.00</td>
                                                        <td class="success">1010.00</td>
                                                    </tr>
                                                    <tr>
                                                        <td>Mary</td>
                                                        <td>9456.00</td>
                                                        <td>940.00</td>
                                                        <td>8516.00</td>
                                                        <td>200.00</td>
                                                        <td class="success">8316.00</td>
                                                    </tr>
                                                    <tr>
                                                        <td>July</td>
                                                        <td>4910.00</td>
                                                        <td>3900.00</td>
                                                        <td>1010.00</td>
                                                        <td>590.00</td>
                                                        <td class="success">4910.00</td>
                                                    </tr>
                                                    </tbody>
                                                    <tfoot>
                                                    <td class="warning" style="font-weight: bold;" >TOTAL</td>
                                                    <td class="warning" style="font-weight: bold;" >27000.00</td>
                                                    <td class="warning" style="font-weight: bold;" >13000.00</td>
                                                    <td class="warning" style="font-weight: bold;" >18888.00</td>
                                                    <td class="warning" style="font-weight: bold;" >15000.00</td>
                                                    <td class="warning" style="font-weight: bold;" >49190.00</td>
                                                    </tfoot>
                                                </table>
                                            </div>
                                        </div>
                                    </md-content>
                                </md-tab>
                                <md-tab label="two">
                                    <md-content class="md-padding">
                                        <h1 class="md-display-2">Tab Two</h1>
                                        <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla venenatis ante augue.
                                            Phasellus volutpat neque ac dui mattis vulputate. Etiam consequat aliquam cursus. In
                                            sodales pretium ultrices. Maecenas lectus est, sollicitudin consectetur felis nec,
                                            feugiat ultricies mi. Aliquam erat volutpat. Nam placerat, tortor in ultrices porttitor,
                                            orci enim rutrum enim, vel tempor sapien arcu a tellus. Vivamus convallis sodales ante
                                            varius gravida. Curabitur a purus vel augue ultrices ultricies id a nisl. Nullam
                                            malesuada consequat diam, a facilisis tortor volutpat et. Sed urna dolor, aliquet vitae
                                            posuere vulputate, euismod ac lorem. Sed felis risus, pulvinar at interdum quis,
                                            vehicula sed odio. Phasellus in enim venenatis, iaculis tortor eu, bibendum ante. Donec
                                            ac tellus dictum neque volutpat blandit. Praesent efficitur faucibus risus, ac auctor
                                            purus porttitor vitae. Phasellus ornare dui nec orci posuere, nec luctus mauris
                                            semper.</p>
                                        <p>Morbi viverra, ante vel aliquet tincidunt, leo dolor pharetra quam, at semper massa
                                            orci nec magna. Donec posuere nec sapien sed laoreet. Etiam cursus nunc in condimentum
                                            facilisis. Etiam in tempor tortor. Vivamus faucibus egestas enim, at convallis diam
                                            pulvinar vel. Cras ac orci eget nisi maximus cursus. Nunc urna libero, viverra sit amet
                                            nisl at, hendrerit tempor turpis. Maecenas facilisis convallis mi vel tempor. Nullam
                                            vitae nunc leo. Cras sed nisl consectetur, rhoncus sapien sit amet, tempus sapien.</p>
                                        <p>Integer turpis erat, porttitor vitae mi faucibus, laoreet interdum tellus. Curabitur
                                            posuere molestie dictum. Morbi eget congue risus, quis rhoncus quam. Suspendisse vitae
                                            hendrerit erat, at posuere mi. Cras eu fermentum nunc. Sed id ante eu orci commodo
                                            volutpat non ac est. Praesent ligula diam, congue eu enim scelerisque, finibus commodo
                                            lectus.</p>
                                    </md-content>
                                </md-tab>
                                <md-tab label="three">
                                    <md-content class="md-padding">
                                        <h1 class="md-display-2">Tab Three</h1>
                                        <p>Integer turpis erat, porttitor vitae mi faucibus, laoreet interdum tellus. Curabitur
                                            posuere molestie dictum. Morbi eget congue risus, quis rhoncus quam. Suspendisse vitae
                                            hendrerit erat, at posuere mi. Cras eu fermentum nunc. Sed id ante eu orci commodo
                                            volutpat non ac est. Praesent ligula diam, congue eu enim scelerisque, finibus commodo
                                            lectus.</p>
                                    </md-content>
                                </md-tab>
                            </md-tabs>
                        </md-content>
                    </div>
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
<!-- javascripts -->
<#include "../scripts.ftl">
<script type="text/javascript">
    $('.selectpicker').selectpicker();
</script>

<script type="text/javascript">
    $('.timepicker').timepicker();
</script>


</body>

</html>