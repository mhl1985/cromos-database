//Primera carga de datos para mostrar la página
function cargaInicial() {

    let divNovedades = document.getElementById("divNovedades");
    if (divNovedades.className.indexOf("ocultarContenedor")===-1){
        divNovedades.className += " ocultarContenedor";
    }
    let divAleatorios = document.getElementById("divAleatorios");
    if (divAleatorios.className.indexOf("ocultarContenedor")===-1){
        divAleatorios.className += " ocultarContenedor";
    }
    let divColecciones = document.getElementById("divColecciones");
    if (divColecciones.className.indexOf("ocultarContenedor")===-1){
        divColecciones.className += " ocultarContenedor";
    }
    let divCategorias = document.getElementById("divCategorias");
    if (divCategorias.className.indexOf("ocultarContenedor")===-1){
        divCategorias.className += " ocultarContenedor";
    }
    let divUsuario = document.getElementById("divUsuario");
    if (divUsuario.className.indexOf("ocultarContenedor")===-1){
        divUsuario.className += " ocultarContenedor";
    }
    let alertAvisoErrorGeneral = document.getElementById("avisoErrorGeneral");
    if (alertAvisoErrorGeneral.className.indexOf("ocultarAviso")===-1){
        alertAvisoErrorGeneral.className += " ocultarAviso";
    }

    let urlAcceso = "http://localhost:8080/paginas/inicio";
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

            // Añadimos los cromos aleatorios recuperando el DIV, generando elementos dinámicamente y los añadimos
            if (respuesta.cromosAleatorios){
                let divAleatorios = document.getElementById("divAleatorios");
                let divFilaAleatorios = document.getElementById("divFilaAleatorios");
                let textoDivElementoCromoAleatorio;
                let divElementoCromoAleatorio;

                respuesta.cromosAleatorios.forEach(cromoAle => {
                    textoDivElementoCromoAleatorio = "<div class='col'><div class='card shadow-sm'><img src=";
                    textoDivElementoCromoAleatorio += "img/1954_Bowman_Mickey_Mantle.jpg"; //cambiar por urlImgDelantera
                    textoDivElementoCromoAleatorio += " alt=" + cromoAle.nombre;
                    textoDivElementoCromoAleatorio += "><div class='cromo-titulo'><p>";
                    textoDivElementoCromoAleatorio += cromoAle.nombre;
                    textoDivElementoCromoAleatorio += "</p></div><div class='card-body'><p class='card-text'>";
                    textoDivElementoCromoAleatorio += "<b>Nombre y tipo:</b> " + cromoAle.nombre + ", " + cromoAle.tipo;
                    textoDivElementoCromoAleatorio += "</p><div class='d-flex justify-content-between align-items-center'><div class='btn-group'>";
                    textoDivElementoCromoAleatorio += "<button type='button' class='btn btn-sm btn-outline-secondary' id='"
                    textoDivElementoCromoAleatorio += "botonUltimosCromos" + cromoAle.id + "'";
                    textoDivElementoCromoAleatorio += " onClick='clickUltimosCromosCromo(" + cromoAle.id + ")'>Ver cromo</button>";
                    textoDivElementoCromoAleatorio += "<button type='button' class='btn btn-sm btn-outline-secondary' id='"
                    textoDivElementoCromoAleatorio += "botonColeccionUltimosCromos" + cromoAle.id + "'";
                    textoDivElementoCromoAleatorio += " onClick='clickUltimosCromosColeccion(" + cromoAle.id + ")'>Ver colección</button>";
                    textoDivElementoCromoAleatorio += "</div><small class='text-body-secondary'><b><abbr title='Número'>Nº</abbr>:</b> " + cromoAle.numero;
                    textoDivElementoCromoAleatorio += "</small></div></div></div></div>";
                    divElementoCromoAleatorio = document.createElement("div");
                    divElementoCromoAleatorio.innerHTML = textoDivElementoCromoAleatorio;
                    divFilaAleatorios.appendChild(divElementoCromoAleatorio);
                });
                divAleatorios.className = divAleatorios.className.replace(" ocultarContenedor","");
            }

            // Añadimos las colecciones recuperando el DIV, generando elementos dinámicamente y los añadimos
            if (respuesta.ultimasColecciones){
                let divColecciones = document.getElementById("divColecciones");
                let divFilaColecciones = document.getElementById("divFilaColecciones");
                let textoDivElementoColeccion;
                let divElementoColeccion;

                respuesta.ultimasColecciones.forEach(coleccion => {
                    textoDivElementoColeccion = "<div class='col'><div class='card shadow-sm'><img src=";
                    textoDivElementoColeccion += "img/1954_Bowman_Mickey_Mantle.jpg"; //cambiar por urlImgDelantera
                    textoDivElementoColeccion += " alt=" + coleccion.nombre;
                    textoDivElementoColeccion += "><div class='cromo-titulo'><p>";
                    textoDivElementoColeccion += coleccion.nombre;
                    textoDivElementoColeccion += "</p></div><div class='card-body'><p class='card-text'>";
                    textoDivElementoColeccion += "<b>Año, país y editorial</b>: " + coleccion.periodo + ", " + coleccion.pais + ", " + coleccion.editorial.nombre + ".<br/>";
                    textoDivElementoColeccion += "<b>Categoría y subcategoría:</b> " + coleccion.categoria.nombre + " / " + coleccion.subcategoria.nombre + ".<br/>";
                    textoDivElementoColeccion += "<b>Descripción:</b> " + coleccion.descripcion;
                    textoDivElementoColeccion += "</p><div class='d-flex justify-content-between align-items-center'><div class='btn-group'>";
                    textoDivElementoColeccion += "<button type='button' class='btn btn-sm btn-outline-secondary' id='"
                    textoDivElementoColeccion += "botonUltimasColeccionesColeccion" + coleccion.id + "'";
                    textoDivElementoColeccion += " onClick='clickUltimasColeccionesColeccion(" + coleccion.id + ")'>Ver colección</button>";
                    textoDivElementoColeccion += "<button type='button' class='btn btn-sm btn-outline-secondary' id='"
                    textoDivElementoColeccion += "botonUltimasColeccionesCategoria" + coleccion.categoria.id + "'";
                    textoDivElementoColeccion += " onClick='clickUltimasColeccionesCategoria(" + coleccion.categoria.id + ")'>Ver categoría</button>";
                    textoDivElementoColeccion += "</div><small class='text-body-secondary'>" + coleccion.categoria.nombre;
                    textoDivElementoColeccion += "</small></div></div></div></div>";
                    divElementoColeccion = document.createElement("div");
                    divElementoColeccion.innerHTML = textoDivElementoColeccion;
                    divFilaColecciones.appendChild(divElementoColeccion);
                });
                divColecciones.className = divColecciones.className.replace(" ocultarContenedor","");
            }

            // Añadimos las categorías recuperando el DIV, generando elementos dinámicamente y los añadimos
            if (respuesta.categorias){
                let divCategorias = document.getElementById("divCategorias");
                let divFilaCategorias = document.getElementById("divFilaCategorias");
                let textoDivElementoCategoria;
                let divElementoCategoria;

                respuesta.categorias.forEach(categoria => {
                    textoDivElementoCategoria = "<div class='col'><div class='card shadow-sm'>";
                    textoDivElementoCategoria += "<div class='card-body categoria-boton'><div class='d-flex justify-content-between align-items-center'>";
                    textoDivElementoCategoria += categoria.nombre;
                    textoDivElementoCategoria += "<button type='button' class='btn btn-sm btn-outline-secondary' id='"
                    textoDivElementoCategoria += "botonCategoria" + categoria.id + "'";
                    textoDivElementoCategoria += " onClick='clickCategorias(" + categoria.id + ")'>Ver categoría</button>";
                    textoDivElementoCategoria += "</div></div></div></div>";
                    divElementoCategoria = document.createElement("div");
                    divElementoCategoria.innerHTML = textoDivElementoCategoria;
                    divFilaCategorias.appendChild(divElementoCategoria);
                });
                divCategorias.className = divCategorias.className.replace(" ocultarContenedor","");
            }

            // Si hay usuario logado, añadimos sus datos
            if (respuesta.actividadUsuario){
                let divUsuario = document.getElementById("divUsuario");
                let divFilaUsuario = document.getElementById("divFilaUsuario");
                let textoDivElementoUsuario;
                let divElementoUsuario;
                let actividad = respuesta.actividadUsuario;
                let títuloUsuario = document.getElementById("títuloUsuario");
                let enlaceAcceso = document.getElementById("enlaceAcceso");

                //Personalizamos la página si es posible
                let cromosDatabaseNomb = sessionStorage.getItem("CromosDatabaseNomb");
                if (cromosDatabaseNomb){
                    enlaceAcceso.textContent = "Desconéctate como: " + cromosDatabaseNomb;
                    enlaceAcceso.href = "#";
                    títuloUsuario.textContent = cromosDatabaseNomb + ", esta son tus últimas acciones.";
                }else{
                    títuloUsuario.textContent = "Aquí tus últimas acciones.";
                }


                // Informamos de las Colecciones agregadas
                textoDivElementoUsuario = "<div class='col'><div class='card shadow-sm'>";
                textoDivElementoUsuario += "<div class='card-body categoria-boton'><div class='d-flex justify-content-between align-items-center'>";
                textoDivElementoUsuario += "Tienes " + actividad.totalColecciones + " colecciones agregadas.";
                if (actividad.totalColecciones!==0){
                    textoDivElementoUsuario += " Más abajo puedes ver las últimas.";
                }
                textoDivElementoUsuario += "</div></div></div></div>";
                divElementoUsuario = document.createElement("div");
                divElementoUsuario.innerHTML = textoDivElementoUsuario;
                divFilaUsuario.appendChild(divElementoUsuario);
                textoDivElementoUsuario = "";

                // Informamos de los cromos del usuario
                textoDivElementoUsuario = "<div class='col'><div class='card shadow-sm'>";
                textoDivElementoUsuario += "<div class='card-body categoria-boton'><div class='d-flex justify-content-between align-items-center'>";
                textoDivElementoUsuario += "Tienes " + actividad.totalCromosUsuario + " cromos en tu colección.";
                textoDivElementoUsuario += "</div></div></div></div>";
                divElementoUsuario = document.createElement("div");
                divElementoUsuario.innerHTML = textoDivElementoUsuario;
                divFilaUsuario.appendChild(divElementoUsuario);
                textoDivElementoUsuario = "";

                // Informamos de los cromos intercambiables
                textoDivElementoUsuario = "<div class='col'><div class='card shadow-sm'>";
                textoDivElementoUsuario += "<div class='card-body categoria-boton'><div class='d-flex justify-content-between align-items-center'>";
                textoDivElementoUsuario += "Tienes " + actividad.totalCromosIntercambiables + " cromos disponibles para el intercambio.";
                textoDivElementoUsuario += "</div></div></div></div>";
                divElementoUsuario = document.createElement("div");
                divElementoUsuario.innerHTML = textoDivElementoUsuario;
                divFilaUsuario.appendChild(divElementoUsuario);
                textoDivElementoUsuario = "";

                actividad.ultimasColeccionesAgregadas.forEach(coleccionUsu => {
                    textoDivElementoUsuario = "<div class='col'><div class='card shadow-sm'><img src=";
                    textoDivElementoUsuario += "img/1954_Bowman_Mickey_Mantle.jpg"; //cambiar por urlImgDelantera
                    textoDivElementoUsuario += " alt=" + coleccionUsu.nombre;
                    textoDivElementoUsuario += "><div class='cromo-titulo'><p>";
                    textoDivElementoUsuario += coleccionUsu.nombre;
                    textoDivElementoUsuario += "</p></div><div class='card-body'><p class='card-text'>";
                    textoDivElementoUsuario += "<b>Año, país y editorial</b>: " + coleccionUsu.periodo + ", " + coleccionUsu.pais + ", " + coleccionUsu.editorial.nombre + ".<br/>";
                    textoDivElementoUsuario += "<b>Categoría y subcategoría:</b> " + coleccionUsu.categoria.nombre + " / " + coleccionUsu.subcategoria.nombre + ".<br/>";
                    textoDivElementoUsuario += "<b>Descripción:</b> " + coleccionUsu.descripcion;
                    textoDivElementoUsuario += "</p><div class='d-flex justify-content-between align-items-center'><div class='btn-group'>";
                    textoDivElementoUsuario += "<button type='button' class='btn btn-sm btn-outline-secondary' id='"
                    textoDivElementoUsuario += "botonUltimasColeccionesColeccion" + coleccionUsu.id + "'";
                    textoDivElementoUsuario += " onClick='clickUltimasColeccionesColeccion(" + coleccionUsu.id + ")'>Ver colección</button>";
                    textoDivElementoUsuario += "<button type='button' class='btn btn-sm btn-outline-secondary' id='"
                    textoDivElementoUsuario += "botonUltimasColeccionesCategoria" + coleccionUsu.categoria.id + "'";
                    textoDivElementoUsuario += " onClick='clickUltimasColeccionesCategoria(" + coleccionUsu.categoria.id + ")'>Ver categoría</button>";
                    textoDivElementoUsuario += "</div><small class='text-body-secondary'>" + coleccionUsu.categoria.nombre;
                    textoDivElementoUsuario += "</small></div></div></div></div>";
                    divElementoUsuario = document.createElement("div");
                    divElementoUsuario.innerHTML = textoDivElementoUsuario;
                    divFilaUsuario.appendChild(divElementoUsuario);
                });

                divUsuario.className = divUsuario.className.replace(" ocultarContenedor","");
            }

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
    console.error("Error en la carga");
    let alertAvisoErrorGeneral = document.getElementById("avisoErrorGeneral");
    alertAvisoErrorGeneral.className = alertAvisoErrorGeneral.className.replace(" ocultarAviso","");
}


