<template>
    <main-layout>
        <div id="demoDiv" style="text-align:center">
            <h1>{{msg}}</h1>
            <br/>
            <alert type="warning" v-if="show" dismissible @dismissed="show = false">
                <b>Warning!</b> 请先学习一下<a href="https://github.com/wxsms/uiv">uiv (https://github.com/wxsms/uiv)</a>.
            </alert>

            <btn type="primary">Primary</btn>
            <btn type="success">Success</btn>
            <btn type="info">Info</btn>
            <btn type="warning">Warning</btn>
            <btn type="danger">Danger</btn>
            <section>
                <date-picker v-model="date"/>
                <br/>
                <alert type="info" v-show="date">You selected <b>{{date}}</b>.</alert>
            </section>

        </div>
    </main-layout>
</template>

<script>
    import MainLayout from './layouts/Main.vue'

    export default {
        components: {
            MainLayout
        },
        name: 'Demo',
        data() {
            return {
                msg: 'Welcome to Your UIV Demo components',
                show: true,
                date: null
            }
        }
    }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>
