<%@ page import="com.aram.gol.Board" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'board.label', default: 'Board')}"/>
    <title><g:message code="default.list.label" args="[entityName]"/></title>
</head>

<body>
<a href="#list-board" class="skip" tabindex="-1"><g:message code="default.link.skip.label"
                                                            default="Skip to content&hellip;"/></a>

<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
        <g:render template="createForm"/>

    </ul>
</div>

<div id="list-board" class="content scaffold-list" role="main">
    <h1><g:message code="default.list.label" args="[entityName]"/></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <table>
        <thead>
        <tr>

            <g:sortableColumn property="id" title="${message(code: 'board.id.label', default: 'Id')}"/>

            <g:sortableColumn property="rows" title="${message(code: 'board.rows.label', default: 'Rows')}"/>

            <g:sortableColumn property="columns" title="${message(code: 'board.columns.label', default: 'Columns')}"/>

        </tr>
        </thead>
        <tbody>
        <g:each in="${boardInstanceList}" status="i" var="boardInstance">
            <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">

                <td><g:link action="show"
                            id="${boardInstance.id}">${fieldValue(bean: boardInstance, field: "id")}</g:link></td>

                <td>${fieldValue(bean: boardInstance, field: "rows")}</td>
                <td>${fieldValue(bean: boardInstance, field: "columns")}</td>

            </tr>
        </g:each>
        </tbody>
    </table>

    <div class="pagination">
        <g:paginate total="${boardInstanceCount ?: 0}"/>
    </div>
</div>
</body>
</html>
