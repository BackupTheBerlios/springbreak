<%@ include file="/taglibs.jsp"%> 

<cewolf:chart 
    id="line" 
    type="line" 
    xaxislabel="Number Subscribers" 
    yaxislabel="Week of Year"> 
   <cewolf:colorpaint 
            color="#FAFAFA" /> 
    <cewolf:data> 
        <!-- here I use the graph object I ve put in the request in my controller class--> 
        <cewolf:producer id="data"/> 
    </cewolf:data> 
    <cewolf:chartpostprocessor id="data" />
</cewolf:chart> 
<cewolf:img chartid="line" renderer="cewolf" width="600" height="400"> 
</cewolf:img>