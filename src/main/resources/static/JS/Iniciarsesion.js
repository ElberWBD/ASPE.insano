document.getElementById("loginForm").addEventListener("submit", async function (e) {
    e.preventDefault(); // Evita el envío tradicional del formulario

    const email = document.querySelector('input[type="email"]').value.trim();
    const password = document.querySelector('input[type="password"]').value.trim();

    if (!email || !password) {
        alert("Por favor, completa todos los campos.");
        return;
    }

    try {
        const response = await fetch("http://localhost:8080/api/clientes/login", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                email: email,
                password: password // si tienes campo contraseña en Cliente
            })
        });

        if (response.ok) {
            const cliente = await response.json();

            // Guardar cliente en localStorage (sin contraseña)
            localStorage.setItem("clienteActual", JSON.stringify(cliente));

            alert("Inicio de sesión exitoso. Bienvenido, " + cliente.razonSocial + "!");

            // Redirigir al index principal
            window.location.href = "/Index.html";

        } else if (response.status === 401) {
            alert("Contraseña incorrecta.");
        } else if (response.status === 404) {
            alert("Cliente no encontrado.");
        } else {
            alert("Error al iniciar sesión. Código: " + response.status);
        }

    } catch (error) {
        console.error("Error:", error);
        alert("No se pudo conectar con el servidor.");
    }
});
