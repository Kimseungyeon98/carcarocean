![image](https://github.com/Kimseungyeon98/carcarocean/assets/121033246/633fd00d-148a-4142-a897-b7c0bc45d57b)

프로젝트 발표 자료: [CarCarOcean.pdf](https://github.com/user-attachments/files/15815660/CarCarOcean.pdf)

# 프로젝트 이름
> ***CarCarOcean(카카오션)***
> 

# 프로젝트 소개
> 자신이 타던  차를  빠르고 합리적인 가격에  팔  수  있으며, 반대로  사회초년생들을  위한  중고차  구매를 검수자들이  검토  후  판매하는  중고차  거래  사이트입니다.
> 
> 중고차 판매, 검수 완료된 중고 차량 구매, 자동차 중고 용품 거래, 최신 자동차 뉴스 및 정보 공유, 커뮤니티 기능을 제공하는 종합 자동차 서비스 플랫폼입니다. 회원 등급제 혜택과 1:1 채팅 기능을 통해 안전하고 편리한 거래와 소통을 지원합니다.

# 프로젝트 기간
> 2024.05.13 ~ 2024.06.13
> 

# 참여 인원
> |***김승연(조장)***|구승회|김찬윤|박근휘|함종태|윤찬호|
> |---|---|---|---|---|---|
> |차량 구매 및 현황|판매후기 게시판|구매후기 게시판|MyPage|자동차 뉴스 게시판|자유 게시판|
> |관심 차량|메인 페이지|공지사항|회원관리|이벤트 게시판|정보공유 게시판|
> |차량 판매 및 현황|Header,Footer 디자인|||고객문의 게시판||
> |차량 관리(관리자)|회원관리|||||
> |검수자 관리(관리자)|챗봇(FAQ)|||||
> |중고 거래||||||
> |채팅||||||
> 

# 사용 기술
> ## 협업 툴
 >> Notion (문서 관리)
> > 
 >> Kakao Oven (UI 설계)
> > 
 >> ERD Cloud (DB 설계)
> > 
>## IDE
 >> Eclipse
> > 
>## 프론트
 >> HTML
> > 
 >> CSS
> > 
 >> JAVASCRIPT
> >
 >> BootStrap
> >
 >> JSP
> >
 >> jQuery
> >
 >> Ajax
> > 
>## 백엔드
 >> JAVA8
> >
 >> JDBC
> >
 >> Dispatcher Servlet
> >
 >> WebSocket
> > 
>## DB
 >> Oracle
> >

# ERD
>
> <div>
>     <img src="https://github.com/Kimseungyeon98/carcarocean/assets/121033246/80f67b74-fa7d-4635-91a1-2ada82450be8" width="500px" height="300px">
> </div>

# 트러블슈팅
> 1. 깃허브 충돌 문제
>> 저를 포함한 프로젝트를 진행한 팀원 모두 협업하는 것이 처음이었기 때문에, 깃허브로 협업하는 방법을 몰랐습니다.
>> 따라서 PL역할을 담당하게 된 제가 깃허브를 공부하게 되었습니다.
>> 
>> 공부한 내용을 바탕으로 팀원 전부 협업이 처음이고 실수가 잦을 것으로 예상되어, GitHub-Flow 전략과 Git-Flow 전략 중 Git-Flow 전략을 선택했습니다.
>> **Main** 브랜치와 **Develop** 브랜치 그리고 5개의 개인 **Feature** 브랜치를 생성하여 각자의 브랜치에서 작업을 진행하게 도와줬습니다.
>>
>> 어느 정도의 기능 구현이 끝나면 Commit하고 Develop으로 PR을 보내게 했고, 매일 팀원들이 보낸 PR을 보며 코드를 리뷰하며 Merge까지 진행했습니다.
>> 추가로 일주일에 한번 씩은 Main에서 Merge하여 백업 겸으로 최신버전을 유지하게 했습니다.
>> 덕분에 로컬에서 작업한 후 Develop으로 Merge 하는 과정에서 해결하기 어려운 충돌이 발생했을때 Main의 최신 버전을 통해 원만하게 해결할 수 있었습니다.
>> 
> 2. 동시성 문제
>> 프로젝트에서 발생한 동시성 문제는 중고차량 거래 사이트의 특성 상, 차량 재고가 항상 1개라는 점에서 시작됐습니다.
>> 갑자기 든 호기심에 팀원 분들께 "음 차량을 동시에 구매해봅시다!" 라고 요청했고, 결과는 당연하게도 DB에서 에러가 발생했습니다.
>> 
>> 여러 사용자가 동시에 차량을 구매할 경우 데이터베이스에서 동시성 문제가 발생할 수 있었습니다.
>> 이 문제를 해결하기 위해 저는 DB 락, 트랜잭션, 그리고 동기화 메커니즘을 공부하였습니다.
>>
>> [티스토리 블로그: DB 락](https://tmddus3002.tistory.com/146)
>> 
>> 우선, **단일 서버** 환경이라는 점을 고려했습니다.
>> 그렇기 때문에 가장 효율적인 **동기화**와 **트랜잭션**을 걸어주는 두 가지 방법을 떠올렸습니다.
>> 하지만 **JSP**와 **DispatcherServlet**을 사용하는 프로젝트 특성상 코드를 트랜잭션으로 묶는 것이 쉽지 않았습니다.
>> 따라서 저는 **동기화**를 통해 **동시성 문제를 해결**하기로 결정했습니다.
>> 
>> 동기화를 적용하기 위해선, 차량 구매의 핵심 로직 부분을 **synchronized** 블록으로 감쌌습니다.
>> 이는 한 번에 하나의 스레드만이 해당 블록에 접근할 수 있도록 하여 데이터 일관성을 보장해줍니다.
>>
> 3. 채팅 구현 문제
>> 처음 채팅 기능을 구현할 때 우선 비동기적으로 채팅이 오고 갈 수 있고 채팅 내역이 갱신될 수 있게 하기 위해서 Ajax를 사용했습니다.
>>
>> 구현하고 완성하고 나니 한 가지 큰 단점이 보였습니다. 채팅을 보내야만 채팅창이 갱신된다는 점이었습니다.
>> 즉, Ajax는 비동기 통신 방식이지만 결국 HTTP 프로토콜을 사용하기 때문에 **StateLess**합니다.
>> 따라서 클라이언트가 서버에 어떤 요청을 해야지만 서버는 응답할 수 있습니다.
>>
>> 이를 해결하기 위해 JS에 setInterval 함수를 사용해 2초마다 채팅창을 갱신하도록 했습니다.
>> 그러나 채팅을 주고 받지 않더라도 계속해서 2초마다 채팅 정보를 읽어오기 때문에 채팅이 많아지게 되면 과부화 가능성이 있다고 판단했습니다. 또한 2초라는 간격이 UX를 굉장히 불편하게 만들었습니다.
>>
>> 새로운 해결책으로 WebSocket을 사용했습니다.
>> 앞서 말한 단점을 모두 보완할 수 있었습니다.
>> WebSocket은 **StateFul** 합니다. 따라서 요청을 보내지 않아도 응답을 받을 수 있었습니다.
>>
>> 작동 방식을 간단히 설명해보자면 채팅창에 참여하면 웹 소켓이 만들어집니다.
>> 그 후 채팅을 보낼 때 웹 소켓에 특정 신호를 보내고, 동시에 그 신호를 해당 웹 소켓 세션에 접속해 있는 모든 클라이언트들에게 뿌립니다.
>> 웹 소켓에서 신호를 받게 되면 채팅창을 갱신해줍니다.
>> 결과적으로 채팅을 보내거나 받을 때만 채팅창이 갱신되기 때문에 서버 부하를 줄이고 UX를 향상 시킨 **실시간 통신**이 이루어지게 됩니다.
>>

# 회고 / 느낀 점
> 첫 팀 프로젝트인데 팀장 역할을 맡게 되어 처음에는 부담스러웠습니다.
> 그러나 팀원들이 저를 믿고 따라주었기 때문에 보다 쉽게 리더십을 발휘할 수 있었습니다.
> 프로젝트는 1달 동안 진행되었지만, 처음부터 타이트한 일정을 잡아 2주 만에 기본적인 기능들을 구현 완료했습니다.
> 이로 인해 나머지 시간 동안은 각자 추가 기능 구현이나 미흡한 부분을 보완하는 데 집중할 수 있었습니다.
>
> 프로젝트 진행 도중에는 팀원들과 함께 공부하고 성장하기 위해 다양한 방법을 도입했습니다.
> 예를 들어, 깃허브에서 커밋할 때는 서로의 Pull Request를 merge하도록 하여 코드 리뷰를 강제하고, 강의에서 배운 내용에만 의존하지 않고 새로운 접근 방식으로 문제를 해결하도록 유도했습니다.
> 해결되지 않는 문제는 함께 자료를 찾아가며 공부하면서 진행했습니다.
>
> 이런 방식은 처음에는 힘들었지만, 프로젝트가 끝난 지금 돌이켜보면 모두가 빠르게 성장할 수 있도록 도와준 유익한 시간이었습니다.
> 팀원 각자가 자신의 역할을 수행하고 동시에 서로를 지원하며 함께 성장할 수 있는 좋은 경험이었습니다.
> 
