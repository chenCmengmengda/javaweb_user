var pageIndex;
var pageNum;
var user=decodeURI(window.location.search.slice(window.location.search.lastIndexOf('?')+1));

//欢迎页
function welcome(){
	$('#data-wrap').empty();
	$('.container-right').css({border:'none'});
	$('#data-wrap').append(
		"<h1 class='wel-1'>欢迎登录用户管理系统</h1>"+
		"<h2 class='wel-2'>在这里，您可以管理您的用户信息</h2>"+
		"<h2 class='wel-3'>当前时间:</h2>"+
		"<h2 class='wel-4' id='date'></h2>"
	);
	var date=new Date();
	date=date.toLocaleString();
	$('#date').text(date);
	setInterval(function(){
		date=new Date();
		date=date.toLocaleString();
		$('#date').text(date);
	});
}

//设置cookie
function setCookie(str,val){
	obj=new Object();
	obj.str=str;
	obj.val=val;
	doData('/UsersSystem/user?method=setCookie',obj,null);
}

//获取cookie
function getCookie(str){
	obj=new Object();
	obj.str=str;
	var val;
	doDataSyn('/UsersSystem/user?method=getCookie',obj,function(data){
		val=data.val;
	});
	return val;
}

//总数据查询
function pageData(){
	doDataSyn('/UsersSystem/user?method=getPage',null,function(data){
		pageNum=data.pageNum;
	});
}

//总数据显示
function display(page){
	pageData();
	if(pageIndex==null || pageIndex==0){
		pageIndex=1;
	}
	else if(pageIndex>pageNum){
		pageIndex=pageNum;
	}
	if(page=='first'){
		pageIndex=1;
	}
	else if(page =='pre' && pageIndex>1){
		pageIndex--;
	}
	else if(page=='next' && pageIndex<pageNum){
		pageIndex++;
	}
	else if(page=='last'){
		pageIndex=pageNum;
	}
	var obj=new Object();
	obj.pageIndex=pageIndex;
	doData('/UsersSystem/user?method=findAll',obj,function(data){
		$('#data-wrap').empty();
		$('.container-right').css({border:'1px solid #8c8c8c'});
		$('#data-wrap').append(
			"<div class='container-header'>"+
				"<h1>用户信息表</h1>"+
			"</div>"+
			"<div class='table-wrap'>"+
				"<input type='button' onclick=window.location.href='add.html?'+user value='添加'/>&nbsp"+
				"<input type='text' id='txtSearch' autocomplete='off' placeholder='请输入关键字'/>&nbsp"+
				"<input type='button' onclick=search('first') value='查询' />"+
				"<table class='table'>"+
					"<thead>"+
						"<tr>"+
							"<td>Num</td>"+
							"<td>UserName</td>"+
							"<td>Sex</td>"+
							"<td>UserPassword</td>"+
							"<td>Email</td>"+
							"<td>type</td>"+
							"<td>Edit/Delete</td>"+
						"</tr>"+
					"</thead>"+
					"<tbody id='data'>"+
					"</tbody>"+
				"</table>"+
			"</div>"+
			"<div class='page' id='page'></div>"
		);
		$.each(data,function(index,item){
			$('#data').append(
				"<tr>"+
					"<td>"+item.id+"</td>"+
					"<td>"+item.username+"</td>"+
					"<td>"+item.Sex+"</td>"+
					"<td>"+item.password+"</td>"+
					"<td>"+item.Email+"</td>"+
					"<td>"+item.type+"</td>"+
					"<td>"+
						"<a href='edit.html?"+item.id+"&"+user+"'>编辑</a>/"+
						"<a href=javascript:del("+item.id+")>删除</a>"+
					"</td>"+	
				"</tr>"
			);
		});
		$('#page').append(
			"<a href=javascript:display('first')>首页</a>&nbsp"+
			"<a href=javascript:display('pre')>上一页</a>&nbsp"+
			"<a href=javascript:display('next')>下一页</a>&nbsp"+
			"<a href=javascript:display('last')>尾页</a>&nbsp"+
			"<a>当前页:</a>"+
			"<a>"+pageIndex+"/</a>"+
			"<a>"+pageNum+"</a>"
		);
	});
	setCookie('pageIndex',pageIndex);
}

//查询页数
function pageSearch(obj){
	doDataSyn('/UsersSystem/user?method=getSearchPage',obj,function(data){
		pageNum=data.pageNum;
	});
}

//查询显示
function search(page){
	var obj_search=new Object();
	obj_search.search=document.getElementById('txtSearch').value;
	pageSearch(obj_search);
	if(page=='first'){
		pageIndex=1;
	}
	else if(page =='pre' && pageIndex>1){
		pageIndex--;
	}
	else if(page=='next' && pageIndex<pageNum){
		pageIndex++;
	}
	else if(page=='last'){
		pageIndex=pageNum;
	}
	var obj=new Object();
	obj.search=document.getElementById('txtSearch').value;
	obj.pageIndex=pageIndex;
	doData('/UsersSystem/user?method=findSearchAll',obj,function(data){
		$('#data').empty();
		$('#page').empty();
		$.each(data,function(index,item){
			$('#data').append(
				"<tr>"+
					"<td>"+item.id+"</td>"+
					"<td>"+item.username+"</td>"+
					"<td>"+item.Sex+"</td>"+
					"<td>"+item.password+"</td>"+
					"<td>"+item.Email+"</td>"+
					"<td>"+item.type+"</td>"+
					"<td>"+
						"<a href='edit.html?"+item.id+"&"+user+"'>编辑</a>/"+
						"<a href=javascript:del("+item.id+")>删除</a>"+
					"</td>"+
				"</tr>"
			);
		});
		$('#page').append(
			"<a href=javascript:display()>返回</a>&nbsp"+
			"<a href=javascript:search('first')>首页</a>&nbsp"+
			"<a href=javascript:search('pre')>上一页</a>&nbsp"+
			"<a href=javascript:search('next')>下一页</a>&nbsp"+
			"<a href=javascript:search('last')>尾页</a>&nbsp"+
			"<a>当前页:</a>"+
			"<a>"+pageIndex+"/</a>"+
			"<a>"+pageNum+"</a>"
		);
	});
}

//删除
function del(val){
	if(confirm('你确定要删除这条数据吗?')){
		var obj=new Object();
		obj.id=val;
		doData('/UsersSystem/user?method=delete',obj,function(data){
			if(data.message==true){
				display();
			}
			else{
				alert('数据删除失败');
			}
		})
	}
}

$(function(){
	$('.user').text(user);
	welcome();
	$('#welcome').click(function(){
		welcome();
	});
	$('#userInfo').click(function(){
		pageIndex=getCookie('pageIndex');
		display();
	});
})