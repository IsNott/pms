<template>
<div>
  <el-row>
    <el-col :span="6">
      <!--   头像区域   -->
      <div class="avatar_box">
        <img src="../assets/img/logo/logo.jpg" alt="">
        <el-button @click="dialogVisible=true">修改头像</el-button>
      </div>

    </el-col>
    <el-col :span="10">
      性别：<el-input class="detail" v-model="detail.sex" placeholder="请输入性别"></el-input>
      昵称：<el-input class="detail" v-model="detail.name" placeholder="请输入昵称"></el-input>
      邮箱：<el-input class="detail" v-model="detail.email" placeholder="请输入邮箱"></el-input>
      签名：<el-input class="detail" v-model="detail.getname" placeholder="请输入签名"></el-input>
      <el-button class="reDetail">修改信息</el-button>
    </el-col>
  </el-row>
  <div class="footerBtn">

    <!-- 弹出层-裁剪 -->
    <el-dialog title="编辑头像" :visible.sync="dialogVisible" :before-close="handleClose">
      <span>
        <el-row>
          <input
              ref="filElem"
              type="file"
              id="uploads"
              accept="image/png, image/jpeg, image/gif, image/jpg"
              @change="uploadImg($event,1)"
              class="el-button hide"
              style="margin-bottom:15px"
          />
          <div class="upload-img" @click="clickUpload">点击上传图片</div>
        </el-row>
        <el-row>
          <el-col :span="14">
            <!-- 裁剪 -->
            <vueCropper
                style="width:100%;height:300px"
                ref="cropper"
                :img="attach.customaryUrl"
                :autoCrop="options.autoCrop"
                :fixedBox="options.fixedBox"
                :canMoveBox="options.canMoveBox"
                :autoCropWidth="options.autoCropWidth"
                :autoCropHeight="options.autoCropHeight"
                :centerBox="options.centerBox"
                @realTime="realTime"
            ></vueCropper>
          </el-col>
          <el-col :span="6.5">
            <h2 align="center" class=" mar-left-50">头像预览</h2>
            <div class="show-preview">
              <div :style="previews.div" class="preview">
                <img :src="previews.url" :style="previews.img" width="100%" />
              </div>
            </div>
          </el-col>
        </el-row>
        <el-row class="footerBtn" align="center">
          <el-button type="primary " size="medium" round @click="confirm('blob')">确认</el-button>
          <el-button type="info" size="medium" round @click="handleClose">取消</el-button>
        </el-row>
      </span>
    </el-dialog>
  </div>

  <div>

  </div>
</div>
</template>

<script>
//数据库里需要存两份图片地址，一张为原图地址，一张为裁剪后的头像地址
export default {
  data() {
    return {
      detail:{
        sex:'男',
        name:'zwl',
        email:'10000ssss',
        getname:'ddddddccccc'
    },
      dialogVisible: false,
      options: {
        autoCrop: true, //默认生成截图框
        fixedBox: true, //固定截图框大小
        canMoveBox: false, //截图框不能拖动
        centerBox: false, //截图框被限制在图片里面
        autoCropWidth:200,  //截图框宽度
        autoCropHeight:200, //截图框高度
      },
      previews: {}, //实时预览图数据
      attach: {
        //后端附件表
        id: "",
        userId: "",
        customaryUrl: "", //原图片路径
        laterUrl: "", //裁剪后图片路径  /static/logo.png
        attachType: "photo" //附件类型
      },
    };
  },
  methods: {
    //控制弹出层关闭
    handleClose() {
      this.dialogVisible = false;
    },
    //实时预览
    realTime(data) {
      this.previews = data;
    },
    //点击图片调取input
    clickUpload(){
      this.$refs.filElem.dispatchEvent(new MouseEvent('click'))
    },
    //选择本地图片
    uploadImg(e, num) {
      var file = e.target.files[0];
      if (!/\.(gif|jpg|jpeg|png|bmp|GIF|JPG|PNG)$/.test(e.target.value)) {
        this.$message.error("图片类型必须是.gif,jpeg,jpg,png,bmp中的一种");
        return false;
      }
      //fileReader 接口，用于异步读取文件数据
      var reader = new FileReader();
      reader.readAsDataURL(file); //重要 以dataURL形式读取文件
      reader.onload = e => {
        // data = window.URL.createObjectURL(new Blob([e.target.result])) 转化为blob格式
        let data = e.target.result;
        this.attach.customaryUrl = data;
        // 转化为base64
        // reader.readAsDataURL(file)
        // 转化为blob
      };
    },
    //确认截图,上传
    confirm(type) {
      this.$refs.cropper.getCropData(res => {
        console.log(res)//这里截图后的url 是base64格式 让后台转下就可以

      });
    }
  }
};
</script>
<style lang="less" scoped>
/* 弹性布局 水平居中 */
.el-dialog{
  width: 700px !important;
  height: auto;
}
.show-preview {
  display: flex;
  justify-content: center;
}
.preview {
  border-radius: 50%;
  overflow: hidden;
  border: 1px solid #cccccc;
  background: #cccccc;
  width: 150px !important;
  height: 150px !important;
  margin-left: 50px;
  margin-top: 50px;
}
.upload-img{
  width: 100px;
  height: 30px;
  border:1px solid #f08512;
  margin-bottom: 5px;
  line-height: 30px;
  text-align: center;
  cursor: pointer;
}
.footerBtn {
  display: flex;
  justify-content: center;
  margin-top: 15px;
}
.avatar_box{
  height: 130px;
  width: 130px;
  border: 1px solid #eee;
  border-radius: 50%;
  padding:3px;
  box-shadow: 0 0 4px #ddd;
  margin-left: 20px;
  background-color: #fff;
img{
  width: 100%;
  height: 100%;
  border-radius: 50%;
}
}

.el-button--default{
  position: relative;
  margin-top:5px;
  left: 20px;
}
.detail{
 margin-top: 5px;
  width: 400px;
}
.reDetail{
  margin-left: 18px;
}
</style>

