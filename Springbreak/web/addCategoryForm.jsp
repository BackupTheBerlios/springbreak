<head>
<title>Add Category</title> 

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


<link type="text/css" rel="StyleSheet" href="./styles/rgbsliders/rgbsliders.css" />

<script type="text/javascript" src="./slider_js/range.js"></script>
<script type="text/javascript" src="./slider_js/timer.js"></script>
<script type="text/javascript" src="./slider_js/slider.js"></script>

</head>
<body>

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
			<td > 
				
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
				<div style="width: 300px; height: 100px">
				   <table class="color-picker" cellspacing="2" cellpadding="0" border="0">
						<col style="width: 40px" />
						<col style="" />
						<col style="width: 10px" />
						<col style="width: 50px" />
						<tr>
							<td><label for="red-slider">Red:</label></td>
							<td>
								<div class="slider" id="red-slider" tabIndex="1">
									<spring:bind path="category.red">
										<input class="slider-input" name="${status.expression}" value="${status.value}" id="red-slider-input" />
									</spring:bind>
								</div>
							</td>
							<td><input id="red-input" maxlength="3" tabIndex="2" /></td>
							<td rowspan="3" id="color-result"></td>
						</tr>
						<tr>
							<td><label for="green-slider">Green:</label></td>
							<td>
								<div class="slider" id="green-slider" tabIndex="3">
									<spring:bind path="category.green">
										<input class="slider-input" name="${status.expression}" value="${status.value}" id="green-slider-input" />
									</spring:bind>
								</div>
							</td>
							<td><input id="green-input" maxlength="3" tabIndex="4" /></td>
						</tr>
						<tr>
							<td><label for="blue-slider">Blue:</label></td>
							<td>
								<div class="slider" id="blue-slider" tabIndex="5">
									<spring:bind path="category.blue">
										<input class="slider-input" name="${status.expression}" value="${status.value}" id="blue-slider-input" />
									</spring:bind>
								</div>
							</td>
							<td><input id="blue-input" maxlength="3" tabIndex="6" /></td>
						</tr>
					</table>
				</div>
			</td>
			
		</tr>
		
		
		
		<tr> 
		<td><br/><input type="submit" class="button" name="save" value="Save"/></td>
		</tr>
			
	</table> 
	</fieldset>
    
</form> 
















<script type="text/javascript">

// init code
var r = new Slider(document.getElementById("red-slider"), document.getElementById("red-slider-input"));
r.setMaximum(255);
var g = new Slider(document.getElementById("green-slider"), document.getElementById("green-slider-input"));
g.setMaximum(255);
var b = new Slider(document.getElementById("blue-slider"), document.getElementById("blue-slider-input"));
b.setMaximum(255);

var ri = document.getElementById("red-input");
ri.onchange = function () {
	r.setValue(parseInt(this.value));
};

var gi = document.getElementById("green-input");
gi.onchange = function () {
	g.setValue(parseInt(this.value));
};

var bi = document.getElementById("blue-input");
bi.onchange = function () {
	b.setValue(parseInt(this.value));
};

r.onchange = g.onchange = b.onchange = function () {
	var cr = document.getElementById("color-result");
	cr.style.backgroundColor = "rgb(" + r.getValue() + "," + 
								g.getValue() + "," + 
								b.getValue() + ")";
	ri.value = r.getValue();
	gi.value = g.getValue();
	bi.value = b.getValue();
	
	if (typeof window.onchange == "function")
		window.onchange();
};

r.setValue(128);
g.setValue(128);
b.setValue(128);

// end init

function setRgb(r, g, b) {
	r.setValue(r);
	g.setValue(g);
	b.setValue(b);
}

function getRgb() {
	return {
		r:	r.getValue(),
		g:	g.getValue(),
		b:	b.getValue()
	};
}

function fixSize() {
	r.recalculate();
	g.recalculate();
	b.recalculate();
}

window.onresize = fixSize;

fixSize();


</script>




</body>
<content tag="underground">

<strong><fmt:message key="common.newsagg"/></strong>
</content>

