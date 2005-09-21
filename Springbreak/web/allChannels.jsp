<head>
 <%@ include file="/taglibs.jsp"%> 
 <title>Display all Channels </title>
</head> 
<body> 
 <display:table name="channels" class="adminstyle" pagesize="20" requestURI="/showChannels.html">
  <display:column property="id" sortable="true" title="ID" class="idcol"/>
  <display:column property="title" sortable="true" title="Headline"/>
  <display:column property="description" title="Description"/>
  <display:column property="locationString" sortable="true" title="URL"/>
  <display:column property="format" sortable="true" title="Format"/>
  <display:column href="showCommentsToChannel.html" paramId="id" paramProperty="id">show stats</display:column>
</display:table>

</body> 
 