
/**
 * ===== HEADER & MENU LEFT =====
 */

var CURRENT_URL = window.location.href.split('#')[0].split('?')[0],
    $BODY = $('body'),
    $MENU_TOGGLE = $('#menu_toggle'),
    $SIDEBAR_MENU = $('#sidebar-menu'),
    $SIDEBAR_FOOTER = $('.sidebar-footer'),
    $LEFT_COL = $('.left_col'),
    $MAIN_CONTENT = $('.main_content'),
    $NAV_MENU = $('.nav_menu'),
    $FOOTER = $('footer');

	
// Sidebar
function init_sidebar() {
	// TODO: This is some kind of easy fix, maybe we can improve this
	var setContentHeight = function () {
		// reset height
		var bodyHeight = $BODY.outerHeight(),
			footerHeight = $BODY.hasClass('footer_fixed') ? -10 : $FOOTER.height(),
			leftColHeight = $LEFT_COL.eq(1).height() + $SIDEBAR_FOOTER.height(),
			contentHeight = bodyHeight < leftColHeight ? leftColHeight : bodyHeight;

		// normalize content
		contentHeight -= $NAV_MENU.height() + footerHeight;

		$MAIN_CONTENT.css('min-height', window.innerHeight - ( $('footer').height() + 30) );
	};

  	$SIDEBAR_MENU.find('a').on('click', function(ev) {
        var $li = $(this).parent();


    	if ($('body').is('.nav-sm')) {
    		return;
    	}
        if ($li.is('.active')) {
            $li.removeClass('active active-sm');
            $('ul:first', $li).slideUp(function() {
                setContentHeight();
            });
        } else {
            // prevent closing menu if we are on child menu
            if (!$li.parent().is('.child_menu')) {

                $SIDEBAR_MENU.find('li').removeClass('active active-sm');
                $SIDEBAR_MENU.find('li ul').slideUp();
            }
            $li.addClass('active');

            $('ul:first', $li).slideDown(function() {
                setContentHeight();
            });
        }
    });

	// toggle small or large menu 
	$MENU_TOGGLE.on('click', function() {
		$(this).toggleClass('open');

		if ($BODY.hasClass('nav-md')) {
			$SIDEBAR_MENU.find('li.active ul').hide();
			$SIDEBAR_MENU.find('li.active').addClass('active-sm').removeClass('active');
		} else {
			$SIDEBAR_MENU.find('li.active-sm ul').show();
			$SIDEBAR_MENU.find('li.active-sm').addClass('active').removeClass('active-sm');
		}

		$BODY.toggleClass('nav-md nav-sm');

		setContentHeight();
	});

	// Open menu child active
	if($('body').hasClass('nav-md')){
		// check active menu
		$SIDEBAR_MENU.find('a[href="' + CURRENT_URL + '"]').parent('li').addClass('current-page');

		$SIDEBAR_MENU.find('a').filter(function () {
			return this.href == CURRENT_URL;
		}).parent('li').addClass('current-page').parents('ul').slideDown(function() {
			setContentHeight();
		}).parent('li').addClass('active');
	}

	// Auto add arrow to list if list have ul child menu
	$('.child_menu ul').prev('a').append('<span class="fa fa-angle-down pull-right"></span>');
	
}

// Change skin
function changeSkin() {
    var my_skins = [
    	"skin-brown",
    	"skin-blue",
    	"skin-green"
    ];
	// Change skins 
	$('.change-skins a').on('click', function(e) {
		e.preventDefault();

		var skins = $(this).data('color');
		localStorage.setItem('color', skins);

		$('.change-skins a').removeClass('active');
		$(this).addClass('active');

	    $.each(my_skins, function (i) {
	      $("body").removeClass(my_skins[i]);
	    });
	    $("body").addClass(skins);

	});	
	
	// Set skins when refresh page
	var dataColor = localStorage.getItem('color');
	if(dataColor){
		$('body').addClass(dataColor);

		$('.change-skins a').removeClass('active');
		$('.change-skins a[data-color= ' + dataColor + ']').addClass('active');		
	} 
}

$(document).ready(function() {	
	init_sidebar();

    // Add class fixed .top-heading when scroll
    $(window).scroll(function(){
    	var getPositon = $(window).scrollTop();
    	if(getPositon >= 1){
    		$('.top-heading').addClass('fixed');
    	} else {
    		$('.top-heading').removeClass('fixed');
    	}
    });

    changeSkin();
   
});	
/**
 * ===== END HEADER & MENU LEFT =====
 */


/**
 * ===== BODY & POPUP =====
 */
$(document).ready(function() {
    // Popup todo
    $('.addlist-popup button').on('click', function() {
    	$('.addlist-popup').fadeOut();
    });
    $('.open-pu-todo button').on('click', function(e){
    	e.stopPropagation();
    	$(this).parents().find('.addlist-popup').fadeIn();
    });
});	

/**
 * ===== Calendar =====
 */

  $(function () {

   
   var isValidDate = function(value, format) {
		format = format || false;
		// lets parse the date to the best of our knowledge
		if (format) {
			value = parseDate(value);
		}

		var timestamp = Date.parse(value);

		return isNaN(timestamp) == false;
   }
   
   var parseDate = function(value) {
		var m = value.match(/^(\d{1,2})(\/|-)?(\d{1,2})(\/|-)?(\d{4})$/);
		if (m)
			value = m[5] + '-' + ("00" + m[3]).slice(-2) + '-' + ("00" + m[1]).slice(-2);

		return value;
   }
   

 });