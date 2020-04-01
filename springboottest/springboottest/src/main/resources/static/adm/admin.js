



$(document).ready(function () {

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

    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function (e, xhr, options) {
        xhr.setRequestHeader(header, token);
    })




    var $table = $('#table')
    var $remove = $('#remove')
    var selections = []
    var listData = [];
    var user = {};
    window.ajaxOptions = {
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token)
        }
    }
    window.queryParams = function (params) {

        return params
    }
    function getIdSelections() {
        return $.map($table.bootstrapTable('getSelections'), function (row) {
            return row.id
        })
    }



    function operateFormatter(value, row, index) {
        return [
            '<a class="eye" href="javascript:void(0)"  data-toggle=\"modal\" data-target=\"#userModal\" title="Edit">',
            '<i class="fa fa-fw" aria-hidden="true" title="Edit"></i>',
            '</a>  ',
            '<a class="remove" href="javascript:void(0)" title="Remove">',
            '<i class="fa fa-trash"></i>',
            '</a>'
        ].join('')
    }


    function dateFormater(value, row, index) {
        if (value != null) {
            var dd = '';
            var MM = '';
            var yy = '';

            var date = new Date(value);

            yy = date.getFullYear();

            if (date.getDate() < 10) {
                dd = '0' + date.getDate();
            } else
                dd = date.getDate();

            if (date.getMonth() + 1 < 10) {
                MM = '0' + (date.getMonth() + 1);
            } else
                MM = (date.getMonth() + 1);
            var dateString = dd + '/' + MM + '/' + yy;
            return dateString;
        }
        ;
    }

    window.operateEvents = {
        'click .eye': function (e, value, row, index) {
            user = row;
            console.log(user)

            $("#edit-username").val(user.username);
            $("#edit-fullname").val(user.fullname);
            // user.birthDate = new Date($("#edit-birth").val());
            $("#edit-gender").val(user.gender);
            $("#edit-password").val(user.password);
            $("#edit-status").val(user.status);


        },
        'click .remove': function (e, value, row, index) {
            user = row;
            $("#delete").click();
        }
    }


    function initTable() {

        $table.bootstrapTable('destroy').bootstrapTable({
            locale: $('#locale').val(),
            columns: [
                [{
                    title: 'id',
                    field: 'id',
                    rowspan: 2,
                    align: 'center',
                    valign: 'middle',
                    sortable: true,
                }, {
                    title: 'Thông tin',
                    colspan: 6,
                    align: 'center'
                }], [{
                    field: 'username',
                    title: 'username',
                    sortable: true,
                    align: 'center'

                }, {
                    field: 'fullname',
                    title: 'fullname',
                    sortable: true,
                    align: 'center',
                    // formatter: dateFormater,
                }, {
                    field: 'gender',
                    title: 'gender',
                    sortable: true,
                    align: 'center'
                },
                //     {
                //     field: 'password',
                //     title: 'password',
                //     sortable: true,
                //     align: 'center'
                // },
                    {
                        field: 'status',
                        title: 'status',
                        sortable: true,
                        align: 'center'
                    },
                    {
                    title: 'Hành động',
                    align: 'center',
                    events: window.operateEvents,
                    formatter: operateFormatter
                }]
            ],
            data: listData
        })

        $table.on('check.bs.table uncheck.bs.table ' +
            'check-all.bs.table uncheck-all.bs.table',
            function () {
                $remove.prop('disabled', !$table.bootstrapTable('getSelections').length)

                // save your data, here just save the current page
                selections = getIdSelections()
                // push or splice the selections if you want to save all data selections
            })
        $table.on('all.bs.table', function (e, name, args) {
            console.log(name, args)
        })
        $remove.click(function () {
            var ids = getIdSelections()
            $table.bootstrapTable('remove', {
                field: 'id',
                values: ids
            })
            $remove.prop('disabled', true)
        })
    }

    $(function () {
        getData();

        $('#locale').change(initTable)
    })


    function getData() {
        var search = {};
        var username = $('#txtNameSearch').val();
        var fullname = $('#txtPhoneSearch').val();
        if (username != null && username.trim().length > 0) {
            search.username = username;
        }
        if (fullname != null && fullname.trim().length > 0) {
            search.fullname = fullname;
        }


        $.ajax({
            type: "POST",
            contentType: "application/json", headers: {
                header, token
            },
            url: "/getUserList",
            dataType: 'json',
            cache: false,
            data: JSON.stringify({
                username: search.username,
                fullname: search.fullname
            }),
            timeout: 600000,
            success: function (data) {
                listData = data;
                initTable();
            },
            error: function (data) {
                toastr["error"]("Thất Bại ! </br> Đã có lỗi xảy ra vui lòng thử lại sau");
            }
        });

    }

    $("#btn-search").click(function () {
        getData();
    })
    $("#create").click(function () {
        user ={};
        $("#edit-username").val();
        $("#edit-fullname").val();
        // user.birthDate = new Date($("#edit-birth").val());
        $("#edit-gender").val();
        $("#edit-password").val();
        $("#edit-status").val();



    })

    $("#btn-save").click(function () {
        user.username =$("#edit-username").val();
        user.fullname = $("#edit-fullname").val();
        // user.birthDate = new Date($("#edit-birth").val());
        user.gender = $("#edit-gender").val();
        user.password = $("#edit-password").val();
        user.status = $("#edit-status").val();

        if (user.username == null || user.username.trim() == "") {
            toastr["warning"]("Thông Báo ! Tên không được để trống");
        } else if (user.fullname == null) {
            toastr["warning"]("Thông Báo ! Số DT không được để trống");
        } else if ( user.gender == null) {
            toastr["warning"]("Thông Báo ! Ngày sinh không được để trống");
        } else if ( user.password == null) {
            toastr["warning"]("Thông Báo ! Ngày sinh không được để trống");
        } else if ( user.status == null) {
            toastr["warning"]("Thông Báo ! Ngày sinh không được để trống");

        } else {
            $.ajax({
                type: "POST",
                contentType: "application/json", headers: {
                    header, token
                },
                url: "/saveUser",
                dataType: 'json',
                cache: false,
                data: JSON.stringify(user) ,
                timeout: 600000,
                success: function (data) {
                    toastr["success"]("Thành Công ! </br>Thêm mới thành công");
                    user = data;
                    getData();
                },
                error: function (data) {
                    toastr["error"]("Thất Bại ! </br> Đã có lỗi xảy ra vui lòng thử lại sau");
                }
            });
        }
    })


    $("#delete").click(function () {
        var r = confirm("Bạn có chắc chắn muốn xóa  '" + user.name + "'");

        if (r == true) {
            $.ajax({
                url: 'deleteUser',
                dataType: 'json',
                type: 'POST',
                headers: {
                    header, token
                },
                cache: false,
                contentType: 'application/json',
                data: JSON.stringify(user),

                success: function (data) {

                    if (data == true) {
                        toastr["success"]("Thành Công !</br> Xóa thành công ");
                        getData();
                    } else {
                        toastr["error"]("Thất Bại !</br> Đã có lỗi xảy ra vui lòng thử lại sau");
                    }
                },
                error: function (data) {
                    toastr["error"]("Thất Bại !</br> Đã có lỗi xảy ra vui lòng thử lại sau");
                }
            })
        } else {
            console.log("cancel");
        }
    })

    $("#uploadBtn").click(function (event) {

        // Stop default form Submit.
        event.preventDefault();

        // Get form
        var form = $('#singleUploadForm2')[0];

        var data = new FormData(form);

        // Call Ajax Submit.
        ajaxSubmitForm(data, "/upload-csv-file");
    });

    function ajaxSubmitForm(data, url) {

        $.ajax({
            type: "POST",
            enctype: 'multipart/form-data',
            headers: {
                header, token
            },
            url: url,
            data: data,

            // prevent jQuery from automatically transforming the data into a query string
            processData: false,
            contentType: false,
            cache: false,
            timeout: 1000000,
            success: function (data) {
                toastr["success"]("Thành Công ! Import thành công");
                getData();

            },
            error: function (jqXHR) {
                toastr["error"]("Thất Bại ! Đã có lỗi xảy ra vui lòng thử lại sau");
            }
        });
    }

})