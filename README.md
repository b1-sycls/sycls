# 🎟️에티켓(Every Ticket)
![에티켓이미지](https://github.com/user-attachments/assets/34672602-5c6e-4355-9a5e-a0e14ddfa6b1)

## 프로젝트 소개
에티켓은 사용자가 간편하고 효율적으로 공연 티켓을 예매할 수 있는 플랫폼입니다. 이 프로젝트는 안정성과 확장성을 고려한 아키텍처 설계, 다양한 AWS 서비스를 활용한 인프라 구축, CI/CD 파이프라인의 자동화 등 다양한 기술적 도전을 통해 구축되었습니다.

## 목차

1. [사이트 바로가기](#사이트-바로가기)
2. [팀노션 바로가기](#팀노션-바로가기)
3. [팀원 소개](#팀원-소개)
4. [기술 환경](#기술-환경)
    - [Backend](#backend)
    - [Infrastructure](#infrastructure)
    - [FrontEnd](#frontend)
5. [아키텍처](#아키텍처)
6. [ERD](#ERD)
7. [기술환경](#기술환경)
8. [기술적 의사 결정](#기술적-의사-결정)
    - [서비스 단위의 아키텍처 구성](#서비스-단위의-아키텍처-구성)
    - [CI/CD 파이프라인 구축과 자동화](#cicd-파이프라인-구축과-자동화)
    - [무중단 배포](#무중단-배포)
    - [AWS WAF 사용](#aws-waf-사용)
    - [ElastiCache 도입](#elasticache-도입)
    - [공연 좌석 무분별 점유 문제 해결](#공연-좌석-무분별-점유-문제-해결)
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

## 🏗 아키텍처 
![B1-Architecture-last drawio](https://github.com/user-attachments/assets/74187d81-0140-426b-9076-cf7594fff457)
![🏗 아키텍처 Wiki]([https://github.com/b1-sycls/sycls/wiki/🍀-주요-기술](https://github.com/b1-sycls/sycls/wiki/🏗-아키텍처))

## ERD
![티켓예매 v3 (1) (1)](https://github.com/user-attachments/assets/6ae18336-f0a5-464f-aa63-3928e7fdc600)

## 🍀 주요 기술
[🍀 주요 기술](https://github.com/b1-sycls/sycls/wiki/🍀-주요-기술)

## 🗣️기술적 의사 결정

### 서비스 단위의 아키텍처 구성

<details>
<summary>도입 배경 및 효과</summary>

**도입 배경**: 모놀리식 아키텍처에서 발생할 수 있는 장애 전파 문제를 해결하기 위해 관심사 분리를 통해 유지보수성과 확장성을 고려한 구조로 설계하였습니다.

**효과**: 관심사 분리로 인해 유지보수성이 향상되었으며, 장애 전파가 방지되고 독립적으로 관리할 수 있는 확장성이 증가했습니다.

</details>

### CI/CD 파이프라인 구축과 자동화

<details>
<summary>도입 배경 및 효과</summary>

**도입 배경**: EC2에 직접 git clone을 받아 수동으로 배포하는 불편함을 개선하고, 자동화 배포를 위해 CI/CD 파이프라인을 구축했습니다.

**효과**: 자동화 배포로 인해 수동 작업의 불편함이 줄어들고, 더 안정적이고 일관된 배포가 가능해졌습니다.

</details>

### 무중단 배포

<details>
<summary>도입 배경 및 효과</summary>

**도입 배경**: 배포 시 서버가 중단되어 사용자 경험과 비즈니스에 악영향을 미치는 문제를 해결하기 위해 무중단 배포를 도입했습니다.

**효과**: Blue/Green 배포 방식을 통해 서비스 가용성을 유지하며, 배포 리스크를 최소화하고 신속한 롤백이 가능해졌습니다.

</details>

### AWS WAF 사용

<details>
<summary>도입 배경 및 효과</summary>

**도입 배경**: Public API 지원으로 인한 서버 과부하와 악의적인 트래픽으로부터 보호하기 위해 AWS WAF를 도입했습니다.

**효과**: API 호출에 대한 커스텀 룰셋 적용과 다양한 웹 공격에 대한 방어를 통해 애플리케이션의 보안과 안정성을 강화했습니다.

</details>

### ElastiCache 도입

<details>
<summary>도입 배경 및 효과</summary>

**도입 배경**: 데이터베이스 부하를 줄이고 성능을 최적화하기 위해 AWS ElastiCache(Redis)를 도입했습니다.

**효과**: 데이터베이스 통신 횟수가 줄어들고, 전체 시스템 성능이 향상되었으며, 중요한 데이터의 인메모리 관리로 사용자 경험이 개선되었습니다.

</details>

### 공연 좌석 무분별 점유 문제 해결

<details>
<summary>도입 배경 및 효과</summary>

**도입 배경**: 결제 페이지에서 좌석 선택을 완료하지 않고 돌아가는 경우 좌석이 중복 선택되는 문제를 해결하기 위해 새로운 좌석 관리 방식을 도입했습니다.

**효과**: 좌석 중복 선택을 방지하고 시스템의 신뢰성을 높여 사용자 경험을 개선했습니다.

</details>

## 🛠트러블슈팅

### Spring boot 3.x ElastiCache for Redis Connection 문제

<details>
<summary>트러블슈팅 과정</summary>

**문제 정의**: 회원가입 과정에서 이메일 인증 코드 전송 시 Redis 연결 문제 발생  
**원인**: EC2와 ElastiCache 간의 성능 차이  
**해결 방법**: 로그 추가, 지연 초기화, 명령 대기 시간 설정 등을 통해 문제를 해결하고, 최종적으로 성능 환경을 맞춰 문제를 근본적으로 해결했습니다.

</details>

### Redis Token 관리 문제

<details>
<summary>트러블슈팅 과정</summary>

**문제 정의**: JWT 토큰을 Redis에 저장할 때, 같은 유저의 AccessToken과 RefreshToken이 중복 저장됨  
**해결 방법**: RefreshToken을 Key로 사용해 AccessToken과 함께 관리하여 데이터 중복 문제를 해결했습니다.

</details>

### @RequestBody와 @RequestPart 동시 수신 문제

<details>
<summary>트러블슈팅 과정</summary>

**문제 정의**: MultipartFile과 DTO를 동시에 수신하지 못함  
**해결 방법**: @RequestPart를 사용하여 DTO를 수신하고, 요청 시 Content-Type을 지정하여 문제를 해결했습니다.

</details>

### 좌석 점유 캐싱 문제

<details>
<summary>트러블슈팅 과정</summary>

**문제 정의**: 잦은 RDB 조회로 인한 성능 문제  
**해결 방법**: Redis에 좌석 정보를 캐싱하고 TTL을 설정하여 성능을 개선했습니다.

</details>

### 조회 성능 최적화

<details>
<summary>트러블슈팅 과정</summary>

**문제 정의**: 대용량 데이터로 인해 조회 성능이 저하됨  
**해결 방법**: 인덱스 생성 및 쿼리 최적화를 통해 성능을 대폭 향상시켰습니다.

</details>

### 좌석 등급 관리 문제

<details>
<summary>트러블슈팅 과정</summary>

**문제 정의**: 단일 좌석의 등급 관리가 어렵고 비효율적임  
**해결 방법**: 좌석 등급을 일괄적으로 관리할 수 있는 구조로 변경했습니다.

</details>

### Double Submit 방지

<details>
<summary>트러블슈팅 과정</summary>

**문제 정의**: 동일한 요청이 여러 번 중복 등록되는 문제  
**해결 방법**: 프론트에서 버튼 비활성화와 Redis를 사용한 분산락으로 중복 요청을 방지했습니다.

</details>
