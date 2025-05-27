<template>
    <div>

        <!--搜索框-->
        <div style="margin-bottom: 15px">
            <el-input style="width: 240px" placeholder="请输入景点代码" v-model="params.courseCode"></el-input>
            <el-input style="width: 240px; margin-left: 5px" placeholder="请输入景点名" v-model="params.courseName"></el-input>
            <el-button style="margin-left: 5px" type="primary" @click="load"><i class="el-icon-search"></i>&ensp;搜索</el-button>
            <el-button style="margin-left: 5px" type="warning" @click="reset"><i class="el-icon-refresh"></i>&ensp;重置</el-button>
        </div>

        <el-table :data="tableData" border stripe>
            <el-table-column prop="teacherName" label="商家名" ></el-table-column>
            <el-table-column prop="courseName" label="景点名"></el-table-column>
            <el-table-column prop="courseCode" label="景点代码" ></el-table-column>
            <el-table-column prop="day" label="时间" ></el-table-column>
            <el-table-column prop="time" label="时间" ></el-table-column>
            <el-table-column prop="begin" label="起始" ></el-table-column>
            <el-table-column prop="end" label="结束" ></el-table-column>
            <el-table-column prop="roomName" label="地点"></el-table-column>
            <el-table-column prop="now" label="已购人数"></el-table-column>
            <el-table-column prop="max" label="限购人数"></el-table-column>
            <el-table-column fixed="right" label="操作" width="150px">
                <template #default="scope">
                    <el-button size="mini" type="primary" v-if="scope.row.now < scope.row.max" @click="handleEdit(scope.row.id)">选购</el-button>
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

    </div>

</template>

<script>

    import request from "../../utils/request";
    import Cookies from 'js-cookie';

    export default {
        name: "searchList",
        data() {
            return {
                tableData: [],
                user: Cookies.get('user') ? JSON.parse(Cookies.get('user')) : {},
                total: '',
                params: {
                    username: '',
                    courseCode:'',
                    courseName:'',
                    pageNum: 1,
                    pageSize: 10,
                },
                searchCourseParams: {
                    teachId: '',
                    studentId: '',
                }
            }
        },

        //页面加载之后会调用
        created() {
            this.user = Cookies.get('user') ? JSON.parse(Cookies.get('user')) : {};
            this.params.username = this.user.username;
            this.load();
        },

        methods: {

            load() {
                request.get("/stu/searchList", { params: this.params }).then(res => {
                    if (res.code === '200'){
                        this.tableData = res.data.list;
                        this.total = res.data.total;
                    } else {
                        alert(res.msg);
                    }
                })
            },

            reset() {
                this.params.courseCode = '';
                this.params.courseName = '';
                this.padding.pageNum = 1;
                this.padding.pageSize = 10;
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

            handleEdit(id){
                this.searchCourseParams.teachId = id;
                this.searchCourseParams.studentId = this.user.username;
                request.post("/stu/add", this.searchCourseParams).then(res => {
                    if (res.code === '200'){
                        this.$notify.success("恭喜您购票成功~");
                        this.load();
                    } else {
                        alert(res.msg);
                    }
                })
            },

        }
    }
</script>

<style scoped>

</style>