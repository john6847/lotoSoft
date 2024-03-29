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
<section id="container" class="" ng-controller="bankController">
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
        <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
          <h3 class="page-header"><i class="fa fa-file-text-o"></i>Fòm Pou Kreye Bank</h3>
          <ol class="breadcrumb">
            <li><i class="fa fa-home"></i><a href="/home">Paj Akèy</a></li>
            <li><i class="fa fa-file-text-o"></i>Kreye Bank</li>
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
        <form class="form-horizontal form-validate" id="bankForm" name="bankForm" action="/bank/create" th:object="${bank}"
              method="post">
          <div class="col-lg-6 col-md-6 col-sm-12 col-xs-12">
            <div class="row">
              <div class="col-lg-12">
                <section class="panel">
                  <header class="panel-heading">
                    Enfomasyon Bank lan
                  </header>
                  <div class="panel-body">
                    <div class="row">
                      <div class="col-md-12">
                        <span class="text-info" style="font-size: 16px;"><strong>Not: Pou Kreye yon Bank ou bezwen Vande ak Machin</strong></span>
                      </div>
                    </div>
                    <hr>

                    <div class="form-group">
                      <label class="col-lg-2 col-md-2 col-sm-2 control-label col-xs-12"
                             for="description">Deskripsyon<span class="required">*</span></label>
                      <div class="col-lg-10 col-md-10 col-sm-10 col-xs-12">
                        <input type="text" class="form-control round-input" placeholder="Antre deskripsyon bank lan" id="description" name="description"
                               minlength="2" required>
                      </div>
                    </div>

                    <div class="form-group">
                      <label class="control-label col-lg-2 col-md-2 col-sm-2 col-xs-12" for="seller">Vandè<span
                                class="required">*</span></label>
                      <div class="col-lg-10 col-md-10 col-sm-10 col-xs-12">
                        <select class="form-control round-input"
                                name="seller"
                                id="seller"
                                data-size="5"
                                ng-model="global.selectedSeller"
                                ng-change="sellerChange(0)"
                                data-none-selected-text="Chwazi Vande a"
                                data-allow-clear="true"
                                data-none-results-text="Vande sa pa egziste"
                                data-placeholder="Chwazi vande la"
                                required>
                          <option value="" selected disabled>---Chwazi yon vandè---</option>
                            <#if sellers??>
                                <#list sellers as seller>
                                  <option value="${seller.id}">${seller.user.name}</option>
                                </#list>
                            </#if>

                        </select>
                      </div>
                    </div>
                    <div class="form-group" ng-if="global.selectedSeller">
                      <label class="col-lg-2 col-md-2 col-sm-2 col-xs-12 control-label" for="pos">Machin</label>
                      <div class="col-lg-10 col-md-10 col-sm-10 col-xs-12">
                        <select class="form-control round-input"
                                name="pos"
                                id="pos"
                                ng-model="global.selectedPos"
                                data-size="5"
                                required>
                          <option value="" selected disabled>- Chwazi yon machin -</option>
                          <option ng-repeat="p in pos track by p.id" value="{{p.id}}">{{p.description}}</option>
                        </select>
                        <span style="color: red;" ng-show="bankForm.pos.$dirty && !global.selectedPos">Ou dwe chwazi yon machin.</span>
                      </div>
                    </div>
                  </div>
                </section>
              </div>
            </div>
          </div>
          <div class="col-lg-6 col-md-6 col-sm-12 col-xs-12">
            <div class="row">
              <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                <section class="panel">
                  <header class="panel-heading">
                    Adrès Bank lan
                  </header>
                  <div class="panel-body">
                    <div class="form-group">
                      <label class="control-label col-lg-2 col-md-2 col-sm-2 col-xs-12" for="region">Depatman</label>
                      <div class="col-lg-10 col-md-10 col-sm-10 col-xs-12">
                        <input type="text"
                               class="form-control round-input"
                               name="region"
                               id="region"
                               placeholder="Antre non depatman an"
                               maxlength="100">
                      </div>
                    </div>

                    <div class="form-group">
                      <label class="control-label col-lg-2 col-md-2 col-sm-2 col-xs-12" for="city">Vil</label>
                      <div class="col-lg-10 col-md-10 col-sm-10 col-xs-12">
                        <input type="text"
                               class="form-control round-input"
                               name="city"
                               id="city"
                               placeholder="Antre non vil la"
                               maxlength="100">
                      </div>
                    </div>

                    <div class="form-group">
                      <label class="control-label col-lg-2 col-md-2 col-sm-2 col-xs-12" for="sector">Sektè</label>
                      <div class="col-lg-10 col-md-10 col-sm-10 col-xs-12">
                        <input type="text"
                               class="form-control round-input"
                               name="sector"
                               id="sector"
                               placeholder="Antre non sektè a"
                               maxlength="100">
                      </div>
                    </div>

                    <div class="form-group">
                      <label class="control-label col-lg-2 col-md-2 col-sm-2 col-xs-12" for="street">Rout</label>
                      <div class="col-lg-10 col-md-10 col-sm-10 col-xs-12">
                        <input type="text"
                               class="form-control round-input"
                               name="street"
                               id="street"
                               placeholder="Antre non rout la"
                               maxlength="250">
                      </div>
                    </div>
                  </div>
                </section>
              </div>
            </div>
          </div>

          <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
            <div class="breadcrumb" style="height: auto;">
              <div class="row">
                <div class="form-group" style="margin-bottom: 10px;">
                  <div class="col-lg-6 col-md-9 col-sm-12 col-xs-12 col-lg-offset-6 col-md-offset-3 col-xs-12">
                    <div class="col-lg-4 col-md-4 col-sm-4 col-xs-12">
                      <a href="/home"
                         class="btn btn-warning form-control">
                         <i class="fa fa-arrow-left"></i>
                         Anile
                      </a>
                    </div>
                    <div class="col-lg-4 col-md-4 col-sm-4 col-xs-12">
                      <button type="reset"
                              class="btn btn-danger form-control"
                              title="Efase tout done bank lan">
                        <i class="fa fa-times"></i>
                        Reyajiste
                      </button>
                    </div>
                    <div class="col-lg-4 col-md-4 col-sm-4 col-xs-12">
                      <button class="btn btn-primary form-control"
                              type="submit"
                              ng-disabled="bankForm.pos.$invalid || bankForm.seller.$invalid"
                              title="Anrejistre tout done bank la">
                        <i class="fa fa-save"></i>
                        Anrejistre
                      </button>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </form>
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
    <#include "../loader.ftl">

    <#include "../scripts.ftl">
  <script type="text/javascript">
      $('.selectpicker').selectpicker();
      $.fn.selectpicker.Constructor.BootstrapVersion = '4';

      $("#bankForm").on("submit", function () {
          $("#custom-loader").fadeIn();
      });//submit

  </script>


</body>

</html>