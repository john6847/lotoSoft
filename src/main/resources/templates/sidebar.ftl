<aside>
  <div id="sidebar" class="nav-collapse ">
    <!-- sidebar menu start-->
    <ul class="sidebar-menu">
      <li class="active">
        <a class="" href="/home">
          <i class="icon_house_alt"></i>
          <span>Dashboard</span>
        </a>
      </li>

      <#if user??>
        <#list user.roles as rol>
          <#if rol.name=="ROLE_ADMIN">
            <li class="sub-menu">
              <a href="javascript:;" class="">
                <i class="icon_document_alt"></i>
                <span>Kreye</span>
                <span class="menu-arrow arrow_carrot-right"></span>
              </a>
              <ul class="sub">
                <li>
                  <a class="" title="Ajoute nouvo vandè" href="/seller/create">Vandè</a>
                </li>
                <li>
                  <a class="" title="Ajoute nouvo machin" href="/pos/create">Machin</a>
                </li>
                <li>
                  <a class="" title="Ajoute nouvo tiraj" href="/draw/create">Tiraj</a>
                </li>
                <li>
                  <a class="" title="Ajoute nouvo konbinezon" href="/combinationType/create">Konbinezon</a>
                </li>
                <li>
                  <a class="" title="Ajoute nouvo Gwoup" href="/group/create">Gwoup</a>
                </li>
              </ul>
            </li>
            <li class="sub-menu">
              <a href="javascript:;" class="">
                <i class="fa fa-eye"></i>
                <span>Lekti</span>
                <span class="menu-arrow arrow_carrot-right"></span>
              </a>
              <ul class="sub">
                <li>
                  <a class="" title="Gade vandè yo" href="/seller">Vandè</a>
                </li>
                <li>
                  <a class="" title="Gade machin yo" href="/pos">Machin</a>
                </li>
                <li>
                  <a class="" title="Gade tiraj yo" href="/draw">Tiraj</a>
                </li>
                <li>
                  <a class="" title="Gade konbinezon yo" href="/combinationType">Konbinezon</a>
                </li>
                <li>
                  <a class="" title="Gade Gwoup yo" href="/group">Gwoup</a>
                </li>
              </ul>
            </li>
            <li class="sub-menu">
              <a href="javascript:;" class="">
                <i class="icon_profile"></i>
                <span>Itilizatè</span>
                <span class="menu-arrow arrow_carrot-right"></span>
              </a>
              <ul class="sub">
                <li>
                  <a class="" title="Kreye yon itilizatè" href="/user/1/create">Kreye Itilizatè</a>
                </li>
                <li>
                  <a class="" title="Li yon itilizatè" href="/user/1">Li Itilizatè</a>
                </li>
              </ul>
            </li>
            <li class="sub-menu">
              <a href="javascript:;" class="">
                <i class="icon_cog"></i>
                <span>Konfigirasyon</span>
                <span class="menu-arrow arrow_carrot-right"></span>
              </a>
              <ul class="sub">
                <li>
                  <a class="" title="Konfigire Konbinezon" href="/configuration/combination">Konbinezon</a>
                </li>
                <li>
                  <a class="" title="Konfigire tip tiraj" href="/configuration/shift">Tip Tiraj</a>
                </li>
              </ul>
            </li>
            <#elseif rol.name=="ROLE_SUPER_MEGA_ADMIN">
              <li class="sub-menu">
                <a href="javascript:;" class="">
                  <i class="icon_building"></i>
                  <span>Antrepriz</span>
                  <span class="menu-arrow arrow_carrot-right"></span>
                </a>
                <ul class="sub">
                  <li>
                    <a class="" href="/enterprise/create">Kreye Antrepriz</a>
                  </li>
                  <li>
                    <a href="/enterprise">Gade Antrepriz</a>
                  </li>
                </ul>
              </li>

              <li class="sub-menu">
                <a href="javascript:;" class="">
                  <i class="fa fa-user"></i>
                  <span>Kliyan</span>
                  <span class="menu-arrow arrow_carrot-right"></span>
                </a>
                <ul class="sub">
                  <li>
                    <a class="" href="/user/2/create">Kreye Kliyan</a>
                  </li>
                  <li>
                    <a href="/user/2">Gade Kliyan</a>
                  </li>
                </ul>
              </li>

          </#if>
        </#list>
      </#if>
      <li>
        <a class="" href="widgets.html">
          <i class="icon_genius"></i>
          <span>Widgets</span>
        </a>
      </li>
      <li>
        <a class="" href="chart-chartjs.html">
          <i class="icon_piechart"></i>
          <span>Charts</span>
        </a>
      </li>

      <li class="sub-menu">
        <a href="javascript:;" class="">
          <i class="icon_table"></i>
          <span>Tables</span>
          <span class="menu-arrow arrow_carrot-right"></span>
        </a>
        <ul class="sub">
          <li><a class="" href="basic_table.html">Basic Table</a></li>
        </ul>
      </li>

      <li class="sub-menu">
        <a href="javascript:;" class="">
          <i class="icon_documents_alt"></i>
          <span>Pages</span>
          <span class="menu-arrow arrow_carrot-right"></span>
        </a>
        <ul class="sub">
          <li><a class="" href="profile.html">Profile</a></li>
          <li>
            <a class="" href="login.ftl"><span>Login Page</span></a>
          </li>
          <li>
            <a class="" href="contact.html"><span>Contact Page</span></a>
          </li>
          <li><a class="" href="blank.html">Blank Page</a></li>
          <li><a class="" href="404.ftl">404 Error</a></li>
        </ul>
      </li>
    </ul>
    <!-- sidebar menu end-->
  </div>
</aside>
