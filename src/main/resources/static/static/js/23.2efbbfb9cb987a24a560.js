webpackJsonp([23],{A5ya:function(e,s){},C3Ad:function(e,s,t){"use strict";Object.defineProperty(s,"__esModule",{value:!0});var r=t("Xxa5"),n=t.n(r),a=t("D2Ra"),i=t("NYxO"),o=Object.assign||function(e){for(var s=1;s<arguments.length;s++){var t=arguments[s];for(var r in t)Object.prototype.hasOwnProperty.call(t,r)&&(e[r]=t[r])}return e};function u(e){return function(){var s=e.apply(this,arguments);return new Promise(function(e,t){return function r(n,a){try{var i=s[n](a),o=i.value}catch(e){return void t(e)}if(!i.done)return Promise.resolve(o).then(function(e){r("next",e)},function(e){r("throw",e)});e(o)}("next")})}}var l={data:function(){return{id:"",username:"",phone:"",email:"",question:"",answer:"",saveUser:"",emailError:"",emailOk:!0,phoneError:"",phoneOk:!0}},computed:o({},Object(i.c)(["user"])),created:function(){"null"!=sessionStorage.getItem("haichuang_user")&&this.setuser(JSON.parse(sessionStorage.getItem("haichuang_user")))},mounted:function(){this.user&&(this.saveUser=this.user,this.id=this.user.id,this.username=this.user.username,this.phone=this.user.phone,this.email=this.user.email,this.question=this.user.question,this.answer=this.user.answer)},methods:o({},Object(i.b)(["setuser"]),{saveInfo:function(){var e=this;return u(n.a.mark(function s(){var t,r,i,o,u,l,c,h;return n.a.wrap(function(s){for(;;)switch(s.prev=s.next){case 0:if(t=e.phone,r=e.email,i=e.question,o=e.answer,u=e.phoneOk,l=e.emailOk,e.emailError,e.phoneError,!(i.trim()&&o.trim()&&t.trim()&&r.trim())){s.next=17;break}if(!u||!l){s.next=14;break}return(c=new FormData).append("phone",t),c.append("email",r),c.append("question",i),c.append("answer",o),s.next=10,Object(a.P)(c);case 10:0==(h=s.sent).status?(e.saveUser.phone=t,e.saveUser.email=r,e.saveUser.question=i,e.saveUser.answer=o,sessionStorage.setItem("haichuang_user",JSON.stringify(e.saveUser)),e.setuser(e.saveUser),e.message(h.msg,1500,"success")):e.message(h.msg,1500,"error"),s.next=15;break;case 14:e.message("请输入正确信息",1500,"error");case 15:s.next=18;break;case 17:e.message("请输入完整信息",1500,"warning");case 18:case"end":return s.stop()}},s,e)}))()},ruleEmail:function(){var e=this;return u(n.a.mark(function s(){var t,r;return n.a.wrap(function(s){for(;;)switch(s.prev=s.next){case 0:if(t=e.email,/^([a-zA-Z0-9]+[-_\.]?)+@[a-zA-Z0-9]+\.[a-z]+$/.test(t)){s.next=8;break}e.message("邮箱格式不正确",1500,"error"),e.emailError="邮箱格式不正确",e.emailOk=!1,s.next=17;break;case 8:if(t==e.user.email){s.next=15;break}return s.next=11,Object(a.g)({str:t,type:"email"});case 11:0==(r=s.sent).status?(e.emailOk=!0,e.emailError=""):(e.emailOk=!1,e.emailError=r.msg),s.next=17;break;case 15:e.emailOk=!0,e.emailError="";case 17:case"end":return s.stop()}},s,e)}))()},rulePhone:function(){var e=this.phone;/^1[34578]\d{9}$/.test(e)?(this.phoneOk=!0,this.phoneError=""):(this.phoneError="手机号码不正确",this.phoneOk=!1)},message:function(e,s,t){this.$message({message:e,duration:s,type:t})}})},c={render:function(){var e=this,s=e.$createElement,t=e._self._c||s;return t("div",{staticClass:"info"},[t("p",[t("span",{staticClass:"infoName"},[e._v("用户名：")]),e._v(e._s(e.username))]),e._v(" "),t("p",[e._m(0),e._v(" "),t("el-input",{attrs:{placeholder:"请输入手机号"},on:{blur:e.rulePhone},model:{value:e.phone,callback:function(s){e.phone=s},expression:"phone"}}),e._v(" "),e.phoneOk?e._e():t("span",{staticStyle:{color:"red","padding-left":"10px","font-size":"14px"}},[e._v(e._s(e.phoneError))])],1),e._v(" "),t("p",[e._m(1),e._v(" "),t("el-input",{attrs:{placeholder:"请输入邮箱"},on:{blur:e.ruleEmail},model:{value:e.email,callback:function(s){e.email=s},expression:"email"}}),e._v(" "),e.emailOk?e._e():t("span",{staticStyle:{color:"red","padding-left":"10px","font-size":"14px"}},[e._v(e._s(e.emailError))])],1),e._v(" "),t("p",[e._m(2),e._v(" "),t("el-input",{attrs:{placeholder:"请输入密保问题"},model:{value:e.question,callback:function(s){e.question=s},expression:"question"}})],1),e._v(" "),t("p",[e._m(3),e._v(" "),t("el-input",{attrs:{placeholder:"请输入密保答案"},model:{value:e.answer,callback:function(s){e.answer=s},expression:"answer"}})],1),e._v(" "),t("p",[t("span",{staticClass:"infoName"}),e._v(" "),t("el-button",{on:{click:e.saveInfo}},[e._v("保存")])],1)])},staticRenderFns:[function(){var e=this.$createElement,s=this._self._c||e;return s("span",{staticClass:"infoName"},[s("i",[this._v("*")]),this._v("手机号：")])},function(){var e=this.$createElement,s=this._self._c||e;return s("span",{staticClass:"infoName"},[s("i",[this._v("*")]),this._v("邮箱：")])},function(){var e=this.$createElement,s=this._self._c||e;return s("span",{staticClass:"infoName"},[s("i",[this._v("*")]),this._v("密保问题：")])},function(){var e=this.$createElement,s=this._self._c||e;return s("span",{staticClass:"infoName"},[s("i",[this._v("*")]),this._v("密保答案：")])}]};var h=t("VU/8")(l,c,!1,function(e){t("A5ya")},null,null);s.default=h.exports}});