<template>
    <main-layout>
        <h1>{{msg}}</h1>
        <br/>
        <alert type="warning" v-if="show" dismissible @dismissed="show = false">
            <b>Warning!</b> 请先学习一下<a href="https://github.com/matfish2/vue-tables-2">vue-table-2
            (https://github.com/matfish2/vue-tables-2)</a>.
        </alert>
        <div id="people">
            <v-client-table :data="tableData" :columns="columns" :options="options"></v-client-table>
        </div>
    </main-layout>
</template>

<script>
    import MainLayout from './layouts/Main.vue'

    export default {

        components: {
            MainLayout
        },
        name: 'Table',
        data() {
            return {
                msg: 'Welcome to Your vue-table-2 Demo components',
                show: true,
                columns: ['id', 'name', 'age'],
                tableData: [
                    {id: 1, name: 'John', age: '20'},
                    {id: 2, name: 'Jane', age: '24'},
                    {id: 3, name: 'Susan', age: '16'},
                    {id: 4, name: 'Chris', age: '55'},
                    {id: 5, name: 'Dan', age: '40'}
                ],
                options: {
                    // see the options API
                }
            }
        }
    }
</script>

<style scoped>

</style>
