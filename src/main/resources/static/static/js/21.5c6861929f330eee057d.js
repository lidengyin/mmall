webpackJsonp([21],{"6EcA":function(t,e){},E4IY:function(t,e,s){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var r=s("Xxa5"),a=s.n(r),i=s("NYxO"),n=s("D2Ra"),o=Object.assign||function(t){for(var e=1;e<arguments.length;e++){var s=arguments[e];for(var r in s)Object.prototype.hasOwnProperty.call(s,r)&&(t[r]=s[r])}return t};function c(t){return function(){var e=t.apply(this,arguments);return new Promise(function(t,s){return function r(a,i){try{var n=e[a](i),o=n.value}catch(t){return void s(t)}if(!n.done)return Promise.resolve(o).then(function(t){r("next",t)},function(t){r("throw",t)});t(o)}("next")})}}var l={name:"goodDetail",data:function(){return{myOrder:"",orderNo:"",shippingVo:"",error:!1}},computed:o({},Object(i.c)([])),created:function(){this.$route.query.orderNo?(this.choiceMenuId("orderList"),this.orderNo=this.$route.query.orderNo,this.reqOrderDetail(this.orderNo),this.changePathNames([{name:"订单管理"},{name:"订单列表",path:"orderList"},{name:"订单详情"}])):this.$router.replace({name:"orderList"})},methods:o({},Object(i.b)(["choiceMenuId","changePathNames"]),{toBack:function(){this.$router.go(-1)},reqOrderDetail:function(){var t=this;return c(a.a.mark(function e(){var s,r;return a.a.wrap(function(e){for(;;)switch(e.prev=e.next){case 0:return s=t.orderNo,e.next=3,Object(n.m)({orderNo:s});case 3:0===(r=e.sent).status?(t.myOrder=r.data,t.shippingVo=t.myOrder.shippingVo):(t.error=!0,t.message(r.msg,1500,"error"));case 5:case"end":return e.stop()}},e,t)}))()},sendGood:function(){var t=this;return c(a.a.mark(function e(){var s,r;return a.a.wrap(function(e){for(;;)switch(e.prev=e.next){case 0:return s=t.orderNo,e.next=3,Object(n.J)({orderNo:s});case 3:0==(r=e.sent).status?t.myOrder.statusDesc="已发货":t.message(r.msg,1500,"error");case 5:case"end":return e.stop()}},e,t)}))()},message:function(t,e,s){this.$message({message:t,duration:e,type:s})}})},v={render:function(){var t=this,e=t.$createElement,r=t._self._c||e;return r("div",{staticClass:"orderDetail"},[t.error?r("div",[r("div",{staticClass:"outError"},[r("img",{attrs:{src:s("Q1lq"),alt:""}}),t._v(" "),r("div",{staticClass:"fontDiv"},[r("span",{staticClass:"fontError"},[t._v("获取商品详情出错")]),t._v(" "),r("el-button",{staticClass:"returnOrder",attrs:{type:"text"},on:{click:t.toBack}},[t._v("返回")])],1)])]):r("div",[r("div",{staticClass:"orderInfoDiv"},[r("h2",{staticClass:"orderDetailTile"},[t._v("订单详情")]),t._v(" "),r("p",{staticClass:"titleP"},[r("span",{staticClass:"title"},[t._v("订单号：")]),t._v(t._s(t.myOrder.orderNo))]),t._v(" "),r("p",{staticClass:"titleP"},[r("span",{staticClass:"title"},[t._v("创建时间：")]),t._v(t._s(t.myOrder.createTime))]),t._v(" "),r("p",{directives:[{name:"show",rawName:"v-show",value:t.shippingVo,expression:"shippingVo"}],staticClass:"titleP"},[r("span",{staticClass:"title"},[t._v("收件人：")]),t._v("\n        "+t._s(t.shippingVo.receiverName)+"  \n        "+t._s(t.shippingVo.receiverProvince)+" \n        "+t._s(t.shippingVo.receiverDistrict)+" \n        "+t._s(t.shippingVo.receiverCity)+" \n        "+t._s(t.shippingVo.receiverAddress)+" \n      ")]),t._v(" "),r("p",{staticClass:"titleP"},[r("span",{staticClass:"title"},[t._v("订单状态：")]),t._v(t._s(t.myOrder.statusDesc)+" "),"已付款"==t.myOrder.statusDesc?r("el-button",{staticStyle:{"margin-left":"20px"},attrs:{type:"text"},on:{click:t.sendGood}},[t._v("立即发货")]):t._e()],1),t._v(" "),r("p",{staticClass:"titleP"},[r("span",{staticClass:"title"},[t._v("支付方式：")]),t._v(t._s(t.myOrder.paymentTypeDesc))]),t._v(" "),r("p",{staticClass:"titleP"},[r("span",{staticClass:"title"},[t._v("订单金额：")]),t._v("￥"+t._s(t.myOrder.payment))])]),t._v(" "),r("div",{staticClass:"orderDetailDiv"},[r("div",{staticClass:"detail"},[r("h2",{staticClass:"orderDetailTile"},[t._v("商品清单")]),t._v(" "),r("ul",[t._m(0),t._v(" "),t._l(t.myOrder.orderItemVoList,function(e,s){return r("li",{key:s},[r("div",{staticClass:"goodDiv"},[r("img",{attrs:{src:e.productImage,alt:""}}),t._v(" "),r("p",{staticClass:"orderName"},[t._v(t._s(e.productName))])]),t._v(" "),r("span",{staticClass:"other"},[t._v("￥"+t._s(e.currentUnitPrice))]),t._v(" "),r("span",{staticClass:"other"},[t._v(t._s(e.quantity))]),t._v(" "),r("span",{staticClass:"other"},[t._v("￥"+t._s(e.totalPrice))])])})],2)])])])])},staticRenderFns:[function(){var t=this.$createElement,e=this._self._c||t;return e("p",{staticClass:"liTitle"},[e("span",{staticClass:"goodinfo"},[this._v("商品信息")]),this._v(" "),e("span",{staticClass:"other"},[this._v("单价")]),this._v(" "),e("span",{staticClass:"other"},[this._v("数量")]),this._v(" "),e("span",{staticClass:"other"},[this._v("合计")])])}]};var d=s("VU/8")(l,v,!1,function(t){s("6EcA")},null,null);e.default=d.exports}});