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
    <section id="main-content" ng-controller="combinationTypeController" ng-init="change()">
      <section class="wrapper">
        <div class="row">
          <div class="col-lg-12">
            <h3 class="page-header"><i class="fa fa-edit"></i>Fòm pou Aktyalize Tip Konbinezon</h3>
            <ol class="breadcrumb">
              <li><i class="fa fa-home"></i><a href="/home">Paj Akèy</a></li>
              <li><i class="fa fa-edit"></i>Aktyalize Tip Konbinezon</li>
            </ol>
          </div>
        </div>
        <div class="row">
          <div class="col-lg-12 col-xs-12 col-xl-12 col-md-12 col-sm-12">
            <#if error??>
              <div class="alert alert-danger" role="alert">${error}</div>
            </#if>
          </div>
          <div class="col-lg-12">
            <section class="panel">
              <header class="panel-heading">
                Aktyalize Tip Konbinezon
              </header>
              <div class="panel-body">
                <form class="form-horizontal" method="post" th:object="${combinationType}" action="/combinationType/update">
                    <input type="hidden" name="id" id="id" value="${combinationType.id}">
                  <div class="form-group">
                    <label class="control-label col-lg-2 col-md-2 col-sm-2" for="inputSuccess">Konbinezon <span class="required">*</span></label>
                    <div class="col-lg-10 col-md-10 col-sm-10">
                      <select class="form-control selectpicker"
                        name="products"
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
                          <#list products as p>
                            <#if p.id == combinationType.products.id>
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
                    <label class="control-label col-lg-2 col-md-2 col-sm-2" for="inputSuccess">Pri Pèman <span class="required">*</span></label>
                    <div class="col-lg-10 col-md-10 col-sm-10">
                      <input type="number" class="form-control round-input" id="payedPrice" value="${(combinationType.payedPrice)! 0}" name="payedPrice" >
                    </div>
                  </div>

                  <div class="form-group" ng-if="isBolet">
                    <label class="control-label col-lg-2 col-md-2 col-sm-2" for="inputSuccess">Pri Pèman Premye Lo</label>
                    <div class="col-lg-10 col-md-10 col-sm-10">
                      <input type="number" class="form-control round-input" id="payedPriceFirstDraw" value="${(combinationType.payedPriceFirstDraw)!0}" name="payedPriceFirstDraw" >
                    </div>
                  </div>

                  <div class="form-group" ng-if="isBolet">
                    <label class="control-label col-lg-2 col-md-2 col-sm-2" for="inputSuccess">Pri Pèman Dezyèm Lo</label>
                    <div class="col-lg-10 col-md-10 col-sm-10">
                      <input type="number" class="form-control round-input" id="payedPriceSecondDraw" value="${(combinationType.payedPriceSecondDraw)!0}" name="payedPriceSecondDraw" >
                    </div>
                  </div>

                  <div class="form-group" ng-if="isBolet">
                    <label class="control-label col-lg-2 col-md-2 col-sm-2" for="inputSuccess">Pri Pèman Twazyèm Lo</label>
                    <div class="col-lg-10 col-md-10 col-sm-10">
                      <input type="number" class="form-control round-input" id="payedPriceThirdDraw" value="${(combinationType.payedPriceThirdDraw)! 0}" name="payedPriceThirdDraw" >
                    </div>
                  </div>

                    <div class="form-group">
                        <label class="control-label col-lg-2 col-md-2 col-sm-2" for="note">Nòt:</label>
                        <div class="col-lg-10 col-md-10 col-sm-10">
                          <textarea class="form-control" rows="5" id="note" maxlength="255" name="note">${combinationType.note}</textarea>
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
  <!-- javascripts -->
<#include "../scripts.ftl">
<script type = "text/javascript" >
  $('.selectpicker').selectpicker();
</script>

</body>

</html>