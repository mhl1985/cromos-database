//Primera carga de datos para mostrar la página
function cargaInicial() {

    let divUsuarios = document.getElementById("divUsuarios");
    if (divUsuarios.className.indexOf("ocultarContenedor")===-1){
        divUsuarios.className += " ocultarContenedor";
    }
    let alertAvisoErrorGeneral = document.getElementById("avisoErrorGeneral");
    if (alertAvisoErrorGeneral.className.indexOf("ocultarAviso")===-1){
        alertAvisoErrorGeneral.className += " ocultarAviso";
    }

    let urlAcceso = "http://localhost:8080/admin/usuarios";
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
        //Comprobamos que no se ha devuelto ningún código de error y que hay datos en el listado
        if (!respuesta.status && respuesta.length){

            //Personalizamos la página si es posible
            let cromosDatabaseNomb = sessionStorage.getItem("CromosDatabaseNomb");
            let enlaceAcceso = document.getElementById("enlaceAcceso");
            if (cromosDatabaseNomb){
                enlaceAcceso.textContent = "Desconéctate como: " + cromosDatabaseNomb;
                enlaceAcceso.href = "#";
            }

            //Mostramos cabecera con datos y botón
            let descripcionListaUsuarios = document.getElementById("descripcionUsuarios");
            let textoDescripcionListaUsuarios = "<p>Ahora mismo hay " + respuesta.length + " usuarios en Cromos Database. Pulsa el botón para editarlos.<br/>No te olvides de volver a darle para guardar los datos.</p>";
            let divElementoListaUsuarios = document.createElement("div");
            divElementoListaUsuarios.innerHTML = textoDescripcionListaUsuarios;
            descripcionListaUsuarios.appendChild(divElementoListaUsuarios);

            // Mostramos los usuarios recuperando el DIV, generando elementos dinámicamente y los añadimos
            let divUsuarios = document.getElementById("divUsuarios");
            let divFilaUsuarios = document.getElementById("divFilaUsuarios");
            let textoDivElementoUsuario;
            let divElementoUsuario;
            while (divFilaUsuarios.firstChild) {
                divFilaUsuarios.removeChild(divFilaUsuarios.firstChild);
            }
            respuesta.forEach(usuario => {
                textoDivElementoUsuario = "<div class='col'><div class='card shadow-sm'><div class='card-body'><p class='card-text'>";
                textoDivElementoUsuario += "<b>ID usuario:</b> " + usuario.idUsuario + "<br/>";
                textoDivElementoUsuario += "<b>Fecha de registro:</b> " + usuario.fechaRegistro + "<br/>";
                textoDivElementoUsuario += "</p>";
                textoDivElementoUsuario += "<div>";

                textoDivElementoUsuario += "<form class='form-usuario' name='formularioUsuario" + usuario.idUsuario + "' id='formularioUsuario" + usuario.idUsuario + "'>";
                textoDivElementoUsuario += "<div class='form-espacio'>";
                textoDivElementoUsuario += "<div class='form-floating'>";
                textoDivElementoUsuario += "<input type='text' class='form-control' id='inputUsuarioNombre" + usuario.idUsuario + "' required='true' value='" + usuario.nombreMostrar + "'/>";
                textoDivElementoUsuario += "<label for='inputUsuarioNombre" + usuario.idUsuario + "'>Nombre a mostrar</label>";
                textoDivElementoUsuario += "</div>";
                textoDivElementoUsuario += "</div>";
                textoDivElementoUsuario += "<div class='form-espacio'>";
                textoDivElementoUsuario += "<div class='form-floating'>";
                textoDivElementoUsuario += "<input type='text' class='form-control' id='inputUsuarioCorreo" + usuario.idUsuario + "' required='true' value='" + usuario.email + "'/>";
                textoDivElementoUsuario += "<label for='inputUsuarioCorreo" + usuario.idUsuario + "'>Correo electrónico</label>";
                textoDivElementoUsuario += "</div>";
                textoDivElementoUsuario += "</div>";

                textoDivElementoUsuario += "<div class='form-espacio'>";
                textoDivElementoUsuario += "<div class='form-floating'>";
                textoDivElementoUsuario += "<select class='form-control' id='selectUsuarioActivo" + usuario.idUsuario + "'>";
                if (usuario.activo){
                    textoDivElementoUsuario += "<option value='true' selected>Activo</option>";
                    textoDivElementoUsuario += "<option value='false'>Inactivo</option>";
                } else{
                    textoDivElementoUsuario += "<option value='true'>Activo</option>";
                    textoDivElementoUsuario += "<option value='false' selected>Inactivo</option>";
                }
                textoDivElementoUsuario += "</select><label for='selectUsuarioActivo" + usuario.idUsuario + "'>Usuario activo</label>";
                textoDivElementoUsuario += "</div>";
                textoDivElementoUsuario += "</div>";

                textoDivElementoUsuario += "<div class='form-espacio'>";
                textoDivElementoUsuario += "<div class='form-floating'>";
                textoDivElementoUsuario += "<select class='form-control' id='selectUsuarioRol" + usuario.idUsuario + "'>";
                if (usuario.roles.length === 1){
                    textoDivElementoUsuario += "<option value='usuario' selected>Usuario</option>";
                    textoDivElementoUsuario += "<option value='admin'>Usuario y administrador</option>";
                } else{
                    textoDivElementoUsuario += "<option value='usuario'>Usuario</option>";
                    textoDivElementoUsuario += "<option value='admin' selected>Usuario y administrador</option>";
                }
                textoDivElementoUsuario += "</select><label for='selectUsuarioRol" + usuario.idUsuario + "'>Roles del usuario</label>";
                textoDivElementoUsuario += "</div>";
                textoDivElementoUsuario += "</div>";

                textoDivElementoUsuario += "<div class='form-espacio'>";
                textoDivElementoUsuario += "<div class='form-floating'>";
                textoDivElementoUsuario += "<button type='button' class='btn btn-sm btn-outline-secondary' id='botonEditarUsuario" + usuario.idUsuario + "' onClick='botonEditarUsuario(" + usuario.idUsuario + ")'>Editar usuario con ID: " + usuario.idUsuario + "</button>";
                textoDivElementoUsuario += "</div>";
                textoDivElementoUsuario += "</div>";

                textoDivElementoUsuario += "</form>";

                textoDivElementoUsuario += "</div>";
                textoDivElementoUsuario += "</div>";
                textoDivElementoUsuario += "</div></div>";
                divElementoUsuario = document.createElement("div");
                divElementoUsuario.innerHTML = textoDivElementoUsuario;
                divFilaUsuarios.appendChild(divElementoUsuario);
            });
            divUsuarios.className = divUsuarios.className.replace(" ocultarContenedor","");

        } else {
            let alertAvisoErrorGeneral = document.getElementById("avisoErrorGeneral");
            alertAvisoErrorGeneral.textContent = respuesta.mensaje?respuesta.mensaje:"Error inesperado, inténtalo de nuevo otra vez y si el error persiste comprueba tu conexión.";
            alertAvisoErrorGeneral.className = alertAvisoErrorGeneral.className.replace(" ocultarAviso","");            

        }
    } catch (error) {
        errorCargaDatos(error);
        let alertAvisoErrorGeneral = document.getElementById("avisoErrorGeneral");
        alertAvisoErrorGeneral.textContent = "Error inesperado, inténtalo de nuevo otra vez y si el error persiste comprueba tu conexión.";
        alertAvisoErrorGeneral.className = alertAvisoErrorGeneral.className.replace(" ocultarAviso","");

    }
}


