<!-- Roland Vecera
 18.4.2005
 Testpage für Display Tag Lib
 
 -->
 <%@ include file="/taglibs.jsp"%> 
 <title>Display all Channels </title>
 <style type="text/css">
    <!--
    table.somestyle {
     border: 1px solid black;
     border-collapse: collapse;
    }
    
    tr.odd {
     background-color: #E9E9E9;
     
    }
    th
    {
    background-color: #024588;
    color: WHITE; 
    }
    -->
    </style>
 
 <springbreak:stars value="${stars}" numberOfStars="5" classDiv="div" classImg="img" starFull="images/star/star.gif" starHalf="images/star/starhalf.gif" starEmpty="images/star/starempty.gif" />
 <springbreak:stars value="0" numberOfStars="5" classDiv="div" classImg="img" starFull="images/star/star.gif" starHalf="images/star/starhalf.gif" starEmpty="images/star/starempty.gif" />
 <springbreak:stars value="3" numberOfStars="5" classDiv="div" classImg="img" starFull="images/star/star.gif" starHalf="images/star/starhalf.gif" starEmpty="images/star/starempty.gif" />
 <springbreak:stars value="5" numberOfStars="5" classDiv="div" classImg="img" starFull="images/star/star.gif" starHalf="images/star/starhalf.gif" starEmpty="images/star/starempty.gif" />
 <springbreak:stars value="4" numberOfStars="5" classDiv="div" classImg="img" starFull="images/star/star.gif" starHalf="images/star/starhalf.gif" starEmpty="images/star/starempty.gif" />
 <springbreak:stars value="1.27" numberOfStars="5" classDiv="div" classImg="img" starFull="images/star/star.gif" starHalf="images/star/starhalf.gif" starEmpty="images/star/starempty.gif" />
 <springbreak:stars value="-1" numberOfStars="5" classDiv="div" classImg="img" starFull="images/star/star.gif" starHalf="images/star/starhalf.gif" starEmpty="images/star/starempty.gif" />
 
 <springbreak:stars value="6" numberOfStars="5" classDiv="div" classImg="img" starFull="images/star/star.gif" starHalf="images/star/starhalf.gif" starEmpty="images/star/starempty.gif" />
 <springbreak:stars value="1.27" numberOfStars="0" classDiv="div" classImg="img" starFull="images/star/star.gif" starHalf="images/star/starhalf.gif" starEmpty="images/star/starempty.gif" />
 <springbreak:stars value="" numberOfStars="5" classDiv="div" classImg="img" starFull="images/star/star.gif" starHalf="images/star/starhalf.gif" starEmpty="images/star/starempty.gif" />
 
 <display:table name="channels" class="somestyle" pagesize="3" requestURI="/showChannels.html">
  <display:column property="id" sortable="true" title="ID" class="idcol"/>
  <display:column property="title" sortable="true" title="Headline"/>
   <display:column property="description" title="Description"/>
  <display:column property="locationString"  title="URL"/>
  <display:column property="format" sortable="true" title="Format"/>
 
</display:table>
 
 