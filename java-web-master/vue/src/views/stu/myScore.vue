<template>
    <div>

        <!--搜索框-->
        <div style="margin-bottom: 15px">
            <el-input style="width: 240px; margin-left: 5px" placeholder="请输入课程名" v-model="params.courseName"></el-input>
            <el-select v-model="params.level" style="margin-left: 5px; width: 100px" placeholder="请选择类型">
                <el-option label="全部" value=""></el-option>
                <el-option label="未成功" value="1"></el-option>
            </el-select>
            <el-button style="margin-left: 5px" type="primary" @click="load"><i class="el-icon-search"></i>&ensp;搜索</el-button>
            <el-button style="margin-left: 5px" type="success" @click="educe"><i class="el-icon-refresh"></i>&ensp;导出 </el-button>
        </div>

        <el-table :data="tableData" border stripe>
            <el-table-column prop="courseName" label="商品名"></el-table-column>
            <el-table-column prop="courseCode" label="商品代码" ></el-table-column>
            <el-table-column prop="teacherName" label="商家名" ></el-table-column>
            <el-table-column prop="score" :formatter="scoreFormat" label="总价" ></el-table-column>
        </el-table>

    </div>

</template>

<script>

    import request from "../../utils/request";
    import Cookies from 'js-cookie';

    export default {
        name: "myScore",
        data() {
            return {
                tableData: [],
                user: Cookies.get('user') ? JSON.parse(Cookies.get('user')) : {},
                params: {
                    username: '',
                    courseName:'',
                    level: '',
                },
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
                request.get("/stu/score",{ params: this.params }).then(res => {
                    if (res.code === '200'){
                        this.tableData = res.data;
                    } else {
                        alert(res.msg);
                    }
                })
            },

            educe() {
                request.get("/stu/export",{ params: this.params, responseType: 'blob'}).then((res) => {
                    const link = document.createElement('a');
                    const blob = new Blob([res], { type: 'application/vnd.ms-excel' });
                    link.style.display = 'none';
                    link.href = URL.createObjectURL(blob);
                    link.setAttribute('download', `商品.xlsx`);
                    document.body.appendChild(link);
                    link.click();
                    document.body.removeChild(link);
                });
            },

            scoreFormat(row){
              return row.score === -1 ? '未录入' : row.score;
            }

        }
    }
</script>

<style scoped>

</style>