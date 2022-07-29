import {getRequest} from "./axiosUtils";
import {postRequest} from "./axiosUtils";
import {appRequest} from "./axiosUtils";
import da from "element-ui/src/locale/lang/da";

export const initMenu = (router, store) => {
    console.log('store.state.routes============');
    console.log(store.state.routes)
    if (store.state.routes.length > 0) {
        return;
    }
    postRequest("/pms/menu").then(resp => {
        if (resp.code == 0) {
            // var data = JSON.stringify(resp.data);
            var data = resp.data;
            console.info(data);
            let fmtRoutes = formatRoutes(data);
            window.sessionStorage.setItem("tagList", data.children);
            //router.addRoutes(fmtRoutes);
            store.commit('initRoutes', fmtRoutes);
        }
    }).catch();


}
export const formatRoutes = (routes) => {
    let fmRoutes = [];
    routes.forEach(router => {
        let {
            path,
            component,
            name,
            icon,
            children
        } = router;
        if (children && children instanceof Array) {
            children = formatRoutes(children);
        }
        let fmRouter = {
            path: path,
            name: name,
            icon: icon,
           // component: component,
            children: children,
            component(resolve) {
                if (component.startsWith("Home")) {
                    require(['../views/' + component + '.vue'], resolve);
                } else if (component.startsWith("Emp")) {
                    require(['../views/emp/' + component + '.vue'], resolve);
                } else if (component.startsWith("Sal")) {
                    require(['../views/sal/' + component + '.vue'], resolve);
                } else if (component.startsWith("Sys")) {
                    require(['../views/sys/' + component + '.vue'], resolve);
                } else if (component.startsWith('User')) {
                    require(['../views/user/' + component + '.vue'], resolve);
                } else if (component.startsWith('Dept')) {
                    require(['../views/dept/' + component + '.vue'], resolve);
                } else if (component.startsWith('Msg')) {
                    require(['../views/msg/' + component + '.vue'], resolve);
                } else if (component.startsWith('Att')) {
                    require(['../views/att/' + component + '.vue'], resolve);
                }
            }
        }
        fmRoutes.push(fmRouter);
    })
    return fmRoutes;
}