//Error en la cargar del servicio
function errorCargaDatos(error){
    let divUsuarios = document.getElementById("divUsuarios");
    if (divUsuarios.className.indexOf("ocultarContenedor")===-1){
        divUsuarios.className += " ocultarContenedor";
    }
    let alertAvisoErrorGeneral = document.getElementById("avisoErrorGeneral");
    alertAvisoErrorGeneral.className = alertAvisoErrorGeneral.className.replace(" ocultarAviso","");
}


//Añadimos los formularios para cada caso
function botonEditarUsuario(id){

    let email = document.getElementById("inputUsuarioCorreo" + id).value;
    let nombreMostrar = document.getElementById("inputUsuarioNombre" + id).value;
    let activo = document.getElementById("selectUsuarioActivo" + id).value;
    let roles = document.getElementById("selectUsuarioRol" + id).value;
    if (roles === "usuario"){
        roles = [1];
    } else {
        roles = [1, 2];
    }
    let datosPut = { "email": email, "nombreMostrar": nombreMostrar, "activo": activo, "roles": roles};

    let urlAcceso = "http://localhost:8080/admin/usuarios/" + id;
    let autenticacion = sessionStorage.getItem("CromosDatabaseAuth");
    
    fetch(urlAcceso, {
        method: "PUT",
        headers: {"Content-Type": "application/json","Authorization": autenticacion},
        body: JSON.stringify(datosPut),
    })
    .then((res) => res.json())
    .catch((error) => errorCargaDatos(error))
    .then((response) => comprobarEdicion(response));

}

