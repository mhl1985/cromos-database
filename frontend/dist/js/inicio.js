//Primera carga de datos para mostrar la página
function cargaInicial() {

    let divNovedades = document.getElementById("divNovedades");
    if (divNovedades.className.indexOf("ocultarContenedor")===-1){
        divNovedades.className += " ocultarContenedor";
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

    let urlAcceso = "http://localhost:8080/paginas/inicio";
    
    fetch(urlAcceso, {
    method: "GET",
    headers: {"Content-Type": "application/json",},
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

            // Añadimos las novedades recuperando el DIV, generando elementos dinámicamente y los añadimos
            if (respuesta.ultimosCromos){
                let divNovedades = document.getElementById("divNovedades");
                let divFilaNovedades = document.getElementById("divFilaNovedades");
                let textoDivElementoCromo;
                let divElementoCromo;

                respuesta.ultimosCromos.forEach(cromo => {
                    textoDivElementoCromo = "<div class='col'><div class='card shadow-sm'><img src=";
                    textoDivElementoCromo += "img/1954_Bowman_Mickey_Mantle.jpg"; //cambiar por urlImgDelantera
                    textoDivElementoCromo += " alt=" + cromo.nombre;
                    textoDivElementoCromo += "><div class='cromo-titulo'><p>";
                    textoDivElementoCromo += cromo.nombre;
                    textoDivElementoCromo += "</p></div><div class='card-body'><p class='card-text'>";
                    textoDivElementoCromo += "Nombre: " + cromo.nombre +", estado: " + cromo.tipo;
                    textoDivElementoCromo += "</p><div class='d-flex justify-content-between align-items-center'><div class='btn-group'>";
                    textoDivElementoCromo += "<button type='button' class='btn btn-sm btn-outline-secondary' id='"
                    textoDivElementoCromo += "botonUltimosCromos" + cromo.id + "'";
                    textoDivElementoCromo += ">Ver cromo</button>";
                    textoDivElementoCromo += "<button type='button' class='btn btn-sm btn-outline-secondary' id='"
                    textoDivElementoCromo += "botonColeccionUltimosCromos" + cromo.id + "'";
                    textoDivElementoCromo += " onClick='clickColeccionCromo(" + cromo.id + ")'>Ver colección</button>";
                    textoDivElementoCromo += "</div><small class='text-body-secondary'>" + cromo.numero;
                    textoDivElementoCromo += "</small></div></div></div></div>";
                    divElementoCromo = document.createElement("div");
                    divElementoCromo.innerHTML = textoDivElementoCromo;
                    divFilaNovedades.appendChild(divElementoCromo);
                });
                divNovedades.className = divNovedades.className.replace(" ocultarContenedor","");
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
                    textoDivElementoColeccion += coleccion.periodo + ". " + coleccion.descripcion;
                    textoDivElementoColeccion += "</p><div class='d-flex justify-content-between align-items-center'><div class='btn-group'>";
                    textoDivElementoColeccion += "<button type='button' class='btn btn-sm btn-outline-secondary' id='"
                    textoDivElementoColeccion += "botonUltimasColecciones" + coleccion.id + "'";
                    textoDivElementoColeccion += ">Ver colección</button>";
                    textoDivElementoColeccion += "</div><small class='text-body-secondary'>" + coleccion.pais;
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
                    textoDivElementoCategoria += "<div class='card-body'><div class='d-flex justify-content-between align-items-center'>";
                    textoDivElementoCategoria += categoria.nombre;
                    textoDivElementoCategoria += "<button type='button' class='btn btn-sm btn-outline-secondary' id='"
                    textoDivElementoCategoria += "botonCategoria" + categoria.id + "'";
                    textoDivElementoCategoria += ">Ver categoría</button>";
                    textoDivElementoCategoria += "</div></div></div></div>";
                    divElementoCategoria = document.createElement("div");
                    divElementoCategoria.innerHTML = textoDivElementoCategoria;
                    divFilaCategorias.appendChild(divElementoCategoria);
                });
                divCategorias.className = divCategorias.className.replace(" ocultarContenedor","");
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