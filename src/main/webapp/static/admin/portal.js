
Ext.define('Hope.app.portal', {
    extend: 'Ext.ux.portal.PortalPanel',
    initComponent: function () {
        var me = this;

        Ext.apply(this, {
            autoScroll: true,
            layout : 'column',
            defaults: {
                defaults: {
                   // ui: 'light',
                   // closable: false
                }
            },
            items: [{
									xtype : 'portalcolumn',									
									columnWidth : 0.7,
									items : [{
												title : '新闻动态',
												height : 150,
												iconCls : 'icon-news'
											}, {
												title : '最新通知',
												height : 150,
												iconCls : 'icon-notice'
											}, {
												title : '业绩报表',
												height : 150,
												iconCls : 'icon-chart'
											}, {
												title : '邮件列表',
												height : 150,
												iconCls : 'icon-email-list'
											}]
								}, {
									xtype : 'portalcolumn',
									columnWidth : 0.3,
									items : [{
												title : '功能链接',
												height : 150,
												iconCls : 'icon-link'
											}, {
												title : '待办事项',
												height : 150,
												iconCls : 'icon-note'
											}, {
												title : '邮件列表',
												height : 150,
												iconCls : 'icon-email-list'
											}, {
												title : '邮件列表',
												height : 150,
												iconCls : 'icon-email-list'
											}]
								}],
            //isReLayout: false,
            listeners: {
                'drop': function () {
                    Ext.Ajax.request({
                        url: '../UserPortal/SavePortal',
                        params: {
                            PageCode: 'Home',
                            PortalJson: me.getJsonPortal()
                        },
                        success: function (response) {
                            var res = Ext.JSON.decode(response.responseText);
                            if (res && !res.success)
                                Ext.Msg.alert('Error', res.msg);
                        }
                    });
                },
                'beforeadd': function () {
                    if (this.isReLayout) return;
                    this.isReLayout = true;
                    Ext.Ajax.request({
                        url: 'data/GetPortal',
                        params: {
                            PageCode: 'Home'
                        },
                        success: function (response) {
                            if (response.responseText != '') {
                                var res = Ext.JSON.decode(response.responseText);
                                me.setPortal(res);
                            }
                        }
                    });
                }
            }
        });

        this.callParent(arguments);
    }
});