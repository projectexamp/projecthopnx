



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
    var student = {};
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
            '<a class="eye" href="javascript:void(0)"  data-toggle=\"modal\" data-target=\"#studentModal\" title="Edit">',
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
            student = row;
            console.log(student)

            $("#edit-name").val(student.name);
            $("#edit-phone").val(student.phoneNumber);
            $("#edit-addr").val(student.addr);
            if (student.birthDate != null) {
                var d = new Date(student.birthDate );
                var day = ("0" + d.getDate()).slice(-2);
                var month = ("0" + (d.getMonth() + 1)).slice(-2);

                var date = d .getFullYear() + "-" + (month) + "-" + (day);
                $("#edit-birth").val(date);

            }

        },
        'click .remove': function (e, value, row, index) {
            student = row;
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
                        field: 'name',
                        title: 'Tên',
                        sortable: true,
                        align: 'center'

                    }, {
                        field: 'birthDate',
                        title: 'Ngày sinh',
                        sortable: true,
                        align: 'center',
                        formatter: dateFormater,
                    }, {
                        field: 'addr',
                        title: 'Địa chỉ',
                        sortable: true,
                        align: 'center'
                    }, {
                        field: 'phoneNumber',
                        title: 'Số điện thoại',
                        sortable: true,
                        align: 'center'
                    },
                //     {
                //     title: 'Hành động',
                //     align: 'center',
                //     events: window.operateEvents,
                //     formatter: operateFormatter
                // }
                ]
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
        var nameSearch = $('#txtNameSearch').val();
        var phoneSearch = $('#txtPhoneSearch').val();
        if (nameSearch != null && nameSearch.trim().length > 0) {
            search.name = nameSearch;
        }
        if (phoneSearch != null && phoneSearch.trim().length > 0) {
            search.phoneNumber = phoneSearch;
        }


        $.ajax({
            type: "POST",
            contentType: "application/json", headers: {
                header, token
            },
            url: "/getStudentList",
            dataType: 'json',
            cache: false,
            data: JSON.stringify({
                name: search.name,
                phoneNumber: search.phoneNumber
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
        student ={};
        $("#edit-name").val("");
        $("#edit-phone").val("");
        $("#edit-birth").val("");
        $("#edit-addr").val("");
    })

    $("#btn-save").click(function () {
        student.name =$("#edit-name").val();
        student.phoneNumber = $("#edit-phone").val();
        student.birthDate = new Date($("#edit-birth").val());
        student.addr = $("#edit-addr").val();
        
        if (student.name == null || student.name.trim() == "") {
            toastr["warning"]("Thông Báo ! Tên không được để trống");
        } else if (student.phoneNumber == null) {
            toastr["warning"]("Thông Báo ! Số DT không được để trống");
        } else if ( student.birthDate == null) {
            toastr["warning"]("Thông Báo ! Ngày sinh không được để trống");
        } else {
            $.ajax({
                type: "POST",
                contentType: "application/json", headers: {
                    header, token
                },
                url: "/saveStudent",
                dataType: 'json',
                cache: false,
                data: JSON.stringify(student) ,
                timeout: 600000,
                success: function (data) {     
                        toastr["success"]("Thành Công ! </br>Thêm mới thành công");
                        student = data;
                        getData();          
                },
                error: function (data) {
                    toastr["error"]("Thất Bại ! </br> Đã có lỗi xảy ra vui lòng thử lại sau");
                }
            });
        }
    })

   
    $("#delete").click(function () {
        var r = confirm("Bạn có chắc chắn muốn xóa  '" + student.name + "'");

        if (r == true) {
            $.ajax({
                url: 'deleteStudent',
                dataType: 'json',
                type: 'POST',
                headers: {
                    header, token
                },
                cache: false,
                contentType: 'application/json',
                data: JSON.stringify(student),

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

   

})