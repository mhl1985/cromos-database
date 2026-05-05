//Primera carga de datos para mostrar la página
function cargaInicial() {
    

    let divNovedades = document.getElementById("divFilaNovedades");
    if (divFilaNovedades.className.indexOf("ocultarAviso")===-1){
        divFilaNovedades.className += " ocultarAviso";
    }

    let divColecciones = document.getElementById("divColecciones");
    if (divColecciones.className.indexOf("ocultarAviso")===-1){
        divColecciones.className += " ocultarAviso";
    }

    let divCategorias = document.getElementById("divCategorias");
    if (divCategorias.className.indexOf("ocultarAviso")===-1){
        divCategorias.className += " ocultarAviso";
    }

    let divIntercambios = document.getElementById("divIntercambios");
    if (divIntercambios.className.indexOf("ocultarAviso")===-1){
        divIntercambios.className += " ocultarAviso";
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

        // Añadimos los últimos cambios recuperando el DIV, generando elementos dinámicamente y los añadimos
        let divFilaNovedades = document.getElementById("divFilaNovedades");
        let textoDivElementoCromo;
        let divElementoCromo;

        respuesta.ultimosCromos.forEach(cromo => {
            textoDivElementoCromo = "<div class='col'><div class='card shadow-sm'><img src=";
            textoDivElementoCromo += "img/1954_Bowman_Mickey_Mantle.jpg"; //cambiar por urlImgDelantera
            textoDivElementoCromo += " alt=";
            textoDivElementoCromo += cromo.nombre;
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
            textoDivElementoCromo += ">Ver colección</button>";
            textoDivElementoCromo += "</div><small class='text-body-secondary'>";
            textoDivElementoCromo += cromo.numero;
            textoDivElementoCromo += "</small></div></div></div></div>";
            divElementoCromo = document.createElement("div");
            divElementoCromo.innerHTML = textoDivElementoCromo;
            divFilaNovedades.appendChild(divElementoCromo);
        });

        divFilaNovedades.className = divFilaNovedades.className.replace(" ocultarAviso","");

        
        let divColecciones = document.getElementById("divColecciones");
        divColecciones.className = divColecciones.className.replace(" ocultarAviso","");

        let divCategorias = document.getElementById("divCategorias");
        divCategorias.className = divCategorias.className.replace(" ocultarAviso","");

        let divIntercambios = document.getElementById("divIntercambios");
        divIntercambios.className = divIntercambios.className.replace(" ocultarAviso","");

    } catch (error) {
        errorCargaDatos(error);
        let alertAvisoErrorGeneral = document.getElementById("avisoErrorGeneral");
        alertAvisoErrorGeneral.className = alertAvisoErrorGeneral.className.replace(" ocultarAviso","");

    }
}

//Error en la cargar del servicio
function errorCargaDatos(error){
    console.error("Error en la carga");
    let alertAvisoErrorGeneral = document.getElementById("avisoErrorGeneral");
    alertAvisoErrorGeneral.className = alertAvisoErrorGeneral.className.replace(" ocultarAviso","");
}

