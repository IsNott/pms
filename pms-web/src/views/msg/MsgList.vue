<template>
    <div>
        <div style="padding-bottom: 10px">
            <el-input placeholder="请输入信息内容进行搜索" prefix-icon="el-icon-search"
                      clearable
                      @clear="initMsg"
                      style="width: 250px;margin-right: 10px" v-model="keyword"
                      @keydown.enter.native="initMsg"></el-input>
            <el-button icon="el-icon-search" type="primary" @click="initMsg">
                搜索
            </el-button>
            <el-button icon="el-icon-search" type="primary" @click="initUserMsg">
                仅查看用户信息
            </el-button>
            <el-button icon="el-icon-search" type="primary" @click="initAnnou">
                仅查看公告
            </el-button>
        </div>
        <div id="table" style="border: #99a9bf;font-size: initial">
            <el-table
                    :data="tableData"
                    style="width: 100%;font-size: initial"
                    max-height="450"
                    :v-loading="loading"
                    element-loading-text="正在加载..."
            >
                <el-table-column
                        prop="senderName"
                        label="发送人"
                        width="120">
                </el-table-column>
                <el-table-column
                        prop="text"
                        label="文本内容"
                        width="300">
                </el-table-column>
                <el-table-column
                        prop="sendtime"
                        label="发送时间"
                        width="200">
                </el-table-column>
                <el-table-column
                        prop="receiverName"
                        label="接收人"
                        width="120">
                </el-table-column>
                <el-table-column
                        prop="msgStatu"
                        label="状态"
                        width="120">
                </el-table-column>
                <el-table-column
                        prop="msgType"
                        label="类型"
                        width="160">
                </el-table-column>
                <el-table-column
                        fixed="right"
                        label="操作"
                >
                    <template slot-scope="scope">
                        <el-button
                                @click="removeMsg(scope.row)"
                                type="text"
                                size="small">
                            移除
                        </el-button>
                        <el-button
                                @click="editMsg(scope.row)"
                                type="text"
                                v-if="scope.row.msgType=='公告'"
                                size="small"
                                :disabled="scope.row.receiverName=='已离职'?true:false">
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

            <el-dialog title="编辑公告"
                       :visible.sync="dialogVisible">
                <el-form>
                    <el-form-item prop="text" label="请输入文本">
                        <el-input v-model="text" type="textarea"></el-input>
                    </el-form-item>
                </el-form>
                <el-button style="margin-left: 35px;position: center" type="primary" @click="updateAnnou('form')">
                    确定
                </el-button>
                <el-button style="margin-left: 35px;position: center" @click="dialogVisible=false">取消</el-button>

            </el-dialog>
        </div>
    </div>
</template>
<script>
    export default {
        name: "DeptList",
        data() {
            return {
                tableData: [],
                total: 0,
                page: 1,
                size: 10,
                loading: false,
                keyword: '',
                type: '',
                id: '',
                dialogVisible: false,
                text: ''
            }
        },
        methods: {
            pageCurrent(currentPage) {
                this.page = currentPage;
                this.initMsg();
            },
            sizeChange(currentSize) {
                this.size = currentSize;
                this.initMsg();
            },
            initUserMsg() {
                this.type = 0;

                this.initMsg();
            },
            initAnnou() {
                this.type = 1;
                this.initMsg();
            },
            initMsg() {
                var page = this.page ? this.page : 1;
                var rows = this.size ? this.size : 10;
                var keyword = this.keyword ? this.keyword : '';
                // var type = this.type ? this.type : '';
                var type = '';
                if (this.type === 0) {
                    type = 0;
                } else if (this.type === 1) {
                    type = 1;
                } else {
                    type = '';
                }
                // console.error(this.type,type)
                this.postRequest("/pms/msg/queryMsgByAdmin", {page, rows, keyword, type}).then(res => {
                    if (res.code == 0) {
                        //console.error(res.data, "msgList")
                        this.tableData = res.data.records;
                        this.page = res.data.current
                        this.size = res.data.size
                        this.total = res.data.total
                        this.tableData.forEach(item => {
                            item.msgType = item.type == 0 ? '信息' : '公告';
                            item.msgStatu = item.statu == 1 ? '已读' : '未读';
                        })
                    }
                })

            },
            editMsg(row) {
                this.id = row.id;
                this.dialogVisible = true;
                this.text = row.text;
            },
            updateAnnou(formName) {
                var text = this.text;
                var id = this.id;
                this.postRequest('/pms/msg/updateAnnou', {id, text}).then(res => {
                    if (res.code === 0) {
                        this.$message.success("修改成功");
                    }
                })
                this.dialogVisible = false;
                this.initMsg();
            },
            removeMsg(row) {

            }
        }
        ,
        created() {
            this.initMsg()
        }
    }
</script>
<style scoped>

</style>