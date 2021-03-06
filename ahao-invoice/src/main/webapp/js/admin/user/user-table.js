'use strict';
$(document).ready(function () {
    //1.初始化Table
    $.bootstrapTable({
        selector: '#table-user',
        url: contextPath+'admin/users/page',
        rowStyle: function (row) {
            if (!row.enabled || !!row.accountExpired ||
                !!row.accountExpired || !!row.credentialsExpired) {
                return {classes: 'danger'};
            }
            return '';
        },
        columns: [{
            checkbox: true
        },
            {
                field: 'id',
                title: '用户Id'
            },
            {
                field: 'username',
                title: '用户名'
            },
            {
                field: 'lastLoginTime',
                title: '上次登录时间',
                formatter: function (value) {
                    return $.format.date(value, 'yyyy-MM-dd HH:mm:ss');
                }
            },
            {
                field: 'lastLoginIp',
                title: '上次登录Ip'
            },
            {
                field: 'email',
                title: '电子邮箱'
            },
            {
                field: 'createTime',
                title: '创建时间',
                formatter: function (value) {
                    return $.format.date(value, 'yyyy-MM-dd HH:mm:ss');
                }
            },
            {
                field: 'modifyTime',
                title: '修改时间',
                formatter: function (value) {
                    return $.format.date(value, 'yyyy-MM-dd HH:mm:ss');
                }
            },
            {
                field: 'id',
                title: '操作',
                formatter: function (value) {
                    return '<a type="button" class="btn btn-primary btn-circle btn-sm" ' +
                        'href="'+contextPath+'admin/user/' + value + '">' +
                        '<i class="fa fa-pencil-square-o"></i>' +
                        '</a> &nbsp;' +
                        '<a class="btn btn-warning btn-circle btn-sm btn-delete" ' +
                        'data-id="' + value + '">' +
                        '<i class="fa fa-times"></i>' +
                        '</a>';
                }
            }]
    });

    //2.初始化Button的点击事件
    $('#btn_delete_list').click(function () {
        $.deleteTable('list', {
            url: contextPath+'admin/users',
            key: 'userIds',
            table: '#table-user'
        })
    });

    $('body').on('click', '.btn-delete', function () {
        $.deleteTable('one', {
            url: contextPath+'admin/users',
            key: 'userIds',
            id: $(this).attr('data-id')
        });
    })
});