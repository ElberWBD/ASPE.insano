const contenido = document.querySelector('.contenido');
const signUpLink = document.querySelector('.SignUpLink');
const signInLink = document.querySelector('.SignInLink');


if (signUpLink && signInLink) {
    signUpLink.addEventListener('click', () => {
        contenido.classList.add('active');
    });

    signInLink.addEventListener('click', () => {
        contenido.classList.remove('active');
    });
}


document.getElementById("registerForm")?.addEventListener("submit", async function(event) {
    event.preventDefault();

    const submitButton = this.querySelector('button[type="submit"]');
    const originalText = submitButton.textContent;


    const cliente = {
        nombre: document.getElementById("nombre")?.value.trim() || '',
        apellido: document.getElementById("apellido")?.value.trim() || '',
        razonSocial: document.getElementById("razon_social")?.value.trim() || '',
        email: document.getElementById("email_register")?.value.trim() || '',
        telefono: document.getElementById("telefono")?.value.trim() || '',
        password: document.getElementById("password_register")?.value.trim() || ''
    };
    

    if (!cliente.nombre || !cliente.apellido || !cliente.email || !cliente.password) {
        alert("❌ Por favor, complete al menos los campos obligatorios (Nombre, Apellido, Correo y Contraseña).");
        return;
    }
    
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(cliente.email)) {
        alert("❌ Por favor, ingrese un correo electrónico válido.");
        return;
    }

    submitButton.innerHTML = '⏳ Registrando...';
    submitButton.disabled = true;

    try {
        const response = await fetch("http://localhost:8080/clientes/guardar", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Accept": "application/json"
            },
            body: JSON.stringify(cliente)
        });

        if (response.ok) {
            const data = await response.json();
            
            sessionStorage.setItem("clienteNombre", data.nombre);
            sessionStorage.setItem("clienteEmail", data.email);
            sessionStorage.setItem("clienteId", data.id || "");

            alert("✅ Registro exitoso. Bienvenido, " + data.nombre + "!");

            setTimeout(() => {
                window.location.href = "Index.html"; 
            }, 1000);

        } else {
            const errorText = await response.text();
            let errorMessage = "❌ Error al registrar: ";
            
            if (errorText.includes("Email already exists") || errorText.includes("email duplicado")) {
                errorMessage += "El correo electrónico ya está registrado.";
            } else {
                errorMessage += `[${response.status}] ${errorText.substring(0, 80)}`;
            }
            alert(errorMessage);
        }

    } catch (error) {
        console.error("Error en la conexión/registro:", error);
        alert("⚠️ No se pudo conectar con el servidor (clientes/guardar).");
    } finally {
        if (submitButton.disabled) {
            submitButton.innerHTML = originalText;
            submitButton.disabled = false;
        }
    }
});

document.getElementById("loginForm")?.addEventListener("submit", async function(event) {
    event.preventDefault();

    const submitButton = this.querySelector('button[type="submit"]');
    const originalText = submitButton.textContent;

    const credenciales = {
        email: document.getElementById("email_login")?.value.trim() || '',
        password: document.getElementById("password_login")?.value.trim() || ''
    };


    if (!credenciales.email || !credenciales.password) {
        alert("❌ Por favor, ingrese su correo y contraseña.");
        return;
    }


    submitButton.innerHTML = '⏳ Iniciando...';
    submitButton.disabled = true;

    try {
        const response = await fetch("http://localhost:8080/clientes/login", { 
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(credenciales)
        });

        if (response.ok) {
            const data = await response.json();

            sessionStorage.setItem("clienteNombre", data.nombre);
            sessionStorage.setItem("clienteEmail", data.email);
            sessionStorage.setItem("clienteId", data.id || "");
            
            alert("✅ Inicio de sesión exitoso. ¡Bienvenido de vuelta, " + data.nombre + "!");

            setTimeout(() => {
                window.location.href = "Index.html";
            }, 1000);

        } else if (response.status === 401 || response.status === 403) {
            alert("❌ Credenciales inválidas. Correo o contraseña incorrectos.");
        } else {
            const errorText = await response.text();
            alert(`❌ Error al iniciar sesión: [${response.status}] ${errorText.substring(0, 80)}`);
        }

    } catch (error) {
        console.error("Error en la conexión/login:", error);
        alert("⚠️ No se pudo conectar con el servidor (clientes/login).");
    } finally {
        if (submitButton.disabled) {
            submitButton.innerHTML = originalText;
            submitButton.disabled = false;
        }
    }
});



document.querySelectorAll('.input-box input').forEach(input => {
    input.addEventListener('focus', function() {
        this.style.transform = 'scale(1.01)';
    });
    
    input.addEventListener('blur', function() {
        this.style.transform = 'scale(1)';
    });
});