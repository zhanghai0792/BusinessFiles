/*暂未使用到*/
//===============定义全局变量和扩展=============================
var Header_menu;//列的右键菜单
var mergeCellarr=new Array();
 $.extend($.fn.form.methods, {//序列化form  
    serialize: function(jq){  
        var arrayValue = $(jq[0]).serializeArray();
        var json = {};
        $.each(arrayValue, function() {
            var item = this;
            if (json[item["name"]]) {
                json[item["name"]] = json[item["name"]] + "," + item["value"];
            } else {
                json[item["name"]] = item["value"];
            }
        });
        return json; 
    },
    getValue:function(jq,name){  
        var jsonValue = $(jq[0]).form("serialize");
        return jsonValue[name]; 
    },
    setValue:function(jq,name,value){
        return jq.each(function () {
                _b(this, _29);
                var data = {};
                data[name] = value;
                $(this).form("load",data);
        });
    }
});

$.extend($.fn.datagrid.methods,{
	getRowDom: function (target, index) {
        if (!$.isNumeric(index) || index < 0) { return $(); }
        var t = $(target), panel = t.datagrid("getPanel");
        return panel.find("div.datagrid-view div.datagrid-body table tr.datagrid-row[datagrid-row-index=" + index + "]");
    }
});



/*String.prototype.endsWith = function(str){
	return (this.match(str+"$")==str);
}*/

String.prototype.endWith=function(str){  
    if(str==null||str==""||this.length==0||str.length>this.length)  
      return false;  
    if(this.substring(this.length-str.length)==str)  
      return true;  
    else  
      return false;  
    return true;  
}  

function serializeObject(form){
        var o={};
        $.each(form.serializeArray(),function(index){
                  if(o[this['name'] ]){
                  o[this['name'] ] = o[this['name'] ] + "," + this['value'];

                   }else{
                      o[this['name'] ]=this['value'];
                   }
            });
          return o;
   }
   
   $.extend($.fn.datagrid.methods, {//键盘上下控制选中行
	keyCtr : function (jq) {
		return jq.each(function () {
			var grid = $(this);
			grid.datagrid('getPanel').panel('panel').attr('tabindex', 1).bind('keydown', function (e) {
				switch (e.keyCode) {
				case 38: // up
					var selected = grid.datagrid('getSelected');
					if (selected) {
						var index = grid.datagrid('getRowIndex', selected);
						grid.datagrid('selectRow', index - 1);
					} else {
						var rows = grid.datagrid('getRows');
						grid.datagrid('selectRow', rows.length - 1);
					}
					break;
				case 40: // down
					var selected = grid.datagrid('getSelected');
					if (selected) {
						var index = grid.datagrid('getRowIndex', selected);
						grid.datagrid('selectRow', index + 1);
					} else {
						grid.datagrid('selectRow', 0);
					}
					break;
				}
			});
		});
	}
});
//============================================
   //刷新的datagrid,操作的dialog,序列化的form,form中的数据,访问的servlet和method 
   function SerializeMyForm(datagrid,dialog,form,data,servlet,method){//提供提交表单的封装方法
$("#"+form).form('submit',{
	url:"/assetsManageSys/"+servlet+"?method="+method,
	novalidate:true,
	queryParams:{data:JSON.stringify(data)},
	onSubmit: function(){
	var isValid = $(this).form('validate');
	if (!isValid||data==null||data==""){
	//$.messager.alert("提示", "请填写所有的必填项!");
	return isValid;}
	},    
	 success:function(result){
	  data = eval("(" + result + ")");
	      if(data.success)
		{
			$("#"+dialog).dialog('close');
			$("#"+datagrid).datagrid('load',data);
			$.messager.show({
				title:'提示',
				timeout:3000,
				msg:data.message,
				showType:'slide',
				style:{
					top:document.body.scrollTop+document.documentElement.scrollTop,
				}
			});
		}else{
			$.messager.alert("错误", data.message); } 
			
			  /* if(data.success)
			$('#'+dialog).dialog('close');
			$('#'+datagrid).datagrid('load',data);
			$.messager.show({
				title:'提示',
				timeout:3000,
				msg:data.message,
				showType:'slide',
				style:{
					top:document.body.scrollTop+document.documentElement.scrollTop,
				}
			}); */
			data="";
	    }
	});
}
  //增加和移除编辑器 
   $.extend($.fn.datagrid.methods, {
	addEditor : function(jq, param) {
		if (param instanceof Array) {
			$.each(param, function(index, item) {
				var e = $(jq).datagrid('getColumnOption', item.field);
				e.editor = item.editor;
			});
		} else {
			var e = $(jq).datagrid('getColumnOption', param.field);
			e.editor = param.editor;
		}
	},
	removeEditor : function(jq, param) {
		if (param instanceof Array) {
			$.each(param, function(index, item) {
				var e = $(jq).datagrid('getColumnOption', item);
				e.editor = {};
			});
		} else {
			var e = $(jq).datagrid('getColumnOption', param);
			e.editor = {};
		}
	}
});
$.extend($.fn.validatebox.methods, {  
	remove: function(jq, newposition){  
		return jq.each(function(){  
			$(this).removeClass("validatebox-text validatebox-invalid").unbind('focus.validatebox').unbind('blur.validatebox');
		});  
	},
	reduce: function(jq, newposition){  
		return jq.each(function(){  
		   var opt = $(this).data().validatebox.options;
		   $(this).addClass("validatebox-text").validatebox(opt);
		});  
	}	
});
$.extend($.fn.validatebox.defaults.rules, {    
	lengths: {    
		        validator: function(value, param){    
		            return value.length >= param[0] && value.length <= param[1];    
		        }, message : "输入内容长度必须介于{0}和{1}之间"
		    },
     account: {//param的值为[]中值//用户账号验证(只能包括 _ 数字 字母) 
        validator: function (value, param) {
            if (value.length < param[0] || value.length > param[1]) {
                $.fn.validatebox.defaults.rules.account.message = '长度必须在' + param[0] + '至' + param[1] + '范围';
                return false;
            } else {
                if (!/^[\w]+$/.test(value)) {
                    $.fn.validatebox.defaults.rules.account.message = '只能数字、字母、下划线组成.';
                    return false;
                } else {
                
                    return true;
                }
            }
        }, message: '只能数字、字母、下划线组成'
    },
    pledgeArea: { 
        numberbox: function (value, param) {
             if (value>Number(param[0]-param[1])) {
  	$.fn.numberbox.defaults.rules.pledgeArea.message = '剩余可抵押面积为:' + Number(param[0]-param[1]) +'平方米';
                return false;
            } else {
            return true;
                 if (!/^(\d{1,3}(,\d\d\d)*(\.\d{1,3}(,\d\d\d)*)?|\d+(\.\d+))?$/i.test(value)||value<0) {
                    $.fn.number.defaults.rules.pledgeArea.message = '只能填写大于0的数字.';
                    return false;
                } else {
                    return true;
                } 
            } 
        }, message: '剩余抵押面积不足'
    }, 
      
		});
 $.extend($.fn.validatebox.defaults.rules, {
	LR_edcardName :{  
            validator : function(value,param){ 
                var flag=false ;
                 $.ajax({  
                    url :'/assetsManageSys/LandReserveServlet?method=judgesame',  
                    type : 'POST',                    
                    timeout : 6000,  
                    data:{cardName:value},
                    async: false,    
                    success : function(data) {
                      data = eval('(' + data + ')');
                        if (data.message) {
                            flag = true;      
                        }else{
                            flag = false;  
                        }  
                    },  
                });   
                 if(flag==true){  
                 $('#LR_edcardName').validatebox('remove');
                }
                return flag;   
            },  
            message: '土地证号已经存在！'  
        },
    LR_PrightID:{  
            validator : function(value,param){ 
                var flag=false ;
                 $.ajax({  
                    url :'/assetsManageSys/LandReserveServlet?method=judgeRightIDSame',  
                    type : 'POST',                    
                    timeout : 6000,  
                    data:{rightID:value},
                    async: false,    
                    success : function(data) {
                      data = eval('(' + data + ')');
                        if (data.message) {
                            flag = true;      
                        }else{
                            flag = false;  
                        }  
                    },  
                });   
                 if(flag==true){  
                 $('#LR_edrightID').validatebox('remove');
                 $('#LR_edrightID_one').validatebox('remove');
                }
                return flag;   
            },  
            message: '该他项权证号已经存在！'  
        },
});
$.extend($.fn.validatebox.defaults.rules, {
	LS_edcardName :{  
            validator : function(value,param){ 
                var flag=false ;
                 $.ajax({  
                    url :'/assetsManageSys/LandSaleServlet?method=judgesame',  
                    type : 'POST',                    
                    timeout : 6000,  
                    data:{cardName:value},
                    async: false,    
                    success : function(data) {
                      data = eval('(' + data + ')');
                        if (data.message) {
                            flag = true;      
                        }else{
                            flag = false;  
                        }  
                    },  
                });   
                 if(flag==true){  
                 $('#LS_edcardName').validatebox('remove');
                }
                return flag;   
            },  
            message: '土地证号已经存在！'  
        },
      LS_PrightID:{  
            validator : function(value,param){ 
                var flag=false ;
                 $.ajax({  
                    url :'/assetsManageSys/LandSaleServlet?method=judgeRightIDSame',  
                    type : 'POST',                    
                    timeout : 6000,  
                    data:{rightID:value},
                    async: false,    
                    success : function(data) {
                      data = eval('(' + data + ')');
                        if (data.message) {
                            flag = true;      
                        }else{
                            flag = false;  
                        }  
                    },  
                });   
                 if(flag==true){  
                 $('#LS_edrightID').validatebox('remove');
                 $('#LS_edrightID_one').validatebox('remove');
                }
                return flag;   
            },  
            message: '该他项权证号已经存在！'  
        },
});

 $.extend($.fn.validatebox.defaults.rules, {
 	H_edcardName :{  
            validator : function(value,param){ 
                var flag=false ;
                 $.ajax({  
                    url :'/assetsManageSys/HouseServlet?method=judgesame',  
                    type : 'POST',                    
                    timeout : 6000,  
                    data:{cardName:value},
                    async: false,    
                    success : function(data) {
                      data = eval('(' + data + ')');
                        if (data.message) {
                            flag = true;      
                        }else{
                            flag = false;  
                        }  
                    },  
                });   
                 if(flag==true){  
                 $('#H_edcardName').validatebox('remove');
                }
                return flag;   
            },  
            message: '登记名称已经存在！'  
        },
	H_edrightID :{  
            validator : function(value,param){ 
                var flag=false ;
                 $.ajax({  
                    url :'/assetsManageSys/HouseServlet?method=judgeRightIDSame',  
                    type : 'POST',                    
                    timeout : 6000,  
                    data:{rightID:value},
                    async: false,    
                    success : function(data) {
                      data = eval('(' + data + ')');
                        if (data.message) {
                            flag = true;      
                        }else{
                            flag = false;  
                        }  
                    },  
                });   
                 if(flag==true){  
                 $('#H_edrightID').validatebox('remove');
                }
                return flag;   
            },  
            message: '该他项权证号已经存在！'  
        },
});

