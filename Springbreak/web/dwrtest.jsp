<html>

<head>
<%@ include file="/taglibs.jsp"%> 
<%@ taglib uri="http://ajaxtags.org/tags/ajax" prefix="ajax" %>
<script type='text/javascript' src='/springbreak/dwr/interface/feedSubscriberDAO.js'></script>
<script type='text/javascript' src='/springbreak/dwr/engine.js'></script>


</head>
<body>
asdf
<input type="button" onclick="feedSubscriberDAO.getFeedSubscriberByUser('vec', productCallback);" value="Get all products"/>
<script type="text/javascript">
  var productCallback = function(products) {
    var text = 'Products: <br><ul>';
    for(i = 0; i < products.length; i++) {
      text += '<li>';
      text += products[i].category.title;
      text += '</li>';
    }
    document.getElementById('productlist').innerHTML = text;
  }
</script>
<span id="productlist"></span>

</body>
</html>