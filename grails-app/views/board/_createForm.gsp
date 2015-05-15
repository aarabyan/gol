<g:form url="[action: 'create']">
    <li><g:actionSubmit class="create" action="create"
                        value="${message(code: 'default.new.label', args: [entityName])}"/></li>
    <li><g:message code="game.rows.label" default="Rows"/> <g:field name="rows" type="number" value="${boardInstance?.rows ?: 20}" required=""/></li>
    <li><g:message code="game.columns.label" default="Columns"/> <g:field name="columns" type="number" value="${boardInstance?.columns ?: 20}" required=""/></li>
</g:form>