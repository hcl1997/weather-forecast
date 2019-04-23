//获得我的所有游记
$.get("/ssm_demo/note/myLastTenNote",{},function (result) {
    var noteList = result.data.noteList;
    var customer = result.data.customer;
    console.log("noteList......"+noteList);
    console.log("customer......"+customer);
    //遍历集合
    $.each(noteList,function (index,item) {
        console.log("noteHeadImg:"+item.noteHeadImg);
        console.log("noteId:"+item.noteId);
        console.log("custId:"+item.custId);
        console.log("noteTitle:"+item.noteTitle);
        console.log("noteTripDate:"+item.noteTripDate);
        /*index 下标从0开始*/
        console.log("index==============:"+index);
        var ele = "  <li style=\"list-style: none;float: left;margin-left: 40px;margin-bottom: 40px\">\n" +
            "                                    <a href=\"javascript:;\" >\n" +
            "                                        <div class=\"noteHeadImg\" style=\"width: 400px;height: 150px;border: 1px solid grey;;background-repeat: no-repeat \"></div>\n" +
            "                                        <span class=\"noteTitle\" style=\"font-weight: bold;font-size: 16px;margin-right: 30px;display: block\"></span>\n" +
            "                                        <span class=\"noteCreateTime\" style=\"font-weight: bold;font-size: 16px\"></span>\n" +
            "                                        <span class=\"custName\" style=\"font-weight: bold;font-size: 16px\"></span>\n" +
            "                                        <span  style=\"font-weight: bold;font-size: 16px\">,坐标：</span>\n" +
            "                                        <span class=\"custCity\" style=\"font-weight: bold;font-size: 16px\"></span>\n" +

            "                                    </a>\n" +
            "           </li>";

        //渲染数据,在ul后面追加数据
        $("#mynote_list").append(ele)
        var li = $("#mynote_list").children().eq(index);//li
        var a = li.children().eq(0);//li 下的a标签
        a.attr("name",item.noteId);
        console.log("li:"+li);
        console.log("a;"+a);

        //由于服务器关闭上传到服务器的图片会消失
        a.children().eq(0).css("backgroundImage","url('/ssm_demo/images/jiangxuan_4.jpg')");
        a.children().eq(1).text(item.noteTitleTi);
        a.children().eq(2).text(new Date(item.noteCreateTime).format("Y-m-d"));
        a.children().eq(3).text(customer.custName);
        a.children().eq(5).text(customer.custCity);


    })

})