//Primera carga de datos para mostrar la página
function cargaInicial() {

    let divCabecera = document.getElementById("divCabecera");
    if (divCabecera.className.indexOf("ocultarContenedor")===-1){
        divCabecera.className += " ocultarContenedor";
    }
    let alertAvisoErrorGeneral = document.getElementById("avisoErrorGeneral");
    if (alertAvisoErrorGeneral.className.indexOf("ocultarAviso")===-1){
        alertAvisoErrorGeneral.className += " ocultarAviso";
    }

    let urlAcceso = "http://localhost:8080/paginas/categoria/";
    let idCategoria = sessionStorage.getItem("CromosDatabaseCate");
    if (parseInt(idCategoria)){
        urlAcceso += idCategoria;
    }else{
        urlAcceso += parseInt((Math.random()*5)+1);
    }
    let autenticacion = sessionStorage.getItem("CromosDatabaseAuth");

    fetch(urlAcceso, {
    method: "GET",
    headers: {"Content-Type": "application/json","Authorization": autenticacion},
    })
    .then((res) => res.json())
    .catch((error) => errorCargaDatos(error))
    .then((response) => cargaDatos(response));
}


//Cargar contenido del servicio
function cargaDatos(respuesta){
    try {
        //Comprobamos que no se ha devuelto ningún código de error
        if (!respuesta.status){

            // Metemos la cabecera de la colección
            let cromosDatabaseNomb = sessionStorage.getItem("CromosDatabaseNomb");
            let tituloCategoria = document.getElementById("tituloCategoria");
            tituloCategoria.textContent = respuesta.nombreCategoria;
            let subcategorias = respuesta.subcategorias;
            let descripcionCategoria = document.getElementById("descripcionCategoria");
            let textoDescripcionCategoria = "<p>" + respuesta.descripcionCategoria + "</p>";
            textoDescripcionCategoria += "<p> Esta categoría tiene " + subcategorias.length + " subcategorías, aquí las tienes juntos a algunas de sus colecciones!</p>";
            divDescripcionCategoria = document.createElement("div");
            divDescripcionCategoria.innerHTML = textoDescripcionCategoria;
            descripcionCategoria.appendChild(divDescripcionCategoria);

            let divCabecera = document.getElementById("divCabecera");
            let divListaSubCategorias = document.getElementById("divListaSubCategorias");
            let textoDivListaSubCategorias;
            let divElementoSubCategoria;
            let textoColecciones;

            subcategorias.forEach(subcate => {
                textoDivListaSubCategorias = "<div><section class='py-5 text-center container'><div class='row py-lg-5'>";
                textoDivListaSubCategorias += "<div class='col-lg-10 mx-auto'><h3 class='fw-light'>";
                textoDivListaSubCategorias += subcate.nombreSubcategoria;
                textoDivListaSubCategorias += "</h3><p class='lead text-body-secondary'>";
                textoDivListaSubCategorias += subcate.descripcionSubcategoria;
                textoDivListaSubCategorias += "</p></div></div></section><div class='album py-5 bg-body-tertiary'>";
                textoDivListaSubCategorias += "<div class='container'><div class='row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3'>";





                textoColecciones = "";
                subcate.coleccionesSubcategoria.forEach(coleccion => {
                    textoColecciones += "<div class='col'><div class='card shadow-sm'><img src=";
                    textoColecciones += "img/1954_Bowman_Mickey_Mantle.jpg"; //cambiar por urlImgDelantera
                    textoColecciones += " alt='Imagen de la colección " + coleccion.nombre + "'";
                    textoColecciones += "><div class='cromo-titulo'><p>";
                    textoColecciones += coleccion.nombre;
                    textoColecciones += "</p></div><div class='card-body'><p class='card-text'>";
                    textoColecciones += "<b>Año, país y editorial</b>: " + coleccion.periodo + ", " + coleccion.pais + ", " + coleccion.editorial.nombre + ".<br/>";
                    textoColecciones += "<b>Categoría y subcategoría:</b> " + coleccion.categoria.nombre + " / " + coleccion.subcategoria.nombre + ".<br/>";
                    textoColecciones += "<b>Descripción:</b> " + coleccion.descripcion;
                    textoColecciones += "</p><div class='d-flex justify-content-between align-items-center'><div class='btn-group'>";
                    textoColecciones += "<button type='button' class='btn btn-sm btn-outline-secondary' id='"
                    textoColecciones += "botonUltimasColeccionesColeccion" + coleccion.id + "'";
                    textoColecciones += " onClick='clickVerColeccion(" + coleccion.id + ")'>Ver colección</button>";
                    textoColecciones += "</div><small class='text-body-secondary'>" + coleccion.subcategoria.nombre;
                    textoColecciones += "</small></div></div></div></div>";
                });

                textoDivListaSubCategorias += textoColecciones;
                textoDivListaSubCategorias += "</div></div></div></div>";

                divElementoSubCategoria = document.createElement("div");
                divElementoSubCategoria.innerHTML = textoDivListaSubCategorias;
                divListaSubCategorias.appendChild(divElementoSubCategoria);
            });

            divCabecera.className = divCabecera.className.replace(" ocultarContenedor","");

        } else {
            let alertAvisoErrorGeneral = document.getElementById("avisoErrorGeneral");
            alertAvisoErrorGeneral.textContent = respuesta.mensaje?respuesta.mensaje:"Error inesperado, inténtelo de nuevo otra vez y si el error persiste compruebe su conexión.";
            alertAvisoErrorGeneral.className = alertAvisoErrorGeneral.className.replace(" ocultarAviso","");            

        }
    } catch (error) {
        errorCargaDatos(error);
        let alertAvisoErrorGeneral = document.getElementById("avisoErrorGeneral");
        alertAvisoErrorGeneral.textContent = "Error inesperado, inténtelo de nuevo otra vez y si el error persiste compruebe su conexión.";
        alertAvisoErrorGeneral.className = alertAvisoErrorGeneral.className.replace(" ocultarAviso","");

    }
}


//Error en la cargar del servicio
function errorCargaDatos(error){
    let alertAvisoErrorGeneral = document.getElementById("avisoErrorGeneral");
    alertAvisoErrorGeneral.className = alertAvisoErrorGeneral.className.replace(" ocultarAviso","");
}


function clickVerColeccion (id){
    sessionStorage.setItem("CromosDatabaseCole", id);
    window.location.href = "coleccion.html";
}


function clickEnlaceAcceso (){
    if (enlaceAcceso.textContent != "Acceso"){
        sessionStorage.setItem("CromosDatabaseAuth", "");
        sessionStorage.setItem("CromosDatabaseNomb", "");
        sessionStorage.setItem("CromosDatabaseCrom", "");
        sessionStorage.setItem("CromosDatabaseCole", "");
        sessionStorage.setItem("CromosDatabaseCate", "");
        event.preventDefault();
        location.reload();
    }
}