function clickColeccionCromo(id){
    console.log("pulsado el botón con ID: " + id);
    let urlAcceso = "http://localhost:8080/paginas/colecciones/" + id;
    
    fetch(urlAcceso, {
    method: "GET",
    headers: {"Content-Type": "application/json",},
    })
    .then((res) => res.json())
    .catch((error) => console.error(error))
    .then((response) => console.log(response));

}


function clickUltimosCromosCromo (id){
    console.log("clickUltimosCromosCromo id:" + id);
}


function clickUltimosCromosColeccion (id){
    console.log("clickUltimosCromosColeccion id:" + id);
}


function clickUltimasColeccionesColeccion (id){
    console.log("clickUltimasColeccionesColeccion id:" + id);
    sessionStorage.setItem("CromosDatabaseCole", id);
    window.location.href = "coleccion.html";
}


function clickUltimasColeccionesCategoria (id){
    console.log("clickUltimasColeccionesCategoria id:" + id);
}


function clickCategorias (id){
    console.log("clickCategorias id:" + id);
}


function clickEnlaceAcceso (){
    console.log("clickEnlaceAcceso");
    if (enlaceAcceso.textContent != "Acceso"){
        sessionStorage.setItem("CromosDatabaseAuth", "");
        sessionStorage.setItem("CromosDatabaseNomb", "");
        sessionStorage.setItem("CromosDatabaseCole", "");
        event.preventDefault();
        location.reload();
    }
}


