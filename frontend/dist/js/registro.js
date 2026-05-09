//Error en la cargar del servicio de creación
function errorCargaDatosCrear(error){

    let avisoDatosCrear = document.getElementById("avisoDatosCrear");
    if (avisoDatosCrear.className.indexOf("ocultarAviso")===-1){
        avisoDatosCrear.className += " ocultarAviso";
    }
    let avisoCorrectoCrear = document.getElementById("avisoCorrectoCrear");
    if (avisoCorrectoCrear.className.indexOf("ocultarAviso")===-1){
        avisoCorrectoCrear.className += " ocultarAviso";
    }

    let avisoErrorCrear = document.getElementById("avisoErrorCrear");
    avisoErrorCrear.textContent = "Error inesperado, inténtelo de nuevo otra vez y si el error persiste compruebe su conexión.";
    avisoErrorCrear.className = avisoErrorCrear.className.replace(" ocultarAviso","");
}


//Envío de datos para acceso con usuario
function crearUsuario() {
    
    event.preventDefault();

    let avisoDatosCrear = document.getElementById("avisoDatosCrear");
    if (avisoDatosCrear.className.indexOf("ocultarAviso")===-1){
        avisoDatosCrear.className += " ocultarAviso";
    }
    let avisoErrorCrear = document.getElementById("avisoErrorCrear");
    if (avisoErrorCrear.className.indexOf("ocultarAviso")===-1){
        avisoErrorCrear.className += " ocultarAviso";
    }
    let avisoCorrectoCrear = document.getElementById("avisoCorrectoCrear");
    if (avisoCorrectoCrear.className.indexOf("ocultarAviso")===-1){
        avisoCorrectoCrear.className += " ocultarAviso";
    }

    let formularioCrear = document.forms["formularioCrear"];

    if (formularioCrear.crearCorreo.value && formularioCrear.crearNombre.value &&
        formularioCrear.crearContrasena.value && formularioCrear.crearContrasena2.value){
            if(formularioCrear.crearContrasena.value === formularioCrear.crearContrasena2.value){
                let urlAcceso = "http://localhost:8080/auth/registro";
                let datosAcceso = {"email": formularioCrear.crearCorreo.value, "contrasena": formularioCrear.crearContrasena.value, "nombreMostrar": formularioCrear.crearNombre.value};
                
                fetch(urlAcceso, {
                method: "POST",
                headers: {"Content-Type": "application/json",},
                body: JSON.stringify(datosAcceso),
                })
                .then((res) => res.json())
                .catch((error) => errorCargaDatosCrear(error))
                .then((response) => cargaDatosCrear(response));

            }else{
                avisoDatosCrear.textContent = "Las contraseñas no coinciden.";
                avisoDatosCrear.className = avisoDatosCrear.className.replace(" ocultarAviso","");
            }

    }else{
        avisoDatosCrear.textContent = "Todos los campos son obligatorios.";
        avisoDatosCrear.className = avisoDatosCrear.className.replace(" ocultarAviso","");
    }

}


function cargaDatosCrear(respuesta){
    try {
        if(!respuesta.status){
            let avisoCorrectoCrear = document.getElementById("avisoCorrectoCrear");
            avisoCorrectoCrear.textContent = "Genial " + respuesta.nombreMostrar + ", ya has creado el usuario. Ahora tienes que volver a meter tus datos. En breve vamos a la página de acceso, también puedes ir tú.";
            avisoCorrectoCrear.className = avisoCorrectoCrear.className.replace(" ocultarAviso","");
            let botonCrear = document.getElementById("botonCrear");
            if (botonCrear.className.indexOf("ocultarAviso")===-1){
                botonCrear.className += " ocultarAviso";
            }
            setTimeout(navegarAcceso, 3000);

        }else{
            let avisoDatosCrear = document.getElementById("avisoDatosCrear");
            avisoDatosCrear.textContent = respuesta.mensaje?respuesta.mensaje:"Error inesperado, inténtelo de nuevo otra vez y si el error persiste compruebe su conexión.";
            avisoDatosCrear.className = avisoDatosCrear.className.replace(" ocultarAviso","");
        }
    } catch (error) {
        errorCargaDatosCrear(error);
    }
}


//Forzamos la navegación
function navegarAcceso(){
    window.location.href = "acceso.html";
}


