var burger = document.getElementById('burger');
var nav = document.getElementsByTagName('nav');
burger.click  = function() {
    nav.classList.toggle('opened');
}