var menuList = $('.menuList');
$('.menu > .menuParent').each(function(i) {
	var menu = $(this).find('.menuList');
	if(menu.length > 0){
		$(this).find('.ListTitle').click(function(){
			if(menu.css('display') == 'none'){
				menu.slideDown(300);
			}else{
				menu.slideUp(300);
			}
		});
	}
});