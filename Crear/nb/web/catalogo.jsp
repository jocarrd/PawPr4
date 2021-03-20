

<%@page import="paw.bd.Paginador"%>
<%@page import="paw.model.Articulo"%>
<%@page import="java.util.List"%>
<%@page import="paw.bd.GestorBD"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<html>
    <%
        int tamanio;
        if (config.getInitParameter("tamanioPagina") == null) {
            tamanio = 15;
        } else {
            tamanio = Integer.parseInt(config.getInitParameter("tamanioPagina"));
        }

        Paginador d = sol.GestorBD.getPaginadorArticulos(tamanio);

        int pagina;
        if (request.getParameter("pag") == null || request.getParameter("pag").trim().length() == 0) {
            pagina = 1;
        } else {
            pagina = Integer.parseInt(request.getParameter("pag"));
        }

        List<Articulo> arts = sol.GestorBD.getArticulos(pagina, tamanio);


    %>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" lang="es-ES">
        <title>Electrosa >> cat&aacute;logo</title>
        <meta name="description" content="Aplicación de prueba de Programación de aplicaciones Web; Grado en informática; Universidad de La Rioja." lang="es-ES">
        <meta name="keywords" content="electrodomesticos" lang="es-ES">
        <meta name="language" content="es-ES">
        <meta name="robots" content="index,follow">

        <link href="css/electrosa.css" rel="stylesheet" media="all" type="text/css">
        <link href="css/catalogoMosaico.css" rel="stylesheet" media="all" type="text/css">
        <!--    <link href="css/catalogoLista.css" rel="stylesheet" media="all" type="text/css"> -->
    </head>

    <body >
        <%@ include file='cabecera.html'%>

        <div class="sombra">
            <div class="nucleo">

                <div id="migas">
                    <a href="index.html" title="Inicio" >Inicio</a>&nbsp; | &nbsp; 
                    <a href="generica.html" title="Hojear catalogo">Hojear catalogo</a>	<!-- &nbsp; | &nbsp; 
                    <a href="..." title="Otra cosa">Otra cosa</a>   -->	
                </div>
                <div class="contenido">
                    <h1>Nuestros productos</h1>
                    <p>Puede buscar los productos que necesite en nuestro cat&aacute;logo. Lo hemos organizado por marcas, tipo de electrodom&eacute;stico y rango de precios. Lo precios indicados en rojo corresponden a ofertas. </p>
                    <div class="filtroCatalogo">
                        <form name="filtroCatalogo" id="filtroCatalogo" action="">

                            <label for="tipo">Tipo :</label>
                            <select name="tipo" id="tipo">
                                <%                                    paw.bd.GestorBD compilado = new paw.bd.GestorBD();
                                    List<String> tipos = compilado.getTiposArticulos();
                                %>
                                <option value="-1">- Cualquiera -</option>
                                <% for (int i = 0; i < tipos.size(); i++) {%>
                                <option value=<%=tipos.get(i)%>><%=tipos.get(i)%></option>
                                <%}%>
                            </select>

                            <label for="fabricante">Fabricante :</label>
                            <select name="fabricante" id="fabricante">
                                <%

                                    List<String> fabricantes = compilado.getFabricantes();
                                %>

                                <option value="-1">- Cualquiera -</option>
                                
                                <% for (int i = 0; i < fabricantes.size(); i++) {%>
                                <option value=<%=fabricantes.get(i)%>><%=fabricantes.get(i)%></option>
                                <%}%>
                            </select>

                            <label for="precio">Precio :</label>
                            <select name="precio" id="precio">
                                <option value="-1">- Cualquiera -</option>
                                <option value="10-50">10-50 &euro;</option>
                                <option value="50-100">50-100 &euro;</option>
                                <option value="100-200">100-200 &euro;</option>
                                <option value="200-500">200-500 &euro;</option>
                                <option value="500-1000">500-1.000 &euro;</option>
                                <option value="1000">Mas de 1.000 &euro;</option>
                            </select>


                            <input name="buscar" id="buscar" type="image" title="Buscar" src="img/search25.png" alt="Buscar">

                        </form>

                        <div class="modovisual">
                            <a href="catalogo.html">Mosaico</a> &nbsp; | &nbsp; <a href="catalogo.html">Lista</a>
                        </div>
                        <div class="clear"></div>
                    </div>

                    <div class="resumResul redondeo">
                        Encontrados <%=d.getNumRegistros()%> artículos. Mostrando página <%=pagina%> de 38.
                        <span class="paginador"> 

                            <%if (pagina != 1) {%>
                            <a href="catalogo.jsp?pag=2">Anterior</a>   
                            <%}%>
                            <a href="catalogo.jsp?pag=1">1</a>              
                            <a href="catalogo.jsp?pag=2">2</a>                          
                            <a href="catalogo.jsp?pag=3">3</a>              
                            <a href="catalogo.jsp?pag=4">4</a>
                            <a href="catalogo.jsp?pag=5">5</a>
                            <% if (pagina != d.getNumPaginas()) {%>
                            <a href="catalogo.jsp?pag=<%=pagina + 1%>">Siguiente</a>    
                            <%}%>
                        </span>
                    </div>

                    <ul class="resultBusqueda">
                        <% int i = 1;
                            for (Articulo a : arts) {%>
                        <%
                            String fotografia = "img/fotosElectr/" + a.getFoto();
                            String oferta = " ";
                        %>
                        <% if (i % 3 == 0)
                                oferta = "oferta";%>
                        <li class="item redondeo">
                            <div class="foto">
                                <a href="fichaArticulo.jsp?cart=<%=a.getCodigo()%>"><img src=<%= fotografia%> alt="Mie/088FO" longdesc="<%=a.getDescripcion()%>" width="80"></a>
                            </div>
                            <div class="datos">
                                <span><%= a.getNombre()%></span>
                                <div class="precio">
                                    <span class=<%=oferta%>  > <%=a.getPvp()%> &euro;</span>
                                </div>
                                <div class="carro">
                                    <img src="img/shopcartadd_16x16.png" title="Añadir a mi carro de la compra">
                                </div>
                            </div>			  
                            <div class="codigo"><a href="catalogo.jsp"><%=a.getCodigo()%></a></div>
                        </li>
                        <%i++;
                            }%>
                    </ul>			
                    <div class="clear"></div>

                </div>
            </div>

            <div class="separa"></div>

            <div class="pie">
                <%@ include file='pie.html'%>
            </div>

        </div>
    </body>
</html>