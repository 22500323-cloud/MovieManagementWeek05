# Movie Management Week 05

Spring Boot를 이용하여 영화 정보를 관리하는 REST API CRUD 프로젝트입니다.

## 1. 프로젝트 소개

영화 정보를 등록, 조회, 수정, 삭제할 수 있는 REST API를 구현했습니다.

영화 데이터는 데이터베이스를 사용하지 않고 Java Collection인 `ArrayList`를 이용하여 메모리에 저장합니다.

### 영화 정보

* title : 영화 제목
* director : 감독
* genre : 장르
* year : 개봉 연도
* rating : 평점
* runningTime : 상영 시간

총 6개의 필드를 사용했으며, `id`는 서버에서 자동으로 생성합니다.

---

## 2. 개발 환경

* Java 17
* Spring Boot
* Spring Web
* Gradle
* IntelliJ IDEA
* Java Collection (`ArrayList`)

데이터베이스를 사용하지 않고 메모리 기반 저장소를 사용했습니다.

---

## 3. 프로젝트 구조

text
src
└── main
    └── java
        └── com.webservice.moviemanagementweek05
            ├── controller
            │   └── MovieController.java
            │
            ├── domain
            │   └── Movie.java
            │
            ├── dto
            │   ├── MovieRequest.java
            │   └── MovieResponse.java
            │
            ├── exception
            │   ├── GlobalExceptionHandler.java
            │   └── MovieNotFoundException.java
            │
            ├── repository
            │   ├── MovieRepository.java
            │   └── MemoryMovieRepository.java
            │
            ├── service
            │   └── MovieService.java
            │
            └── MovieManagementWeek05Application.java


---

## 4. 계층 구조

text
Client
  ↓
MovieController
  ↓
MovieService
  ↓
MovieRepository
  ↓
MemoryMovieRepository
  ↓
ArrayList<Movie>


Controller에서는 HTTP 요청을 받고 Service를 호출합니다.

Service에서는 영화 등록, 조회, 수정, 삭제와 입력값 검증을 처리합니다.

Repository에서는 영화 데이터를 저장하고 조회합니다.

실제 저장은 `MemoryMovieRepository`에서 `ArrayList`를 이용하여 처리합니다.

---

## 5. API 목록

| 기능        | Method | URL                         |
| --------- | ------ | --------------------------- |
| 영화 등록     | POST   | `/api/movies`               |
| 전체 영화 조회  | GET    | `/api/movies`               |
| 영화 한 개 조회 | GET    | `/api/movies/{id}`          |
| 영화 수정     | PUT    | `/api/movies/{id}`          |
| 영화 삭제     | DELETE | `/api/movies/{id}`          |
| 장르 검색     | GET    | `/api/movies?genre={genre}` |

---

## 6. 영화 등록

### Request

http
POST /api/movies
Content-Type: application/json


json
{
  "title": "Parasite",
  "director": "Bong Joon-ho",
  "genre": "Drama",
  "year": 2019,
  "rating": 9.0,
  "runningTime": 132
}


### Response

json
{
  "id": 1,
  "title": "Parasite",
  "director": "Bong Joon-ho",
  "genre": "Drama",
  "year": 2019,
  "rating": 9.0,
  "runningTime": 132
}


영화가 등록되면 ID가 자동으로 생성됩니다.

---

## 7. 전체 영화 조회

http
GET /api/movies


### Response

json
[
  {
    "id": 1,
    "title": "Parasite",
    "director": "Bong Joon-ho",
    "genre": "Drama",
    "year": 2019,
    "rating": 9.0,
    "runningTime": 132
  }
]


---

## 8. 영화 한 개 조회

http
GET /api/movies/1


존재하는 ID라면 해당 영화 정보를 반환합니다.

존재하지 않는 ID라면 `404 Not Found`를 반환합니다.

---

## 9. 영화 수정
http
PUT /api/movies/1
Content-Type: application/json


json
{
  "title": "Parasite",
  "director": "Bong Joon-ho",
  "genre": "Thriller",
  "year": 2019,
  "rating": 9.5,
  "runningTime": 132
}


수정된 영화 정보를 반환합니다.

---

## 10. 영화 삭제

http
DELETE /api/movies/1


삭제에 성공하면 `204 No Content`를 반환합니다.

삭제된 영화 ID를 다시 조회하면 `404 Not Found`가 반환됩니다.

---

## 11. 잘못된 입력 처리

영화 등록 및 수정 시 입력값을 검사합니다.

예를 들어 제목이 비어 있으면:

json
{
  "title": "",
  "director": "Bong Joon-ho",
  "genre": "Drama",
  "year": 2019,
  "rating": 9.0,
  "runningTime": 132
}


다음과 같이 `400 Bad Request`가 반환됩니다.

json
{
  "message": "Title is required"
}


또한 다음과 같은 입력을 검사합니다.

* 제목이 비어 있는 경우
* 감독이 비어 있는 경우
* 장르가 비어 있는 경우
* 연도가 0 이하인 경우
* 평점이 0~10 범위를 벗어나는 경우
* 상영 시간이 0 이하인 경우

`GlobalExceptionHandler`에서 `IllegalArgumentException`을 처리하여 `400 Bad Request`를 반환합니다.

---

## 12. 장르 검색 기능

추가 기능으로 장르 검색을 구현했습니다.

http
GET /api/movies?genre=Drama


예를 들어 `Drama`를 검색하면 Drama 장르의 영화만 반환합니다.

### 테스트 데이터

json
{
  "title": "Parasite",
  "director": "Bong Joon-ho",
  "genre": "Drama",
  "year": 2019,
  "rating": 9.0,
  "runningTime": 132
}


json
{
  "title": "Inception",
  "director": "Christopher Nolan",
  "genre": "Sci-Fi",
  "year": 2010,
  "rating": 8.8,
  "runningTime": 148
}


다음 요청:

http
GET /api/movies?genre=Drama


을 보내면 `Drama` 장르인 `Parasite`가 조회됩니다.

---

## 13. 로컬 실행 방법

프로젝트 폴더에서 다음 명령어를 실행합니다.

powershell
.\gradlew bootRun


또는 IntelliJ에서 `MovieManagementWeek05Application`을 실행합니다.

서버가 실행되면 다음 주소를 사용할 수 있습니다.

text
http://localhost:8080


---

## 14. 로컬 테스트

IntelliJ HTTP Client의 `test.http`를 이용하여 CRUD를 테스트했습니다.

테스트 순서는 다음과 같습니다.

1. 영화 등록
2. 전체 영화 조회
3. 영화 한 개 조회
4. 영화 수정
5. 수정 결과 확인
6. 영화 삭제
7. 삭제 결과 확인
8. 존재하지 않는 영화 조회
9. 잘못된 입력 테스트
10. 장르 검색 테스트

### 테스트 결과

* CREATE : `201 Created`
* READ ALL : `200 OK`
* READ ONE : `200 OK`
* UPDATE : `200 OK`
* DELETE : `204 No Content`
* 존재하지 않는 ID : `404 Not Found`
* 잘못된 입력 : `400 Bad Request`
* 장르 검색 : `200 OK`

---

## 15. Solution 분석

### Q1. 영화 등록은 어디에서 처리되는가?

`MovieController`의 `create()` 메서드에서 POST 요청을 받고 `MovieService`의 `create()` 메서드를 호출합니다.

`MovieService.create()`에서 `Movie` 객체를 생성하고 Repository의 `save()`를 호출하여 저장합니다.

### Q2. 영화 ID는 어떻게 자동으로 생성되는가?

`MemoryMovieRepository`의 `nextId`를 이용합니다.

영화를 저장할 때 현재 `nextId`를 영화 ID로 사용한 후 `nextId`를 1 증가시킵니다.

### Q3. 영화 한 개를 조회할 때 존재하지 않는 ID는 어떻게 처리하는가?

`MovieService.findById()`에서 `findById()` 결과가 없으면 `MovieNotFoundException`을 발생시킵니다.

`GlobalExceptionHandler`가 이 예외를 처리하여 `404 Not Found`를 반환합니다.

### Q4. 잘못된 입력은 어떻게 처리하는가?

`MovieService`의 `validate()` 메서드에서 입력값을 검사합니다.

조건에 맞지 않는 입력이 들어오면 `IllegalArgumentException`을 발생시킵니다.

`GlobalExceptionHandler`에서 이를 처리하여 `400 Bad Request`를 반환합니다.

### Q5. 영화 수정은 어디에서 처리되는가?

`MovieController`의 `update()`가 요청을 받고 `MovieService.update()`를 호출합니다.

Service에서 기존 영화를 찾은 후 `Movie`의 `update()` 메서드를 호출하여 내용을 변경합니다.

### Q6. 장르 검색은 어떻게 구현했는가?

`MovieController`에서 `genre` 파라미터를 받아 `MovieService.findByGenre()`를 호출합니다.

Repository의 `findByGenre()`에서 Java Stream을 사용하여 원하는 장르의 영화만 필터링합니다.

