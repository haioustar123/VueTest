import Vue from 'vue'
import Router from 'vue-router'
import Welcome from '@/components/Welcome'
import Demo from '@/components/Demo'
import Table from '@/components/Table'

Vue.use(Router)

export default new Router({
    routes: [
        {
            path: '/',
            name: 'Welcome',
            component: Welcome
        },
        {
            path: '/demo',
            name: 'Demo',
            component: Demo
        },
        {
            path: '/table',
            name: 'Table',
            component: Table
        }
    ]
})
