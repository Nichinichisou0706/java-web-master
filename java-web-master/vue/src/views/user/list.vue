<template>
    <div>

        <!--搜索框-->
        <div style="margin-bottom: 15px">
            <el-input style="width: 240px" placeholder="请输入用户名" v-model="params.username"></el-input>
            <el-input style="width: 240px; margin-left: 5px" placeholder="请输入姓名" v-model="params.name"></el-input>
            <el-input style="width: 240px; margin-left: 5px; margin-right: 5px" placeholder="请输入联系方式" v-model="params.phone"></el-input>
            <el-select v-model="params.role" style="width: 150px" placeholder="请选择身份">
                <el-option label="请选择身份" value=""></el-option>
                <el-option label="用户" value="用户"></el-option>
                <el-option label="商家" value="商家"></el-option>
            </el-select>
            <el-button style="margin-left: 5px" type="primary" @click="load"><i class="el-icon-search"></i>&ensp;搜索</el-button>
            <el-button style="margin-left: 5px" type="warning" @click="reset"><i class="el-icon-refresh"></i>&ensp;重置</el-button>
        </div>

        <el-table :data="tableData" border stripe>
            <el-table-column prop="username" label="用户名"></el-table-column>
            <el-table-column prop="name" label="姓名" ></el-table-column>
            <el-table-column prop="sex" label="性别"></el-table-column>
            <el-table-column prop="phone" label="电话" ></el-table-column>
            <el-table-column prop="email" label="邮箱"></el-table-column>
            <el-table-column prop="role" label="身份"></el-table-column>
            <el-table-column prop="createTime" label="创建时间"></el-table-column>
            <el-table-column fixed="right" label="操作" width="300px">
                <template #default="scope">
                    <el-button size="mini" type="primary" @click="$router.push('/user/update?id=' + scope.row.id)">编辑</el-button>
                    &nbsp;
                    <el-popconfirm title="您确定要重置吗吗?" @confirm="handleSet(scope.row.id)">
                        <template #reference>
                            <el-button size="mini" type="success">重置密码</el-button>
                        </template>
                    </el-popconfirm>
                    &nbsp;
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

    </div>

</template>

<script>

    import request from "../../utils/request";

    export default {
        name: "list",
        data() {
            return {
                total: '',
                tableData: [],
                params: {
                    username: '',
                    name: '',
                    phone: '',
                    role: '',
                    pageNum: 1,
                    pageSize: 10,
                },
            }
        },
        //页面加载之后会调用
        created() {
            this.load();
        },
        methods: {

            load() {
                request.get("/user/page", { params: this.params }).then(res => {
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
                    name: '',
                    phone: '',
                    role: '',
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

            handleSet(id){
                request.post('/user/update/' + id).then(res => {
                    if (res.code === '200'){
                        this.$notify.success("重置密码成功");
                    } else {
                        alert(res.msg);
                    }
                })
            },

            handleDelete(id){
                request.delete('/user/delete/' + id).then(res => {
                    if (res.code === '200'){
                        alert("删除成功");
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