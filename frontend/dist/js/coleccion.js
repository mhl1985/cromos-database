//Primera carga de datos para mostrar la página
function cargaInicial() {

    let divCromos = document.getElementById("divCromos");
    if (divCromos.className.indexOf("ocultarContenedor")===-1){
        divCromos.className += " ocultarContenedor";
    }
    let alertAvisoErrorGeneral = document.getElementById("avisoErrorGeneral");
    if (alertAvisoErrorGeneral.className.indexOf("ocultarAviso")===-1){
        alertAvisoErrorGeneral.className += " ocultarAviso";
    }

    let urlAcceso = "http://localhost:8080/paginas/colecciones/";
    let idColeccion = sessionStorage.getItem("CromosDatabaseCole");
    if (parseInt(idColeccion)){
        urlAcceso += idColeccion;
    }else{
        urlAcceso += parseInt((Math.random()*10)+1);
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
            let coleccion = respuesta.coleccion;
            let tituloColeccion = document.getElementById("tituloColeccion");
            tituloColeccion.textContent = coleccion.nombre;

            let descripcionColeccion = document.getElementById("descripcionColeccion");
            let textoDescripcionColeccion = "<b>Año, país y editorial</b>: " + coleccion.periodo + ", " + coleccion.pais + ", " + coleccion.editorial.nombre + ".<br/>";
            textoDescripcionColeccion += "<b>Categoría y subcategoría:</b> " + coleccion.categoria.nombre + " / " + coleccion.subcategoria.nombre + ".<br/>";
            textoDescripcionColeccion += "<b>Descripción:</b> " + coleccion.descripcion + "<br/>";

            if (sessionStorage.getItem("CromosDatabaseAuth") && cromosDatabaseNomb){
                if (respuesta.cromosUsuario){
                    textoDescripcionColeccion += "<br/>Tienes esta colección agregada a tu cuenta. Puedes editar los cromos que tienes, pero no te olvides de darle a guardar abajo del todo!";
                }else{
                    textoDescripcionColeccion += "<br/><b>" + cromosDatabaseNomb + "</b>";
                    textoDescripcionColeccion += ", puedes agregar esta colección a tu cuenta pulsando aquí: ";
                    textoDescripcionColeccion += "<div><button type='button' class='btn btn-sm btn-outline-secondary' id='"
                    textoDescripcionColeccion += "botonAgregarColeccionUsuario" + coleccion.id + "'";
                    textoDescripcionColeccion += " onClick='clickBotonAgregarColeccionUsuario(" + coleccion.id + ")'>Agregar colección</button></div>";
                }

                if (cromosDatabaseNomb){
                    enlaceAcceso.textContent = "Desconéctate como: " + cromosDatabaseNomb;
                    enlaceAcceso.href = "#";
                }

            }else{
                textoDescripcionColeccion += "<br/>Si quieres apuntar tus cromos de esta colección accede o crea tu cuenta aquí: ";
                textoDescripcionColeccion += "<a href='acceso.html'>Acceder</a>";
            }

            let pElementoTituloColeccion = document.createElement("p");
            pElementoTituloColeccion.innerHTML = textoDescripcionColeccion;
            descripcionColeccion.appendChild(pElementoTituloColeccion);

            let divCromos = document.getElementById("divCromos");

            // Añadimos los cromos de la colección recuperando el DIV, generando elementos dinámicamente y los añadimos
            if (respuesta.cromos){
                let divFilaCromos = document.getElementById("divFilaCromos");
                let textoDivElementoCromo;
                let divElementoCromo;

                respuesta.cromos.forEach(cromo => {
                    textoDivElementoCromo = "<div class='col'><div class='card shadow-sm'><img src=";
                    textoDivElementoCromo += "img/1954_Bowman_Mickey_Mantle.jpg"; //cambiar por urlImgDelantera
                    textoDivElementoCromo += " alt=" + cromo.nombre;
                    textoDivElementoCromo += "><div class='cromo-titulo'><p>";
                    textoDivElementoCromo += cromo.nombre;
                    textoDivElementoCromo += "</p></div><div class='card-body'><p class='card-text'>";
                    textoDivElementoCromo += "<b>Nombre y tipo:</b> " + cromo.nombre + ", " + cromo.tipo;
                    textoDivElementoCromo += "</p><div class='d-flex justify-content-between align-items-center'><div class='btn-group'>";
                    textoDivElementoCromo += "<button type='button' class='btn btn-sm btn-outline-secondary' id='"
                    textoDivElementoCromo += "botonCromo" + cromo.id + "'";
                    textoDivElementoCromo += " onClick='clickVerCromo(" + cromo.id + ")'>Ver cromo</button>";
                    textoDivElementoCromo += "</div><small class='text-body-secondary'><b><abbr title='Número'>Nº</abbr>:</b> " + cromo.numero;
                    textoDivElementoCromo += "</small></div></div></div></div>";
                    divElementoCromo = document.createElement("div");
                    divElementoCromo.innerHTML = textoDivElementoCromo;
                    divFilaCromos.appendChild(divElementoCromo);
                });
            }

            divCromos.className = divCromos.className.replace(" ocultarContenedor","");

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


function clickBotonAgregarColeccionUsuario(id){
}


function clickVerCromo(id){
    sessionStorage.setItem("CromosDatabaseCrom", id);
    window.location.href = "cromo.html";
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


