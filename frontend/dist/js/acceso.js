//Envío de datos para acceso con usuario
function accederServicio() {
    
    event.preventDefault();

    let avisoDatosAcceso = document.getElementById("avisoDatosAcceso");
    if (avisoDatosAcceso.className.indexOf("ocultarAviso")===-1){
        avisoDatosAcceso.className += " ocultarAviso";
    }
    let avisoErrorAcceso = document.getElementById("avisoErrorAcceso");
    if (avisoErrorAcceso.className.indexOf("ocultarAviso")===-1){
        avisoErrorAcceso.className += " ocultarAviso";
    }

    let formularioAcceso = document.forms["formularioAcceso"];

    if (formularioAcceso.accesoCorreo.value && formularioAcceso.accesoContrasena.value){
        let urlAcceso = "http://localhost:8080/auth/login";
        let datosAcceso = { "email": formularioAcceso.accesoCorreo.value, "contrasena": formularioAcceso.accesoContrasena.value};
        
        fetch(urlAcceso, {
        method: "POST",
        headers: {"Content-Type": "application/json",},
        body: JSON.stringify(datosAcceso),
        })
        .then((res) => res.json())
        .catch((error) => errorCargaDatosAcceso(error))
        .then((response) => cargaDatosAcceso(response));

    }else{
        avisoDatosAcceso.textContent = "Los campos son obligatorios.";
        avisoDatosAcceso.className = avisoDatosAcceso.className.replace(" ocultarAviso","");
    }

}


//Cargar contenido del servicio
function cargaDatosAcceso(respuesta){
    try {
        if(!respuesta.status){
            sessionStorage.setItem("token", respuesta.token);
            sessionStorage.setItem("type", "Bearer");

            document.cookie = "token=" + respuesta.token;
            document.cookie = "type=Bearer";

        }else{
            let avisoDatosAcceso = document.getElementById("avisoDatosAcceso");
            avisoDatosAcceso.textContent = respuesta.mensaje?respuesta.mensaje:"Error inesperado, inténtelo de nuevo otra vez y si el error persiste compruebe su conexión.";
            avisoDatosAcceso.className = avisoDatosAcceso.className.replace(" ocultarAviso","");
        }
    } catch (error) {
        errorCargaDatosAcceso(error);
    }
}


//Error en la cargar del servicio de acceso
function errorCargaDatosAcceso(error){
    let avisoErrorAcceso = document.getElementById("avisoErrorAcceso");
    avisoErrorAcceso.textContent = "Error inesperado, inténtelo de nuevo otra vez y si el error persiste compruebe su conexión.";
    avisoErrorAcceso.className = avisoErrorAcceso.className.replace(" ocultarAviso","");
}

