<template xmlns:style="http://www.w3.org/1999/xhtml">
    <div>
        <el-container direction="vertical">
            <el-header class="homeHeader" style="height: 111px;">
                <div @click="back" style="cursor:pointer;" class="title">人事管理系统</div>
                <div id="pmsHead"
                     style="margin-left: 525px;margin-top: 6px;font-size: 20px;cursor:pointer;place-items: center">
                    <i class="el-icon-bell" @click="toMsg" style="color: beige">通讯信息({{num}})</i>
                    <i class="el-icon-warning-outline" @click="toAnnou" style="color: wheat;padding-left:60px">系统公告({{annNum}})</i>
                    <div style="padding-left:168px;font: icon;font-size: medium;color: #cac6c6;text-decoration: #cac6c6 underline;padding-top: 30px;">
                        <br/>
                        <i style="cursor:pointer;" @click="toFile">文件列表</i>
                        <i style="cursor:pointer;padding-left: 15px" @click="toHrCenter">人事中心</i>
                    </div>
                </div>

                <div>
                    <el-dropdown class="userInfo" @command="commandHandler">
                        <div class="face"><span class="el-dropdown-link">
            {{ userInfo.nickName }}
            <i class="el-icon-arrow-down el-icon--right">
              <el-avatar :src="userface" shape="square" :size="50" @error="errorHandler"></el-avatar>
            </i>
          </span>
                        </div>
                        <el-dropdown-menu slot="dropdown">
                            <el-dropdown-item command="msgCenter">信息中心</el-dropdown-item>
                            <el-dropdown-item command="userCenter">个人中心</el-dropdown-item>
                            <el-dropdown-item command="setting">修改密码</el-dropdown-item>
                            <el-dropdown-item command="logout" divided>
                                退出登录
                            </el-dropdown-item>
                        </el-dropdown-menu>
                    </el-dropdown>

                </div>
            </el-header>
            <el-container>
                <el-aside width="200px" class="menu">
                    <!--                <el-menu :default-openeds="['1', '3']" router>-->
                    <el-menu @select="handldSelect"  unique-opened>
                        <el-submenu
                                :index="index + ''"
                                v-for="(item, index) in routes"
                                :key="index"
                                v-if="!item.hidden">
                            <template slot="title">
                                <i :class="item.iconCls"
                                   style="color: aquamarine; margin-right: 5px"
                                ></i>
                                <span>{{ item.name }}</span>
                            </template>
                            <el-menu-item
                                    :index="children.path"
                                    v-for="(children, indexj) in item.children"
                                    :key="indexj">
                                {{ children.name }}
                            </el-menu-item>
                        </el-submenu>
                    </el-menu>
                </el-aside>
                <el-main>
                    <div>
                        <el-breadcrumb v-if="this.$router.currentRoute.path !== '/home'">
                            <el-breadcrumb-item :to="{ path: '/home' }">首页
                            </el-breadcrumb-item>
                            <el-breadcrumb-item><a href="/">{{ this.$route.name }}</a></el-breadcrumb-item>
                        </el-breadcrumb>

                        <router-view class="homeRouterView"/>

                        <div class="homeWelcome" v-if="$router.currentRoute.path === '/home'">
                            欢迎使用人事管理系统
                        </div>
                        <div v-if="this.$router.currentRoute.path == '/home'">
                            <div style="margin-left:30px;margin-bottom:30px;margin-top:15px;align-self: center;text-trim:force-start;font-family: 微软雅黑;font-size: 25px;color: #cc794e">
                                欢迎:{{emp.name}} | 工号：{{emp.id}} | {{emp.deptName}}
                            </div>
                        </div>
                        <div style="margin-bottom: 400px">
                            <el-card class="box-card" v-if="this.$router.currentRoute.path == '/home'" style="NewCard">
                                <div slot="header" class="clearfix">
                                    <span>待处理消息</span>
                                    <el-button
                                            style="float: right; padding: 3px 0"
                                            type="text"
                                            @click="readMsg(tableData)"
                                    >全部已读
                                    </el-button
                                    >
                                </div>
                                <el-table :data="tableData" style="width: 100%">
                                    <el-table-column label="日期" width="450">
                                        <template slot-scope="scope">
                                            <i class="el-icon-time"></i>
                                            <span style="margin-left: 10px">{{ scope.row.sendtime }}</span>
                                        </template>
                                    </el-table-column>

                                    <el-table-column label="发送人姓名" width="450">
                                        <template slot-scope="scope">
                                            <el-popover trigger="hover" placement="top">
                                                <p>发件: {{ scope.row.sender }}</p>
                                                <p>内容: {{ scope.row.text }}</p>
                                                <div slot="reference" class="name-wrapper">
                                                    <el-tag size="medium">{{ scope.row.sender }}</el-tag>
                                                </div>
                                            </el-popover>
                                        </template>
                                    </el-table-column>
                                    <el-table-column label="操作">
                                        <template slot-scope="scope">
                                            <el-button
                                                    size="mini"
                                                    @click="handleEdit(scope.$index, scope.row)"
                                                    v-if="scope.row.senderid!== null"
                                            >回复
                                            </el-button
                                            >
                                            <el-button
                                                    size="mini"
                                                    type="mini"
                                                    @click="handleread(scope.row)"
                                            >已读
                                            </el-button
                                            >
                                        </template>
                                    </el-table-column>
                                </el-table>
                            </el-card>
                        </div>
                    </div>
                </el-main>
            </el-container>
        </el-container>
    </div>
