// session.js
document.addEventListener("DOMContentLoaded", function () {
  const btnLogin = document.getElementById("btnLogin");
  const btnLogout = document.getElementById("btnLogout");
  const dni = localStorage.getItem("dniUsuario");

  if (btnLogin && btnLogout) {
    if (dni) {
      btnLogin.style.display = "none";
      btnLogout.style.display = "inline-block";
    } else {
      btnLogin.style.display = "inline-block";
      btnLogout.style.display = "none";
    }
  }
});

// Función de cierre de sesión
function cerrarSesion() {
  localStorage.removeItem("dniUsuario");
  window.location.href = "/login"; // o /login si tienes la vista mapeada
}
