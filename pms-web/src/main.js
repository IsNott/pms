import Vue from 'vue'
import App from './App.vue'
import router from './router'
import ElementUI from 'element-ui'
import Router from 'vue-router'

import 'element-ui/lib/theme-chalk/index.css';
import store from './store'
import VueCropper from 'vue-cropper';


Vue.config.productionTip = false
import {postRequest} from "./utils/axiosUtils";
import {deleteRequest} from "./utils/axiosUtils";
import {getRequest} from "./utils/axiosUtils";
import {putRequest} from "./utils/axiosUtils";
import {initMenu} from "./utils/menus";
import {appRequest} from "./utils/axiosUtils";
import {post} from "./utils/axiosUtils"




import 'font-awesome/css/font-awesome.css'

Vue.prototype.postRequest = postRequest;
Vue.prototype.deleteRequest = deleteRequest;
Vue.prototype.getRequest = getRequest;
Vue.prototype.putRequest = putRequest;
Vue.prototype.appRequest = appRequest;
Vue.prototype.post = post;



Vue.use(ElementUI, {size: 'small'});
Vue.use(VueCropper);

if (window.sessionStorage.getItem('user') != null) {
router.beforeEach((to, from, next) => {
    if (window.sessionStorage.getItem('user') != null) {
        initMenu(router, store);
        next();

    } else {
        next();
    }
});
}



new Vue({
    store,
    router,
    render: h => h(App)
}).$mount('#app')
