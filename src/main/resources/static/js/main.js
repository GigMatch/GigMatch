"use strict";

$(window).scroll(function () {
    $('nav').toggleClass('scrolled', $(this).scrollTop() > 500);
});