</template>

<script>

    export default {
        name: "Home",
        data() {
            return {
                num: 0,
                annNum: 0,
                userInfo: {},
                messageData: [],
                userface: this.queryFace,
                user: JSON.parse(window.sessionStorage.getItem("user")),
                emp: JSON.parse(window.sessionStorage.getItem("empInfo")),
                tableData: [],
                //DisResp: this.handleDisResp,
                map: []
            };
        },
        created() {
            this.queryFace();
            this.queryMsgNum();
            this.queryUserInfo(this.user.id);
        },
        mounted() {
            this.initMsgCard()
        },

        methods: {
            readMsg(item) {
                var ids = []
                item.forEach(value => {
                    ids.push(value.id)
                })
                this.postRequest('/pms/msg/readMsg', {ids}).then(res => {
                    if (res.code == 0) {
                        this.initMsgCard();
                        this.$message.success("成功")
                    }
                })
            },
            handldSelect(key,keyPath){
                this.$router.push("/home/"+keyPath[1]);
            },
            toFile() {
                this.$router.replace('/fileCenter')
            },
            toHrCenter() {
                this.$router.replace('/hrCenter')
            },
            queryMsgNum() {
                this.getRequest("/pms/msg/num").then(res => {
                    this.map = res.data;
                    // console.error(this.map,"msg=====");
                    this.num = this.map.msg;
                    this.annNum = this.map.announce;
                })
            },
            toMsg() {
                this.$router.push("/msg/UserMsg/" + '0');
            },
            toAnnou() {
                this.$router.push("/msg/UserMsg/" + '1');
            },
            queryFace() {
                var face = "/pms/user/face"
                this.userface = face;
            },
            errorHandler() {
                this.userface = "src/components/default.jpg"
            },
            queryUserInfo(id) {
                this.postRequest("/pms/user/getInfoById", {id}).then(res => {
                    if (res.code == 0) {
                        this.userInfo = res.data;
                    }
                });
            },
            initMsgCard() {
                this.getRequest('/pms/msg/unreadMsg').then(res => {
                    if (res.code == 0) {
                        //console.log(res.data);
                        this.tableData = res.data.records;

                    }
                })
            },
            handleEdit(index, row) {
                this.$prompt('请输入文本', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                }).then(({value}) => {
                    var msg = {
                        senderid: this.user.empNo,
                        receiverid: row.senderid,
                        text: value
                    }
                    this.postRequest("/pms/msg/sendMsg", msg).then(res => {
                        if (res.code == 0) {
                            var ids = row.id
                            this.read(ids);
                            this.queryMsgNum();
                            this.$message({
                                type: 'success',
                                message: '发送成功'
                            });
                        }
                    })
                }).catch(() => {
                });
            },
            read(ids) {
                this.postRequest('/pms/msg/readMsg', {ids}).then(res => {
                    if (res.code == 0) {
                        this.initMsgCard();
                        this.queryMsgNum();
                        this.$message.success("成功")
                    }
                })
            },
            handleread(row) {
                console.log(row);
                this.postRequest('/pms/msg/readMsg', {ids: row.id}).then(res => {
                    if (res.code == 0) {
                        this.queryMsgNum();
                        return this.initMsgCard();
                    }
                })
            },
            back() {
                if (this.$route.name !== '导航一') {
                    this.$router.replace("/home")
                    this.$router.go(0)
                }
            },
            commandHandler(command) {
                if (command === "logout") {
                    this.$confirm("此操作将退出登录, 是否继续?", "提示", {
                        confirmButtonText: "确定",
                        cancelButtonText: "取消",
                        type: "warning",
                    })
                        .then(() => {
                            this.postRequest("/pms/user/logout").then((resp) => {
                                if (resp) {
                                    // window.sessionStorage.removeItem('tokenStr');
                                    window.sessionStorage.removeItem("user");
                                    window.sessionStorage.removeItem("emoInfo");
                                    window.sessionStorage.removeItem("userInfo");
                                    window.sessionStorage.clear();
                                    this.$store.commit("initRoutes", []);
                                    this.$router.replace("/");
                                }
                            });
                        })
                        .catch(() => {
                            this.$message({
                                type: "info",
                                message: "已取消操作",
                            });
                        });
                } else if (command === "setting") {
                    this.$router.replace("/setting");
                } else if (command === "userCenter") {
                    // if(localStorage.getItem("user")){
                    //     let Userinfo = user.data;
                    //     console.info("currentUser info :" + Userinfo);
                    //
                    // }
                    this.$router.replace("/userCenter");
                } else if (command == "msgCenter") {
                    this.$router.replace("/msgCenter");
                }
            },
        },
        computed: {
            routes() {
                return this.$store.state.routes;
            },
        },

    }
