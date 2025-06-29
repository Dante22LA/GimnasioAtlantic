// Esperar a que se cargue todo el DOM
document.addEventListener("DOMContentLoaded", function () {
  const btnLogin = document.getElementById("btnLogin");
  const btnLogout = document.getElementById("btnLogout");
  const dni = localStorage.getItem("dniUsuario");
  const rango = localStorage.getItem("rangoUsuario");

  // Mostrar/ocultar botones según sesión activa
  if (btnLogin && btnLogout) {
    if (dni) {
      btnLogin.style.display = "none";
      btnLogout.style.display = "inline-block";
    } else {
      btnLogin.style.display = "inline-block";
      btnLogout.style.display = "none";
    }
  }

  // Opcional: puedes personalizar el navbar, ocultar links, etc.
  // Ejemplo: ocultar sección "Admin" si no es admin
  const adminLinks = document.querySelectorAll(".admin-only");
  if (adminLinks.length > 0) {
    if (rango !== "admin") {
      adminLinks.forEach(link => link.style.display = "none");
    }
  }
});

// Función de cierre de sesión
function cerrarSesion() {
  localStorage.removeItem("dniUsuario");
  localStorage.removeItem("rangoUsuario");
  window.location.href = "/login"; // redirige al login después de cerrar sesión
}

