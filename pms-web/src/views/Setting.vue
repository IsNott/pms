<template>
    <div>
        <h3>修改密码</h3>

        <el-form :rules="rule" ref="form" :model="form" style="width: 30%;">
            <el-form-item prop="pwd" label="原密码">
                <el-input type="password" v-model="form.pwd" placeholder="请输入原密码"></el-input>
            </el-form-item>
            <el-form-item prop="npwd" label="新密码">
                <el-input type="password" v-model="form.npwd" placeholder="请输入新密码"></el-input>
            </el-form-item>
            <el-form-item prop="code" label="验证码">
                <el-input type="text" v-model="form.code" placeholder="点击图片更换验证码"></el-input>
            </el-form-item>
            <el-form-item prop="code">
                <img :src="captcha" @click="updateCaptcha">
            </el-form-item>
        </el-form>
        <el-button @click="submitPwd('form')" type="primary">修改</el-button>
        <el-button @click="back">返回</el-button>
    </div>
</template>

<script>

    export default {
        name: 'changePwd',
        data() {
            return {
                form: {
                    pwd: '',
                    cap: '',
                    code: ''
                },
                captcha: '/captcha?time=' + new Date(),
                rule: {
                    pwd: [{
                        required: true,
                        message: '请输入原密码',
                        trigger: 'blur'
                    }, {
                        min: 6,
                        max: 12,
                        message: '长度在 6 到 12 个字符', trigger: 'blur'
                    }],
                    npwd: [{
                        required: true,
                        message: '请输入新密码',
                        trigger: 'blur'
                    }, {
                        min: 6,
                        max: 12,
                        message: '长度在 6 到 12 个字符', trigger: 'blur'
                    }],
                    code: [{
                        required: true,
                        message: '请输入验证码',
                        trigger: 'blur'
                    }]
                }
            }
        },
        methods: {
            submitPwd(formName) {
                this.$refs[formName].validate(valid => {
                    if (valid) {
                        var pwdParam = this.form
                        this.postRequest('/pms/user/changePwd', pwdParam).then(res => {
                            if (res.code == 0) {
                                this.$message.success("修改成功");
                            }
                        })
                    }
                })

            },
            back() {
                this.$router.replace("/")
            },
            updateCaptcha() {
                this.captchaUrl = '/captcha?time=' + new Date();
            }
        },
        created() {
        }
    }

</script>

<style>
</style>