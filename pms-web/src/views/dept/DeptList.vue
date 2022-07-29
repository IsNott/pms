<template>
    <div>
        <div>
            <h3>部门列表</h3>

            <el-table
                    :data="tableData"
                    style="width: 100%;font-size: initial">
                <el-table-column
                        prop="id"
                        label="编号"
                        width="180">
                </el-table-column>
                <el-table-column
                        prop="deptName"
                        label="名称"
                        width="180">
                </el-table-column>
                <el-table-column
                        prop="deptLocal"
                        label="地址"
                >
                </el-table-column>
                <el-table-column
                        prop="managerNo"
                        label="经理工号"
                        width="120px">
                </el-table-column>
                <el-table-column
                        prop="managerName"
                        label="部门经理"
                        width="120px">
                </el-table-column>

                <el-table-column
                        fixed="right"
                        label="操作"
                >
                    <template slot-scope="scope">
                        <el-button @click="handleClick(scope.row)" type="text" size="small">查看员工</el-button>
                        <el-button type="text" @click="open(scope.row)" size="small">编辑</el-button>
                        <el-button type="text" @click="deleteByid(scope.row)" size="small">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </div>

        <el-dialog title="编辑信息" :visible.sync="dialogFormVisible"
        >
            <el-form :model="formData">
                <el-form-item label="部门名称" :label-width="formLabelWidth">
                    <el-input v-model="formData.deptName" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="部门地址" :label-width="formLabelWidth">
                    <el-input v-model="formData.deptLocal" autocomplete="off"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="handleCan">取 消</el-button>
                <el-button type="primary" @click="handleUpdate">确 定</el-button>
            </div>
        </el-dialog>
        <el-dialog title="员工列表" :visible.sync="empDialogVisible">
            <el-table :data="deptEmps">
                <el-table-column prop="name" label="姓名">
                </el-table-column>
                <el-table-column prop="position" label="职位">
                </el-table-column>

                <el-table-column fixed="right" label="操作">
                    <template slot-scope="scope">
                        <el-button type="text" @click="unableEmp(scope.row)" size="small">停用</el-button>
                        <el-button type="text" @click="openInner(scope.row)" size="small">迁出</el-button>
                    </template>
                </el-table-column>

            </el-table>

            <el-dialog
                    width="30%"
                    title="迁出"
                    :visible.sync="innerDiaVisible"
                    append-to-body>
                <el-form>
                    <el-form-item label="部门"></el-form-item>
                    <el-select v-model="temp">
                        <el-option
                                v-for="item in depts"
                                :key="item.id"
                                :label="item.deptName"
                                :value="item.id">
                        </el-option>
                    </el-select>
                    <el-button @click="moveEmp" style="margin-left: 20px" type="primary">确定</el-button>
                    <el-button @click="innerDiaVisible = false" style="margin-left: 20px" type="info">取消</el-button>
                </el-form>
            </el-dialog>

        </el-dialog>
    </div>
</template>

<script>
    export default {
        name: "DeptList",
        data() {
            return {
                temp: [],
                tableData: [],
                formData: [],
                dialogFormVisible: false,
                dialogVisible: false,
                innerDiaVisible: false,
                defaultProps: {
                    children: 'empList',
                    label: 'emp'
                },
                empDialogVisible: false,
                emps: [],
                deptEmps: [],
                state1: '',
                formLabelWidth: "120px",
                depts: [],
                value: '',
                changeEmp: [],
                keyword: '',
            }
        },
        methods: {
            queryAllDept() {
                this.postRequest('/pms/department/deptList').then(res => {
                    this.depts = res.data;
                    window.sessionStorage.setItem("depts", JSON.stringify(res.data));
                })
            },
            moveEmp() {
                console.error(this.changeEmp);
                console.error(this.temp);
                var deptId = this.temp;
                var empNo = this.changeEmp.id;
                this.postRequest('/pms/department/moveEmp', {deptId, empNo}).then(res => {
                    if (res.code == 0) {
                        this.$message.success("修改成功");
                        this.queryDept();
                        this.innerDiaVisible = false;
                        if (this.empDialogVisible) {
                            this.empDialogVisible = false;
                        }
                    } else {
                        this.$message.error(res.msg);
                    }
                })
            },
            handleUpdate() {
                this.dialogFormVisible = false
                // console.error(this.formData)
                var department = this.formData;
                this.postRequest("/pms/department/update",department).then(res => {
                    if (res.code == 0) {
                        this.queryDept();
                        this.$message.success("更新成功");
                    } else {
                        this.$message.error(res.msg);
                    }
                })
            },
            handleClick(row) {
                this.empDialogVisible = true;
                this.deptEmps = row.empList;
            },
            queryDept() {
                this.postRequest("/pms/department/queryDept").then(res => {
                    if (res.code == 0) {
                        //console.error("deptVo", res.data)
                        this.tableData = res.data;
                    }
                })
            },
            deleteByid(row) {
                if (row.empList.length > 0) {
                    this.$message.warning("部门下存在员工，不能删除")
                } else {
                    var id = row.id;
                    this.postRequest('/pms/department/remove', {id}).then(res => {
                        if (res.code == 0) {
                            this.$message.success("成功");
                            this.queryDept()
                        } else {
                            this.$message.error("删除失败");
                        }
                    })
                }
            },
            querySearch(queryString, cb) {
                var empList = this.emps;
                var results = queryString ? empList.filter(this.createFilter(queryString)) : empList;
                // 调用 callback 返回建议列表的数据
                cb(results);
            },
            handleSelect(row) {
                this.receiverid = row.id;
                this.state1 = row.name;
            },
            createFilter(queryString) {
                return (empList) => {
                    return (empList.name.toLowerCase().indexOf(queryString.toLowerCase()) === 0);
                };
            },
            handleCan() {
                this.dialogFormVisible = false;
                this.queryDept();
            },
            open(row) {
                this.formData = row;
                this.dialogFormVisible = true;
            },
            unableEmp(row) {
                // console.error(row)
                var id = row.id
                this.postRequest('/emp/remove', {id}).then(res => {
                    if (res.code == 0) {
                        this.queryDept();

                        this.$message.success("成功")
                    } else {
                        this.$message.error("失败");
                    }
                    this.dialogFormVisible = false;
                })
            },
            openInner(row) {
                this.innerDiaVisible = true;
                //console.error(row)
                this.changeEmp = row;
            },
            initEmp() {
                this.postRequest('/emp/list').then(res => {
                    if (res.code == 0) {
                        this.emps = res.data

                    }
                })
            }
        },
        created() {
            this.queryDept()
            this.initEmp()
            this.queryAllDept()
        }
    }
</script>

<style lang="less" scoped>

</style>