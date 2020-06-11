import axios from 'axios'

const TokenKey = 'EA-Auth-Token'
// 创建axios实例
const service = axios.create({
    baseURL: process.env.VUE_APP_SERVER_URL, // api 的 base_url
    timeout: 5000 // 请求超时时间
})

// request拦截器
service.interceptors.request.use(config => {
        if (!config.headers['Content-Type']) {
            config.headers['Content-Type'] = 'application/json'
        }
        if (localStorage.getItem(TokenKey) && JSON.parse(localStorage.getItem(TokenKey)).access_token) {
            config.headers['Authorization'] = `Bearer ${JSON.parse(localStorage.getItem(TokenKey)).access_token}` // 让每个请求携带自定义token 请根据实际情况自行修改
        }
        return config
    },
    error => {
        // Do something with request error
        console.log(error) // for debug
        Promise.reject(error)
    })

// response 拦截器
service.interceptors.response.use(response => {
        /**
         * status为非200是抛错 可结合自己业务进行修改
         */
        if (response.status >= 200 && response.status < 300) {
            return response.data
        } else {
            let error = new Error(response.statusText)
            error.response = response
            console.error(error)
            return Promise.reject(error)
        }
    },
    error => {
        console.log('in err :' + error) // for debug
        return Promise.reject(error)
    })

export default service
