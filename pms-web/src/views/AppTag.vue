// AppTag组件
<template>
    <div class="app-tag">
        <el-tag 
            closable 
            size="medium" 
            v-for="(tag,index) in tags" 
            :key="tag.name" 
            :disable-transitions="true"
            :effect="$route.path === tag.url ?'dark':'plain'" 
            @close="handleClose(tag,index)" 
            @click="handleClick(tag)">
                {{tag.name}}
        </el-tag>
    </div>
</template>
<script>
    import { mapState,mapMutations } from 'vuex';
    export default {
        name: 'app-tag',
        data() {
            return {
                tags: []
            }
        },
        created() {
        //stateTagsList是state.js中的存放tags数组的key，stateTagsList的值默认为空数组
            this.tags = this.stateTagsList;
        },

        computed: {
            ...mapState(['stateTagsList'])
        },

        methods: {
            ...mapMutations({
                close: 'mutationCloseTag'
            }),
            handleClose(tag, index) {
                if (this.tags.length === 1) { // 如果只有一个标签则不能关闭
                    return
                }
                this.close(tag) // 删除当前tag
                if (this.$router.path === tag.url) { // 如果关闭的标签不是当前路由的话，不做路由跳转
                    return
                } else {
                    if (index === (this.tags.length - 1)) { // 关闭最后一个标签,则路由跳转至最后一个
                        this.$router.push(this.tags[index].url)
                    } else { // 路由跳转至下一个标签页
                        if(index===0){
                            this.$router.push(this.tags[0].url)
                        }else{
                            this.$router.push(this.tags[index - 1].url)
                        }
                    }
                }
            },
            // 点击tags具体标签
            handleClick(tag) {
                this.$router.push(tag.url)
            }
        }
    }
