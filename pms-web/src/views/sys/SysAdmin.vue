<template>
    <div>
        <h3>管理员列表</h3>
        <el-table
                :data="tableData"
                border
            style="width: 100%;font-size: initial">
            <el-table-column
                    prop="roleId"
                    label="权限id"
                    width="180">
            </el-table-column>
            <el-table-column
                    prop="username"
                    label="用户名"
                    width="180">
            </el-table-column>
            <el-table-column
                    prop="email"
                    label="邮箱"
                    width="180">
            </el-table-column>
            <el-table-column
                    prop="statu"
                    label="状态">
            </el-table-column>
            <el-table-column
                    fixed="right"
                    label="操作">
                <template slot-scope="scope">
                    <el-button type="danger" @click="delAdmin(scope.row)">移除</el-button>
                </template>
            </el-table-column>
        </el-table>

    </div>
</template>
<script>
    export default {
        name: 'AdminList',
        data() {
            return {
                tableData: [],
                roleList:[],

            }
        },
        created() {
            this.initAdmin();
        },
        methods: {
            initAdmin() {
                this.postRequest('/pms/user/adminList').then(res => {
                    if (res.code == 0) {
                        console.log("admins", res.data);
                        res.data.forEach(item=>{
                            item.delflag === 0? item.statu='启用':'停用';
                        })
                        this.tableData = res.data;
                    }
                })
            },
            delAdmin(row){
                var id = row.id
                this.postRequest('/pms/user/removeAdmin',{id}).then(res=>{
                    if(res.code == 0){
                        this.$message.success("成功")
                        this.initAdmin();
                    }
                })
            },
            authAdmin(row){
                console.error("asdasd",row);
            }

        }
    }
</script>

<style scoped></style>