function toThousands(num) {
	 if(/.*\..*/.test(num))return (num.split('.')[0]|| 0).toString().replace(/(\d)(?=(?:\d{3})+$)/g, '$1,')+"."+num.split('.')[1];
     else return (num).toString().replace(/(\d)(?=(?:\d{3})+$)/g, '$1,');//不包含小数点
 }

var firstpager=true;//第一页

function CreateFormPageHeader(strPrintName,printDatagrid,FONT_SIZE) {//生成表头
	var HeaderObject=new Object();
	var tableString="";
	if(firstpager){
	 tableString = '<div style="width:100%;text-align:center;margin:0 auto;">';
	}else{
		tableString = '<div style="width:100%;text-align:center;margin:0 auto;page-break-before: always;">';
	}
	if(firstpager){
	    tableString += '<div style="border:1px;width:100%;text-align:center; margin:0 auto;font-size:22px;">';
	    tableString+='<strong>'+strPrintName+'</strong>';
	    tableString+='</div>';
	    firstpager=false;
	}
	
    tableString += '<table cellspacing="0" border="1" style="align:center;margin:0 auto;font-size:'+FONT_SIZE+'px;border-collapse:collapse;" >';//class="pb"
     
    var frozenColumns = printDatagrid.datagrid("options").frozenColumns;  // 得到frozenColumns对象
    var columns = printDatagrid.datagrid("options").columns;// 得到columns对象
    var nameList = '';
    var fm=new Array();//formatter
    // 载入title
      /* if(frozenColumns != undefined && frozenColumns != ''&&frozenColumns[0].length>1) {
       for(var i = 0;i<frozenColumns[0].length; i++) {
           columns.splice(0,0,frozenColumns[0][i]);
       }
    } */  
    if (typeof columns != 'undefined' && columns != '') {
    	var j=0;var lastnum=0;
         $(columns).each(function (index) {
            tableString += '\n<tr>';
            /*if (typeof frozenColumns != 'undefined' && typeof frozenColumns[index] != 'undefined') {
                for (var i = 0; i < frozenColumns[index].length; ++i) {
                    if (!frozenColumns[index][i].hidden) {
                        tableString += '\n<th width="' + frozenColumns[index][i].width + '"';
                        if (typeof frozenColumns[index][i].rowspan != 'undefined' && frozenColumns[index][i].rowspan > 1) {
                            tableString += ' rowspan="' + frozenColumns[index][i].rowspan + '"';
                        }
                        if (typeof frozenColumns[index][i].colspan != 'undefined' && frozenColumns[index][i].colspan > 1) {
                            tableString += ' colspan="' + frozenColumns[index][i].colspan + '"';
                        }
                        if (typeof frozenColumns[index][i].field != 'undefined' && frozenColumns[index][i].field != '') {
                            nameList += ',{"f":"' + frozenColumns[index][i].field + '", "a":"' + frozenColumns[index][i].align + '"}';
                        }
                        tableString += '>' + frozenColumns[0][i].title + '</th>';
                    }
                }
            }*/
            
            if(frozenColumns != undefined && frozenColumns != ''&&frozenColumns[0].length>1&&index==0) {
		       for(var k = 0;k<frozenColumns[0].length; k++) {
		           if(frozenColumns[0][k].hidden != true) {
		           if(frozenColumns[0][k].field=='ID'){
		           frozenColumns[0][k].title="序号";frozenColumns[0][k].width="15px";
		           }
		             tableString = tableString + '\n<th  width= "' +frozenColumns[0][k].width + '" ' 
		             +'rowspan="2"'+'>'+ frozenColumns[0][k].title + '</th>';
		             fm.push(frozenColumns[0][k].formatter);	
		           nameList += ',{"f":"' + frozenColumns[0][k].field + '", "a":"' + frozenColumns[0][k].align + '"}';
		             }
		         }
		        }

            for (var i = 0; i < columns[index].length; ++i) {
                if (columns[index][i].align!="left") {////columns[index][i].deltaWidth==9
                	if(columns[index][i].field=='ID'){
                		columns[index][i].title="序号";columns[index][i].width="30px";
                		}
                    	tableString += '\n<th width="' + columns[index][i].width + '"';
                    if (typeof columns[index][i].rowspan != 'undefined' && columns[index][i].rowspan > 1) {
                        tableString += ' rowspan="' + columns[index][i].rowspan + '"';
                    }
                    if (typeof columns[index][i].colspan != 'undefined' && columns[index][i].colspan > 1) {
                        tableString += ' colspan="' + columns[index][i].colspan + '"';
                    }
                    if (typeof columns[index][i].field != 'undefined' && columns[index][i].field != ''&&index==0) {
	                   if(columns[index][i].colspan>1)
                    {
		                  	j+=lastnum;
	                    	var t = j;	                    	
		                    for(;t<columns[index][i].colspan+j;t++)
		                    {	
		                    	fm.push(columns[index+1][t].formatter);
		                   	 	nameList += ',{"f":"' + columns[index+1][t].field + '", "a":"' + columns[index+1][t].align + '"}';
		                   }
		                   lastnum = columns[index][i].colspan;
                    }
                    else {
                    	fm.push(columns[index][i].formatter);
	                	nameList += ',{"f":"' + columns[index][i].field + '", "a":"' + columns[index][i].align + '"}';
	                    }
                    }
                    tableString += '>' + columns[index][i].title + '</th>';
                }
            }
            tableString += '\n</tr>';
        }); 
    }
/***/    
    HeaderObject.tableString=tableString; HeaderObject.nameList=nameList;HeaderObject.fm=fm;
    return HeaderObject;
}

