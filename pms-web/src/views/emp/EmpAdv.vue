<template>
    <div>
        <div>
            <el-descriptions title="员工高级资料" direction="vertical" :column="6" border>
                <el-descriptions-item label="姓名">{{detail.name}}</el-descriptions-item>
                <el-descriptions-item label="手机号">{{detail.tel}}</el-descriptions-item>
                <el-descriptions-item label="公司职位">{{detail.position}}</el-descriptions-item>
                <el-descriptions-item label="年龄">{{detail.ages}}</el-descriptions-item>
                <el-descriptions-item label="国籍">{{detail.nation}}</el-descriptions-item>
                <el-descriptions-item label="民族">{{detail.ethnic}}</el-descriptions-item>
                <el-descriptions-item label="毕业院校">{{detail.school}}</el-descriptions-item>
                <el-descriptions-item label="学历">{{detail.education}}</el-descriptions-item>
                <el-descriptions-item label="籍贯" :span="2">{{detail.hometown}}</el-descriptions-item>
                <el-descriptions-item label="政治面貌">{{detail.political}}</el-descriptions-item>
                <el-descriptions-item label="出生日期">{{detail.birthday}}</el-descriptions-item>
                <el-descriptions-item label="入职时间">{{detail.entryTime}}</el-descriptions-item>
                <el-descriptions-item label="合同到期时间">{{detail.endTime}}</el-descriptions-item>
                <el-descriptions-item label="合同类型">
                    <el-tag size="small">{{detail.contractType}}</el-tag>
                </el-descriptions-item>
                <el-descriptions-item label="合同年限">{{detail.contractYears}}</el-descriptions-item>
                <el-descriptions-item label="身份证号">{{detail.idCard}}</el-descriptions-item>
                <el-descriptions-item label="职称">{{detail.title}}</el-descriptions-item>
                <el-descriptions-item label="联络地址">{{detail.address}}</el-descriptions-item>
            </el-descriptions>

        </div>
        <div class="button">
            <el-button type="primary" @click="changeDetail">编辑信息</el-button>
            <el-button @click="back">返回</el-button>
        </div>

        <el-dialog title="编辑" :visible.sync="dialogFormVisible">
            <el-form :rules="rule" ref="empDetail" :model="detail">
                <el-form-item prop="position" label="职位" :label-width="formLabelWidth">
                    <el-input v-model="detail.position" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item prop="nation" label="国籍" :label-width="formLabelWidth">
                    <el-input v-model="detail.nation" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item prop="ethnic" label="民族" :label-width="formLabelWidth">
                    <el-input v-model="detail.ethnic" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item prop="school" label="毕业院校" :label-width="formLabelWidth">
                    <el-input v-model="detail.school" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item prop="education" label="学历" :label-width="formLabelWidth">
                    <el-input v-model="detail.education" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item prop="hometown" label="籍贯" :label-width="formLabelWidth">
                    <el-input v-model="detail.hometown" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item prop="political" label="政治面貌" :label-width="formLabelWidth">
                    <el-input v-model="detail.political" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="合同类型" :label-width="formLabelWidth">
                    <el-select v-model="detail.contract" placeholder="请选择">
                        <el-option label="劳动合同" value="1"></el-option>
                        <el-option label="劳务合同" value="0"></el-option>
                    </el-select>
                </el-form-item>

                <el-form-item label="职称" :label-width="formLabelWidth">
                    <el-input v-model="detail.title" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="地址" prop="address" :label-width="formLabelWidth">
                    <el-input v-model="detail.address" autocomplete="off"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="handleCan">取 消</el-button>
                <el-button type="primary" @click="handleUpdate('empDetail')">确 定</el-button>

            </div>
        </el-dialog>
    </div>
</template>

<script>
    export default {
        name: "EmpAdv",
        data() {
            return {
                id: 1,
                detail: [],
                dialogFormVisible: false,
                formLabelWidth: '120px',
                rule: {
                    position: [{required: true, message: '请输入职位', trigger: 'blur'}],
                    nation: [{required: true, message: '请输入国籍', trigger: 'blur'}],
                    ethnic: [{required: true, message: '请输入民族', trigger: 'blur'}],
                    school: [{required: true, message: '请输入毕业院校', trigger: 'blur'}],
                    education: [{required: true, message: '请输入毕业学历', trigger: 'blur'}],
                    hometown: [{required: true, message: '请输入籍贯', trigger: 'blur'}],
                    political: [{required: true, message: '请输入政治面貌', trigger: 'blur'}],
                    address:[{required: true, message: '请输入地址', trigger: 'blur'}]
                }
            }
        },
        created() {
            this.getId();
            this.queryDetail();
        },
        methods: {
            getId() {
                this.id = this.$route.params.id;
            },
            queryDetail() {
                this.postRequest("/emp/detail/" + this.id).then(res => {
                    if (res.code == 0) {
                        this.detail = res.data;
                        this.detail.contract == 0 ? this.detail.contractType = '劳务合同' : this.detail.contractType = '劳动合同';
                    }
                })
            },
            back() {
                this.$router.go(-1)
            },
            handleUpdate(formName) {
                this.$refs[formName].validate(valid => {
                    if (valid) {
                        console.error(this.detail);
                        this.dialogFormVisible = false;
                        this.postRequest('/emp/detail/updateById',this.detail).then(res=>{
                            if(res.code == 0){
                                this.$message.success("修改成功")
                            }
                        })
                        this.queryDetail();
                    }
                })
            },
            changeDetail(formName) {
                this.dialogFormVisible = true;


            },
            handleCan() {

                this.dialogFormVisible = false;
                this.queryDetail();

            }
        }
    }
</script>

<style scoped>
    .button {
        margin-top: 30px;
        margin-left: 30px;
        position: center;
    }
</style>