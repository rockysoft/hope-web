/*
权限管理面板
*/
Ext.define('Hope.app.sys.roleactions', {
    extend: 'Ext.ux.custom.HopeGridPanel',
    initComponent: function () {
        var me = this;

        Ext.define('ActionList', {
            extend: 'Ext.data.Model',
            idProperty: 'id',
            fields: [
                { name: 'id', type: 'int' },
                'name', //'ActionUrl', 
                'description'
            ]
        });

        var store = me.createStore({
            modelName: 'ActionList',
            proxyUrl: appBaseUri+'account/permissions',//'RoleAction/GetPage',
            proxyDeleteUrl: appBaseUri+'account/permissions/remove'//,//'RoleAction/Delete',
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
                dataIndex: 'name'
            }
            /*, {
                type: 'string',
                dataIndex: 'ActionUrl'
            }*/]
        }

        var actionStore = Ext.create('Ext.data.JsonStore', {
            proxy: {
                type: 'ajax',
                url: appBaseUri+'account/permissions',//'Home/GetAllActions',
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
            text: "权限名称",
            dataIndex: 'name',
            width: 200,
            sortable: true,
            editor: {
                allowBlank: false
            }
        }, /*{
            text: "对应Action",
            dataIndex: 'ActionUrl',
            flex: 1,
            sortable: true,
            editor: actionCombox
        }, */{
            text: "权限描述",
            dataIndex: 'description',
            flex: 1,
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
    },
    /*
    onAddClick: function () {
        var me = this;
        me.rowEditing.cancelEdit();

        var r = Ext.create('ActionList');

        me.getStore().insert(0, r);
        me.rowEditing.startEdit(0, 0);
    }
    */
    onAddClick: function () {
        var me = this;
        Hope.openWindow('添加权限', 'sys.actioninfo', 360, {
            onSaveSuccess: function (action) {
                me.getStore().reload();
            }
        });
    },
    onEditClick: function () {
        var me = this;
        var selections = this.getSelectionModel().getSelection();
        if (selections.length == 1) {
            var record = selections[0];
            Hope.openWindow('修改权限', 'sys.actioninfo', 360, {
                dataId: record.get("id"),
                onSaveSuccess: function (action) {
                    me.getStore().reload();
                }
            });
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