function print_Page(strPrintName, printDatagrid,FONT_SIZE,ROW_NUM){
	//window.open("");
	//window.open("about:blank");
	PageSetup_Null();
	
	/*var FONT_SIZE=13;
	var ROW_NUM=20;*/
	
	var rowNums = printDatagrid.datagrid("getRows").length;
	var t=Math.ceil(rowNums/ROW_NUM);//需要打印的页数,向上取整
	var rowss = 0;
	var  tableString1="";
	for(var ri=0;ri<t;ri++){
		//alert(FONT_SIZE+","+ROW_NUM);
		var HeaderObject=CreateFormPageHeader(strPrintName, printDatagrid,FONT_SIZE);
		tableString1+=HeaderObject.tableString;//每页都先加上表头
		tableString1+=CreateFormPage(strPrintName, printDatagrid,rowss,FONT_SIZE,ROW_NUM);//再加上数据
		rowss += ROW_NUM;
	}
	firstpager=true;
	//window.print();
	//window.open("about:blank").document.body.innerHTML = tableString1;
	
	
	var win=window.open("about:blank");
	win.document.body.innerHTML = tableString1;
	win.print();
	//win.location.url="/assetsManageSys/Jsps/sysManager/sysManager.jsp";
	 /*window.onload=function(){ 
		  }*/
	//win.blur();
//	console.info(win);
	
//	console.info(win.parent);
	win.opener = null;//为了不出现提示框 
	win.close();
	
	
	//console.info(tableString1);
}
 
