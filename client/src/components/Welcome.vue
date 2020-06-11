<template>
    <main-layout>
        <div id="content" role="main" style="text-align: center">
            <section class="row colset-2-its">
                <h1>Welcome</h1>
                <div class="navbar-collapse collapse" aria-expanded="false">
                    <ul class="nav navbar-nav ">
                        <dropdown tag="li">
                            <btn type="primary" class="dropdown-toggle" data-toggle="dropdown" role="button"
                                 aria-haspopup="true"
                                 aria-expanded="false">Server info <span class="caret"></span></btn>
                            <template slot="dropdown">
                                <li v-if="serverInfo"><a href="#">OS name: {{serverInfo['os.name']}}</a></li>
                                <li role="separator" class="divider"></li>
                                <li v-if="serverInfo"><a href="#">OS version: {{serverInfo['os.version']}}</a></li>
                                <li role="separator" class="divider"></li>
                                <li v-if="serverInfo"><a href="#">JVM name:
                                    {{serverInfo['java.vm.specification.vendor']}}</a></li>
                                <li role="separator" class="divider"></li>
                                <li v-if="serverInfo"><a href="#">JVM version: {{serverInfo['java.version']}}</a></li>
                                <li role="separator" class="divider"></li>
                            </template>
                        </dropdown>
                        <dropdown tag="li">
                            <btn type="primary" class="dropdown-toggle" data-toggle="dropdown" role="button"
                                 aria-haspopup="true"
                                 aria-expanded="false">Component <span class="caret"></span></btn>
                            <template slot="dropdown" v-if="serverInfo">
                                <li v-if="serverInfo.artefacts"><a href="#">Controllers:
                                    {{serverInfo.artefacts.controllers}}</a></li>
                                <li v-if="serverInfo.artefacts"><a href="#">Domains:
                                    {{serverInfo.artefacts.domains}}</a></li>
                                <li v-if="serverInfo.artefacts"><a href="#">Services:
                                    {{serverInfo.artefacts.services}}</a></li>
                            </template>
                        </dropdown>
                    </ul>
                </div>
                <p>
                    EA & Vue.js application!
                </p>
                <button v-on:click="refreshToken">refresh token</button>
            </section>
        </div>
    </main-layout>
</template>

<script>
    import MainLayout from './layouts/Main.vue'
    import request from '@/utils/request'
    import queryString from 'query-string'

    const TokenKey = 'EA-Auth-Token'
    export default {
        components: {
            MainLayout
        },
        name: 'Welcome',
        data() {
            return {
                msg: 'Welcome to Your EA & Vue.js App',
                auth: null,
                serverInfo: null,
                showLinks: false,
                serverURL: process.env.SERVER_URL
            }
        },
        methods: {
            toggleLinks: function () {
                this.showLinks = !this.$data.showLinks
            },
            login: function () {
                request({
                    url: '/api/login',
                    method: 'POST',
                    data: JSON.stringify({username: 'user', password: 'user'})
                }).then(json => {
                    localStorage.setItem(TokenKey, JSON.stringify(json))
                    this.fetchInfo()
                }).catch(error => console.log(error.message))
            },
            refreshToken: function () {
                request({
                    url: '/oauth/access_token',
                    method: 'POST',
                    headers: {'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'},
                    data: queryString.stringify({
                        'grant_type': 'refresh_token',
                        'refresh_token': JSON.parse(localStorage.getItem(TokenKey)).refresh_token
                    })
                }).then(json => {
                    localStorage.setItem(TokenKey, JSON.stringify(json))
                }).catch(error => console.log(error.message))
            },
            fetchInfo: function () {
                request({
                    url: '/application/index',
                    method: 'POST',
                    data: JSON.stringify({})
                }).then(json => {
                    this.serverInfo = json
                    console.log(this.serverInfo)
                }).catch(error => console.log(error.message))
            }
        },
        created: function () {
            this.login()
        },
        mounted: function () {
            // this.login()
        }
    }
</script>
