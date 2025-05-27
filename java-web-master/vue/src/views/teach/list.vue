<template>
    <div>

        <!--搜索框-->
        <div style="margin-bottom: 15px">
            <el-input style="width: 240px" placeholder="请输入商家号" v-model="params.teacherId"></el-input>
            <el-input style="width: 240px; margin-left: 5px" placeholder="请输入景点代码" v-model="params.courseCode"></el-input>
            <el-button style="margin-left: 5px" type="primary" @click="load"><i class="el-icon-search"></i>&ensp;搜索</el-button>
            <el-button style="margin-left: 5px" type="warning" @click="reset"><i class="el-icon-refresh"></i>&ensp;重置</el-button>
            <el-button style="margin-left: 5px" type="success" @click="add"><i class="el-icon-add-location"></i>&ensp;新增票务</el-button>
        </div>

        <el-table :data="tableData" border stripe>
            <el-table-column prop="teacherId" label="商家号"></el-table-column>
            <el-table-column prop="teacherName" label="商家姓名" ></el-table-column>
            <el-table-column prop="courseName" label="景点名"></el-table-column>
            <el-table-column prop="courseCode" label="景点代码" ></el-table-column>
            <el-table-column prop="day" label="时间" ></el-table-column>
            <el-table-column prop="time" label="进入时间" ></el-table-column>
            <el-table-column prop="begin" label="起始" ></el-table-column>
            <el-table-column prop="end" label="结束" ></el-table-column>
            <el-table-column prop="roomName" label="地点"></el-table-column>
            <el-table-column prop="now" label="已购人数"></el-table-column>
            <el-table-column prop="max" label="限购人数"></el-table-column>
            <el-table-column fixed="right" label="操作">
                <template #default="scope">
                    <el-popconfirm title="您确定要删除吗?" @confirm="handleDelete(scope.row.id)">
                        <template #reference>
                            <el-button size="mini" type="danger">删除</el-button>
                        </template>
                    </el-popconfirm>
                </template>
            </el-table-column>
        </el-table>

        <!--分页-->
        <div style="margin-top: 20px">
            <el-pagination
                    background
                    @size-change="handleSizeChange"
                    @current-change="handleCurrentChange"
                    layout="total, sizes, prev, pager, next, jumper"
                    :current-page="params.pageNum"
                    :page-sizes="[5, 10, 20]"
                    :page-size="params.pageSize"
                    :total="total">
            </el-pagination>
        </div>

        <el-dialog title="新增景点" :visible.sync="dialogVisible" width="30%">
            <el-form :model="form" label-width="120px" :rules="rules" ref="ruleForm">
                <el-form-item label="商家名字" prop="teacherName">
                    <el-input v-model="form.teacherName" readonly style="width: 75%;"></el-input>
                </el-form-item>
                <el-form-item label="商家号" prop="teacherId">
                    <el-select v-model="form.teacherId" clearable filterable placeholder="请选择" @change="setTeacher">
                        <el-option
                                v-for="item in teachers"
                                :key="item.id"
                                :label="item.username"
                                :value="item.username">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="景点名" prop="courseName">
                    <el-input v-model="form.courseName" readonly style="width: 75%;"></el-input>
                </el-form-item>
                <el-form-item label="景点代码" prop="courseCode">
                    <el-select v-model="form.courseCode" clearable filterable placeholder="请选择" @change="setCourse">
                        <el-option
                                v-for="item in courses"
                                :key="item.id"
                                :label="item.courseCode"
                                :value="item.courseCode">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="起始">
                    <el-input v-model="form.begin" readonly style="width: 30%;"></el-input>
                </el-form-item>
                <el-form-item label="结束">
                    <el-input v-model="form.end" readonly style="width: 30%;"></el-input>
                </el-form-item>
                <el-form-item label="星期几" prop="day">
                    <el-select v-model="form.day" placeholder="请选择周几">
                        <el-option label="周一" value="周一"></el-option>
                        <el-option label="周二" value="周二"></el-option>
                        <el-option label="周三" value="周三"></el-option>
                        <el-option label="周四" value="周四"></el-option>
                        <el-option label="周五" value="周五"></el-option>
                        <el-option label="周六" value="周六"></el-option>
                        <el-option label="周七" value="周七"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="开放时间" prop="time">
                    <el-select v-model="form.time" placeholder="请选择开放时间">
                        <el-option label="8:00-10:00" value="8:00-10:00"></el-option>
                        <el-option label="10:00-12:00" value="10:00-12:00"></el-option>
                        <el-option label="12:00-14:00" value="12:00-14:00"></el-option>
                        <el-option label="14:00-16:00" value="14:00-16:00"></el-option>
                        <el-option label="16:00-18:00" value="16:00-18:00"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="地点" prop="roomId">
                    <el-select v-model="form.roomId" clearable filterable placeholder="请选择">
                        <el-option
                                v-for="item in rooms"
                                :key="item.id"
                                :label="item.name"
                                :value="item.id">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="限购人数" prop="max">
                    <el-input v-model="form.max" style="width: 30%;"></el-input>
                </el-form-item>
            </el-form>
            <template #footer>
          <span class="dialog-footer">
            <el-button @click="dialogVisible = false">取消</el-button>
            <el-button type="primary" @click="save">确认</el-button>
          </span>
            </template>
        </el-dialog>

    </div>

