# 판다산다 - 중고거래 플랫폼
## 프로젝트 소개
- 상품을 판매할 때 각자의 개개인의 쇼핑몰을 만들어 상품을 판매하는 느낌을 줍니다.
- 안전한 중고 거래가 될 수 있도록 미리 시간과 장소를 정하고, 결제를 할 때는 상대방과의 동의를 구하는 식으로 구현되었습니다.
- 직거래 시에 신속하게 거래를 할 수 있는 장점이 있습니다.
  
## 주요 기능 ##
회원가입, 로그인, 마이스토어, 상품 등록, 계좌 등록, 구매 신청, 카테고리, 상품 검색
## 기술 스택
React, Java(Version: 21), Spring Boot, JPA, Axios, MySQL, AWS(EC2, S3, RDS), Nginx, PM2, Git

|프론트엔드|백엔드|
|-|-|
|https://github.com/2WeekO/panda-sanda-market-front|https://github.com/2WeekO/pandasanda-market-back|

### URL: https://pandasanda.shop

- 안전하고 신뢰할 수 있으며 간편한 중고거래 플랫폼입니다.
- 사기 방지를 위해서 판매자와 구매자가 얼마든지 구매 요청을 취소할 수 있도록 상품 관리 기능을 제공하고 있습니다.
  
### 홈페이지
|PC 화면|모바일 화면|
|-|-|
|![{EC1E49F2-B36C-4841-A61A-2E4C8F0E06EB}](https://github.com/user-attachments/assets/12e64cf2-4645-492f-9eca-81a86a549f44)|![{ED1AF387-E367-40E6-915C-7CB24CFC355F}](https://github.com/user-attachments/assets/46dc3986-5d2e-416c-a6c5-6bff52aa877f)|
|![{2B0FDEBA-1368-4C36-A8D0-A12B54835A9A}](https://github.com/user-attachments/assets/6fe4d483-b0f6-4813-9686-ad67f19ae0a0)|![{A23116E0-5384-4E92-B68E-448BC856DBC4}](https://github.com/user-attachments/assets/00293477-09cb-4e00-a4c6-734f978efe0c)|




## 설계

| Archtecture 설계 | MySQL DB설계 | 컴포넌트 설계(Figma)|
|-|-|-|
|![{47F3AD69-905E-4DF0-8B38-5FBED06EBA7C}](https://github.com/user-attachments/assets/552de45c-b327-47bc-84b5-ab5dbfc58cfe) |![{101DD119-D70F-407A-88D4-DF4C7A9EB0E1}](https://github.com/user-attachments/assets/2c778869-9c5c-4202-a1ae-26683c296f79)|![{FB6EFE88-A0DD-4A6F-830A-981F83FE7F79}](https://github.com/user-attachments/assets/294c884d-4434-4d0c-992c-e1b9b7a92156)|


### 추가 개발해야할 기능
- OAuth 2.0 소셜 로그인 기능
- 유저 프로필 기능
- 회원가입 이메일 인증 기능(Redis)
- 구매 & 판매 상황 보기 편하도록 UI 수정
