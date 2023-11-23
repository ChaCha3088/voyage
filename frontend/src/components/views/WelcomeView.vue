<script setup>
import { useMemberStore } from "@/stores/member";
import { storeToRefs } from "pinia";

const memberStore = useMemberStore();

const { refreshToken } = storeToRefs(memberStore);



//     if (refreshToken.value != null) 

//   // 로그인 상태서 새로고침시 이름을 불러오기 위함
</script>

<template>
    <div id="myCarousel" class="carousel slide" data-bs-ride="carousel"> <!-- 3개의 탭이 있는 캐러셀 -->
        <div class="carousel-indicators">
            <button type="button" data-bs-target="#myCarousel" data-bs-slide-to="0" class="active" aria-current="true"
                aria-label="Slide 1"></button>
            <button type="button" data-bs-target="#myCarousel" data-bs-slide-to="1" aria-label="Slide 2"></button>
            <button type="button" data-bs-target="#myCarousel" data-bs-slide-to="2" aria-label="Slide 3"></button>
        </div>
        <div class="carousel-inner">
            <div class="carousel-item active"> <!-- 첫번째 탭 -->
                <img src="../images/다운로드.jpg" class="d-block w-100" alt="이미지3">
                <div class="container">
                    <div class="carousel-caption text-start">
                        <h1>처음이신가요?</h1>
                        <p>회원가입을 통해 회원이 되고 정보를 얻어보세요</p>
                        <p><a class="btn btn-lg btn-primary" href="#">
                                <div v-if="refreshToken == null">
                                    <router-link :to="{ name: 'signup' }" class="nav-link">회원가입하기</router-link>
                                </div>
                                <div v-else><router-link :to="{ name: 'profile' }" class="nav-link">회원가입하기</router-link>
                                </div>
                            </a></p>
                    </div>
                </div>
            </div>
            <div class="carousel-item"> <!-- 두번째 탭 -->
                <img src="../images/다운로드2.jpg" class="d-block w-100" alt="이미지3">

                <div class="container">
                    <div class="carousel-caption">
                        <h1>이미 회원이신가요?</h1>
                        <p>로그인하고 정보를 관리해보세요</p>
                        <p><a class="btn btn-lg btn-primary" href="#">
                                <div v-if="refreshToken == null">
                                    <router-link :to="{ name: 'signin' }" class="nav-link">로그인하기</router-link>
                                </div>
                                <div v-else><router-link :to="{ name: 'profile' }" class="nav-link">로그인하기</router-link>
                                </div>
                            </a></p>
                    </div>
                </div>
            </div>
            <div class="carousel-item"> <!-- 세번째 탭 -->
                <img src="../images/다운로드3.jpg" class="d-block w-100" alt="이미지3">

                <div class="container">
                    <div class="carousel-caption text-end">
                        <h1>여행에 관해 더 자세하게</h1>
                        <p>관광지를 검색해보세요</p>
                        <p><a class="btn btn-lg btn-primary" href="#"><router-link :to="{ name: 'attractionInfo' }"
                                    class="nav-link">검색하러가기</router-link></a></p>
                    </div>
                </div>
            </div>
        </div>
        <button class="carousel-control-prev" type="button" data-bs-target="#myCarousel" data-bs-slide="prev">
            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
            <span class="visually-hidden">Previous</span>
        </button>
        <button class="carousel-control-next" type="button" data-bs-target="#myCarousel" data-bs-slide="next">
            <span class="carousel-control-next-icon" aria-hidden="true"></span>
            <span class="visually-hidden">Next</span>
        </button>
    </div>


    <!-- Marketing messaging and featurettes
  ================================================== -->
    <!-- Wrap the rest of the page in another container to center all the content. -->

    <div class="container marketing"> <!--캐러셀 아래 정보탭-->
        <div class="row featurette">
            <div class="col-md-7 order-md-2">
                <h2 class="featurette-heading" style="margin-bottom: 3vh;"><span class="text-muted">미리보기</span>
                </h2>
            </div>
            <div class="col-md-5 order-md-1">
                <div width="500" height="500" background-color="#eee" color="#aaa"
                    class="bd-placeholder-img-lg featurette-image img-fluid mx-auto"></div>
            </div>
        </div>
        <!-- Three columns of text below the carousel -->
        <div class="row">
            <div class="col-lg-4">
                <div width="140" height="140" background="#777" color="#777" class="rounded-circle"></div>

                <div class="card mb-3" style="width: 20vw;"> <!-- 메인 페이지용 정보 하드 코딩1 start -->
                    <img src="http://tong.visitkorea.or.kr/cms/resource/45/1903045_image2_1.jpg" class="card-img-top"
                        alt="이미지 없음" width="9rem">
                    <div class="card-body">
                        <h5 class="card-title">서울신라호텔</h5>
                        <p class="card-text">서울특별시 중구 동호로 249</p>
                    </div>
                    <p><a class="btn btn-secondary" style="margin-bottom: 1vw;" data-bs-toggle="modal"
                            data-bs-target="#staticBackdrop1">자세히 보기</a></p> <!-- 누르면 해당 target 모달창 나옴 -->

                    <div class="modal fade" id="staticBackdrop1" data-bs-backdrop="static" data-bs-keyboard="false"
                        tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
                        <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h1 class="modal-title fs-5" id="staticBackdropLabel">서울신라호텔</h1>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal"
                                        aria-label="Close"></button>
                                </div>
                                <div class="modal-body">

                                    <div class="card mb-3" style="width: 24vw;">
                                        <img src="http://tong.visitkorea.or.kr/cms/resource/45/1903045_image2_1.jpg"
                                            class="card-img-top" alt="이미지 없음" width="9rem">
                                        <div class="card-body">
                                            <h5 class="card-title">서울신라호텔</h5>
                                            <p class="card-text">서울특별시 중구 동호로 249 (장충동2가) 우) 4605</p>
                                            <p class="card-text">02-2233-3131 </p>
                                        </div>
                                    </div>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div> <!-- 메인 페이지용 정보 하드 코딩1 end -->

            </div><!-- /.col-lg-4 -->
            <div class="col-lg-4">
                <div width="140" height="140" background="#777" color="#777" class="rounded-circle"></div>

                <div class="card mb-3" style="width: 20vw;"> <!-- 메인 페이지용 정보 하드 코딩2 start -->
                    <img src="http://tong.visitkorea.or.kr/cms/resource/71/2539471_image2_1.JPG" class="card-img-top"
                        alt="이미지 없음" width="9rem">
                    <div class="card-body">
                        <h5 class="card-title">에버랜드</h5>
                        <p class="card-text">경기도 용인시 처인구 포곡읍 에버랜드로 199</p>
                    </div>
                    <p><a class="btn btn-secondary" style="margin-bottom: 1vw;" data-bs-toggle="modal"
                            data-bs-target="#staticBackdrop2">자세히 보기</a></p> <!-- 누르면 해당 target 모달창 나옴 -->

                    <div class="modal fade" id="staticBackdrop2" data-bs-backdrop="static" data-bs-keyboard="false"
                        tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
                        <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h1 class="modal-title fs-5" id="staticBackdropLabel">에버랜드</h1>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal"
                                        aria-label="Close"></button>
                                </div>
                                <div class="modal-body">

                                    <div class="card mb-3" style="width: 24vw;">
                                        <img src="http://tong.visitkorea.or.kr/cms/resource/71/2539471_image2_1.JPG"
                                            class="card-img-top" alt="이미지 없음" width="9rem">
                                        <div class="card-body">
                                            <h5 class="card-title">에버랜드</h5>
                                            <p class="card-text">경기도 용인시 처인구 포곡읍 에버랜드로 199 (포곡읍) 우) 63920</p>
                                            <p class="card-text">031-320-5000</p>
                                        </div>
                                    </div>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div> <!-- 메인 페이지용 정보 하드 코딩2 end -->
            </div><!-- /.col-lg-4 -->
            <div class="col-lg-4">
                <div width="140" height="140" background="#777" color="#777" class="rounded-circle"></div>

                <div class="card mb-3" style="width: 20vw;"> <!-- 메인 페이지용 정보 하드 코딩3 start -->
                    <img src="http://tong.visitkorea.or.kr/cms/resource/37/2712637_image2_1.jpg" class="card-img-top"
                        alt="이미지 없음" width="9rem">
                    <div class="card-body">
                        <h5 class="card-title">경주 불국사</h5>
                        <p class="card-text">경상북도 경주시 불국로 385</p>
                    </div>
                    <p><a class="btn btn-secondary" style="margin-bottom: 1vw;" data-bs-toggle="modal"
                            data-bs-target="#staticBackdrop3">자세히 보기</a></p> <!-- 누르면 해당 target 모달창 나옴 -->

                    <div class="modal fade" id="staticBackdrop3" data-bs-backdrop="static" data-bs-keyboard="false"
                        tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
                        <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h1 class="modal-title fs-5" id="staticBackdropLabel">경주 불국사</h1>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal"
                                        aria-label="Close"></button>
                                </div>
                                <div class="modal-body">

                                    <div class="card mb-3" style="width: 24vw;">
                                        <img src="http://tong.visitkorea.or.kr/cms/resource/37/2712637_image2_1.jpg"
                                            class="card-img-top" alt="이미지 없음" width="9rem">
                                        <div class="card-body">
                                            <h5 class="card-title">경주 불국사</h5>
                                            <p class="card-text">경상북도 경주시 불국로 385 (진현동) 우) 38127</p>
                                            <p class="card-text">054-746-9913</p>
                                        </div>
                                    </div>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div> <!-- 메인 페이지용 정보 하드 코딩3 end -->
            </div><!-- /.col-lg-4 -->
        </div><!-- /.row -->


        <!-- START THE FEATURETTES -->

        <hr class="featurette-divider">

        <div class="row featurette">
            <div class="col-md-7">
                <h2 class="featurette-heading"><span class="text-muted">여행을 계획중이신가요? </span></h2>
                <p class="lead">관광지에 관해 먼저 검색해보세요.</p>
            </div>
            <div class="col-md-5">
                <div width="500" height="500" background="#eee" color="#aaa"
                    class="bd-placeholder-img-lg featurette-image img-fluid mx-auto"></div>
            </div>
        </div>
        <!-- 
        <hr class="featurette-divider">

        <div class="row featurette">
            <div class="col-md-7 order-md-2">
                <h2 class="featurette-heading">Oh yeah, it’s that good. <span class="text-muted">See for
                        yourself.</span>
                </h2>
                <p class="lead">Another featurette? Of course. More placeholder content here to give you an idea of how
                    this
                    layout would work with some actual real-world content in place.</p>
            </div>
            <div class="col-md-5 order-md-1">
                <div width="500" height="500" background-color="#eee" color="#aaa"
                    class="bd-placeholder-img-lg featurette-image img-fluid mx-auto"></div>
            </div>
        </div>

        <hr class="featurette-divider">

        <div class="row featurette">
            <div class="col-md-7">
                <h2 class="featurette-heading">And lastly, this one. <span class="text-muted">Checkmate.</span></h2>
                <p class="lead">And yes, this is the last block of representative placeholder content. Again, not really
                    intended to be actually read, simply here to give you a better view of what this would look like
                    with
                    some actual content. Your content.</p>
            </div>
            <div class="col-md-5">
                <div width="500" height="500" background-color="#eee" color="#aaa"
                    class="bd-placeholder-img-lg featurette-image img-fluid mx-auto"></div>
            </div>
        </div> -->

        <hr class="featurette-divider">

    </div><!-- /.container -->