function CreateFormPage(strPrintName, printDatagrid,ri,FONT_SIZE,ROW_NUM) {
    var HeaderObject=CreateFormPageHeader(strPrintName, printDatagrid,FONT_SIZE);
    var  tableString="";//数据
    var nameList=HeaderObject.nameList;
    var fm=HeaderObject.fm;
    
    var rows = printDatagrid.datagrid("getRows");
    var m=0;
    var nl = eval('([' + nameList.substring(1) + '])');
    
    //考虑数据行高
   /*  var dom =printDatagrid.datagrid('getRowDom', 0);//暂时考虑第1行高度
	var total=printDatagrid.datagrid('getData').total;
	var RowHeight= Number(dom[0].clientHeight)*Number(total);
	console.info(RowHeight); */
    
    for (var i = ri; i < rows.length && i < ri+ROW_NUM; ++i) {
        tableString += '\n<tr>';
        $(nl).each(function (j) {
            var e = nl[j].f.lastIndexOf('_0');
            tableString += '\n<td';
	            if (nl[j].a != 'undefined' && nl[j].a != '') {
	            		tableString += ' style="text-align:' + nl[j].a + ';" ';
	            }
	            
	            tableString += '>';
	            if(nl[j].f=="ID")	rows[i][nl[j].f]=i+1;
	             if (e + 2 == nl[j].f.length) 
	            {
	            	if(rows[i][nl[j].f.substring(0, e)]==null||rows[i][nl[j].f.substring(0, e)]=='undefined')
	            		rows[i][nl[j].f.substring(0, e)]="";
	            	tableString += rows[i][nl[j].f.substring(0, e)];
	            }
	            else{
	            		if(rows[i][nl[j].f]==null)//||rows[i][nl[j].f]=='undefined'||typeof rows[i][nl[j].f]=="undefined"
	            		rows[i][nl[j].f]="";
	            		 	//if(typeof (rows[i][nl[j].f])=='object')rows[i][nl[j].f]="";
			              //else {
			               		  if(typeof(fm[m])=="undefined")
			               		 {
			               			tableString += rows[i][nl[j].f]; 
			               		}
			               		else {
			               		/* if(fm[m](rows[i][nl[j].f],rows[i],null)=="undefined"||fm[m](rows[i][nl[j].f],rows[i],null)==null)
			               			fm[m](rows[i][nl[j].f],rows[i],null)==""; */
			               	//console.info(fm[m](rows[i][nl[j].f],rows[i],null))
			               		tableString +=fm[m](rows[i][nl[j].f],rows[i],null);
			               		}
			              		  
			              // 	}
			               	m++;
	              } 
            tableString += '</td>';
        });
        m=0;
        tableString += '\n</tr>';
    }
    tableString += '\n</table>';
    tableString+='</div>';
return tableString;
/*window.document.body.innerHTML = tableString;
window.print();*/ 
}


function mergeCells(arr,dg,data){//合并datagrid的方法
	var rowCount = dg.datagrid("getRows").length;
	var cellName;var span;
	var perValue = "";var curValue = "";
	var perCondition="";var curCondition="";
	var flag=true;var condiName="";
	
	for (var i =arr.length - 1; i >= 0; i--) {
			cellName = arr[i].mergeFiled;
			condiName=arr[i].premiseFiled;
			if(condiName!=null){
				flag=false;
				}
			perValue = "";perCondition="";span = 1;
	for (var row = 0; row <= rowCount; row++) {
			if (row == rowCount) {
				curValue = ""; curCondition="";
			} else {
				curValue = dg.datagrid("getRows")[row][cellName];
				//curValue =data[row][cellName];
				//console.info(dg.datagrid("getRows")[row][cellName]);
				/* if(cellName=="ORGSTARTTIME"){//特殊处理这个时间字段
					curValue =formatDate(dg.datagrid("getRows")[row][cellName],"");
				} */
				/* if(cellName=="rightID"&&curValue==""){
					//console.info(curValue+"/"+cellName);
				} */
				  
				if(!flag){
					curCondition=dg.datagrid("getRows")[row][condiName];
					//curCondition=data[row][condiName];
				}
			}
			
			if (perValue == curValue&&(flag||perCondition==curCondition)&&curCondition!="") {
				 span += 1;
				// console.info(perValue+"/"+curValue+"/"+perCondition+"/"+curCondition);
			} else {
				var index = row - span;
				if(index>=0){
				dg.datagrid('mergeCells', {
					index : index,
					field : cellName,
					rowspan : span,
					colspan : null,
				});
				var obj = new Object(); obj.index=index; obj.cellName=cellName; obj.rowspan=span;
				if(span>1){
				mergeCellarr.push(obj);//记住 需要合并单元格及所跨的行数
				//console.info(index+" /"+cellName + " /" +span);
				}
			}
				span = 1;
				perValue = curValue;
				if(!flag){
					perCondition=curCondition;
				}
			}
		}
	}
	return mergeCellarr;
}

//专用于AllPledge.jsp 页面的打印
function printAP_Page(strPrintName, printDatagrid,mergeCellArr,FONT_SIZE,ROW_NUM){
	
	/*var FONT_SIZE=13;
	var ROW_NUM=20;*/
	
	var rowNums = printDatagrid.datagrid("getRows").length;
	var t=Math.ceil(rowNums/ROW_NUM);//需要打印的页数,向上取整
	var rowss = 0;
	var  tableString1="";
	for(var ri=0;ri<t;ri++){
		var HeaderObject=CreateFormPageAP_Header(strPrintName, printDatagrid,FONT_SIZE);
		tableString1+=HeaderObject.tableString;//每页都先加上表头
		tableString1+=CreateFormPageAP(strPrintName, printDatagrid,rowss,mergeCellArr,FONT_SIZE,ROW_NUM);//再加上数据
		rowss += ROW_NUM;
	}
	//window.document.body.innerHTML = tableString1; 
	//window.print(); 
	countrow = 0;firstpager_AP=true;
	
	var win=window.open("about:blank");
	
	
	win.document.body.innerHTML = tableString1;
	win.print();
	win.opener = null;//为了不出现提示框 
	win.close();
	
}
var firstpager_AP=true;
function CreateFormPageAP_Header(strPrintName, printDatagrid,FONT_SIZE){
	var HeaderObject=new Object();
	var tableString;
	if(firstpager_AP){
	 tableString = '<div style="width:100%;text-align:center;margin:0 auto;">';
	}else{
		tableString = '<div style="width:100%;text-align:center;margin:0 auto;page-break-before: always;">';
	}
	if(firstpager_AP){
	    tableString += '<div style="border:1px;width:100%;text-align:center; margin:0 auto;font-size:22px;">';
	    tableString+='<strong>'+strPrintName+'</strong>';
	    tableString+='</div>';
	    firstpager_AP=false;
	}
    tableString += '<table cellspacing="0" border="1" style="align:center;margin:0 auto;font-size:'+FONT_SIZE+'px;border-collapse:collapse;" >';
    var columns = printDatagrid.datagrid("options").columns;// 得到columns对象
    var nameList = '';
    var fm=new Array();//formatter
    if (typeof columns != 'undefined' && columns != '') {
    	var j=0;var lastnum=0;
         $(columns).each(function (index) {
            tableString += '\n<tr>';
		         
            for (var i = 0; i < columns[index].length; ++i) {
                if (!columns[index][i].hidden) {
                	if(columns[index][i].field=='ID'){
                		columns[index][i].title="序号";columns[index][i].width="15px";
                		//nameList += ',{"f":"ID ", "a":"center"}';
                		}
                    	tableString += '\n<th width="' + columns[index][i].width + '"';
                    if (typeof columns[index][i].rowspan != 'undefined' && columns[index][i].rowspan > 1) {
                        tableString += ' rowspan="' + columns[index][i].rowspan + '"';
                    }
                    if (typeof columns[index][i].colspan != 'undefined' && columns[index][i].colspan > 1) {
                        tableString += ' colspan="' + columns[index][i].colspan + '"';
                    }
                    if (typeof columns[index][i].field != 'undefined' && columns[index][i].field != ''&&index==0) {
	                   if(columns[index][i].colspan>1)
                    {
		                  	j+=lastnum;
	                    	var t = j;	                    	
		                    for(;t<columns[index][i].colspan+j;t++)
		                    {	
		                    	fm.push(columns[index+1][t].formatter);
		                   	 	nameList += ',{"f":"' + columns[index+1][t].field + '", "a":"' + columns[index+1][t].align + '"}';
		                   }
		                   lastnum = columns[index][i].colspan;
                    }
                    else {
                    	fm.push(columns[index][i].formatter);
	                	nameList += ',{"f":"' + columns[index][i].field + '", "a":"' + columns[index][i].align + '"}';
	                    }
                    }
                    tableString += '>' + columns[index][i].title + '</th>';
                }
            }
            tableString += '\n</tr>';
        }); 
    }
    HeaderObject.tableString=tableString; HeaderObject.nameList=nameList;HeaderObject.fm=fm;
    return HeaderObject;
}

