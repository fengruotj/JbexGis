var url = "http://192.168.56.1:8080/test/servlet/";
var SvrCfg = {
	url : window.location.host.split(":")[0],
	port : "6163",
	mapName : "Points",
	Resolution : 19561.469089868671875,
	mapBound : new OpenLayers.Bounds(9436815.94810172, 2056147.05687751,
			14444552.0351081, 6010111.43504396)
};
var map;
var publicInfoLayer, jbexInfoLayer, dynamicInfoLayer; // 公众，结伴，动态
var selectControlPoint;

var public_obj, jbex_obj, dynamic_obj; // 通过ajax从服务器传来的json对象object

//index.html的初始化函数	
function init() {

	var slideNav = new OpenLayers.Control.TouchNavigation({
		dragPanOptions : { //惯性滑动,
			enableKinetic : { //enableKinetic,可以设为bool，也可设为object,设为object时,object会考到{<OpenLayers.Kinetic> 的构造函数中
				deceleration : 0.0055
			//地图滑动的速率
			}
		}
	});

	map = new OpenLayers.Map("map", {
		maxExtent : SvrCfg.mapBound,
		maxResolution : SvrCfg.Resolution,
		controls : [ new OpenLayers.Control.Navigation(),
				new OpenLayers.Control.LayerSwitcher(),
				new OpenLayers.Control.MousePosition(), slideNav ],
		theme : null,
	});

	var baseLayer = new OpenLayers.Layer.OSM(
			'OpenStreetMap',
			null,
			{
				transitionEffect : "resize",
				attribution : "&copy; <a href='http://www.openstreetmap.org/copyright'>OpenStreetMap</a> contributors"
			});

	map.addLayer(baseLayer);

	map.setCenter(new OpenLayers.LonLat(110.397128, 39.916527).transform(
			new OpenLayers.Projection("EPSG:4326"), map.getProjectionObject()),
			3); //添加平移缩放工具条 
	map.addControl(new OpenLayers.Control.OverviewMap()); //添加鹰眼图 
	map.addControl(new OpenLayers.Control.PanZoomBar({
		position : new OpenLayers.Pixel(4, 10)
	})); //添加平移缩放工具条
	//map.addControl(new OpenLayers.Control.Scale($('scale')));  //获取地图比例尺 

	getPublicJsonObj("WebGetPublicInfo");
	getJbexJsonObj("WebGetJbexInfo");
	getDynamicJsonObj("WebGetDynamicInfo");

	if (!map.getCenter()) {
		map.zoomToMaxExtent();
	}
}

//user_manager.html的初始化函数
function user_init() {
	getUserJsonObj();
}
//得到所有publicInfo的坐标
function getpublicInfoFeatures() {

	//渲染器
	var renderer = OpenLayers.Util.getParameters(window.location.href).renderer;
	renderer = (renderer) ? [ renderer ]
			: OpenLayers.Layer.Vector.prototype.renderers;

	window.publicInfoLayer = new OpenLayers.Layer.Vector("PublicInfoLayer", {
		renderers : renderer,
		styleMap : new OpenLayers.StyleMap({
			"default" : new OpenLayers.Style(OpenLayers.Util.applyDefaults({
				externalGraphic : "img/marker-blue.png",
				graphicOpacity : 1,
				pointRadius : 10,
				graphicWidth : 30,
				graphicHeight : 30,
				graphicYOffset : -28
			}, OpenLayers.Feature.Vector.style["default"])),
			"select" : new OpenLayers.Style({
				externalGraphic : "img/bluemarker.jpg"
			})
		})
	});
	var publicinfo = public_obj.publicinfo;
	//console.log(publicinfo);
	var features = new Array();
	for (var i = 0; i < publicinfo.length; i++) {
		var myLocation;
		var cur_publicinfo = publicinfo[i];
		var dot_x = cur_publicinfo.DotX;
		var dot_y = cur_publicinfo.DotY;
		//console.log(dot_x);
		//console.log(dot_y);
		myLocation = new OpenLayers.Geometry.Point(dot_x, dot_y).transform(
				new OpenLayers.Projection("EPSG:4326"), map
						.getProjectionObject());
		features[i] = new OpenLayers.Feature.Vector(myLocation, {
			id : cur_publicinfo.id,
			dot_x : cur_publicinfo.DotX,
			dot_y : cur_publicinfo.DotY,
			user_id : cur_publicinfo.user_id,
			user_name : cur_publicinfo.user_name,
			title : cur_publicinfo.title,
			detail : cur_publicinfo.detail,
			time : cur_publicinfo.time,
			label : cur_publicinfo.label
		});
	}
	window.publicInfoLayer.addFeatures(features);
}

