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
                            <div layout-gt-xs="row">
                                <md-input-container>
                                    <label>Enter date</label>
                                    <md-datepicker ng-model="user.submissionDate" aria-label="Enter date"></md-datepicker>
                                </md-input-container>
<#--                                https://material.angularjs.org/latest/demo/input-->
                            </div>
                        </md-card>
                    </md-content>
                </div>
                <div class="col-lg-9">
                    <div ng-cloak>
                        <md-content>
                            <md-tabs md-dynamic-height md-border-bottom>
                                <md-tab label="one">
                                    <md-content class="md-padding">
                                        <h1 class="md-display-2">Tab One</h1>
                                        <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla venenatis ante augue.
                                            Phasellus volutpat neque ac dui mattis vulputate. Etiam consequat aliquam cursus. In
                                            sodales pretium ultrices. Maecenas lectus est, sollicitudin consectetur felis nec,
                                            feugiat ultricies mi.</p>
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