# 🎟️에티켓(Every Ticket)
![에티켓이미지](https://github.com/user-attachments/assets/34672602-5c6e-4355-9a5e-a0e14ddfa6b1)

## 프로젝트 소개
에티켓은 사용자가 간편하고 효율적으로 공연 티켓을 예매할 수 있는 플랫폼입니다. 이 프로젝트는 안정성과 확장성을 고려한 아키텍처 설계, 다양한 AWS 서비스를 활용한 인프라 구축, CI/CD 파이프라인의 자동화 등 다양한 기술적 도전을 통해 구축되었습니다.
[자세한 내용은 WIKI를 참조해주세요.](https://github.com/b1-sycls/sycls/wiki)
## 목차

1. [사이트 바로가기](#사이트-바로가기)
2. [팀노션 바로가기](#팀노션-바로가기)
3. [팀원 소개](#팀원-소개)
4. [주요 기술](#주요-기술)
5. [아키텍처](#아키텍처)
6. [ERD](#ERD)
7. [주요 기술](#주요-기술)
8. [기술적 의사 결정](#기술적-의사-결정)
    - [서비스 단위의 아키텍처 구성](#서비스-단위의-아키텍처-구성)
    - [CI/CD 파이프라인 구축과 자동화](#cicd-파이프라인-구축과-자동화)
    - [무중단 배포](#무중단-배포)
    - [AWS WAF 사용](#aws-waf-사용)
    - [ElastiCache 도입](#elasticache-도입)
    - [공연 좌석 무분별 점유 문제 해결](#공연-좌석-무분별-점유-문제-해결)
    - [토큰 관리 방법](#토큰-관리-방법)
9. [트러블슈팅](#트러블슈팅)
    - [Spring boot 3.x ElastiCache for Redis Connection 문제](#spring-boot-3x-elasticache-for-redis-connection-문제)
    - [Redis Token 관리 문제](#redis-token-관리-문제)
    - [@RequestBody와 @RequestPart 동시 수신 문제](#requestbody와-requestpart-동시-수신-문제)
    - [좌석 점유 캐싱 문제](#좌석-점유-캐싱-문제)
    - [조회 성능 최적화](#조회-성능-최적화)
    - [좌석 등급 관리 문제](#좌석-등급-관리-문제)
    - [Double Submit 방지](#double-submit-방지)


## 사이트 바로가기
- **유저 사이트**: [www.everyticket.shop](http://www.everyticket.shop)
- **관리자 사이트**: [www.everyticket.shop/manage](http://www.everyticket.shop/manage)

## 팀노션 바로가기
- [에티켓 팀 노션](https://teamsparta.notion.site/everyTicket-91486dd6f1af4dbca95a33ba3bc219b7)
  
## 팀원 소개
| 이름     | 역할                       | Blog                                           | GitHub           |
|----------|----------------------------|------------------------------------------------|------------------|
| 서찬원   | 예매, 결제                 | [Blog](https://scwonn60.tistory.com)           | [Chanwon-Seo](https://github.com/Chanwon-Seo) |
| 조경민   | 공연, 회차, 카테고리, 출연진 | [Blog](https://velog.io/@one_step_than/posts)  | [CKM123423](https://github.com/CKM123423) |
| 윤성모   | 공연장, 좌석, 리뷰           | [Blog](https://velog.io/@momoysm/posts)        | [momoysm](https://github.com/momoysm) |
| 이진욱   | 사용자 인증, 회원 관리       | [Notion](https://leecoding.notion.site)       | [Leejinuk123](https://github.com/Leejinuk123) |

## 아키텍처 
![B1-Architecture-last drawio](https://github.com/user-attachments/assets/74187d81-0140-426b-9076-cf7594fff457)
[🏗 아키텍처 Wiki](https://github.com/b1-sycls/sycls/wiki/%F0%9F%8F%97-%EC%95%84%ED%82%A4%ED%85%8D%EC%B2%98)


## ERD
![티켓예매 v3 (1) (1)](https://github.com/user-attachments/assets/6ae18336-f0a5-464f-aa63-3928e7fdc600)

## 주요 기술
- [🍀 주요 기술](https://github.com/b1-sycls/sycls/wiki/%F0%9F%8D%80-%EC%A3%BC%EC%9A%94-%EA%B8%B0%EC%88%A0)

## 🗣️기술적 의사 결정

### 서비스 단위의 아키텍처 구성

[1. 서비스 단위의 아키텍처 구성](https://github.com/b1-sycls/sycls/wiki/%EC%84%9C%EB%B9%84%EC%8A%A4-%EB%8B%A8%EC%9C%84%EC%9D%98-%EC%95%84%ED%82%A4%ED%85%8D%EC%B2%98-%EA%B5%AC%EC%84%B1)

### CI/CD 파이프라인 구축과 자동화

[2. CI/CD 파이프라인 구축과 자동화](https://github.com/b1-sycls/sycls/wiki/CI-CD-%ED%8C%8C%EC%9D%B4%ED%94%84%EB%9D%BC%EC%9D%B8-%EA%B5%AC%EC%B6%95%EA%B3%BC-%EC%9E%90%EB%8F%99%ED%99%94)

### 무중단 배포

[3. 무중단 배포를 결정한 이유](https://github.com/b1-sycls/sycls/wiki/%EB%AC%B4%EC%A4%91%EB%8B%A8-%EB%B0%B0%ED%8F%AC%EB%A5%BC-%EA%B2%B0%EC%A0%95%ED%95%9C-%EC%9D%B4%EC%9C%A0)


### AWS WAF 사용

[4. AWS WAF를 사용한 이유](https://github.com/b1-sycls/sycls/wiki/AWS-WAF%EB%A5%BC-%EC%82%AC%EC%9A%A9%ED%95%9C-%EC%9D%B4%EC%9C%A0)

### ElastiCache 도입

[5. ElastiCache](https://github.com/b1-sycls/sycls/wiki/ElastiCache)

### 공연 좌석 무분별 점유 문제 해결

[6. 공연 좌석 무분별 점유 문제 및 해결 필요성](https://github.com/b1-sycls/sycls/wiki/%EA%B3%B5%EC%97%B0-%EC%A2%8C%EC%84%9D-%EB%AC%B4%EB%B6%84%EB%B3%84-%EC%A0%90%EC%9C%A0-%EB%AC%B8%EC%A0%9C-%EB%B0%8F-%ED%95%B4%EA%B2%B0-%ED%95%84%EC%9A%94%EC%84%B1)

### 토큰 관리 방법
[7. 토큰 관리 방법](https://github.com/b1-sycls/sycls/wiki/%ED%86%A0%ED%81%B0-%EA%B4%80%EB%A6%AC-%EB%B0%A9%EB%B2%95)

## 🛠트러블슈팅

### Spring boot 3.x ElastiCache for Redis Connection 문제

[1. Spring Boot와 ElastiCache 연결 문제](https://github.com/b1-sycls/sycls/wiki/Spring-Boot%EC%99%80-ElastiCache-%EC%97%B0%EA%B2%B0-%EB%AC%B8%EC%A0%9C)

### Redis Token 관리 문제

[2. Redis Token 관리 구조 문제](https://github.com/b1-sycls/sycls/wiki/Redis-Token-%EA%B4%80%EB%A6%AC-%EA%B5%AC%EC%A1%B0-%EB%AC%B8%EC%A0%9C)

### @RequestBody와 @RequestPart 동시 수신 문제

[3. @RequestBody 와 @RequestPart 동시에 받기](https://github.com/b1-sycls/sycls/wiki/@RequestBody-%EC%99%80-@RequestPart-%EB%8F%99%EC%8B%9C%EC%97%90-%EB%B0%9B%EA%B8%B0)

### 좌석 점유 캐싱 문제

[4. 좌석 점유 캐싱](https://github.com/b1-sycls/sycls/wiki/%EC%A2%8C%EC%84%9D-%EC%A0%90%EC%9C%A0-%EC%BA%90%EC%8B%B1)

### 조회 성능 최적화

[5. 조회 성능 최적화 ( 유저 피드백 )](https://github.com/b1-sycls/sycls/wiki/5.-%EC%A1%B0%ED%9A%8C-%EC%84%B1%EB%8A%A5-%EC%B5%9C%EC%A0%81%ED%99%94-(-%EC%9C%A0%EC%A0%80-%ED%94%BC%EB%93%9C%EB%B0%B1-))

### 좌석 등급 관리 문제

[6. 좌석‐등급 관리 방법 변경](https://github.com/b1-sycls/sycls/wiki/%EC%A2%8C%EC%84%9D%E2%80%90%EB%93%B1%EA%B8%89-%EA%B4%80%EB%A6%AC-%EB%B0%A9%EB%B2%95-%EB%B3%80%EA%B2%BD)

### Double Submit 방지

[7. Double Submit 방지](https://github.com/b1-sycls/sycls/wiki/Double-Submit-%EB%B0%A9%EC%A7%80)
