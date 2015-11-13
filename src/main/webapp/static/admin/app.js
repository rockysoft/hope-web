Ext.Ajax.timeout = 60000;
Ext.Loader.setConfig({
    enabled: true
});
Ext.require([
    'Ext.util.History',
    'Ext.ux.statusbar.StatusBar',
    'Ext.ux.portal.PortalPanel',
    'Ext.ux.TabScrollerMenu',
    'Ext.state.*'
]);

var mainTab,
    //全局分页大小
    globalPageSize=20,
    //全局时间列宽度
    globalDateColumnWidth=160;

	getTools = function () {
        return [{
            xtype: 'tool',
            type: 'gear',
            handler: function(e, target, header, tool){
                var portlet = header.ownerCt;
                portlet.setLoading('Loading...');
                Ext.defer(function() {
                    portlet.setLoading(false);
                }, 2000);
            }
        }];
    };

Ext.onReady(function () {
		Ext.BLANK_IMAGE_URL = "static/images/s.gif";
    Ext.QuickTips.init();
    Ext.History.init();
    //Ext.state.Manager.setProvider(Ext.create('Ext.ux.custom.HttpProvider'));

    var tokenDelimiter = ':';

    var mainPortal = Ext.create('Hope.app.portal', {
        title: '起始页',iconCls : 'icon-activity'
    });

    /*
    var mainCalendar = Ext.create('Hope.app.calendar', {
    title: '日程管理'
    });*/

	var content = '<div class="portlet-content"></div>';

    mainTab = Ext.create('Ext.TabPanel', {
        region: 'center',
        margins: '2 0 0 0',
        deferredRender: false,
        activeTab: 0,
        enableTabScroll : true,
			animScroll : true,
			border : true,
			autoScroll : true,
			split : true,
        plugins: [Ext.create('Ext.ux.TabReorderer'), Ext.create('Ext.ux.TabCloseMenu', {
		        		  	closeTabText: '关闭面板',
		        		  	closeOthersTabsText: '关闭其他',
		        		  	closeAllTabsText: '关闭所有'
		        		  })/*, {
            ptype: 'tabscrollermenu',
            maxText: 15,
            pageSize: 5
        }*/
        					
        ],
        items: [{
						iconCls : 'icon-activity',
						title : '平台首页',
						xtype : 'portalpanel',
						layout : 'column',
						items: [{
                        id: 'col-1',
                        items: [{
                            id: 'portlet-1',
                            title: 'Grid Portlet',
                            tools: this.getTools(),
                            items: Ext.create('Ext.ux.portal.GridPortlet'),
                            listeners: {
                                'close': Ext.bind(this.onPortletClose, this)
                            }
                        },{
                            id: 'portlet-2',
                            title: '待办事项',
                            tools: this.getTools(),
                            html: content,
                            listeners: {
                                'close': Ext.bind(this.onPortletClose, this)
                            }
                        }]
                    },{
                        id: 'col-2',
                        items: [{
                            id: 'portlet-21',
                            title: '我的消息',
                            tools: this.getTools(),
                            html: '<div class="portlet-content"></div>',
                            listeners: {
                                'close': Ext.bind(this.onPortletClose, this)
                            }
                        },{
                            id: 'portlet-22',
                            title: 'Stock Portlet',
                            tools: this.getTools(),
                            items: Ext.create('Ext.ux.portal.ChartPortlet'),
                            listeners: {
                                'close': Ext.bind(this.onPortletClose, this)
                            }
                        }]
                    },{
                        id: 'col-3',
                        items: [{
                            id: 'portlet-31',
                            title: '系统公告',
                            tools: this.getTools(),
                            html: '<div class="portlet-content"></div>',
                            listeners: {
                                'close': Ext.bind(this.onPortletClose, this)
                            }
                        },{
                            id: 'portlet-32',
                            title: '快捷通道',
                            tools: this.getTools(),
                            html: '<div class="portlet-content"></div>',
                            listeners: {
                                'close': Ext.bind(this.onPortletClose, this)
                            }
                        },{
                            id: 'portlet-33',
                            title: '系统信息',
                            tools: this.getTools(),
                            html: '<div class="portlet-content"></div>',
                            listeners: {
                                'close': Ext.bind(this.onPortletClose, this)
                            }
                        }]
                    }]
							/*
						items : [{
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
								}]
								*/
					}]
        
        //[mainPortal]
        /*,
        listeners: {
            tabchange: onTabChange,
            afterrender: onAfterRender
        }*/
    });

    var menuTreeStore = Ext.create('Ext.data.TreeStore', {
        autoLoad: true,
                listeners: {
                    load: function(treestore, node, records, successful, eOpts ) {
                        if(successful){
                        }else {
                        	//Hope.errTip("会话已过期 ，请重新登录！");
                        	//top.location.href = appBaseUri+'logout';
                     		Ext.MessageBox.confirm('提示', '会话已过期 ，重新登录?' , function(btn){
                        		if(btn == "yes"){  
                        			top.location.href = appBaseUri+'logout';
                        		}  
                    		})
                        }
                    }
                },
        proxy: {
            type: 'ajax',
            url: appBaseUri+'account/menus/GetAuthorizedMenus',
            reader: {
                type: 'json',
                root: 'children',
                    listeners: {
                        exception: function(proxy, response, operation, eOpts){ alert('exception'); }
                    }
            }
        }
    });

    var treeFilterField = Ext.create('Ext.form.field.Trigger', {
        width: '100%',
        emptyText: '功能查找...',
        trigger1Cls: 'x-form-clear-trigger',
        onTrigger1Click: function () {
            treeFilterField.setValue('');
            menuTreeStore.clearFilter(true);
        },
        listeners: {
            'keyup': {
                element: 'el',
                fn: function (e) {
                    var regex = new RegExp(Ext.String.escapeRegex(treeFilterField.getValue()), 'i');
                    menuTreeStore.clearFilter(true);
                    menuTreeStore.filter(new Ext.util.Filter({
                        filterFn: function (item) {
                            return regex.test(item.get('text'));
                        }
                    }));
                }
            }
        }
    });

    var treePanel = Ext.create('Ext.tree.Panel', {
        id: 'menuTree',
        region: 'west',
        split: true,
        title: '功能导航',
        width: 160,
        stateId: 'appnav',
        stateful: true,
        margins: '2 0 0 0',
        collapsible: true,
        animCollapse: true,
        xtype: 'treepanel',
        rootVisible: false,
        store: menuTreeStore,
        tbar: [treeFilterField],
        bbar: [{
            xtype: 'button',
            text: '刷新缓存',
            iconCls: 'icon_paint_brush',
            tooltip: '点击更新缓存',
            handler: function () {
                Ext.Ajax.request({
                    url: appBaseUri+'Home/ClearCache',
                    success: function (response) {
                        if (response.responseText != '') {
                            var res = Ext.JSON.decode(response.responseText);
                            Hope.msgTip(res.msg);
                        }
                    }
                });
            }
        }],
        listeners: {
            'itemclick': function (e, record) {
                if (record.data.leaf) {
                    Hope.openTab(record.data.id, record.data.text, record.raw.url, {
                        cButtons: record.raw.buttons ? record.raw.buttons.split(',') : [],
                        cName: record.raw.controller,
                        cParams: record.raw.MenuConfig
                    });
                }
            }
            /*
            ,
             load:function(store,record,successful,operation,eOpts)
                        {      
                                var id=0;
                                if(record.data.id!="root")  id=record.data.id;
                                Ext.Ajax.request({
                                    url: '/bookmgr/functionlist', //返回数据格式：[{id:0,text:''},{id:1,text:'',leaf:true}]
                                    params:{id:id},
                                    success: function(response){
                                        var list=Ext.JSON.decode(response.responseText);
                                        record.removeAll(true);
                                        for(var i=0;i<list.length;i++)
                                        {
                                                record.appendChild({text:list[i].text,leaf:list[i].leaf,id:list[i].id});
                                        }
                                    },
                                    failure:function(response)
                                    {
                                            
                                    }
                                });
                        }
                        */
        }
    });

    var viewport = Ext.create('Ext.Viewport', {
        layout: 'border',
        items: [{
            region: 'north',
            xtype: 'container',
            height: 34,
            id: 'app-header',
            layout: {
                type: 'hbox',
                //padding : '5',  
                align: 'middle'
            },
            defaults: {
                xtype: 'component'
                //margins : '10'  
            },
            items: [{
                id: 'app-header-title',
                html: appName,
                minWidth:160
            }, {
                id: 'app-header-gohome',
                style:' text-align:center;',
                html: '',
                flex: 1
            }, {
                id: 'app-header-r1',
                html: '<!--<a href="'+appBaseUri+'Home/Logout" title="退出登录"><img src="static/images/exit.png" /></a>-->',
                flex: 1
            }, {
            xtype: 'toolbar',margin : '5 10 5 10', border: false,
            items: [
                {
                    text: '个人中心',
                    menu: [{
                text: '修改密码',
                iconCls: 'icon_key',
                handler: function () {
                    Hope.openWindow('修改密码', 'profile.changepwd', 300);
                }
            }, {
                text: '修改资料',
                handler: function () {
                    Hope.openWindow('修改资料', 'profile.profileinfo', 300);
                }
            }]
                },'-',
                {
                text: '安全退出',
                handler: function () {
                        Ext.MessageBox.confirm('提示', '确定要退出系统吗?' , function(btn){
                        	if(btn == "yes"){  top.location.href = appBaseUri+'logout';
                        	/*
                            Ext.Ajax.request({
                url: 'logout',
                method: 'GET',
                success: function( response, options ) {
                    window.location.reload( true );
                },
                failure: function( response, options ) {
                    Ext.Msg.alert( 'Attention', 'Sorry, an error occurred during your request. Please try again.' );
                }
            });                */
                        }  
                    })                   
                    }
            }
            ]
            }
            ]
        }, treePanel, mainTab, {
            region: 'south',
            border: false,
            items: [Ext.create('Ext.ux.StatusBar', {
                border: false,
                text: 'Ready',
                style: 'background:#3892D3;',
                defaults: {
                    style: 'color:#fff;'
                },
                items: ['->', idata.myInfo.loginName + ',欢迎回来', '-', '上次登录时间：' + idata.myInfo.prevLoginDate, '上次登录IP：' + idata.myInfo.prevLoginIp, '-', '版本：v1.1']
            })]
        }]
    });

    function onTabChange(tabPanel, tab) {
        var tabs = [],
            ownerCt = tabPanel.ownerCt,
            oldToken, newToken;

        tabs.push(tab.id);
        tabs.push(tabPanel.id);

        while (ownerCt && ownerCt.is('tabpanel')) {
            tabs.push(ownerCt.id);
            ownerCt = ownerCt.ownerCt;
        }

        newToken = tabs.reverse().join(tokenDelimiter);

        oldToken = Ext.History.getToken();

        if (oldToken === null || oldToken.search(newToken) === -1) {
            Ext.History.add(newToken);
        }
    }

    function onAfterRender() {
        Ext.History.on('change', function (token) {
            var parts, tabPanel, length, i;

            if (token) {
                parts = token.split(tokenDelimiter);
                length = parts.length;

                for (i = 0; i < length - 1; i++) {
                    Ext.getCmp(parts[i]).setActiveTab(Ext.getCmp(parts[i + 1]));
                }
            }
        });

        var activeTab1 = mainTab.getActiveTab(),
            activeTab2 = activeTab1;

        onTabChange(activeTab1, activeTab2);
    }
});

