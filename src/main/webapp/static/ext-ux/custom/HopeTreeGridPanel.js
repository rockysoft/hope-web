/**
 * @author Nicolas Ferrero
 * A TabPanel with grouping support.
 */
Ext.define('Ext.ux.custom.HopeTreeGridPanel', {
    extend: 'Ext.tree.Panel',
    alias: 'widget.Hopetreegrid',
    xtype: 'tree-grid',
    initComponent: function () {
        var me = this;

        var UniqueID = me.cName + (me.cId ? me.cId : '') + (me.myId ? me.myId : '');

        this.cellEditing = Ext.create('Ext.grid.plugin.CellEditing', {
            clicksToEdit: 2
        });

        var tbarMenus = new Array();
        if (Hope.HaveActionMenu(me.cButtons, 'Add')) {
            tbarMenus.push({
                xtype: 'button',
                itemId: 'btnAdd',
                iconCls: 'icon_add',
                text: '添加',
                scope: this,
                disabled: !Hope.HaveAction(me.cName + 'Add'),
                handler: me.onAddClick
            });
        }
        if (Hope.HaveActionMenu(me.cButtons, 'Edit')) {
            tbarMenus.push({
                xtype: 'button',
                itemId: 'btnEdit',
                iconCls: 'icon_edit',
                text: '修改',
                scope: this,
                disabled: true,
                handler: me.onEditClick
            });
        }
        if (Hope.HaveActionMenu(me.cButtons, 'Delete')) {
            tbarMenus.push({
                xtype: 'button',
                itemId: 'btnDelete',
                iconCls: 'icon_delete',
                text: '删除',
                scope: this,
                disabled: true,
                handler: me.onDeleteClick
            });
        }
/*        
        if (Hope.HaveActionMenu(me.cButtons, 'Sync')) {
            tbarMenus.push({
                xtype: 'button',
                itemId: 'btnSync',
                iconCls: 'icon_cloud_upload',
                text: '同步更改的数据',
                scope: this,
                handler: me.onSyncClick
            });
        }
       
        if (Hope.HaveActionMenu(me.cButtons, 'Sync') && Hope.HaveAction(me.cName + 'Edit')) {
            tbarMenus.push('-');
            tbarMenus.push('提示：双击单元格可修改数据');
        }
*/ 
        var oldColumns = me.columns;
        var roleColumns = Hope.HaveAction('RoleAssignColumns') ? ['->', {
            xtype: 'button',
            itemId: 'btnColumnsAction',
            text: '列权限',
            scope: this,
            handler: function () {
                Ext.create('Hope.app.infrastructure.rolecolums', {
                    dataColumns: oldColumns,
                    UniqueID: UniqueID
                });
            }
        }] : [];

        tbarMenus = Ext.Array.merge(tbarMenus, me.tbarMenus, roleColumns);

        if (tbarMenus.length == 0) me.hideTBar = true;
        this.ttoolbar = Ext.create('Ext.toolbar.Toolbar', {
            hidden: me.hideTBar || false,
            items: tbarMenus
        });

        if (me.cName) {
            var newcolumns = [];
            var haveColumn = new Hope.HaveColumn(UniqueID);
            Ext.each(me.columns, function (item) {
                var have = haveColumn.have(item.dataIndex);
                if (have || have == -2) {
                    var newitem = Ext.apply(item, {
                        id: UniqueID + item.dataIndex
                    });
                    newcolumns.push(newitem);
                }
            });
            me.columns = newcolumns;
        }

        Ext.apply(this, {
            stateful: me.cName ? true : false,
            stateId: me.cName ? UniqueID + 'treegird' : null,
            enableColumnMove: me.cName ? true : false,
            plugins: Hope.HaveActionMenu(me.cButtons, 'Sync') && Hope.HaveAction(me.cName + 'Edit') ? [this.cellEditing] : this.plugins,
            selModel: Ext.create('Ext.selection.CheckboxModel'),
            border: false,
            useArrows: true,
            rootVisible: false,
            multiSelect: true,
            singleExpand: true,
            tbar: this.ttoolbar,
            viewConfig: {
                plugins: {
                    ptype: 'treeviewdragdrop',
                    appendOnly: false
                },
                listeners: {
                    'drop': this.onDragDrop
                }
            }
        });
        this.getSelectionModel().on('selectionchange', function (sm, records) {
            if (me.down('#btnEdit')) me.down('#btnEdit').setDisabled(sm.getCount() != 1 || !Hope.HaveAction(me.cName + 'Edit'));
            if (me.down('#btnDelete')) me.down('#btnDelete').setDisabled(sm.getCount() == 0 || !Hope.HaveAction(me.cName + 'Delete'));
        });

        this.callParent(arguments);
    },
    createStore: function (config) {
        Ext.applyIf(this, config);

        return Ext.create('Ext.data.TreeStore', {
            model: config.modelName,
            //autoDestroy: true,
            //autoLoad: true,
            proxy: {
                type: 'ajax',
                url: config.proxyUrl
            },
            sorters: [{
                property: config.sortProperty || 'id',
                direction: config.sortDirection || 'DESC'
            }]
        });
    },
    onDragDrop: function (node, data, overModel, dropPosition, eOpts) {
        Hope.log({
            movedId: data.records[0].getId(),
            referenceId: overModel.getId(),
            dropPosition: dropPosition
        });
    },
    onAddClick: function () {
    },
    onEditClick: function () {
    },
    onDeleteClick: function () {
        var me = this;
        Hope.confirmTip('删除的记录不可恢复，继续吗？', function (btn) {
            if (btn == 'yes') {
                var s = me.getSelectionModel().getSelection();
                var ids = [];
                for (var i = 0, r; r = s[i]; i++) {
                    ids.push(r.get('id'));
                }
                Ext.Ajax.request({
                    url: me.proxyDeleteUrl,
                    params: {
                        ids: ids.join(',')
                    },
                    //method:'delete',
                    success: function (response) {
                        if (response.responseText != '') {
                            var res = Ext.JSON.decode(response.responseText);
                            if (res.success) {
                                Hope.msgTip('操作成功！');
                                me.getStore().reload();
                            }
                            else
                                Hope.errTip(res.msg);
                        }
                    }
                });
            }
        });
    },
    onSyncClick: function () {
        var me = this;
        if (Ext.select('td[class*=x-grid-dirty-cell]').getCount() <= 0) {
            Hope.msgTip('没有需要同步的数据！');
            return;
        }
        if (me.down('#btnSync') == null) {
            me.getStore().sync({
                success: function () {
                    me.getStore().reload();
                    Hope.msgTip('操作成功！');
                },
                failure: function () {
                    Hope.errTip('数据同步失败！');
                }
            });
        }
        else {
            var orgText = me.down('#btnSync').getText();
            me.down('#btnSync').setText('数据同步中...').setDisabled(true);
            me.getStore().sync({
                success: function () {
                    me.getStore().reload();
                    Hope.msgTip('操作成功！');
                    me.down('#btnSync').setText(orgText).setDisabled(false); ;
                },
                failure: function () {
                    Hope.errTip('数据同步失败！');
                    me.down('#btnSync').setText(orgText).setDisabled(false);
                }
            });
        }
    }
});
