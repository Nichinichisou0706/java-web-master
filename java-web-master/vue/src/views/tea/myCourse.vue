<template>
    <div>

        <el-table :data="tableData" border stripe>
            <el-table-column prop="courseName" label="景点名"></el-table-column>
            <el-table-column prop="courseCode" label="景点代码" ></el-table-column>
            <el-table-column prop="day" label="时间" ></el-table-column>
            <el-table-column prop="time" label="时间" ></el-table-column>
            <el-table-column prop="begin" label="起始" ></el-table-column>
            <el-table-column prop="end" label="结束" ></el-table-column>
            <el-table-column prop="roomName" label="地点"></el-table-column>
            <el-table-column prop="now" label="人数"></el-table-column>
            <el-table-column fixed="right" label="操作" width="300px">
                <template #default="scope">
                    <el-button size="mini" type="primary" @click="record(scope.row)"> 商品录入 </el-button>
                    &nbsp;
                    <el-button size="mini" type="success" @click="educe(scope.row)"> 商品导出 </el-button>
                    &nbsp;
                    <el-button size="mini" type="primary" @click="manage(scope.row)"> 价格管理 </el-button>
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
                username: {},
                params: {
                    id: '',
                }
            }
        },

        //页面加载之后会调用
        created() {
            this.user = Cookies.get('user') ? JSON.parse(Cookies.get('user')) : {};
            this.username = this.user.username;
            this.load();
        },

        methods: {

            load() {
                request.get("/tea/myTeachList/" + this.username).then(res => {
                    if (res.code === '200'){
                        this.tableData = res.data;
                    } else {
                        alert(res.msg);
                    }
                })
            },

            record(row) {
                if (row.now === 0){
                    this.$notify.error("该景点无人选购哦，不需要进行管理~")
                } else {
                    this.$router.push('/tea/score?id=' + row.id);
                }
            },

            educe(row) {
                this.params.id = row.id;
                if (row.now === 0){
                    this.$notify.error("该景点无人选购哦，不需要导出~");
                } else {
                    request.get("/tea/export",{ params: this.params, responseType: 'blob'}).then((res) => {
                        const link = document.createElement('a');
                        const blob = new Blob([res], { type: 'application/vnd.ms-excel' });
                        link.style.display = 'none';
                        link.href = URL.createObjectURL(blob);
                        link.setAttribute('download', row.courseName +`商品.xlsx`);
                        document.body.appendChild(link);
                        link.click();
                        document.body.removeChild(link);
                    });
                }
            },

            manage(row){
                if (row.now === 0){
                    this.$notify.error("该景点无人选购哦，不需要进行管理~")
                } else {
                    this.$router.push('/tea/manageScore?id=' + row.id);
                }
            }
        }
    }
</script>

<style scoped>

</style>