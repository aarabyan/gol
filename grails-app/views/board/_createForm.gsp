<g:form url="[action: 'create']">
    <li><g:actionSubmit class="create" action="create"
                        value="${message(code: 'default.new.label', args: [entityName])}"/></li>
    <li>Rows <g:field name="rows" type="number" value="20" required=""/></li>
    <li>Columns <g:field name="columns" type="number" value="20" required=""/></li>
</g:form>