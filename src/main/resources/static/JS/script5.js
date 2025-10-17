document.addEventListener("DOMContentLoaded", () => {
    const cards = document.querySelectorAll(".gallery-card");
  
    const observer = new IntersectionObserver(entries => {
      entries.forEach((entry, index) => {
        if (entry.isIntersecting) {
          setTimeout(() => {
            entry.target.classList.add("visible");
          }, index * 250); // intervalo entre tarjetas
          observer.unobserve(entry.target);
        }
      });
    }, { threshold: 0.2 });
  
    cards.forEach(card => observer.observe(card));
  });