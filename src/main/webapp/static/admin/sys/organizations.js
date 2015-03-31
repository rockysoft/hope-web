/*
组织机构管理面板
*/
Ext.define('Hope.app.sys.organizations', {
    extend: 'Ext.ux.custom.HopeTreeGridPanel',
    initComponent: function () {
        var me = this;

        Ext.define('organizationList', {
            extend: 'Ext.data.Model',
            idProperty: 'id',
            fields: [
                { name: 'id', type: 'int' },
                { name: 'parentId', type: 'int' },
                { name: 'sort', type: 'int' },
                'name', "description",
                { name: 'AddTime', type: 'date', dateFormat: 'Y-m-d H:i:s' },
                { name: 'LastEditTime', type: 'date', dateFormat: 'Y-m-d H:i:s' },
                'AddName', 'LastEditName'
            ]
        });

        var store = me.createStore({
            modelName: 'organizationList',
            proxyUrl: appBaseUri+'account/organizations',//'Team/GetList'
            sortProperty: 'ID1',//没有这个属性，即不本地排序，按json数据的顺序显示。
            sortDirection: 'DESC'
        });

        var columns = [{
            xtype: 'treecolumn',
            text: "机构名称",
            dataIndex: 'name',
            flex: 1,
            sortable: false,
            editor: {
                allowBlank: false
            }
        }, {
            text: "描述",
            dataIndex: 'description',
            width: 300,
            sortable: true,
            editor: {
                allowBlank: false
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
            columns: columns,
            proxyDeleteUrl: appBaseUri+'account/organizations/remove',//'Team/Delete'
        });

        this.callParent(arguments);
    },
    onAddClick: function () {
        var me = this;
        Hope.openWindow('添加机构', 'sys.organizationinfo', 380, {
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
            Hope.openWindow('修改机构', 'sys.organizationinfo', 380, {
                dataId: record.get("id"),
                onSaveSuccess: function (action) {
                    me.getStore().reload();
                }
            });
        }
    }
});

/*
添加/修改机构
*/
Ext.define('Hope.app.sys.organizationinfo', {
    extend: 'Ext.ux.custom.HopeWindowInfoPanel',
    initComponent: function () {
        var me = this;

        Ext.define('ModelInfo', {
            extend: 'Ext.data.Model',
            fields: [
                'name', 'description', 'parentName',
                { name: 'parentId', type: 'int' },{ name: 'sort', type: 'int' },
                { name: 'id', type: 'int' }
            ]
        });

        var parentOrganizationFiled = Ext.create('Ext.ux.TreePicker', {
            name: 'parentId',
            fieldLabel: '上级机构',
            //url: appBaseUri+'account/organizations',//'Team/GetList',
            selectParentNode:true,
            displayField: 'name',
            valueField: 'id',
            emptyText: '不选则为顶级机构',
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

        Ext.apply(this, {
            modelName: 'ModelInfo',
            loadUrl: appBaseUri+'account/organizations',//'Team/GetExtForm',
            updateUrl: appBaseUri+'account/organizations',
            saveUrl: appBaseUri+'account/organizations',//'Team/Save',
            defaults: {
                anchor: '100%'
            },
            items: [
                { xtype: 'hiddenfield', name: 'id' },parentOrganizationFiled,
                { name: 'name', xtype: 'textfield', fieldLabel: '机构名称', allowBlank: false },
                { name: 'description', xtype: 'textareafield', fieldLabel: '描述' },
                { name: 'sort', xtype: 'textfield', fieldLabel: '排序', allowBlank: false}
            ]
        });

        this.callParent(arguments);

        me.form.on('beforeSetafterLoad', function (data) {
            //parentOrganizationFiled.setFieldValue(data['parentId'], data['parentName']);
            parentOrganizationFiled.setValue(data['parentId']);
        });
    }
});