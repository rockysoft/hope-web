/*
权限管理面板
*/
Ext.define('Hope.app.sys.loginlogs', {
    extend: 'Ext.ux.custom.HopeGridPanel',
    initComponent: function () {
        var me = this;

        Ext.define('LoginLogList', {
            extend: 'Ext.data.Model',
            idProperty: 'id',
            fields: [
                { name: 'id', type: 'int' },
                'userId', 'loginName', 'createTime', 'ip', 'type', 'status'
            ]
        });

        var store = me.createStore({
            modelName: 'LoginLogList',
            proxyUrl: appBaseUri+'account/loginLogs',//'RoleAction/GetPage',
            proxyDeleteUrl: appBaseUri+'account/loginLogs/remove'//,//'RoleAction/Delete',
            //sortProperty: 'ActionUrl'
        });
        
        // configure whether filter query is encoded or not (initially)
    		var encode = false;
    
    		// configure whether filtering is performed locally or remotely (initially)
    		var local = true;

        var filters = {
            ftype: 'filters',
            encode: encode,
            local: local,
            filters: [{
                type: 'string',
                dataIndex: 'userId'
            }
            /*, {
                type: 'string',
                dataIndex: 'ActionUrl'
            }*/]
        }

        var actionStore = Ext.create('Ext.data.JsonStore', {
            proxy: {
                type: 'ajax',
                url: appBaseUri+'account/loginLogs',//'Home/GetAllActions',
                reader: {
                    type: 'json',
                    root: 'list',
                    idProperty: 'ItemText'
                }
            },
            fields: ['ItemText']
        });
/*
        var actionCombox = Ext.create('Ext.form.field.ComboBox', {
            hideLabel: true,
            hideName: 'ActionUrl',
            allowBlank: false,
            store: actionStore,
            valueField: 'ItemText',
            displayField: 'ItemText',
            typeAhead: true,
            queryMode: 'remote',
            emptyText: '请选择...'
        });
*/
        var columns = [{
            text: "操作员ID",
            dataIndex: 'userId',
            flex: 1,
            sortable: true,
            editor: {
                allowBlank: false
            }
        }, {
            text: "登录名",
            dataIndex: 'loginName',
            flex: 1,
            editor: {
                allowBlank: false
            }
        }, {
            text: "发生时间",
            dataIndex: 'createTime',
            flex: 2,
            sortable: true
            //editor: actionCombox
        }, {
            text: "IP",
            dataIndex: 'ip',
            flex: 2,
            editor: {
                allowBlank: false
            }
        }, {
            text: "类型",
            dataIndex: 'type',
            flex: 1,
            renderer:this.changeType
         }, {
            text: "状态",
            dataIndex: 'status',
            flex: 1,
            renderer:this.changeStatus
        }];

        me.rowEditing = Ext.create('Ext.grid.plugin.RowEditing', {
            clicksToMoveEditor: 1,
            errorSummary:false,
            saveBtnText: '保存',
            cancelBtnText: '取消',
            listeners: {
                cancelEdit: function (rowEditing, context) {
                    if (context.record.phantom) {
                        store.remove(context.record);
                    }
                },
                edit: function (editor, e, eOpts) {//点击update事件，ajax提交到动态页保存起来
                    Ext.Ajax.request({
                        url: appBaseUri+'RoleAction/Save',
                        params: e.record.getData(),
                        success: function (response) {
                            var res = Ext.JSON.decode(response.responseText);
                            if (!res.success)
                                Ext.Msg.alert('Error', res.msg);
                            else {
                                e.record.commit();
                                store.reload();
                            }
                        }
                    });
                }
            }
        });

        Ext.apply(this, {
            store: store,
            columns: columns,
            features: [filters],
            //plugins: [me.rowEditing]
            //hideBBar: true,//?
            idProperty: 'id'
        });
        
        this.callParent(arguments);
        store.load();
    },
    changeType : function(value){
    	if (value == '1') {
        return "<span style='color:black;'>登录</span>";
    	} else if (value == '2') {
        return "<span style='color:black;'>退出</span>";
    	} else {
        return "<span style='color:red;font-weight:bold;'>未知</span>";
    	}
  	},
    changeStatus : function(value){
    	if (value == '2') {
        return "<span style='color:red;font-weight:bold;'>登录失败</span>";
    	} else if (value == '1') {
        return "<span style='color:black;'>登录成功</span>";
    	} else if (value == '4') {
        return "<span style='color:red;font-weight:bold;'>退出失败</span>";
    	} else if (value == '3') {
        return "<span style='color:black;'>退出成功</span>";
    	} else {
        return "<span style='color:red;font-weight:bold;'>未知</span>";
    	}
  	}	
});

/*
添加/修改角色
*/
Ext.define('Hope.app.sys.actioninfo', {
    extend: 'Ext.ux.custom.HopeWindowInfoPanel',
    initComponent: function () {
        var me = this;

        Ext.define('actionModel', {
            extend: 'Ext.data.Model',
            fields: [
                'name', 'description',
                { name: 'id', type: 'int' }
            ]
        });

        Ext.apply(this, {
            modelName: 'actionModel',
            loadUrl: appBaseUri+'account/permissions',//'Role/GetExtForm',
            updateUrl: appBaseUri+'account/permissions',//'Role/Save',
            saveUrl: appBaseUri+'account/permissions',//'Role/Save',
            items: [
                { xtype: 'hiddenfield', name: 'id' },
                { name: 'name', xtype: 'textfield', fieldLabel: '权限名称', allowBlank: false },
                { name: 'description', xtype: 'textfield', fieldLabel: '权限描述', allowBlank: false }
            ]
        });

        this.callParent(arguments);
    }
});