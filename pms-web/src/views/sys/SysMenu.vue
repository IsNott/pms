<template>
    <div>
        <el-table
                :data="tableData"
                style="width: 100%;font-size: initial">
            <el-table-column
                    prop="id"
                    label="id"
                    width="180">
            </el-table-column>
            <el-table-column
                    prop="name"
                    label="名称"
                    width="180">
            </el-table-column>
            <el-table-column
                    prop="component"
                    label="组件"
                    width="180">
            </el-table-column>

            <el-table-column
                    prop="statu"
                    label="状态">
            </el-table-column>
            <el-table-column
                    label="操作">
                <template slot-scope="scope">
                    <el-button type="warning" @click="disableMenu(scope.row)" v-if="scope.row.statu==='启用'">停用
                    </el-button>
                    <el-button v-if="scope.row.statu==='停用'" @click="enableMenu(scope.row)">启用</el-button>
                    <el-button @click="openChild(scope.row)">查看子菜单</el-button>
                </template>
            </el-table-column>
        </el-table>
        <div>
            <el-dialog
                    title="子菜单"
                    :visible.sync="dialogVisible"
                    width="60%">
                <el-table
                        :data="child"
                        style="width: 100%">
                    <el-table-column
                            prop="id"
                            label="id"
                            width="180">
                    </el-table-column>
                    <el-table-column
                            prop="name"
                            label="名称"
                            width="180">
                    </el-table-column>
                    <el-table-column
                            prop="statu"
                            label="状态">
                    </el-table-column>
                    <el-table-column
                            label="操作">
                        <template slot-scope="scope">
                            <el-button type="warning" @click="disableMenu(scope.row)" v-if="scope.row.statu==='启用'">停用
                            </el-button>
                            <el-button v-if="scope.row.statu==='停用'" @click="enableMenu(scope.row)">启用</el-button>
                        </template>
                    </el-table-column>
                </el-table>

            </el-dialog>
        </div>
    </div>
</template>
<script>
    import {postRequest} from "../../utils/axiosUtils";

    export default {
        data() {
            return {
                tableData: [],
                type: '',
                child: [],
                dialogVisible: false,
            }
        },
        methods: {
            initMenus() {
                this.postRequest('/pms/menu/list').then(res => {
                    if (res.code == 0) {
                        var data = res.data;
                        data.forEach(item => {
                            item.delflag === 0 ? item.statu = '启用' : item.statu = '停用';
                        })
                        this.tableData = data;
                    }
                })
            },
            enableMenu(row) {
                if(this.dialogVisible){
                    this.dialogVisible = false;
                }
                var type = 0;
                this.changeMenu(row, type);
            },
            disableMenu(row) {
                if(this.dialogVisible){
                    this.dialogVisible = false;
                }
                var type = 1
                this.changeMenu(row, type);
            },
            changeMenu(row, type) {
                var id = row.id;
                var delfalg = type
                this.postRequest('/pms/menu/changeMenu', {delfalg, id}).then(res => {
                    if (res.code == 0) {
                        this.$message.success("修改成功");
                        this.initMenus();
                    }
                })
            },
            openChild(row) {
                this.dialogVisible = true;
                var data = row.childrens;
                data.forEach(item => {
                    item.delflag === 0 ? item.statu = '启用' : item.statu = '停用';
                });
                this.child = data;
            }

        },
        created() {
            this.initMenus();
        }
    }
</script>
<style></style>
