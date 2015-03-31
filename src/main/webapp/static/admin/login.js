
Ext.define("Hope.app.login", {
    extend : "Ext.window.Window",
    
    /** 页面基本属性配置 */
    width : 480,
    autoHeight : true,
    closable : false,
    resizable : false,
    draggable : false,
    //bbar: new Ext.StatusBar({  
           //     id: 'form-statusbar',  
           //     defaultText: '待登录',  
            //    plugins: new Ext.ux.ValidationStatus({form:'loginForm'})  
           // })  ,
    layout : {
        type : "vbox",
        align : "stretch",
        pack : "start"
    },
    
    /** 初始化页面组件 */
    initComponent : function() {
    		var me = this;
        me.showCaptcha = me.showCaptcha == undefined ? true : me.showCaptcha;
        me.errMsg = me.errMsg == undefined ? true : me.errMsg;
        //items
        if (me.showCaptcha) {
        this.items = [{
            height : 100,
            border : false,
            html : "<div class=\"app-logo\">Control Panel</div>"
        }, {
            xtype : "form",
            standardSubmit: true, 
            itemId : "loginForm",
            layout : {
                type : "vbox",
                align : "center",
                pack : "start"
            },
            flex : 1,
            bodyPadding : "10 0 5 0",
            margin : "0 -1 -1 -1",
            defaults : {
                xtype : "textfield",
                allowBlank : false,
                fieldStyle : "font-weight:bold;",
                labelWidth : 50,
                width : 250
            },
            items : [{           	
                name : "username",
                fieldLabel: '用户名',
                emptyText : "用户名",
                blankText : "请输入用户名"
            }, {            		
                inputType : "password",
                name : "password",
                fieldLabel: '密码',
                emptyText : "密码",
                blankText : "请输入密码"
            }, {
                xtype : "fieldcontainer",
                layout : "hbox",
                items : [{
                    name : "captcha",
                    flex : 1,
                    xtype : "textfield",
                    labelWidth : 50,
                    fieldLabel: '验证码',
                    emptyText : "验证码",
                    allowBlank : false,
                    blankText : "请输入验证码",
                    maxLength : 4,
                    maxLengthText : "验证码错误",
                    minLength : 4,
                    minLengthText : "验证码错误",
                    fieldStyle : "font-weight:bold;"
                }, {
                    xtype : "container",
                    width : 80,
                    margin : "0 0 0 1",
                    html : "<img onclick=\"javascprit:loginPage.refreshImg(this);\" src=\"servlet/captchaCode\" alt=\"看不清？点击刷新\" title=\"看不清？点击刷新\" style=\"cursor:pointer; width:80; height:23;\">"
                }]
            }]
        }];
      	} else {
      		this.items = [{
            height : 100,
            border : false,
            html : "<div class=\"app-logo\">Control Panel</div>"
        }, {
            xtype : "form",
            standardSubmit: true, 
            itemId : "loginForm",
            layout : {
                type : "vbox",
                align : "center",
                pack : "start"
            },
            flex : 1,
            bodyPadding : "10 0 5 0",
            margin : "0 -1 -1 -1",
            defaults : {
                xtype : "textfield",
                allowBlank : false,
                fieldStyle : "font-weight:bold;",
                labelWidth : 50,
                width : 300
            },
            items : [{
                name : "username",
                fieldLabel: '用户名',
                emptyText : "用户名",
                blankText : "请输入用户名"
            }, {
                inputType : "password",
                name : "password",
                fieldLabel: '密码',
                emptyText : "密码",
                blankText : "请输入密码"
            }]
        }];
      	}
        
        //登录按钮
        this.buttons = [{
            xtype : "tbtext",
            itemId : "msgLabel",
            text : me.errMsg,
            style : "color: red; font-weight: bold;"
        }, {
            text : "登&nbsp;&nbsp;&nbsp;录",
            width : 100,
            itemId : "loginBtn",
            handler : this.loginAction,
            scope : this
        }];
        
        // 窗口大小改变时，从新设置窗口位置
        /*
    		window.onresize = function () {//alert('width:'+width+',height:'+height);
        		var left = (Ext.getBody.getWidth() - this.getWidth()) / 2;
        		var top = (Ext.getBody.getHeight() - this.getHeight()) / 2;
        		this.setPosition(left, top);
    		};
    		*/
    		var me=this;
    		
    		function resizeProcess(width, height) {//alert('width:'+width+',height:'+height);
        		var left = (width - me.getWidth()) / 2;
        		var top = (height - me.getHeight()) / 2;
        		me.setPosition(left, top);
    		};
    		
    		Ext.EventManager.onWindowResize(function(width, height){
					//改变窗口的时候会提示出窗口的宽高
					//alert('width:'+width+',height:'+height);
					resizeProcess(width, height) 
				});
    
        this.callParent();
        
         //回车事件
        this.on("afterrender", function(thiz, opts) {
            Ext.create("Ext.util.KeyNav", thiz.getEl(), {enter : this.loginAction, scope : this});
        }, this);
        
        //输入焦点
        this.on("show", function() {
            this.down("#loginForm").getForm().findField("username").focus(true)
        }, this, {delay : 300});
    },
    
    /** 刷新验证码 */
    refreshImg : function(thiz) {
        thiz.src = "servlet/captchaCode?" + Math.round(Math.random()*100000);
    },
    
    /** 登录操作 */
    loginAction : function() {
    	  var me = this;
        var formPanel = this.down("#loginForm");
        var form = formPanel.getForm();
        
        //校验表单
        if(!form.isValid()) {
            return;
        }
        
        var loginBtn = this.down("#loginBtn");
        var msgLabel = this.down("#msgLabel");
        //alert(me.errMsg);
        //执行登录
       // msgLabel.setText(null);
        this.getEl().mask();
        loginBtn.setText("登&nbsp;&nbsp;录&nbsp;&nbsp;中..");
        form.submit({
    			url:'login'//,
    			//waitTitle:'登录',
    			//waitMsg:'正在登录，请稍后...'
    		});
        /*
        Ext.Ajax.request({
            method : "POST",
            url : "login",
            params : form.getValues(),
            success : function(response, options) {
                var rtn = Ext.decode(response.responseText);
                if(rtn.success) {
                    window.location.href = "home";
                } else {
                    loginBtn.setText("登&nbsp;&nbsp;&nbsp;录");
                    this.getEl().unmask();
                    form.findField("password").setValue("");
                    if (form.findField("captcha"))
                    	form.findField("captcha").setValue("");
                    form.findField("username").focus(true);
                    msgLabel.setText(rtn.message);
                    form.clearInvalid();
                }
            },
            failure : function() {
                loginBtn.setText("登&nbsp;&nbsp;&nbsp;录");
                this.getEl().unmask();
            },
            scope : this
        });
        */
    }
});