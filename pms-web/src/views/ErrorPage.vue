<template>
    <div>
        <el-result title="404 not found" subTitle="抱歉，服务器出错" class="title">
            <template slot="icon">
                <el-image :src="url" class="img" :fit="fit"></el-image>
            </template>
            <template slot="extra">
                <el-button type="primary" size="medium" @click="backToIndex">返回首页</el-button>
            </template>
        </el-result>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                fit: 'contain',
                url: "https://pms-userinfo.oss-cn-guangzhou.aliyuncs.com/errorImg.jpeg"
            };
        },
        methods: {
            backToIndex() {
                this.$router.replace("/home");
            }
        },
        computed: {
            routes() {
                return this.$store.state.routes;
            }
        }
    }
</script>
<style>

    .img {
        width: 1500px;
        height: 500px;
    }
</style>