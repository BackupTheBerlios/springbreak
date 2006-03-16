function hidediv(pass) {
var divs = document.getElementsByTagName('div');
for(i=0;i<divs.length;i++){
if(divs[i].id.match(pass)){//if they are 'see' divs
if (document.getElementById) // DOM3 = IE5, NS6
divs[i].style.display="none";// show/hide
else
if (document.layers) // Netscape 4
document.layers[divs[i]].display = 'none';
else // IE 4
document.all.hideshow.divs[i].visibility = 'none';
}
}
}

function showdiv(pass) {
var divs = document.getElementsByTagName('div');
for(i=0;i<divs.length;i++){
if(divs[i].id.match(pass)){
if (document.getElementById)
divs[i].style.display="block";
else
if (document.layers) // Netscape 4
document.layers[divs[i]].display = 'block';
else // IE 4
document.all.hideshow.divs[i].visibility = 'block';
}
}
} 