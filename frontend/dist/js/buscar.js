//Primera carga de datos para mostrar la página
function cargaInicial() {
    //Personalizamos la página si es posible
    let enlaceAcceso = document.getElementById("enlaceAcceso");
    let cromosDatabaseNomb = sessionStorage.getItem("CromosDatabaseNomb");
    if (cromosDatabaseNomb){
        enlaceAcceso.textContent = "Desconéctate como: " + cromosDatabaseNomb;
        enlaceAcceso.href = "#";
    }
}


//Cargar contenido del servicio si se ha buscado por cromo
function cargaDatosCromo(respuesta){
    try {
        //Comprobamos que no se ha devuelto ningún código de error
        if (!respuesta.status){

            // Añadimos las colecciones recuperando el DIV, generando elementos dinámicamente y los añadimos
            if (respuesta.length){
                let divCromos = document.getElementById("divCromos");
                let divFilaCromos = document.getElementById("divFilaCromos");
                let textoDivElementoCromo;
                let divElementoCromo;
                while (divFilaCromos.firstChild) {
                    divFilaCromos.removeChild(divFilaCromos.firstChild);
                }

                respuesta.forEach(cromo => {
                    textoDivElementoCromo = "<div class='col'><div class='card shadow-sm'><img src=";
                    textoDivElementoCromo += "img/1954_Bowman_Mickey_Mantle.jpg"; //cambiar por urlImgDelantera
                    textoDivElementoCromo += " alt='Imagen del cromo " + cromo.nombre + "'";
                    textoDivElementoCromo += "><div class='cromo-titulo'><p>";
                    textoDivElementoCromo += cromo.nombre;
                    textoDivElementoCromo += "</p></div><div class='card-body'><p class='card-text'>";
                    textoDivElementoCromo += "<b>Nombre:</b> " + cromo.nombre + "<br/>";
//                    textoDivElementoCromo += "<b>Descripción:</b> " + cromo.descripcion + "<br/>";
//                    textoDivElementoCromo += "<b>Colección y tipo:</b> " + cromo.nombreColeccion + ", " + cromo.tipo;
                    textoDivElementoCromo += "</p><div class='d-flex justify-content-between align-items-center'><div class='btn-group'>";
                    textoDivElementoCromo += "<button type='button' class='btn btn-sm btn-outline-secondary' id='"
                    textoDivElementoCromo += "botonUltimosCromos" + cromo.id + "'";
                    textoDivElementoCromo += " onClick='clickVerCromo(" + cromo.id + ")'>Ver cromo</button>";
//                    textoDivElementoCromo += "<button type='button' class='btn btn-sm btn-outline-secondary' id='"
//                    textoDivElementoCromo += "botonColeccionUltimosCromos" + cromo.id + "-" + cromo.idColeccion + "'";
//                    textoDivElementoCromo += " onClick='clickVerColeccion(" + cromo.idColeccion + ")'>Ver colección</button>";
                    textoDivElementoCromo += "</div><small class='text-body-secondary'><b><abbr title='Número'>Nº</abbr>:</b> " + cromo.numero;
                    textoDivElementoCromo += "</small></div></div></div></div>";
                    divElementoCromo = document.createElement("div");
                    divElementoCromo.innerHTML = textoDivElementoCromo;
                    divFilaCromos.appendChild(divElementoCromo);
                });
                divCromos.className = divCromos.className.replace(" ocultarContenedor","");

            } else {
                let avisoDatosBusqueda = document.getElementById("avisoDatosBusqueda");
                avisoDatosBusqueda.textContent = "No hay ningún cromo con ese nombre en nuestra base de datos. Prueba otra cosa...";
                avisoDatosBusqueda.className = avisoDatosBusqueda.className.replace(" ocultarAviso","");
            }

        } else {
            let avisoDatosBusqueda = document.getElementById("avisoDatosBusqueda");
            avisoDatosBusqueda.textContent = respuesta.mensaje?respuesta.mensaje:"Error inesperado, inténtelo de nuevo otra vez y si el error persiste compruebe su conexión.";
            avisoDatosBusqueda.className = avisoDatosBusqueda.className.replace(" ocultarAviso","");
        }
    } catch (error) {
        errorCargaDatos(error);
        let alertAvisoErrorGeneral = document.getElementById("avisoErrorGeneral");
        alertAvisoErrorGeneral.textContent = "Error inesperado, inténtelo de nuevo otra vez y si el error persiste compruebe su conexión.";
        alertAvisoErrorGeneral.className = alertAvisoErrorGeneral.className.replace(" ocultarAviso","");

    }
}


