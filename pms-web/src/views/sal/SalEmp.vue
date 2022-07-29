<template xmlns="http://www.w3.org/1999/html">
    <div id="salDes" style="font-size: initial">
        <el-descriptions title="员工个人薪资"  direction="vertical" :column="4" border>
            <el-descriptions-item label="姓名" >{{emp.name}}</el-descriptions-item>
            <el-descriptions-item label="基本薪资">{{sal.salary}}(元)</el-descriptions-item>
            <el-descriptions-item label="补贴">{{sal.subsidy}}(元)</el-descriptions-item>
            <el-descriptions-item label="税率" :span="2">{{sal.rate}}</el-descriptions-item>
            <el-descriptions-item label="加班时薪">{{sal.otSal}}(元/小时)</el-descriptions-item>
            <el-descriptions-item label="加班时长">{{sal.overTime}}(小时)</el-descriptions-item>
            <el-descriptions-item label="税前总收入">{{sal.total}}(元)</el-descriptions-item>
            <el-descriptions-item label="税后总收入">{{sal.totalAftax}}(元)</el-descriptions-item>
            <el-descriptions-item label="税率说明">小于5000元/月不扣税，大于5000元/月小于8000元/月扣除3%税费，大于8000元/月扣除5%税费</el-descriptions-item>
            <el-descriptions-item label="税费计算" style="text-align: right">（基础工资+（加班时长*加班时薪）+补贴）* 税率</el-descriptions-item>
        </el-descriptions>
        <div style="margin-left:15px;margin-top: 20px">
        <a href="/pms/sal/excel" style="margin-right: 30px">导出明细</a>
        <el-button @click="back">返回</el-button>
        </div>
    </div>
</template>
<script>
    export default {
        name: 'SalEmp',
        data() {
            return {
                sal:[],
                emp:JSON.parse(window.sessionStorage.getItem("empInfo"))
            }
        },
        created(){
            this.empSal();
        },
        methods:{
            empSal(){
                this.postRequest("/pms/sal/querySalByEmpNo").then(res=>{
                    if(res.code == 0){
                        this.sal = res.data;
                    }
                })
            },
            back(){
                this.$router.go(-1)
            },

        }
    }
</script>
<style>
    #salDes{
        font-size: 20px;
    }
</style>