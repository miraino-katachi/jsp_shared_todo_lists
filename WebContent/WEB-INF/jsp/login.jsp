<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%-- ページのタイトルを設定する --%>
<%
	pageContext.setAttribute("title", "会員ログイン", PageContext.PAGE_SCOPE);
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
						<c:if test="${error != null}">
							<div class="alert alert-danger" role="alert">${error}</div>
						</c:if>
						<c:if test="${success != null}">
							<div class="alert alert-success" role="alert">${success}</div>
						</c:if>
						<form action="${root_path}/Login" method="post">
							<input type="hidden" name="token" value="${token}">
							<div class="form-group">
								<label for="email">E-mailアドレス</label>
								<input type="text"
									name="email" id="email" class="form-control"
									value="<c:out value="${user.email}"/>">
							</div>
							<div class="form-group">
								<label for="password">パスワード</label>
								<input type="password"
									name="password" id="password" class="form-control"
									value="<c:out value="${user.password}"/>">
							</div>
							<button type="submit" class="btn btn-outline-success">ログイン</button>
						</form>
					</div>
				</div>
				<a href="${root_path}/UserRegister">新規会員登録はこちら</a>
			</div>
			<div class="col-md-3"></div>
		</div>
	</div>
</body>

</html>