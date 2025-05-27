<template>
    <div style="height: 100vh; position: relative; overflow: hidden">
        <div style="width: 650px; height: 500px; background-color: white; border-radius: 10px; margin: 150px auto; padding:50px">
            <div style="margin: 30px; text-align: center; font-size: 30px; font-weight: bold; color: dodgerblue">欢 迎 注 册</div>
            <el-form :model="user" :rules="rules" ref="ruleForm">
                <el-form-item prop="username">
                    <el-input placeholder="请输入用户名" prefix-icon="el-icon-user" size="medium" v-model="user.username"></el-input>
                </el-form-item>
                <el-form-item prop="password">
                    <el-input type="password" show-password="show-password" placeholder="请输入密码" prefix-icon="el-icon-lock" size="medium" v-model="user.password"></el-input>
                </el-form-item>
                <el-form-item prop="password2">
                    <el-input type="password" show-password="show-password" placeholder="请确认密码" prefix-icon="el-icon-lock" size="medium" v-model="user.password2"></el-input>
                </el-form-item>
                <el-form-item prop="phone">
                    <el-input placeholder="请输入电话号码" prefix-icon="el-icon-phone" size="medium" v-model="user.phone"></el-input>
                </el-form-item>
                <el-form-item prop="email">
                    <el-input placeholder="请输入邮箱" prefix-icon="el-icon-coin" size="medium" v-model="user.email"></el-input>
                </el-form-item>
                <el-form-item style="text-align: center">
                    <el-button style="width: 60%" size="medium" type="primary" @click="enroll">注册</el-button>
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>

<script>
    import request from "../../utils/request";

    export default {
        name: "enroll",
        data() {
            //自定义表单校验

            const checkTxt = (rule, value, callback) => {
                if (value != null && value !== '') {
                    if (value.indexOf(' ') !== -1) {
                        callback(new Error('输入中不能包含空格'));
                    } else {
                        callback();
                    }
                } else {
                    callback(new Error('请输入用户名'));
                }
            };

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

            const checkPassword = (rule, value, callback) => {
                if (!/^(?![\d]+$)(?![a-zA-Z]+$)(?![^\da-zA-Z]+$)([^\u4e00-\u9fa5\s]){6,20}$/.test(value)) {
                    callback(new Error('请输入6-20位英文字母、数字或者符号（除空格），且字母、数字和标点符号至少包含两种'));
                } else {
                    callback();
                }
            };


            return {
                user: {},
                rules: {
                    username: [
                        { validator: checkTxt, trigger: 'blur' }
                    ],
                    password: [
                        { validator: checkPassword, trigger: 'blur' }
                    ],
                    password2: [
                        { validator: checkPassword, trigger: 'blur' }
                    ],
                    phone: [
                        { validator: checkPhone, trigger: 'blur' }
                    ],
                    email: [
                        { validator: checkEmail, trigger: 'blur' }
                    ],
                }
            }
        },
        methods: {
            enroll() {
                this.$refs['ruleForm'].validate((valid) => {
                    if (valid) {
                        if (this.user.password === this.user.password2){
                            request.post("/user/enroll",this.user).then(res => {
                                if (res.code === '200'){
                                    this.$notify.success("注册成功!");
                                    this.$router.push('/login');
                                } else {
                                    this.$notify.error(res.msg);
                                }
                            })
                        } else {
                            this.$notify.error("请检查，两次密码输入不一致");
                        }
                    }
                });
            },
        }
    }
</script>

<style scoped>
    .cover {
        width: fit-content;
        background-color: white;
        position: absolute;
        top:50%;
        left:50%;
        transform: translate(-50%, -50%);
        z-index: 1000;
    }
</style>