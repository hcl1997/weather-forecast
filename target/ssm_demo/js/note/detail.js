//加载我的最近的游记详情
$.get("/ssm_demo/note/myLastNote",{},function (result) {
    var note = result.data.note;
    console.log("note.noteHeadImage"+note.noteHeadImg);
    console.log("note.noteCreateTime"+note.noteCreateTime);
    console.log("note.noteCity"+note.noteCity);
    console.log("note.noteTitle"+note.noteTitle);
    console.log("note.noteContent"+note.noteContent);
    //渲染界面
    $(".note_title").html(note.noteTitle);
    $(".set-note-bg").css("backgroundImage",note.noteHeadImg);
    $(".noteTripDate").html(new Date( note.noteTripDate).format("Y-m-d"));
    $(".noteTripDays").html(note.noteTripDays  );
    $(".noteTripPartner").html(note.noteTripPartner  );
    $(".noteTripFeePerPeople").html(note.noteTripFeePerPeople  );
    $(".note_content").html(note.noteContent );

})