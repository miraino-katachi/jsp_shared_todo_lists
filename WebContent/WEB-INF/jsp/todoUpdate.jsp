<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%-- ページのタイトルを設定する --%>
<%
	pageContext.setAttribute("title", "TODOリスト更新", PageContext.PAGE_SCOPE);
%>

<!DOCTYPE html>
<html lang="jp">

<%-- head部を読み込む --%>
<%@ include file="/WEB-INF/jsp/include/head.jsp"%>

<body>
	<div class="container-md">
		<%@ include file="/WEB-INF/jsp/include/navbar.jsp"%>
		<h1>${title}</h1>
		<div class="row">
			<div class="col-md">
				<form action="${root_path}/TodoUpdate" method="post">
					<input type="hidden" name="token" value="${token}">
					<input type="hidden" name="id" value="<c:out value="${todoItem.id}"/>">
					<div class="form-group">
						<label for="registrationDate">登録日</label>
						<input type="date"
							name="registrationDate" id="registrationDate"
							class="form-control<c:if test="${error.registrationDate!=null}"> is-invalid</c:if>"
							value="<c:out value="${todoItem.registrationDate}"/>">
						<span class="text-danger">${error.registrationDate}</span>
					</div>
					<div class="form-group">
						<label for="todoItem">TODO項目</label>
						<input type="text"
							name="todoItem" id="todoItem"
							class="form-control<c:if test="${error.todoItem!=null}"> is-invalid</c:if>"
							value="<c:out value="${todoItem.todoItem}"/>">
						<span class="text-danger">${error.todoItem}</span>
					</div>
					<div class="form-group">
						<label for="expirationDate">期限日</label>
						<input type="date"
							name="expirationDate" id="expirationDate"
							class="form-control<c:if test="${error.expirationDate!=null}"> is-invalid</c:if>"
							value="<c:out value="${todoItem.expirationDate}"/>">
						<span class="text-danger">${error.expirationDate}</span>
					</div>
					<div class="custom-control custom-checkbox mb-3">
						<input type="checkbox" class="custom-control-input"
							id="finishedDate" name="finishedDate" value="1"
							<c:if test="${todoItem.finishedDate != null}"> checked</c:if>>
						<label class="custom-control-label" for="finishedDate">完了にする</label>
					</div>
					<div class="custom-control custom-checkbox mb-3">
						<input type="checkbox" class="custom-control-input"
							id="isDeleted" name="isDeleted" value="1"
							<c:if test="${todoItem.isDeleted == 1}"> checked</c:if>>
						<label class="custom-control-label" for="isDeleted">削除する</label>
					</div>
					<button type="submit" class="btn btn-outline-success">更新</button>
				</form>
			</div>
		</div>
	</div>

	<%-- navbarで使用するJavaScript --%>
	<%@ include file="/WEB-INF/jsp/include/js.jsp"%>
</body>

</html>