var Hope = new Object();

//打开tab
Hope.openTab = function (tabId, tabTitle, tab, config) {
	if (window.console) {
      console.log(config);
	}
    var _tab = mainTab.getComponent('tab' + tabId);
    if (!_tab) {
        mainTab.setLoading('Loading...');
        _tab = Ext.create('Ext.panel.Panel', {
            closable: true,
            id: 'tab' + tabId,
            title: tabTitle,
            layout: 'fit',
            autoScroll: true,
            border: false,
            //items: typeof (tab) == 'string' ? Ext.create('Hope.app.' + tab, config) : tab
            items: typeof (tab) == 'string' ? Ext.create(tab, config) : tab
        });
        mainTab.add(_tab);
        mainTab.setLoading(false);
    }
    mainTab.setActiveTab(_tab);
}

//打开window
Hope.openWindow = function (winTitle, win, winWidth, config) {
    Ext.create('Ext.window.Window', {
        autoShow: true,
        modal: true,
        title: winTitle,
        width: winWidth || 280,
        items: typeof(win) == 'string' ? Ext.create('Hope.app.' + win, config) : win
    });
}

//关闭tab
Hope.closeTab = function (tabId) {
    var tab = mainTab.getActiveTab();
    tab.close();
    if (tabId != undefined) {
        mainTab.setActiveTab(tabId);
    }
};