</script>

<style scoped>
    .pmsHead {

    }

    .newCard {
        width: 50%;
    }

    .homeHeader {
        background: rgb(87, 85, 82);
        display: flex;
        align-items: center;
        justify-content: space-between;
        padding: 0 25px;
        box-sizing: border-box;
        margin-bottom: 20px;
    }

    .title {
        font-size: 25px;
        font-family: "Helvetica Neue", Helvetica, "PingFang SC", "Hiragino Sans GB",
        "Microsoft YaHei", "微软雅黑", Arial, sans-serif;
        color: white;
        align-items: center;
    }

    .menu {
        font-family: "Helvetica Neue", Helvetica, "PingFang SC", "Hiragino Sans GB",
        "Microsoft YaHei", "微软雅黑", Arial, sans-serif;
    }

    .el-aside {
        height: 100vh;
    }

    .userInfo {
        cursor: pointer;
        color: rgb(196, 196, 196);
        margin-top: 5px;
        font-size: 20px;
    }

    .el-dropdown-link img {
        width: 55px;
        margin-left: 5px;
        margin-top: 5px;
        height: 55px;
    }

    .homeWelcome {
        border: #f0f9eb dashed 3px;
        height: 50px;
        text-align: center;
        font-size: 30px;
        font-family: "Helvetica Neue", Helvetica, "PingFang SC", "Hiragino Sans GB",
        "Microsoft YaHei", "微软雅黑", Arial, sans-serif;
        color: coral;
        padding-top: 50px;
        padding-bottom: 30px;
    }

    .homeRouterView {
        margin-top: 20px;
    }

    .el-icon-bell {
        cusor: pointer;
        color: rgb(196, 196, 196);

    }
</style>