var countrow = 0;
function CreateFormPageAP(strPrintName, printDatagrid,ri,mergeCellArr,FONT_SIZE,ROW_NUM) {
	var HeaderObject=CreateFormPageAP_Header(strPrintName, printDatagrid,FONT_SIZE);
    var  tableString="";//数据
    var nameList=HeaderObject.nameList;
    var fm=HeaderObject.fm;
    
    var rows = printDatagrid.datagrid("getRows");
    var m=0;
    var rowsflag = false;
    
    var flagnum=false;
    var nl = eval('([' + nameList.substring(1) + '])');
    for (var i = ri; i < rows.length && i < ri+ROW_NUM; ++i) {
        tableString += '\n<tr>';
        $(nl).each(function (j) {
            var e = nl[j].f.lastIndexOf('_0');
            tableString += '\n<td';
	           // if (nl[j].a != 'undefined' && nl[j].a != '') {//不屏蔽ID
	            	//console.info(mergeCellArr[j].cellName+"/"+mergeCellArr[j].rowspan+"/"+mergeCellArr[j].index);
	            	/* if(nl[j].f==mergeCellArr[j].cellName)
	            		{tableString +=' rowspan="'+mergeCellArr[j].rowspan+'" ';} */
	            	/*  if(rowss > 1){
	            		//tableString +=' display="none;" ';
	            		tableString += ' style="text-align:' + nl[j].a + ';display:none;"';
	            		rowss--;
	            	} else
	            		tableString += ' style="text-align:' + nl[j].a + ';" '; */
	          //  if(mergeCellArr!=null){//判断是否需要合并单元格	
	            	 for(var k=0;k<mergeCellArr.length;k++)
	            	{
	            		if(i==mergeCellArr[k].index&&nl[j].f==mergeCellArr[k].cellName){
	            		tableString +=' rowspan="'+mergeCellArr[k].rowspan+'" ';
	            		
	            		 if(nl[j].f=="ID"){
	            		 	flagnum=true;
	            		 	rows[i][nl[j].f]=i+1-countrow;
	            		 	countrow += (mergeCellArr[k].rowspan-1);
	            		 }
	            		 break;
	            		}else {
	            			if(i>mergeCellArr[k].index&&i-mergeCellArr[k].index+1<=mergeCellArr[k].rowspan&&nl[j].f==mergeCellArr[k].cellName){
		            			//mergeCellArr[k].index=i;
		            			tableString += ' style="text-align:' + nl[j].a + ';display:none;"';
		            			rowsflag=true;
		            			break;
	            			}
	            		}
	            	} 	
	            	if(!rowsflag){
	                	tableString += ' style="text-align:' + nl[j].a + ';" ';
	                	 if(nl[j].f=="ID"&&!flagnum)	{
	                	 	rows[i][nl[j].f]=i+1-countrow;
	                	 }
	                }
	                flagnum=false;
	                rowsflag=false;
	              // }else
	            //		tableString += ' style="text-align:' + nl[j].a + ';" ';
	           // }
	            
	            tableString += '>';
	           
	             if (e + 2 == nl[j].f.length) 
	            {
	            	if(rows[i][nl[j].f.substring(0, e)]==null||rows[i][nl[j].f.substring(0, e)]=='undefined')
	            		rows[i][nl[j].f.substring(0, e)]="";
	            	tableString += rows[i][nl[j].f.substring(0, e)];
	            }
	            else{
	            		if(rows[i][nl[j].f]==null)//||rows[i][nl[j].f]=='undefined'||typeof rows[i][nl[j].f]=="undefined"
	            		rows[i][nl[j].f]="";
	            		 	//if(typeof (rows[i][nl[j].f])=='object')rows[i][nl[j].f]="";
			              //else {
			               		  if(typeof(fm[m])=="undefined")
			               		 {
			               			tableString += rows[i][nl[j].f]; 
			               		}
			               		else {
			               		/* if(fm[m](rows[i][nl[j].f],rows[i],null)=="undefined"||fm[m](rows[i][nl[j].f],rows[i],null)==null)
			               			fm[m](rows[i][nl[j].f],rows[i],null)==""; */
			               	//console.info(fm[m](rows[i][nl[j].f],rows[i],null))
			               		tableString +=fm[m](rows[i][nl[j].f],rows[i],null);
			               		} 
			              // 	}
			               	m++;
	              } 
            tableString += '</td>';
        });
        m=0;
        tableString += '\n</tr>';
    }
    tableString += '\n</table>';
    tableString+='</div>';
    return tableString;
/*window.document.body.innerHTML = tableString;
window.print(); */
}


//var spriceunit="元";//单价单位
//var tpriceunit="万元";//总价单位
//var areaunit="平方米";

var MypSize=20;
var MypList=[20,50,100,150,200];



var datagrid;var dNameinfo;
var cNameAllinfo=[];
var cNameSomeinfo=[];
//============================================


$(function(){
$.ajax({type: "post",url:'/assetsManageSys/DepartmentServlet?method=getAlldName',dataType: "json",async:false,
             success: function(data){
             	dNameinfo = data;
           }});
$.ajax({type: "post",url:'/assetsManageSys/CompanyServlet?method=getAllCompanyName',dataType: "json",async:false,
             success: function(data){
             	var temp=[];
             	temp = temp.concat(data); 
             	cNameAllinfo= temp ;
             	cNameSomeinfo=data.splice(data.pop());
				 }});
});         



