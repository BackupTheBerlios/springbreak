
<script type="text/javascript">
<!--
var req;

function validate() {
    var red = document.getElementById("red");
    var green = document.getElementById("green");
    var blue = document.getElementById("blue");
    
    var url = "./ajaxtest.html?red=" + escape(red.value)+"&green="+escape(green.value)+"&blue="+escape(blue.value);
    //alert (url);
    if (window.XMLHttpRequest) {
        req = new XMLHttpRequest();
    } else if (window.ActiveXObject) {
        req = new ActiveXObject("Microsoft.XMLHTTP");
    }
    req.open("GET", url, true);
    req.onreadystatechange = callback;
    req.send(null);
    }
    
function callback() {
    if (req.readyState == 4) {
        if (req.status == 200) {
            var message = req.responseXML.getElementsByTagName("message")[0];
            var modElement = document.getElementById("color");
            //if (message != "notvalid")
            //	alert ("NO VALID HTML COLOR");
            //else
            //	alert (message.childNodes[0].nodeValue);
           // 	alert (modElement.getAttribute("bgcolor"));
            	modElement.setAttribute("bgcolor",message.childNodes[0].nodeValue);
        }
    }
}

//-->
</script>



<%@ include file="/taglibs.jsp"%> 
<title>Springbreak Add Category</title> 



<!--
Roland Vecera
Input für AddCategoryController
-->




<spring:bind path="category.*">
<c:if test="${not empty status.errorMessages}"> 
<div class="error">
	<c:forEach items="${status.errorMessages}"
		var="errorMessage">
		<font class="error">
			<c:out value="${errorMessage}"/><br>
		</font>
	</c:forEach>
</div>
</c:if>
</spring:bind>

<p>
<b>Add a new category to your menu!</b><br/><br/>

Choose a title and a color for your category. 
<br/>Note: Category doesn't show up, until
you add a channel to this category!
</p>


<form name="category" method="post" action="<c:url value="/addCategory.html"/>" > 
	<fieldset style="width: 70%">
    <legend>Add new Category</legend>
    
	<table> 
		
		<tr> 
			<td>
				Title: 
			</td> 
			<td colspan="2" > 
				
				<spring:bind path="category.title">
	               <input size="25" maxlength="100" type="text" name="title" value=""/>
	              
	            </spring:bind>
	            <br/>
			</td>
			
		</tr> 
		<tr> 
			<td>
				Color:
			</td> 
			<td> 
				<table>
				<tr>
					<td>
						<spring:bind path="category.red">
	               		<input size="3" maxlength="3" type="text" name="red" id="red" value="0" onblur="validate();"/>
	            		</spring:bind>
	            	</td>
	            	<td>R</td>
	            </tr>
	            <tr>
	            	<td>
	            		<spring:bind path="category.green">
	               		<input  size="3" maxlength="3" type="text" name="green" id="green" value="0" onblur="validate();"/>
	            	</spring:bind>
	            	</td>
	            	<td>G</td>
	            </tr>
	            <tr>
	            <td>
	            	<spring:bind path="category.blue">
	               	<input  size="3"  maxlength="3" type="text" name="blue" id="blue" value="0" onblur="validate();"/>
	            	</spring:bind>
	            </td>
	            <td>B</td>
	            </tr>
	            </table>
			</td>
			<td id="color" align="center" bgcolor="WHITE">
			Click for color-preview!
		</td>
			
		</tr>
		
		
		
		<tr> 
		<td><br/><input type="submit" class="button" name="save" value="Save"/></td>
		</tr>
			
	</table> 
	</fieldset>
    
</form> 

<content tag="underground">

<strong><fmt:message key="common.newsagg"/></strong>
</content>
