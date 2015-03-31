/**
 * @author Nicolas Ferrero
 * A TabPanel with grouping support.
 */
Ext.define('Ext.ux.custom.HopeWindowInfoPanel', {
    extend: 'Ext.form.Panel',
    alias: 'widget.Hopewinform',
    initComponent: function () {
        var me = this;
        me.dataId = me.dataId == undefined ? 0 : me.dataId;

        var reader = Ext.create('Ext.data.JsonReader', {
            model: me.modelName,
            root: 'data'
        });

        var saveBtnText = me.saveBtnText || '保存';

        Ext.apply(this, {
            bodyPadding: 10,
            border: false,
            reader: reader,
            jsonSubmit:true,
            fieldDefaults: {
                labelWidth: 90
            },
            buttonAlign:idata.myInfo.buttonAlign || 'right',
            buttons: [{
                text: saveBtnText+'并关闭',
                iconCls: 'icon_list',
                scope: this,
                handler: function () {
                    me.onSaveClick(true);
                }
            }, {
                text: saveBtnText,
                iconCls: 'icon_save',
                scope: this,
                handler: function () {
                    me.onSaveClick(false);
                }
            }, {
                text: '取消',
                iconCls: 'icon_delete',
                handler: function () {
                    me.up('window').close();
                }
            }]
        });

        this.callParent(arguments);

        this.on('boxready', function () {
            if (me.loadUrl && me.dataId != 0) {
                me.getForm().waitMsgTarget = me.getEl();
                me.load({
                    url: me.loadUrl+'/'+me.dataId, method : 'GET',
                    //params: {
                     //   'id': me.dataId
                    //},
                    waitMsg: '数据载入中，请稍候...'
                });
            }
        });
        this.on('render', function (panel) {
            panel.el.on('keypress', function (e) {
                if (e.getKey() == e.ENTER) {
                    me.onSaveClick();
                }
            });
        });
    },
    onSaveClick: function (closeWindow) {
        var me = this;
        if (me.getForm().isValid()) {
            me.getForm().waitMsgTarget = me.getEl();
            //alert(me.dataId == 0 ? me.saveUrl : me.updateUrl+'/'+me.dataId);
            var url = me.dataId == 0 ? me.saveUrl : me.updateUrl+'/'+me.dataId;
            var method = me.dataId == 0 ? "POST" : "PUT";
            //alert(url);
            me.getForm().submit({
            		//headers: {'Content-Type':'application/json; charset=utf-8'},
                url: url,
                method : method,
                submitEmptyText:false,
                waitMsg: '保存中，请稍候...',
                success: function (form, action) {
                    Hope.msgTip('保存成功！');
                    if (closeWindow) me.up('window').close();
                    if (me.onSaveSuccess) me.onSaveSuccess(action);
                },
                failure: function (form, action) {
                    Hope.errTip(action.result.msg);
                }
            });
        }
    }
});
