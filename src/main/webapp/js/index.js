//加载游记分页的信息（游记记录，分页信息）
var currentPage;
var flag ;
function loadNote(pageNum,flag) {
    console.log("当前页码 pageNum："+pageNum);
    currentPage = pageNum;

    var nextPage = pageNum+1;
    var previousPage = pageNum-1;
    $.get("/ssm_demo/note/getall/"+pageNum,{},function (result) {
        var noteList = result.data.noteList;
        var totalCount = result.data.totalCount;
        var totalPage = result.data.totalPage;
        var noteEle = " <div class=\"col-md-12\">\n" +
            "\t\t\t\t\t\t\t\t\t\t<a href=\"javascript:;\" style='display: inline'>\n" +
            "\t\t\t\t\t\t\t\t\t\t\t<img src=\"images/jiangxuan_4.jpg\" style=\"width: 50%;height: 50%;display: inline\">\n" +
            "\t\t\t\t\t\t\t\t\t\t</a>\n" +
            "                                        <div class=\"has_border\" style=\"margin-top: 60px;float: right;width: 50%\">\n" +
            "                                            <span style=\"display: inline\" class=\"noteTitle\"> </span>\n" +
            "                                            <div >\n" +
            "                                                坐标：<em class=\"noteCity\"></em>\n" +
            "                                                作者：<a href=\"\"><strong class=\"noteAuthor\"></strong></a>\n" +
            "                                            </div>\n" +
            "                                        </div>\n" +
            "                               \n" +
            "                        </div>";
        var record = $(".guone_r .row");
        //渲染游记数据
        $.each(noteList,function (index,item) {
            console.log("item.noteTitle======="+item.noteTitle);
            console.log("item.noteCity"+item.noteCity);
            console.log("item.others1:"+item.others1);
            console.log("item.custId:"+item.custId);
            if (pageNum ==1&&flag==1)
                record.append(noteEle);
            var note_html = record.children().eq(index);
            console.log("note_html"+note_html.html());

            var div = note_html.children().eq(1);
            console.log("div"+div.html());
            var title = div.children().eq(0);
            var city = div.children().eq(1).children().eq(0);
            var author = div.children().eq(1).children().eq(1);
            console.log("title:" +title.html());
            console.log("city:" +city.html());
            console.log("author:" +author.html());

            title.text(item.noteTitle);
            city.text(item.noteCity);
            author.text(item.others1);
        });
        //渲染页码信息
        $(".totalPage").text(totalPage);
        $(".totalCount").text(totalCount);

        //当前页码
        $(".currentPage").text(pageNum);
        console.log("当前页码："+currentPage);

        //上一页与下一页
        if(pageNum>1)
            $(".preivous").show();
        else {
            $(".preivous").hide();
        }
        if(pageNum ==totalPage)
            $(".next").hide();
        else{
            $(".next").show();
        }

    });
}

loadNote(1,1);