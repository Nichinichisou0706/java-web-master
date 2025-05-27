<template>
    <div style="width: 60%">
        <el-form style="margin-left: 5px; margin-top: 10px" :model="form" label-width="100px" :rules="rules" ref="ruleForm">
            <el-form-item label="用户名:" prop="username">
                <el-input v-model="form.username" readonly style="width: 20%"></el-input>
            </el-form-item>
            <el-form-item label="头像:">
                    <el-image
                        style="width: 100px; height: 100px"
                        :src="this.form.cover"
                        :preview-teleported="true">
                    </el-image>
                <el-upload ref="upload" :action="'http://localhost:9090/files/upload'" :on-success="filesUploadSuccess">
                    <el-button type="primary">点击上传</el-button>
                </el-upload>
            </el-form-item>
            <el-form-item label="姓名:" prop="name">
                <el-input v-model="form.name" style="width: 20%"></el-input>
            </el-form-item>
            <el-form-item label="性别" prop="sex">
                <el-select v-model="form.sex" style="width: 15%" placeholder="请选择性别">
                    <el-option label="男" value="男"></el-option>
                    <el-option label="女" value="女"></el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="身份证号:" prop="personNumber">
                <el-input v-model="form.personNumber" style="width: 35%"></el-input>
            </el-form-item>
            <el-form-item label="电话:" prop="phone">
                <el-input v-model="form.phone" style="width: 25%"></el-input>
            </el-form-item>
            <el-form-item label="邮箱:" prop="email">
                <el-input v-model="form.email" style="width: 40%"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button style="margin-top: 10px; margin-left: 5px" type="primary" @click="save"> 更新 </el-button>
            </el-form-item>
        </el-form>
    </div>
</template>

<script>
    import request from "../../utils/request";
    import Cookies from "js-cookie";

    export default {
        name: "info",

        data() {

            //自定义表单校验
            const checkPhone = (rule, value, callback) => {
                if (!/^[1][3,4,5,6,7,8,9][0-9]{9}$/.test(value)) {
                    callback(new Error('请输入合法的手机号'));
                }
                callback();
            };

            const checkEmail = (rule, value, callback) => {
                const reg = /^([a-zA-Z0-9]+[-_\.]?)+@[a-zA-Z0-9]+\.[a-z]+$/;
                if (value === '' || value === undefined || value == null) {
                    callback(new Error('请输入邮箱'));
                } else {
                    if (!reg.test(value)) {
                        callback(new Error('请输入正确的邮箱地址'));
                    } else {
                        callback();
                    }
                }
            };

            const checkID = (rule, value, callback) => {
                if (!value) {
                    callback(new Error('身份证不能为空'))
                }
                if (! /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/.test(value)) {
                    callback(new Error('请输入正确的二代身份证号码'))
                } else {
                    callback()
                }
            };

            return {
                form: {},
                user: Cookies.get('user') ? JSON.parse(Cookies.get('user')) : {},
                rules: {
                    name: [
                        { required: true, message: '请输入姓名', trigger: 'blur'}
                    ],
                    personNumber: [
                        { validator: checkID, trigger: 'blur' }
                    ],
                    phone: [
                        { validator: checkPhone, trigger: 'blur' }
                    ],
                    email: [
                        { validator: checkEmail, trigger: 'blur' }
                    ],
                    sex: [
                        { required: true, message: '请选择性别', trigger: 'blur'}
                    ],
                }
            }
        },

        created() {
            const id = this.$route.query.id;
            this.load(id);
        },

        methods: {

            load(id){
                request.get("/user/" + id).then(res => {
                    this.form = res.data;
                });
            },

            save(){
                this.$refs['ruleForm'].validate((valid) => {
                    if (valid) {
                        request.post("/user/updateUser",this.form).then(res => {
                            if (res.code === '200'){
                                this.$notify.success("更新成功");
                                this.load(this.form.id);
                                Cookies.set('user', JSON.stringify(this.form));
                            } else {
                                this.$notify.error(res.msg);
                            }
                        })
                    }
                });
            },

            filesUploadSuccess(res){
                this.$nextTick(() => {
                    if (this.$refs['upload']){
                        this.$refs['upload'].clearFiles();  //清除历史文件列表
                    }
                });
                this.form.cover = res.data;
            },
        }
    }
</script>

<style scoped>

</style>