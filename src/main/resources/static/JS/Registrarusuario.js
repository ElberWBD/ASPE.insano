document.getElementById("registerForm").addEventListener("submit", async function(e) {
    e.preventDefault();

    const usuario = {
        username: document.getElementById("username").value.trim(),
        email: document.getElementById("email").value.trim(),
        passwordHash: document.getElementById("password").value.trim(),
        active: true
    };

    try {
        const res = await fetch("/api/usuarios", { // usa ruta relativa
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(usuario)
        });

        const data = await res.json();
        if (res.ok) {
            alert("✅ Usuario registrado correctamente");
            console.log("Usuario guardado:", data);
            window.location.href = "/Login.html";
        } else {
            console.error("Error del servidor:", data);
            alert("⚠️ No se pudo registrar el usuario");
        }
    } catch (err) {
        console.error("Error de red:", err);
        alert("❌ Error al conectar con el servidor");
    }
});