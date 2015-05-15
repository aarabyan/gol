<a id="start" onclick="startGame()" class="btn"><g:message code="default.button.start.label" default="Start"/></a>
<a id="stop" onclick="stopGame()" class="btn"><g:message code="default.button.stop.label" default="Stop Game"/></a>
<a id="next" onclick="nextStep()" class="btn"><g:message code="default.button.next.label" default="Next Step"/></a>
<g:message code="default.generation.label" default="Generation"/> <label id="generation">0</label>

<g:hiddenField name="nextStepUrl" value="${createLink(controller: 'game', action: 'nextStep')}"/>