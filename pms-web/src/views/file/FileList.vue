<template>
    <div>
        <h3>文件列表</h3>
        <div style="margin-bottom: 30px">
            <el-upload
                    class="upload-demo"
                    action="/pms/file/upload"
                    :before-upload="beforUpload"
                    :on-success="(res)=>{
                    return successUpload(res)
                }"
                    multiple
            >
                <el-button size="small" type="primary">点击上传</el-button>
                <div slot="tip" class="el-upload__tip">只能上传doc、docx、ppt、ppt、pdf、rar、zip、xlsx、xls，且不超过3MB的文件</div>
            </el-upload>
        </div>
        <el-table
                :data="tableData.filter(data => !search || data.fileName.toLowerCase().includes(search.toLowerCase()))"
                style="width: 100%;font-size: initial"

        >
            <el-table-column
                    label="上传时间"
                    prop="uploadTime">
            </el-table-column>
            <el-table-column
                    label="上传人"
                    prop="uploader">
            </el-table-column>
            <el-table-column
                    label="文件名"
                    prop="fileName">
            </el-table-column>
            <el-table-column
                    align="right">
                <template slot="header" slot-scope="scope">
                    <el-input
                            v-model="search"
                            size="mini"
                            placeholder="输入文件名关键字搜索"/>
                </template>
                <template slot-scope="scope">
                    <el-button
                            size="mini"
                            type="primary"
                            style="margin-right:30px "
                            @click="handleDownLoad(scope.row)">下载
                    </el-button>

                    <el-button
                            size="mini"
                            type="danger"
                            v-if="user.roleId!==0"
                            @click="handleDelete(scope.row)" style="margin-right: 50px">删除
                    </el-button>
                </template>
            </el-table-column>
        </el-table>
        <div>
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
        name: 'FileList',
        data() {
            return {
                user: JSON.parse(window.sessionStorage.getItem("user")),
                keyword: '',
                tableData: [],
                total: 0,
                page: 1,
                size: 10,
                search: '',
            }
        },

        created() {
            this.initFiles();
        },
        methods: {
            initFiles() {
                var page = this.page ? this.page : 1;
                var rows = this.size ? this.size : 10;
                var name = this.keyword ? this.keyword : '';
                this.postRequest('/pms/file/queryFileByPage', {page, rows, name}).then(res => {
                    if (res.code == 0) {
                        this.tableData = res.data.records;
                        // this.page = res.data.current;
                        // this.size = res.data.size;
                        this.total = res.data.total;
                    }
                })
            },
            handleDelete(row) {
                console.error(row, "row")
                var id = row.id
                this.postRequest('/pms/file/remove', {id}).then(res => {
                    if (res.code == 0) {
                        this.initFiles();
                        this.$message.success("删除成功");
                    } else {
                        this.$message.error("失败");
                    }
                })
            },
            pageCurrent(currentPage) {
                this.page = currentPage;
                this.initEmp();
            },
            sizeChange(currentSize) {
                this.size = currentSize;
                this.initEmp();
            },
            handleDownLoad(row) {
                var id = row.id;
                var filename = '';
                var filetype = '';
                console.log(id)
                this.postRequest("/pms/file/type", {id}).then(res => {
                    if (res.code == 0) {
                        filename = res.data.name;
                        filetype = res.data.type;
                    }
                })
                this.handDownLoad(id, filename, filetype);
            },
            //todo 获取文件类型
            handDownLoad(id, filename, filetype) {
                this.postRequest('/pms/file/download/'+id).then(res=>{
                    let blob = new Blob([res.data])
                    let URLOBJ = window.URL || window.webkitURL;
                    const ele = document.createElement('a');
                    let href = URLOBJ.createObjectURL(blob);
                    ele.href = href;
                    ele.download = filename;
                    document.body.appendChild(ele);
                    ele.click();
                    URLOBJ.revokeObjectURL(href);
                    document.body.removeChild(ele);
                });
            },
            beforUpload(file) {
                var isFile = false;
                let extName = file.name.substring(file.name.lastIndexOf(".")).toLowerCase();
                let AllUpExt = ".txt|.rar|.zip|.doc|.docx|.xls|.xlsx|.pdf|";
                if (AllUpExt.indexOf(extName + "|") == "-1") {
                    this.$message.error(this, "error", "文件格式不正确!");
                    isFile = false;
                } else {
                    // 操作
                    isFile = true;
                }
                const isLt3M = file.size / 1024 / 1024 < 3;

                if (!isFile) {
                    this.$message.error("上传格式只能是txt、ppt、doc、docx、pdf、rar文件!");
                }
                if (!isLt3M) {
                    this.$message.error("上传图片大小不能超过 3MB!");
                }
                return isFile && isLt3M;
            }
        },
        successUpload(res) {
            if (res.code == 0) {
                this.initFiles();
                this.$message.success("上传成功");
            }
        }

    }
</script>

<style></style>