//Envío de datos para acceso con usuario
function accederServicio() {
    
    event.preventDefault();

    let alertAvisoDatosAcceso = document.getElementById("avisoDatosAcceso");
    if (alertAvisoDatosAcceso.className.indexOf("ocultarAviso")===-1){
        alertAvisoDatosAcceso.className += " ocultarAviso";
    }
    let alertAvisoErrorAcceso = document.getElementById("avisoErrorAcceso");
    if (alertAvisoErrorAcceso.className.indexOf("ocultarAviso")===-1){
        alertAvisoErrorAcceso.className += " ocultarAviso";
    }

    let formularioAcceso = document.forms["formularioAcceso"];

    if ((formularioAcceso.accesoCorreo.value != "") && (formularioAcceso.accesoContrasena.value != "")){
        let urlAcceso = "js/login.json";
        let accesoCorreo = document.forms["formularioAcceso"].accesoCorreo.value;
        let accesoContrasena = document.forms["formularioAcceso"].accesoContrasena.value;
        let datosAcceso = { "email": accesoCorreo, "contrasena": accesoContrasena};
        
        fetch(urlAcceso, {
        method: "GET",
        headers: {"Content-Type": "application/json",},
    //    body: JSON.stringify(datosAcceso),
        })
        .then((res) => res.json())
        .catch((error) => errorCargaDatos(error))
        .then((response) => cargaDatos(response));

    }else{
        let alertAvisoDatosAcceso = document.getElementById("avisoDatosAcceso");
        alertAvisoDatosAcceso.className = alertAvisoDatosAcceso.className.replace(" ocultarAviso","");
    }

}

//Cargar contenido del servicio
function cargaDatos(respuesta){
    try {
        console.log(respuesta.email);
    } catch (error) {
        errorCargaDatos(error);
    }
}

//Error en la cargar del servicio
function errorCargaDatos(error){
    let alertAvisoErrorAcceso = document.getElementById("avisoErrorAcceso");
    alertAvisoErrorAcceso.className = alertAvisoErrorAcceso.className.replace(" ocultarAviso","");
}

