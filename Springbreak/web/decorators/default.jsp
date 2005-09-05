<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
        
<%@ include file="/taglibs.jsp"%>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
    <title><decorator:title default="Equinox"/></title>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <c:set var="ctx" value="${pageContext.request.contextPath}" scope="request"/>
    <link href="${ctx}/styles/global.css" type="text/css" rel="stylesheet"/>
    
    <link href="${ctx}/styles/ajaxtags-sample.css" type="text/css" rel="stylesheet"/>
    <link href="${ctx}/images/favicon.ico" rel="SHORTCUT ICON"/>
    <decorator:head/>
</head>
<body>
<div id="container">
	<div id="head">
		Springbreak - RSS AGGREGATOR
	</div>
    <div id="menuleft">
    	<div id="menufeeds">
    		<%@ include file="/menu.jsp" %>
    	</div>
    	<div id="menuoptions">
         <decorator:getProperty property="page.menubar"/>
         <%@ include file="/menumanage.jsp" %>
        </div>
	</div>
     <div id="content">
            <!-- %@ include file="/messages.jsp"% -->
            <decorator:body />
     </div>
</div>
</body>
</html>
