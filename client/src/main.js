// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import * as uiv from 'uiv'
import locale from 'uiv/src/locale/lang/zh-CN'
import {ServerTable, ClientTable} from 'vue-tables-2'

import './assets/css/bootstrap.css'
import './assets/css/app.css'
import './assets/css/main.css'

Vue.config.productionTip = false

Vue.use(uiv, {locale})
Vue.use(ClientTable)
Vue.use(ServerTable)
/* eslint-disable no-new */
new Vue({
    el: '#app',
    router,
    components: {App},
    template: '<App/>'
})