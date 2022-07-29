/**
 *  -------专用表单验证----------
 *  验证:邮箱
 *  验证:身份证
 *  验证:电话
 *  验证:传真
 *  验证:图片
 *  验证:可自行扩展.....
 * @type {RegExp}
 */
 var regId = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/
 var email = /^(\w+\.?)*\w+@(?:\w+\.)\w+$/
 var tel = /^1[345789]\d{9}$/
 var fax = /^(\d{3,4}-)?\d{7,8}$/
 var username = /^[a-zA-Z0-9_]{5,20}$/
 var FormValidate = (function () {
     // eslint-disable-next-line
     function FormValidate() {}
     // From表单验证规则  可用于公用的校验部分
     FormValidate.Form = function () {
         return {
             // 姓名的验证规则
             Email: function (rule, value, callback) {
                 if (!value) {
                     return callback(new Error('邮箱不能为空'))
                 }
                 if (!email.test(value)) {
                     callback(new Error('请输入正确的邮箱!'))
                 } else {
                     callback()
                 }
             },
             // 身份证的验证规则
             ID: function (rule, value, callback) {
                 if (!value) {
                     return callback(new Error('身份证不能为空'))
                 }
                 if (!regId.test(value)) {
                     callback(new Error('请输入正确的二代身份证号码'))
                 } else {
                     callback()
                 }
             },
             Username: (rule, value, callback) => {
                if (!value) {
                    return callback(new Error('昵称不能为空'))
                }
                if (!username.test(value)) {
                    callback(new Error('请正确填写昵称,名称不能为纯数字或以下划线开始!'))
                } else {
                    callback()
                }
            },
             // 电话号码的验证
             Tel: (rule, value, callback) => {
                 if (!value) {
                     return callback(new Error('电话不能为空'))
                 }
                 if (!tel.test(value)) {
                     callback(new Error('请正确填写电话号码'))
                 } else {
                     callback()
                 }
             }   
         }
     }
 
     // FromOne表单验证规则  用于FromOne个性化的校验
     FormValidate.FormOne = function () {
         return {
             // 姓名的验证规则
             Name: function (rule, value, callback) {
                 if (!value) {
                     return callback(new Error('姓名不能为空'))
                 }
                 if (!isNaN(value)) {
                     callback(new Error('请输入正确名称,名称不能为纯数字!'))
                 } else if (value.length < 2 || value.length > 20) {
                     callback(new Error('请输入2到20个字符!'))
                 } else {
                     callback()
                 }
             },
             Img: function (rule, value, callback) {
                 if (!value) {
                     return callback(new Error('封面不能为空'))
                 } else {
                     callback()
                 }
             },
             Username: function (rule, value, callback) {
                if (!value) {
                    return callback(new Error('用户名不能为空'))
                }
                if (!isNaN(value)) {
                    callback(new Error('请输入正确昵称,名称不能为纯数字或以下划线开始!'))
                } else if (value.length < 5 || value.length > 20) {
                    callback(new Error('请输入5到20个字符!'))
                } else {
                    callback()
                }
            }

         }
     }
 
     // FromTwo表单验证规则  用于FromTwo表单个性化的校验
     FormValidate.FormTwo = function () {
         return {
             // 传真的校验规则
             Fax: (rule, value, callback) => {
                 if (!value) {
                     return callback(new Error('传真不能为空'))
                 }
                 if (!fax.test(value)) {
                     callback(new Error('请正确填写传真'))
                 } else {
                     callback()
                 }
             }
         }
     }
     return FormValidate
 }())
 exports.FormValidate = FormValidate
