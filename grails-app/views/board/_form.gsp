<%@ page import="com.aram.gol.Board" %>

%{--<fieldset class="form">--}%

<table class="gameTable">
<g:each in="${0..boardInstance.rows - 1}" var="row">
    <g:set var="shift" value="${row * boardInstance.columns}"/>
    <tr>
        <g:each in="${0..boardInstance.columns - 1}" var="col">

            <g:set var="alive" value="${boardInstance.isAlive(row, col) ? "class=alive" : ""}"/>
            <td id="${shift + col}" ${alive}></td>
        </g:each>
    </tr>
</g:each>
<g:hiddenField name="rows" value="${boardInstance.rows}"/>
<g:hiddenField name="columns" value="${boardInstance.columns}"/>
<g:hiddenField name="aliveFields"/>
%{--</table>--}%
%{--<fieldset class="form">--}%
