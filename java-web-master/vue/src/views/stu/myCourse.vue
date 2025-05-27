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
            <el-table-column prop="teacherName" label="商家姓名" ></el-table-column>
            <el-table-column prop="courseName" label="景点名"></el-table-column>
            <el-table-column prop="courseCode" label="景点代码" ></el-table-column>
            <el-table-column prop="day" label="时间" ></el-table-column>
            <el-table-column prop="time" label="时间" ></el-table-column>
            <el-table-column prop="begin" label="起始" ></el-table-column>
            <el-table-column prop="end" label="结束" ></el-table-column>
            <el-table-column prop="roomName" label="地点"></el-table-column>
            <el-table-column fixed="right" label="操作" width="150px">
                <template #default="scope">
                    <el-popconfirm title="您确定要退票吗?" @confirm="handleDelete(scope.row.id)">
                        <template #reference>
                            <el-button size="mini" type="danger">退票</el-button>
                        </template>
                    </el-popconfirm>
                </template>
            </el-table-column>
        </el-table>

    </div>

</template>

<script>

    import request from "../../utils/request";
    import Cookies from 'js-cookie';

    export default {
        name: "myCourse",
        data() {
            return {
                tableData: [],
                user: Cookies.get('user') ? JSON.parse(Cookies.get('user')) : {},
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
                request.get("/stu/mySearchList", { params: this.params }).then(res => {
                    if (res.code === '200'){
                        this.tableData = res.data;
                    } else {
                        alert(res.msg);
                    }
                })
            },

            reset() {
                this.params.courseCode = '';
                this.params.courseName = '';
                this.load();
            },

            handleDelete(id){
                request.delete('/stu/search/delete/' + id).then(res => {
                    if (res.code === '200'){
                        alert("退票成功");
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