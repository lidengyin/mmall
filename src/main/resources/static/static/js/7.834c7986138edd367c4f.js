webpackJsonp([7],{"3AJa":function(e,t){},OlDp:function(e,t,r){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var a=r("Xxa5"),n=r.n(a),s=r("D2Ra"),o=r("NYxO"),i=Object.assign||function(e){for(var t=1;t<arguments.length;t++){var r=arguments[t];for(var a in r)Object.prototype.hasOwnProperty.call(r,a)&&(e[a]=r[a])}return e};var c={data:function(){return{ruleForm:{categoryName:""},rules:{categoryName:[{required:!0,message:"请输入品类名称",trigger:"blur"},{max:8,message:"品类名称不得超过8个字",trigger:"blur"}]},defaultProps:{label:"categoryName"}}},computed:i({},Object(o.c)(["menus","showAdd"])),props:{parentId:{required:!0}},created:function(){},mounted:function(){},methods:i({},Object(o.b)(["reqMenus1","showadd"]),{closeAdd:function(){this.showadd(!1)},uploadType:function(){var e,t=this;return(e=n.a.mark(function e(){var r,a,o,i;return n.a.wrap(function(e){for(;;)switch(e.prev=e.next){case 0:if(r=t.parentId,!((a=t.ruleForm.categoryName).trim()&&a.length<9)){e.next=17;break}if(null==sessionStorage.getItem("haichuang_user_manage")){e.next=13;break}return(o=new FormData).append("parentId",r),o.append("categoryName",a),e.next=9,Object(s.c)(o);case 9:0==(i=e.sent).status?(t.$emit("setNewList",r),t.message(i.msg,1500,"success"),t.closeAdd()):t.message(i.msg,1500,"error"),e.next=15;break;case 13:t.message("用户未登录，将返回登录界面",1500,"error"),t.$router.replace({name:"login"});case 15:e.next=18;break;case 17:t.message("品类名称有误",1500,"warning");case 18:case"end":return e.stop()}},e,t)}),function(){var t=e.apply(this,arguments);return new Promise(function(e,r){return function a(n,s){try{var o=t[n](s),i=o.value}catch(e){return void r(e)}if(!o.done)return Promise.resolve(i).then(function(e){a("next",e)},function(e){a("throw",e)});e(i)}("next")})})()},message:function(e,t,r){this.$message({message:e,type:r,duration:t})},resetForm:function(e){this.$refs[e].resetFields()}}),watch:{showAdd:function(e,t){e||this.resetForm("ruleForm")},parentId:function(e,t){console.log(e)}}},l={render:function(){var e=this,t=e.$createElement,r=e._self._c||t;return r("div",{staticClass:"addType"},[r("el-form",{ref:"ruleForm",staticClass:"demo-ruleForm",attrs:{model:e.ruleForm,rules:e.rules,"label-position":"right","label-width":"100px"}},[r("div",{staticClass:"title"},[e._v("\n        注册品类：\n      ")]),e._v(" "),r("el-form-item",{attrs:{label:"品类名称",prop:"categoryName","label-width":"80px"}},[r("el-input",{attrs:{placeholder:"请输入品类名称,不得超过8个字"},model:{value:e.ruleForm.categoryName,callback:function(t){e.$set(e.ruleForm,"categoryName",t)},expression:"ruleForm.categoryName"}})],1)],1),e._v(" "),r("div",{staticStyle:{height:"36px","margin-top":"30px"}},[r("el-row",{staticStyle:{float:"right"}},[r("el-button",{staticStyle:{padding:"10px 20px"},attrs:{type:"danger"},on:{click:e.closeAdd}},[e._v("取消")]),e._v(" "),r("el-button",{staticStyle:{padding:"10px 20px"},attrs:{type:"primary"},on:{click:e.uploadType}},[e._v("确定")])],1)],1)],1)},staticRenderFns:[]};var u=r("VU/8")(c,l,!1,function(e){r("3AJa")},null,null).exports,d=Object.assign||function(e){for(var t=1;t<arguments.length;t++){var r=arguments[t];for(var a in r)Object.prototype.hasOwnProperty.call(r,a)&&(e[a]=r[a])}return e};var p={data:function(){return{categoryId:"",parentId:"",ruleForm:{categoryName:""},rules:{categoryName:[{required:!0,message:"请输入角色名",trigger:"blur"},{max:8,message:"角色名不得超过8个字",trigger:"blur"}]},defaultProps:{label:"categoryName"}}},computed:d({},Object(o.c)(["showFix"])),props:{type:{required:!0}},created:function(){},mounted:function(){this.ruleForm.categoryName=this.type.name,this.categoryId=this.type.id,this.parentId=this.type.parentId,""==this.categoryId&&(this.message("请求出错",1e3,"error"),this.closeFix())},watch:{type:function(e,t){this.ruleForm.categoryName=e.name,this.parentId=e.parentId,this.categoryId=e.id},categoryId:function(e,t){""==e&&(this.message("请求出错",1500,"error"),this.closeFix())},deep:!0},methods:d({},Object(o.b)(["showfix"]),{closeFix:function(){this.showfix(!1)},updatetype:function(){var e,t=this;return(e=n.a.mark(function e(){var r,a,o,i,c;return n.a.wrap(function(e){for(;;)switch(e.prev=e.next){case 0:if(r=t.categoryId,a=t.parentId,!((o=t.ruleForm.categoryName).trim()&&o.length<9)){e.next=17;break}if(null==sessionStorage.getItem("haichuang_user_manage")){e.next=13;break}return(i=new FormData).append("categoryId",r),i.append("categoryName",o),e.next=9,Object(s.K)(i);case 9:0==(c=e.sent).status?(console.log("1"),t.$emit("setNewName",a,r,o),console.log("2"),t.closeFix(),setTimeout(function(){t.message(c.msg,1200,"success")},100),console.log("3")):t.message(c.msg,1200,"error"),e.next=15;break;case 13:t.message("用户未登录，将返回登录界面",1500,"error"),t.$router.replace({name:"login"});case 15:e.next=18;break;case 17:t.message("品类名称有误",1500,"warning");case 18:case"end":return e.stop()}},e,t)}),function(){var t=e.apply(this,arguments);return new Promise(function(e,r){return function a(n,s){try{var o=t[n](s),i=o.value}catch(e){return void r(e)}if(!o.done)return Promise.resolve(i).then(function(e){a("next",e)},function(e){a("throw",e)});e(i)}("next")})})()},message:function(e,t,r){this.$message({message:e,type:r,duration:t})},resetForm:function(e){this.$refs[e].resetFields()}})},m={render:function(){var e=this,t=e.$createElement,r=e._self._c||t;return r("div",{directives:[{name:"show",rawName:"v-show",value:e.showFix,expression:"showFix"}],staticClass:"fixType"},[r("el-form",{ref:"ruleForm",staticClass:"demo-ruleForm",attrs:{model:e.ruleForm,rules:e.rules,"label-position":"right","label-width":"100px"}},[r("div",{staticClass:"title"},[e._v("\n      修改品类：\n    ")]),e._v(" "),r("el-form-item",{attrs:{label:"品类名称",prop:"categoryName","label-width":"80px"}},[r("el-input",{attrs:{placeholder:"请输入品类名称,不得超过8个字"},model:{value:e.ruleForm.categoryName,callback:function(t){e.$set(e.ruleForm,"categoryName",t)},expression:"ruleForm.categoryName"}})],1)],1),e._v(" "),r("div",{staticStyle:{height:"36px","margin-top":"30px"}},[r("el-row",{staticStyle:{float:"right"}},[r("el-button",{staticStyle:{padding:"10px 20px"},attrs:{type:"danger"},on:{click:e.closeFix}},[e._v("取消")]),e._v(" "),r("el-button",{staticStyle:{padding:"10px 20px"},attrs:{type:"primary"},on:{click:e.updatetype}},[e._v("确定")])],1)],1)],1)},staticRenderFns:[]};var f=r("VU/8")(p,m,!1,function(e){r("QpRf")},null,null).exports,h=Object.assign||function(e){for(var t=1;t<arguments.length;t++){var r=arguments[t];for(var a in r)Object.prototype.hasOwnProperty.call(r,a)&&(e[a]=r[a])}return e};function g(e){return function(){var t=e.apply(this,arguments);return new Promise(function(e,r){return function a(n,s){try{var o=t[n](s),i=o.value}catch(e){return void r(e)}if(!o.done)return Promise.resolve(i).then(function(e){a("next",e)},function(e){a("throw",e)});e(i)}("next")})}}var y={data:function(){return{categoryId:0,parentId:0,loading:!1,types:[],type:""}},computed:h({},Object(o.c)(["showAdd","showFix"])),components:{AddType:u,FixType:f},created:function(){this.reqTypes(this.categoryId)},mounted:function(){this.choiceMenuId("mType"),this.changePathNames([{name:"品类管理"}])},methods:h({},Object(o.b)(["choiceMenuId","showadd","showfix","changePathNames"]),{load:function(e,t,r){var a=this;return g(n.a.mark(function t(){var o,i,c,l;return n.a.wrap(function(t){for(;;)switch(t.prev=t.next){case 0:return o=e.id,t.next=3,Object(s.r)({categoryId:o});case 3:i=t.sent,c="",0==i.status?(c=i.data).map(function(e){e.createTime=e.createTime.slice(0,10)}):(l=i.msg,a.message(l,1200,"error")),r(c);case 7:case"end":return t.stop()}},t,a)}))()},reqTypes:function(e){var t=this;return g(n.a.mark(function r(){var a,o,i;return n.a.wrap(function(r){for(;;)switch(r.prev=r.next){case 0:return r.next=2,Object(s.r)({categoryId:e});case 2:0==(a=r.sent).status?(t.loading=!1,(o=a.data).map(function(){var e=g(n.a.mark(function e(r){return n.a.wrap(function(e){for(;;)switch(e.prev=e.next){case 0:return r.createTime=r.createTime.slice(0,10),e.next=3,Object(s.r)({categoryId:r.id});case 3:0==(a=e.sent).status&&a.data.length>0&&(r.sortOrder=!0);case 5:case"end":return e.stop()}},e,t)}));return function(t){return e.apply(this,arguments)}}()),t.types=o):(t.loading=!1,i=a.msg,t.message(i,1500,"error"));case 4:case"end":return r.stop()}},r,t)}))()},fixType:function(e){this.type=e,this.showfix(!0)},addType:function(e){var t=e.id;this.parentId=t,this.showadd(!0)},setNewName:function(e,t,r){var a,o=this;console.log(e,t,r),0==e?this.types.map(function(e){e.createTime=e.createTime.slice(0,10),e.id==t&&(e.name=r)}):this.types.map((a=g(n.a.mark(function t(r){var a;return n.a.wrap(function(t){for(;;)switch(t.prev=t.next){case 0:if(r.id!=e){t.next=5;break}return t.next=3,Object(s.r)({categoryId:r.id});case 3:0==(a=t.sent).status&&a.data.length>0&&o.$set(o.$refs.myTable.store.states.lazyTreeNodeMap,e,a.data);case 5:case"end":return t.stop()}},t,o)})),function(e){return a.apply(this,arguments)}))},setNewList:function(e){var t,r=this;0!=e?this.types.map((t=g(n.a.mark(function t(a){var o;return n.a.wrap(function(t){for(;;)switch(t.prev=t.next){case 0:if(a.createTime=a.createTime.slice(0,10),a.id!=e){t.next=6;break}return t.next=4,Object(s.r)({categoryId:e});case 4:0==(o=t.sent).status&&o.data.length>0&&r.$set(r.$refs.myTable.store.states.lazyTreeNodeMap,e,o.data);case 6:case"end":return t.stop()}},t,r)})),function(e){return t.apply(this,arguments)})):this.reqTypes(e)},message:function(e,t,r){this.$message({message:e,type:r,duration:t})},deleteType:function(){var e=this;this.$confirm("此操作将永久删除该品类, 是否继续?","提示",{confirmButtonText:"确定",cancelButtonText:"取消",type:"warning"}).then(function(){e.message("删除成功!",1500,"success")})},closeAdd:function(){this.showadd(!1)},closeFix:function(){this.showfix(!1)}}),watch:{types:function(e,t){console.log(e)}}},v={render:function(){var e=this,t=e.$createElement,r=e._self._c||t;return r("div",{staticClass:"type"},[r("div",{staticStyle:{padding:"20px"}},[r("el-button",{staticStyle:{"margin-top":"-5px"},attrs:{type:"primary",icon:"el-icon-plus"},on:{click:function(t){return e.addType({id:0})}}},[e._v("注册品类")]),e._v(" "),r("el-dialog",{attrs:{visible:e.showAdd,"before-close":e.closeAdd,width:"500px"},on:{"update:visible":function(t){e.showAdd=t}}},[r("AddType",{attrs:{parentId:e.parentId},on:{setNewList:e.setNewList}})],1),e._v(" "),r("el-dialog",{attrs:{visible:e.showFix,"before-close":e.closeFix,width:"500px"},on:{"update:visible":function(t){e.showFix=t}}},[r("FixType",{attrs:{type:e.type},on:{setNewName:e.setNewName}})],1),e._v(" "),r("div",{staticClass:"lis"},[r("el-table",{ref:"myTable",staticStyle:{width:"100%"},attrs:{data:e.types,"row-key":"id",border:"",stripe:"",lazy:"",load:e.load,"tree-props":{children:"sortOrder",hasChildren:"sortOrder"}}},[r("el-table-column",{attrs:{prop:"name",label:"品类名","min-width":"120"}}),e._v(" "),r("el-table-column",{attrs:{prop:"id",label:"ID","min-width":"120"}}),e._v(" "),r("el-table-column",{attrs:{prop:"createTime",label:"更新时间","min-width":"120"}}),e._v(" "),r("el-table-column",{attrs:{label:"操作","min-width":"150"},scopedSlots:e._u([{key:"default",fn:function(t){return[r("span",{staticClass:"typeButton",on:{click:function(r){return e.fixType(t.row)}}},[e._v("修改品类")]),e._v(" "),r("span",{directives:[{name:"show",rawName:"v-show",value:0==t.row.parentId,expression:"scope.row.parentId==0"}],staticClass:"typeButton",on:{click:function(r){return e.addType(t.row)}}},[e._v("添加子品类")])]}}])})],1)],1)],1)])},staticRenderFns:[]};var x=r("VU/8")(y,v,!1,function(e){r("l20C")},null,null);t.default=x.exports},QpRf:function(e,t){},l20C:function(e,t){}});