var index;
var flag;
var nowDate=new Date();

//下拉
function dropdown(){
	$('.nav-list').click(function(){
		index=$(this).index()/2-0.5;
		var dwHeight=$('.dropdown').eq(index).find('.dropdown-list').height();
		if(dwHeight==0){
			flag=false;
		}
		else{
			flag=true;
		}
		if(index>=0 && flag==false && (new Date-nowDate)>=500){
			$('.dropdown').eq(index).find('.dropdown-list').css({border:'1px solid #8c8c8c'});
			$('.dropdown').eq(index).find('.dropdown-list').animate({height:'40px'},500);
		}
		else if(index>=0 && flag==true && (new Date-nowDate)>=500){
			$('.dropdown').eq(index).find('.dropdown-list').css({border:'none'});
			$('.dropdown').eq(index).find('.dropdown-list').animate({height:'0px'},500);
		}
	})
}

$(function(){
	dropdown();
})