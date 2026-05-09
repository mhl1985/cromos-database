let respuestaServicio;
let enEdicion = false;

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

            //Cargamos los datos ahora para poder cargarlos al editar el formulario
            respuestaServicio = respuesta;

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
                if (respuesta.cromosUsuario === null){
                    textoDescripcionColeccion += "<br/><b>" + cromosDatabaseNomb + "</b>";
                    textoDescripcionColeccion += ", puedes agregar esta colección a tu cuenta pulsando aquí: ";
                    textoDescripcionColeccion += "<div><button type='button' class='btn btn-sm btn-outline-secondary' id='"
                    textoDescripcionColeccion += "botonAgregarColeccionUsuario" + coleccion.id + "'";
                    textoDescripcionColeccion += " onClick='clickBotonAgregarColeccionUsuario(" + coleccion.id + ")'>Agregar colección</button></div>";
                }else if(respuesta.cromosUsuario === []){
                    textoDescripcionColeccion += "<br/>Tienes esta colección agregada a tu cuenta, pero aún no has puesto qué cromos tienes. Pulsa este botón para agregarlos:";
                    textoDescripcionColeccion += "<div><button type='button' class='btn btn-sm btn-outline-secondary' id='"
                    textoDescripcionColeccion += "botonEditarColeccion'";
                    textoDescripcionColeccion += " onClick='botonEditarColeccion()'>Editar colección</button></div>";
                    textoDescripcionColeccion += "Recuerda darle otra vez al botón para guardar tus cambios.";
                }else{
                    textoDescripcionColeccion += "<br/>Tienes esta colección agregada a tu cuenta. Puedes habilitar su edición pulsando en este botón:";
                    textoDescripcionColeccion += "<div><button type='button' class='btn btn-sm btn-outline-secondary' id='"
                    textoDescripcionColeccion += "botonEditarColeccion'";
                    textoDescripcionColeccion += " onClick='botonEditarColeccion()'>Editar colección</button></div>";
                    textoDescripcionColeccion += "Recuerda darle otra vez al botón para guardar tus cambios.";
                }

                if (cromosDatabaseNomb){
                    enlaceAcceso.textContent = "Desconéctate como: " + cromosDatabaseNomb;
                    enlaceAcceso.href = "#";
                }

            }else{
                textoDescripcionColeccion += "<br/>Si quieres apuntar tus cromos de esta colección accede o crea tu cuenta aquí: ";
                textoDescripcionColeccion += "<a href='acceso.html'>Acceder</a>.";
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
                    textoDivElementoCromo += " alt='Imagen del cromo " + cromo.nombre + "'";
                    textoDivElementoCromo += "><div class='cromo-titulo'><p>";
                    textoDivElementoCromo += cromo.nombre;
                    textoDivElementoCromo += "</p></div><div class='card-body'><p class='card-text'>";
                    textoDivElementoCromo += "<b>Nombre y tipo:</b> " + cromo.nombre + ", " + cromo.tipo;
                    textoDivElementoCromo += "</p><div class='d-flex justify-content-between align-items-center'><div class='btn-group'>";
                    textoDivElementoCromo += "<button type='button' class='btn btn-sm btn-outline-secondary' id='"
                    textoDivElementoCromo += "botonCromo" + cromo.id + "'";
                    textoDivElementoCromo += " onClick='clickVerCromo(" + cromo.id + ")'>Ver cromo</button>";
                    textoDivElementoCromo += "</div><small class='text-body-secondary'><b><abbr title='Número'>Nº</abbr>:</b> " + cromo.numero;
                    textoDivElementoCromo += "</small>";
                    textoDivElementoCromo += "</div>";
                    textoDivElementoCromo += "<div id='divFormCromo" + cromo.id + "'>";
                    textoDivElementoCromo += "</div>";
                    textoDivElementoCromo += "</div></div></div>";
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
    let divCromos = document.getElementById("divCromos");
    if (divCromos.className.indexOf("ocultarContenedor")===-1){
        divCromos.className += " ocultarContenedor";
    }
    let alertAvisoErrorGeneral = document.getElementById("avisoErrorGeneral");
    alertAvisoErrorGeneral.className = alertAvisoErrorGeneral.className.replace(" ocultarAviso","");
}



//Recargamos la página para añadir la colección
function repintarAnadir(id){
    location.reload();
}


