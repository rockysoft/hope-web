Ext.define('Hope.app.profile.changepwd', {
    extend: 'Ext.ux.custom.HopeWindowInfoPanel',
    initComponent: function () {
        var me = this;

        var pwdField = Ext.create('Ext.form.field.Text', { name: 'newPassword', fieldLabel: '新密码', inputType: 'password', allowBlank: false });

        Ext.apply(this, {
            saveUrl: appBaseUri+'account/users/changePwd', //'../User/ChangePwd',
            items: [
                { name: 'oldPassword', xtype: 'textfield', inputType: 'password', fieldLabel: '原密码', allowBlank: false },
                pwdField,
                { name: 'confirmPassword', xtype: 'textfield', inputType: 'password', vtype: 'confirmpwd', firstPassField: pwdField, fieldLabel: '重复新密码', allowBlank: false }
            ]
        });

        this.callParent(arguments);
    }
});