//得到所有jbexInfoLayer的坐标
function getjbexInfoFeatures() {

	//渲染器
	var renderer = OpenLayers.Util.getParameters(window.location.href).renderer;
	renderer = (renderer) ? [ renderer ]
			: OpenLayers.Layer.Vector.prototype.renderers;

	window.jbexInfoLayer = new OpenLayers.Layer.Vector("JbexInfoLayer", {
		renderers : renderer,
		styleMap : new OpenLayers.StyleMap({
			"default" : new OpenLayers.Style(OpenLayers.Util.applyDefaults({
				externalGraphic : "img/marker-gold.png",
				graphicOpacity : 1,
				pointRadius : 10,
				graphicWidth : 30,
				graphicHeight : 30,
				graphicYOffset : -28
			}, OpenLayers.Feature.Vector.style["default"])),
			"select" : new OpenLayers.Style({
				externalGraphic : "img/bluemarker.jpg"
			})
		})
	});

	var jbexinfo = jbex_obj.jbexinfo;
	//console.log(jbexinfo);
	var features = new Array();
	for (var i = 0; i < jbexinfo.length; i++) {
		var myLocation;
		var cur_jbexinfo = jbexinfo[i];
		var dot_x = cur_jbexinfo.DotX;
		var dot_y = cur_jbexinfo.DotY;
		//console.log(dot_x);
		//console.log(dot_y);
		myLocation = new OpenLayers.Geometry.Point(dot_x, dot_y).transform(
				new OpenLayers.Projection("EPSG:4326"), map
						.getProjectionObject());
		features[i] = new OpenLayers.Feature.Vector(myLocation, {
			id : cur_jbexinfo.id,
			dot_x : cur_jbexinfo.DotX,
			dot_y : cur_jbexinfo.DotY,
			user_id : cur_jbexinfo.user_id,
			user_name : cur_jbexinfo.user_name,
			title : cur_jbexinfo.title,
			detail : cur_jbexinfo.detail,
			time : cur_jbexinfo.time,
			label : cur_jbexinfo.label
		});
	}
	window.jbexInfoLayer.addFeatures(features);
}

//得到所有dynamicInfoLayer的坐标
function getdynamicInfoFeatures() {

	//渲染器
	var renderer = OpenLayers.Util.getParameters(window.location.href).renderer;
	renderer = (renderer) ? [ renderer ]
			: OpenLayers.Layer.Vector.prototype.renderers;

	window.dynamicInfoLayer = new OpenLayers.Layer.Vector("DynamicInfoLayer", {
		renderers : renderer,
		styleMap : new OpenLayers.StyleMap({
			"default" : new OpenLayers.Style(OpenLayers.Util.applyDefaults({
				externalGraphic : "img/marker-green.png",
				graphicOpacity : 1,
				pointRadius : 10,
				graphicWidth : 30,
				graphicHeight : 30,
				graphicYOffset : -28
			}, OpenLayers.Feature.Vector.style["default"])),
			"select" : new OpenLayers.Style({
				externalGraphic : "img/bluemarker.jpg"
			})
		})
	});

	var dynamic = dynamic_obj.dynamic;
	//console.log(dynamic);
	var features = new Array();
	for (var i = 0; i < dynamic.length; i++) {
		var myLocation;
		var cur_dynamic = dynamic[i];
		var dot_x = cur_dynamic.DotX;
		var dot_y = cur_dynamic.DotY;
		//console.log(dot_x);
		//console.log(dot_y);
		myLocation = new OpenLayers.Geometry.Point(dot_x, dot_y).transform(
				new OpenLayers.Projection("EPSG:4326"), map
						.getProjectionObject());
		features[i] = new OpenLayers.Feature.Vector(myLocation, {
			id : cur_dynamic.id,
			dot_x : cur_dynamic.DotX,
			dot_y : cur_dynamic.DotY,
			user_id : cur_dynamic.user_id,
			user_name : cur_dynamic.user_name,
			title : cur_dynamic.title,
			detail : cur_dynamic.detail,
			time : cur_dynamic.time,
			label : cur_dynamic.label
		});
	}
	window.dynamicInfoLayer.addFeatures(features);
}

