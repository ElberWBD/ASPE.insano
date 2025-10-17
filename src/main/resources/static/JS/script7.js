
  function cambiarImg(src) {
    document.getElementById("imgPrincipal").src = src;
  }

  function seleccionarPlazo(btn) {
    document.querySelectorAll(".plazos button").forEach(b => b.classList.remove("activo"));
    btn.classList.add("activo");
  }

  function abrirModal() {
    document.getElementById("modal").style.display = "flex";
  }

  function cerrarModal() {
    document.getElementById("modal").style.display = "none";
  }

  function cambiarDescripcion() {
    const servicio = document.getElementById("servicio").value;
    const descripcion = document.getElementById("descripcionServicio");

    const textos = {
      ecommerce: "Desarrollo de tiendas online modernas con integración de pago, panel administrativo y diseño adaptable a móviles.",
      marketing: "Consultorías estratégicas para mejorar la presencia digital y optimizar campañas publicitarias.",
      eventos: "Organización de eventos y campañas BTL creativas para aumentar la conexión de tu marca con el público.",
      produccion: "Creación de videos promocionales, spots publicitarios y contenido audiovisual de alta calidad."
    };
    descripcion.textContent = textos[servicio];
  }

