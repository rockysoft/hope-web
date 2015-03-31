Ext.define('Hope.app.profile.profileinfo', {
    extend: 'Ext.ux.custom.HopeWindowInfoPanel',
    initComponent: function () {
        var me = this;

        Ext.define('ProfileInfo', {
            extend: 'Ext.data.Model',
            fields: [
                'Name', 'Email', 'ButtonAlign'
            ]
        });

        Ext.apply(this, {
            modelName: 'ProfileInfo',
            loadUrl: appBaseUri+'User/GetByUserID',
            saveUrl: appBaseUri+'User/ChangeProfile',
            dataId:-1,
            items: [
                { name: 'Name', xtype: 'textfield', fieldLabel: '姓名', allowBlank: false },
                { name: 'Email', xtype: 'textfield', fieldLabel: '邮箱', allowBlank: false },
                {
                    xtype: 'radiogroup',
                    fieldLabel: '保存按钮位置',
                    columns: 3,
                    vertical: true,
                    items: [
                        { boxLabel: '左边', name: 'ButtonAlign', inputValue: 'left' },
                        { boxLabel: '中间', name: 'ButtonAlign', inputValue: 'center' },
                        { boxLabel: '右边', name: 'ButtonAlign', inputValue: 'right', checked: true }
                    ]
                }
            ]
        });

        this.callParent(arguments);
    }
});