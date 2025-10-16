/* JS/sesion.js — gestión ligera de sesión en cliente */

const RUTA_LOGIN = "/Login.html";   // adónde redirigir si no hay sesión

function getUsuarioSesion() {
  try {
    const usuarioId = sessionStorage.getItem("usuarioId");
    const username  = sessionStorage.getItem("username");
    const email     = sessionStorage.getItem("email");
    if (!usuarioId) return null;
    return { usuarioId, username, email };
  } catch {
    return null;
  }
}

// 1) Proteger página (redirige si no hay sesión)
function protegerPagina() {
  const usuario = getUsuarioSesion();
  if (!usuario) {
    window.location.href = RUTA_LOGIN;
  }
}

// 2) Poblar UI de usuario (saludo, email y botón de logout)
function poblarDatosUsuario() {
  const usuario = getUsuarioSesion();
  const barra   = document.querySelector(".usuario-bar");   // tu bloque opcional

  if (!usuario) {
    if (barra) barra.style.display = "none"; // oculta la barrita en páginas públicas
    document.body.classList.remove("is-auth");
    return;
  }

  document.body.classList.add("is-auth");
  if (barra) barra.style.display = ""; // mostrar si existe

  const saludoEl = document.getElementById("saludoUsuario");
  const emailEl  = document.getElementById("emailUsuario");
  if (saludoEl) saludoEl.textContent = usuario.username ? `Bienvenido, ${usuario.username}` : `Bienvenido`;
  if (emailEl)  emailEl.textContent  = usuario.email ?? "";

  const btnLogout = document.getElementById("btnLogout");
  if (btnLogout && !btnLogout.dataset.bound) {
    btnLogout.addEventListener("click", (e) => {
      e.preventDefault();
      cerrarSesion();
    });
    btnLogout.dataset.bound = "1";
  }
}

// 3) Ocultar SOLO el botón “Iniciar sesión” del header
function toggleHeaderLogin() {
  const usuario = getUsuarioSesion();

  // Prioridad 1: por id
  let btnLogin = document.getElementById("btnLoginHeader");

  // Prioridad 2: por data-atributo
  if (!btnLogin) btnLogin = document.querySelector('[data-login="true"]');

  // Prioridad 3 (respaldo): un link/botón que vaya a Login.html
  if (!btnLogin) btnLogin = document.querySelector('a[href$="Login.html"], button[onclick*="Login.html"]');

  if (btnLogin) {
    btnLogin.style.display = usuario ? "none" : "";
  }
}

// 4) Logout
function cerrarSesion() {
  try {
    sessionStorage.removeItem("usuarioId");
    sessionStorage.removeItem("username");
    sessionStorage.removeItem("email");
    localStorage.removeItem("usuarioActual"); // compatibilidad con tu Index
  } catch {}
  window.location.href = RUTA_LOGIN;
}

// Helper para arrancar todo desde el HTML
function iniciarSesionUI({ proteger = true, poblar = true, toggleLogin = true } = {}) {
  if (proteger) protegerPagina();
  if (poblar)   poblarDatosUsuario();
  if (toggleLogin) toggleHeaderLogin();
}

window.SesionApp = { getUsuarioSesion, protegerPagina, poblarDatosUsuario, toggleHeaderLogin, cerrarSesion, iniciarSesionUI };
