//上传头图,异步请求
$("#myfile").change(function () {
    $("#headImgForm").ajaxSubmit({
        type:"POST",
        url:"/ssm_demo/note/headImgUpload",
        dataType:"json",
        success:function (result) {
            var filePath = result.data.filePath;
            console.log("file upload result:"+filePath);
            var imgUrl="url(/ssm_demo/"+filePath+")";
            console.log("imgUrl:"+imgUrl);
            $(".set-note-bg").css("backgroundImage",imgUrl);
            console.log("上传完毕");
            $(".setNoteHeaderImgForm").hide();
            $(".setNoteHeaderImgTip").hide();
            $("#headImgPath").val(imgUrl);

        }
    });

})

//提交按钮添加游记
$("#submitNoteBtn").click(function () {
    $.ajax({
        type:"POST",
        url:"/ssm_demo/note/add",
        data:$("#noteForm").serialize(),
        success:function (result) {
            if (result.status == "SUCCESS"){
                //添加成功，在detail页面中回显
                alert("添加成功");
                window.location.href="/ssm_demo/note/goToDetail"
            } else {
                alert("添加失败");
            }

        }
    })

})