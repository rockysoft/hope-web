Ext.define('Hope.app.sys.users', {
    extend: 'Ext.panel.Panel',
    initComponent: function () {
        var me = this;

        var organizationTreeStore = Ext.create('Ext.data.TreeStore', {
            autoLoad: true,
            fields: [
                { name: 'id', type: 'int', mapping: 'id' },
                { name: 'text', type: 'string', mapping: 'name' }
            ],
            proxy: {
                type: 'ajax',
                url: appBaseUri + 'account/organizations',//'Team/GetList',
                reader: {
                    type: 'json',
                    root: 'children'
                }
            }, root: {
                text: '所有部门'
            }
        });

        var userlist = Ext.create('Hope.app.sys.userlist', {
            cButtons: me.cButtons,
            cName: me.cName
        });

        Ext.apply(this, {
            border: false,
            layout: 'border',
            items: [{
                region: 'west',
                title: '按机构查看',
                width: 180,
                style: 'border-right:1px #ccc solid;',
                ui: 'light',
                xtype: 'treepanel',
                rootVisible: false,
                store: organizationTreeStore,
                listeners: {
                    'itemclick': function (e, record) {
                        userlist.getStore().getProxy().extraParams.organizationId = record.data.id || -1;
                        userlist.getStore().loadPage(1);
                    }
                }
            }, userlist]
        });

        this.callParent(arguments);
    }
});

Ext.define('Hope.app.sys.userlist', {
    extend: 'Ext.ux.custom.HopeGridPanel',
    region: 'center',
    initComponent: function () {
        var me = this;

        Ext.define('ModelList', {
            extend: 'Ext.data.Model',
            idProperty: 'id',
            fields: [
             {name: 'id', mapping: 'id', type: 'int'},
             {name: 'loginName', mapping: 'loginName', type: 'string'},
             {name: 'realName', mapping: 'realName', type: 'string'},
             {name: 'errorCount', mapping: 'errorCount', type: 'int'},
             {name: 'sex', mapping: 'sex', type: 'int'},
             {name: 'email', mapping: 'email', type: 'string'},
             {name: 'mobile', mapping: 'mobile', type: 'string'},
             {name: 'officePhone', mapping: 'officePhone', type: 'string', persist: true},
             //{name: 'lastLoginTime', mapping: 'lastLoginTime', type: 'string'},
             {name: 'lastLoginTime', mapping: 'lastLoginTime', type: 'date', dateFormat:'c'},
             {name: 'lastLoginIp', mapping: 'lastLoginIp', type: 'string'},
             {name: 'status', mapping: 'status', type: 'string'}
    				]
        });

        var store = me.createStore({
            modelName: 'ModelList',
            proxyUrl: appBaseUri+'account/users',//'User/GetPage',
            proxyDeleteUrl: appBaseUri+'account/users/remove',//'User/Delete',
            extraParams: me.extraParams
        });

        var filters = {
            ftype: 'filters',
            encode: true,
            filters: [{
                type: 'string',
                dataIndex: 'loginName'
            }, {
                type: 'string',
                dataIndex: 'realName'
            }, {
                type: 'string',
                dataIndex: 'email'
            }, {
                type: 'string',
                dataIndex: 'lastLoginIp'
            }, {
                type: 'date',
                dataIndex: 'lastLoginTime'
            }, {
                type: 'date',
                dataIndex: 'AddTime'
            }, {
                type: 'string',
                dataIndex: 'AddName'
            }, {
                type: 'date',
                dataIndex: 'LastEditTime'
            }, {
                type: 'string',
                dataIndex: 'LastEditName'
            }]
        }

        var columns = [{
            text: "账号",
            dataIndex: 'loginName',
            width: 120,
            sortable: true,
            editor: {}
        }, {
            text: "姓名",
            dataIndex: 'realName',
            width: 120,
            sortable: true,
            editor: {}
        },
        /*
        {
					text: '登录失败次数',
					editor: {
						xtype: 'numberfield',
						minValue: 1,
						maxValue: 100,
						hideTrigger: true
					},
					dataIndex: 'errorCount'
        },
        */
        {
					text: '性别',
					editor: 'textfield',
					dataIndex: 'sex',
					renderer:this.changeSex
        }, {
            text: "电子邮箱",
            dataIndex: 'email',
            width: 200,
            sortable: true,
            editor: {}
        },{
					text: '账户状态',
					editor: 'textfield',
					dataIndex: 'status',
					renderer:this.changeStatus
        }, {
            text: "最后登录时间",
            dataIndex: 'lastLoginTime',
            width: globalDateColumnWidth,
            sortable: true,
            xtype: 'datecolumn',
            //renderer : Ext.util.Format.dateRenderer('Y-m-d H:i:s')
            format: 'Y-m-d H:i:s'
        }, {
            text: "最后登录IP",
            dataIndex: 'lastLoginIp'
        }, {
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
        }];

        Ext.apply(this, {
            store: store,
            features: [filters],
            columns: columns
        });

        this.callParent(arguments);
        store.load();
    },
    changeSex : function(value){
    	if (value == '1') {
        return "<span style='color:red;font-weight:bold;'>男</span>";
    	} else if (value == '2') {
        return "<span style='color:green;font-weight:bold;'>女</span>";
    	} else {
        return "<span style='color:black;font-weight:bold;'>未知</span>";
    	}
  	},
    changeStatus : function(value){
    	if (value == '0') {
        return "<span style='color:black;font-weight:bold;'>未激活</span>";
    	} else if (value == '1') {
        return "<span style='color:green;font-weight:bold;'>正常</span>";
    	} else if (value == '-1') {
        return "<span style='color:yellow;font-weight:bold;'>锁定</span>";
    	} else if (value == '-2') {
        return "<span style='color:red;font-weight:bold;'>禁用</span>";
    	}
  	},  	
    onAddClick: function () {
        var me = this;
        Hope.openWindow('添加用户', Ext.create('Hope.app.sys.userinfo', {
            onSaveSuccess: function (action) {
                me.getStore().reload();
            }
        }), 480);
    },
    onEditClick: function () {
        var me = this;
        var selections = this.getSelectionModel().getSelection();
        if (selections.length == 1) {
            var record = selections[0];
            Hope.openWindow('修改用户', Ext.create('Hope.app.sys.userinfo', {
                dataId: record.get("id"),
                onSaveSuccess: function (action) {
                    me.getStore().reload();
                }
            }), 480);
        }
    }
});