//Cargar contenido del servicio si se ha buscado por colección
function cargaDatosColeccion(respuesta){
    try {
        //Comprobamos que no se ha devuelto ningún código de error
        if (!respuesta.status){

            // Añadimos las colecciones recuperando el DIV, generando elementos dinámicamente y los añadimos
            if (respuesta.length){
                let divColecciones = document.getElementById("divColecciones");
                let divFilaColecciones = document.getElementById("divFilaColecciones");
                let textoDivElementoColeccion;
                let divElementoColeccion;
                while (divFilaColecciones.firstChild) {
                    divFilaColecciones.removeChild(divFilaColecciones.firstChild);
                }

                respuesta.forEach(coleccion => {
                    textoDivElementoColeccion = "<div class='col'><div class='card shadow-sm'><img src=";
                    textoDivElementoColeccion += "img/1954_Bowman_Mickey_Mantle.jpg"; //cambiar por urlImgDelantera
                    textoDivElementoColeccion += " alt='Imagen de la colección " + coleccion.nombre + "'";
                    textoDivElementoColeccion += "><div class='cromo-titulo'><p>";
                    textoDivElementoColeccion += coleccion.nombre;
                    textoDivElementoColeccion += "</p></div><div class='card-body'><p class='card-text'>";
                    textoDivElementoColeccion += "<b>Año, país y editorial</b>: " + coleccion.periodo + ", " + coleccion.pais + ", " + coleccion.editorial.nombre + ".<br/>";
                    textoDivElementoColeccion += "<b>Categoría y subcategoría:</b> " + coleccion.categoria.nombre + " / " + coleccion.subcategoria.nombre + ".<br/>";
                    textoDivElementoColeccion += "<b>Descripción:</b> " + coleccion.descripcion;
                    textoDivElementoColeccion += "</p><div class='d-flex justify-content-between align-items-center'><div class='btn-group'>";
                    textoDivElementoColeccion += "<button type='button' class='btn btn-sm btn-outline-secondary' id='"
                    textoDivElementoColeccion += "botonUltimasColeccionesColeccion" + coleccion.id + "'";
                    textoDivElementoColeccion += " onClick='clickVerColeccion(" + coleccion.id + ")'>Ver colección</button>";
                    textoDivElementoColeccion += "<button type='button' class='btn btn-sm btn-outline-secondary' id='"
                    textoDivElementoColeccion += "botonUltimasColeccionesCategoria" + coleccion.id + "-" + coleccion.categoria.id + "'";
                    textoDivElementoColeccion += " onClick='clickVerCategoria(" + coleccion.categoria.id + ")'>Ver categoría</button>";
                    textoDivElementoColeccion += "</div><small class='text-body-secondary'>" + coleccion.subcategoria.nombre;
                    textoDivElementoColeccion += "</small></div></div></div></div>";
                    divElementoColeccion = document.createElement("div");
                    divElementoColeccion.innerHTML = textoDivElementoColeccion;
                    divFilaColecciones.appendChild(divElementoColeccion);
                });
                divColecciones.className = divColecciones.className.replace(" ocultarContenedor","");

            } else {
                let avisoDatosBusqueda = document.getElementById("avisoDatosBusqueda");
                avisoDatosBusqueda.textContent = "No hay ninguna colección con ese nombre en nuestra base de datos. Prueba otra cosa...";
                avisoDatosBusqueda.className = avisoDatosBusqueda.className.replace(" ocultarAviso","");
            }

        } else {
            let avisoDatosBusqueda = document.getElementById("avisoDatosBusqueda");
            avisoDatosBusqueda.textContent = respuesta.mensaje?respuesta.mensaje:"Error inesperado, inténtelo de nuevo otra vez y si el error persiste compruebe su conexión.";
            avisoDatosBusqueda.className = avisoDatosBusqueda.className.replace(" ocultarAviso","");

        }
    } catch (error) {
        errorCargaDatos(error);
        let alertAvisoErrorGeneral = document.getElementById("avisoErrorGeneral");
        alertAvisoErrorGeneral.textContent = "Error inesperado, inténtelo de nuevo otra vez y si el error persiste compruebe su conexión.";
        alertAvisoErrorGeneral.className = alertAvisoErrorGeneral.className.replace(" ocultarAviso","");

    }
}


