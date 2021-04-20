const CONSTANT = {
    DATA_TABLES: {
        DEFAULT_OPTION: {
            language: {
                "processing": "数据处理中...",
                "lengthMenu": "_MENU_ 条/页",
                "info": "第 _PAGE_ 页，共计 _PAGES_ 页，_TOTAL_ 条数据",
                "emptyTable": "无数据可用",
                "zeroRecords": "无数据显示",
                "paginate": {
                    "first": "首页",
                    "last": "尾页",
                    "next": "下一页",
                    "previous": "上一页"
                }
            },
            searching: false,
            ordering: false,
            info: true,
            paging: true,
            scrollX: true,
            scrollY: true,
            lengthChange: true,
            lengthMenu: [10, 25, 50, 75, 100],
            pageLength: 50,
            pagingType: "full_numbers",
            stripeClasses: ['strip1', 'strip2', 'strip3'],
            autoWidth: false,
            processing: true,
            destroy: false,
            rowId: "id",
            serverSide: true,
        },
        COLUMN: {
            // 复选框
            CHECKBOX: {
                className: "td-checkbox",
                orderable: false,
                width: "30px",
                data: null,
                render: function (data, type, row, meta) {
                    return '<input type="checkbox" class="iCheck">';
                }
            },

            RENDER: {	//常用render可以抽取出来，如日期时间、头像等
                ELLIPSIS: function (data, type, row, meta) {
                    data = data || "";
                    return '<span title="' + data + '">' + data + '</span>';
                }
            }
        }
    }
};