function memorySettingColumnMenu(Header_menu,printDatagrid){
	var item;
	  	var columns =printDatagrid.datagrid("options").columns;// 得到columns对象
		for(var i=0;i<columns[0].length;i++)
	    {
			item=Header_menu.menu('findItem',columns[0][i].title);
		     if (item!=null&&item.iconCls != 'icon-ok'&&item.name[0]!="dateString") {
							 if(item.name[0]=="entryValue"||item.name[0]=="Usearea"||item.name[0]=="Useprice"||
						item.name[0]=="UsepledgeArea"||item.name[0]=="UsePledgeDate"||item.name[0]=="Uselandsale"
						||item.name[0]=="UselandPledge"||item.name[0]=="UsehousePledge"){//item.name[0]=="UseAllPledge"||
								 
							 for(var i=0;i<item.name.length;i++)
							 {printDatagrid.datagrid('hideColumn', item.name[i]);}
							 }/*else if(item.name[0]=="UselandPledge"){
									for(var j=0;j<itemarr.length;j++)
									{printDatagrid.datagrid('hideColumn', itemarr[j]);}
								}*/
							 
							 printDatagrid.datagrid('hideColumn', item.name[0]);
								Header_menu.menu('setIcon', {
									target : item.target,
									iconCls : 'icon-empty', 
								});
							 }
			}
		
		Header_menu.menu({
			hideOnUnhover:false,
				onClick : function(item) {
					Header_menu.menu('show');
					if (item.iconCls == 'icon-ok'&&item.name[0]!="dateString") {
						 if(item.name[0]=="entryValue"||item.name[0]=="Usearea"||item.name[0]=="Useprice"||
						 item.name[0]=="UsepledgeArea"||item.name[0]=="UsePledgeDate"||item.name[0]=="UseareaPledge"
							 ||item.name[0]=="UselandPledge"||item.name[0]=="UsehousePledge"){//||item.name[0]=="Uselandsale"
							//||item.name[0]=="UseAllPledge" 
						 for(var i=0;i<item.name.length;i++)
						{printDatagrid.datagrid('hideColumn', item.name[i]);}
						}/*else if(item.name[0]=="UselandPledge"){
							for(var j=0;j<itemarr.length;j++)
							{printDatagrid.datagrid('hideColumn', itemarr[j]);}
						}else if(item.name[0]=="UseAllPledge"){
							for(var j=0;j<itemarrAll.length;j++)
							{printDatagrid.datagrid('hideColumn', itemarrAll[j]);}
						}*/
							printDatagrid.datagrid('hideColumn', item.name[0]);
							Header_menu.menu('setIcon', {
								target : item.target,
								iconCls : 'icon-empty', 
							}); 
						
					} else {
						if(item.name[0]=="entryValue"||item.name[0]=="Usearea"||item.name[0]=="Useprice"||
						item.name[0]=="UsepledgeArea"||item.name[0]=="UsePledgeDate"||item.name[0]=="UseareaPledge"
							||item.name[0]=="UselandPledge"||item.name[0]=="UsehousePledge"
						){
						for(var i=0;i<item.name.length;i++)
						{printDatagrid.datagrid('showColumn', item.name[i]);}
						
						}/*else if(item.name[0]=="UselandPledge"){
							for(var j=0;j<itemarr.length;j++)
							{printDatagrid.datagrid('showColumn', itemarr[j]);}
						}else if(item.name[0]=="UseAllPledge"){
							for(var j=1;j<itemarrAll.length;j++)
							{printDatagrid.datagrid('showColumn', itemarrAll[j]);}
								printDatagrid.datagrid('reload');
						}*/
						printDatagrid.datagrid('showColumn', item.name[0]);
						Header_menu.menu('setIcon', {
							target : item.target,
							iconCls : 'icon-ok',
						});
					}
				 
				}
			});
		
		
	return Header_menu;
}
var itemarr=new Array();
var itemarr_house=new Array();//房产
var itemarrAll=new Array();//总计抵押
var firstflag=false;

	function createColumnMenu(Header_menu,printDatagrid){
		
	/*var itemarr=new Array();
	var itemarrAll=new Array();//总计抵押
	var firstflag=false;*/
	Header_menu = $('<div></div>').appendTo('body');
	
		Header_menu.menu({
		hideOnUnhover:false,
			onClick : function(item) {
				Header_menu.menu('show');
				
				if (item.iconCls == 'icon-ok'&&item.name[0]!="dateString") {
					 if(item.name[0]=="entryValue"||item.name[0]=="Usearea"||item.name[0]=="Useprice"||
					 item.name[0]=="UsepledgeArea"||item.name[0]=="UsePledgeDate"||item.name[0]=="UseareaPledge"
						 ){
					 for(var i=0;i<item.name.length;i++)
					{printDatagrid.datagrid('hideColumn', item.name[i]);}
					}else if(item.name[0]=="UselandPledge"){
						
						for(var j=0;j<itemarr.length;j++)
						{printDatagrid.datagrid('hideColumn', itemarr[j]);}
					}else if(item.name[0]=="UsehousePledge"){
						for(var j=0;j<itemarr_house.length;j++)
						{printDatagrid.datagrid('hideColumn', itemarr_house[j]);}
					}/*else if(item.name[0]=="UseAllPledge"){
						for(var j=0;j<itemarrAll.length;j++)
						{printDatagrid.datagrid('hideColumn', itemarrAll[j]);}
					}*/
						//printDatagrid.datagrid('hideColumn', item.name[0]);//有问题
						Header_menu.menu('setIcon', {
							target : item.target,
							iconCls : 'icon-empty', 
						}); 
					
				} else {
					
					if(item.name[0]=="entryValue"||item.name[0]=="Usearea"||item.name[0]=="Useprice"||
					item.name[0]=="UsepledgeArea"||item.name[0]=="UsePledgeDate"||item.name[0]=="UseareaPledge"
					){
						
					for(var i=0;i<item.name.length;i++)
					{printDatagrid.datagrid('showColumn', item.name[i]);}
					
					}else if(item.name[0]=="UselandPledge"){
						for(var j=2;j<itemarr.length;j++)
						{printDatagrid.datagrid('showColumn', itemarr[j]);}
					}else if(item.name[0]=="UsehousePledge"){
						for(var j=2;j<itemarr_house.length;j++)
						{printDatagrid.datagrid('showColumn', itemarr_house[j]);}
					}/*else if(item.name[0]=="UseAllPledge"){
						for(var j=1;j<itemarrAll.length;j++)
						{printDatagrid.datagrid('showColumn', itemarrAll[j]);}
							//printDatagrid.datagrid('reload');
					}*/
					
					//printDatagrid.datagrid('showColumn', item.name[0]); //有问题
					Header_menu.menu('setIcon', {
						target : item.target,
						iconCls : 'icon-ok',
					});
				}
			 
			}
		});
   	//var frozenColumns = printDatagrid.datagrid("options").frozenColumns;  // 得到frozenColumns对象
    var columns =printDatagrid.datagrid("options").columns;// 得到columns对象
		for(var i=0;i<columns[0].length;i++)
    {
   		if(typeof (columns[0][i].title) != "undefined"&&columns[0][i].title!="dateString") {
   			if(columns[0][i].field=="entryValue")
   			{
   				Header_menu.menu('appendItem', {
					text : columns[0][i].title,
					name :[columns[0][i].field,"entryValue_Original","entryValue_Used","entryValue_Pure"],
					iconCls : 'icon-ok',
				});
   			}else if(columns[0][i].field=="Usearea")
   			{
   				Header_menu.menu('appendItem', {
					text : columns[0][i].title,
					name :[columns[0][i].field,"area","aream"],
					iconCls : 'icon-ok',
				});
   			}else if(columns[0][i].field=="Useprice"){
   				Header_menu.menu('appendItem', {
					text : columns[0][i].title,
					name :[columns[0][i].field,"price","totalPrice"],
					iconCls : 'icon-ok',
				});
   			}else if(columns[0][i].field=="UsepledgeArea"){
   				Header_menu.menu('appendItem', {
					text : columns[0][i].title,
					name :[columns[0][i].field,"pledgeArea","pledgeAream"],
					iconCls : 'icon-ok',
				});
   			}else if(columns[0][i].field=="UsePledgeDate"){
   				Header_menu.menu('appendItem', {
					text : columns[0][i].title,
					name :[columns[0][i].field,"year","limitDate"],
					iconCls : 'icon-ok',
				});
   			}else if(columns[0][i].field=="UseareaPledge"){
   				Header_menu.menu('appendItem', {
					text : columns[0][i].title,
					name :[columns[0][i].field,"areaPledge","areaPledgem"],
					iconCls : 'icon-ok',
				});
   			}else if(columns[0][i].field=="UselandPledge"){
   				itemarr.push(columns[0][i].field);
   			 for(var j=2;j<columns[1].length;j++){
   			itemarr.push(columns[1][j].field);} 
   			Header_menu.menu('appendItem', {
				text : columns[0][i].title,
				name :itemarr,
				iconCls : 'icon-ok',
				}); 
				/* for(var j=0;j<columns[1].length;j++){
   			itemarr.push(columns[1][j].field);
   				 Header_menu.menu('appendItem', {
						text : columns[1][j].title,
						name :[columns[1][j].field],
						iconCls : 'icon-ok',
						}); 
						} */
   			}else if(columns[0][i].field=="UsehousePledge"){
   				itemarr_house.push(columns[0][i].field);
      			 for(var j=2;j<columns[1].length;j++){
      			itemarr_house.push(columns[1][j].field);} 
      			Header_menu.menu('appendItem', {
   				text : columns[0][i].title,
   				name :itemarr_house,
   				iconCls : 'icon-ok',
   				}); 
      			}/*else if(columns[0][i].field=="UseAllPledge"){
   			//if(!firstflag){
   			itemarrAll.push(columns[0][i].field);
   			for(var j=2;j<columns[1].length;j++){
   			itemarrAll.push(columns[1][j].field);
   			//firstflag=true;
   			}
   			//console.info(itemarrAll);
   				Header_menu.menu('appendItem', {
					text : columns[0][i].title,
					name :itemarrAll,
					iconCls : 'icon-ok',
				});
   			//}
   			}*/
   			
				else{
						Header_menu.menu('appendItem', {
						text : columns[0][i].title,
						name :[columns[0][i].field],
						iconCls : 'icon-ok',
						}); 
				}
			}
    }
    //firstflag=false;
	return 	Header_menu;   
}
    
	 	//在系统注册表中,设置网页打印的页眉页脚和页边距
       function PageSetup_Null() {
            var HKEY_Root, HKEY_Path, HKEY_Key;
            HKEY_Root = "HKEY_CURRENT_USER";
            HKEY_Path = "\\Software\\Microsoft\\Internet Explorer\\PageSetup\\";
            try {
                var Wsh = new ActiveXObject("WScript.Shell");
                HKEY_Key = "header";
                //设置页眉（为空）
                //Wsh.RegRead(HKEY_Root+HKEY_Path+HKEY_Key)可获得原页面设置   
                Wsh.RegWrite(HKEY_Root + HKEY_Path + HKEY_Key, "");
                HKEY_Key = "footer";
                //设置页脚（为空）   
                Wsh.RegWrite(HKEY_Root + HKEY_Path + HKEY_Key, "");
                //设置纸张选项纵向还是横向
                HKEY_Key = "orientation";
                //设置左页边距
                Wsh.RegWrite(HKEY_Root + HKEY_Path + HKEY_Key, 2);
               
                //这里需要浏览器版本，8.0以下的页边距设置与8.0及以上不一样，注意注册表里的单位是英寸，打印设置中是毫米，1英寸=25.4毫米
                if (checkIEV() < 8.0) {
                    HKEY_Key = "margin_left";
                    //设置左页边距
                    Wsh.RegWrite(HKEY_Root + HKEY_Path + HKEY_Key, "0.196850393701");///5mm
                    HKEY_Key = "margin_right";
                    //设置右页边距
                    Wsh.RegWrite(HKEY_Root + HKEY_Path + HKEY_Key, "0.196850393701");
                    HKEY_Key = "margin_top";
                    //设置上页边距
                    Wsh.RegWrite(HKEY_Root + HKEY_Path + HKEY_Key, "0.629921259843");//16mm
                    
                    HKEY_Key = "margin_bottom";
                    //设置下页边距   
                   // Wsh.RegWrite(HKEY_Root + HKEY_Path + HKEY_Key, "0.4");
                    Wsh.RegWrite(HKEY_Root + HKEY_Path + HKEY_Key, "0.196850393701");

					//设置纸张选项纵向还是横向
                    HKEY_Key = "orientation";
                    Wsh.RegWrite(HKEY_Root + HKEY_Path + HKEY_Key, 2);
                }else {
                    HKEY_Key = "margin_left";
                    //设置左页边距
                    Wsh.RegWrite(HKEY_Root + HKEY_Path + HKEY_Key, "0.196850393701");
                    HKEY_Key = "margin_right";
                    //设置右页边距
                    Wsh.RegWrite(HKEY_Root + HKEY_Path + HKEY_Key, "0.196850393701");
                    HKEY_Key = "margin_top";
                    //设置上页边距
                    Wsh.RegWrite(HKEY_Root + HKEY_Path + HKEY_Key, "0.629921259843");
                    
                   // HKEY_Key = "margin_bottom";
                    //设置下页边距   
                   // Wsh.RegWrite(HKEY_Root + HKEY_Path + HKEY_Key, "0.4");

					//设置纸张选项纵向还是横向
                    HKEY_Key = "orientation";
                    //设置左页边距
                    Wsh.RegWrite(HKEY_Root + HKEY_Path + HKEY_Key, 2);
					//设置纸张选项缩放比例
                    //HKEY_Key = "Shrink_To_Fit";
                   
                    //Wsh.RegWrite(HKEY_Root + HKEY_Path + HKEY_Key, "no");
                }
            }
            catch (e) {
            alert("ActiveX控件被禁用,请按下面步骤操作：\n1、请将浏览器切换至兼容模式或使用IE浏览器\n2、请打开IE浏览器‘工具’菜单/‘选项’/‘安全’下的‘自定义级别’，\n把‘对没有标记为安全的activex控件进行初始化和脚本运行’设置为‘启用’。\n3、刷新本页 \n4、请确认选择的列正确否则可能造成格式错误!");
            }
                //noPrint();
}
       //暂时未使用
       function printorder()   
       {   
                PageSetup_temp();//取得默认值   
                PageSetup_Null();//设置页面   
                factory.execwb(6,6);//打印页面
                PageSetup_Default();//还原页面设置   
                window.close();   
       }