//Si funciona la edición muestro aviso
function comprobarEdicion(respuesta){

    if(respuesta && respuesta.status){
        let divUsuarios = document.getElementById("divUsuarios");
        if (divUsuarios.className.indexOf("ocultarContenedor")===-1){
            divUsuarios.className += " ocultarContenedor";
        }
        let avisoCorrectoCambio = document.getElementById("avisoCorrectoCambio");
        if (avisoCorrectoCambio.className.indexOf("ocultarAviso")===-1){
            avisoCorrectoCambio.className += " ocultarAviso";
        }
        let alertAvisoErrorGeneral = document.getElementById("avisoErrorGeneral");
        alertAvisoErrorGeneral.textContent = respuesta.mensaje?respuesta.mensaje:"Error inesperado, inténtalo de nuevo otra vez y si el error persiste comprueba tu conexión.";
        alertAvisoErrorGeneral.className = alertAvisoErrorGeneral.className.replace(" ocultarAviso","");
    } else {
        let divUsuarios = document.getElementById("divUsuarios");
        if (divUsuarios.className.indexOf("ocultarContenedor")===-1){
            divUsuarios.className += " ocultarContenedor";
        }
        let alertAvisoErrorGeneral = document.getElementById("avisoErrorGeneral");
        if (alertAvisoErrorGeneral.className.indexOf("ocultarAviso")===-1){
            alertAvisoErrorGeneral.className += " ocultarAviso";
        }
        let avisoCorrectoCambio = document.getElementById("avisoCorrectoCambio");
        avisoCorrectoCambio.className = avisoCorrectoCambio.className.replace(" ocultarAviso","");

    }

}


//Si funciona la edición muestro aviso
function mostrarAvisoOK(){
    let divUsuarios = document.getElementById("divUsuarios");
    if (divUsuarios.className.indexOf("ocultarContenedor")===-1){
        divUsuarios.className += " ocultarContenedor";
    }
    let alertAvisoErrorGeneral = document.getElementById("avisoErrorGeneral");
    if (alertAvisoErrorGeneral.className.indexOf("ocultarAviso")===-1){
        alertAvisoErrorGeneral.className += " ocultarAviso";
    }
    let avisoCorrectoCambio = document.getElementById("avisoCorrectoCambio");
    avisoCorrectoCambio.className = avisoCorrectoCambio.className.replace(" ocultarAviso","");
}


//Borramos datos de usuario
function clickEnlaceAcceso (){
    if (enlaceAcceso.textContent != "Acceso"){
        sessionStorage.setItem("CromosDatabaseAuth", "");
        sessionStorage.setItem("CromosDatabaseNomb", "");
        sessionStorage.setItem("CromosDatabaseAdmi", "");
        sessionStorage.setItem("CromosDatabaseCrom", "");
        sessionStorage.setItem("CromosDatabaseCole", "");
        sessionStorage.setItem("CromosDatabaseCate", "");
        event.preventDefault();
        location.href = "inicio.html";
    }
}


