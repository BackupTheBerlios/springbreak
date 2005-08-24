
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
<p>Fill in Category </p> 


<!--
Roland Vecera
Input für AddCategoryController


TODO: was macht onsubmit="return validateChannel(this)
-->

<form name="category" method="post" action="<c:url value="/addCategory.html"/>" > 
	<table> 
		
		<tr> 
			<td>
				Title 
			</td> 
			<td> 
				
				<spring:bind path="category.title">
	               <input maxlength="100" type="text" name="title" value=""/>
	              
	            </spring:bind>
			</td>
			
		</tr> 
		<tr> 
			<td>
				Color
			</td> 
			<td> 
				
				<spring:bind path="category.red">
	               <input maxlength="3" type="text" name="red" id="red" value="0" onblur="validate();"/>
	            </spring:bind>
	            <spring:bind path="category.green">
	               <input maxlength="3" type="text" name="green" id="green" value="0" onblur="validate();"/>
	            </spring:bind>
	            <spring:bind path="category.blue">
	               <input maxlength="3" type="text" name="blue" id="blue" value="0" onblur="validate();"/>
	            </spring:bind>
			</td>
			
		</tr>
		<tr> 
		<td id="color" bgcolor="WHITE">
			Click for color-preview!
		</td>
		</tr>
		
		
		<tr> 
		<td><input type="submit" class="button" name="save" value="Save"/></td>
		</tr>
			
	</table> 
</form> 
<content tag="underground">

<strong><fmt:message key="common.newsagg"/></strong>
</content>
