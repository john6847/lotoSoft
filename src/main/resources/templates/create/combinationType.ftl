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
  <section id="main-content" ng-controller="combinationTypeController">
    <section class="wrapper" ng-init="init(false)">
      <div class="row">
        <div class="col-lg-12">
          <h3 class="page-header"><i class="fa fa-file-text-o"></i>Fòm Pou Kreye Tip Konbinezon</h3>
          <ol class="breadcrumb">
            <li><i class="fa fa-home"></i><a href="/home">Paj Akèy</a></li>
            <li><i class="fa fa-file-text-o"></i>Kreye Tip Konbinezon</li>
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
        <div class="col-lg-12 col-xs-12 col-xl-12 col-md-12 col-sm-12">
          <section class="panel">
            <header class="panel-heading">
              Tip Konbinezon
            </header>
            <div class="panel-body">
              <form class="form-horizontal" id="combinationTypeForm" method="post" th:object="${combinationType}"
                    action="/combinationType/create">
                <div class="form-group">
                  <label class="control-label col-lg-2 col-md-2 col-sm-2 col-xs-12" for="products">Konbinezon <span
                            class="required">*</span></label>
                  <div class="col-lg-10 col-md-10 col-sm-10 col-xs-12">
                    <select class="form-control selectpicker"
                            name="products"
                            ng-init="product = 1"
                            ng-model="product"
                            data-live-search="true"
                            data-size="5"
                            ng-change="change()"
                            data-none-selected-text="Chwazi Tip Konbinezon an"
                            data-none-results-text=" Tip konbinezon sa pa egziste"
                            data-placeholder="Chwazi Tip Konbinezon an"
                            id="products"
                            required>
                        <#if products??>
                            <#list products as p >
                                <#if p.id == 1>
                                  <option value="${p.id}" selected>${p.name}</option>
                                <#else>
                                  <option value="${p.id}">${p.name}</option>
                                </#if>
                            </#list>
                        </#if>
                    </select>
                  </div>
                </div>

                <div class="form-group" ng-if="!isBolet">
                  <label class="control-label col-lg-2 col-md-2 col-sm-2 col-xs-12" for="payedPrice">Pri Pèman <span
                            class="required">*</span></label>
                  <div class="col-lg-10 col-md-10 col-sm-10 col-xs-12">
                    <input type="number" class="form-control round-input" id="payedPrice" name="payedPrice">
                  </div>
                </div>

                <div class="form-group" ng-if="isBolet">
                  <label class="control-label col-lg-2 col-md-2 col-sm-2 col-xs-12" for="payedPriceFirstDraw">Pri Pèman
                    Premye Lo</label>
                  <div class="col-lg-10 col-md-10 col-sm-10 col-xs-12">
                    <input type="number" class="form-control round-input" id="payedPriceFirstDraw"
                           name="payedPriceFirstDraw">
                  </div>
                </div>

                <div class="form-group" ng-if="isBolet">
                  <label class="control-label col-lg-2 col-md-2 col-sm-2 col-xs-12" for="payedPriceSecondDraw">Pri Pèman
                    Dezyèm Lo</label>
                  <div class="col-lg-10 col-md-10 col-sm-10 col-xs-12">
                    <input type="number" class="form-control round-input" id="payedPriceSecondDraw"
                           name="payedPriceSecondDraw">
                  </div>
                </div>

                <div class="form-group" ng-if="isBolet">
                  <label class="control-label col-lg-2 col-md-2 col-sm-2 col-xs-12" for="payedPriceThirdDraw">Pri Pèman
                    Twazyèm Lo</label>
                  <div class="col-lg-10 col-md-10 col-sm-10 col-xs-12">
                    <input type="number" class="form-control round-input" id="payedPriceThirdDraw"
                           name="payedPriceThirdDraw">
                  </div>
                </div>

                <div class="form-group">
                  <label class="control-label col-lg-2 col-md-2 col-sm-2 col-xs-12" for="note">Nòt</label>
                  <div class="col-lg-10 col-md-10 col-sm-10 col-xs-12">
                    <textarea class="form-control" rows="5" id="note" maxlength="255" name="note"></textarea>
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
                          <i class="fa fa-times" aria-hidden="true"></i>
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
<#include "../loader.ftl">
<!-- javascripts -->
<#include "../scripts.ftl">
<script type="text/javascript">
    $('.selectpicker').selectpicker();

    $("#combinationTypeForm").on("submit", function () {
        $("#custom-loader").fadeIn();
    });//submit
</script>


</body>

</html>