</template>

<style scoped lang="scss">
@import '@/assets/base.css';

#welcome {
    max-width: 1280px;
    margin: 0 auto;
    padding: 2rem;

    font-weight: normal;
}

a,
.green {
    text-decoration: none;
    color: white;
    transition: 0.4s;
}

// ----

/* GLOBAL STYLES
-------------------------------------------------- */
/* Padding below the footer and lighter body text */

body {
    padding-top: 3rem;
    padding-bottom: 3rem;
    color: #5a5a5a;
}


/* CUSTOMIZE THE CAROUSEL
-------------------------------------------------- */

/* Carousel base class */
.carousel {
    margin-bottom: 4rem;
}


/* Since positioning the image, we need to help out the caption */
.carousel-caption {
    bottom: 3rem;
    z-index: 10;
}

/* Declare heights because of positioning of img element */
.carousel-item {
    height: 32rem;
}

.carousel-item>img {
    position: absolute;
    top: 0;
    left: 0;
    min-width: 100%;
    height: 32rem;
}


/* MARKETING CONTENT
-------------------------------------------------- */

/* Center align the text within the three columns below the carousel */
.marketing .col-lg-4 {
    margin-bottom: 1.5rem;
    text-align: center;
}

.marketing h2 {
    font-weight: 400;
}

/* rtl:begin:ignore */
.marketing .col-lg-4 p {
    margin-right: .75rem;
    margin-left: .75rem;
}

/* rtl:end:ignore */


/* Featurettes
------------------------- */

.featurette-divider {
    margin: 5rem 0;
    /* Space out the Bootstrap <hr> more */
}

/* Thin out the marketing headings */
.featurette-heading {
    font-weight: 300;
    line-height: 1;
    /* rtl:remove */
    letter-spacing: -.05rem;
}


/* RESPONSIVE CSS
-------------------------------------------------- */

@media (min-width: 40em) {

    /* Bump up size of carousel content */
    .carousel-caption p {
        margin-bottom: 1.25rem;
        font-size: 1.25rem;
        line-height: 1.4;
    }

    .featurette-heading {
        font-size: 50px;
    }
}

@media (min-width: 62em) {
    .featurette-heading {
        margin-top: 7rem;
    }
}
</style>