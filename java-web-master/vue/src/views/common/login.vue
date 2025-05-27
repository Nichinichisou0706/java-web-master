<template>
    <div style="height: 100vh; position: relative; overflow: hidden">
        <el-card class="cover" v-if="loginUser.id">
            <slide-verify :l="42"
                          :r="10"
                          :w="310"
                          :h="155"
                          :accuracy="5"
                          :imgs="['http://localhost:9090/files/3.jpg',
                    'http://localhost:9090/files/4.png',
                    'http://localhost:9090/files/5.jpg']"
                          slider-text="向右滑动"
                          @success="onSuccess"
                          @fail="onFail"
                          @refresh="onRefresh"
            ></slide-verify>
        </el-card>
        <div style="width: 500px; height: 350px; background-color: papayawhip; border-radius: 10px; margin: 150px auto; padding:50px">
            <div style="margin: 30px; text-align: center; font-size: 30px; font-weight: bold; color: dodgerblue">欢 迎 登 录</div>
            <el-form :model="user" ref="loginForm" :rules="rules">
                <el-form-item prop="username">
                    <el-input placeholder="请输入账号" prefix-icon="el-icon-user" size="medium" v-model="user.username"></el-input>
                </el-form-item>
                <el-form-item prop="password">
                    <el-input type="password" show-password="show-password" placeholder="请输入密码" prefix-icon="el-icon-lock" size="medium" v-model="user.password"></el-input>
                </el-form-item>
                <div>{{msg}}</div>
                <el-form-item>
                    <el-button style="width: 35%; margin-left: 40px" size="medium" type="primary" @click="$router.push('/enroll')">注册</el-button>
                    <el-button style="width: 35%; margin-left: 20px" size="medium" type="primary" @click="login">登录</el-button>
                </el-form-item>
                <a href="javascript:void 0" @click="forgetPassword" style="float: right; margin-right: 5px">找回密码</a>
            </el-form>
        </div>


        <el-dialog title="找回密码" :visible.sync="dialogFormVisible" width="30%" >
            <el-form :model="form" ref="passForm" :rules="passRules" label-width="100px" size="small">
                <el-form-item label="用户名" prop="username">
                    <el-input size="medium" v-model="form.username" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="邮箱" prop="email">
                    <el-input size="medium" v-model="form.email" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="验证码">
                    <el-input size="medium"  style="width: 200px" v-model="form.code"></el-input>
                    <el-button type="primary" style="margin-left: 5px" size="medium" class="ml-5" @click="sendEmailCode">获取验证码</el-button>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="resetPassword">重置密码</el-button>
            </div>
        </el-dialog>


    </div>
</template>

<script>
    import request from "../../utils/request";
    import Cookies from 'js-cookie';

    export default {
        name: "login",
        data() {

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

            return {
                loginUser: {},
                user: {},
                form: {},
                code: '',
                username: '',
                dialogFormVisible: false,
                rules: {
                    username: [
                        { required: true, message: '请输入用户名', trigger: 'blur'}
                    ],
                    password: [
                        { required: true, message: '请输入密码', trigger: 'blur'}
                    ],
                },
                passRules: {
                    username: [
                        { required: true, message: '请输入用户名', trigger: 'blur'}
                    ],
                    email: [
                        { validator: checkEmail, trigger: 'blur' }
                    ],
                },
            }
        },
        created() {
          this.form = {};
          this.code = {};
          this.username = {};
        },
        methods: {
            login() {
                //如果上个用户不是通过退出系统退出的，ta的cookie有可能还在浏览器中
                if (Cookies.get('user') != null){
                    Cookies.remove('user');
                }
                this.$refs['loginForm'].validate((valid) => {
                    if (valid) {
                        request.post("/user/login",this.user).then(res => {
                            if (res.code === '200'){
                                if (res.data != null){
                                    this.loginUser = res.data;  // 滑块组件就出现了
                                }
                            } else {
                                this.$notify.error(res.msg);
                            }
                        })
                    }
                })
            },

            onSuccess() { // 滑块验证通过之后触发的
                Cookies.set('user', JSON.stringify(this.loginUser));
                alert("登陆成功,欢迎使用!");
                this.$router.push('/')
            },

            onFail() {
                console.log('onFail')
            },

            onRefresh() {
                console.log('refresh')
            },

            forgetPassword() {
                this.form = {};
                this.username = {};
                this.dialogFormVisible = true;
            },

            sendEmailCode() {
                this.$refs['passForm'].validate((valid) => {
                    if (valid) {
                        this.$notify.success("验证码正在发送，请耐心等待~~");
                        request.post("/user/remind",this.form).then(res => {
                            if (res.code === '200'){
                                this.code = res.data.substring(0,4);
                                this.username = res.data.substring(4);
                                this.$notify.success("验证码发送成功，请耐心等待~~");
                            } else {
                                this.$notify.error(res.msg);
                            }
                        })
                    }
                })
            },

            resetPassword() {
                this.$refs['passForm'].validate((valid) => {
                    if (valid) {
                        if (this.code === this.form.code && this.username === this.form.username) {
                            request.post("/user/reset",this.form).then(res => {
                                if (res.code === '200'){
                                    this.$notify.success("密码重置成功，为了账户安全，请登录后进入个人信息页面修改密码~");
                                    this.form = {};
                                    this.dialogFormVisible = false;
                                } else {
                                    this.$notify.error(res.msg);
                                }
                            })
                        } else {
                            this.$notify.error("验证码不正确哦~");
                        }
                    }
                })
            }
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