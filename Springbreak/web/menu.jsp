<script type="text/javascript">
function manage(val) {
 
 if (document.getElementById("menu_category"+val).style.display =='inline')
  {
  document.getElementById("menu_category"+val).style.display ='none';
  document.getElementById("menu_category"+val).style.visibility ='hidden';
  }
 else
  { 
  document.getElementById("menu_category"+val).style.display ='inline';
  document.getElementById("menu_category"+val).style.visibility ='visible';
  }
  
  var url = "./storeOpenElements.html?cat_id=" + escape(val);
    if (window.XMLHttpRequest) {
        req = new XMLHttpRequest();
    } else if (window.ActiveXObject) {
        req = new ActiveXObject("Microsoft.XMLHTTP");
    }
    req.open("GET", url, true);
    req.onreadystatechange = callbackStore;
    req.send(null);
    }
function swap (imageElement)
{
//alert (imageElement.src.substring(imageElement.src.length-9, imageElement.src.length));
 if (imageElement.src.substring(imageElement.src.length-9,imageElement.src.length) =='/open.gif')
 	imageElement.src='./images/menu/close.gif';
 else
    imageElement.src='./images/menu/open.gif';
} 
 
function callbackStore() {
    if (req.readyState == 4) {
        if (req.status == 200) {
            var message = req.responseXML.getElementsByTagName("message")[0];
            //do nothing!
        }
    }
}



</script>

<script type="text/javascript">
<!--
var req;

function validate() {
    var view = document.getElementById("sview");
   // alert(view.value);
    var url = "./storeViewInSession.html?view=" + escape(view.value);
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
        	//alert("da");
            var message = req.responseXML.getElementsByTagName("message")[0];
            //alert (message);
            //var modElement = document.getElementById("sviewOpt"+message);
            //if (message != "notvalid")
            //	alert ("NO VALID HTML COLOR");
            //else
            //	alert (message.childNodes[0].nodeValue);
           // 	alert (modElement.getAttribute("bgcolor"));
            //	modElement.selected = true;
        }
    }
}

//-->
</script>

<%
   java.util.HashMap openElements = (java.util.HashMap)request.getSession().getValue("openElements");
   %>

<span>Show me </span>
<select id="sview" name="sview" size="1" onchange="validate();">
<option id="sviewOpt0" value="0" <c:if test="${sessionScope.view == 0}">selected</c:if>>Since last visit</option>
<option id="sviewOpt10" value="10" <c:if test="${sessionScope.view == 10}">selected</c:if>>10 Items</option>
<option id="sviewOpt30"value="30" <c:if test="${sessionScope.view == 30}">selected</c:if>>30 Items</option>
<option id="sviewOpt50" value="50" <c:if test="${sessionScope.view == 50}">selected</c:if>>50 Items</option>
</select>

<c:set var="lastcategory" value=""/>
<br/>
<br/>
<a class="category" class="menu_channel" href="main.html">All Channels</a>
<c:forEach var="fs" items="${sessionScope.feedSubscriberSession.feedSubscribers}"> 
      
      <c:set var="temp" scope="page" value="${fs.category.id}"/>
      
      <c:if test="${fs.category.title != lastcategory}"> 
      	<c:if test="${lastcategory != ''}">
      			</div>
      		</div>
      	</c:if>
      		
      		<div style="position: relative; margin-top: 5px; padding: 3px; background:${fs.category.htmlColor}" >
      		   <a class="category" href="main.html?category_id=${fs.category.id}">${fs.category.title}</a>
      		   
<% //This is the solution for the KLAPPBAR Menu to remember state
	//in openElements, the open parts are stored;
	//so check for every categoryID if its in this HashMap
	//Trick: store ${fs.category.id} with <c:set> to be able to access it with JSP!
	if ((openElements != null) && (openElements.containsKey(((Integer)pageContext.getAttribute("temp")).toString())))
   {
%>
<span style="position:absolute; top: 3px; right: 3px;"><image src="./images/menu/close.gif" alt="X" onclick="manage(${fs.category.id});swap(this)"/></span>
<div style="display:inline; visibility: visible;" id="menu_category${fs.category.id}">
<%    
  } 
  else
  {   
%>
<span style="position:absolute; top: 3px; right: 3px;"><image src="./images/menu/open.gif" alt="O" onclick="manage(${fs.category.id});swap(this)"/></span>
<div style="display:none; visibility: hidden;" id="menu_category${fs.category.id}">
<%} %>

      			
      </c:if>
      
      		<div style="margin-left: 7px;" id="menu_channel${fs.channel.id}">
      		
      			<a class="menu_channel" href="main.html?channel_id=${fs.channel.id}">${fs.channel.title}</a>
      			<a class="menu_channel" href="showCommentsToChannel.html?id=<c:out value='${fs.channel.id}'/>"><img src="./images/menu/info.gif" alt="I" /></a>
      			<a class="menu_channel" href="addComment.html?id=<c:out value='${fs.channel.id}'/>"><img src="./images/menu/comment.gif" alt="C" /></a>
            	   
      		</div>
    <c:set var="lastcategory" value="${fs.category.title}"/>  
</c:forEach>  
			</div>
		</div>