//刷新ActiveTab下的gridpanel
Hope.listReload = function () {
    if (mainTab.getActiveTab().down('gridpanel'))
        mainTab.getActiveTab().down('gridpanel').getStore().reload();
}

//成功提示
Hope.msgTip = function (msg) {
    function createBox(t, s) {
        return '<div class="msg"><h3>' + t + '</h3><p>' + s + '</p></div>';
    }
    
    var msgCt;
    if (!msgCt) {
        msgCt = Ext.DomHelper.insertFirst(document.body, { id: 'msg-div' }, true);
    }
    var m = Ext.DomHelper.append(msgCt, createBox('提示：', msg), true);
    m.hide();
    m.slideIn('t').ghost("t", { delay: 1000, remove: true });
};

//错误提示
Hope.errTip = function (msg, e) {
    Ext.MessageBox.show({
        title: '出错啦！',
        msg: msg,
        buttons: Ext.MessageBox.OK,
        animateTarget: e,
        icon: Ext.MessageBox.ERROR
    });
};

//一般提示
Hope.infoTip = function (msg, e) {
    Ext.MessageBox.show({
        title: '系统提示！',
        msg: msg,
        buttons: Ext.MessageBox.OK,
        animateTarget: e,
        icon: Ext.MessageBox.INFO
    });
};

