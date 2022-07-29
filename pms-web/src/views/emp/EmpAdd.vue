<template>
    <div>
        <div id="basic" v-if="active===0" style="margin-left: 30px">
            <h3>填写基本资料</h3>
            <el-form :rules="rules" ref="Basic" style="width: 50%" :model="formconfig.editBasic">
                <el-form-item prop="name" label="姓名">
                    <el-input class="detail" style="width: 43%"  v-model="formconfig.editBasic.name" type="text"
                              placeholder="请输入姓名"></el-input>
                </el-form-item>
                <el-form-item prop="deptno" label="部门" >
                    <el-select v-model="formconfig.editBasic.deptno" clearable placeholder="请选择" >
                        <el-option
                                v-for="item in dept"
                                :key="item.id"
                                :label="item.deptName"
                                :value="item.id"
                        >
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item prop="birthday" label="生日">
<!--                    <span class="demonstration">出生日期</span>-->
                    <el-date-picker

                            v-model="formconfig.editBasic.birthday"
                            type="date"
                            placeholder="选择日期">
                    </el-date-picker>
                </el-form-item>
                <el-form-item prop="entryTime" label="入职">
<!--                    <span class="demonstration">入职时间</span>-->
                    <el-date-picker

                            v-model="formconfig.editBasic.entryTime"
                            type="date"
                            placeholder="选择日期">
                    </el-date-picker>
                </el-form-item>
                <el-form-item prop="tel" label="电话">
                    <el-input class="detail"  style="width: 43%" v-model="formconfig.editBasic.tel" type="text"
                              placeholder="请输入电话"></el-input>
                </el-form-item>
                <el-form-item prop="gender" label="性别">
                    <el-select v-model="formconfig.editBasic.gender">
                        <el-option
                                v-for="item in gender"
                                :key="item.id"
                                :label="item.name"
                                :value="item.value">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="职位" prop="position">
                    <el-input class="detail" style="width: 43%" v-model="formconfig.editBasic.position" type="text"
                              placeholder="请输入职位"></el-input>
                </el-form-item>
            </el-form>
            <el-button style="margin-top: 12px;" @click="nextClick('Basic')">下一步</el-button>
            <el-button v-if="active!==0" @click="backToBefore">返回上一步</el-button>
            <el-button @click="back">取消</el-button>
        </div>
        <div id="advInfo" v-if="active===1" style="margin-left: 30px">
            <el-form ref="advInfo" :rules="advRules" :model="formconfig.advInfo">
                <el-form-item class="detail" prop="idCard" label="身份证号码">
                    <el-input type="text" v-model="formconfig.advInfo.idCard" placeholder="请输入身份证号码"></el-input>
                </el-form-item>
                <el-form-item class="detail" prop="ethnic" label="民族">
                    <el-input type="text" v-model="formconfig.advInfo.ethnic" placeholder="请输入国籍"></el-input>
                </el-form-item>
                <el-form-item class="detail" prop="nation" label="国籍">
                    <el-input type="text" v-model="formconfig.advInfo.nation" placeholder="请输入国籍"></el-input>
                </el-form-item>
                <el-form-item class="detail" prop="political" label="政治面貌">
                    <el-input type="text" v-model="formconfig.advInfo.political" placeholder="请输入政治面貌"></el-input>
                </el-form-item>
                <el-form-item class="detail" prop="hometown" label="籍贯">
                    <el-input type="text" v-model="formconfig.advInfo.hometown" placeholder="请输入籍贯"></el-input>
                </el-form-item>
                <el-form-item class="detail" prop="marital" label="婚姻状况">
                    <el-select v-model="formconfig.advInfo.marital" clearable placeholder="请选择">
                        <el-option
                                v-for="item in marital"
                                :key="item.id"
                                :label="item.name"
                                :value="item.value">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item class="detail" prop="contract" label="合同类型" v-model="formconfig.advInfo.contract">
                    <el-select v-model="formconfig.advInfo.contract" clearable placeholder="请选择">
                        <el-option
                                v-for="item in contract"
                                :key="item.id"
                                :label="item.name"
                                :value="item.value">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item class="detail" prop="contractYears" label="合同年限"
                >
                    <el-input v-model="formconfig.advInfo.contractYears" type="text" placeholder="请输入合同年限"></el-input>
                </el-form-item>
                <el-form-item class="detail" prop="education" label="教育程度">
                    <el-input type="text" v-model="formconfig.advInfo.education" placeholder="请输入教育程度"></el-input>
                </el-form-item>
                <el-form-item class="detail" prop="school" label="毕业院校">
                    <el-input type="text" v-model="formconfig.advInfo.school" placeholder="请输入毕业院校"></el-input>
                </el-form-item>
                <el-form-item class="detail" prop="title" label="职称">
                    <el-input type="text" v-model="formconfig.advInfo.title" placeholder="请输入职称"></el-input>
                </el-form-item>
                <el-form-item class="detail" prop="address" label="地址">
                    <el-input type="textarea" v-model="formconfig.advInfo.address" placeholder="请输入地址"></el-input>
                </el-form-item>
            </el-form>
            <el-button style="margin-top: 12px;" @click="sumbit('advInfo')">提交</el-button>
            <el-button v-if="active!==0" @click="backToBefore">返回上一步</el-button>
            <el-button @click="back">取消</el-button>
        </div>

        <div id="submitInfo" v-if="active===2">
            <el-button v-if="active!==0" @click="backToHome">返回首页</el-button>
        </div>


        <div style="margin-top: 60px">
            <el-steps :active="active" finish-status="success">
                <el-step title="填写基本资料"></el-step>
                <el-step title="提交高级资料"></el-step>
                <el-step title="返回"></el-step>
            </el-steps>
        </div>
    </div>
