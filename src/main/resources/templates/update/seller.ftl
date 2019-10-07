<!DOCTYPE html>
<html lang="en"
      xmlns="http://www.w3.org/1999/xhtml"
      xmlns:th="http://www.thymeleaf.org"
      xmlns:sec="http://www.thymeleaf.org/thymeleaf-extras-springsecurity3">

<head>
    <#include "../header.ftl">
  <link href="/dist/select2/css/select2.min.css" rel="stylesheet"/>
</head>

<body ng-app="lottery" ng-cloak>

  <!-- container section start -->
  <section id="container" class="" ng-controller="sellerController">
    <!--nav start-->
      <#include "../nav.ftl" >
    <!--header end-->

    <!--sidebar start-->
    <aside>
        <#include "../sidebar.ftl">
    </aside>
    <!--sidebar end-->

    <!--main content start-->
    <section id="main-content" ng-init="init(false)">
      <section class="wrapper">
        <div class="row">
          <div class="col-lg-12">
            <h3 class="page-header"><i class="fa fa-edit"></i>Fòm Pou Aktyalize Vandè</h3>
            <ol class="breadcrumb">
              <li><i class="fa fa-home"></i><a href="/home">Paj Akèy</a></li>
              <li><i class="fa fa-edit"></i>Vandè</li>
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
                Vandè
              </header>
              <div class="panel-body">
                <form class="form-horizontal" id="sellerForm" action="/seller/update" th:object="${seller}" method="post">
                  <input type="hidden" name="id" id="id" value="${seller.id}">

                    <#if isParentSeller == false>
                      <div class="form-group funkyradio">
                        <div class="col-sm-offset-2 col-sm-offset-2 col-sm-offset-2 col-lg-3 col-md-3 col-sm-3 col-xs-12 funkyradio-info">
                            <#if seller.groups??>
                              <span style="display: none" ng-init="haveGroup = true"></span>
                              <input type="checkbox" name="haveAGroup" id="haveAGroup" checked ng-model="haveGroup">
                            <#else>
                              <input type="checkbox" name="haveAGroup" id="haveAGroup" ng-model="haveGroup">
                              <span style="display: none" ng-init="haveGroup = false"></span>
                            </#if>
                          <label for="haveAGroup">Fè pati de yon gwoup</label>
                        </div>
                      </div>

                      <div class="form-group" ng-if="haveGroup">
                        <label class="col-lg-2 col-md-2 col-sm-2 control-label col-xs-12" for="groups">Gwoup</label>
                        <div class="col-lg-10 col-md-10 col-sm-10 col-xs-12">
                          <select class="form-control round-input"
                                  name="groups"
                                  data-live-search="true"
                                  data-size="5"
                                  data-none-selected-text="Chwazi yon Gwoup pou vandè a"
                                  data-none-results-text="Gwoup sa pa egziste"
                                  data-placeholder="Chwazi Gwoup la"
                                  id="groups">
                              <#if allGroups??>
                                  <#list allGroups as g>
                                      <#if seller.groups??>
                                          <#if g.id == seller.groups.id>
                                            <option value="${g.id}" selected>${g.description}</option>
                                          <#else>
                                            <option value="${g.id}">${g.description}</option>
                                          </#if>
                                      <#else>
                                        <option value="${g.id}">${g.description}</option>
                                      </#if>
                                  </#list>
                              </#if>
                          </select>
                        </div>
                      </div>

                    </#if>


                  <div class="form-group funkyradio">
                    <div class="col-sm-offset-2 col-sm-offset-2 col-sm-offset-2 col-lg-3 col-md-3 col-sm-3 col-xs-12 funkyradio-info">
                        <#if seller.paymentType == 0>
                          <span style="display: none" ng-init="useMonthlyPayment = true"></span>
                          <input type="checkbox" name="useMonthlyPayment" id="useMonthlyPayment" checked
                                 ng-model="useMonthlyPayment">
                        <#else>
                          <input type="checkbox" name="useMonthlyPayment" id="useMonthlyPayment"
                                 ng-model="useMonthlyPayment">
                          <span style="display: none" ng-init="useMonthlyPayment = false"></span>
                        </#if>
                      <label for="useMonthlyPayment">Ititlize pèman pa mwa</label>
                    </div>
                  </div>

                  <div class="form-group" ng-if="!useMonthlyPayment">
                    <label class="col-lg-2 col-md-2 col-sm-2 control-label col-xs-12" for="percentageCharged">Pousantaj
                      <span class="required">*</span></label>
                    <div class="col-lg-10 col-md-10 col-sm-10 col-xs-12 input-group">
                      <input type="number" class="form-control round-input" name="percentageCharged"
                             value="${seller.percentageCharged}" id="percentageCharged">
                      <span class="input-group-addon">%</span>
                    </div>
                  </div>
                  <div class="form-group" ng-if="useMonthlyPayment">
                    <label class="col-lg-2 col-md-2 col-sm-2 control-label col-xs-12" for="amountCharged">Peman chak mwa
                      <span class="required">*</span></label>
                    <div class="col-lg-10 col-md-10 col-sm-10 col-xs-12 input-group">
                      <input type="number" class="form-control round-input" name="amountCharged"
                             value="${seller.amountCharged?c}" id="amountCharged">
                      <span class="input-group-addon">HTG</span>
                    </div>
                  </div>

                  <div class="form-group">
                    <div class="col-lg-6 col-md-9 col-sm-12 col-xs-12 col-lg-offset-6 col-md-offset-3 col-xs-12">
                      <div class="row">
                        <div class="col-lg-4 col-md-4 col-sm-4 col-xs-12">
                          <a href="/home"
                             class="btn btn-warning form-control">
                            <i class="fa fa-arrow-left"></i>
                            Anile
                          </a>
                        </div>
                        <div class="col-lg-4 col-md-4 col-sm-12 col-xs-12">
                          <button class="btn btn-danger form-control"
                                  type="reset"
                                  title="Efase tout done tiraj sa">
                            <i class="fa fa-times"></i>
                            Reyajiste
                          </button>
                        </div>
                        <div class="col-lg-4 col-md-4 col-sm-12 col-xs-12">
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
  </section>
  <#include "../loader.ftl">

  <!-- container section end -->
  <#include "../scripts.ftl">

  <script>
      $(document).ready(function () {
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


  <script type="text/javascript">
      $('.selectpicker').selectpicker();

      $.fn.selectpicker.Constructor.BootstrapVersion = '4';

      $("#sellerForm").on("submit", function () {
          $("#custom-loader").fadeIn();
      });//sumbmit

  </script>
</body>

</html>