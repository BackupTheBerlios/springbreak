<head>
	<title>Show Information</title>
	<%@ include file="/taglibs.jsp"%> 
</head>
<body>

<p><b>Further Information</b><br>
This page gives you some further information about the RSS-Channel.
</p> 

Information about <a class="channellocation" href="${channel.locationString}" target="blank">${channel.title}</a>

<table style="width: 70%; background:#E0E0E0; margin: 5px;">
	<tr>
		<td>
			Title:
		</td>
		<td>
			${channel.title}
		</td>
	</tr>
	<tr>
		<td>
			Website:
		</td>
		<td>
			<a class="channellocation" href="${channel.siteString}" target="blank">${channel.siteString}</a>
		</td>
	</tr>
	<tr>
		<td>
			RSS-Channel:
		</td>
		<td>
			<a class="channellocation" href="${channel.locationString}" target="blank">${channel.locationString}</a>
		</td>
	</tr>
	<tr>
		<td>
			Publisher:
		</td>
		<td>
			${channel.publisher}
		</td>
	</tr>
	<tr>
		<td>
			Creator:
		</td>
		<td>
			${channel.creator}
		</td>
	</tr>
	<tr>
		<td>
			Copyright:
		</td>
		<td>
			${channel.copyright}
		</td>
	</tr>
	
	<tr>
		<td>
			Description:
		</td>
		<td>
			${channel.description}
		</td>
	</tr>
	<tr>
		<td>
			Format:
		</td>
		<td>
			${channel.format}
		</td>
	</tr>
	<tr>
		<td>
			Language:
		</td>
		<td>
			${channel.language}
		</td>
	</tr>
	<tr>
		<td colspan="2"><a class="channellocation" href="showSubscribersStatistic.html?channel_id=${channel.id}&numberWeeks=30">Show statistics to RSS-Channel '${channel.title}'</a> </td>
	</tr>
</table>
<br/>
<p><b>Following comments where done by our users:</b></p>

<table style="width: 70%">
<tr>
<td>
Average Rating is: <springbreak:stars value="${avgRating}" numberOfStars="5" classDiv="div" classImg="img" starFull="images/star/star.gif" starHalf="images/star/starhalf.gif" starEmpty="images/star/starempty.gif" />
</td>
<td style="vertical-align: top">
Total number of Reviews: <c:out value="${countReviews}"/>
</td>
</tr>
</table>

<c:forEach var="comment" items="${channel.comments}" varStatus="status"> 

      <table style="width: 70%;  background: #E0E0E0; margin-bottom: 3px;">
      <tr align="left">
      	<th style="vertical-align:top; max-width:100px "><springbreak:stars value="${comment.stars}" numberOfStars="5" classDiv="stars" classImg="img" starFull="images/star/star.gif" starHalf="images/star/starhalf.gif" starEmpty="images/star/starempty.gif" /></th>
      	<th style="width: 100%"> ${comment.title}</th>
      </tr>
      <tr>
      	<td colspan="2">${comment.user.username} on ${comment.addedDate}</td>
      </tr>

      <tr>
      <td colspan="2">
      	<div class="description"><c:out value="${comment.text}"/></div>
      
      </td>
      </tr>
      <table> 
		
</c:forEach> 
                 
</body>
  
  
    