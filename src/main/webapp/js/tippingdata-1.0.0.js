(function(root, factory){
	if(typeof define === 'function' && (define.amd || define.cmd)){
		define('tippingData', function(exports){
			return factory(root, exports);
		});	
	}else{
		root.tippingData = factory(root, {});
	}
})(this, function(root, tippingData){
	
	var Http = function(){
		function request(method, url, data, success, error){
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
			
			var xhr = null;
			
			if(window.ActiveXObject){//IE
		        try {
		            //IE6以及以后版本中可以使用
		        	xhr = new ActiveXObject("Msxml2.XMLHTTP");
		        }catch (e) {
		            //IE5.5以及以后版本可以使用
		        	xhr = new ActiveXObject("Microsoft.XMLHTTP");
		        }
		    }else if(window.XMLHttpRequest){//Firefox，Opera 8.0+，Safari，Chrome
		    	xhr = new XMLHttpRequest();
		    }
			
			var xhr = new XMLHttpRequest();
			xhr.onreadystatechange = function(){
				if(xhr.readyState == 4 && xhr.status == 200){
					eval(xhr.responseText);
					createCallback;
				}else{
					if(typeof error === 'function'){
						error(xhr.status);
					}
				}
			}
			if(method === 'GET'){
				url += '?' + data;
				data = null;
			}
			xhr.open(method, url);
			
			if(method === 'POST'){
				xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
			}
			xhr.send(data);
		}
		
		function get(url, data, success, error){
			request('GET', url, data, success, error);
		}
		
		function post(url, data, success, error){
			request('POST', url, data, success, error);
		}
		
		return {
			get: get,
			post: post
		}
	}();
	
	// 统计接口地址
	var api = 'http://data.mocentre.com/api/act/';
	//var api = 'http://yun.mecentre.com/api/act/';
	
	var prefix = 'act_';

	var id;
	
	var level = _param(prefix+ 'level') || 1;
	
	// 配置选项
	var config;
	
	// 任务配置
	tippingData.config = function(userinfo, options){
		options.debug = options.debug || false;
		options.tid = options.tid || 0;
		options.token = options.token || '';
		
		config = options;
		//localStorage.getItem('tippingdata-task-' + config.tid)
		id = 0;
		
		_init(userinfo);
	}
	
	// 分享到微信朋友圈
	tippingData.shareTimeline = function(options){
		return _shareHandler(1, options);
	}
	
	// 分享到微信朋友&微信群
	tippingData.shareAppMessage = function(options){
		return _shareHandler(2, options);
	}
	
	// 分享到QQ空间
	tippingData.shareQZone = function(){
		return _shareHandler(3, options);
	}
	
	// 分享到QQ
	tippingData.shareQQ = function(){
		return _shareHandler(4, options);
	}
	
	// 分享到腾讯微博
	tippingData.shareWeibo = function(){
		return _shareHandler(5, options);
	}
	
	// 初始化
	function _init(userinfo){
		// 任务信息
		var data = {};
		data.token = config.token;
		// 用户ID
		//data.id = id;
		
		// 参数信息
		data.pid = _param(prefix + 'pid') || '0';
		data.from = _param(prefix+ 'from') || '0';
		data.level = level;
		
		// 浏览器信息
		data.agent = navigator.userAgent;
		data.width = window.screen.width;
		
		// 用户信息
		if(userinfo.indexOf('openid') > 0){
			try {
				userinfo = JSON.parse(userinfo);
				data.headimgurl = userinfo.headimgurl;
				data.nickname = userinfo.nickname;
				data.sex = userinfo.sex;
				data.country = userinfo.country;
				data.province = userinfo.province;
				data.city = userinfo.city;
				data.openid = userinfo.openid;
				data.unionid = userinfo.unionid;
			}catch(e){
				if(config.debug){
					alert(e);
				}
			}
		}
		
		Http.post(api + 'init', data, function(response){
			if(response.success){
				//localStorage['tippingdata-task-' + config.tid] = response.data
				id = response.data;
			}else{
				if(config.debug){
					alert(response.msg);
				}
			}
		});
		
	}
	
	// 分享处理
	function _shareHandler(type, options){
		var _options = function(){
			var obj = {};
			for(key in options){
				obj[key] = options[key];
			}
			return obj;
		}();
		
		var _params = function(){
			var obj = {
				from: type,
				pid: id,
				level: level+1
			};
			
			var arr = [];
			for(var key in obj){
				arr.push(prefix + key + '=' + obj[key]);
			}
			return arr.join('&');
		}();
		
		if(_options.link.indexOf('?') === -1){
			_options.link += '?' + _params;
		}else{
			_options.link += '&' + _params;
		}
		
		_options.success = function(){
			Http.post(api + 'share', {
				id: id,
				token: config.token,
				share: type
			}, function(response){
				if(typeof options.success == 'function'){
					options.success();
				}
			});
		}
		
		return _options;
	}
	
	function _param(name){
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");  
		var r = window.location.search.substr(1).match(reg);  
		if (r != null) return unescape(r[2]);  
		return null;  
	}
	
	return tippingData;
});
