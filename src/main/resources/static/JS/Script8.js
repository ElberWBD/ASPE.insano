const contenido = document.querySelector('.contenido');
const signUpLink = document.querySelector('.SignUpLink');
const signInLink = document.querySelector('.SignInLink');

if (signUpLink && signInLink) {
    signUpLink.addEventListener('click', () => contenido.classList.add('active'));
    signInLink.addEventListener('click', () => contenido.classList.remove('active'));
}

/* ------------------ REGISTRO ------------------ */
document.getElementById("registerForm")?.addEventListener("submit", async function(event) {
    event.preventDefault();

    const cliente = {
        nombre: document.getElementById("nombre").value.trim(),
        apellido: document.getElementById("apellido").value.trim(),
        razonSocial: document.getElementById("razon_social").value.trim(),
        email: document.getElementById("email_register").value.trim(),
        telefono: document.getElementById("telefono").value.trim(),
        password: document.getElementById("password_register").value.trim()
    };

    const response = await fetch("http://localhost:8080/clientes/guardar", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(cliente)
    });

    if (response.ok) {
        const data = await response.json();

        sessionStorage.setItem("clienteId", data.id);
        sessionStorage.setItem("clienteNombre", data.nombre);
        sessionStorage.setItem("clienteEmail", data.email);

        alert("Registro exitoso. Bienvenido " + data.nombre);
        window.location.href = "Index.html";
    } else {
        alert("Error en registro.");
    }
});

/* ------------------ LOGIN ------------------ */
document.getElementById("loginForm")?.addEventListener("submit", async function(event) {
    event.preventDefault();

    const credenciales = {
        email: document.getElementById("email_login").value.trim(),
        password: document.getElementById("password_login").value.trim()
    };

    const response = await fetch("http://localhost:8080/clientes/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(credenciales)
    });

    if (response.ok) {
        const data = await response.json();

        sessionStorage.setItem("clienteId", data.id);
        sessionStorage.setItem("clienteNombre", data.nombre);
        sessionStorage.setItem("clienteEmail", data.email);

        alert("Inicio de sesión exitoso");
        window.location.href = "Index.html";
    } else {
        alert("Correo o contraseña incorrectos.");
    }
});

/* Animación */
document.querySelectorAll('.input-box input').forEach(input => {
    input.addEventListener('focus', () => input.style.transform = 'scale(1.01)');
    input.addEventListener('blur', () => input.style.transform = 'scale(1)');
});