//Añadimos los formularios para cada caso
function botonEditarColeccion(){
    if(!enEdicion){
        enEdicion = true;
        let respuesta = respuestaServicio;
        let listaTodosCromos = respuesta.cromos;
        let listaUsuarioCromos = respuesta.cromosUsuario;
        let divForm;
        let divElementoForm;
        let textoDivForm;

        listaTodosCromos.forEach(cromo => {
            textoDivForm = "<form class='form-coleccion' name='formularioCromo" + cromo.id + "' id='formularioCromo" + cromo.id + "'>";
            textoDivForm += "<div class='row'>";
            textoDivForm += "<div class='col-6'>";
            textoDivForm += "<div class='form-floating'>";
            textoDivForm += "<input type='number' class='form-control' id='inputTotalCromo" + cromo.id + "' placeholder='1' required='true' value='0' />";
            textoDivForm += "<label for='inputTotalCromo" + cromo.id + "'>Total</label>";
            textoDivForm += "</div>";
            textoDivForm += "</div>";
            textoDivForm += "<div class='col-6'>";
            textoDivForm += "<div class='form-floating'>";
            textoDivForm += "<input type='number' class='form-control' id='inputInterCromo" + cromo.id + "' placeholder='0' required='true' value='0' />";
            textoDivForm += "<label for='inputInterCromo" + cromo.id + "'>Intercambio</label>";
            textoDivForm += "</div>";
            textoDivForm += "</div>";
            textoDivForm += "</div>";
            textoDivForm += "<div class='form-espacio'>";
            textoDivForm += "<div class='form-floating'>";
            textoDivForm += "<input type='text' class='form-control' id='inputObservCromo" + cromo.id + "' placeholder='1' required='true' value=''/>";
            textoDivForm += "<label for='inputObservCromo" + cromo.id + "'>Observaciones</label>";
            textoDivForm += "</div>";
            textoDivForm += "</div>";
            textoDivForm += "</form>";
            divForm = document.getElementById("divFormCromo" + cromo.id);
            divElementoForm = document.createElement("div");
            divElementoForm.innerHTML = textoDivForm;
            divForm.appendChild(divElementoForm);
        });

        let inputTotalCromo;
        let inputInterCromo;
        let inputObservCromo;

        listaUsuarioCromos.forEach(cromoUsu => {
            inputTotalCromo = document.getElementById("inputTotalCromo" + cromoUsu.idCromo);
            inputTotalCromo.value = cromoUsu.cantidadTotal;
            inputInterCromo = document.getElementById("inputInterCromo" + cromoUsu.idCromo);
            inputInterCromo.value = cromoUsu.cantidadIntercambiable;
            inputObservCromo = document.getElementById("inputObservCromo" + cromoUsu.idCromo);
            inputObservCromo.value = cromoUsu.observaciones;
        });
    }else{
        enEdicion = false;
        let respuesta = respuestaServicio;
        let listaTodosCromos = respuesta.cromos;

        let inputTotalCromo;
        let inputInterCromo;
        let inputObservCromo;
        let valueInputTotalCromo;
        let valueInputInterCromo;
        let valueInputObservCromo;
        let datosCromos = [];
        let datoCromo;
        let formDatos;

        listaTodosCromos.forEach(cromo => {
            inputTotalCromo = document.getElementById("inputTotalCromo" + cromo.id);
            inputInterCromo = document.getElementById("inputInterCromo" + cromo.id);
            inputObservCromo = document.getElementById("inputObservCromo" + cromo.id);
            valueInputTotalCromo = inputTotalCromo.value;
            valueInputInterCromo = inputInterCromo.value;
            valueInputObservCromo = inputObservCromo.value;
            if(valueInputTotalCromo != "0"){
                datoCromo = {"idCromo": cromo.id, "marcado": true, "cantidadTotal": valueInputTotalCromo, "cantidadIntercambiable": valueInputInterCromo, "observaciones": valueInputObservCromo};
                datosCromos.push(datoCromo);
            }
            formDatos = document.getElementById("formularioCromo" + cromo.id);
            formDatos.remove();
        });



    let formularioAcceso = document.forms["formularioAcceso"];

    let urlAcceso = "http://localhost:8080/mis-colecciones/" + sessionStorage.getItem("CromosDatabaseCole") + "/cromos";
    let datosPut = { "cromos": datosCromos};
    let autenticacion = sessionStorage.getItem("CromosDatabaseAuth");
    
    fetch(urlAcceso, {
    method: "PUT",
    headers: {"Content-Type": "application/json","Authorization": autenticacion},
    body: JSON.stringify(datosPut),
    })
    .then((res) => res.json())
    .catch((error) => errorCargaDatos(error))
    .then((response) => mostrarAvisoOK(response));
    }
}


//Si funciona la edición muestro aviso
function mostrarAvisoOK(){
    let divCromos = document.getElementById("divCromos");
    if (divCromos.className.indexOf("ocultarContenedor")===-1){
        divCromos.className += " ocultarContenedor";
    }
    let alertAvisoErrorGeneral = document.getElementById("avisoCorrectoCambio");
    alertAvisoErrorGeneral.className = alertAvisoErrorGeneral.className.replace(" ocultarAviso","");
}

//Envío de datos para acceso con usuario
function clickBotonAgregarColeccionUsuario() {
    
    event.preventDefault();

    let urlAcceso = "http://localhost:8080/mis-colecciones/" + sessionStorage.getItem("CromosDatabaseCole");
    let autenticacion = sessionStorage.getItem("CromosDatabaseAuth");
    fetch(urlAcceso, {
    method: "POST",
    headers: {"Content-Type": "application/json","Authorization": autenticacion},
    })
    .then((res) => res.json())
    .catch((error) => errorCargaDatos(error))
    .then((response) => repintarAnadir(response));

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