---

## 16. 개발 과정

### 1단계 - 프로젝트 생성

Spring Boot와 Spring Web을 사용하여 새로운 프로젝트를 생성했습니다.

### 2단계 - 영화 도메인 및 DTO 구현

`Movie`, `MovieRequest`, `MovieResponse`를 구현했습니다.

Request DTO와 Response DTO를 분리하여 사용했습니다.

### 3단계 - Repository 및 Service 구현

`MovieRepository` 인터페이스와 `MemoryMovieRepository`를 구현했습니다.

`ArrayList`를 이용하여 영화 데이터를 저장했습니다.

### 4단계 - REST CRUD 구현

`MovieController`에서 POST, GET, PUT, DELETE API를 구현했습니다.

### 5단계 - 예외 처리 및 추가 기능

입력값 검증과 존재하지 않는 영화 처리 기능을 구현했습니다.

추가 기능으로 장르 검색을 구현했습니다.

---

## 17. Docker

Docker를 이용한 실행 환경을 구성하기 위해 `Dockerfile`을 추가했습니다.

Java 17 환경에서 Gradle을 이용하여 Spring Boot 애플리케이션을 빌드하고 실행하도록 구성했습니다.

---

## 18. Git

Git을 이용하여 개발 과정을 단계별로 관리했습니다.

주요 커밋은 다음과 같습니다.

* 프로젝트 생성
* Movie 생성
* MovieRequest 구현
* MovieResponse 구현
* MovieRepository 구현
* MovieService 구현
* Spring Bean 등록
* 예외 처리 기능 구현
* 장르 검색 기능 구현
* README 및 Docker 설정

총 10개 이상의 커밋으로 개발 과정을 관리했습니다.

---

## 19. GitHub

Personal GitHub Repository:

https://github.com/22500323-cloud/MovieManagementWeek05.git

Organization GitHub Repository:

https://github.com/2026-2-WebService/assign05-c01-22500323.git

---

## 20. 배포

Deployment URL:

`[배포 URL 입력]`

배포 후 GET, POST 및 장르 검색 기능을 테스트할 예정입니다.

---

# Weekly Report

## 1. Key Learning

첫 번째로 Spring Boot에서 Controller, Service, Repository로 역할을 나누는 구조를 이해했습니다.

두 번째로 Request DTO와 Response DTO를 분리하여 사용하는 방법을 배웠습니다.

세 번째로 Java Collection을 이용하여 데이터베이스 없이 CRUD 기능을 구현하는 방법을 배웠습니다.

## 2. Problem & Solution

처음에는 Controller와 다른 클래스의 package 위치가 맞지 않아 API 요청이 정상적으로 동작하지 않는 문제가 있었습니다.

package 구조를 정리하고 Spring Boot의 Component Scan 범위를 확인하여 문제를 해결했습니다.

또한 존재하지 않는 영화 ID를 조회했을 때 발생하는 예외를 처리하기 위해 `MovieNotFoundException`과 `GlobalExceptionHandler`를 추가했습니다.

## 3. Code Review

CRUD 기능을 Controller에 모두 작성하지 않고 Controller, Service, Repository로 나누어 구현했습니다.

이를 통해 각 클래스의 역할을 구분할 수 있었습니다.

## 4. AI Usage

개발 과정에서 AI를 활용하여 오류 메시지의 원인을 확인하고 코드 구조를 이해하는 데 도움을 받았습니다.

특히 Spring Boot의 package 구조, 예외 처리, Repository 구조를 확인하는 과정에서 활용했습니다.

AI가 제시한 코드를 그대로 사용하기보다는 현재 프로젝트의 구조에 맞게 수정하고 직접 실행하여 결과를 확인했습니다.

## 5. Reflection

이번 과제를 통해 Spring Boot에서 REST API가 어떻게 동작하는지 조금 더 이해하게 되었습니다.

처음에는 Controller, Service, Repository의 역할이 헷갈렸지만 직접 CRUD 기능을 구현하면서 각각의 역할을 구분할 수 있게 되었습니다.

특히 오류가 발생했을 때 단순히 코드를 다시 작성하는 것이 아니라 오류 메시지를 확인하고 어느 클래스에서 문제가 발생했는지 찾아가는 과정이 중요하다는 것을 배웠습니다.

## 6. Suggestion

실습 과정에서 기본 CRUD 이후 예외 처리나 추가 기능까지 직접 구현해보는 과정이 Spring Boot 구조를 이해하는 데 도움이 되었습니다.
