
const menuButtons = document.querySelectorAll(".menu-button");

const sidebarWrapper = document.querySelector(".sidebar-wrapper");
const main = document.querySelector("main");

if (window.innerWidth <= 1000) {
    sidebarWrapper.classList.add("closed");
}

menuButtons.forEach((menu) => {
    menu.addEventListener('click', () => {
        if (isSidebarOpen()) {
            sidebarWrapper.classList.add("closed");
            menu.querySelector("span").innerText = "menu";
            main.classList.remove("overlay");
            return;
        }
        sidebarWrapper.classList.remove("closed");
        main.classList.add("overlay");
        menu.querySelector("span").innerText = "close";
    })
})

const isSidebarOpen = () => {
    if (sidebarWrapper.classList.contains("closed") ){
        return false;
    }
    return true;
}