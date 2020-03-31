

$(document).ready(function () {

    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function(e,xhr,options) {
        xhr.setRequestHeader(header, token);
    })

    $(document).ajaxStart(function () {
        $("#ajax-wait").css("display", "block");
    });
    $(document).ajaxComplete(function () {
        $("#ajax-wait").css("display", "none");
    });

    $(window).load(function() {
        $("#wait").hide(1000);
    });
    toastr.options = {
        "closeButton": true,
        "debug": false,
        "newestOnTop": false,
        "progressBar": true,
        "positionClass": "toast-top-right",
        "preventDuplicates": false,
        "onclick": null,
        "showDuration": "300",
        "hideDuration": "1000",
        "timeOut": "5000",
        "extendedTimeOut": "1000",
        "showEasing": "swing",
        "hideEasing": "linear",
        "showMethod": "fadeIn",
        "hideMethod": "fadeOut"
    }

    // if(message!= null && message.length > 0){
    //     bootbox.alert({
    //         message: message,
    //         className: 'rubberBand animated'
    //     });
    // }
    // alert("a")

    $("#btn-create").click(function () {

        var captcha = grecaptcha.getResponse(attencaptcha);
        var name = $("#name").val();
        var numberCustomer = $("#numbercustomer").val();
        var type = $("#type").val();
        var note = $("#note").val();
        var addr = $("#addr").val();
        if (name==null || name.trim() == "") {
            toastr["warning"]("Thông Báo ! Tên khách không được để trống");
        } else if (numberCustomer == null) {
            toastr["warning"]("Thông Báo ! Số lượng khách không được để trống");
        } else if (type == null ) {
            toastr["warning"]("Thông Báo ! Vui lòng chọn nhà trai hoặc gái");
        } else if (captcha == null || captcha.trim()=="" ) {
            toastr["warning"]("Thông Báo ! Vui lòng nhập captcha");
        }else {
            $.ajax({
                type: "POST",
                contentType: "application/json",
                url: "/rest/attendance/create?captchaResponse="+captcha,
                dataType: 'json',
                cache: false,
                headers: {
                    header, token
                },
                data: JSON.stringify({
                    name: name,
                    numberCustomer: numberCustomer,
                    type: type,
                    note:note,
                    addr:addr,
                }),
                timeout: 600000,
                success: function (data) {
                    if (data == true) {
                        grecaptcha.reset(attencaptcha);
                        bootbox.alert({
                                    message: "Xác nhận thành công",
                                    className: 'rubberBand animated'
                                });
                        $("#numberCustomer").val(1);
                        $("#name").val("");
                        $("#note").val("");
                        $("#addr").val("")
                    } else {
                        toastr["error"]("Thất Bại !  </br> Đã có lỗi xảy ra vui lòng thử lại sau");
                        bootbox.alert({
                            message: "Thất bại, Đã có lỗi xảy ra !",
                            className: 'rubberBand animated'
                        });
                    }
                },
                error: function (data) {
                    toastr["error"]("Thất Bại !  </br> Đã có lỗi xảy ra vui lòng thử lại sau");
                    bootbox.alert({
                        message: "Thất bại, Đã có lỗi xảy ra !",
                        className: 'rubberBand animated'
                    });
                }
            });
        }
    })

    $("#send-wish-btn").click(function () {
        $('#wishModal').modal({
            backdrop: 'static',
            keyboard: true,
            show: true
        });
    })

    $("#btn-createwish").click(function () {

        var captcha = grecaptcha.getResponse(wishcaptcha);
        var name = $("#cusName").val();
        var wish = $("#wish").val();
        if (name==null || name.trim() == "") {
            toastr["warning"]("Thông Báo ! Tên khách không được để trống");
        } else if (wish == null || wish.trim() == "") {
            toastr["warning"]("Thông Báo ! Lời chúc không được để trống");
        } else if (captcha == null || captcha.trim()=="" ) {
            toastr["warning"]("Thông Báo ! Vui lòng nhập captcha");
        }else {
            $.ajax({
                type: "POST",
                contentType: "application/json",
                url: "/rest/wish/create?captchaResponse="+captcha,
                dataType: 'json',
                cache: false,
                headers: {
                    header, token
                },
                data: JSON.stringify({
                    name: name,
                    wish: wish,
                }),
                timeout: 600000,
                success: function (data) {
                    if (data == true) {
                        $('#wishModal').modal('hide');
                        grecaptcha.reset(wishcaptcha);
                        bootbox.alert({
                            message: "Gửi thành công",
                            className: 'rubberBand animated'
                        });
                        $("#cusName").val(1);
                        $("#wish").val("");
                    } else {
                        toastr["error"]("Thất Bại !  </br> Đã có lỗi xảy ra vui lòng thử lại sau");
                        bootbox.alert({
                            message: "Thất bại, Đã có lỗi xảy ra !",
                            className: 'rubberBand animated'
                        });
                    }
                },
                error: function (data) {
                    toastr["error"]("Thất Bại !  </br> Đã có lỗi xảy ra vui lòng thử lại sau");
                    bootbox.alert({
                        message: "Thất bại, Đã có lỗi xảy ra !",
                        className: 'rubberBand animated'
                    });
                }
            });
        }
    })
})