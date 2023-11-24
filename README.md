# Voyage
## About This Project
> 여행 정보를 조회하고 검색할 수 있는 서비스입니다.
## Table of Contents
- [Voyage](#voyage)
  - [About This Project](#about-this-project)
  - [Table of Contents](#table-of-contents)
    - [개발 기간](#개발-기간)
  - [Developers](#developers)
    - [Backend](#backend)
    - [Frontend](#frontend)
  - [Tech Stack](#tech-stack)
    - [Backend](#backend-1)
    - [Frontend](#frontend-1)
    - [Tools](#tools)
  - [기능 구현 목록](#기능-구현-목록)
    - [협업](#협업)
    - [Backend](#backend-2)
      - [Authentication](#authentication)
      - [회원](#회원)
      - [여행 정보](#여행-정보)
    - [ERD](#erd)
    - [API Document](#api-document)
  - [ERD](#erd-1)
  - [API Document](#api-document-1)
- [중점 사항](#중점 사항)
  - [JWT 재발급 로직 개선](#JWT-재발급-로직-개선)
  - [Validation](#validation)
    - [Authentication](#authentication)
    - [Controller단](#controller단)
  - [Transaction](#transaction)
    - [Amazon S3에 이미지 업로드 후, DB에 저장하는 작업](#amazon-s3에-이미지-업로드-후-db에-저장하는-작업)
  - [Pagination VS No Offset](#pagination-vs-no-offset)
  - [Implementing No Offset With QueryDsl](#implementing-no-offset-with-querydsl)
- [Demo](#demo)
  - [1. Main Page](#1-main-page)
  - [2. Sign In](#2-sign-in)
  - [3. 관광지 조회 with No Offset](#3-관광지-조회-with-no-offset)
  - [4. 관광지 검색 with QueryDsl & BooleanExpression](#4-관광지-검색-with-querydsl--booleanexpression)
  - [5. Kakao Map API](#5-kakao-map-api)
  - [6. Change Profile Image](#6-change-profile-image)
  - [7. Change Password](#7-change-password)
  - [8. Delete Member](#8-delete-member)
  - [9. Summary Video](#9-summary-video)

### 개발 기간
> 2023 11/01 - 2023/11/24

## Developers
### Backend
**[Cha Cha](https://github.com/ChaCha3088)**
### Frontend
이혜지

## Tech Stack
![Tech Stack](https://github.com/ChaCha3088/voyage/assets/90785316/29b79f2f-00f6-4f78-a7df-9d16e479a849)
### Backend
- Java 11
- Spring Boot 2.7.17
- JPA
- QueryDSL 5.0.0
- MySQL 8.0.34
### Frontend
- Vue3
- HTML
- JavaScript
- CSS

### Tools
- Git
- Gradle 8.4
- IntelliJ IDEA
- ERD Cloud

## 기능 구현 목록
### 협업
- [x] Git Flow
- [x] Git Commit Convention
- [x] Git Branch Convention
- [x] API 문서 작성

### Backend
- [x] 기획 및 요구사항 정의
- [x] ERD 설계
- [x] Spring Boot 프로젝트 생성
- [x] Spring Security 적용
- [x] Spring Data JPA 적용
- [x] QueryDsl 적용
- [x] MySQL 연동
- [x] AWS S3 연동

#### Authentication
- [x] 회원 가입
- [x] 로그인
- [x] 로그아웃
- [x] JWT 발급
- [x] JWT 재발급

#### 회원
- [x] 회원 비밀번호 수정
- [x] 회원 프로필 이미지
    - [x] 업로드 및 수정
- [x] 회원 탈퇴

#### 여행 정보
- [x] 조회
    - [x] No Offset 적용
- [x] 검색
    - [x] QueryDsl 적용
    - [x] BooleanExpression 적용

## ERD
![ERD](https://github.com/ChaCha3088/voyage/assets/90785316/eeb88b6a-184b-40f0-929c-d7a270383c55)

## API Document
[Notion](https://cha3088.notion.site/API-54ad5cc3557b4485959a965e3f1b4b82?pvs=4)

# 중점 사항
## JWT 재발급 로직 개선
> DB에 Refresh Token을 저장하고,
> Refresh Token을 재발급하면 새로운 Refresh Token을 발급하는 방식으로 개선

![Refresh Token](https://github.com/ChaCha3088/voyage/assets/90785316/32bc6b94-e32a-491d-9685-da845d8ad6ab)

[Back to Table of Contents](#table-of-contents)
## Validation
> 요청이 들어오는 Controller단에서부터 검증하도록 개선
> 비즈니스 요구사항을 만족하도록 검증
### Authentication
#### 회원 가입 시, 이메일 규칙, 비밀번호 규칙 검증
![Authentication](https://github.com/ChaCha3088/voyage/assets/90785316/c5fe1adb-bc57-4df2-9de4-09ab7674e404)

### Controller단
> `@Valid` 적용
> `@RequestMapping` consume Content-Type 적용

![Controller](https://github.com/ChaCha3088/voyage/assets/90785316/202a19c3-7e79-4e2c-8f00-c04b7af10468)

[Back to Table of Contents](#table-of-contents)

## Transaction
> 논리적으로 하나의 작업으로 처리되어야 하는 작업들을 하나의 트랜잭션으로 묶어서 처리
### Amazon S3에 이미지 업로드 후, DB에 저장하는 작업
![Transaction](https://github.com/ChaCha3088/voyage/assets/90785316/b236332d-3c42-4a5e-892f-dd39083993a1)
[Back to Table of Contents](#table-of-contents)

## Pagination VS No Offset
> No Offset을 사용하여 페이지에 관계없이 일정한 속도로 조회
> 34,895건 기준 최대 117.42배 개선

![Pagination VS No Offset](https://github.com/ChaCha3088/voyage/assets/90785316/4a98fde7-90d2-4818-a6ca-5a153d244087)
[Back to Table of Contents](#table-of-contents)

## Implementing No Offset With QueryDsl
> QueryDsl을 사용하여 No Offset을 구현
> BooleanExpression을 활용하여 동적 쿼리 구현

![No Offset With QueryDsl](https://github.com/ChaCha3088/voyage/assets/90785316/3c2ab018-e427-47ca-8299-a19ff2bd7717)

## Demo
### 1. Main Page
![Main Page](https://file.notion.so/f/f/93d88247-7ea3-4e21-aa86-4d58e8afca25/31986a2d-1348-4d7a-b77c-c180d72e46e5/1._Main.gif?id=38b9ce78-f5b5-4901-8e98-18a5b779b1cb&table=block&spaceId=93d88247-7ea3-4e21-aa86-4d58e8afca25&expirationTimestamp=1700841600000&signature=1eitmR50XI2XuRHWUCS4XbgCPmwFrHw1q61a7EXErQc&downloadName=1.+Main.gif)
### 2. Sign In
![Sign In](https://file.notion.so/f/f/93d88247-7ea3-4e21-aa86-4d58e8afca25/433d778e-a8e4-4d8d-bd85-260de29be876/3._Sign_In.gif?id=7cb7f8a8-f07e-4ae2-a4e3-025a5770c8b9&table=block&spaceId=93d88247-7ea3-4e21-aa86-4d58e8afca25&expirationTimestamp=1700841600000&signature=CkhE3jltyPTBJcoq3W5kYG1c6dTbspxfP6A0IiDmoJ4&downloadName=3.+Sign+In.gif)
### 3. 관광지 조회 with No Offset
![관광지 조회 with No Offset](https://file.notion.so/f/f/93d88247-7ea3-4e21-aa86-4d58e8afca25/1e48d010-3ad2-48c7-94f1-87ea9bfd9d3e/4._No_Offset.gif?id=989b87bc-bdf9-4015-959b-0b6124b41770&table=block&spaceId=93d88247-7ea3-4e21-aa86-4d58e8afca25&expirationTimestamp=1700841600000&signature=Iy8ZjklU8mQYnu0z_I1ayrZIkqTOp3eHAirkzsGqasM&downloadName=4.+No+Offset.gif)
### 4. 관광지 검색 with QueryDsl & BooleanExpression
![관광지 검색 with QueryDsl & BooleanExpression](https://file.notion.so/f/f/93d88247-7ea3-4e21-aa86-4d58e8afca25/5c41b6a6-1156-4fde-ac85-93347b2fde78/5._Search.gif?id=6ce10b8c-9ce2-4f7d-9bcd-730ced2ff22f&table=block&spaceId=93d88247-7ea3-4e21-aa86-4d58e8afca25&expirationTimestamp=1700841600000&signature=XHuq_xLva48ZMHOnUQF1_PXLYKuJumMOi-i4jxck4CE&downloadName=5.+Search.gif)
### 5. Kakao Map API
![Kakao Map API](https://file.notion.so/f/f/93d88247-7ea3-4e21-aa86-4d58e8afca25/95e8340e-1a35-4d3c-9a25-49bb4800e87f/6.-KakaoMap-API.gif?id=103e2ce4-f919-4728-b696-0d0f2d0eb6fb&table=block&spaceId=93d88247-7ea3-4e21-aa86-4d58e8afca25&expirationTimestamp=1700841600000&signature=hxDmXqBPQRQ6EEi1ZOtVhQle6tHDJiT9n7LiO-O-k_s&downloadName=6.-KakaoMap-API.gif)
### 6. Change Profile Image
![Change Profile Image](https://file.notion.so/f/f/93d88247-7ea3-4e21-aa86-4d58e8afca25/b39ca341-9e03-4cb9-a1fe-ddc806f57b2f/7.-Change-Profile.gif?id=a87def05-08af-4fed-9492-fcbb1cefb247&table=block&spaceId=93d88247-7ea3-4e21-aa86-4d58e8afca25&expirationTimestamp=1700841600000&signature=1417XRqZylnfgQ2K8fdLwu-IcCko-OKo6Sfb-gXeOxM&downloadName=7.-Change-Profile.gif)
### 7. Change Password
![Change Password](https://file.notion.so/f/f/93d88247-7ea3-4e21-aa86-4d58e8afca25/5362e00f-2a5a-4c83-89e9-571a80f7ee5b/8.-Change-Password.gif?id=de588342-63c2-42fc-9bae-497be50a7d37&table=block&spaceId=93d88247-7ea3-4e21-aa86-4d58e8afca25&expirationTimestamp=1700841600000&signature=KNWK2XNxNtRQu_iZ5WWX-sRWoQJvKmuXvqymVCF5iOE&downloadName=8.-Change-Password.gif)
### 8. Delete Member
![Delete Member](https://file.notion.so/f/f/93d88247-7ea3-4e21-aa86-4d58e8afca25/262dc7d5-d5be-4f8e-9539-ba36a85df556/9.-Delete-Member.gif?id=ce3eca6e-cd48-4ab9-ad64-c21f6a7aae05&table=block&spaceId=93d88247-7ea3-4e21-aa86-4d58e8afca25&expirationTimestamp=1700841600000&signature=kwlDl6S77PmYVsguqutgHpcEn--hHzXpjX1IDMqEJbM&downloadName=9.-Delete-Member.gif)
### 9. Summary Video
[Summary Video Link](https://file.notion.so/f/f/93d88247-7ea3-4e21-aa86-4d58e8afca25/ed0de011-9baf-455f-9155-13994fec4ae7/발표_영상.mov?id=b3609e8e-e5a0-4ef4-b25e-ec00afba1e6f&table=block&spaceId=93d88247-7ea3-4e21-aa86-4d58e8afca25&expirationTimestamp=1700841600000&signature=k86XeV5kD_u2g3sBwIR4Hkcp_PHTDx52HHI_VU9mM0Y&downloadName=발표+영상.mov)

[Back to Table of Contents](#table-of-contents)
