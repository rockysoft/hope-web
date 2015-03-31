/*
角色管理面板
*/
Ext.define('Hope.app.sys.roles', {
    extend: 'Ext.panel.Panel',
    initComponent: function () {
        var me = this;

        var tabs = Ext.widget('tabpanel', {
            activeTab: 0,
            plain: true,
            region: 'center',
            margins: '1 0 0 1',
            autoScroll: true,
            items: [Ext.create('Hope.app.sys.menulist'), Ext.create('Hope.app.sys.actiongird')]
        });

        Ext.apply(this, {
            layout: 'border',
            items: [Ext.create('Hope.app.sys.rolesgrid', {
                cButtons: me.cButtons,
                cName: me.cName
            }), tabs]
        });

        this.callParent(arguments);
    }
});

/*
角色列表
*/
Ext.define('Hope.app.sys.rolesgrid', {
    extend: 'Ext.ux.custom.HopeGridPanel',
    id: 'roleGrid',
    region: 'west',
    width: '30%',
    initComponent: function () {
        var me = this;

        me.curItemValue = 0;

        Ext.define('RoleList', {
            extend: 'Ext.data.Model',
            idProperty: 'id',
            fields: [
                { name: 'id', mapping: 'id', type: 'int' },
                { name: 'name', mapping: 'name', type: 'string' },
                { name: 'description', mapping: 'description', type: 'string' }
                //{ name: 'AddTime', type: 'date', dateFormat: 'Y-m-d H:i:s' },
               // { name: 'LastEditTime', type: 'date', dateFormat: 'Y-m-d H:i:s' },
               // 'AddName', 'LastEditName'
            ]
        });

        var store = me.createStore({
            modelName: 'RoleList',
            proxyUrl: appBaseUri+ 'account/roles',//'Role/GetAll',
            proxyDeleteUrl: appBaseUri+'account/roles'
        });

        var columns = [{
            text: "角色名称",
            dataIndex: 'name',
            flex: 1,
            sortable: false,
            editor: {
                allowBlank: false
            }
        }, {
            text: "角色代码",
            dataIndex: 'description',
            flex: 1,
            sortable: false,
            editor: {
                allowBlank: false
            }
        }/*, {
            text: "创建时间",
            dataIndex: 'AddTime',
            width: globalDateColumnWidth,
            hidden: true,
            sortable: true,
            xtype: 'datecolumn',
            format: 'Y-m-d H:i:s'
        }, {
            text: "创建人",
            dataIndex: 'AddName',
            width: 120,
            hidden: true,
            sortable: true
        }, {
            text: "最后修改时间",
            dataIndex: 'LastEditTime',
            width: globalDateColumnWidth,
            hidden: true,
            sortable: true,
            xtype: 'datecolumn',
            format: 'Y-m-d H:i:s'
        }, {
            text: "修改人",
            dataIndex: 'LastEditName',
            width: 120,
            hidden: true,
            sortable: true
        }*/];

        Ext.apply(this, {
            store: store,
            columns: columns,
            hideBBar: true,
            idProperty: 'id',
            listeners: {
                'itemclick': function (item, record) {
                    me.curItemValue = record.get('id');
                    Ext.getCmp('actionGird').getStore().load();
                    Ext.getCmp('roleMenu').getStore().load({
                        params: {
                            'roleId': me.curItemValue
                        }
                    });
                    
                }
            }
        });

        this.callParent(arguments);
        store.load();
    },
    onAddClick: function () {
        var me = this;
        Hope.openWindow('添加角色', 'sys.roleinfo', 360, {
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
            Hope.openWindow('修改角色', 'sys.roleinfo', 360, {
                dataId: record.get("id"),
                onSaveSuccess: function (action) {
                    me.getStore().reload();
                }
            });
        }
    }
});

/*
树形菜单
*/
Ext.define('Hope.app.sys.menulist', {
    extend: 'Ext.tree.Panel',
    id: 'roleMenu',
    initComponent: function () {
        var me = this;

        var store = Ext.create('Ext.data.TreeStore', {
            autoLoad: true,
            proxy: {
                type: 'ajax',
                url: appBaseUri+'account/menus/GetAll', //'RoleMenu/GetAll', //'account/roles/'+r.get('roleId')+'/menus'
                reader: {
                    type: 'json',
                    root: 'children'
                }
            }
        });

        Ext.apply(this, {
            title: '菜单权限',
            border: false,
            store: store,
            rootVisible: false,
            tbar: [{
                xtype: 'button',
                iconCls: 'icon_save',
                text: '保存选择好的菜单权限',
                scope: this,
                handler: me.SaveMenuPermission
            }]
        });
        this.listeners= {  
			"checkchange":Ext.bind(this.checkChange,this)
		};

        this.callParent(arguments);
    },
    
    checkChange:function(node, checked, eOpts){
    	/*
        if (checked == true) {
            node.checked = checked;
            //获得所有叶子节点,并将其选中状态与当前节点同步
            var childNodes = node.childNodes;
            for(var i=0;i<childNodes.length;i++){
                var child = childNodes[i];
                if(child.get("leaf")){
                    child.set("checked", true);
                }
            }
        }
        
       //当前节点取消选中时，其叶子节点状态与其级联
       if (!node.get("leaf") && !checked){
           node.cascadeBy(function(node){
               node.set('checked', checked);
           });
       }
       */
    		if (checked) {
    			if (node.parentNode && node.parentNode.id!='-root')
                this.checkParentNode(node.parentNode, checked);
            }

            this.checkChildNode(node, checked);
    },
    
    checkChildNode:function(node, checked){
    		if (node == undefined) {
                return;
            }
             node.cascadeBy(function(node){
               node.set('checked', checked);
           });
    },
    
    checkParentNode:function(node, checked) {
            if (!node.get("checked")) {
            	node.set('checked', true);
            	if (node.parentNode && node.parentNode.id!='-root')
            		this.checkParentNode(node.parentNode, checked);
            }
    },
    //保存权限
    SaveMenuPermission: function () {
        var me = this;
        var roleId = Ext.getCmp('roleGrid').curItemValue;
        if (!roleId) {
            Hope.infoTip('请先选择角色');
            return;
        };
        var s = me.getChecked();
        var ids = [];
        for (var i = 0, r; r = s[i]; i++) {
            if(r.get('id') != 'root') ids.push(r.get('id'));
        }

        me.setLoading('权限保存中...');
        Ext.Ajax.request({
            url: appBaseUri+'account/roles/'+roleId+'/menus', //'Role/SaveRoleMenu',//appBaseUri+'account/roles/'+roleId+'/permissions',
            params: {
                menuIds: ids.join(',')
//                RoleId: RoleID
            },
            success: function (response) {
                me.setLoading(false);
                var res = Ext.JSON.decode(response.responseText);
                if (res && !res.success)
                    Ext.Msg.alert('Error', res.msg);
                else {
                    Hope.msgTip('保存成功！');
                }
            },
            failure: function (response, opts) {
                me.setLoading(false);
            }
        });
    }
});

/*
权限列表
*/
Ext.define('Hope.app.sys.actiongird', {
    extend: 'Ext.grid.Panel',
    requires: [
        'Ext.ux.grid.FiltersFeature'
    ],
    id: 'actionGird',
    //oldActions: [],
    //selectActions: [],
    //unselectActions: [],
    initComponent: function () {
        var me = this;

        var filters = {
            ftype: 'filters',
            encode: true,
            filters: [{
                type: 'string',
                dataIndex: 'name'
            }, {
                type: 'string',
                dataIndex: 'description'
            }]
        }

        var columns = [{
            text: "权限名称",
            dataIndex: 'name',
            width: 200,
            sortable: true
        }, {
            text: "权限描述",
            dataIndex: 'description',
            flex: 1
        }];

        var store = me.createStore();
        var firstLoaded = false;
        var sm = Ext.create('Ext.selection.CheckboxModel');
        Ext.apply(this, {
            title: '功能权限',
            border: false,
            store: store,
            features: [filters],
            columns: columns,
            selModel: sm,//Ext.create('Ext.selection.CheckboxModel'),
            border: false,
            enableColumnMove: false,
            tbar: [{
                xtype: 'button',
                iconCls: 'icon_save',
                text: '保存选择好的功能权限',
                scope: this,
                handler: me.SaveActionPermission
            }],
            bbar: Ext.create('Ext.PagingToolbar', {
                store: store,
                displayInfo: true,
                displayMsg: '显示从{0}条数据到{1}条数据，共{2}条数据',
                emptyMsg: "没有数据"
            }),
            listeners: {
                'select': function (cur, record) {
                    //me.selectActions.push(record.get('permissionId'));
                },
                'deselect': function (cur, record) {
                    //me.unselectActions.push(record.get('permissionId'));
                },
                'activate': function () {
                    if (!firstLoaded) {
                        store.load();
                        firstLoaded = true;
                    }
                }
            }
        });

        this.callParent(arguments);
    },
    createStore: function () {
        var me = this;

        Ext.define('ActionList', {
            extend: 'Ext.data.Model',
            idProperty: 'id',
            fields: [
                { name: 'id', type: 'int' },
                'name', 'description'
            ]
        });

        return Ext.create('Ext.data.Store', {
            model: 'ActionList',
            autoDestroy: true,
            remoteSort: true,
            pageSize: globalPageSize,
            proxy: {
                type: 'ajax',
                url: appBaseUri+'account/permissions',
                reader: {
                    type: 'json',
                    root: 'root',//'data',
                    totalProperty: 'total'
                }
            },
           // sorters: [{
          //      property: 'permissionUrl',
           //     direction: 'DESC'
           // }],
            listeners: {
                'load': function (store, records) {
                    me.onChangeChecked(store, records);
 
                }
            }
        });
    },
    onChangeChecked: function (store, records) {
        var me = this;
		var actionSM = me.getSelectionModel();
		if (actionSM.hasSelection())
			actionSM.deselectAll();
/*
        Ext.each(records, function (item, itemIndex) {
            me.getView().onRowDeselect(itemIndex);
        });
*/
        var sm = Ext.getCmp('roleGrid').getSelectionModel();
        if (sm.hasSelection()) {
            var r = sm.getSelection()[0];
            Ext.Ajax.request({
                url: appBaseUri+'account/roles/'+r.get('id')+'/permissions',
              //  params: {
              //      'roleId': r.get('roleId')
              //  },
                success: function (response) {
                    var res = Ext.JSON.decode(response.responseText);
                    if (res == null) return;
                    try {
                        var oldActions = [];//存放选中记录
                        Ext.each(res, function (item, itemIndex) {
                            var index = me.getStore().findBy(function (record, id) {
                                return record.get('id') == item.id;
                            });
                            //records1.push(me.getStore().getAt(index));
                            //oldActions.push(item.permissionId);
                            oldActions.push(me.getStore().getAt(index));
                        });
   						me.getSelectionModel().select(oldActions);//执行选中记录			
                        //me.oldActions = oldActions
                    } catch (e) {

                    }
                }
            });
        }
    },
    //保存权限
    SaveActionPermission: function () {
        var me = this;
        var roleId = Ext.getCmp('roleGrid').curItemValue;
        if (!roleId) {
            Hope.infoTip('请先选择角色');
            return;
        };
/*
        var selects = Ext.Array.unique(me.selectActions); //去掉所选重复数据
        
        var unselects = Ext.Array.unique(me.unselectActions); //去掉反选重复数据
        //合并需要保存的数据并返回不重复的数组
        var saves = Ext.Array.merge(me.oldActions, selects);
        //从要保存的数组里删除不需要保存的数组
        Ext.Array.forEach(unselects, function (item, index) {
            //删除
            Ext.Array.remove(saves, item);
        });
*/
records = me.getSelectionModel().getSelection();
var ids = []; 
Ext.each(records, function(r){
   ids.push(r.get("id")); // 根据唯一标识的属性名取值 
});

        me.setLoading('权限保存中...');
        Ext.Ajax.request({
            url: appBaseUri+'account/roles/'+roleId+'/permissions',
            params: {
                permissionIds: ids.join(',')
                //roleId: roleId
            },
            success: function (response) {
                var res = Ext.JSON.decode(response.responseText);
                if (res && !res.success)
                    Ext.Msg.alert('Error', res.msg);
                else {
                    Hope.msgTip('保存成功！');
                    me.setLoading(false);
                }
            },
            failure: function (response, opts) {
                me.setLoading(false);
            }
        });
    }
});

/*
添加/修改角色
*/
Ext.define('Hope.app.sys.roleinfo', {
    extend: 'Ext.ux.custom.HopeWindowInfoPanel',
    initComponent: function () {
        var me = this;

        Ext.define('RoleInfo', {
            extend: 'Ext.data.Model',
            fields: [
                'name', 'description',
                { name: 'id', type: 'int' }
            ]
        });

        Ext.apply(this, {
            modelName: 'RoleInfo',
            loadUrl: appBaseUri+'account/roles',//'Role/GetExtForm',
            updateUrl: appBaseUri+'account/roles',//'Role/Save',
            saveUrl: appBaseUri+'account/roles',//'Role/Save',
            items: [
                { xtype: 'hiddenfield', name: 'id' },
                { name: 'name', xtype: 'textfield', fieldLabel: '角色名称', allowBlank: false },
                { name: 'description', xtype: 'textfield', fieldLabel: '角色代码', allowBlank: false }
            ]
        });

        this.callParent(arguments);
    }
});