
var runningLive = false;

getLatestImage = function(data) {
	$('<img id="acq-image"/>')
		.attr('src', serverUrl + '/snapImage')
		.load(function(){
			$('#acq-image').remove();
			$('#acq-image-div').append( $(this) );
			if (runningLive) {
				getLatestImage();
			}
		});
}

registerEnterKey = function(selectorStr) {
    $(selectorStr).keyup(function(e) {
        if (e.which == 13) // Enter key
            $(this).blur();
    });
    $(selectorStr).keypress(function(e) { return e.which != 13; });
}