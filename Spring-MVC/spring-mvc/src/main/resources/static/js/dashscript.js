function toggleMenu() {
    let menu = document.querySelector(".floating-menu");
    let icon = menu.querySelector("i");

    menu.classList.toggle("open");

    if (menu.classList.contains("open")) {
        icon.classList.remove("fa-plus");
        icon.classList.add("fa-times"); // Change to "×" icon
    } else {
        icon.classList.remove("fa-times");
        icon.classList.add("fa-plus"); // Revert back to "+"
    }
}