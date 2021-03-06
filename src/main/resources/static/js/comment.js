function post() {
    var questionId = $("#question_id").val();
    var content = $("#comment_content").val();
    var questionUserCode = $("#question_userCode").val();
    var questionCode = $("#questionCode").val();
    comment2target(questionId, 1, content,questionUserCode,questionCode);
}

function comment2target(targetId, type, content,questionUserCode,questionCode) {
    if (!content) {
        alert("不能回复空内容~~~");
        return;
    }
    console.log(questionUserCode);
    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: 'application/json',
        data: JSON.stringify({
            "parentCode": targetId,
            "context": content,
            "parentType": type,
            "receiverCode": questionUserCode,
            "questionCode": questionCode
        }),
        success: function (response) {
            if (response.code == 200) {
                window.location.reload();
            } else {
                if (response.code == 201) {
                    alert(response.message);
                    /*if (isAccepted) {
                        window.open("https://github.com/login/oauth/authorize?client_id=2859958f9f059979ed3a&redirect_uri=" + document.location.origin + "/callback&scope=user&state=1");
                        window.localStorage.setItem("closable", true);
                    }*/
                } else {
                    alert(response.message);
                }
            }
        },
        dataType: "json"
    });
}
function comment(e) {
    var commentId = e.getAttribute("data-id");
    var content = $("#input-" + commentId).val();
    var questionUserCode = $("#question_userCode").val();
    var questionCode = $("#questionCode").val();
    comment2target(commentId, 2, content,questionUserCode,questionCode);
}

/**
 * 展开二级评论
 */
function collapseComments(e) {
    var id = e.getAttribute("data-id");
    var comments = $("#comment-" + id);

    // 获取一下二级评论的展开状态
    var collapse = e.getAttribute("data-collapse");
    if (collapse) {
        // 折叠二级评论
        comments.removeClass("in");
        e.removeAttribute("data-collapse");
        e.classList.remove("active");
    } else{
        var subCommentContainer = $("#comment-" + id);
        if (subCommentContainer.children().length != 1) {
            //展开二级评论
            comments.addClass("in");
            // 标记二级评论展开状态
            e.setAttribute("data-collapse", "in");
            e.classList.add("active");
        }else {
            $.getJSON("/comment/"+id,function (data) {
                $.each(data,function (index,comment) {
                    console.log(comment.context);
                    var mediaLeftElement = $("<div/>", {
                        "class": "media-left"
                    }).append($("<img/>", {
                        "class": "media-object xs-image img-rounded",
                        "src": comment.user.imageUrl
                    }));

                    var mediaBodyElement = $("<div/>", {
                        "class": "media-body"
                    }).append($("<h5/>", {
                        "class": "media-heading",
                        "html": comment.user.userName
                    })).append($("<div/>", {
                        "html": comment.context
                    })).append($("<div/>", {
                        "class": "menu"
                    }).append($("<span/>", {
                        "class": "pull-right",
                        "html": moment(comment.createTime).format('YYYY-MM-DD')
                    })));

                    var mediaElement = $("<div/>", {
                        "class": "media"
                    }).append(mediaLeftElement).append(mediaBodyElement);

                    var commentElement = $("<div/>", {
                        "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12 comments"
                    }).append(mediaElement);

                    subCommentContainer.prepend(commentElement);
                });
                //展开二级评论
                comments.addClass("in");
                // 标记二级评论展开状态
                e.setAttribute("data-collapse", "in");
                e.classList.add("active");
            });
        }
    }
}
