<template>
    <div>

        <!--搜索框-->
        <div style="margin-bottom: 15px">
            <el-input style="width: 240px" placeholder="请输入景点名" v-model="params.courseName"></el-input>
            <el-input style="width: 240px; margin-left: 5px" placeholder="请输入景点代码" v-model="params.courseCode"></el-input>
            <el-button style="margin-left: 5px" type="primary" @click="load"><i class="el-icon-search"></i>&ensp;搜索</el-button>
            <el-button style="margin-left: 5px" type="warning" @click="reset"><i class="el-icon-refresh"></i>&ensp;重置</el-button>
        </div>

        <el-table :data="tableData" border stripe>
            <el-table-column prop="id" label="编号" ></el-table-column>
            <el-table-column prop="courseName" label="景点名" ></el-table-column>
            <el-table-column prop="courseCode" label="景点代码"></el-table-column>
            <el-table-column prop="begin" label="起始" ></el-table-column>
            <el-table-column prop="end" label="结束"></el-table-column>
            <el-table-column fixed="right" label="操作" width="350px">
                <template #default="scope">
                    <el-tooltip placement="top">
                        <div slot="content" v-if="scope.row.status === 1">购票退票权限已开放</div>
                        <div slot="content" v-if="scope.row.status === 0">购票退票权限已关闭</div>
                        <el-switch
                                v-model="scope.row.status"
                                active-color="#13ce66"
                                inactive-color="#ff4949"
                                @change="changeStatus(scope.row)"
                                :active-value="1"
                                :inactive-value="0" >
                        </el-switch>
                    </el-tooltip>
                    &nbsp;
                    <el-button size="mini" style="margin-left: 5px" type="primary" @click="$router.push('/course/update?id=' + scope.row.id)">编辑</el-button>
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
                    courseName: '',
                    courseCode: '',
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
                request.get("/course/page", { params: this.params }).then(res => {
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
                    courseName: '',
                    courseCode: '',
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

            changeStatus(row) {
                request.put('/course/change/' + row.id).then(res => {
                    if (res.code === '200'){
                        this.$notify.success("购买状态更新成功");
                        this.load();
                    } else {
                        this.$notify.error(res.msg);
                    }
                })
            },

            handleDelete(id){
                request.delete('/course/delete/' + id).then(res => {
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