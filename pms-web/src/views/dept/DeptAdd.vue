<template style="align-items: center">
    <div>
        <h3 style="margin-left: 30px">新建部门</h3>

        <el-form :rules="deptFormRule" ref="deptForm" :model="form" style="margin-left: 30px">
            <el-form-item label="部门名字" prop="deptName" :label-width="formLabelWidth">
                <el-input v-model="form.deptName" placeholde="请输入部门名字" style="width: 200px"></el-input>
            </el-form-item>
            <el-form-item label="部门地址" prop="deptLocal" :label-width="formLabelWidth">
                <el-input v-model="form.deptLocal" placeholde="请输入部门名字" style="width: 200px"></el-input>
            </el-form-item>
            <el-form-item label="部门经理" prop="value" :label-width="formLabelWidth">
                <el-select v-model="form.manager" style="width: 200px">
                    <el-option
                            v-for="item in emps"
                            :key="item.id"
                            :label="item.name"
                            :value="item.id">
                    </el-option>
                </el-select>
            </el-form-item>

        </el-form>
        <div style="margin-left: 130px">
            <el-button @click="addDept('deptForm')" type="primary">确定</el-button>
            <el-button @click="back">取消</el-button>
        </div>
    </div>
</template>
<script>
    export default {
        name: 'deptadd',
        data() {
            return {
                value: '',
                emps: [],
                form: {
                    deptName: '',
                    deptLocal: '',
                    manager:'',
                },
                formLabelWidth: '100px',
                deptFormRule: {
                    deptName: [{required: true, message: '请输入部门名称', trigger: 'blur'},
                        {min: 2, max: 10, message: '长度在 2 到 10 个字符', trigger: 'blur'}],
                    deptLocal: [{required: true, message: '请输入部门地址', trigger: 'blur'},
                        {min: 6, max: 18, message: '长度在 6 到 18 个字符', trigger: 'blur'}],
                    manager: [{required: true, message: '请选择部门经理', trigger: 'blur'}],

                }
            }
        },
        methods: {
            initEmps() {
                this.postRequest('/emp/initNomEmp').then(res => {
                    if (res.code == 0) {
                        this.emps = res.data;
                    }
                })

            },
            back() {
                this.$router.replace("/")
            },
            addDept(formName) {
                this.$refs[formName].validate(valid=>{
                    if(valid){
                        this.postRequest('/pms/department/addDept',this.form).then(res=>{
                            if(res.code==0){
                                this.$message.success("新增成功");
                                this.form = [];
                            }else {
                                this.$message.error("新增失败");
                            }
                        })
                    }
                })

            }
        },
        created() {
            this.initEmps();
        }
    }
</script>
<style></style>