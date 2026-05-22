//Primera carga de datos para mostrar la página
function cargaInicial() {

    //Personalizamos la página si es posible
    let enlaceAcceso = document.getElementById("enlaceAcceso");
    let cromosDatabaseNomb = sessionStorage.getItem("CromosDatabaseNomb");
    if (cromosDatabaseNomb){
        enlaceAcceso.textContent = "Desconéctate como: " + cromosDatabaseNomb;
        enlaceAcceso.href = "#";
    }

    // Si es administrador mostramos enlace
    let cromosDatabaseAdmi = sessionStorage.getItem("CromosDatabaseAdmi");
    if (cromosDatabaseAdmi === "ROLE_ADMIN"){
        let enlaceAdministrar = document.getElementById("enlaceAdministrar");
        enlaceAdministrar.className = enlaceAdministrar.className.replace(" ocultarContenedor","");
    }

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
        location.reload();
    }
}