Ext.define('Hope.app.sys.userinfo', {
    extend: 'Ext.ux.custom.HopeWindowInfoPanel',
    initComponent: function () {
        var me = this;

        Ext.define('ModelInfo', {
            extend: 'Ext.data.Model',
            fields: [
                { name: 'id', type: 'int' },
                'loginName', 'realName', 'email', 'sex', 'roles', 'orgId'//'organization'
            ]
        });

        Ext.define('RoleList', {
            extend: 'Ext.data.Model',
            idProperty: 'id',
            fields: [
            	{ name: 'id', type: 'int' },
            	'name'
            ]
        });

        var rolesStore = Ext.create('Ext.data.Store', {
            model: 'RoleList',
            remoteSort: true,
            pageSize: globalPageSize,
            proxy: {
                type: 'ajax',
                url: appBaseUri+'account/roles',//'Role/GetAll',
                reader: {
                    type: 'json',
                    root: 'root',
                    totalProperty: 'total'
                }
            },
            sorters: [{
                property: 'id',
                direction: 'DESC'
            }]
        });

        var organizationFiled = Ext.create('Ext.ux.TreePicker', {
            name: 'orgId',
            fieldLabel: '所属部门',
            //url: appBaseUri+'account/organizations',//'Team/GetList',
            //selectParentNode: true,
            displayField: 'name',
            valueField: 'id',
            emptyText: '请选择...',
            anchor: '80%',            
            //allowBlank: false,
            //rootVisible: false,
            minPickerHeight: 200,
            //margin: '100 0 0 150',

            store: Ext.create('Ext.data.TreeStore',{
				fields: ['id','name'],
				root: {
					name: '所有部门',
					expanded: true
				},
				proxy: {
					type: 'ajax',
					url: appBaseUri+'account/organizations',//'app/data/tree.json',
					reader: {
						type: 'json',
						root: 'children'
					}
				}
			})

        });
        
        var rolesField = Ext.create('Ext.ux.form.BoxSelect', {
            fieldLabel: "角色",
            name:'roles',
            displayField: "name",
            valueField: "id",
            anchor: '100%',
            emptyText: "请选择角色",
            store: rolesStore,
            queryMode: "remote",
            allowBlank: false
        });

        var pwdField = Ext.create('Ext.form.field.Text', { name: 'password', fieldLabel: '密码', inputType: 'password', allowBlank: me.dataId > 0 ? true : false });

        Ext.apply(this, {
            modelName: 'ModelInfo',
            loadUrl: appBaseUri+'account/users',//'User/GetExtForm',
            updateUrl: appBaseUri+'account/users',
            saveUrl: appBaseUri+'account/users',//'User/Save',
            items: [
                { xtype: 'hiddenfield', name: 'id' },
                {
                    layout: 'column',
                    border: false,
                    defaults: {
                        defaults: { xtype: 'textfield' }
                    },
                    items: [{
                        columnWidth: .5,
                        layout: 'form',
                        padding: '0 20 0 0',
                        border: false,
                        items: [
                            { name: 'loginName', fieldLabel: '账号', allowBlank: false, readOnly: me.dataId > 0 },
                            pwdField
                        ]
                    }, {
                        columnWidth: .5,
                        layout: 'form',
                        border: false,
                        items: [
                            { name: 'realName', fieldLabel: '姓名', allowBlank: false },
                            { name: 'confirmPassword', fieldLabel: '确认密码', inputType: 'password', vtype: 'confirmpwd', firstPassField: pwdField, allowBlank: me.dataId > 0 ? true : false }
                        ]
                    }]
                },
                { name: 'email', xtype: 'textfield', fieldLabel: '电子邮箱', vtype: 'email', anchor: '80%', allowBlank: false },
                {
            xtype: 'radiogroup',anchor: '80%',
            fieldLabel: '性别',
           // cls: 'x-check-group-alt',
            items: [
                {boxLabel: '男', name: 'sex', inputValue: 1},
                {boxLabel: '女', name: 'sex', inputValue: 2},
                {boxLabel: '保密', name: 'sex', inputValue: 0, checked: true}
            ]
        },
                organizationFiled,
                rolesField
            ]
        });

        this.callParent(arguments);
				rolesStore.load();
        me.form.on('beforeSetafterLoad', function (data) {//alert(data['orgId']);
            //organizationFiled.setFieldValue(data['organization'].id, data['organization'].name);
            organizationFiled.setValue(data['orgId']);
            if (data['roles']) {
                rolesField.clearValue();
                rolesField.addValue(data['roles']);
            }
            if (data['roles']) {
                //actionMutiCombox.clearValue();
                //actionMutiCombox.addValue("Add,Edit".split(','));
            }         
        });
    }
});