//检查得到IE的版本
   function checkIEV() {
           var X, V, N;
           V = navigator.appVersion;
           N = navigator.appName;
           if (N == "Microsoft Internet Explorer")
               X = parseFloat(V.substring(V.indexOf("MSIE") + 5, V.lastIndexOf("Windows")));
           else
               X = parseFloat(V);
           return X;
    } 
 //设置网页打印的页眉页脚和页边距为默认值   
	function   PageSetup_Default()   
	{      
		try   
		{   
	   var Wsh=new ActiveXObject("WScript.Shell");      
	   HKEY_Key="header";   
		//还原页眉   
	   Wsh.RegWrite(HKEY_Root+HKEY_Path+HKEY_Key,head);   
	   HKEY_Key="footer";   
		//还原页脚   
	   Wsh.RegWrite(HKEY_Root+HKEY_Path+HKEY_Key,foot);   
	   HKEY_Key="margin_bottom";   
		//还原下页边距   
	   Wsh.RegWrite(HKEY_Root+HKEY_Path+HKEY_Key,bottom);   
	   HKEY_Key="margin_left";   
		//还原左页边距   
	   Wsh.RegWrite(HKEY_Root+HKEY_Path+HKEY_Key,left);   
	   HKEY_Key="margin_right";   
		//还原右页边距   
	   Wsh.RegWrite(HKEY_Root+HKEY_Path+HKEY_Key,right);   
	   HKEY_Key="margin_top";   
		//还原上页边距   
	   Wsh.RegWrite(HKEY_Root+HKEY_Path+HKEY_Key,top);   
	}   
	catch(e){
	     alert("不允许ActiveX控件");   
	}
} 
	
	//取得页面打印设置的原参数数据   
	function PageSetup_temp() {   
	    try   
	{   
	   var Wsh=new ActiveXObject("WScript.Shell");   
	   HKEY_Key="header";   
		//取得页眉默认值   
	   head = Wsh.RegRead(HKEY_Root+HKEY_Path+HKEY_Key);   
	   HKEY_Key="footer";   
		//取得页脚默认值   
	   foot = Wsh.RegRead(HKEY_Root+HKEY_Path+HKEY_Key);   
	   HKEY_Key="margin_bottom";   
		//取得下页边距   
	   bottom = Wsh.RegRead(HKEY_Root+HKEY_Path+HKEY_Key);   
	   HKEY_Key="margin_left";   
		//取得左页边距   
	   left = Wsh.RegRead(HKEY_Root+HKEY_Path+HKEY_Key);   
	   HKEY_Key="margin_right";   
		//取得右页边距   
	   right = Wsh.RegRead(HKEY_Root+HKEY_Path+HKEY_Key);   
	   HKEY_Key="margin_top";   
		//取得上页边距   
	   top = Wsh.RegRead(HKEY_Root+HKEY_Path+HKEY_Key);   
		}   
		catch(e){   
	     	alert("不允许ActiveX控件");   
			}   
	}