</template>
<script>
    export default {
        data() {
            return {
                active: 0,
                dept: [],
                info: [],
                advInfo: [],
                formconfig: {
                    editBasic: {
                        name: '',
                        tel: '',
                        gender: '',
                        birthday: '',
                        entryTime: '',
                        deptno: '',
                    },
                    advInfo: {
                        nation: '',
                        ethnic: '',
                        political: '',
                        hometown: '',
                        address: '',
                        marital: '',
                        contract: '',
                        contractYears: '',
                        school: '',
                        education: '',
                        title: '',
                        idCard: '',
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
                marital: [
                    {
                        id: 1,
                        value: '0',
                        name: '未婚',
                    },
                    {
                        id: 2,
                        value: '1',
                        name: '已婚',
                    }
                ],
                contract: [
                    {
                        id: 1,
                        value: '0',
                        name: '劳务合同',
                    },
                    {
                        id: 2,
                        value: '1',
                        name: '劳动合同',
                    }
                ],

                rules: {
                    name: [{required: true, message: '请输入名称', trigger: 'blur'},
                        {
                            pattern: /^[\u0391-\uFFE5a-zA-Z·.。;&\\s]{0,}$/,
                            min: 2,
                            max: 10,
                            message: '长度在 2 到 10 个的中英文字符', trigger: 'blur'
                        }],
                    tel: [
                        {required: true, message: '请输入电话号码', trigger: 'blur'},
                        {
                            pattern: /^((1[3,5,8,7,9][0-9])|(14[5,7])|(17[0,6,7,8])|(19[1,7]))\d{8}$/,
                            message: '请输入正确的电话格式',
                            trigger: 'blur'
                        }
                    ],
                    deptno: [{required: true, message: '请选择部门', trigger: 'blur'}],
                    birthday: [{required: true, message: '请输入日期', trigger: 'blur'},],
                    entryTime: [{required: true, message: '请输入日期', trigger: 'blur'},],
                    gender: [
                        {required: true, message: '请选择性别', trigger: 'blur'},
                    ],
                    position: [{required: true, message: '请输入职位名称', trigger: 'blur'},
                        {min: 2, max: 10, message: '长度在 2 到 10 个字符', trigger: 'blur'}],
                },
                advRules: {
                    nation: [{required: true, message: '请输入国籍', trigger: 'blur'}],
                    ethnic: [{required: true, message: '请输入民族', trigger: 'blur'}],
                    political: [{required: true, message: '请输入政治面貌', trigger: 'blur'}],
                    hometown: [{required: true, message: '请输入籍贯', trigger: 'blur'}],
                    marital: [{required: true, message: '请选择婚姻状况', trigger: 'blur'}],
                    contract: [{required: true, message: '请选择合同类型', trigger: 'blur'}],
                    contractYears: [{required: true, message: '请输入合同年限', trigger: 'blur'},
                        {
                            pattern: /^[1-9]*[1-9][0-9]*$/,
                            message: "请输入正确数字",
                            trigger: 'blur'
                        }],
                    school: [{required: true, message: '请输入毕业院校', trigger: 'blur'}],
                    education: [{required: true, message: '请输入教育程度', trigger: 'blur'}],
                    title: [{required: true, message: '请输入职称，若无可填”无“', trigger: 'blur'}],
                    idCard: [{required: true, message: '请输入身份证号码', trigger: 'blur'}, {
                        pattern: /^([1-6][1-9]|50)\d{4}(18|19|20)\d{2}((0[1-9])|10|11|12)(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/,
                        message: '请输入正确的身份证号码',
                        trigger: 'blur'
                    }],
                    address: [{required: true, message: '请输入地址', trigger: 'blur'}]

                },
                id: 1,
            }
        },
        created() {
            this.depts();
        },
        methods: {
            nextClick(formName) {
                this.$refs[formName].validate(valid => {
                    if (valid) {
                        var info = this.formconfig.editBasic;
                        if (this.active++ > 2) this.active = 0;
                        console.error(this.formconfig.editBasic, "基础资料")
                        info.birthday = typeof info.birthday === 'string' ? info.birthday : info.birthday.getFullYear() + '-' + (info.birthday.getMonth() + 1) + '-' + info.birthday.getDate() + ' ' + info.birthday.getHours() + ':' + info.birthday.getMinutes() + ':' + info.birthday.getSeconds();
                        info.entryTime = typeof info.entryTime === 'string' ? info.entryTime : info.entryTime.getFullYear() + '-' + (info.entryTime.getMonth() + 1) + '-' + info.entryTime.getDate() + ' ' + info.entryTime.getHours() + ':' + info.entryTime.getMinutes() + ':' + info.entryTime.getSeconds();
                        this.info = info;
                        var emp = this.info
                        this.postRequest('/emp/add', emp).then(res => {
                            if (res.code == 0) {
                                this.id = res.data.id;
                            }
                        })

                    }
                })
            },
            sumbit(formName) {
                this.$refs[formName].validate(valid => {
                    if (valid) {
                        this.advInfo = this.formconfig.advInfo;
                        console.error(this.advInfo, "高级资料")
                        var info = this.info;
                        var advInfo = this.advInfo;
                        advInfo.empNo = this.id
                        this.postRequest('/emp/detail/addDetail', advInfo).then(res => {
                            if(res.code ==0){
                                this.active = 2;
                                this.$message.success("新增成功");
                            }
                        })
                    }
                })
            },
            backToHome() {
                this.$router.replace("/home") //返回首页
            },
            depts() {
                this.postRequest('/pms/department/deptList').then(res => {
                    if (res.code == 0) {
                        this.dept = res.data;
                    }
                })
            },
            backToBefore() {
                this.active--;
            },
            back() {
                this.$router.push("/home");
            }
        }
    }
</script>
<style scoped>
    .detail {
        width: 480px;
    }
    /deep/ .el-input__inner{
        margin-left: 40px !important;
    }
    /deep/ .el-icon-date:before{
        position: absolute;
        left: 46px;
    }
</style>