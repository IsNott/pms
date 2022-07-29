<template>
    <div>
        <div>
            <el-descriptions title="员工高级资料" direction="vertical" :column="6" border>
                <el-descriptions-item label="姓名">{{detail.name}}</el-descriptions-item>
                <el-descriptions-item label="手机号">{{detail.tel}}</el-descriptions-item>
                <el-descriptions-item label="公司职位">{{detail.position}}</el-descriptions-item>
                <el-descriptions-item label="年龄">{{detail.ages}}</el-descriptions-item>
                <el-descriptions-item label="国籍">{{detail.nation}}</el-descriptions-item>
                <el-descriptions-item label="民族">{{detail.ethnic}}</el-descriptions-item>
                <el-descriptions-item label="毕业院校">{{detail.school}}</el-descriptions-item>
                <el-descriptions-item label="学历">{{detail.education}}</el-descriptions-item>
                <el-descriptions-item label="籍贯" :span="2">{{detail.hometown}}</el-descriptions-item>
                <el-descriptions-item label="政治面貌">{{detail.political}}</el-descriptions-item>
                <el-descriptions-item label="出生日期">{{detail.birthday}}</el-descriptions-item>
                <el-descriptions-item label="入职时间">{{detail.entryTime}}</el-descriptions-item>
                <el-descriptions-item label="合同到期时间">{{detail.endTime}}</el-descriptions-item>
                <el-descriptions-item label="合同类型">
                    <el-tag size="small">{{detail.contractType}}</el-tag>
                </el-descriptions-item>
                <el-descriptions-item label="合同年限">{{detail.contractYears}}</el-descriptions-item>
                <el-descriptions-item label="身份证号">{{detail.idCard}}</el-descriptions-item>
                <el-descriptions-item label="职称">{{detail.title}}</el-descriptions-item>
                <el-descriptions-item label="联络地址">{{detail.address}}</el-descriptions-item>
            </el-descriptions>

        </div>
        <div class="button">
            <el-button @click="back">返回</el-button>
        </div>

    </div>
</template>

<script>
    export default {
        name: "EmpAdv",
        data() {
            return {
                id:1,
                detail: [],
            }
        },
        created() {
            this.getId();
            this.queryDetail();
        },
        methods: {
            getId() {
                this.id = this.$route.params.id;
            },
            queryDetail() {
                this.postRequest("/emp/detail/" + this.id).then(res => {
                    if (res.code == 0) {
                        this.detail = res.data;
                        this.detail.contract == 0 ? this.detail.contractType = '劳务合同' : this.detail.contractType = '劳动合同';
                    }
                })
            },
            back() {
                this.$router.push('/hrCenter')
            },

        }
    }
</script>

<style scoped>
    .button {
        margin-top: 30px;
        margin-left: 30px;
        position: center;
    }
</style>