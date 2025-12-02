document.addEventListener('DOMContentLoaded', () => {
  const loginBtn   = document.getElementById('loginButton');
  const logoutBtn  = document.getElementById('logoutButton');
  const welcomeSpan= document.getElementById('welcomeUser');

  const clienteId   = sessionStorage.getItem('clienteId');
  const clienteNombre = sessionStorage.getItem('clienteNombre');

  if (clienteId && clienteNombre) {
    // usuario autenticado
    if (loginBtn)  loginBtn.style.display = 'none';
    if (logoutBtn) logoutBtn.style.display = 'inline-block';
    if (welcomeSpan) welcomeSpan.textContent = `Bienvenido, ${clienteNombre}`;
  } else {
    // usuario no autenticado
    if (loginBtn)  loginBtn.style.display = 'inline-block';
    if (logoutBtn) logoutBtn.style.display = 'none';
    if (welcomeSpan) welcomeSpan.textContent = '';
  }
});

function cerrarSesionCliente() {
  sessionStorage.removeItem('clienteId');
  sessionStorage.removeItem('clienteNombre');
  sessionStorage.removeItem('clienteEmail');
  location.reload();
}
