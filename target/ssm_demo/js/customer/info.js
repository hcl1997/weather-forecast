//html页面加载完毕才执行,window.onload必须等到页面内（包括图片的）所有元素加载到浏览器中后才能执行。
// 而$(document).ready(function(){})是DOM结构加载完毕后就会执行。
$(document).ready(function () {
    getLoginCustomerTelno();
})
//1、从session中获得登录用户手机号,然后根据手机号查询用户信息
function  getLoginCustomerTelno() {
    $.get("/ssm_demo/login/getLoginTelno",{},function (result) {
        var telno = result.data.customerTelno;
        console.log("telno======"+telno);
        getCustomerInfo(telno);
    });
}

//2、getCustomerInfo，通过手机号查询用户信息
function getCustomerInfo(tel) {
    console.log("getCustomerInfo telno"+tel);
    $.get("/ssm_demo/login/info",{telno:tel},function (result) {
        var customer = result.data.customer;
        console.log("用户id"+customer.custId);
        console.log("用户姓名"+customer.custName);
        console.log("用户城市"+customer.custCity);
        console.log("用户简介"+customer.custResume);
        //3、渲染用户数据
        console.log("customer ="+customer);
        $("input[name=custName]").val(customer.custName);
        $("input[name=custCity]").val(customer.custCity);
        $("input[name=custBirth]").val(customer.custBirth);
        $("input[name=custResume]").val(customer.custResume);
        console.log("customer gender:"+customer.custGender);
        if (customer.custGender == 'M'){
        } else {
            $("input[value=M]").removeAttr("checked");
            $("input[value=F]").attr("checked","checked");
        }
    });
}


/*//跟换用户头像
$("#myfile").myfile(function () {
    $("#userlogoImg").ajaxSubmit({
        type:post,
        url:"/ssm_demo/login/changelogo",
        dataType:"json",
        success:function (result) {
            var filepath = result.data.filePath;
            console.log("头像上传位置："+filepath)
            var imgUrl = "url(/ssm_demo/"+filepath+")";
            console.log("imgUrl:"+imgUrl);
            $(".user-img").css("backgroundImage",imgUrl);
            console.log("更换完毕");
        }
    })

})*/


