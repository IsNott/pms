<template>
    <div>
        <h5>{{emp.name}}个人考勤记录</h5>
        <el-table
        :data="tableData"
        width="100%"
        style="font-size: initial">
            <el-table-column label="编号" prop="id" ></el-table-column>
            <el-table-column label="上班打卡" prop="workAtten"></el-table-column>
            <el-table-column label="下班打卡" prop="leaveAtten"></el-table-column>
        </el-table>

        <div id="pagination">
            <el-pagination
                    background
                    layout="sizes, prev, pager, next, jumper, ->, total, slot"
                    :total=total
                    @size-change="sizeChange"
                    @current-change="pageCurrent"
                    class="page">
            </el-pagination>
        </div>
    </div>
</template>

<script>
    export default {
        data(){
            return{
                page:1,
                size:10,
                total:0,
                keyword: '',
                tableData:[],
                emp:JSON.parse(window.sessionStorage.getItem("empInfo"))
            }
        },created() {
            this.initAttData();
        },methods:{
            initAttData(){
                var page = this.page ? this.page : 1;
                var rows = this.size ? this.size : 10;
                var keyword = this.keyword ? this.keyword : '';
                this.postRequest('/pms/attend/list').then(res=>{
                    if(res.code == 0){
                        this.tableData = res.data.records;
                        this.total = res.data.total;
                        console.error(res.data);
                    }
                })
            },
            pageCurrent(currentPage) {
                this.page = currentPage;
                this.initMsg();
            },
            sizeChange(currentSize) {
                this.size = currentSize;
                this.initMsg();
            }
        }
    }
</script>

<style></style>