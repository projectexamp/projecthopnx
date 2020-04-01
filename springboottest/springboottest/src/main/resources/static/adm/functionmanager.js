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
    var func = {};
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
            '<a class="eye" href="javascript:void(0)"  data-toggle=\"modal\" data-target=\"#funcModal\" title="Edit">',
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
            func = row;
            console.log(func)

            $("#edit-status").val(func.status);
            $("#edit-functionOrder").val(func.functionOrder);
            $("#edit-functionUrl").val(func.functionUrl);
            $("#edit-functionName").val(func.functionName);
            $("#edit-description").val(func.description);
            $("#edit-functionCode").val(func.functionCode);

            // $("#edit-name").val(func.name);
            // $("#edit-phone").val(func.phoneNumber);
            // $("#edit-addr").val(func.addr);
            // if (func.birthDate != null) {
            //     var d = new Date(func.birthDate);
            //     var day = ("0" + d.getDate()).slice(-2);
            //     var month = ("0" + (d.getMonth() + 1)).slice(-2);
            //
            //     var date = d.getFullYear() + "-" + (month) + "-" + (day);
            //     $("#edit-birth").val(date);
            //
            // }

        },
        'click .remove': function (e, value, row, index) {
            func = row;
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
                }], [
                //     {
                //     field: 'status',
                //     title: 'status',
                //     sortable: true,
                //     align: 'center'
                //
                // },
                    {
                    field: 'functionOrder',
                    title: 'functionOrder',
                    sortable: true,
                    align: 'center',
                    // formatter: dateFormater,
                }, {
                    field: 'functionUrl',
                    title: 'functionUrl',
                    sortable: true,
                    align: 'center'
                }, {
                    field: 'functionName',
                    title: 'functionName',
                    sortable: true,
                    align: 'center'
                },
                    {
                        field: 'description',
                        title: 'description',
                        sortable: true,
                        align: 'center'
                    },
                    {
                        field: 'functionCode',
                        title: 'functionCode',
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
        var functionName = $('#txtNameSearch').val();
        var functionCode = $('#txtPhoneSearch').val();
        if (functionName != null && functionName.trim().length > 0) {
            search.functionName = functionName;
        }
        if (functionCode != null && functionCode.trim().length > 0) {
            search.functionCode = functionCode;
        }


        $.ajax({
            type: "POST",
            contentType: "application/json", headers: {
                header, token
            },
            url: "/getFunctionList",
            dataType: 'json',
            cache: false,
            data: JSON.stringify({
                functionName: search.functionName,
                functionCode: search.functionCode
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
        func = {};
        $("#edit-status").val("");
        $("#edit-functionOrder").val("");
        $("#edit-functionUrl").val("");
        $("#edit-functionName").val();
        $("#edit-description").val();
        $("#edit-functionCode").val();
    })

    $("#btn-save").click(function () {
        func.status = $("#edit-status").val();
        func.functionOrder = $("#edit-functionOrder").val();
        // func.functionUrl = new Date($("edit-functionUrl").val());
        func.functionUrl = $("#edit-functionUrl").val() ;
        func.functionName = $("#edit-functionName").val();
        func.description = $("#edit-description").val();
        func.functionCode = $("#edit-functionCode").val();



        if (func.status == null || func.status.trim() == "") {
            toastr["warning"]("Thông Báo ! Tên không được để trống");
        } else if (func.functionOrder == null) {
            toastr["warning"]("Thông Báo ! Số DT không được để trống");
        } else if (func.functionUrl == null) {
            toastr["warning"]("Thông Báo ! Ngày sinh không được để trống");
        }else if (func.functionName == null) {
            toastr["warning"]("Thông Báo ! Số DT không được để trống");
        }else if (func.description == null) {
            toastr["warning"]("Thông Báo ! Số DT không được để trống");
        }
        else if (func.functionCode == null) {
            toastr["warning"]("Thông Báo ! Số DT không được để trống");

        } else {
            $.ajax({
                type: "POST",
                contentType: "application/json", headers: {
                    header, token
                },
                url: "/saveFunction",
                dataType: 'json',
                cache: false,
                data: JSON.stringify(func),
                timeout: 600000,
                success: function (data) {
                    toastr["success"]("Thành Công ! </br>Cập nhật thành công");
                    func = data;
                    getData();
                },
                error: function (data) {
                    toastr["error"]("Thất Bại ! </br> Đã có lỗi xảy ra vui lòng thử lại sau");
                }
            });
        }
    })


    $("#delete").click(function () {
        var r = confirm("Bạn có chắc chắn muốn xóa  '" + func.functionName + "'");

        if (r == true) {
            $.ajax({
                url: '/deleteFunction',
                dataType: 'json',
                type: 'POST',
                headers: {
                    header, token
                },
                cache: false,
                contentType: 'application/json',
                data: JSON.stringify(func),

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