/*
菜单管理面板
*/
Ext.define('Hope.app.sys.rolemenus', {
    extend: 'Ext.ux.custom.HopeTreeGridPanel',
    initComponent: function () {
        var me = this;

        Ext.define('RoleMenuList', {
            extend: 'Ext.data.Model',
            idProperty: 'id',
            fields: [
                { name: 'id', type: 'String' },
                { name: 'parentId', type: 'String' },
                { name: 'sort', type: 'int' },
                'text', 'url', //'MenuConfig', 
                'iconCls', 'buttons', 'controller'
            ]
        });

        var store = me.createStore({
            modelName: 'RoleMenuList',//root: 'children',
            proxyUrl: appBaseUri+'account/menus', //'data/menu123.json'
            sortProperty: 'ID',//没有这个属性，即不本地排序，按json数据的顺序显示。
            sortDirection: 'DESC'
        });

        var columns = [{
            xtype: 'treecolumn',
            text: "菜单名称",
            dataIndex: 'text',
            width: 200,
            sortable: false,
            editor: {
                allowBlank: false
            }
        }, {
            text: "链接地址",
            dataIndex: 'url',
            width: 200,
            sortable: true,
            editor: {
                allowBlank: false
            }
        }, {
            text: "功能按钮",
            dataIndex: 'buttons',
            flex: 1
        }, {
            text: "控制器",
            dataIndex: 'controller',
            width: 200
        }, {
            text: "Icon",
            dataIndex: 'iconCls',
            width: 200,
            editor: {
            }
        }, {
            text: "排序",
            dataIndex: 'sort',
            width: 100,
            sortable: true,
            editor: {
                allowBlank: false,
                vtype: 'num'
            }
        }];

        Ext.apply(this, {
            store: store,
            columns: columns,
            proxyDeleteUrl: appBaseUri+'account/menus/remove'//'RoleMenu/Delete'
        });

        this.callParent(arguments);
    },
    onAddClick: function () {
        var me = this;
        Hope.openWindow('添加菜单', 'sys.rolemenuinfo', 600, {
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
            Hope.openWindow('修改菜单', 'sys.rolemenuinfo', 600, {
                dataId: record.get("id"),
                onSaveSuccess: function (action) {
                    me.getStore().reload();
                }
            });
        }
    }
});

/*
添加/修改菜单
*/
Ext.define('Hope.app.sys.rolemenuinfo', {
    extend: 'Ext.ux.custom.HopeWindowInfoPanel',
    initComponent: function () {
        var me = this;

        Ext.define('RoleMenuInfo', {
            extend: 'Ext.data.Model',
            fields: [
                'text', 'url', 'sort', //'MenuConfig', 
                'iconCls', 'buttons', 'controller',
                { name: 'parentId', type: 'String' },
                { name: 'id', type: 'String' }
            ]
        });
        
         var parentFiled = Ext.create('Ext.ux.TreePicker', {
            name: 'parentId',
            fieldLabel: '上级菜单',
            url: appBaseUri+'account/menus',//'data/menu123.json',
            selectParentNode: true,
            displayField: 'text',
            valueField:'id',
            emptyText: '不选则为顶级菜单',
            anchor: '60%'
        });


        var controllerStore = Ext.create('Ext.data.JsonStore', {
            proxy: {
                type: 'ajax',
                url: appBaseUri+'data/GetAllControllers',
                reader: {
                    type: 'json',
                    root: 'list',
                    idProperty: 'ItemValue'
                }
            },
            fields: ['ItemText', 'ItemValue']
        });

        var controllerCombox = Ext.create('Ext.form.field.ComboBox', {
            fieldLabel: '控制器',
            name: 'controller',
            store: controllerStore,
            valueField: 'ItemValue',
            displayField: 'ItemText',
            typeAhead: true,
            queryMode: 'remote',
            emptyText: '请选择...'
        });

        var actionStore = Ext.create('Ext.data.Store', {
            data: [
                { "ItemText": "添加", "ItemValue": "Add" },
                { "ItemText": "修改", "ItemValue": "Edit" },
                { "ItemText": "删除", "ItemValue": "Delete" },
                { "ItemText": "数据同步", "ItemValue": "Sync" },
                { "ItemText": "导出", "ItemValue": "Export" }
            ],
            fields: ['ItemText', 'ItemValue']
        });

        var actionMutiCombox = Ext.create('Ext.ux.form.BoxSelect', {
            fieldLabel: "功能按钮",
            name: 'buttons',
            displayField: "ItemText",
            valueField: "ItemValue",
            anchor: '100%',
            emptyText: "请选择...",
            store: actionStore,
            queryMode: "local"
        });

        Ext.apply(this, {
            modelName: 'RoleMenuInfo',
            loadUrl: appBaseUri+'account/menus',//'data/GetExtForm',
            updateUrl: appBaseUri+'account/menus',
            saveUrl: appBaseUri+'account/menus',
            items: [
                { xtype: 'hiddenfield', name: 'id' }, parentFiled,
                { 
                		layout: 'column',
                		border: false,
                    items: [{
                        columnWidth: .5,
                        layout: 'form',
                        padding: '0 20 0 0',
                        border: false,
                        items: [
                            { name: 'text', xtype: 'textfield', fieldLabel: '菜单名称', allowBlank: false },
                            controllerCombox
                        ]
                    }, {
                        columnWidth: .5,
                        layout: 'form',
                        border: false,
                        items: [
                            { name: 'url', xtype: 'textfield', fieldLabel: '链接地址' },{ name: 'sort', xtype: 'textfield', fieldLabel: '排序', allowBlank: false}
                           // { name: 'MenuConfig', xtype: 'textfield', fieldLabel: '配置参数' }
                        ]
                    }]
                },
                actionMutiCombox
            ]
        });

        this.callParent(arguments);

        me.form.on('beforeSetafterLoad', function (data) {
            parentFiled.store.load({
                callback: function (records, operation, success) {//alert("abc");
                    //Ext.each(records, function (item, index) {alert(item.data.MenuName);
                        //if (item.data.Id == data['ParentId']) {
                        	//parentFiled.selectItem(records[index]);
                        	 //return false;//跳出循环
                        //}
                    //});
                    //alert("data['parentId']="+data['parentId']);
              			function processNodes(nodes) {
                      for (var i=0; i<nodes.length; i++) {//alert(nodes[i].data.id);
                          //nodes[i].title = nodes[i].text;
                      	if (nodes[i].data.id == data['parentId']) {
                      			parentFiled.selectItem(nodes[i]);
                          	return false;//跳出循环
                      	}else if (nodes[i].childNodes) {//alert(nodes[i].data.children);
                              processNodes(nodes[i].childNodes);
                      	}
                      }
                      return nodes;
                  	}
                  	processNodes(records);
                }
            });
            controllerStore.load({
                callback: function (records, operation, success) {
                    controllerCombox.setValue(data['controller']);
                }
            });
            if (data['buttons']) {
                actionMutiCombox.clearValue();
                actionMutiCombox.addValue(data['buttons'].split(','));
            }
        });
    }
});