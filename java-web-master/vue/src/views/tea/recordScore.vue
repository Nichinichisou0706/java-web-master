<template>
    <div>
        <el-table :data="tableData" stripe border style="width: 450px">
            <el-table-column prop="studentId" label="用户名" ></el-table-column>
            <el-table-column prop="studentName" label="姓名"></el-table-column>
            <el-table-column prop="score" label="价格" :formatter="scoreFormat">
                <template slot-scope="scope">
                    <el-input
                        :formatter="scoreFormat"
                        @blur="maxLen = $event.target.value"
                        oninput="value = value.replace(/\D/g, '').replace(/^0{1,}/g, '').replace('-1')"
                        v-model="scope.row.score">
                    </el-input>
                </template>
            </el-table-column>
        </el-table>
        <el-button style="margin-top: 10px; margin-left: 5px" type="primary" @click="save">提交</el-button>
    </div>

</template>

<script>

    import request from "../../utils/request";
    import Cookies from 'js-cookie';

    export default {
        name: "recordScore",
        data() {
            return {
                user: Cookies.get('user') ? JSON.parse(Cookies.get('user')) : {},
                username: '',
                teachId: '',
                tableData: [],
                params: {
                    id: '',
                }
            }
        },
        created() {
            const id = this.$route.query.id;
            if (id === '' || id == null){
                this.$notify.warning("请从商品录入进入该页面哦~");
            } else {
                this.username = Cookies.get('user').username;
                this.params.id = id;
                this.load();
            }
        },
        methods: {
            load() {
                request.get("/tea/manageScore", { params: this.params }).then(res => {
                    if (res.code === '200'){
                        this.tableData = res.data;
                    } else {
                        alert(res.msg);
                    }
                })
            },
            save() {
                request.post("/tea/updateAll",this.tableData).then(res => {
                    if (res.code === '200'){
                        this.$notify.success("添加成功~");
                        this.load();
                    } else {
                        this.$notify.error(res.msg);
                    }
                })
            },
            scoreFormat(row){
                return row.score === -1 ? '尚未录入' : row.score;
            }
        }
    }
</script>

<style scoped>

</style>