(function(root, factory){
	if(typeof define === 'function' && (define.amd || define.cmd)){
		define('pullData', function(exports){
			return factory(root, exports);
		});	
	}else{
		root.pullData = factory(root, {});
	}
})(this, function(root, pullData){
	
	var Http = function(){
		function request(url, data, success, error){
			data = (function(){
				var params = [];
				params.push("callback" + '=' + 'createCallback');
				for(var key in data){
					params.push(key + '=' + data[key]);
				}
				return params.join('&');
			})();
			
			function createCallback(result){
				if(typeof success === 'function'){
					success(result);
					//success(JSON.parse(xhr.responseText));
				}
			}
			
			var xmlHttp = null;
			
			if(window.ActiveXObject){//IE
		        try {
		            //IE6以及以后版本中可以使用
		            xmlHttp = new ActiveXObject("Msxml2.XMLHTTP");
		        }catch (e) {
		            //IE5.5以及以后版本可以使用
		            xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
		        }
		    }else if(window.XMLHttpRequest){//Firefox，Opera 8.0+，Safari，Chrome
		    	xmlHttp = new XMLHttpRequest();
		    }
			
			xmlHttp.onreadystatechange = function(){
				if(xmlHttp.readyState == 4 && xmlHttp.status == 200){
					eval(xmlHttp.responseText);
					createCallback;
				}else{
					if(typeof error === 'function'){
						error(xmlHttp.status);
					}
				}
			}
			url += '?' + data;
			xmlHttp.open('GET', url, true);
			xmlHttp.send();
		}
		
		function get(url, data, success, error){
			request(url, data, success, error);
		}
		
		return {
			get: get
		}
	}();
	
	var api = 'http://demo-yun.mocentre.com/ad/common/';

	var config;
	
	pullData.config = function(options){
		options.debug = options.debug || false;
		options.token = options.token || '';
		
		config = options;
		
		//_init();
		_locaInit();
	}
	
	//获取经纬度
	function _locaInit(){
		if (navigator.geolocation) {
			navigator.geolocation.getCurrentPosition(locaSuccess,locaError,{
				// 指示浏览器获取高精度的位置，默认为false
		        enableHighAccuracy: true,
		        // 指定获取地理位置的超时时间，默认不限时，单位为毫秒
		        timeout: 5000,
		        // 最长有效期，在重复获取地理位置时，此参数指定多久再次获取位置。
		        maximumAge: 3000
			});
		}else{
			_init();
		}
	};
	
	function locaSuccess(position){
		_init(position);
	}
	
	function locaError(error){
		_init();
		switch(error.code) {
	        case error.TIMEOUT:
	            //alert("A timeout occured! Please try again!");
	            break;
	        case error.POSITION_UNAVAILABLE:
	        	//alert('We can\'t detect your location. Sorry!');
	            break;
	        case error.PERMISSION_DENIED:
	        	//alert('Please allow geolocation access for this to work.');
	            break;
	        case error.UNKNOWN_ERROR:
	        	//alert('An unknown error occured!');
	            break;
	    }
	}
	
	// 初始化
	function _init(position){
		// 任务信息
		var data = {};
		data.tid = config.tid;	
		data.token = config.token;
		// 浏览器信息
		data.agent = navigator.userAgent;
		data.width = window.screen.width;
		//经纬度
		if(position){
			var coords = position.coords;
			data.longitude = coords.longitude;
			data.latitude = coords.latitude;
		}
		
		Http.get(api + 'pull', data, function(response){
			if(response.success){
				var data = response.data;
				if(data!=null){
					var adIframe = document.createElement('iframe');
					adIframe.setAttribute("src", data.script);
					adIframe.setAttribute("style",data.style);
					document.body.appendChild(adIframe);
					var time = data.showTime;
					if(time&&time>0){
						window.setTimeout(function(){
							removeElement(adIframe);
						},time*1000); 
					}
				}
			}else{
				if(config.debug){
					alert(response.message);
				}
			}
		});
		
	}
	
	function _param(name){
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");  
		var r = window.location.search.substr(1).match(reg);  
		if (r != null) return unescape(r[2]);  
		return null;  
	}
	
	function removeElement(_element){
        var _parentElement = _element.parentNode;
        if(_parentElement){
               _parentElement.removeChild(_element);
        }
	}
	
	function insertAfter(newElement, targetElement){
	    var parent = targetElement.parentNode;
	    if (parent.lastChild == targetElement) {
	        parent.appendChild(newElement);
	    }
	    else {
	        parent.insertBefore(newElement, targetElement.nextSibling);
	    }
	}
	
	return pullData;
});

pullData.config({debug: false,token: mc_token});