//===========PublicInfoLayer被点击的响应=============//
function clickPublicInfoLayer(e) {
	var feature = e.feature;
	if (feature.popup == null) {
		var popup = new OpenLayers.Popup.FramedCloud("popup",
		//new OpenLayers.LonLat(feature.attributes.dot_x, feature.attributes.dot_y), // 这个传入的参数是地图坐标
		OpenLayers.LonLat.fromString(feature.geometry.toShortString()), // 这个传入的参数是经纬度
		null, "id:" + feature.attributes.id + "<br>user_id:"
				+ feature.attributes.user_id + "<br>user_name:"
				+ feature.attributes.user_name + "<br>title:"
				+ feature.attributes.title + "<br>detail:"
				+ feature.attributes.detail + "<br>time:"
				+ feature.attributes.time + "<br>label:"
				+ feature.attributes.label
				+ '<br><button style="margin:auto;" ' + 'onclick="deleteData'
				+ "('DeletepublicinfoServlet', " + feature.attributes.id
				+ ')">删除</button>', null, false);
		popup.autoSize = true;
		feature.popup = popup;
		map.addPopup(popup);
		console.log(feature.attributes.dot_x);
		console.log(feature.attributes.dot_y);
	} else {
		map.removePopup(feature.popup);
		feature.popup.destroy();
		feature.popup = null;
	}
}

//===========JbexInfoLayer被点击的响应=============//
function clickJbexInfoLayer(e) {
	var feature = e.feature;
	if (feature.popup == null) {
		var popup = new OpenLayers.Popup.FramedCloud("popup",
		//new OpenLayers.LonLat(feature.attributes.dot_x, feature.attributes.dot_y), // 这个传入的参数是地图坐标
		OpenLayers.LonLat.fromString(feature.geometry.toShortString()), // 这个传入的参数是经纬度
		null, "id:" + feature.attributes.id + "<br>user_id:"
				+ feature.attributes.user_id + "<br>user_name:"
				+ feature.attributes.user_name + "<br>title:"
				+ feature.attributes.title + "<br>detail:"
				+ feature.attributes.detail + "<br>time:"
				+ feature.attributes.time + "<br>label:"
				+ feature.attributes.label
				+ '<br><button style="margin:auto;" ' + 'onclick="deleteData'
				+ "('DeleteJbexinfoServlet', " + feature.attributes.id
				+ ')">删除</button>', null, false);
		popup.autoSize = true;
		feature.popup = popup;
		map.addPopup(popup);
		console.log(feature.attributes.dot_x);
		console.log(feature.attributes.dot_y);
	} else {
		map.removePopup(feature.popup);
		feature.popup.destroy();
		feature.popup = null;
	}
}

