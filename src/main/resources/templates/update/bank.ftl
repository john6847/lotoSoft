<!DOCTYPE html>
<html lang="en"
      xmlns="http://www.w3.org/1999/xhtml"
      xmlns:th="http://www.thymeleaf.org"
>

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
        <div class="col-lg-12">
          <h3 class="page-header"><i class="fa fa-edit"></i>Fòm Pou Aktyalize Bank</h3>
          <ol class="breadcrumb">
            <li><i class="fa fa-home"></i><a href="/home">Paj Akèy</a></li>
            <li><i class="fa fa-edit"></i>Kreye Bank</li>
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
        <form class="form-horizontal form-validate" id="bankForm" action="/bank/create" th:object="${bank}"
              method="post">
          <input type="hidden" name="id" id="id" value="${bank.id}">
          <div class="col-lg-6 col-md-6 col-sm-12 col-xs-12">
            <div class="row">
              <div class="col-lg-12">
                <section class="panel">
                  <header class="panel-heading">
                    Enfòmasyon Bank lan
                  </header>
                  <div class="panel-body">
                    <div class="form-group">
                      <label class="col-lg-2 col-md-2 col-sm-2 control-label">Deskripsyon<span class="required">*</span></label>
                      <div class="col-lg-10   col-md-10 col-sm-10">
                        <input type="text" class="form-control round-input" id="description" name="description"
                               value="${(bank.description)! ""}" minlength="2" required>
                      </div>
                    </div>

                    <div class="form-group">
                      <label class="control-label col-lg-2 col-md-2 col-sm-2 col-xs-12" for="seller">Vandè<span
                                class="required">*</span></label>
                      <div class="col-lg-10 col-md-10 col-sm-10 col-xs-12">
                        <select class="form-control selectpicker"
                                name="seller"
                                id="seller"
                                data-size="5"
                                ng-model="selectedSeller"
                                ng-change="sellerChange(1)"
                                data-none-selected-text="Chwazi Vande a"
                                data-allow-clear="true"
                                data-none-results-text="Vande sa pa egziste"
                                data-placeholder="Chwazi vande la">
                            <#if sellers??>
                                <#list sellers as seller>
                                    <#if seller.id == bank.seller.id>
                                      <option value="${seller.id}" selected>${seller.user.name}</option>
                                    <#else>
                                      <option value="${seller.id}">${seller.user.name}</option>
                                    </#if>
                                </#list>
                            </#if>
                        </select>
                      </div>
                    </div>
                    <div class="form-group" ng-if="pos.length <= 0">
                      <label class="col-lg-2 col-md-2 col-sm-2 control-label col-xs-12" for="pos">Machin</label>
                      <div class="col-lg-10 col-md-10 col-sm-10 col-xs-12">
                        <select class="form-control round-input"
                                name="pos"
                                id="pos"
                                data-live-search="true"
                                data-none-results-text="Machin sa pa egziste"
                                data-placeholder="Chwazi machin nan"
                                data-none-selected-text="Chwazi Machin nan"
                                data-size="5"
                                required>
                            <#if pos??>
                                <#list pos as p>
                                    <#if p.id == bank.pos.id>
                                      <option value="${p.id}" selected>${p.description}</option>
                                    <#else>
                                      <option value="${p.id}">${p.description}</option>
                                    </#if>
                                </#list>
                            </#if>
                        </select>
                      </div>
                    </div>
                    <div class="form-group" ng-if="pos.length > 0">
                      <label class="col-lg-2 col-md-2 col-sm-2 control-label col-xs-12" for="pos">Machin</label>
                      <div class="col-lg-10 col-md-10 col-sm-10 col-xs-12">
                        <select class="form-control round-input"
                                name="pos"
                                id="pos"
                                ng-model="selectedPos"
                                data-live-search="true"
                                data-none-results-text="Machin sa pa egziste"
                                data-placeholder="Chwazi machin nan"
                                data-none-selected-text="Chwazi Machin nan"
                                data-size="5"
                                required>
                          <option ng-repeat="p in pos track by p.id" value="{{p.id}}">{{p.description}}</option>

                        </select>
                      </div>
                    </div>
                  </div>
                </section>
              </div>
            </div>
          </div>
          <div class="col-lg-6 col-md-6 col-sm-12 col-xs-12">
            <div class="row">
              <div class="col-lg-12">
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
                               maxlength="100"
                               value="${(bank.address.region)! ""}">
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
                               maxlength="100"
                               value="${(bank.address.city)! ""}">
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
                               maxlength="100"
                               value="${(bank.address.sector)! ""}">
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
                               maxlength="100"
                               value="${(bank.address.street)! ""}">
                      </div>
                    </div>


                  </div>

                </section>
              </div>
            </div>
          </div>

          <div class="col-lg-12">
            <div class="breadcrumb" style="height: auto;">
              <div class="form-group" style="margin-bottom: 10px;">
                <div class="col-lg-6 col-md-6 col-sm-12 col-lg-offset-6 col-md-offset-6 col-xs-12">
                  <div class="row">
                    <div class="col-lg-6 col-md-6 col-sm-12 col-xs-12">
                      <button type="reset"
                              class="btn btn-danger form-control"
                              title="Efase tout done bank lan">
                        <i class="fa fa-times"></i>
                        Reyajiste
                      </button>
                    </div>
                    <div class="col-lg-6 col-md-6 col-sm-12 col-xs-12">
                      <button class="btn btn-primary form-control"
                              type="submit"
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
      });//sumbmit
  </script>


</body>

</html>