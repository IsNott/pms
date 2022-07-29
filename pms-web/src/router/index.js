import Vue from 'vue'
import Router from 'vue-router'
import Login from "../views/Login";
import Home from "../views/Home";
import UserCenter from "../views/UserCenter";
import Setting from "../views/Setting";
import ErrorPage from "../views/ErrorPage";
import EmpBasic from "../views/emp/EmpBasic";
import MsgCenter from "../views/msg/MsgCenter";
import DeptList from "../views/dept/DeptList"
import MsgList from "../views/msg/MsgList"
import EmpAdd from "../views/emp/EmpAdd"
import EmpAdv from "../views/emp/EmpAdv";
import UserMsg from "../views/msg/UserMsg"
import UserList from "../views/user/UserList";
import AdminList from "../views/sys/SysAdmin"
import SalEmp from "../views/sal/SalEmp";
import FileList from "../views/file/FileList"
import AttList from "../views/att/AttList"
import HrCenter from "../views/HrCenter"
import SysMenu from "../views/sys/SysMenu"
import EmpDetail from "../views/EmpDetail"
import DeptAdd from "../views/dept/DeptAdd";
import AnnouSend from "../views/msg/AnnouSend";
import SysAuth from "../views/sys/SysAuth"
import FileCenter from "../views/FileCenter";
import Audit from "../views/Audit";

Vue.use(Router);

export default new Router({
    routes: [
        {
            path: '/',
            name: 'Login',
            component: Login,
            hidden: true
        },

        {
            path: '/home',
            name: '导航一',
            children: [
                {
                    path: 'emp/list',
                    name: "员工列表",
                    component: EmpBasic,

                },
                {
                    path: '/userCenter',
                    name: '个人中心',
                    component: UserCenter
                },
                {
                    path: '/setting',
                    name: '设置密码',
                    component: Setting
                },
                {
                    path: '/msgCenter',
                    name: '信息中心',
                    component: MsgCenter
                },
                {
                    path: 'dept/list',
                    name: '部门列表',
                    component: DeptList
                },
                {
                    path: 'msg/list',
                    name: '信息列表',
                    component: MsgList
                },
                {
                    path: 'emp/add',
                    name: '新增员工',
                    component: EmpAdd
                },
                {
                    path: '/emp/detail/:id',
                    name: "高级资料",
                    component: EmpAdv
                },
                {
                    path: '/msg/UserMsg/:type',
                    name: "用户未读信息列表",
                    component: UserMsg
                },
                {
                    path: 'user/list',
                    name: '用户列表',
                    component: UserList
                },
                {
                    path:'sys/admin',
                    name:'管理员列表',
                    component: AdminList
                },
                {
                    path:'sal/emp',
                    name:'员工薪资表',
                    component: SalEmp
                },
                {
                    path:'file/list',
                    name:'文件列表',
                    component: FileList
                },
                {
                    path:'att/list',
                    name:'考勤列表',
                    component: AttList
                },
                {
                    path:'/fileCenter',
                    name:'文件中心',
                    component: FileCenter
                },
                {
                    path:'/hrCenter',
                    name:'人事中心',
                    component: HrCenter
                },
                {
                    path:'sys/menu',
                    name:'菜单列表',
                    component: SysMenu
                },
                {
                    path:'/empDetail/:id',
                    name:'我的详细信息',
                    component: EmpDetail
                },
                {
                    path:'dept/add',
                    name:'新建部门',
                    component:DeptAdd
                },
                {
                    path:'msg/annou',
                    name:'新建部门',
                    component:AnnouSend
                },
                {
                    path:'sys/auth',
                    name:'角色授权',
                    component:SysAuth
                },{
                    path: '/audit',
                    name:'审核处理',
                    component: Audit
                }
            ],
            component: Home
        },

        {
            path: '/errorPage',
            name: '错误页面',
            component: ErrorPage
        }

    ]
})

const VueRouterPush = Router.prototype.push
Router.prototype.push = function push (to) {
    return VueRouterPush.call(this, to).catch(err => err)
}