//===========DynamicInfoLayer被点击的响应=============//
function clickDynamicInfoLayer(e) {
	var feature = e.feature;
	if (feature.popup == null) {
		var popup = new OpenLayers.Popup.FramedCloud("popup",
		//new OpenLayers.LonLat(feature.attributes.dot_x, feature.attributes.dot_y), // 这个传入的参数是地图坐标
		OpenLayers.LonLat.fromString(feature.geometry.toShortString()), // 这个传入的参数是经纬度
		null, "id:" + feature.attributes.id + "<br>user_id:"
				+ feature.attributes.user_id + "<br>user_name:"
				+ feature.attributes.user_name + "<br>title:"
				+ feature.attributes.title + "<br>detail:"
				+ feature.attributes.detail + "<br>time:"
				+ feature.attributes.time + "<br>label:"
				+ feature.attributes.label
				+ '<br><button style="margin:auto;" ' + 'onclick="deleteData'
				+ "('DeletedynamicinfoServlet', " + feature.attributes.id
				+ ')">删除</button>', null, false);
		popup.autoSize = true;
		feature.popup = popup;
		map.addPopup(popup);
		console.log(feature.attributes.dot_x);
		console.log(feature.attributes.dot_y);
	} else {
		map.removePopup(feature.popup);
		feature.popup.destroy();
		feature.popup = null;
	}
}

//===========Layer不被点击的响应=============//
function unclickLayer(e) {
	var feature = e.feature;
	if (feature.popup != null) {
		map.removePopup(feature.popup);
		feature.popup.destroy();
		feature.popup = null;
	}
}

//=============selectControlPoint============//
function setSelectControlPoint(layer) {
	selectControlPoint = new OpenLayers.Control.SelectFeature([ layer ], {
		clickout : true, //{Boolean}是否在地物之外点击时，取消选择地物
		toggle : false, //{Boolean} 单击当前选中的要素时，是否取消其选中状态
		multiple : false, //{Boolean} 允许选择多个几何要素，默认为false。
		hover : false, //{Boolean} 在鼠标悬浮在地物上时，选中地物；移出地物时，取消选中。                                                    
		toggleKey : "ctrlKey", // ctrl key removes from selection
		multipleKey : "shiftKey" // shift key adds to selection	
	});
	map.addControl(selectControlPoint);
	selectControlPoint.activate();
}

//Ajax从服务器拿数据
function getPublicJsonObj(url_servlet) {
	var urlStr = url + url_servlet + "?callback=?";
	// 调用jQuery提供的Ajax方法
	$.ajax({
		type : "POST",
		async : false, //(默认: true) 默认设置下，所有请求均为异步请求。如果需要发送同步请求，请将此选项设置为 false。注意，同步请求将锁住浏览器，用户其它操作必须等待请求完成才可以执行。
		url : urlStr,
		data : "{}",
		dataType : "jsonp",
		success : function(msg) {
			public_obj = eval(msg); //调用Json2.js中提供的JSON解析器来解析成JSONObject
			getpublicInfoFeatures();
			map.addLayer(publicInfoLayer);
			setSelectControlPoint(publicInfoLayer);
			//publicInfoLayer点击的响应事件
			publicInfoLayer.events.on({
				"featureselected" : clickPublicInfoLayer,
				"featureunselected" : unclickLayer,
			});
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert(errorThrown);
			alert(XMLHttpRequest.status);//200
			alert(XMLHttpRequest.readyState);//4
			alert(textStatus);//pasererror
		}
	});
}

function getJbexJsonObj(url_servlet) {
	var urlStr = url + url_servlet + "?callback=?";
	// 调用jQuery提供的Ajax方法
	$.ajax({
		type : "POST",
		async : false, //(默认: true) 默认设置下，所有请求均为异步请求。如果需要发送同步请求，请将此选项设置为 false。注意，同步请求将锁住浏览器，用户其它操作必须等待请求完成才可以执行。
		url : urlStr,
		data : "{}",
		dataType : "jsonp",
		success : function(msg) {
			jbex_obj = eval(msg); //调用Json2.js中提供的JSON解析器来解析成JSONObject
			getjbexInfoFeatures();
			map.addLayer(jbexInfoLayer);
			setSelectControlPoint(jbexInfoLayer);
			//jbexInfoLayer点击的响应事件
			jbexInfoLayer.events.on({
				"featureselected" : clickJbexInfoLayer,
				"featureunselected" : unclickLayer,
			});
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert(errorThrown);
			alert(XMLHttpRequest.status);//200
			alert(XMLHttpRequest.readyState);//4
			alert(textStatus);//pasererror
		}
	});
}

