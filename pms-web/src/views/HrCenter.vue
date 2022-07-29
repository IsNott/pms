<template>
    <div>
        <div style="padding-bottom: 10px">
            <el-select v-model="type" clearable placeholder="按类型选择">
                <el-option
                        v-for="item in options"
                        :key="item.id"
                        :label="item.name"
                        :value="item.id"
                >
                </el-option>
            </el-select>
            <el-button icon="el-icon-search" type="primary" style="margin-left: 15px" @click="initAudit">
                搜索
            </el-button>
            <el-button icon="el-icon-edit" type="primary" style="margin-left: 15px" @click="editAudit">
                填写登记
            </el-button>
            <el-button icon="el-icon-edit" type="primary" style="margin-left: 15px" @click="toEmpDetail">
                我的详细信息
            </el-button>
            <el-button icon="el-icon-edit" type="primary" style="margin-left: 15px" v-if="user.roleId !== 0 "
                       @click="toAudit" >
                待处理申请
            </el-button>
        </div>

        <el-table
                :data="tableData"
                max-height="450"
                border
                :v-loading="loading"
                style="width: 100%;font-size: initial"
                >
            <el-table-column
                    prop="auditType"
                    label="申请类型"
                    width="180">
            </el-table-column>
            <el-table-column
                    prop="reason"
                    label="理由"
                    width="500">
            </el-table-column>
            <el-table-column
                    prop="auditorName"
                    label="审核人"
                    width="180">
            </el-table-column>
            <el-table-column
                    prop="auditStatus"
                    label="状态">
            </el-table-column>
            <el-table-column
                    prop="auditDuration"
                    label="时长"
                    width="180">
            </el-table-column>
            <el-table-column
                    prop="option"
                    label="时长单位">
            </el-table-column>
            <el-table-column
                    prop="stattime"
                    label="开始时间">
            </el-table-column>
            <el-table-column
                    prop="endtime"
                    label="结束时间">
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

        <el-dialog
                title="填写登记"
                :visible.sync="dialogVisible"
                width="40%"
                :before-close="handleClose">
            <el-radio v-model="form.type" label="1" value="0">加班</el-radio>
            <el-radio v-model="form.type" label="2" value="1">请假</el-radio>
            <el-radio v-model="form.type" label="3" value="2">离职</el-radio>
            <el-form>
                <el-form-item prop="reason" label="理由" :label-width=formLabelWidth>
                    <el-input v-model="form.reason" placeholder="请输入..." style="width: 125px"></el-input>
                </el-form-item>
                <el-form-item label="时长选项" v-if="form.type !== '3'" :label-width="formLabelWidth">
                    <el-radio v-model="form.auditOption" label="1" value="0">小时</el-radio>
                    <el-radio v-model="form.auditOption" label="2" value="1">天</el-radio>
                </el-form-item>
                <el-form-item v-if="form.type !== '3'" label="开始时间" :label-width="formLabelWidth">
                    <el-date-picker
                            v-model="form.starttime"
                            type="datetime"
                            placeholder="选择日期时间">
                    </el-date-picker>

                </el-form-item>
                <el-form-item v-if="form.type !== '3'" label="结束时间" :label-width="formLabelWidth">
                    <el-date-picker
                            v-model="form.endtime"
                            type="datetime"
                            placeholder="选择日期时间">
                    </el-date-picker>
                </el-form-item>
                <el-form-item label="时长" v-if="form.type !== '3'" :label-width="formLabelWidth">
                    <el-input v-model="form.auditDuration" label="1" value="0"></el-input>

                </el-form-item>
            </el-form>
            <el-button @click="dialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="dialogVisible = false">确 定</el-button>
        </el-dialog>
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
                type: '',
                loading: false,
                dialogVisible: false,
                formLabelWidth: '70px',
                auditData:[],
                user: JSON.parse(window.sessionStorage.getItem("user")),
                options: [
                    {
                        id: 0,
                        name: '请假',
                    },
                    {
                        id: 1,
                        name: '离职'
                    },
                    {
                        id: 2,
                        name: '加班'
                    }],
                auOptions: [
                    {
                        id: 0,
                        name: '天'
                    },
                    {
                        id: 1,
                        name: '小时'
                    }
                ],
                form: []
            }
        },
        methods: {
            initAudit() {
                var page = this.page ? this.page : 1;
                var size = this.size ? this.size : 10;
                var type = this.type === 0 ? 0 : this.type;
                this.postRequest('/pms/audit/list', {page, size, type}).then(res => {
                    if (res.code == 0) {
                        var data = res.data.records;
                        this.total = res.data.total;
                        // this.size = res.data.size;
                        // this.page = res.data.current;
                        data.forEach(item => {
                            item.auditOption == 0 ? item.option = '小时' : item.option = '天';
                            if (item.type == 0) {
                                item.auditType = '请假';
                            }
                            if (item.type == 1) {
                                item.auditType = '离职';
                            }
                            if (item.type == 2) {
                                item.auditType = '加班';
                            }
                            if (item.status == 1) {
                                item.auditStatus = '通过';
                            }
                            if (item.status == -1) {
                                item.auditStatus = '不通过';
                            }
                            if (item.status == 0) {
                                item.auditStatus = '审核中';
                            }

                        })
                        this.tableData = data;
                    }
                })
            },
            pageCurrent(currentPage) {
                this.page = currentPage;
                this.initAudit();
            },
            sizeChange(currentSize) {
                this.size = currentSize;
                this.initAudit();
            },
            handleClose(){
                this.dialogVisible = false;
                this.initAudit();
            },
            toEmpDetail() {
                this.$router.replace('/empDetail/' + this.user.empNo)
            },
            editAudit() {
                this.dialogVisible = true;
            },
            toAudit(){
                this.$router.push("/audit")
            }
        },
        created() {
            this.initAudit();
        }
    }
</script>
<style scoped>

    .el-form-item--small.el-form-item{
        width:33%
    }
    .el-radio{
        margin-bottom:15px
    }
</style>