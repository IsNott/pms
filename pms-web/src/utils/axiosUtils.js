import axios from "axios";
import {Message} from "element-ui";
import router from "../router"
import QS from 'qs';

/**
 * 请求拦截器
 */
axios.interceptors.request.use(
    config => {
        let token = sessionStorage.getItem('tokenStr');
        config.headers['Authorization'] = token;
        return config;
    },
    error => {
        console.log(error)
    }
);


//响应拦截器
axios.interceptors.response.use(
    success => {
        if (success.status && success.status === 200) {
            if (success.data.code === -999) {
                Message.error({
                    message: success.data.msg
                });
                return
            }
            if (success.data.code === 0) {
                // Message.success({
                //   message:success.data.msg
                // })
            }
            if (success.data.code === 999) {
                Message.success({
                    message: '服务器出错了'
                })
            }
            if (success.data.code === -10001 && router.path !== "/") {
                router.replace("/")
            }
        }
        return success.data;
    },
    error => {
        if (error.response.code === 504 || error.response.status === 404) {
            //this.$router.replace("/404");
            Message.error({
                message: '服务器出错了'
            })
        } else if (error.response.code === -1000) {
            this.$message.error("无权限");
            router.replace("/errorPage")
        } else if (error.response.code === 500) {
            Message.error({
                message: '不支持当前请求'
            })
        } else if (error.response.code === -10001) {
            router.replace("/")
        } else if (error.response.status === 401) {
            // Message.error({
            //     message: '未登录 请先登录'
            // })
            router.replace("/")
        } else {
            if (error.response.data.message) {
                Message.error({
                        message: error.response.msg
                    }
                )
            } else {
                Message.error(
                    {message: "未知错误"}
                )
            }
        }
        return;
    });


let base = '';
// let base='http://47.99.103.163:8080'
export const postRequest = (url, params) => {
    return axios({
        method: 'post',
        url: `${base}${url}`,
        data: params,
        transformRequest: [function (data) {
            let ret = '';
            for (let i in data) {
                ret += encodeURIComponent(i) + '=' + encodeURIComponent(data[i]) + '&';
            }
            // console.log(ret);
            return ret;
        }],
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        }
    })
};

export function post(url, params) {
    return axios({
        method: 'post',
        url: `${base}${url}`,
        headers: {
            'Content-Type': 'application/json;charset=UTF-8',
        },
        dataType: 'JSON',
        data: params
    })
};


export const appRequest = (url, params) => {
    return axios({
        method: 'post',
        url: `${base}${url}`,
        data: params instanceof FormData ? params : QS.stringify(params),
        headers: {
            'Content-Type': 'application/json;charset=UTF-8'
        }
    })
};
export const deleteRequest = (url, params) => {
    return axios({
        method: 'delete',
        url: `${base}${url}`,
        data: params,
        transformRequest: [function (data) {
            let ret = '';
            for (let i in data) {
                ret += encodeURIComponent(i) + '=' + encodeURIComponent(data[i]) + '&';
            }
            // console.log(ret);
            return ret;
        }],
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        }
    })
};
export const getRequest = (url, params) => {
    return axios({
        method: 'get',
        url: `${base}${url}`,
        data: params,
        transformRequest: [function (data) {
            let ret = '';
            for (let i in data) {
                ret += encodeURIComponent(i) + '=' + encodeURIComponent(data[i]) + '&';
            }
            // console.log(ret);
            return ret;
        }],
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        }
    })
};

export const putRequest = (url, params) => {
    return axios({
        method: 'put',
        url: `${base}${url}`,
        data: params,
        transformRequest: [function (data) {
            let ret = '';
            for (let i in data) {
                ret += encodeURIComponent(i) + '=' + encodeURIComponent(data[i]) + '&';
            }
            //console.log(ret);
            return ret;
        }],
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        }
    })
};