function getDynamicJsonObj(url_servlet) {
	var urlStr = url + url_servlet + "?callback=?";
	// 调用jQuery提供的Ajax方法
	$.ajax({
		type : "POST",
		async : false, //(默认: true) 默认设置下，所有请求均为异步请求。如果需要发送同步请求，请将此选项设置为 false。注意，同步请求将锁住浏览器，用户其它操作必须等待请求完成才可以执行。
		url : urlStr,
		data : "{}",
		dataType : "jsonp",
		success : function(msg) {
			dynamic_obj = eval(msg); //调用Json2.js中提供的JSON解析器来解析成JSONObject
			getdynamicInfoFeatures();
			map.addLayer(dynamicInfoLayer);
			setSelectControlPoint(dynamicInfoLayer);
			//jbexInfoLayer点击的响应事件
			dynamicInfoLayer.events.on({
				"featureselected" : clickDynamicInfoLayer,
				"featureunselected" : unclickLayer,
			});
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert(errorThrown);
			alert(XMLHttpRequest.status);//200
			alert(XMLHttpRequest.readyState);//4
			alert(textStatus);//pasererror
		}
	});
}

function getUserJsonObj() {
	var urlStr = url + "WebGetUserInfo?callback=?";
	// 调用jQuery提供的Ajax方法
	$.ajax({
		type : "POST",
		async : false, //(默认: true) 默认设置下，所有请求均为异步请求。如果需要发送同步请求，请将此选项设置为 false。注意，同步请求将锁住浏览器，用户其它操作必须等待请求完成才可以执行。
		url : urlStr,
		data : "{}",
		dataType : "jsonp",
		success : function(msg) {
			user_obj = eval(msg); //调用Json2.js中提供的JSON解析器来解析成JSONObject
			userinfo = user_obj.user;
			for (var i = 0; i < userinfo.length; i++) {
				$("#t" + i).remove();
				var row;
				if (i % 2 == 0) {
					row = $("<tr id=t" + i + " class='success'></tr>");
				} else {
					row = $("<tr id=t" + i + "></tr>");
				}
				row.append($("<td>" + userinfo[i].user_id + "</td><td>"
						+ userinfo[i].user_name + "</td><td>"
						+ userinfo[i].password + "</td><td>"
						+ userinfo[i].user_nickname + "</td><td>"
						+ userinfo[i].person_signature + "</td><td>"
						+ userinfo[i].sex + "</td><td>" + userinfo[i].school
						+ "</td><td>" + userinfo[i].academy + "</td><td>"
						+ userinfo[i].state + "</td><td>"
						+ userinfo[i].birthday + "</td><td>"
						+ userinfo[i].telephone + "</td><td>"
						+ userinfo[i].picture + "</td><td>"
						+ userinfo[i].SecurityControl + "</td>"));
				$("#tbody_").append(row);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert(errorThrown);
			alert(XMLHttpRequest.status);//200
			alert(XMLHttpRequest.readyState);//4
			alert(textStatus);//pasererror
		}
	});
}

//delete数据
function deleteData(url_servlet, id) {
	alert("确认删除该条记录？");
	var urlStr;
	if (url_servlet == "DeletepublicinfoServlet") {
		urlStr = url + url_servlet + "?publicinfoid=" + id;
	} else if (url_servlet == "DeleteJbexinfoServlet") {
		urlStr = url + url_servlet + "?jbexinfoid=" + id;
	} else {
		urlStr = url + url_servlet + "?dynamicinfoid=" + id;
	}
	$.ajax({
		type : "GET",
		async : false, //(默认: true) 默认设置下，所有请求均为异步请求。如果需要发送同步请求，请将此选项设置为 false。注意，同步请求将锁住浏览器，用户其它操作必须等待请求完成才可以执行。
		url : urlStr,
		data : "{}",
		dataType : "text",
		success : function(msg) {
			if (msg) {
				alert("delete success!");
			} else {
				alert("delete failed!");
			}
			window.location.reload(true);
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("访问servlet失败！");
			alert(errorThrown);
			alert(XMLHttpRequest.status);//200
			alert(XMLHttpRequest.readyState);//4
			alert(textStatus);//pasererror
		}
	});
}

//query数据
function queryData() {
	var kind = document.getElementById("layerkind").value;
	var id = document.getElementById("layerid").value;
	switch (kind) {
	case "public":
		$.ajax({
			type : "POST",
			async : false, //(默认: true) 默认设置下，所有请求均为异步请求。如果需要发送同步请求，请将此选项设置为 false。注意，同步请求将锁住浏览器，用户其它操作必须等待请求完成才可以执行。
			url : url + "WebGetPublicInfo?callback=?",
			data : "{}",
			dataType : "jsonp",
			success : function(msg) {
				var public_obj_ = eval(msg); //调用Json2.js中提供的JSON解析器来解析成JSONObject
				var publicinfo = public_obj_.publicinfo;
				createTable(publicinfo, id);
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				alert(errorThrown);
				alert(XMLHttpRequest.status);//200
				alert(XMLHttpRequest.readyState);//4
				alert(textStatus);//pasererror
			}
		});
		break;
	case "jbex":
		$.ajax({
			type : "POST",
			async : false, //(默认: true) 默认设置下，所有请求均为异步请求。如果需要发送同步请求，请将此选项设置为 false。注意，同步请求将锁住浏览器，用户其它操作必须等待请求完成才可以执行。
			url : url + "WebGetJbexInfo?callback=?",
			data : "{}",
			dataType : "jsonp",
			success : function(msg) {
				var jbex_obj_ = eval(msg); //调用Json2.js中提供的JSON解析器来解析成JSONObject
				var jbexinfo = jbex_obj_.jbexinfo;
				createTable(jbexinfo, id);
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				alert(errorThrown);
				alert(XMLHttpRequest.status);//200
				alert(XMLHttpRequest.readyState);//4
				alert(textStatus);//pasererror
			}
		});
		break;
	case "dynamic":
		$.ajax({
			type : "POST",
			async : false, //(默认: true) 默认设置下，所有请求均为异步请求。如果需要发送同步请求，请将此选项设置为 false。注意，同步请求将锁住浏览器，用户其它操作必须等待请求完成才可以执行。
			url : url + "WebGetDynamicInfo?callback=?",
			data : "{}",
			dataType : "jsonp",
			success : function(msg) {
				var dynamic_obj_ = eval(msg); //调用Json2.js中提供的JSON解析器来解析成JSONObject
				var dynamicinfo = dynamic_obj_.dynamic;
				createTable(dynamicinfo, id);
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				alert(errorThrown);
				alert(XMLHttpRequest.status);//200
				alert(XMLHttpRequest.readyState);//4
				alert(textStatus);//pasererror
			}
		});
		break;
	}
}

//query用户数据 
function queryUser() {
	var user_id = document.getElementById("user_id").value;
	var urlStr = url + "WebGetUserInfo?callback=?";
	if (user_id == "") {
		getUserJsonObj();
	} else {
		// 调用jQuery提供的Ajax方法
		$.ajax({
			type : "POST",
			async : false, //(默认: true) 默认设置下，所有请求均为异步请求。如果需要发送同步请求，请将此选项设置为 false。注意，同步请求将锁住浏览器，用户其它操作必须等待请求完成才可以执行。
			url : urlStr,
			data : "{}",
			dataType : "jsonp",
			success : function(msg) {
				user_obj = eval(msg); //调用Json2.js中提供的JSON解析器来解析成JSONObject
				userinfo = user_obj.user;
				for (var i = 0; i < userinfo.length; i++) {
					$("#t" + i).remove();
					if (user_id == userinfo[i].user_id) {
						row = $("<tr id=t" + i + " class='success'></tr>");
						row.append($("<td>" + userinfo[i].user_id + "</td><td>"
								+ userinfo[i].user_name + "</td><td>"
								+ userinfo[i].password + "</td><td>"
								+ userinfo[i].user_nickname + "</td><td>"
								+ userinfo[i].person_signature + "</td><td>"
								+ userinfo[i].sex + "</td><td>"
								+ userinfo[i].school + "</td><td>"
								+ userinfo[i].academy + "</td><td>"
								+ userinfo[i].state + "</td><td>"
								+ userinfo[i].birthday + "</td><td>"
								+ userinfo[i].telephone + "</td><td>"
								+ userinfo[i].picture + "</td><td>"
								+ userinfo[i].SecurityControl + "</td>"));
						$("#tbody_").append(row);
					}
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				alert(errorThrown);
				alert(XMLHttpRequest.status);//200
				alert(XMLHttpRequest.readyState);//4
				alert(textStatus);//pasererror
			}
		});
	}
}

//update用户权限
function updateUserSecurityControl() {
	var user_id = document.getElementById("user_id_update").value;
	var SecurityControl = document.getElementById("value_SecurityControl").value;
	var urlStr = url + "SetUserSecurityControl?callback=?&user_id=" + user_id
			+ "&SecurityControl=" + SecurityControl;
	// 调用jQuery提供的Ajax方法
	$.ajax({
		type : "GET",
		async : false, //(默认: true) 默认设置下，所有请求均为异步请求。如果需要发送同步请求，请将此选项设置为 false。注意，同步请求将锁住浏览器，用户其它操作必须等待请求完成才可以执行。
		url : urlStr,
		data : "{}",
		dataType : "text",
		success : function(msg) {
			if (msg) {
				alert("update success!");
			} else {
				alert("update failed!");
			}
			window.location.reload(true);
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert(errorThrown);
			alert(XMLHttpRequest.status);//200
			alert(XMLHttpRequest.readyState);//4
			alert(textStatus);//pasererror
		}
	});
}

//动态生成一个table
function createTable(info, id) {
	// 使用 $("<table></table>") 生成一个 table
	var tab = $("<table id='table' border='1'></table>");
	var row0 = $("<tr></tr>");
	row0
			.append($("<td>id</td><td>dot_x</td><td>dot_y</td><td>user_id</td><td>user_name</td><td>title</td><td>detail</td><td>time</td><td>label</td>"));
	tab.append(row0);
	if (id == "") {
		for (var i = 0; i < info.length; i++) {
			var row = $("<tr></tr>");
			row.append($("<td>" + info[i].id + "</td><td>" + info[i].DotX
					+ "</td><td>" + info[i].DotY + "</td><td>"
					+ info[i].user_id + "</td><td>" + info[i].user_name
					+ "</td><td>" + info[i].title + "</td><td>"
					+ info[i].detail + "</td><td>" + info[i].time + "</td><td>"
					+ info[i].label + "</td>"));
			tab.append(row);
		}
	} else {
		for (var i = 0; i < info.length; i++) {
			if (info[i].id == id) {
				var row = $("<tr></tr>");
				row.append($("<td>" + info[i].id + "</td><td>" + info[i].DotX
						+ "</td><td>" + info[i].DotY + "</td><td>"
						+ info[i].user_id + "</td><td>" + info[i].user_name
						+ "</td><td>" + info[i].title + "</td><td>"
						+ info[i].detail + "</td><td>" + info[i].time
						+ "</td><td>" + info[i].label + "</td>"));
				tab.append(row);
			}
		}
	}
	// 最后把生成的 <table>***</table> 放到 id=div 的控件中
	$("#tableDialog").append(tab);
	$("#tableDialog")
			.append(
					$("<button id='destroyBnt' class='btn' onclick='destroyTable()'>返回</button>"));
	var x = document.getElementById("tableDialog");
	x.showModal();
}

//destroy一个table
function destroyTable() {
	$("#table").remove();
	$("#destroyBnt").remove();
	var x = document.getElementById("tableDialog");
	x.close();
}