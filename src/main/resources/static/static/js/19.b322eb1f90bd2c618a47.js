webpackJsonp([19],{A5t1:function(t,s,e){"use strict";Object.defineProperty(s,"__esModule",{value:!0});var r=e("Xxa5"),i=e.n(r),a=e("D2Ra");var n={name:"",components:{},data:function(){return{orderNo:"",Orders:"",orders:"",shippingVo:""}},methods:{reqDetail:function(t){var s,e=this;return(s=i.a.mark(function s(){var r,n;return i.a.wrap(function(s){for(;;)switch(s.prev=s.next){case 0:return(r=new FormData).append("orderNo",t),s.next=4,Object(a.z)(r);case 4:0==(n=s.sent).status&&(e.Orders=n.data,e.orders=n.data.orderItemVoList,e.shippingVo=e.Orders.shippingVo,console.log(n));case 6:case"end":return s.stop()}},s,e)}),function(){var t=s.apply(this,arguments);return new Promise(function(s,e){return function r(i,a){try{var n=t[i](a),o=n.value}catch(t){return void e(t)}if(!n.done)return Promise.resolve(o).then(function(t){r("next",t)},function(t){r("throw",t)});s(o)}("next")})})()},toDetail:function(t){var s=this.$router.resolve({name:"detail",query:{id:JSON.stringify(t)}}).href;window.open(s,"_blank")}},watch:{},computed:{},created:function(){console.log(this.$route.query.orderNo),this.$route.query.orderNo?(this.orderNo=JSON.parse(this.$route.query.orderNo),this.reqDetail(this.orderNo)):this.$router.replace({name:order})},mounted:function(){},beforeDestroy:function(){},destroyed:function(){}},o={render:function(){var t=this,s=t.$createElement,e=t._self._c||s;return e("div",{staticClass:"userOrderDetail"},[e("div",{staticClass:"orderInfoDiv"},[e("h2",{staticClass:"orderDetailTile"},[t._v("订单详情")]),t._v(" "),e("p",{staticClass:"titleP"},[e("span",{staticClass:"title"},[t._v("订单号：")]),t._v(t._s(t.Orders.orderNo))]),t._v(" "),e("p",{staticClass:"titleP"},[e("span",{staticClass:"title"},[t._v("创建时间：")]),t._v(t._s(t.Orders.createTime))]),t._v(" "),e("p",{directives:[{name:"show",rawName:"v-show",value:t.shippingVo,expression:"shippingVo"}],staticClass:"titleP"},[e("span",{staticClass:"title"},[t._v("收件人：")]),t._v("\n      "+t._s(t.shippingVo.receiverName)+"  \n      "+t._s(t.shippingVo.receiverProvince)+" \n      "+t._s(t.shippingVo.receiverDistrict)+" \n      "+t._s(t.shippingVo.receiverCity)+" \n      "+t._s(t.shippingVo.receiverAddress)+" \n    ")]),t._v(" "),e("p",{staticClass:"titleP"},[e("span",{staticClass:"title"},[t._v("订单状态：")]),t._v(t._s(t.Orders.statusDesc))]),t._v(" "),e("p",{staticClass:"titleP"},[e("span",{staticClass:"title"},[t._v("支付方式：")]),t._v(t._s(t.Orders.paymentTypeDesc))])]),t._v(" "),e("div",{staticClass:"orderDetailDiv"},[e("div",{staticClass:"detail"},[e("h2",{staticClass:"orderDetailTile"},[t._v("商品清单")]),t._v(" "),e("ul",[t._m(0),t._v(" "),t._l(t.orders,function(s,r){return e("li",{key:r},[e("div",{staticClass:"goodDiv",on:{click:function(e){return t.toDetail(s.productId)}}},[e("img",{attrs:{src:s.productImage,alt:""}}),t._v(" "),e("p",{staticClass:"orderName"},[t._v(t._s(s.productName)+"asdsda asdsa")])]),t._v(" "),e("span",{staticClass:"other"},[t._v("￥"+t._s(s.currentUnitPrice))]),t._v(" "),e("span",{staticClass:"other"},[t._v(t._s(s.quantity))]),t._v(" "),e("span",{staticClass:"other"},[t._v("￥"+t._s(s.totalPrice))])])})],2)])])])},staticRenderFns:[function(){var t=this.$createElement,s=this._self._c||t;return s("p",{staticClass:"liTitle"},[s("span",{staticClass:"goodinfo"},[this._v("商品信息")]),this._v(" "),s("span",{staticClass:"other"},[this._v("单价")]),this._v(" "),s("span",{staticClass:"other"},[this._v("数量")]),this._v(" "),s("span",{staticClass:"other"},[this._v("合计")])])}]};var c=e("VU/8")(n,o,!1,function(t){e("ZvLx")},null,null);s.default=c.exports},ZvLx:function(t,s){}});