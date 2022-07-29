<template>
    <div class="back">
        <div id="login">
            <h2>人事管理系统</h2>
            <el-form  :rules="rules" ref="loginForm" :model="loginForm" class="loginContainer" >
                <h3 class="loginTitle">登录</h3>
                <el-form-item prop="username">
                    <el-input type="text" v-model="loginForm.username" placeholder="请输入用户名"></el-input>
                </el-form-item>
                <el-form-item prop="password">
                    <el-input type="password" v-model="loginForm.password" placeholder="请输入密码"></el-input>
                </el-form-item>
                <el-form-item prop="code">
                    <el-input type="text" v-model="loginForm.code" placeholder="点击图片更换验证码"></el-input>
                </el-form-item>
                <el-form-item>
                    <img :src="captchaUrl" @click="updateCaptcha">
                </el-form-item>
                <el-checkbox v-model="loginForm.rememberMe">记住我</el-checkbox>
                <el-button @click="submitLogin" type="primary" style="width: 100%" >登录</el-button>
            </el-form>
        </div>
    </div>
</template>

<script>
    import {getRequest} from "../utils/axiosUtils";
    import {initMenu} from "../utils/menus";
    import router from "../router";
    import store from "../store";

    export default {
        name: "Login",
        data() {
            return {
                back:'../components/background.jpg',
                captchaUrl: '/captcha?time=' + new Date(),
                // captchaUrl:'http://localhost:8081/captcha?time='+new Date(),
                loginForm: {
                    username: 'admin',
                    password: '888888',
                    code: "",
                    rememberMe: true
                },
                rules: {
                    username: [
                        {
                            required: true,
                            message: '请输入用户名',
                            trigger: 'blur'
                        }
                    ],
                    password: [
                        {
                            required: true,
                            message: '请输入密码',
                            trigger: 'blur'
                        }
                    ],
                    code: [
                        {
                            required: true,
                            message: '请输入验证码',
                            trigger: 'blur'
                        }
                    ]
                }
            }
        },
        created() {
            let that = this
            document.onkeydown = function (e) {
                e = window.event || e
                // 验证在登录界面和按得键是回车键enter
                if (that.$route.path === '/' && (e.code === 'Enter' || e.code === 'enter')) {
                    // 登录事件
                    that.submitLogin()
                }
            }
        },
        change:function (){
            let username = this.Form.username
            let form= JSON.parse(localStorage.getItem("form"))
            if (username === form.username){
                this.Form.password = form.password
            }
        },
        methods: {
            updateCaptcha() {
                this.captchaUrl = '/captcha?time=' + new Date();
            },
            submitLogin() {
                this.$refs.loginForm.validate(
                    valid => {
                        if (valid) {
                            /**
                             * 发送登录请求
                             */
                            this.postRequest('/pms/user/login', this.loginForm).then(resp => {
                                if (resp.code == 0) {
                                    /**
                                     * 响应存在
                                     */
                                    window.sessionStorage.setItem('user', JSON.stringify(resp.data.userInfo));
                                    window.sessionStorage.setItem('empInfo', JSON.stringify(resp.data.empInfo));
                                    this.$router.replace('/home');
                                    initMenu(router, store);
                                    if (this.Form.remenber) {
                                        let obj = JSON.stringify(form)
                                        //如果选择记住密码，将账号和密码存缓存
                                        localStorage.setItem("form", obj)
                                    }
                                }
                                else if(resp.code == -999){
                                    this.updateCaptcha();
                                }
                            })
                        } else {
                            this.$message.error('请输入所需信息')
                            return false;
                        }
                    }
                )
            }
        }
    }
</script>

<style>
    .back{

    }
    .loginContainer {
        border-radius: 15px;
        background-clip: padding-box;
        margin: 36px auto;
        width: 350px;
        padding: 15px 35px 15px 35px;
        background: #fff;
        box-shadow: 0 0 25px #cac6c6;
    }

    .loginTitle {
        margin: 0px auto 40px auto;

    }

    .el-form-item__content {
        display: flex;
        align-items: center;
    }
    #login{
        text-align: center;

    }
</style>