//得到datagrid的当前显示列
	
function getprintArray(printDatagrid){
		var printArray=new Array();
		var columns =printDatagrid.datagrid("options").columns;
		  if (typeof columns != 'undefined' && columns != '') {
		for(var i=0;i<columns[0].length;i++){
		 	if(typeof columns[0][i] != 'undefined'&&!columns[0][i].hidden&&columns[0][i].field!='ID'){
		 		printArray.push(columns[0][i].field);
		 	}
		 }
		}
		  return printArray;
}
	
/*
 /*for(int j=0;j<jsaMerge.size();j++){
                JSONObject jso = jsaMerge.getJSONObject(j);
                System.out.println(((Integer)jso.get("index")+6)+","+
                (String) jso.get("cellName")+","+
                (Integer) jso.get("rowspan"));
               if(rowNo==((Integer)jso.get("index")+6)&&
            	enFields[i-1].equals((String) jso.get("cellName"))){
            	   row_m=rowNo;
            	   rowSpan_m=rowNo+(Integer)jso.get("rowspan");
            	   //sheet.mergeCells(i,rowNo, i,rowSpan_m);
            	 System.out.println(fieldValue+",,"+rowNo+",,"+enFields[i-1]);  
               }else{
            	   if(rowNo>((Integer)jso.get("index")+6)&&rowNo-(Integer)jso.get("index")+6+1<=(Integer)jso.get("rowspan")&&enFields[i-1].equals((String) jso.get("cellName")))
            	   {
            		  // sheet.mergeCells(i,rowNo, i,rowSpan_m);
            	   } 
               }
                	
            }*/ 
		