//选择性提示
Hope.confirmTip = function (msg,fn, buttons, e) {
    Ext.MessageBox.show({
        title: '确认继续？',
        msg: msg,
        buttons: buttons || Ext.MessageBox.YESNO,
        animateTarget: e,
        fn: fn
    });
};

//控制台日志
Hope.log = function (obj) {
    if (window.console) {
        console.log(obj);
    }
}

//导出excel
Hope.ImportToExcel = function (me, exportHideColumn, onlySelected) {
    onlySelected = onlySelected || false;
    //要导出的列
    var exportColumns = new Array();
    Ext.each(me.columns, function (item, i) {
        if (i > 0) {
            if (exportHideColumn)
                exportColumns.push({ text: item.initialConfig.text, dataIndex: item.initialConfig.dataIndex, width: item.initialConfig.width });
            if (!exportHideColumn && !item.initialConfig.hidden)
                exportColumns.push({ text: item.initialConfig.text, dataIndex: item.initialConfig.dataIndex, width: item.initialConfig.width });
        }
    });

    //筛选条件
    var exportFilters = new Array();
    if (me.filters) {
        Ext.each(me.filters.getFilterData(), function (item, i) {
            var data = {};
            for (var key in item.data) {
                data[key] = item.data[key];
            }
            data["field"] = item.field;
            exportFilters.push(data);
        });
    }

    //排序
    var exportSorters = new Array();
    Ext.each(me.store.getSorters(), function (item, i) {
        exportSorters.push({ property: item.property, direction: item.direction });
    });

    var jsonData = {
        ExportColumns: exportColumns,
        ExportFilters: exportFilters,
        ExportSorters: exportSorters
    }
    if (onlySelected) {
        //选中数据
        var s = me.getSelectionModel().getSelection();
        var importRecords = new Array();
        Ext.each(s, function (item, i) {
            importRecords.push(item.data);
        });
        if (importRecords.length == 0) {
            Hope.infoTip('未选择任何数据！');
            return;
        }
        jsonData['SelectedRecords'] = Ext.JSON.encode(importRecords);
    }
    var newParams = {
        postType: 'export',
        jsonData: Ext.JSON.encode(jsonData)
    };
    var extended = Ext.apply(newParams, me.extraParams);

    if (!Ext.fly('frmDummy')) {
        var frm = document.createElement('form');
        frm.id = 'frmDummy';
        frm.name = frm.id;
        frm.className = 'x-hidden';
        document.body.appendChild(frm);
    }
    Ext.Ajax.request({
        url: "../" + me.cName + "/Export",
        method: 'POST',
        form: Ext.fly('frmDummy'),
        success: function (o, s, r) {

        },
        isUpload: true,
        params: extended
    });
}

//拥有指定权限
Hope.HaveAction = function (name) {
    return Ext.Array.contains(idata.myInfo.actions, name);
}

//拥有指定按钮
Hope.HaveActionMenu = function (items, name) {
    if (items && items.length > 0)
        return Ext.Array.contains(items, name)
    return false;
}

//拥有指定角色
Hope.HaveRole = function (name) {
    return Ext.Array.contains(idata.myInfo.roles, name);
}

//执行指定Action
Hope.run = function (url, params,itemStore) {
    Ext.Ajax.request({
        url: url,
        params: params,
        success: function (response) {
            if (response.responseText != '') {
                var res = Ext.JSON.decode(response.responseText);
                if (res.success) {
                    Hope.msgTip('操作成功！');
                    if(itemStore) itemStore.reload();
                }
                else
                    Hope.errTip(res.msg);
            }
        }
    });
}

//拥有指定列
Hope.HaveColumn = function(cName){
    var columns = idata.myInfo.roleColumns[cName];
    this.have = function(columnName){
        if (columns == undefined)return -2;
        return Ext.Array.contains(columns, columnName);
    }
}

/*字符串拼接类 
用法：
var buffer = new StringBuffer ();
buffer.append("hello ");
buffer.append("world");
var result = buffer.toString();
*/
function StringBuffer() {
    this._strings_ = new Array();
}

StringBuffer.prototype.append = function (str) {
    this._strings_.push(str);
};

StringBuffer.prototype.toString = function () {
    return this._strings_.join("");
};