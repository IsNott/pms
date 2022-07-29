<template>
    <div>
        <el-container direction="vertical" >
            <el-header class="homeHeader">
                <div class="title">
                    人事管理系统
                </div>
                <el-dropdown class="userInfo" @command="commandHandler">
                     <span class="el-dropdown-link">
                      {{user.username}}
                     <i class="el-icon-arrow-down el-icon--right">
                         <el-avatar :src="userface" shape="square" :size="50"  ></el-avatar>
                     </i>
                     </span>
                    <el-dropdown-menu slot="dropdown" >
                        <el-dropdown-item command="home" v-if="this.$router.currentRoute.path!=='/home'">首页</el-dropdown-item>
                        <el-dropdown-item command="userCenter" v-if="this.$router.currentRoute.path!=='/userCenter'">个人中心</el-dropdown-item>
                        <el-dropdown-item  command="setting" v-if="this.$router.currentRoute.path!=='/setting'">设置</el-dropdown-item>
                        <el-dropdown-item  command="logout" divided>
                          退出登录
                        </el-dropdown-item>
                    </el-dropdown-menu>
                </el-dropdown>
            </el-header>
        
        </el-container>
    </div>
    
</template>

<script>
export default {
        name: "Home",
        data(){
            return{
                userface: '/pms/user/face',
                user: JSON.parse(window.sessionStorage.getItem('user'))
            }
        },
        methods:{
            commandHandler(command){
                if (command==="logout"){
                    this.$confirm('此操作将退出登录, 是否继续?', '提示', {
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        type: 'warning'
                    }).then(() => {
                        this.postRequest('/pms/user/logout').then(resp=>{
                            if (resp)
                            {
                               // window.sessionStorage.removeItem('tokenStr');
                                window.sessionStorage.removeItem('user');
                                this.$store.commit('initRoutes',[]);
                                this.$router.replace('/')
                            }
                        })
                    }).catch(() => {
                        this.$message({
                            type: 'info',
                            message: '已取消操作'
                        });
                    });
                }
                else if (command==='setting')
                {
                    this.$router.replace('/setting')
                }
                else if (command==='userCenter'){
                    this.$router.replace('/userCenter')
                }
                 else if (command==='home'){
                    this.$router.replace('/home')
                }
            },
        },
        computed:{
            routes(){
                return this.$store.state.routes;
            }
        }
    }
</script>

<style scoped>
    .homeHeader{
        background: rgb(87, 85, 82);
        display: flex;
        align-items: center;
        justify-content: space-between;
        padding: 0 15px;
        box-sizing: border-box;
        margin-bottom: 30px;

    }
    .title{
        font-size: 25px;
        font-family: "Helvetica Neue", Helvetica, "PingFang SC", "Hiragino Sans GB", "Microsoft YaHei", "微软雅黑", Arial, sans-serif;
        color: white;

    }
    .menu{
        font-family: "Helvetica Neue", Helvetica, "PingFang SC", "Hiragino Sans GB", "Microsoft YaHei", "微软雅黑", Arial, sans-serif;
    }

    .userInfo{
        cursor: pointer;
        color: rgb(196, 196, 196);
        margin-top: 5px;
        font-size: 20px;
    }
    .el-dropdown-link img{
        width: 55px;
        margin-left: 5px;
        margin-top: 5px;
        height: 55px;
    }
    .homeRouterView{
        margin-top: 20px;
    }
</style>