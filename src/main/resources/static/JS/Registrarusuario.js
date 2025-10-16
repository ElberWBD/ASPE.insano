// Registrarusuario.js — Registro de usuarios del portal (modelo Usuario)
document.addEventListener("DOMContentLoaded", () => {
  const form = document.getElementById("registerForm");
  if (!form) return;

  const btn = form.querySelector('button[type="submit"]');

  form.addEventListener("submit", async (e) => {
    e.preventDefault();

    const usernameEl = document.getElementById("username");
    const emailEl    = document.getElementById("email");
    const passEl     = document.getElementById("password");

    const username = (usernameEl?.value || "").trim();
    const email    = (emailEl?.value || "").trim();
    const password = (passEl?.value || "").trim();

    if (!username || !email || !password) {
      alert("Completa usuario, email y contraseña.");
      return;
    }

    if (btn) { btn.disabled = true; btn.dataset.originalText = btn.textContent; btn.textContent = "Registrando..."; }

    try {
      const usuario = {
        username: username,
        email: email,
        passwordHash: password, // campo que mapea con tu entidad Usuario
        active: true
      };

      const res = await fetch("/api/usuarios", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(usuario)
      });

      const raw = await res.text();
      let data = null;
      try { data = raw ? JSON.parse(raw) : null; } catch {}

      if (res.ok) {
        alert("✅ Usuario registrado correctamente");
        window.location.href = "/Login.html";
        return;
      }

      alert("⚠️ No se pudo registrar el usuario. [" + res.status + "] " + (data?.message || raw || ""));
    } catch (err) {
      console.error("Error de red:", err);
      alert("❌ Error al conectar con el servidor");
    } finally {
      if (btn) { btn.disabled = false; btn.textContent = btn.dataset.originalText || "Registrarse"; }
    }
  });
});
