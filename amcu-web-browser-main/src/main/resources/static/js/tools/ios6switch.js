//iOS6Switch.js
(function(){
	//FastClick 0.6.7
	function FastClick(a){"use strict";var b,c=this;if(this.trackingClick=!1,this.trackingClickStart=0,this.targetElement=null,this.touchStartX=0,this.touchStartY=0,this.lastTouchIdentifier=0,this.touchBoundary=10,this.layer=a,!a||!a.nodeType)throw new TypeError("Layer must be a document node");this.onClick=function(){return FastClick.prototype.onClick.apply(c,arguments)},this.onMouse=function(){return FastClick.prototype.onMouse.apply(c,arguments)},this.onTouchStart=function(){return FastClick.prototype.onTouchStart.apply(c,arguments)},this.onTouchEnd=function(){return FastClick.prototype.onTouchEnd.apply(c,arguments)},this.onTouchCancel=function(){return FastClick.prototype.onTouchCancel.apply(c,arguments)},FastClick.notNeeded(a)||(this.deviceIsAndroid&&(a.addEventListener("mouseover",this.onMouse,!0),a.addEventListener("mousedown",this.onMouse,!0),a.addEventListener("mouseup",this.onMouse,!0)),a.addEventListener("click",this.onClick,!0),a.addEventListener("touchstart",this.onTouchStart,!1),a.addEventListener("touchend",this.onTouchEnd,!1),a.addEventListener("touchcancel",this.onTouchCancel,!1),Event.prototype.stopImmediatePropagation||(a.removeEventListener=function(b,c,d){var e=Node.prototype.removeEventListener;"click"===b?e.call(a,b,c.hijacked||c,d):e.call(a,b,c,d)},a.addEventListener=function(b,c,d){var e=Node.prototype.addEventListener;"click"===b?e.call(a,b,c.hijacked||(c.hijacked=function(a){a.propagationStopped||c(a)}),d):e.call(a,b,c,d)}),"function"==typeof a.onclick&&(b=a.onclick,a.addEventListener("click",function(a){b(a)},!1),a.onclick=null))}FastClick.prototype.deviceIsAndroid=navigator.userAgent.indexOf("Android")>0,FastClick.prototype.deviceIsIOS=/iP(ad|hone|od)/.test(navigator.userAgent),FastClick.prototype.deviceIsIOS4=FastClick.prototype.deviceIsIOS&&/OS 4_\d(_\d)?/.test(navigator.userAgent),FastClick.prototype.deviceIsIOSWithBadTarget=FastClick.prototype.deviceIsIOS&&/OS ([6-9]|\d{2})_\d/.test(navigator.userAgent),FastClick.prototype.needsClick=function(a){"use strict";switch(a.nodeName.toLowerCase()){case"button":case"select":case"textarea":if(a.disabled)return!0;break;case"input":if(this.deviceIsIOS&&"file"===a.type||a.disabled)return!0;break;case"label":case"video":return!0}return/\bneedsclick\b/.test(a.className)},FastClick.prototype.needsFocus=function(a){"use strict";switch(a.nodeName.toLowerCase()){case"textarea":case"select":return!0;case"input":switch(a.type){case"button":case"checkbox":case"file":case"image":case"radio":case"submit":return!1}return!a.disabled&&!a.readOnly;default:return/\bneedsfocus\b/.test(a.className)}},FastClick.prototype.sendClick=function(a,b){"use strict";var c,d;document.activeElement&&document.activeElement!==a&&document.activeElement.blur(),d=b.changedTouches[0],c=document.createEvent("MouseEvents"),c.initMouseEvent("click",!0,!0,window,1,d.screenX,d.screenY,d.clientX,d.clientY,!1,!1,!1,!1,0,null),c.forwardedTouchEvent=!0,a.dispatchEvent(c)},FastClick.prototype.focus=function(a){"use strict";var b;this.deviceIsIOS&&a.setSelectionRange?(b=a.value.length,a.setSelectionRange(b,b)):a.focus()},FastClick.prototype.updateScrollParent=function(a){"use strict";var b,c;if(b=a.fastClickScrollParent,!b||!b.contains(a)){c=a;do{if(c.scrollHeight>c.offsetHeight){b=c,a.fastClickScrollParent=c;break}c=c.parentElement}while(c)}b&&(b.fastClickLastScrollTop=b.scrollTop)},FastClick.prototype.getTargetElementFromEventTarget=function(a){"use strict";return a.nodeType===Node.TEXT_NODE?a.parentNode:a},FastClick.prototype.onTouchStart=function(a){"use strict";var b,c,d;if(a.targetTouches.length>1)return!0;if(b=this.getTargetElementFromEventTarget(a.target),c=a.targetTouches[0],this.deviceIsIOS){if(d=window.getSelection(),d.rangeCount&&!d.isCollapsed)return!0;if(!this.deviceIsIOS4){if(c.identifier===this.lastTouchIdentifier)return a.preventDefault(),!1;this.lastTouchIdentifier=c.identifier,this.updateScrollParent(b)}}return this.trackingClick=!0,this.trackingClickStart=a.timeStamp,this.targetElement=b,this.touchStartX=c.pageX,this.touchStartY=c.pageY,a.timeStamp-this.lastClickTime<200&&a.preventDefault(),!0},FastClick.prototype.touchHasMoved=function(a){"use strict";var b=a.changedTouches[0],c=this.touchBoundary;return Math.abs(b.pageX-this.touchStartX)>c||Math.abs(b.pageY-this.touchStartY)>c?!0:!1},FastClick.prototype.findControl=function(a){"use strict";return void 0!==a.control?a.control:a.htmlFor?document.getElementById(a.htmlFor):a.querySelector("button, input:not([type=hidden]), keygen, meter, output, progress, select, textarea")},FastClick.prototype.onTouchEnd=function(a){"use strict";var b,c,d,e,f,g=this.targetElement;if((this.touchHasMoved(a)||a.timeStamp-this.trackingClickStart>300)&&(this.trackingClick=!1,this.targetElement=null),!this.trackingClick)return!0;if(a.timeStamp-this.lastClickTime<200)return this.cancelNextClick=!0,!0;if(this.lastClickTime=a.timeStamp,c=this.trackingClickStart,this.trackingClick=!1,this.trackingClickStart=0,this.deviceIsIOSWithBadTarget&&(f=a.changedTouches[0],g=document.elementFromPoint(f.pageX-window.pageXOffset,f.pageY-window.pageYOffset)),d=g.tagName.toLowerCase(),"label"===d){if(b=this.findControl(g)){if(this.focus(g),this.deviceIsAndroid)return!1;g=b}}else if(this.needsFocus(g))return a.timeStamp-c>100||this.deviceIsIOS&&window.top!==window&&"input"===d?(this.targetElement=null,!1):(this.focus(g),this.deviceIsIOS4&&"select"===d||(this.targetElement=null,a.preventDefault()),!1);return this.deviceIsIOS&&!this.deviceIsIOS4&&(e=g.fastClickScrollParent,e&&e.fastClickLastScrollTop!==e.scrollTop)?!0:(this.needsClick(g)||(a.preventDefault(),this.sendClick(g,a)),!1)},FastClick.prototype.onTouchCancel=function(){"use strict";this.trackingClick=!1,this.targetElement=null},FastClick.prototype.onMouse=function(a){"use strict";return this.targetElement?a.forwardedTouchEvent?!0:a.cancelable?!this.needsClick(this.targetElement)||this.cancelNextClick?(a.stopImmediatePropagation?a.stopImmediatePropagation():a.propagationStopped=!0,a.stopPropagation(),a.preventDefault(),!1):!0:!0:!0},FastClick.prototype.onClick=function(a){"use strict";var b;return this.trackingClick?(this.targetElement=null,this.trackingClick=!1,!0):"submit"===a.target.type&&0===a.detail?!0:(b=this.onMouse(a),b||(this.targetElement=null),b)},FastClick.prototype.destroy=function(){"use strict";var a=this.layer;this.deviceIsAndroid&&(a.removeEventListener("mouseover",this.onMouse,!0),a.removeEventListener("mousedown",this.onMouse,!0),a.removeEventListener("mouseup",this.onMouse,!0)),a.removeEventListener("click",this.onClick,!0),a.removeEventListener("touchstart",this.onTouchStart,!1),a.removeEventListener("touchend",this.onTouchEnd,!1),a.removeEventListener("touchcancel",this.onTouchCancel,!1)},FastClick.notNeeded=function(a){"use strict";var b;if("undefined"==typeof window.ontouchstart)return!0;if(/Chrome\/[0-9]+/.test(navigator.userAgent)){if(!FastClick.prototype.deviceIsAndroid)return!0;if(b=document.querySelector("meta[name=viewport]"),b&&-1!==b.content.indexOf("user-scalable=no"))return!0}return"none"===a.style.msTouchAction?!0:!1},FastClick.attach=function(a){"use strict";return new FastClick(a)},"undefined"!=typeof define&&define.amd?define(function(){"use strict";return FastClick}):"undefined"!=typeof module&&module.exports?(module.exports=FastClick.attach,module.exports.FastClick=FastClick):window.FastClick=FastClick;
	var id = 0;
	var sList = new Array, hList = new Array;
	Array.prototype.remove = function(dx){
		if(isNaN(dx) || dx > this.length){
			return false;
		}
		for(var i = 0, n = 0; i < this.length; i ++){
			if(this[i] != this[dx]){
				this[n ++] = this[i];
			}
		}
		this.length -= 1;
	}
	var getBorderWidth = function(e, b){
		var w = Number(e.css("border-" + b + "-width").replace(/px/g, ""));
		isNaN(w) && (w = 0);
		return w;
	}
	var init = function(_elem, _opt){
		if(_elem.attr("ios6switch") == "true") return;
		_elem.attr("ios6switch", "true");
		_opt.showOriginalCheckbox != true && _elem.css("display", "none");
		var switchelement = '<div class="ios6switch ios6switch' + id + '"><div class="ios6switch_shadow_and_highlight"></div><div class="ios6switch_draglayer"><div class="ios6switch_switch_on"></div><div class="ios6switch_thumb"></div><div class="ios6switch_switch_off"></div></div></div>';
		_elem.after(switchelement);
		sList.push("ios6switch" + id);
		hList.push(-1);
		var thisid = id;
		var _switcher = $(".ios6switch" + id);
		_opt.addClass != "" && _switcher.addClass(_opt.addClass);
		_switcher.css({
			height: _opt.size + "px"
		});
		_switcher.find(".ios6switch_switch_on").html(_opt.switchonText);
		_switcher.find(".ios6switch_switch_off").html(_opt.switchoffText);
		_switcher.find(".ios6switch_switch_on").css({
			"background-color": _opt.switchonColor,
			"color": _opt.switchonTextColor,
		});
		_switcher.find(".ios6switch_switch_off").css({
			"background-color": _opt.switchoffColor,
			"color": _opt.switchoffTextColor,
		});
		_opt.thumbSwitchonColor == "" && (_opt.thumbSwitchonColor = _opt.thumbColor);
		_opt.thumbPressedColor == "" && (_opt.thumbPressedColor = _opt.thumbColor);
		_opt.thumbSwitchonPressedColor == "" && (_opt.thumbSwitchonPressedColor = _opt.thumbPressedColor);
		_switcher.find(".ios6switch_thumb").attr("data-thumb-color", _opt.thumbColor);
		_switcher.find(".ios6switch_thumb").attr("data-thumb-on-color", _opt.thumbSwitchonColor);
		_switcher.find(".ios6switch_thumb").attr("data-thumb-pressed-color", _opt.thumbPressedColor);
		_switcher.find(".ios6switch_thumb").attr("data-thumb-on-pressed-color", _opt.thumbSwitchonPressedColor);
		_switcher.find(".ios6switch_thumb").attr("data-pressed", "false");
		setSize(_switcher);
		setThumbColor(_switcher);
		var startpress = function(){
			_switcher.attr("data-disabled") != "true" && _switcher.find(".ios6switch_thumb").attr("data-pressed", "true");
			setThumbColor(_switcher);
		}
		var endpress = function(){
			_switcher.find(".ios6switch_thumb").attr("data-pressed", "false");
			setThumbColor(_switcher);
		}
		_switcher.find(".ios6switch_thumb").mousedown(startpress);
		$(document).mouseup(endpress);
		_switcher.find(".ios6switch_thumb")[0].addEventListener && _switcher.find(".ios6switch_thumb")[0].addEventListener("touchstart", startpress, false);
		_switcher.find(".ios6switch_thumb")[0].addEventListener && document.addEventListener('touchend', endpress, false);
		var disabled = false;
		var posX, lastX, dragged = false, dragging = false, dragging2 = false;
		if(_opt.draggable == true){
			var startdrag = function(e){
				if(disabled != true){
					if(e.changedTouches) {
						e.pageX = e.changedTouches[0].pageX;
					}
					posX = e.pageX - _switcher.find(".ios6switch_draglayer").position().left;
					_switcher.find(".ios6switch_draglayer").stop(true);
					lastX = e.pageX;
					dragging = true;
				}
			}
			var stopdrag = function(){
				if(dragging2 == true){
					dragged = true;
					toggleswitch();
				}
				dragging = false;
				dragging2 = false;
			}
			var move = function(e){
				if(e.changedTouches){
					e.pageX = e.changedTouches[0].pageX;
				}
				if(dragging == true && Math.abs(e.pageX - lastX) > 5){
					dragging2 = true;
				}
				if(dragging2 == true){
					var thisposX = e.pageX - posX;
					if(thisposX < 0){
						thisposX = 0;
					}
					else if(thisposX > _switcher.innerWidth() - _switcher.innerHeight()){
						thisposX = _switcher.innerWidth() - _switcher.innerHeight();
					}
					_switcher.find(".ios6switch_draglayer").css({
						"left": thisposX + "px"
					});
					setThumbColor(_switcher);
				}
				dragging && (e.preventDefault ? e.preventDefault() : window.event.returnValue = false);
			}
			_switcher.find(".ios6switch_thumb").mousedown(startdrag);
			$(document).mouseup(stopdrag);
			$(document).mousemove(move);
			_switcher.find(".ios6switch_thumb")[0].addEventListener && _switcher.find(".ios6switch_thumb")[0].addEventListener("touchstart", startdrag, false);
			document.addEventListener && document.addEventListener('touchend', stopdrag, false);
			document.addEventListener && document.addEventListener('touchmove', function(e){
				if(e.targetTouches.length <= 1 && dragging == true) move(e);
			}, false);
		}
		var disable = function(){
			disabled = true;
			_switcher.css("opacity", _opt.disabledOpacity);
			_switcher.attr("data-disabled", "true");
			_elem[0].disabled = true;
		}
		var enable = function(){
			disabled = false;
			_switcher.css("opacity", "");
			_switcher.attr("data-disabled", "false");
			_elem[0].disabled = false;
		}
		_elem[0].disabled == true && disable();
		var checkstatus = _elem[0].checked;
		_elem[0].checked == true && _switcher.find(".ios6switch_draglayer").css("left", ((_switcher.innerHeight() * 79 / 27) - _switcher.innerHeight()) + "px");
		FastClick.attach(_switcher[0]);
		_elem.click(function(){
			checkstatus = !_elem[0].checked;
			toggleswitch();
		});
		var soundElement;
		if(_opt.sound != ""){
			soundElement = "<audio id=\"switchsound" + thisid + "\" preload=\"auto\" src=\"" + _opt.sound + "\"></audio>";
			$("body").append(soundElement);
		};
		var toggleswitch = function(){
			if(disabled == true) return;
			var thumbx = _switcher.find(".ios6switch_draglayer").position().left;
			var switchw = _switcher.innerWidth() - _switcher.innerHeight();
			var evt = document.createEvent ? document.createEvent("HTMLEvents") : false;
			evt && evt.initEvent("change", true, true);
			_switcher.attr("hasAnimate", "true");
			if(dragged == true && thumbx <= switchw / 2 || dragged != true && checkstatus == true){
				_switcher.find(".ios6switch_draglayer").stop(true).animate({
					left: "0px"
				}, (thumbx / switchw) * _opt.animateSpeed, _opt.animateEasing, function(){
					_switcher.removeAttr("hasAnimate");
					_elem[0].checked = false;
					evt ? _elem[0].dispatchEvent(evt) : _elem[0].fireEvent("onchange");
				});
				checkstatus = false;
			}
			else{
				_switcher.find(".ios6switch_draglayer").stop(true).animate({
					left: (_switcher.innerWidth() - _switcher.innerHeight()) + "px"
				}, ((switchw - thumbx) / switchw) * _opt.animateSpeed, _opt.animateEasing, function(){
					_switcher.removeAttr("hasAnimate");
					_elem[0].checked = true;
					evt ? _elem[0].dispatchEvent(evt) : _elem[0].fireEvent("onchange");
				});
				checkstatus = true;
			}
			setTimeout(function(){
				dragged = false;
			}, 10);
			if(_opt.sound != "" && typeof $("#switchsound" + thisid)[0].play != "undefined"){
				$("#switchsound" + thisid)[0].currentTime = 0;
				$("#switchsound" + thisid)[0].play();
			}
		}
		_switcher[0].addEventListener ? _switcher[0].addEventListener("click", toggleswitch) : _switcher[0].attachEvent("onclick", toggleswitch);
		var switchon = function(){
			checkstatus = false;
			toggleswitch();
		}
		var switchoff = function(){
			checkstatus = true;
			toggleswitch();
		}
		var destroy = function(){
			sList.remove(thisid);
			hList.remove(thisid);
			id --;
			_elem.unbind("disable");
			_elem.unbind("enable");
			_elem.unbind("switchon");
			_elem.unbind("switchoff");
			_elem.unbind("toggleswitch");
			_elem.unbind("destroy");
			_switcher.remove();
			$("#switchsound" + thisid).remove();
			_elem.removeAttr("ios6switch");
			_elem.css("display", "");
		}
		id ++;
		_elem.bind("disable", disable);
		_elem.bind("enable", enable);
		_elem.bind("switchon", switchon);
		_elem.bind("switchoff", switchoff);
		_elem.bind("toggleswitch", toggleswitch);
		_elem.bind("destroy", destroy);
	}
	var startinterval = false;
	var interval = function(){
		if(startinterval != true) return;
		for(var i = 0; i < id; i ++){
			var te = $("." + sList[i]);
			if(hList[i] != te.innerHeight()){
				setSize(te);
				hList[i] = te.innerHeight();
			}
			te.attr("hasAnimate") == "true" && setThumbColor(te);
		}
	}
	setInterval(interval, 25);
	var setThumbColor = function(te){
		var switchh = te.innerHeight();
		var switchw = te.innerWidth();
		var layerx = te.find(".ios6switch_draglayer").position().left;
		if(te.find(".ios6switch_thumb").attr("data-pressed") == "true"){
			(layerx <= (switchw - switchh) / 2) ? te.find(".ios6switch_thumb").css("background-color", te.find(".ios6switch_thumb").attr("data-thumb-pressed-color")) : te.find(".ios6switch_thumb").css("background-color", te.find(".ios6switch_thumb").attr("data-thumb-on-pressed-color"));
		}
		else{
			(layerx <= (switchw - switchh) / 2) ? te.find(".ios6switch_thumb").css("background-color", te.find(".ios6switch_thumb").attr("data-thumb-color")) : te.find(".ios6switch_thumb").css("background-color", te.find(".ios6switch_thumb").attr("data-thumb-on-color"));
		}
	}
	var setSize = function(te){
		var layerat = te.find(".ios6switch_draglayer").position().left / te.innerWidth();
		te.css("width", (te.innerHeight() * 79 / 27) + "px");
		var thumbx = te.find(".ios6switch_thumb").position().left;
		var thumbb1 = getBorderWidth(te.find(".ios6switch_thumb"), "top");
		var thumbb2 = getBorderWidth(te.find(".ios6switch_thumb"), "bottom");
		var layerx = te.find(".ios6switch_draglayer").position().left;
		var switchh = te.innerHeight();
		var switchw = te.innerWidth();
		te.find(".ios6switch_thumb").css({
			width: (switchh - thumbb1 - thumbb2) + "px",
			height: (switchh - thumbb1 - thumbb2) + "px"
		});
		te.css("border-radius", (te.outerHeight() / 2) + "px");
		te.find(".ios6switch_switch_on").css("left", (thumbx + (switchh / 2) - switchw) + "px");
		te.find(".ios6switch_switch_off").css("left", (thumbx + (switchh / 2)) + "px");
		te.find(".ios6switch_draglayer").css("left", (te.innerWidth() * layerat) + "px");
		te.find(".ios6switch_switch_on, .ios6switch_switch_off").css({
			"line-height" : switchh + "px",
			"font-size" : (switchh * .7) + "px"
		});
	}
	$.fn.extend({
		ios6switch: function(opt){
			var _def = {
				draggable: true,
				size: 27,
				showOriginalCheckbox: false,
				disabledOpacity: 0.5,
				animateSpeed: 400,
				animateEasing: "swing",
				addClass: "",
				sound: "",
				thumbColor: "#FFFFFF",
				thumbPressedColor: "#CCCCCC",
				thumbSwitchonColor: "",
				thumbSwitchonPressedColor: "",
				switchoffColor: "#FFFFFF",
				switchonColor: "#008AF2",
				switchoffTextColor: "#555555",
				switchonTextColor: "#FFFFFF",
				switchoffText: "0",
				switchonText: "1"
			}
			var opt = opt || {};
			var _opt = $.extend(_def, opt);
			for(i in $(this)){
				var te = $(this).eq(i);
				startinterval = true;
				te.is("input[type=checkbox]") && init($(this).eq(i), _opt);
			}
		}
	})
})(jQuery);