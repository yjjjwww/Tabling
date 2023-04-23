# 매장 테이블 예약 서비스

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
![스크린샷 2023-04-24 오전 5.04.34.png](..%2F..%2FDesktop%2F%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202023-04-24%20%EC%98%A4%EC%A0%84%205.04.34.png)
___
## 데이터베이스 스키마
