<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- navbar -->
<nav class="navbar navbar-expand-lg navbar-light bg-light mb-3">
    <a class="navbar-brand" href="${root_path}/Main">TODOリスト</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
        aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item in-active">
                <a class="nav-link" href="${root_path}/TodoRegister">TODOリスト追加</a>
            </li>
            <li class="nav-item dropdown in-active">
                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown"
                    aria-haspopup="true" aria-expanded="false">
                    <c:out value="${user.name}" />さん
                </a>
                <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                    <a class="dropdown-item" href="${root_path}/UserUpdate">会員情報修正</a>
                    <div class="dropdown-divider"></div>
                    <a class="dropdown-item" href="${root_path}/Logout">ログアウト</a>
                </div>
            </li>
        </ul>
        <form class="form-inline my-2 my-lg-0" action="${root_path}/Main" method="get">
            <input class="form-control mr-sm-2" type="search" placeholder="Search" aria-label="Search" name="key" id="key" value="<c:out value="${key}"/>">
            <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
        </form>
    </div>
</nav>
<!-- /navbar -->