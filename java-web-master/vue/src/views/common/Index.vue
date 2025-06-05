<template>
  <div>
    <!-- 轮播图 -->
    <el-carousel :interval="5000" arrow="always">
      <el-carousel-item v-for="(image, index) in carouselImages" :key="index">
        <img :src="image" alt="旅游图片" style="width: 100%; height: 400px; object-fit: cover;">
      </el-carousel-item>
    </el-carousel>

    <p>
        京城漫游，一触即达——探索古都魅力，乐享智慧旅程！
    </p>
    <p style="color:#42b983">
        Explore Beijing, One Tap Away — Discover Ancient Charms, Enjoy Smart Travel!
    </p>
    <br>
    <p>
        短暂的逃离西西弗斯式生活，让身心和时间属于自己。
    </p>
    <h2 style="color:#42b983;font-size: 40px;text-align: center">
        景点选购
    </h2>
    <!-- 新增景点预定搜索框 -->
    <div style="margin-bottom: 15px">
      <el-input style="width: 240px" placeholder="请输入景点代码" v-model="params.courseCode"></el-input>
      <el-input style="width: 240px; margin-left: 5px" placeholder="请输入景点名" v-model="params.courseName"></el-input>
      <el-button style="margin-left: 5px" type="primary" @click="load"><i class="el-icon-search"></i>&ensp;搜索</el-button>
    </div>

    <!-- 新增景点预定表格 -->
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
          <el-button size="mini" type="primary" v-if="scope.row.now < scope.row.max" @click="handleConfirm(scope.row.id)">选购</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 新增分页 -->
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
    <br>
    <div class="links-link">
      <a href="https://github.com/Nichinichisou0706/java-web-master.git" target="_blank">代码仓库</a>
    </div>
    <!-- 底部信息 -->
    <el-footer style="background-color: #333; color: white; text-align: center; padding: 20px;">
      <p>版权所有 &copy; 2024 京城漫游网站      联系电话: 152-6096-8634</p>
    </el-footer>
  </div>
</template>



<script>

import request from "../../utils/request";
import Cookies from 'js-cookie';

export default {
    name: "searchList",
    data() {
        return {
            // 轮播图图片地址
            carouselImages: [
            require('@/assets/banner1.jpeg'),
            require('@/assets/banner2.jpeg'),
            require('@/assets/banner3.jpeg'),
            require('@/assets/banner4.jpeg'),
            require('@/assets/banner5.jpeg'),
            require('@/assets/banner6.jpeg')
      ],
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
        handleConfirm(id) {
                const isConfirmed = confirm('确认要选购吗？');
                if (isConfirmed) {
                    this.handleEdit(id);
                }
            },
        handleEdit(id){
            this.searchCourseParams.teachId = id;
            this.searchCourseParams.studentId = this.user.username;
            request.post("/stu/add", this.searchCourseParams).then(res => {
                if (res.code === '200'){
                  alert("选购成功");
                  this.$router.push('/stu/myCourse'); // 跳转到 myCourse 页面
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