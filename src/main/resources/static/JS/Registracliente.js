document.getElementById("formLogin").addEventListener("submit", async (e) => {
    e.preventDefault();
  
    const datosLogin = {
      email: document.getElementById("email").value,
      password: document.getElementById("password").value
    };
  
    const response = await fetch("http://localhost:8080/api/clientes/login", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(datosLogin)
    });
  
    const data = await response.json();
    if (response.ok) {
      alert("Inicio de sesión exitoso ✅");
      console.log("Cliente:", data);
      // Aquí podrías guardar info del usuario:
      // localStorage.setItem("cliente", JSON.stringify(data));
      // window.location.href = "dashboard.html";
    } else {
      alert("Error al iniciar sesión ❌: " + data);
    }
  });