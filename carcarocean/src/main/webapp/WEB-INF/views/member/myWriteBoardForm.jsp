<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri = "http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>내가 쓴 글</title>
</head>
<jsp:include page="/WEB-INF/views/common/header.jsp" />
<body>
	<div class="container">
		<div class="row">
			<c:set var="sub_title" value="${member.mem_name}님 글"
				scope="request" />
			<jsp:include page="/WEB-INF/views/member/myPageMenu.jsp" />
		<main class="col-md-10 pt-5 pb-5">
                <div class="mypage-div">
             	    <h3>자유게시판</h3><a href = "${pageContext.request.contextPath}/board/list.do">더보기..</a>
                    <table class="table">
                        <thead>
                            <tr>
                                <th>#</th>
                                <th>제목</th>
                                <th>작성일</th>
                                <th>수정일</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="board" items="${list}">
                                <tr>
                                    <td width = "80px">${board.board_num}</td>
                                    <td width = "500px">${board.board_title}</td>
                                    <td>${fn:substring(board.board_reg,0,10)}</td>
                                    <c:if test = "${!empty board.board_modify}">
									<td>${fn:substring(board.board_modify,0,10)}</td>
									</c:if>
									<c:if test = "${empty board.board_modify}">
                                    <td>데이터없음</td>
                                    </c:if>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                	<h3>구매후기</h3><a href = "${pageContext.request.contextPath}/s_re/list.do">더보기..</a>
                    <table class="table">
                        <thead>
                            <tr>
                                <th>#</th>
                                <th>제목</th>
                                <th>작성일</th>
                                <th>수정일</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="s_re" items="${slist}">
                                <tr>
                                    <td width = "80px">${s_re.s_re_num}</td>
                                    <td width = "500px">${s_re.s_re_title}</td>
                                    <td>${fn:substring(s_re.s_re_reg,0,10)}</td>
                                    <c:if test = "${!empty s_re.s_re_modify}">
									<td>${fn:substring(s_re.s_re_modify,0,10)}</td>
									</c:if>
									<c:if test = "${empty s_re.s_re_modify}">
                                    <td>데이터없음</td>
                                    </c:if>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                    <h3>판매후기</h3><a href = "${pageContext.request.contextPath}/b_re/list.do">더보기..</a>
                    <table class="table">
                        <thead>
                            <tr>
                                <th>#</th>
                                <th>제목</th>
                                <th>작성일</th>
                                <th>수정일</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="b_re" items="${blist}">
                                <tr>
                                    <td width = "80px">${b_re.b_re_num}</td>
                                    <td width = "500px">${b_re.b_re_title}</td>
                                    <td>${fn:substring(b_re.b_re_reg,0,10)}</td>
                                    <c:if test = "${!empty b_re.b_re_modify}">
									<td>${fn:substring(b_re.b_re_modify,0,10)}</td>
									</c:if>
									<c:if test = "${empty b_re.b_re_modify}">
                                    <td>데이터없음</td>
                                    </c:if>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </main>
		</div>
	</div>
	<jsp:include page="/WEB-INF/views/common/footer.jsp" />
</body>
</html>