//Cargar contenido del servicio si se ha buscado por categoría
function cargaDatosCategoria(respuesta){
    try {
        //Comprobamos que no se ha devuelto ningún código de error
        if (!respuesta.status){

            // Añadimos las colecciones recuperando el DIV, generando elementos dinámicamente y los añadimos
            if (respuesta.length){


                let divCategorias = document.getElementById("divCategorias");
                let divFilaCategorias = document.getElementById("divFilaCategorias");
                let textoDivElementoCategoria;
                let divElementoCategoria;
                while (divFilaCategorias.firstChild) {
                    divFilaCategorias.removeChild(divFilaCategorias.firstChild);
                }
                respuesta.forEach(categoria => {
                    textoDivElementoCategoria = "<div class='col'><div class='card shadow-sm'><div class='card-body'><p class='card-text'>";
                    textoDivElementoCategoria += "<b>Nombre:</b> " + categoria.nombre + "<br/>";
//                    textoDivElementoCategoria += "<b>Descripción:</b> " + categoria.descripcion + "</p>";
                    textoDivElementoCategoria += "<div class='d-flex justify-content-between align-items-center'><div class='btn-group'>";
                    textoDivElementoCategoria += "<button type='button' class='btn btn-sm btn-outline-secondary' id='"
                    textoDivElementoCategoria += "botonCategoria" + categoria.id + "'";
                    textoDivElementoCategoria += " onClick='clickVerCategoria(" + categoria.id + ")'>Ver categoría</button>";
                    textoDivElementoCategoria += "</div><small class='text-body-secondary'>" + categoria.nombre;
                    textoDivElementoCategoria += "</small></div></div></div></div>";
                    divElementoCategoria = document.createElement("div");
                    divElementoCategoria.innerHTML = textoDivElementoCategoria;
                    divFilaCategorias.appendChild(divElementoCategoria);
                });
                divCategorias.className = divCategorias.className.replace(" ocultarContenedor","");

            } else {
                let avisoDatosBusqueda = document.getElementById("avisoDatosBusqueda");
                avisoDatosBusqueda.textContent = "No hay ninguna categoría con ese nombre en nuestra base de datos. Prueba otra cosa...";
                avisoDatosBusqueda.className = avisoDatosBusqueda.className.replace(" ocultarAviso","");
            }

        } else {
            let avisoDatosBusqueda = document.getElementById("avisoDatosBusqueda");
            avisoDatosBusqueda.textContent = respuesta.mensaje?respuesta.mensaje:"Error inesperado, inténtelo de nuevo otra vez y si el error persiste compruebe su conexión.";
            avisoDatosBusqueda.className = avisoDatosBusqueda.className.replace(" ocultarAviso","");

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
    let avisoDatosBusqueda = document.getElementById("avisoDatosBusqueda");
    if (avisoDatosBusqueda.className.indexOf("ocultarAviso")===-1){
        avisoDatosBusqueda.className += " ocultarAviso";
    }
    let divCromos = document.getElementById("divCromos");
    if (divCromos.className.indexOf("ocultarContenedor")===-1){
        divCromos.className += " ocultarContenedor";
    }
    let divColecciones = document.getElementById("divColecciones");
    if (divColecciones.className.indexOf("ocultarContenedor")===-1){
        divColecciones.className += " ocultarContenedor";
    }
    let divCategorias = document.getElementById("divCategorias");
    if (divCategorias.className.indexOf("ocultarContenedor")===-1){
        divCategorias.className += " ocultarContenedor";
    }
    let alertAvisoErrorGeneral = document.getElementById("avisoErrorGeneral");
    alertAvisoErrorGeneral.textContent = "Error inesperado, inténtelo de nuevo otra vez y si el error persiste compruebe su conexión.";
    alertAvisoErrorGeneral.className = alertAvisoErrorGeneral.className.replace(" ocultarAviso","");
}


function clickVerCromo(id){
    sessionStorage.setItem("CromosDatabaseCrom", id);
    window.location.href = "cromo.html";
}


function clickVerColeccion (id){
    sessionStorage.setItem("CromosDatabaseCole", id);
    window.location.href = "coleccion.html";
}


function clickVerCategoria (id){
    sessionStorage.setItem("CromosDatabaseCate", id);
    window.location.href = "categoria.html";
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


//Envío de datos para buscar
function clickBuscar() {
    
    event.preventDefault();

    let avisoDatosBusqueda = document.getElementById("avisoDatosBusqueda");
    if (avisoDatosBusqueda.className.indexOf("ocultarAviso")===-1){
        avisoDatosBusqueda.className += " ocultarAviso";
    }
    let divCromos = document.getElementById("divCromos");
    if (divCromos.className.indexOf("ocultarContenedor")===-1){
        divCromos.className += " ocultarContenedor";
    }
    let divColecciones = document.getElementById("divColecciones");
    if (divColecciones.className.indexOf("ocultarContenedor")===-1){
        divColecciones.className += " ocultarContenedor";
    }
    let divCategorias = document.getElementById("divCategorias");
    if (divCategorias.className.indexOf("ocultarContenedor")===-1){
        divCategorias.className += " ocultarContenedor";
    }
    let alertAvisoErrorGeneral = document.getElementById("avisoErrorGeneral");
    if (alertAvisoErrorGeneral.className.indexOf("ocultarAviso")===-1){
        alertAvisoErrorGeneral.className += " ocultarAviso";
    }

    let formularioBusqueda = document.forms["formularioBusqueda"];

    if (!formularioBusqueda.textoBusqueda.value){
        avisoDatosBusqueda.textContent = "Los campos son obligatorios.";
        avisoDatosBusqueda.className = avisoDatosBusqueda.className.replace(" ocultarAviso","");
    }else if (formularioBusqueda.textoBusqueda.value.length < 3){
        avisoDatosBusqueda.textContent = "Tienes que introducir al menos 3 caracteres.";
        avisoDatosBusqueda.className = avisoDatosBusqueda.className.replace(" ocultarAviso","");
    }else{
        if (formularioBusqueda.tipoBusqueda.value === "cole"){
            let urlAcceso = "http://localhost:8080/colecciones?nombre=" + formularioBusqueda.textoBusqueda.value;
            
            fetch(urlAcceso, {
            method: "GET",
            headers: {"Content-Type": "application/json",},
            })
            .then((res) => res.json())
            .catch((error) => errorCargaDatos(error))
            .then((response) => cargaDatosColeccion(response));

        }else if (formularioBusqueda.tipoBusqueda.value === "crom"){
            let urlAcceso = "http://localhost:8080/cromos?nombre=" + formularioBusqueda.textoBusqueda.value;
            
            fetch(urlAcceso, {
            method: "GET",
            headers: {"Content-Type": "application/json",},
            })
            .then((res) => res.json())
            .catch((error) => errorCargaDatos(error))
            .then((response) => cargaDatosCromo(response));

        }else if (formularioBusqueda.tipoBusqueda.value === "cate"){
            let urlAcceso = "http://localhost:8080/categorias?nombre=" + formularioBusqueda.textoBusqueda.value;
            
            fetch(urlAcceso, {
            method: "GET",
            headers: {"Content-Type": "application/json",},
            })
            .then((res) => res.json())
            .catch((error) => errorCargaDatos(error))
            .then((response) => cargaDatosCategoria(response));
        }
    }

}


