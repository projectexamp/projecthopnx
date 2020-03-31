



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

            $("#edit-name").val(user.name);
            $("#edit-phone").val(user.phoneNumber);
            $("#edit-addr").val(user.addr);
            if (user.birthDate != null) {
                var d = new Date(user.birthDate );
                var day = ("0" + d.getDate()).slice(-2);
                var month = ("0" + (d.getMonth() + 1)).slice(-2);

                var date = d .getFullYear() + "-" + (month) + "-" + (day);
                $("#edit-birth").val(date);

            }

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
                    field: 'roleOrder',
                    title: 'roleOrder',
                    sortable: true,
                    align: 'center'

                }, {
                    field: 'roleName',
                    title: 'roleName',
                    sortable: true,
                    align: 'center',
                    // formatter: dateFormater,
                }, {
                    field: 'description',
                    title: 'description',
                    sortable: true,
                    align: 'center'
                }, {
                    field: 'roleCode',
                    title: 'roleCode',
                    sortable: true,
                    align: 'center'
                },
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
        var roleName = $('#txtNameSearch').val();
        var roleCode = $('#txtPhoneSearch').val();
        if (roleName != null && roleName.trim().length > 0) {
            search.roleName = roleName;
        }
        if (roleCode != null && roleCode.trim().length > 0) {
            search.roleCode = roleCode;
        }


        $.ajax({
            type: "POST",
            contentType: "application/json", headers: {
                header, token
            },
            url: "/getRoleList",
            dataType: 'json',
            cache: false,
            data: JSON.stringify({
                roleName: search.roleName,
                roleCode: search.roleCode
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
        $("#edit-name").val("");
        $("#edit-phone").val("");
        $("#edit-birth").val("");
        $("#edit-addr").val("");
    })

    $("#btn-save").click(function () {
        user.name =$("#edit-name").val();
        user.phoneNumber = $("#edit-phone").val();
        user.birthDate = new Date($("#edit-birth").val());
        user.addr = $("#edit-addr").val();

        if (user.name == null || user.name.trim() == "") {
            toastr["warning"]("Thông Báo ! Tên không được để trống");
        } else if (user.phoneNumber == null) {
            toastr["warning"]("Thông Báo ! Số DT không được để trống");
        } else if ( user.birthDate == null) {
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