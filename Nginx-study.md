> 출처 : [Nginx HTTP Server(한국어판)](https://book.naver.com/bookdb/book_detail.nhn?bid=6740233)

## Nginx 등장 배경
>성능을 결정하는데 여러 요소가 있지만 크게 웹 서버, 웹 클라이언트, 통신망의 세 가지를 생각할 수 있다.  
웹 서버와 웹 클라이언트의 처리 속도가 아무리 빨라도 통신망의 속도가 느리면 웹 서버와 웹 클라이언트의 좋은 성능은 무용지물이 된다.  
따라서 웹의 역사 초기에는 통신망 성능 이슈에 초점이 맞춰져 있었다.  
하지만 이제는 웹 서버의 성능 이슈를 생각해야한다.  
_from 올긴이의 말_  

## Nginx의 장점
>1. 다른 경쟁 제품들보다 속도가 빠르다.  
엔진엑스는 비동기 소켓을 사용하는데, 요청을 받을 때마다 매번 프로세스를 만들지 않고, 코어당 하나의 프로세스만으로 수천 개의 접속을 충분히 처리하면서도 CPU 부하와 메모리 사용은 휠씬 적다.  
2. 사용하기 쉽다.  
아파치 등의 웹 서버에 비해 환경 설정 파일을 매우 간단하게 읽고 수정할 수 있다.  
3. 플러그인 시스템  
엔진엑스는 BSD 라이선스 기반의 오픈소스 프로젝트이며, '모듈'이라 불리는 플러그인 시스템이 있다.  
_from 들어가며_

## Nginx 다운로드 및 설치
- 선행 요소 설치  
  - __GCC__  
  엔진엑스는 C로 작성되어 있기에 C언어 컴파일 도구를 설치해야한다.
  - __pcre__, __pcre-devel__  
  엔진엑스를 컴파일하는데 펄 호환 정규식이 필요하다.  
  엔진엑스 재작성 모듈과 HTTP 코어 모듈은 PCRE 구문에 따르는 정규표현식을 사용한다.
  pcre는 컴파일된 버전의 라이브러리를 제공하고  
  pcre-devel은 프로젝트를 컴파일하기 위한 개발용 헤더와 소스코드를 제공한다.
  - __zlib__, __zlib-devel__  
  zlib은 압축 알고리즘을 제공한다. 엔진엑스 모듈에서 gzip 압축을 사용하려면 zlib 라이브러리가 필요하다.
  - __openssl__, __openssl-del__   
  OpenSSL 프로젝트는 고성능 범용 암호 해독 라이브러리, SSL v2/v3, TLS v1을 구현하여 상용 제품 수준의 오픈소스 툴킷을 개발한다. (SSL v2/v3은 보안 결함으로 더이상 사용하지 않습니다. [참고링크](https://wiki.openssl.org/index.php/SSL_and_TLS_Protocols))  
  엔진엑스는 OpenSSL 라이브러리를 이용해 보안 웹페이지를 서비스하기 때문에 openssl를 설치해야한다.