<table border="0" cellspacing="0" cellpadding="0">
<tr><td>
From:
</td><td>
<select name="lowerBoundDay" size="1">
	<c:forEach var="i" begin="1" end="31" step="1">
		<c:if test="${i < 10}">
			<c:if test="${searchResult.lowerBoundDay == i}">
				<option value="0<c:out value="${i}" />" selected>0<c:out value="${i}"/></option>
			</c:if>
			<c:if test="${searchResult.lowerBoundDay != i}">
				<option value="0<c:out value="${i}" />">0<c:out value="${i}"/></option>
			</c:if>
		</c:if>
		<c:if test="${i >= 10}">
			<c:if test="${searchResult.lowerBoundDay == i}">
				<option value="<c:out value="${i}"/>" selected><c:out value="${i}"/></option>
			</c:if>
			<c:if test="${searchResult.lowerBoundDay != i}">
				<option value="<c:out value="${i}"/>"><c:out value="${i}"/></option>
			</c:if>
		</c:if>
	</c:forEach>
</select>
<select name="lowerBoundMonth" size="1">
	<c:forEach var="i" begin="1" end="12" step="1">
		<c:if test="${i < 10}">
			<c:if test="${searchResult.lowerBoundMonth == i}">
				<option value="0<c:out value="${i}"/>" selected>0<c:out value="${i}"/></option>
			</c:if>
			<c:if test="${searchResult.lowerBoundMonth != i}">
				<option value="0<c:out value="${i}"/>" >0<c:out value="${i}"/></option>
			</c:if>
		</c:if>
		<c:if test="${i >= 10}">
			<c:if test="${searchResult.lowerBoundMonth == i}">
				<option value="<c:out value="${i}"/>" selected><c:out value="${i}"/></option>
			</c:if>
			<c:if test="${searchResult.lowerBoundMonth != i}">
				<option value="<c:out value="${i}"/>"><c:out value="${i}"/></option>
			</c:if>
		</c:if>
	</c:forEach>
</select>
<select name="lowerBoundYear" size="1">
	<c:forEach var="i" begin="2001" end="2009" step="1">
		<c:if test="${searchResult.lowerBoundYear == i}">
			<option value="<c:out value="${i}"/>" selected><c:out value="${i}"/></option>#
		</c:if>
		<c:if test="${searchResult.lowerBoundYear != i}">
			<option value="<c:out value="${i}"/>"><c:out value="${i}"/></option>#
		</c:if>
	</c:forEach>
</select>
</td></tr>
<tr><td>
To:
</td><td>
<select name="upperBoundDay" size="1">
	<c:forEach var="i" begin="1" end="31" step="1">
		<c:if test="${i < 10}">
			<c:if test="${searchResult.upperBoundDay == i}">
				<option value="0<c:out value="${i}" />" selected>0<c:out value="${i}"/></option>
			</c:if>
			<c:if test="${searchResult.upperBoundDay != i}">
				<option value="0<c:out value="${i}" />">0<c:out value="${i}"/></option>
			</c:if>
		</c:if>
		<c:if test="${i >= 10}">
			<c:if test="${searchResult.upperBoundDay == i}">
				<option value="<c:out value="${i}"/>" selected><c:out value="${i}"/></option>
			</c:if>
			<c:if test="${searchResult.upperBoundDay != i}">
				<option value="<c:out value="${i}"/>"><c:out value="${i}"/></option>
			</c:if>
		</c:if>
	</c:forEach>
</select>
<select name="upperBoundMonth" size="1">
	<c:forEach var="i" begin="1" end="12" step="1">
		<c:if test="${i < 10}">
			<c:if test="${searchResult.upperBoundMonth == i}">
				<option value="0<c:out value="${i}"/>" selected>0<c:out value="${i}"/></option>
			</c:if>
			<c:if test="${searchResult.upperBoundMonth != i}">
				<option value="0<c:out value="${i}"/>" >0<c:out value="${i}"/></option>
			</c:if>
		</c:if>
		<c:if test="${i >= 10}">
			<c:if test="${searchResult.upperBoundMonth == i}">
				<option value="<c:out value="${i}"/>" selected><c:out value="${i}"/></option>
			</c:if>
			<c:if test="${searchResult.upperBoundMonth != i}">
				<option value="<c:out value="${i}"/>"><c:out value="${i}"/></option>
			</c:if>
		</c:if>
	</c:forEach>
</select>
<select name="upperBoundYear" size="1">
	<c:forEach var="i" begin="2001" end="2009" step="1">
		<c:if test="${searchResult.upperBoundYear == i}">
			<option value="<c:out value="${i}"/>" selected><c:out value="${i}"/></option>#
		</c:if>
		<c:if test="${searchResult.upperBoundYear != i}">
			<option value="<c:out value="${i}"/>"><c:out value="${i}"/></option>#
		</c:if>
	</c:forEach>
</select>
</td></tr>
<tr><td>
</table>