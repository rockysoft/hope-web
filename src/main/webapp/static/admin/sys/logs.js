/*
权限管理面板
*/
Ext.define('Hope.app.sys.logs', {
    extend: 'Ext.ux.custom.HopeGridPanel',
    initComponent: function () {
        var me = this;

        Ext.define('LogList', {
            extend: 'Ext.data.Model',
            idProperty: 'id',
            fields: [
                { name: 'id', type: 'int' },
                'userId', 'createTime', 
                'content', 'operation'
            ]
        });

        var store = me.createStore({
            modelName: 'LogList',
            proxyUrl: appBaseUri+'account/logs',//'RoleAction/GetPage',
            proxyDeleteUrl: appBaseUri+'account/logs/remove'//,//'RoleAction/Delete',
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
                url: appBaseUri+'account/logs',//'Home/GetAllActions',
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
            flex: 0.5,
            sortable: true,
            editor: {
                allowBlank: false
            }
        }, {
            text: "操作时间",
            dataIndex: 'createTime',
            flex: 1,
            sortable: true
            //editor: actionCombox
        }, {
            text: "操作内容",
            dataIndex: 'content',
            flex: 6,
            editor: {
                allowBlank: false
            }
        }, {
            text: "操作",
            dataIndex: 'operation',
            flex: 0.5,
            editor: {
                allowBlank: false
            }
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