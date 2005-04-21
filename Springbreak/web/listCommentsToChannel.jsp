<%@ include file="/taglibs.jsp"%> 
<title>Springbreak Comments</title> 
<p>Comment Information: </p> 

Following Comments to Channel <c:out value="${channel.title}"/>
<br/>
Average Rating is: <springbreak:stars value="${avgRating}" numberOfStars="5" classDiv="div" classImg="img" starFull="images/star/star.gif" starHalf="images/star/starhalf.gif" starEmpty="images/star/starempty.gif" />
<br/>
Total number of Reviews: <c:out value="${countReviews}"/>
<br/>

<c:forEach var="comment" items="${channel.comments}"> 
      <table>
      <tr>
      <th><c:out value="${comment.user.username}"/> - </th><th><c:out value="${comment.title}"/></th>
      </tr>
      <tr>
      <td>Rating:</td>
      <td><springbreak:stars value="${comment.stars}" numberOfStars="5" classDiv="div" classImg="img" starFull="images/star/star.gif" starHalf="images/star/starhalf.gif" starEmpty="images/star/starempty.gif" /></td>
      </tr>
      <tr>
      <td colspan="2">
      <c:out value="${comment.text}"/>
        <hr/>	
      </td>
      </tr>
      <table> 
    		
</c:forEach> 
                 
<!-- list Commments to a Channel
    +output number of Comments
    +output AVGReview
  -->
  
  
    