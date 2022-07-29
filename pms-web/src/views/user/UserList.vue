<template>
    <div>
        <div>
            <div style="padding-bottom: 10px">
                <el-input placeholder="请输入用户名进行搜索，可以直接回车搜索..." prefix-icon="el-icon-search"
                          clearable
                          @clear="initUser"
                          style="width: 350px;margin-right: 10px" v-model="keyword"
                          @keydown.enter.native="initUser"></el-input>
                <el-button icon="el-icon-search" type="primary" @click="initUser">
                    搜索
                </el-button>
            </div>
            <div id="table" style="border: #99a9bf">
                <el-table
                        :data="tableData"
                        style="width: 100%;font-size: initial"
                        max-height="450"
                        :v-loading="loading"
                        element-loading-text="正在加载..."
                >

                    <el-table-column
                            prop="username"
                            label="用户名"
                            width="120">
                    </el-table-column>
                    <el-table-column
                            prop="password"
                            label="密码"
                            width="120">
                    </el-table-column>
                    <el-table-column
                            prop="statu"
                            label="状态"
                            width="120">
                    </el-table-column>
                    <el-table-column
                            prop="nickName"
                            label="昵称"
                            width="120">
                    </el-table-column>
                    <el-table-column
                            prop="token"
                            label="签名"
                            width="120">
                    </el-table-column>
                    <el-table-column
                            prop="email"
                            label="邮箱"
                            width="120">
                    </el-table-column>
                    <el-table-column
                            prop="createTime"
                            label="创建时间"
                            width="180">
                    </el-table-column>
                    <el-table-column
                            prop="empNo"
                            label="工号"
                            width="180">
                    </el-table-column>
                    <el-table-column
                            prop="empName"
                            label="员工姓名"
                            width="180">
                    </el-table-column>
                    <el-table-column
                            prop="url"
                            label="头像地址"
                            width="180">
                    </el-table-column>
                    <el-table-column
                            prop="roleName"
                            label="角色"
                            width="180">
                    </el-table-column>
                    <el-table-column
                            fixed="right"
                            label="操作"
                            width="120"
                    >
                        <template slot-scope="scope">
                            <el-button
                                    @click="removeUser(scope.row)"
                                    type="text"
                                    size="mini">
                                停用
                            </el-button>
                            <el-button
                                    @click="openEdit(scope.row)"
                                    type="text"
                                    size="mini">
                                编辑
                            </el-button>
                        </template>
                    </el-table-column>
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

                <!-- Form -->
                <el-dialog title="编辑信息" :visible.sync="dialogFormVisible">
                    <el-form :model="form" >
                        <el-form-item label="用户名" :label-width="formLabelWidth">
                            <el-input v-model="form.username" autocomplete="off"></el-input>
                        </el-form-item>
                        <el-form-item label="密码" :label-width="formLabelWidth">
                            <el-input v-model="form.password" autocomplete="off"></el-input>
                        </el-form-item>
                        <el-form-item label="昵称" :label-width="formLabelWidth">
                            <el-input v-model="form.nickName" autocomplete="off"></el-input>
                        </el-form-item>
                        <el-form-item label="邮箱" :label-width="formLabelWidth">
                            <el-input v-model="form.email" autocomplete="off"></el-input>
                        </el-form-item>
                        <el-form-item label="签名" :label-width="formLabelWidth">
                            <el-input v-model="form.token" autocomplete="off"></el-input>
                        </el-form-item>
                        <el-form-item label="头像地址" :label-width="formLabelWidth">
                            <el-input v-model="form.url" autocomplete="off"></el-input>
                        </el-form-item>
                    </el-form>
                    <div slot="footer" class="dialog-footer">
                        <el-button @click="cancel">取 消</el-button>
                        <el-button type="primary" @click="sumbit">确 定</el-button>
                    </div>
                </el-dialog>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        name: "UserList",
        data() {
            return {
                tableData: [],
                total: 0,
                page: 1,
                size: 10,
                loading: false,
                keyword: '',
                dialogFormVisible: false,
                formLabelWidth: '120px',
                form: {
                    username: '',
                    password: '',
                    nickName: '',
                    token: '',
                    email: '',
                    url: '',
                    status: '',
                }
            }
        },
        created() {
            this.initUser();
        },
        methods: {
            openEdit(row) {
                this.form = row;
                this.dialogFormVisible = true;
            },
            cancel(){
                this.dialogFormVisible = false;
                this.initUser();
            },
            sumbit(){
                //console.error(this.form,"form");
                var update = this.form;
                this.postRequest('/pms/user/user/userupdate',update).then(res=>{
                    if(res.code==0){
                        this.$message.success("修改成功");
                    }
                })
                this.dialogFormVisible = false;
                this.initUser();
            },
            removeUser(row) {
                var ids = row.id
                this.postRequest('/pms/user/remove', {ids}).then(res => {
                    if (res.code == 0) {
                        this.$message.success("成功")
                        this.initUser();
                    }
                })
            },
            pageCurrent(currentPage) {
                this.page = currentPage;
                this.initUser();
            },
            sizeChange(currentSize) {
                this.size = currentSize;
                this.initUser();
            },
            initUser() {
                var page = this.page ? this.page : 1;
                var size = this.size ? this.size : 10;
                var keyword = this.keyword ? this.keyword : '';
                this.postRequest('/pms/user/queryUserList', {page, size, keyword}).then(res => {
                    if (res.code == 0) {
                        this.tableData = res.data.records;
                        this.total = res.data.total;
                        this.tableData.forEach(item => {
                            item.statu = item.deflag == 1 ? '停用' : '启用';
                        })
                        console.error(this.tableData)
                    }
                })
            }
        }
    }

</script>

<style scoped>

</style>