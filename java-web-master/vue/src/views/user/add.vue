<template>
    <div style="width: 95%">
        <div style="margin-bottom: 30px">新增用户</div>
        <el-form :inline="true" :model="form" label-width="100px" :rules="rules" ref="ruleForm">
            <el-form-item label="用户名" prop="username">
                <el-input v-model="form.username" placeholder="请输入用户名"></el-input>
            </el-form-item>
            <el-form-item label="姓名" prop="name">
                <el-input v-model="form.name" placeholder="请输入姓名"></el-input>
            </el-form-item>
            <el-form-item label="性别" prop="sex">
                <el-select v-model="form.sex" placeholder="请选择性别">
                    <el-option label="男" value="男"></el-option>
                    <el-option label="女" value="女"></el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="身份证" prop="personNumber">
                <el-input v-model="form.personNumber" placeholder="请输入身份证号码" style=""></el-input>
            </el-form-item>
            <el-form-item label="联系方式" prop="phone">
                <el-input v-model="form.phone" placeholder="请输入联系方式" style=""></el-input>
            </el-form-item>
            <el-form-item label="邮箱" prop="email">
                <el-input v-model="form.email" placeholder="请输入邮箱" style=""></el-input>
            </el-form-item>
            <el-form-item label="身份" prop="role">
                <el-select v-model="form.role" placeholder="请选择身份">
                    <el-option label="用户" value="用户"></el-option>
                    <el-option label="商家" value="商家"></el-option>
                </el-select>
            </el-form-item>
        </el-form>
        <div style="text-align: center; margin-top: 30px">
            <el-button type="primary" @click="save" size="medium">提交</el-button>
        </div>
    </div>
</template>

<script>

    import request from "../../utils/request";

    export default {
        name: "add",
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
                //表单校验规则
                rules: {
                    username: [
                        { required: true, message: '请输入用户名', trigger: 'blur'}
                    ],
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
                    role: [
                        { required: true, message: '请选择身份', trigger: 'blur'}
                    ],
                }
            }
        },
        methods: {
            save() {
                this.$refs['ruleForm'].validate((valid) => {
                    if (valid) {
                        request.post("/user/add",this.form).then(res => {
                            if (res.code === '200'){
                                this.$notify.success("新增成功");
                                //this.form = {};       这两种方式都是用来清空form
                                this.$refs['ruleForm'].resetFields();
                                this.$router.push('/user/list');
                            } else {
                                this.$notify.error(res.msg);
                            }
                        })
                    }
                })
            }
        }
    }
</script>

