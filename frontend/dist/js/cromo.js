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

    let urlAcceso = "http://localhost:8080/paginas/cromos/";
    let idColeccion = sessionStorage.getItem("CromosDatabaseCrom");
    if (parseInt(idColeccion)){
        urlAcceso += idColeccion;
    }else{
        urlAcceso += parseInt((Math.random()*100)+1);
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
            sessionStorage.setItem("CromosDatabaseCole", respuesta.coleccion.id);
            sessionStorage.setItem("CromosDatabaseCate", respuesta.coleccion.categoria.id);
            let cromo = respuesta.cromo;
            let coleccion = respuesta.coleccion;
            let tituloCromo = document.getElementById("tituloCromo");
            tituloCromo.textContent = cromo.nombre;

            let descripcionCromo = document.getElementById("descripcionCromo");
            let textoDescripcionCromo = "<b>Colección</b>: " + coleccion.nombre + ". <a href='coleccion.html'>Ver colección</a>.<br/>";
            textoDescripcionCromo += "<b>Descripción:</b> " + cromo.descripcion + "<br/>";
            textoDescripcionCromo += "<b>Año, país y editorial</b>: " + coleccion.periodo + ", " + coleccion.pais + ", " + coleccion.editorial.nombre + ".<br/>";
            textoDescripcionCromo += "<b>Categoría y subcategoría:</b> " + coleccion.categoria.nombre + " / " + coleccion.subcategoria.nombre + ". <a href='categoria.html'>Ver categoría</a>.<br/>";

            if (sessionStorage.getItem("CromosDatabaseAuth") && cromosDatabaseNomb){
                if (respuesta.cromoUsuario){
                    textoDescripcionCromo += "<br/>Tienes esta cromo agregado a tu cuenta. Puedes editar la cantidad que tienes, pero no te olvides de darle a guardar abajo del todo!";
                }else{
                    textoDescripcionCromo += "<br/><b>" + cromosDatabaseNomb + "</b>";
                    textoDescripcionCromo += ", puedes agregar esta colección a tu cuenta o indicar que tienes este cromo pulsando aquí: ";
                    textoDescripcionCromo += "<div><button type='button' class='btn btn-sm btn-outline-secondary' id='"
                    textoDescripcionCromo += "botonAgregarColeccionUsuario" + coleccion.id + "'";
                    textoDescripcionCromo += " onClick='clickBotonAgregarColeccionUsuario(" + coleccion.id + ")'>Agregar colección</button></div>";
                }

                if (cromosDatabaseNomb){
                    enlaceAcceso.textContent = "Desconéctate como: " + cromosDatabaseNomb;
                    enlaceAcceso.href = "#";
                }

            }else{
                textoDescripcionCromo += "<br/>Si quieres apuntar que tienes este cromo accede o crea tu cuenta aquí: ";
                textoDescripcionCromo += "<a href='acceso.html'>Acceder</a>.";
            }

            let imagenDelantera = document.getElementById("imagenDelantera");
            imagenDelantera.src = "img/1954_Bowman_Mickey_Mantle.jpg"; //cambiar por urlImgDelantera
            imagenDelantera.alt = "Imagen delantera del cromo " + cromo.nombre;
            let imagenTrasera = document.getElementById("imagenTrasera");
            imagenTrasera.src = "img/1954_Bowman_Mickey_Mantle.jpg"; //cambiar por urlImgTrase
            imagenTrasera.alt = "Imagen trasera del cromo " + cromo.nombre;

            let pElementoTituloCromo = document.createElement("p");
            pElementoTituloCromo.innerHTML = textoDescripcionCromo;
            descripcionCromo.appendChild(pElementoTituloCromo);

            let divCromos = document.getElementById("divCromos");
            let divFilaUsuarios = document.getElementById("divFilaUsuarios");
            let divFilaUsuariosDescripcion = document.getElementById("divFilaUsuariosDescripcion");
            let textoDivFilaUsuariosDescripcion;
            let divElementoTexto;

/* Aquí se añade un botón para ver la colección, pero no estoy convencido de ponerlo...

            textoDescripcionCromo = "<div><button type='button' class='btn btn-sm btn-outline-secondary' id='";
            textoDescripcionCromo += "botonVerColeccion" + coleccion.id + "'";
            textoDescripcionCromo += " onClick='clickVerColeccion(" + coleccion.id + ")'>Ver colección</button></div>";

            divElementoTexto = document.createElement("div");
            divElementoTexto.innerHTML = textoDescripcionCromo;
            divFilaUsuariosDescripcion.appendChild(divElementoTexto);
*/

            // Añadimos los usuario que tienen este cromo recuperando el DIV, generando elementos dinámicamente y los añadimos
            if (respuesta.usuariosPoseedores && respuesta.usuariosPoseedores.length){

                textoDivFilaUsuariosDescripcion = "<p>Actualmente hay " + respuesta.usuariosPoseedores.length + " usuarios que tienen este cromo:";
                divElementoTexto = document.createElement("div");
                divElementoTexto.innerHTML = textoDivFilaUsuariosDescripcion;
                divFilaUsuariosDescripcion.appendChild(divElementoTexto);

                let textoDivElementoCromo;
                let divElementoCromo;
                respuesta.usuariosPoseedores.forEach(usuario => {
                    textoDivElementoCromo = "<div class='col'><div class='card shadow-sm'><div class='card-body'><p class='card-text'>";
                    textoDivElementoCromo += "<p><b>Nombre:</b> " + usuario.nombreMostrar + "<br/>";
                    if (cromosDatabaseNomb){
                        textoDivElementoCromo += "<b>Correo:</b> " + usuario.email + "<br/></p>";
                    } else{
                        textoDivElementoCromo += "<b>Correo:</b> Solo los usuarios logados pueden ver el correo: <a href='acceso.html'>Acceder</a>.<br/></p>";
                    }
                    textoDivElementoCromo += "</div></div></div>";
                    divElementoCromo = document.createElement("div");
                    divElementoCromo.innerHTML = textoDivElementoCromo;
                    divFilaUsuarios.appendChild(divElementoCromo);
                });

            } else {

                textoDivFilaUsuariosDescripcion = "<p>Ningún usuario tiene este cromo disponible para el intercambio ahora mismo. <b>Debe ser muy raro!!</b> ";
                textoDivFilaUsuariosDescripcion += "Si tú le tienes agregégale y ayuda a alguien a terminar su colección.</p>";
                divElementoTexto = document.createElement("div");
                divElementoTexto.innerHTML = textoDivFilaUsuariosDescripcion;
                divFilaUsuariosDescripcion.appendChild(divElementoTexto);

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


