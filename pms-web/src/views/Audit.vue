<template>
    <div>
    <h3>审核处理</h3>
    <el-table :data="tableData" style="font-size: initial;" width="100%" >
        <el-table-column width="180px"
                         prop="auditType"
                         label="类型">
        </el-table-column>
        <el-table-column width="100px" prop="empNo" label="工号"></el-table-column>
        <el-table-column width="180px" prop="reason" label="申请理由"></el-table-column>
        <el-table-column width="100px" prop="auditDuration" label="申请时长"></el-table-column>
        <el-table-column width="80px" prop="option" label="选项"></el-table-column>
        <el-table-column width="180px" prop="stattime" label="开始时间"></el-table-column>
        <el-table-column width="180px" prop="endtime" label="结束时间"></el-table-column>
        <el-table-column label="操作">
        <template slot-scope="scope">
            <el-button @click="agree(scope.row)" type="primary">同意</el-button>
            <el-button @click="disagree(scope.row)" type="info">不同意</el-button>
        </template>
        </el-table-column>
    </el-table>
        <div style="margin-top: 20px">
        <el-button @click='back' type="primary" >返回</el-button>
        </div>
    </div>
    </template>
<script>
    export default {
        name: 'audit',
        data() {
            return {
                tableData:[],
                user: JSON.parse(window.sessionStorage.getItem("user")),
            }
        }, methods: {
            initAudit(){
                this.postRequest('/pms/audit/myAudit').then(res=>{
                        res.data.forEach(item=>{
                        item.auditOption == 0 ? item.option='小时':item.option='天';
                        if(item.type == 0){
                            item.auditType = '请假'
                        }
                        if(item.type == 1){
                            item.auditType = '离职'
                        }
                        if(item.type == 2){
                            item.auditType = '加班';
                        }
                    })
                    this.tableData = res.data;
                    console.log(this.tableData);
                })
            },
            back(){
                this.$router.go(-1);
            }
        }, created() {
            this.initAudit();
        }
    }
</script>
<style></style>