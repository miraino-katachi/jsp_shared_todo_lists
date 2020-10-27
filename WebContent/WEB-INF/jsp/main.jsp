<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%-- ページのタイトルを設定する --%>
<%
	pageContext.setAttribute("title", "TODOリスト", PageContext.PAGE_SCOPE);
%>

<!DOCTYPE html>
<html lang="jp">

<%-- head部を読み込む --%>
<%@ include file="/WEB-INF/jsp/include/head.jsp"%>

<body>
	<div class="container-md">
		<%-- navbar --%>
		<%@ include file="/WEB-INF/jsp/include/navbar.jsp"%>

		<!--         <h1 class="display-3">${title}</h1> -->
		<div class="table-responsive">
			<table class="table table-borderd table-hover">
				<thead>
					<tr>
						<th>登録日</th>
						<th>TODO項目</th>
						<th>期限日</th>
						<th>完了日</th>
						<th></th>
					</tr>
				</thead>
				<tbody>
				<c:forEach var="item" items="${items}">
					<tr
						<c:choose>
							<c:when test="${item.expirationDate.before(today)}">
								class="alert alert-danger"
							</c:when>
							<c:when test="${item.finishedDate != null}">
								class="finished"
							</c:when>
						</c:choose>
					>
						<fmt:formatDate var="date" value="${item.registrationDate}" pattern="yyyy/MM/dd"/>
						<td><c:out value="${date}"/></td>
						<td><c:out value="${item.todoItem}"/></td>
						<fmt:formatDate var="date" value="${item.expirationDate}" pattern="yyyy/MM/dd"/>
						<td><c:out value="${date}"/></td>
						<fmt:formatDate var="date" value="${item.finishedDate}" pattern="yyyy/MM/dd"/>
						<td><c:out value="${date}"/></td>
						<td>
							<form action="${root_path}/TodoUpdate" method="get">
								<input type="hidden" name="id" value="<c:out value="${item.id}"/>">
								<input type="submit" value="更新" class="btn btn-outline-success">
							</form>
						</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</div>

	</div>

	<%-- navbarで使用するJavaScript --%>
	<%@ include file="/WEB-INF/jsp/include/js.jsp"%>
</body>

</html>