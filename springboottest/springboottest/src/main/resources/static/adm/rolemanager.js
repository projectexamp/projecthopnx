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
    var listFuncOfRole = [] ;
    var role = {};
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
            '<a class="eye" href="javascript:void(0)"  data-toggle=\"modal\" data-target=\"#roleModal\" title="Edit">',
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

    $('.itemSearch').select2({
        tags: true,
        multiple: true,
        tokenSeparators: [',', ' '],
        // minimumInputLength: 2,
        // minimumResultsForSearch: 10,
        ajax: {
            url: "/getFunctionListAll",
            dataType: "json",
            type: "GET",
            headers: {
                header, token
            },
            data: function (params) {

                var queryParameters = {
                    term: params.term
                }
                return queryParameters;
            },
            processResults: function (data) {
                return {
                    results: $.map(data, function (item) {
                        return {
                            text: item.functionName,
                            id: item.id

                        }
                    })
                };
            }

        }


    });


    window.operateEvents = {
        'click .eye': function (e, value, row, index) {
            role = row;
            console.log(role)

            $("#edit-status").val(role.status);
            $("#edit-roleName").val(role.roleName);
            // role.birthDate = new Date($("#edit-birth").val());
            $("#edit-description").val(role.description);
            $("#edit-roleCode").val(role.roleCode);
            $("#edit-roleOrder").val(role.roleOrder);
            // $(".itemSearch").select2().val("2");
            // $(".itemSearch").select2().;
            // $('.itemSearch').select2().val("1").trigger('change') ;
            // $(".itemSearch").select2('val', 1) ;

        },
        'click .remove': function (e, value, row, index) {
            role = row;
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
                    colspan: 8,
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
                },
                    {
                    field: 'description',
                    title: 'description',
                    sortable: true,
                    align: 'center',
                    visible  : false
                },
                    {
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
                        field: 'funcStr',
                        title: 'functions',
                        sortable: true,
                        align: 'center',
                        // visible: false
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
        role = {};

        $("#edit-status").val();
        $("#edit-roleName").val();
        $("#edit-description").val();
        $("#edit-roleCode").val();
        $("#edit-roleOrder").val();
    })

    $("#btn-save").click(function () {
        role.status = $("#edit-status").val();
        role.roleName = $("#edit-roleName").val();
        // role.birthDate = new Date($("#edit-birth").val());
        role.description = $("#edit-description").val();
        role.roleCode = $("#edit-roleCode").val();
        role.roleOrder = $("#edit-roleOrder").val();
        // role.func = [];

        var valu = $('.itemSearch').val() ;
        role.func = valu;
        console.log(valu) ;


        if (role.roleName == null || role.roleName.trim() == "") {
            toastr["warning"]("Thông Báo ! Tên không được để trống");
        } else if (role.status == null) {
            toastr["warning"]("Thông Báo ! Số DT không được để trống");
        } else if (role.description == null) {
            toastr["warning"]("Thông Báo ! Ngày sinh không được để trống");
        } else if (role.roleCode == null) {
            toastr["warning"]("Thông Báo ! Ngày sinh không được để trống");
        } else if (role.roleOrder == null) {
            toastr["warning"]("Thông Báo ! Ngày sinh không được để trống");


        } else {
            $.ajax({
                type: "POST",
                contentType: "application/json", headers: {
                    header, token
                },
                url: "/saveRole",
                dataType: 'json',
                cache: false,
                data: JSON.stringify(role),
                timeout: 600000,
                success: function (data) {
                    toastr["success"]("Thành Công ! </br>Cập nhật thành công");
                    role = data;
                    getData();
                },
                error: function (data) {
                    toastr["error"]("Thất Bại ! </br> Đã có lỗi xảy ra vui lòng thử lại sau");
                }
            });
        }
    })


    $("#delete").click(function () {
        var r = confirm("Bạn có chắc chắn muốn xóa  '" + role.roleName + "'");

        if (r == true) {
            $.ajax({
                url: '/deleteRole',
                dataType: 'json',
                type: 'POST',
                headers: {
                    header, token
                },
                cache: false,
                contentType: 'application/json',
                data: JSON.stringify(role),

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


    // function getFuncSelected() {
    //     var search = {};
    //     var roleName = $('#txtNameSearch').val();
    //     var roleCode = $('#txtPhoneSearch').val();
    //     if (roleName != null && roleName.trim().length > 0) {
    //         search.roleName = roleName;
    //     }
    //     if (roleCode != null && roleCode.trim().length > 0) {
    //         search.roleCode = roleCode;
    //     }
    //
    //
    //     $.ajax({
    //         type: "POST",
    //         contentType: "application/json", headers: {
    //             header, token
    //         },
    //         url: "/getRoleList",
    //         dataType: 'json',
    //         cache: false,
    //         data: JSON.stringify({
    //             roleName: search.roleName,
    //             roleCode: search.roleCode
    //         }),
    //         timeout: 600000,
    //         success: function (data) {
    //             listData = data;
    //             initTable();
    //         },
    //         error: function (data) {
    //             toastr["error"]("Thất Bại ! </br> Đã có lỗi xảy ra vui lòng thử lại sau");
    //         }
    //     });
    //
    // }




})