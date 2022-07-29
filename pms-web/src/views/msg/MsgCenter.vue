<template>
    <div>
        <div>
            <el-button type="primary" @click="queryAllMsg">所有信息</el-button>
            <el-button type="primary" @click="diaFormVisible = true">新建信息</el-button>
            <el-button type="primary" @click="queryAnnou">查看公告</el-button>
            <el-button type="primary" @click="queryUserMsg">通讯信息</el-button>
        </div>
        <div style="margin-bottom: 15px">
            <el-dialog title="新建信息" :visible.sync="diaFormVisible">
                <el-form :model="form">
                    <el-form-item label="消息内容" :label-width="formLabelWidth">
                        <el-input type="textarea" v-model="form.text" autocomplete="off"></el-input>
                    </el-form-item>
                    <el-form-item label="收件人" :label-width="formLabelWidth">
                        <el-col :span="12">
                            <!--                            <div class="sub-title">激活即列出输入建议</div>-->
                            <el-autocomplete
                                    class="inline-input"
                                    v-model="state1"
                                    :fetch-suggestions="querySearch"
                                    placeholder="请输入内容"
                                    @select="handleSelect"
                            >
                                <template slot-scope="{ item }">
                                    <div>{{ item.name }}</div>
                                </template>
                            </el-autocomplete>
                        </el-col>
                    </el-form-item>
                </el-form>
                <div slot="footer" class="dialog-footer">
                    <el-button @click="diaFormVisible = false">取 消</el-button>
                    <el-button type="primary" @click="sendNewMsg">确 定</el-button>
                </div>
            </el-dialog>
        </div>
        <el-table
                :data="tableData"
                border
                style="width: 100%;font-size: initial"
                v-loading="loading"
                element-loading-text="正在加载..."
                element-loading-spinner="el-icon-loading"
                element-loading-background="rgba(0, 0, 0, 0.8)">

            <el-table-column
                    prop="senderid"
                    label="发送人员工编号"
                    width="180">
            </el-table-column>
            <el-table-column
                    prop="sender"
                    label="发送姓名"
                    width="180">
            </el-table-column>
            <el-table-column
                    prop="sendtime"
                    label="发送时间"
                    width="180">
            </el-table-column>
            <el-table-column
                    prop="text"
                    label="信息内容">
            </el-table-column>
            <el-table-column
                    prop="statu"
                    label="信息状态">
            </el-table-column>
            <el-table-column
                    prop="type"
                    label="信息类型">
            </el-table-column>
            <el-table-column
                    fixed="right"
                    label="操作"
                    width="100">
                <template slot-scope="scope">
                    <el-button @click="handleClick(scope.row)" type="text" size="small">查看</el-button>
                    <el-button type="text" @click="open(scope.row.senderid)" size="small" :disabled="disResp(scope.row)">回复</el-button>
                    <el-button type="text" @click="deleteByid(scope.row.id)" size="small">删除</el-button>
                </template>
            </el-table-column>
        </el-table>

        <div id="page">
            <el-pagination
                    background
                    layout="sizes, prev, pager, next, jumper, ->, total, slot"
                    :total=total
                    @size-change="sizeChange"
                    @current-change="pageCurrent"
                    class="page">
            </el-pagination>
        </div>
    </div>

</template>

