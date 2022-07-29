<template>
  <div>
    <el-aside width="200px" class="menu">
      <!--                <el-menu :default-openeds="['1', '3']" router>-->
      <el-menu router unique-opened>
        <el-submenu
          :index="index + ''"
          v-for="(item, index) in routes"
          :key="index"
          v-if="!item.hidden"
        >
          <template slot="title">
            <i
              :class="item.iconCls"
              style="color: aquamarine; margin-right: 5px"
            ></i>
            <span>{{ item.name }}</span>
          </template>
          <el-menu-item
            :index="children.path"
            v-for="(children, indexj) in item.children"
            :key="indexj"
            @click="clickMenu(children)"
          >
            {{ children.name }}
          </el-menu-item>
        </el-submenu>
      </el-menu>
    </el-aside>
  </div>
</template>

<script>
export default {
  data() {
    return {
      userface: "/pms/user/face",
      user: JSON.parse(window.sessionStorage.getItem("user")),
    };
  },
  methods: {// 点击菜单
            clickMenu(value) {
            //通过vuex将数据存储在store中
                this.$store.commit('mutationSelectTags', value)
            },
  },
  computed: {
    routes() {
      return this.$store.state.routes;
    }
  }
}
</script>


<style scoped>


.menu {
  font-family: "Helvetica Neue", Helvetica, "PingFang SC", "Hiragino Sans GB",
    "Microsoft YaHei", "微软雅黑", Arial, sans-serif;
}

</style>