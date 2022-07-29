<template>
    <div>
        <div style="padding-bottom: 10px">
            <el-input placeholder="请输入员工名进行搜索，可以直接回车搜索..." prefix-icon="el-icon-search"
                      clearable
                      @clear="initEmp"
                      style="width: 350px;margin-right: 10px" v-model="keyword"
                      @keydown.enter.native="initEmp"></el-input>
            <el-button icon="el-icon-search" type="primary" @click="initEmp">
                搜索
            </el-button>
        </div>
        <div id="table" style="border: #99a9bf;">
            <el-table
                    :data="tableData"
                    style="width: 100%;font-size: initial"
                    max-height="500"
                    :v-loading="loading"
                    element-loading-text="正在加载..."
            >
                <el-table-column
                        fixed
                        prop="id"
                        label="工号"
                        width="100">
                </el-table-column>
                <el-table-column
                        prop="name"
                        label="姓名"
                        width="120">
                </el-table-column>
                <el-table-column
                        prop="dept"
                        label="部门"
                        width="120">
                </el-table-column>
                <el-table-column
                        prop="tel"
                        label="电话"
                        width="120">
                </el-table-column>
                <el-table-column
                        prop="statu"
                        label="在职状态"
                        width="120">
                </el-table-column>
                <el-table-column
                        prop="gender"
                        label="性别"
                        width="120">
                </el-table-column>
                <el-table-column
                        prop="birthday"
                        label="生日"
                        width="180">
                </el-table-column>
                <el-table-column
                        prop="entryTime"
                        label="入职时间"
                        width="180">
                </el-table-column>
                <el-table-column
                        fixed="right"
                        label="操作"
                >
                    <template slot-scope="scope">
                        <el-button
                                @click="removeEmp(scope.row)"
                                type="text"
                                size="small">
                            移除
                        </el-button>
                        <el-button
                                @click="openEdit(scope.row)"
                                type="text"
                                size="small">
                            编辑
                        </el-button>
                        <el-button
                                @click="review(scope.row)"
                                type="text"
                                size="small">
                            查看高级资料
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
            <el-dialog title="编辑" :visible.sync="dialogVisible" class="formEdit">
                <el-form :model="formconfig.editBasic" :rules="rules" ref="Basic">
                    <el-form-item label="姓名" prop="name" :label-width="formLabelWidth">
                        <el-input v-model="formconfig.editBasic.name" autocomplete="off"></el-input>
                    </el-form-item>
                    <el-form-item label="部门" :label-width="formLabelWidth">
                        <el-select v-model="formconfig.editBasic.deptno" clearable placeholder="请选择">
                            <el-option
                                    v-for="item in deptList"
                                    :key="item.id"
                                    :label="item.deptName"
                                    :value="item.id">
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="tel" prop="tel" :label-width="formLabelWidth">
                        <el-input v-model="formconfig.editBasic.tel" :label-width="formLabelWidth"></el-input>
                    </el-form-item>
                    <el-form-item label="出勤状态" :label-width="formLabelWidth">
                        <el-select v-model="formconfig.editBasic.statu" autocomplete="off">
                            <el-option
                                    v-for="item in statu"
                                    :key="item.id"
                                    :label="item.name"
                                    :value="item.value">
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="性别" :label-width="formLabelWidth">
                        <el-select v-model="formconfig.editBasic.gender">
                            <el-option
                                    v-for="item in gender"
                                    :key="item.id"
                                    :label="item.name"
                                    :value="item.value">
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item prop="birthday" :label-width="formLabelWidth">
                        <span class="demonstration">出生日期</span>
                        <el-date-picker
                                v-model="formconfig.editBasic.birthday"
                                type="date"
                                placeholder="选择日期">
                        </el-date-picker>
                    </el-form-item>
                    <el-form-item prop="entryTime" :label-width="formLabelWidth">
                        <span class="demonstration">入职时间</span>
                        <el-date-picker
                                v-model="formconfig.editBasic.entryTime"
                                type="date"
                                placeholder="选择日期">
                        </el-date-picker>
                    </el-form-item>
                </el-form>
                <div slot="footer" class="dialog-footer">
                    <el-button @click="cansel">取 消</el-button>
                    <el-button type="primary" @click="updataBasicInfo('Basic')">确 定</el-button>
                </div>
            </el-dialog>
        </div>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                tableData: [],
                total: 0,
                page: 1,
                size: 10,
                loading: false,
                dept: [],
                keyword: '',
                deptMap: [],
                dialogVisible: false,
                dialogFormVisible: false,
                formLabelWidth: '120px',
                rules: {
                    name: [{required: true, message: '请输入名称', trigger: 'blur'},
                        {min: 2, max: 10, message: '长度在 2 到 10 个字符', trigger: 'blur'}],
                    tel: [
                        {required: true, message: '请输入电话号码', trigger: 'blur'},
                        {
                            pattern: /^((1[3,5,8,7,9][0-9])|(14[5,7])|(17[0,6,7,8])|(19[1,7]))\d{8}$/,
                            message: '长度在 13 个字符',
                            trigger: 'blur'
                        }
                    ],
                    birthday: [{required: true, message: '请输入日期', trigger: 'blur'},],
                    entryTime: [{required: true, message: '请输入日期', trigger: 'blur'},],
                },
                formconfig: {
                    editBasic: {
                        id: 1,
                        name: '',
                        tel: '',
                        statu: '',
                        gender: '',
                        birthday: '',
                        entryTime: '',
                        deptno: 1,
                    }
                },
                gender: [
                    {
                        id: 1,
                        value: '1',
                        name: '男'
                    },
                    {
                        id: 2,
                        value: '0',
                        name: '女'
                    }
                ],
                statu: [
                    {
                        id: 1,
                        value: 0,
                        name: '出勤'
                    },
                    {
                        id: 2,
                        value: 1,
                        name: '请假'
                    }
                ],
                deptList: []
            }
        },
        created() {
            this.queryDeptMap();
            this.initEmp();
            this.queryDeptList();
        },

        methods: {
            cansel() {
                this.queryDeptMap();
                this.initEmp();
                this.queryDeptList();
                this.dialogVisible = false;
            },
            queryDeptList() {
                this.postRequest("/pms/department/deptList").then(res => {
                    if (res.code == 0) {
                        this.deptList = res.data;
                        // window.sessionStorage.setItem("dept", JSON.parse(this.deptList));
                    }
                })
            },
            updataBasicInfo(formName) {
                this.$refs[formName].validate(valid => {
                    if (valid) {
                        var info = this.formconfig.editBasic
                        console.error(info)
                        info.birthday = typeof info.birthday === 'string' ? info.birthday : info.birthday.getFullYear() + '-' + (info.birthday.getMonth() + 1) + '-' + info.birthday.getDate() + ' ' + info.birthday.getHours() + ':' + info.birthday.getMinutes() + ':' + info.birthday.getSeconds();
                        info.entryTime = typeof info.entryTime === 'string' ? info.entryTime : info.entryTime.getFullYear() + '-' + (info.entryTime.getMonth() + 1) + '-' + info.entryTime.getDate() + ' ' + info.entryTime.getHours() + ':' + info.entryTime.getMinutes() + ':' + info.entryTime.getSeconds();
                        this.postRequest("/emp/updateBasic", info).then(res => {
                            if (res.code == 0) {
                                this.$message.success("更新成功");
                                this.initEmp();
                            } else {
                                this.$message.error("更新失败");
                            }
                        })
                        this.dialogVisible = false;
                    }
                })

            },
            removeEmp(row) {
                this.$confirm('此操作将移除该员工, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning',
                }).then(() => {
                    var id = row.id
                    this.postRequest("/emp/remove", {id}).then(res => {
                        if (res.code == 0) {
                            this.$message.success("删除成功")
                        }
                    })
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });
            },
            openEdit(row) {
                this.dialogVisible = true;
                this.formconfig.editBasic = row;
            },
            review(row) {
                this.$router.push("/emp/detail/" + row.id);
            },
            initEmp() {
                var page = this.page ? this.page : 1;
                var size = this.size ? this.size : 10;
                var name = this.keyword ? this.keyword : '';
                this.loading = true;
                console.error(size)
                this.postRequest("/emp/queryEmp", {page, size, name}).then(res => {
                    this.loading = false;
                    if (res.code == 0) {
                        var data = res.data.records ? res.data.records : []
                        this.total = res.data.total
                        var list = [];
                        data.forEach(item => {
                            var emp = {
                                id: 1,
                                dept: '',
                                manager: '',
                                name: '',
                                tel: '',
                                statu: '',
                                entryTime: '',
                                delTime: '',
                                position: '',
                                gender: '',
                                birthday: '',
                                deptno: 1,
                            };
                            emp.deptno = item.deptno;
                            emp.id = item.id;
                            emp.name = item.name;
                            emp.tel = item.tel;
                            emp.entryTime = item.entryTime;
                            emp.position = item.position;
                            emp.birthday = item.birthday;
                            emp.gender = item.gender == '1' ? '男' : "女"
                            emp.statu = item.statu == 0 ? '出勤' : '请假'
                            //console.error(item)
                            emp.dept = this.deptMap.get(item.deptno);
                            list.push(emp)
                        });
                        this.tableData = list;
                    }
                });
            },
            pageCurrent(currentPage) {
                this.page = currentPage;
                this.initEmp();
            },
            sizeChange(currentSize) {
                this.size = currentSize;
                this.initEmp();
            },
            queryDeptMap() {
                this.postRequest("/pms/department/deptList").then(res => {
                    var data = res.data;
                    var map = new Map();
                    data.forEach(item => {
                        map.set(item.id, item.deptName)
                    })
                    this.deptMap = map;
                    console.log("dept", this.deptMap)
                })
            }
        }
    }
</script>

<style lang="less" scoped>
    .input {
        width: 200px;
    }

    .formEdit {

    }

    .page {
        margin-top: 20px;
        display: flex;
        justify-content: flex-end;
    }
</style>