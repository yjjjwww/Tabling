# 매장 테이블 예약 서비스
___

Use : Spring, Jpa, MariaDB

Goal : 식당이나 점포를 이용하기 전에 미리 예약을 하여 편하게 식당/점포를 이용할 수 있는 서비스 개발한다.
___
## Swagger API
http://localhost:8080/swagger-ui/index.html
___
## Customer
- 회원가입
- 로그인(JWT 토큰 발행 : "CUSTOMER" Role)
- 매장 목록 조회
- 매장 예약
- 매장 방문 확인
- 리뷰 등록, 수정, 삭제
___
## Manager
- 회원가입
- 로그인(JWT 토큰 발행 : "MANAGER" Role)
- 파트너 가입
- 매장별 예약 목록 조회
- 매장 예약 승인, 취소
___
## Review
- 모든 리뷰 최신순으로 조회
- 매장별 리뷰 최신순으로 조회
___
## 개발 서비스 흐름도
<img width="1113" alt="스크린샷 2023-04-24 오전 5 04 34" src="https://user-images.githubusercontent.com/100079037/233864250-790d5f43-95b6-4388-ba89-5a48b4066042.png">

___
## 데이터베이스 스키마
![tabling](https://user-images.githubusercontent.com/100079037/233864317-4cb64156-86f0-4660-b9eb-bd0e4688acd6.png)
