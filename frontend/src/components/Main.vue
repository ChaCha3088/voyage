<script setup>
</script>

<template>
    <div class="hello">
        <div id="carouselExampleDark" class="carousel carousel-dark slide" data-bs-ride="carousel">
            <div class="carousel-indicators">
                <button type="button" data-bs-target="#carouselExampleDark" data-bs-slide-to="0" class="active"
                    aria-current="true" aria-label="Slide 1"></button>
                <button type="button" data-bs-target="#carouselExampleDark" data-bs-slide-to="1"
                    aria-label="Slide 2"></button>
                <button type="button" data-bs-target="#carouselExampleDark" data-bs-slide-to="2"
                    aria-label="Slide 3"></button>
            </div>
            <div class="carousel-inner">
                <div class="carousel-item active" data-bs-interval="10000">
                    <img src="..." class="d-block w-100" alt="...">
                    <div class="carousel-caption d-none d-md-block">
                        <h5>First slide label</h5>
                        <p>Some representative placeholder content for the first slide.</p>
                    </div>
                </div>
                <div class="carousel-item" data-bs-interval="2000">
                    <img src="..." class="d-block w-100" alt="...">
                    <div class="carousel-caption d-none d-md-block">
                        <h5>Second slide label</h5>
                        <p>Some representative placeholder content for the second slide.</p>
                    </div>
                </div>
                <div class="carousel-item">
                    <img src="..." class="d-block w-100" alt="...">
                    <div class="carousel-caption d-none d-md-block">
                        <h5>Third slide label</h5>
                        <p>Some representative placeholder content for the third slide.</p>
                    </div>
                </div>
            </div>
            <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleDark" data-bs-slide="prev">
                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                <span class="visually-hidden">Previous</span>
            </button>
            <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleDark" data-bs-slide="next">
                <span class="carousel-control-next-icon" aria-hidden="true"></span>
                <span class="visually-hidden">Next</span>
            </button>
        </div>

        <h3>아마도 게시판 글이 들어갈 자리</h3>

        <h3>아직 정해지지 않은 자리</h3>
    </div>
</template>

<style scoped lang="scss">
$white : white;
$black : black;

$carousel-control-color: $white;
$carousel-control-width: 15%;
$carousel-control-opacity: .5;
$carousel-control-hover-opacity: .9;
$carousel-control-transition: opacity .15s ease;

$carousel-indicator-width: 30px;
$carousel-indicator-height: 3px;
$carousel-indicator-hit-area-height: 10px;
$carousel-indicator-spacer: 3px;
$carousel-indicator-opacity: .5;
$carousel-indicator-active-bg: $white;
$carousel-indicator-active-opacity: 1;
$carousel-indicator-transition: opacity .6s ease;

$carousel-caption-width: 70%;
$carousel-caption-color: $white;
$carousel-caption-padding-y: 1.25rem;
$carousel-caption-spacer: 1.25rem;

$carousel-control-icon-width: 2rem;

$carousel-control-prev-icon-bg: url("data:image/svg+xml,<svg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 16 16' fill='#{$carousel-control-color}'><path d='M11.354 1.646a.5.5 0 0 1 0 .708L5.707 8l5.647 5.646a.5.5 0 0 1-.708.708l-6-6a.5.5 0 0 1 0-.708l6-6a.5.5 0 0 1 .708 0z'/></svg>");
$carousel-control-next-icon-bg: url("data:image/svg+xml,<svg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 16 16' fill='#{$carousel-control-color}'><path d='M4.646 1.646a.5.5 0 0 1 .708 0l6 6a.5.5 0 0 1 0 .708l-6 6a.5.5 0 0 1-.708-.708L10.293 8 4.646 2.354a.5.5 0 0 1 0-.708z'/></svg>");

$carousel-transition-duration: .6s;
$carousel-transition: transform $carousel-transition-duration ease-in-out; // Define transform transition first if using multiple transitions (e.g., `transform 2s ease, opacity .5s ease-out`)

$carousel-dark-indicator-active-bg: $black;
$carousel-dark-caption-color: $black;
$carousel-dark-control-icon-filter: invert(1) grayscale(100);
</style>