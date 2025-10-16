// JS/Iniciarsesion.js — Login del portal de clientes alineado a /api/usuarios/login
document.addEventListener("DOMContentLoaded", () => {
  const form = document.getElementById("loginForm");
  if (!form) return;

  const btn = form.querySelector('button[type="submit"]');
  const emailInput = form.querySelector('input[type="email"]');
  const passInput  = form.querySelector('input[type="password"]');

  form.addEventListener("submit", async (e) => {
    e.preventDefault();

    const email = (emailInput?.value || "").trim();
    const password = (passInput?.value || "").trim();

    if (!email || !password) {
      alert("Por favor, completa todos los campos.");
      return;
    }

    if (btn) { btn.disabled = true; btn.dataset.originalText = btn.textContent; btn.textContent = "Ingresando..."; }

    try {
      // Importante: ruta relativa al mismo host/puerto que sirve el HTML (8081)
      const res = await fetch("/api/usuarios/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        // El backend espera 'passwordHash'
        body: JSON.stringify({ email: email, passwordHash: password })
      });

      const raw = await res.text();
      let data = null;
      try { data = raw ? JSON.parse(raw) : null; } catch { /* puede venir texto plano */ }

      if (res.ok) {
        // Guardamos la sesión mínima para el resto del sitio
        if (data) {
          sessionStorage.setItem("usuarioId", data.usuarioId ?? "");
          sessionStorage.setItem("username", data.username ?? "");
          sessionStorage.setItem("email", data.email ?? email);

          // Para mantener TU icono en Index.html (usa localStorage.usuarioActual)
          try {
            localStorage.setItem("usuarioActual", JSON.stringify({
              username: data.username ?? ""
            }));
          } catch {}
        }
        alert("✅ Sesión iniciada");
        window.location.href = "/Index.html";
        return;
      }

      if (res.status === 401) {
        alert((data && (data.message || data.error)) || "Credenciales inválidas (401).");
      } else if (res.status === 404) {
        alert((data && (data.message || data.error)) || "Usuario no encontrado (404).");
      } else if (res.status === 400) {
        alert((data && (data.message || data.error)) || "Solicitud inválida (400).");
      } else {
        alert("❌ No se pudo iniciar sesión. [" + res.status + "] " + (data?.message || raw || "Error inesperado."));
      }
    } catch (err) {
      console.error("Error de red:", err);
      alert("❌ No se pudo conectar con el servidor.");
    } finally {
      if (btn) { btn.disabled = false; btn.textContent = btn.dataset.originalText || "Iniciar sesión"; }
    }
  });
});
