<!DOCTYPE html>
<html lang="en"
      xmlns="http://www.w3.org/1999/xhtml"
      xmlns:th="http://www.thymeleaf.org"
      xmlns:sec="http://www.thymeleaf.org/thymeleaf-extras-springsecurity3">

<head>
  <#include "../header.ftl">
      <link href="/dist/select2/css/select2.min.css" rel="stylesheet" />
</head>

<body>
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
            <h3 class="page-header"><i class="fa fa-edit"></i>Fòm pou aktyalize tiraj</h3>
            <ol class="breadcrumb">
              <li><i class="fa fa-home"></i><a href="/home">Paj Akèy</a></li>
              <li><i class="fa fa-edit"></i>Aktyalize Tiraj</li>
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
                Aktyalize Tiraj
              </header>
              <div class="panel-body">
                <form class="form-horizontal" action="/draw/update" th:object="${draw}" method="post">
                    <input type="hidden" name="id" id="id" value="${draw.id}">

                  <div class="form-group">
                    <label class="col-lg-2 col-md-2 col-sm-2 control-label">Dat<span class="required">*</span></label>
                    <div class="col-lg-10 col-md-10 col-sm-10">
                      <input type="date" id="drawDate" name="drawDate" class="form-control" value="${(draw.drawDate?string["yyyy-MM-dd"])!""}"  required>
                    </div>
                  </div>

                  <div class="form-group">
                    <label class="control-label col-lg-2 col-md-2 col-sm-2" for="inputSuccess">Tip Tiraj <span class="required">*</span></label>
                    <div class="col-lg-10 col-md-10 col-sm-10">
                      <select class="form-control m-bot15 selectpicker"
                          name="shift"
                          id="shift"
                          data-size="5"
                          data-none-selected-text="Chwazi Tip Tiraj la"
                          required
                          autofocus>
                        <#if shifts??>
                          <#list shifts as shift>
                            <#if shift.id = draw.shift.id>
                              <option value="${shift.id}" selected>
                                {{shift.name}}
                              </option>
                            <#else >
                              <option value="${shift.id}">
                                {{shift.name}}
                              </option>
                            </#if>
                          </#list>
                        </#if>

                      </select>
                    </div>
                  </div>
                  <div class="form-group">
                    <label class="control-label col-lg-2 col-md-2 col-sm-2" for="inputSuccess">Lotto 3 chif <span class="required">*</span></label>
                    <div class="col-lg-10  col-md-10 col-sm-10">
                      <select class="form-control selectpicker show-tick"
                              name="numberThreeDigits"
                              id="numberThreeDigits"
                              data-live-search="true"
                              data-size="7"
                              data-none-results-text="Nimewo sa pa egziste"
                              data-actions-box="true"
                              data-none-selected-text="Chwazi yon loto 3 chif"
                              required>

                            <#if numberThreeDigits??>
                              <#list numberThreeDigits as numberThreeDigit>
                                <#if numberThreeDigit.id == draw.numberThreeDigits.id>
                                  <option value="${numberThreeDigit.id}" selected>${numberThreeDigit.numberInStringFormat}</option>
                                <#else>
                                  <option value="${numberThreeDigit.id}">${numberThreeDigit.numberInStringFormat}</option>
                                </#if>
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
                                  <#assign exist = 0>
                                  <#list draw.numberTwoDigits as n>
                                    <#if n.id == numberTwoDigit.id>
                                      <#assign exist = 1>
                                    </#if>
                                  </#list>
                                  <#if exist == 1>
                                    <option value="${numberTwoDigit.id}" selected>${numberTwoDigit.numberInStringFormat}</option>
                                  <#else>
                                    <option value="${numberTwoDigit.id}">${numberTwoDigit.numberInStringFormat}</option>
                                  </#if>
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
                            type="button"
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

                </div>
              </form>
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
      <scripttype = "text/javascript" >
              $('.selectpicker').selectpicker();

      $.fn.selectpicker.Constructor.BootstrapVersion = '4';
  </script>
</body>

</html>