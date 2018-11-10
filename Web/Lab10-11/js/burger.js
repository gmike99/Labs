
var burger = document.getElementById('burger');
var navigation = document.getElementsByTagName('nav')[0];

burger.onclick = function () {
    if (navigation.classList.contains('hidden')) {
        navigation.classList.remove('hidden');
    } else {
        navigation.classList.add('hidden');
    }
};