<%@ include file="/taglibs.jsp"%> 

<cewolf:chart 
    id="line" 
    type="line" 
    xaxislabel="Week of Year" 
    yaxislabel="Number of Subscribers"> 
   <cewolf:colorpaint 
            color="#C2C2C2" /> 
    <cewolf:data> 
        <!-- here I use the graph object I ve put in the request in my controller class--> 
        <cewolf:producer id="dataSubscribers"/> 
    </cewolf:data> 
    <cewolf:chartpostprocessor id="dataSubscribers" />
</cewolf:chart> 
<cewolf:img chartid="line" renderer="cewolf" width="500" height="340"> 
</cewolf:img>

<br/>
<br/>

<cewolf:chart 
    id="line" 
    type="line" 
    xaxislabel="Week of Year" 
    yaxislabel="Number of Postings"> 
   <cewolf:colorpaint 
            color="#E0E0E0" /> 
    <cewolf:data> 
        <!-- here I use the graph object I ve put in the request in my controller class--> 
        <cewolf:producer id="dataPostings"/> 
    </cewolf:data> 
    <cewolf:chartpostprocessor id="dataPostings" />
</cewolf:chart> 
<cewolf:img chartid="line" renderer="cewolf" width="500" height="340"> 
</cewolf:img>

