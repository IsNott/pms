<template>
    <div>
        <h3>角色授权</h3>
        <el-table
                :data="tableData"
                style="width: 100%;font-size: initial">
            <el-table-column
                    prop="id"
                    label="角色id"
                    width="180">
            </el-table-column>
            <el-table-column
                    prop="roleName"
                    label="角色"
                    width="180">
            </el-table-column>
            <el-table-column
                    prop="remark"
                    label="备注"
                    width="180">
            </el-table-column>
            <el-table-column
                    prop="statu"
                    label="状态">
            </el-table-column>
            <el-table-column label="操作">
                <template slot-scope="scope">
                    <el-button type="primary" @click="handleClick(scope.row)">授权</el-button>
                </template>
            </el-table-column>
        </el-table>

        <el-dialog title="权限" :visible.sync="dialogTableVisible">
            <el-table :data="perData" style="font-size: initial">
                <el-table-column property="id" label="权限id" width="150"></el-table-column>
                <el-table-column property="name" v-model="selectList" label="名称" width="200"></el-table-column>
                <el-table-column label="操作" width="250px" type="index">
                    <template slot-scope="scope">
                        <el-button @click="able(scope.row)" type="primary">授权</el-button>
                        <el-button @click="unable(scope.row)" type="info">停用</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </el-dialog>

    </div>
</template>
<script>
    export default {
        name: "sysAuth",
        data() {
            return {
                selectList: [],
                tableData: [],
                perData: [],
                dialogTableVisible: false,
                checkData: true,
                roleId :'',
                type:'',
            }
        },
        methods: {
            initRole() {
                this.postRequest('/pms/role/list').then(res => {
                    if (res.code == 0) {
                        var data = res.data;
                        data.forEach(item => {
                            item.delflag === 0 ? item.statu = '启用' : item.statu = '停用';
                        })
                        this.tableData = data;
                    }
                })
            },
            handleClick(row) {
                this.dialogTableVisible = true;
                var roleId = row.id;
                this.roleId = row.id;
                this.postRequest('/pms/role-permission/list', {roleId}).then(res => {
                    if (res.code == 0) {
                        this.selectList = res.data;
                    }

                })
            },
            unable(row){
                var type = 1;
                var id = row.id;
                this.changeAuth(type,id);
            },
            able(row){
                var type = 0;
                var id = row.id;
                this.changeAuth(type,id);
            },
            changeAuth(type,id){
                var delFlag = type;
                var roleId = this.roleId;
                this.postRequest('/pms/menu/auth',{delFlag,id,roleId}).then(res=>{
                    if(res.code ==0){
                        this.$message.success("成功");
                    }
                })
            }
        },
        created() {
            this.postRequest('/pms/menu').then(res => {
                if (res.code == 0) {
                    this.perData = res.data;
                }
            })
            this.initRole();
        }
    }
</script>
<style></style>