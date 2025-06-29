document.addEventListener("DOMContentLoaded", function () {
  const dni = localStorage.getItem("dniUsuario");
  const rango = localStorage.getItem("rangoUsuario");

  // Si no hay sesión iniciada
  if (!dni || !rango) {
    alert("Debes iniciar sesión primero.");
    window.location.href = "/login";
    return;
  }

  // Si el rango no es admin, redirige
  if (rango !== "admin") {
    alert("Acceso restringido solo para administradores.");
    window.location.href = "/";
  }
});