<script>

    export default {
        data() {
            return {
                loading: false,
                total: 0,
                page: 1,
                size: 10,
                tableData: [],
                state1: '',
                empList: [],
                user: JSON.parse(window.sessionStorage.getItem("user")),
                msgs: [],
                diaFormVisible: false,
                form: {
                    name: '',
                    receiver: ''
                },
                receiverid: 1,
                formLabelWidth: '120px',
                sender: [],
                initType: null,
                type:'',
                disabled: false,
            }
        },
        mounted() {
            this.queryEmpList();
        },
        created() {
            this.queryUserMsgList();
        },

        computed: {},
        methods: {
            disResp(row){
             return row.sender == "系统发送"? true:false;
            },
            queryAllMsg() {
                this.type = null;
                this.queryUserMsgList();
            },
            queryAnnou(){
                this.type = '1';
                this.queryUserMsgList();
            },
            queryUserMsg(){
                this.type = '0';
                this.queryUserMsgList();
            },
            sendNewMsg() {
                var msg = {
                    text: this.form.text,
                    receiverid: this.receiverid,
                    senderid: this.user.empNo
                }
                this.postRequest("/pms/msg/sendMsg", msg).then(res => {
                    this.diaFormVisible = false
                    if (res.code == 0) {
                        this.$message.success("发送成功")
                    } else {
                        this.$message.error(res.msg);
                    }
                })
            },
            querySearch(queryString, cb) {
                var empList = this.empList;
                // console.error("emp",empList)
                var results = queryString ? empList.filter(this.createFilter(queryString)) : empList;
                // 调用 callback 返回建议列表的数据
                // console.error("result",results);
                cb(results);
            },
            handleSelect(row) {
                this.receiverid = row.id;
                this.state1 = row.name;
                // console.error("row",row)
            },
            createFilter(queryString) {
                return (empList) => {
                    return (empList.name.toLowerCase().indexOf(queryString.toLowerCase()) === 0);
                };
            },
            queryEmpList() {
                this.postRequest("/emp/list").then(res => {
                    if (res.code == 0) {
                        this.empList = res.data;
                    }
                })
            },
            pageCurrent(currentPage) {
                this.page = currentPage;
                this.queryUserMsgList();
            },
            sizeChange(currentSize) {
                this.size = currentSize;
                this.queryUserMsgList();
            },
            deleteByid(id) {
                console.log(id);
                this.$confirm('此操作将永久删除该信息, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning',
                }).then(() => {
                    var ids = id + ""
                    this.postRequest("/pms/msg/removeMsg", {ids}).then(res => {
                        if (res.code == 0) {
                            this.$message({
                                type: 'success',
                                message: '删除成功!'
                            }),
                                this.queryUserMsgList(1, 10)
                        } else {
                            this.$message({
                                type: 'error',
                                message: '删除失败'
                            })
                        }
                    })
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });
            },
            open(id) {
                this.$prompt('请输入文本', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                }).then(({value}) => {
                    var msg = {
                        senderid: this.user.empNo,
                        receiverid: id,
                        text: value
                    }
                    this.postRequest("/pms/msg/sendMsg", msg).then(res => {
                        if (res.code == 0) {
                            this.$message({
                                type: 'success',
                                message: '发送成功'
                            });
                        }
                    })
                }).catch(() => {
                });
            },
            handleClick(row) {
                this.$alert(row.text, '信息内容', {
                    confirmButtonText: '确定',
                });
               // console.error(row,"sdasda")
                if (row.statu === '未读') {
                    var ids = row.id
                    this.postRequest("/pms/msg/readMsg", {ids}).then(res => {
                        if (res) {
                            this.queryUserMsgList();
                        }
                    })
                }
            },
            queryUserMsgList() {
                var page = this.page ? this.page : 1;
                var size = this.size ? this.size : 10;
                var type = this.type ? this.type : '';
                this.loading = true;
                this.postRequest("/pms/msg/queryUserMsgPage", {page, size,type}).then(res => {
                        this.loading = false;
                        if (res.code == 0) {
                            var msgTemp = []
                            var temp = res.data.records;
                            this.total = res.data.total;
                            console.log(temp)
                            temp.forEach(item => {
                                var msg = {
                                    senderid: 1,
                                    sender: '',
                                    //type: '',
                                    statu: '',
                                    sendtime: '',
                                    text: '',
                                    id: 1,
                                    type: 1,
                                };
                                msg.type = (item.type == 1 ? "公告" : "用户信息")
                                msg.statu = (item.statu == 1 ? "已读" : "未读")
                                msg.sendtime = item.sendtime
                                msg.text = item.text
                                msg.senderid = item.senderid ? item.senderid : "系统信息";
                                msg.id = item.id
                                msg.sender = item.sender?item.sender : "系统信息";
                                msgTemp.push(msg);
                            })
                            this.tableData = msgTemp;
                        } else {
                            this.$message.error(res.msg);
                        }
                    }
                )
            },

        }
    }
</script>

<style>
    .page {
        margin-top: 20px;
        display: flex;
        justify-content: flex-end;
    }

</style>