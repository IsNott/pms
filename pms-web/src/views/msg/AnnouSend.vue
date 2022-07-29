<template>
    <div>
        <h3>发送公告</h3>
        <el-form>
            <el-form-item label="公告内容" v-model="form">
                <el-input type="textarea" v-model="form.text" style="width: 50%" placeholder="请输入公告内容"></el-input>
            </el-form-item>
            <el-button @click="sendAnnou" style="margin-left: 300px">发送</el-button>
        </el-form>
    </div>
</template>
<script>
    export default {
        data() {
            return {
                form: {
                    text: ''
                }
            }
        },
        methods: {
            sendAnnou() {
                var text = this.form.text;
                this.postRequest('/pms/msg/sendAnnou', {text}).then(res => {
                    if (res.code == 0) {
                        this.$message.success("发送成功");
                    }
                })
            }
        }
    }
</script>
<style></style>