<template>
    <div style="width: 95%">
        <div style="margin-bottom: 30px">编辑景点信息，请从景点管理进入该页面哦~</div>
        <el-form :inline="true" :model="form" label-width="100px" :rules="rules" ref="ruleForm">
            <el-form-item label="景点名" prop="courseName">
                <el-input v-model="form.courseName" placeholder="请输入景点名"></el-input>
            </el-form-item>
            <el-form-item label="景点代码" prop="courseCode">
                <el-input v-model="form.courseCode" placeholder="请输入景点代码"></el-input>
            </el-form-item>
            <el-form-item label="开始" prop="begin">
                <el-select v-model="form.begin" placeholder="请选择开始">
                    <el-option label="周一" value="1"></el-option>
                    <el-option label="周二" value="2"></el-option>
                    <el-option label="周三" value="3"></el-option>
                    <el-option label="周四" value="4"></el-option>
                    <el-option label="周五" value="5"></el-option>
                    <el-option label="周六" value="6"></el-option>
                    <el-option label="周日" value="7"></el-option>

                </el-select>
            </el-form-item>
            <el-form-item label="结束" prop="end">
                <el-select v-model="form.end" placeholder="请选择结束">
                    <el-option label="周一" value="1"></el-option>
                    <el-option label="周二" value="2"></el-option>
                    <el-option label="周三" value="3"></el-option>
                    <el-option label="周四" value="4"></el-option>
                    <el-option label="周五" value="5"></el-option>
                    <el-option label="周六" value="6"></el-option>
                    <el-option label="周日" value="7"></el-option>

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
            return {
                form: {},
                //表单校验规则
                rules: {
                    courseName: [
                        { required: true, message: '请输入景点名', trigger: 'blur'}
                    ],
                    courseCode: [
                        { required: true, message: '请输入景点码', trigger: 'blur'}
                    ],
                    begin: [
                        { required: true, message: '请选择开始', trigger: 'blur'}
                    ],
                    end: [
                        { required: true, message: '请选择结束', trigger: 'blur'}
                    ],
                }
            }
        },

        created() {
            const id = this.$route.query.id;
            request.get("/course/" + id).then(res => {
                this.form = res.data;
            });
        },

        methods: {
            save() {
                if (!this.form.id){
                    this.$notify.error("请从景点管理进入该页面哦~");
                } else {
                    this.$refs['ruleForm'].validate((valid) => {
                        if (valid) {
                            if (Number(this.form.begin) > Number(this.form.end)) {
                                this.$notify.error("设置不合理，请检查~");
                            } else {
                                request.post("/course/update",this.form).then(res => {
                                    if (res.code === '200'){
                                        this.$notify.success("编辑成功");
                                        //this.form = {};       这两种方式都是用来清空form
                                        this.$refs['ruleForm'].resetFields();
                                        this.$router.push('/course/list');
                                    } else {
                                        this.$notify.error(res.msg);
                                    }
                                })
                            }
                        }
                    })
                }
            }
        }
    }
</script>

