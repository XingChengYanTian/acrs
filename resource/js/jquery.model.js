/**
 *@description 本JS用于提示 ，完全替换浏览器alert，confirm ，其中的css主要在common.css 754行至结尾
 *如有错误或改进的请联系 ly380355001@163.com
 *@date 2014-10-14
 *@author Leeyns
 */
jQuery.model= {
			//回调函数
			rollback:null,
			//model 对话框HTML
			modelHTML:'<span style="display:block" id="model-span"><div class="model" style="display:block;width:auto;min-width: 200px;max-width: 600px"><div class="model-content"><div id="model-msg" style="max-width: 600px"></div><div><a class="button green_button model_sure_btn model_sure" style="width: 80px">确认</a>&nbsp;&nbsp;<a  style="width: 80px" class="button orange_button model_cancle_btn model_cancle">取消</a></div></div></div><div class="lean_overlay" style="display:block"></div></span>',
			//ID
			modelId:"",
			//对话框大小设置
			initModel:function(){
				var model_width = parseInt($(".model").css("width"));
				var model_height = parseInt($(".model").css("height"));
				
				//window.console.log(model_width+ "  "+model_height);
				//clienX clientY
				var pagey = $(window).height();
				var pagex = $(window).width();
				
				//window.console.log(pagex+ "  "+pagey);
				
				var modelLeft = (pagex- model_width)/2;
				var modelTop = (pagey-model_height)/3;
				
				//window.console.log(modelLeft+ "  "+modelTop);
				
				$(".model").css({"left":modelLeft+"px","top":modelTop});
				
			},
			//关闭对话框
			close:function(model_id){
				 if(model_id!=null && model_id!=""){
					modelId = model_id;
					$(model_id).css("display","none");
				 }else{
					$("#model-span").css("display","none");
				 }
				 $("#model-span").remove();
				 return this;
			},
			//打开对话框
			open:function(model_id){
				if(model_id!=null && model_id!=""){
					modelId = model_id;
					$(model_id).css("display","block")
				}else{
					$("#model-span").css("display","block");
				}
				return this;
			},
			//去掉确认按钮
			removeSure:function(){
				$(".model_sure_btn").css("display","none");
				return this;
			},
			removeCancle:function(){
				$(".model_cancle_btn").css("display","none");
				return this;								
			},
			runAlert:function(){
				$("body").append(this.modelHTML);
				this.initModel();
				return this;
			},
			func_time:function(){},
			alertMsg:function(paramObj){//paramObj: {"msg":"","times":0,"rollback":functon(){}}
				 
				var msg = paramObj.msg;//提示消息
				var _this = this;
                if(!/^(<+.+>.+<\/+.+>){1,}$/.test(msg)){
                    var sg = "";
                    var tag_index = 0;
                    for(var i = 0;i<msg.length;i++){
                        sg+=msg.charAt(i);
                        if(i%60==0 && i!=0){
                            sg+="</br>";
                        }
                    }
                    msg = sg;
                }
				var times = paramObj.times;//提示对话框的自动关闭时间
				var fun_rollback = paramObj.rollback;//回调函数
				
				//window.console.log(msg+" \n"+times+"s \n"+( typeof fun_rollback) );
				if(msg==null || msg == ""){msg = ""}; 
				//回调函数是否为空
				if(fun_rollback!=null){
					//window.console.log("set rollback ...");
					_this.rollback = fun_rollback;
				}
				if(times==null || times <= 0){
					//window.console.log("enter no times ...");
					  _this.runAlert();
					  $("#model-msg").html(msg);
				}else{//定时关闭对话框
					_this.runAlert();
					$("#model-msg").html(msg);
					var func_a = function(){
						if (times <= 0) {
						
							 clearInterval(_this.func_time);
							 _this.close();	
						 
							 if(_this.rollback !=null){
							 
								_this.rollback();
							 }
							
							 
						}
						times --;
					};
					_this.func_time = setInterval(func_a, 1000);
				}
				return _this;
			}
		 };
		//取消 按钮点击事件
		 $(".model_cancle").live("click",function(){
			 $("#model-span").css("display","block").remove();
			 clearInterval($.model.func_time);
		 });
		 //确认 按钮点击事件
		 $(".model_sure_btn,.model_sure").live({
			"click":function(){
				if(jQuery.model.rollback!=null){
					$.model.rollback();
					
				}
				clearInterval($.model.func_time);
				$.model.close();
				$.model.rollback = null;
			}
		 });
		 
		