# Voyage
## About This Project
> 여행 정보를 조회하고 검색할 수 있는 서비스입니다.
## Table of Contents
- [About This Project](#about-this-project)
  - [기획 의도](#기획-의도)
  - [기획 배경](#기획-배경)
  - [기획 목표](#기획-목표)
  - [기획 대상](#기획-대상)
  - [개발 기간](#개발-기간)
- [Developers](#developers)
- [Tech Stack](#tech-stack)
  - [Backend](#backend)
  - [Frontend](#frontend)
  - [Tools](#tools)
- [API Document](#api-document)
- [기능 구현 목록](#기능-구현-목록)
  - [협업](#협업)
  - [Backend](#backend-1)
    - [Authentication](#authentication)
    - [회원](#회원)
    - [여행 정보](#여행-정보)
  - [Frontend](#frontend-1)
- [Demo](#demo)

### 개발 기간
> 2023 11/01 - 2023/11/24

## Developers
- [Cha Cha](https://github.com/ChaCha3088)
- 능이 버섯(이혜지)

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

## [API Document](https://cha3088.notion.site/API-54ad5cc3557b4485959a965e3f1b4b82?pvs=4)
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

### Frontend
- [ ] Vue3
  - [ ] 메인
  - [ ] 회원
    - [ ] 회원가입
    - [ ] 로그인
    - [ ] 회원 정보 수정
    - [ ] 회원 탈퇴
  - [ ] 공지사항
    - [ ] 조회
    - [ ] 작성
    - [ ] 수정
    - [ ] 삭제
  - [ ] 여행 정보
    - [x] 조회
    - [x] 검색
    - [ ] 작성
    - [ ] 수정
    - [ ] 삭제

[Back to Table of Contents](#table-of-contents)

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
