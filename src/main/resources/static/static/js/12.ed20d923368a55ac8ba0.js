webpackJsonp([12],{I3qI:function(e,t,a){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var n=a("wI1O"),s={render:function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",{staticClass:"mainshop"},[a("div",{staticClass:"searchDiv"},[a("div",{staticClass:"myShopWidth"},[e._m(0),e._v(" "),a("div",{staticClass:"search"},[a("div",{staticClass:"inputDiv"},[a("el-input",{staticClass:"input-with-select",attrs:{placeholder:"请输入商品名"},model:{value:e.keyword,callback:function(t){e.keyword=t},expression:"keyword"}},[a("el-button",{attrs:{slot:"append",id:"searchButton"},on:{click:function(t){return e.toSearch(1)}},slot:"append"},[e._v("搜索")])],1)],1)])])]),e._v(" "),a("div",{staticClass:"typesDiv myShopWidth"},[a("div",{staticClass:"leftType"},[a("ul",e._l(e.types,function(t,n){return a("li",{key:n,attrs:{id:"myType"+t.id},on:{click:function(a){return e.choiceType(t)}}},[a("span",[e._v(e._s(t.name))]),e._v(" "),e._l(t.sortOrder,function(t,n){return a("span",{key:n,attrs:{id:t.id}},[e._v("\n            "+e._s(t.name)+"\n          ")])})],2)}),0)]),e._v(" "),a("div",{staticClass:"rightImages"},[a("el-carousel",e._l(e.images,function(e,t){return a("el-carousel-item",{key:t},[a("img",{directives:[{name:"lazy",rawName:"v-lazy",value:e.url,expression:"image.url"}],attrs:{alt:e.name}})])}),1)],1)]),e._v(" "),a("div",{staticClass:"likeDiv myShopWidth"},[a("h3",[e._v("商品列表")]),e._v(" "),a("div",{staticClass:"like"},[e.goods.length>0?a("ul",e._l(e.goods,function(t,n){return a("li",{key:n,on:{click:function(a){return e.toGoodDetail(t.id)}}},[a("div",{staticClass:"images"},[a("img",{directives:[{name:"lazy",rawName:"v-lazy",value:t.mainImage,expression:"good.mainImage"}],attrs:{alt:""}})]),e._v(" "),a("div",{staticClass:"nameDiv"},[a("h4",{staticClass:"title"},[e._v(e._s(t.name))]),e._v(" "),a("p",[a("span",{staticClass:"price"},[a("span",[e._v("¥")]),e._v(e._s(t.price)+"\n              ")])])])])}),0):a("div",{staticClass:"emptyGood"},[e._v("\n        暂无商品\n      ")])]),e._v(" "),e.goods.length>0?a("div",{staticClass:"myPagination",staticStyle:{"text-align":"center",height:"70px","line-height":"70px",overflow:"hidden"}},[a("div",{staticStyle:{display:"inline-block",height:"30px","margin-top":"15px"}},[e.Goods.total?a("el-pagination",{attrs:{background:"",layout:"prev, pager, next,total",total:e.Goods.total,"current-page":e.pageNum,"page-size":e.pageSize},on:{"current-change":e.handleCurrentChange}}):e._e()],1)]):e._e()])])},staticRenderFns:[function(){var e=this.$createElement,t=this._self._c||e;return t("div",{staticClass:"logo"},[t("span",[this._v("海创电商")])])}]};var i=function(e){a("JUFd")},r=a("VU/8")(n.a,s,!1,i,null,null);t.default=r.exports},JUFd:function(e,t){},wI1O:function(e,t,a){"use strict";(function(e){var n=a("Xxa5"),s=a.n(n),i=a("D2Ra"),r=a("NYxO"),o=Object.assign||function(e){for(var t=1;t<arguments.length;t++){var a=arguments[t];for(var n in a)Object.prototype.hasOwnProperty.call(a,n)&&(e[n]=a[n])}return e};function c(e){return function(){var t=e.apply(this,arguments);return new Promise(function(e,a){return function n(s,i){try{var r=t[s](i),o=r.value}catch(e){return void a(e)}if(!r.done)return Promise.resolve(o).then(function(e){n("next",e)},function(e){n("throw",e)});e(o)}("next")})}}t.a={data:function(){return{categoryId:100001,pageNum:1,pageSize:20,orderBy:0,keyword:"",types:[],images:[{name:"图片1",url:"https://img.alicdn.com/simba/img/TB1eY4DEeT2gK0jSZFvSutnFXXa.jpg"},{name:"图片2",url:"https://img.alicdn.com/tps/i4/TB18yllEhD1gK0jSZFswu2ldVXa.png_q90_.webp"},{name:"图片3",url:"https://img.alicdn.com/tfs/TB1Kv77DUY1gK0jSZFMXXaWcVXa-520-280.jpg_q90_.webp"}],loading:!1}},components:{},computed:o({},Object(r.c)(["user","Goods","goods"])),created:function(){this.$route.query.keyword&&(this.keyword=JSON.parse(this.$route.query.keyword)),this.toSearch(1),this.reqTypes()},mounted:function(){},methods:o({},Object(r.b)(["saveGoods"]),{handleCurrentChange:function(e){this.toSearch(e)},toSearch:function(e){var t=this;return c(s.a.mark(function a(){var n,r,o,c,u,l,d;return s.a.wrap(function(a){for(;;)switch(a.prev=a.next){case 0:return t.pageNum=e,n=t.pageNum,r=t.pageSize,o=t.orderBy,c=t.keyword,u=t.categoryId,l=c?{pageNum:n,pageSize:r,orderBy:o,keyword:c}:{pageNum:n,pageSize:r,orderBy:o,categoryId:u},a.next=5,Object(i.C)(l);case 5:0===(d=a.sent).status?(t.saveGoods({Goods:d.data,goods:d.data.list}),t.keyword=""):t.message(d.msg,1500,"warning");case 7:case"end":return a.stop()}},a,t)}))()},reqTypes:function(){var e=this;return c(s.a.mark(function t(){var a,n;return s.a.wrap(function(t){for(;;)switch(t.prev=t.next){case 0:return t.next=2,Object(i.r)({categoryId:0});case 2:0==(a=t.sent).status&&((n=a.data).map(function(){var t=c(s.a.mark(function t(n){return s.a.wrap(function(e){for(;;)switch(e.prev=e.next){case 0:return e.next=2,Object(i.r)({categoryId:n.id});case 2:0==(a=e.sent).status&&a.data.length>0&&(n.sortOrder=a.data.slice(0,3));case 4:case"end":return e.stop()}},t,e)}));return function(e){return t.apply(this,arguments)}}()),e.types=n,console.log(e.types));case 4:case"end":return t.stop()}},t,e)}))()},choiceType:function(t){e(".activeLi").removeClass("activeLi"),e("#myType"+t.id).addClass("activeLi"),this.categoryId=t.id,this.toSearch(1)},toGoodDetail:function(e){var t=this.$router.resolve({name:"detail",query:{id:JSON.stringify(e)}}).href;window.open(t,"_blank")},message:function(e,t,a){this.$message({message:e,duration:t,type:a})}})}}).call(t,a("7t+N"))}});