Ext.define('Hope.app.profile.changpwd', {
    extend: 'Ext.ux.custom.HopeWindowInfoPanel',
    initComponent: function () {
        var me = this;

        var pwdField = Ext.create('Ext.form.field.Text', { name: 'NewPwd', fieldLabel: '新密码', inputType: 'password', allowBlank: false });

        Ext.apply(this, {
            saveUrl: appBaseUri+'account/users/changPwd', //'../User/ChangePwd',
            items: [
                { name: 'Pwd', xtype: 'textfield', inputType: 'password', fieldLabel: '原密码', allowBlank: false },
                pwdField,
                { name: 'confirm_new_pwd', xtype: 'textfield', inputType: 'password', vtype: 'confirmpwd', firstPassField: pwdField, fieldLabel: '重复新密码', allowBlank: false }
            ]
        });

        this.callParent(arguments);
    }
});