</template>

<script>

    import request from "../../utils/request";

    export default {
        name: "list",
        data() {

            const checkNumber = (rule, value, callback) => {
                if (!value) {
                    return callback(new Error('输入不可以为空'));
                }
                setTimeout(() => {
                    if (!Number(value)) {
                        callback(new Error('请输入正整数'));
                    } else {
                        const re = /^[1-9][0-9]{0,1}$/;
                        const rsCheck = re.test(value);
                        if (!rsCheck) {
                            callback(new Error('请输入正整数，值为【1,99】'));
                        } else {
                            callback();
                        }
                    }
                }, 0);
            };


            return {
                total: '',
                dialogVisible: false,
                tableData: [],
                rooms: [],
                courses: [],
                teachers: [],
                form: {},
                params: {
                    teacherId: '',
                    courseCode:'',
                    pageNum: 1,
                    pageSize: 10,
                },
                rules: {
                    day: [
                        { required: true, message: '请选择周几', trigger: 'blur'}
                    ],
                    time: [
                        { required: true, message: '请选择时间', trigger: 'blur'}
                    ],
                    teacherId: [
                        { required: true, message: '请选择商家号', trigger: 'blur'}
                    ],
                    courseCode: [
                        { required: true, message: '请选择景点代码', trigger: 'blur'}
                    ],
                    roomId: [
                        { required: true, message: '请选择地点', trigger: 'blur'}
                    ],
                    max: [
                        { validator: checkNumber, trigger: 'blur' }
                    ],
                }
            }
        },
        //页面加载之后会调用
        created() {
            this.init();
            this.load();
        },
        methods: {

            init() {
                request.get('/teach/room/list').then(res => {
                    this.rooms = res.data;
                });
                request.get('/user/teacher/list').then(res => {
                    this.teachers = res.data;
                });
                request.get('/course/list').then(res => {
                    this.courses = res.data;
                });
            },

            setTeacher(){
                let teacher = this.teachers.find(item => item.username === this.form.teacherId);
                request.get('/user/' + teacher.id).then(res => {
                    this.$set(this.form, 'teacherName', res.data.name);
                })
            },

            setCourse(){
                let course = this.courses.find(item => item.courseCode === this.form.courseCode);
                request.get('/course/' + course.id).then(res => {
                    this.$set(this.form, 'courseName', res.data.courseName);
                    this.$set(this.form, 'begin', res.data.begin);
                    this.$set(this.form, 'end', res.data.end);
                })
            },

            load() {
                request.get("/teach/page", { params: this.params }).then(res => {
                    if (res.code === '200'){
                        this.tableData = res.data.list;
                        this.total = res.data.total;
                    } else {
                        alert(res.msg);
                    }
                })
            },

            reset() {
                this.params = {
                    teacherId: '',
                    courseCode:'',
                    pageNum: 1,
                    pageSize: 10,
                };
                this.load();
            },

            handleCurrentChange(pageNum){
                this.params.pageNum = pageNum;
                this.load();
            },

            handleSizeChange(pageSize){
                this.params.pageSize = pageSize;
                this.load();
            },

            handleDelete(id){
                request.delete('/teach/delete/' + id).then(res => {
                    if (res.code === '200'){
                        alert("删除成功");
                        this.load();
                    } else {
                        alert(res.msg);
                    }
                })
            },

            add(){
                this.form = {};
                this.dialogVisible = true;
            },

            save() {
                this.$refs['ruleForm'].validate((valid) => {
                    if (valid) {
                        request.post("/teach/add",this.form).then(res => {
                            if (res.code === '200'){
                                this.$notify.success("发布成功");
                                this.form = {};
                                this.dialogVisible = false;
                                this.load();
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

<style scoped>

</style>