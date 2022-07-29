<template>
    <div>
        {{name}}(为减少服务器压力，仅显示最近十条未读信息，预览全部信息请到信息中心)
        <el-table
                ref="multipleTable"
                :data="msgList"
                tooltip-effect="dark"
                style="width: 100%;font-size: initial"
                @selection-change="handleSelectionChange">
            <el-table-column
                    type="selection"
                    width="55">
            </el-table-column>

            <el-table-column
                    prop="type"
                    label="类型"
                    width="120">
            </el-table-column>
            <el-table-column
                    label="日期"
                    width="120">
                <template slot-scope="scope">{{ scope.row.sendtime }}</template>
            </el-table-column>
            <el-table-column
                    prop="sender"
                    label="姓名"
                    width="120">
            </el-table-column>
            <el-table-column
                    prop="text"
                    label="内容"
                    show-overflow-tooltip>
            </el-table-column>
            <el-table-column
                    fixed="right"
                    label="操作"
                    width="120">
                <template slot-scope="scope">
                    <el-button
                            @click="openMsg(scope.row)"
                            type="text"
                            size="small">
                        查看
                    </el-button>
                    <el-button
                            @click="handleRemove([scope.row])"
                            type="text"
                            size="small">
                        删除
                    </el-button>
                </template>
            </el-table-column>
        </el-table>
        <div style="margin-top: 20px">
            <el-button @click="handleRead([msgList])">已读</el-button>
            <el-button @click="toggleSelection()">取消选择</el-button>
        </div>
    </div>
</template>
<script>
    export default {
        name: 'UserMsg',
        data() {
            return {
                id: 1,
                type: 1,
                msgList: [],
                name:this.$route.name
            }
        },

        created() {
            this.getTypeAndId();
            this.queryMsg();
        },
        watch: {
            '$route'(to, from) { //监听路由是否变化
                if(to.fullPath !== from.fullPath){
                    this.getTypeAndId();
                    this.queryMsg();//重新加载数据
                }
            }

        },
        methods: {
            openMsg(row) {
                this.$alert(row.text, '信息内容', {
                    confirmButtonText: '确定',
                });
                var ids = row.id
                this.postRequest("/pms/msg/readMsg", {ids}).then(res => {
                    if (res) {
                        this.queryMsg();
                    }
                })
            },
            toggleSelection(rows) {
                if (rows) {
                    rows.forEach(row => {
                        this.$refs.multipleTable.toggleRowSelection(row);
                    });
                } else {
                    this.$refs.multipleTable.clearSelection();
                }
            },
            handleRemove(rows) {
                var ids = []
                rows.forEach(item => {
                    ids.push(item.id);
                })
                this.postRequest('/pms/msg/removeMsg', {ids}).then(res => {
                    if (res.code == 0) {
                        this.$message.success("成功")
                        this.queryMsg();
                    }
                })
            },
            handleRead(rows) {
                var ids = []
                rows.forEach(item => {
                    ids.push(item.id);
                })
                this.postRequest('/pms/msg/readMsg', {ids}).then(res => {
                    if (res.code == 0) {
                        this.$message.success("成功")
                        this.queryMsg();
                    }
                })
            },
            handleSelectionChange(val) {
                this.multipleSelection = val;
            },
            getTypeAndId() {
                this.type = this.$route.params.type;
            },
            queryMsg() {
                var type = this.type
                this.postRequest("/pms/msg/unreadMsg", {type}).then(res => {
                    if (res.code == 0) {
                        //console.error("msg", res.data)
                        this.msgList = res.data.records;
                        this.total = res.data.total
                    }
                })
            }
        }
    }
</script>
<style></style>