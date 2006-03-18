<head>
<title>Event Google</title>
<script language="javascript" type='text/javascript' src='jscripts/showHideVisibility.js'>
</script> 
</head>
<body>
<%@ include file="/head.jsp"%>
<center><h2>> Help <</h2></center>
<br/>
<table border="0" width="80%" align="center">
	<tr valign="top">
		<td bgcolor="#E5ECF9">
			<table width="100%" cellspacing="0" cellpadding="5" style="border-top:solid #3366CC 1px">
				<tr>
					<td bgcolor="#E5ECF9">
						<b>Query Syntax</b>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td>
			<table border="0">
				<tr valign="top">
					The basic query grammar is built in BNF form:<br/><br/>
					Query  ::= ( Clause )*<br/>
					Clause ::= ["+", "-"] [&lt;TERM&gt; ":"] ( &lt;TERM&gt; | "(" Query ")" )<br/>
					<br/>
					A "+" and "-" as a prefix signs that the clause is required in the result set or prohibited.<br/>
					A clause can be a term or a nested query enclosed by parentheses.
					<br/>
					<br/><br/><br/>
					<center><a href="http://lucene.apache.org/java/docs/queryparsersyntax.html">Take a look at a detailed Query Tutorial</a></center>
				</tr>
			</table>
		</td>
	</tr>
</table>


		