jQuery(function(){
	var _quickSearchInput = jQuery("#quickSearch .inputSearch input");
	var _quickSearchSubmit = jQuery("#quickSearch .submitSearch");
	var _quickSearch = jQuery("#quickSearch .inputSearch");
	var _quickSearchMaster = jQuery("#quickSearch");
	
	_quickSearch.click(function(e){
		e.stopImmediatePropagation();
	});
	$('body').click(function(){
		if (_quickSearchInput.val() == "")
		{
			_quickSearch.animate({width:0}, 300, function(){
				_quickSearch.addClass('hide');
			});
			_quickSearchMaster.removeClass('active');
		}
	});
	_quickSearchSubmit.click(function(e){
		e.stopImmediatePropagation();
		_quickSearchInput.focus();
		if (_quickSearchMaster.hasClass('active'))
		{
			document.location.href = "search.html";
			return false;
		}
		_quickSearch.css('width', 0)
			.removeClass('hide')
			.animate({width:170}, 300);
		_quickSearchMaster.addClass('active');
		_quickSearchInput.focus();
		return false;
	});
	
	_quickSearchInput.keypress(function(e){
		if (e.which == 13)
			document.location.href = "search.html";
	});

	if (_quickSearchInput.val() != "") {
		_quickSearch.css('width', 170).removeClass('hide');
		_quickSearchMaster.addClass('active');
	}
	
	$('.downloads .url>input').click(function(){
		$(this).select();
	});
	
	var _github = jQuery('#github div');
	var _github2;
	var _zX, _zY;
	
	if (_github) {
		_github2 = _github.find('a');
		_github2.mousemove(function(e){
			_zX = ((e.offsetX - 190) / 50);
			_zY = ((e.offsetY - 75) / 20);
			
			_github2.css('left', _zX)
				.css('top', _zY - 20);

			_github.css('background-position-x', (_zX * -1) - 10)
				.css('background-position-y', (_zY * -1) - 10);
		});
	}
	$('.dropdown-toggle').dropdown();
});