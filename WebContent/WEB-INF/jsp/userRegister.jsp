<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%-- ページのタイトルを設定する --%>
<%
	pageContext.setAttribute("title", "新規会員登録", PageContext.PAGE_SCOPE);
%>

<!DOCTYPE html>
<html lang="jp">

<%-- head部を読み込む --%>
<%@ include file="/WEB-INF/jsp/include/head.jsp"%>

<body>
	<div class="container-md">
		<div class="row">
			<div class="col-md-3"></div>
			<div class="col-md-6">
				<div class="card my-3">
					<div class="card-header">${title}</div>
					<div class="card-body">
						<c:if test="${db_error != null}">
							<div class="alert alert-danger" role="alert">${db_error}
							</div>
						</c:if>
						<form action="${root_path}/UserRegister" method="post">
							<div class="form-group">
								<label for="email">E-mailアドレス</label>
								<input type="text"
									name="email" id="email"
									class="form-control<c:if test="${error.email!=null}"> is-invalid</c:if>"
									value="<c:out value="${user.email}"/>">
								<c:if test="${error.email!=null}">
									<span class="text-danger">${error.email}</span>
								</c:if>
							</div>
							<div class="form-group">
								<label for="password">パスワード</label>
								<input type="password"
									name="password" id="password"
									class="form-control<c:if test="${error.password!=null}"> is-invalid</c:if>"
									value="<c:out value="${user.password}"/>">
								<c:if test="${error.password!=null}">
									<span class="text-danger">${error.password}</span>
								</c:if>
							</div>
							<div class="form-group">
								<label for="userName">ニックネーム</label> <input type="text"
									name="name" id="name"
									class="form-control<c:if test="${error.name!=null}"> is-invalid</c:if>"
									value="<c:out value="${user.name}"/>">
								<c:if test="${error.name!=null}">
									<span class="text-danger">${error.name}</span>
								</c:if>
							</div>
							<button type="submit" class="btn btn-outline-success">登録</button>
						</form>
					</div>
				</div>
				<a href="${root_path}/Login">ログインページはこちら</a>
			</div>
			<div class="col-md-3"></div>
		</div>
	</div>
</body>

</html>