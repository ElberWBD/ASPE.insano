// Seleccionamos todas las filas de características
const features = document.querySelectorAll('.feature-row');

// Definimos un observer que se activará cuando las filas entren en la vista
const observer = new IntersectionObserver((entries, observer) => {
    entries.forEach(entry => {
        if (entry.isIntersecting) {
            // Cuando la tarjeta entra en la vista, agregamos la clase de animación
            entry.target.classList.add('animate');
        }
    });
}, {
    threshold: 0.1 // La tarjeta debe estar al 10% visible para activar la animación
});

// Iniciamos la observación de todas las filas
features.forEach(feature => {
    observer.observe(feature);
});

// Ahora seleccionamos todas las palabras dentro del h1
const words = document.querySelectorAll('.word');

// Definimos un observer que se activará cuando las palabras entren en la vista
const wordObserver = new IntersectionObserver((entries, observer) => {
    entries.forEach(entry => {
        if (entry.isIntersecting) {
            // Cuando la palabra entra en la vista, agregamos la clase de animación
            entry.target.classList.add('animate');
        }
    });
}, {
    threshold: 0.1 // La palabra debe estar al 10% visible para activar la animación
});

// Iniciamos la observación de todas las palabras
words.forEach((word, index) => {
    word.style.animationDelay = `${index * 0.3}s`; // Agregamos un retraso incremental para cada palabra
    wordObserver.observe(word);
});

let image = document.querySelector(".image");
let text = document.querySelector(".text");

image.onclick = () => {
  image.style.transition = "0.4s";
  text.style.transition = "0.4s";

  if (image.style.top != "-60px") {
    image.style.top = "-60px";
    image.style.width = "100px";
    image.style.height = "100px";
    image.style.padding = "5px";
    text.style.opacity = "1";
    text.style.transitionDelay = "0.4s";
  } else {
    image.style.top = "0px";
    image.style.width = "225px";
    image.style.height = "225px";
    image.style.padding = "0px";
    image.style.transitionDelay = "0.2s";
    text.style.opacity = "0";
  }
};

let currentIndex = 0;  // El índice de la imagen actual

// Función para mover el carrousel hacia la izquierda o derecha
function moveSlide(step) {
    const slides = document.querySelectorAll('.imagen-con-recuadro');
    const totalSlides = slides.length;

    // Calcular el índice de la próxima imagen
    currentIndex = (currentIndex + step + totalSlides) % totalSlides;

    // Mover el carrousel hacia la imagen correspondiente
    const slideWidth = slides[0].clientWidth;  // Ancho de una imagen
    const newTransformValue = -currentIndex * slideWidth;  // Desplazamiento calculado

    document.querySelector('.carrousel-slide').style.transform = `translateX(${newTransformValue}px)`;
}

